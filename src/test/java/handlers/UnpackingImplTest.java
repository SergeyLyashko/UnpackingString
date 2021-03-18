package handlers;

import exceptions.NoCloseStringPackException;
import exceptions.NoOpenStringPackException;
import exceptions.NoSuchSizePackingException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UnpackingImplTest {

    @Test
    public void unpack() throws NoSuchSizePackingException, NoOpenStringPackException, NoCloseStringPackException {
        UnpackingImpl unpackingMock = mock(UnpackingImpl.class);
        when(unpackingMock.unpack("testIn")).thenReturn("testOut");
    }

    @Test
    public void unpack_valid_string() throws NoSuchSizePackingException, NoOpenStringPackException, NoCloseStringPackException {
        UnpackingImpl unpackingMock = spy(new UnpackingImpl());
        String in = "2[3[xz]y]k";
        String out = "xzxzxzyxzxzxzyk";
        assertEquals(unpackingMock.unpack(in), out);
    }

    @Test
    public void unpack_invalid_string() throws NoCloseStringPackException, NoSuchSizePackingException, NoOpenStringPackException {
        UnpackingImpl unpackingMock = spy(new UnpackingImpl());
        String in = "2[test]";
        assertNotEquals(unpackingMock.unpack(in), "test");
    }

    @Test
    public void unpack_throw() throws NoCloseStringPackException, NoSuchSizePackingException, NoOpenStringPackException {
        UnpackingImpl unpackingMock = spy(new UnpackingImpl());
        String in = "test";
        doThrow(new NoSuchSizePackingException()).when(unpackingMock).unpack(in);
        doThrow(new NoCloseStringPackException()).when(unpackingMock).unpack(in);
        doThrow(new NoOpenStringPackException()).when(unpackingMock).unpack(in);
    }
}