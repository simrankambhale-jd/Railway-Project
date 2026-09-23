let allStations = [];
let allTrains = [];

document.addEventListener('DOMContentLoaded', () => {
    requireLogin();
    const user = currentUser();
    if (!user) {
        return;
    }
    const roles = user.roles || [];
    const isAdmin = roles.some(role => {
        return role.name === 'ROLE_ADMIN';
    });
    if (!isAdmin) {
        alert('You do not have admin access.');
        location.href = 'dashboard.html'
        return;
    }
    loadAll();
});
async function loadAll(){
    try{
        allStations=await api('/admin/train/station');
        allTrains=await api('/admin/train');
        renderStations();
        renderTrains();
        fillSelect('trainId',allTrains,'id',x=>x.trainNo+' - '+x.name);
        fillSelect('routeTrainId',allTrains,'id',x=>x.trainNo+' - '+x.name);
        fillSelect('sourceId',allStations,'id',x=>x.code+' - '+x.name);
        fillSelect('destId',allStations,'id',x=>x.code+' - '+x.name);
        fillSelect('routeStationId',allStations,'id',x=>x.code+' - '+x.name)
    }catch(e){
        alert(e.message)
    }
}
function renderStations(){
    document.getElementById('stationTable').innerHTML=allStations.map(s=>`<tr>
<td>${s.id}</td>
<td>${s.code}</td>
<td>${s.name}</td>
<td>${s.city}</td>
<td>${s.state}</td>
<td><button class="danger small" onclick="del('/admin/train/station/'+s.id)">Delete</button></td></tr>`).join('')
}
function renderTrains(){
    document.getElementById('trainTable').innerHTML=allTrains.map(t=>`<tr>
<td>${t.id}</td>
<td>${t.trainNo}</td>
<td>${t.name}</td>
<td>${t.distance||''}</td>
<td>${t.SourceStation?.name||''} → ${t.DestinationStation?.name||''}</td>
<td><button class="danger small" onclick="del('/admin/train/'+t.id)">Delete</button></td></tr>`).join('')
}
async function del(path){
    if(!confirm('Delete this record?'))return;
    try{
        await api(path,{
            method:'DELETE'
        });
        loadAll()
    }catch(e){
        alert(e.message)
    }
}
async function createStation(e){
    e.preventDefault();
    try{
        await api('/admin/train/station',{
            method:'POST',body:[{
                code:scCode.value,name:scName.value,city:scCity.value,state:scState.value
            }]
        });
        e.target.reset();
        loadAll()
    }catch(e){
        show('stationMsg',e.message)
    }
}
async function createTrain(e){
    e.preventDefault();
    try{
        await api('/admin/train',{
            method:'POST',body:{
                trainNo:trNo.value,name:trName.value,distance:trDistance.value,SourceStation:{
                    id:Number(sourceId.value)
                },DestinationStation:{
                    id:Number(destId.value)
                }
            }
        });
        e.target.reset();
        loadAll()
    }catch(e){
        show('trainMsg',e.message)
    }
}
async function createSchedule(e){
    e.preventDefault();
    try{
        await api('/admin/train/schedule',{
            method:'POST',body:{
                trainId:{
                    id:Number(trainId.value)
                },rundate:runDate.value,availableSeats:Number(runSeats.value)
            }
        });
        e.target.reset();
        loadSchedules()
    }catch(e){
        show('scheduleMsg',e.message)
    }
}
async function loadSchedules(){
    try{
        const x=await api('/admin/train/schedule');
        document.getElementById('scheduleTable').innerHTML=x.map(s=>`<tr>
<td>${s.id}</td>
<td>${s.trainId?.trainNo||s.trainId?.id||''}</td>
<td>${s.rundate}</td><td>${s.availableSeats}</td>
<td><button class="danger small" onclick="del('/admin/train/schedule/'+s.id)">Delete</button></td></tr>`).join('')
    }catch(e){
        show('scheduleMsg',e.message)
    }
}
async function createCoach(e){
    e.preventDefault();
    try{
        await api('/admin/train/coach',{
            method:'POST',body:[{
                trainSchedule:{
                    id:Number(coachSchedule.value)
                },totalSeats:Number(totalSeats.value),coach:coachType.value,availableSeats:Number(totalSeats.value),price:Number(coachPrice.value),coachNumber:Number(coachNumber.value)
            }]
        });
        e.target.reset();
        loadCoaches()
    }catch(e){
        show('coachMsg',e.message)
    }
}
async function loadCoaches(){
    try{
        const x=await api('/admin/train/coach');
        document.getElementById('coachTable').innerHTML=x.map(c=>`<tr><td>${c.id}</td><td>${c.trainSchedule?.id||''}</td><td>${c.coach} ${c.coachNumber||''}</td><td>${c.totalSeats}</td><td>${c.availableSeats}</td><td>₹${c.price}</td><td><button class="danger small" onclick="del('/admin/train/coach/'+c.id)">Delete</button></td></tr>`).join('')
    }catch(e){
        show('coachMsg',e.message)
    }
}
async function createSeat(e){
    e.preventDefault();
    try{
        await api('/admin/train/seat',{
            method:'POST',body:[{
                seatNumber:Number(seatNumber.value),trainScheduleId:Number(seatSchedule.value),berthType:berthType.value,price:Number(seatPrice.value),available:true,coachId:Number(seatCoachId.value)
            }]
        });
        e.target.reset();
        loadSeats()
    }catch(e){
        show('seatAdminMsg',e.message)
    }
}
async function loadSeats(){
    try{
        const x=await api('/admin/train/seat');
        document.getElementById('seatTable').innerHTML=x.slice(0,100).map(s=>`<tr><td>${s.id}</td><td>${s.seatNumber}</td><td>${s.coachId}</td><td>${s.trainScheduleId}</td><td>${s.berthType}</td><td>${s.available}</td><td>₹${s.price}</td><td><button class="danger small" onclick="del('/admin/train/seat/'+s.id)">Delete</button></td></tr>`).join('')
    }catch(e){
        show('seatAdminMsg',e.message)
    }
}
async function createRoute(e){
    e.preventDefault();
    try{
        await api('/admin/train/routes',{
            method:'POST',body:[{
                train:{
                    id:Number(routeTrainId.value)
                },station:{
                    id:Number(routeStationId.value)
                },departureTime:dep.value,arrivalTime:arr.value,haltMinutes:Number(halt.value),distance:Number(routeDistance.value),stationOrder:Number(stationOrder.value)
            }]
        });
        e.target.reset();
        loadRoutes()
    }catch(e){
        show('routeMsg',e.message)
    }
}
async function loadRoutes(){
    try{
        const x=await api('/admin/train/routes');
        document.getElementById('routeTable').innerHTML=x.map(r=>`<tr><td>${r.id}</td><td>${r.train?.trainNo||''}</td><td>${r.station?.code||''}</td><td>${r.arrivalTime||''}</td><td>${r.departureTime||''}</td><td>${r.stationOrder}</td><td><button class="danger small" onclick="del('/admin/train/routes/'+r.id)">Delete</button></td></tr>`).join('')
    }catch(e){
        show('routeMsg',e.message)
    }
}
function tab(id,btn){
    document.querySelectorAll('.section').forEach(x=>x.classList.remove('active'));
    document.getElementById(id).classList.add('active');
    document.querySelectorAll('.tabs button').forEach(x=>x.classList.remove('active'));
    btn.classList.add('active');
    if(id==='schedules')loadSchedules();
    if(id==='coaches')loadCoaches();
    if(id==='seats')loadSeats();
    if(id==='routes')loadRoutes()
}
