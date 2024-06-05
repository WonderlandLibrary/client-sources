package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class RightTurn extends LeftTurn
{
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
            this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
        else {
            this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
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
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        else {
            this.func_175804_a(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        return true;
    }
}
