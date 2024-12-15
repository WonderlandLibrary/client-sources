package com.alan.clients.module.impl.ghost.wtap;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.module.impl.ghost.WTap;
import com.alan.clients.value.Mode;
import net.minecraft.entity.EntityLivingBase;

public final class SilentWTap extends Mode<WTap> {
    private EntityLivingBase target;

    public SilentWTap(String name, WTap parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (target != null && target.hurtTime == 9) {
            event.setSprinting(false);
        }
    };

    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        target = event.getTarget();
    };
}
