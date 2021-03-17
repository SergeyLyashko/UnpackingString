package out;

import main.Printer;
import org.springframework.stereotype.Service;

@Service("printer")
class SystemOutputPrinterImpl implements Printer {

    @Override
    public void print(String string) {
        System.out.println(string);
    }

    @Override
    public void printError(String error) {
        System.err.println(error);
    }
}
