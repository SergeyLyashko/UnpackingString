package handlers;

import configuration.UnpackingStringConfiguration;
import exceptions.ContentDigitPackingException;
import exceptions.NoCloseStringPackException;
import exceptions.NoOpenStringPackException;
import exceptions.NoSuchSizePackingException;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

import static org.mockito.Mockito.*;

public class UnpackingExecutorImplTest {

    @Test
    public void executor() {
        UnpackingExecutorImpl unpackingExecutorMock = mock(UnpackingExecutorImpl.class);
        doNothing().when(unpackingExecutorMock).execute();
    }

    public void executor_not_open_bracket() {
        ApplicationContext context = new AnnotationConfigApplicationContext(UnpackingStringConfiguration.class);
        UnpackingImpl unpackingSpy = spy(new UnpackingImpl());
        unpackingSpy.setApplicationContext(context);


    }
}