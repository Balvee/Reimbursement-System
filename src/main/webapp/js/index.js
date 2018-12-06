(function(){

    // Authenticate user
    const doLogin = (evt) => {
        evt.preventDefault();
        const username = document.querySelector('#username').value;
        const password = document.querySelector('#password').value;

        fetch('http://localhost:8080/login', {
            method: 'POST',
            body: JSON.stringify({username: username, password: password})

        }) .then(resp => {
            if(resp.ok) {
                if(!document.querySelector('#error').classList.contains('hidden')) {
                    document.querySelector('#error').classList.add('hidden');
                    document.querySelector('#success').classList.remove('hidden');
                } else {
                    document.querySelector('#success').classList.remove('hidden');
                }
                return resp.json();
            } else {
                if(resp.status === 401 || resp.status === 400) {
                    if(!document.querySelector('#success').classList.contains('hidden')) {
                        document.querySelector('#success').classList.add('hidden');
                        document.querySelector('#error').classList.remove('hidden');
                    } else {
                        document.querySelector('#error').classList.remove('hidden');
                    }
                    throw {};
                }
            }
        }) .then(token => {
            localStorage.setItem('profile', token.idToken);
            localStorage.setItem('username', token.user);
            localStorage.setItem('roles', token.roles);
            localStorage.setItem('action', 'init');
            if(token.roles.toString() == 'MANAGER'){
                location.href = "http://localhost:8080/admin_reimbursements.html"
            } else {
                location.href = "http://localhost:8080/user_reimbursements.html"
            }
        })
            .catch(error => {});
    }; // doLogin
    document.querySelector('#login-submit')
        .addEventListener('click', doLogin);
    document.querySelector('form')
        .addEventListener('submit', doLogin);


    // Prompt alert for user email
    const forgotPassword = (evt) => {
        evt.preventDefault();
        let email = prompt('Please enter your email.');
        if(email != null) {
            let url = new URL('http://localhost:8080/mail'),
                params = {email: email}

            Object.keys(params).forEach(key => url.searchParams.append(key, params[key]));

            fetch(url, {
                method: 'GET'
            }) .then(resp => {
                if(resp.ok) {
                    alert("A temporary password has been sent to your email. Please sign in immediately and update your password.");
                } else {
                    alert("Email not found. Please try again.");
                    if(resp.status === 401 || resp.status === 400) {
                        throw {};
                    }
                }
            })
        }
    }; // forgotPassword
    document.querySelector('#forgot_password')
        .addEventListener('click', forgotPassword);

})()