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
<script src="js/sdgui3.js"></script>
<script src="js/tabs.js"></script>
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
				<canvas id="modelCanvas" height="90"
					style="position: relative; width: 100%;" />
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
						<option value="anal">anal canal</option>
						<option value="rectum">rectum</option>
						<option value="sigmoid">sigmoid</option>
						<option value="descending">descending</option>
						<option value="transverse">transverse</option>
						<option value="ascending">ascending</option>
						<option value="cecum">caecum</option>
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
						<option value="anal">anal canal</option>
						<option value="rectum">rectum</option>
						<option value="sigmoid">sigmoid</option>
						<option value="descending">descending</option>
						<option value="transverse">transverse</option>
						<option value="ascending">ascending</option>
						<option value="cecum">caecum</option>
					</select> <br /> <br />
					<button type="button"
						onclick="QueryRange(document.getElementById('place').value, document.getElementById('nearBy').value);">Query</button>

					<br /> <br />
				</div>

				<div id="basic" class="tabcontent">
					Enter the name of colon structure you are interested in: <select
						id="componentId">
						<option value="anal">anal canal</option>
						<option value="rectum">rectum</option>
						<option value="sigmoid">sigmoid</option>
						<option value="descending">descending</option>
						<option value="transverse">transverse</option>
						<option value="ascending">ascending</option>
						<option value="cecum">caecum</option>
					</select> <br /> <br />
					<button type="button"
						onclick="Query('searchByComponent', document.getElementById('componentId').value);">Query</button>

					<br /> <br />
				</div>

				<div id="landmark" class="tabcontent">
					Enter the name of colon landmark you are interested in: <select
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
		document.getElementById('modelCanvas').addEventListener('click', mouseClicked, true);
	</script>
</body>
</html>