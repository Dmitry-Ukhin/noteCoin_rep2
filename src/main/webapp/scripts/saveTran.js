function SaveTran() {
    var xhr = new XMLHttpRequest();
    var request = "/save_tran";
    var http_method = "POST";
    var isAsynchr = true;

    /*
    Get data from form
     */
    var element = document.getElementById("typeTran");
    var type;
    if (element.checked){
        type = "incomes";
    }else if (!element.checked){
        type = "expenses";
    }

    element = document.getElementById("sum");
    var sum;
    if (element.value !== null){
        sum = element.value;
        element.value = "";
    }else{
        delete xhr;
        alert("sum not entry");
    }
    element = document.getElementById("descriptionNewTran");
    var descr = element.value;
    element.value = "";

    var elm = document.getElementById("div-buttonNewTran");
    elm.style.animation = "hideElement 1s";
    var newone = elm.cloneNode(true);
    elm.parentNode.replaceChild(newone, elm);

    /*
    Construct POST request
     */
    var data = "type=" +type+ "&sum=" +sum+ "&description=" +descr;

    /*
    Send request
     */
    if (xhr !== null) {
        xhr.open(http_method, request, isAsynchr);
        xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        if (xhr.readyState === 1) {
            xhr.send(data);
        } else {
            alert("readyState != 1");
        }
    }

    /*
    Get response and change DOM
     */
    // xhr.onreadystatechange = function () {
    //     if (this.readyState === 4 && this.status === 200) {
    //         document.getElementById("p1").innerHTML = this.responseText;
    //     }
    // };
}
