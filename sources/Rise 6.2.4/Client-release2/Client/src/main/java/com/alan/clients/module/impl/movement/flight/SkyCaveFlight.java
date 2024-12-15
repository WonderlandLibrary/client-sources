package com.alan.clients.module.impl.movement.flight;

import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.MoveEvent;
import com.alan.clients.module.impl.movement.Flight;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.NumberValue;

public final class SkyCaveFlight extends Mode<Flight> {
    public SkyCaveFlight(String name, Flight parent) {
        super(name, parent);
    }

    private final NumberValue speed = new NumberValue("Speed", this, 1.2, 0.8, 10, 0.05);
    private final NumberValue damageSpeed = new NumberValue("Damage Speed", this, 5, 0.8, 10, 0.05);

    private double moveSpeed;
    private int stage;

    @Override
    public void onEnable() {
        moveSpeed = 0;
        stage = mc.thePlayer.onGround ? 0 : -1;
    }

    @EventLink(Priorities.VERY_HIGH)
    private final Listener<MoveEvent> onMoveEvent = event -> {

        if (!MoveUtil.isMoving() || mc.thePlayer.isCollidedHorizontally) {
            stage = -1;
        }

        if (mc.thePlayer.ticksSinceVelocity == 1) {
            moveSpeed = damageSpeed.getValue().doubleValue();
        }

        switch (stage) {
            case -1:
                mc.thePlayer.motionY = 0;
                event.setPosY(-0.00001);
                break;
            case 0:
                moveSpeed = 0.3;
                break;
            case 1:
                if (mc.thePlayer.onGround) {
                    event.setPosY(mc.thePlayer.motionY = 0.3999);
                    moveSpeed *= 2.14;
                }
                break;
            case 2:
                moveSpeed = speed.getValue().doubleValue();
                break;
            default:
                moveSpeed -= moveSpeed / 109;
                mc.thePlayer.motionY = 0;
                event.setPosY(-0.00001);
                break;
        }

        if (mc.thePlayer.ticksExisted % 20 == 0) {
            event.setPosY(-0.035);
        }

        if (stage != -1) {
            mc.thePlayer.jumpMovementFactor = 0F;
            MoveUtil.setSpeedMoveEvent(event, Math.max(moveSpeed, MoveUtil.getAllowedHorizontalDistance()));
            stage++;
        }
    };
}

