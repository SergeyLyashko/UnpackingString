package exceptions;

public class NoCloseStringPackException extends Exception {

    public NoCloseStringPackException(){
        super("Package of string not closed.");
    }
}
