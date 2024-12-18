package datos;

import java.sql.*;

public class ConexionSQL {

    static String bd = "diccionario";
    static String login = "root";
    static String password = "";
    static String url = "jdbc:mysql://localhost/" + bd;
    Connection con = null;
    Statement stm;
    ResultSet rs;

    public void conectar() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, login, password);
        this.stm = con.createStatement();
    }

    public void desconectar() throws Exception {
        this.con.close();
        this.stm.close();
    }

    public ResultSet mostrar(String sql) {
        try {
            conectar();
            rs = stm.executeQuery(sql);
        } catch (Exception e) {
            System.err.println("Hubo un error en mostrar");
        }
        return rs;
    }
    
    public void ejecutar(String sql) {
        try {
            conectar();
            stm.execute(sql);
            desconectar();
        } catch (Exception e) {
        }
    }
}
