package dev.stephen.nexus.gui.clickgui.old.components.settings;

import dev.stephen.nexus.gui.clickgui.old.components.ModuleButton;
import dev.stephen.nexus.module.setting.Setting;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.utils.render.TextRenderer;
import dev.stephen.nexus.utils.render.ThemeUtils;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public final class CheckBox extends RenderableSetting {

    private final BooleanSetting setting;

    public CheckBox(ModuleButton parent, Setting setting, int offset) {
        super(parent, setting, offset);
        this.setting = (BooleanSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        TextRenderer.drawMinecraftText(setting.getName(), context, parentX() + 6, (parentY() + parentOffset() + offset) + 6, Color.WHITE.getRGB());
        context.fill((parentX() + parentWidth()) - 30, (parentY() + parentOffset() + offset) + 5, (parentX() + parentWidth() - 10), (parentY() + parentOffset() + offset + parentHeight()) - 5, setting.getValue() ? ThemeUtils.getMainColor().getRGB() : Color.darkGray.getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if(isHovered(mouseX, mouseY)) {
            setting.toggle();
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}