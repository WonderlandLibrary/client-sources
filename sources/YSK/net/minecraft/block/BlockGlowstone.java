package net.minecraft.block;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockGlowstone extends Block
{
    @Override
    public int quantityDroppedWithBonus(final int n, final Random random) {
        return MathHelper.clamp_int(this.quantityDropped(random) + random.nextInt(n + " ".length()), " ".length(), 0x5B ^ 0x5F);
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.sandColor;
    }
    
    public BlockGlowstone(final Material material) {
        super(material);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "  ".length() + random.nextInt("   ".length());
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.glowstone_dust;
    }
}
