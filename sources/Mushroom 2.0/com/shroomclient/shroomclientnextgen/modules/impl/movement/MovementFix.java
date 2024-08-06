package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.modules.Module;

/*
@RegisterModule(
        name = "Movement Fix",
        uniqueId = "movefix",
        description = "Makes Your Movement Reflect Your Serverside Yaw To Bypass Antihakes",
        category = ModuleCategory.Movement
)
 */
public class MovementFix extends Module {

    public static boolean shouldMoveFix() {
        return false;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
