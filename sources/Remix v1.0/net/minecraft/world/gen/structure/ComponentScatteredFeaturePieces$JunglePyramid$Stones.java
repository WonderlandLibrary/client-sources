package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.init.*;

static class Stones extends BlockSelector
{
    private Stones() {
    }
    
    Stones(final ComponentScatteredFeaturePieces.SwitchEnumFacing p_i45583_1_) {
        this();
    }
    
    @Override
    public void selectBlocks(final Random p_75062_1_, final int p_75062_2_, final int p_75062_3_, final int p_75062_4_, final boolean p_75062_5_) {
        if (p_75062_1_.nextFloat() < 0.4f) {
            this.field_151562_a = Blocks.cobblestone.getDefaultState();
        }
        else {
            this.field_151562_a = Blocks.mossy_cobblestone.getDefaultState();
        }
    }
}
