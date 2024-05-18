package wwe;

import wwe.utils.*;
import java.awt.*;
import wwe.modules.gui.*;
import org.lwjgl.opengl.*;
import java.math.*;
import net.minecraft.client.gui.*;

public class uQsp extends kUUQ
{
    public boolean vwqN;
    public NumberValue YSpD;
    public ohwW SvfS;
    public int jXta;
    public int EISd;
    public int LFEY;
    public boolean banq;
    private double IPEW;
    
    public uQsp(final NumberValue ySpD, final ohwW svfS, final int jXta) {
        super();
        this.banq = false;
        this.YSpD = ySpD;
        this.SvfS = svfS;
        this.EISd = svfS.rhHA.kVnf + svfS.rhHA.dedb;
        this.LFEY = svfS.rhHA.kVnf + svfS.yQtC;
        this.jXta = jXta;
    }
    
    @Override
    public void JyWl(final int n, final int n2, final float n3) {
        final float n4 = (float)this.SvfS.rhHA.kVnf;
        final float n5 = (float)(this.SvfS.rhHA.CQIZ + this.jXta);
        final float n6 = (float)(this.SvfS.rhHA.kVnf + this.SvfS.rhHA.dedb);
        final float n7 = (float)(this.SvfS.rhHA.CQIZ + this.jXta + 12);
        int n8;
        if (this.vwqN) {
            n8 = new Color(15, 15, 15, 245).getRGB();
            if (uQsp.\u200b\u200b\u2000\u2006) {
                throw null;
            }
        }
        else {
            n8 = new Color(15, 15, 15, 215).getRGB();
        }
        gnNa.wQYt(n4, n5, n6, n7, n8);
        final int n9 = (int)(this.YSpD.OnGi() / this.YSpD.aYiG * this.SvfS.rhHA.dedb);
        final float n10 = (float)this.SvfS.rhHA.kVnf;
        final float n11 = (float)(this.SvfS.rhHA.CQIZ + this.jXta);
        final double n12 = this.SvfS.rhHA.kVnf;
        double ipew;
        if (this.YSpD.OnGi() > this.YSpD.aYiG) {
            ipew = this.SvfS.rhHA.dedb;
            if (uQsp.\u200b\u200b\u2000\u2006) {
                throw null;
            }
        }
        else if (this.YSpD.OnGi() < this.YSpD.LxxX) {
            ipew = 0.0;
            if (uQsp.\u200b\u200b\u2000\u2006) {
                throw null;
            }
        }
        else {
            ipew = this.IPEW;
        }
        final float n13 = (float)(int)(n12 + ipew);
        final float n14 = (float)(this.SvfS.rhHA.CQIZ + this.jXta + 12);
        int n15;
        if (this.vwqN) {
            n15 = new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue()).getRGB();
            if (uQsp.\u200b\u200b\u2000\u2006) {
                throw null;
            }
        }
        else {
            n15 = new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue()).darker().getRGB();
        }
        gnNa.wQYt(n10, n11, n13, n14, n15);
        gnNa.wQYt((float)this.SvfS.rhHA.kVnf, (float)(this.SvfS.rhHA.CQIZ + this.jXta), (float)this.SvfS.rhHA.kVnf, (float)(this.SvfS.rhHA.CQIZ + this.jXta + 12), -15658735);
        GL11.glPushMatrix();
        GL11.glScalef(0.75f, 0.75f, 0.75f);
        XybC.eTim(this.YSpD.VQBN + " : " + Math.round(this.YSpD.OnGi() * 100.0) / 100.0, (this.SvfS.rhHA.kVnf + 7) * 1.33f, (this.SvfS.rhHA.CQIZ + this.jXta) * 1.33f + 5.0f, -1);
        GL11.glPopMatrix();
    }
    
    @Override
    public void QdjS(final int n, final int n2) {
        boolean vwqN;
        if (this.jwHt(n, n2) || this.Usxi(n, n2)) {
            vwqN = true;
            if (uQsp.\u200b\u200b\u2000\u2006) {
                throw null;
            }
        }
        else {
            vwqN = false;
        }
        this.vwqN = vwqN;
        this.LFEY = this.SvfS.rhHA.CQIZ + this.jXta;
        this.EISd = this.SvfS.rhHA.kVnf;
        final double n3 = Math.min(88, Math.max(0, n - this.EISd));
        final double lxxX = this.YSpD.LxxX;
        final double aYiG = this.YSpD.aYiG;
        this.IPEW = 88.0 * (this.YSpD.OnGi() - lxxX) / (aYiG - lxxX);
        if (this.banq) {
            if (n3 == 0.0) {
                this.YSpD.NdCc(this.YSpD.LxxX);
                if (uQsp.\u200b\u200b\u2000\u2006) {
                    throw null;
                }
            }
            else {
                this.YSpD.NdCc(JDXJ(n3 / 88.0 * (aYiG - lxxX) + lxxX, 2));
            }
        }
    }
    
    private static double JDXJ(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    @Override
    public void rVom(final int n, final int n2, final int n3) {
        if (this.jwHt(n, n2) && this.SvfS.fzNA) {
            this.banq = true;
        }
        if (this.Usxi(n, n2) && this.SvfS.fzNA) {
            this.banq = true;
        }
    }
    
    @Override
    public void XrGT(final int n, final int n2, final int n3) {
        this.banq = false;
    }
    
    public boolean jwHt(final int n, final int n2) {
        return n > this.EISd - 2 && n < this.EISd + (this.SvfS.rhHA.dedb / 2 + 1) && n2 > this.LFEY - 1 && n2 < this.LFEY + 12;
    }
    
    public boolean Usxi(final int n, final int n2) {
        return n > this.EISd + this.SvfS.rhHA.dedb / 2 && n < this.EISd + this.SvfS.rhHA.dedb && n2 > this.LFEY - 1 && n2 < this.LFEY + 12;
    }
    
    @Override
    public void Lrih(final int n, final int n2, final float n3, final ScaledResolution scaledResolution) {
        if (this.vwqN) {
            gnNa.dYaT(2.0f, (float)(scaledResolution.getScaledHeight() - 13), (float)(XybC.wKEb().getStringWidth("  " + this.YSpD.qYCS) + 2), (float)(scaledResolution.getScaledHeight() + 19), 1.0f, new Color(15, 15, 15).getRGB(), -1);
            XybC.eTim(this.YSpD.qYCS, 6.0f, (float)(scaledResolution.getScaledHeight() - 10), -1);
        }
    }
}
