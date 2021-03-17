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
        try {
            return buildUnpackedString();
        } catch (NoCloseStringPackException e) {
            printer.printError("Error: package string not closed.");
        }
        return null;
    }

    private void fillStack(char ch) {
        switch (ch){
            case END_DEFINE:
                defineUnpackContent();
                break;
            default:
                charStack.push(ch);
        }
    }

    private void defineUnpackContent() {
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
            unpackedContentReturnToStack(unpackBuilder.toString());
        } catch (NoSuchSizePackingException e) {
            printer.printError("Error: Unpacking factor for: ["+temp+"] not specified & will be unpack without this.");
        }
    }

    private int defineSizeUnpackedContent() throws NoSuchSizePackingException {
        StringBuilder unpackSizeBuilder = context.getBean("stringBuilder", StringBuilder.class);
        while (!charStack.empty()){
            char peek = charStack.peek();
            if(Character.isDigit(peek)){
                char pop = charStack.pop();
                unpackSizeBuilder.append(pop);
            }else {
                break;
            }
        }
        return sizeParser(unpackSizeBuilder);
    }

    private void unpackedContentReturnToStack(String unpack){
        for (char ch: unpack.toCharArray()){
            charStack.push(ch);
        }
    }

    private int sizeParser(StringBuilder unpackSizeBuilder) throws NoSuchSizePackingException {
        if(unpackSizeBuilder.length() == 0){
            throw new NoSuchSizePackingException();
        }
        if(unpackSizeBuilder.length() > 1) {
            String number = unpackSizeBuilder.reverse().toString();
            return Integer.parseInt(number);
        }
        return Integer.parseInt(unpackSizeBuilder.toString());
    }

    private String buildUnpackedString() throws NoCloseStringPackException {
        StringBuilder builderUnpackString = context.getBean("stringBuilder", StringBuilder.class);
        for (Character element : charStack) {
            if (element == START_DEFINE) {
                throw new NoCloseStringPackException();
            }
            builderUnpackString.append(element);
        }
        return builderUnpackString.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }
}
