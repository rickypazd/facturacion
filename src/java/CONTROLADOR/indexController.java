/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLADOR;

import Conexion.Conexion;
import UTILES.URL;


import java.io.*;

import java.nio.file.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author RICKY
 */
@MultipartConfig
@WebServlet(name = "indexController", urlPatterns = {"/indexController"})
public class indexController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Conexion con = new Conexion(URL.db_usr, URL.db_pass); //conexion linux

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            String evento = request.getParameter("evento");
            boolean retornar = true;
            String html = "";
            switch (evento) {
                case "login":
                    html = login(request, con);
                    break;
                case "insert_imexsucrequisitos":
                    html = insert_imexsucrequisitos(request, con);
                    break;

            }
            con.Close();
            if (retornar) {
                response.getWriter().write(html);
            }
        } catch (SQLException ex) {
            Logger.getLogger(indexController.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("falso");
        } catch (JSONException ex) {
            Logger.getLogger(indexController.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("falso");
        } catch (Exception ex) {
            Logger.getLogger(indexController.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().write("falso");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String login(HttpServletRequest request, Conexion con) throws SQLException, JSONException {
//        String usuario = request.getParameter("usuario");
//        String pass = request.getParameter("pass");
//        String token = request.getParameter("token");
//        USUARIO usr = new USUARIO(con);
//        JSONObject obj = usr.get_por_usr_y_pass(usuario, pass);
        return "exito";
    }

    private String insert_imexsucrequisitos(HttpServletRequest request, Conexion con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
