package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Crossing2 extends Piece
{
    public Crossing2() {
    }
    
    public Crossing2(final int p_i45616_1_, final Random p_i45616_2_, final StructureBoundingBox p_i45616_3_, final EnumFacing p_i45616_4_) {
        super(p_i45616_1_);
        this.coordBaseMode = p_i45616_4_;
        this.boundingBox = p_i45616_3_;
    }
    
    public static Crossing2 func_175878_a(final List p_175878_0_, final Random p_175878_1_, final int p_175878_2_, final int p_175878_3_, final int p_175878_4_, final EnumFacing p_175878_5_, final int p_175878_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175878_2_, p_175878_3_, p_175878_4_, -1, 0, 0, 5, 7, 5, p_175878_5_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175878_0_, var7) == null) ? new Crossing2(p_175878_6_, p_175878_1_, var7, p_175878_5_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
        this.getNextComponentX((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
        this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        for (int var4 = 0; var4 <= 4; ++var4) {
            for (int var5 = 0; var5 <= 4; ++var5) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
            }
        }
        return true;
    }
}
