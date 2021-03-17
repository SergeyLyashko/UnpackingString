package handlers;

import exceptions.ContentDigitPackingException;
import exceptions.NoCloseStringPackException;
import exceptions.NoOpenStringPackException;
import exceptions.NoSuchSizePackingException;
import main.InputHandler;
import main.Printer;
import main.Unpacking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service("inputHandler")
class InputHandlerImpl implements InputHandler, ApplicationContextAware {

    private ApplicationContext context;
    private Printer printer;
    private Scanner scanner;

    @Autowired
    public void setPrinter(Printer printer){
        this.printer = printer;
    }

    @Autowired
    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }

    @Override
    public void handle() {
        printHeader();
        String packedString;
        while (!(packedString = scanner.next()).equalsIgnoreCase("quit")) {
            Unpacking unpacking = context.getBean("unpacking", Unpacking.class);
            try {
                String unpack = unpacking.unpack(packedString);
                printer.print("unpack: " + unpack);
            } catch (NoOpenStringPackException | NoCloseStringPackException | NoSuchSizePackingException | ContentDigitPackingException exception) {
                printer.printError(exception.toString());
            } catch (NumberFormatException exception){
                printer.printError(exception+" - this unpacking factor beyond reasonable limits.");
            } finally {
                printer.print("Please input packed string or quit for exit: ");
            }
        }
        printer.print("Bye!");
    }

    private void printHeader(){
        printer.print("This is app for unpacked string format example: 2[xyz]2[abc] = xyzxyzabcabc");
        printer.print("Supported validation of parameters & unpacking number factor > 9.");
        printer.print("Please input packed string or quit for exit: ");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }
}
