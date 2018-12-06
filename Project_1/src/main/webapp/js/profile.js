(function () {

    if(!localStorage.getItem('profile')) {
        location.href = "http://localhost:8080/index.html";
    }

    let url = new URL('http://localhost:8080/homepage/profile'),
        params = {employeeNum: localStorage.getItem('username'), employeeType: localStorage.getItem('roles'), action: "GET"}

    Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));

    // Retrieve user information
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('profile')
        }
    })  .then(resp => {
            resp.json().then(function(data) {

                let p = document.getElementById("emptype");
                let p2 = document.getElementById("empNum");
                let p3 = document.getElementById("fName");
                let p4 = document.getElementById("lName");
                let p5 = document.getElementById("usr")
                let p6 = document.getElementById("email");
                let p7 = document.getElementById("address");
                let p8 = document.getElementById("phone")

                p.innerText = data['type'];
                p2.innerText = data['employeeNum'];
                p3.innerText = data['firstName'];
                p4.innerText = data['lastName'];
                p5.innerText = data['username'];
                p6.innerText = data['email'];
                p7.innerText = data['address'];
                p8.innerText = data['phone'];
            })
    }) .catch(resp => {
        if (resp.status == 500) location.href = "http://localhost:8080/index.html"
    });

    // Save user updated information
    const saveInfo = (evt) => {
        evt.preventDefault();
        let first_name = document.forms['updateInfo']['first_name'].value;
        let last_name = document.forms['updateInfo']['last_name'].value;
        let email = document.forms['updateInfo']['e_mail'].value;
        let address = document.forms['updateInfo']['addr'].value;
        let phone = document.forms['updateInfo']['phone_num'].value;
        let password = document.forms['updateInfo']['passwd'].value;

        let url = new URL('http://localhost:8080/homepage/profile'),
            params = {first_name: first_name, last_name: last_name, phone: phone, email: email, address: address, action: 'update', password: password, user: localStorage.getItem('username')}

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
    }// saveInfo
    document.querySelector('#save')
        .addEventListener('click', saveInfo);

    // Switch views from primary to secondary with click
    const editInfo = (evt) => {
        evt.preventDefault();
        let div = document.getElementById("info_div");
        let newDiv = document.getElementById("editInfo_div");
        let first_name = document.getElementById("first_name");
        first_name.value = document.getElementById("fName").innerText;
        let last_name = document.getElementById("last_name");
        last_name.value = document.getElementById("lName").innerText;
        let email = document.getElementById("e_mail");
        email.value = document.getElementById("email").innerText;
        let phone = document.getElementById("phone_num");
        phone.value = document.getElementById("phone").innerText;
        let address = document.getElementById("addr");
        address.value = document.getElementById("address").innerText;
        let password = document.getElementById("passwd");
        password.value = "";
        div.classList.add('hidden');
        newDiv.classList.remove('hidden');
    } // editInfo
    document.querySelector('#editInfo')
        .addEventListener('click', editInfo);

    // Switch views from secondary to primary without click
    const returnHome = () => {
        let table = document.getElementById("info_div");
        let newTable = document.getElementById("editInfo_div");
        table.classList.remove('hidden');
        newTable.classList.add('hidden');
    } // returnHome

    // Switch views from secondary to primary with click
    const closeInfo = (evt) => {
        evt.preventDefault();
        let div = document.getElementById("info_div");
        let newDiv = document.getElementById("editInfo_div");
        div.classList.remove('hidden');
        newDiv.classList.add('hidden');
    } // closeInfo
    document.querySelector('#close')
        .addEventListener('click', closeInfo);

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