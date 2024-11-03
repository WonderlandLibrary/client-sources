package dev.stephen.nexus.module.modules.movement;

import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;

public class MoveFix extends Module {
    public static final BooleanSetting silent = new BooleanSetting("Silent", false);

    public MoveFix() {
        super("MoveFix", "Fixes your movement", 0, ModuleCategory.MOVEMENT);
        this.addSettings(silent);
    }
}
