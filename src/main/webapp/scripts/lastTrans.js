function lastTrans() {
    var data;
    var request = "/lastTrans";
    var http_method = "POST";
    var isAsynchr = true;

    data = "size=3";

    var xhr = new XMLHttpRequest();
    xhr.open(http_method, request, isAsynchr);
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    if (xhr.readyState === 1) {
        xhr.send(data);
    }else{
        alert("readyState != 1");
    }

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            if (xhr.responseText !== "") {
                insertResp(xhr.responseText);
            }
        }
    };
}

function insertResp(resp) {
    var type = "null", date = "null", sum = "null";
    var transaction;
    var start = 0, end = 0;
    var table, row;

    table = document.getElementById("tableLastTrans");
    if (table.rows.length > 1) {
        for (var i = 1; i < table.rows.length;) {
            row = table.rows[i];
            row.parentNode.removeChild(row);
        }
    }

    /*
    Insert Transactions to table
     */
    for (i = 0; start < resp.length || i < 20; i++) {

        end = resp.indexOf("}", start) + 1;
        transaction = resp.substring(start, end);
        start = end;
        var json = JSON.parse(transaction);

        type = json.type;
        sum = json.sum;
        date = json.date;
        table = document.getElementById("tableLastTrans");
        row = table.insertRow(i);
        row.className = "tr-L";

        var cell1 = row.insertCell(0);
        cell1.className = "td-L";
        var cell2 = row.insertCell(1);
        cell2.className = "td-L";
        var cell3 = row.insertCell(2);
        cell3.className = "td-L";

        cell1.innerHTML = type;
        cell2.innerHTML = sum;
        cell3.innerHTML = date;
    }

    var wait = document.getElementById("div-Wait");
    if (wait !== null) {
        wait.remove();
    }
}