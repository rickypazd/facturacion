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

public class IMEX_CATEGORIAPAQT {

    private int IEX_ID;
    private String IEX_DESC;
    private String IEX_NOMBRE;

    private Conexion con = null;

    private String TBL = "IMEX_CATEGORIAPAQT";

    public IMEX_CATEGORIAPAQT(Conexion con) {
        this.con = con;
    }

    public long Insertar() throws SQLException {
        String consulta = "INSERT INTO " + TBL + "(\n"
                + "	IEX_DESC, IEX_NOMBRE)\n"
                + "	VALUES (?, ?);";
        PreparedStatement ps = con.statamet(consulta, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, getIEX_DESC());
        ps.setString(2, getIEX_NOMBRE());
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
        parseObj.put("IEX_ID", rs.getInt("IEX_ID"));
        parseObj.put("IEX_DESC", rs.getString("IEX_DESC") != null ? rs.getString("IEX_DESC") : "");
        parseObj.put("IEX_NOMBRE", rs.getString("IEX_NOMBRE") != null ? rs.getString("IEX_NOMBRE") : "");
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("IEX_ID", getIEX_ID());
        obj.put("IEX_DESC", getIEX_DESC());
        obj.put("IEX_NOMBRE", getIEX_NOMBRE());

        return obj;
    }

    public int getIEX_ID() {
        return IEX_ID;
    }

    public void setIEX_ID(int IEX_ID) {
        this.IEX_ID = IEX_ID;
    }

    public String getIEX_DESC() {
        return IEX_DESC;
    }

    public void setIEX_DESC(String IEX_DESC) {
        this.IEX_DESC = IEX_DESC;
    }

    public String getIEX_NOMBRE() {
        return IEX_NOMBRE;
    }

    public void setIEX_NOMBRE(String IEX_NOMBRE) {
        this.IEX_NOMBRE = IEX_NOMBRE;
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
