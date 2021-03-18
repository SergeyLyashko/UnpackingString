package handlers;

import configuration.UnpackingStringConfiguration;
import exceptions.NoCloseStringPackException;
import exceptions.NoOpenStringPackException;
import exceptions.NoSuchSizePackingException;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.mockito.Mockito.*;

public class UnpackingImplTest {

    @Test
    public void unpack() throws NoSuchSizePackingException, NoOpenStringPackException, NoCloseStringPackException {
        UnpackingImpl unpackingMock = mock(UnpackingImpl.class);
        when(unpackingMock.unpack("testIn")).thenReturn("testOut");
    }

    @Test
    public void unpack_valid_string() throws NoSuchSizePackingException, NoOpenStringPackException, NoCloseStringPackException {
        UnpackingImpl unpackingSpy = spy(new UnpackingImpl());
        String in = "2[3[xz]y]k";
        String out = "xzxzxzyxzxzxzyk";
        when(unpackingSpy.unpack(in)).thenReturn(out);
    }
}