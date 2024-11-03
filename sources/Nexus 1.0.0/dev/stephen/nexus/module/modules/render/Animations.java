package dev.stephen.nexus.module.modules.render;

import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.setting.impl.ModeSetting;
import dev.stephen.nexus.module.setting.impl.NumberSetting;

public class Animations extends Module {
    public static final ModeSetting mode = new ModeSetting("Mode", "1.7", "1.7", "Exhi");
    public static final NumberSetting xOffset = new NumberSetting("X Offset", -5, 5, 0, 0.01);
    public static final NumberSetting yOffset = new NumberSetting("Y Offset", -5, 5, 0, 0.01);
    public static final NumberSetting itemScale = new NumberSetting("Item Scale", -5, 5, 0, 0.01);
    public static final NumberSetting swingSpeed = new NumberSetting("Swing Speed", 1, 50, 12, 1);

    public Animations() {
        super("Animations", "Pre 1.9 block animation", 0, ModuleCategory.RENDER);
        this.addSettings(mode, xOffset, yOffset, itemScale, swingSpeed);
    }
}
