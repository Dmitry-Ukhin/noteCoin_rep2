function sendRec() {
	var xhr = new XMLHttpRequest();

	xhr.open('GET', 'req_js', false);

	xhr.send();

	if(xhr.status != 200) {
		alert(xhr.status + ': ' + xhr.statusText);
	}else{
		alert(xhr.responseText);
	}
}
