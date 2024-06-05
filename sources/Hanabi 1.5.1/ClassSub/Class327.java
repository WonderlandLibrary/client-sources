package ClassSub;

import java.util.*;
import java.text.*;

public class Class327
{
    final String name;
    static final Date dNow;
    static final SimpleDateFormat ft;
    
    
    public Class327(final String name) {
        this.name = name;
    }
    
    public void log(final Object o) {
        if (o != null) {
            System.out.println("[" + this.name + "][" + Class327.ft.format(Class327.dNow) + "]" + o.toString());
        }
    }
    
    static {
        dNow = new Date();
        ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    }
}
