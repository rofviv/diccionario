package negocio;

import datos.ConsultaSQL;
import java.sql.ResultSet;

public class NPalabra {
    ConsultaSQL consult = new ConsultaSQL();
    
    public ResultSet mostrar_palabra() {
        return consult.mostrar_palabra();
    }
    
    public ResultSet obtenerID_palabra(String palabra) {
        consult.setNombre(palabra);
        return consult.obtenerId_Palabra();
    }
    
    public void editarPalabra(int id_p, String p) {
        consult.setId_palabra(id_p);
        consult.setNombre(p);
        consult.editar_palabra();
    }
    
    public ResultSet buscarSinonimosID(int id) {
        consult.setId_palabra(id);
        return consult.sinonimoID();
    }
    
    public ResultSet buscarSinonimosNombre(String nombre) {
        consult.setNombre(nombre);
        return consult.sinonimoNombre();
    }
    
    public ResultSet buscarFamiliaNombre(String nombre) {
        consult.setNombre(nombre);
        return consult.familiaNombre();
    }
    
    public ResultSet buscarParonimoNombre(String nombre) {
        consult.setNombre(nombre);
        return consult.paronimoNombre();
    }
    
    public ResultSet significadoClasificacion(int id) {
        consult.setId_palabra(id);
        return consult.significadoClasificacion();
    }
    
    public ResultSet buscarPalabra(String nombre) {
        consult.setNombre(nombre);
        return consult.buscarPalabra();
    }
    
    public ResultSet buscarPorIndice(char indice) {
        consult.setIndice(indice);
        return consult.buscarIndice();
    }
    
    public void insertarPalabra(String palabra) {
        consult.setNombre(palabra);
        consult.insertar_palabra();
    }
    
    public void insertarSinonimo(int id_p, int id_s) {
        consult.setId_palabra(id_p);
        consult.guardarSinonimo(id_s);
    }
    
    public void insertarFamilia(int id_p, int id_f) {
        consult.setId_palabra(id_p);
        consult.guardarFamilia(id_f);
    } 
    
    public void insertarParonimo(int id_p, int id_par) {
        consult.setId_palabra(id_p);
        consult.guardarParonimo(id_par);
    }
    
    public void insertarAntonimo(int id_p, int id_a) {
        consult.setId_palabra(id_p);
        consult.guardarAntonimo(id_a);
    }
    
    public ResultSet autenticarUsuario(String login, String password) {
         return consult.autenticarUsuario(login, password);
    }
    
    public void insertarSignificado(int id_p, int id_t, String significado) {
        consult.insertar_significado(id_p, id_t, significado);
    }
    
    public void eliminarSignificado(String nombre) {
        consult.setNombre(nombre);
        consult.eliminar_significado();
    }
    
    public void eliminarPalabra(String nombre) {
        consult.setNombre(nombre);
        consult.eliminar_palabra();
    }
    
    public void eliminarAntonimo(int id_a) {
        consult.setId_palabra(id_a);
        consult.eliminar_antonimo();
    }
    
    public void eliminarSinonimo(int id_s) {
        consult.setId_palabra(id_s);
        consult.eliminar_sinonimo();
    }
    
    public void eliminarParonimo(int id_p) {
        consult.setId_palabra(id_p);
        consult.eliminar_paronimo();
    }
    
    public void eliminarFamilia(int id_f) {
        consult.setId_palabra(id_f);
        consult.eliminar_familia();
    }
    
    public ResultSet mostrarTipo() {
        return consult.mostrar_tipos();
    }
}
