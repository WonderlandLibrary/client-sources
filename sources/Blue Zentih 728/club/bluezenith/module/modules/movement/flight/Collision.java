package club.bluezenith.module.modules.movement.flight;

import club.bluezenith.events.impl.CollisionEvent;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.modules.movement.Flight;
import club.bluezenith.util.player.MovementUtil;
import net.minecraft.util.AxisAlignedBB;

public class Collision implements IFlight {

    double startY;

    @Override
    public void onCollision(CollisionEvent event, Flight flight) {
        if (event.pos != null && event.pos.getY() < mc.thePlayer.posY && !mc.thePlayer.isSneaking() && !flight.sameY.get() || (event.pos.getY() < startY || !MovementUtil.areMovementKeysPressed())) {
            event.boundingBox = AxisAlignedBB.fromBounds(-5, -1,  -5, 5, 1, 5).offset(event.pos.getX(), event.pos.getY(), event.pos.getZ());
            if(!mc.thePlayer.isSprinting())
            mc.thePlayer.setSprinting(true);
        }
    }

    @Override
    public void onPlayerUpdate(UpdatePlayerEvent event, Flight flight) {
        if(mc.thePlayer.isCollidedVertically) {
            if(MovementUtil.areMovementKeysPressed()) {
                if (mc.thePlayer.motionY < 0) {
                    MovementUtil.setSpeed((float) (flight.speed.get() * (mc.thePlayer.motionY * -18)));
                } else MovementUtil.setSpeed(0.3F);
            }
            if(!flight.sameY.get() || mc.thePlayer.posY < startY) {
                if(mc.thePlayer.ticksExisted % 10 != 0) return;
                mc.thePlayer.jump();
                mc.thePlayer.motionY = 0;
                event.onGround = false;
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.25, mc.thePlayer.posZ);
            }
        } else if(startY == 0.0D && mc.thePlayer.motionY < 0.2D) {
            startY = mc.thePlayer.posY;
        }
    }

    @Override
    public void onMoveEvent(MoveEvent event, Flight flight) {

    }

    @Override
    public void onEnable(Flight flight) {
        if(flight.motionYOnStart.get() != 0F) {
            mc.thePlayer.jump(flight.motionYOnStart.get());
        }
    }

    @Override
    public void onDisable(Flight flight) {
        startY = 0.0;
    }
}
