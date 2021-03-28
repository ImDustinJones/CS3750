function emptyGrade() {
    if(document.getElementById("gradePointsBox").value==="") {
        document.getElementById('gradeSubBtn').disabled = true;
    } else {
        document.getElementById('gradeSubBtn').disabled = false;
    }
}