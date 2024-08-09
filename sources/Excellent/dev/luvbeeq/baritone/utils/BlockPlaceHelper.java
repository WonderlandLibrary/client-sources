package dev.luvbeeq.baritone.utils;

import dev.luvbeeq.baritone.Baritone;
import dev.luvbeeq.baritone.api.utils.IPlayerContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

public class BlockPlaceHelper {

    private final IPlayerContext ctx;
    private int rightClickTimer;

    BlockPlaceHelper(IPlayerContext playerContext) {
        this.ctx = playerContext;
    }

    public void tick(boolean rightClickRequested) {
        if (rightClickTimer > 0) {
            rightClickTimer--;
            return;
        }
        RayTraceResult mouseOver = ctx.objectMouseOver();
        if (!rightClickRequested || ctx.player().isRowingBoat() || mouseOver == null || mouseOver.getType() != RayTraceResult.Type.BLOCK) {
            return;
        }
        rightClickTimer = Baritone.settings().rightClickSpeed.value;
        for (Hand hand : Hand.values()) {
            if (ctx.playerController().processRightClickBlock(ctx.player(), ctx.world(), hand, (BlockRayTraceResult) mouseOver) == ActionResultType.SUCCESS) {
                ctx.player().swingArm(hand);
                return;
            }
            if (!ctx.player().getHeldItem(hand).isEmpty() && ctx.playerController().processRightClick(ctx.player(), ctx.world(), hand) == ActionResultType.SUCCESS) {
                return;
            }
        }
    }
}
