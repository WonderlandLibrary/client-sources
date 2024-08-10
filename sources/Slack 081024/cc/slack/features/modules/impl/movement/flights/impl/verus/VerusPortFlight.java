package cc.slack.features.modules.impl.movement.flights.impl.verus;

import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.movement.flights.IFlight;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;

public class VerusPortFlight implements IFlight {

    double startY;

    @Override
    public void onEnable() {
        startY = Math.floor(mc.thePlayer.posY);
    }

    @Override
    public void onMotion(MotionEvent event) {
        if (mc.thePlayer.motionY > 0.2) {
            mc.thePlayer.motionY = -0.0784000015258789;
        }

        if (MovementUtil.isMoving()) {
            double amplifier = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() : 0;
            double speedBoost = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? amplifier == 1 ? 0.035 : amplifier > 1 ? 0.035 * (amplifier / 2) : 0.035 / 2 : 0;
            double motionBoost = MovementUtil.isOnGround(0.15) && !mc.thePlayer.onGround ? 0.045 : 0;

            double boost = 0;

            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
                boost += 0.125;
        }




            if(MovementUtil.isOnGround(0.15) && boost == 0) {
                mc.thePlayer.motionY -= 0.0075;
            }

            if(mc.thePlayer.moveStrafing == 0)
                MovementUtil.strafe((float) (0.3345 + speedBoost + motionBoost + boost));
            else
                MovementUtil.strafe((float) (0.333 + speedBoost + motionBoost + boost));

        } else {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            }
            MovementUtil.strafe(0);
        }
    }

    @Override
    public void onCollide(CollideEvent event) {
        if (event.getBlock() instanceof BlockAir && event.getY() <= startY)
            event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 1, startY, event.getZ() + 1));
    }

    @Override
    public String toString() {
        return "Verus Port";
    }
}
