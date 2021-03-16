package handlers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service("postfix")
@Scope("prototype")
public class PostfixTransform {

    private final Stack<Character> operatorStack;
    //private StringBuilder stringBuilder = new StringBuilder();
    // TODO
    private String out = "";

    public PostfixTransform() {
        this.operatorStack = new Stack<>();
    }

    public void transform(String input) {
        char[] charArray = input.toCharArray();
        for (char ch : charArray) {
            checkSymbol(ch);
        }
        /*while (!operatorStack.empty()){
            Character pop = operatorStack.pop();
            out += pop;
        }
        // TODO
        System.out.println(out);*/
        operatorStack.forEach(System.out::print);
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

    private void addOperator(char ch) {
        while (!operatorStack.empty()){
            char pop = operatorStack.pop();
            if(pop == '['){
                operatorStack.push(pop);
                break;
            }else {
                out += pop;
            }
        }
        operatorStack.push(ch);
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
        System.out.println("reverse: "+reverse);
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
