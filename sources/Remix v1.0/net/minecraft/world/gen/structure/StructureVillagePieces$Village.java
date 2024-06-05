package net.minecraft.world.gen.structure;

import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

abstract static class Village extends StructureComponent
{
    protected int field_143015_k;
    private int villagersSpawned;
    private boolean field_143014_b;
    
    public Village() {
        this.field_143015_k = -1;
    }
    
    protected Village(final Start p_i2107_1_, final int p_i2107_2_) {
        super(p_i2107_2_);
        this.field_143015_k = -1;
        if (p_i2107_1_ != null) {
            this.field_143014_b = p_i2107_1_.inDesert;
        }
    }
    
    protected static boolean canVillageGoDeeper(final StructureBoundingBox p_74895_0_) {
        return p_74895_0_ != null && p_74895_0_.minY > 10;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        p_143012_1_.setInteger("HPos", this.field_143015_k);
        p_143012_1_.setInteger("VCount", this.villagersSpawned);
        p_143012_1_.setBoolean("Desert", this.field_143014_b);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        this.field_143015_k = p_143011_1_.getInteger("HPos");
        this.villagersSpawned = p_143011_1_.getInteger("VCount");
        this.field_143014_b = p_143011_1_.getBoolean("Desert");
    }
    
    protected StructureComponent getNextComponentNN(final Start p_74891_1_, final List p_74891_2_, final Random p_74891_3_, final int p_74891_4_, final int p_74891_5_) {
        if (this.coordBaseMode != null) {
            switch (StructureVillagePieces.SwitchEnumFacing.field_176064_a[this.coordBaseMode.ordinal()]) {
                case 1: {
                    return StructureVillagePieces.access$100(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, this.getComponentType());
                }
                case 2: {
                    return StructureVillagePieces.access$100(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, this.getComponentType());
                }
                case 3: {
                    return StructureVillagePieces.access$100(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                }
                case 4: {
                    return StructureVillagePieces.access$100(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                }
            }
        }
        return null;
    }
    
    protected StructureComponent getNextComponentPP(final Start p_74894_1_, final List p_74894_2_, final Random p_74894_3_, final int p_74894_4_, final int p_74894_5_) {
        if (this.coordBaseMode != null) {
            switch (StructureVillagePieces.SwitchEnumFacing.field_176064_a[this.coordBaseMode.ordinal()]) {
                case 1: {
                    return StructureVillagePieces.access$100(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, this.getComponentType());
                }
                case 2: {
                    return StructureVillagePieces.access$100(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, this.getComponentType());
                }
                case 3: {
                    return StructureVillagePieces.access$100(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                }
                case 4: {
                    return StructureVillagePieces.access$100(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                }
            }
        }
        return null;
    }
    
    protected int getAverageGroundLevel(final World worldIn, final StructureBoundingBox p_74889_2_) {
        int var3 = 0;
        int var4 = 0;
        for (int var5 = this.boundingBox.minZ; var5 <= this.boundingBox.maxZ; ++var5) {
            for (int var6 = this.boundingBox.minX; var6 <= this.boundingBox.maxX; ++var6) {
                final BlockPos var7 = new BlockPos(var6, 64, var5);
                if (p_74889_2_.func_175898_b(var7)) {
                    var3 += Math.max(worldIn.func_175672_r(var7).getY(), worldIn.provider.getAverageGroundLevel());
                    ++var4;
                }
            }
        }
        if (var4 == 0) {
            return -1;
        }
        return var3 / var4;
    }
    
    protected void spawnVillagers(final World worldIn, final StructureBoundingBox p_74893_2_, final int p_74893_3_, final int p_74893_4_, final int p_74893_5_, final int p_74893_6_) {
        if (this.villagersSpawned < p_74893_6_) {
            for (int var7 = this.villagersSpawned; var7 < p_74893_6_; ++var7) {
                final int var8 = this.getXWithOffset(p_74893_3_ + var7, p_74893_5_);
                final int var9 = this.getYWithOffset(p_74893_4_);
                final int var10 = this.getZWithOffset(p_74893_3_ + var7, p_74893_5_);
                if (!p_74893_2_.func_175898_b(new BlockPos(var8, var9, var10))) {
                    break;
                }
                ++this.villagersSpawned;
                final EntityVillager var11 = new EntityVillager(worldIn);
                var11.setLocationAndAngles(var8 + 0.5, var9, var10 + 0.5, 0.0f, 0.0f);
                var11.func_180482_a(worldIn.getDifficultyForLocation(new BlockPos(var11)), null);
                var11.setProfession(this.func_180779_c(var7, var11.getProfession()));
                worldIn.spawnEntityInWorld(var11);
            }
        }
    }
    
    protected int func_180779_c(final int p_180779_1_, final int p_180779_2_) {
        return p_180779_2_;
    }
    
    protected IBlockState func_175847_a(final IBlockState p_175847_1_) {
        if (this.field_143014_b) {
            if (p_175847_1_.getBlock() == Blocks.log || p_175847_1_.getBlock() == Blocks.log2) {
                return Blocks.sandstone.getDefaultState();
            }
            if (p_175847_1_.getBlock() == Blocks.cobblestone) {
                return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.func_176675_a());
            }
            if (p_175847_1_.getBlock() == Blocks.planks) {
                return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a());
            }
            if (p_175847_1_.getBlock() == Blocks.oak_stairs) {
                return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, p_175847_1_.getValue(BlockStairs.FACING));
            }
            if (p_175847_1_.getBlock() == Blocks.stone_stairs) {
                return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, p_175847_1_.getValue(BlockStairs.FACING));
            }
            if (p_175847_1_.getBlock() == Blocks.gravel) {
                return Blocks.sandstone.getDefaultState();
            }
        }
        return p_175847_1_;
    }
    
    @Override
    protected void func_175811_a(final World worldIn, final IBlockState p_175811_2_, final int p_175811_3_, final int p_175811_4_, final int p_175811_5_, final StructureBoundingBox p_175811_6_) {
        final IBlockState var7 = this.func_175847_a(p_175811_2_);
        super.func_175811_a(worldIn, var7, p_175811_3_, p_175811_4_, p_175811_5_, p_175811_6_);
    }
    
    @Override
    protected void func_175804_a(final World worldIn, final StructureBoundingBox p_175804_2_, final int p_175804_3_, final int p_175804_4_, final int p_175804_5_, final int p_175804_6_, final int p_175804_7_, final int p_175804_8_, final IBlockState p_175804_9_, final IBlockState p_175804_10_, final boolean p_175804_11_) {
        final IBlockState var12 = this.func_175847_a(p_175804_9_);
        final IBlockState var13 = this.func_175847_a(p_175804_10_);
        super.func_175804_a(worldIn, p_175804_2_, p_175804_3_, p_175804_4_, p_175804_5_, p_175804_6_, p_175804_7_, p_175804_8_, var12, var13, p_175804_11_);
    }
    
    @Override
    protected void func_175808_b(final World worldIn, final IBlockState p_175808_2_, final int p_175808_3_, final int p_175808_4_, final int p_175808_5_, final StructureBoundingBox p_175808_6_) {
        final IBlockState var7 = this.func_175847_a(p_175808_2_);
        super.func_175808_b(worldIn, var7, p_175808_3_, p_175808_4_, p_175808_5_, p_175808_6_);
    }
    
    protected void func_175846_a(final boolean p_175846_1_) {
        this.field_143014_b = p_175846_1_;
    }
}
