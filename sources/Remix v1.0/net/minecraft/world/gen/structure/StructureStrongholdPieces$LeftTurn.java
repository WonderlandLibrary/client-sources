package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class LeftTurn extends Stronghold
{
    public LeftTurn() {
    }
    
    public LeftTurn(final int p_i45579_1_, final Random p_i45579_2_, final StructureBoundingBox p_i45579_3_, final EnumFacing p_i45579_4_) {
        super(p_i45579_1_);
        this.coordBaseMode = p_i45579_4_;
        this.field_143013_d = this.getRandomDoor(p_i45579_2_);
        this.boundingBox = p_i45579_3_;
    }
    
    public static LeftTurn func_175867_a(final List p_175867_0_, final Random p_175867_1_, final int p_175867_2_, final int p_175867_3_, final int p_175867_4_, final EnumFacing p_175867_5_, final int p_175867_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175867_2_, p_175867_3_, p_175867_4_, -1, -1, 0, 5, 5, 5, p_175867_5_);
        return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175867_0_, var7) == null) ? new LeftTurn(p_175867_6_, p_175867_1_, var7, p_175867_5_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
            this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
        else {
            this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 4, true, p_74875_2_, StructureStrongholdPieces.access$000());
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
        if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
            this.func_175804_a(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        else {
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        return true;
    }
}
