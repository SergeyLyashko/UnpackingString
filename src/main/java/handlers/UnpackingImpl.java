package handlers;

import exceptions.NoCloseStringPackException;
import exceptions.NoOpenStringPackException;
import exceptions.NoSuchSizePackingException;
import main.Unpacking;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("unpacking")
@Scope("prototype")
class UnpackingImpl implements Unpacking {

    private static final char OPEN_PACK = '[';
    private static final char CLOSE_PACK = ']';

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
        List<Character> contentFromStack = buildContentFromStack(charStack);
        String unpackSizeFactor = buildUnpackSizeFactor(charStack);
        int size = Integer.parseInt(unpackSizeFactor);
        List<Character> characters = unpackContent(contentFromStack, size);
        characters.forEach(charStack::push);
    }

    private List<Character> buildContentFromStack(Stack<Character> charStack) throws NoSuchSizePackingException, NoOpenStringPackException {
        List<Character> reversedContent = new ArrayList<>();
        while (!charStack.empty()){
            char pop = charStack.pop();
            if(pop == OPEN_PACK && charStack.empty()){
                throw new NoSuchSizePackingException();
            }else if(pop == OPEN_PACK){
                break;
            } else {
                reversedContent.add(pop);
            }
        }
        if(charStack.empty()){
            throw new NoOpenStringPackException();
        }
        Collections.reverse(reversedContent);
        return reversedContent;
    }

    private String buildUnpackSizeFactor(Stack<Character> charStack) throws NoSuchSizePackingException {
        StringBuilder sizeBuilder = new StringBuilder();
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

    private List<Character> unpackContent(List<Character> content, int size) throws NoSuchSizePackingException {
        if(size == 0){
            throw new NoSuchSizePackingException();
        }
        List<Character> all = new ArrayList<>();
        while (size-->0){
            all.addAll(content);
        }
        return all;
    }

    private String buildUnpackedString(Stack<Character> stringContentStack) throws NoCloseStringPackException {
        StringBuilder builderUnpackString = new StringBuilder();
        for (Character element : stringContentStack) {
            if (element == OPEN_PACK) {
                throw new NoCloseStringPackException();
            }
            builderUnpackString.append(element);
        }
        return builderUnpackString.toString();
    }
}
