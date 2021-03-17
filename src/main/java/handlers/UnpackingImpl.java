package handlers;

import main.Printer;
import main.Unpacking;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service("unpacking")
@Scope("prototype")
class UnpackingImpl implements Unpacking, ApplicationContextAware {

    private final Stack<Character> charStack;
    private static final char START_DEFINE = '[';
    private static final char END_DEFINE = ']';
    private ApplicationContext context;
    private Printer printer;

    UnpackingImpl() {
        this.charStack = new Stack<>();
    }

    @Autowired
    public void setPrinter(Printer printer){
        this.printer = printer;
    }

    @Override
    public String unpack(String packedString) {
        char[] charArray = packedString.toCharArray();
        for (char ch : charArray) {
            fillStack(ch);
        }
        StringBuilder builderUnpackString = context.getBean("stringBuilder", StringBuilder.class);
        charStack.forEach(builderUnpackString::append);
        return builderUnpackString.toString();
    }

    private void fillStack(char ch) {
        switch (ch){
            case END_DEFINE:
                defineUnpackingContent();
                break;
            default:
                charStack.push(ch);
        }
    }

    private void defineUnpackingContent() {
        StringBuilder contentBuilder = context.getBean("stringBuilder", StringBuilder.class);
        while (!charStack.empty()){
            char pop = charStack.pop();
            if(pop == START_DEFINE){
                break;
            }else {
                contentBuilder.append(pop);
            }
        }
        String reverse = contentBuilder.reverse().toString();
        unpackContent(reverse);
    }

    private void unpackContent(String temp) {
        try {
            int size = defineSizeUnpackedContent();
            StringBuilder unpackBuilder = context.getBean("stringBuilder", StringBuilder.class);
            while (size-->0){
                unpackBuilder.append(temp);
            }
            unpackedReturnToStack(unpackBuilder.toString());
        } catch (NoSuchSizePackingException e) {
            printer.printError("Error: Unpacking factor for: ["+temp+"] not specified & will be unpack without this.");
        }
    }

    private int defineSizeUnpackedContent() throws NoSuchSizePackingException {
        StringBuilder numberBuilder = context.getBean("stringBuilder", StringBuilder.class);
        while (!charStack.empty()){
            char peek = charStack.peek();
            if(Character.isDigit(peek)){
                char pop = charStack.pop();
                numberBuilder.append(pop);
            }else {
                break;
            }
        }
        return numberParser(numberBuilder);
    }

    private int numberParser(StringBuilder numberBuilder) throws NoSuchSizePackingException {
        if(numberBuilder.length() == 0){
            throw new NoSuchSizePackingException();
        }
        if(numberBuilder.length() > 1) {
            String number = numberBuilder.reverse().toString();
            return Integer.parseInt(number);
        }
        return Integer.parseInt(numberBuilder.toString());
    }

    private void unpackedReturnToStack(String unpack){
        for (char ch: unpack.toCharArray()){
            charStack.push(ch);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }
}
