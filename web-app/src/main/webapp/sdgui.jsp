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
</style>
</head>

<body onload="openTab(event, 'basic')">



	<div class="container">
		<h1>Test App for CZI Spatial Mapping Service</h1>
		<br />
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="com-sm-8">
				<div class="container" style="position: relative;">
					<div id="model" style="width: 100%;">
						<img src="images/cziModel8.jpg">
					</div>
				</div>
			</div>
			<div class="col-sm-2"></div>
		</div>
		<br />
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8" style="background-color: lavenderblush;">
				<!-- Tab links -->
				<div class="tab">
					<button class="tablinks" onclick="openTab(event, 'basic')">Basic
						SD</button>
					<button class="tablinks"
						onclick="openTab(event, 'landmark')">Landmark</button>
					<button class="tablinks"
						onclick="openTab(event, 'searchByPosition')">Not In Use</button>
					<button class="tablinks" onclick="openTab(event, 'searchByRange')">Not
						In Use</button>
				</div>
			</div>
			<div class="col-sm-2"></div>
		</div>
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-8" style="background-color: lavenderblush;">
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
						<option value="junk">junk value</option>
					</select>
					<!-- <input type="text" maxlength="15" size="19" id="componentId"> -->
					<br /> <br />
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
						<option value="junk">junk value</option>
					</select>
					<!-- <input type="text" maxlength="15" size="19" id="componentId"> -->
					<br /> <br />
					<button type="button"
						onclick="Query('searchByPosition', document.getElementById('landmarkId').value);">Query</button>

					<br /> <br />
				</div>				

				<textarea id="display" rows="15" style="width: 100%"></textarea>
			</div>
			<div class="col-sm-2"></div>
		</div>
	</div>

	<script>
		function updateModel(componentName) {
			switch(componentName) {
				case "anus":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_anus.jpg'>";
					break;
				case "rectum":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_rectum.jpg'>";
					break;
				case "sigmoid":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_sigmoid.jpg'>";
					break;
				case "descending":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_descending.jpg'>";
					break;
				case "transverse":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_transverse.jpg'>";
					break;
				case "ascending":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_ascending.jpg'>";
					break;
				case "caecum":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_caecum.jpg'>";
					break;
// landmark
				case "apr":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_apr.jpg'>";
					break;
				case "icv":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_icv.jpg'>";
					break;
				case "hf":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_hf.jpg'>";
					break;
				case "sf":
					document.getElementById("model").innerHTML = "<img src='images/cziModel8_sf.jpg'>";
					break;					
				default:
					document.getElementById("model").innerHTML = "<img src='images/cziModel8.jpg'>";
			}
		}

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
			document.getElementById("model").innerHTML = "<img src='images/cziModel8.jpg'>";			
			document.getElementById('display').innerHTML = "";
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					document.getElementById("display").innerHTML = this.responseText;
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
					case "apr" :
						position = 10;
						break;
					case "icv" :
						position = 150;
						break;
					case "hf" :
						position = 131;
						break;
					case "sf" :
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

		function QueryRange() {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					document.getElementById("display").innerHTML = this.responseText;
				}
				;
				if (this.readyState == 4 && this.status == 500) {
					document.getElementById("display").innerHTML = this.responseText;
				}
				;
			};
			xhttp.open("GET", "query/searchByRange/"
					+ document.getElementById("start").value + "/"
					+ document.getElementById("stop").value, true);
			xhttp.send();
		}
	</script>
</body>
</html>