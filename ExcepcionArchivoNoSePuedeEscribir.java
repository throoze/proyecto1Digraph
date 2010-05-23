public class ExcepcionArchivoNoSePuedeEscribir extends Exception{

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