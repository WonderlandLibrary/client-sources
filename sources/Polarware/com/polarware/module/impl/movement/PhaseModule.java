package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.phase.NormalPhase;
import com.polarware.module.impl.movement.phase.WatchdogAutoPhase;
import com.polarware.value.impl.ModeValue;

/**
 * @author Alan
 * @since 20/10/2021
 */

@ModuleInfo(name = "module.movement.phase.name", description = "module.movement.phase.description", category = Category.MOVEMENT)
public class PhaseModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new NormalPhase("Normal", this))
            .add(new WatchdogAutoPhase("Watchdog", this))
            .setDefault("Normal");

}