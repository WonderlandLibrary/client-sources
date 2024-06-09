package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDragonEgg extends Block
{
    protected static final AxisAlignedBB DRAGON_EGG_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);

    public BlockDragonEgg()
    {
        super(Material.DRAGON_EGG, MapColor.BLACK);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return DRAGON_EGG_AABB;
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }

    public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_)
    {
        p_189540_2_.scheduleUpdate(p_189540_3_, this, this.tickRate(p_189540_2_));
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        this.checkFall(worldIn, pos);
    }

    private void checkFall(World worldIn, BlockPos pos)
    {
        if (BlockFalling.canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0)
        {
            int i = 32;

            if (!BlockFalling.fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32)))
            {
                worldIn.spawnEntityInWorld(new EntityFallingBlock(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), this.getDefaultState()));
            }
            else
            {
                worldIn.setBlockToAir(pos);
                BlockPos blockpos;

                for (blockpos = pos; BlockFalling.canFallThrough(worldIn.getBlockState(blockpos)) && blockpos.getY() > 0; blockpos = blockpos.down())
                {
                    ;
                }

                if (blockpos.getY() > 0)
                {
                    worldIn.setBlockState(blockpos, this.getDefaultState(), 2);
                }
            }
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        this.teleport(worldIn, pos);
        return true;
    }

    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
        this.teleport(worldIn, pos);
    }

    private void teleport(World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getBlock() == this)
        {
            for (int i = 0; i < 1000; ++i)
            {
                BlockPos blockpos = pos.add(worldIn.rand.nextInt(16) - worldIn.rand.nextInt(16), worldIn.rand.nextInt(8) - worldIn.rand.nextInt(8), worldIn.rand.nextInt(16) - worldIn.rand.nextInt(16));

                if (worldIn.getBlockState(blockpos).getBlock().blockMaterial == Material.AIR)
                {
                    if (worldIn.isRemote)
                    {
                        for (int j = 0; j < 128; ++j)
                        {
                            double d0 = worldIn.rand.nextDouble();
                            float f = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
                            float f1 = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
                            float f2 = (worldIn.rand.nextFloat() - 0.5F) * 0.2F;
                            double d1 = (double)blockpos.getX() + (double)(pos.getX() - blockpos.getX()) * d0 + (worldIn.rand.nextDouble() - 0.5D) + 0.5D;
                            double d2 = (double)blockpos.getY() + (double)(pos.getY() - blockpos.getY()) * d0 + worldIn.rand.nextDouble() - 0.5D;
                            double d3 = (double)blockpos.getZ() + (double)(pos.getZ() - blockpos.getZ()) * d0 + (worldIn.rand.nextDouble() - 0.5D) + 0.5D;
                            worldIn.spawnParticle(EnumParticleTypes.PORTAL, d1, d2, d3, (double)f, (double)f1, (double)f2, new int[0]);
                        }
                    }
                    else
                    {
                        worldIn.setBlockState(blockpos, iblockstate, 2);
                        worldIn.setBlockToAir(pos);
                    }

                    return;
                }
            }
        }
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate(World worldIn)
    {
        return 5;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }
}
