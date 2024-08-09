package ez.h.features.visual;

import ez.h.event.events.*;
import org.lwjgl.opengl.*;
import ez.h.utils.*;
import org.lwjgl.util.glu.*;
import java.util.*;
import ez.h.event.*;
import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;

public class SpawnerESP extends Feature
{
    OptionColor color;
    
    @EventTarget
    public void onRender(final EventRender3D eventRender3D) {
        for (final avj avj : SpawnerESP.mc.f.g) {
            if (!(avj instanceof avy)) {
                continue;
            }
            final double n = avj.w().p() - SpawnerESP.mc.ac().o;
            final double n2 = avj.w().q() - SpawnerESP.mc.ac().p;
            final double n3 = avj.w().r() - SpawnerESP.mc.ac().q;
            GL11.glPushMatrix();
            GL11.glTranslated(n + 0.5, n2 + 0.9, n3 + 0.5);
            GL11.glRotated(90.0, 1.0, 0.0, 0.0);
            GL11.glDisable(126 + 1272 - 437 + 2592);
            GL11.glEnable(2222 + 1153 - 2157 + 1824);
            GL11.glDisable(66 + 1596 + 348 + 874);
            GL11.glDisable(2356 + 2181 - 2939 + 1331);
            GL11.glBlendFunc(281 + 626 - 629 + 492, 679 + 396 - 904 + 600);
            GL11.glLineWidth(1.0f);
            RenderUtils.color(this.color.getColor().getRGB());
            final Cylinder cylinder = new Cylinder();
            cylinder.setDrawStyle(445 + 3172 + 27826 + 68568);
            cylinder.setOrientation(50834 + 31006 - 42702 + 60883);
            cylinder.draw(0.62f, 0.62f, 0.9f, 8, 1);
            RenderUtils.color(RenderUtils.injectAlpha(this.color.getColor().getRGB(), 41 + 67 + 20 + 22).getRGB());
            cylinder.setDrawStyle(88365 + 66589 - 63586 + 8644);
            cylinder.setOrientation(30686 + 93470 - 74768 + 50633);
            cylinder.draw(0.62f, 0.65f, 0.9f, 8, 1);
            GL11.glDisable(72 + 1737 + 740 + 493);
            GL11.glEnable(1030 + 858 - 496 + 1492);
            GL11.glEnable(1253 + 3096 - 1205 + 409);
            GL11.glEnable(1605 + 1556 - 2851 + 2619);
            bus.I();
            GL11.glPopMatrix();
        }
    }
    
    public SpawnerESP() {
        super("SpawnerESP", "\u041f\u043e\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0435\u0442 \u0441\u043f\u0430\u0432\u043d\u0435\u0440\u0430 \u0437\u0430 \u0441\u0442\u0435\u043d\u0430\u043c\u0438.", Category.VISUAL);
        this.color = new OptionColor(this, "Color", new Color(181 + 5 - 123 + 192, 97 + 105 - 32 + 85, 21 + 122 + 52 + 60), true);
        this.addOptions(this.color);
    }
}
