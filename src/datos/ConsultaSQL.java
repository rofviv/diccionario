package datos;

import java.sql.ResultSet;

public class ConsultaSQL {

    ConexionSQL cn = new ConexionSQL();
    private int id_palabra;
    private String nombre;
    private char indice;
    private ResultSet rs;

    public void setId_palabra(int id_palabra) {
        this.id_palabra = id_palabra;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIndice(char indice) {
        this.indice = indice;
    }

    public ResultSet autenticarUsuario(String login, String password) {
        String sql = "SELECT count(id_usuario) FROM usuario "
                + "WHERE login = '" + login + "' "
                + "AND password = '" + password + "'";
        return cn.mostrar(sql);
    }
    
    public void editar_palabra() {
        String sql = "UPDATE palabra SET nombre = '" + this.nombre + "' WHERE "
                + "id_palabra = " + this.id_palabra + ";";
        cn.ejecutar(sql);
    }

    public void insertar_palabra() {
        String sql = "INSERT INTO palabra VALUES (null, '" + this.nombre + "')";
        cn.ejecutar(sql);
    }

    public ResultSet mostrar_tipos() {
        String sql = "SELECT tipo FROM clasificacion";
        return cn.mostrar(sql);
    }

    public ResultSet obtenerId_Palabra() {
        String sql = "SELECT id_palabra FROM palabra "
                + "WHERE nombre = '" + this.nombre + "'";
        return cn.mostrar(sql);
    }

    public void insertar_significado(int id_p, int id_t, String significado) {
        String sql = "INSERT INTO significado VALUES "
                + "(null, " + id_p + ", " + id_t + ", "
                + "'" + significado + "')";
        cn.ejecutar(sql);
    }

    public void eliminar_significado() {
        String sql = "DELETE FROM significado "
                + "WHERE id_palabra IN(SELECT id_palabra FROM palabra "
                + "WHERE nombre = '" + this.nombre + "')";
        cn.ejecutar(sql);
    }

    public void eliminar_palabra() {
        String sql = "DELETE FROM palabra "
                + "WHERE nombre = '" + this.nombre + "'";
        cn.ejecutar(sql);
    }

    public void eliminar_antonimo() {
        String sql = "DELETE FROM antonimo "
                + "WHERE id_palabra = " + this.id_palabra;
        cn.ejecutar(sql);
    }

    public void eliminar_sinonimo() {
        String sql = "DELETE FROM sinonimo "
                + "WHERE id_palabra = " + this.id_palabra;
        cn.ejecutar(sql);
    }

    public void eliminar_paronimo() {
        String sql = "DELETE FROM paronimo "
                + "WHERE id_palabra = " + this.id_palabra;
        cn.ejecutar(sql);
    }

    public void eliminar_familia() {
        String sql = "DELETE FROM familia "
                + "WHERE id_palabra = " + this.id_palabra;
        cn.ejecutar(sql);
    }

    public ResultSet mostrar_palabra() {
        String sql = "SELECT id_palabra, nombre FROM palabra";
        return cn.mostrar(sql);
    }

    public ResultSet sinonimoID() {
        String sql = "SELECT nombre FROM palabra p, sinonimo s "
                + "WHERE s.id_palabra = p.id_palabra AND s.id_sinonimo = "
                + this.id_palabra + " ORDER by p.nombre";
        return cn.mostrar(sql);
    }

    public ResultSet sinonimoNombre() {
        rs = cn.mostrar("SELECT id_palabra FROM palabra WHERE nombre = '" + this.nombre + "'");
        try {
            rs.next();
            int id = Integer.parseInt(rs.getString(1));
            setId_palabra(id);
        } catch (Exception e) {
            System.out.println("Error en la consulta de sinonimos");
        }
        return sinonimoID();
    }

    public ResultSet familiaID() {
        String sql = "SELECT nombre FROM palabra p, familia f "
                + "WHERE f.id_palabra = p.id_palabra AND f.id_familia = "
                + this.id_palabra + " ORDER by p.nombre";
        return cn.mostrar(sql);
    }

    public ResultSet familiaNombre() {
        rs = cn.mostrar("SELECT id_palabra FROM palabra WHERE nombre = '" + this.nombre + "'");
        try {
            rs.next();
            int id = Integer.parseInt(rs.getString(1));
            setId_palabra(id);
        } catch (Exception e) {
            System.out.println("Error en la consulta de familia");
        }
        return familiaID();
    }

    public ResultSet paronimoID() {
        String sql = "SELECT nombre FROM palabra p, paronimo par "
                + "WHERE par.id_palabra = p.id_palabra AND par.id_paronimo = "
                + this.id_palabra + " ORDER by p.nombre";
        return cn.mostrar(sql);
    }

    public ResultSet paronimoNombre() {
        rs = cn.mostrar("SELECT id_palabra FROM palabra WHERE nombre = '" + this.nombre + "'");
        try {
            rs.next();
            int id = Integer.parseInt(rs.getString(1));
            setId_palabra(id);
        } catch (Exception e) {
            System.err.println("Error en la consulta de paronimos");
        }
        return paronimoID();
    }

    public ResultSet significadoClasificacion() {
        String sql = "SELECT s.significado, c.tipo "
                + "FROM palabra p, significado s, clasificacion c "
                + "WHERE p.id_palabra = s.id_palabra "
                + "AND s.id_clasificacion = c.id_clasificacion "
                + "AND p.id_palabra = " + this.id_palabra + " ORDER by p.nombre";
        return cn.mostrar(sql);
    }

    public ResultSet buscarPalabra() {
        String sql = "SELECT p.nombre, s.significado, c.tipo "
                + "from palabra p, significado s, clasificacion c "
                + "where p.id_palabra = s.id_palabra "
                + "and s.id_clasificacion = c.id_clasificacion "
                + "and p.nombre like '" + this.nombre + "' order by p.nombre";
        return cn.mostrar(sql);
    }

    public void guardarSinonimo(int id_sinonimo) {
        String sql = "INSERT INTO sinonimo (id_palabra, id_sinonimo) "
                + "VALUES (" + this.id_palabra + ", " + id_sinonimo + ");";
        cn.ejecutar(sql);
        sql = "INSERT INTO sinonimo (id_palabra, id_sinonimo) "
                + "VALUES (" + id_sinonimo + ", " + this.id_palabra + ");";
        cn.ejecutar(sql);
    }

    public void guardarFamilia(int id_familia) {
        String sql = "INSERT INTO familia (id_palabra, id_familia) "
                + "VALUES (" + this.id_palabra + ", " + id_familia + ");";
        cn.ejecutar(sql);
        sql = "INSERT INTO familia (id_palabra, id_familia) "
                + "VALUES (" + id_familia + ", " + this.id_palabra + ");";
        cn.ejecutar(sql);
    }

    public void guardarParonimo(int id_paronimo) {
        String sql = "INSERT INTO paronimo (id_palabra, id_paronimo) "
                + "VALUES (" + this.id_palabra + ", " + id_paronimo + ");";
        cn.ejecutar(sql);
        sql = "INSERT INTO paronimo (id_palabra, id_paronimo) "
                + "VALUES (" + id_paronimo + ", " + this.id_palabra + ");";
        cn.ejecutar(sql);
    }

    public void guardarAntonimo(int id_antonimo) {
        String sql = "INSERT INTO antonimo (id_palabra, id_antonimo) "
                + "VALUES (" + this.id_palabra + ", " + id_antonimo + ");";
        cn.ejecutar(sql);
        sql = "INSERT INTO antonimo (id_palabra, id_antonimo) "
                + "VALUES (" + id_antonimo + ", " + this.id_palabra + ");";
        cn.ejecutar(sql);
    }

    public ResultSet buscarIndice() {
        String sql = "select nombre from palabra "
                + "where nombre like '" + this.indice + "%' order by nombre ASC";
        return cn.mostrar(sql);
    }
}
