/**
 * Last change 16.03.18 20:40
 */
function SaveTran() {
    var xhr = new XMLHttpRequest();
    var strReq = "/save_tran";
    var http_method = "POST";
    var isAsynchr = true;

    /*
    Get data from form
     */
    var form = document.getElementsByName("type");
    for (var i = 0; i < form.length; i++){
        if (form[i].type === "radio" && form[i].checked){
            var type = form[i].value;
        }
    }
    form = document.getElementsByName("sum");
    var sum = form[0].value;
    form = document.getElementsByName("description");
    var descr = form[0].value;

    /*
    Construct POST request
     */
    var data = "type=" +type+ "&sum=" +sum+ "&description=" +descr;

    /*
    Send request
     */
    xhr.open(http_method, strReq, isAsynchr);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    if (xhr.readyState === 1) {
        xhr.send(data);
    }else{
        alert("readyState != 1");
    }

    /*
    Get response and change DOM
     */
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("p1").innerHTML = this.responseText;
        }
    };
}