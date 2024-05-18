package wwe;

import java.awt.*;
import org.lwjgl.opengl.*;
import wwe.utils.*;

public class BiVj extends kUUQ
{
    public boolean vwqN;
    public ohwW SvfS;
    public int jXta;
    public int EISd;
    public int LFEY;
    public qMIe NjOR;
    
    public BiVj(final ohwW svfS, final qMIe njOR, final int jXta) {
        super();
        this.SvfS = svfS;
        this.NjOR = njOR;
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
            if (BiVj.\u200f\u2009\u200c\u2006\u2003) {
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
            if (BiVj.\u200f\u2009\u200c\u2006\u2003) {
                throw null;
            }
        }
        else {
            n13 = new Color(15, 15, 15, 215).getRGB();
        }
        gnNa.wQYt(n9, n10, n11, n12, n13);
        GL11.glPushMatrix();
        GL11.glScalef(0.75f, 0.75f, 0.75f);
        XybC.eTim("Visible: " + this.NjOR.sUHq(), (this.SvfS.rhHA.kVnf + 7) * 1.33f, (this.SvfS.rhHA.CQIZ + this.jXta) * 1.33f + 5.0f, -1);
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
            this.NjOR.FSRf(this.NjOR.sUHq());
            Wrapper.ypJK().qmiM();
        }
    }
    
    public boolean aXbq(final int n, final int n2) {
        return n > this.EISd && n < this.EISd + 88 && n2 > this.LFEY - 1 && n2 < this.LFEY + 12;
    }
}
