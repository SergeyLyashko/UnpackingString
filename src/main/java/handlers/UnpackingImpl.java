package handlers;

import main.Unpacking;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service("unpacking")
@Scope("prototype")
class UnpackingImpl implements Unpacking {

    private final Stack<Character> operatorStack;
    //private StringBuilder stringBuilder = new StringBuilder();

    UnpackingImpl() {
        this.operatorStack = new Stack<>();
    }

    @Override
    public String unpack(String packedString) {
        char[] charArray = packedString.toCharArray();
        for (char ch : charArray) {
            checkSymbol(ch);
        }
        StringBuilder builderUnpackString = new StringBuilder();
        operatorStack.forEach(builderUnpackString::append);
        return builderUnpackString.toString();
    }

    private void checkSymbol(char ch) {
        switch (ch){
            case '[':
                operatorStack.push(ch);
                break;
            case ']':
                unpack();
                break;
            default:
                checkOtherChar(ch);
        }
    }

    private void checkOtherChar(char ch) {
        operatorStack.push(ch);
        /*
        if(Character.isDigit(ch)){
            addOperator(ch);
        }else {
            tempStack.push(ch);
        }*/
    }

    private void unpack() {
        String temp = "";
        while (!operatorStack.empty()){
            char pop = operatorStack.pop();
            if(pop == '['){
                break;
            }else {
                temp += pop;
            }
        }
        String reverse = new StringBuilder(temp).reverse().toString();
       unpackBracketsContent(reverse);
    }

    private void unpackBracketsContent(String temp) {
        String unpack = "";
        Character pop = operatorStack.pop();
        if(Character.isDigit(pop)){
            int count = Character.getNumericValue(pop);
            while (count-->0){
                unpack += temp;
            }
        }
        for (char ch: unpack.toCharArray()){
            operatorStack.push(ch);
        }
    }
}
