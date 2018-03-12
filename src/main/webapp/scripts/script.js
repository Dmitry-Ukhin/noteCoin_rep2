function sendRec() {
	var xhr = new XMLHttpRequest();

	var req = '/noteCoin-0.1b.40/query?command_line=' + document.forms["start_form"].elements["command_line"].value;

	xhr.open('GET', req, false);

	xhr.send();


	if(xhr.status != 200) {
		alert(xhr.status + ': ' + xhr.statusText);

		alert(req);	
	}else{
		writeResponse(xhr.responseText);
	}
}

function writeResponse(resp){
	var blockToResponse = document.getElementById("response");
	blockToResponse.innerHTML = resp;
}
