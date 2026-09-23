let stations=[], searchResults=[], selectedTrain=null, selectedCoach=null, selectedSeats=[];
document.addEventListener('DOMContentLoaded',()=>{
    requireLogin();const u=currentUser();document.getElementById('userName').textContent=u?.name||u?.email||'User';loadStations();loadBookings();
});
async function loadStations(){
    try{
        stations=await api('/user/stations');
        fillSelect('source',stations,'id',x=>x.code+' - '+x.name);
        fillSelect('destination',stations,'id',x=>x.code+' - '+x.name)
    }catch(e){
        show('searchMsg','Could not load stations: '+e.message)
    }
}
async function searchTrains(e){
    e.preventDefault();
    searchResults=[];
    selectedTrain=null;
    selectedCoach=null;
    selectedSeats=[];
    document.getElementById('results').innerHTML='';
    show('searchMsg','');
    try{
        const body={
            sourceStationId:Number(source.value),destinationStationId:Number(destination.value),journeyDate:journeyDate.value
        };
        searchResults=await api('/user/train',{
            method:'POST',body
        });
        renderResults()
    }catch(e){
        show('searchMsg',e.message)
    }
}
function renderResults(){
    if(!searchResults.length){
        document.getElementById('results').innerHTML='<p class="muted">No trains found.</p>';
        return
    }
    document.getElementById('results').innerHTML=searchResults.map((t,i)=>`<div class="card"><b>${t.trainNo} - ${t.trainName}</b><p>${t.sourceStationName} → ${t.destinationStationName}</p><p>${t.journeyDate} | Departure: ${t.departureTime||'-'} | Arrival: ${t.arrivalTime||'-'}</p><div class="grid">${(t.coaches||[]).map((c,j)=>`<button class="green" onclick="selectCoach(${i},${j})">${
        c.coach
    }
    ${
        c.coachNumber||''
    }
    <br>${
        c.availableSeats
    }
    seats | ₹${
        c.price
    }
    </button>`).join('')}</div></div>`).join('')
}
function selectCoach(i,j) {
    selectedTrain = searchResults[i];
    selectedCoach = selectedTrain.coaches[j];
    selectedSeats = [];
    document.getElementById('bookingBox').classList.remove('hidden');
    document.getElementById('bookingTitle').textContent = `${selectedTrain.trainName} - ${selectedCoach.coach} ${selectedCoach.coachNumber || ''}`;
    loadSeats()
}
async function loadSeats() {
    try {
        const seats = await api(
            '/user/seats/' +
            selectedTrain.scheduleId +
            '/' +
            selectedCoach.id
        );

        document.getElementById('seats').innerHTML = seats.map(seat => {

            if (seat.available) {
                return `
                    <span
                        class="seat available"
                        onclick="toggleSeat(${seat.id})"
                    >
                        ${seat.seatNumber}
                    </span>
                `;
            }

            return `
                <span class="seat unavailable">
                    ${seat.seatNumber}
                </span>
            `;

        }).join('');

        document.getElementById('passengers').innerHTML = '';

    } catch (error) {
        show('seatMsg', error.message);
    }
}
async function toggleSeat(id){
    if(selectedSeats.includes(id))selectedSeats=selectedSeats.filter(x=>x!==id);
    else if(selectedSeats.length<4)selectedSeats.push(id);
    await loadPassengerForms()
}
async function loadPassengerForms(){
    const seats=await api('/user/seats/'+selectedTrain.scheduleId+'/'+selectedCoach.id);
    document.getElementById('passengers').innerHTML=selectedSeats.map((id,i)=>{
        const s=seats.find(x=>x.id===id);
        return `<div class="card">
         <b>Passenger ${i+1} - Seat ${s?.seatNumber}</b>
         <div class="form-grid">
        <input id="pname${i}" placeholder="Name">
        <input id="page${i}" type="number" placeholder="Age">
        <select id="pgender${i}">
       <option>MALE</option>
        <option>FEMALE</option>
        <option>OTHER</option>
       </select>
        <select id="pberth${i}">
        <option value="LOWER">LOWER</option>
       <option value="MIDDLE">MIDDLE</option>
       <option value="UPPER">UPPER</option>
        <option value="SIDE_LOWER">SIDE_LOWER</option>
       <option value="SIDE_UPPER">SIDE_UPPER</option>
        </select></div></div>`
    }).join('')
}
async function book(){
    if(!selectedSeats.length){
        show('seatMsg','Select at least one seat');
        return
    }
    try{
        const u=currentUser();
        const seats=await api('/user/seats/'+selectedTrain.scheduleId+'/'+selectedCoach.id);
        const passengers=selectedSeats.map((id,i)=>({
            seatId:id,name:document.getElementById('pname'+i).value,age:Number(document.getElementById('page'+i).value),gender:document.getElementById('pgender'+i).value,berthPreference:document.getElementById('pberth'+i).value
        }));
        const data=await api('/user/booking',{
            method:'POST',body:{
                userId:u.id,trainId:selectedTrain.trainId,trainScheduleId:selectedTrain.scheduleId,sourceStationId:selectedTrain.sourceStationId,destinationStationId:selectedTrain.destinationStationId,coachId:selectedCoach.id,passengers
            }
        });
        show('seatMsg','Booking successful. PNR: '+data.PNR,true);
        loadBookings()
    }catch(e){
        show('seatMsg',e.message)
    }
}
async function loadBookings(){
    const u=currentUser();
    if(!u)return;
    try{
        const data=await api('/user/booking/'+u.id);
        document.getElementById('tickets').innerHTML=data.length?data.map(t=>`<div class="card">
       <b>PNR: ${t.PNR}</b>
      <span class="badge">${t.status}</span>
      <p>${t.trainName} (${t.trainCode})</p>
       <p>${t.sourceStationName} → ${t.destinationStationName}</p>
      <p>Date: ${t.journeyDate} | Coach: ${t.coach||t.coachType||'-'} | Fare: ₹${t.totalFare}</p>
     <p>${(t.passengers||[]).map(p=>`${
            p.name}, ${p.age}, ${p.gender}, Seat ${p.seatNumber}`).join('<br>')}</p>
      <button class="danger small" onclick="cancelTicket(${t.id})">Cancel</button></div>`).join(''):'<p class="muted">No bookings yet.</p>'
    }catch(e){
        document.getElementById('tickets').innerHTML='<p class="error">'+e.message+'</p>'
    }
}
async function cancelTicket(id){
    if(!confirm('Cancel this booking?'))return;
    try{
        const msg=await api('/user/booking/'+id,{
            method:'DELETE'
        });
        alert(msg);
        loadBookings()
    }catch(e){
        alert(e.message)
    }
}
