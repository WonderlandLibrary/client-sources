package HORIZON-6-0-SKIDPROTECTION;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.image.BufferedImage;
import com.google.common.collect.Lists;
import java.util.List;

public class TextureAtlasSprite
{
    private final String áˆºÑ¢Õ;
    protected List HorizonCode_Horizon_È;
    protected int[][] Â;
    private AnimationMetadataSection ÂµÈ;
    protected boolean Ý;
    protected int Ø­áŒŠá;
    protected int Âµá€;
    protected int Ó;
    protected int à;
    private float á;
    private float ˆÏ­;
    private float £á;
    private float Å;
    protected int Ø;
    protected int áŒŠÆ;
    private static String £à;
    private static String µà;
    private static final String ˆà = "CL_00001062";
    private int ¥Æ;
    
    static {
        TextureAtlasSprite.£à = "builtin/clock";
        TextureAtlasSprite.µà = "builtin/compass";
    }
    
    protected TextureAtlasSprite(final String p_i1282_1_) {
        this.HorizonCode_Horizon_È = Lists.newArrayList();
        this.¥Æ = -1;
        this.áˆºÑ¢Õ = p_i1282_1_;
    }
    
    protected static TextureAtlasSprite HorizonCode_Horizon_È(final ResourceLocation_1975012498 p_176604_0_) {
        final String var1 = p_176604_0_.toString();
        return TextureAtlasSprite.£à.equals(var1) ? new TextureClock(var1) : (TextureAtlasSprite.µà.equals(var1) ? new TextureCompass(var1) : new TextureAtlasSprite(var1));
    }
    
    public static void HorizonCode_Horizon_È(final String p_176602_0_) {
        TextureAtlasSprite.£à = p_176602_0_;
    }
    
    public static void Â(final String p_176603_0_) {
        TextureAtlasSprite.µà = p_176603_0_;
    }
    
    public void HorizonCode_Horizon_È(final int p_110971_1_, final int p_110971_2_, final int p_110971_3_, final int p_110971_4_, final boolean p_110971_5_) {
        this.Ø­áŒŠá = p_110971_3_;
        this.Âµá€ = p_110971_4_;
        this.Ý = p_110971_5_;
        final float var6 = (float)(0.009999999776482582 / p_110971_1_);
        final float var7 = (float)(0.009999999776482582 / p_110971_2_);
        this.á = p_110971_3_ / p_110971_1_ + var6;
        this.ˆÏ­ = (p_110971_3_ + this.Ó) / p_110971_1_ - var6;
        this.£á = p_110971_4_ / p_110971_2_ + var7;
        this.Å = (p_110971_4_ + this.à) / p_110971_2_ - var7;
    }
    
    public void HorizonCode_Horizon_È(final TextureAtlasSprite p_94217_1_) {
        this.Ø­áŒŠá = p_94217_1_.Ø­áŒŠá;
        this.Âµá€ = p_94217_1_.Âµá€;
        this.Ó = p_94217_1_.Ó;
        this.à = p_94217_1_.à;
        this.Ý = p_94217_1_.Ý;
        this.á = p_94217_1_.á;
        this.ˆÏ­ = p_94217_1_.ˆÏ­;
        this.£á = p_94217_1_.£á;
        this.Å = p_94217_1_.Å;
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public int Â() {
        return this.Âµá€;
    }
    
    public int Ý() {
        return this.Ó;
    }
    
    public int Ø­áŒŠá() {
        return this.à;
    }
    
    public float Âµá€() {
        return this.á;
    }
    
    public float Ó() {
        return this.ˆÏ­;
    }
    
    public float HorizonCode_Horizon_È(final double p_94214_1_) {
        final float var3 = this.ˆÏ­ - this.á;
        return this.á + var3 * (float)p_94214_1_ / 16.0f;
    }
    
    public float à() {
        return this.£á;
    }
    
    public float Ø() {
        return this.Å;
    }
    
    public float Â(final double p_94207_1_) {
        final float var3 = this.Å - this.£á;
        return this.£á + var3 * ((float)p_94207_1_ / 16.0f);
    }
    
    public String áŒŠÆ() {
        return this.áˆºÑ¢Õ;
    }
    
    public void áˆºÑ¢Õ() {
        ++this.áŒŠÆ;
        if (this.áŒŠÆ >= this.ÂµÈ.HorizonCode_Horizon_È(this.Ø)) {
            final int var1 = this.ÂµÈ.Ý(this.Ø);
            final int var2 = (this.ÂµÈ.Ý() == 0) ? this.HorizonCode_Horizon_È.size() : this.ÂµÈ.Ý();
            this.Ø = (this.Ø + 1) % var2;
            this.áŒŠÆ = 0;
            final int var3 = this.ÂµÈ.Ý(this.Ø);
            if (var1 != var3 && var3 >= 0 && var3 < this.HorizonCode_Horizon_È.size()) {
                TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È.get(var3), this.Ó, this.à, this.Ø­áŒŠá, this.Âµá€, false, false);
            }
        }
        else if (this.ÂµÈ.Âµá€()) {
            this.Å();
        }
    }
    
    private void Å() {
        final double var1 = 1.0 - this.áŒŠÆ / this.ÂµÈ.HorizonCode_Horizon_È(this.Ø);
        final int var2 = this.ÂµÈ.Ý(this.Ø);
        final int var3 = (this.ÂµÈ.Ý() == 0) ? this.HorizonCode_Horizon_È.size() : this.ÂµÈ.Ý();
        final int var4 = this.ÂµÈ.Ý((this.Ø + 1) % var3);
        if (var2 != var4 && var4 >= 0 && var4 < this.HorizonCode_Horizon_È.size()) {
            final int[][] var5 = this.HorizonCode_Horizon_È.get(var2);
            final int[][] var6 = this.HorizonCode_Horizon_È.get(var4);
            if (this.Â == null || this.Â.length != var5.length) {
                this.Â = new int[var5.length][];
            }
            for (int var7 = 0; var7 < var5.length; ++var7) {
                if (this.Â[var7] == null) {
                    this.Â[var7] = new int[var5[var7].length];
                }
                if (var7 < var6.length && var6[var7].length == var5[var7].length) {
                    for (int var8 = 0; var8 < var5[var7].length; ++var8) {
                        final int var9 = var5[var7][var8];
                        final int var10 = var6[var7][var8];
                        final int var11 = (int)(((var9 & 0xFF0000) >> 16) * var1 + ((var10 & 0xFF0000) >> 16) * (1.0 - var1));
                        final int var12 = (int)(((var9 & 0xFF00) >> 8) * var1 + ((var10 & 0xFF00) >> 8) * (1.0 - var1));
                        final int var13 = (int)((var9 & 0xFF) * var1 + (var10 & 0xFF) * (1.0 - var1));
                        this.Â[var7][var8] = ((var9 & 0xFF000000) | var11 << 16 | var12 << 8 | var13);
                    }
                }
            }
            TextureUtil.HorizonCode_Horizon_È(this.Â, this.Ó, this.à, this.Ø­áŒŠá, this.Âµá€, false, false);
        }
    }
    
    public int[][] HorizonCode_Horizon_È(final int p_147965_1_) {
        return this.HorizonCode_Horizon_È.get(p_147965_1_);
    }
    
    public int ÂµÈ() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    public void Â(final int p_110966_1_) {
        this.Ó = p_110966_1_;
    }
    
    public void Ý(final int p_110969_1_) {
        this.à = p_110969_1_;
    }
    
    public void HorizonCode_Horizon_È(final BufferedImage[] p_180598_1_, final AnimationMetadataSection p_180598_2_) {
        this.£à();
        final int var3 = p_180598_1_[0].getWidth();
        final int var4 = p_180598_1_[0].getHeight();
        this.Ó = var3;
        this.à = var4;
        final int[][] var5 = new int[p_180598_1_.length][];
        for (int var6 = 0; var6 < p_180598_1_.length; ++var6) {
            final BufferedImage i = p_180598_1_[var6];
            if (i != null) {
                if (var6 > 0 && (i.getWidth() != var3 >> var6 || i.getHeight() != var4 >> var6)) {
                    throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", var6, i.getWidth(), i.getHeight(), var3 >> var6, var4 >> var6));
                }
                var5[var6] = new int[i.getWidth() * i.getHeight()];
                i.getRGB(0, 0, i.getWidth(), i.getHeight(), var5[var6], 0, i.getWidth());
            }
        }
        if (p_180598_2_ == null) {
            if (var4 != var3) {
                throw new RuntimeException("broken aspect ratio and not an animation");
            }
            this.HorizonCode_Horizon_È.add(var5);
        }
        else {
            final int var6 = var4 / var3;
            final int var7 = var3;
            final int datas = var3;
            this.à = this.Ó;
            if (p_180598_2_.Ý() > 0) {
                for (final int di : p_180598_2_.Ó()) {
                    if (di >= var6) {
                        throw new RuntimeException("invalid frameindex " + di);
                    }
                    this.Ó(di);
                    this.HorizonCode_Horizon_È.set(di, HorizonCode_Horizon_È(var5, var7, datas, di));
                }
                this.ÂµÈ = p_180598_2_;
            }
            else {
                final ArrayList var8 = Lists.newArrayList();
                for (int di = 0; di < var6; ++di) {
                    this.HorizonCode_Horizon_È.add(HorizonCode_Horizon_È(var5, var7, datas, di));
                    var8.add(new AnimationFrame(di, -1));
                }
                this.ÂµÈ = new AnimationMetadataSection(var8, this.Ó, this.à, p_180598_2_.Ø­áŒŠá(), p_180598_2_.Âµá€());
            }
        }
        for (int var7 = 0; var7 < this.HorizonCode_Horizon_È.size(); ++var7) {
            final int[][] var9 = this.HorizonCode_Horizon_È.get(var7);
            if (var9 != null) {
                for (int di = 0; di < var9.length; ++di) {
                    final int[] var10 = var9[di];
                    this.HorizonCode_Horizon_È(var10);
                }
            }
        }
    }
    
    public void Ø­áŒŠá(final int p_147963_1_) {
        final ArrayList var2 = Lists.newArrayList();
        for (int var3 = 0; var3 < this.HorizonCode_Horizon_È.size(); ++var3) {
            final int[][] var4 = this.HorizonCode_Horizon_È.get(var3);
            if (var4 != null) {
                try {
                    var2.add(TextureUtil.HorizonCode_Horizon_È(p_147963_1_, this.Ó, var4));
                }
                catch (Throwable var6) {
                    final CrashReport var5 = CrashReport.HorizonCode_Horizon_È(var6, "Generating mipmaps for frame");
                    final CrashReportCategory var7 = var5.HorizonCode_Horizon_È("Frame being iterated");
                    var7.HorizonCode_Horizon_È("Frame index", var3);
                    var7.HorizonCode_Horizon_È("Frame sizes", new Callable() {
                        private static final String Â = "CL_00001063";
                        
                        public String HorizonCode_Horizon_È() {
                            final StringBuilder var1 = new StringBuilder();
                            for (final int[] var4 : var4) {
                                if (var1.length() > 0) {
                                    var1.append(", ");
                                }
                                var1.append((var4 == null) ? "null" : var4.length);
                            }
                            return var1.toString();
                        }
                    });
                    throw new ReportedException(var5);
                }
            }
        }
        this.HorizonCode_Horizon_È(var2);
    }
    
    private void Ó(final int p_130099_1_) {
        if (this.HorizonCode_Horizon_È.size() <= p_130099_1_) {
            for (int var2 = this.HorizonCode_Horizon_È.size(); var2 <= p_130099_1_; ++var2) {
                this.HorizonCode_Horizon_È.add(null);
            }
        }
    }
    
    private static int[][] HorizonCode_Horizon_È(final int[][] p_147962_0_, final int p_147962_1_, final int p_147962_2_, final int p_147962_3_) {
        final int[][] var4 = new int[p_147962_0_.length][];
        for (int var5 = 0; var5 < p_147962_0_.length; ++var5) {
            final int[] var6 = p_147962_0_[var5];
            if (var6 != null) {
                var4[var5] = new int[(p_147962_1_ >> var5) * (p_147962_2_ >> var5)];
                System.arraycopy(var6, p_147962_3_ * var4[var5].length, var4[var5], 0, var4[var5].length);
            }
        }
        return var4;
    }
    
    public void á() {
        this.HorizonCode_Horizon_È.clear();
    }
    
    public boolean ˆÏ­() {
        return this.ÂµÈ != null;
    }
    
    public void HorizonCode_Horizon_È(final List p_110968_1_) {
        this.HorizonCode_Horizon_È = p_110968_1_;
    }
    
    private void £à() {
        this.ÂµÈ = null;
        this.HorizonCode_Horizon_È(Lists.newArrayList());
        this.Ø = 0;
        this.áŒŠÆ = 0;
    }
    
    @Override
    public String toString() {
        return "TextureAtlasSprite{name='" + this.áˆºÑ¢Õ + '\'' + ", frameCount=" + this.HorizonCode_Horizon_È.size() + ", rotated=" + this.Ý + ", x=" + this.Ø­áŒŠá + ", y=" + this.Âµá€ + ", height=" + this.à + ", width=" + this.Ó + ", u0=" + this.á + ", u1=" + this.ˆÏ­ + ", v0=" + this.£á + ", v1=" + this.Å + '}';
    }
    
    public boolean HorizonCode_Horizon_È(final IResourceManager manager, final ResourceLocation_1975012498 location) {
        return false;
    }
    
    public boolean Â(final IResourceManager manager, final ResourceLocation_1975012498 location) {
        return true;
    }
    
    public int £á() {
        return this.¥Æ;
    }
    
    public void Âµá€(final int indexInMap) {
        this.¥Æ = indexInMap;
    }
    
    private void HorizonCode_Horizon_È(final int[] data) {
        if (data != null) {
            long redSum = 0L;
            long greenSum = 0L;
            long blueSum = 0L;
            long count = 0L;
            for (int redAvg = 0; redAvg < data.length; ++redAvg) {
                final int greenAvg = data[redAvg];
                final int blueAvg = greenAvg >> 24 & 0xFF;
                if (blueAvg != 0) {
                    final int i = greenAvg >> 16 & 0xFF;
                    final int col = greenAvg >> 8 & 0xFF;
                    final int alpha = greenAvg & 0xFF;
                    redSum += i;
                    greenSum += col;
                    blueSum += alpha;
                    ++count;
                }
            }
            if (count > 0L) {
                final int redAvg = (int)(redSum / count);
                final int greenAvg = (int)(greenSum / count);
                final int blueAvg = (int)(blueSum / count);
                for (int i = 0; i < data.length; ++i) {
                    final int col = data[i];
                    final int alpha = col >> 24 & 0xFF;
                    if (alpha == 0) {
                        data[i] = (redAvg << 16 | greenAvg << 8 | blueAvg);
                    }
                }
            }
        }
    }
    
    public double HorizonCode_Horizon_È(final float atlasU) {
        final float dU = this.ˆÏ­ - this.á;
        return (atlasU - this.á) / dU * 16.0f;
    }
    
    public double Â(final float atlasV) {
        final float dV = this.Å - this.£á;
        return (atlasV - this.£á) / dV * 16.0f;
    }
}
