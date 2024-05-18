package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public abstract class EntityThrowable extends Entity implements IProjectile
{
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private Block Ó;
    protected boolean HorizonCode_Horizon_È;
    public int Â;
    private EntityLivingBase à;
    private String Ø;
    private int áŒŠÆ;
    private int áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00001723";
    
    public EntityThrowable(final World worldIn) {
        super(worldIn);
        this.Ý = -1;
        this.Ø­áŒŠá = -1;
        this.Âµá€ = -1;
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
    
    public EntityThrowable(final World worldIn, final EntityLivingBase p_i1777_2_) {
        super(worldIn);
        this.Ý = -1;
        this.Ø­áŒŠá = -1;
        this.Âµá€ = -1;
        this.à = p_i1777_2_;
        this.HorizonCode_Horizon_È(0.25f, 0.25f);
        this.Â(p_i1777_2_.ŒÏ, p_i1777_2_.Çªà¢ + p_i1777_2_.Ðƒáƒ(), p_i1777_2_.Ê, p_i1777_2_.É, p_i1777_2_.áƒ);
        this.ŒÏ -= MathHelper.Â(this.É / 180.0f * 3.1415927f) * 0.16f;
        this.Çªà¢ -= 0.10000000149011612;
        this.Ê -= MathHelper.HorizonCode_Horizon_È(this.É / 180.0f * 3.1415927f) * 0.16f;
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        final float var3 = 0.4f;
        this.ÇŽÉ = -MathHelper.HorizonCode_Horizon_È(this.É / 180.0f * 3.1415927f) * MathHelper.Â(this.áƒ / 180.0f * 3.1415927f) * var3;
        this.ÇŽÕ = MathHelper.Â(this.É / 180.0f * 3.1415927f) * MathHelper.Â(this.áƒ / 180.0f * 3.1415927f) * var3;
        this.ˆá = -MathHelper.HorizonCode_Horizon_È((this.áƒ + this.áŒŠÆ()) / 180.0f * 3.1415927f) * var3;
        this.a_(this.ÇŽÉ, this.ˆá, this.ÇŽÕ, this.Ø(), 1.0f);
    }
    
    public EntityThrowable(final World worldIn, final double p_i1778_2_, final double p_i1778_4_, final double p_i1778_6_) {
        super(worldIn);
        this.Ý = -1;
        this.Ø­áŒŠá = -1;
        this.Âµá€ = -1;
        this.áŒŠÆ = 0;
        this.HorizonCode_Horizon_È(0.25f, 0.25f);
        this.Ý(p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }
    
    protected float Ø() {
        return 1.5f;
    }
    
    protected float áŒŠÆ() {
        return 0.0f;
    }
    
    @Override
    public void a_(double p_70186_1_, double p_70186_3_, double p_70186_5_, final float p_70186_7_, final float p_70186_8_) {
        final float var9 = MathHelper.HorizonCode_Horizon_È(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= var9;
        p_70186_3_ /= var9;
        p_70186_5_ /= var9;
        p_70186_1_ += this.ˆáƒ.nextGaussian() * 0.007499999832361937 * p_70186_8_;
        p_70186_3_ += this.ˆáƒ.nextGaussian() * 0.007499999832361937 * p_70186_8_;
        p_70186_5_ += this.ˆáƒ.nextGaussian() * 0.007499999832361937 * p_70186_8_;
        p_70186_1_ *= p_70186_7_;
        p_70186_3_ *= p_70186_7_;
        p_70186_5_ *= p_70186_7_;
        this.ÇŽÉ = p_70186_1_;
        this.ˆá = p_70186_3_;
        this.ÇŽÕ = p_70186_5_;
        final float var10 = MathHelper.HorizonCode_Horizon_È(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
        final float n = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0 / 3.141592653589793);
        this.É = n;
        this.á€ = n;
        final float n2 = (float)(Math.atan2(p_70186_3_, var10) * 180.0 / 3.141592653589793);
        this.áƒ = n2;
        this.Õ = n2;
        this.áŒŠÆ = 0;
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
        if (this.Â > 0) {
            --this.Â;
        }
        if (this.HorizonCode_Horizon_È) {
            if (this.Ï­Ðƒà.Â(new BlockPos(this.Ý, this.Ø­áŒŠá, this.Âµá€)).Ý() == this.Ó) {
                ++this.áŒŠÆ;
                if (this.áŒŠÆ == 1200) {
                    this.á€();
                }
                return;
            }
            this.HorizonCode_Horizon_È = false;
            this.ÇŽÉ *= this.ˆáƒ.nextFloat() * 0.2f;
            this.ˆá *= this.ˆáƒ.nextFloat() * 0.2f;
            this.ÇŽÕ *= this.ˆáƒ.nextFloat() * 0.2f;
            this.áŒŠÆ = 0;
            this.áˆºÑ¢Õ = 0;
        }
        else {
            ++this.áˆºÑ¢Õ;
        }
        Vec3 var1 = new Vec3(this.ŒÏ, this.Çªà¢, this.Ê);
        Vec3 var2 = new Vec3(this.ŒÏ + this.ÇŽÉ, this.Çªà¢ + this.ˆá, this.Ê + this.ÇŽÕ);
        MovingObjectPosition var3 = this.Ï­Ðƒà.HorizonCode_Horizon_È(var1, var2);
        var1 = new Vec3(this.ŒÏ, this.Çªà¢, this.Ê);
        var2 = new Vec3(this.ŒÏ + this.ÇŽÉ, this.Çªà¢ + this.ˆá, this.Ê + this.ÇŽÕ);
        if (var3 != null) {
            var2 = new Vec3(var3.Ý.HorizonCode_Horizon_È, var3.Ý.Â, var3.Ý.Ý);
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            Entity var4 = null;
            final List var5 = this.Ï­Ðƒà.Â(this, this.£É().HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ).Â(1.0, 1.0, 1.0));
            double var6 = 0.0;
            final EntityLivingBase var7 = this.µà();
            for (int var8 = 0; var8 < var5.size(); ++var8) {
                final Entity var9 = var5.get(var8);
                if (var9.Ô() && (var9 != var7 || this.áˆºÑ¢Õ >= 5)) {
                    final float var10 = 0.3f;
                    final AxisAlignedBB var11 = var9.£É().Â(var10, var10, var10);
                    final MovingObjectPosition var12 = var11.HorizonCode_Horizon_È(var1, var2);
                    if (var12 != null) {
                        final double var13 = var1.Ó(var12.Ý);
                        if (var13 < var6 || var6 == 0.0) {
                            var4 = var9;
                            var6 = var13;
                        }
                    }
                }
            }
            if (var4 != null) {
                var3 = new MovingObjectPosition(var4);
            }
        }
        if (var3 != null) {
            if (var3.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â && this.Ï­Ðƒà.Â(var3.HorizonCode_Horizon_È()).Ý() == Blocks.µÐƒáƒ) {
                this.£Õ();
            }
            else {
                this.HorizonCode_Horizon_È(var3);
            }
        }
        this.ŒÏ += this.ÇŽÉ;
        this.Çªà¢ += this.ˆá;
        this.Ê += this.ÇŽÕ;
        final float var14 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
        this.É = (float)(Math.atan2(this.ÇŽÉ, this.ÇŽÕ) * 180.0 / 3.141592653589793);
        this.áƒ = (float)(Math.atan2(this.ˆá, var14) * 180.0 / 3.141592653589793);
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
        float var15 = 0.99f;
        final float var16 = this.à();
        if (this.£ÂµÄ()) {
            for (int var17 = 0; var17 < 4; ++var17) {
                final float var18 = 0.25f;
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€, this.ŒÏ - this.ÇŽÉ * var18, this.Çªà¢ - this.ˆá * var18, this.Ê - this.ÇŽÕ * var18, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
            }
            var15 = 0.8f;
        }
        this.ÇŽÉ *= var15;
        this.ˆá *= var15;
        this.ÇŽÕ *= var15;
        this.ˆá -= var16;
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
    }
    
    protected float à() {
        return 0.03f;
    }
    
    protected abstract void HorizonCode_Horizon_È(final MovingObjectPosition p0);
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("xTile", (short)this.Ý);
        tagCompound.HorizonCode_Horizon_È("yTile", (short)this.Ø­áŒŠá);
        tagCompound.HorizonCode_Horizon_È("zTile", (short)this.Âµá€);
        final ResourceLocation_1975012498 var2 = (ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(this.Ó);
        tagCompound.HorizonCode_Horizon_È("inTile", (var2 == null) ? "" : var2.toString());
        tagCompound.HorizonCode_Horizon_È("shake", (byte)this.Â);
        tagCompound.HorizonCode_Horizon_È("inGround", (byte)(this.HorizonCode_Horizon_È ? 1 : 0));
        if ((this.Ø == null || this.Ø.length() == 0) && this.à instanceof EntityPlayer) {
            this.Ø = this.à.v_();
        }
        tagCompound.HorizonCode_Horizon_È("ownerName", (this.Ø == null) ? "" : this.Ø);
    }
    
    public void Â(final NBTTagCompound tagCompund) {
        this.Ý = tagCompund.Âµá€("xTile");
        this.Ø­áŒŠá = tagCompund.Âµá€("yTile");
        this.Âµá€ = tagCompund.Âµá€("zTile");
        if (tagCompund.Â("inTile", 8)) {
            this.Ó = Block.HorizonCode_Horizon_È(tagCompund.áˆºÑ¢Õ("inTile"));
        }
        else {
            this.Ó = Block.HorizonCode_Horizon_È(tagCompund.Ø­áŒŠá("inTile") & 0xFF);
        }
        this.Â = (tagCompund.Ø­áŒŠá("shake") & 0xFF);
        this.HorizonCode_Horizon_È = (tagCompund.Ø­áŒŠá("inGround") == 1);
        this.Ø = tagCompund.áˆºÑ¢Õ("ownerName");
        if (this.Ø != null && this.Ø.length() == 0) {
            this.Ø = null;
        }
    }
    
    public EntityLivingBase µà() {
        if (this.à == null && this.Ø != null && this.Ø.length() > 0) {
            this.à = this.Ï­Ðƒà.HorizonCode_Horizon_È(this.Ø);
        }
        return this.à;
    }
}
