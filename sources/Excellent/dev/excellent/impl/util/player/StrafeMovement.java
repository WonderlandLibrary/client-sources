package dev.excellent.impl.util.player;

import dev.excellent.api.event.impl.player.MoveEvent;
import dev.excellent.api.interfaces.game.IMinecraft;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;

@Getter
@Setter
public class StrafeMovement implements IMinecraft {
    private double oldSpeed, contextFriction;
    private boolean needSwap;
    private boolean needSprintState;
    private int counter, noSlowTicks;

    public double calculateSpeed(final MoveEvent move, boolean damageBoost, boolean hasTime, boolean autoJump,
                                 float damageSpeed) {

        final boolean fromGround = mc.player.isOnGround();
        final boolean toGround = move.isToGround();
        final boolean jump = move.getMotion().y > 0;
        final float speedAttributes = getAIMoveSpeed(mc.player);
        final float frictionFactor = getFrictionFactor(mc.player, move);
        float n6 = mc.player.isPotionActive(Effects.JUMP_BOOST) && mc.player.isHandActive() ? 0.88f : 0.91F;

        if (fromGround) {
            n6 = frictionFactor;
        }
        final float n7 = 0.16277136f / (n6 * n6 * n6);
        float n8;
        if (fromGround) {
            n8 = speedAttributes * n7;
            if (jump) {
                n8 += 0.2f;
            }
        } else {
            n8 = (damageBoost && hasTime && (autoJump || mc.gameSettings.keyBindJump.isKeyDown()) ? damageSpeed : 0.0255f);
        }
        boolean noslow = false;
        double max2 = oldSpeed + n8;
        double max = 0.0;
        if (mc.player.isHandActive() && !jump) {
            double n10 = oldSpeed + n8 * 0.25;
            double motionY2 = move.getMotion().y;
            if (motionY2 != 0.0 && Math.abs(motionY2) < 0.08) {
                n10 += 0.055;
            }
            if (max2 > (max = Math.max(0.043, n10))) {
                noslow = true;
                ++noSlowTicks;
            } else {
                noSlowTicks = Math.max(noSlowTicks - 1, 0);
            }
        } else {
            noSlowTicks = 0;
        }
        if (noSlowTicks > 3) {
            max2 = max - (mc.player.isPotionActive(Effects.JUMP_BOOST) && mc.player.isHandActive() ? 0.3 : 0.019);
        } else {
            max2 = Math.max(noslow ? 0 : 0.25, max2) - (counter++ % 2 == 0 ? 0.001 : 0.002);
        }
        contextFriction = n6;
        if (!toGround && !fromGround) {
            needSwap = true;
        }
        if (!fromGround && !toGround) {
            needSprintState = !mc.player.serverSprintState;
        }
        if (toGround && fromGround) {
            needSprintState = false;
        }
        return max2;
    }

    public void postMove(final double horizontal) {
        oldSpeed = horizontal * contextFriction;
    }

    private float getAIMoveSpeed(final ClientPlayerEntity contextPlayer) {
        boolean prevSprinting = contextPlayer.isSprinting();
        contextPlayer.setSprinting(false);
        float speed = contextPlayer.getAIMoveSpeed() * 1.3f;
        contextPlayer.setSprinting(prevSprinting);
        return speed;
    }

    private float getFrictionFactor(final ClientPlayerEntity contextPlayer, final MoveEvent move) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        blockpos$mutable.setPos(move.getFrom().x, move.getAabbFrom().minY - 1.0D, move.getFrom().z);

        return contextPlayer.world.getBlockState(blockpos$mutable).getBlock().slipperiness * 0.91F;
    }
}