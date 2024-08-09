package wtf.shiyeno.events.impl.player;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import wtf.shiyeno.events.Event;

public class EventLiquidSolid extends Event  {
    private final FlowingFluidBlock blockLiquid;
    private final BlockPos pos;
    private VoxelShape collision;

    public EventLiquidSolid(FlowingFluidBlock blockLiquid, BlockPos pos) {
        this.blockLiquid = blockLiquid;
        this.pos = pos;
        this.collision = VoxelShapes.empty();
    }

    public FlowingFluidBlock getBlock() {
        return blockLiquid;
    }

    public BlockPos getPos() {
        return pos;
    }

    public VoxelShape getCollision() {
        return collision;
    }

    public void setCollision(VoxelShape collision) {
        this.collision = collision;
    }
}