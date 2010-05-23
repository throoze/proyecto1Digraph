import java.io.IOException;
public class ExcepcionArchivoNoSePuedeEscribir extends IOException {

    public ExcepcionArchivoNoSePuedeEscribir(){
        super();
    }

    public ExcepcionArchivoNoSePuedeEscribir(String message){
        super(message);
    }

    public ExcepcionArchivoNoSePuedeEscribir(String message, Throwable cause){
        super(message,cause);
    }
}