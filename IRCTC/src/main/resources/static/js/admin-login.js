async function adminLogin(e){
    e.preventDefault();
    show('msg','');
    try{
        const data=await api('/auth/login',{
            method:'POST',body:{
                username:username.value,password:password.value
            }
        });
        const roles=data.user.roles||[];
        const isAdmin=roles.some(role=>role.name==='ROLE_ADMIN');
        if(!isAdmin){
            show('msg','You do not have admin access.');
            return
        }
        localStorage.setItem('token',data.token);
        localStorage.setItem('refreshToken',data.refreshToken);
        localStorage.setItem('user',JSON.stringify(data.user));
        location.href='admin.html'
    }catch(err){
        show('msg',err.message)
    }
}
