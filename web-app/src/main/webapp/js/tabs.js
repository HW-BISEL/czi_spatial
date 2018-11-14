function openTab(evt, tabName) {
	document.getElementById('displayArea').innerHTML = "<br />";
	var i, tabcontent, tablinks;
	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tablinks");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}
	document.getElementById(tabName).style.display = "block";
	if (tabName == 'click') {
		clickQuery = true;
	} else {
		clickQuery = false;
	}
	evt.currentTarget.className += " active";
	resizeCanvas();
}