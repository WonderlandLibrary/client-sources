package net.minecraft.client.renderer;

import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import com.google.common.collect.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;

class BlockModelShapes$3 extends StateMapperBase {
    @Override
    protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
        final LinkedHashMap var2 = Maps.newLinkedHashMap((Map)p_178132_1_.getProperties());
        if (p_178132_1_.getValue(BlockStem.FACING_PROP) != EnumFacing.UP) {
            var2.remove(BlockStem.AGE_PROP);
        }
        return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(p_178132_1_.getBlock()), this.func_178131_a(var2));
    }
}