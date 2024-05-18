package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.nio.ByteOrder;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.apache.logging.log4j.LogManager;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;

public class WorldRenderer
{
    private ByteBuffer HorizonCode_Horizon_È;
    private IntBuffer Â;
    private FloatBuffer Ý;
    private int Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private int à;
    private int Ø;
    private int áŒŠÆ;
    private boolean áˆºÑ¢Õ;
    private int ÂµÈ;
    private double á;
    private double ˆÏ­;
    private double £á;
    private int Å;
    private int £à;
    private VertexFormat µà;
    private boolean ˆà;
    private int ¥Æ;
    private static final String Ø­à = "CL_00000942";
    
    public WorldRenderer(final int p_i46275_1_) {
        this.¥Æ = p_i46275_1_;
        this.HorizonCode_Horizon_È = GLAllocation.Ý(p_i46275_1_ * 4);
        this.Â = this.HorizonCode_Horizon_È.asIntBuffer();
        this.Ý = this.HorizonCode_Horizon_È.asFloatBuffer();
        (this.µà = new VertexFormat()).HorizonCode_Horizon_È(new VertexFormatElement(0, VertexFormatElement.HorizonCode_Horizon_È.HorizonCode_Horizon_È, VertexFormatElement.Â.HorizonCode_Horizon_È, 3));
    }
    
    private void Âµá€(final int p_178983_1_) {
        LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + this.¥Æ * 4 + " bytes, new size " + (this.¥Æ * 4 + p_178983_1_) + " bytes.");
        this.¥Æ += p_178983_1_ / 4;
        final ByteBuffer var2 = GLAllocation.Ý(this.¥Æ * 4);
        this.Â.position(0);
        var2.asIntBuffer().put(this.Â);
        this.HorizonCode_Horizon_È = var2;
        this.Â = this.HorizonCode_Horizon_È.asIntBuffer();
        this.Ý = this.HorizonCode_Horizon_È.asFloatBuffer();
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È(final float p_178971_1_, final float p_178971_2_, final float p_178971_3_) {
        final int[] var4 = new int[this.áŒŠÆ];
        final PriorityQueue var5 = new PriorityQueue(this.áŒŠÆ, new QuadComparator(this.Ý, (float)(p_178971_1_ + this.á), (float)(p_178971_2_ + this.ˆÏ­), (float)(p_178971_3_ + this.£á), this.µà.Ó() / 4));
        final int var6 = this.µà.Ó();
        for (int var7 = 0; var7 < this.áŒŠÆ; var7 += var6) {
            var5.add(var7);
        }
        int var7 = 0;
        while (!var5.isEmpty()) {
            final int var8 = (int)var5.remove();
            for (int var9 = 0; var9 < var6; ++var9) {
                var4[var7 + var9] = this.Â.get(var8 + var9);
            }
            var7 += var6;
        }
        this.Â.clear();
        this.Â.put(var4);
        return new HorizonCode_Horizon_È(var4, this.áŒŠÆ, this.Ø­áŒŠá, new VertexFormat(this.µà));
    }
    
    public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_178993_1_) {
        if (p_178993_1_.HorizonCode_Horizon_È().length > this.Â.capacity()) {
            this.Âµá€(2097152);
        }
        this.Â.clear();
        this.Â.put(p_178993_1_.HorizonCode_Horizon_È());
        this.áŒŠÆ = p_178993_1_.Â();
        this.Ø­áŒŠá = p_178993_1_.Ý();
        this.µà = new VertexFormat(p_178993_1_.Ø­áŒŠá());
    }
    
    public void HorizonCode_Horizon_È() {
        this.Ø­áŒŠá = 0;
        this.áŒŠÆ = 0;
        this.µà.HorizonCode_Horizon_È();
        this.µà.HorizonCode_Horizon_È(new VertexFormatElement(0, VertexFormatElement.HorizonCode_Horizon_È.HorizonCode_Horizon_È, VertexFormatElement.Â.HorizonCode_Horizon_È, 3));
    }
    
    public void Â() {
        this.HorizonCode_Horizon_È(7);
    }
    
    public void HorizonCode_Horizon_È(final int p_178964_1_) {
        if (this.ˆà) {
            throw new IllegalStateException("Already building!");
        }
        this.ˆà = true;
        this.HorizonCode_Horizon_È();
        this.ÂµÈ = p_178964_1_;
        this.áˆºÑ¢Õ = false;
    }
    
    public void HorizonCode_Horizon_È(final double p_178992_1_, final double p_178992_3_) {
        if (!this.µà.HorizonCode_Horizon_È(0) && !this.µà.HorizonCode_Horizon_È(1)) {
            final VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.HorizonCode_Horizon_È.HorizonCode_Horizon_È, VertexFormatElement.Â.Ø­áŒŠá, 2);
            this.µà.HorizonCode_Horizon_È(var5);
        }
        this.Âµá€ = p_178992_1_;
        this.Ó = p_178992_3_;
    }
    
    public void Â(final int p_178963_1_) {
        if (!this.µà.HorizonCode_Horizon_È(1)) {
            if (!this.µà.HorizonCode_Horizon_È(0)) {
                this.µà.HorizonCode_Horizon_È(new VertexFormatElement(0, VertexFormatElement.HorizonCode_Horizon_È.HorizonCode_Horizon_È, VertexFormatElement.Â.Ø­áŒŠá, 2));
            }
            final VertexFormatElement var2 = new VertexFormatElement(1, VertexFormatElement.HorizonCode_Horizon_È.Âµá€, VertexFormatElement.Â.Ø­áŒŠá, 2);
            this.µà.HorizonCode_Horizon_È(var2);
        }
        this.à = p_178963_1_;
    }
    
    public void Â(final float p_178986_1_, final float p_178986_2_, final float p_178986_3_) {
        this.HorizonCode_Horizon_È((int)(p_178986_1_ * 255.0f), (int)(p_178986_2_ * 255.0f), (int)(p_178986_3_ * 255.0f));
    }
    
    public void HorizonCode_Horizon_È(final float p_178960_1_, final float p_178960_2_, final float p_178960_3_, final float p_178960_4_) {
        this.Â((int)(p_178960_1_ * 255.0f), (int)(p_178960_2_ * 255.0f), (int)(p_178960_3_ * 255.0f), (int)(p_178960_4_ * 255.0f));
    }
    
    public void HorizonCode_Horizon_È(final int p_78913_1_, final int p_78913_2_, final int p_78913_3_) {
        this.Â(p_78913_1_, p_78913_2_, p_78913_3_, 255);
    }
    
    public void HorizonCode_Horizon_È(final int p_178962_1_, final int p_178962_2_, final int p_178962_3_, final int p_178962_4_) {
        final int var5 = (this.Ø­áŒŠá - 4) * (this.µà.Ó() / 4) + this.µà.Â(1) / 4;
        final int var6 = this.µà.Ó() >> 2;
        this.Â.put(var5, p_178962_1_);
        this.Â.put(var5 + var6, p_178962_2_);
        this.Â.put(var5 + var6 * 2, p_178962_3_);
        this.Â.put(var5 + var6 * 3, p_178962_4_);
    }
    
    public void HorizonCode_Horizon_È(final double p_178987_1_, final double p_178987_3_, final double p_178987_5_) {
        if (this.áŒŠÆ >= this.¥Æ - this.µà.Ó()) {
            this.Âµá€(2097152);
        }
        final int var7 = this.µà.Ó() / 4;
        final int var8 = (this.Ø­áŒŠá - 4) * var7;
        for (int var9 = 0; var9 < 4; ++var9) {
            final int var10 = var8 + var9 * var7;
            final int var11 = var10 + 1;
            final int var12 = var11 + 1;
            this.Â.put(var10, Float.floatToRawIntBits((float)(p_178987_1_ + this.á) + Float.intBitsToFloat(this.Â.get(var10))));
            this.Â.put(var11, Float.floatToRawIntBits((float)(p_178987_3_ + this.ˆÏ­) + Float.intBitsToFloat(this.Â.get(var11))));
            this.Â.put(var12, Float.floatToRawIntBits((float)(p_178987_5_ + this.£á) + Float.intBitsToFloat(this.Â.get(var12))));
        }
    }
    
    private int Ó(final int p_78909_1_) {
        return ((this.Ø­áŒŠá - p_78909_1_) * this.µà.Ó() + this.µà.Âµá€()) / 4;
    }
    
    public void HorizonCode_Horizon_È(final float p_178978_1_, final float p_178978_2_, final float p_178978_3_, final int p_178978_4_) {
        final int var5 = this.Ó(p_178978_4_);
        int var6 = this.Â.get(var5);
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            final int var7 = (int)((var6 & 0xFF) * p_178978_1_);
            final int var8 = (int)((var6 >> 8 & 0xFF) * p_178978_2_);
            final int var9 = (int)((var6 >> 16 & 0xFF) * p_178978_3_);
            var6 &= 0xFF000000;
            var6 |= (var9 << 16 | var8 << 8 | var7);
        }
        else {
            final int var7 = (int)((this.Ø >> 24 & 0xFF) * p_178978_1_);
            final int var8 = (int)((this.Ø >> 16 & 0xFF) * p_178978_2_);
            final int var9 = (int)((this.Ø >> 8 & 0xFF) * p_178978_3_);
            var6 &= 0xFF;
            var6 |= (var7 << 24 | var8 << 16 | var9 << 8);
        }
        if (this.áˆºÑ¢Õ) {
            var6 = -1;
        }
        this.Â.put(var5, var6);
    }
    
    private void Â(final int p_178988_1_, final int p_178988_2_) {
        final int var3 = this.Ó(p_178988_2_);
        final int var4 = p_178988_1_ >> 16 & 0xFF;
        final int var5 = p_178988_1_ >> 8 & 0xFF;
        final int var6 = p_178988_1_ & 0xFF;
        final int var7 = p_178988_1_ >> 24 & 0xFF;
        this.HorizonCode_Horizon_È(var3, var4, var5, var6, var7);
    }
    
    public void Â(final float p_178994_1_, final float p_178994_2_, final float p_178994_3_, final int p_178994_4_) {
        final int var5 = this.Ó(p_178994_4_);
        final int var6 = MathHelper.HorizonCode_Horizon_È((int)(p_178994_1_ * 255.0f), 0, 255);
        final int var7 = MathHelper.HorizonCode_Horizon_È((int)(p_178994_2_ * 255.0f), 0, 255);
        final int var8 = MathHelper.HorizonCode_Horizon_È((int)(p_178994_3_ * 255.0f), 0, 255);
        this.HorizonCode_Horizon_È(var5, var6, var7, var8, 255);
    }
    
    private void HorizonCode_Horizon_È(final int p_178972_1_, final int p_178972_2_, final int p_178972_3_, final int p_178972_4_, final int p_178972_5_) {
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.Â.put(p_178972_1_, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | p_178972_2_);
        }
        else {
            this.Â.put(p_178972_1_, p_178972_2_ << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
        }
    }
    
    public void Â(int p_178961_1_, int p_178961_2_, int p_178961_3_, int p_178961_4_) {
        if (!this.áˆºÑ¢Õ) {
            if (p_178961_1_ > 255) {
                p_178961_1_ = 255;
            }
            if (p_178961_2_ > 255) {
                p_178961_2_ = 255;
            }
            if (p_178961_3_ > 255) {
                p_178961_3_ = 255;
            }
            if (p_178961_4_ > 255) {
                p_178961_4_ = 255;
            }
            if (p_178961_1_ < 0) {
                p_178961_1_ = 0;
            }
            if (p_178961_2_ < 0) {
                p_178961_2_ = 0;
            }
            if (p_178961_3_ < 0) {
                p_178961_3_ = 0;
            }
            if (p_178961_4_ < 0) {
                p_178961_4_ = 0;
            }
            if (!this.µà.Ø­áŒŠá()) {
                final VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.HorizonCode_Horizon_È.Â, VertexFormatElement.Â.Ý, 4);
                this.µà.HorizonCode_Horizon_È(var5);
            }
            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                this.Ø = (p_178961_4_ << 24 | p_178961_3_ << 16 | p_178961_2_ << 8 | p_178961_1_);
            }
            else {
                this.Ø = (p_178961_1_ << 24 | p_178961_2_ << 16 | p_178961_3_ << 8 | p_178961_4_);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final byte p_178982_1_, final byte p_178982_2_, final byte p_178982_3_) {
        this.HorizonCode_Horizon_È(p_178982_1_ & 0xFF, p_178982_2_ & 0xFF, p_178982_3_ & 0xFF);
    }
    
    public void HorizonCode_Horizon_È(final double p_178985_1_, final double p_178985_3_, final double p_178985_5_, final double p_178985_7_, final double p_178985_9_) {
        this.HorizonCode_Horizon_È(p_178985_7_, p_178985_9_);
        this.Â(p_178985_1_, p_178985_3_, p_178985_5_);
    }
    
    public void HorizonCode_Horizon_È(final VertexFormat p_178967_1_) {
        this.µà = new VertexFormat(p_178967_1_);
    }
    
    public void HorizonCode_Horizon_È(final int[] p_178981_1_) {
        final int var2 = this.µà.Ó() / 4;
        this.Ø­áŒŠá += p_178981_1_.length / var2;
        this.Â.position(this.áŒŠÆ);
        this.Â.put(p_178981_1_);
        this.áŒŠÆ += p_178981_1_.length;
    }
    
    public void Â(final double p_178984_1_, final double p_178984_3_, final double p_178984_5_) {
        if (this.áŒŠÆ >= this.¥Æ - this.µà.Ó()) {
            this.Âµá€(2097152);
        }
        final List var7 = this.µà.à();
        for (int listSize = var7.size(), i = 0; i < listSize; ++i) {
            final VertexFormatElement var8 = var7.get(i);
            final int var9 = var8.HorizonCode_Horizon_È() >> 2;
            final int var10 = this.áŒŠÆ + var9;
            switch (WorldRenderer.Â.HorizonCode_Horizon_È[var8.Ý().ordinal()]) {
                case 1: {
                    this.Â.put(var10, Float.floatToRawIntBits((float)(p_178984_1_ + this.á)));
                    this.Â.put(var10 + 1, Float.floatToRawIntBits((float)(p_178984_3_ + this.ˆÏ­)));
                    this.Â.put(var10 + 2, Float.floatToRawIntBits((float)(p_178984_5_ + this.£á)));
                    break;
                }
                case 2: {
                    this.Â.put(var10, this.Ø);
                    break;
                }
                case 3: {
                    if (var8.Âµá€() == 0) {
                        this.Â.put(var10, Float.floatToRawIntBits((float)this.Âµá€));
                        this.Â.put(var10 + 1, Float.floatToRawIntBits((float)this.Ó));
                        break;
                    }
                    this.Â.put(var10, this.à);
                    break;
                }
                case 4: {
                    this.Â.put(var10, this.Å);
                    break;
                }
            }
        }
        this.áŒŠÆ += this.µà.Ó() >> 2;
        ++this.Ø­áŒŠá;
    }
    
    public void Ý(final int p_178991_1_) {
        final int var2 = p_178991_1_ >> 16 & 0xFF;
        final int var3 = p_178991_1_ >> 8 & 0xFF;
        final int var4 = p_178991_1_ & 0xFF;
        this.HorizonCode_Horizon_È(var2, var3, var4);
    }
    
    public void HorizonCode_Horizon_È(final int p_178974_1_, final int p_178974_2_) {
        final int var3 = p_178974_1_ >> 16 & 0xFF;
        final int var4 = p_178974_1_ >> 8 & 0xFF;
        final int var5 = p_178974_1_ & 0xFF;
        this.Â(var3, var4, var5, p_178974_2_);
    }
    
    public void Ý() {
        this.áˆºÑ¢Õ = true;
    }
    
    public void Ý(final float p_178980_1_, final float p_178980_2_, final float p_178980_3_) {
        if (!this.µà.Â()) {
            final VertexFormatElement var7 = new VertexFormatElement(0, VertexFormatElement.HorizonCode_Horizon_È.Ý, VertexFormatElement.Â.Â, 3);
            this.µà.HorizonCode_Horizon_È(var7);
            this.µà.HorizonCode_Horizon_È(new VertexFormatElement(0, VertexFormatElement.HorizonCode_Horizon_È.Â, VertexFormatElement.Â.à, 1));
        }
        final byte var8 = (byte)(p_178980_1_ * 127.0f);
        final byte var9 = (byte)(p_178980_2_ * 127.0f);
        final byte var10 = (byte)(p_178980_3_ * 127.0f);
        this.Å = ((var8 & 0xFF) | (var9 & 0xFF) << 8 | (var10 & 0xFF) << 16);
    }
    
    public void Ø­áŒŠá(final float p_178975_1_, final float p_178975_2_, final float p_178975_3_) {
        final byte var4 = (byte)(p_178975_1_ * 127.0f);
        final byte var5 = (byte)(p_178975_2_ * 127.0f);
        final byte var6 = (byte)(p_178975_3_ * 127.0f);
        final int var7 = this.µà.Ó() >> 2;
        final int var8 = (this.Ø­áŒŠá - 4) * var7 + this.µà.Ý() / 4;
        this.Å = ((var4 & 0xFF) | (var5 & 0xFF) << 8 | (var6 & 0xFF) << 16);
        this.Â.put(var8, this.Å);
        this.Â.put(var8 + var7, this.Å);
        this.Â.put(var8 + var7 * 2, this.Å);
        this.Â.put(var8 + var7 * 3, this.Å);
    }
    
    public void Ý(final double p_178969_1_, final double p_178969_3_, final double p_178969_5_) {
        this.á = p_178969_1_;
        this.ˆÏ­ = p_178969_3_;
        this.£á = p_178969_5_;
    }
    
    public int Ø­áŒŠá() {
        if (!this.ˆà) {
            throw new IllegalStateException("Not building!");
        }
        this.ˆà = false;
        if (this.Ø­áŒŠá > 0) {
            this.HorizonCode_Horizon_È.position(0);
            this.HorizonCode_Horizon_È.limit(this.áŒŠÆ * 4);
        }
        return this.£à = this.áŒŠÆ * 4;
    }
    
    public int Âµá€() {
        return this.£à;
    }
    
    public ByteBuffer Ó() {
        return this.HorizonCode_Horizon_È;
    }
    
    public VertexFormat à() {
        return this.µà;
    }
    
    public int Ø() {
        return this.Ø­áŒŠá;
    }
    
    public int áŒŠÆ() {
        return this.ÂµÈ;
    }
    
    public void Ø­áŒŠá(final int p_178968_1_) {
        for (int var2 = 0; var2 < 4; ++var2) {
            this.Â(p_178968_1_, var2 + 1);
        }
    }
    
    public void Âµá€(final float p_178990_1_, final float p_178990_2_, final float p_178990_3_) {
        for (int var4 = 0; var4 < 4; ++var4) {
            this.Â(p_178990_1_, p_178990_2_, p_178990_3_, var4 + 1);
        }
    }
    
    public class HorizonCode_Horizon_È
    {
        private final int[] Â;
        private final int Ý;
        private final int Ø­áŒŠá;
        private final VertexFormat Âµá€;
        private static final String Ó = "CL_00002568";
        
        public HorizonCode_Horizon_È(final int[] p_i46274_2_, final int p_i46274_3_, final int p_i46274_4_, final VertexFormat p_i46274_5_) {
            this.Â = p_i46274_2_;
            this.Ý = p_i46274_3_;
            this.Ø­áŒŠá = p_i46274_4_;
            this.Âµá€ = p_i46274_5_;
        }
        
        public int[] HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        public int Â() {
            return this.Ý;
        }
        
        public int Ý() {
            return this.Ø­áŒŠá;
        }
        
        public VertexFormat Ø­áŒŠá() {
            return this.Âµá€;
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002569";
        
        static {
            HorizonCode_Horizon_È = new int[VertexFormatElement.Â.values().length];
            try {
                WorldRenderer.Â.HorizonCode_Horizon_È[VertexFormatElement.Â.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                WorldRenderer.Â.HorizonCode_Horizon_È[VertexFormatElement.Â.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                WorldRenderer.Â.HorizonCode_Horizon_È[VertexFormatElement.Â.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                WorldRenderer.Â.HorizonCode_Horizon_È[VertexFormatElement.Â.Â.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
