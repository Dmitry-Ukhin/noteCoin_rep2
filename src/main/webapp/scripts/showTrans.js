var suffix;
var element;

function loadTrans(idForm) {
    var type, date, descr, data;
    var request = "/show";
    var http_method = "POST";
    var isAsynchr = true;



    if (idForm === "showTrans"){
        suffix = "-show";
    }else if (idForm === "showTrans_frame-0"){
        suffix = "_frame-0";
    }

    /*
    Get data from form
    */
    var options = document.getElementsByName("option"+suffix);
    for (var i = 0; i < options.length; i++){
        if(options[i].selected){
            type = options[i].text;
        }
    }

    date = getDate();
    element = document.getElementById("description"+suffix);
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
            if (xhr.responseText !== "") {
                insertResp(xhr.responseText);
            }else{
                document.getElementById("div-Wait").innerHTML="Nothing";
            }
        }
    };
}

function getDate() {
    var date;
    var year;
    var month;
    var day;

    var element;
    /**
     * TODO: construct right form of date
     * @type {HTMLElement | null}
     */
    element = document.getElementById("year"+suffix);
    if (element.value !== "") {
        year = element.value;
    }

    element = document.getElementById("month"+suffix);
    if (element.value !== "") {
        if (element.value < 10) {
            month = "0" + element.value;
        } else {
            month = element.value;
        }
    }


    element = document.getElementById("day"+suffix);
    if (element.value !== "") {
        if (element.value < 10) {
            day = "0" + element.value;
        } else {
            day = element.value;
        }
    }

    date = year + "-" + month + "-" + day;
    return date;
}

function insertResp(resp) {

    var type = "null", date = "null", descr = "null", sum = "null";
    var transaction;
    var start = 0, end = 0;
    var table, row;

    table = document.getElementById("list_frame-0");
    if (table.rows.length > 1) {
        for (var i = 1; i < table.rows.length;) {
            row = table.rows[i];
            row.parentNode.removeChild(row);
        }
    }

    /*
    Insert Transactions to table
     */
    for (i = 1; start < resp.length; i++) {

        end = resp.indexOf("}", start) + 1;
        transaction = resp.substring(start, end);
        start = end;
        var json = JSON.parse(transaction);

        type = json.type;
        sum = json.sum;
        date = json.date;
        descr = json.descr;
        table = document.getElementById("list_frame-0");
        row = table.insertRow(i);
        row.className = "tr-list";

        var cell1 = row.insertCell(0);
        cell1.className = "td-list";
        var cell2 = row.insertCell(1);
        cell2.className = "td-list";
        var cell3 = row.insertCell(2);
        cell3.className = "td-list";
        var cell4 = row.insertCell(3);
        cell4.className = "td-list";
        var cell5 = row.insertCell(4);
        cell5.className = "td-button";

        cell1.innerHTML = type;
        cell2.innerHTML = sum;
        cell3.innerHTML = date;
        cell4.innerHTML = descr;

        /*
        Create button for remove Transaction
         */
        var button = document.createElement("button");
        button.id = "button-" + i;
        button.className = "rem";
        button.addEventListener("click", {handleEvent: removeTran, index: i});
        button.addEventListener("click", reload_frame0);

        cell5.appendChild(button);
    }

    var wait = document.getElementById("div-Wait");
    if (wait !== null) {
        wait.remove();
    }
}