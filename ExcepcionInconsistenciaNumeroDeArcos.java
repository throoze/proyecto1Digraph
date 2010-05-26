import java.io.IOException;

/**
 *
 * @author victor
 */
public class ExcepcionInconsistenciaNumeroDeArcos extends IOException {

    /**
     * Creates a new instance of <code>ExcepcionInconsistenciaNumeroDeArcos</code> without detail message.
     */
    public ExcepcionInconsistenciaNumeroDeArcos() {
        super();
    }

    /**
     * Constructs an instance of <code>ExcepcionInconsistenciaNumeroDeArcos</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ExcepcionInconsistenciaNumeroDeArcos(String msg) {
        super(msg);
    }

    public ExcepcionInconsistenciaNumeroDeArcos(String msg, Throwable cause) {
        super(msg,cause);
    }
}
