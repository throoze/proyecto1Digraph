public class ExcepcionArchivoNoSePuedeLeer extends Exception{

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