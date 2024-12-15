package com.alan.clients.module.impl.movement.speed;

import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.MoveInputEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.util.player.DamageUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.Mode;
import net.minecraft.util.Vec3;

public class SparkySpeed extends Mode<Speed> {
    public SparkySpeed(String name, Speed parent) {
        super(name, parent);
    }

    public Vec3 position = new Vec3(0, 0, 0);
    int jumps = 0;
    float forward = 0;
    float strafe = 0;

    @Override
    public void onEnable() {
        DamageUtil.damagePlayer(DamageUtil.DamageType.POSITION, 3.42F, 1, false, false);
        mc.timer.timerSpeed = 0.2F;
        jumps = 0;
    }

    @EventLink(value = Priorities.HIGH)
    Listener<MoveInputEvent> moveInput = event -> {
        forward = event.getForward();
        strafe = event.getStrafe();
    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        MoveUtil.strafe();

        if (MoveUtil.isMoving() && mc.thePlayer.onGround) {
            MoveUtil.strafe();
            mc.thePlayer.jump();
            jumps++;
        }

        if (mc.thePlayer.offGroundTicks == 5) {
            mc.thePlayer.motionY = -0.09800000190734864;

        } else if (mc.thePlayer.onGround) {

            mc.thePlayer.motionY = .9;
        } else if (mc.thePlayer.onGround) {

            mc.thePlayer.motionY = .42;

        } else {
            event.setSpeed(.6);
        }

        if (mc.thePlayer.hurtTime > 0) {

            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        }
    };

    @EventLink(value = Priorities.LOW)
    Listener<PreUpdateEvent> onPreUpdate = event -> {
        RotationComponent.setRotations(new Vector2f((float) Math.toDegrees(MoveUtil.direction(forward, strafe)), mc.thePlayer.rotationPitch),
                10, MovementFix.NORMAL);
    };
}

