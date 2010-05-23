public class ExcepcionArchivoNoExiste extends Exception {

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