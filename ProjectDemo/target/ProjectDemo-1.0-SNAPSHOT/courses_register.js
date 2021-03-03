
function showDiv(value) {
    if(document.getElementById(value).style.display === "none"){
        document.getElementById(value).style.display = "table-row";
    }
    else{
        document.getElementById(value).style.display = "none";
    }
}