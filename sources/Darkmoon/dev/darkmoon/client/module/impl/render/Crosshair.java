package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.Utility;
import dev.darkmoon.client.utility.math.MathUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

@ModuleAnnotation(name = "Crosshair", category = Category.RENDER)
public class Crosshair extends Module {
    private float coldwnLast;

    @EventTarget
    public void onRender2D(final EventRender2D event) {
        int crosshairColor = Arraylist.getColor(180).getRGB();

        final float screenWidth = (float)event.getResolution().getScaledWidth();
        final float screenHeight = (float)event.getResolution().getScaledHeight();
        final float width = screenWidth / 2.0f;
        final float height = screenHeight / 2.0f;


        final double cinc = Utility.mc.player.getCooledAttackStrength(0.0f) * 359.0f;
        this.coldwnLast = (float) MathUtility.lerp(this.coldwnLast, (float)cinc, 0.30000001192092896);
        RenderUtility.drawCircle(width, height, 1.0f, 360.0f, 4, new Color(52, 52, 52, 190).hashCode(),3);
        RenderUtility.drawCircle(width, height, 1.0f, 1.0f + this.coldwnLast, 4, crosshairColor, 3);
    }
}