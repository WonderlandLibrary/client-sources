package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRedstoneLight extends Block
{
    private final boolean isOn;

    public BlockRedstoneLight(boolean isOn)
    {
        super(Material.REDSTONE_LIGHT);
        this.isOn = isOn;

        if (isOn)
        {
            this.setLightLevel(1.0F);
        }
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            if (this.isOn && !worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, Blocks.REDSTONE_LAMP.getDefaultState(), 2);
            }
            else if (!this.isOn && worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, Blocks.LIT_REDSTONE_LAMP.getDefaultState(), 2);
            }
        }
    }

    public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_)
    {
        if (!p_189540_2_.isRemote)
        {
            if (this.isOn && !p_189540_2_.isBlockPowered(p_189540_3_))
            {
                p_189540_2_.scheduleUpdate(p_189540_3_, this, 4);
            }
            else if (!this.isOn && p_189540_2_.isBlockPowered(p_189540_3_))
            {
                p_189540_2_.setBlockState(p_189540_3_, Blocks.LIT_REDSTONE_LAMP.getDefaultState(), 2);
            }
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (this.isOn && !worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, Blocks.REDSTONE_LAMP.getDefaultState(), 2);
            }
        }
    }

    @Nullable

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.REDSTONE_LAMP);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Blocks.REDSTONE_LAMP);
    }

    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Blocks.REDSTONE_LAMP);
    }
}
