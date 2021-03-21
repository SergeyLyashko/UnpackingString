package exceptions;

public class InvalidCharacterException extends Exception {

    public InvalidCharacterException(){
        super("Input string contains invalid characters");
    }
}
