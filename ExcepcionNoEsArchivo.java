import java.io.IOException;
public class ExcepcionNoEsArchivo extends IOException {

    public ExcepcionNoEsArchivo(){
        super();
    }

    public ExcepcionNoEsArchivo(String message){
        super(message);
    }

    public ExcepcionNoEsArchivo(String message, Throwable cause){
        super(message,cause);
    }
}