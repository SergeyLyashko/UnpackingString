package handlers;

import org.springframework.stereotype.Service;

import java.util.Stack;

@Service("checker")
public class CheckerImpl {

    private final Stack<Character> bracketStack;
    private final Stack<String> resultStack;
    private StringBuilder stringBuilder = new StringBuilder();

    public CheckerImpl() {
        bracketStack = new Stack<>();
        resultStack = new Stack<>();
    }

    public void read(String input) {
        char[] charArray = input.toCharArray();
        for(int index=charArray.length-1; index>=0; index--){
            check(charArray[index]);
        }
        // TODO
        System.out.println(stringBuilder.toString());
    }

    private void check(char ch) {
        switch (ch){
            case '[':
                bracketStack.pop();
                break;
            case ']':
                bracketStack.push(ch);
                break;
            default:
                checkOtherChar(ch);
        }
    }

    private void checkOtherChar(char ch) {
        if(Character.isDigit(ch)){
            buildCountBracketsContent(Character.getNumericValue(ch));
        }else {
            pushSymbolToStack(ch);
        }
    }

    void pushSymbolToStack(char ch){
        if(!bracketStack.empty()) {
            resultStack.push(String.valueOf(ch));
        }else {
            stringBuilder.append(ch);
        }
    }

    private void buildCountBracketsContent(int count) {
        String bracketsContent = buildBracketsContent();
        StringBuilder builder = new StringBuilder();
        while (count-->0){
            builder.append(bracketsContent);
        }
        builder.append(stringBuilder);
        stringBuilder = builder;
    }

    private String buildBracketsContent(){
        StringBuilder builder = new StringBuilder();
        while (!resultStack.empty()) {
            String pop = resultStack.pop();
            builder.append(pop);
        }
        return builder.toString();
    }

    // TODO
    private boolean checkMatches(char brace) {
        if(!bracketStack.empty()){
            char pop = bracketStack.pop();
            return (pop == '[' || brace != ']');
        }else{
            return false;
        }
    }
}
