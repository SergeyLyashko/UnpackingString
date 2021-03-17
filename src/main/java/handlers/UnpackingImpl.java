package handlers;

import exceptions.ContentDigitPackingException;
import exceptions.NoCloseStringPackException;
import exceptions.NoOpenStringPackException;
import exceptions.NoSuchSizePackingException;
import main.Unpacking;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service("unpacking")
@Scope("prototype")
class UnpackingImpl implements Unpacking, ApplicationContextAware {

    private static final char START_DEFINE = '[';
    private static final char END_DEFINE = ']';
    private ApplicationContext context;

    @Override
    public String unpack(String packedString) throws NoCloseStringPackException, NoOpenStringPackException, NoSuchSizePackingException,
            ContentDigitPackingException {
        Stack<Character> stringContentStack = createElementsPackStack(packedString);
        return buildUnpackedString(stringContentStack);
    }

    private Stack<Character> createElementsPackStack(String packedString) throws NoOpenStringPackException, NoSuchSizePackingException {
        Stack<Character> charStack = null;
        char[] charArray = packedString.toCharArray();
        if(charArray.length != 0){
            charStack = new Stack<>();
            for (char ch : charArray) {
                fillStack(ch, charStack);
            }
        }
        return charStack;
    }

    private void fillStack(char ch, Stack<Character> charStack) throws NoOpenStringPackException, NoSuchSizePackingException {
        switch (ch){
            case END_DEFINE:
                defineUnpackContent(charStack);
                break;
            default:
                charStack.push(ch);
        }
    }

    private void defineUnpackContent(Stack<Character> charStack) throws NoOpenStringPackException, NoSuchSizePackingException {
        StringBuilder contentBuilder = context.getBean("stringBuilder", StringBuilder.class);
        while (!charStack.empty()){
            char pop = charStack.pop();
            if(pop == START_DEFINE && charStack.empty()){
                charStack.push('0');
                break;
            }else if(pop == START_DEFINE){
                break;
            } else {
                contentBuilder.append(pop);
            }
        }
        buildContent(contentBuilder, charStack);
    }

    private void buildContent(StringBuilder contentBuilder, Stack<Character> charStack) throws NoOpenStringPackException, NoSuchSizePackingException {
        if(charStack.empty()){
            throw new NoOpenStringPackException();
        }
        String reverse = contentBuilder.reverse().toString();
        unpackContent(reverse, charStack);
    }

    private void unpackContent(String temp, Stack<Character> charStack) throws NoSuchSizePackingException {
        int size = defineSizeUnpackedContent(charStack);
        if(size == 0){
            throw new NoSuchSizePackingException();
        }
        StringBuilder unpackBuilder = context.getBean("stringBuilder", StringBuilder.class);
        while (size-->0){
            unpackBuilder.append(temp);
        }
        unpackedContentReturnToStack(unpackBuilder.toString(), charStack);
    }

    private int defineSizeUnpackedContent(Stack<Character> charStack) throws NoSuchSizePackingException {
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

    private void unpackedContentReturnToStack(String unpack, Stack<Character> charStack){
        for (char ch: unpack.toCharArray()){
            charStack.push(ch);
        }
    }

    private int sizeParser(StringBuilder unpackSizeBuilder) throws NoSuchSizePackingException {
        if(unpackSizeBuilder.length() == 0){
            throw new NoSuchSizePackingException();
        }
        if(unpackSizeBuilder.length() == 1){
            return Integer.parseInt(unpackSizeBuilder.toString());
        }
        if(unpackSizeBuilder.length() > 1) {
            String number = unpackSizeBuilder.reverse().toString();
            return Integer.parseInt(number);
        }
        return 0;
    }

    private String buildUnpackedString(Stack<Character> stringContentStack) throws NoCloseStringPackException, ContentDigitPackingException {
        StringBuilder builderUnpackString = context.getBean("stringBuilder", StringBuilder.class);
        for (Character element : stringContentStack) {
            if (element == START_DEFINE) {
                throw new NoCloseStringPackException();
            }
            if(Character.isDigit(element)){
                throw new ContentDigitPackingException();
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
