package clases;

import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import javax.swing.text.JTextComponent;
import java.sql.ResultSet;
import negocio.NPalabra;

public class AutoCompletar {

    AutoCompleterCallback s;
    TextAutoCompleter txtCompleter;
    ResultSet rs;
    NPalabra np;
    Object ob;

    public AutoCompletar(JTextComponent txt) {
        np = new NPalabra();
        txtCompleter = new TextAutoCompleter(txt, new AutoCompleterCallback() {
            @Override
            public void callback(Object o) {
                ob = o;
//                System.out.println("Selecciono " + palabraSeleccionada());
            }
        });
    }

    public String palabraSeleccionada() {
        return ob.toString();
    }

    public void txtReiniciar() {
        txtCompleter.removeAllItems();
    }

    public void txtPredictivo(boolean sw) {
        if (sw) {
            txtCompleter.setMode(-1);
            txtCompleter.setCaseSensitive(false);

            try {
                rs = np.mostrar_palabra();
                while (rs.next()) {
                    txtCompleter.addItem(rs.getString(2));
                }
            } catch (Exception e) {
            }
        }
    }
}
