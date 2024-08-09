package ez.h.utils.cosmetic.impl;

import org.lwjgl.opengl.*;
import ez.h.features.visual.*;

public class DragonWing extends bqf
{
    private static brs mr1;
    private static brs mr2;
    
    private static float interpolate(final float n, final float n2, final float n3) {
        float n4 = (n + (n2 - n) * n3) % 360.0f;
        if (n4 < 0.0f) {
            n4 += 360.0f;
        }
        return n4;
    }
    
    private static float calRotateHeadNowX(final float n, final float n2, final float n3, final aed aed) {
        if (!aed.equals((Object)bib.z().h) && ((0.0f > n && 0.0f < n2) || (0.0f < n && 0.0f > n2))) {
            return n2;
        }
        return (n + (n2 - n) * n3) % 360.0f;
    }
    
    public DragonWing() {
        this.a("wing.bone", 0, 0);
        this.a("wing.skin", -10, 8);
        this.a("wingtip.bone", 0, 5);
        this.a("wingtip.skin", -10, 0x8D ^ 0x9F);
        (DragonWing.mr1 = new brs((bqf)this, "wing")).b(0x87 ^ 0x99, 0x18 ^ 0x6);
        DragonWing.mr1.a(-2.0f, 0.0f, 0.0f);
        DragonWing.mr1.a("bone", -10.0f, -1.0f, -1.0f, 0xB1 ^ 0xBB, 2, 2);
        DragonWing.mr1.a("skin", -10.0f, 0.0f, 0.5f, 0x49 ^ 0x43, 0, 0x31 ^ 0x3B);
        (DragonWing.mr2 = new brs((bqf)this, "wingtip")).b(0x9 ^ 0x17, 0x60 ^ 0x7E);
        DragonWing.mr2.a(-10.0f, 0.0f, 0.0f);
        DragonWing.mr2.a("bone", -10.0f, -0.5f, -0.5f, 0x88 ^ 0x82, 1, 1);
        DragonWing.mr2.a("skin", -10.0f, 0.0f, 0.5f, 0x61 ^ 0x6B, 0, 0x57 ^ 0x5D);
        DragonWing.mr1.a(DragonWing.mr2);
    }
    
    private static float calRotateHeadNowY(final float n, final float n2, final float n3) {
        return (n + (n2 - n) * n3) % 180.0f;
    }
    
    public static void render(final aed aed, final float n) {
        final double n2 = interpolate(aed.aO, aed.aN, n);
        GL11.glPushMatrix();
        GL11.glScaled((double)Wings.scale.getNum(), (double)Wings.scale.getNum(), (double)Wings.scale.getNum());
        GL11.glRotated(Math.toRadians(n2) - 4.0, 0.0, 1.0, 0.0);
        GL11.glTranslated(0.0, 0.1, 0.095);
        if (aed.aU()) {
            GL11.glTranslated(0.0, 0.2, 0.05);
        }
        if (!((aip)aed.bv.b.get(2)).isEmpty()) {
            GL11.glTranslated(0.0, 0.0, 0.05);
        }
        nf nf;
        if (Wings.mode.isMode("White")) {
            nf = new nf("wild/White.png");
        }
        else {
            nf = new nf("wild/Dark.png");
        }
        bib.z().N().a(nf);
        for (int i = 0; i < 2; ++i) {
            final float n3 = System.currentTimeMillis() % 1000L / 1000.0f * 3.1415927f * 2.0f;
            DragonWing.mr1.f = (float)(Math.toRadians(-80.0) - Math.cos(n3) * 0.20000000298023224);
            DragonWing.mr1.g = (float)(Math.toRadians(30.0) + Math.sin(n3) * 0.4000000059604645);
            DragonWing.mr1.h = (float)Math.toRadians(20.0);
            DragonWing.mr2.h = (float)(-(Math.sin(n3 + 2.0f) + 0.5) * 0.75);
            DragonWing.mr1.a(0.0625f);
            GL11.glScalef(-1.0f, 1.0f, 1.0f);
        }
        GL11.glCullFace(278 + 580 - 111 + 282);
        GL11.glPopMatrix();
    }
    
    private static float calRotateBodyNowX(final float n, final float n2, final float n3) {
        return (n + (n2 - n) * n3) % 360.0f;
    }
}
