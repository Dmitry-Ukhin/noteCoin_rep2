/**
 * last change 17.03.18 01:20
 */
function showTrans() {
    var type, date, descr, data;
    var request = "/show";
    var http_method = "POST";
    var isAsynchr = true;
    var resp;

    /*
    Get data from form
     */
    var element = document.getElementsByName("type-show");
    for (var i = 0; i < element.length; i++){
        if (element[i].type === "radio" && element[i].checked){
            type = element[i].value;
        }
    }
    date = getDate(element);
    element = document.getElementById("description2");
    descr = element.value;

    /*
    Construct POST request
     */
    data = "type=" + type + "&date=" + date + "&description=" + descr;

    /*
    Send request
     */
    var xhr = new XMLHttpRequest();
    xhr.open(http_method, request, isAsynchr);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    if (xhr.readyState === 1) {
        xhr.send(data);
    }else{
        alert("readyState != 1");
    }

    /*
    Get response
     */
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            resp = xhr.responseText;
            alert(resp);
        }
    };
}

function getDate(element) {
    var date;
    var year;
    var month;
    var day;

    /**
     * TODO: construct right form of date
     * @type {HTMLElement | null}
     */
    element = document.getElementById("year");
    year = element.value;
    element = document.getElementById("month");
    month = element.value;
    element = document.getElementById("day");
    day = element.value;

    date = year + "-" + month + "-" + day;
    return date;
}

function insertResp(resp) {

}