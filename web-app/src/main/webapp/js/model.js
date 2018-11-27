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
	ctx.rect(analEnd * unit, can.height / 2, unit * 4, y_size);
	ctx.fill();

	ctx.fillText("anal", 148 * unit, (can.height / 2) - 100);
	ctx.fillText("canal", 148 * unit, (can.height / 2) - 80);
	ctx.fillText("rectum", 140 * unit, (can.height / 2) - 100);
	ctx.fillText("sigmoid", 114 * unit, (can.height / 2) - 100);
	ctx.fillText("descending", 82 * unit, (can.height / 2) - 100);
	ctx.fillText("transverse", 44 * unit, (can.height / 2) - 100);
	ctx.fillText("ascending", 12 * unit, (can.height / 2) - 100);
	ctx.fillText("cecum", 2 * unit, (can.height / 2) - 100);

	ctx.beginPath();
	ctx.fillStyle = "olive";
	ctx.rect(apr * unit, can.height / 2, unit, y_size);
	ctx.fillText("APR", apr * unit, (can.height / 2) - 40);
	ctx.fill();

	ctx.beginPath();
	ctx.fillStyle = "green";
	ctx.rect(icv * unit, can.height / 2, unit, y_size);
	ctx.fillText("ICV", unit, (can.height / 2) - 40);
	ctx.fill();

	ctx.beginPath();
	ctx.fillStyle = "teal";
	ctx.rect(hf * unit, can.height / 2, unit, y_size);
	ctx.fillText("HF", hf * unit, (can.height / 2) - 40);
	ctx.fill();

	ctx.beginPath();
	ctx.fillStyle = "olive";
	ctx.rect(sf * unit, can.height / 2, unit, y_size);
	ctx.fillText("SF", sf * unit, (can.height / 2) - 40);
	ctx.fill();
	
	ctx.beginPath();
	ctx.fillStyle = "purple";
	ctx.rect(149 * unit, can.height / 2, unit, y_size);
	ctx.fillText("anus", 148 * unit, (can.height / 2) - 40);
	ctx.fill();

	ctx.fillStyle = "black";
	ctx.fillText("0",  149 * unit, (can.height / 2) + 40);
	ctx.fillText("4", analEnd * unit, (can.height / 2) + 40);
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
	case "anal":
		ctx.beginPath();
		ctx.rect(analStart * unit, y, -(unit * 4), y_size);
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
		ctx.rect(transverseStart * unit + unit, y, -(unit * 51), y_size);
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
	case "anal_start":
		ctx.beginPath();
		ctx.rect(analStart * unit, y, -(unit * 3), y_size);
		ctx.fill();
		break;
	case "anal_middle":
		ctx.beginPath();
		ctx.rect(analStart * unit, y, -(unit * 4), y_size);
		ctx.fill();
		break;
	case "anal_end":
		ctx.beginPath();
		ctx.rect(analEnd * unit, y, (unit * 3), y_size);
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
	case "anal_distal":
		ctx.beginPath();
		ctx.rect(analStart * unit, y, -(unit * 2), y_size);
		ctx.fill();
		break;
	case "anal_proximal":
		ctx.beginPath();
		ctx.rect(analEnd * unit, y, (unit * 2), y_size);
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
		ctx.rect((transverseStart * unit) + unit, y, -(unit * 25), y_size);
		ctx.fill();
		break;
	case "transverse_proximal":
		ctx.beginPath();
		ctx.rect(transverseEnd * unit, y, (unit * 25), y_size);
		ctx.fill();
		break;
	case "ascending_distal":
		ctx.beginPath();
		ctx.rect((ascendingStart * unit) + unit, y, -(unit * 7.5), y_size);
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