package dev.stephen.nexus.module.modules.other;

import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;

public class AntiCheat extends Module {
    public static final BooleanSetting checkSelf = new BooleanSetting("Check self", true);

    public AntiCheat() {
        super("AntiCheat", "Detects if other players are cheating", 0, ModuleCategory.OTHER);
        this.addSettings(checkSelf);
    }
}