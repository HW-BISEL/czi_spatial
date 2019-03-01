const r_cecumEnd = 0;
const r_proximalEnd = 70;
const r_rectumEnd = 170;
const r_analEnd = 240;
const r_anus = 250;
const r_icv = 0;

const r_length = r_anus - r_icv;

function drawRat() {
	can = document.getElementById('modelCanvas2');
	ctx = can.getContext('2d');
	var unit = (can.width - 1) / r_anus;
	var y_size = 20;
	ctx.font = "20px Georgia";
	ctx.textAlign = "center";
	ctx.lineWidth = 1;
		
	ctx.beginPath();
	ctx.rect(r_cecumEnd * unit, can.height / 2, unit * (r_proximalEnd - r_cecumEnd), y_size);
	ctx.stroke();

	ctx.beginPath();
	ctx.rect(r_proximalEnd * unit, can.height / 2, unit * (r_rectumEnd - r_proximalEnd), y_size);
	ctx.fill();		
	
	ctx.beginPath();
	ctx.rect(r_rectumEnd * unit, can.height / 2, unit * (r_analEnd - r_rectumEnd), y_size);
	ctx.stroke();
	
	ctx.beginPath();
	ctx.rect(r_analEnd * unit, can.height / 2, unit * (r_anus - r_analEnd), y_size);
	ctx.fill();	
	
	ctx.fillText("anal", ((r_anus - r_analEnd)/2 + r_analEnd) * unit, (can.height / 2) - 25);
	ctx.fillText("canal", ((r_anus - r_analEnd)/2 + r_analEnd) * unit, (can.height / 2) - 10);
	ctx.fillText("rectum", ((r_analEnd - r_rectumEnd)/2 + r_rectumEnd) * unit, (can.height / 2) - 30);
	ctx.fillText("proximal, mid & distal", ((r_rectumEnd - r_proximalEnd)/2 + r_proximalEnd) * unit, (can.height / 2) - 30);
	ctx.fillText("caecum", ((r_proximalEnd - r_cecumEnd)/2 + r_cecumEnd) * unit, (can.height / 2) - 30);	
	//
	ctx.beginPath();
	ctx.fillStyle = "purple";
	ctx.strokeStyle = "purple";
	ctx.moveTo(1, can.height / 2);
	ctx.lineTo(1, can.height / 2+40);	
	ctx.fillText("ICV", 15, (can.height / 2) + (y_size * 2) + 20);
	ctx.stroke();	
	
	ctx.beginPath();
	ctx.fillStyle = "olive";
	ctx.strokeStyle = "olive";
	ctx.moveTo(can.width - 1, can.height / 2);
	ctx.lineTo(can.width - 1, can.height / 2+40);
	ctx.fillText("anus", (r_anus * unit) - 20, (can.height / 2) + (y_size * 2) + 20);
	ctx.stroke();
}
