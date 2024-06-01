package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.wallclimb.*;
import com.polarware.value.impl.ModeValue;

/**
 * @author Alan
 * @since 20/10/2021
 */
@ModuleInfo(name = "module.movement.wallclimb.name", description = "module.movement.wallclimb.description", category = Category.MOVEMENT)
public class WallClimbModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VulcanWallClimb("Vulcan", this))
            .add(new VerusWallClimb("Verus", this))
            .add(new KauriWallClimb("Kauri", this))
            .add(new WatchdogWallClimb("Watchdog", this))
            .add(new MineMenClubWallClimb("MineMenClub", this))
            .setDefault("Vulcan");
}