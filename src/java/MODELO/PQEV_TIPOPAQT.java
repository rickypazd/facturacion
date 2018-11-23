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

public class PQEV_TIPOPAQT {

    private int PQE_ID;
    private String PQE_DESC;
    private int PQE_IDTIPOCONTAIN;
    private int PQE_IDCATEGORIAIN;

    private Conexion con = null;

    private String TBL = "PQEV_TIPOPAQT";

    public PQEV_TIPOPAQT(Conexion con) {
        this.con = con;
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
        parseObj.put("PQE_ID", rs.getInt("PQE_ID"));
        parseObj.put("PQE_DESC", rs.getString("PQE_DESC") != null ? rs.getString("PQE_DESC") : "");
        parseObj.put("PQE_IDTIPOCONTAIN", rs.getInt("PQE_IDTIPOCONTAIN"));
        parseObj.put("PQE_IDCATEGORIAIN", rs.getInt("PQE_IDCATEGORIAIN"));
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("PQE_ID", getPQE_ID());
        obj.put("PQE_DESC", getPQE_DESC());
        obj.put("PQE_IDTIPOCONTAIN", getPQE_IDTIPOCONTAIN());
        obj.put("PQE_IDCATEGORIAIN", getPQE_IDCATEGORIAIN());

        return obj;
    }

    public int getPQE_ID() {
        return PQE_ID;
    }

    public void setPQE_ID(int PQE_ID) {
        this.PQE_ID = PQE_ID;
    }

    public String getPQE_DESC() {
        return PQE_DESC;
    }

    public void setPQE_DESC(String PQE_DESC) {
        this.PQE_DESC = PQE_DESC;
    }

    public int getPQE_IDTIPOCONTAIN() {
        return PQE_IDTIPOCONTAIN;
    }

    public void setPQE_IDTIPOCONTAIN(int PQE_IDTIPOCONTAIN) {
        this.PQE_IDTIPOCONTAIN = PQE_IDTIPOCONTAIN;
    }

    public int getPQE_IDCATEGORIAIN() {
        return PQE_IDCATEGORIAIN;
    }

    public void setPQE_IDCATEGORIAIN(int PQE_IDCATEGORIAIN) {
        this.PQE_IDCATEGORIAIN = PQE_IDCATEGORIAIN;
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
