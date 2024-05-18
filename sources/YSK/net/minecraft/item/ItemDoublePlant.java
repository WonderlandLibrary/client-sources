package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.block.*;
import com.google.common.base.*;

public class ItemDoublePlant extends ItemMultiTexture
{
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        final BlockDoublePlant.EnumPlantType byMetadata = BlockDoublePlant.EnumPlantType.byMetadata(itemStack.getMetadata());
        int n2;
        if (byMetadata != BlockDoublePlant.EnumPlantType.GRASS && byMetadata != BlockDoublePlant.EnumPlantType.FERN) {
            n2 = super.getColorFromItemStack(itemStack, n);
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            n2 = ColorizerGrass.getGrassColor(0.5, 1.0);
        }
        return n2;
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemDoublePlant(final Block block, final Block block2, final Function<ItemStack, String> function) {
        super(block, block2, function);
    }
}
