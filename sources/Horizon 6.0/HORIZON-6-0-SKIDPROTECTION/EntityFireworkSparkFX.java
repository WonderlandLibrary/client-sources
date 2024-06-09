package HORIZON-6-0-SKIDPROTECTION;

public class EntityFireworkSparkFX extends EntityFX
{
    private int HorizonCode_Horizon_È;
    private boolean ÇŽá;
    private boolean Ñ¢à;
    private final EffectRenderer ÇªØ­;
    private float £áŒŠá;
    private float áˆº;
    private float Šà;
    private boolean áŒŠá€;
    private static final String ¥Ï = "CL_00000905";
    
    public EntityFireworkSparkFX(final World worldIn, final double p_i46356_2_, final double p_i46356_4_, final double p_i46356_6_, final double p_i46356_8_, final double p_i46356_10_, final double p_i46356_12_, final EffectRenderer p_i46356_14_) {
        super(worldIn, p_i46356_2_, p_i46356_4_, p_i46356_6_);
        this.HorizonCode_Horizon_È = 160;
        this.ÇŽÉ = p_i46356_8_;
        this.ˆá = p_i46356_10_;
        this.ÇŽÕ = p_i46356_12_;
        this.ÇªØ­ = p_i46356_14_;
        this.Ø *= 0.75f;
        this.à = 48 + this.ˆáƒ.nextInt(12);
        this.ÇªÓ = false;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_92045_1_) {
        this.ÇŽá = p_92045_1_;
    }
    
    public void Ý(final boolean p_92043_1_) {
        this.Ñ¢à = p_92043_1_;
    }
    
    public void Â(final int p_92044_1_) {
        final float var2 = ((p_92044_1_ & 0xFF0000) >> 16) / 255.0f;
        final float var3 = ((p_92044_1_ & 0xFF00) >> 8) / 255.0f;
        final float var4 = ((p_92044_1_ & 0xFF) >> 0) / 255.0f;
        final float var5 = 1.0f;
        this.HorizonCode_Horizon_È(var2 * var5, var3 * var5, var4 * var5);
    }
    
    public void Ý(final int p_92046_1_) {
        this.£áŒŠá = ((p_92046_1_ & 0xFF0000) >> 16) / 255.0f;
        this.áˆº = ((p_92046_1_ & 0xFF00) >> 8) / 255.0f;
        this.Šà = ((p_92046_1_ & 0xFF) >> 0) / 255.0f;
        this.áŒŠá€ = true;
    }
    
    @Override
    public AxisAlignedBB t_() {
        return null;
    }
    
    @Override
    public boolean £à() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        if (!this.Ñ¢à || this.Ó < this.à / 3 || (this.Ó + this.à) / 3 % 2 == 0) {
            super.HorizonCode_Horizon_È(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
        }
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        if (this.Ó++ >= this.à) {
            this.á€();
        }
        if (this.Ó > this.à / 2) {
            this.Âµá€(1.0f - (this.Ó - this.à / 2) / this.à);
            if (this.áŒŠá€) {
                this.áˆºÑ¢Õ += (this.£áŒŠá - this.áˆºÑ¢Õ) * 0.2f;
                this.ÂµÈ += (this.áˆº - this.ÂµÈ) * 0.2f;
                this.á += (this.Šà - this.á) * 0.2f;
            }
        }
        this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È + (7 - this.Ó * 8 / this.à));
        this.ˆá -= 0.004;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        this.ÇŽÉ *= 0.9100000262260437;
        this.ˆá *= 0.9100000262260437;
        this.ÇŽÕ *= 0.9100000262260437;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
        if (this.ÇŽá && this.Ó < this.à / 2 && (this.Ó + this.à) % 2 == 0) {
            final EntityFireworkSparkFX var1 = new EntityFireworkSparkFX(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢, this.Ê, 0.0, 0.0, 0.0, this.ÇªØ­);
            var1.Âµá€(0.99f);
            var1.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, this.ÂµÈ, this.á);
            var1.Ó = var1.à / 2;
            if (this.áŒŠá€) {
                var1.áŒŠá€ = true;
                var1.£áŒŠá = this.£áŒŠá;
                var1.áˆº = this.áˆº;
                var1.Šà = this.Šà;
            }
            var1.Ñ¢à = this.Ñ¢à;
            this.ÇªØ­.HorizonCode_Horizon_È(var1);
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        return 15728880;
    }
    
    @Override
    public float Â(final float p_70013_1_) {
        return 1.0f;
    }
}
