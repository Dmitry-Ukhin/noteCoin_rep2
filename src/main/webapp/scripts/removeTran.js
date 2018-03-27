function removeTran(event) {
    var http_method = "POST";
    var isAsynchr = true;
    var request = "/remove";
    var xhr = new XMLHttpRequest();
    var data;

    /*
    Get data
     */
    var table = document.getElementById("list_frame-0");
    var row = table.rows[this.index];
    var type = row.cells[0].innerHTML;
    type = type.toLowerCase();
    var date = row.cells[2].innerHTML;
    var descr = row.cells[3].innerHTML;

    row.remove();

    var elm = document.getElementById("div-table");
    elm.style.animation = "ripple-list 1s";
    var newone = elm.cloneNode(true);
    elm.parentNode.replaceChild(newone, elm);


    /*
    Construct POST request
     */
    data = "type=" + type + "&date=" + date + "&description=" + descr;
    // alert(data);
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

    // xhr.onreadystatechange = function () {
    //     if (this.readyState === 4 && this.status === 200) {
    //         document.getElementById("status-deleted").innerHTML = this.responseText;
    //     }
    // };
}

// function ripple() {
//     var elem = document.getElementById("button-" + index);
//     var opacity = 0.1;
//     var id = setInterval(changeOpacity, 2){
//         function changeOpacity() {
//             if (opacity === 1){
//                 clearInterval(id);
//             }else{
//                 opacity += 0.1;
//                 elem.style.opacity = "opacity=" + opacity + ";";
//             }
//         }
//     }
// }