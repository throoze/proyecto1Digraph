import java.io.IOException;
/**
 *
 * @author victor
 */
public class ExcepcionArcoRepetido extends IOException {

    /**
     * Creates a new instance of <code>ExcepcionArcoRepetido</code> without detail message.
     */
    public ExcepcionArcoRepetido() {
        super();
    }


    /**
     * Constructs an instance of <code>ExcepcionArcoRepetido</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ExcepcionArcoRepetido(String msg) {
        super(msg);
    }

    public ExcepcionArcoRepetido(String msg, Throwable cause) {
        super(msg,cause);
    }
}
