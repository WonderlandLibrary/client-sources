package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;

public class BlockRedstoneLight extends Block
{
    private final boolean isOn;
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.redstone_lamp);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.redstone_lamp);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && this.isOn && !world.isBlockPowered(blockPos)) {
            world.setBlockState(blockPos, Blocks.redstone_lamp.getDefaultState(), "  ".length());
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            if (this.isOn && !world.isBlockPowered(blockPos)) {
                world.scheduleUpdate(blockPos, this, 0xB1 ^ 0xB5);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else if (!this.isOn && world.isBlockPowered(blockPos)) {
                world.setBlockState(blockPos, Blocks.lit_redstone_lamp.getDefaultState(), "  ".length());
            }
        }
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Blocks.redstone_lamp);
    }
    
    public BlockRedstoneLight(final boolean isOn) {
        super(Material.redstoneLight);
        this.isOn = isOn;
        if (isOn) {
            this.setLightLevel(1.0f);
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            if (this.isOn && !world.isBlockPowered(blockPos)) {
                world.setBlockState(blockPos, Blocks.redstone_lamp.getDefaultState(), "  ".length());
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else if (!this.isOn && world.isBlockPowered(blockPos)) {
                world.setBlockState(blockPos, Blocks.lit_redstone_lamp.getDefaultState(), "  ".length());
            }
        }
    }
}
