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
<body onload="openTab(event, 'basic'); resizeCanvas();"
	onresize="resizeCanvas();">

	<div class="container">
		<h1>Test App for CZI Spatial Descriptions</h1>
		<br />
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="com-sm-8">
				<canvas id="modelCanvas"
					style="position: relative; width: 100%; border: 1px solid #000000;" />
			</div>
		</div>
		<div class="col-sm-2"></div>
	</div>
	<br />
	<div class="row">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">
			<!-- Tab links -->
			<div class="tab">
				<button class="tablinks" onclick="openTab(event, 'basic')">Basic</button>
				<button class="tablinks" onclick="openTab(event, 'landmark')">Landmark</button>
				<button class="tablinks" onclick="openTab(event, 'near')">Near</button>
				<button class="tablinks" onclick="openTab(event, 'half')">Half</button>
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
					<!-- <option value="descending">descending</option>
						<option value="transverse">transverse</option>
						<option value="ascending">ascending</option>
						<option value="caecum">caecum</option>-->
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
					<!-- <option value="descending">descending</option>
						<option value="transverse">transverse</option>
						<option value="ascending">ascending</option>
						<option value="caecum">caecum</option>-->
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
					<option value="caecum">caecum</option>
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

			<div id="displayArea" style="width: 100%;"></div>
		</div>
		<div class="col-sm-2"></div>
	</div>
	<br />
	<div class="row">
		<div class="col-sm-2"></div>
		<div class="com-sm-8">
			<p>
				&copy; 2018 <a href="http://www.macs.hw.ac.uk/bisel">BISEL</a>, part
				of <a href="http://www.hw.ac.uk">Heriot-Watt University</a>.
				Development funded by <a
					href="https://www.chanzuckerberg.com/science/projects-hca">Chan
					Zuckerberg Initiative</a>.
			</p>
		</div>
		<div class="col-sm-2"></div>
	</div>
	</div>

	<script>
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

		function updateModel(componentName) {			
			can = document.getElementById('modelCanvas');
			ctx = can.getContext('2d');			
						
			ctx.fillStyle = "red";
			var unit = can.width / 150;
			var y = can.height/1.5;
			var y_size = 10;
			
			switch (componentName) {
			case "anus":
				ctx.beginPath();
				ctx.rect(can.width-4, can.height*0.475, -(unit*4), y_size);				
				ctx.fill();
				break;
			case "rectum":
				ctx.beginPath();
				ctx.rect((can.width-4)-(4*unit), can.height*0.475, -(unit*12), y_size);				
				ctx.fill();
				break;
			case "sigmoid":
				ctx.beginPath();
				ctx.rect((can.width-4)-(16*unit), can.height*0.475, -(unit*40), y_size);				
				ctx.fill();				
				break;
			case "descending":
				ctx.beginPath();
				ctx.rect((can.width-2)-(56*unit), can.height*0.475, -(unit*25), y_size);				
				ctx.fill();					
				break;
			case "transverse":
				ctx.beginPath();
				ctx.rect((can.width-1)-(81*unit), can.height*0.475, -(unit*50), y_size);				
				ctx.fill();					
				break;
			case "ascending":
				ctx.beginPath();
				ctx.rect((can.width)-(131*unit), can.height*0.475, -(unit*15), y_size);				
				ctx.fill();					
				break;
			case "caecum":
				ctx.beginPath();
				ctx.rect((can.width)-(146*unit)+1, can.height*0.475, -(unit*4), y_size);				
				ctx.fill();	
				break;
			// landmark
			case "apr":
				ctx.beginPath();
				ctx.rect((can.width)-(12*unit), can.height*0.475, (unit*3), y_size);				
				ctx.fill();
				break;
			case "icv":
				ctx.beginPath();
				ctx.rect(2, can.height*0.475, (unit*2), y_size);				
				ctx.fill();
				break;
			case "hf":
				ctx.beginPath();
				ctx.rect((can.width)-(133*unit), can.height*0.475, (unit*4), y_size);				
				ctx.fill();
				break;
			case "sf":
				ctx.beginPath();
				ctx.rect((can.width)-(83*unit), can.height*0.475, (unit*4), y_size);				
				ctx.fill();				
				break;
			// near
			case "anus_start":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_anus_start.jpg'>";
				break;
			case "anus_middle":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_anus.jpg'>";
				break;
			case "anus_end":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_anus_end.jpg'>";
				break;
			case "rectum_start":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_rectum_start.jpg'>";
				break;
			case "rectum_middle":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_rectum_middle.jpg'>";
				break;
			case "rectum_end":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_rectum_end.jpg'>";
				break;
			case "sigmoid_start":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_sigmoid_start.jpg'>";
				break;
			case "sigmoid_middle":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_sigmoid_middle.jpg'>";
				break;
			case "sigmoid_end":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_sigmoid_end.jpg'>";
				break;
			// half
			case "anus_distal":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_anus_distal.jpg'>";
				break;
			case "anus_proximal":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_anus_proximal.jpg'>";
				break;
			case "rectum_distal":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_rectum_distal.jpg'>";
				break;
			case "rectum_proximal":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_rectum_proximal.jpg'>";
				break;
			case "sigmoid_distal":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_sigmoid_distal.jpg'>";
				break;
			case "sigmoid_proximal":
				document.getElementById("model").innerHTML = "<img src='images/cziModel8_sigmoid_proximal.jpg'>";
				break;
			default:
				document.getElementById("model").innerHTML = "<img src='images/cziModel8.jpg'>";
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
			evt.currentTarget.className += " active";
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

		function Query(operation, value) {
			document.getElementById('displayArea').innerHTML = "<br />";
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					processOutput(this.responseText);
				}
				;
				if (this.readyState == 4 && this.status == 500) {
					document.getElementById("display").innerHTML = this.responseText;
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
			}

			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					processOutput(this.responseText);
				}
				;
				if (this.readyState == 4 && this.status == 500) {
					document.getElementById("display").innerHTML = this.responseText;
				}
				;
			};
			xhttp.open("GET",
					"query/searchByRange/" + startPos + "/" + stopPos, true);
			xhttp.send();
			//updateModel(image);
		}

		function QueryRange(place, nearBy) {
			document.getElementById('displayArea').innerHTML = "<br />";

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
			} else if (nearBy == 'caecum') {
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
				if (this.readyState == 4 && this.status == 200) {
					processOutput(this.responseText);
				}
				;
				if (this.readyState == 4 && this.status == 500) {
					document.getElementById("display").innerHTML = this.responseText;
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