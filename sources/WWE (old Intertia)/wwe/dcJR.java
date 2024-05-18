package wwe;

import java.net.*;
import java.io.*;

public class dcJR
{
    private rfyb vBFs;
    private String Zztg;
    private String CRHJ;
    private String kCgC;
    private BufferedReader ApCs;
    private BufferedWriter VEsU;
    private Socket zneM;
    private boolean WtfG;
    private long zUKy;
    private int EWNa;
    
    dcJR(final rfyb vbFs, final String zztg, final String crhj, final String kCgC, final long zuKy, final int ewNa) {
        super();
        this.CRHJ = null;
        this.kCgC = null;
        this.zUKy = (0xDF2FE6CFD1417C05L ^ 0xDF2FE6CFD1417C05L);
        this.EWNa = 0;
        this.vBFs = vbFs;
        this.zUKy = zuKy;
        this.EWNa = ewNa;
        this.Zztg = zztg;
        this.CRHJ = crhj;
        this.kCgC = kCgC;
        this.WtfG = true;
    }
    
    dcJR(final rfyb vbFs, final String zztg, final Socket zneM) throws IOException {
        super();
        this.CRHJ = null;
        this.kCgC = null;
        this.zUKy = (0x821326A868ECFF94L ^ 0x821326A868ECFF94L);
        this.EWNa = 0;
        this.vBFs = vbFs;
        this.Zztg = zztg;
        this.zneM = zneM;
        this.ApCs = new BufferedReader(new InputStreamReader(this.zneM.getInputStream()));
        this.VEsU = new BufferedWriter(new OutputStreamWriter(this.zneM.getOutputStream()));
        this.WtfG = false;
    }
    
    public synchronized void mYKU() throws IOException {
        if (this.WtfG) {
            this.WtfG = false;
            final int[] array = this.vBFs.Lodk(this.zUKy);
            this.zneM = new Socket(array[0] + "." + array[1] + "." + array[2] + "." + array[3], this.EWNa);
            this.ApCs = new BufferedReader(new InputStreamReader(this.zneM.getInputStream()));
            this.VEsU = new BufferedWriter(new OutputStreamWriter(this.zneM.getOutputStream()));
        }
    }
    
    public String VwPt() throws IOException {
        if (this.WtfG) {
            throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
        }
        return this.ApCs.readLine();
    }
    
    public void ZLEf(final String s) throws IOException {
        if (this.WtfG) {
            throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
        }
        this.VEsU.write(s + "\r\n");
        this.VEsU.flush();
    }
    
    public void pjOC() throws IOException {
        if (this.WtfG) {
            throw new IOException("You must call the accept() method of the DccChat request before you can use it.");
        }
        this.zneM.close();
    }
    
    public String xWJs() {
        return this.Zztg;
    }
    
    public String xxRe() {
        return this.CRHJ;
    }
    
    public String LxXl() {
        return this.kCgC;
    }
    
    public BufferedReader EsPG() {
        return this.ApCs;
    }
    
    public BufferedWriter FUId() {
        return this.VEsU;
    }
    
    public Socket xHVb() {
        return this.zneM;
    }
    
    public long RFQb() {
        return this.zUKy;
    }
}
