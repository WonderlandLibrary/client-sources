package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;

@RegisterModule(
    name = "Ambience",
    uniqueId = "ambience",
    description = "Sets A Custom Time Of Day",
    category = ModuleCategory.Render
)
public class Ambience extends Module {

    @ConfigOption(
        name = "Time Of Day",
        description = "",
        min = 1,
        max = 24000,
        order = 1
    )
    public static Integer TOD = 12000;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
