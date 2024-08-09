package ez.h.features.visual;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import org.lwjgl.opengl.*;
import ez.h.utils.*;
import ez.h.ui.fonts.*;
import ez.h.event.*;

public class BlockOverlay extends Feature
{
    public double x1;
    OptionSlider size;
    public double z1;
    public double damage;
    public double y1;
    public int alpha;
    OptionColor color;
    OptionBoolean glow;
    
    public BlockOverlay() {
        super("BlockOverlay", "\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044e \u043e \u043d\u0430\u0432\u0435\u0434\u0451\u043d\u043d\u043e\u043c \u0431\u043b\u043e\u043a\u0435.", Category.VISUAL);
        this.color = new OptionColor(this, "Color", new Color(0xBA ^ 0x88, 62 + 151 - 186 + 173, 191 + 6 - 32 + 35));
        this.glow = new OptionBoolean(this, "Glow", true);
        this.addOptions(this.color, this.glow);
    }
    
    @EventTarget
    public void onUpdate(final EventRender3D eventRender3D) {
        if (BlockOverlay.mc.f == null || BlockOverlay.mc.h == null) {
            return;
        }
        if (BlockOverlay.mc.s == null) {
            return;
        }
        final et a = BlockOverlay.mc.s.a();
        if (a == null) {
            return;
        }
        if (!BlockOverlay.mc.t.ae.i) {
            return;
        }
        this.x1 = MathUtils.lerp((float)a.p(), (float)this.x1, 0.1f);
        this.y1 = MathUtils.lerp((float)a.q(), (float)this.y1, 0.1f);
        this.z1 = MathUtils.lerp((float)a.r(), (float)this.z1, 0.1f);
        this.damage = MathUtils.lerp(BlockOverlay.mc.c.e * 90.0f, (float)this.damage, 0.1f);
        this.alpha = (int)MathUtils.lerp((BlockOverlay.mc.c.e > 0.0f) ? ((float)((BlockOverlay.mc.f.o(a).u() instanceof aom) ? 0 : (227 + 252 - 296 + 72))) : 0.0f, (float)this.alpha, 0.05f);
        final double n = this.x1 - BlockOverlay.mc.ac().o;
        final double n2 = this.y1 - BlockOverlay.mc.ac().p + 1.5;
        final double n3 = this.z1 - BlockOverlay.mc.ac().q;
        GL11.glPushMatrix();
        bus.j();
        GL11.glTranslated(n, n2, n3);
        final float n4 = (float)(0.009999999776482582 + BlockOverlay.mc.h.e(this.x1, this.y1, this.z1) / 1200.0);
        GL11.glRotated((double)(-BlockOverlay.mc.ac().e), 0.0, 1.0, 0.0);
        GL11.glRotated((double)BlockOverlay.mc.ac().f, 1.0, 0.0, 0.0);
        GL11.glScalef(-n4, -n4, n4);
        if (this.glow.enabled) {
            RenderUtils.drawBlurredShadow(0.0f, 0.0f, 100.0f, 50.0f, 0x92 ^ 0x9D, new Color(0, 0, 0, this.alpha));
        }
        RenderUtils.drawRoundedRect(0.0, 0.0, 100.0, 50.0, 2.0, new Color(0x3 ^ 0x16, 0x17 ^ 0xE, 0x25 ^ 0x32, this.alpha).getRGB());
        if (!(BlockOverlay.mc.f.o(a).u() instanceof aom) && BlockOverlay.mc.c.e > 0.0f) {
            CFontManager.manropesmall.drawString(BlockOverlay.mc.f.o(a).u().c(), 5.0f, 5.0f, new Color(108 + 78 + 29 + 40, 219 + 160 - 192 + 68, 6 + 40 - 45 + 254, this.alpha).getRGB());
            CFontManager.manropesmall.drawString(String.valueOf((int)(BlockOverlay.mc.c.e * 100.0f)) + "%", 5.0f, 17.0f, new Color(253 + 17 - 65 + 50, 67 + 26 + 66 + 96, 79 + 82 + 58 + 36, this.alpha).getRGB());
            RenderUtils.drawRoundedRect(5.0, 30.0, 90.0, 5.0, 2.0, new Color(0xAC ^ 0xB8, 0xB3 ^ 0xA7, 0x1E ^ 0x9).getRGB());
            if (this.glow.enabled) {
                RenderUtils.drawBlurredShadow(5.0f, 30.0f, (float)this.damage, 5.0f, 0x75 ^ 0x7A, this.color.getColor());
            }
            RenderUtils.drawRoundedRect(5.0, 30.0, (float)this.damage, 5.0, 2.0, this.color.getColor().getRGB());
        }
        bus.k();
        GL11.glPopMatrix();
    }
}
