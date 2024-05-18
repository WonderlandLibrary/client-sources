package wwe;

import org.lwjgl.opengl.*;
import wwe.utils.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import java.io.*;

public class EylK
{
    public int YuJW;
    public int IaUa;
    public int ogHC;
    public int CuIi;
    public JImF rMNe;
    
    public EylK(final int yuJW, final int iaUa, final int ogHC, final int cuIi, final JImF rmNe) {
        super();
        this.YuJW = yuJW;
        this.IaUa = iaUa;
        this.ogHC = ogHC;
        this.CuIi = cuIi;
        this.rMNe = rmNe;
    }
    
    public void cVOn(final int n, final int n2, final float n3) {
        final JImF rmNe = this.rMNe;
        JImF.drawRect(this.YuJW, this.IaUa, this.YuJW + this.ogHC, this.IaUa + this.ogHC, -1);
        if (this.rMNe.hmkw[this.CuIi] == 'x' || this.rMNe.hmkw[this.CuIi] == 'o') {
            String s;
            if (this.rMNe.hmkw[this.CuIi] == 'x') {
                s = "X";
                if (EylK.\u200a\u200c\u200a\u2001\u200a\u2000\u200f) {
                    throw null;
                }
            }
            else {
                s = "O";
            }
            final String s2 = s;
            GL11.glPushMatrix();
            GL11.glScalef(3.0f, 3.0f, 3.0f);
            final FontRenderer fontRenderer = Wrapper.fontRenderer;
            final String s3 = s2;
            final float n4 = (float)((this.YuJW - Wrapper.fontRenderer.getStringWidth(s2) / 2 * 3 + this.ogHC / 2) / 3);
            final float n5 = (float)((this.IaUa - Wrapper.fontRenderer.getStringWidth(s2) / 2 * 3 + this.ogHC / 2) / 3);
            int rgb;
            if (this.rMNe.hmkw[this.CuIi] == 'x') {
                rgb = -65536;
                if (EylK.\u200a\u200c\u200a\u2001\u200a\u2000\u200f) {
                    throw null;
                }
            }
            else {
                rgb = Color.blue.getRGB();
            }
            fontRenderer.drawStringWithShadow(s3, n4, n5, rgb);
            GL11.glPopMatrix();
        }
    }
    
    public void acXl(final int n, final int n2, final int n3) throws IOException {
        if (this.NgqR(n, n2) && this.rMNe.LTGY(this.CuIi)) {
            vlwv.kXkt.DKWU(new cjZo(this.CuIi));
        }
    }
    
    public boolean NgqR(final int n, final int n2) {
        boolean b;
        if (n > this.YuJW && n < this.YuJW + this.ogHC && n2 > this.IaUa && n2 < this.IaUa + this.ogHC) {
            b = true;
            if (EylK.\u200a\u200c\u200a\u2001\u200a\u2000\u200f) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
}
