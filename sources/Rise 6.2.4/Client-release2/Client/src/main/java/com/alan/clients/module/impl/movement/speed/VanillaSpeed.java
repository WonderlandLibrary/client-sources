package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.NumberValue;

public class VanillaSpeed extends Mode<Speed> {
    public VanillaSpeed(String name, Speed parent) {
        super(name, parent);
    }
    private final NumberValue speed = new NumberValue("Speed", this, 1, 0.1, 9.5, 0.1);



    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (MoveUtil.isMoving() && mc.thePlayer.onGround) {
            this.mc.thePlayer.jump();
        }

        event.setSpeed(speed.getValue().floatValue());

    };
}