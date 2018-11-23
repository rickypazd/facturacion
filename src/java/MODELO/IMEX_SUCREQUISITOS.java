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

public class IMEX_SUCREQUISITOS {

    private int IEX_SUCURSAL;
    private int IEX_ID;
    private int IEX_IDTIPOPAQUETE;
    private double IEX_COSTO;
    private int IEX_TIPORESPONSE;
    private int IEX_TIPOTRANSAC;
    private int IEX_TIPOCOSTO;
    private String IEX_REQUISITODESC;
    private String IEX_NOMBRE;
    private String IEX_DESCRIPCION;

    private Conexion con = null;

    private String TBL = "IMEX_SUCREQUISITOS";

    public IMEX_SUCREQUISITOS(Conexion con) {
        this.con = con;
    }

    public long Insertar() throws SQLException {
        String consulta = "INSERT INTO " + TBL + "(\n"
                + "	IEX_SUCURSAL, IEX_IDTIPOPAQUETE, IEX_TIPOCOSTO, IEX_TIPORESPONSE, IEX_TIPOTRANSAC,  IEX_COSTO, IEX_REQUISITODESC, IEX_NOMBRE, IEX_DESCRIPCION)\n"
                + "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.statamet(consulta, Statement.RETURN_GENERATED_KEYS);
        if (getIEX_SUCURSAL() == 0) {
            ps.setNull(1, 1);
        } else {
            ps.setInt(1, getIEX_SUCURSAL());
        }
        if (getIEX_IDTIPOPAQUETE() == 0) {
            ps.setNull(2, 1);
        } else {
            ps.setInt(2, getIEX_IDTIPOPAQUETE());
        }
        ps.setInt(3, getIEX_TIPOCOSTO());
        ps.setInt(4, getIEX_TIPORESPONSE());
        ps.setInt(5, getIEX_TIPOTRANSAC());
        ps.setDouble(6, getIEX_COSTO());
        ps.setString(7, getIEX_REQUISITODESC());
        ps.setString(8, getIEX_NOMBRE());
        ps.setString(9, getIEX_DESCRIPCION());
        int affectedRosw = ps.executeUpdate();
        if (affectedRosw == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        long id = 0;
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            } else {
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
        parseObj.put("IEX_SUCURSAL", rs.getInt("IEX_SUCURSAL"));
        parseObj.put("IEX_ID", rs.getInt("IEX_ID"));
        parseObj.put("IEX_IDTIPOPAQUETE", rs.getInt("IEX_IDTIPOPAQUETE"));
        parseObj.put("IEX_TIPORESPONSE", rs.getInt("IEX_TIPORESPONSE"));
        parseObj.put("IEX_TIPOCOSTO", rs.getInt("IEX_TIPOCOSTO"));
        parseObj.put("IEX_COSTO", rs.getDouble("IEX_COSTO"));
        parseObj.put("IEX_REQUISITODESC", rs.getString("IEX_REQUISITODESC") != null ? rs.getString("IEX_REQUISITODESC") : "");
        parseObj.put("IEX_NOMBRE", rs.getString("IEX_NOMBRE") != null ? rs.getString("IEX_NOMBRE") : "");
        parseObj.put("IEX_DESCRIPCION", rs.getString("IEX_DESCRIPCION") != null ? rs.getString("IEX_DESCRIPCION") : "");
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("IEX_SUCURSAL", getIEX_SUCURSAL());
        obj.put("IEX_ID", getIEX_ID());
        obj.put("IEX_IDTIPOPAQUETE", getIEX_IDTIPOPAQUETE());
        obj.put("IEX_TIPORESPONSE", getIEX_TIPORESPONSE());
        obj.put("IEX_TIPOCOSTO", getIEX_TIPOCOSTO());
        obj.put("IEX_COSTO", getIEX_COSTO());
        obj.put("IEX_REQUISITODESC", getIEX_REQUISITODESC());
        obj.put("IEX_NOMBRE", getIEX_NOMBRE());
        obj.put("IEX_DESCRIPCION", getIEX_DESCRIPCION());
        return obj;
    }

    public int getIEX_SUCURSAL() {
        return IEX_SUCURSAL;
    }

    public void setIEX_SUCURSAL(int IEX_SUCURSAL) {
        this.IEX_SUCURSAL = IEX_SUCURSAL;
    }

    public int getIEX_ID() {
        return IEX_ID;
    }

    public void setIEX_ID(int IEX_ID) {
        this.IEX_ID = IEX_ID;
    }

    public int getIEX_IDTIPOPAQUETE() {
        return IEX_IDTIPOPAQUETE;
    }

    public void setIEX_IDTIPOPAQUETE(int IEX_IDTIPOPAQUETE) {
        this.IEX_IDTIPOPAQUETE = IEX_IDTIPOPAQUETE;
    }

    public double getIEX_COSTO() {
        return IEX_COSTO;
    }

    public void setIEX_COSTO(double IEX_COSTO) {
        this.IEX_COSTO = IEX_COSTO;
    }

    public int getIEX_TIPORESPONSE() {
        return IEX_TIPORESPONSE;
    }

    public void setIEX_TIPORESPONSE(int IEX_TIPORESPONSE) {
        this.IEX_TIPORESPONSE = IEX_TIPORESPONSE;
    }

    public int getIEX_TIPOTRANSAC() {
        return IEX_TIPOTRANSAC;
    }

    public void setIEX_TIPOTRANSAC(int IEX_TIPOTRANSAC) {
        this.IEX_TIPOTRANSAC = IEX_TIPOTRANSAC;
    }

    public int getIEX_TIPOCOSTO() {
        return IEX_TIPOCOSTO;
    }

    public void setIEX_TIPOCOSTO(int IEX_TIPOCOSTO) {
        this.IEX_TIPOCOSTO = IEX_TIPOCOSTO;
    }

    public String getIEX_REQUISITODESC() {
        return IEX_REQUISITODESC;
    }

    public void setIEX_REQUISITODESC(String IEX_REQUISITODESC) {
        this.IEX_REQUISITODESC = IEX_REQUISITODESC;
    }

    public String getIEX_NOMBRE() {
        return IEX_NOMBRE;
    }

    public void setIEX_NOMBRE(String IEX_NOMBRE) {
        this.IEX_NOMBRE = IEX_NOMBRE;
    }

    public String getIEX_DESCRIPCION() {
        return IEX_DESCRIPCION;
    }

    public void setIEX_DESCRIPCION(String IEX_DESCRIPCION) {
        this.IEX_DESCRIPCION = IEX_DESCRIPCION;
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
