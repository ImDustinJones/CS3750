function emptyGrade(subID) {

    if(document.getElementById("gradePointsBox" + subID).value==="") {
        document.getElementById('gradeSubBtn' + subID).disabled = true;
    } else {
        document.getElementById('gradeSubBtn' + subID).disabled = false;
    }
}