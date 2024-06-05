package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class StairsStraight extends Stronghold
{
    public StairsStraight() {
    }
    
    public StairsStraight(final int p_i45572_1_, final Random p_i45572_2_, final StructureBoundingBox p_i45572_3_, final EnumFacing p_i45572_4_) {
        super(p_i45572_1_);
        this.coordBaseMode = p_i45572_4_;
        this.field_143013_d = this.getRandomDoor(p_i45572_2_);
        this.boundingBox = p_i45572_3_;
    }
    
    public static StairsStraight func_175861_a(final List p_175861_0_, final Random p_175861_1_, final int p_175861_2_, final int p_175861_3_, final int p_175861_4_, final EnumFacing p_175861_5_, final int p_175861_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175861_2_, p_175861_3_, p_175861_4_, -1, -7, 0, 5, 11, 8, p_175861_5_);
        return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175861_0_, var7) == null) ? new StairsStraight(p_175861_6_, p_175861_1_, var7, p_175861_5_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 10, 7, true, p_74875_2_, StructureStrongholdPieces.access$000());
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 7, 0);
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, Door.OPENING, 1, 1, 7);
        final int var4 = this.getMetadataWithOffset(Blocks.stone_stairs, 2);
        for (int var5 = 0; var5 < 6; ++var5) {
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 1, 6 - var5, 1 + var5, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 2, 6 - var5, 1 + var5, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(var4), 3, 6 - var5, 1 + var5, p_74875_3_);
            if (var5 < 5) {
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5 - var5, 1 + var5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 5 - var5, 1 + var5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 5 - var5, 1 + var5, p_74875_3_);
            }
        }
        return true;
    }
}
