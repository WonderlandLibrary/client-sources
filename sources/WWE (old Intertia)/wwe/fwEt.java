package wwe;

import java.awt.*;
import wwe.utils.*;
import net.minecraft.client.gui.*;
import java.io.*;
import java.math.*;

public class fwEt
{
    public boolean ruZA;
    public int aXth;
    public int VjMq;
    public boolean uWNh;
    public double lCzq;
    public oBqx xGMZ;
    public int FWSB;
    private int zMeL;
    
    public fwEt(final oBqx xgmz, final int zMeL, final int aXth, final int vjMq) {
        super();
        this.xGMZ = xgmz;
        this.aXth = aXth;
        this.VjMq = vjMq;
        this.FWSB = Integer.parseInt(xgmz.kEZV.getText());
        this.zMeL = zMeL;
    }
    
    public void QYaQ(final int n, final int n2, final float n3) {
        int fwsb;
        if (this.zMeL == 0) {
            if (this.xGMZ.kEZV.getText().isEmpty()) {
                fwsb = 0;
                if (fwEt.\u2004\u200e) {
                    throw null;
                }
            }
            else {
                fwsb = Integer.parseInt(this.xGMZ.kEZV.getText());
                if (fwEt.\u2004\u200e) {
                    throw null;
                }
            }
        }
        else if (this.zMeL == 1) {
            if (this.xGMZ.ZxjL.getText().isEmpty()) {
                fwsb = 0;
                if (fwEt.\u2004\u200e) {
                    throw null;
                }
            }
            else {
                fwsb = Integer.parseInt(this.xGMZ.ZxjL.getText());
                if (fwEt.\u2004\u200e) {
                    throw null;
                }
            }
        }
        else if (this.zMeL == 2) {
            if (this.xGMZ.namX.getText().isEmpty()) {
                fwsb = 0;
                if (fwEt.\u2004\u200e) {
                    throw null;
                }
            }
            else {
                fwsb = Integer.parseInt(this.xGMZ.namX.getText());
                if (fwEt.\u2004\u200e) {
                    throw null;
                }
            }
        }
        else {
            fwsb = 0;
        }
        this.FWSB = fwsb;
        boolean ruZA;
        if (this.KoCN(n, n2) || this.Hmup(n, n2)) {
            ruZA = true;
            if (fwEt.\u2004\u200e) {
                throw null;
            }
        }
        else {
            ruZA = false;
        }
        this.ruZA = ruZA;
        final double n4 = Math.min(255, Math.max(0, n - this.aXth));
        final double n5 = 0.0;
        final double n6 = 255.0;
        this.lCzq = 255.0 * (this.FWSB - n5) / (n6 - n5);
        if (this.uWNh) {
            if (n4 == 0.0) {
                if (this.zMeL == 0) {
                    this.xGMZ.kEZV.setText("0");
                }
                if (this.zMeL == 1) {
                    this.xGMZ.ZxjL.setText("0");
                }
                if (this.zMeL == 2) {
                    this.xGMZ.namX.setText("0");
                    if (fwEt.\u2004\u200e) {
                        throw null;
                    }
                }
            }
            else {
                final double tkVp = TKVp(n4 / 255.0 * (n6 - n5) + n5, 2);
                if (this.zMeL == 0) {
                    this.xGMZ.kEZV.setText((int)tkVp + "");
                }
                if (this.zMeL == 1) {
                    this.xGMZ.ZxjL.setText((int)tkVp + "");
                }
                if (this.zMeL == 2) {
                    this.xGMZ.namX.setText((int)tkVp + "");
                }
            }
        }
        final int aXth = this.aXth;
        final int vjMq = this.VjMq;
        final int n7 = this.aXth + 255;
        final int n8 = this.VjMq + 12;
        int n9;
        if (this.ruZA) {
            n9 = new Color(15, 15, 15, 245).getRGB();
            if (fwEt.\u2004\u200e) {
                throw null;
            }
        }
        else {
            n9 = new Color(15, 15, 15, 215).getRGB();
        }
        Gui.drawRect(aXth, vjMq, n7, n8, n9);
        final int n10 = this.FWSB / 255 * 255;
        final int aXth2 = this.aXth;
        final int vjMq2 = this.VjMq;
        final double n11 = this.aXth;
        double lCzq;
        if (this.FWSB > 255) {
            lCzq = 255.0;
            if (fwEt.\u2004\u200e) {
                throw null;
            }
        }
        else if (this.FWSB < 0) {
            lCzq = 0.0;
            if (fwEt.\u2004\u200e) {
                throw null;
            }
        }
        else {
            lCzq = this.lCzq;
        }
        final int n12 = (int)(n11 + lCzq);
        final int n13 = this.VjMq + 12;
        int n14;
        if (this.zMeL == 0) {
            n14 = -65536;
            if (fwEt.\u2004\u200e) {
                throw null;
            }
        }
        else if (this.zMeL == 1) {
            n14 = -16711936;
            if (fwEt.\u2004\u200e) {
                throw null;
            }
        }
        else if (this.zMeL == 2) {
            n14 = -16776961;
            if (fwEt.\u2004\u200e) {
                throw null;
            }
        }
        else {
            n14 = -1;
        }
        Gui.drawRect(aXth2, vjMq2, n12, n13, n14);
        Gui.drawRect(this.aXth, this.VjMq + 12, this.aXth, this.VjMq + 12, -15658735);
        final FontRenderer fontRenderer = Wrapper.fontRenderer;
        final StringBuilder sb = new StringBuilder();
        String s;
        if (this.zMeL == 0) {
            s = "Red";
            if (fwEt.\u2004\u200e) {
                throw null;
            }
        }
        else if (this.zMeL == 1) {
            s = "Green";
            if (fwEt.\u2004\u200e) {
                throw null;
            }
        }
        else if (this.zMeL == 2) {
            s = "Blue";
            if (fwEt.\u2004\u200e) {
                throw null;
            }
        }
        else {
            s = "Unknown";
        }
        fontRenderer.drawString(sb.append(s).append(" : ").append(this.FWSB).append("").toString(), this.aXth + 5, this.VjMq + 2, -1);
    }
    
    public void rhRb(final int n, final int n2, final int n3) throws IOException {
        if (this.KoCN(n, n2)) {
            this.uWNh = true;
        }
        if (this.Hmup(n, n2)) {
            this.uWNh = true;
        }
    }
    
    public void dEUV(final int n, final int n2, final int n3) {
        this.uWNh = false;
    }
    
    private static double TKVp(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    public boolean KoCN(final int n, final int n2) {
        return n > this.aXth - 2 && n < this.aXth + 128 && n2 > this.VjMq && n2 < this.VjMq + 12 + 1;
    }
    
    public boolean Hmup(final int n, final int n2) {
        return n > this.aXth + 127 && n < this.aXth + 255 && n2 > this.VjMq && n2 < this.VjMq + 12 + 1;
    }
}
