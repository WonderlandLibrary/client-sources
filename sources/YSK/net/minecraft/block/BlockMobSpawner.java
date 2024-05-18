package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class BlockMobSpawner extends BlockContainer
{
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityMobSpawner();
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        this.dropXpOnBlockBreak(world, blockPos, (0xB2 ^ 0xBD) + world.rand.nextInt(0xA0 ^ 0xAF) + world.rand.nextInt(0x83 ^ 0x8C));
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    protected BlockMobSpawner() {
        super(Material.rock);
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return null;
    }
}
