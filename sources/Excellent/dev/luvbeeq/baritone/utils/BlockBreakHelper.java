package dev.luvbeeq.baritone.utils;

import dev.luvbeeq.baritone.api.utils.IPlayerContext;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

/**
 * @author Brady
 * @since 8/25/2018
 */
public final class BlockBreakHelper {

    private final IPlayerContext ctx;
    private boolean didBreakLastTick;

    BlockBreakHelper(IPlayerContext ctx) {
        this.ctx = ctx;
    }

    public void stopBreakingBlock() {
        // The player controller will never be null, but the player can be
        if (ctx.player() != null && didBreakLastTick) {
            if (!ctx.playerController().hasBrokenBlock()) {
                // insane bypass to check breaking succeeded
                ctx.playerController().setHittingBlock(true);
            }
            ctx.playerController().resetBlockRemoving();
            didBreakLastTick = false;
        }
    }

    public void tick(boolean isLeftClick) {
        RayTraceResult trace = ctx.objectMouseOver();
        boolean isBlockTrace = trace != null && trace.getType() == RayTraceResult.Type.BLOCK;

        if (isLeftClick && isBlockTrace && trace instanceof BlockRayTraceResult wrapper) {

            if (!didBreakLastTick) {
                ctx.playerController().syncHeldItem();
                ctx.playerController().clickBlock(wrapper.getPos(), wrapper.getFace());
                ctx.player().swingArm(Hand.MAIN_HAND);
            }

//             Attempt to break the block
            if (ctx.playerController().onPlayerDamageBlock(wrapper.getPos(), wrapper.getFace())) {
                ctx.player().swingArm(Hand.MAIN_HAND);
            }

            if (ctx.playerController().hasBrokenBlock()) {
                ctx.playerController().setHittingBlock(false);
            }

            didBreakLastTick = true;
        } else if (didBreakLastTick) {
            stopBreakingBlock();
            didBreakLastTick = false;
        }
    }
}
