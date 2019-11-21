<!DOCTYPE html>
<html lang="en">
<head>
<title>CZI Spatial Mapping</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link href="stylesheet.css" rel="stylesheet" type="text/css">
</head>

<body onload="openTab(event, 'searchByImage')">



	<div class="container">
		<h1>Test App for CZI Spatial Mapping Service</h1>
		<br />
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8" style="background-color: lavenderblush;">
				<!-- Tab links -->
				<div class="tab">
					<!-- <button class="tablinks" onclick="openTab(event, 'searchByImage')">searchByImage</button> -->
					<button class="tablinks"
						onclick="openTab(event, 'searchByComponent')">searchByComponent</button>
					<button class="tablinks"
						onclick="openTab(event, 'searchByPosition')">searchByPosition</button>
					<button class="tablinks" onclick="openTab(event, 'searchByRange')">searchByRange</button>
					<button class="tablinks" onclick="openTab(event, 'mapping')">mapping</button>
				</div>
			</div>
			<div class="col-sm-2"></div>
		</div>
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8" style="background-color: lavenderblush;">
				<div id="searchByComponent" class="tabcontent">
					Enter the name of colon structure you are interested in: <select
						id="componentId">
						<option value="anal canal">anal canal</option>
						<option value="rectum">rectum</option>
						<option value="sigmoid">sigmoid</option>
						<option value="descending">descending</option>
						<option value="transverse">transverse</option>
						<option value="ascending">ascending</option>
						<option value="caecum">caecum</option>
						<option value="junk">junk value</option>
					</select>
					<!-- <input type="text" maxlength="15" size="19" id="componentId"> -->
					<br /> <br />
					<button type="button"
						onclick="Query('searchByComponent', document.getElementById('componentId').value);">Query</button>

					<br /> <br />
				</div>
				<div id="searchByPosition" class="tabcontent">
					Enter the position (as an integer) you are interested in between 0
					and 1500: <input type="text" maxlength="4" size="4" id="position">
					<br /> <br />
					<button type="button"
						onclick="Query('searchByPosition', document.getElementById('position').value);">Query</button>

					<br /> <br />
				</div>
				<div id="searchByRange" class="tabcontent">
					Enter the start & stop positions (as an integer) between 0 and 1500: <input type="text" maxlength="4"
						size="4" id="start"> to <input type="text" maxlength="4"
						size="4" id="stop"> <br /> <br />
					<button type="button" onclick="QueryRange();">Query</button>
					<br /> <br />
				</div>
				<div id="mapping" class="tabcontent">
					Mapping from human to mouse; enter a mouse point between 0 and
					1500: <input type="text" maxlength="3" size="4" id="mapPos">
					<br /><br />
					<button type="button"
						onclick="Query('mapping', document.getElementById('mapPos').value);">Query</button>

					<br /> <br />
				</div>
				<textarea id="display" rows="15" style="width: 100%"></textarea>
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
		function openTab(evt, tabName) {
			document.getElementById('display').innerHTML = "";
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
		function Query(operation, value) {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					document.getElementById("display").innerHTML = this.responseText;
				}
				;
				if (this.readyState == 4 && this.status != 200) {
					document.getElementById("display").innerHTML = this.responseText;
				}
				;
			};
			if (operation == 'mapping') {
				var url = "mapping/human/mouse?point="+value;
			} else {
				var url = "query/human/";
				if (operation == "searchByImage") {
					url += "searchByImage/" + value;
				} else if (operation == "searchByComponent") {
					url += "searchByComponent?component=" + value;
				} else if (operation == "searchByPosition") {
					url += "searchByPosition?point=" + value;
				}
			}
			xhttp.open("GET", url, true);
			xhttp.send();
		}
		function QueryRange() {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					document.getElementById("display").innerHTML = this.responseText;
				}
				;
				if (this.readyState == 4 && this.status != 200) {
					document.getElementById("display").innerHTML = this.responseText;
				}
				;
			};
			xhttp.open("GET", "query/human/searchByRange?point1="
					+ document.getElementById("start").value + "&point2="
					+ document.getElementById("stop").value, true);
			xhttp.send();
		}
	</script>
</body>
</html>