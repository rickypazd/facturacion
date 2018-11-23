package MODELO;

import Conexion.Conexion;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IMEX_SUCURSAL {

    private String IEP_NOMBRE;
    private int IEP_ID;
    private int IEP_PAIS;
    private double IEP_LONGITUD;
    private double IEP_LATITUD;
    private String IEP_CODPOSTAL;
    private int IEP_DEPARTAMENTO;

    private Conexion con = null;

    private String TBL = "IMEX_SUCURSAL";

    public IMEX_SUCURSAL(Conexion con) {
        this.con = con;
    }

    public long Insertar() throws SQLException {
        String consulta = "INSERT INTO " + TBL + "(\n"
                + "	IEP_NOMBRE, IEP_PAIS, IEP_LONGITUD, IEP_LATITUD, IEP_CODPOSTAL,  IEP_DEPARTAMENTO)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, getIEP_NOMBRE());
        ps.setInt(2, getIEP_PAIS());
        ps.setDouble(3, getIEP_LONGITUD());
        ps.setDouble(4, getIEP_LATITUD());
        ps.setString(5, getIEP_CODPOSTAL());
        ps.setInt(6, getIEP_DEPARTAMENTO());
        int affectedRosw =ps.executeUpdate();
        if(affectedRosw == 0){
         throw new SQLException("Creating user failed, no rows affected.");
        }
        long id=0;
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id=generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        ps.close();
        return id;
    }

    public JSONObject getById(int id) throws SQLException, JSONException {
        String consulta = "select ar.* "
                + "from " + TBL + " ar\n"
                + "where ar.id=?";
        PreparedStatement ps = con.statamet(consulta);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        JSONObject obj = new JSONObject();
        if (rs.next()) {
            obj = parseJson(rs);
        } else {
            obj.put("exito", "no");
        }
        ps.close();
        rs.close();
        return obj;
    }

    public JSONArray getAll() throws SQLException, JSONException {
        String consulta = "select ar.* "
                + "from " + TBL + " ar";
        PreparedStatement ps = con.statamet(consulta);
        ResultSet rs = ps.executeQuery();
        JSONArray arr = new JSONArray();
        JSONObject obj;
        while (rs.next()) {
            obj = new JSONObject();
            obj = parseJson(rs);
            arr.put(obj);
        }
        ps.close();
        rs.close();
        return arr;
    }

    private JSONObject parseObj;

    private JSONObject parseJson(ResultSet rs) throws JSONException, SQLException {
        parseObj = new JSONObject();
        parseObj.put("IEP_NOMBRE", rs.getString("IEP_NOMBRE") != null ? rs.getString("IEP_NOMBRE") : "");
        parseObj.put("IEP_ID", rs.getInt("IEP_ID"));//CAREFUL
        parseObj.put("IEP_PAIS", rs.getInt("IEP_PAIS"));
        parseObj.put("IEP_LONGITUD", rs.getDouble("IEP_LONGITUD"));
        parseObj.put("IEP_LATITUD", rs.getDouble("IEP_LATITUD"));
        parseObj.put("IEP_CODPOSTAL", rs.getString("IEP_CODPOSTAL") != null ? rs.getString("IEP_CODPOSTAL") : "");
        parseObj.put("IEP_DEPARTAMENTO", rs.getInt("IEP_DEPARTAMENTO"));
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("IEP_NOMBRE", getIEP_NOMBRE());
        obj.put("IEP_ID", getIEP_ID());
        obj.put("IEP_PAIS", getIEP_PAIS());
        obj.put("IEP_LONGITUD", getIEP_LONGITUD());
        obj.put("IEP_LATITUD", getIEP_LATITUD());
        obj.put("IEP_CODPOSTAL", getIEP_CODPOSTAL());
        obj.put("IEP_DEPARTAMENTO", getIEP_DEPARTAMENTO());
        return obj;
    }

    public String getIEP_NOMBRE() {
        return IEP_NOMBRE;
    }

    public void setIEP_NOMBRE(String IEP_NOMBRE) {
        this.IEP_NOMBRE = IEP_NOMBRE;
    }

    public int getIEP_ID() {
        return IEP_ID;
    }

    public void setIEP_ID(int IEP_ID) {
        this.IEP_ID = IEP_ID;
    }

    public int getIEP_PAIS() {
        return IEP_PAIS;
    }

    public void setIEP_PAIS(int IEP_PAIS) {
        this.IEP_PAIS = IEP_PAIS;
    }

    public double getIEP_LONGITUD() {
        return IEP_LONGITUD;
    }

    public void setIEP_LONGITUD(double IEP_LONGITUD) {
        this.IEP_LONGITUD = IEP_LONGITUD;
    }

    public double getIEP_LATITUD() {
        return IEP_LATITUD;
    }

    public void setIEP_LATITUD(double IEP_LATITUD) {
        this.IEP_LATITUD = IEP_LATITUD;
    }

    public String getIEP_CODPOSTAL() {
        return IEP_CODPOSTAL;
    }

    public void setIEP_CODPOSTAL(String IEP_CODPOSTAL) {
        this.IEP_CODPOSTAL = IEP_CODPOSTAL;
    }

    public int getIEP_DEPARTAMENTO() {
        return IEP_DEPARTAMENTO;
    }

    public void setIEP_DEPARTAMENTO(int IEP_DEPARTAMENTO) {
        this.IEP_DEPARTAMENTO = IEP_DEPARTAMENTO;
    }

    

    public String getTBL() {
        return TBL;
    }

    public void setTBL(String TBL) {
        this.TBL = TBL;
    }

    public Conexion getCon() {
        return con;
    }

    public void setCon(Conexion con) {
        this.con = con;
    }

    public JSONObject getParseObj() {
        return parseObj;
    }

    public void setParseObj(JSONObject parseObj) {
        this.parseObj = parseObj;
    }

}
