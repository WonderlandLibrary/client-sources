package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class ItemSeeds extends Item
{
    private Block soilBlockID;
    private Block crops;
    
    public ItemSeeds(final Block crops, final Block soilBlockID) {
        this.crops = crops;
        this.soilBlockID = soilBlockID;
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing != EnumFacing.UP) {
            return "".length() != 0;
        }
        if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return "".length() != 0;
        }
        if (world.getBlockState(blockPos).getBlock() == this.soilBlockID && world.isAirBlock(blockPos.up())) {
            world.setBlockState(blockPos.up(), this.crops.getDefaultState());
            itemStack.stackSize -= " ".length();
            return " ".length() != 0;
        }
        return "".length() != 0;
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
}
