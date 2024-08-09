package ez.h.features.visual;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;
import org.lwjgl.opengl.*;
import ez.h.utils.*;

public class ChinaHat extends Feature
{
    OptionColor color;
    OptionSlider alpha;
    OptionSlider height;
    OptionMode mode;
    OptionSlider radius;
    
    public ChinaHat() {
        super("ChinaHat", "\u0420\u0438\u0441\u0443\u0435\u0442 \u043a\u0438\u0442\u0430\u0439\u0441\u043a\u0443\u044e \u0448\u043b\u044f\u043f\u0443 \u043d\u0430\u0434 \u0438\u0433\u0440\u043e\u043a\u043e\u043c.", Category.VISUAL);
        this.radius = new OptionSlider(this, "Radius", 1.0f, 0.1f, 2.0f, OptionSlider.SliderType.NULL);
        this.height = new OptionSlider(this, "Height", 0.1f, 0.1f, 0.5f, OptionSlider.SliderType.NULL);
        this.mode = new OptionMode(this, "Mode", "Astolfo", new String[] { "Astolfo", "Rainbow", "Client", "Custom", "Cosmo" }, 0);
        this.color = new OptionColor(this, "Color", Color.WHITE);
        this.alpha = new OptionSlider(this, "Alpha", 200.0f, 0.0f, 255.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.radius, this.height, this.mode, this.color, this.alpha);
    }
    
    @EventTarget
    public void onRender(final EventRender3D eventRender3D) {
        if (ChinaHat.mc.t.aw == 0) {
            return;
        }
        if (ChinaHat.mc.h.F || ChinaHat.mc.h.cd() <= 0.0f) {
            return;
        }
        this.render();
    }
    
    @Override
    public void updateElements() {
        this.color.display = this.mode.isMode("Custom");
        this.alpha.display = (!this.mode.isMode("Custom") && !this.mode.isMode("Cosmo"));
        super.updateElements();
    }
    
    final void render() {
        final float n = (float)(ChinaHat.mc.h.M + (ChinaHat.mc.h.p - ChinaHat.mc.h.M) * ChinaHat.mc.aj());
        final float n2 = (float)(ChinaHat.mc.h.N + (ChinaHat.mc.h.q - ChinaHat.mc.h.N) * ChinaHat.mc.aj());
        final float n3 = (float)(ChinaHat.mc.h.O + (ChinaHat.mc.h.r - ChinaHat.mc.h.O) * ChinaHat.mc.aj());
        final float n4 = (float)(n - ChinaHat.mc.ac().o);
        final float n5 = (float)(n2 - ChinaHat.mc.ac().p);
        final float n6 = (float)(n3 - ChinaHat.mc.ac().q);
        final float n7 = n4 - 0.5f;
        final float n8 = n6 - 0.5f;
        final float n9 = n5 + ChinaHat.mc.h.H;
        GL11.glPushMatrix();
        GL11.glEnable(1673 + 2535 - 1472 + 306);
        GL11.glBlendFunc(299 + 153 + 15 + 303, 213 + 102 + 285 + 171);
        GL11.glTranslated((double)(n7 + 0.5f), (double)(n9 + 0.45f - (ChinaHat.mc.h.aU() ? 0.25f : 0.0f) + ChinaHat.mc.h.H + this.height.getNum()), (double)(n8 + 0.5f));
        GL11.glRotated((double)(-ChinaHat.mc.h.v % 360.0f), 0.0, 1.0, 0.0);
        GL11.glTranslated(-(n7 + 0.5), -(n9 + 0.5), -(n8 + 0.5));
        GL11.glDisable(85 + 3462 - 423 + 429);
        GL11.glEnable(2545 + 1317 - 3439 + 2425);
        GL11.glDisable(2826 + 1069 - 2632 + 1666);
        GL11.glDepthMask(false);
        if (!this.mode.isMode("Cosmo")) {
            if (this.mode.isMode("Custom")) {
                RenderUtils.color(this.color.getColor().getRGB());
            }
            else if (this.mode.isMode("Astolfo")) {
                RenderUtils.color(RenderUtils.injectAlpha(RenderUtils.astolfo(5000.0f, 1).getRGB(), (int)this.alpha.getNum()).getRGB());
            }
            else {
                RenderUtils.color(RenderUtils.injectAlpha(this.color.getColor().getRGB(), (int)this.alpha.getNum()).getRGB());
            }
        }
        if (this.mode.isMode("Cosmo")) {
            RenderUtils.shaderAttach((vg)ChinaHat.mc.h);
        }
        RenderUtils.drawCone(this.radius.getNum(), this.height.getNum(), 0x52 ^ 0x60, true);
        if (this.mode.isMode("Cosmo")) {
            RenderUtils.shaderDetach();
        }
        GL11.glDisable(100 + 1964 - 1723 + 2507);
        GL11.glEnable(2944 + 1843 - 1655 + 421);
        GL11.glEnable(1195 + 264 - 83 + 1553);
        GL11.glDepthMask(true);
        GL11.glDisable(1209 + 2489 - 1065 + 409);
        GL11.glPopMatrix();
    }
}
