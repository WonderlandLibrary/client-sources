package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;

@RegisterModule(
    name = "Zoom",
    uniqueId = "zoom",
    description = "Optifine Zoom (bind this)",
    category = ModuleCategory.Render
)
public class Zoom extends Module {

    @Override
    protected void onEnable() {
        if (C.mc.options == null) return;

        C.mc.options.smoothCameraEnabled = true;
    }

    @Override
    protected void onDisable() {
        if (C.mc.options == null) return;

        C.mc.options.smoothCameraEnabled = false;
    }
}
