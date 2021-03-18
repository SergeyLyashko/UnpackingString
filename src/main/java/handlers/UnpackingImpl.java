package handlers;

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

    private static final char OPEN_PACK = '[';
    private static final char CLOSE_PACK = ']';
    private ApplicationContext context;

    @Override
    public String unpack(String packedString) throws NoCloseStringPackException, NoOpenStringPackException, NoSuchSizePackingException {
        char[] packedStringAsCharsArray = packedString.toCharArray();
        Stack<Character> unpackedCharsStack = createUnpackedCharsStack(packedStringAsCharsArray);
        return buildUnpackedString(unpackedCharsStack);
    }

    private Stack<Character> createUnpackedCharsStack(char[] charArray) throws NoOpenStringPackException, NoSuchSizePackingException {
        Stack<Character> charsStack = new Stack<>();
        for (char ch : charArray) {
            if (ch == CLOSE_PACK) {
                unpackContentToStack(charsStack);
            } else {
                charsStack.push(ch);
            }
        }
        return charsStack;
    }

    private void unpackContentToStack(Stack<Character> charStack) throws NoOpenStringPackException, NoSuchSizePackingException {
        StringBuilder packedContent = buildPackedContent(charStack);
        if(charStack.empty()){
            throw new NoOpenStringPackException();
        }
        String reverse = packedContent.reverse().toString();
        String unpackSizeFactor = buildUnpackSizeFactor(charStack);
        int size = Integer.parseInt(unpackSizeFactor);
        String unpackContent = unpackContent(reverse, size);
        unpackedContentReturnToStack(unpackContent, charStack);
    }

    // TODO charArray ???
    private StringBuilder buildPackedContent(Stack<Character> charStack) throws NoSuchSizePackingException {
        StringBuilder contentBuilder = context.getBean("stringBuilder", StringBuilder.class);
        while (!charStack.empty()){
            char pop = charStack.pop();
            if(pop == OPEN_PACK && charStack.empty()){
                throw new NoSuchSizePackingException();
            }else if(pop == OPEN_PACK){
                break;
            } else {
                contentBuilder.append(pop);
            }
        }
        return contentBuilder;
    }

    private String unpackContent(String content, int size) throws NoSuchSizePackingException {
        if(size == 0){
            throw new NoSuchSizePackingException();
        }
        StringBuilder unpackBuilder = context.getBean("stringBuilder", StringBuilder.class);
        while (size-->0){
            unpackBuilder.append(content);
        }
        return unpackBuilder.toString();
    }

    private String buildUnpackSizeFactor(Stack<Character> charStack) throws NoSuchSizePackingException {
        StringBuilder sizeBuilder = context.getBean("stringBuilder", StringBuilder.class);
        while (!charStack.empty()){
            char peek = charStack.peek();
            if(!Character.isDigit(peek)){
                break;
            }
            char pop = charStack.pop();
            sizeBuilder.append(pop);
        }
        if(sizeBuilder.length() == 0){
            throw new NoSuchSizePackingException();
        }
        if(sizeBuilder.length() > 1) {
            return sizeBuilder.reverse().toString();
        }
        return sizeBuilder.toString();
    }

    private void unpackedContentReturnToStack(String unpack, Stack<Character> charStack){
        for (char ch: unpack.toCharArray()){
            charStack.push(ch);
        }
    }

    private String buildUnpackedString(Stack<Character> stringContentStack) throws NoCloseStringPackException {
        StringBuilder builderUnpackString = context.getBean("stringBuilder", StringBuilder.class);
        for (Character element : stringContentStack) {
            if (element == OPEN_PACK) {
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
