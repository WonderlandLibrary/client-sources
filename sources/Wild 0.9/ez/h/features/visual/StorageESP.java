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

public class StorageESP extends Feature
{
    OptionBoolean shulker;
    OptionBoolean chest;
    OptionBoolean enderchest;
    OptionColor color;
    
    @EventTarget
    public void onRender3D(final EventRender3D eventRender3D) {
        for (final avj avj : StorageESP.mc.f.g) {
            if (!(avj instanceof avl) && !(avj instanceof avs) && !(avj instanceof awb)) {
                continue;
            }
            if (avj instanceof avl && !this.chest.enabled) {
                continue;
            }
            if (avj instanceof avs && !this.enderchest.enabled) {
                continue;
            }
            if (avj instanceof awb && !this.shulker.enabled) {
                continue;
            }
            final double n = avj.w().p() - StorageESP.mc.ac().o;
            final double n2 = avj.w().q() - StorageESP.mc.ac().p;
            final double n3 = avj.w().r() - StorageESP.mc.ac().q;
            GL11.glPushMatrix();
            GL11.glTranslated(n + 0.5, n2 + 0.9, n3 + 0.5);
            GL11.glRotated(90.0, 1.0, 0.0, 0.0);
            GL11.glDisable(457 + 421 + 2479 + 196);
            GL11.glEnable(1295 + 2211 - 657 + 193);
            GL11.glDisable(594 + 1919 - 1286 + 1657);
            GL11.glDisable(1640 + 1377 - 2807 + 2719);
            GL11.glBlendFunc(251 + 685 - 473 + 307, 30 + 698 - 274 + 317);
            GL11.glLineWidth(1.0f);
            RenderUtils.color(this.color.getColor().getRGB());
            final Cylinder cylinder = new Cylinder();
            cylinder.setDrawStyle(17437 + 62355 - 45772 + 65991);
            cylinder.setOrientation(63419 + 19851 - 32432 + 49183);
            cylinder.draw(0.62f, 0.62f, 0.9f, 8, 1);
            RenderUtils.color(RenderUtils.injectAlpha(this.color.getColor().getRGB(), 35 + 123 - 18 + 10).getRGB());
            cylinder.setDrawStyle(42976 + 37211 - 44462 + 64287);
            cylinder.setOrientation(22207 + 32043 + 21872 + 23899);
            cylinder.draw(0.62f, 0.65f, 0.9f, 8, 1);
            GL11.glDisable(1152 + 1253 - 303 + 940);
            GL11.glEnable(274 + 1020 + 479 + 1111);
            GL11.glEnable(1292 + 218 + 1705 + 338);
            GL11.glEnable(1567 + 2082 - 2723 + 2003);
            bus.I();
            GL11.glPopMatrix();
        }
    }
    
    public StorageESP() {
        super("StorageESP", "\u041f\u043e\u0434\u0441\u0432\u0435\u0447\u0438\u0432\u0430\u0435\u0442 \u0441\u0443\u043d\u0434\u0443\u043a\u0438 \u0438 \u0448\u0430\u043b\u043a\u0435\u0440\u0430 \u0437\u0430 \u0441\u0442\u0435\u043d\u0430\u043c\u0438.", Category.VISUAL);
        this.chest = new OptionBoolean(this, "Chest", true);
        this.enderchest = new OptionBoolean(this, "Ender Chests", true);
        this.shulker = new OptionBoolean(this, "Shulker", true);
        this.color = new OptionColor(this, "Color", new Color(105 + 220 - 238 + 168, 141 + 136 - 91 + 69, 146 + 122 - 201 + 188), true);
        this.addOptions(this.color, this.chest, this.enderchest, this.shulker);
    }
}
