package exceptions;

public class NoOpenStringPackException extends Exception {

    public NoOpenStringPackException(){
        super("Package of string wasn't open.");
    }
}
