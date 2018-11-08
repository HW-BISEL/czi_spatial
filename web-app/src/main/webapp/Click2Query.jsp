<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="stylesheet.css" rel="stylesheet" type="text/css">
<title>Click to query</title>
</head>
<body onresize="resizeCanvas()" onload="resizeCanvas()">

	<div class="container">
		<h1>Click to query CZI Spatial Model</h1>
		<br />
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="com-sm-8">
				<h3>
					Choose type of query: I want to search for a <select id="queryType"
						onselect="showInstructions()" onchange="showInstructions()">
						<option default value="">&nbsp;</option>
						<option value="point">point</option>
						<option value="range">range</option>
					</select>.
					<button type="button" onclick="reset()">Reset</button>
				</h3>
			</div>
			<div class="col-sm-2"></div>
		</div>
		<div class="row" id="pointInstructions" style="display: none;">
			<div class="col-sm-2"></div>
			<div class="com-sm-8">
				<h4>Instructions: click once on the model to search in that
					location.</h4>
			</div>
			<div class="col-sm-2"></div>
		</div>
		<div class="row" id="rangeInstructions" style="display: none;">
			<div class="col-sm-2"></div>
			<div class="com-sm-8">
				<h4>Instructions: click twice on the model to search between
					the points indicated by the clicks.</h4>
			</div>
			<div class="col-sm-2"></div>
		</div>
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="com-sm-8">
				<canvas id="modelCanvas"
					style="position: relative; width: 100%; border: 1px solid #000000;">
						</canvas>
			</div>
			<div class="col-sm-2"></div>
		</div>
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="com-sm-8">
				<div id="displayArea" style="width: 100%;"></div>
			</div>
			<div class="col-sm-2"></div>
		</div>
		<br />
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="com-sm-8">
				<p>
					&copy; 2018 <a href="http://www.macs.hw.ac.uk/bisel">BISEL</a>,
					part of <a href="http://www.hw.ac.uk">Heriot-Watt University</a>.
					Development funded by <a
						href="https://www.chanzuckerberg.com/science/projects-hca">Chan
						Zuckerberg Initiative</a>.
				</p>
			</div>
			<div class="col-sm-2"></div>
		</div>
	</div>

	<script>
		var can = document.getElementById('modelCanvas');
		var ctx = can.getContext('2d');
		var click1 = 151;
		var disable = true;

		function reset() {
			document.getElementById('displayArea').innerHTML = "";
			resizeCanvas();
			click1 = 151;
			disable = false;
			document.getElementById('pointInstructions').style.display = 'none';
			document.getElementById('rangeInstructions').style.display = 'none';
			document.getElementById('queryType').value = "";
		}

		function showInstructions() {
			document.getElementById('displayArea').innerHTML = "";
			resizeCanvas();
			click1 = 151;
			disable = false;			
			var choice = document.getElementById('queryType');

			if (choice.value == 'point') {
				document.getElementById('pointInstructions').style.display = 'block';
				document.getElementById('rangeInstructions').style.display = 'none';
			} else if (choice.value == 'range') {
				document.getElementById('pointInstructions').style.display = 'none';
				document.getElementById('rangeInstructions').style.display = 'block';
			} else {
				document.getElementById('pointInstructions').style.display = 'none';
				document.getElementById('rangeInstructions').style.display = 'none';
			}
		}

		function drawModel() {
			can = document.getElementById('modelCanvas');
			ctx = can.getContext('2d');
			var img = new Image();
			img.onload = function() {
				var imageAspectRatio = img.width / img.height;
				var canvasAspectRatio = can.width / can.height;
				var renderableHeight, renderableWidth, xStart, yStart;

				// If image's aspect ratio is less than canvas's we fit on height
				// and place the image centrally along width
				if (imageAspectRatio < canvasAspectRatio) {
					renderableHeight = can.height;
					renderableWidth = img.width
							* (renderableHeight / img.height);
					xStart = (can.width - renderableWidth) / 2;
					yStart = 0;
				}

				// If image's aspect ratio is greater than canvas's we fit on width
				// and place the image centrally along height
				else if (imageAspectRatio > canvasAspectRatio) {
					renderableWidth = can.width
					renderableHeight = img.height
							* (renderableWidth / img.width);
					xStart = 0;
					yStart = (can.height - renderableHeight) / 2;
				}

				// Happy path - keep aspect ratio
				else {
					renderableHeight = can.height;
					renderableWidth = can.width;
					xStart = 0;
					yStart = 0;
				}

				ctx.drawImage(img, xStart, yStart, renderableWidth,
						renderableHeight);

			}
			img.src = "images/cziModel8_web.svg";
		}

		function resizeCanvas() {
			var dpi = window.devicePixelRatio;
			can = document.getElementById('modelCanvas');
			ctx = can.getContext('2d');
			var style_height = +getComputedStyle(can)
					.getPropertyValue("height").slice(0, -2);
			var style_width = +getComputedStyle(can).getPropertyValue("width")
					.slice(0, -2);
			can.setAttribute('height', style_height * dpi);
			can.setAttribute('width', style_width * dpi);
			drawModel();
		};

		const mouse = {
			x : 0,
			y : 0, // coordinates
			lastX : 0,
			lastY : 0, // last frames mouse position 
		}

		can
				.addEventListener(
						"click",
						function(event) {
							if (disable == false && document.getElementById("queryType").value != "") {
								can = document.getElementById('modelCanvas');
								var bounds = can.getBoundingClientRect();
								// get the mouse coordinates, subtract the canvas top left and any scrolling
								mouse.x = event.pageX - bounds.left - scrollX;
								mouse.y = event.pageY - bounds.top - scrollY;
								// first normalize the mouse coordinates from 0 to 1 (0,0) top left
								// off canvas and (1,1) bottom right by dividing by the bounds width and height
								mouse.x /= bounds.width;
								mouse.y /= bounds.height;
								// then scale to canvas coordinates by multiplying the normalized coords with the canvas resolution
								mouse.x *= can.width;
								mouse.y *= can.height;								

								// draw point								
								ctx = can.getContext('2d');
								var queryPos = Math
										.round(150 - ((mouse.x / can.width) * 150));
								ctx.beginPath();
								ctx.arc(mouse.x, mouse.y, 5, 0, 2 * Math.PI,
										true);
								ctx.fillStyle = "red";
								ctx.fill();

								if (document.getElementById('queryType').value == 'point') {
									Query(queryPos);
									disable = true;
								} else if (document.getElementById('queryType').value == 'range'
										&& click1 == 151) {
									click1 = queryPos;
								} else if (document.getElementById('queryType').value == 'range'
										&& click1 != 151) {
									if (queryPos > click1) {
										QueryRange(click1, queryPos);
									} else {
										QueryRange(queryPos, click1);
									}
									disable = true;
								}
							} else {
								alert("Please chose a query using the listbox at the top of the screen.");
							}
						});

		function Query(value) {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && (this.status == 200 || this.status == 500)) {
					processOutput(this.responseText);
				};
			};
			var url = "query/searchByPosition/" + value;
			xhttp.open("GET", url, true);
			xhttp.send();
		}

		function QueryRange(start, stop) {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && (this.status == 200 || this.status == 500)) {
					processOutput(this.responseText);
				};
			};
			xhttp.open("GET", "query/searchByRange/" + start + "/" + stop, true);
			xhttp.send();
		}

		function processOutput(queryResult) {
			var output = "<br /><h3>Results</h3><table><tr><th>image id</th><th>position</th></tr>";
			var obj = JSON.parse(queryResult);
			for (i in obj.result) {
				output += "<tr><td>" + obj.result[i].imageId + "</td><td>"
						+ obj.result[i].position + "</td></tr>";
			}
			output += "</table>";
			document.getElementById("displayArea").innerHTML = output;
		}
	</script>
</body>
</html>