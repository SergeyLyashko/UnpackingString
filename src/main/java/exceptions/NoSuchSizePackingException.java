package exceptions;

public class NoSuchSizePackingException extends Exception {

    public NoSuchSizePackingException(){
        super("Unpacking factor not specified.");
    }

}
