package ez.h.features.visual;

import ez.h.event.events.*;
import ez.h.*;
import ez.h.event.*;
import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import ez.h.utils.*;

public class HurtCam extends Feature
{
    public static OptionSlider size;
    public static OptionColor color;
    public static OptionBoolean remove;
    
    @EventTarget
    public void onRender(final EventRender2D eventRender2D) {
        if (HurtCam.remove.enabled) {
            return;
        }
        if (HurtCam.mc.h == null) {
            return;
        }
        if (HurtCam.mc.h.ay <= 0) {
            return;
        }
        if (Main.getFeatureByName("SuperBow").isEnabled()) {
            return;
        }
        this.renderVignetteScaledResolution(new bit(HurtCam.mc));
    }
    
    public HurtCam() {
        super("HurtCam", "\u0418\u0437\u043c\u0435\u043d\u044f\u0435\u0442 \u0430\u043d\u0438\u043c\u0430\u0446\u0438\u044e \u043f\u043e\u043b\u0443\u0447\u0435\u043d\u0438\u044f \u0443\u0440\u043e\u043d\u0430.", Category.VISUAL);
        HurtCam.remove = new OptionBoolean(this, "Remove", true);
        HurtCam.size = new OptionSlider(this, "Size", 1.0f, 0.0f, 1.0f, OptionSlider.SliderType.NULL);
        HurtCam.color = new OptionColor(this, "Color", new Color(56 + 104 - 111 + 151, 0, 0), true);
        this.addOptions(HurtCam.remove, HurtCam.color, HurtCam.size);
    }
    
    void renderVignetteScaledResolution(final bit bit) {
        bus.G();
        bus.m();
        bus.e();
        bus.a(false);
        RenderUtils.color(RenderUtils.injectAlpha(HurtCam.color.getColor().getRGB(), rk.a((int)((0x2 ^ 0x1B) * HurtCam.mc.h.ay * HurtCam.size.getNum()), 0, 199 + 155 - 330 + 231)).getRGB());
        bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
        HurtCam.mc.N().a(new nf("textures/misc/vignette.png"));
        final bve a = bve.a();
        final buk c = a.c();
        c.a(7, cdy.g);
        c.b(0.0, (double)bit.b(), -90.0).a(0.0, 1.0).d();
        c.b((double)bit.a(), (double)bit.b(), -90.0).a(1.0, 1.0).d();
        c.b((double)bit.a(), 0.0, -90.0).a(1.0, 0.0).d();
        c.b(0.0, 0.0, -90.0).a(0.0, 0.0).d();
        a.b();
        bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
        bus.a(true);
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        bus.H();
    }
}
