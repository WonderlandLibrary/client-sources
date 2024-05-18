package wwe;

import wwe.modules.gui.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import wwe.utils.*;
import net.minecraft.client.gui.*;
import org.apache.commons.io.*;
import java.io.*;
import java.util.*;
import net.minecraft.util.math.*;

public class XdhZ
{
    public ArrayList<NoaN> HSDz;
    public String nLkA;
    public boolean wMBz;
    public int zipv;
    public int zzJD;
    public int OsiI;
    public int ooJc;
    public boolean ioOs;
    public int dAcA;
    public int zuca;
    public boolean fzpE;
    public boolean yasy;
    public boolean fwwC;
    public NoaN sILe;
    public int AUlY;
    public boolean jwvM;
    public int KyWB;
    public int PVCD;
    
    public XdhZ(final String nLkA) {
        super();
        this.HSDz = new ArrayList<NoaN>();
        this.nLkA = nLkA;
        this.zipv = 88;
        this.OsiI = 5;
        this.zzJD = 5;
        this.ooJc = 13;
        this.dAcA = 0;
        this.wMBz = false;
        this.ioOs = false;
        int ooJc = this.ooJc;
        for (int i = 0; i < vlwv.zkER.size(); ++i) {
            this.HSDz.add(new NoaN(vlwv.zkER.get(i).kAWG, this, ooJc));
            ooJc += 12;
        }
    }
    
    public void OQea(final int n, final int n2, final float n3) {
        this.uRXN(0, this.AUlY);
        this.QqND(0.0, false);
        this.jwvM = this.pZuF(n, n2);
        int n4;
        if (this.HSDz.size() <= 5) {
            n4 = this.HSDz.size() * 12;
            if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                throw null;
            }
        }
        else {
            n4 = 60;
        }
        final int n5 = n4;
        this.fzpE = this.lwFg(n, n2, this.OsiI + 2, this.OsiI + 28, this.zzJD + this.ooJc + n5 + 3, this.zzJD + this.ooJc + n5 + 13);
        this.yasy = this.lwFg(n, n2, this.OsiI + this.zipv - 27, this.OsiI + this.zipv - 17, this.zzJD + this.ooJc + n5 + 3, this.zzJD + this.ooJc + n5 + 13);
        this.fwwC = this.lwFg(n, n2, this.OsiI + this.zipv - 12, this.OsiI + this.zipv - 2, this.zzJD + this.ooJc + n5 + 3, this.zzJD + this.ooJc + n5 + 13);
        if (this.ioOs) {
            this.OsiI = n - this.dAcA;
            this.zzJD = n2 - this.zuca;
        }
        gnNa.wQYt((float)this.OsiI, (float)this.zzJD, (float)(this.OsiI + this.zipv), (float)(this.zzJD + this.ooJc), new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue(), 175).getRGB());
        if (this.ioOs) {
            gnNa.wQYt((float)this.OsiI, (float)this.zzJD, (float)(this.OsiI + this.zipv), (float)(this.zzJD + this.ooJc), new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue()).getRGB());
        }
        XybC.eTim(this.nLkA, (float)(this.OsiI + 3), (float)(this.zzJD + 2), -1);
        GL11.glPushMatrix();
        if (this.wMBz) {
            gnNa.wQYt((float)this.OsiI, (float)(this.zzJD + this.ooJc + n5), (float)(this.OsiI + this.zipv), (float)(this.zzJD + this.ooJc + n5 + 16), new Color(15, 15, 15, 215).getRGB());
            final float n6 = (float)(this.OsiI + 2);
            final float n7 = (float)(this.zzJD + this.ooJc + n5 + 3);
            final float n8 = (float)(this.OsiI + 28);
            final float n9 = (float)(this.zzJD + this.ooJc + n5 + 13);
            int n10;
            if (this.fzpE) {
                n10 = new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue(), 215).getRGB();
                if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                    throw null;
                }
            }
            else {
                n10 = new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue(), 175).getRGB();
            }
            gnNa.wQYt(n6, n7, n8, n9, n10);
            XybC.eTim("Load", (float)(this.OsiI + 3), (float)(this.zzJD + this.ooJc + n5 + 4), -1);
            final float n11 = (float)(this.OsiI + this.zipv - 27);
            final float n12 = (float)(this.zzJD + this.ooJc + n5 + 3);
            final float n13 = (float)(this.OsiI + this.zipv - 17);
            final float n14 = (float)(this.zzJD + this.ooJc + n5 + 13);
            int n15;
            if (this.yasy) {
                n15 = new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue(), 215).getRGB();
                if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                    throw null;
                }
            }
            else {
                n15 = new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue(), 175).getRGB();
            }
            gnNa.wQYt(n11, n12, n13, n14, n15);
            XybC.eTim("-", (float)(this.OsiI + this.zipv - 25), (float)(this.zzJD + this.ooJc + n5 + 4), -1);
            final float n16 = (float)(this.OsiI + this.zipv - 12);
            final float n17 = (float)(this.zzJD + this.ooJc + n5 + 3);
            final float n18 = (float)(this.OsiI + this.zipv - 2);
            final float n19 = (float)(this.zzJD + this.ooJc + n5 + 13);
            int n20;
            if (this.fwwC) {
                n20 = new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue(), 215).getRGB();
                if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                    throw null;
                }
            }
            else {
                n20 = new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue(), 175).getRGB();
            }
            gnNa.wQYt(n16, n17, n18, n19, n20);
            XybC.eTim("+", (float)(this.OsiI + this.zipv - 10), (float)(this.zzJD + this.ooJc + n5 + 4), -1);
            if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("Normal")) {
                final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
                final int scaleFactor = scaledResolution.getScaleFactor();
                GL11.glScissor(0, (int)((scaledResolution.getScaledHeight_double() - this.zzJD - this.ooJc - 60.0) * scaleFactor), (int)(scaledResolution.getScaledWidth_double() * scaleFactor), 60 * scaleFactor);
                GL11.glEnable(3089);
            }
            if (!this.HSDz.isEmpty()) {
                final Iterator<NoaN> iterator = this.HSDz.iterator();
                while (iterator.hasNext()) {
                    iterator.next().SOOn();
                    if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                        throw null;
                    }
                }
            }
            if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("Normal")) {
                GL11.glDisable(3089);
            }
        }
        GL11.glPopMatrix();
    }
    
    public boolean kCbX(final int n, final int n2) {
        return n >= this.OsiI && n <= this.OsiI + this.zipv && n2 >= this.zzJD && n2 <= this.zzJD + this.ooJc;
    }
    
    private boolean pZuF(final int n, final int n2) {
        if (n >= this.OsiI && n <= this.OsiI + this.zipv && n2 >= this.zzJD) {
            final int n3 = this.zzJD + this.ooJc;
            int n4;
            if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("Normal")) {
                if (this.HSDz.size() > 5) {
                    n4 = 60;
                    if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                        throw null;
                    }
                }
                else {
                    n4 = this.HSDz.size() * 12;
                    if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                        throw null;
                    }
                }
            }
            else {
                n4 = this.HSDz.size() * 12;
            }
            if (n2 < n3 + n4) {
                return true;
            }
        }
        return false;
    }
    
    public void KeMT(final double n) {
    }
    
    public void KolI(final int n, final int n2, final int n3) {
        if (this.kCbX(n, n2)) {
            this.ioOs = true;
            this.dAcA = n - this.OsiI;
            this.zuca = n2 - this.zzJD;
        }
        if (this.kCbX(n, n2) && n3 == 1) {
            boolean wmBz;
            if (!this.wMBz) {
                wmBz = true;
                if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                    throw null;
                }
            }
            else {
                wmBz = false;
            }
            this.wMBz = wmBz;
        }
        if (this.wMBz) {
            if (this.fwwC) {
                Wrapper.mc.displayGuiScreen((GuiScreen)new IhAb());
            }
            if (this.yasy && this.sILe != null) {
                int i = 0;
                while (i < vlwv.zkER.size()) {
                    final hfst hfst = vlwv.zkER.get(i);
                    if (hfst.kAWG == this.sILe.lALN) {
                        this.aFjO(hfst);
                    }
                    ++i;
                    if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                        throw null;
                    }
                }
            }
            if (this.fzpE && this.sILe != null) {
                try {
                    Wrapper.ypJK().eTCw(this.sILe.lALN);
                    Wrapper.ypJK().rXPb();
                    if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                        throw null;
                    }
                }
                catch (Exception ex) {
                    HAxG.TuzD("It appears something went wrong");
                    ex.printStackTrace();
                }
            }
            if (!this.HSDz.isEmpty()) {
                final Iterator<NoaN> iterator = this.HSDz.iterator();
                while (iterator.hasNext()) {
                    iterator.next().rVom(n, n2, n3);
                    if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                        throw null;
                    }
                }
            }
        }
    }
    
    public void QWLO(final hfst hfst, final boolean b) {
        int i = 0;
        while (i < vlwv.zkER.size()) {
            if (hfst.kAWG == vlwv.zkER.get(i).kAWG) {
                HAxG.TuzD("It appears that may already be a preset?");
                return;
            }
            ++i;
            if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                throw null;
            }
        }
        vlwv.zkER.add(hfst);
        this.HSDz.add(new NoaN(hfst.kAWG, this, this.HSDz.size() * 12 + 13));
        if (b) {
            Wrapper.ypJK().hziJ(hfst.kAWG);
        }
    }
    
    public void aFjO(final hfst hfst) {
        vlwv.zkER.remove(hfst);
        try {
            FileUtils.deleteDirectory(new File(Wrapper.ypJK().oSNt.getAbsolutePath() + File.separator + this.sILe.lALN));
            if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        this.HSDz.removeAll(this.HSDz);
        int i = 0;
        while (i < vlwv.zkER.size()) {
            this.HSDz.add(new NoaN(vlwv.zkER.get(i).kAWG, this, this.HSDz.size() * 12 + 13));
            ++i;
            if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                throw null;
            }
        }
    }
    
    public boolean lwFg(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        boolean b;
        if (n >= n3 && n <= n4 && n2 >= n5 && n2 <= n6) {
            b = true;
            if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public void uRXN(final int n, final int n2) {
        int jXta = this.ooJc + n - n2;
        int i = 0;
        while (i < this.HSDz.size()) {
            this.HSDz.get(i).jXta = jXta;
            jXta += 12;
            ++i;
            if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                throw null;
            }
        }
    }
    
    public void odQY(final double n) {
        if (vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("All")) {
            if (n < 0.0) {
                this.zzJD -= 5;
                if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                    throw null;
                }
            }
            else if (n > 0.0) {
                this.zzJD += 5;
            }
        }
        if (this.wMBz && this.jwvM) {
            this.QqND(n, true);
        }
    }
    
    public void QqND(double clamp, final boolean b) {
        int aUlY = 12;
        if (this.HSDz.size() <= 5) {
            aUlY = 0;
        }
        clamp = MathHelper.clamp(clamp, 1.0, -1.0);
        if (b && vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).scrolling.OnGi().equalsIgnoreCase("Normal")) {
            this.AUlY += (int)(clamp * 10.0);
        }
        if (this.AUlY < 0) {
            this.AUlY = 0;
        }
        int i = 0;
        while (i < this.HSDz.size()) {
            final NoaN noaN = this.HSDz.get(i);
            if (i > Math.min(this.HSDz.size(), 5)) {
                aUlY += 12;
            }
            ++i;
            if (XdhZ.\u2000\u200e\u2005\u2001\u200c\u200a) {
                throw null;
            }
        }
        if (this.AUlY > Math.abs(aUlY)) {
            this.AUlY = aUlY;
        }
    }
}
