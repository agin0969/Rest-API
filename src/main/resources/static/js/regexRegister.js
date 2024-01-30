function showAlert(name) {
	var passwordPatternDiv = document.getElementById(name);
	passwordPatternDiv.style.display = "block";
}
function hideAlert(){
	var a = document.getElementById("btn-alert")
	a.style.display="none";
}