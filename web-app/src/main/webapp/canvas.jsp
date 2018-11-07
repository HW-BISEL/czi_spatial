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
<title>Test Canvas</title>
<style>
body {
	font-family: Arial;
}

/* Style the tab */
.tab {
	overflow: hidden;
	border: 1px solid #ccc;
	background-color: #f1f1f1;
}

/* Style the buttons inside the tab */
.tab button {
	background-color: inherit;
	float: left;
	border: none;
	outline: none;
	cursor: pointer;
	padding: 14px 16px;
	transition: 0.3s;
	font-size: 17px;
}

/* Change background color of buttons on hover */
.tab button:hover {
	background-color: #ddd;
}

/* Create an active/current tablink class */
.tab button.active {
	background-color: #ccc;
}

/* Style the tab content */
.tabcontent {
	display: none;
	padding: 6px 12px;
	border: 1px solid #ccc;
	border-top: none;
}

table {
	align: centre;
	width: 100%;
}

th {
	background-color: #ADD8E6;
	padding: 15px;
	text-align: center;
	border-bottom: 1px solid #ddd;
}

td {
	padding: 15px;
	text-align: center;
	border-bottom: 1px solid #ddd;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}
</style>
</head>
<body onresize="resizeCanvas()" onload="reloadCanvas()">

	<div class="container">
		<h1>Test Canvas for CZI Spatial Descriptions</h1>
		<br />
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="com-sm-8">
			<h3>Click once on the model in place you wish to search.</h3>
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
			img.src = "http://localhost:8080/CZI/images/cziModel8_web.svg";
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

		can.addEventListener("click", function(event) {
			can = document.getElementById('modelCanvas');
			ctx = can.getContext('2d');
			var offsetX = 0, offsetY = 0

			if (can.offsetParent) {
				do {
					offsetX += can.offsetLeft;
					offsetY += can.offsetTop;
				} while ((can = can.offsetParent));
			}

			var x = event.pageX - offsetX;
			var y = event.pageY - offsetY;

			can = document.getElementById('modelCanvas');
			/* alert("X: " + x + "  Y:" + y + "  can.width: " + can.width); */
			var queryPos = Math.round(150 - ((x / can.width) * 150));
			alert("queryPos: " + queryPos);
			ctx.beginPath();
			ctx.arc(x, y, 5, 0, 2 * Math.PI, true);
			ctx.fillStyle = "red";
			ctx.fill();
			Query(queryPos);
		});

		function reloadCanvas() {
			resizeCanvas();
		};
		
		function Query(value) {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					processOutput(this.responseText);
				}
				;
				if (this.readyState == 4 && this.status == 500) {
					processOutput(this.responseText);
				}
				;
			};
			var url = "query/searchByPosition/" + value;
			xhttp.open("GET", url, true);
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