package wwe;

import java.awt.image.*;
import net.minecraft.client.renderer.texture.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class yfok
{
    public int duIW;
    public int jcBM;
    private int gZhT;
    private final wwe/ohSY[] zrQr;
    private final Font xBKh;
    private boolean oFOR;
    private int RtnK;
    private int ZvdZ;
    
    public yfok(final Font xbKh, final boolean ofor, final int zvdZ) {
        super();
        this.duIW = 1024;
        this.jcBM = 1024;
        this.zrQr = new wwe/ohSY[256];
        this.RtnK = -1;
        this.ZvdZ = 8;
        this.xBKh = xbKh;
        this.oFOR = ofor;
        this.ZvdZ = zvdZ;
        this.mdVC(ofor);
    }
    
    public yfok(final Font xbKh, final boolean ofor) {
        super();
        this.duIW = 1024;
        this.jcBM = 1024;
        this.zrQr = new wwe/ohSY[256];
        this.RtnK = -1;
        this.ZvdZ = 8;
        this.xBKh = xbKh;
        this.oFOR = ofor;
        this.ZvdZ = 8;
        this.mdVC(ofor);
    }
    
    private void mdVC(final boolean b) {
        if (this.xBKh.getSize() <= 15) {
            this.duIW = 256;
            this.jcBM = 256;
        }
        if (this.xBKh.getSize() <= 43) {
            this.duIW = 512;
            this.jcBM = 512;
            if (yfok.\u2005\u2003\u200b\u200a) {
                throw null;
            }
        }
        else if (this.xBKh.getSize() <= 91) {
            this.duIW = 1024;
            this.jcBM = 1024;
            if (yfok.\u2005\u2003\u200b\u200a) {
                throw null;
            }
        }
        else {
            this.duIW = 2048;
            this.jcBM = 2048;
        }
        final BufferedImage bufferedImage = new BufferedImage(this.duIW, this.jcBM, 2);
        final Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setFont(this.xBKh);
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, this.duIW, this.jcBM);
        graphics2D.setColor(Color.white);
        int qkaV = 0;
        int bXnb = 0;
        int hkwu = 0;
        int i = 0;
        while (i < 256) {
            final BufferedImage fmBz = this.FmBz((char)i, b);
            final wwe/ohSY wwe/ohSY = new wwe/ohSY(null);
            wwe/ohSY.luLZ = fmBz.getWidth();
            wwe/ohSY.QkaV = fmBz.getHeight();
            if (bXnb + wwe/ohSY.luLZ >= this.duIW) {
                bXnb = 0;
                hkwu += qkaV;
                qkaV = 0;
            }
            wwe/ohSY.bXnb = bXnb;
            wwe/ohSY.hkwu = hkwu;
            if (wwe/ohSY.QkaV > this.RtnK) {
                this.RtnK = wwe/ohSY.QkaV;
            }
            if (wwe/ohSY.QkaV > qkaV) {
                qkaV = wwe/ohSY.QkaV;
            }
            this.zrQr[i] = wwe/ohSY;
            graphics2D.drawImage(fmBz, bXnb, hkwu, null);
            bXnb += wwe/ohSY.luLZ;
            ++i;
            if (yfok.\u2005\u2003\u200b\u200a) {
                throw null;
            }
        }
        try {
            this.gZhT = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), bufferedImage, true, true);
            if (yfok.\u2005\u2003\u200b\u200a) {
                throw null;
            }
        }
        catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
    
    private BufferedImage FmBz(final char c, final boolean b) {
        final Graphics2D graphics2D = (Graphics2D)new BufferedImage(1, 1, 2).getGraphics();
        if (b) {
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            if (yfok.\u2005\u2003\u200b\u200a) {
                throw null;
            }
        }
        else {
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
        graphics2D.setFont(this.xBKh);
        final FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n = fontMetrics.charWidth(c) + 8;
        if (n <= 0) {
            n = 7;
        }
        int size = fontMetrics.getHeight() + 3;
        if (size <= 0) {
            size = this.xBKh.getSize();
        }
        final BufferedImage bufferedImage = new BufferedImage(n, size, 2);
        final Graphics2D graphics2D2 = (Graphics2D)bufferedImage.getGraphics();
        if (b) {
            graphics2D2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            if (yfok.\u2005\u2003\u200b\u200a) {
                throw null;
            }
        }
        else {
            graphics2D2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
        graphics2D2.setFont(this.xBKh);
        graphics2D2.setColor(Color.WHITE);
        graphics2D2.drawString(String.valueOf(c), 3, 1 + fontMetrics.getAscent());
        return bufferedImage;
    }
    
    public void nhAI(final char c, final float n, final float n2) throws ArrayIndexOutOfBoundsException {
        try {
            this.vyUQ(n, n2, (float)this.zrQr[c].luLZ, (float)this.zrQr[c].QkaV, (float)this.zrQr[c].bXnb, (float)this.zrQr[c].hkwu, (float)this.zrQr[c].luLZ, (float)this.zrQr[c].QkaV);
            if (yfok.\u2005\u2003\u200b\u200a) {
                throw null;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void vyUQ(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        final float n9 = n5 / this.duIW;
        final float n10 = n6 / this.jcBM;
        final float n11 = n7 / this.duIW;
        final float n12 = n8 / this.jcBM;
        GL11.glBegin(4);
        GL11.glTexCoord2f(n9 + n11, n10);
        GL11.glVertex2d((double)(n + n3), (double)n2);
        GL11.glTexCoord2f(n9, n10);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glTexCoord2f(n9, n10 + n12);
        GL11.glVertex2d((double)n, (double)(n2 + n4));
        GL11.glTexCoord2f(n9, n10 + n12);
        GL11.glVertex2d((double)n, (double)(n2 + n4));
        GL11.glTexCoord2f(n9 + n11, n10 + n12);
        GL11.glVertex2d((double)(n + n3), (double)(n2 + n4));
        GL11.glTexCoord2f(n9 + n11, n10);
        GL11.glVertex2d((double)(n + n3), (double)n2);
        GL11.glEnd();
    }
    
    public void RhUE(final String s, double n, double n2, final Color color, final boolean b) {
        n *= 2.0;
        n2 = n2 * 2.0 - 2.0;
        GL11.glPushMatrix();
        GL11.glScaled(0.25, 0.25, 0.25);
        GlStateManager.bindTexture(this.gZhT);
        final int length = s.length();
        int i = 0;
        while (i < length) {
            final char char1 = s.charAt(i);
            if (char1 < this.zrQr.length && char1 >= '\0') {
                if (b) {
                    this.CzpU(this.rpFb((color.getRGB() & 0xFCFCFC) >> 2 | (color.getRGB() & 0xFF000000)));
                    this.nhAI(char1, (float)n + 3.0f, (float)n2 + 3.0f);
                }
                this.CzpU(color);
                this.nhAI(char1, (float)n, (float)n2);
                n += this.zrQr[char1].luLZ - this.ZvdZ;
            }
            ++i;
            if (yfok.\u2005\u2003\u200b\u200a) {
                throw null;
            }
        }
        GL11.glPopMatrix();
    }
    
    public void CzpU(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public int IJUi(final String s) {
        int n = 1;
        final char[] array = s.toCharArray();
        final int length = array.length;
        int i = 0;
        while (i < length) {
            if (array[i] == '\n') {
                ++n;
            }
            ++i;
            if (yfok.\u2005\u2003\u200b\u200a) {
                throw null;
            }
        }
        return (this.RtnK - this.ZvdZ) / 2 * n;
    }
    
    public int McJQ() {
        return (this.RtnK - this.ZvdZ) / 2;
    }
    
    public int JuED(final String s) {
        int n = 0;
        final char[] array = s.toCharArray();
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final char c = array[i];
            if (c < this.zrQr.length && c >= '\0') {
                n += this.zrQr[c].luLZ - this.ZvdZ;
            }
            ++i;
            if (yfok.\u2005\u2003\u200b\u200a) {
                throw null;
            }
        }
        return n / 2;
    }
    
    public boolean xTYq() {
        return this.oFOR;
    }
    
    public void gVYg(final boolean ofor) {
        if (this.oFOR != ofor) {
            this.mdVC(this.oFOR = ofor);
        }
    }
    
    public Font HtRp() {
        return this.xBKh;
    }
    
    public Color rpFb(final int n) {
        return new Color((n >> 16 & 0xFF) / 255.0f, (n >> 8 & 0xFF) / 255.0f, (n & 0xFF) / 255.0f, (n >> 24 & 0xFF) / 255.0f);
    }
    
    private class wwe/ohSY
    {
        public int luLZ;
        public int QkaV;
        public int bXnb;
        public int hkwu;
        final yfok Uvbh;
        
        private wwe/ohSY(final yfok uvbh) {
            this.Uvbh = uvbh;
            super();
        }
        
        wwe/ohSY(final yfok yfok, final OEuN oEuN) {
            this(yfok);
        }
    }
}
