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

public class PQEV_TIPOSENVIOPAQT {

    private int PQE_CONTAINID;
    private String PQE_NOMBRE;

    private Conexion con = null;

    private String TBL = "PQEV_TIPOSENVIOPAQT";

    public PQEV_TIPOSENVIOPAQT(Conexion con) {
        this.con = con;
    }

    public long Insertar() throws SQLException {
        String consulta = "INSERT INTO " + TBL + "(\n"
                + "	PQE_NOMBRE)\n"
                + "	VALUES (?);";
        PreparedStatement ps = con.statamet(consulta, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, getPQE_NOMBRE());
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

    public JSONArray gelAll() throws SQLException, JSONException {
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
        parseObj.put("PQE_CONTAINID", rs.getInt("PQE_CONTAINID"));
        parseObj.put("PQE_NOMBRE", rs.getString("PQE_NOMBRE") != null ? rs.getString("PQE_NOMBRE") : "");
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("PQE_CONTAINID", getPQE_CONTAINID());
        obj.put("PQE_NOMBRE", getPQE_NOMBRE());

        return obj;
    }

    public int getPQE_CONTAINID() {
        return PQE_CONTAINID;
    }

    public void setPQE_CONTAINID(int PQE_CONTAINID) {
        this.PQE_CONTAINID = PQE_CONTAINID;
    }

    public String getPQE_NOMBRE() {
        return PQE_NOMBRE;
    }

    public void setPQE_NOMBRE(String PQE_NOMBRE) {
        this.PQE_NOMBRE = PQE_NOMBRE;
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
