package com.alan.clients.module.impl.movement;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.impl.NumberValue;

@ModuleInfo(aliases = {"module.movement.strafe.name"}, description = "module.movement.strafe.description", category = Category.MOVEMENT)
public class Strafe extends Module {
    private NumberValue strength = new NumberValue("Strength", this, 100, 1, 100, 1);
    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
    }

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        MoveUtil.partialStrafePercent(strength.getValue().floatValue());
    };
}