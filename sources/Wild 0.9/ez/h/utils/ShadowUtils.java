package ez.h.utils;

import java.io.*;
import org.lwjgl.opengl.*;

public class ShadowUtils
{
    private static bvd frameBuffer;
    private static float lastHeight;
    private static final bib mc;
    private static float lastWidth;
    private static final nf blurDirectory;
    private static float lastStrength;
    private static bvd blackBuffer;
    private static bvd initialFB;
    private static ccy mainShader;
    
    static {
        ShadowUtils.mainShader = null;
        ShadowUtils.lastWidth = 0.0f;
        ShadowUtils.lastHeight = 0.0f;
        ShadowUtils.lastStrength = 0.0f;
        mc = bib.z();
        blurDirectory = new nf("wild/shadow.json");
    }
    
    public static void initBlur(final bit bit, final float lastStrength) {
        final int a = bit.a();
        final int b = bit.b();
        final int e = bit.e();
        if (ShadowUtils.lastWidth != a || ShadowUtils.lastHeight != b || ShadowUtils.initialFB == null || ShadowUtils.frameBuffer == null || ShadowUtils.mainShader == null) {
            (ShadowUtils.initialFB = new bvd(a * e, b * e, true)).a(0.0f, 0.0f, 0.0f, 0.0f);
            ShadowUtils.initialFB.a(3074 + 4546 - 2345 + 4454);
            try {
                ShadowUtils.mainShader = new ccy(ShadowUtils.mc.N(), ShadowUtils.mc.O(), ShadowUtils.initialFB, ShadowUtils.blurDirectory);
            }
            catch (IOException ex) {}
            ShadowUtils.mainShader.a(a * e, b * e);
            ShadowUtils.frameBuffer = ShadowUtils.mainShader.a;
            ShadowUtils.blackBuffer = ShadowUtils.mainShader.a("braindead");
        }
        ShadowUtils.lastWidth = (float)a;
        ShadowUtils.lastHeight = (float)b;
        if (lastStrength != ShadowUtils.lastStrength) {
            ShadowUtils.lastStrength = lastStrength;
            for (int i = 0; i < 2; ++i) {
                ((ccz)ShadowUtils.mainShader.d.get(i)).c().a("Radius").a(lastStrength);
            }
        }
    }
    
    public static void processShadow(final boolean b, final float n) {
        if (!cii.j()) {
            return;
        }
        final bit bit = new bit(ShadowUtils.mc);
        initBlur(bit, n);
        if (b) {
            ShadowUtils.mc.b().e();
            ShadowUtils.initialFB.f();
            ShadowUtils.blackBuffer.f();
            ShadowUtils.initialFB.a(true);
        }
        else {
            ShadowUtils.frameBuffer.a(true);
            ShadowUtils.mainShader.a(ShadowUtils.mc.Y.renderPartialTicks);
            ShadowUtils.mc.b().a(true);
            final float n2 = (float)bit.a();
            final float n3 = (float)bit.b();
            final float n4 = ShadowUtils.blackBuffer.c / (float)ShadowUtils.blackBuffer.a;
            final float n5 = ShadowUtils.blackBuffer.d / (float)ShadowUtils.blackBuffer.b;
            bus.G();
            bus.g();
            bus.d();
            bus.y();
            bus.j();
            bus.a(false);
            bus.a(true, true, true, true);
            bus.m();
            bus.b(478 + 695 - 983 + 580, 251 + 729 - 783 + 574);
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            ShadowUtils.blackBuffer.c();
            GL11.glTexParameteri(2097 + 2685 - 3598 + 2369, 9882 + 118 - 6397 + 6639, 11311 + 885 + 12908 + 7967);
            GL11.glTexParameteri(2659 + 2763 - 4799 + 2930, 6175 + 5635 - 8736 + 7169, 9034 + 9763 - 2230 + 16504);
            final bve a = bve.a();
            final buk c = a.c();
            c.a(7, cdy.i);
            c.b(0.0, (double)n3, 0.0).a(0.0, 0.0).b(145 + 20 - 40 + 130, 45 + 135 - 97 + 172, 204 + 94 - 289 + 246, 246 + 5 - 186 + 190).d();
            c.b((double)n2, (double)n3, 0.0).a((double)n4, 0.0).b(204 + 76 - 80 + 55, 77 + 135 - 65 + 108, 223 + 148 - 369 + 253, 82 + 7 + 158 + 8).d();
            c.b((double)n2, 0.0, 0.0).a((double)n4, (double)n5).b(99 + 141 + 12 + 3, 57 + 138 - 55 + 115, 65 + 80 - 28 + 138, 38 + 177 - 8 + 48).d();
            c.b(0.0, 0.0, 0.0).a(0.0, (double)n5).b(236 + 191 - 417 + 245, 208 + 45 - 80 + 82, 248 + 94 - 222 + 135, 198 + 85 - 52 + 24).d();
            a.b();
            ShadowUtils.blackBuffer.d();
            bus.l();
            bus.e();
            bus.k();
            bus.a(true);
            bus.y();
            bus.H();
            bus.I();
            bus.c(1.0f, 1.0f, 1.0f, 1.0f);
            bus.m();
            bus.b(701 + 557 - 1247 + 759, 24 + 149 + 134 + 464);
        }
    }
}
