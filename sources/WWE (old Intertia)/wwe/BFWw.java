package wwe;

import java.io.*;

public class BFWw extends Thread
{
    private rfyb dSmT;
    private URoz PjNM;
    
    BFWw(final rfyb dSmT, final URoz pjNM) {
        super();
        this.dSmT = null;
        this.PjNM = null;
        this.dSmT = dSmT;
        this.PjNM = pjNM;
        this.setName(this.getClass() + "-Thread");
    }
    
    static void CjuN(final rfyb rfyb, final BufferedWriter bufferedWriter, String substring) {
        if (substring.length() > rfyb.xsPA() - 2) {
            substring = substring.substring(0, rfyb.xsPA() - 2);
        }
        synchronized (bufferedWriter) {
            try {
                bufferedWriter.write(substring + "\r\n");
                bufferedWriter.flush();
                rfyb.jgkl(">>>" + substring);
                if (BFWw.\u2004\u2006\u2001\u200e\u2001\u200a\u200f) {
                    throw null;
                }
            }
            catch (Exception ex) {}
            // monitorexit(bufferedWriter)
            if (BFWw.\u2004\u2006\u2001\u200e\u2001\u200a\u200f) {
                throw null;
            }
        }
    }
    
    @Override
    public void run() {
        try {
            do {
                Thread.sleep(this.dSmT.fLwJ());
                final String s = (String)this.PjNM.uFcI();
                if (s != null) {
                    this.dSmT.qOaH(s);
                    if (BFWw.\u2004\u2006\u2001\u200e\u2001\u200a\u200f) {
                        throw null;
                    }
                    continue;
                }
            } while (!BFWw.\u2004\u2006\u2001\u200e\u2001\u200a\u200f);
            throw null;
        }
        catch (InterruptedException ex) {}
    }
}
