package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockGravel extends BlockFalling
{
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.stoneColor;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, int length) {
        if (length > "   ".length()) {
            length = "   ".length();
        }
        Item item;
        if (random.nextInt((0x69 ^ 0x63) - length * "   ".length()) == 0) {
            item = Items.flint;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            item = Item.getItemFromBlock(this);
        }
        return item;
    }
}
