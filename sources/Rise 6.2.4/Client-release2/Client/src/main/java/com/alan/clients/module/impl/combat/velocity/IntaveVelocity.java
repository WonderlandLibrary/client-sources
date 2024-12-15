package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.HitSlowDownEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.value.Mode;

public final class IntaveVelocity extends Mode<Velocity> {

    private boolean attacked, slowDown;

    public IntaveVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (getParent().onSwing.getValue() && !mc.thePlayer.isSwingInProgress) return;

        if (attacked && !slowDown && mc.thePlayer.isSprinting()) {
            mc.thePlayer.motionX *= 0.6D;
            mc.thePlayer.motionZ *= 0.6D;
            mc.thePlayer.setSprinting(false);
        }

        attacked = false;
        slowDown = false;
    };

    @EventLink
    public final Listener<HitSlowDownEvent> onHitSlowDown = event -> {
        slowDown = true;
    };

    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        attacked = true;
    };
}
