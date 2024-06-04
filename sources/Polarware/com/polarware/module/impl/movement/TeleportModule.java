package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.teleport.WatchdogTeleport;
import com.polarware.value.impl.ModeValue;

/**
 * @author Alan
 * @since 18/11/2022
 */

@ModuleInfo(name = "module.movement.teleport.name", description = "module.movement.teleport.description", category = Category.MOVEMENT)
public class TeleportModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new WatchdogTeleport("Watchdog", this))
            .setDefault("Vanilla");

}