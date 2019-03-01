const mouse = {
	x : 0,
	y : 0, // coordinates
	lastX : 0,
	lastY : 0, // last frames mouse position
	guiX : 0, // used for swapping last x
	queryPos : 0,
	queryPos2 : 0
}

var clickQuery = false;
var lastX = 0;

$.getScript("js/human.js");
$.getScript("js/scale.js");
$.getScript("js/mouse.js");
$.getScript("js/rat.js");

function resizeCanvas() {
	var dpi = window.devicePixelRatio;
	var can = document.getElementById('modelCanvas');
	var ctx = can.getContext('2d');
	var style_height = +getComputedStyle(can).getPropertyValue("height").slice(
			0, -2);
	var style_width = +getComputedStyle(can).getPropertyValue("width").slice(0,
			-2);

	can.setAttribute('height', style_height * dpi);
	can.setAttribute('width', style_width * dpi);
	drawHuman();

	can = document.getElementById('scaleCanvas');
	can.setAttribute('height', style_height * dpi);
	can.setAttribute('width', style_width * dpi);
	drawScale();

	can = document.getElementById('modelCanvas2');
	can.setAttribute('height', style_height * dpi);
	can.setAttribute('width', style_width * dpi);
	if(document.getElementById('species2').value == 'MOUSE') { 
		drawMouse();
	} else {
		drawRat();
	}
};

function mouseClicked(event) {
	if (clickQuery) {
		var clickTypeSelected = document.getElementById('clickType').value;
		if ($(model2row).is(':hidden') && clickTypeSelected == '') {
			alert("Please use the listbox to select a type of query.");
		} else {
			if (clickTypeSelected == 'point' || lastX == 0
					|| $(model2row).is(':visible')) {
				resizeCanvas();
				document.getElementById('displayArea').innerHTML = "<br />";
			}
			var can = document.getElementById('modelCanvas');
			var ctx = can.getContext('2d');
			var bounds = can.getBoundingClientRect();
			// get the mouse coordinates, subtract the canvas top left and any
			// scrolling
			mouse.x = event.pageX - bounds.left - scrollX;
			mouse.y = event.pageY - bounds.top - scrollY;
			// first normalize the mouse coordinates from 0 to 1 (0,0) top left
			// off canvas and (1,1) bottom right by dividing by the bounds width
			// and height
			mouse.x /= bounds.width;
			mouse.y /= bounds.height;
			// then scale to canvas coordinates by multiplying the normalized
			// coords with the canvas resolution
			mouse.x *= can.width;
			mouse.y *= can.height;

			queryPos = Math
					.round(h_length - ((mouse.x / can.width) * h_length));

			// draw line
			var can = document.getElementById('modelCanvas');
			var ctx = can.getContext('2d');
			ctx.beginPath();
			ctx.strokeStyle = "red";
			ctx.lineWidth = 10;
			ctx.moveTo(mouse.x, (can.height / 2));
			ctx.lineTo(mouse.x, (can.height / 2) + 40);
			ctx.stroke();

			if ($(model2row).is(':visible')) {
				if(document.getElementById('species2').value == 'MOUSE') {
					Query4Mapping('human', 'mouse');
				} else {
					Query4Mapping('human', 'rat');
				}
			} else if (clickTypeSelected == 'point') {
				QueryBySingleClick('human');
			} else if (lastX == 0) {
				lastX = queryPos;
			} else if (lastX != 0) {
				if (lastX < queryPos) {
					QueryByDoubleClick('human');
				} else {
					QueryByDoubleClick('human');
				}
				guiX = lastX;
				lastX = 0;
			}
		}
	} else {
		alert("Switch to the 'Click 2 Query' tab");
	}
}

function drawMousePoint(point, colour) {
	var can = document.getElementById('modelCanvas2');
	var m_unit = (can.width - 1) / m_length;
	var ctx = can.getContext('2d');
	ctx.beginPath();
	if (colour === "green") {
		ctx.strokeStyle = "green";
	} else {
		ctx.strokeStyle = "red";
	}
	ctx.lineWidth = 10;
	ctx.moveTo((m_anus - point) * m_unit, (can.height / 2));
	ctx.lineTo((m_anus - point) * m_unit, (can.height / 2) + 40);
	ctx.stroke();
}

function drawRatPoint(point, colour) {
	var can = document.getElementById('modelCanvas2');
	var r_unit = (can.width - 1) / r_length;
	var ctx = can.getContext('2d');
	ctx.beginPath();
	if (colour === "green") {
		ctx.strokeStyle = "green";
	} else {
		ctx.strokeStyle = "red";
	}
	ctx.lineWidth = 10;
	ctx.moveTo((r_anus - point) * r_unit, (can.height / 2));
	ctx.lineTo((r_anus - point) * r_unit, (can.height / 2) + 40);
	ctx.stroke();
}

function processOutput(queryResult) {
	var output = '';
	var obj = JSON.parse(queryResult);
	if (obj.status === 'fail') {
		output = "<div><b>MESSAGE:<p>" + obj.message + "</p></b></div>";
	} else {
		if (clickQuery || $(model2row).is(':visible')) {
			var clickTypeSelected = document.getElementById('clickType').value;
			if (clickTypeSelected == 'point' && $(model2row).is(':hidden')) {
				output = "<br /><h3>Results for point: "
						+ queryPos
						+ "</h3><table><tr><th>image id</th><th>position</th></tr>";
			} else if (clickTypeSelected == 'range'
					&& $(model2row).is(':hidden')) {
				// range
				var rangePos1 = queryPos;
				var rangePos2 = guiX;
				if (rangePos1 < rangePos2) {
					output = "<br /><h3>Results for range: "
							+ rangePos1
							+ " to "
							+ rangePos2
							+ "</h3><table><tr><th>image id</th><th>position</th></tr>";
				} else {
					output = "<br /><h3>Results for range: "
							+ rangePos2
							+ " to "
							+ rangePos1
							+ "</h3><table><tr><th>image id</th><th>position</th></tr>";
				}
				guiX = 0;
			} else {
				var species2 = "";
				if(document.getElementById('species2').value == 'MOUSE') {
					species2 = 'Mouse';
				} else {
					species2 = 'Rat';
				}
				output = "<br/><p><span style=\"color: green;\">Green</span> line in the " + species2 + " model represents the proportional mapping based on whole colon.<br /> <span style=\"color: red;\">Red</span> line is the mapping based on sectional proportional distances.</p>" 
						+ "<br /><h3>Human point: " + queryPos
						+ "mm maps to " + species2+" point: " + queryPos2
						+ "mm</h3><p>Results for the " + species2 + " are: </p> <br />"
						+ "<table><tr><th>image id</th><th>position</th></tr>";

				if(document.getElementById('species2').value == 'MOUSE') {
					drawMousePoint(queryPos2, 'red');
				} else {
					drawRatPoint(queryPos2, 'red');
				}
			
				queryPos2 = 0;
			}
		} else {			
			output = "<h3>Results</h3><table><tr><th>image id</th><th>position</th></tr>";
		}
		var obj = JSON.parse(queryResult);
		for (i in obj.result) {
			output += "<tr><td>" + obj.result[i].imageId + "</td><td>"
					+ obj.result[i].position + "</td></tr>";
		}
		output += "</table>";
	}
	document.getElementById("displayArea").innerHTML = output;
}

function Query4Mapping(species1, species2) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && (this.status == 200 || this.status == 500)) {
			var obj = JSON.parse(this.responseText);
			queryPos2 = obj.result.position2;
			var pdPoint = obj.result.pdWholeColon;
			if(document.getElementById('species2').value == 'MOUSE') {
				drawMousePoint(pdPoint, 'green');
				QueryBySingleClick2('mouse');
			} else {
				drawRatPoint(pdPoint, 'green');
				QueryBySingleClick2('rat');
			}									
		}
		;
	};
	var url = "mapping/" + species1 + "/" + species2 + "?point=" + queryPos;
	xhttp.open("GET", encodeURI(url), true);
	xhttp.send();
}

function QueryBySingleClick2(species) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && (this.status == 200 || this.status == 500)) {
			processOutput(this.responseText);
		}
		;
	};
	var url = "query/" + species + "/searchByPosition?point=" + queryPos2;
	xhttp.open("GET", encodeURI(url), true);
	xhttp.send();
}

function QueryBySingleClick(species) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && (this.status == 200 || this.status == 500)) {
			processOutput(this.responseText);
		}
		;
	};
	var url = "query/" + species + "/searchByPosition?point=" + queryPos;
	xhttp.open("GET", encodeURI(url), true);
	xhttp.send();
}

function QueryByDoubleClick(species) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && (this.status == 200 || this.status == 500)) {
			processOutput(this.responseText);
		}
		;
	};
	var url = "query/" + species + "/searchByRange?point1=" + lastX
			+ "&point2=" + queryPos;
	xhttp.open("GET", encodeURI(url), true);
	xhttp.send();
}

function Query(operation, value, species) {
	resizeCanvas();
	document.getElementById('displayArea').innerHTML = "<br />";
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && (this.status == 200 || this.status == 500)) {
			processOutput(this.responseText);
		}
		;
	};
	var url = "query/";
	if (operation == "searchByComponent") {
		url += species + "/searchByComponent?component=" + value;
		// } else if (operation == "searchByPosition") {
		// var position = 2000;
		// switch (value) {
		// case "apr":
		// position = 100;
		// break;
		// case "icv":
		// position = 1500;
		// break;
		// case "hf":
		// position = 1310;
		// break;
		// case "sf":
		// position = 810;
		// break;
		// default:
		// alert('Invalid landmark selected!');
		// }
		// url += species+"/searchByPosition?point=" + position;
	} else {
		alert("Unknow search type");
	}
	xhttp.open("GET", encodeURI(url), true);
	xhttp.send();
	updateHuman(value);
}

// function QueryHalf(axis, component) {
// document.getElementById('displayArea').innerHTML = "<br />";
// resizeCanvas();
// var startPos = 200;
// var stopPos = 201;
// var image = component + "_" + axis;
//
// if (component == 'anal') {
// if (axis == 'distal') {
// startPos = 0;
// stopPos = 2;
// } else if (axis == 'proximal') {
// startPos = 2;
// stopPos = 4;
// }
// } else if (component == 'rectum') {
// if (axis == 'distal') {
// startPos = 4;
// stopPos = 10;
// } else if (axis == 'proximal') {
// // ± 3
// startPos = 10;
// stopPos = 16;
// }
// } else if (component == 'sigmoid') {
// if (axis == 'distal') {
// startPos = 16;
// stopPos = 36;
// } else if (axis == 'proximal') {
// startPos = 36;
// stopPos = 56;
// }
// } else if (component == 'descending') {
// if (axis == 'distal') {
// startPos = 16;
// stopPos = 36;
// } else if (axis == 'proximal') {
// startPos = 36;
// stopPos = 56;
// }
// } else if (component == 'transverse') {
// if (axis == 'distal') {
// startPos = 16;
// stopPos = 36;
// } else if (axis == 'proximal') {
// startPos = 36;
// stopPos = 56;
// }
// } else if (component == 'ascending') {
// if (axis == 'distal') {
// startPos = 16;
// stopPos = 36;
// } else if (axis == 'proximal') {
// startPos = 36;
// stopPos = 56;
// }
// } else if (component == 'cecum') {
// if (axis == 'distal') {
// startPos = 16;
// stopPos = 36;
// } else if (axis == 'proximal') {
// startPos = 36;
// stopPos = 56;
// }
// }
//
// var xhttp = new XMLHttpRequest();
// xhttp.onreadystatechange = function() {
// if (this.readyState == 4 && this.status == 200 || this.readyState == 4
// && this.status == 500) {
// processOutput(this.responseText);
// }
// ;
// };
// xhttp.open("GET", "query/searchByRange/" + startPos + "/" + stopPos, true);
// xhttp.send();
// updateModel(image);
// }

// function QueryRange(place, nearBy) {
// document.getElementById('displayArea').innerHTML = "<br />";
// resizeCanvas();
// var startPos = 200;
// var stopPos = 201;
// var image = nearBy + "_" + place;
//
// if (nearBy == 'anal') {
// if (place == 'start') {
// startPos = 0;
// stopPos = startPos + 3;
// } else if (place == 'middle') {
// // the whole thing
// startPos = 0;
// stopPos = 4;
// } else if (place == 'end') {
// stopPos = 4;
// startPos = stopPos - 3;
// }
// } else if (nearBy == 'rectum') {
// if (place == 'start') {
// startPos = 4;
// stopPos = startPos + 3;
// } else if (place == 'middle') {
// // ± 3
// startPos = 7;
// stopPos = 13;
// } else if (place == 'end') {
// stopPos = 16;
// startPos = stopPos - 3;
// }
// } else if (nearBy == 'sigmoid') {
// if (place == 'start') {
// startPos = 16;
// stopPos = startPos + 3;
// } else if (place == 'middle') {
// startPos = 33;
// stopPos = 39;
// } else if (place == 'end') {
// stopPos = 56;
// startPos = stopPos - 3;
// }
// } else if (nearBy == 'descending') {
// if (place == 'start') {
// startPos = 56;
// stopPos = startPos + 3;
// } else if (place == 'middle') {
// // not quite the middle
// startPos = 65;
// stopPos = 71;
// } else if (place == 'end') {
// stopPos = 81;
// startPos = stopPos - 3;
// }
// } else if (nearBy == 'transverse') {
// if (place == 'start') {
// startPos = 81;
// stopPos = startPos + 3;
// } else if (place == 'middle') {
// startPos = 103;
// stopPos = 109;
// } else if (place == 'end') {
// stopPos = 131;
// startPos = stopPos - 3;
// }
// } else if (nearBy == 'ascending') {
// if (place == 'start') {
// startPos = 131;
// stopPos = startPos + 3;
// } else if (place == 'middle') {
// startPos = 135;
// stopPos = 141;
// } else if (place == 'end') {
// stopPos = 146;
// startPos = stopPos - 3;
// }
// } else if (nearBy == 'cecum') {
// if (place == 'start') {
// startPos = 146;
// stopPos = startPos + 3;
// } else if (place == 'middle') {
// // whole thing
// startPos = 146;
// stopPos = 150;
// } else if (place == 'end') {
// stopPos = 150;
// startPos = stopPos - 3;
// }
// }
//
// var xhttp = new XMLHttpRequest();
// xhttp.onreadystatechange = function() {
// if (this.readyState == 4 && this.status == 200 || this.readyState == 4
// && this.status == 500) {
// processOutput(this.responseText);
// }
// ;
// };
// xhttp.open("GET", "query/searchByRange/" + startPos + "/" + stopPos, true);
// xhttp.send();
// updateModel(image);
// }
