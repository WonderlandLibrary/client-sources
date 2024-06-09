package HORIZON-6-0-SKIDPROTECTION;

public class EntityXPOrb extends Entity
{
    public int HorizonCode_Horizon_È;
    public int Â;
    public int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private EntityPlayer Ó;
    private int à;
    private static final String Ø = "CL_00001544";
    
    public EntityXPOrb(final World worldIn, final double p_i1585_2_, final double p_i1585_4_, final double p_i1585_6_, final int p_i1585_8_) {
        super(worldIn);
        this.Ø­áŒŠá = 5;
        this.HorizonCode_Horizon_È(0.5f, 0.5f);
        this.Ý(p_i1585_2_, p_i1585_4_, p_i1585_6_);
        this.É = (float)(Math.random() * 360.0);
        this.ÇŽÉ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.ˆá = (float)(Math.random() * 0.2) * 2.0f;
        this.ÇŽÕ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612) * 2.0f;
        this.Âµá€ = p_i1585_8_;
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    public EntityXPOrb(final World worldIn) {
        super(worldIn);
        this.Ø­áŒŠá = 5;
        this.HorizonCode_Horizon_È(0.25f, 0.25f);
    }
    
    @Override
    protected void ÂµÈ() {
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        float var2 = 0.5f;
        var2 = MathHelper.HorizonCode_Horizon_È(var2, 0.0f, 1.0f);
        final int var3 = super.HorizonCode_Horizon_È(p_70070_1_);
        int var4 = var3 & 0xFF;
        final int var5 = var3 >> 16 & 0xFF;
        var4 += (int)(var2 * 15.0f * 16.0f);
        if (var4 > 240) {
            var4 = 240;
        }
        return var4 | var5 << 16;
    }
    
    @Override
    public void á() {
        super.á();
        if (this.Ý > 0) {
            --this.Ý;
        }
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        this.ˆá -= 0.029999999329447746;
        if (this.Ï­Ðƒà.Â(new BlockPos(this)).Ý().Ó() == Material.áŒŠÆ) {
            this.ˆá = 0.20000000298023224;
            this.ÇŽÉ = (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f;
            this.ÇŽÕ = (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f;
            this.HorizonCode_Horizon_È("random.fizz", 0.4f, 2.0f + this.ˆáƒ.nextFloat() * 0.4f);
        }
        this.Â(this.ŒÏ, (this.£É().Â + this.£É().Âµá€) / 2.0, this.Ê);
        final double var1 = 8.0;
        if (this.à < this.HorizonCode_Horizon_È - 20 + this.ˆá() % 100) {
            if (this.Ó == null || this.Ó.Âµá€(this) > var1 * var1) {
                this.Ó = this.Ï­Ðƒà.HorizonCode_Horizon_È(this, var1);
            }
            this.à = this.HorizonCode_Horizon_È;
        }
        if (this.Ó != null && this.Ó.Ø­áŒŠá()) {
            this.Ó = null;
        }
        if (this.Ó != null) {
            final double var2 = (this.Ó.ŒÏ - this.ŒÏ) / var1;
            final double var3 = (this.Ó.Çªà¢ + this.Ó.Ðƒáƒ() - this.Çªà¢) / var1;
            final double var4 = (this.Ó.Ê - this.Ê) / var1;
            final double var5 = Math.sqrt(var2 * var2 + var3 * var3 + var4 * var4);
            double var6 = 1.0 - var5;
            if (var6 > 0.0) {
                var6 *= var6;
                this.ÇŽÉ += var2 / var5 * var6 * 0.1;
                this.ˆá += var3 / var5 * var6 * 0.1;
                this.ÇŽÕ += var4 / var5 * var6 * 0.1;
            }
        }
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        float var7 = 0.98f;
        if (this.ŠÂµà) {
            var7 = this.Ï­Ðƒà.Â(new BlockPos(MathHelper.Ý(this.ŒÏ), MathHelper.Ý(this.£É().Â) - 1, MathHelper.Ý(this.Ê))).Ý().áƒ * 0.98f;
        }
        this.ÇŽÉ *= var7;
        this.ˆá *= 0.9800000190734863;
        this.ÇŽÕ *= var7;
        if (this.ŠÂµà) {
            this.ˆá *= -0.8999999761581421;
        }
        ++this.HorizonCode_Horizon_È;
        ++this.Â;
        if (this.Â >= 6000) {
            this.á€();
        }
    }
    
    @Override
    public boolean Ø­Âµ() {
        return this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É(), Material.Ø, this);
    }
    
    @Override
    protected void Ó(final int amount) {
        this.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È, amount);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        this.Ï();
        this.Ø­áŒŠá -= (int)amount;
        if (this.Ø­áŒŠá <= 0) {
            this.á€();
        }
        return false;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("Health", (short)(byte)this.Ø­áŒŠá);
        tagCompound.HorizonCode_Horizon_È("Age", (short)this.Â);
        tagCompound.HorizonCode_Horizon_È("Value", (short)this.Âµá€);
    }
    
    public void Â(final NBTTagCompound tagCompund) {
        this.Ø­áŒŠá = (tagCompund.Âµá€("Health") & 0xFF);
        this.Â = tagCompund.Âµá€("Age");
        this.Âµá€ = tagCompund.Âµá€("Value");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer entityIn) {
        if (!this.Ï­Ðƒà.ŠÄ && this.Ý == 0 && entityIn.Œáƒ == 0) {
            entityIn.Œáƒ = 2;
            this.Ï­Ðƒà.HorizonCode_Horizon_È((Entity)entityIn, "random.orb", 0.1f, 0.5f * ((this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.7f + 1.8f));
            entityIn.Â(this, 1);
            entityIn.ˆà(this.Âµá€);
            this.á€();
        }
    }
    
    public int à() {
        return this.Âµá€;
    }
    
    public int Ø() {
        return (this.Âµá€ >= 2477) ? 10 : ((this.Âµá€ >= 1237) ? 9 : ((this.Âµá€ >= 617) ? 8 : ((this.Âµá€ >= 307) ? 7 : ((this.Âµá€ >= 149) ? 6 : ((this.Âµá€ >= 73) ? 5 : ((this.Âµá€ >= 37) ? 4 : ((this.Âµá€ >= 17) ? 3 : ((this.Âµá€ >= 7) ? 2 : ((this.Âµá€ >= 3) ? 1 : 0)))))))));
    }
    
    public static int HorizonCode_Horizon_È(final int p_70527_0_) {
        return (p_70527_0_ >= 2477) ? 2477 : ((p_70527_0_ >= 1237) ? 1237 : ((p_70527_0_ >= 617) ? 617 : ((p_70527_0_ >= 307) ? 307 : ((p_70527_0_ >= 149) ? 149 : ((p_70527_0_ >= 73) ? 73 : ((p_70527_0_ >= 37) ? 37 : ((p_70527_0_ >= 17) ? 17 : ((p_70527_0_ >= 7) ? 7 : ((p_70527_0_ >= 3) ? 3 : 1)))))))));
    }
    
    @Override
    public boolean Å() {
        return false;
    }
}
