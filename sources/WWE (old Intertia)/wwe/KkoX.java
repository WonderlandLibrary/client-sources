package wwe;

import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.text.*;
import java.io.*;

public class KkoX
{
    public int RDoM;
    public int VEXW;
    public File EGLp;
    public String mxzj;
    public boolean iAPf;
    public boolean naqm;
    public boolean UIHu;
    public gbIL eaRR;
    
    public KkoX(final gbIL eaRR, final int rDoM, final int vexw, final File egLp) {
        super();
        this.eaRR = eaRR;
        this.RDoM = rDoM;
        this.VEXW = vexw;
        this.EGLp = egLp;
    }
    
    public KkoX(final gbIL eaRR, final int rDoM, final int vexw, final String mxzj) {
        super();
        this.eaRR = eaRR;
        this.RDoM = rDoM;
        this.VEXW = vexw;
        this.mxzj = mxzj;
        this.iAPf = true;
    }
    
    public void Fgbt(final int n, final int n2, final float n3) {
        final int n4 = 250;
        final int n5 = 10;
        boolean uiHu;
        if (n > this.RDoM && n < this.RDoM + n4 && n2 > this.VEXW - 1 && n2 < this.VEXW + n5) {
            uiHu = true;
            if (KkoX.\u200e\u200f\u2001\u200e\u2008\u2001\u200a) {
                throw null;
            }
        }
        else {
            uiHu = false;
        }
        this.UIHu = uiHu;
        final int rDoM = this.RDoM;
        final int vexw = this.VEXW;
        final int n6 = this.RDoM + n4;
        final int n7 = this.VEXW + n5;
        int n8;
        if (this.eaRR.Ezsz != null && this.eaRR.Ezsz == this) {
            n8 = new Color(0, 120, 215, 100).getRGB();
            if (KkoX.\u200e\u200f\u2001\u200e\u2008\u2001\u200a) {
                throw null;
            }
        }
        else if (this.UIHu) {
            n8 = new Color(0, 120, 215, 50).getRGB();
            if (KkoX.\u200e\u200f\u2001\u200e\u2008\u2001\u200a) {
                throw null;
            }
        }
        else {
            n8 = -1;
        }
        Gui.drawRect(rDoM, vexw, n6, n7, n8);
        final giQz tCnN = bozE.TCnN;
        String s;
        if (this.EGLp == null) {
            s = this.mxzj;
            if (KkoX.\u200e\u200f\u2001\u200e\u2008\u2001\u200a) {
                throw null;
            }
        }
        else {
            s = this.EGLp.getName();
        }
        tCnN.drawString(s, this.RDoM + 5, this.VEXW, Color.black.getRGB());
    }
    
    public boolean IxcR(final int n, final int n2, final int n3) throws IOException {
        if (this.eaRR.Ezsz != null && this.eaRR.Ezsz.mxzj != null && this.eaRR.Ezsz == this && this.UIHu) {
            this.eaRR.iekf = this.eaRR.iekf.getParentFile();
            while (this.eaRR.iekf == null) {
                final gbIL eaRR = this.eaRR;
                final gbIL eaRR2 = this.eaRR;
                eaRR.iekf = new File(gbIL.yUUB.getAbsolutePath().replaceAll("\\.", ""));
                if (KkoX.\u200e\u200f\u2001\u200e\u2008\u2001\u200a) {
                    throw null;
                }
            }
            final gbIL eaRR3 = this.eaRR;
            gbIL.yUUB = this.eaRR.iekf;
            this.eaRR.Ezsz = null;
            this.eaRR.initGui();
            return true;
        }
        if (this.eaRR.Ezsz != null && this.eaRR.Ezsz.EGLp != null && this.eaRR.Ezsz == this && this.UIHu) {
            if (this.eaRR.Ezsz.EGLp.isDirectory()) {
                this.eaRR.iekf = this.eaRR.Ezsz.EGLp;
                this.eaRR.Ezsz = null;
                this.eaRR.initGui();
                if (KkoX.\u200e\u200f\u2001\u200e\u2008\u2001\u200a) {
                    throw null;
                }
            }
            else if (this.eaRR.Ezsz.EGLp.isFile()) {
                if (this.eaRR.YMUh(this.eaRR.Ezsz.EGLp)) {
                    this.eaRR.WmnF.gyPG(this.eaRR.Ezsz.EGLp);
                    this.eaRR.initGui();
                    this.eaRR.mc.displayGuiScreen(this.eaRR.MRWv);
                    if (KkoX.\u200e\u200f\u2001\u200e\u2008\u2001\u200a) {
                        throw null;
                    }
                }
                else {
                    this.eaRR.iHDa = TextFormatting.RED + "Incorrect file type";
                }
                this.eaRR.Ezsz = null;
            }
            return true;
        }
        if (this.UIHu) {
            this.eaRR.Ezsz = this;
            return true;
        }
        return false;
    }
}
