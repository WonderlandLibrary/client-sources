package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;

public class DefaultStateMapper extends StateMapperBase
{
    @Override
    protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
        return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(p_178132_1_.getBlock()), this.func_178131_a((Map)p_178132_1_.getProperties()));
    }
}
