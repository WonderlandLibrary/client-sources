package wwe;

import wwe.modules.*;
import wwe.utils.*;

public class qMIe
{
    private String BomB;
    private String ZnKN;
    private String Jken;
    private int OUcb;
    private Category ayOm;
    private boolean QSVB;
    private int ibSR;
    private boolean yKHz;
    private boolean paks;
    
    public qMIe(final String s, final String jken, final int oUcb, final Category ayOm) {
        super();
        this.BomB = s;
        this.ZnKN = s;
        this.Jken = jken;
        this.OUcb = oUcb;
        this.ayOm = ayOm;
        this.ibSR = ayOm.YihZ;
        this.paks = true;
        this.HrDr();
    }
    
    public String czUH() {
        return this.BomB;
    }
    
    public void UZWg(final String bomB) {
        this.BomB = bomB;
    }
    
    public int APjp() {
        return this.OUcb;
    }
    
    public void hzzk(final int oUcb) {
        this.OUcb = oUcb;
    }
    
    public Category ufol() {
        return this.ayOm;
    }
    
    public int UUxj() {
        if (this.ayOm == Category.COMBAT) {
            return vlwv.fomn.Jnvr.JqMr().getRGB();
        }
        if (this.ayOm == Category.PLAYER) {
            return vlwv.fomn.CBaw.JqMr().getRGB();
        }
        if (this.ayOm == Category.MOVEMENT) {
            return vlwv.fomn.REhN.JqMr().getRGB();
        }
        if (this.ayOm == Category.RENDER) {
            return vlwv.fomn.Fkzu.JqMr().getRGB();
        }
        if (this.ayOm == Category.EXPLOITS) {
            return vlwv.fomn.fELe.JqMr().getRGB();
        }
        if (this.ayOm == Category.WORLD) {
            return vlwv.fomn.gonM.JqMr().getRGB();
        }
        if (this.ayOm == Category.GUI) {
            return -16777216;
        }
        return -1;
    }
    
    public void vDjP(final int ibSR) {
        this.ibSR = ibSR;
    }
    
    public boolean IuiN() {
        return this.QSVB;
    }
    
    public void vHny(final boolean b) {
        this.LZeF();
        if (b) {
            this.LPxx();
            this.QSVB = true;
            if (qMIe.\u200c) {
                throw null;
            }
        }
        else {
            this.qBmy();
            this.QSVB = false;
        }
    }
    
    public void GDVp() {
        boolean b;
        if (!this.IuiN()) {
            b = true;
            if (qMIe.\u200c) {
                throw null;
            }
        }
        else {
            b = false;
        }
        this.vHny(b);
        Wrapper.ypJK().ZqQq();
    }
    
    public void LZeF() {
    }
    
    public void LPxx() {
    }
    
    public void qBmy() {
    }
    
    public void hPRw() {
    }
    
    public void qHNm() {
    }
    
    public void HrDr() {
    }
    
    public void OgHM() {
    }
    
    public final boolean jrFS(final Category category) {
        return category == this.ayOm;
    }
    
    public String Odlv() {
        return this.ZnKN;
    }
    
    public void owgW(final String znKN) {
        this.ZnKN = znKN;
    }
    
    public String GDCZ() {
        return this.Jken;
    }
    
    public void QXzK(final String jken) {
        this.Jken = jken;
    }
    
    public boolean sUHq() {
        boolean b;
        if (!this.yKHz) {
            b = true;
            if (qMIe.\u200c) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public void FSRf(final boolean ykHz) {
        this.yKHz = ykHz;
    }
    
    public boolean LXyR() {
        return this.paks;
    }
    
    public void azsP(final boolean paks) {
        this.paks = paks;
    }
}
