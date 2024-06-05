package net.minecraft.client.renderer;

import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import java.util.*;

class BlockModelShapes$6 extends StateMapperBase {
    @Override
    protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
        final LinkedHashMap var2 = Maps.newLinkedHashMap((Map)p_178132_1_.getProperties());
        final String var3 = BlockStoneSlab.field_176556_M.getName((Comparable)var2.remove(BlockStoneSlab.field_176556_M));
        var2.remove(BlockStoneSlab.field_176555_b);
        final String var4 = p_178132_1_.getValue(BlockStoneSlab.field_176555_b) ? "all" : "normal";
        return new ModelResourceLocation(var3 + "_double_slab", var4);
    }
}