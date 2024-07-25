package club.bluezenith.module.modules.movement.flight;

import club.bluezenith.events.impl.CollisionEvent;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.modules.movement.Flight;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.player.MovementUtil;
import net.minecraft.util.AxisAlignedBB;

import static java.lang.Math.round;

public class OldVerus implements IFlight {

    public final float[] moveSpeed = new float[3];
    final MillisTimer timer = new MillisTimer();
    public int f = 3;

    @Override
    public void onCollision(CollisionEvent event, Flight flight) {
        if(mc.thePlayer == null || mc.thePlayer.ticksExisted < 20) return;
        if(!event.block.getMaterial().isSolid() && event.pos.getY() < mc.thePlayer.posY) {
            if(moveSpeed[2] > f) {
                event.boundingBox = AxisAlignedBB.fromBounds(-1, -1, -1, 1, 0.42, 1).offset(event.pos.getX(), Math.round(event.pos.getY()), event.pos.getZ());
            }
        }
    }

    @Override
    public void onPlayerUpdate(UpdatePlayerEvent event, Flight flight) {
        if(MovementUtil.areMovementKeysPressed() && moveSpeed[2] > f) {
            float mv = moveSpeed[0] / 0.1536f;
            if(mv <= 2.213541 && moveSpeed[1] != 1) {
                moveSpeed[0] += 0.01f;
            } else if(mv >= 0.1536f) {
                moveSpeed[1] = 1;
                moveSpeed[0] -= 0.03;
            }
            MovementUtil.setSpeed(mv);

        } else if(moveSpeed[2] <= f) {
            if(moveSpeed[2] <= f) {
                event.onGround = false;
                if(timer.hasTicksPassed(12)) {
                    mc.thePlayer.jump();
                    timer.reset();
                    moveSpeed[2]++;
                }
            } else if(timer.hasTicksPassed(12)) {
                event.onGround = true;
                moveSpeed[2]++;
                timer.reset();
                mc.thePlayer.setPosition(mc.thePlayer.posX, round(mc.thePlayer.posY) + 0.42, mc.thePlayer.posZ);
            }
            if(mc.thePlayer.hurtTime == 9) {
                moveSpeed[2] = f + 1;
            }
        } else {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        }
    }

    @Override
    public void onMoveEvent(MoveEvent event, Flight flight) {
        if(moveSpeed[2] <= f && flight.mode.is("OldVerus")) {
            event.x = 0;
            event.z = 0;
        }
    }

    @Override
    public void onEnable(Flight flight) {
        moveSpeed[0] = 0;
        moveSpeed[1] = 0;
        moveSpeed[2] = 4;
    }

    @Override
    public void onDisable(Flight flight) {
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        moveSpeed[0] = 0;
    }
}
