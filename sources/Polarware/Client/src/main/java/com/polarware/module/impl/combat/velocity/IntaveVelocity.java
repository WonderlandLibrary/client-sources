package com.polarware.module.impl.combat.velocity;

import com.polarware.component.impl.player.RotationComponent;
import com.polarware.event.impl.other.TickEvent;
import com.polarware.module.impl.combat.VelocityModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.util.RayCastUtil;
import com.polarware.value.Mode;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.MovingObjectPosition;

public final class IntaveVelocity extends Mode<VelocityModule> {

    private boolean attacked;

    public IntaveVelocity(String name, VelocityModule parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<TickEvent> onTickEvent = event -> {
        if ((getParent().onSwing.getValue() || getParent().onSprint.getValue()) && mc.thePlayer.swingProgress == 0) return;

        if (mc.objectMouseOver!= null && mc.objectMouseOver.typeOfHit.equals(MovingObjectPosition.MovingObjectType.ENTITY) && mc.thePlayer.hurtTime > 0 && !attacked) {
            mc.thePlayer.motionX *= 0.6D;
            mc.thePlayer.motionZ *= 0.6D;
            mc.thePlayer.setSprinting(false);
        }

        attacked = false;
    };

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        attacked = true;
    };
}