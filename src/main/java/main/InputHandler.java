package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service("inputHandler")
class InputHandler implements ApplicationContextAware {

    private ApplicationContext context;
    private Printer printer;

    @Autowired
    public void setPrinter(Printer printer){
        this.printer = printer;
    }

    void handle(Scanner scan) {
        printer.print("This is app for unpacked string format example: 2[xyz]2[abc] = xyzxyzabcabc");
        printer.print("Parameter validation support");
        String packedString;
        while (!(packedString = scan.next()).equalsIgnoreCase("quit")) {
            printer.print("Please input packed string or quit for exit: ");
            Unpacking unpacking = context.getBean("unpacking", Unpacking.class);
            String unpack = unpacking.unpack(packedString);
            printer.print("unpack: "+unpack);
        }
        printer.print("Bye!");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }
}
