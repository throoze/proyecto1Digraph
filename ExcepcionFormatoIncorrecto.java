import java.io.IOException;
/**
 *
 * @author victor
 */
public class ExcepcionFormatoIncorrecto extends IOException {

    /**
     * Creates a new instance of <code>ExcepcionFormatoIncorrecto</code> without detail message.
     */
    public ExcepcionFormatoIncorrecto() {
        super();
    }

    /**
     * Constructs an instance of <code>ExcepcionFormatoIncorrecto</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ExcepcionFormatoIncorrecto(String message) {
        super(message);
    }

    public ExcepcionFormatoIncorrecto(String message, Throwable cause) {
        super(message,cause);
    }
}
