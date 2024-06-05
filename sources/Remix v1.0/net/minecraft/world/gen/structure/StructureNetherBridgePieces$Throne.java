package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;

public static class Throne extends Piece
{
    private boolean hasSpawner;
    
    public Throne() {
    }
    
    public Throne(final int p_i45611_1_, final Random p_i45611_2_, final StructureBoundingBox p_i45611_3_, final EnumFacing p_i45611_4_) {
        super(p_i45611_1_);
        this.coordBaseMode = p_i45611_4_;
        this.boundingBox = p_i45611_3_;
    }
    
    public static Throne func_175874_a(final List p_175874_0_, final Random p_175874_1_, final int p_175874_2_, final int p_175874_3_, final int p_175874_4_, final int p_175874_5_, final EnumFacing p_175874_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175874_2_, p_175874_3_, p_175874_4_, -2, 0, 0, 7, 8, 9, p_175874_6_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175874_0_, var7) == null) ? new Throne(p_175874_5_, p_175874_1_, var7, p_175874_6_) : null;
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.hasSpawner = p_143011_1_.getBoolean("Mob");
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("Mob", this.hasSpawner);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 6, 7, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 0, 5, 1, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 1, 5, 2, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 2, 5, 3, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 4, 3, 5, 4, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 1, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 2, 0, 5, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 5, 2, 1, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 5, 2, 5, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 5, 3, 0, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 5, 3, 6, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 5, 8, 5, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 1, 6, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 5, 6, 3, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 0, 6, 3, 0, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 6, 3, 6, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 6, 8, 5, 7, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 8, 8, 4, 8, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
        if (!this.hasSpawner) {
            final BlockPos var4 = new BlockPos(this.getXWithOffset(3, 5), this.getYWithOffset(5), this.getZWithOffset(3, 5));
            if (p_74875_3_.func_175898_b(var4)) {
                this.hasSpawner = true;
                worldIn.setBlockState(var4, Blocks.mob_spawner.getDefaultState(), 2);
                final TileEntity var5 = worldIn.getTileEntity(var4);
                if (var5 instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner)var5).getSpawnerBaseLogic().setEntityName("Blaze");
                }
            }
        }
        for (int var6 = 0; var6 <= 6; ++var6) {
            for (int var7 = 0; var7 <= 6; ++var7) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var6, -1, var7, p_74875_3_);
            }
        }
        return true;
    }
}
