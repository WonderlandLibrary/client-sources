package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockMelon extends Block
{
    protected BlockMelon() {
        super(Material.gourd, MapColor.limeColor);
        this.setCreativeTab(CreativeTabs.tabBlock);
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "   ".length() + random.nextInt(0x17 ^ 0x12);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.melon;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return Math.min(0x95 ^ 0x9C, this.quantityDropped(random) + random.nextInt(" ".length() + n));
    }
}
