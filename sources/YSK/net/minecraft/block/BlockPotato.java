package net.minecraft.block;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;

public class BlockPotato extends BlockCrops
{
    @Override
    protected Item getCrop() {
        return Items.potato;
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected Item getSeed() {
        return Items.potato;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        if (!world.isRemote && blockState.getValue((IProperty<Integer>)BlockPotato.AGE) >= (0x41 ^ 0x46) && world.rand.nextInt(0x89 ^ 0xBB) == 0) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.poisonous_potato));
        }
    }
}
