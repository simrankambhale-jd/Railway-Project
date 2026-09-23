async function register(e){
    e.preventDefault();
    show('msg','');
    try{
        const data=await api('/auth/register',{
            method:'POST',body:{
                name:name.value,email:email.value,password:password.value,phone:phone.value
            }
        });
        show('msg','Registration successful. Please login.',true);
        setTimeout(()=>location.href='login.html',800)
    }catch(err){
        show('msg',err.message)
    }
}
