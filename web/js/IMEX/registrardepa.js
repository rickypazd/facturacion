/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var url = "admin/adminController";
var listaArituclos = null;
var id_pais = 0;
var id_departamento = 0;
$(document).ready(function(){
    cargarPais(); 
});

function cargarPais() {
    var TokenAcceso = "edsoncito";
    $.post(url, {evento: "GETALL_CLTE_PAIS", TokenAcceso: TokenAcceso}, function (respuesta) {
        if (respuesta != null) {
            var obj = $.parseJSON(respuesta);
            if (obj.estado != 1) {
                //error
                
            } else {
                //exito 
                
                var objCLTE_PAIS = $.parseJSON(obj.resp); 
                var html="";
                $.each(objCLTE_PAIS, function(i,obj){
                  //  alert("i:"+i+" obj:"+obj+" id:"+obj.PAIS_ID);  
                    html+="<li><a href='javaScript:void(0);' onclick='select_pais("+obj.PAIS_ID+",this);' data-obj='"+JSON.stringify(obj)+"'>"+obj.PAIS_DESCRPCION+"</a></li>";
                });
                  $("#pais").html(html);

            }
        }
    });
}


function select_pais(pais_id, iten){
    //var data = $(iten).data("obj");
   // $(iten).parent().parent().parent().css("display","none");
   
    if (pais_id != ""){
        id_pais = pais_id;
    }
    
    
}

function registrardepartamento() {
    var exito = true;
    var nombre = $("#nombre").val() || null;
    var TokenAcceso = "edsoncito";
    if (nombre != null && nombre.length > 0 && id_pais >0) {
    } else {
        $("#nome").removeClass("invisible").addClass("visible");
        $("#coninobli").removeClass("invisible").addClass("visible");
        exito = false;
    }
    if (exito) {
        $.post(url, {evento: "INSERT_IMEX_DEPARTAMENTOSP", TokenAcceso: TokenAcceso,
            IMEX_CIUDADES: nombre,IMEX_PAISID :id_pais}, function (respuesta) {
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
                    //error
                    alert(obj.mensaje);
                } else {
                    //exito
                    
                    $("#nombre").val("");
                    $("#nome").removeClass("visible").addClass("invisible");
                    $("#coninobli").removeClass("visible").addClass("invisible");
                    $("#nombre").focus();

                }
            }
        });
    } else {

    }
}