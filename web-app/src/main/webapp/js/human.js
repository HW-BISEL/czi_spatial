const analStart = 1500;
const analEnd = 1460;
const rectumStart = analEnd;
const rectumMiddle = 1400;
const rectumEnd = 1340;
const sigmoidStart = rectumEnd;
const sigmoidMiddle = 1140;
const sigmoidEnd = 940;
const descendingStart = sigmoidEnd;
const descendingMiddle = 815;
const descendingEnd = 690;
const transverseStart = descendingEnd;
const transverseMiddle = 440;
const transverseEnd = 190;
const ascendingStart = transverseEnd;
const ascendingMiddle = 115;
const ascendingEnd = 40;
const cecumStart = ascendingEnd;
const cecumMiddle = 20;
const cecumEnd = 0;

const apr = 1400;
const icv = 0;
const hf = transverseEnd;
const sf = transverseStart;
const anus = 1500;

const h_length = anus - icv;

function drawHuman() {
	can = document.getElementById('modelCanvas');
	ctx = can.getContext('2d');
	var unit = (can.width - 1) / anus;
	var y_size = 20;
	ctx.font = "20px Georgia";
	ctx.textAlign = "center";
	ctx.lineWidth = 1;
	
	ctx.beginPath();	
	ctx.rect(cecumEnd * unit, can.height / 2, unit * (cecumStart - cecumEnd), y_size);
	ctx.fill();

	ctx.beginPath();	
	ctx.rect(ascendingEnd * unit, can.height / 2, unit * (ascendingStart - ascendingEnd), y_size);
	ctx.stroke();
	
	ctx.beginPath();
	ctx.rect(transverseEnd * unit, can.height / 2, unit * (transverseStart - transverseEnd), y_size);
	ctx.fill();
	
	ctx.beginPath();
	ctx.rect(descendingEnd * unit, can.height / 2, unit * (descendingStart - descendingEnd), y_size);
	ctx.stroke();
		
	ctx.beginPath();
	ctx.rect(sigmoidEnd * unit, can.height / 2, unit * (sigmoidStart - sigmoidEnd), y_size);
	ctx.fill();
	
	ctx.beginPath();
	ctx.rect(rectumEnd * unit, can.height / 2, (rectumStart - rectumEnd) * unit, y_size);
	ctx.stroke();
	
	ctx.beginPath();
	ctx.rect(analEnd * unit, can.height / 2, unit * (analStart - analEnd), y_size);
	ctx.fill();

	ctx.fillText("anal", 1480 * unit, (can.height / 2) - 40);
	ctx.fillText("canal", 1480 * unit, (can.height / 2) - 20);
	ctx.fillText("rectum", 1400 * unit, (can.height / 2) - 40);
	ctx.fillText("sigmoid", 1140 * unit, (can.height / 2) - 40);
	ctx.fillText("descending", 820 * unit, (can.height / 2) - 40);
	ctx.fillText("transverse", 440 * unit, (can.height / 2) - 40);
	ctx.fillText("ascending", 120 * unit, (can.height / 2) - 40);
	ctx.fillText("caecum", 30 * unit, (can.height / 2) - 40);
//
	ctx.beginPath();
	ctx.fillStyle = "purple";
	ctx.strokeStyle = "purple";
	ctx.moveTo(1, can.height / 2);
	ctx.lineTo(1, can.height / 2+40);	
	ctx.fillText("ICV", 15, (can.height / 2) + (y_size * 2) + 20);
	ctx.stroke();	

	ctx.beginPath();
	ctx.fillStyle = "blue";
	ctx.strokeStyle = "blue";
	ctx.moveTo(hf * unit, can.height / 2);
	ctx.lineTo(hf * unit, can.height / 2+40);
	ctx.fillText("HF", hf * unit, (can.height / 2) + (y_size * 2) + 20);
	ctx.stroke();

	ctx.beginPath();
	ctx.fillStyle = "teal";
	ctx.strokeStyle = "teal";
	ctx.moveTo(sf * unit, can.height / 2);
	ctx.lineTo(sf * unit, can.height / 2+40);	
	ctx.fillText("SF", sf * unit, (can.height / 2) + (y_size * 2) + 20);
	ctx.stroke();
	
	ctx.beginPath();
	ctx.fillStyle = "green";
	ctx.strokeStyle = "green";
	ctx.moveTo(apr * unit, can.height / 2);
	ctx.lineTo(apr * unit, can.height / 2+40);	
	ctx.fillText("APR", apr * unit, (can.height / 2) + (y_size * 2) + 20);
	ctx.stroke();	
	
	ctx.beginPath();
	ctx.fillStyle = "olive";
	ctx.strokeStyle = "olive";
	ctx.rect((anus * unit) - 1, can.height / 2, unit, y_size * 2);
	ctx.fillText("anus", (anus * unit) - 20, (can.height / 2) + (y_size * 2) + 20);
	ctx.fill();

//	ctx.fillStyle = "black";
//	ctx.fillText("0",  149 * unit, (can.height / 2) + 60);
//	ctx.fillText("4", analEnd * unit, (can.height / 2) + 60);
//	ctx.fillText("16", rectumEnd * unit, (can.height / 2) + 60);
//	ctx.fillText("56", sigmoidEnd * unit, (can.height / 2) + 60);
//	ctx.fillText("81", descendingEnd * unit, (can.height / 2) + 60);
//	ctx.fillText("131", transverseEnd * unit, (can.height / 2) + 60);
//	ctx.fillText("146", ascendingEnd * unit, (can.height / 2) + 60);
//	ctx.fillText("150", unit, (can.height / 2) + 60);
}

function updateHuman(componentName) {
	can = document.getElementById('modelCanvas');
	ctx = can.getContext('2d');	
	ctx.fillStyle = "red";
	var unit = (can.width-1) / h_length;
	var y = can.height / 2;
	var y_size = 20;

	switch (componentName) {
	case "anal canal":
		ctx.beginPath();
		ctx.rect(analStart * unit, y, -(analStart - analEnd) * unit, y_size);
		ctx.fill();
		break;
	case "rectum":
		ctx.beginPath();
		ctx.rect(rectumStart * unit, y, -(rectumStart - rectumEnd) * unit, y_size);
		ctx.fill();
		break;
	case "sigmoid":
		ctx.beginPath();
		ctx.rect(sigmoidStart * unit, y, -(sigmoidStart - sigmoidEnd) * unit, y_size);
		ctx.fill();
		break;
	case "descending":
		ctx.beginPath();
		ctx.rect(descendingStart * unit, y, -(descendingStart - descendingEnd) * unit, y_size);
		ctx.fill();
		break;
	case "transverse":
		ctx.beginPath();
		ctx.rect(transverseStart * unit + unit, y, -(transverseStart - transverseEnd) * unit, y_size);
		ctx.fill();
		break;
	case "ascending":
		ctx.beginPath();
		ctx.rect(ascendingStart * unit, y, -(ascendingStart - ascendingEnd) * unit, y_size);
		ctx.fill();
		break;
	case "cecum":
		ctx.beginPath();
		ctx.rect(cecumStart * unit, y, -(cecumStart - cecumEnd) * unit, y_size);
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