package dev.nexus.modules.impl.render;

import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.modules.setting.NumberSetting;

public class Theme extends Module {
    public final NumberSetting red = new NumberSetting("Red", 0, 255, 164, 1);
    public final NumberSetting green = new NumberSetting("Green", 0, 255, 0, 1);
    public final NumberSetting blue = new NumberSetting("Blue", 0, 255, 0, 1);

    public Theme() {
        super("Theme", 0, ModuleCategory.RENDER);
        this.addSettings(red, green, blue);
    }

    @Override
    public void onEnable() {
        toggle();
        super.onEnable();
    }
}
