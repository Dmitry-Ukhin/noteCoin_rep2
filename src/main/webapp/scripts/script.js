/**
 * Last change 16.03.18 20:40
 */
function SaveTran() {
    var xhr = new XMLHttpRequest();
    var request = "/save_tran";
    var http_method = "POST";
    var isAsynchr = true;

    /*
    Get data from form
     */
    var element = document.getElementsByName("type");
    for (var i = 0; i < element.length; i++){
        if (element[i].type === "radio" && element[i].checked){
            var type = element[i].value;
        }
    }
    element = document.getElementsByName("sum");
    var sum = element[0].value;
    element = document.getElementsByName("description");
    var descr = element[0].value;

    /*
    Construct POST request
     */
    var data = "type=" +type+ "&sum=" +sum+ "&description=" +descr;

    /*
    Send request
     */
    xhr.open(http_method, request, isAsynchr);
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