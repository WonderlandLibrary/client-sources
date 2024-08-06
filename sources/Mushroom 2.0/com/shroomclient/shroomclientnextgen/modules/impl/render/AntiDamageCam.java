package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;

@RegisterModule(
    name = "No HurtCam",
    uniqueId = "antidamagecam",
    description = "Removes Camera Shake When Damaged",
    category = ModuleCategory.Render
)
public class AntiDamageCam extends Module {

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
