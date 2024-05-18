package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;
import java.util.List;

public abstract class StructureComponent
{
    protected StructureBoundingBox Âµá€;
    protected EnumFacing Ó;
    protected int à;
    private static final String HorizonCode_Horizon_È = "CL_00000511";
    
    public StructureComponent() {
    }
    
    protected StructureComponent(final int p_i2091_1_) {
        this.à = p_i2091_1_;
    }
    
    public NBTTagCompound HorizonCode_Horizon_È() {
        final NBTTagCompound var1 = new NBTTagCompound();
        var1.HorizonCode_Horizon_È("id", MapGenStructureIO.HorizonCode_Horizon_È(this));
        var1.HorizonCode_Horizon_È("BB", this.Âµá€.à());
        var1.HorizonCode_Horizon_È("O", (this.Ó == null) ? -1 : this.Ó.Ý());
        var1.HorizonCode_Horizon_È("GD", this.à);
        this.HorizonCode_Horizon_È(var1);
        return var1;
    }
    
    protected abstract void HorizonCode_Horizon_È(final NBTTagCompound p0);
    
    public void HorizonCode_Horizon_È(final World worldIn, final NBTTagCompound p_143009_2_) {
        if (p_143009_2_.Ý("BB")) {
            this.Âµá€ = new StructureBoundingBox(p_143009_2_.á("BB"));
        }
        final int var3 = p_143009_2_.Ó("O");
        this.Ó = ((var3 == -1) ? null : EnumFacing.Â(var3));
        this.à = p_143009_2_.Ó("GD");
        this.Â(p_143009_2_);
    }
    
    protected abstract void Â(final NBTTagCompound p0);
    
    public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
    }
    
    public abstract boolean HorizonCode_Horizon_È(final World p0, final Random p1, final StructureBoundingBox p2);
    
    public StructureBoundingBox Â() {
        return this.Âµá€;
    }
    
    public int Ý() {
        return this.à;
    }
    
    public static StructureComponent HorizonCode_Horizon_È(final List p_74883_0_, final StructureBoundingBox p_74883_1_) {
        for (final StructureComponent var3 : p_74883_0_) {
            if (var3.Â() != null && var3.Â().HorizonCode_Horizon_È(p_74883_1_)) {
                return var3;
            }
        }
        return null;
    }
    
    public BlockPos Ø­áŒŠá() {
        return new BlockPos(this.Âµá€.Ó());
    }
    
    protected boolean HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_74860_2_) {
        final int var3 = Math.max(this.Âµá€.HorizonCode_Horizon_È - 1, p_74860_2_.HorizonCode_Horizon_È);
        final int var4 = Math.max(this.Âµá€.Â - 1, p_74860_2_.Â);
        final int var5 = Math.max(this.Âµá€.Ý - 1, p_74860_2_.Ý);
        final int var6 = Math.min(this.Âµá€.Ø­áŒŠá + 1, p_74860_2_.Ø­áŒŠá);
        final int var7 = Math.min(this.Âµá€.Âµá€ + 1, p_74860_2_.Âµá€);
        final int var8 = Math.min(this.Âµá€.Ó + 1, p_74860_2_.Ó);
        for (int var9 = var3; var9 <= var6; ++var9) {
            for (int var10 = var5; var10 <= var8; ++var10) {
                if (worldIn.Â(new BlockPos(var9, var4, var10)).Ý().Ó().HorizonCode_Horizon_È()) {
                    return true;
                }
                if (worldIn.Â(new BlockPos(var9, var7, var10)).Ý().Ó().HorizonCode_Horizon_È()) {
                    return true;
                }
            }
        }
        for (int var9 = var3; var9 <= var6; ++var9) {
            for (int var10 = var4; var10 <= var7; ++var10) {
                if (worldIn.Â(new BlockPos(var9, var10, var5)).Ý().Ó().HorizonCode_Horizon_È()) {
                    return true;
                }
                if (worldIn.Â(new BlockPos(var9, var10, var8)).Ý().Ó().HorizonCode_Horizon_È()) {
                    return true;
                }
            }
        }
        for (int var9 = var5; var9 <= var8; ++var9) {
            for (int var10 = var4; var10 <= var7; ++var10) {
                if (worldIn.Â(new BlockPos(var3, var10, var9)).Ý().Ó().HorizonCode_Horizon_È()) {
                    return true;
                }
                if (worldIn.Â(new BlockPos(var6, var10, var9)).Ý().Ó().HorizonCode_Horizon_È()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected int HorizonCode_Horizon_È(final int p_74865_1_, final int p_74865_2_) {
        if (this.Ó == null) {
            return p_74865_1_;
        }
        switch (Â.HorizonCode_Horizon_È[this.Ó.ordinal()]) {
            case 1:
            case 2: {
                return this.Âµá€.HorizonCode_Horizon_È + p_74865_1_;
            }
            case 3: {
                return this.Âµá€.Ø­áŒŠá - p_74865_2_;
            }
            case 4: {
                return this.Âµá€.HorizonCode_Horizon_È + p_74865_2_;
            }
            default: {
                return p_74865_1_;
            }
        }
    }
    
    protected int HorizonCode_Horizon_È(final int p_74862_1_) {
        return (this.Ó == null) ? p_74862_1_ : (p_74862_1_ + this.Âµá€.Â);
    }
    
    protected int Â(final int p_74873_1_, final int p_74873_2_) {
        if (this.Ó == null) {
            return p_74873_2_;
        }
        switch (Â.HorizonCode_Horizon_È[this.Ó.ordinal()]) {
            case 1: {
                return this.Âµá€.Ó - p_74873_2_;
            }
            case 2: {
                return this.Âµá€.Ý + p_74873_2_;
            }
            case 3:
            case 4: {
                return this.Âµá€.Ý + p_74873_1_;
            }
            default: {
                return p_74873_2_;
            }
        }
    }
    
    protected int HorizonCode_Horizon_È(final Block p_151555_1_, final int p_151555_2_) {
        if (p_151555_1_ == Blocks.áŒŠáŠ) {
            if (this.Ó == EnumFacing.Âµá€ || this.Ó == EnumFacing.Ó) {
                if (p_151555_2_ == 1) {
                    return 0;
                }
                return 1;
            }
        }
        else if (p_151555_1_ instanceof BlockDoor) {
            if (this.Ó == EnumFacing.Ø­áŒŠá) {
                if (p_151555_2_ == 0) {
                    return 2;
                }
                if (p_151555_2_ == 2) {
                    return 0;
                }
            }
            else {
                if (this.Ó == EnumFacing.Âµá€) {
                    return p_151555_2_ + 1 & 0x3;
                }
                if (this.Ó == EnumFacing.Ó) {
                    return p_151555_2_ + 3 & 0x3;
                }
            }
        }
        else if (p_151555_1_ != Blocks.ˆÓ && p_151555_1_ != Blocks.áˆºÏ && p_151555_1_ != Blocks.ÇªÂ && p_151555_1_ != Blocks.¥Å && p_151555_1_ != Blocks.µÏ) {
            if (p_151555_1_ == Blocks.áŒŠÏ) {
                if (this.Ó == EnumFacing.Ø­áŒŠá) {
                    if (p_151555_2_ == EnumFacing.Ý.Â()) {
                        return EnumFacing.Ø­áŒŠá.Â();
                    }
                    if (p_151555_2_ == EnumFacing.Ø­áŒŠá.Â()) {
                        return EnumFacing.Ý.Â();
                    }
                }
                else if (this.Ó == EnumFacing.Âµá€) {
                    if (p_151555_2_ == EnumFacing.Ý.Â()) {
                        return EnumFacing.Âµá€.Â();
                    }
                    if (p_151555_2_ == EnumFacing.Ø­áŒŠá.Â()) {
                        return EnumFacing.Ó.Â();
                    }
                    if (p_151555_2_ == EnumFacing.Âµá€.Â()) {
                        return EnumFacing.Ý.Â();
                    }
                    if (p_151555_2_ == EnumFacing.Ó.Â()) {
                        return EnumFacing.Ø­áŒŠá.Â();
                    }
                }
                else if (this.Ó == EnumFacing.Ó) {
                    if (p_151555_2_ == EnumFacing.Ý.Â()) {
                        return EnumFacing.Ó.Â();
                    }
                    if (p_151555_2_ == EnumFacing.Ø­áŒŠá.Â()) {
                        return EnumFacing.Âµá€.Â();
                    }
                    if (p_151555_2_ == EnumFacing.Âµá€.Â()) {
                        return EnumFacing.Ý.Â();
                    }
                    if (p_151555_2_ == EnumFacing.Ó.Â()) {
                        return EnumFacing.Ø­áŒŠá.Â();
                    }
                }
            }
            else if (p_151555_1_ == Blocks.Šà) {
                if (this.Ó == EnumFacing.Ø­áŒŠá) {
                    if (p_151555_2_ == 3) {
                        return 4;
                    }
                    if (p_151555_2_ == 4) {
                        return 3;
                    }
                }
                else if (this.Ó == EnumFacing.Âµá€) {
                    if (p_151555_2_ == 3) {
                        return 1;
                    }
                    if (p_151555_2_ == 4) {
                        return 2;
                    }
                    if (p_151555_2_ == 2) {
                        return 3;
                    }
                    if (p_151555_2_ == 1) {
                        return 4;
                    }
                }
                else if (this.Ó == EnumFacing.Ó) {
                    if (p_151555_2_ == 3) {
                        return 2;
                    }
                    if (p_151555_2_ == 4) {
                        return 1;
                    }
                    if (p_151555_2_ == 2) {
                        return 3;
                    }
                    if (p_151555_2_ == 1) {
                        return 4;
                    }
                }
            }
            else if (p_151555_1_ != Blocks.ˆÂ && !(p_151555_1_ instanceof BlockDirectional)) {
                if (p_151555_1_ == Blocks.Õ || p_151555_1_ == Blocks.ÇŽÕ || p_151555_1_ == Blocks.ÇªÔ || p_151555_1_ == Blocks.Ñ¢á) {
                    if (this.Ó == EnumFacing.Ø­áŒŠá) {
                        if (p_151555_2_ == EnumFacing.Ý.Â() || p_151555_2_ == EnumFacing.Ø­áŒŠá.Â()) {
                            return EnumFacing.HorizonCode_Horizon_È(p_151555_2_).Âµá€().Â();
                        }
                    }
                    else if (this.Ó == EnumFacing.Âµá€) {
                        if (p_151555_2_ == EnumFacing.Ý.Â()) {
                            return EnumFacing.Âµá€.Â();
                        }
                        if (p_151555_2_ == EnumFacing.Ø­áŒŠá.Â()) {
                            return EnumFacing.Ó.Â();
                        }
                        if (p_151555_2_ == EnumFacing.Âµá€.Â()) {
                            return EnumFacing.Ý.Â();
                        }
                        if (p_151555_2_ == EnumFacing.Ó.Â()) {
                            return EnumFacing.Ø­áŒŠá.Â();
                        }
                    }
                    else if (this.Ó == EnumFacing.Ó) {
                        if (p_151555_2_ == EnumFacing.Ý.Â()) {
                            return EnumFacing.Ó.Â();
                        }
                        if (p_151555_2_ == EnumFacing.Ø­áŒŠá.Â()) {
                            return EnumFacing.Âµá€.Â();
                        }
                        if (p_151555_2_ == EnumFacing.Âµá€.Â()) {
                            return EnumFacing.Ý.Â();
                        }
                        if (p_151555_2_ == EnumFacing.Ó.Â()) {
                            return EnumFacing.Ø­áŒŠá.Â();
                        }
                    }
                }
            }
            else {
                final EnumFacing var3 = EnumFacing.Â(p_151555_2_);
                if (this.Ó == EnumFacing.Ø­áŒŠá) {
                    if (var3 == EnumFacing.Ø­áŒŠá || var3 == EnumFacing.Ý) {
                        return var3.Âµá€().Ý();
                    }
                }
                else if (this.Ó == EnumFacing.Âµá€) {
                    if (var3 == EnumFacing.Ý) {
                        return EnumFacing.Âµá€.Ý();
                    }
                    if (var3 == EnumFacing.Ø­áŒŠá) {
                        return EnumFacing.Ó.Ý();
                    }
                    if (var3 == EnumFacing.Âµá€) {
                        return EnumFacing.Ý.Ý();
                    }
                    if (var3 == EnumFacing.Ó) {
                        return EnumFacing.Ø­áŒŠá.Ý();
                    }
                }
                else if (this.Ó == EnumFacing.Ó) {
                    if (var3 == EnumFacing.Ý) {
                        return EnumFacing.Ó.Ý();
                    }
                    if (var3 == EnumFacing.Ø­áŒŠá) {
                        return EnumFacing.Âµá€.Ý();
                    }
                    if (var3 == EnumFacing.Âµá€) {
                        return EnumFacing.Ý.Ý();
                    }
                    if (var3 == EnumFacing.Ó) {
                        return EnumFacing.Ø­áŒŠá.Ý();
                    }
                }
            }
        }
        else if (this.Ó == EnumFacing.Ø­áŒŠá) {
            if (p_151555_2_ == 2) {
                return 3;
            }
            if (p_151555_2_ == 3) {
                return 2;
            }
        }
        else if (this.Ó == EnumFacing.Âµá€) {
            if (p_151555_2_ == 0) {
                return 2;
            }
            if (p_151555_2_ == 1) {
                return 3;
            }
            if (p_151555_2_ == 2) {
                return 0;
            }
            if (p_151555_2_ == 3) {
                return 1;
            }
        }
        else if (this.Ó == EnumFacing.Ó) {
            if (p_151555_2_ == 0) {
                return 2;
            }
            if (p_151555_2_ == 1) {
                return 3;
            }
            if (p_151555_2_ == 2) {
                return 1;
            }
            if (p_151555_2_ == 3) {
                return 0;
            }
        }
        return p_151555_2_;
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final IBlockState p_175811_2_, final int p_175811_3_, final int p_175811_4_, final int p_175811_5_, final StructureBoundingBox p_175811_6_) {
        final BlockPos var7 = new BlockPos(this.HorizonCode_Horizon_È(p_175811_3_, p_175811_5_), this.HorizonCode_Horizon_È(p_175811_4_), this.Â(p_175811_3_, p_175811_5_));
        if (p_175811_6_.HorizonCode_Horizon_È(var7)) {
            worldIn.HorizonCode_Horizon_È(var7, p_175811_2_, 2);
        }
    }
    
    protected IBlockState HorizonCode_Horizon_È(final World worldIn, final int p_175807_2_, final int p_175807_3_, final int p_175807_4_, final StructureBoundingBox p_175807_5_) {
        final int var6 = this.HorizonCode_Horizon_È(p_175807_2_, p_175807_4_);
        final int var7 = this.HorizonCode_Horizon_È(p_175807_3_);
        final int var8 = this.Â(p_175807_2_, p_175807_4_);
        return p_175807_5_.HorizonCode_Horizon_È(new BlockPos(var6, var7, var8)) ? worldIn.Â(new BlockPos(var6, var7, var8)) : Blocks.Â.¥à();
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_74878_2_, final int p_74878_3_, final int p_74878_4_, final int p_74878_5_, final int p_74878_6_, final int p_74878_7_, final int p_74878_8_) {
        for (int var9 = p_74878_4_; var9 <= p_74878_7_; ++var9) {
            for (int var10 = p_74878_3_; var10 <= p_74878_6_; ++var10) {
                for (int var11 = p_74878_5_; var11 <= p_74878_8_; ++var11) {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), var10, var9, var11, p_74878_2_);
                }
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_175804_2_, final int p_175804_3_, final int p_175804_4_, final int p_175804_5_, final int p_175804_6_, final int p_175804_7_, final int p_175804_8_, final IBlockState p_175804_9_, final IBlockState p_175804_10_, final boolean p_175804_11_) {
        for (int var12 = p_175804_4_; var12 <= p_175804_7_; ++var12) {
            for (int var13 = p_175804_3_; var13 <= p_175804_6_; ++var13) {
                for (int var14 = p_175804_5_; var14 <= p_175804_8_; ++var14) {
                    if (!p_175804_11_ || this.HorizonCode_Horizon_È(worldIn, var13, var12, var14, p_175804_2_).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                        if (var12 != p_175804_4_ && var12 != p_175804_7_ && var13 != p_175804_3_ && var13 != p_175804_6_ && var14 != p_175804_5_ && var14 != p_175804_8_) {
                            this.HorizonCode_Horizon_È(worldIn, p_175804_10_, var13, var12, var14, p_175804_2_);
                        }
                        else {
                            this.HorizonCode_Horizon_È(worldIn, p_175804_9_, var13, var12, var14, p_175804_2_);
                        }
                    }
                }
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_74882_2_, final int p_74882_3_, final int p_74882_4_, final int p_74882_5_, final int p_74882_6_, final int p_74882_7_, final int p_74882_8_, final boolean p_74882_9_, final Random p_74882_10_, final HorizonCode_Horizon_È p_74882_11_) {
        for (int var12 = p_74882_4_; var12 <= p_74882_7_; ++var12) {
            for (int var13 = p_74882_3_; var13 <= p_74882_6_; ++var13) {
                for (int var14 = p_74882_5_; var14 <= p_74882_8_; ++var14) {
                    if (!p_74882_9_ || this.HorizonCode_Horizon_È(worldIn, var13, var12, var14, p_74882_2_).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                        p_74882_11_.HorizonCode_Horizon_È(p_74882_10_, var13, var12, var14, var12 == p_74882_4_ || var12 == p_74882_7_ || var13 == p_74882_3_ || var13 == p_74882_6_ || var14 == p_74882_5_ || var14 == p_74882_8_);
                        this.HorizonCode_Horizon_È(worldIn, p_74882_11_.HorizonCode_Horizon_È(), var13, var12, var14, p_74882_2_);
                    }
                }
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_175805_2_, final Random p_175805_3_, final float p_175805_4_, final int p_175805_5_, final int p_175805_6_, final int p_175805_7_, final int p_175805_8_, final int p_175805_9_, final int p_175805_10_, final IBlockState p_175805_11_, final IBlockState p_175805_12_, final boolean p_175805_13_) {
        for (int var14 = p_175805_6_; var14 <= p_175805_9_; ++var14) {
            for (int var15 = p_175805_5_; var15 <= p_175805_8_; ++var15) {
                for (int var16 = p_175805_7_; var16 <= p_175805_10_; ++var16) {
                    if (p_175805_3_.nextFloat() <= p_175805_4_ && (!p_175805_13_ || this.HorizonCode_Horizon_È(worldIn, var15, var14, var16, p_175805_2_).Ý().Ó() != Material.HorizonCode_Horizon_È)) {
                        if (var14 != p_175805_6_ && var14 != p_175805_9_ && var15 != p_175805_5_ && var15 != p_175805_8_ && var16 != p_175805_7_ && var16 != p_175805_10_) {
                            this.HorizonCode_Horizon_È(worldIn, p_175805_12_, var15, var14, var16, p_175805_2_);
                        }
                        else {
                            this.HorizonCode_Horizon_È(worldIn, p_175805_11_, var15, var14, var16, p_175805_2_);
                        }
                    }
                }
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_175809_2_, final Random p_175809_3_, final float p_175809_4_, final int p_175809_5_, final int p_175809_6_, final int p_175809_7_, final IBlockState p_175809_8_) {
        if (p_175809_3_.nextFloat() < p_175809_4_) {
            this.HorizonCode_Horizon_È(worldIn, p_175809_8_, p_175809_5_, p_175809_6_, p_175809_7_, p_175809_2_);
        }
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_180777_2_, final int p_180777_3_, final int p_180777_4_, final int p_180777_5_, final int p_180777_6_, final int p_180777_7_, final int p_180777_8_, final IBlockState p_180777_9_, final boolean p_180777_10_) {
        final float var11 = p_180777_6_ - p_180777_3_ + 1;
        final float var12 = p_180777_7_ - p_180777_4_ + 1;
        final float var13 = p_180777_8_ - p_180777_5_ + 1;
        final float var14 = p_180777_3_ + var11 / 2.0f;
        final float var15 = p_180777_5_ + var13 / 2.0f;
        for (int var16 = p_180777_4_; var16 <= p_180777_7_; ++var16) {
            final float var17 = (var16 - p_180777_4_) / var12;
            for (int var18 = p_180777_3_; var18 <= p_180777_6_; ++var18) {
                final float var19 = (var18 - var14) / (var11 * 0.5f);
                for (int var20 = p_180777_5_; var20 <= p_180777_8_; ++var20) {
                    final float var21 = (var20 - var15) / (var13 * 0.5f);
                    if (!p_180777_10_ || this.HorizonCode_Horizon_È(worldIn, var18, var16, var20, p_180777_2_).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                        final float var22 = var19 * var19 + var17 * var17 + var21 * var21;
                        if (var22 <= 1.05f) {
                            this.HorizonCode_Horizon_È(worldIn, p_180777_9_, var18, var16, var20, p_180777_2_);
                        }
                    }
                }
            }
        }
    }
    
    protected void Â(final World worldIn, final int p_74871_2_, final int p_74871_3_, final int p_74871_4_, final StructureBoundingBox p_74871_5_) {
        BlockPos var6 = new BlockPos(this.HorizonCode_Horizon_È(p_74871_2_, p_74871_4_), this.HorizonCode_Horizon_È(p_74871_3_), this.Â(p_74871_2_, p_74871_4_));
        if (p_74871_5_.HorizonCode_Horizon_È(var6)) {
            while (!worldIn.Ø­áŒŠá(var6) && var6.Â() < 255) {
                worldIn.HorizonCode_Horizon_È(var6, Blocks.Â.¥à(), 2);
                var6 = var6.Ø­áŒŠá();
            }
        }
    }
    
    protected void Â(final World worldIn, final IBlockState p_175808_2_, final int p_175808_3_, final int p_175808_4_, final int p_175808_5_, final StructureBoundingBox p_175808_6_) {
        final int var7 = this.HorizonCode_Horizon_È(p_175808_3_, p_175808_5_);
        int var8 = this.HorizonCode_Horizon_È(p_175808_4_);
        final int var9 = this.Â(p_175808_3_, p_175808_5_);
        if (p_175808_6_.HorizonCode_Horizon_È(new BlockPos(var7, var8, var9))) {
            while ((worldIn.Ø­áŒŠá(new BlockPos(var7, var8, var9)) || worldIn.Â(new BlockPos(var7, var8, var9)).Ý().Ó().HorizonCode_Horizon_È()) && var8 > 1) {
                worldIn.HorizonCode_Horizon_È(new BlockPos(var7, var8, var9), p_175808_2_, 2);
                --var8;
            }
        }
    }
    
    protected boolean HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_180778_2_, final Random p_180778_3_, final int p_180778_4_, final int p_180778_5_, final int p_180778_6_, final List p_180778_7_, final int p_180778_8_) {
        final BlockPos var9 = new BlockPos(this.HorizonCode_Horizon_È(p_180778_4_, p_180778_6_), this.HorizonCode_Horizon_È(p_180778_5_), this.Â(p_180778_4_, p_180778_6_));
        if (p_180778_2_.HorizonCode_Horizon_È(var9) && worldIn.Â(var9).Ý() != Blocks.ˆáƒ) {
            final IBlockState var10 = Blocks.ˆáƒ.¥à();
            worldIn.HorizonCode_Horizon_È(var9, Blocks.ˆáƒ.Ó(worldIn, var9, var10), 2);
            final TileEntity var11 = worldIn.HorizonCode_Horizon_È(var9);
            if (var11 instanceof TileEntityChest) {
                WeightedRandomChestContent.HorizonCode_Horizon_È(p_180778_3_, p_180778_7_, (IInventory)var11, p_180778_8_);
            }
            return true;
        }
        return false;
    }
    
    protected boolean HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_175806_2_, final Random p_175806_3_, final int p_175806_4_, final int p_175806_5_, final int p_175806_6_, final int p_175806_7_, final List p_175806_8_, final int p_175806_9_) {
        final BlockPos var10 = new BlockPos(this.HorizonCode_Horizon_È(p_175806_4_, p_175806_6_), this.HorizonCode_Horizon_È(p_175806_5_), this.Â(p_175806_4_, p_175806_6_));
        if (p_175806_2_.HorizonCode_Horizon_È(var10) && worldIn.Â(var10).Ý() != Blocks.Ñ¢á) {
            worldIn.HorizonCode_Horizon_È(var10, Blocks.Ñ¢á.Ý(this.HorizonCode_Horizon_È(Blocks.Ñ¢á, p_175806_7_)), 2);
            final TileEntity var11 = worldIn.HorizonCode_Horizon_È(var10);
            if (var11 instanceof TileEntityDispenser) {
                WeightedRandomChestContent.HorizonCode_Horizon_È(p_175806_3_, p_175806_8_, (TileEntityDispenser)var11, p_175806_9_);
            }
            return true;
        }
        return false;
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_175810_2_, final Random p_175810_3_, final int p_175810_4_, final int p_175810_5_, final int p_175810_6_, final EnumFacing p_175810_7_) {
        final BlockPos var8 = new BlockPos(this.HorizonCode_Horizon_È(p_175810_4_, p_175810_6_), this.HorizonCode_Horizon_È(p_175810_5_), this.Â(p_175810_4_, p_175810_6_));
        if (p_175810_2_.HorizonCode_Horizon_È(var8)) {
            ItemDoor.HorizonCode_Horizon_È(worldIn, var8, p_175810_7_.à(), Blocks.Ï­Ô);
        }
    }
    
    public abstract static class HorizonCode_Horizon_È
    {
        protected IBlockState HorizonCode_Horizon_È;
        private static final String Â = "CL_00000512";
        
        protected HorizonCode_Horizon_È() {
            this.HorizonCode_Horizon_È = Blocks.Â.¥à();
        }
        
        public abstract void HorizonCode_Horizon_È(final Random p0, final int p1, final int p2, final int p3, final boolean p4);
        
        public IBlockState HorizonCode_Horizon_È() {
            return this.HorizonCode_Horizon_È;
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00001969";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                StructureComponent.Â.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                StructureComponent.Â.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                StructureComponent.Â.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                StructureComponent.Â.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
