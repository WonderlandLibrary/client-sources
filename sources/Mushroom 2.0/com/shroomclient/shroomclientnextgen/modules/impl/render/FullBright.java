package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;

@RegisterModule(
    name = "Full Bright",
    uniqueId = "fb",
    description = "Then God Said \"Let There Be Light\"",
    category = ModuleCategory.Render,
    enabledByDefault = true
)
public class FullBright extends Module {

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
