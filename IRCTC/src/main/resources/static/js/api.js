const API = '';
function token(){
    return localStorage.getItem('token');
}
function currentUser(){
    try{
        return JSON.parse(localStorage.getItem('user')||'null')
    }catch(e){
        return null
    }
}
async function api(path, options={
}){
    options.headers = options.headers || {
    };
    if(options.body && typeof options.body !== 'string'){
        options.headers['Content-Type']='application/json';
        options.body=JSON.stringify(options.body);
    }
    if(token()) options.headers['Authorization']='Bearer '+token();
    const res=await fetch(API+path,options);
    const text=await res.text();
    let data=null;
    try{
        data=text?JSON.parse(text):null
    }catch(e){
        data=text
    }
    if(!res.ok){
        throw new Error((data&&data.message)|| (data&&data.error)|| text || ('HTTP '+res.status));
    }
    return data;
}
function logout(){
    localStorage.clear();
    location.href='index.html'
}
function requireLogin(){
    if(!token())location.href='login.html'
}
function show(id,msg,ok=false){
    const e=document.getElementById(id);
    if(e){
        e.textContent=msg;
        e.className=ok?'success':'error'
    }
}
function fillSelect(id,items,valueKey='id',textFn=x=>x.name){
    const s=document.getElementById(id);
    if(!s)return;
    s.innerHTML='<option value="">Select</option>';
    items.forEach(x=>{
        const o=document.createElement('option');o.value=x[valueKey];o.textContent=textFn(x);s.appendChild(o)
    })
}
