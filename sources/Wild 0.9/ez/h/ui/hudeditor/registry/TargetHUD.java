package ez.h.ui.hudeditor.registry;

import ez.h.ui.hudeditor.*;
import ez.h.animengine.*;
import ez.h.features.combat.*;
import ez.h.utils.*;
import java.awt.*;
import ez.h.ui.fonts.*;
import org.lwjgl.opengl.*;

public class TargetHUD extends DraggableElement
{
    public static float barWidth;
    public static float scaleFactor;
    
    public TargetHUD() {
        super("TargetHUD", 200.0f, 200.0f);
    }
    
    static {
        TargetHUD.scaleFactor = 0.0f;
    }
    
    @Override
    public void render(final float n, final float n2, final float n3, final boolean b) {
        if (TargetHUD.scaleFactor < 1.0f) {
            TargetHUD.scaleFactor += 0.05f;
        }
        Easings.easeInOutQuart(TargetHUD.scaleFactor);
        this.width = 110.0f;
        this.height = 35.0f;
        if (b) {
            super.render(n, n2, n3, true);
            return;
        }
        if (this.mc.v() == null || !KillAura.targethud.enabled || KillAura.target == null || this.mc.v().a(KillAura.target.h_()) == null) {
            TargetHUD.barWidth = 0.0f;
            return;
        }
        final aed target = KillAura.target;
        final float n4 = this.x - RenderUtils.getScaledScreen()[0] / 2.0f + this.width / 2.0f;
        final float n5 = this.y - RenderUtils.getScaledScreen()[1] / 2.0f - this.height / 2.0f + 4.0f;
        final bit bit = new bit(this.mc);
        final float n6 = (float)bit.a();
        final float n7 = (float)bit.b();
        TargetHUD.barWidth = rk.a(MathUtils.lerp(TargetHUD.barWidth, 72.0f * ((target.cd() + target.cD()) / target.cj()), 0.2f), 0.0f, 72.0f);
        bus.G();
        bus.c(n4, n5, 0.0f);
        bus.g();
        bus.j();
        ScaleUtils.scale_pre();
        RenderUtils.drawBlurredShadow(n6 / 2.0f - 55.0f - 3.0f, n7 / 2.0f - 17.5f - 3.0f, 116.0f, 41.0f, 0x92 ^ 0x9F, new Color(-2130640896, true));
        bus.I();
        RenderUtils.drawRect(n6 / 2.0f - 55.0f, n7 / 2.0f - 17.5f, n6 / 2.0f + 55.0f, n7 / 2.0f + 17.5f, new Color(335084875 + 1099999520 - 1360409077 + 1921813386, true).getRGB());
        bus.I();
        CFontManager.rany.drawString(target.h_(), n6 / 2.0f - 55.0f + 4.0f, n7 / 2.0f - 17.5f + 3.0f, -1);
        try {
            this.mc.N().a(this.mc.v().a(target.bm()).g());
        }
        catch (Exception ex) {
            this.mc.N().a(this.mc.h.m());
        }
        bir.a((int)(n6 / 2.0f - 55.0f) + 4, (int)n7 / 2 - 4, 8.0f, 8.0f, 8, 8, 0x32 ^ 0x26, 0x10 ^ 0x4, 64.0f, 64.0f);
        RenderUtils.drawRoundedRect(n6 / 2.0f - 55.0f + 25.0f + 5.0f, n7 / 2.0f + 17.5f - 8.0f, 72.0, 5.0, 5.0, Integer.MIN_VALUE);
        RenderUtils.drawBlurredShadow(n6 / 2.0f - 55.0f + 25.0f - 3.0f + 5.0f, n7 / 2.0f + 17.5f - 11.0f, TargetHUD.barWidth + 6.0f, 10.0f, 8, KillAura.barColor.getColor());
        RenderUtils.drawRoundedRect(n6 / 2.0f - 55.0f + 25.0f + 5.0f, n7 / 2.0f + 17.5f - 8.0f, TargetHUD.barWidth, 5.0, TargetHUD.barWidth / 12.0f, KillAura.barColor.getColor().getRGB());
        CFontManager.manropesmall.drawString((int)((target.cd() + target.cD()) / target.cj() * 100.0f) + "%", n6 / 2.0f - 55.0f + 25.0f + 5.0f, n7 / 2.0f + 17.5f - 20.0f, -1);
        GL11.glAlphaFunc(358 + 414 - 437 + 181, 0.01f);
        RenderUtils.drawBlurredShadow(n6 / 2.0f - 55.0f - 2.0f, n7 / 2.0f - 17.5f - 2.0f + 1.0f, 5.0f, 36.0f, 5, RenderUtils.injectAlpha(KillAura.barColor.getColor().getRGB(), 2 + 3 + 19 + 196));
        RenderUtils.drawRectWH(n6 / 2.0f - 55.0f, n7 / 2.0f - 17.5f + 1.0f, 1.0f, 33.0f, KillAura.barColor.getColor().getRGB());
        this.drawItem(target.cp(), (int)(n6 - 16.0f) / 2.0f + 55.0f - 26.0f, (float)(int)(n7 / 2.0f + 17.5f - 27.0f));
        this.drawItem(target.co(), (int)(n6 + 16.0f) / 2.0f + 55.0f - 26.0f, (float)(int)(n7 / 2.0f + 17.5f - 27.0f));
        ScaleUtils.scale_post();
        bus.k();
        bus.H();
    }
    
    void drawItem(final aip aip, final float n, final float n2) {
        final bzw ad = this.mc.ad();
        bus.G();
        bus.k();
        bus.b(1.0f, 1.0f, 1.0f);
        ad.b(aip, (int)(n / 1.0f), (int)(n2 / 1.0f));
        ad.a(this.mc.k, aip, (int)(n / 1.0f), (int)(n2 / 1.0f), aip.e() ? (aip.getCount() + "") : null);
        bus.H();
    }
}
