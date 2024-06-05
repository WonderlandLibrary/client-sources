package me.vaziak.sensation.client.impl.visual;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.EventRender2D;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.ColorProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.utils.anthony.Draw;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class Crosshair extends Module {
    public ColorProperty color = new ColorProperty("Color", "Color of the crosshair", null, 1f, 0f, 1f, 255);
    private BooleanProperty fixed = new BooleanProperty("Fixed", "Enable if you don't want the crosshair to spread.", null, false);
    private DoubleProperty width = new DoubleProperty("Width", "The width of the crosshair", null, 0.4, 0.25, 5, 0.05, null);
    private DoubleProperty gap = new DoubleProperty("Gap", "The gap in the center of the crosshair", null, 1.2, 0.25, 5, 0.05, null);
    private DoubleProperty length = new DoubleProperty("Length", "The length of it", null, 5, 0.25, 15, 0.05, null);

    public Crosshair() {
        super("Crosshair", Category.VISUAL);
        registerValue(color, fixed, width, gap, length);
    }

    @Collect
    public void onRender(EventRender2D e) {
        ScaledResolution scaledRes = new ScaledResolution(mc);
        Draw.drawBorderedRectangle(
                scaledRes.getScaledWidth() / 2 - width.getValue(),
                scaledRes.getScaledHeight() / 2 - gap.getValue() - length.getValue() - getGap(),
                scaledRes.getScaledWidth() / 2 + 1.0f + width.getValue(),
                scaledRes.getScaledHeight() / 2 - gap.getValue() - getGap(),
                0.5f, color.getValue().getRGB(),
                new Color(0, 0, 0, 255).getRGB(), true);
        Draw.drawBorderedRectangle(
                scaledRes.getScaledWidth() / 2 - width.getValue(),
                scaledRes.getScaledHeight() / 2 + gap.getValue() + 1 + getGap() - 0.15,
                scaledRes.getScaledWidth() / 2 + 1.0f + width.getValue(),
                scaledRes.getScaledHeight() / 2 + 1 + gap.getValue() + length.getValue() + getGap()  - 0.15, 0.5f, color.getValue().getRGB(),
                new Color(0, 0, 0, 255).getRGB(), true);
        Draw.drawBorderedRectangle(
                scaledRes.getScaledWidth() / 2 - gap.getValue() - getGap() - length.getValue()  + 0.15,
                scaledRes.getScaledHeight() / 2 - width.getValue(),
                scaledRes.getScaledWidth() / 2 - gap.getValue() - getGap() + 0.15,
                scaledRes.getScaledHeight() / 2 + 1.0f + width.getValue(), 0.5f, color.getValue().getRGB(),
                new Color(0, 0, 0, 255).getRGB(), true);
        Draw.drawBorderedRectangle(
                scaledRes.getScaledWidth() / 2 + 1 + gap.getValue() + getGap(),
                scaledRes.getScaledHeight() / 2 - width.getValue(),
                scaledRes.getScaledWidth() / 2 + length.getValue() + gap.getValue() + getGap() + 1.0f,
                scaledRes.getScaledHeight() / 2 + 1.0f + width.getValue(), 0.5f, color.getValue().getRGB(),
                new Color(0, 0, 0, 255).getRGB(), true);
    }

    public double getGap() {
        return (fixed.getValue() ? 0 : (mc.thePlayer.isMoving() ? (mc.thePlayer.isSprinting() ? 2 : 1) : 0) - (mc.thePlayer.isSneaking() ? 1 : 0) + (mc.thePlayer.swingProgress > 0 ? 2 : 0));
    }
}
