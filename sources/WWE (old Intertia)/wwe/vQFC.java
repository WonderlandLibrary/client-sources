package wwe;

import java.net.*;
import java.util.*;
import java.io.*;

public class vQFC extends Thread
{
    private rfyb EeiH;
    private Socket beAY;
    private BufferedReader aNdt;
    private BufferedWriter Crzn;
    private boolean Ekju;
    private boolean TUvu;
    public static final int tMGz = 512;
    
    vQFC(final rfyb eeiH, final Socket beAY, final BufferedReader aNdt, final BufferedWriter crzn) {
        super();
        this.EeiH = null;
        this.beAY = null;
        this.aNdt = null;
        this.Crzn = null;
        this.Ekju = true;
        this.TUvu = false;
        this.EeiH = eeiH;
        this.beAY = beAY;
        this.aNdt = aNdt;
        this.Crzn = crzn;
        this.setName(this.getClass() + "-Thread");
    }
    
    void CSeR(final String s) {
        BFWw.CjuN(this.EeiH, this.Crzn, s);
    }
    
    boolean mZgA() {
        return this.Ekju;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                try {
                    String line;
                    while ((line = this.aNdt.readLine()) != null) {
                        try {
                            this.EeiH.aFqa(line);
                            if (vQFC.\u2004\u2003\u2006\u2006\u2000\u200e\u2003\u200c) {
                                throw null;
                            }
                            continue;
                        }
                        catch (Throwable t) {
                            final StringWriter stringWriter = new StringWriter();
                            final PrintWriter printWriter = new PrintWriter(stringWriter);
                            t.printStackTrace(printWriter);
                            printWriter.flush();
                            final StringTokenizer stringTokenizer = new StringTokenizer(stringWriter.toString(), "\r\n");
                            synchronized (this.EeiH) {
                                this.EeiH.jgkl("### Your implementation of PircBot is faulty and you have");
                                this.EeiH.jgkl("### allowed an uncaught Exception or Error to propagate in your");
                                this.EeiH.jgkl("### code. It may be possible for PircBot to continue operating");
                                this.EeiH.jgkl("### normally. Here is the stack trace that was produced: -");
                                this.EeiH.jgkl("### ");
                                while (stringTokenizer.hasMoreTokens()) {
                                    this.EeiH.jgkl("### " + stringTokenizer.nextToken());
                                    if (vQFC.\u2004\u2003\u2006\u2006\u2000\u200e\u2003\u200c) {
                                        throw null;
                                    }
                                }
                                // monitorexit(this.EeiH)
                                if (vQFC.\u2004\u2003\u2006\u2006\u2000\u200e\u2003\u200c) {
                                    throw null;
                                }
                            }
                            if (vQFC.\u2004\u2003\u2006\u2006\u2000\u200e\u2003\u200c) {
                                throw null;
                            }
                            continue;
                        }
                        break;
                    }
                    if (line == null) {}
                    if (vQFC.\u2004\u2003\u2006\u2006\u2000\u200e\u2003\u200c) {
                        throw null;
                    }
                    continue;
                }
                catch (InterruptedIOException ex) {
                    this.CSeR("PING " + System.currentTimeMillis() / (0x5B490BCD3CEEE121L ^ 0x5B490BCD3CEEE2C9L));
                    if (vQFC.\u2004\u2003\u2006\u2006\u2000\u200e\u2003\u200c) {
                        throw null;
                    }
                    continue;
                }
            }
        }
        catch (Exception ex2) {
            try {
                this.beAY.close();
                if (vQFC.\u2004\u2003\u2006\u2006\u2000\u200e\u2003\u200c) {
                    throw null;
                }
            }
            catch (Exception ex3) {}
            if (!this.TUvu) {
                this.EeiH.jgkl("*** Disconnected.");
                this.Ekju = false;
                this.EeiH.OODH();
            }
        }
    }
    
    public void gAUP() {
        try {
            this.TUvu = true;
            this.beAY.close();
            if (vQFC.\u2004\u2003\u2006\u2006\u2000\u200e\u2003\u200c) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
}
