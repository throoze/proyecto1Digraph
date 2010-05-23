public class ExcepcionNoEsArchivo extends Exception {

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