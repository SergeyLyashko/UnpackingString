package main;

import exceptions.InvalidCharacterException;
import exceptions.NoCloseStringPackException;
import exceptions.NoOpenStringPackException;
import exceptions.NoSuchSizePackingException;

public interface Unpacking {

    String unpack(String packedString) throws NoCloseStringPackException, NoOpenStringPackException, NoSuchSizePackingException, InvalidCharacterException;
}
