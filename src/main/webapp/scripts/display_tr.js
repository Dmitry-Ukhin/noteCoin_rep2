/**
 * last change 17.03.18 01:20
 */
function showTrans() {
    var type, date, descr, data;
    var request = "/show";
    var http_method = "POST";
    var isAsynchr = true;

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
            insertResp(xhr.responseText);
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
    if (element.value < 10){
        month = "0"+element.value;
    }else{
        month = element.value;
    }
    element = document.getElementById("day");
    if (element.value < 10){
        day = "0"+element.value;
    }else{
        day = element.value;
    }

    date = year + "-" + month + "-" + day;
    return date;
}

function insertResp(resp) {
    var type = "null", date = "null", descr = "null", sum = "null";
    var transaction;
    var start = 0, end = 0;

    for (var i = 1 ; start < resp.length; i++){
        end = resp.indexOf("}") + 1;
        transaction = resp.substring(start, end);
        start = end;

        JSON.parse(resp, function (k, v) {
            if (k === "type"){
                type = v;
            }else if (k === "sum"){
                sum = v;
            }else if (k === "date"){
                date = v;
            }else if (k === "descr"){
                descr = v;
            }
        });
        alert("type=" + type + " sum=" + sum + " date=" + date + " descr=" + descr);

        var table = document.getElementById("table");
        var row = table.insertRow(i);

        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);

        cell1.innerHTML = type;
        cell2.innerHTML = sum;
        cell3.innerHTML = date;
        cell4.innerHTML = descr;
        alert(i);
    }
}