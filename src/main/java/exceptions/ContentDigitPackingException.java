package exceptions;

public class ContentDigitPackingException extends Exception {

    public ContentDigitPackingException(){
        super("String for unpacking contain digit.");
    }
}
