var button = document.getElementById("btnAdd");
var selectFiscal = document.getElementById("selFiscal");
var divFiscais = document.getElementById("listFiscais");
button.addEventListener("click",addPerson);


function addPerson(){
    //alert("TESTE");
    var item = document.createElement("div");
    item.className = "alert alert-primary alert-dismissible fade show";
    item.setAttribute("role", "alert");
    item.id = $("#selFiscal option:selected").val();
    item.innerHTML = $("#selFiscal option:selected").text();
    var close = document.createElement("button");
    close.setAttribute("type","button");
    close.setAttribute("data-dismiss","alert");
    close.className = "close";
    var sp = document.createElement("span");
    sp.setAttribute("aria-hidden", "true");
    sp.innerHTML = "&times";
    close.appendChild(sp);
    item.appendChild(close);
    divFiscais.appendChild(item);
    $("#"+item.id).on('close.bs.alert', removePerson);
    item = null;
}

function removePerson(){
    alert("removed");
}