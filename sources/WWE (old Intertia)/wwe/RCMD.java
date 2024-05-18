package wwe;

import java.awt.*;

public class RCMD
{
    public String VGnY;
    public String cpTz;
    public DlwW VhuH;
    public int zlwQ;
    public int elYg;
    public int Kbjh;
    public int RzMj;
    public int IzwG;
    public int YzNk;
    
    public RCMD(final String vGnY, final String cpTz, final DlwW vhuH, final int n, final int n2, final int n3, final flSi flSi) {
        super();
        this.VGnY = vGnY;
        this.cpTz = cpTz;
        this.VhuH = vhuH;
        this.zlwQ = n;
        this.elYg = n2;
        this.Kbjh = n3;
        this.RzMj = n;
        this.IzwG = n2;
        this.YzNk = n3;
        flSi.JNkz.add(this);
    }
    
    public Color JqMr() {
        if (this.zlwQ > 255 || this.elYg > 255 || this.Kbjh > 255) {
            return new Color(1, 1, 1);
        }
        Color color;
        if (new Color(this.zlwQ, this.elYg, this.Kbjh) == null) {
            color = new Color(this.RzMj, this.IzwG, this.YzNk);
            if (RCMD.\u2007\u200e\u2002\u2005) {
                throw null;
            }
        }
        else {
            color = new Color(this.zlwQ, this.elYg, this.Kbjh);
        }
        return color;
    }
}
