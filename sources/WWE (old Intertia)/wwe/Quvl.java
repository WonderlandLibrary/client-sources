package wwe;

import java.io.*;

public class Quvl extends rfyb
{
    private final String tKXX = "irc.mibbit.net";
    private final int VqZU = 6667;
    private final String jqOi = "#WWEHackedClient";
    private static String WrQX;
    
    public Quvl(String string) {
        super();
        try {
            Integer.parseInt(string.substring(0, 1));
            string = "MC" + string;
        }
        catch (NumberFormatException ex) {}
        Quvl.WrQX = string;
    }
    
    public void XJrX() {
        this.iYjn(true);
        this.JcTH(Quvl.WrQX);
        this.jtNl(Quvl.WrQX);
        try {
            this.qMNW("irc.mibbit.net", 6667);
            if (Quvl.\u2001\u2001\u2008\u2007\u2002\u200d\u2001\u2004) {
                throw null;
            }
        }
        catch (woMI woMI) {
            woMI.printStackTrace();
            if (Quvl.\u2001\u2001\u2008\u2007\u2002\u200d\u2001\u2004) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            if (Quvl.\u2001\u2001\u2008\u2007\u2002\u200d\u2001\u2004) {
                throw null;
            }
        }
        catch (ltaQ ltaQ) {
            ltaQ.printStackTrace();
        }
        this.eExQ("#WWEHackedClient");
    }
}
