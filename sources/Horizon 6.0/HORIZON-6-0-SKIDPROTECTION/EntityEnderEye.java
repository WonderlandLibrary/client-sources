package HORIZON-6-0-SKIDPROTECTION;

public class EntityEnderEye extends Entity
{
    private double HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    private int Ø­áŒŠá;
    private boolean Âµá€;
    private static final String Ó = "CL_00001716";
    
    public EntityEnderEye(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.25f, 0.25f);
    }
    
    @Override
    protected void ÂµÈ() {
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final double distance) {
        double var3 = this.£É().HorizonCode_Horizon_È() * 4.0;
        var3 *= 64.0;
        return distance < var3 * var3;
    }
    
    public EntityEnderEye(final World worldIn, final double p_i1758_2_, final double p_i1758_4_, final double p_i1758_6_) {
        super(worldIn);
        this.Ø­áŒŠá = 0;
        this.HorizonCode_Horizon_È(0.25f, 0.25f);
        this.Ý(p_i1758_2_, p_i1758_4_, p_i1758_6_);
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_180465_1_) {
        final double var2 = p_180465_1_.HorizonCode_Horizon_È();
        final int var3 = p_180465_1_.Â();
        final double var4 = p_180465_1_.Ý();
        final double var5 = var2 - this.ŒÏ;
        final double var6 = var4 - this.Ê;
        final float var7 = MathHelper.HorizonCode_Horizon_È(var5 * var5 + var6 * var6);
        if (var7 > 12.0f) {
            this.HorizonCode_Horizon_È = this.ŒÏ + var5 / var7 * 12.0;
            this.Ý = this.Ê + var6 / var7 * 12.0;
            this.Â = this.Çªà¢ + 8.0;
        }
        else {
            this.HorizonCode_Horizon_È = var2;
            this.Â = var3;
            this.Ý = var4;
        }
        this.Ø­áŒŠá = 0;
        this.Âµá€ = (this.ˆáƒ.nextInt(5) > 0);
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
        this.ŒÏ += this.ÇŽÉ;
        this.Çªà¢ += this.ˆá;
        this.Ê += this.ÇŽÕ;
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
        if (!this.Ï­Ðƒà.ŠÄ) {
            final double var2 = this.HorizonCode_Horizon_È - this.ŒÏ;
            final double var3 = this.Ý - this.Ê;
            final float var4 = (float)Math.sqrt(var2 * var2 + var3 * var3);
            final float var5 = (float)Math.atan2(var3, var2);
            double var6 = var1 + (var4 - var1) * 0.0025;
            if (var4 < 1.0f) {
                var6 *= 0.8;
                this.ˆá *= 0.8;
            }
            this.ÇŽÉ = Math.cos(var5) * var6;
            this.ÇŽÕ = Math.sin(var5) * var6;
            if (this.Çªà¢ < this.Â) {
                this.ˆá += (1.0 - this.ˆá) * 0.014999999664723873;
            }
            else {
                this.ˆá += (-1.0 - this.ˆá) * 0.014999999664723873;
            }
        }
        final float var7 = 0.25f;
        if (this.£ÂµÄ()) {
            for (int var8 = 0; var8 < 4; ++var8) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€, this.ŒÏ - this.ÇŽÉ * var7, this.Çªà¢ - this.ˆá * var7, this.Ê - this.ÇŽÕ * var7, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
            }
        }
        else {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà, this.ŒÏ - this.ÇŽÉ * var7 + this.ˆáƒ.nextDouble() * 0.6 - 0.3, this.Çªà¢ - this.ˆá * var7 - 0.5, this.Ê - this.ÇŽÕ * var7 + this.ˆáƒ.nextDouble() * 0.6 - 0.3, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
            ++this.Ø­áŒŠá;
            if (this.Ø­áŒŠá > 80 && !this.Ï­Ðƒà.ŠÄ) {
                this.á€();
                if (this.Âµá€) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(new EntityItem(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢, this.Ê, new ItemStack(Items.¥áŠ)));
                }
                else {
                    this.Ï­Ðƒà.Â(2003, new BlockPos(this), 0);
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
    }
    
    public void Â(final NBTTagCompound tagCompund) {
    }
    
    @Override
    public float Â(final float p_70013_1_) {
        return 1.0f;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        return 15728880;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
}
