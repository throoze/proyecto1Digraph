import java.io.IOException;
public class ExcepcionArchivoNoSePuedeLeer extends IOException{

    public ExcepcionArchivoNoSePuedeLeer(){
        super();
    }

    public ExcepcionArchivoNoSePuedeLeer(String message){
        super(message);
    }

    public ExcepcionArchivoNoSePuedeLeer(String message, Throwable cause){
        super(message,cause);
    }
}