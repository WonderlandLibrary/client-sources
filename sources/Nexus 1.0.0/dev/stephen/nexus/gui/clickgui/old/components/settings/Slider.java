package dev.stephen.nexus.gui.clickgui.old.components.settings;

import dev.stephen.nexus.gui.clickgui.old.components.ModuleButton;
import dev.stephen.nexus.module.setting.Setting;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import dev.stephen.nexus.utils.math.MathUtils;
import dev.stephen.nexus.utils.render.TextRenderer;
import dev.stephen.nexus.utils.render.ThemeUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public final class Slider extends RenderableSetting {
    public boolean dragging;
    private final NumberSetting setting;

    public Slider(ModuleButton parent, Setting setting, int offset) {
        super(parent, setting, offset);
        this.setting = (NumberSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        double offsetX = (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin()) * parentWidth();
        context.fill(parentX(), parentY() + offset + parentOffset(), (int) (parentX() + offsetX), parentY() + offset + parentOffset() + parentHeight(), ThemeUtils.getMainColor().darker().getRGB());

        TextRenderer.drawMinecraftText(setting.getName() + ": " + setting.getValue(), context, parentX() + 6, (parentY() + offset + parentOffset()) + 6, Color.white.getRGB());
    }

    private void slide(double mouseX) {
        double a = mouseX - parentX();
        double b = MathHelper.clamp(a / parentWidth(), 0, 1);
        setting.setValue(MathUtils.roundToDecimal(b * (setting.getMax() - setting.getMin()) + setting.getMin(), setting.getIncrement()));
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            dragging = true;
            slide(mouseX);
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging && button == 0) {
            dragging = false;
        }
        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) {
            slide(mouseX);
        }
        super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }
}