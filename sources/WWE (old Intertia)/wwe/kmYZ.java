package wwe;

import wwe.utils.*;
import java.util.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import wwe.modules.gui.*;
import net.minecraft.client.gui.*;

public class kmYZ extends kUUQ
{
    public boolean vwqN;
    public BBeR BAIk;
    public ohwW SvfS;
    public int jXta;
    public int EISd;
    public int LFEY;
    public ArrayList<kUUQ> CYJH;
    
    public kmYZ(final BBeR baIk, final ohwW svfS, final int jXta) {
        super();
        this.CYJH = new ArrayList<kUUQ>();
        this.BAIk = baIk;
        this.SvfS = svfS;
        this.EISd = svfS.rhHA.kVnf + svfS.rhHA.dedb;
        this.LFEY = svfS.rhHA.CQIZ + svfS.yQtC;
        this.jXta = jXta;
        int n = jXta + 12;
        for (final Map.Entry<String, IcRI> entry : this.BAIk.DxzD.entrySet()) {
            if (entry.getValue().dTln() && entry.getValue().geCg) {
                this.CYJH.add(new nzoK(entry.getValue(), this.SvfS, n));
                n += 12;
            }
            if (entry.getValue().MATs() && entry.getValue().geCg) {
                this.CYJH.add(new uQsp(entry.getValue(), this.SvfS, n));
                n += 12;
            }
            if (entry.getValue().wnpo() && entry.getValue().geCg) {
                this.CYJH.add(new RwuA(entry.getValue(), this.SvfS, n));
                n += 12;
            }
        }
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
            if (kmYZ.\u200b\u2008) {
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
            if (kmYZ.\u200b\u2008) {
                throw null;
            }
        }
        else {
            n13 = new Color(15, 15, 15, 215).getRGB();
        }
        gnNa.wQYt(n9, n10, n11, n12, n13);
        GL11.glPushMatrix();
        GL11.glScalef(0.75f, 0.75f, 0.75f);
        XybC.eTim(this.BAIk.VQBN, (this.SvfS.rhHA.kVnf + 10 + 4) * 1.33f + 5.0f, (this.SvfS.rhHA.CQIZ + this.jXta + 2) * 1.33f + 2.0f, -1);
        GL11.glPopMatrix();
        gnNa.Pgxp((float)(this.SvfS.rhHA.kVnf + 8), (float)(this.SvfS.rhHA.CQIZ + this.jXta + 6), 3.0f, 1.0f, -1);
        if (this.BAIk.OnGi()) {
            gnNa.ILFo((float)(this.SvfS.rhHA.kVnf + 8), (float)(this.SvfS.rhHA.CQIZ + this.jXta + 6), 3.0f, new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue()).getRGB());
        }
        if (this.BAIk.OnGi()) {
            final Iterator<kUUQ> iterator = this.CYJH.iterator();
            while (iterator.hasNext()) {
                iterator.next().JyWl(n, n2, n3);
                if (kmYZ.\u200b\u2008) {
                    throw null;
                }
            }
        }
    }
    
    @Override
    public void QdjS(final int n, final int n2) {
        this.vwqN = this.aXbq(n, n2);
        this.LFEY = this.SvfS.rhHA.CQIZ + this.jXta;
        this.EISd = this.SvfS.rhHA.kVnf;
        if (this.BAIk.OnGi()) {
            final Iterator<kUUQ> iterator = this.CYJH.iterator();
            while (iterator.hasNext()) {
                iterator.next().QdjS(n, n2);
                if (kmYZ.\u200b\u2008) {
                    throw null;
                }
            }
        }
    }
    
    @Override
    public void rVom(final int n, final int n2, final int n3) {
        if (this.aXbq(n, n2) && this.SvfS.fzNA) {
            final BBeR baIk = this.BAIk;
            boolean b;
            if (!this.BAIk.OnGi()) {
                b = true;
                if (kmYZ.\u200b\u2008) {
                    throw null;
                }
            }
            else {
                b = false;
            }
            baIk.NdCc(b);
        }
        if (this.BAIk.OnGi()) {
            final Iterator<kUUQ> iterator = this.CYJH.iterator();
            while (iterator.hasNext()) {
                iterator.next().rVom(n, n2, n3);
                if (kmYZ.\u200b\u2008) {
                    throw null;
                }
            }
        }
    }
    
    @Override
    public void XrGT(final int n, final int n2, final int n3) {
        if (this.BAIk.OnGi()) {
            final Iterator<kUUQ> iterator = this.CYJH.iterator();
            while (iterator.hasNext()) {
                iterator.next().XrGT(n, n2, n3);
                if (kmYZ.\u200b\u2008) {
                    throw null;
                }
            }
        }
    }
    
    public boolean aXbq(final int n, final int n2) {
        return n > this.EISd && n < this.EISd + 88 && n2 > this.LFEY - 1 && n2 < this.LFEY + 12;
    }
    
    @Override
    public void Lrih(final int n, final int n2, final float n3, final ScaledResolution scaledResolution) {
        if (this.vwqN) {
            gnNa.dYaT(2.0f, (float)(scaledResolution.getScaledHeight() - 13), (float)(XybC.wKEb().getStringWidth("  " + this.BAIk.qYCS) + 2), (float)(scaledResolution.getScaledHeight() + 19), 1.0f, new Color(15, 15, 15).getRGB(), -1);
            XybC.eTim(this.BAIk.qYCS, 6.0f, (float)(scaledResolution.getScaledHeight() - 10), -1);
        }
    }
}
