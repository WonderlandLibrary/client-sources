/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package digital.rbq.module.impl.visuals;

import java.awt.Color;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import digital.rbq.annotations.Label;
import digital.rbq.events.render.RenderCrosshairEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.BoolOption;
import digital.rbq.module.option.impl.ColorOption;
import digital.rbq.module.option.impl.DoubleOption;

@Label(value="Crosshair")
@Category(value=ModuleCategory.VISUALS)
public final class CrosshairMod
extends Module {
    public static final BoolOption dot = new BoolOption("Dot", true);
    public static final DoubleOption thickness = new DoubleOption("Thickness", 1.0, 0.5, 4.0, 0.5);
    public static final DoubleOption length = new DoubleOption("Length", 3.0, 0.5, 10.0, 0.5);
    public static final DoubleOption gap = new DoubleOption("Gap", 3.0, 0.5, 4.0, 0.5);
    public static final BoolOption outline = new BoolOption("Outline", true);
    public static final DoubleOption outlineThickness = new DoubleOption("Outline thickness", 0.5, outline::getValue, 0.5, 4.0, 0.5);
    public static final ColorOption color = new ColorOption("Color", new Color(163, 61, 61));

    public CrosshairMod() {
        this.addOptions(dot, thickness, length, gap, outline, outlineThickness, color);
    }

    @Listener(value=RenderCrosshairEvent.class)
    public final void onRenderCrosshair(RenderCrosshairEvent event) {
        GL11.glPushMatrix();
        event.setCancelled();
        ScaledResolution sr = event.getScaledRes();
        double thickness = (Double)CrosshairMod.thickness.getValue() / 2.0;
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        float middleX = (float)width / 2.0f;
        float middleY = (float)height / 2.0f;
        if (dot.getValue().booleanValue()) {
            Gui.drawRect((double)middleX - thickness - (Double)outlineThickness.getValue(), (double)middleY - thickness - (Double)outlineThickness.getValue(), (double)middleX + thickness + (Double)outlineThickness.getValue(), (double)middleY + thickness + (Double)outlineThickness.getValue(), Color.BLACK.getRGB());
            Gui.drawRect((double)middleX - thickness, (double)middleY - thickness, (double)middleX + thickness, (double)middleY + thickness, ((Color)color.getValue()).getRGB());
        }
        Gui.drawRect((double)middleX - thickness - (Double)outlineThickness.getValue(), (double)middleY - (Double)gap.getValue() - (Double)length.getValue() - (Double)outlineThickness.getValue(), (double)middleX + thickness + (Double)outlineThickness.getValue(), (double)middleY - (Double)gap.getValue() + (Double)outlineThickness.getValue(), Color.BLACK.getRGB());
        Gui.drawRect((double)middleX - thickness, (double)middleY - (Double)gap.getValue() - (Double)length.getValue(), (double)middleX + thickness, (double)middleY - (Double)gap.getValue(), ((Color)color.getValue()).getRGB());
        Gui.drawRect((double)middleX - (Double)gap.getValue() - (Double)length.getValue() - (Double)outlineThickness.getValue(), (double)middleY - thickness - (Double)outlineThickness.getValue(), (double)middleX - (Double)gap.getValue() + (Double)outlineThickness.getValue(), (double)middleY + thickness + (Double)outlineThickness.getValue(), Color.BLACK.getRGB());
        Gui.drawRect((double)middleX - (Double)gap.getValue() - (Double)length.getValue(), (double)middleY - thickness, (double)middleX - (Double)gap.getValue(), (double)middleY + thickness, ((Color)color.getValue()).getRGB());
        Gui.drawRect((double)middleX - thickness - (Double)outlineThickness.getValue(), (double)middleY + (Double)gap.getValue() - (Double)outlineThickness.getValue(), (double)middleX + thickness + (Double)outlineThickness.getValue(), (double)middleY + (Double)gap.getValue() + (Double)length.getValue() + (Double)outlineThickness.getValue(), Color.BLACK.getRGB());
        Gui.drawRect((double)middleX - thickness, (double)middleY + (Double)gap.getValue(), (double)middleX + thickness, (double)middleY + (Double)gap.getValue() + (Double)length.getValue(), ((Color)color.getValue()).getRGB());
        Gui.drawRect((double)middleX + (Double)gap.getValue() - (Double)outlineThickness.getValue(), (double)middleY - thickness - (Double)outlineThickness.getValue(), (double)middleX + (Double)gap.getValue() + (Double)length.getValue() + (Double)outlineThickness.getValue(), (double)middleY + thickness + (Double)outlineThickness.getValue(), Color.BLACK.getRGB());
        Gui.drawRect((double)middleX + (Double)gap.getValue(), (double)middleY - thickness, (double)middleX + (Double)gap.getValue() + (Double)length.getValue(), (double)middleY + thickness, ((Color)color.getValue()).getRGB());
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.enableBlend();
        GL11.glPopMatrix();
    }
}

