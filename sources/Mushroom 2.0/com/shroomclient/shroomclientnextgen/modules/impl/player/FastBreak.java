package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;

@RegisterModule(
    name = "Fast Break",
    uniqueId = "fastbreak",
    description = "Break Blocks At A Faster Speed",
    category = ModuleCategory.Player
)
public class FastBreak extends Module {

    @ConfigOption(
        name = "Break Speed",
        description = "Speed Multiplier",
        max = 3,
        precision = 2,
        order = 1
    )
    public static Float Speed = 1.4F;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    public static float breakSpeed() {
        return (ModuleManager.isEnabled(FastBreak.class) ? Speed : 1);
    }
}
