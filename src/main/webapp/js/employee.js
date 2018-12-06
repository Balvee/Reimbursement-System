(function () {

    if(!localStorage.getItem('profile')) {
        location.href = "http://localhost:8080/index.html";
    }

    let url = new URL('http://localhost:8080/homepage/employees'),
        params = {action: 'GET'}

    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));

    // Retrieve all employees
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('profile')
        }
    }) .then(resp => {
            if(resp.ok && localStorage.getItem('roles') === "MANAGER") {
                resp.json().then(function(data) {
                    let table = document.getElementById("reimbursement_table");

                    for (let i = 0; i < data.length; i++){

                        let tr = document.createElement('tr');

                        let td1 = document.createElement('td');
                        let td2 = document.createElement('td');
                        let td3 = document.createElement('td');
                        let td4 = document.createElement('td');

                        td1.innerText = data[i]['type'];
                        tr.appendChild(td1);
                        td2.innerText = data[i]['employeeNum'];
                        tr.appendChild(td2);
                        let  employeeName = data[i]['firstName'] + " " + data[i]['lastName'];
                        td3.innerText = employeeName;
                        tr.appendChild(td3);
                        td4.innerText = data[i]['username'];
                        tr.appendChild(td4);
                        table.appendChild(tr);
                    }
                })
            }
        })

        .catch(resp => {
            if (resp.status == 500) location.href = "http://localhost:8080/index.html"
        });

    // Switch from primary to secondary view with click
    const addEmployee = (evt) => {
        evt.preventDefault();
        let div = document.getElementById("employee_list");
        let newDiv = document.getElementById("add_employee");
        div.classList.add('hidden');
        newDiv.classList.remove('hidden');
    } // addEmployee
    document.querySelector('#addEmployee')
        .addEventListener('click', addEmployee);

    // Save new employee
    const saveEmployee = (evt) => {
        evt.preventDefault();
        let first_name = document.forms['addEmployee']['firstName'].value;
        let last_name = document.forms['addEmployee']['lastName'].value;
        let type = document.forms['addEmployee']['type'].value;
        let username = document.forms['addEmployee']['username'].value;
        let email = document.forms['addEmployee']['email'].value;
        let address = document.forms['addEmployee']['address'].value;
        let phone = document.forms['addEmployee']['phone'].value;

        let url = new URL('http://localhost:8080/homepage/employees'),
            params = {first_name: first_name, last_name: last_name, email: email,
                username: username, address: address, phone: phone, type:type, action: 'add'}

        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));

        fetch(url, {
            method: 'GET',
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('profile') },
        }) .then (resp => {
            if(resp.ok) {
                if(!document.getElementById("error").classList.contains('hidden')) document.getElementById("error").classList.add('hidden');
                location.reload();
                returnHome();
            } else {
                let error = document.getElementById("error");
                error.classList.remove('hidden');
            }
        }) .catch(resp => {
            if (resp.status == 401) {
                let error = document.getElementById("error");
                error.classList.remove('hidden');
            }
        });
    } // saveEmployee
    document.querySelector('#save')
        .addEventListener('click', saveEmployee);

    // Switch from secondary to primary view without click
    const returnHome = () => {
        let table = document.getElementById("employee_list");
        let newTable = document.getElementById("add_employee");
        table.classList.remove('hidden');
        newTable.classList.add('hidden');
    } // returnHome

    // Switch from secondary to primary view with click
    const closeEmployee = (evt) => {
        evt.preventDefault();
        let div = document.getElementById("employee_list");
        let newDiv = document.getElementById("add_employee");
        div.classList.remove('hidden');
        newDiv.classList.add('hidden');
    } // closeEmployee
    document.querySelector('#closeEmployee')
        .addEventListener('click', closeEmployee);

    // Logout
    const logout = (evt) => {
        evt.preventDefault();
        localStorage.removeItem('username');
        localStorage.removeItem('roles');
        localStorage.removeItem('profile');
        location.href = "http://localhost:8080/index.html";
    } // logout
    document.querySelector('#logout')
        .addEventListener('click', logout);
})();