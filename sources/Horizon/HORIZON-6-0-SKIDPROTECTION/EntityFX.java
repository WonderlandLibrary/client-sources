package HORIZON-6-0-SKIDPROTECTION;

public class EntityFX extends Entity
{
    protected int Â;
    protected int Ý;
    protected float Ø­áŒŠá;
    protected float Âµá€;
    protected int Ó;
    protected int à;
    protected float Ø;
    protected float áŒŠÆ;
    protected float áˆºÑ¢Õ;
    protected float ÂµÈ;
    protected float á;
    protected float ˆÏ­;
    protected TextureAtlasSprite £á;
    public static double Å;
    public static double £à;
    public static double µà;
    private static final String HorizonCode_Horizon_È = "CL_00000914";
    
    protected EntityFX(final World worldIn, final double p_i46352_2_, final double p_i46352_4_, final double p_i46352_6_) {
        super(worldIn);
        this.ˆÏ­ = 1.0f;
        this.HorizonCode_Horizon_È(0.2f, 0.2f);
        this.Ý(p_i46352_2_, p_i46352_4_, p_i46352_6_);
        this.áˆºáˆºÈ = p_i46352_2_;
        this.ÇŽá€ = p_i46352_4_;
        this.Ï = p_i46352_6_;
        final float áˆºÑ¢Õ = 1.0f;
        this.á = áˆºÑ¢Õ;
        this.ÂµÈ = áˆºÑ¢Õ;
        this.áˆºÑ¢Õ = áˆºÑ¢Õ;
        this.Ø­áŒŠá = this.ˆáƒ.nextFloat() * 3.0f;
        this.Âµá€ = this.ˆáƒ.nextFloat() * 3.0f;
        this.Ø = (this.ˆáƒ.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.à = (int)(4.0f / (this.ˆáƒ.nextFloat() * 0.9f + 0.1f));
        this.Ó = 0;
    }
    
    public EntityFX(final World worldIn, final double p_i1219_2_, final double p_i1219_4_, final double p_i1219_6_, final double p_i1219_8_, final double p_i1219_10_, final double p_i1219_12_) {
        this(worldIn, p_i1219_2_, p_i1219_4_, p_i1219_6_);
        this.ÇŽÉ = p_i1219_8_ + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        this.ˆá = p_i1219_10_ + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        this.ÇŽÕ = p_i1219_12_ + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
        final float var14 = (float)(Math.random() + Math.random() + 1.0) * 0.15f;
        final float var15 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ˆá * this.ˆá + this.ÇŽÕ * this.ÇŽÕ);
        this.ÇŽÉ = this.ÇŽÉ / var15 * var14 * 0.4000000059604645;
        this.ˆá = this.ˆá / var15 * var14 * 0.4000000059604645 + 0.10000000149011612;
        this.ÇŽÕ = this.ÇŽÕ / var15 * var14 * 0.4000000059604645;
    }
    
    public EntityFX Ý(final float p_70543_1_) {
        this.ÇŽÉ *= p_70543_1_;
        this.ˆá = (this.ˆá - 0.10000000149011612) * p_70543_1_ + 0.10000000149011612;
        this.ÇŽÕ *= p_70543_1_;
        return this;
    }
    
    public EntityFX Ø­áŒŠá(final float p_70541_1_) {
        this.HorizonCode_Horizon_È(0.2f * p_70541_1_, 0.2f * p_70541_1_);
        this.Ø *= p_70541_1_;
        return this;
    }
    
    public void HorizonCode_Horizon_È(final float p_70538_1_, final float p_70538_2_, final float p_70538_3_) {
        this.áˆºÑ¢Õ = p_70538_1_;
        this.ÂµÈ = p_70538_2_;
        this.á = p_70538_3_;
    }
    
    public void Âµá€(final float p_82338_1_) {
        if (this.ˆÏ­ == 1.0f && p_82338_1_ < 1.0f) {
            Minecraft.áŒŠà().Å.Â(this);
        }
        else if (this.ˆÏ­ < 1.0f && p_82338_1_ == 1.0f) {
            Minecraft.áŒŠà().Å.Ý(this);
        }
        this.ˆÏ­ = p_82338_1_;
    }
    
    public float Ó() {
        return this.áˆºÑ¢Õ;
    }
    
    public float à() {
        return this.ÂµÈ;
    }
    
    public float Ø() {
        return this.á;
    }
    
    public float áŒŠÆ() {
        return this.ˆÏ­;
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    protected void ÂµÈ() {
    }
    
    @Override
    public void á() {
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        if (this.Ó++ >= this.à) {
            this.á€();
        }
        this.ˆá -= 0.04 * this.áŒŠÆ;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        this.ÇŽÉ *= 0.9800000190734863;
        this.ˆá *= 0.9800000190734863;
        this.ÇŽÕ *= 0.9800000190734863;
        if (this.ŠÂµà) {
            this.ÇŽÉ *= 0.699999988079071;
            this.ÇŽÕ *= 0.699999988079071;
        }
    }
    
    public void HorizonCode_Horizon_È(final WorldRenderer p_180434_1_, final Entity p_180434_2_, final float p_180434_3_, final float p_180434_4_, final float p_180434_5_, final float p_180434_6_, final float p_180434_7_, final float p_180434_8_) {
        float var9 = this.Â / 16.0f;
        float var10 = var9 + 0.0624375f;
        float var11 = this.Ý / 16.0f;
        float var12 = var11 + 0.0624375f;
        final float var13 = 0.1f * this.Ø;
        if (this.£á != null) {
            var9 = this.£á.Âµá€();
            var10 = this.£á.Ó();
            var11 = this.£á.à();
            var12 = this.£á.Ø();
        }
        final float var14 = (float)(this.áŒŠà + (this.ŒÏ - this.áŒŠà) * p_180434_3_ - EntityFX.Å);
        final float var15 = (float)(this.ŠÄ + (this.Çªà¢ - this.ŠÄ) * p_180434_3_ - EntityFX.£à);
        final float var16 = (float)(this.Ñ¢á + (this.Ê - this.Ñ¢á) * p_180434_3_ - EntityFX.µà);
        p_180434_1_.HorizonCode_Horizon_È(this.áˆºÑ¢Õ, this.ÂµÈ, this.á, this.ˆÏ­);
        p_180434_1_.HorizonCode_Horizon_È(var14 - p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13, var16 - p_180434_6_ * var13 - p_180434_8_ * var13, var10, var12);
        p_180434_1_.HorizonCode_Horizon_È(var14 - p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13, var16 - p_180434_6_ * var13 + p_180434_8_ * var13, var10, var11);
        p_180434_1_.HorizonCode_Horizon_È(var14 + p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13, var16 + p_180434_6_ * var13 + p_180434_8_ * var13, var9, var11);
        p_180434_1_.HorizonCode_Horizon_È(var14 + p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13, var16 + p_180434_6_ * var13 - p_180434_8_ * var13, var9, var12);
    }
    
    public int Ø­áŒŠá() {
        return 0;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
    }
    
    public void Â(final NBTTagCompound tagCompund) {
    }
    
    public void HorizonCode_Horizon_È(final TextureAtlasSprite p_180435_1_) {
        final int var2 = this.Ø­áŒŠá();
        if (var2 == 1) {
            this.£á = p_180435_1_;
            return;
        }
        throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
    }
    
    public void HorizonCode_Horizon_È(final int p_70536_1_) {
        if (this.Ø­áŒŠá() != 0) {
            throw new RuntimeException("Invalid call to Particle.setMiscTex");
        }
        this.Â = p_70536_1_ % 16;
        this.Ý = p_70536_1_ / 16;
    }
    
    public void ˆÏ­() {
        ++this.Â;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getClass().getSimpleName()) + ", Pos (" + this.ŒÏ + "," + this.Çªà¢ + "," + this.Ê + "), RGBA (" + this.áˆºÑ¢Õ + "," + this.ÂµÈ + "," + this.á + "," + this.ˆÏ­ + "), Age " + this.Ó;
    }
}
