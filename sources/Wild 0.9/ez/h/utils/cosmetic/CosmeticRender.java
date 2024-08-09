package ez.h.utils.cosmetic;

import ez.h.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;

public class CosmeticRender implements ccg<bua>
{
    private final cct playerRenderer;
    
    public CosmeticRender(final cct playerRenderer) {
        this.playerRenderer = playerRenderer;
    }
    
    public void doRenderLayer(final bua bua, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!Main.getFeatureByName("Wings").isEnabled()) {
            return;
        }
        if (!bua.a() || bua.aX()) {
            return;
        }
        if (bua == bib.z().h) {
            Cosmetic.renderAccessory(new String[] { "Dragon_wing" }, (aed)bua, n3);
        }
    }
    
    public static void bindTexture(final String s, final String s2) {
        final but but = (but)new but() {
            final buz ibd = new buz();
            
            public void a() {
            }
            
            public BufferedImage a(final BufferedImage bufferedImage) {
                return parseCape(bufferedImage);
            }
        };
        final nf nf = new nf(s2);
        final cdr n = bib.z().N();
        n.b(nf);
        n.a(nf, (cds)new cdh((File)null, s, (nf)null, (but)but));
    }
    
    private static BufferedImage parseCape(final BufferedImage bufferedImage) {
        int n = 0xD8 ^ 0x98;
        int n2 = 0x71 ^ 0x51;
        for (int width = bufferedImage.getWidth(), height = bufferedImage.getHeight(); n < width || n2 < height; n *= 2, n2 *= 2) {}
        final BufferedImage bufferedImage2 = new BufferedImage(n, n2, 2);
        final Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        return bufferedImage2;
    }
    
    public boolean a() {
        return false;
    }
}
