package wwe;

import java.io.*;
import java.awt.*;
import wwe.utils.*;

public class Deoo
{
    public QkhT BFKa;
    public File avoi;
    public int rjpj;
    public int kWmF;
    public int iSaV;
    public int lyAE;
    public boolean VUFK;
    
    public Deoo(final QkhT bfKa, final File avoi, final int rjpj, final int kWmF, final int lyAE, final int iSaV) {
        super();
        this.BFKa = bfKa;
        this.avoi = avoi;
        this.rjpj = rjpj;
        this.kWmF = kWmF;
        this.lyAE = lyAE;
        this.iSaV = iSaV;
    }
    
    public void mmCA(final int n, final int n2, final float n3) {
        boolean vufk;
        if (n >= this.rjpj && n2 >= this.kWmF + this.BFKa.VgRj && n < this.rjpj + this.lyAE && n2 < this.kWmF + this.iSaV + this.BFKa.VgRj && n2 < this.BFKa.height - 30 && n2 > 40) {
            vufk = true;
            if (Deoo.\u200e\u2000\u200f\u2007\u2006\u2004\u2007) {
                throw null;
            }
        }
        else {
            vufk = false;
        }
        this.VUFK = vufk;
        final QkhT bfKa = this.BFKa;
        final int rjpj = this.rjpj;
        final int n4 = this.kWmF + this.BFKa.VgRj;
        final int n5 = this.rjpj + this.lyAE;
        final int n6 = this.kWmF + this.iSaV + this.BFKa.VgRj;
        int rgb;
        if (this.BFKa.wtTS == this) {
            rgb = new Color(-1873784752).darker().getRGB();
            if (Deoo.\u200e\u2000\u200f\u2007\u2006\u2004\u2007) {
                throw null;
            }
        }
        else {
            rgb = -1873784752;
        }
        QkhT.drawRect(rjpj, n4, n5, n6, rgb);
        Wrapper.fontRenderer.drawStringWithShadow(this.avoi.getName().substring(0, this.avoi.getName().lastIndexOf(".")), (float)(this.rjpj + (this.lyAE / 2 - Wrapper.fontRenderer.getStringWidth(this.avoi.getName().substring(0, this.avoi.getName().lastIndexOf("."))) / 2)), (float)(this.kWmF + this.BFKa.VgRj + 1), -1);
    }
}
