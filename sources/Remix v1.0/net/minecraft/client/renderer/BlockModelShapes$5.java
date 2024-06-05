package net.minecraft.client.renderer;

import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import java.util.*;

class BlockModelShapes$5 extends StateMapperBase {
    @Override
    protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
        final LinkedHashMap var2 = Maps.newLinkedHashMap((Map)p_178132_1_.getProperties());
        final String var3 = BlockDirt.VARIANT.getName((Comparable)var2.remove(BlockDirt.VARIANT));
        if (BlockDirt.DirtType.PODZOL != p_178132_1_.getValue(BlockDirt.VARIANT)) {
            var2.remove(BlockDirt.SNOWY);
        }
        return new ModelResourceLocation(var3, this.func_178131_a(var2));
    }
}