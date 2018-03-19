/**
 * last change 18.03.18 21:58
 */
function removeTran(type, date, descr) {
    var http_method = "POST";
    var isAsynchr = true;
    var request = "/remove";
    var xhr = new XMLHttpRequest();
    var data;

    /*
    Construct POST request
     */
    data = "type=" + type.innerHTML + "&date=" + date.innerHTML + "&description=" + descr.innerHTML;

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

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("status-deleted").innerHTML = this.responseText;
        }
    };
}