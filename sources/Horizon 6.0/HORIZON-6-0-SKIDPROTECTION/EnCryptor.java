package HORIZON-6-0-SKIDPROTECTION;

import java.nio.charset.Charset;
import javax.xml.bind.DatatypeConverter;

public class EnCryptor
{
    public static String HorizonCode_Horizon_È(final String text) {
        return new String(DatatypeConverter.parseBase64Binary(text), Charset.defaultCharset());
    }
    
    public static String Â(final String text) {
        return HorizonCode_Horizon_È(new String(DatatypeConverter.parseBase64Binary(text), Charset.defaultCharset()));
    }
    
    public static String Ý(final String text) {
        return Â(new String(DatatypeConverter.parseBase64Binary(text), Charset.defaultCharset()));
    }
    
    public static String Ø­áŒŠá(final String text) {
        return Ý(new String(DatatypeConverter.parseBase64Binary(text), Charset.defaultCharset()));
    }
    
    public static String Âµá€(final String text) {
        return Ø­áŒŠá(new String(DatatypeConverter.parseBase64Binary(text), Charset.defaultCharset()));
    }
    
    public static String Ó(final String text) {
        return Âµá€(new String(DatatypeConverter.parseBase64Binary(text), Charset.defaultCharset()));
    }
    
    public static String à(final String text) {
        return Ó(new String(DatatypeConverter.parseBase64Binary(text), Charset.defaultCharset()));
    }
    
    public static String Ø(final String text) {
        return à(new String(DatatypeConverter.parseBase64Binary(text), Charset.defaultCharset()));
    }
    
    public static String áŒŠÆ(final String text) {
        return Ø(new String(DatatypeConverter.parseBase64Binary(text), Charset.defaultCharset()));
    }
    
    public static String áˆºÑ¢Õ(final String text) {
        return áŒŠÆ(new String(DatatypeConverter.parseBase64Binary(text), Charset.defaultCharset()));
    }
}
