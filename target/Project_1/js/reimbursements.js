(function () {

    if(!localStorage.getItem('profile')) {
        location.href = "http://localhost:8080/index.html";
    }

    let url = new URL('http://localhost:8080/homepage/reimbursements'),
        params = {employeeNum: localStorage.getItem('username'), employeeType: localStorage.getItem('roles'), action: 'GET', status: 0, user: 0}

    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));

    // Retrieve all reimbursements
    fetch(url, {
        method: 'GET',
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('profile') },
    }) .then(resp => {
        if(resp.ok && localStorage.getItem('roles') === "MANAGER") {
            resp.json().then(function(data) {

                getEmployees();
                let table = document.getElementById("reimbursement_table");

                for (let i = 0; i < data.length; i++){

                    let tr = document.createElement('tr');
                    let td1 = document.createElement('td');
                    let td2 = document.createElement('td');
                    let td3 = document.createElement('td');
                    let td4 = document.createElement('td');
                    let td5 = document.createElement('td');
                    let td6 = document.createElement('td');

                    td1.innerText = data[i]['type'];
                    tr.appendChild(td1);
                    let  employeeName = data[i]['employeeFirstName'] + " " + data[i]['employeeLastName'];
                    td2.innerText = employeeName;
                    tr.appendChild(td2);
                    td3.innerText = data[i]['date_time'];
                    tr.appendChild(td3);
                    let num = parseFloat(Math.round(data[i]['amount'] * 100) / 100).toFixed(2);
                    td4.innerText = "$" + num;
                    tr.appendChild(td4);
                    if(data[i]['managerFirstName'] == null || data[i]['managerLastName'] == null) {
                        td5.innerText = 'N/A'
                    } else {
                        let  managerName = data[i]['managerFirstName'] + " " + data[i]['managerLastName'];
                        td5.innerText = managerName;
                    }
                    tr.appendChild(td5);
                    td6.innerText = data[i]['note'];
                    tr.appendChild(td6);
                    table.appendChild(tr);
                }
            })
        } else if ( resp.ok && localStorage.getItem('roles') === "EMPLOYEE") {
            resp.json().then(function(data) {
                let table = document.getElementById("reimbursement_table");

                for (let i = 0; i < data.length; i++){

                    let tr = document.createElement('tr');
                    let td1 = document.createElement('td');
                    let td3 = document.createElement('td');
                    let td4 = document.createElement('td');
                    let td5 = document.createElement('td');
                    let td6 = document.createElement('td');

                    td1.innerText = data[i]['type'];
                    tr.appendChild(td1);
                    td3.innerText = data[i]['date_time'];
                    tr.appendChild(td3);
                    let num = parseFloat(Math.round(data[i]['amount'] * 100) / 100).toFixed(2);
                    td4.innerText = "$" + num;
                    tr.appendChild(td4);
                    if(data[i]['managerFirstName'] == null || data[i]['managerLastName'] == null) {
                        td5.innerText = 'N/A';
                    } else {
                        let  managerName = data[i]['managerFirstName'] + " " + data[i]['managerLastName'];
                        td5.innerText = managerName;
                    }
                    tr.appendChild(td5);
                    td6.innerText = data[i]['note'];
                    tr.appendChild(td6);
                    table.appendChild(tr);
                }
            })
        }
    }) .catch(resp => {
        if (resp.status == 500) location.href = "http://localhost:8080/index.html"
    });

    // Fill in select of employees
    const getEmployees = () => {

        let url = new URL('http://localhost:8080/homepage/employees'),
            params = {employeeNum: localStorage.getItem('username'), employeeType: localStorage.getItem('roles'), action: 'GET', status: 0}

        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));

        fetch(url, {
            method: 'GET',
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('profile') },
        }) .then(resp => {
            resp.json().then(function(data) {
                let select = document.getElementById("employees_select");
                for (let i = 0; i < data.length; i++){
                    let option = document.createElement('option');
                    let  employeeName = data[i]['firstName'] + " " + data[i]['lastName'];
                    option.innerText = employeeName;
                    option.value = data[i]['employeeNum'];
                    select.appendChild(option);
                }
            })
        });
    };

    // Retrieve all reimbursements based on status change
    const getReimbursements = (evt) => {
        evt.preventDefault();
        let status = document.getElementById("status").value;
        let user = document.getElementById("employees_select").value;

        let url = new URL('http://localhost:8080/homepage/reimbursements'),
            params = {employeeNum: localStorage.getItem('username'), employeeType: localStorage.getItem('roles'), action: 'GET', status: status, user: user}

        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
        fetch(url, {
            method: 'GET',
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('profile') },
        }) .then(resp => {
            let table = document.getElementById("reimbursement_table");

            while (table.firstChild) {
                table.removeChild(table.firstChild);
            }

            if(resp.headers.get('Content-Length') != 0 && resp.ok) {
                if(localStorage.getItem('roles') === "MANAGER") {
                    let htr = document.createElement('tr');
                    let htd1 = document.createElement('th');
                    let htd2 = document.createElement('th');
                    let htd3 = document.createElement('th');
                    let htd4 = document.createElement('th');
                    let htd5 = document.createElement('th');
                    let htd6 = document.createElement('th');
                    htd1.innerText = "Status";
                    htr.appendChild(htd1);
                    htd2.innerText = "Employee";
                    htr.appendChild(htd2);
                    htd3.innerText = "Date/Time";
                    htr.appendChild(htd3);
                    htd4.innerText = "Amount";
                    htr.appendChild(htd4);
                    htd5.innerText = "Approved By";
                    htr.appendChild(htd5);
                    htd6.innerText = "Note";
                    htr.appendChild(htd6);
                    table.appendChild(htr);
                    resp.json().then(function(data) {
                        for (let i = 0; i < data.length; i++){

                            let tr = document.createElement('tr');
                            let td1 = document.createElement('td');
                            let td2 = document.createElement('td');
                            let td3 = document.createElement('td');
                            let td4 = document.createElement('td');
                            let td5 = document.createElement('td');
                            let td6 = document.createElement('td');

                            td1.innerText = data[i]['type'];
                            tr.appendChild(td1);
                            let  employeeName = data[i]['employeeFirstName'] + " " + data[i]['employeeLastName'];
                            td2.innerText = employeeName;
                            tr.appendChild(td2);
                            td3.innerText = data[i]['date_time'];
                            tr.appendChild(td3);
                            let num = parseFloat(Math.round(data[i]['amount'] * 100) / 100).toFixed(2);
                            td4.innerText = "$" + num;
                            tr.appendChild(td4);
                            if(data[i]['managerFirstName'] == null || data[i]['managerLastName'] == null) {
                                td5.innerText = 'N/A'
                            } else {
                                let  managerName = data[i]['managerFirstName'] + " " + data[i]['managerLastName'];
                                td5.innerText = managerName;
                            }
                            tr.appendChild(td5);
                            td6.innerText = data[i]['note'];
                            tr.appendChild(td6);
                            table.appendChild(tr);
                        }
                    })
                } else if (localStorage.getItem('roles') === "EMPLOYEE") {
                    let htr = document.createElement('tr');
                    let htd1 = document.createElement('th');
                    let htd3 = document.createElement('th');
                    let htd4 = document.createElement('th');
                    let htd5 = document.createElement('th');
                    let htd6 = document.createElement('th');
                    htd1.innerText = "Status";
                    htr.appendChild(htd1);
                    htd3.innerText = "Date/Time";
                    htr.appendChild(htd3);
                    htd4.innerText = "Amount";
                    htr.appendChild(htd4);
                    htd5.innerText = "Approved By";
                    htr.appendChild(htd5);
                    htd6.innerText = "Note";
                    htr.appendChild(htd6);
                    table.appendChild(htr);

                    resp.json().then(function(data) {
                        for (let i = 0; i < data.length; i++){

                            let tr = document.createElement('tr');
                            let td1 = document.createElement('td');
                            let td3 = document.createElement('td');
                            let td4 = document.createElement('td');
                            let td5 = document.createElement('td');
                            let td6 = document.createElement('td');

                            td1.innerText = data[i]['type'];
                            tr.appendChild(td1);
                            td3.innerText = data[i]['date_time'];
                            tr.appendChild(td3);
                            let num = parseFloat(Math.round(data[i]['amount'] * 100) / 100).toFixed(2);
                            td4.innerText = "$" + num;
                            tr.appendChild(td4);
                            if(data[i]['managerFirstName'] == null || data[i]['managerLastName'] == null) {
                                td5.innerText = 'N/A';
                            } else {
                                let  managerName = data[i]['managerFirstName'] + " " + data[i]['managerLastName'];
                                td5.innerText = managerName;
                            }
                            tr.appendChild(td5);
                            td6.innerText = data[i]['note'];
                            tr.appendChild(td6);
                            table.appendChild(tr);
                        }
                    })
                }
            }
        }) .catch(resp => {
            if (resp.status == 500) location.href = "http://localhost:8080/index.html"
        });
    };// getReimbursement
    document.querySelector('#status')
        .addEventListener('change', getReimbursements);

    // Retrieve all reimbursements based on status change and employee
    const getUserReimbursements = (evt) => {
        evt.preventDefault();
        let status = document.getElementById("status").value;
        let user = document.getElementById("employees_select").value;

        let url = new URL('http://localhost:8080/homepage/reimbursements'),
            params = {employeeNum: localStorage.getItem('username'), employeeType: localStorage.getItem('roles'), action: 'GET', status: status, user: user}

        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
        fetch(url, {
            method: 'GET',
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('profile') },
        }) .then(resp => {
            let table = document.getElementById("reimbursement_table");

            while (table.firstChild) {
                table.removeChild(table.firstChild);
            }

            if(resp.headers.get('Content-Length') != 0 && resp.ok) {
                if(localStorage.getItem('roles') === "MANAGER") {
                    let htr = document.createElement('tr');
                    let htd1 = document.createElement('th');
                    let htd2 = document.createElement('th');
                    let htd3 = document.createElement('th');
                    let htd4 = document.createElement('th');
                    let htd5 = document.createElement('th');
                    let htd6 = document.createElement('th');
                    htd1.innerText = "Status";
                    htr.appendChild(htd1);
                    htd2.innerText = "Employee";
                    htr.appendChild(htd2);
                    htd3.innerText = "Date/Time";
                    htr.appendChild(htd3);
                    htd4.innerText = "Amount";
                    htr.appendChild(htd4);
                    htd5.innerText = "Approved By";
                    htr.appendChild(htd5);
                    htd6.innerText = "Note";
                    htr.appendChild(htd6);
                    table.appendChild(htr);
                    resp.json().then(function(data) {
                        for (let i = 0; i < data.length; i++){

                            let tr = document.createElement('tr');
                            let td1 = document.createElement('td');
                            let td2 = document.createElement('td');
                            let td3 = document.createElement('td');
                            let td4 = document.createElement('td');
                            let td5 = document.createElement('td');
                            let td6 = document.createElement('td');

                            td1.innerText = data[i]['type'];
                            tr.appendChild(td1);
                            let  employeeName = data[i]['employeeFirstName'] + " " + data[i]['employeeLastName'];
                            td2.innerText = employeeName;
                            tr.appendChild(td2);
                            td3.innerText = data[i]['date_time'];
                            tr.appendChild(td3);
                            let num = parseFloat(Math.round(data[i]['amount'] * 100) / 100).toFixed(2);
                            td4.innerText = "$" + num;
                            tr.appendChild(td4);
                            if(data[i]['managerFirstName'] == null || data[i]['managerLastName'] == null) {
                                td5.innerText = 'N/A'
                            } else {
                                let  managerName = data[i]['managerFirstName'] + " " + data[i]['managerLastName'];
                                td5.innerText = managerName;
                            }
                            tr.appendChild(td5);
                            td6.innerText = data[i]['note'];
                            tr.appendChild(td6);
                            table.appendChild(tr);
                        }
                    })
                } else if (localStorage.getItem('roles') === "EMPLOYEE") {
                    let htr = document.createElement('tr');
                    let htd1 = document.createElement('th');
                    let htd3 = document.createElement('th');
                    let htd4 = document.createElement('th');
                    let htd5 = document.createElement('th');
                    let htd6 = document.createElement('th');
                    htd1.innerText = "Status";
                    htr.appendChild(htd1);
                    htd3.innerText = "Date/Time";
                    htr.appendChild(htd3);
                    htd4.innerText = "Amount";
                    htr.appendChild(htd4);
                    htd5.innerText = "Approved By";
                    htr.appendChild(htd5);
                    htd6.innerText = "Note";
                    htr.appendChild(htd6);
                    table.appendChild(htr);

                    resp.json().then(function(data) {
                        for (let i = 0; i < data.length; i++){

                            let tr = document.createElement('tr');
                            let td1 = document.createElement('td');
                            let td3 = document.createElement('td');
                            let td4 = document.createElement('td');
                            let td5 = document.createElement('td');
                            let td6 = document.createElement('td');

                            td1.innerText = data[i]['type'];
                            tr.appendChild(td1);
                            td3.innerText = data[i]['date_time'];
                            tr.appendChild(td3);
                            let num = parseFloat(Math.round(data[i]['amount'] * 100) / 100).toFixed(2);
                            td4.innerText = "$" + num;
                            tr.appendChild(td4);
                            if(data[i]['managerFirstName'] == null || data[i]['managerLastName'] == null) {
                                td5.innerText = 'N/A';
                            } else {
                                let  managerName = data[i]['managerFirstName'] + " " + data[i]['managerLastName'];
                                td5.innerText = managerName;
                            }
                            tr.appendChild(td5);
                            td6.innerText = data[i]['note'];
                            tr.appendChild(td6);
                            table.appendChild(tr);
                        }
                    })
                }
            }
        }) .catch(resp => {
            if (resp.status == 500) location.href = "http://localhost:8080/index.html"
        });
    };// getReimbursement
    document.querySelector('#employees_select')
        .addEventListener('change', getUserReimbursements);

    // Switch views from primary to secondary with click
    const addReimbursement = (evt) => {
        evt.preventDefault();
        let table = document.getElementById("reimbursement_div");
        let newTable = document.getElementById("new_table");
        table.classList.add('hidden');
        newTable.classList.remove('hidden');
    } // addReimbursement
    document.querySelector('#add')
        .addEventListener('click', addReimbursement);

    // Switch views from primary to secondary with click
    const changeReimbursement = (evt) => {
        evt.preventDefault();
        let status = 3;
        let user = 0;

        let url = new URL('http://localhost:8080/homepage/reimbursements'),
            params = {employeeNum: localStorage.getItem('username'), employeeType: localStorage.getItem('roles'), action: 'GET', status: status, user: user}

        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
        fetch(url, {
            method: 'GET',
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('profile') },
        }) .then(resp => {
            let select = document.getElementById("update_reimbursement_select");

            if(resp.headers.get('Content-Length') != 0 && resp.ok) {
                resp.json().then(function(data) {
                    for (let i = 0; i < data.length; i++){
                        let option = document.createElement('option');
                        option.value = data[i]['id'];
                        let num = parseFloat(Math.round(data[i]['amount'] * 100) / 100).toFixed(2);
                        option.innerText = "NAME:***** " + data[i]['employeeFirstName'] + " " + data[i]['employeeLastName'] + " *****AMOUNT:*****$" + num + " *****NOTE:***** " + data[i]['note'];
                        select.appendChild(option);
                    }
                })
            }
        }) .catch(resp => {
            if (resp.status == 500) location.href = "http://localhost:8080/index.html"
        });

        let table = document.getElementById("reimbursement_div");
        let newTable = document.getElementById("update_reimbursement");
        table.classList.add('hidden');
        newTable.classList.remove('hidden');
    } // addReimbursement
    document.querySelector('#change_status')
        .addEventListener('click', changeReimbursement);

    // Save reimbursement
    const saveReimbursement = (evt) => {
        evt.preventDefault();
        let amount = document.forms['addReimbursementForm']['amount'].value;
        let note = document.forms['addReimbursementForm']['note'].value;

        let url = new URL('http://localhost:8080/homepage/reimbursements'),
            params = {amount: amount, note: note, action: 'add', user: localStorage.getItem('username')}

        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
        fetch(url, {
            method: 'GET',
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('profile') },
        }) .then (resp => {
            if(resp.ok) {
                location.reload();
                returnHome();
            } else {
                let error = document.getElementById("error");
                error.innerText = "Sorry could not save reimbursement. Please try again";
            }
        });
    } // saveReimbursement
    document.querySelector('#save')
        .addEventListener('click', saveReimbursement);

    // Update reimbursement
    const updateReimbursement = (evt) => {
        evt.preventDefault();
        let id = document.getElementById("update_reimbursement_select").value;
        let change_status = null;

        if(document.getElementById("approve").checked && !document.getElementById("reject").checked) change_status = 1;
        if(document.getElementById("reject").checked  && !document.getElementById("approve").checked) change_status = 2;
        if(!document.getElementById("approve").checked && !document.getElementById("reject").checked) change_status = 3;

        console.log(change_status);


        let url = new URL('http://localhost:8080/homepage/reimbursements'),
            params = {manager_num: localStorage.getItem("username"), id: id, action: 'update', type: change_status}

        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));
        fetch(url, {
            method: 'GET',
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('profile') },
        }) .then (resp => {
            if(resp.ok) {
                location.reload();
                returnHome();
            } else {
                let error = document.getElementById("error");
                error.innerText = "Sorry could not save reimbursement. Please try again";
            }
        });
    } // updateReimbursement
    document.querySelector('#update')
        .addEventListener('click', updateReimbursement);

    // Switch views from secondary to primary without click
    const returnHome = () => {
        let table = document.getElementById("reimbursement_div");
        let newTable = document.getElementById("new_table");
        table.classList.remove('hidden');
        newTable.classList.add('hidden');
    } // returnHome

    // Switch views from secondary to primary with click
    const closeUpdateReimbursement = (evt) => {
        evt.preventDefault();
        let table = document.getElementById("reimbursement_div");
        let newTable = document.getElementById("update_reimbursement");
        table.classList.remove('hidden');
        newTable.classList.add('hidden');
    } // closeReimbursement
    document.querySelector('#close_reimbursement')
        .addEventListener('click', closeUpdateReimbursement);

    // Switch views from secondary to primary with click
    const closeReimbursement = (evt) => {
        evt.preventDefault();
        let table = document.getElementById("reimbursement_div");
        let newTable = document.getElementById("new_table");
        table.classList.remove('hidden');
        newTable.classList.add('hidden');
    } // closeReimbursement
    document.querySelector('#close')
        .addEventListener('click', closeReimbursement);

    // Logout
    const logout = (evt) => {
        evt.preventDefault();
        localStorage.removeItem('username');
        localStorage.removeItem('roles');
        localStorage.removeItem('profile');
        localStorage.removeItem('action');
        location.href = "http://localhost:8080/index.html";
    } // logout
    document.querySelector('#logout')
        .addEventListener('click', logout);
})()