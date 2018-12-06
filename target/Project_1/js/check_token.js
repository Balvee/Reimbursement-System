(function () {
    if(!localStorage.getItem('profile')) location.href = "http://localhost:8080/index.html";
    if(localStorage.getItem('roles') == "EMPLOYEE" && location.pathname == "/admin_profile.html")location.href = "http://localhost:8080/user_profile.html";
    if(localStorage.getItem('roles') == "EMPLOYEE" && location.pathname == "/admin_reimbursements.html")location.href = "http://localhost:8080/user_reimbursements.html";
    if(localStorage.getItem('roles') == "EMPLOYEE" && location.pathname == "/admin_employees.html")location.href = "http://localhost:8080/user_reimbursements.html";
    if(localStorage.getItem('roles') == "MANAGER" && location.pathname == "/user_profile.html")location.href = "http://localhost:8080/admin_profile.html";
    if(localStorage.getItem('roles') == "MANAGER" && location.pathname == "/user_reimbursements.html")location.href = "http://localhost:8080/admin_reimbursements.html";

    // Retrieve all reimbursements
    fetch("http://localhost:8080/homepage/reimbursements", {
        method: 'POST',
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('profile') },
    }) .catch(resp => {
        if (resp.status == 500) location.href = "http://localhost:8080/index.html"
    });
})()