package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

static class Stones extends StructureComponent.BlockSelector
{
    private Stones() {
    }
    
    Stones(final Object p_i2080_1_) {
        this();
    }
    
    @Override
    public void selectBlocks(final Random p_75062_1_, final int p_75062_2_, final int p_75062_3_, final int p_75062_4_, final boolean p_75062_5_) {
        if (p_75062_5_) {
            final float var6 = p_75062_1_.nextFloat();
            if (var6 < 0.2f) {
                this.field_151562_a = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CRACKED_META);
            }
            else if (var6 < 0.5f) {
                this.field_151562_a = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.MOSSY_META);
            }
            else if (var6 < 0.55f) {
                this.field_151562_a = Blocks.monster_egg.getStateFromMeta(BlockSilverfish.EnumType.STONEBRICK.func_176881_a());
            }
            else {
                this.field_151562_a = Blocks.stonebrick.getDefaultState();
            }
        }
        else {
            this.field_151562_a = Blocks.air.getDefaultState();
        }
    }
}
