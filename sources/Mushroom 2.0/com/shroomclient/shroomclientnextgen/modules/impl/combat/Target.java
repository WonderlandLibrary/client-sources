package com.shroomclient.shroomclientnextgen.modules.impl.combat;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;

@RegisterModule(
    name = "Target",
    uniqueId = "targetka",
    description = "Target Sorting For Killaura",
    category = ModuleCategory.Combat
)
public class Target extends Module {

    @ConfigOption(
        name = "Team Check 1",
        description = "Checks If The Names Are The Same Color",
        order = 2
    )
    public Boolean nameColorCheck = true;

    @ConfigOption(
        name = "Team Check 2",
        description = "Checks If The Players Are On The Same Team",
        order = 3
    )
    public Boolean onTeamCheck = true;

    @Override
    protected void onEnable() {
        ModuleManager.setEnabled(Target.class, false, true);
    }

    @Override
    protected void onDisable() {}
}
