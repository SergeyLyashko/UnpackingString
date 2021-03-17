package main;

import exceptions.ContentDigitPackingException;
import exceptions.NoCloseStringPackException;
import exceptions.NoOpenStringPackException;
import exceptions.NoSuchSizePackingException;

public interface Unpacking {

    String unpack(String packedString) throws NoCloseStringPackException, NoOpenStringPackException, NoSuchSizePackingException, ContentDigitPackingException;
}
