package net.minecraft.world.gen.structure;

import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public abstract static class Piece extends StructureComponent
{
    protected static final IBlockState field_175828_a;
    protected static final IBlockState field_175826_b;
    protected static final IBlockState field_175827_c;
    protected static final IBlockState field_175824_d;
    protected static final IBlockState field_175825_e;
    protected static final IBlockState field_175822_f;
    protected static final int field_175823_g;
    protected static final int field_175831_h;
    protected static final int field_175832_i;
    protected static final int field_175829_j;
    protected RoomDefinition field_175830_k;
    
    public Piece() {
        super(0);
    }
    
    public Piece(final int p_i45588_1_) {
        super(p_i45588_1_);
    }
    
    public Piece(final EnumFacing p_i45589_1_, final StructureBoundingBox p_i45589_2_) {
        super(1);
        this.coordBaseMode = p_i45589_1_;
        this.boundingBox = p_i45589_2_;
    }
    
    protected Piece(final int p_i45590_1_, final EnumFacing p_i45590_2_, final RoomDefinition p_i45590_3_, final int p_i45590_4_, final int p_i45590_5_, final int p_i45590_6_) {
        super(p_i45590_1_);
        this.coordBaseMode = p_i45590_2_;
        this.field_175830_k = p_i45590_3_;
        final int var7 = p_i45590_3_.field_175967_a;
        final int var8 = var7 % 5;
        final int var9 = var7 / 5 % 5;
        final int var10 = var7 / 25;
        if (p_i45590_2_ != EnumFacing.NORTH && p_i45590_2_ != EnumFacing.SOUTH) {
            this.boundingBox = new StructureBoundingBox(0, 0, 0, p_i45590_6_ * 8 - 1, p_i45590_5_ * 4 - 1, p_i45590_4_ * 8 - 1);
        }
        else {
            this.boundingBox = new StructureBoundingBox(0, 0, 0, p_i45590_4_ * 8 - 1, p_i45590_5_ * 4 - 1, p_i45590_6_ * 8 - 1);
        }
        switch (StructureOceanMonumentPieces.SwitchEnumFacing.field_175971_a[p_i45590_2_.ordinal()]) {
            case 1: {
                this.boundingBox.offset(var8 * 8, var10 * 4, -(var9 + p_i45590_6_) * 8 + 1);
                break;
            }
            case 2: {
                this.boundingBox.offset(var8 * 8, var10 * 4, var9 * 8);
                break;
            }
            case 3: {
                this.boundingBox.offset(-(var9 + p_i45590_6_) * 8 + 1, var10 * 4, var8 * 8);
                break;
            }
            default: {
                this.boundingBox.offset(var9 * 8, var10 * 4, var8 * 8);
                break;
            }
        }
    }
    
    protected static final int func_175820_a(final int p_175820_0_, final int p_175820_1_, final int p_175820_2_) {
        return p_175820_1_ * 25 + p_175820_2_ * 5 + p_175820_0_;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
    }
    
    protected void func_175821_a(final World worldIn, final StructureBoundingBox p_175821_2_, final int p_175821_3_, final int p_175821_4_, final boolean p_175821_5_) {
        if (p_175821_5_) {
            this.func_175804_a(worldIn, p_175821_2_, p_175821_3_ + 0, 0, p_175821_4_ + 0, p_175821_3_ + 2, 0, p_175821_4_ + 8 - 1, Piece.field_175828_a, Piece.field_175828_a, false);
            this.func_175804_a(worldIn, p_175821_2_, p_175821_3_ + 5, 0, p_175821_4_ + 0, p_175821_3_ + 8 - 1, 0, p_175821_4_ + 8 - 1, Piece.field_175828_a, Piece.field_175828_a, false);
            this.func_175804_a(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 0, p_175821_3_ + 4, 0, p_175821_4_ + 2, Piece.field_175828_a, Piece.field_175828_a, false);
            this.func_175804_a(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 5, p_175821_3_ + 4, 0, p_175821_4_ + 8 - 1, Piece.field_175828_a, Piece.field_175828_a, false);
            this.func_175804_a(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 2, p_175821_3_ + 4, 0, p_175821_4_ + 2, Piece.field_175826_b, Piece.field_175826_b, false);
            this.func_175804_a(worldIn, p_175821_2_, p_175821_3_ + 3, 0, p_175821_4_ + 5, p_175821_3_ + 4, 0, p_175821_4_ + 5, Piece.field_175826_b, Piece.field_175826_b, false);
            this.func_175804_a(worldIn, p_175821_2_, p_175821_3_ + 2, 0, p_175821_4_ + 3, p_175821_3_ + 2, 0, p_175821_4_ + 4, Piece.field_175826_b, Piece.field_175826_b, false);
            this.func_175804_a(worldIn, p_175821_2_, p_175821_3_ + 5, 0, p_175821_4_ + 3, p_175821_3_ + 5, 0, p_175821_4_ + 4, Piece.field_175826_b, Piece.field_175826_b, false);
        }
        else {
            this.func_175804_a(worldIn, p_175821_2_, p_175821_3_ + 0, 0, p_175821_4_ + 0, p_175821_3_ + 8 - 1, 0, p_175821_4_ + 8 - 1, Piece.field_175828_a, Piece.field_175828_a, false);
        }
    }
    
    protected void func_175819_a(final World worldIn, final StructureBoundingBox p_175819_2_, final int p_175819_3_, final int p_175819_4_, final int p_175819_5_, final int p_175819_6_, final int p_175819_7_, final int p_175819_8_, final IBlockState p_175819_9_) {
        for (int var10 = p_175819_4_; var10 <= p_175819_7_; ++var10) {
            for (int var11 = p_175819_3_; var11 <= p_175819_6_; ++var11) {
                for (int var12 = p_175819_5_; var12 <= p_175819_8_; ++var12) {
                    if (this.func_175807_a(worldIn, var11, var10, var12, p_175819_2_) == Piece.field_175822_f) {
                        this.func_175811_a(worldIn, p_175819_9_, var11, var10, var12, p_175819_2_);
                    }
                }
            }
        }
    }
    
    protected boolean func_175818_a(final StructureBoundingBox p_175818_1_, final int p_175818_2_, final int p_175818_3_, final int p_175818_4_, final int p_175818_5_) {
        final int var6 = this.getXWithOffset(p_175818_2_, p_175818_3_);
        final int var7 = this.getZWithOffset(p_175818_2_, p_175818_3_);
        final int var8 = this.getXWithOffset(p_175818_4_, p_175818_5_);
        final int var9 = this.getZWithOffset(p_175818_4_, p_175818_5_);
        return p_175818_1_.intersectsWith(Math.min(var6, var8), Math.min(var7, var9), Math.max(var6, var8), Math.max(var7, var9));
    }
    
    protected boolean func_175817_a(final World worldIn, final StructureBoundingBox p_175817_2_, final int p_175817_3_, final int p_175817_4_, final int p_175817_5_) {
        final int var6 = this.getXWithOffset(p_175817_3_, p_175817_5_);
        final int var7 = this.getYWithOffset(p_175817_4_);
        final int var8 = this.getZWithOffset(p_175817_3_, p_175817_5_);
        if (p_175817_2_.func_175898_b(new BlockPos(var6, var7, var8))) {
            final EntityGuardian var9 = new EntityGuardian(worldIn);
            var9.func_175467_a(true);
            var9.heal(var9.getMaxHealth());
            var9.setLocationAndAngles(var6 + 0.5, var7, var8 + 0.5, 0.0f, 0.0f);
            var9.func_180482_a(worldIn.getDifficultyForLocation(new BlockPos(var9)), null);
            worldIn.spawnEntityInWorld(var9);
            return true;
        }
        return false;
    }
    
    static {
        field_175828_a = Blocks.prismarine.getStateFromMeta(BlockPrismarine.ROUGHMETA);
        field_175826_b = Blocks.prismarine.getStateFromMeta(BlockPrismarine.BRICKSMETA);
        field_175827_c = Blocks.prismarine.getStateFromMeta(BlockPrismarine.DARKMETA);
        field_175824_d = Piece.field_175826_b;
        field_175825_e = Blocks.sea_lantern.getDefaultState();
        field_175822_f = Blocks.water.getDefaultState();
        field_175823_g = func_175820_a(2, 0, 0);
        field_175831_h = func_175820_a(2, 2, 0);
        field_175832_i = func_175820_a(0, 1, 0);
        field_175829_j = func_175820_a(4, 1, 0);
    }
}
