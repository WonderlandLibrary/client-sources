package net.shoreline.client.impl.manager.player;

import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.shoreline.client.Shoreline;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.Globals;
import net.shoreline.client.util.player.RotationUtil;
import net.shoreline.client.util.world.SneakBlocks;

import java.util.Set;

/**
 * @author linus
 * @since 1.0
 */
public class InteractionManager implements Globals {
    //
    private boolean blockCancel;
    // TODO: usingItem impl
    private boolean breakingBlock, usingItem;

    /**
     *
     */
    public InteractionManager() {
        Shoreline.EVENT_HANDLER.subscribe(this);
    }

    /**
     * @param event
     */
    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event) {
        if (mc.player != null && mc.world != null) {
            if (event.getPacket() instanceof PlayerActionC2SPacket packet) {
                if (packet.getAction() == PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) {
                    breakingBlock = true;
                } else if (packet.getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK
                        || packet.getAction() == PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK) {
                    breakingBlock = false;
                }
            } else if (event.getPacket() instanceof PlayerInteractItemC2SPacket) {
                usingItem = true;
            } else if (event.getPacket() instanceof PlayerInteractBlockC2SPacket) {
                usingItem = true;
            }
        }
    }

    /**
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event) {
        if (event.getStage() == EventStage.PRE) {
            blockCancel = false;
        } else if (event.getStage() == EventStage.POST) {
            if (!blockCancel && mc.interactionManager != null) {
                mc.interactionManager.cancelBlockBreaking();
            }
        }
    }

    public float[] placeBlock(BlockPos pos, boolean rotate, boolean strictDirection, boolean grim) {
        return placeBlock(pos, Hand.MAIN_HAND, rotate, strictDirection, grim);
    }

    /**
     * @param pos
     * @param hand
     * @param strictDirection
     */
    public float[] placeBlock(BlockPos pos, Hand hand, boolean rotate, boolean strictDirection, boolean grim)
    {
        final BlockState state = mc.world.getBlockState(pos);
        // Get the first usable side
        final Direction side = getInteractDirection(pos, strictDirection);
        if (!state.isReplaceable() || side == null)
        {
            return null;
        }
        final BlockPos neighbor = pos.offset(side.getOpposite());
        final BlockState state1 = mc.world.getBlockState(neighbor);
        final Vec3d rotateVec = neighbor.toCenterPos();
        // Calculate rotations towards block
        float[] angles = new float[] { Managers.ROTATION.getServerYaw(), Managers.ROTATION.getServerPitch() };
        if (rotate)
        {
            angles = RotationUtil.getRotationsTo(mc.player.getEyePos(), rotateVec);
        }
        BlockHitResult hitResult = new BlockHitResult(rotateVec, side, neighbor, false);
        boolean sneaking = !mc.player.isSneaking() && SneakBlocks.isSneakBlock(state1.getBlock());
        if (sneaking) {
            Managers.NETWORK.sendPacket(new ClientCommandC2SPacket(mc.player,
                    ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
        }
        if (rotate)
        {
            Managers.ROTATION.setRotationSilent(angles[0], angles[1], grim);
        }
        final ActionResult actionResult = mc.interactionManager.interactBlock(mc.player, hand, hitResult);
        final boolean success = actionResult.isAccepted();
        if (success && actionResult.shouldSwingHand())
        {
            mc.player.swingHand(hand);
        }
        if (sneaking) {
            Managers.NETWORK.sendPacket(new ClientCommandC2SPacket(mc.player,
                    ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
        }
        return angles;
//        BlockState state = mc.world.getBlockState(pos);
//        Direction sideHit = getInteractDirection(pos, strictDirection);
//        if (!state.isReplaceable() || sideHit == null) {
//            return null;
//        }
//        BlockPos offsetPos = pos.offset(sideHit.getOpposite());
//        Vec3d rotateVec = Vec3d.ofCenter(offsetPos);
//        BlockState state1 = mc.world.getBlockState(offsetPos);
//        rotateVec = rotateVec.add(sideHit.getOffsetX() * 0.5, sideHit.getOffsetY() * 0.5, sideHit.getOffsetZ() * 0.5);
//        float[] rotations = RotationUtil.getRotationsTo(mc.player.getEyePos(), rotateVec);
//
//        HitResult result = RayCastUtil.rayCast(4.0, rotations);//new BlockHitResult(hitVec, sideHit, offsetPos, false);
//        if (!(result instanceof BlockHitResult blockHitResult))
//        {
//            return null;
//        }
//
//        if (rotate) {
////            Managers.NETWORK.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(
////                    rotations[0], rotations[1], mc.player.isOnGround()));
//            Managers.ROTATION.submitInstant(rotations[0], rotations[1], true);
//        }
//
//        boolean sneaking = !mc.player.isSneaking() && SneakBlocks.isSneakBlock(state1.getBlock());
//        if (sneaking) {
//            Managers.NETWORK.sendPacket(new ClientCommandC2SPacket(mc.player,
//                    ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
//        }
//
//        final ActionResult actionResult = mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, blockHitResult);
//        final boolean success = actionResult.equals(ActionResult.SUCCESS);
//        if (success && actionResult.shouldSwingHand())
//        {
//            mc.player.swingHand(Hand.MAIN_HAND);
//        }
//
////        Managers.NETWORK.sendSequencedPacket(id ->
////                new PlayerInteractBlockC2SPacket(hand, blockHitResult, id));
////        // Managers.NETWORK.sendPacket(new HandSwingC2SPacket(hand));
////        mc.player.swingHand(hand);
//
//        if (sneaking) {
//            Managers.NETWORK.sendPacket(new ClientCommandC2SPacket(mc.player,
//                    ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
//        }
//        return success ? rotations : null;
    }

    /**
     * @param blockPos
     * @param strictDirection
     * @return
     */
    public Direction getInteractDirection(BlockPos blockPos, boolean strictDirection) {
        Set<Direction> ncpDirections = Managers.NCP.getPlaceDirectionsNCP(mc.player.getEyePos(), blockPos.toCenterPos());
        Direction interactDirection = null;
        for (Direction dir : Direction.values()) {
            BlockPos pos1 = blockPos.offset(dir);
            BlockState state = mc.world.getBlockState(pos1);
            //
            if (state.isAir() || !state.getFluidState().isEmpty()) {
                continue;
            }
            if (strictDirection && !ncpDirections.contains(dir.getOpposite())) {
                continue;
            }
            interactDirection = dir;
            break;
        }
        if (interactDirection == null) {
            return null;
        }
        return interactDirection.getOpposite();
    }

    /**
     * @param pos
     * @param direction
     */
    public void breakBlock(BlockPos pos, Direction direction) {
        breakBlock(pos, direction, true);
    }

    /**
     * @param pos
     * @param direction
     * @param swing
     */
    public void breakBlock(BlockPos pos, Direction direction, boolean swing) {
        // Create new instance
        BlockPos breakPos = pos;
        if (mc.interactionManager.isBreakingBlock()) {
            mc.interactionManager.updateBlockBreakingProgress(breakPos, direction);
        } else {
            mc.interactionManager.attackBlock(breakPos, direction);
        }
        if (swing) {
            mc.player.swingHand(Hand.MAIN_HAND);
        } else {
            mc.player.networkHandler.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
        }
        blockCancel = true;
    }

    /**
     * @return
     */
    public boolean isBreakingBlock() {
        return breakingBlock;
    }
}
