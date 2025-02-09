function handleRoleChange() {
    var role = document.getElementById("role").value;
    var codeField = document.getElementById("teacherCodeField");

    if (role === "Преподаватель") {
        codeField.style.display = "block";
    } else {
        codeField.style.display = "none";
    }
}
