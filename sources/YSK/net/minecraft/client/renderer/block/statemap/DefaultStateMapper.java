package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.block.properties.*;

public class DefaultStateMapper extends StateMapperBase
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
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ModelResourceLocation getModelResourceLocation(final IBlockState blockState) {
        return new ModelResourceLocation(Block.blockRegistry.getNameForObject(blockState.getBlock()), this.getPropertyString((Map<IProperty, Comparable>)blockState.getProperties()));
    }
}
