package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;

public class GrimSpeed extends Mode<Speed> {

    public GrimSpeed(String name, Speed parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<StrafeEvent> strafe = event -> mc.theWorld.playerEntities.stream()
            .filter(entityPlayer -> entityPlayer != mc.thePlayer &&
                    mc.thePlayer.getEntityBoundingBox().expand(1, 1, 1)
                            .intersectsWith(entityPlayer.getEntityBoundingBox()))
            .forEach(entityPlayer -> MoveUtil.moveFlying(0.08));
}
