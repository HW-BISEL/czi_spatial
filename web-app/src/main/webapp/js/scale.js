function drawScale() {	
	can = document.getElementById('scaleCanvas');
	ctx = can.getContext('2d');
	var unit = can.width / 100;
	var y_size = 20;
	ctx.font = "20px Georgia";
	ctx.textAlign = "center";
	ctx.lineWidth = 1;
	ctx.beginPath();
	ctx.moveTo(0, can.height / 2 + 5);
	ctx.lineTo(can.width, can.height / 2 + 5);
	ctx.stroke()

	ctx.beginPath();
	ctx.fillStyle = "purple";
	ctx.rect(99 * unit, can.height / 2, unit, y_size * 2);
	ctx.fillText("100 %", unit + 5, (can.height / 2) - 40);
	ctx.fill();
	
	ctx.beginPath();
	ctx.fillStyle = "purple";
	ctx.rect(25 * unit, can.height / 2, unit, y_size * 2);
	ctx.fillText("75", 25 * unit + 10, (can.height / 2) - 40);
	ctx.fill();
	
	ctx.beginPath();
	ctx.fillStyle = "purple";
	ctx.rect(50 * unit, can.height / 2, unit, y_size * 2);
	ctx.fillText("50", 50 * unit + 10, (can.height / 2) - 40);
	ctx.fill();	
	
	ctx.beginPath();
	ctx.fillStyle = "purple";
	ctx.rect(75 * unit, can.height / 2, unit, y_size * 2);
	ctx.fillText("25", 75 * unit + 10, (can.height / 2) - 40);
	ctx.fill();	
	
	ctx.beginPath();
	ctx.fillStyle = "purple";
	ctx.rect(0 * unit, can.height / 2, unit, y_size * 2);
	ctx.fillText("0%", 99 * unit, (can.height / 2) - 40);
	ctx.fill();
}
