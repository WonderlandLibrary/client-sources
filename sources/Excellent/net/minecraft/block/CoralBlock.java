package net.minecraft.block;

import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class CoralBlock extends Block
{
    private final Block deadBlock;

    public CoralBlock(Block deadBlock, AbstractBlock.Properties properties)
    {
        super(properties);
        this.deadBlock = deadBlock;
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        if (!this.canLive(worldIn, pos))
        {
            worldIn.setBlockState(pos, this.deadBlock.getDefaultState(), 2);
        }
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder
     * immediately returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (!this.canLive(worldIn, currentPos))
        {
            worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 60 + worldIn.getRandom().nextInt(40));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    protected boolean canLive(IBlockReader reader, BlockPos pos)
    {
        for (Direction direction : Direction.values())
        {
            FluidState fluidstate = reader.getFluidState(pos.offset(direction));

            if (fluidstate.isTagged(FluidTags.WATER))
            {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        if (!this.canLive(context.getWorld(), context.getPos()))
        {
            context.getWorld().getPendingBlockTicks().scheduleTick(context.getPos(), this, 60 + context.getWorld().getRandom().nextInt(40));
        }

        return this.getDefaultState();
    }
}
