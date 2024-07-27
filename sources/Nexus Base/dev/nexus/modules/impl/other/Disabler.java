package dev.nexus.modules.impl.other;

import dev.nexus.modules.Module;
import dev.nexus.modules.ModuleCategory;
import dev.nexus.modules.setting.BooleanSetting;

public class Disabler extends Module {
    public final BooleanSetting joinClaim = new BooleanSetting("Join Claim", true);

    public Disabler() {
        super("Disabler", 0, ModuleCategory.OTHER);
        this.addSettings(joinClaim);
    }
}
