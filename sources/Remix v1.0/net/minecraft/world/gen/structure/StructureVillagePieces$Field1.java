package net.minecraft.world.gen.structure;

import net.minecraft.block.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public static class Field1 extends Village
{
    private Block cropTypeA;
    private Block cropTypeB;
    private Block cropTypeC;
    private Block cropTypeD;
    
    public Field1() {
    }
    
    public Field1(final Start p_i45570_1_, final int p_i45570_2_, final Random p_i45570_3_, final StructureBoundingBox p_i45570_4_, final EnumFacing p_i45570_5_) {
        super(p_i45570_1_, p_i45570_2_);
        this.coordBaseMode = p_i45570_5_;
        this.boundingBox = p_i45570_4_;
        this.cropTypeA = this.func_151559_a(p_i45570_3_);
        this.cropTypeB = this.func_151559_a(p_i45570_3_);
        this.cropTypeC = this.func_151559_a(p_i45570_3_);
        this.cropTypeD = this.func_151559_a(p_i45570_3_);
    }
    
    public static Field1 func_175851_a(final Start p_175851_0_, final List p_175851_1_, final Random p_175851_2_, final int p_175851_3_, final int p_175851_4_, final int p_175851_5_, final EnumFacing p_175851_6_, final int p_175851_7_) {
        final StructureBoundingBox var8 = StructureBoundingBox.func_175897_a(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 13, 4, 9, p_175851_6_);
        return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175851_1_, var8) == null) ? new Field1(p_175851_0_, p_175851_7_, p_175851_2_, var8, p_175851_6_) : null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
        p_143012_1_.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
        p_143012_1_.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
        p_143012_1_.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.cropTypeA = Block.getBlockById(p_143011_1_.getInteger("CA"));
        this.cropTypeB = Block.getBlockById(p_143011_1_.getInteger("CB"));
        this.cropTypeC = Block.getBlockById(p_143011_1_.getInteger("CC"));
        this.cropTypeD = Block.getBlockById(p_143011_1_.getInteger("CD"));
    }
    
    private Block func_151559_a(final Random p_151559_1_) {
        switch (p_151559_1_.nextInt(5)) {
            case 0: {
                return Blocks.carrots;
            }
            case 1: {
                return Blocks.potatoes;
            }
            default: {
                return Blocks.wheat;
            }
        }
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(worldIn, p_74875_3_);
            if (this.field_143015_k < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 12, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 0, 1, 8, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 0, 1, 11, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 12, 0, 0, 12, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 0, 11, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 8, 11, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 0, 1, 9, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
        for (int var4 = 1; var4 <= 7; ++var4) {
            this.func_175811_a(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7)), 1, 1, var4, p_74875_3_);
            this.func_175811_a(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7)), 2, 1, var4, p_74875_3_);
            this.func_175811_a(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7)), 4, 1, var4, p_74875_3_);
            this.func_175811_a(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7)), 5, 1, var4, p_74875_3_);
            this.func_175811_a(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7)), 7, 1, var4, p_74875_3_);
            this.func_175811_a(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7)), 8, 1, var4, p_74875_3_);
            this.func_175811_a(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7)), 10, 1, var4, p_74875_3_);
            this.func_175811_a(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(p_74875_2_, 2, 7)), 11, 1, var4, p_74875_3_);
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            for (int var5 = 0; var5 < 13; ++var5) {
                this.clearCurrentPositionBlocksUpwards(worldIn, var5, 4, var4, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.dirt.getDefaultState(), var5, -1, var4, p_74875_3_);
            }
        }
        return true;
    }
}
