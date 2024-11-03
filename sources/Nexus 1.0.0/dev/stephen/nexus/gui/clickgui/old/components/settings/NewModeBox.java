package dev.stephen.nexus.gui.clickgui.old.components.settings;

import dev.stephen.nexus.gui.clickgui.old.components.ModuleButton;
import dev.stephen.nexus.module.setting.Setting;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;
import dev.stephen.nexus.utils.render.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public final class NewModeBox extends RenderableSetting {
    public final NewModeSetting setting;

    public NewModeBox(ModuleButton parent, Setting setting, int offset) {
        super(parent, setting, offset);
        this.setting = (NewModeSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        TextRenderer.drawMinecraftText(setting.getName() + ": " + setting.getCurrentMode().getName(), context, parentX() + 6, (parentY() + parentOffset() + offset) + 6, Color.WHITE.getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            setting.cycle();
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}