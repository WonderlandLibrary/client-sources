package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class End extends Piece
{
    private int fillSeed;
    
    public End() {
    }
    
    public End(final int p_i45621_1_, final Random p_i45621_2_, final StructureBoundingBox p_i45621_3_, final EnumFacing p_i45621_4_) {
        super(p_i45621_1_);
        this.coordBaseMode = p_i45621_4_;
        this.boundingBox = p_i45621_3_;
        this.fillSeed = p_i45621_2_.nextInt();
    }
    
    public static End func_175884_a(final List p_175884_0_, final Random p_175884_1_, final int p_175884_2_, final int p_175884_3_, final int p_175884_4_, final EnumFacing p_175884_5_, final int p_175884_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175884_2_, p_175884_3_, p_175884_4_, -1, -3, 0, 5, 10, 8, p_175884_5_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175884_0_, var7) == null) ? new End(p_175884_6_, p_175884_1_, var7, p_175884_5_) : null;
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.fillSeed = p_143011_1_.getInteger("Seed");
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setInteger("Seed", this.fillSeed);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        final Random var4 = new Random(this.fillSeed);
        for (int var5 = 0; var5 <= 4; ++var5) {
            for (int var6 = 3; var6 <= 4; ++var6) {
                final int var7 = var4.nextInt(8);
                this.func_175804_a(worldIn, p_74875_3_, var5, var6, 0, var5, var6, var7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            }
        }
        int var5 = var4.nextInt(8);
        this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 0, 5, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        var5 = var4.nextInt(8);
        this.func_175804_a(worldIn, p_74875_3_, 4, 5, 0, 4, 5, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        for (var5 = 0; var5 <= 4; ++var5) {
            final int var6 = var4.nextInt(5);
            this.func_175804_a(worldIn, p_74875_3_, var5, 2, 0, var5, 2, var6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        }
        for (var5 = 0; var5 <= 4; ++var5) {
            for (int var6 = 0; var6 <= 1; ++var6) {
                final int var7 = var4.nextInt(3);
                this.func_175804_a(worldIn, p_74875_3_, var5, var6, 0, var5, var6, var7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            }
        }
        return true;
    }
}
