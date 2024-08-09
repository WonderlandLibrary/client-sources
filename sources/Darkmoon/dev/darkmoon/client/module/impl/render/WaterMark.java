package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.manager.dragging.DragManager;
import dev.darkmoon.client.manager.dragging.Draggable;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.ColorUtility2;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.AnimationMath;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleAnnotation(name = "WaterMark", category = Category.RENDER)
public class WaterMark extends Module {
    Color color11 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
    Color color22 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
    Color gradientColor11 = ColorUtility2.interpolateColorsBackAndForth(4, 0, color11, color22, false);
    Color gradientColor22 = ColorUtility2.interpolateColorsBackAndForth(4, 90, color11, color22, false);
    public String text;
    public float animWidth = 0.0F;
    public static ModeSetting doubleElement = new ModeSetting("WaterMode", "All", "All", "Smart");
    private final Draggable waterMarkDraggable = DragManager.create(this, "Water Mark", 10, 10);

    public WaterMark() {
        waterMarkDraggable.setWidth((float) (35 + Fonts.nunitoBold16.getStringWidth(text) - 25));
        waterMarkDraggable.setHeight(20);
    }

    @EventTarget
    public void onRender2D(EventRender2D eventRender2D) {
        if (mc.gameSettings.showDebugInfo) return;
        if (doubleElement.is("All")) { // all
            this.text = "darkmoon | " + DarkMoon.getInstance().getUserInfo().getName() + " | fps " + Minecraft.getDebugFPS();
        } else if (doubleElement.is("Smart")) {
            GL11.glEnable(3008);
            GL11.glEnable(3042);
            String[] names = {
                    "darkmoon.wtf",
                    "darkmoo.wt",
                    "darkmo.w",
                    "darkmoon.",
                    ".darkmoon",
                    "n.darkmoo",
                    "on.darkmo",
                    "oon.darkm",
                    "moon.dark",
                    "kmoon.dar",
                    "rkmoon.da",
                    "arkmoon.d",
                    "darkmoon",
                    "darkmoon.",
                    "darkmoon.w",
                    "darkmoon.wt",
                    "darkmoon.wtf"
            };
            this.text = names[(int) (Minecraft.getSystemTime() / 150L % (long) names.length)];
            GL11.glEnable(3008);
            GL11.glEnable(3042);
        }
        int color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
        int color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
        Color color11 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color22 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        int middleColor = ColorUtility.interpolateColorC(color, color2, 0.5f).getRGB();
        DarkMoon.getInstance().getScaleMath().pushScale();
        int itemOffset = waterMarkDraggable.getX();
        int posY = waterMarkDraggable.getY();
        this.animWidth = AnimationMath.fast(this.animWidth, (float)Fonts.nunitoBold16.getStringWidth(text), 9.0F);
        waterMarkDraggable.setWidth((float) (35 + Fonts.nunitoBold16.getStringWidth(text) - 25));
        waterMarkDraggable.setHeight(20);
        RenderUtility.drawDarkMoonShader((float) (itemOffset - 5), posY, (float) (30 + animWidth - 21), 15.0F, 6);
        int finalItemOffset = itemOffset;
        Fonts.nunitoBold16.drawString(text, (double) ((float) itemOffset + 1), (double) (posY + 5), Color.WHITE.getRGB());
    }
}
