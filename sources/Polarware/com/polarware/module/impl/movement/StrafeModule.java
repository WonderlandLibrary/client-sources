package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.util.player.MoveUtil;

/**
 * @author Alan Jr.
 * @since 9/17/2022
 */
@ModuleInfo(name = "module.movement.strafe.name", description = "module.movement.strafe.description", category = Category.MOVEMENT)
public class StrafeModule extends Module {

    @Override
    protected void onDisable() {
        mc.timer.timerSpeed = 1.0F;
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        MoveUtil.strafe();
    };
}