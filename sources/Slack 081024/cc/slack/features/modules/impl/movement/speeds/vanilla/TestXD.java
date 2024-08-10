package cc.slack.features.modules.impl.movement.speeds.vanilla;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.PlayerUtil;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class TestXD implements ISpeed {

    double moveSpeed;
    int airTicks;

    @Override
    public void onEnable() {
        moveSpeed = 0;
        airTicks = 0;
    }

    @Override
    public void onMove(MoveEvent event) {
        if (mc.thePlayer.onGround) {
            if (MovementUtil.isMoving()) event.setY(0.315F);
            moveSpeed = 0.64f;
            airTicks = 0;
        } else {
            if (airTicks == 0)
                moveSpeed *= PlayerUtil.BASE_GROUND_FRICTION * 1.01;

            airTicks++;
        }

        MovementUtil.setSpeed(event, moveSpeed);
        moveSpeed *= PlayerUtil.MOVE_FRICTION;
    }

    @Override
    public String toString() {
        return "AstralMC";
    }

}
