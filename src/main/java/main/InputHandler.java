package main;

import handlers.Checker;
import handlers.CheckerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service("inputHandler")
public class InputHandler implements ApplicationContextAware {

    private ApplicationContext context;
    //private CheckPrinter checkPrinter;
    /*
    @Autowired
    public void setCheckPrinter(CheckPrinter checkPrinter){
        this.checkPrinter = checkPrinter;
    }*/

    public void handle(Scanner scan) {
        //System.out.println("Please input braces set or quit for exit: ");
        String line;
        while (!(line = scan.next()).equalsIgnoreCase("quit")) {
            CheckerImpl checker = context.getBean("checker", CheckerImpl.class);
            checker.read(line);
        }
        System.out.println("Bye!");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }
}
