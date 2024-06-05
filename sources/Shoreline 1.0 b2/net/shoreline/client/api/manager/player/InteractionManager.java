package net.shoreline.client.api.manager.player;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.impl.event.TickEvent;
import net.shoreline.client.impl.event.network.PacketEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.util.Globals;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Set;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class InteractionManager implements Globals
{
    //
    private boolean blockCancel;
    // TODO: usingItem impl
    private boolean breakingBlock, usingItem;

    /**
     *
     */
    public InteractionManager()
    {
        Shoreline.EVENT_HANDLER.subscribe(this);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPacketOutbound(PacketEvent.Outbound event)
    {
        if (mc.player != null && mc.world != null)
        {
            if (event.getPacket() instanceof PlayerActionC2SPacket packet)
            {
                if (packet.getAction() == PlayerActionC2SPacket.Action.START_DESTROY_BLOCK)
                {
                    breakingBlock = true;
                }
                else if (packet.getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK
                        || packet.getAction() == PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK)
                {
                    breakingBlock = false;
                }
            }
            else if (event.getPacket() instanceof PlayerInteractItemC2SPacket)
            {
                usingItem = true;
            }
            else if (event.getPacket() instanceof PlayerInteractBlockC2SPacket)
            {
                usingItem = true;
            }
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onTick(TickEvent event)
    {
        if (event.getStage() == EventStage.PRE)
        {
            blockCancel = false;
        }
        else if (event.getStage() == EventStage.POST)
        {
            if (!blockCancel && mc.interactionManager != null)
            {
                mc.interactionManager.cancelBlockBreaking();
            }
        }
    }

    public void placeBlock(BlockPos pos, boolean strictDirection)
    {
        placeBlock(pos, Hand.MAIN_HAND, strictDirection);
    }

    public void placeBlock(BlockPos pos, Hand hand)
    {
        placeBlock(pos, hand, false);
    }

    public void placeBlock(BlockPos pos)
    {
        placeBlock(pos, Hand.MAIN_HAND);
    }

    /**
     *
     * @param pos
     * @param hand
     * @param strictDirection
     */
    public void placeBlock(BlockPos pos, Hand hand, boolean strictDirection)
    {
        BlockState state = mc.world.getBlockState(pos);
        if (!state.isReplaceable())
        {
            return;
        }
        BlockPos neighbor;
        Direction sideHit = getInteractDirection(pos, strictDirection);
        Vec3d hitVec = Vec3d.ofCenter(pos);
        if (sideHit == null)
        {
            neighbor = pos;
            sideHit = Direction.UP;
        }
        else
        {
            neighbor = pos.offset(sideHit);
            hitVec.add(sideHit.getOffsetX() * 0.5,
                sideHit.getOffsetY() * 0.5, sideHit.getOffsetZ() * 0.5);
        }
        BlockHitResult result = new BlockHitResult(hitVec, sideHit.getOpposite(), neighbor, false);
        mc.interactionManager.interactBlock(mc.player, hand, result);
        // Managers.NETWORK.sendSequencedPacket(id ->
        //        new PlayerInteractBlockC2SPacket(hand, result, id));
        Managers.NETWORK.sendPacket(new HandSwingC2SPacket(hand));
    }

    /**
     *
     * @param blockPos
     * @param strictDirection
     * @return
     */
    private Direction getInteractDirection(BlockPos blockPos, boolean strictDirection)
    {
        for (Direction dir : Direction.values())
        {
            BlockPos pos1 = blockPos.offset(dir);
            BlockState state = mc.world.getBlockState(pos1);
            //
            if (state.isAir() || !state.getFluidState().isEmpty())
            {
                continue;
            }
            if (strictDirection)
            {
                int x = blockPos.getX();
                int y = (int) Math.floor(Managers.POSITION.getY()
                        + mc.player.getStandingEyeHeight());
                int z = blockPos.getZ();
                Set<Direction> strictDirections = Managers.NCP.getPlaceDirectionsNCP(
                        x, y, z, pos1.getX(), pos1.getY(), pos1.getZ());
                if (!strictDirections.contains(dir.getOpposite()))
                {
                    continue;
                }
            }
            return dir;
        }
        return null;
    }

    /**
     *
     * @param pos
     * @param direction
     */
    public void breakBlock(BlockPos pos, Direction direction)
    {
        breakBlock(pos, direction, true);
    }

    /**
     *
     * @param pos
     * @param direction
     * @param swing
     */
    public void breakBlock(BlockPos pos, Direction direction, boolean swing)
    {
        // Create new instance
        BlockPos breakPos = pos;
        if (mc.interactionManager.isBreakingBlock())
        {
            mc.interactionManager.updateBlockBreakingProgress(breakPos, direction);
        }
        else
        {
            mc.interactionManager.attackBlock(breakPos, direction);
        }
        if (swing)
        {
            mc.player.swingHand(Hand.MAIN_HAND);
        }
        else
        {
            mc.player.networkHandler.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
        }
        blockCancel = true;
    }

    /**
     *
     *
     * @return
     */
    public boolean isBreakingBlock()
    {
        return breakingBlock;
    }
}
