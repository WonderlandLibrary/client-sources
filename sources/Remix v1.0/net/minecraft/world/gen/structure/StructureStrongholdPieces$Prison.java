package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Prison extends Stronghold
{
    public Prison() {
    }
    
    public Prison(final int p_i45576_1_, final Random p_i45576_2_, final StructureBoundingBox p_i45576_3_, final EnumFacing p_i45576_4_) {
        super(p_i45576_1_);
        this.coordBaseMode = p_i45576_4_;
        this.field_143013_d = this.getRandomDoor(p_i45576_2_);
        this.boundingBox = p_i45576_3_;
    }
    
    public static Prison func_175860_a(final List p_175860_0_, final Random p_175860_1_, final int p_175860_2_, final int p_175860_3_, final int p_175860_4_, final EnumFacing p_175860_5_, final int p_175860_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175860_2_, p_175860_3_, p_175860_4_, -1, -1, 0, 9, 5, 11, p_175860_5_);
        return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175860_0_, var7) == null) ? new Prison(p_175860_6_, p_175860_1_, var7, p_175860_5_) : null;
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
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 8, 4, 10, true, p_74875_2_, StructureStrongholdPieces.access$000());
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 1, 0);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 10, 3, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 1, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 3, 4, 3, 3, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 7, 4, 3, 7, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 9, 4, 3, 9, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.func_175804_a(worldIn, p_74875_3_, 4, 1, 4, 4, 3, 6, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 5, 7, 3, 5, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), 4, 3, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.iron_bars.getDefaultState(), 4, 3, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 8, p_74875_3_);
        return true;
    }
}
