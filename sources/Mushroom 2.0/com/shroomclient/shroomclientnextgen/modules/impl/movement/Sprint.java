package com.shroomclient.shroomclientnextgen.modules.impl.movement;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;

@RegisterModule(
    name = "Sprint",
    uniqueId = "sprint",
    description = "Auto Sprint",
    category = ModuleCategory.Movement,
    enabledByDefault = true
)
public class Sprint extends Module {

    @ConfigOption(
        name = "Only Sprint While Moving",
        description = "",
        order = 1
    )
    public static Boolean onlySprintwwhileMoving = true;

    @ConfigOption(
        name = "Omni Sprint",
        description = "Sprint Backwards",
        order = 1
    )
    public static Boolean omniSprint = true;

    @ConfigOption(
        name = "Scaffold Check",
        description = "Disables When Scaffolding",
        order = 1
    )
    public static Boolean scaffold = true;

    public static boolean shouldSprint() {
        if (C.p().isUsingItem() && !NoSlow.doNoSlow) return false;
        if (onlySprintwwhileMoving && !MovementUtil.isMoving()) return false;
        if (
            scaffold &&
            (ModuleManager.isEnabled(HypixelScaffold.class) ||
                ModuleManager.isEnabled(Scaffold.class))
        ) return false;
        if (ModuleManager.isEnabled(Scaffold.class)) {
            if (
                Scaffold.sprintMode == Scaffold.MovementMode.Legit ||
                Scaffold.sprintMode == Scaffold.MovementMode.Eagle
            ) return false;
        }

        if (
            ModuleManager.isEnabled(InventoryMove.class) &&
            InventoryMove.mode == InventoryMove.Mode.Hypixel &&
            C.mc.currentScreen != null
        ) return false;
        return ModuleManager.isEnabled(Sprint.class);
    }

    public static boolean shouldOverrideSprint() {
        if (
            ModuleManager.isEnabled(Scaffold.class) &&
            Scaffold.scaffoldMode != Scaffold.ModeStink.Hypixel_New
        ) {
            if (
                Scaffold.sprintMode == Scaffold.MovementMode.Legit ||
                Scaffold.sprintMode == Scaffold.MovementMode.Eagle
            ) return true;
        }

        if (
            ModuleManager.isEnabled(InventoryMove.class) &&
            InventoryMove.mode == InventoryMove.Mode.Hypixel &&
            C.mc.currentScreen != null
        ) return true;
        return ModuleManager.isEnabled(Sprint.class) && omniSprint;
    }

    @SubscribeEvent
    public void onMotion(MotionEvent.Pre event) {
        if (!omniSprint && !C.p().isSprinting() && C.p().forwardSpeed > 0) C.p()
            .setSprinting(true);
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
