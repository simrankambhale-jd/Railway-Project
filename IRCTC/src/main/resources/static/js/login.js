async function login(e) {
    e.preventDefault();

    const email = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const data = await api('/auth/login', {
            method: 'POST',
            body: {
                username: email,
                password: password
            }
        });

        const roles = data.user.roles || [];
        const isAdmin = roles.some(role => role.name === 'ROLE_ADMIN');
        const isNormal = roles.some(role => role.name === 'ROLE_NORMAL');

        if (!isAdmin && !isNormal) {
            show('msg', 'This account does not have access to this application.');
            return;
        }

        localStorage.setItem('token', data.token);
        localStorage.setItem('refreshToken', data.refreshToken);
        localStorage.setItem('user', JSON.stringify(data.user));

        if (isAdmin && isNormal) {
            // Dual-role account (e.g. sahil): let them pick where to go.
            document.getElementById('loginForm').classList.add('hidden');
            document.getElementById('welcomeName').textContent = ' ' + (data.user.name || data.user.email);
            document.getElementById('portalChoice').classList.remove('hidden');
        } else if (isNormal) {
            goToUser();
        } else {
            goToAdmin();
        }

    } catch (error) {
        show('msg', error.message);
    }
}

function goToUser() {
    location.href = 'dashboard.html';
}

function goToAdmin() {
    location.href = 'admin.html';
}
