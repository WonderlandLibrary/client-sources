package ez.h.features.visual;

import ez.h.event.events.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import ez.h.utils.*;
import java.util.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class ItemESP extends Feature
{
    OptionColor color;
    
    @EventTarget
    public void onRender(final EventRender3D eventRender3D) {
        for (final vg vg : ItemESP.mc.f.e) {
            if (!(vg instanceof acl)) {
                continue;
            }
            final double n = vg.M + (vg.p - vg.M) * eventRender3D.getPartialTicks() - ItemESP.mc.ac().o - 0.1;
            final double n2 = vg.N + (vg.q - vg.N) * eventRender3D.getPartialTicks() - ItemESP.mc.ac().p;
            final double n3 = vg.O + (vg.r - vg.O) * eventRender3D.getPartialTicks() - ItemESP.mc.ac().q - 0.15;
            GL11.glPushMatrix();
            GL11.glDisable(2602 + 2472 - 3693 + 1548);
            GL11.glDisable(1453 + 2092 - 2677 + 2685);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.15f, 0.0f, 0.15f);
            bus.m();
            final float n4 = Math.min(Math.max(1.2f * (ItemESP.mc.h.getDistance(vg) * 0.15f), 1.25f), 6.0f) * 0.014f;
            GL11.glTranslatef((float)n, (float)n2 + vg.H + 0.8f, (float)n3);
            bus.a(0.0f, 1.0f, 0.0f);
            bus.b(-ItemESP.mc.ac().e, 0.0f, 1.0f, 0.0f);
            bus.b(ItemESP.mc.ac().f, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(-n4, -n4, n4);
            RenderUtils.drawBlurredShadow(-ItemESP.mc.k.a(((acl)vg).k().r()) / 2.0f - 2.0f, -2.0f, (float)(ItemESP.mc.k.a(((acl)vg).k().r()) + 2), 9.0f, 8, new Color(-1476394999, true));
            ItemESP.mc.k.drawString(((acl)vg).k().r(), -ItemESP.mc.k.a(((acl)vg).k().r()) / 2.0f, 0.0f, this.color.getColor().getRGB());
            GL11.glEnable(2785 + 2457 - 3002 + 1313);
            GL11.glEnable(199 + 1729 - 521 + 1522);
            GL11.glTranslatef(0.0f, 0.1f, 0.0f);
            GL11.glPopMatrix();
        }
    }
    
    public ItemESP() {
        super("ItemESP", "\u041f\u043e\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0435\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u043d\u0430 \u0437\u0435\u043c\u043b\u0435.", Category.VISUAL);
        this.color = new OptionColor(this, "Color", new Color(-1));
        this.addOptions(this.color);
    }
}
