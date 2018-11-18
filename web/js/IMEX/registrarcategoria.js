/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var url = "admin/adminController";
var listaArituclos = null;
$(document).ready(function () {

});

function registrarcategoria() {
    var exito = true;
    var nombre = $("#nombre").val() || null;
    var TokenAcceso = "edsoncito";
    var descripcion = $("#descripcion").val() || null;
    if (nombre != null && nombre.length > 0) {
    } else {
        $("#nome").removeClass("invisible").addClass("visible");
        $("#coninobli").removeClass("invisible").addClass("visible");
        exito = false;
    }
    if (descripcion != null && descripcion.length > 0) {
    } else {
        $("#desc").removeClass("invisible").addClass("visible");
        $("#coninobli").removeClass("invisible").addClass("visible");
        exito = false;
    }
    if (exito) {
        $.post(url, {evento: "INSERT_IMEX_CATEGORIAPAQT", TokenAcceso: TokenAcceso, IEX_DESC: descripcion,
            IEX_NOMBRE: nombre}, function (respuesta) {
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
                    //error
                    alert(obj.mensaje);
                } else {
                    //exito
                    $("#nombre").val("");
                    $("#descripcion").val("");
                    $("#desc").removeClass("visible").addClass("invisible");
                    $("#nome").removeClass("visible").addClass("invisible");
                    $("#coninobli").removeClass("visible").addClass("invisible");
                    $("#nombre").focus();

                }
            }
        });
    } else {

    }
}