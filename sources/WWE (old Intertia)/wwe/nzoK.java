package wwe;

import wwe.utils.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public class nzoK extends kUUQ
{
    public boolean vwqN;
    public ohwW SvfS;
    public ModeValue srlg;
    public int jXta;
    public int EISd;
    public int LFEY;
    
    public nzoK(final ModeValue srlg, final ohwW svfS, final int jXta) {
        super();
        this.srlg = srlg;
        this.SvfS = svfS;
        this.EISd = svfS.rhHA.kVnf + svfS.rhHA.dedb;
        this.LFEY = svfS.rhHA.CQIZ + svfS.yQtC;
        this.jXta = jXta;
    }
    
    @Override
    public void JyWl(final int n, final int n2, final float n3) {
        final float n4 = (float)(this.SvfS.rhHA.kVnf + 2);
        final float n5 = (float)(this.SvfS.rhHA.CQIZ + this.jXta);
        final float n6 = (float)(this.SvfS.rhHA.kVnf + this.SvfS.rhHA.dedb * 1);
        final float n7 = (float)(this.SvfS.rhHA.CQIZ + this.jXta + 12);
        int n8;
        if (this.vwqN) {
            n8 = new Color(15, 15, 15, 245).getRGB();
            if (nzoK.\u2000\u2007\u2003) {
                throw null;
            }
        }
        else {
            n8 = new Color(15, 15, 15, 215).getRGB();
        }
        gnNa.wQYt(n4, n5, n6, n7, n8);
        final float n9 = (float)this.SvfS.rhHA.kVnf;
        final float n10 = (float)(this.SvfS.rhHA.CQIZ + this.jXta);
        final float n11 = (float)(this.SvfS.rhHA.kVnf + 2);
        final float n12 = (float)(this.SvfS.rhHA.CQIZ + this.jXta + 12);
        int n13;
        if (this.vwqN) {
            n13 = new Color(15, 15, 15, 245).getRGB();
            if (nzoK.\u2000\u2007\u2003) {
                throw null;
            }
        }
        else {
            n13 = new Color(15, 15, 15, 215).getRGB();
        }
        gnNa.wQYt(n9, n10, n11, n12, n13);
        GL11.glPushMatrix();
        GL11.glScalef(0.75f, 0.75f, 0.75f);
        XybC.eTim(this.srlg.VQBN + " : " + this.srlg.OnGi(), (this.SvfS.rhHA.kVnf + 7) * 1.33f, (this.SvfS.rhHA.CQIZ + this.jXta) * 1.33f + 5.0f, -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void QdjS(final int n, final int n2) {
        this.vwqN = this.aXbq(n, n2);
        this.LFEY = this.SvfS.rhHA.CQIZ + this.jXta;
        this.EISd = this.SvfS.rhHA.kVnf;
    }
    
    @Override
    public void rVom(final int n, final int n2, final int n3) {
        if (this.aXbq(n, n2) && this.SvfS.fzNA) {
            this.srlg.NdCc(this.srlg.chKb.get(this.xIan()));
        }
    }
    
    public boolean aXbq(final int n, final int n2) {
        return n > this.EISd && n < this.EISd + 88 && n2 > this.LFEY - 1 && n2 < this.LFEY + 12;
    }
    
    public int xIan() {
        int i = 0;
        while (i < this.srlg.chKb.size()) {
            if (this.srlg.chKb.get(i).equals(((IcRI<Object>)this.srlg).OnGi())) {
                if (i + 1 >= this.srlg.chKb.size()) {
                    return 0;
                }
                return i + 1;
            }
            else {
                ++i;
                if (nzoK.\u2000\u2007\u2003) {
                    throw null;
                }
                continue;
            }
        }
        return 0;
    }
    
    @Override
    public void Lrih(final int n, final int n2, final float n3, final ScaledResolution scaledResolution) {
        if (this.vwqN) {
            gnNa.dYaT(2.0f, (float)(scaledResolution.getScaledHeight() - 13), (float)(XybC.wKEb().getStringWidth("  " + this.srlg.qYCS) + 2), (float)(scaledResolution.getScaledHeight() + 19), 1.0f, new Color(15, 15, 15).getRGB(), -1);
            XybC.eTim(this.srlg.qYCS, 6.0f, (float)(scaledResolution.getScaledHeight() - 10), -1);
        }
    }
}
