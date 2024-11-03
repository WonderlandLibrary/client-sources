package dev.stephen.nexus.module.modules.movement;

import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;

public class KeepSprint extends Module {
    public static final BooleanSetting sprint = new BooleanSetting("Sprint",true);

    public KeepSprint() {
        super("KeepSprint", "Removes attack slowdown", 0, ModuleCategory.MOVEMENT);
        this.addSetting(sprint);
    }
}
