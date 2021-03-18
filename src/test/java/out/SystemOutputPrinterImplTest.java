package out;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class SystemOutputPrinterImplTest {

    @Test
    public void print() {
        SystemOutputPrinterImpl outputPrinterMock = mock(SystemOutputPrinterImpl.class);
        doNothing().when(outputPrinterMock).print("test");
        String test = "test";
        outputPrinterMock.print(test);
        verify(outputPrinterMock).print(test);
    }

    @Test
    public void printError() {
        SystemOutputPrinterImpl outputPrinterMock = mock(SystemOutputPrinterImpl.class);
        doNothing().when(outputPrinterMock).printError("error");
        String testError = "error";
        outputPrinterMock.printError(testError);
        verify(outputPrinterMock).printError(testError);
    }
}