package wwe;

import java.awt.*;
import wwe.utils.*;

public class XOnZ
{
    public faUw CPIf;
    public String NIFv;
    public int Fsmm;
    public int xuVN;
    public int dzqs;
    public int qpSj;
    public boolean Fpdu;
    
    public XOnZ(final faUw cpIf, final String niFv, final int fsmm, final int xuVN, final int qpSj, final int dzqs) {
        super();
        this.CPIf = cpIf;
        this.NIFv = niFv;
        this.Fsmm = fsmm;
        this.xuVN = xuVN;
        this.qpSj = qpSj;
        this.dzqs = dzqs;
    }
    
    public void SLyu(final int n, final int n2, final float n3) {
        boolean fpdu;
        if (n >= this.Fsmm && n2 >= this.xuVN + this.CPIf.rvwt && n < this.Fsmm + this.qpSj && n2 < this.xuVN + this.dzqs + this.CPIf.rvwt && n2 < this.CPIf.height - 30 && n2 > 40) {
            fpdu = true;
            if (XOnZ.\u2000\u2004) {
                throw null;
            }
        }
        else {
            fpdu = false;
        }
        this.Fpdu = fpdu;
        final faUw cpIf = this.CPIf;
        final int fsmm = this.Fsmm;
        final int n4 = this.xuVN + this.CPIf.rvwt;
        final int n5 = this.Fsmm + this.qpSj;
        final int n6 = this.xuVN + this.dzqs + this.CPIf.rvwt;
        int rgb;
        if (this.CPIf.LPyl == this) {
            rgb = new Color(-1873784752).darker().getRGB();
            if (XOnZ.\u2000\u2004) {
                throw null;
            }
        }
        else {
            rgb = -1873784752;
        }
        faUw.drawRect(fsmm, n4, n5, n6, rgb);
        Wrapper.fontRenderer.drawStringWithShadow(this.NIFv, (float)(this.Fsmm + (this.qpSj / 2 - Wrapper.fontRenderer.getStringWidth(this.NIFv) / 2)), (float)(this.xuVN + this.CPIf.rvwt + 1), -1);
    }
}
