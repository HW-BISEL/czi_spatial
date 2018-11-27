const analStart = 150;
const analEnd = 146;
const rectumStart = analEnd;
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
const anus = 150;

const mouse = {
	x : 0,
	y : 0, // coordinates
	lastX : 0,
	lastY : 0, // last frames mouse position
	guiX: 0 // lastX for GUI line not query
}

var clickQuery = false;
var lastX = 0;

$.getScript("js/model.js");

function resizeCanvas() {
	var dpi = window.devicePixelRatio;
	can = document.getElementById('modelCanvas');
	ctx = can.getContext('2d');
	var style_height = +getComputedStyle(can).getPropertyValue("height").slice(
			0, -2);
	var style_width = +getComputedStyle(can).getPropertyValue("width").slice(0,
			-2);
	can.setAttribute('height', style_height * dpi);
	can.setAttribute('width', style_width * dpi);
	drawModel();
};

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

			var queryPos = Math.round(150 - ((mouse.x / can.width) * 150));
			
			// draw point
//			ctx = can.getContext('2d');	
//			ctx.beginPath();
//			ctx.arc(mouse.x, mouse.y, 5, 0, 2 * Math.PI, true);
//			ctx.fillStyle = "red";
//			ctx.fill();
			
			// draw line
			ctx.beginPath();
			ctx.strokeStyle = "red";
			ctx.lineWidth = 10;
			ctx.moveTo(mouse.x, (can.height / 2));
			ctx.lineTo(mouse.x, (can.height / 2)+40);
			ctx.stroke();

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
				guiX = lastX;
				lastX = 0;
			}
		}
	} else {
		alert("Switch to the 'Click 2 Query' tab");
	}
}

function processOutput(queryResult) {
	var output = '';
	if (clickQuery) {
		var clickTypeSelected = document.getElementById('clickType').value;
		if (clickTypeSelected == 'point') {
			var queryPos = Math.round(150 - ((mouse.x / can.width) * 150));
			output = "<br /><h3>Results for point: " + queryPos + "</h3><table><tr><th>image id</th><th>position</th></tr>";			
		} else {
			// range
			var rangePos1 = Math.round(150 - ((mouse.x / can.width) * 150));
			var rangePos2 = guiX;
			if(rangePos1 < rangePos2) {
				output = "<br /><h3>Results for range: "+ rangePos1 + " to " + rangePos2 + "</h3><table><tr><th>image id</th><th>position</th></tr>";
			} else {
				output = "<br /><h3>Results for range: "+ rangePos2 + " to " + rangePos1 + "</h3><table><tr><th>image id</th><th>position</th></tr>";
			}
			guiX = 0;
		}
	} else { 
		output = "<br /><h3>Results</h3><table><tr><th>image id</th><th>position</th></tr>";
	}
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
		if (this.readyState == 4 && (this.status == 200 || this.status == 500)) {
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
		if (this.readyState == 4 && (this.status == 200 || this.status == 500)) {
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

	if (component == 'anal') {
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
		if (this.readyState == 4 && this.status == 200 || this.readyState == 4
				&& this.status == 500) {
			processOutput(this.responseText);
		}
		;
	};
	xhttp.open("GET", "query/searchByRange/" + startPos + "/" + stopPos, true);
	xhttp.send();
	updateModel(image);
}

function QueryRange(place, nearBy) {
	document.getElementById('displayArea').innerHTML = "<br />";
	resizeCanvas();
	var startPos = 200;
	var stopPos = 201;
	var image = nearBy + "_" + place;

	if (nearBy == 'anal') {
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
		if (this.readyState == 4 && this.status == 200 || this.readyState == 4
				&& this.status == 500) {
			processOutput(this.responseText);
		}
		;
	};
	xhttp.open("GET", "query/searchByRange/" + startPos + "/" + stopPos, true);
	xhttp.send();
	updateModel(image);
}