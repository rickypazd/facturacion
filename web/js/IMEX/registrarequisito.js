/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var url = "admin/adminController";
var listaArituclos = null;
var id_sucurs = 0;
var tp_paqt = 0;
var tipoResponse = 3;
var expImp = 3;
var tpValor = 3;
var valid = 0;
$(document).ready(function(){
    cargarSucursales();
    cargarTipoPaqt();
    $("input[name=group1]").bind("change",function(){
         valid = $('input[name="group1"]:checked').val();
         if (valid == 1){
             $("#dropdownmenu1").prop("disabled", true);
             $("#dropdownmenu2").prop("disabled", false);
             id_sucurs = 0;//NO SUCUR
             tp_paqt = 0;
             
         }else{
             $("#dropdownmenu2").prop("disabled", true);
             $("#dropdownmenu1").prop("disabled", false);
             id_sucurs = 0;//NO SUCUR
             tp_paqt = 0;
         }
               
    });
});

function cargarSucursales() {
    var TokenAcceso = "edsoncito";
    $.post(url, {evento: "GETALL_IMEX_SUCURSAL", TokenAcceso: TokenAcceso}, function (respuesta) {
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
                    html+="<li><a href='javaScript:void(0);' onclick='select_sucur("+obj.IEP_ID+",this);' data-obj='"+JSON.stringify(obj)+"'>"+obj.IEP_NOMBRE+"</a></li>";
                });
                  $("#sucurs").html(html);

            }
        }
    });
}


function select_sucur(pais_id, iten){
    //var data = $(iten).data("obj");
   // $(iten).parent().parent().parent().css("display","none");
   
    if (pais_id != ""){
        id_sucurs = pais_id;
    }
    
    
}

function cargarTipoPaqt() {
    var TokenAcceso = "edsoncito";
    $.post(url, {evento: "GETALL_PQEV_TIPOPAQT", TokenAcceso: TokenAcceso}, function (respuesta) {
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
                    html+="<li><a href='javaScript:void(0);' onclick='select_tpaqt("+obj.PQE_ID+",this);' data-obj='"+JSON.stringify(obj)+"'>"+obj.PQE_DESC+"</a></li>";
                });
                  $("#tpaqt").html(html);

            }
        }
    });
}


function select_tpaqt(pais_id, iten){
    //var data = $(iten).data("obj");
   // $(iten).parent().parent().parent().css("display","none");
   
    if (pais_id != ""){
        tp_paqt = pais_id;
    }
    
    
}
function select_Response(pais_id, iten){
    //var data = $(iten).data("obj");
   // $(iten).parent().parent().parent().css("display","none");
   
    if (pais_id != ""){
        tipoResponse = pais_id;
    }
}

function select_ExpImp(pais_id, iten){
    //var data = $(iten).data("obj");
   // $(iten).parent().parent().parent().css("display","none");
   
    if (pais_id != ""){
        expImp = pais_id;
    }
}
function select_TpValor(pais_id, iten){
    //var data = $(iten).data("obj");
   // $(iten).parent().parent().parent().css("display","none");
   
    if (pais_id != ""){
        tpValor = pais_id;
    }
}

function registrarequisito() {
    var exito = true;
    var nombre = $("#nombre").val() || null;
    var descripcion = $("#descripcion").val() || null;
    var valcosto = $("#valcos").val() || null;
    var TokenAcceso = "edsoncito";
    if (nombre != null && nombre.length > 0 && (id_sucurs >0 || tp_paqt > 0)) {
    } else {
        $("#nome").removeClass("invisible").addClass("visible");
        $("#coninobli").removeClass("invisible").addClass("visible");
        exito = false;
    }
    if (exito) {
        $.post(url, {evento: "INSERT_IMEX_SUCREQUISITOS", TokenAcceso: TokenAcceso,IEX_NOMBRE:nombre,
        IEX_REQUISITODESC:descripcion,IEX_SUCURSAL:id_sucurs,IEX_DESCRIPCION:"",IEX_TIPORESPONSE:tipoResponse,
        IEX_TIPOTRANSAC:expImp,IEX_IDTIPOPAQUETE:tp_paqt,IEX_TIPOCOSTO:tpValor,IEX_COSTO:valcosto}, function (respuesta) {
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
                    //error
                    alert(obj.mensaje);
                } else {
                    //exito
                    
                    $("#nombre").val("");
                    $("#descripcion").val("");
                    $("#valcos").val("");
                    $("#nome").removeClass("visible").addClass("invisible");
                    $("#coninobli").removeClass("visible").addClass("invisible");
                    $("#nombre").focus();

                }
            }
        });
    } else {
        alert("No selecciono todos los campos");
    }
}