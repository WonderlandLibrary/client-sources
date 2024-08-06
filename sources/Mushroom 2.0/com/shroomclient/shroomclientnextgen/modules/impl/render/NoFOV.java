package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;

@RegisterModule(
    name = "No FOV",
    uniqueId = "nofov",
    description = "Makes The FOV Static",
    category = ModuleCategory.Render
)
public class NoFOV extends Module {

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
    // abstractclientplayerentity mixin

}
