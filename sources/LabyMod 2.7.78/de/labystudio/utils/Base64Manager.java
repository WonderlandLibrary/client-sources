package de.labystudio.utils;

import javax.xml.bind.DatatypeConverter;

public class Base64Manager
{
    public static String encode(String string)
    {
        String s = DatatypeConverter.printBase64Binary(string.getBytes());
        return s;
    }

    public static String decode(String base64String)
    {
        String s = new String(DatatypeConverter.parseBase64Binary(base64String));
        return s;
    }
}
