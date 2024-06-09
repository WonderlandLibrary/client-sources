package HORIZON-6-0-SKIDPROTECTION;

public class EntityFireworkRocket extends Entity
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00001718";
    
    public EntityFireworkRocket(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.25f, 0.25f);
    }
    
    @Override
    protected void ÂµÈ() {
        this.£Ó.HorizonCode_Horizon_È(8, 5);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final double distance) {
        return distance < 4096.0;
    }
    
    public EntityFireworkRocket(final World worldIn, final double p_i1763_2_, final double p_i1763_4_, final double p_i1763_6_, final ItemStack p_i1763_8_) {
        super(worldIn);
        this.HorizonCode_Horizon_È = 0;
        this.HorizonCode_Horizon_È(0.25f, 0.25f);
        this.Ý(p_i1763_2_, p_i1763_4_, p_i1763_6_);
        int var9 = 1;
        if (p_i1763_8_ != null && p_i1763_8_.£á()) {
            this.£Ó.Â(8, p_i1763_8_);
            final NBTTagCompound var10 = p_i1763_8_.Å();
            final NBTTagCompound var11 = var10.ˆÏ­("Fireworks");
            if (var11 != null) {
                var9 += var11.Ø­áŒŠá("Flight");
            }
        }
        this.ÇŽÉ = this.ˆáƒ.nextGaussian() * 0.001;
        this.ÇŽÕ = this.ˆáƒ.nextGaussian() * 0.001;
        this.ˆá = 0.05;
        this.Â = 10 * var9 + this.ˆáƒ.nextInt(6) + this.ˆáƒ.nextInt(7);
    }
    
    @Override
    public void áŒŠÆ(final double x, final double y, final double z) {
        this.ÇŽÉ = x;
        this.ˆá = y;
        this.ÇŽÕ = z;
        if (this.Õ == 0.0f && this.á€ == 0.0f) {
            final float var7 = MathHelper.HorizonCode_Horizon_È(x * x + z * z);
            final float n = (float)(Math.atan2(x, z) * 180.0 / 3.141592653589793);
            this.É = n;
            this.á€ = n;
            final float n2 = (float)(Math.atan2(y, var7) * 180.0 / 3.141592653589793);
            this.áƒ = n2;
            this.Õ = n2;
        }
    }
    
    @Override
    public void á() {
        this.áˆºáˆºÈ = this.ŒÏ;
        this.ÇŽá€ = this.Çªà¢;
        this.Ï = this.Ê;
        super.á();
        this.ÇŽÉ *= 1.15;
        this.ÇŽÕ *= 1.15;
        this.ˆá += 0.04;
        this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
        final float var1 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
        this.É = (float)(Math.atan2(this.ÇŽÉ, this.ÇŽÕ) * 180.0 / 3.141592653589793);
        this.áƒ = (float)(Math.atan2(this.ˆá, var1) * 180.0 / 3.141592653589793);
        while (this.áƒ - this.Õ < -180.0f) {
            this.Õ -= 360.0f;
        }
        while (this.áƒ - this.Õ >= 180.0f) {
            this.Õ += 360.0f;
        }
        while (this.É - this.á€ < -180.0f) {
            this.á€ -= 360.0f;
        }
        while (this.É - this.á€ >= 180.0f) {
            this.á€ += 360.0f;
        }
        this.áƒ = this.Õ + (this.áƒ - this.Õ) * 0.2f;
        this.É = this.á€ + (this.É - this.á€) * 0.2f;
        if (this.HorizonCode_Horizon_È == 0 && !this.áŠ()) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, "fireworks.launch", 3.0f, 1.0f);
        }
        ++this.HorizonCode_Horizon_È;
        if (this.Ï­Ðƒà.ŠÄ && this.HorizonCode_Horizon_È % 2 < 2) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Ø­áŒŠá, this.ŒÏ, this.Çªà¢ - 0.3, this.Ê, this.ˆáƒ.nextGaussian() * 0.05, -this.ˆá * 0.5, this.ˆáƒ.nextGaussian() * 0.05, new int[0]);
        }
        if (!this.Ï­Ðƒà.ŠÄ && this.HorizonCode_Horizon_È > this.Â) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)17);
            this.á€();
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 17 && this.Ï­Ðƒà.ŠÄ) {
            final ItemStack var2 = this.£Ó.Ó(8);
            NBTTagCompound var3 = null;
            if (var2 != null && var2.£á()) {
                var3 = var2.Å().ˆÏ­("Fireworks");
            }
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, var3);
        }
        super.HorizonCode_Horizon_È(p_70103_1_);
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("Life", this.HorizonCode_Horizon_È);
        tagCompound.HorizonCode_Horizon_È("LifeTime", this.Â);
        final ItemStack var2 = this.£Ó.Ó(8);
        if (var2 != null) {
            final NBTTagCompound var3 = new NBTTagCompound();
            var2.Â(var3);
            tagCompound.HorizonCode_Horizon_È("FireworksItem", var3);
        }
    }
    
    public void Â(final NBTTagCompound tagCompund) {
        this.HorizonCode_Horizon_È = tagCompund.Ó("Life");
        this.Â = tagCompund.Ó("LifeTime");
        final NBTTagCompound var2 = tagCompund.ˆÏ­("FireworksItem");
        if (var2 != null) {
            final ItemStack var3 = ItemStack.HorizonCode_Horizon_È(var2);
            if (var3 != null) {
                this.£Ó.Â(8, var3);
            }
        }
    }
    
    @Override
    public float Â(final float p_70013_1_) {
        return super.Â(p_70013_1_);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        return super.HorizonCode_Horizon_È(p_70070_1_);
    }
    
    @Override
    public boolean Å() {
        return false;
    }
}
