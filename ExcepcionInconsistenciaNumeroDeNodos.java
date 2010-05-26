import java.io.IOException;

/**
 *
 * @author victor
 */
public class ExcepcionInconsistenciaNumeroDeNodos extends IOException {

    /**
     * Creates a new instance of <code>ExcepcionInconsistenciaNumeroDeNodos</code> without detail message.
     */
    public ExcepcionInconsistenciaNumeroDeNodos() {
        super();
    }


    /**
     * Constructs an instance of <code>ExcepcionInconsistenciaNumeroDeNodos</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ExcepcionInconsistenciaNumeroDeNodos(String msg) {
        super(msg);
    }

    public ExcepcionInconsistenciaNumeroDeNodos(String msg, Throwable cause) {
        super(msg,cause);
    }
}
