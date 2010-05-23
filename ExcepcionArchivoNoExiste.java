import java.io.IOException;
public class ExcepcionArchivoNoExiste extends IOException {

    public ExcepcionArchivoNoExiste(){
        super();
    }

    public ExcepcionArchivoNoExiste(String message){
        super(message);
    }

    public ExcepcionArchivoNoExiste(String message, Throwable cause){
        super(message,cause);
    }
}