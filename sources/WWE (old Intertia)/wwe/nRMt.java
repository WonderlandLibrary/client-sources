package wwe;

import java.awt.*;
import wwe.utils.*;
import net.minecraft.init.*;
import net.minecraft.client.audio.*;
import java.io.*;

public class nRMt
{
    public BxVd RFpH;
    public String ckBF;
    public int Xeln;
    public int fsDF;
    public int LEEc;
    public int mdjz;
    public boolean EEuE;
    
    public nRMt(final BxVd rFpH, final String ckBF, final int xeln, final int fsDF, final int mdjz, final int leEc) {
        super();
        this.RFpH = rFpH;
        this.ckBF = ckBF;
        this.Xeln = xeln;
        this.fsDF = fsDF;
        this.mdjz = mdjz;
        this.LEEc = leEc;
    }
    
    public void pHsd(final int n, final int n2, final float n3) {
        this.mdjz = XybC.wKEb().getStringWidth(this.ckBF) + 4;
        boolean eEuE;
        if (n >= this.Xeln && n2 >= this.fsDF + this.RFpH.OOVQ && n < this.Xeln + this.mdjz && n2 < this.fsDF + this.LEEc + this.RFpH.OOVQ && n2 < this.RFpH.height - 30 && n2 > 40) {
            eEuE = true;
            if (nRMt.\u200c\u2009\u2004\u2001\u200c) {
                throw null;
            }
        }
        else {
            eEuE = false;
        }
        this.EEuE = eEuE;
        final float n4 = (float)this.Xeln;
        final float n5 = (float)(this.fsDF + this.RFpH.OOVQ);
        final float n6 = (float)(this.Xeln + this.mdjz);
        final float n7 = (float)(this.fsDF + this.LEEc + this.RFpH.OOVQ);
        int rgb;
        if (this.EEuE) {
            rgb = new Color(-1873784752).darker().getRGB();
            if (nRMt.\u200c\u2009\u2004\u2001\u200c) {
                throw null;
            }
        }
        else {
            rgb = -1873784752;
        }
        gnNa.wQYt(n4, n5, n6, n7, rgb);
        XybC.eTim(this.ckBF, (float)(this.Xeln + 2), (float)(this.fsDF + this.RFpH.OOVQ + 1), -1);
    }
    
    public void FTwg(final int n, final int n2, final int n3) throws IOException {
        if (this.EEuE) {
            bozE.Jfgf = null;
            bozE.Jfgf = new giQz(new Font(this.ckBF, 0, 36), true, 8);
            Wrapper.ypJK().OsgT();
            Wrapper.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }
    
    public void kybB(final int n) throws IOException {
    }
}
