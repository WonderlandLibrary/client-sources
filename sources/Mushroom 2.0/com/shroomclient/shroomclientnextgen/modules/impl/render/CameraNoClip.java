package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;

@RegisterModule(
    name = "Camera",
    uniqueId = "cameranoclip",
    description = "",
    category = ModuleCategory.Render,
    enabledByDefault = true
)
public class CameraNoClip extends Module {

    @ConfigOption(
        name = "No Clip",
        description = "Allows The Camera To Clip Through Blocks",
        order = 1
    )
    public static Boolean noClip = true;

    @ConfigOption(
        name = "Distance",
        description = "Changes The F5 Camera Distance",
        min = 0.1,
        max = 20,
        precision = 1,
        order = 2
    )
    public static Float distance = 5f;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
