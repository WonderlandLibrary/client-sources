package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.extension.Extension;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.guis.clickgui.setting.impl.ColorPickerSetting;
import de.tired.event.EventTarget;
import de.tired.event.events.Render2DEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.module.ModuleManager;
import de.tired.tired.other.ClientHelper;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

@ModuleAnnotation(name = "Crosshair", category = ModuleCategory.RENDER, clickG = "Better looking crossair", renderPreview = true)
public class Crossair extends Module {

    public NumberSetting length = new NumberSetting("length", this, 0, 0, 5, .1);
    public NumberSetting gap = new NumberSetting("gap", this, 0, 0, 5, .1);
    public NumberSetting width = new NumberSetting("width", this, 0, 0, 5, .1);
    public NumberSetting size = new NumberSetting("size", this, 0, 0, 5, .1);
    public NumberSetting dynamicgap  = new NumberSetting("dynamicgap", this, 0, 0, 5, .1);
    private BooleanSetting dynamic = new BooleanSetting("dynamic", this, true);
    private int color = new Color(0xff4d4c).getRGB();
    public ColorPickerSetting colorPickerSetting = new ColorPickerSetting("ColorCrossair", this, true, new Color(0, 0, 0, 255), (new Color(0, 0, 0, 255)).getRGB(), null);
    private double animation = 0;


    public void render() {
        ScaledResolution sr = new ScaledResolution(MC);
        int midWidth = sr.getScaledWidth() / 2;
        int midHeight = sr.getScaledHeight() / 2;

        render(midWidth, midHeight);

    }

    @Override
    public void onRender(int x, int y, int width, int height) {
        ScaledResolution sr = new ScaledResolution(MC);
        int midWidth = x;
        int midHeight = y;

        render(midWidth, midHeight);
        super.onRender(x, y, width, height);
    }

    public void render(int x, int y) {
        // top box
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBordered((double) x - (width.getValue()), (double) y - (gap.getValue() + length.getValue()) - ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), (double) x + (width.getValue()), (double) y - (gap.getValue()) - ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), 0.5, colorPickerSetting.ColorPickerC.getRGB(), 0xff000000);
        // bottom box
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBordered((double) x - (width.getValue()), (double) y + (gap.getValue()) + ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), (double) x + (width.getValue()), (double) y + (gap.getValue() + length.getValue()) + ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), 0.5, colorPickerSetting.ColorPickerC.getRGB(), 0xff000000);
        // left box
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBordered((double) x - (gap.getValue() + length.getValue()) - ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), (double) y - (width.getValue()), (double) x - (gap.getValue()) - ((ClientHelper.INSTANCE.moving()&& dynamic.getValue()) ? dynamicgap.getValue() : 0), (double) y + (width.getValue()), 0.5,  colorPickerSetting.ColorPickerC.getRGB(), 0xff000000);
        // right box
        Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBordered((double) x + (gap.getValue()) + ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), (double) y - (width.getValue()), (double) x + (gap.getValue() + length.getValue()) + ((ClientHelper.INSTANCE.moving() && dynamic.getValue()) ? dynamicgap.getValue() : 0), (double) y + (width.getValue()), 0.5,  colorPickerSetting.ColorPickerC.getRGB(), 0xff000000);

    }


    public static Crossair getInstance() {
        return ModuleManager.getInstance(Crossair.class);
    }

    @EventTarget
    public void onRender(Render2DEvent e) {
        render();
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
