package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.step.*;
import com.polarware.value.impl.ModeValue;

@ModuleInfo(name = "module.movement.step.name", description = "module.movement.step.description", category = Category.MOVEMENT)
public class StepModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaStep("Vanilla", this))
            .add(new WatchdogStep("Watchdog", this))
            .add(new NCPStep("NCP", this))
            .add(new NCPPacketLessStep("NCP Packetless", this))
            .add(new VulcanStep("Vulcan", this))
            .add(new MatrixStep("Matrix", this))
            .add(new JumpStep("Jump", this))
            .setDefault("Vanilla");
}