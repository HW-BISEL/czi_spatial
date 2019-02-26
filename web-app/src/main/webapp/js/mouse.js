const m_cecumEnd = 0;
const m_proximalEnd = 40;
const m_distalEnd = 70;
const m_rectumEnd = 135;
const m_analEnd = 138;
const m_anus = 140;
const m_icv = 0;


const m_length = m_anus - m_icv;

function drawMouse() {
	can = document.getElementById('modelCanvas2');
	ctx = can.getContext('2d');
	var unit = (can.width - 1) / m_anus;
	var y_size = 20;
	ctx.font = "20px Georgia";
	ctx.textAlign = "center";
	ctx.lineWidth = 1;
		
	ctx.beginPath();
	ctx.rect(m_cecumEnd * unit, can.height / 2, unit * (m_proximalEnd - m_cecumEnd), y_size);
	ctx.fill();
	
	ctx.beginPath();
	ctx.rect(m_proximalEnd * unit, can.height / 2, unit * (m_distalEnd - m_proximalEnd), y_size);
	ctx.stroke();	

	ctx.beginPath();
	ctx.rect(m_distalEnd * unit, can.height / 2, unit * (m_rectumEnd - m_distalEnd), y_size);
	ctx.fill();		
	
	ctx.beginPath();
	ctx.rect(m_rectumEnd * unit, can.height / 2, unit * (m_analEnd - m_rectumEnd), y_size);
	ctx.stroke();
	
	ctx.beginPath();
	ctx.rect(m_analEnd * unit, can.height / 2, unit * (m_anus - m_analEnd), y_size);
	ctx.fill();	
	
//	ctx.fillText("anal", 138 * unit, (can.height / 2) - 25);
//	ctx.fillText("canal", 138 * unit, (can.height / 2) - 10);
	ctx.fillText("rectum", ((m_analEnd - m_rectumEnd)/2 + m_rectumEnd) * unit, (can.height / 2) - 30);
	ctx.fillText("mid-distal", ((m_rectumEnd - m_distalEnd)/2 + m_distalEnd) * unit, (can.height / 2) - 30);
	ctx.fillText("proximal", ((m_distalEnd - m_proximalEnd)/2 + m_proximalEnd) * unit, (can.height / 2) - 30);
	ctx.fillText("caecum", ((m_proximalEnd - m_cecumEnd)/2 + m_cecumEnd) * unit, (can.height / 2) - 30);	
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
	ctx.fillText("anus", (m_anus * unit) - 20, (can.height / 2) + (y_size * 2) + 20);
	ctx.stroke();
}
