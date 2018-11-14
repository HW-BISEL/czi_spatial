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