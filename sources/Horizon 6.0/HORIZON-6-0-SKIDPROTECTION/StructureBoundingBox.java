package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Objects;

public class StructureBoundingBox
{
    public int HorizonCode_Horizon_È;
    public int Â;
    public int Ý;
    public int Ø­áŒŠá;
    public int Âµá€;
    public int Ó;
    private static final String à = "CL_00000442";
    
    public StructureBoundingBox() {
    }
    
    public StructureBoundingBox(final int[] p_i43000_1_) {
        if (p_i43000_1_.length == 6) {
            this.HorizonCode_Horizon_È = p_i43000_1_[0];
            this.Â = p_i43000_1_[1];
            this.Ý = p_i43000_1_[2];
            this.Ø­áŒŠá = p_i43000_1_[3];
            this.Âµá€ = p_i43000_1_[4];
            this.Ó = p_i43000_1_[5];
        }
    }
    
    public static StructureBoundingBox HorizonCode_Horizon_È() {
        return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }
    
    public static StructureBoundingBox HorizonCode_Horizon_È(final int p_175897_0_, final int p_175897_1_, final int p_175897_2_, final int p_175897_3_, final int p_175897_4_, final int p_175897_5_, final int p_175897_6_, final int p_175897_7_, final int p_175897_8_, final EnumFacing p_175897_9_) {
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[p_175897_9_.ordinal()]) {
            case 1: {
                return new StructureBoundingBox(p_175897_0_ + p_175897_3_, p_175897_1_ + p_175897_4_, p_175897_2_ - p_175897_8_ + 1 + p_175897_5_, p_175897_0_ + p_175897_6_ - 1 + p_175897_3_, p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_5_);
            }
            case 2: {
                return new StructureBoundingBox(p_175897_0_ + p_175897_3_, p_175897_1_ + p_175897_4_, p_175897_2_ + p_175897_5_, p_175897_0_ + p_175897_6_ - 1 + p_175897_3_, p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_8_ - 1 + p_175897_5_);
            }
            case 3: {
                return new StructureBoundingBox(p_175897_0_ - p_175897_8_ + 1 + p_175897_5_, p_175897_1_ + p_175897_4_, p_175897_2_ + p_175897_3_, p_175897_0_ + p_175897_5_, p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_6_ - 1 + p_175897_3_);
            }
            case 4: {
                return new StructureBoundingBox(p_175897_0_ + p_175897_5_, p_175897_1_ + p_175897_4_, p_175897_2_ + p_175897_3_, p_175897_0_ + p_175897_8_ - 1 + p_175897_5_, p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_6_ - 1 + p_175897_3_);
            }
            default: {
                return new StructureBoundingBox(p_175897_0_ + p_175897_3_, p_175897_1_ + p_175897_4_, p_175897_2_ + p_175897_5_, p_175897_0_ + p_175897_6_ - 1 + p_175897_3_, p_175897_1_ + p_175897_7_ - 1 + p_175897_4_, p_175897_2_ + p_175897_8_ - 1 + p_175897_5_);
            }
        }
    }
    
    public static StructureBoundingBox HorizonCode_Horizon_È(final int p_175899_0_, final int p_175899_1_, final int p_175899_2_, final int p_175899_3_, final int p_175899_4_, final int p_175899_5_) {
        return new StructureBoundingBox(Math.min(p_175899_0_, p_175899_3_), Math.min(p_175899_1_, p_175899_4_), Math.min(p_175899_2_, p_175899_5_), Math.max(p_175899_0_, p_175899_3_), Math.max(p_175899_1_, p_175899_4_), Math.max(p_175899_2_, p_175899_5_));
    }
    
    public StructureBoundingBox(final StructureBoundingBox p_i2031_1_) {
        this.HorizonCode_Horizon_È = p_i2031_1_.HorizonCode_Horizon_È;
        this.Â = p_i2031_1_.Â;
        this.Ý = p_i2031_1_.Ý;
        this.Ø­áŒŠá = p_i2031_1_.Ø­áŒŠá;
        this.Âµá€ = p_i2031_1_.Âµá€;
        this.Ó = p_i2031_1_.Ó;
    }
    
    public StructureBoundingBox(final int p_i2032_1_, final int p_i2032_2_, final int p_i2032_3_, final int p_i2032_4_, final int p_i2032_5_, final int p_i2032_6_) {
        this.HorizonCode_Horizon_È = p_i2032_1_;
        this.Â = p_i2032_2_;
        this.Ý = p_i2032_3_;
        this.Ø­áŒŠá = p_i2032_4_;
        this.Âµá€ = p_i2032_5_;
        this.Ó = p_i2032_6_;
    }
    
    public StructureBoundingBox(final Vec3i p_i45626_1_, final Vec3i p_i45626_2_) {
        this.HorizonCode_Horizon_È = Math.min(p_i45626_1_.HorizonCode_Horizon_È(), p_i45626_2_.HorizonCode_Horizon_È());
        this.Â = Math.min(p_i45626_1_.Â(), p_i45626_2_.Â());
        this.Ý = Math.min(p_i45626_1_.Ý(), p_i45626_2_.Ý());
        this.Ø­áŒŠá = Math.max(p_i45626_1_.HorizonCode_Horizon_È(), p_i45626_2_.HorizonCode_Horizon_È());
        this.Âµá€ = Math.max(p_i45626_1_.Â(), p_i45626_2_.Â());
        this.Ó = Math.max(p_i45626_1_.Ý(), p_i45626_2_.Ý());
    }
    
    public StructureBoundingBox(final int p_i2033_1_, final int p_i2033_2_, final int p_i2033_3_, final int p_i2033_4_) {
        this.HorizonCode_Horizon_È = p_i2033_1_;
        this.Ý = p_i2033_2_;
        this.Ø­áŒŠá = p_i2033_3_;
        this.Ó = p_i2033_4_;
        this.Â = 1;
        this.Âµá€ = 512;
    }
    
    public boolean HorizonCode_Horizon_È(final StructureBoundingBox p_78884_1_) {
        return this.Ø­áŒŠá >= p_78884_1_.HorizonCode_Horizon_È && this.HorizonCode_Horizon_È <= p_78884_1_.Ø­áŒŠá && this.Ó >= p_78884_1_.Ý && this.Ý <= p_78884_1_.Ó && this.Âµá€ >= p_78884_1_.Â && this.Â <= p_78884_1_.Âµá€;
    }
    
    public boolean HorizonCode_Horizon_È(final int p_78885_1_, final int p_78885_2_, final int p_78885_3_, final int p_78885_4_) {
        return this.Ø­áŒŠá >= p_78885_1_ && this.HorizonCode_Horizon_È <= p_78885_3_ && this.Ó >= p_78885_2_ && this.Ý <= p_78885_4_;
    }
    
    public void Â(final StructureBoundingBox p_78888_1_) {
        this.HorizonCode_Horizon_È = Math.min(this.HorizonCode_Horizon_È, p_78888_1_.HorizonCode_Horizon_È);
        this.Â = Math.min(this.Â, p_78888_1_.Â);
        this.Ý = Math.min(this.Ý, p_78888_1_.Ý);
        this.Ø­áŒŠá = Math.max(this.Ø­áŒŠá, p_78888_1_.Ø­áŒŠá);
        this.Âµá€ = Math.max(this.Âµá€, p_78888_1_.Âµá€);
        this.Ó = Math.max(this.Ó, p_78888_1_.Ó);
    }
    
    public void HorizonCode_Horizon_È(final int p_78886_1_, final int p_78886_2_, final int p_78886_3_) {
        this.HorizonCode_Horizon_È += p_78886_1_;
        this.Â += p_78886_2_;
        this.Ý += p_78886_3_;
        this.Ø­áŒŠá += p_78886_1_;
        this.Âµá€ += p_78886_2_;
        this.Ó += p_78886_3_;
    }
    
    public boolean HorizonCode_Horizon_È(final Vec3i p_175898_1_) {
        return p_175898_1_.HorizonCode_Horizon_È() >= this.HorizonCode_Horizon_È && p_175898_1_.HorizonCode_Horizon_È() <= this.Ø­áŒŠá && p_175898_1_.Ý() >= this.Ý && p_175898_1_.Ý() <= this.Ó && p_175898_1_.Â() >= this.Â && p_175898_1_.Â() <= this.Âµá€;
    }
    
    public Vec3i Â() {
        return new Vec3i(this.Ø­áŒŠá - this.HorizonCode_Horizon_È, this.Âµá€ - this.Â, this.Ó - this.Ý);
    }
    
    public int Ý() {
        return this.Ø­áŒŠá - this.HorizonCode_Horizon_È + 1;
    }
    
    public int Ø­áŒŠá() {
        return this.Âµá€ - this.Â + 1;
    }
    
    public int Âµá€() {
        return this.Ó - this.Ý + 1;
    }
    
    public Vec3i Ó() {
        return new BlockPos(this.HorizonCode_Horizon_È + (this.Ø­áŒŠá - this.HorizonCode_Horizon_È + 1) / 2, this.Â + (this.Âµá€ - this.Â + 1) / 2, this.Ý + (this.Ó - this.Ý + 1) / 2);
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("x0", this.HorizonCode_Horizon_È).add("y0", this.Â).add("z0", this.Ý).add("x1", this.Ø­áŒŠá).add("y1", this.Âµá€).add("z1", this.Ó).toString();
    }
    
    public NBTTagIntArray à() {
        return new NBTTagIntArray(new int[] { this.HorizonCode_Horizon_È, this.Â, this.Ý, this.Ø­áŒŠá, this.Âµá€, this.Ó });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00001999";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                StructureBoundingBox.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                StructureBoundingBox.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                StructureBoundingBox.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                StructureBoundingBox.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
