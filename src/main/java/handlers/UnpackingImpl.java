package handlers;

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

    private final Stack<Character> operatorStack;
    private ApplicationContext context;

    UnpackingImpl() {
        this.operatorStack = new Stack<>();
    }

    @Override
    public String unpack(String packedString) {
        char[] charArray = packedString.toCharArray();
        for (char ch : charArray) {
            checkSymbol(ch);
        }
        StringBuilder builderUnpackString = context.getBean("stringBuilder", StringBuilder.class);
        operatorStack.forEach(builderUnpackString::append);
        return builderUnpackString.toString();
    }

    private void checkSymbol(char ch) {
        switch (ch){
            case ']':
                defineUnpackingContent();
                break;
            default:
                operatorStack.push(ch);
        }
    }

    private void defineUnpackingContent() {
        StringBuilder contentBuilder = context.getBean("stringBuilder", StringBuilder.class);
        while (!operatorStack.empty()){
            char pop = operatorStack.pop();
            if(pop == '['){
                break;
            }else {
                contentBuilder.append(pop);
            }
        }
        String reverse = contentBuilder.reverse().toString();
        unpackContent(reverse);
    }

    private void unpackContent(String temp) {
        StringBuilder unpackBuilder = context.getBean("stringBuilder", StringBuilder.class);
        int size = defineSizeUnpackedContent();
        while (size-->0){
            unpackBuilder.append(temp);
        }
        unpackedReturnToStack(unpackBuilder.toString());
    }

    private int defineSizeUnpackedContent(){
        StringBuilder numberBuilder = context.getBean("stringBuilder", StringBuilder.class);
        while (!operatorStack.empty()){
            char peek = operatorStack.peek();
            if(Character.isDigit(peek)){
                char pop = operatorStack.pop();
                numberBuilder.append(pop);
            }else {
                break;
            }
        }
        if(numberBuilder.length() > 1) {
            String number = numberBuilder.reverse().toString();
            return Integer.parseInt(number);
        }
        return Integer.parseInt(numberBuilder.toString());
    }

    private void unpackedReturnToStack(String unpack){
        for (char ch: unpack.toCharArray()){
            operatorStack.push(ch);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }
}
