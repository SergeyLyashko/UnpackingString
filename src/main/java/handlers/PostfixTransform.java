package handlers;

import org.springframework.stereotype.Service;

import java.util.Stack;

@Service("postfix")
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
        while (!operatorStack.empty()){
            out += operatorStack.pop();
        }
        // TODO
        System.out.println(out);
    }

    private void checkSymbol(char ch) {
        switch (ch){
            case '[':
                operatorStack.push(ch);
                break;
            case ']':
                extractOperators(ch);
                break;
            default:
                checkOtherChar(ch);
        }
    }

    private void checkOtherChar(char ch) {
        if(Character.isDigit(ch)){
            addOperator(ch);
        }else {
            out += ch;
        }
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

    private void extractOperators(char ch) {
        while (!operatorStack.empty()){
            char pop = operatorStack.pop();
            if(pop == '['){
                unpack();
                //char top = operatorStack.pop();
                //out+= top;
                break;
            }else {
                out += pop;
            }
        }
    }

    private void unpack() {
        char top = operatorStack.pop();
        out+= top;
    }
}
