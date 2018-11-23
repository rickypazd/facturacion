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

public class IMEX_DEPARTAMENTOSP {

    private int IMEX_ID;
    private int IMEX_PAISID;
    private String IMEX_CIUDADES;

    private Conexion con = null;

    private String TBL = "IMEX_DEPARTAMENTOSP";

    public IMEX_DEPARTAMENTOSP(Conexion con) {
        this.con = con;
    }

    public long Insertar() throws SQLException {
        String consulta = "INSERT INTO " + TBL + "(\n"
                + "	IMEX_CIUDADES, IMEX_PAISID)\n"
                + "	VALUES (?, ?);";
        PreparedStatement ps = con.statamet(consulta, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, getIMEX_CIUDADES());
        ps.setInt(2, getIMEX_PAISID());

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
    public JSONArray getAllby_IMEX_PAISID(int id) throws SQLException, JSONException {
        String consulta = "select ar.* "
                + "from " + TBL + " ar where ar.IMEX_PAISID="+id;
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
        parseObj.put("IMEX_CIUDADES", rs.getString("IMEX_CIUDADES") != null ? rs.getString("IMEX_CIUDADES") : "");
        parseObj.put("IMEX_PAISID", rs.getInt("IMEX_PAISID"));
        parseObj.put("IMEX_ID", rs.getInt("IMEX_ID"));
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("IMEX_CIUDADES", getIMEX_CIUDADES());
        obj.put("IMEX_PAISID", getIMEX_PAISID());
        obj.put("IMEX_ID", getIMEX_ID());

        return obj;
    }

    public int getIMEX_ID() {
        return IMEX_ID;
    }

    public void setIMEX_ID(int IMEX_ID) {
        this.IMEX_ID = IMEX_ID;
    }

    public int getIMEX_PAISID() {
        return IMEX_PAISID;
    }

    public void setIMEX_PAISID(int IMEX_PAISID) {
        this.IMEX_PAISID = IMEX_PAISID;
    }

    public String getIMEX_CIUDADES() {
        return IMEX_CIUDADES;
    }

    public void setIMEX_CIUDADES(String IMEX_CIUDADES) {
        this.IMEX_CIUDADES = IMEX_CIUDADES;
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
