/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url="indexController";
$(document).ready(function(){
    $.post(url,{evento:"login"},function(resp){
        
    })
});

function ver_pagina(html){
    window.location.href=html;
}