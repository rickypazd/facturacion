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

public class CLTE_PAIS {

    private int PAIS_ID;
    private String PAIS_DESCRPCION;
    private String PAIS_ZONAHORARIA;

    private Conexion con = null;

    private String TBL = "CLTE_PAIS";

    public CLTE_PAIS(Conexion con) {
        this.con = con;
    }

    public long Insertar() throws SQLException {
        String consulta = "INSERT INTO " + TBL + "(\n"
                + "	PAIS_DESCRPCION, PAIS_ZONAHORARIA)\n"
                + "	VALUES (?, ?);";
        PreparedStatement ps = con.statamet(consulta, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, getPAIS_DESCRPCION());
        ps.setString(2, getPAIS_ZONAHORARIA());
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
        parseObj.put("PAIS_ID", rs.getInt("PAIS_ID"));
        parseObj.put("PAIS_DESCRPCION", rs.getString("PAIS_DESCRPCION") != null ? rs.getString("PAIS_DESCRPCION") : "");
        parseObj.put("PAIS_ZONAHORARIA", rs.getString("PAIS_ZONAHORARIA") != null ? rs.getString("PAIS_ZONAHORARIA") : "");
        return parseObj;
    }

    public JSONObject getJson() throws JSONException, SQLException {
        JSONObject obj = new JSONObject();
        obj.put("PAIS_ID", getPAIS_ID());
        obj.put("PAIS_DESCRPCION", getPAIS_DESCRPCION());
        obj.put("PAIS_ZONAHORARIA", getPAIS_ZONAHORARIA());

        return obj;
    }

    public int getPAIS_ID() {
        return PAIS_ID;
    }

    public void setPAIS_ID(int PAIS_ID) {
        this.PAIS_ID = PAIS_ID;
    }

    public String getPAIS_DESCRPCION() {
        return PAIS_DESCRPCION;
    }

    public void setPAIS_DESCRPCION(String PAIS_DESCRPCION) {
        this.PAIS_DESCRPCION = PAIS_DESCRPCION;
    }

    public String getPAIS_ZONAHORARIA() {
        return PAIS_ZONAHORARIA;
    }

    public void setPAIS_ZONAHORARIA(String PAIS_ZONAHORARIA) {
        this.PAIS_ZONAHORARIA = PAIS_ZONAHORARIA;
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
