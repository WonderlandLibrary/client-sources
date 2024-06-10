package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.utils.*;
import java.awt.*;
import me.kaimson.melonclient.gui.utils.*;

public class CrosshairModule extends Module
{
    public static CrosshairModule INSTANCE;
    private final Setting crosshair;
    private final Setting color;
    private final Setting size;
    private final Setting gap;
    private final Setting thickness;
    private final Setting dot;
    private final Setting dotColor;
    public final Setting showInThird;
    
    public CrosshairModule() {
        super("Crosshair");
        new Setting(this, "Style Options");
        this.crosshair = new Setting(this, "Mode").setDefault(0).setRange("Vanilla", "Cross", "Circle", "Arrow");
        this.color = new Setting(this, "Color").setDefault(new Color(255, 255, 255, 255).getRGB(), 0);
        this.size = new Setting(this, "Size").setDefault(16).setRange(2, 24, 1);
        this.gap = new Setting(this, "Gap").setDefault(4).setRange(0, 32, 1);
        this.thickness = new Setting(this, "Thickness").setDefault(2.0f).setRange(0.5f, 5.0f, 0.5f);
        new Setting(this, "Dot Options");
        this.dot = new Setting(this, "Dot").setDefault(false);
        this.dotColor = new Setting(this, "Dot Color").setDefault(new Color(255, 255, 255, 255).getRGB(), 0);
        new Setting(this, "Other");
        this.showInThird = new Setting(this, "Show in third person view").setDefault(false);
        CrosshairModule.INSTANCE = this;
    }
    
    public void render(final avo gui, final int x, final int y, final int i, final int j) {
        final int color = this.color.getColor();
        final int size = this.size.getInt();
        final int gap = this.gap.getInt();
        float thickness = this.thickness.getFloat();
        if (this.dot.getBoolean()) {
            GLUtils.drawDot(x - 0.1f, (float)y, 3.0f, this.dotColor.getColor());
        }
        switch (this.crosshair.getInt()) {
            case 0: {
                GuiUtils.setGlColor(color);
                gui.b(i, j, 0, 0, 16, 16);
                break;
            }
            case 1: {
                thickness /= 2.0f;
                GLUtils.drawFilledRectangle(x - thickness, (float)(y - gap - size), x + thickness, (float)(y - gap), color, true);
                GLUtils.drawFilledRectangle(x - thickness, (float)(y + gap), x + thickness, (float)(y + gap + size), color, true);
                GLUtils.drawFilledRectangle((float)(x - gap - size), y - thickness, (float)(x - gap), y + thickness, color, true);
                GLUtils.drawFilledRectangle((float)(x + gap), y - thickness, (float)(x + gap + size), y + thickness, color, true);
                break;
            }
            case 2: {
                GLUtils.drawTorus(x, y, (float)gap, gap + thickness, color, true);
                break;
            }
            case 3: {
                GLUtils.drawLines(new float[] { (float)(x - size), (float)(y + size), (float)x, (float)y, (float)x, (float)y, (float)(x + size), (float)(y + size) }, thickness, color, true);
                break;
            }
        }
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
