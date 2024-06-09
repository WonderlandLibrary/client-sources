package HORIZON-6-0-SKIDPROTECTION;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Cryptor
{
    public static final String HorizonCode_Horizon_È = "UTF-8";
    static BASE64Encoder Â;
    static BASE64Decoder Ý;
    
    static {
        Cryptor.Â = new BASE64Encoder();
        Cryptor.Ý = new BASE64Decoder();
    }
    
    public static String HorizonCode_Horizon_È(final String text) {
        return new String(DatatypeConverter.printBase64Binary(text.getBytes(Charset.forName("UTF-8"))));
    }
    
    public static String Â(final String text) {
        return HorizonCode_Horizon_È(new String(DatatypeConverter.printBase64Binary(text.getBytes(Charset.forName("UTF-8")))));
    }
    
    public static String Ý(final String text) {
        return Â(new String(DatatypeConverter.printBase64Binary(text.getBytes(Charset.forName("UTF-8")))));
    }
    
    public static String Ø­áŒŠá(final String text) {
        return Ý(new String(DatatypeConverter.printBase64Binary(text.getBytes(Charset.forName("UTF-8")))));
    }
    
    public static String Âµá€(final String text) {
        return Ø­áŒŠá(new String(DatatypeConverter.printBase64Binary(text.getBytes(Charset.forName("UTF-8")))));
    }
    
    public static String Ó(final String text) {
        return Âµá€(new String(DatatypeConverter.printBase64Binary(text.getBytes(Charset.forName("UTF-8")))));
    }
    
    public static String à(final String text) {
        return Ó(new String(DatatypeConverter.printBase64Binary(text.getBytes(Charset.forName("UTF-8")))));
    }
    
    public static String Ø(final String text) {
        return à(new String(DatatypeConverter.printBase64Binary(text.getBytes(Charset.forName("UTF-8")))));
    }
    
    public static String áŒŠÆ(final String text) {
        return Ø(new String(DatatypeConverter.printBase64Binary(text.getBytes(Charset.forName("UTF-8")))));
    }
    
    public static String áˆºÑ¢Õ(final String text) {
        return áŒŠÆ(new String(DatatypeConverter.printBase64Binary(text.getBytes(Charset.forName("UTF-8")))));
    }
}
