package HORIZON-6-0-SKIDPROTECTION;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;

public class S0M3SH1T
{
    public static String HorizonCode_Horizon_È(final String str) {
        return DatatypeConverter.printBase64Binary(str.getBytes(Charset.forName("UTF-8")));
    }
    
    public static String Â(final String str) {
        return new String(DatatypeConverter.parseBase64Binary(str), Charset.forName("UTF-8"));
    }
}
