package dev.stephen.nexus.gui.clickgui.old.components.settings;

import dev.stephen.nexus.gui.clickgui.old.components.ModuleButton;
import dev.stephen.nexus.module.setting.Setting;
import dev.stephen.nexus.module.setting.impl.RangeSetting;
import dev.stephen.nexus.utils.math.MathUtils;
import dev.stephen.nexus.utils.render.TextRenderer;
import dev.stephen.nexus.utils.render.ThemeUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class RangeSlider extends RenderableSetting {
    private boolean draggingMin;
    private boolean draggingMax;
    private final RangeSetting setting;

    public RangeSlider(ModuleButton parent, Setting setting, int offset) {
        super(parent, setting, offset);
        this.setting = (RangeSetting) setting;
    }

    @Override
    public void render(DrawContext matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        double range = setting.getMax() - setting.getMin();
        double offsetXMin = (setting.getValueMin() - setting.getMin()) / range * parentWidth();
        double offsetXMax = (setting.getValueMax() - setting.getMin()) / range * parentWidth();
        matrices.fill(parentX() + (int) offsetXMin, parentY() + offset + parentOffset(), parentX() + (int) offsetXMax, parentY() + offset + parentOffset() + parentHeight(), ThemeUtils.getMainColor().darker().getRGB());

        TextRenderer.drawMinecraftText(setting.getName() + ": " + setting.getValueMin() + " - " + setting.getValueMax(), matrices, parentX() + 6, (parentY() + offset + parentOffset()) + 6, Color.white.getRGB());
    }

    private void slide(double mouseX) {
        double relativeX = mouseX - parentX();
        double range = setting.getMax() - setting.getMin();
        double relativeMin = MathHelper.clamp(relativeX / parentWidth(), 0, 1);
        double relativeMax = MathHelper.clamp((relativeX + setting.getIncrement()) / parentWidth(), 0, 1);

        double valueMin = MathUtils.roundToDecimal(relativeMin * range + setting.getMin(), setting.getIncrement());
        double valueMax = MathUtils.roundToDecimal(relativeMax * range + setting.getMin(), setting.getIncrement());

        if (draggingMin && !draggingMax) {
            setting.setValueMin(valueMin);
        } else if (draggingMax && !draggingMin) {
            setting.setValueMax(valueMax);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            double relativeX = mouseX - parentX();
            double minHandleX = parentWidth() * (setting.getValueMin() - setting.getMin()) / (setting.getMax() - setting.getMin());
            double maxHandleX = parentWidth() * (setting.getValueMax() - setting.getMin()) / (setting.getMax() - setting.getMin());

            if (Math.abs(relativeX - minHandleX) < 5) {
                draggingMin = true;
            } else if (Math.abs(relativeX - maxHandleX) < 5) {
                draggingMax = true;
            }
            slide(mouseX);
        }
        super.mouseClicked(mouseX, mouseY, button);
    }


    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (draggingMin || draggingMax) {
            draggingMin = false;
            draggingMax = false;
        }
        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggingMin || draggingMax) {
            slide(mouseX);
        }
        super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }
}