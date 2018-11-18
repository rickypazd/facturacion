package CONTROLADOR;

import Conexion.Conexion;
import MODELO.IMEX_CATEGORIAPAQT;
import MODELO.IMEX_SUCREQUISITOS;
import MODELO.PQEV_TIPOSENVIOPAQT;

import UTILES.URL;

import UTILES.RESPUESTA;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.nio.file.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
@WebServlet(name = "adminController", urlPatterns = {"/admin/adminController"})
public class adminController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //<editor-fold defaultstate="collapsed" desc="CONFIGURACIONES">
        Conexion con = new Conexion(URL.db_usr, URL.db_pass);
        con.Transacction();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        String evento = request.getParameter("evento");
        String tokenAcceso = request.getParameter("TokenAcceso");
        boolean retornar = true;
        String html = "";
//</editor-fold>

        if (tokenAcceso.equals(URL.TokenAcceso)) {
            switch (evento) {

                //<editor-fold defaultstate="collapsed" desc="IMEX_SUCREQUISITOS">
                case "INSERT_IMEX_SUCREQUISITOS":
                    html = INSERT_IMEX_SUCREQUISITOS(request, con);
                    break;
                case "GETALL_IMEX_SUCREQUISITOS":
                    html = GETALL_IMEX_SUCREQUISITOS(request, con);
                    break;
                case "GETBYID_IMEX_SUCREQUISITOS":
                    html = GETBYID_IMEX_SUCREQUISITOS(request, con);
                    break;
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="IMEX_CATEGORIAPAQT">   
                case "INSERT_IMEX_CATEGORIAPAQT":
                    html = INSERT_IMEX_CATEGORIAPAQT(request, con);
                    break;
                case "GETALL_IMEX_CATEGORIAPAQT":
                    html = GETALL_IMEX_CATEGORIAPAQT(request, con);
                    break;
                case "GETBYID_IMEX_CATEGORIAPAQT":
                    html = GETBYID_IMEX_CATEGORIAPAQT(request, con);
                    break;
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="PQEV_TIPOSENVIOPAQT">
                case "INSERT_PQEV_TIPOSENVIOPAQT":
                    html = INSERT_PQEV_TIPOSENVIOPAQT(request, con);
                    break;
                case "GETALL_PQEV_TIPOSENVIOPAQT":
                    html = GETALL_PQEV_TIPOSENVIOPAQT(request, con);
                    break;
                case "GETBYID_PQEV_TIPOSENVIOPAQT":
                    html = GETBYID_PQEV_TIPOSENVIOPAQT(request, con);
                    break;
//</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="CASOS DE ERROR">
                default:
                    RESPUESTA resp = new RESPUESTA(0, "Servisis: No se encontro el parametro evento.", "Servicio no encontrado.", "{}");
                    html = resp.toString();
                    break;
            }
        } else {
            RESPUESTA resp = new RESPUESTA(0, "Servisis: Token de acceso erroneo.", "Token denegado", "{}");
            html = resp.toString();
        }
        //</editor-fold>

        con.commit();
        con.Close();
        if (retornar) {
            response.getWriter().write(html);
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

    private void descargar(HttpServletRequest request, HttpServletResponse response, Conexion con) {
        try {
            String SRC = request.getParameter("nombre_arc");
            File fileToDownload = new File(SRC);
            FileInputStream in = new FileInputStream(fileToDownload);
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileToDownload.getName() + "\"");
            //String mimeType =  new FileTypeMap().getContentType(filePath); 
            response.setContentType(Files.probeContentType(Paths.get(SRC)));
            response.setContentLength(in.available());
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            out.flush();
            out.close();
            in.close();
        } catch (FileNotFoundException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "No se encontro el archibo.", "{}");
        } catch (IOException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "No se encontro el archibo.", "{}");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="IMEX_SUCREQUISITOS">
    private String INSERT_IMEX_SUCREQUISITOS(HttpServletRequest request, Conexion con) {
        String nameAlert = "Requisito";
        try {
            int IEX_SUCURSAL = Integer.parseInt(request.getParameter("IEX_SUCURSAL"));
            int IEX_IDTIPOPAQUETE = Integer.parseInt(request.getParameter("IEX_IDTIPOPAQUETE"));
            int IEX_TIPOCOSTO = Integer.parseInt(request.getParameter("IEX_TIPOCOSTO"));
            int IEX_TIPORESPONSE = Integer.parseInt(request.getParameter("IEX_TIPORESPONSE"));
            int IEX_TIPOTRANSAC = Integer.parseInt(request.getParameter("IEX_TIPOTRANSAC"));
            double IEX_COSTO = Double.parseDouble(request.getParameter("IEX_SUCURSAL"));
            String IEX_REQUISITODESC = request.getParameter("IEX_REQUISITODESC");
            String IEX_NOMBRE = request.getParameter("IEX_NOMBRE");
            String IEX_DESCRIPCION = request.getParameter("IEX_DESCRIPCION");
            IMEX_SUCREQUISITOS imex_sucrequisitos = new IMEX_SUCREQUISITOS(con);
            imex_sucrequisitos.setIEX_SUCURSAL(IEX_SUCURSAL);
            imex_sucrequisitos.setIEX_IDTIPOPAQUETE(IEX_IDTIPOPAQUETE);
            imex_sucrequisitos.setIEX_TIPOCOSTO(IEX_TIPOCOSTO);
            imex_sucrequisitos.setIEX_TIPORESPONSE(IEX_TIPORESPONSE);
            imex_sucrequisitos.setIEX_TIPOTRANSAC(IEX_TIPOTRANSAC);
            imex_sucrequisitos.setIEX_COSTO(IEX_COSTO);
            imex_sucrequisitos.setIEX_REQUISITODESC(IEX_REQUISITODESC);
            imex_sucrequisitos.setIEX_NOMBRE(IEX_NOMBRE);
            imex_sucrequisitos.setIEX_DESCRIPCION(IEX_DESCRIPCION);
            long id = imex_sucrequisitos.Insertar();
            imex_sucrequisitos.setIEX_ID((int) id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", imex_sucrequisitos.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String GETALL_IMEX_SUCREQUISITOS(HttpServletRequest request, Conexion con) {
        String nameAlert = "Requisito";
        try {
            IMEX_SUCREQUISITOS imex_sucrequisitos = new IMEX_SUCREQUISITOS(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", imex_sucrequisitos.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String GETBYID_IMEX_SUCREQUISITOS(HttpServletRequest request, Conexion con) {
        String nameAlert = "Requisito";
        try {
            int id = Integer.parseInt(request.getParameter("IEX_ID"));
            IMEX_SUCREQUISITOS imex_sucrequisitos = new IMEX_SUCREQUISITOS(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", imex_sucrequisitos.getById(id).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="IMEX_CATEGORIAPAQT">
    private String INSERT_IMEX_CATEGORIAPAQT(HttpServletRequest request, Conexion con) {
        String nameAlert = "Categoria de paquete";
        try {
            String IEX_NOMBRE = request.getParameter("IEX_NOMBRE");
            String IEX_DESC = request.getParameter("IEX_DESC");
            IMEX_CATEGORIAPAQT imex_categoriapaqt = new IMEX_CATEGORIAPAQT(con);
            imex_categoriapaqt.setIEX_DESC(IEX_DESC);
            imex_categoriapaqt.setIEX_NOMBRE(IEX_NOMBRE);
            long id = imex_categoriapaqt.Insertar();
            imex_categoriapaqt.setIEX_ID((int) id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", imex_categoriapaqt.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String GETALL_IMEX_CATEGORIAPAQT(HttpServletRequest request, Conexion con) {
        String nameAlert = "Categoria de paquete";
        try {
            IMEX_CATEGORIAPAQT imex_categoriapaqt = new IMEX_CATEGORIAPAQT(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", imex_categoriapaqt.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String GETBYID_IMEX_CATEGORIAPAQT(HttpServletRequest request, Conexion con) {
        String nameAlert = "Categoria de paquete";
        try {
            int id = Integer.parseInt(request.getParameter("IEX_ID"));
            IMEX_CATEGORIAPAQT imex_categoriapaqt = new IMEX_CATEGORIAPAQT(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", imex_categoriapaqt.getById(id).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="PQEV_TIPOSENVIOPAQT">
    private String INSERT_PQEV_TIPOSENVIOPAQT(HttpServletRequest request, Conexion con) {
        String nameAlert = "Tipo de envio de paquete";
        try {
            String PQE_NOMBRE = request.getParameter("PQE_NOMBRE");
            PQEV_TIPOSENVIOPAQT pqev_tiposenviopaqt = new PQEV_TIPOSENVIOPAQT(con);
            pqev_tiposenviopaqt.setPQE_NOMBRE(PQE_NOMBRE);
            long id = pqev_tiposenviopaqt.Insertar();
            pqev_tiposenviopaqt.setPQE_CONTAINID((int) id);
            RESPUESTA resp = new RESPUESTA(1, "", nameAlert + " registrado con exito.", pqev_tiposenviopaqt.getJson().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al registrar " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String GETALL_PQEV_TIPOSENVIOPAQT(HttpServletRequest request, Conexion con) {
        String nameAlert = "Categoria de paquete";
        try {
             PQEV_TIPOSENVIOPAQT pqev_tiposenviopaqt = new PQEV_TIPOSENVIOPAQT(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", pqev_tiposenviopaqt.getAll().toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }

    private String GETBYID_PQEV_TIPOSENVIOPAQT(HttpServletRequest request, Conexion con) {
        String nameAlert = "Categoria de paquete";
        try {
            int id = Integer.parseInt(request.getParameter("PQE_CONTAINID"));
            PQEV_TIPOSENVIOPAQT pqev_tiposenviopaqt = new PQEV_TIPOSENVIOPAQT(con);
            RESPUESTA resp = new RESPUESTA(1, "", "Exito.", pqev_tiposenviopaqt.getById(id).toString());
            return resp.toString();
        } catch (SQLException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al obtener " + nameAlert + ".", "{}");
            return resp.toString();
        } catch (JSONException ex) {
            con.rollback();
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
            RESPUESTA resp = new RESPUESTA(0, ex.getMessage(), "Error al convertir " + nameAlert + " a JSON.", "{}");
            return resp.toString();
        }
    }
    // </editor-fold>
}
