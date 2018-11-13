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
<title>SD Gui with SVG</title>
</head>
<!--  openTab(event, 'basic');  -->
<body onload="resizeCanvas();" onresize="resizeCanvas();">

	<div class="container">
		<h1>Test App for CZI Spatial Descriptions</h1>
		<br />
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="com-sm-8" id="canvasDiv">
				<canvas id="modelCanvas"
					style="position: relative; width: 100%; border: 1px solid #000000;" />
			</div>
		</div>
		<div class="col-sm-2"></div>
	</div>
	<br />
	</div>
	<br />
	<div class="container">
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8">
				<!-- Tab links -->
				<div class="tab">
					<button class="tablinks" onclick="openTab(event, 'basic')">Basic</button>
					<button class="tablinks" onclick="openTab(event, 'landmark')">Landmark</button>
					<button class="tablinks" onclick="openTab(event, 'near')">Near</button>
					<button class="tablinks" onclick="openTab(event, 'half')">Half</button>
					<button class="tablinks" onclick="openTab(event, 'click')">Click
						2 Query</button>
				</div>
			</div>
			<div class="col-sm-2"></div>
		</div>
		<div class="row">
			<div class="col-sm-2"></div>

			<div class="col-sm-8">

				<div id="half" class="tabcontent">
					The ROI is <em>near</em> the <select id="axis">
						<option value="proximal">proximal</option>
						<option value="distal">distal</option>
					</select> half of the <select id="componentHalf">
						<option value="anus">anus</option>
						<option value="rectum">rectum</option>
						<option value="sigmoid">sigmoid</option>
						<option value="descending">descending</option>
						<option value="transverse">transverse</option>
						<option value="ascending">ascending</option>
						<option value="cecum">cecum</option>
					</select> <br /> <br />
					<button type="button"
						onclick="QueryHalf(document.getElementById('axis').value, document.getElementById('componentHalf').value);">Query</button>

					<br /> <br />
				</div>


				<div id="near" class="tabcontent">
					The ROI is <em>near</em> the <select id="place">
						<option value="start">start</option>
						<option value="middle">middle</option>
						<option value="end">end</option>
					</select> of the <select id="nearBy">
						<option value="anus">anus</option>
						<option value="rectum">rectum</option>
						<option value="sigmoid">sigmoid</option>
						<option value="descending">descending</option>
						<option value="transverse">transverse</option>
						<option value="ascending">ascending</option>
						<option value="cecum">cecum</option>
					</select> <br /> <br />
					<button type="button"
						onclick="QueryRange(document.getElementById('place').value, document.getElementById('nearBy').value);">Query</button>

					<br /> <br />
				</div>

				<div id="basic" class="tabcontent">
					Enter the name of colon structure you are interested in: <select
						id="componentId">
						<option value="anus">anus</option>
						<option value="rectum">rectum</option>
						<option value="sigmoid">sigmoid</option>
						<option value="descending">descending</option>
						<option value="transverse">transverse</option>
						<option value="ascending">ascending</option>
						<option value="cecum">cecum</option>
					</select> <br /> <br />
					<button type="button"
						onclick="Query('searchByComponent', document.getElementById('componentId').value);">Query</button>

					<br /> <br />
				</div>

				<div id="landmark" class="tabcontent">
					Enter the name of colon structure you are interested in: <select
						id="landmarkId">
						<option value="apr">apr</option>
						<option value="icv">icv</option>
						<option value="hf">hf</option>
						<option value="sf">sf</option>
					</select> <br /> <br />
					<button type="button"
						onclick="Query('searchByPosition', document.getElementById('landmarkId').value);">Query</button>

					<br /> <br />
				</div>

				<div id="click" class="tabcontent">
					<p>
						Point queries require a single mouse click on the model to search
						in that location. Range queries need two clicks, and the search
						occurs between the clicks. <select id="clickType"
							onchange="resizeCanvas()">
							<option value="" default></option>
							<option value="point">point</option>
							<option value="range">range</option>
						</select>
					</p>
					<br /> <br />

				</div>

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
		const anusStart = 150;
		const anusEnd = 146;
		const rectumStart = anusEnd;
		const rectumMiddle = 140;
		const rectumEnd = 134;
		const sigmoidStart = rectumEnd;
		const sigmoidMiddle = 114;
		const sigmoidEnd = 94;
		const descendingStart = sigmoidEnd;
		const descendingMiddle = 81.5;
		const descendingEnd = 69;
		const transverseStart = descendingEnd;
		const transverseMiddle = 44;
		const transverseEnd = 19;
		const ascendingStart = transverseEnd;
		const ascendingMiddle = 11.5;
		const ascendingEnd = 4;
		const cecumStart = ascendingEnd;
		const cecumMiddle = 2;
		const cecumEnd = 0;

		const apr = 140;
		const icv = 0;
		const hf = transverseEnd;
		const sf = transverseStart;

		const mouse = {
			x : 0,
			y : 0, // coordinates
			lastX : 0,
			lastY : 0, // last frames mouse position 
		}

		var clickQuery = false;
		var lastX = 0;

		document.getElementById('modelCanvas').addEventListener('click',
				mouseClicked, true);

		function mouseClicked(event) {
			if (clickQuery) {
				var clickTypeSelected = document.getElementById('clickType').value;
				if (clickTypeSelected == '') {
					alert("Please use the listbox to select a type of query.");
				} else {
					if (clickTypeSelected == 'point' || lastX == 0) {
						resizeCanvas();
						document.getElementById('displayArea').innerHTML = "<br />";
					}
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
					ctx.arc(mouse.x, mouse.y, 5, 0, 2 * Math.PI, true);
					ctx.fillStyle = "red";
					ctx.fill();

					if (clickTypeSelected == 'point') {
						QueryBySingleClick(queryPos);
					} else if (lastX == 0) {
						lastX = queryPos;
					} else if (lastX != 0) {
						if (lastX < queryPos) {
							QueryByDoubleClick(lastX, queryPos);
						} else {
							QueryByDoubleClick(queryPos, lastX);
						}
						lastX = 0;
					}
				}

			} else {
				alert("Switch to the 'Click 2 Query' tab");
			}
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

		function drawModel() {
			can = document.getElementById('modelCanvas');
			ctx = can.getContext('2d');
			var unit = can.width / 150;
			var y_size = 10;
			ctx.font = "20px Georgia";
			ctx.textAlign = "center";
			ctx.lineWidth = 0.5;
			ctx.beginPath();
			ctx.rect(cecumEnd * unit, can.height / 2, unit * 4, y_size);
			ctx.fill();
			ctx.beginPath()
			ctx.moveTo(ascendingEnd * unit, can.height / 2 + 5);
			ctx.lineTo(ascendingStart * unit, can.height / 2 + 5);
			ctx.stroke();
			ctx.beginPath();
			ctx.rect(transverseEnd * unit, can.height / 2, unit * 50, y_size);
			ctx.fill();
			ctx.beginPath();
			ctx.moveTo(descendingEnd * unit, can.height / 2 + 5);
			ctx.lineTo(descendingStart * unit, can.height / 2 + 5);
			ctx.stroke();
			ctx.beginPath();
			ctx.rect(sigmoidEnd * unit, can.height / 2, unit * 40, y_size);
			ctx.fill();
			ctx.beginPath();
			ctx.moveTo(rectumEnd * unit, can.height / 2 + 5);
			ctx.lineTo(rectumStart * unit, can.height / 2 + 5);
			ctx.stroke();
			ctx.beginPath();
			ctx.rect(anusEnd * unit, can.height / 2, unit * 4, y_size);
			ctx.fill();

			ctx.fillText("anus", 147 * unit, (can.height / 2) - 80);
			ctx.fillText("rectum", 140 * unit, (can.height / 2) - 100);
			ctx.fillText("sigmod", 114 * unit, (can.height / 2) - 100);
			ctx.fillText("descending", 82 * unit, (can.height / 2) - 100);
			ctx.fillText("transverse", 44 * unit, (can.height / 2) - 100);
			ctx.fillText("ascending", 12 * unit, (can.height / 2) - 100);
			ctx.fillText("cecum", 4 * unit, (can.height / 2) - 80);

			ctx.beginPath();
			ctx.fillStyle = "blue";
			ctx.rect(apr * unit, can.height / 2, unit, y_size);
			ctx.fillText("APR", apr * unit, (can.height / 2) - 40);
			ctx.fill();

			ctx.beginPath();
			ctx.fillStyle = "green";
			ctx.rect(icv * unit, can.height / 2, unit, y_size);
			ctx.fillText("ICV", 2 * unit, (can.height / 2) - 40);
			ctx.fill();

			ctx.beginPath();
			ctx.fillStyle = "lime";
			ctx.rect(hf * unit, can.height / 2, unit, y_size);
			ctx.fillText("HF", hf * unit, (can.height / 2) - 40);
			ctx.fill();

			ctx.beginPath();
			ctx.fillStyle = "purple";
			ctx.rect(sf * unit, can.height / 2, unit, y_size);
			ctx.fillText("SF", sf * unit, (can.height / 2) - 40);
			ctx.fill();

			ctx.fillStyle = "black";
			ctx.fillText("4", anusEnd * unit, (can.height / 2) + 40);
			ctx.fillText("16", rectumEnd * unit, (can.height / 2) + 40);
			ctx.fillText("56", sigmoidEnd * unit, (can.height / 2) + 40);
			ctx.fillText("81", descendingEnd * unit, (can.height / 2) + 40);
			ctx.fillText("131", transverseEnd * unit, (can.height / 2) + 40);
			ctx.fillText("146", ascendingEnd * unit, (can.height / 2) + 40);
		}

		function updateModel(componentName) {
			can = document.getElementById('modelCanvas');
			ctx = can.getContext('2d');

			ctx.fillStyle = "red";
			var unit = can.width / 150;
			var y = can.height / 2;
			var y_size = 10;

			switch (componentName) {
			case "anus":
				ctx.beginPath();
				ctx.rect(anusStart * unit, y, -(unit * 4), y_size);
				ctx.fill();
				break;
			case "rectum":
				ctx.beginPath();
				ctx.rect(rectumStart * unit, y, -(unit * 12), y_size);
				ctx.fill();
				break;
			case "sigmoid":
				ctx.beginPath();
				ctx.rect(sigmoidStart * unit, y, -(unit * 40), y_size);
				ctx.fill();
				break;
			case "descending":
				ctx.beginPath();
				ctx.rect(descendingStart * unit, y, -(unit * 25), y_size);
				ctx.fill();
				break;
			case "transverse":
				ctx.beginPath();
				ctx.rect(transverseStart * unit, y, -(unit * 51), y_size);
				ctx.fill();
				break;
			case "ascending":
				ctx.beginPath();
				ctx.rect(ascendingStart * unit, y, -(unit * 16), y_size);
				ctx.fill();
				break;
			case "cecum":
				ctx.beginPath();
				ctx.rect(cecumStart * unit, y, -(unit * 4), y_size);
				ctx.fill();
				break;
			// landmark
			case "apr":
				ctx.beginPath();
				ctx.rect(apr * unit, y, (unit * 3), y_size);
				ctx.rect(apr * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			case "icv":
				ctx.beginPath();
				ctx.rect(icv * unit, y, (unit * 3), y_size);
				ctx.fill();
				break;
			case "hf":
				ctx.beginPath();
				ctx.rect(hf * unit, y, (unit * 3), y_size);
				ctx.rect(hf * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			case "sf":
				ctx.beginPath();
				ctx.rect(sf * unit, y, (unit * 3), y_size);
				ctx.rect(sf * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			// near
			case "anus_start":
				ctx.beginPath();
				ctx.rect(anusStart * unit, y, -(unit * 3), y_size);
				ctx.fill();
				break;
			case "anus_middle":
				ctx.beginPath();
				ctx.rect(anusStart * unit, y, -(unit * 4), y_size);
				ctx.fill();
				break;
			case "anus_end":
				ctx.beginPath();
				ctx.rect(anusEnd * unit, y, (unit * 3), y_size);
				ctx.fill();
				break;
			case "rectum_start":
				ctx.beginPath();
				ctx.rect(rectumStart * unit, y, -(unit * 3), y_size);
				ctx.fill();
				break;
			case "rectum_middle":
				ctx.beginPath();
				ctx.rect(rectumMiddle * unit, y, (unit * 3), y_size);
				ctx.rect(rectumMiddle * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			case "rectum_end":
				ctx.beginPath();
				ctx.rect(rectumEnd * unit, y, (unit * 3), y_size);
				ctx.fill();
				break;
			case "sigmoid_start":
				ctx.beginPath();
				ctx.rect(sigmoidStart * unit, y, -(unit * 3), y_size);
				ctx.fill();
				break;
			case "sigmoid_middle":
				ctx.beginPath();
				ctx.rect(sigmoidMiddle * unit, y, (unit * 3), y_size);
				ctx.rect(sigmoidMiddle * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			case "sigmoid_end":
				ctx.beginPath();
				ctx.rect(sigmoidEnd * unit, y, (unit * 3), y_size);
				ctx.fill();
				break;
			case "descending_start":
				ctx.beginPath();
				ctx.rect(descendingStart * unit, y, -(unit * 3), y_size);
				ctx.fill();
				break;
			case "descending_middle":
				ctx.beginPath();
				ctx.rect(descendingMiddle * unit, y, (unit * 3), y_size);
				ctx.rect(descendingMiddle * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			case "descending_end":
				ctx.beginPath();
				ctx.rect(descendingEnd * unit, y, (unit * 3), y_size);
				ctx.fill();
				break;
			case "transverse_start":
				ctx.beginPath();
				ctx.rect((sf * unit) + unit, y, -(unit * 3), y_size);
				ctx.fill();
				break;
			case "transverse_middle":
				ctx.beginPath();
				ctx.rect(transverseMiddle * unit, y, (unit * 3), y_size);
				ctx.rect(transverseMiddle * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			case "transverse_end":
				ctx.beginPath();
				ctx.rect(transverseEnd * unit, y, (unit * 3), y_size);
				ctx.fill();
				break;
			case "ascending_start":
				ctx.beginPath();
				ctx.rect(ascendingStart * unit + unit, y, -(unit * 3), y_size);
				ctx.fill();
				break;
			case "ascending_middle":
				ctx.beginPath();
				ctx.rect(ascendingMiddle * unit, y, (unit * 3), y_size);
				ctx.rect(ascendingMiddle * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			case "ascending_end":
				ctx.beginPath();
				ctx.rect(ascendingEnd * unit, y, (unit * 3), y_size);
				ctx.fill();
				break;
			case "cecum_start":
				ctx.beginPath();
				ctx.rect(cecumStart * unit, y, -(unit * 3), y_size);
				ctx.fill();
				break;
			case "cecum_middle":
				ctx.beginPath();
				ctx.rect(cecumMiddle * unit, y, (unit * 3), y_size);
				ctx.rect(cecumMiddle * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			case "cecum_end":
				ctx.beginPath();
				ctx.rect(cecumEnd * unit, y, (unit * 3), y_size);
				ctx.fill();
				break;

			// half
			case "anus_distal":
				ctx.beginPath();
				ctx.rect(anusStart * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			case "anus_proximal":
				ctx.beginPath();
				ctx.rect(anusEnd * unit, y, (unit * 2), y_size);
				ctx.fill();
				break;
			case "rectum_distal":
				ctx.beginPath();
				ctx.rect(rectumStart * unit, y, -(unit * 6), y_size);
				ctx.fill();
				break;
			case "rectum_proximal":
				ctx.beginPath();
				ctx.rect(rectumEnd * unit, y, (unit * 6), y_size);
				ctx.fill();
				break;
			case "sigmoid_distal":
				ctx.beginPath();
				ctx.rect(sigmoidStart * unit, y, -(unit * 20), y_size);
				ctx.fill();
				break;
			case "sigmoid_proximal":
				ctx.beginPath();
				ctx.rect(sigmoidEnd * unit, y, (unit * 20), y_size);
				ctx.fill();
				break;
			case "descending_distal":
				ctx.beginPath();
				ctx.rect(descendingStart * unit, y, -(unit * 12.5), y_size);
				ctx.fill();
				break;
			case "descending_proximal":
				ctx.beginPath();
				ctx.rect(descendingEnd * unit, y, (unit * 12.5), y_size);
				ctx.fill();
				break;
			case "transverse_distal":
				ctx.beginPath();
				ctx.rect((transverseStart * unit) + unit, y, -(unit * 25),
						y_size);
				ctx.fill();
				break;
			case "transverse_proximal":
				ctx.beginPath();
				ctx.rect(transverseEnd * unit, y, (unit * 25), y_size);
				ctx.fill();
				break;
			case "ascending_distal":
				ctx.beginPath();
				ctx.rect((ascendingStart * unit) + unit, y, -(unit * 7.5),
						y_size);
				ctx.fill();
				break;
			case "ascending_proximal":
				ctx.beginPath();
				ctx.rect(ascendingEnd * unit, y, (unit * 7.5), y_size);
				ctx.fill();
				break;
			case "cecum_distal":
				ctx.beginPath();
				ctx.rect(cecumStart * unit, y, -(unit * 2), y_size);
				ctx.fill();
				break;
			case "cecum_proximal":
				ctx.beginPath();
				ctx.rect(cecumEnd * unit, y, (unit * 2), y_size);
				ctx.fill();
				break;
			default:
				alert("Something has gone wrong!");
			}

		}

		function openTab(evt, tabName) {
			document.getElementById('displayArea').innerHTML = "<br />";
			var i, tabcontent, tablinks;
			tabcontent = document.getElementsByClassName("tabcontent");
			for (i = 0; i < tabcontent.length; i++) {
				tabcontent[i].style.display = "none";
			}
			tablinks = document.getElementsByClassName("tablinks");
			for (i = 0; i < tablinks.length; i++) {
				tablinks[i].className = tablinks[i].className.replace(
						" active", "");
			}
			document.getElementById(tabName).style.display = "block";
			if (tabName == 'click') {
				clickQuery = true;
			} else {
				clickQuery = false;
			}
			evt.currentTarget.className += " active";
			resizeCanvas();
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

		function QueryBySingleClick(value) {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4
						&& (this.status == 200 || this.status == 500)) {
					processOutput(this.responseText);
				}
				;
			};
			var url = "query/searchByPosition/" + value;
			xhttp.open("GET", url, true);
			xhttp.send();
		}

		function QueryByDoubleClick(start, stop) {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4
						&& (this.status == 200 || this.status == 500)) {
					processOutput(this.responseText);
				}
				;
			};
			var url = "query/searchByRange/" + start + "/" + stop;
			xhttp.open("GET", url, true);
			xhttp.send();
		}

		function Query(operation, value) {
			resizeCanvas();
			document.getElementById('displayArea').innerHTML = "<br />";
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					processOutput(this.responseText);
				}
				;
				if (this.readyState == 4 && this.status == 500) {
					document.getElementById("displayArea").innerHTML = this.responseText;
				}
				;
			};
			var url = "query/";
			if (operation == "searchByComponent") {
				url += "searchByComponent/" + value;
			} else if (operation == "searchByPosition") {
				var position = 200;
				switch (value) {
				case "apr":
					position = 10;
					break;
				case "icv":
					position = 150;
					break;
				case "hf":
					position = 131;
					break;
				case "sf":
					position = 81;
					break;
				default:
					alert('Invalid landmark selected!');
				}
				url += "searchByPosition/" + position;
			}
			xhttp.open("GET", url, true);
			xhttp.send();
			updateModel(value);
		}

		function QueryHalf(axis, component) {
			document.getElementById('displayArea').innerHTML = "<br />";
			resizeCanvas();
			var startPos = 200;
			var stopPos = 201;
			var image = component + "_" + axis;

			if (component == 'anus') {
				if (axis == 'distal') {
					startPos = 0;
					stopPos = 2;
				} else if (axis == 'proximal') {
					startPos = 2;
					stopPos = 4;
				}
			} else if (component == 'rectum') {
				if (axis == 'distal') {
					startPos = 4;
					stopPos = 10;
				} else if (axis == 'proximal') {
					// ± 3
					startPos = 10;
					stopPos = 16;
				}
			} else if (component == 'sigmoid') {
				if (axis == 'distal') {
					startPos = 16;
					stopPos = 36;
				} else if (axis == 'proximal') {
					startPos = 36;
					stopPos = 56;
				}
			} else if (component == 'descending') {
				if (axis == 'distal') {
					startPos = 16;
					stopPos = 36;
				} else if (axis == 'proximal') {
					startPos = 36;
					stopPos = 56;
				}
			} else if (component == 'transverse') {
				if (axis == 'distal') {
					startPos = 16;
					stopPos = 36;
				} else if (axis == 'proximal') {
					startPos = 36;
					stopPos = 56;
				}
			} else if (component == 'ascending') {
				if (axis == 'distal') {
					startPos = 16;
					stopPos = 36;
				} else if (axis == 'proximal') {
					startPos = 36;
					stopPos = 56;
				}
			} else if (component == 'cecum') {
				if (axis == 'distal') {
					startPos = 16;
					stopPos = 36;
				} else if (axis == 'proximal') {
					startPos = 36;
					stopPos = 56;
				}
			}

			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200
						|| this.readyState == 4 && this.status == 500) {
					processOutput(this.responseText);
				}
				;
			};
			xhttp.open("GET",
					"query/searchByRange/" + startPos + "/" + stopPos, true);
			xhttp.send();
			updateModel(image);
		}

		function QueryRange(place, nearBy) {
			document.getElementById('displayArea').innerHTML = "<br />";
			resizeCanvas();
			var startPos = 200;
			var stopPos = 201;
			var image = nearBy + "_" + place;

			if (nearBy == 'anus') {
				if (place == 'start') {
					startPos = 0;
					stopPos = startPos + 3;
				} else if (place == 'middle') {
					// the whole thing
					startPos = 0;
					stopPos = 4;
				} else if (place == 'end') {
					stopPos = 4;
					startPos = stopPos - 3;
				}
			} else if (nearBy == 'rectum') {
				if (place == 'start') {
					startPos = 4;
					stopPos = startPos + 3;
				} else if (place == 'middle') {
					// ± 3
					startPos = 7;
					stopPos = 13;
				} else if (place == 'end') {
					stopPos = 16;
					startPos = stopPos - 3;
				}
			} else if (nearBy == 'sigmoid') {
				if (place == 'start') {
					startPos = 16;
					stopPos = startPos + 3;
				} else if (place == 'middle') {
					startPos = 33;
					stopPos = 39;
				} else if (place == 'end') {
					stopPos = 56;
					startPos = stopPos - 3;
				}
			} else if (nearBy == 'descending') {
				if (place == 'start') {
					startPos = 56;
					stopPos = startPos + 3;
				} else if (place == 'middle') {
					// not quite the middle
					startPos = 65;
					stopPos = 71;
				} else if (place == 'end') {
					stopPos = 81;
					startPos = stopPos - 3;
				}
			} else if (nearBy == 'transverse') {
				if (place == 'start') {
					startPos = 81;
					stopPos = startPos + 3;
				} else if (place == 'middle') {
					startPos = 103;
					stopPos = 109;
				} else if (place == 'end') {
					stopPos = 131;
					startPos = stopPos - 3;
				}
			} else if (nearBy == 'ascending') {
				if (place == 'start') {
					startPos = 131;
					stopPos = startPos + 3;
				} else if (place == 'middle') {
					startPos = 135;
					stopPos = 141;
				} else if (place == 'end') {
					stopPos = 146;
					startPos = stopPos - 3;
				}
			} else if (nearBy == 'cecum') {
				if (place == 'start') {
					startPos = 146;
					stopPos = startPos + 3;
				} else if (place == 'middle') {
					// whole thing
					startPos = 146;
					stopPos = 150;
				} else if (place == 'end') {
					stopPos = 150;
					startPos = stopPos - 3;
				}
			}

			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200
						|| this.readyState == 4 && this.status == 500) {
					processOutput(this.responseText);
				}
				;
			};
			xhttp.open("GET",
					"query/searchByRange/" + startPos + "/" + stopPos, true);
			xhttp.send();
			updateModel(image);
		}
	</script>

</body>
</html>