package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public abstract class EntityFireball extends Entity
{
    private int Âµá€;
    private int Ó;
    private int à;
    private Block Ø;
    private boolean áŒŠÆ;
    public EntityLivingBase HorizonCode_Horizon_È;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    public double Â;
    public double Ý;
    public double Ø­áŒŠá;
    private static final String á = "CL_00001717";
    
    public EntityFireball(final World worldIn) {
        super(worldIn);
        this.Âµá€ = -1;
        this.Ó = -1;
        this.à = -1;
        this.HorizonCode_Horizon_È(1.0f, 1.0f);
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
    
    public EntityFireball(final World worldIn, final double p_i1760_2_, final double p_i1760_4_, final double p_i1760_6_, final double p_i1760_8_, final double p_i1760_10_, final double p_i1760_12_) {
        super(worldIn);
        this.Âµá€ = -1;
        this.Ó = -1;
        this.à = -1;
        this.HorizonCode_Horizon_È(1.0f, 1.0f);
        this.Â(p_i1760_2_, p_i1760_4_, p_i1760_6_, this.É, this.áƒ);
        this.Ý(p_i1760_2_, p_i1760_4_, p_i1760_6_);
        final double var14 = MathHelper.HorizonCode_Horizon_È(p_i1760_8_ * p_i1760_8_ + p_i1760_10_ * p_i1760_10_ + p_i1760_12_ * p_i1760_12_);
        this.Â = p_i1760_8_ / var14 * 0.1;
        this.Ý = p_i1760_10_ / var14 * 0.1;
        this.Ø­áŒŠá = p_i1760_12_ / var14 * 0.1;
    }
    
    public EntityFireball(final World worldIn, final EntityLivingBase p_i1761_2_, double p_i1761_3_, double p_i1761_5_, double p_i1761_7_) {
        super(worldIn);
        this.Âµá€ = -1;
        this.Ó = -1;
        this.à = -1;
        this.HorizonCode_Horizon_È = p_i1761_2_;
        this.HorizonCode_Horizon_È(1.0f, 1.0f);
        this.Â(p_i1761_2_.ŒÏ, p_i1761_2_.Çªà¢, p_i1761_2_.Ê, p_i1761_2_.É, p_i1761_2_.áƒ);
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        final double çžé = 0.0;
        this.ÇŽÕ = çžé;
        this.ˆá = çžé;
        this.ÇŽÉ = çžé;
        p_i1761_3_ += this.ˆáƒ.nextGaussian() * 0.4;
        p_i1761_5_ += this.ˆáƒ.nextGaussian() * 0.4;
        p_i1761_7_ += this.ˆáƒ.nextGaussian() * 0.4;
        final double var9 = MathHelper.HorizonCode_Horizon_È(p_i1761_3_ * p_i1761_3_ + p_i1761_5_ * p_i1761_5_ + p_i1761_7_ * p_i1761_7_);
        this.Â = p_i1761_3_ / var9 * 0.1;
        this.Ý = p_i1761_5_ / var9 * 0.1;
        this.Ø­áŒŠá = p_i1761_7_ / var9 * 0.1;
    }
    
    @Override
    public void á() {
        if (!this.Ï­Ðƒà.ŠÄ && ((this.HorizonCode_Horizon_È != null && this.HorizonCode_Horizon_È.ˆáŠ) || !this.Ï­Ðƒà.Ó(new BlockPos(this)))) {
            this.á€();
        }
        else {
            super.á();
            this.Âµá€(1);
            if (this.áŒŠÆ) {
                if (this.Ï­Ðƒà.Â(new BlockPos(this.Âµá€, this.Ó, this.à)).Ý() == this.Ø) {
                    ++this.áˆºÑ¢Õ;
                    if (this.áˆºÑ¢Õ == 600) {
                        this.á€();
                    }
                    return;
                }
                this.áŒŠÆ = false;
                this.ÇŽÉ *= this.ˆáƒ.nextFloat() * 0.2f;
                this.ˆá *= this.ˆáƒ.nextFloat() * 0.2f;
                this.ÇŽÕ *= this.ˆáƒ.nextFloat() * 0.2f;
                this.áˆºÑ¢Õ = 0;
                this.ÂµÈ = 0;
            }
            else {
                ++this.ÂµÈ;
            }
            Vec3 var1 = new Vec3(this.ŒÏ, this.Çªà¢, this.Ê);
            Vec3 var2 = new Vec3(this.ŒÏ + this.ÇŽÉ, this.Çªà¢ + this.ˆá, this.Ê + this.ÇŽÕ);
            MovingObjectPosition var3 = this.Ï­Ðƒà.HorizonCode_Horizon_È(var1, var2);
            var1 = new Vec3(this.ŒÏ, this.Çªà¢, this.Ê);
            var2 = new Vec3(this.ŒÏ + this.ÇŽÉ, this.Çªà¢ + this.ˆá, this.Ê + this.ÇŽÕ);
            if (var3 != null) {
                var2 = new Vec3(var3.Ý.HorizonCode_Horizon_È, var3.Ý.Â, var3.Ý.Ý);
            }
            Entity var4 = null;
            final List var5 = this.Ï­Ðƒà.Â(this, this.£É().HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ).Â(1.0, 1.0, 1.0));
            double var6 = 0.0;
            for (int var7 = 0; var7 < var5.size(); ++var7) {
                final Entity var8 = var5.get(var7);
                if (var8.Ô() && (!var8.Ø(this.HorizonCode_Horizon_È) || this.ÂµÈ >= 25)) {
                    final float var9 = 0.3f;
                    final AxisAlignedBB var10 = var8.£É().Â(var9, var9, var9);
                    final MovingObjectPosition var11 = var10.HorizonCode_Horizon_È(var1, var2);
                    if (var11 != null) {
                        final double var12 = var1.Ó(var11.Ý);
                        if (var12 < var6 || var6 == 0.0) {
                            var4 = var8;
                            var6 = var12;
                        }
                    }
                }
            }
            if (var4 != null) {
                var3 = new MovingObjectPosition(var4);
            }
            if (var3 != null) {
                this.HorizonCode_Horizon_È(var3);
            }
            this.ŒÏ += this.ÇŽÉ;
            this.Çªà¢ += this.ˆá;
            this.Ê += this.ÇŽÕ;
            final float var13 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
            this.É = (float)(Math.atan2(this.ÇŽÕ, this.ÇŽÉ) * 180.0 / 3.141592653589793) + 90.0f;
            this.áƒ = (float)(Math.atan2(var13, this.ˆá) * 180.0 / 3.141592653589793) - 90.0f;
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
            float var14 = this.à();
            if (this.£ÂµÄ()) {
                for (int var15 = 0; var15 < 4; ++var15) {
                    final float var16 = 0.25f;
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€, this.ŒÏ - this.ÇŽÉ * var16, this.Çªà¢ - this.ˆá * var16, this.Ê - this.ÇŽÕ * var16, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
                }
                var14 = 0.8f;
            }
            this.ÇŽÉ += this.Â;
            this.ˆá += this.Ý;
            this.ÇŽÕ += this.Ø­áŒŠá;
            this.ÇŽÉ *= var14;
            this.ˆá *= var14;
            this.ÇŽÕ *= var14;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.á, this.ŒÏ, this.Çªà¢ + 0.5, this.Ê, 0.0, 0.0, 0.0, new int[0]);
            this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        }
    }
    
    protected float à() {
        return 0.95f;
    }
    
    protected abstract void HorizonCode_Horizon_È(final MovingObjectPosition p0);
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("xTile", (short)this.Âµá€);
        tagCompound.HorizonCode_Horizon_È("yTile", (short)this.Ó);
        tagCompound.HorizonCode_Horizon_È("zTile", (short)this.à);
        final ResourceLocation_1975012498 var2 = (ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(this.Ø);
        tagCompound.HorizonCode_Horizon_È("inTile", (var2 == null) ? "" : var2.toString());
        tagCompound.HorizonCode_Horizon_È("inGround", (byte)(this.áŒŠÆ ? 1 : 0));
        tagCompound.HorizonCode_Horizon_È("direction", this.HorizonCode_Horizon_È(new double[] { this.ÇŽÉ, this.ˆá, this.ÇŽÕ }));
    }
    
    public void Â(final NBTTagCompound tagCompund) {
        this.Âµá€ = tagCompund.Âµá€("xTile");
        this.Ó = tagCompund.Âµá€("yTile");
        this.à = tagCompund.Âµá€("zTile");
        if (tagCompund.Â("inTile", 8)) {
            this.Ø = Block.HorizonCode_Horizon_È(tagCompund.áˆºÑ¢Õ("inTile"));
        }
        else {
            this.Ø = Block.HorizonCode_Horizon_È(tagCompund.Ø­áŒŠá("inTile") & 0xFF);
        }
        this.áŒŠÆ = (tagCompund.Ø­áŒŠá("inGround") == 1);
        if (tagCompund.Â("direction", 9)) {
            final NBTTagList var2 = tagCompund.Ý("direction", 6);
            this.ÇŽÉ = var2.Ø­áŒŠá(0);
            this.ˆá = var2.Ø­áŒŠá(1);
            this.ÇŽÕ = var2.Ø­áŒŠá(2);
        }
        else {
            this.á€();
        }
    }
    
    @Override
    public boolean Ô() {
        return true;
    }
    
    @Override
    public float £Ó() {
        return 1.0f;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        this.Ï();
        if (source.áˆºÑ¢Õ() != null) {
            final Vec3 var3 = source.áˆºÑ¢Õ().ˆÐƒØ­à();
            if (var3 != null) {
                this.ÇŽÉ = var3.HorizonCode_Horizon_È;
                this.ˆá = var3.Â;
                this.ÇŽÕ = var3.Ý;
                this.Â = this.ÇŽÉ * 0.1;
                this.Ý = this.ˆá * 0.1;
                this.Ø­áŒŠá = this.ÇŽÕ * 0.1;
            }
            if (source.áˆºÑ¢Õ() instanceof EntityLivingBase) {
                this.HorizonCode_Horizon_È = (EntityLivingBase)source.áˆºÑ¢Õ();
            }
            return true;
        }
        return false;
    }
    
    @Override
    public float Â(final float p_70013_1_) {
        return 1.0f;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        return 15728880;
    }
}
