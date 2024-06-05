package net.minecraft.client.renderer;

import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.block.state.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

class BlockModelShapes$1 extends StateMapperBase {
    @Override
    protected ModelResourceLocation func_178132_a(final IBlockState p_178132_1_) {
        final BlockQuartz.EnumType var2 = (BlockQuartz.EnumType)p_178132_1_.getValue(BlockQuartz.VARIANT_PROP);
        switch (SwitchEnumType.field_178257_a[var2.ordinal()]) {
            default: {
                return new ModelResourceLocation("quartz_block", "normal");
            }
            case 2: {
                return new ModelResourceLocation("chiseled_quartz_block", "normal");
            }
            case 3: {
                return new ModelResourceLocation("quartz_column", "axis=y");
            }
            case 4: {
                return new ModelResourceLocation("quartz_column", "axis=x");
            }
            case 5: {
                return new ModelResourceLocation("quartz_column", "axis=z");
            }
        }
    }
}