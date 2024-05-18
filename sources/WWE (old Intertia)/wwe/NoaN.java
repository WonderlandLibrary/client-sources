package wwe;

import wwe.modules.gui.*;
import java.awt.*;

public class NoaN extends kUUQ
{
    public String smkO;
    public XdhZ TUnV;
    public int jXta;
    private boolean Pynf;
    
    public NoaN(final String s, final XdhZ tUnV, final int jXta) {
        super();
        this.smkO = s;
        this.TUnV = tUnV;
        this.jXta = jXta;
        this.lALN = s;
    }
    
    public void SOOn() {
        if (this.TUnV.zzJD + this.jXta >= this.TUnV.zzJD + this.TUnV.ooJc + 60 && this.TUnV.sILe != null && this.TUnV.sILe == this) {
            this.TUnV.sILe = null;
        }
        if (this.TUnV.zzJD + this.jXta + 12 <= this.TUnV.zzJD + this.TUnV.ooJc && this.TUnV.sILe != null && this.TUnV.sILe == this) {
            this.TUnV.sILe = null;
        }
        final float n = (float)this.TUnV.OsiI;
        final float n2 = (float)(this.TUnV.zzJD + this.jXta);
        final float n3 = (float)(this.TUnV.OsiI + this.TUnV.zipv);
        final float n4 = (float)(this.TUnV.zzJD + this.jXta + 12);
        int n5;
        if (this.TUnV.sILe == this) {
            n5 = new Color(vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).red.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).green.OnGi().intValue(), vlwv.hNxW.qHPF((Class<Gui>)PAPd.class).blue.OnGi().intValue()).darker().getRGB();
            if (NoaN.\u2005) {
                throw null;
            }
        }
        else if (!this.Pynf) {
            n5 = new Color(15, 15, 15, 215).getRGB();
            if (NoaN.\u2005) {
                throw null;
            }
        }
        else {
            n5 = new Color(15, 15, 15, 245).getRGB();
        }
        gnNa.wQYt(n, n2, n3, n4, n5);
        XybC.eTim(this.smkO, (float)(this.TUnV.OsiI + 3), (float)(this.TUnV.zzJD + this.jXta + 2), -1);
    }
    
    @Override
    public void QdjS(final int n, final int n2) {
        this.Pynf = this.aXbq(n, n2);
    }
    
    @Override
    public void rVom(final int n, final int n2, final int n3) {
        if (this.Pynf) {
            if (this.TUnV.sILe == this) {
                this.TUnV.sILe = null;
                if (NoaN.\u2005) {
                    throw null;
                }
            }
            else {
                this.TUnV.sILe = this;
            }
        }
    }
    
    public boolean aXbq(final int n, final int n2) {
        return n > this.TUnV.OsiI && n < this.TUnV.OsiI + this.TUnV.zipv && n2 > this.TUnV.zzJD + this.jXta - 1 && n2 < this.TUnV.zzJD + 12 + this.jXta && this.TUnV.jwvM;
    }
}
