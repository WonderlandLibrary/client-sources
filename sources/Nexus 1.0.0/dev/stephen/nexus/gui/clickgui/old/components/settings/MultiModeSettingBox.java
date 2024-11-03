package dev.stephen.nexus.gui.clickgui.old.components.settings;

import dev.stephen.nexus.gui.clickgui.old.components.ModuleButton;
import dev.stephen.nexus.module.setting.Setting;
import dev.stephen.nexus.module.setting.impl.MultiModeSetting;
import net.minecraft.client.gui.DrawContext;

public class MultiModeSettingBox extends RenderableSetting {
    public final MultiModeSetting setting;

    public MultiModeSettingBox(ModuleButton parent, Setting setting, int offset) {
        super(parent, setting, offset);
        this.setting = (MultiModeSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }
}
