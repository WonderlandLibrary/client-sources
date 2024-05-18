package HORIZON-6-0-SKIDPROTECTION;

public class EntityFireworkStarterFX extends EntityFX
{
    private int ÇŽá;
    private final EffectRenderer Ñ¢à;
    private NBTTagList ÇªØ­;
    boolean HorizonCode_Horizon_È;
    private static final String £áŒŠá = "CL_00000906";
    
    public EntityFireworkStarterFX(final World worldIn, final double p_i46355_2_, final double p_i46355_4_, final double p_i46355_6_, final double p_i46355_8_, final double p_i46355_10_, final double p_i46355_12_, final EffectRenderer p_i46355_14_, final NBTTagCompound p_i46355_15_) {
        super(worldIn, p_i46355_2_, p_i46355_4_, p_i46355_6_, 0.0, 0.0, 0.0);
        this.ÇŽÉ = p_i46355_8_;
        this.ˆá = p_i46355_10_;
        this.ÇŽÕ = p_i46355_12_;
        this.Ñ¢à = p_i46355_14_;
        this.à = 8;
        if (p_i46355_15_ != null) {
            this.ÇªØ­ = p_i46355_15_.Ý("Explosions", 10);
            if (this.ÇªØ­.Âµá€() == 0) {
                this.ÇªØ­ = null;
            }
            else {
                this.à = this.ÇªØ­.Âµá€() * 2 - 1;
                for (int var16 = 0; var16 < this.ÇªØ­.Âµá€(); ++var16) {
                    final NBTTagCompound var17 = this.ÇªØ­.Â(var16);
                    if (var17.£á("Flicker")) {
                        this.HorizonCode_Horizon_È = true;
                        this.à += 15;
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
    }
    
    @Override
    public void á() {
        if (this.ÇŽá == 0 && this.ÇªØ­ != null) {
            final boolean var1 = this.µà();
            boolean var2 = false;
            if (this.ÇªØ­.Âµá€() >= 3) {
                var2 = true;
            }
            else {
                for (int var3 = 0; var3 < this.ÇªØ­.Âµá€(); ++var3) {
                    final NBTTagCompound var4 = this.ÇªØ­.Â(var3);
                    if (var4.Ø­áŒŠá("Type") == 1) {
                        var2 = true;
                        break;
                    }
                }
            }
            final String var5 = "fireworks." + (var2 ? "largeBlast" : "blast") + (var1 ? "_far" : "");
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, var5, 20.0f, 0.95f + this.ˆáƒ.nextFloat() * 0.1f, true);
        }
        if (this.ÇŽá % 2 == 0 && this.ÇªØ­ != null && this.ÇŽá / 2 < this.ÇªØ­.Âµá€()) {
            final int var6 = this.ÇŽá / 2;
            final NBTTagCompound var7 = this.ÇªØ­.Â(var6);
            final byte var8 = var7.Ø­áŒŠá("Type");
            final boolean var9 = var7.£á("Trail");
            final boolean var10 = var7.£á("Flicker");
            int[] var11 = var7.á("Colors");
            final int[] var12 = var7.á("FadeColors");
            if (var11.length == 0) {
                var11 = new int[] { ItemDye.à[0] };
            }
            if (var8 == 1) {
                this.HorizonCode_Horizon_È(0.5, 4, var11, var12, var9, var10);
            }
            else if (var8 == 2) {
                this.HorizonCode_Horizon_È(0.5, new double[][] { { 0.0, 1.0 }, { 0.3455, 0.309 }, { 0.9511, 0.309 }, { 0.3795918367346939, -0.12653061224489795 }, { 0.6122448979591837, -0.8040816326530612 }, { 0.0, -0.35918367346938773 } }, var11, var12, var9, var10, false);
            }
            else if (var8 == 3) {
                this.HorizonCode_Horizon_È(0.5, new double[][] { { 0.0, 0.2 }, { 0.2, 0.2 }, { 0.2, 0.6 }, { 0.6, 0.6 }, { 0.6, 0.2 }, { 0.2, 0.2 }, { 0.2, 0.0 }, { 0.4, 0.0 }, { 0.4, -0.6 }, { 0.2, -0.6 }, { 0.2, -0.4 }, { 0.0, -0.4 } }, var11, var12, var9, var10, true);
            }
            else if (var8 == 4) {
                this.HorizonCode_Horizon_È(var11, var12, var9, var10);
            }
            else {
                this.HorizonCode_Horizon_È(0.25, 2, var11, var12, var9, var10);
            }
            final int var13 = var11[0];
            final float var14 = ((var13 & 0xFF0000) >> 16) / 255.0f;
            final float var15 = ((var13 & 0xFF00) >> 8) / 255.0f;
            final float var16 = ((var13 & 0xFF) >> 0) / 255.0f;
            final EntityFireworkOverlayFX var17 = new EntityFireworkOverlayFX(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢, this.Ê);
            var17.HorizonCode_Horizon_È(var14, var15, var16);
            this.Ñ¢à.HorizonCode_Horizon_È(var17);
        }
        ++this.ÇŽá;
        if (this.ÇŽá > this.à) {
            if (this.HorizonCode_Horizon_È) {
                final boolean var1 = this.µà();
                final String var18 = "fireworks." + (var1 ? "twinkle_far" : "twinkle");
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, var18, 20.0f, 0.9f + this.ˆáƒ.nextFloat() * 0.15f, true);
            }
            this.á€();
        }
    }
    
    private boolean µà() {
        final Minecraft var1 = Minecraft.áŒŠà();
        return var1 == null || var1.ÇŽá€() == null || var1.ÇŽá€().Âµá€(this.ŒÏ, this.Çªà¢, this.Ê) >= 256.0;
    }
    
    private void HorizonCode_Horizon_È(final double p_92034_1_, final double p_92034_3_, final double p_92034_5_, final double p_92034_7_, final double p_92034_9_, final double p_92034_11_, final int[] p_92034_13_, final int[] p_92034_14_, final boolean p_92034_15_, final boolean p_92034_16_) {
        final EntityFireworkSparkFX var17 = new EntityFireworkSparkFX(this.Ï­Ðƒà, p_92034_1_, p_92034_3_, p_92034_5_, p_92034_7_, p_92034_9_, p_92034_11_, this.Ñ¢à);
        var17.Âµá€(0.99f);
        var17.HorizonCode_Horizon_È(p_92034_15_);
        var17.Ý(p_92034_16_);
        final int var18 = this.ˆáƒ.nextInt(p_92034_13_.length);
        var17.Â(p_92034_13_[var18]);
        if (p_92034_14_ != null && p_92034_14_.length > 0) {
            var17.Ý(p_92034_14_[this.ˆáƒ.nextInt(p_92034_14_.length)]);
        }
        this.Ñ¢à.HorizonCode_Horizon_È(var17);
    }
    
    private void HorizonCode_Horizon_È(final double p_92035_1_, final int p_92035_3_, final int[] p_92035_4_, final int[] p_92035_5_, final boolean p_92035_6_, final boolean p_92035_7_) {
        final double var8 = this.ŒÏ;
        final double var9 = this.Çªà¢;
        final double var10 = this.Ê;
        for (int var11 = -p_92035_3_; var11 <= p_92035_3_; ++var11) {
            for (int var12 = -p_92035_3_; var12 <= p_92035_3_; ++var12) {
                for (int var13 = -p_92035_3_; var13 <= p_92035_3_; ++var13) {
                    final double var14 = var12 + (this.ˆáƒ.nextDouble() - this.ˆáƒ.nextDouble()) * 0.5;
                    final double var15 = var11 + (this.ˆáƒ.nextDouble() - this.ˆáƒ.nextDouble()) * 0.5;
                    final double var16 = var13 + (this.ˆáƒ.nextDouble() - this.ˆáƒ.nextDouble()) * 0.5;
                    final double var17 = MathHelper.HorizonCode_Horizon_È(var14 * var14 + var15 * var15 + var16 * var16) / p_92035_1_ + this.ˆáƒ.nextGaussian() * 0.05;
                    this.HorizonCode_Horizon_È(var8, var9, var10, var14 / var17, var15 / var17, var16 / var17, p_92035_4_, p_92035_5_, p_92035_6_, p_92035_7_);
                    if (var11 != -p_92035_3_ && var11 != p_92035_3_ && var12 != -p_92035_3_ && var12 != p_92035_3_) {
                        var13 += p_92035_3_ * 2 - 1;
                    }
                }
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final double p_92038_1_, final double[][] p_92038_3_, final int[] p_92038_4_, final int[] p_92038_5_, final boolean p_92038_6_, final boolean p_92038_7_, final boolean p_92038_8_) {
        final double var9 = p_92038_3_[0][0];
        final double var10 = p_92038_3_[0][1];
        this.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, var9 * p_92038_1_, var10 * p_92038_1_, 0.0, p_92038_4_, p_92038_5_, p_92038_6_, p_92038_7_);
        final float var11 = this.ˆáƒ.nextFloat() * 3.1415927f;
        final double var12 = p_92038_8_ ? 0.034 : 0.34;
        for (int var13 = 0; var13 < 3; ++var13) {
            final double var14 = var11 + var13 * 3.1415927f * var12;
            double var15 = var9;
            double var16 = var10;
            for (int var17 = 1; var17 < p_92038_3_.length; ++var17) {
                final double var18 = p_92038_3_[var17][0];
                final double var19 = p_92038_3_[var17][1];
                for (double var20 = 0.25; var20 <= 1.0; var20 += 0.25) {
                    double var21 = (var15 + (var18 - var15) * var20) * p_92038_1_;
                    final double var22 = (var16 + (var19 - var16) * var20) * p_92038_1_;
                    final double var23 = var21 * Math.sin(var14);
                    var21 *= Math.cos(var14);
                    for (double var24 = -1.0; var24 <= 1.0; var24 += 2.0) {
                        this.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, var21 * var24, var22, var23 * var24, p_92038_4_, p_92038_5_, p_92038_6_, p_92038_7_);
                    }
                }
                var15 = var18;
                var16 = var19;
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final int[] p_92036_1_, final int[] p_92036_2_, final boolean p_92036_3_, final boolean p_92036_4_) {
        final double var5 = this.ˆáƒ.nextGaussian() * 0.05;
        final double var6 = this.ˆáƒ.nextGaussian() * 0.05;
        for (int var7 = 0; var7 < 70; ++var7) {
            final double var8 = this.ÇŽÉ * 0.5 + this.ˆáƒ.nextGaussian() * 0.15 + var5;
            final double var9 = this.ÇŽÕ * 0.5 + this.ˆáƒ.nextGaussian() * 0.15 + var6;
            final double var10 = this.ˆá * 0.5 + this.ˆáƒ.nextDouble() * 0.5;
            this.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, var8, var10, var9, p_92036_1_, p_92036_2_, p_92036_3_, p_92036_4_);
        }
    }
    
    @Override
    public int Ø­áŒŠá() {
        return 0;
    }
}
