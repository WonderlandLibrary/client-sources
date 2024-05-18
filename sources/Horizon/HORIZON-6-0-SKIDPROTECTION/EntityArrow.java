package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class EntityArrow extends Entity implements IProjectile
{
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private Block à;
    private int Ø;
    private boolean áŒŠÆ;
    public int HorizonCode_Horizon_È;
    public int Â;
    public Entity Ý;
    private int áˆºÑ¢Õ;
    private int ÂµÈ;
    private double á;
    private int ˆÏ­;
    private static final String £á = "CL_00001715";
    
    public EntityArrow(final World worldIn) {
        super(worldIn);
        this.Ø­áŒŠá = -1;
        this.Âµá€ = -1;
        this.Ó = -1;
        this.á = 2.0;
        this.¥Æ = 10.0;
        this.HorizonCode_Horizon_È(0.5f, 0.5f);
    }
    
    public EntityArrow(final World worldIn, final double p_i1754_2_, final double p_i1754_4_, final double p_i1754_6_) {
        super(worldIn);
        this.Ø­áŒŠá = -1;
        this.Âµá€ = -1;
        this.Ó = -1;
        this.á = 2.0;
        this.¥Æ = 10.0;
        this.HorizonCode_Horizon_È(0.5f, 0.5f);
        this.Ý(p_i1754_2_, p_i1754_4_, p_i1754_6_);
    }
    
    public EntityArrow(final World worldIn, final EntityLivingBase p_i1755_2_, final EntityLivingBase p_i1755_3_, final float p_i1755_4_, final float p_i1755_5_) {
        super(worldIn);
        this.Ø­áŒŠá = -1;
        this.Âµá€ = -1;
        this.Ó = -1;
        this.á = 2.0;
        this.¥Æ = 10.0;
        this.Ý = p_i1755_2_;
        if (p_i1755_2_ instanceof EntityPlayer) {
            this.HorizonCode_Horizon_È = 1;
        }
        this.Çªà¢ = p_i1755_2_.Çªà¢ + p_i1755_2_.Ðƒáƒ() - 0.10000000149011612;
        final double var6 = p_i1755_3_.ŒÏ - p_i1755_2_.ŒÏ;
        final double var7 = p_i1755_3_.£É().Â + p_i1755_3_.£ÂµÄ / 3.0f - this.Çªà¢;
        final double var8 = p_i1755_3_.Ê - p_i1755_2_.Ê;
        final double var9 = MathHelper.HorizonCode_Horizon_È(var6 * var6 + var8 * var8);
        if (var9 >= 1.0E-7) {
            final float var10 = (float)(Math.atan2(var8, var6) * 180.0 / 3.141592653589793) - 90.0f;
            final float var11 = (float)(-(Math.atan2(var7, var9) * 180.0 / 3.141592653589793));
            final double var12 = var6 / var9;
            final double var13 = var8 / var9;
            this.Â(p_i1755_2_.ŒÏ + var12, this.Çªà¢, p_i1755_2_.Ê + var13, var10, var11);
            final float var14 = (float)(var9 * 0.20000000298023224);
            this.a_(var6, var7 + var14, var8, p_i1755_4_, p_i1755_5_);
        }
    }
    
    public EntityArrow(final World worldIn, final EntityLivingBase p_i1756_2_, final float p_i1756_3_) {
        super(worldIn);
        this.Ø­áŒŠá = -1;
        this.Âµá€ = -1;
        this.Ó = -1;
        this.á = 2.0;
        this.¥Æ = 10.0;
        this.Ý = p_i1756_2_;
        if (p_i1756_2_ instanceof EntityPlayer) {
            this.HorizonCode_Horizon_È = 1;
        }
        this.HorizonCode_Horizon_È(0.5f, 0.5f);
        this.Â(p_i1756_2_.ŒÏ, p_i1756_2_.Çªà¢ + p_i1756_2_.Ðƒáƒ(), p_i1756_2_.Ê, p_i1756_2_.É, p_i1756_2_.áƒ);
        this.ŒÏ -= MathHelper.Â(this.É / 180.0f * 3.1415927f) * 0.16f;
        this.Çªà¢ -= 0.10000000149011612;
        this.Ê -= MathHelper.HorizonCode_Horizon_È(this.É / 180.0f * 3.1415927f) * 0.16f;
        this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
        this.ÇŽÉ = -MathHelper.HorizonCode_Horizon_È(this.É / 180.0f * 3.1415927f) * MathHelper.Â(this.áƒ / 180.0f * 3.1415927f);
        this.ÇŽÕ = MathHelper.Â(this.É / 180.0f * 3.1415927f) * MathHelper.Â(this.áƒ / 180.0f * 3.1415927f);
        this.ˆá = -MathHelper.HorizonCode_Horizon_È(this.áƒ / 180.0f * 3.1415927f);
        this.a_(this.ÇŽÉ, this.ˆá, this.ÇŽÕ, p_i1756_3_ * 1.5f, 1.0f);
    }
    
    @Override
    protected void ÂµÈ() {
        this.£Ó.HorizonCode_Horizon_È(16, (Object)(byte)0);
    }
    
    @Override
    public void a_(double p_70186_1_, double p_70186_3_, double p_70186_5_, final float p_70186_7_, final float p_70186_8_) {
        final float var9 = MathHelper.HorizonCode_Horizon_È(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= var9;
        p_70186_3_ /= var9;
        p_70186_5_ /= var9;
        p_70186_1_ += this.ˆáƒ.nextGaussian() * (this.ˆáƒ.nextBoolean() ? -1 : 1) * 0.007499999832361937 * p_70186_8_;
        p_70186_3_ += this.ˆáƒ.nextGaussian() * (this.ˆáƒ.nextBoolean() ? -1 : 1) * 0.007499999832361937 * p_70186_8_;
        p_70186_5_ += this.ˆáƒ.nextGaussian() * (this.ˆáƒ.nextBoolean() ? -1 : 1) * 0.007499999832361937 * p_70186_8_;
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
        this.áˆºÑ¢Õ = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_, final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
        this.Ý(p_180426_1_, p_180426_3_, p_180426_5_);
        this.Â(p_180426_7_, p_180426_8_);
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
            this.Õ = this.áƒ;
            this.á€ = this.É;
            this.Â(this.ŒÏ, this.Çªà¢, this.Ê, this.É, this.áƒ);
            this.áˆºÑ¢Õ = 0;
        }
    }
    
    @Override
    public void á() {
        super.á();
        if (this.Õ == 0.0f && this.á€ == 0.0f) {
            final float var1 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
            final float n = (float)(Math.atan2(this.ÇŽÉ, this.ÇŽÕ) * 180.0 / 3.141592653589793);
            this.É = n;
            this.á€ = n;
            final float n2 = (float)(Math.atan2(this.ˆá, var1) * 180.0 / 3.141592653589793);
            this.áƒ = n2;
            this.Õ = n2;
        }
        final BlockPos var2 = new BlockPos(this.Ø­áŒŠá, this.Âµá€, this.Ó);
        IBlockState var3 = this.Ï­Ðƒà.Â(var2);
        final Block var4 = var3.Ý();
        if (var4.Ó() != Material.HorizonCode_Horizon_È) {
            var4.Ý((IBlockAccess)this.Ï­Ðƒà, var2);
            final AxisAlignedBB var5 = var4.HorizonCode_Horizon_È(this.Ï­Ðƒà, var2, var3);
            if (var5 != null && var5.HorizonCode_Horizon_È(new Vec3(this.ŒÏ, this.Çªà¢, this.Ê))) {
                this.áŒŠÆ = true;
            }
        }
        if (this.Â > 0) {
            --this.Â;
        }
        if (this.áŒŠÆ) {
            final int var6 = var4.Ý(var3);
            if (var4 == this.à && var6 == this.Ø) {
                ++this.áˆºÑ¢Õ;
                if (this.áˆºÑ¢Õ >= 1200) {
                    this.á€();
                }
            }
            else {
                this.áŒŠÆ = false;
                this.ÇŽÉ *= this.ˆáƒ.nextFloat() * 0.2f;
                this.ˆá *= this.ˆáƒ.nextFloat() * 0.2f;
                this.ÇŽÕ *= this.ˆáƒ.nextFloat() * 0.2f;
                this.áˆºÑ¢Õ = 0;
                this.ÂµÈ = 0;
            }
        }
        else {
            ++this.ÂµÈ;
            Vec3 var7 = new Vec3(this.ŒÏ, this.Çªà¢, this.Ê);
            Vec3 var8 = new Vec3(this.ŒÏ + this.ÇŽÉ, this.Çªà¢ + this.ˆá, this.Ê + this.ÇŽÕ);
            MovingObjectPosition var9 = this.Ï­Ðƒà.HorizonCode_Horizon_È(var7, var8, false, true, false);
            var7 = new Vec3(this.ŒÏ, this.Çªà¢, this.Ê);
            var8 = new Vec3(this.ŒÏ + this.ÇŽÉ, this.Çªà¢ + this.ˆá, this.Ê + this.ÇŽÕ);
            if (var9 != null) {
                var8 = new Vec3(var9.Ý.HorizonCode_Horizon_È, var9.Ý.Â, var9.Ý.Ý);
            }
            Entity var10 = null;
            final List var11 = this.Ï­Ðƒà.Â(this, this.£É().HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ).Â(1.0, 1.0, 1.0));
            double var12 = 0.0;
            for (int var13 = 0; var13 < var11.size(); ++var13) {
                final Entity var14 = var11.get(var13);
                if (var14.Ô() && (var14 != this.Ý || this.ÂµÈ >= 5)) {
                    final float var15 = 0.3f;
                    final AxisAlignedBB var16 = var14.£É().Â(var15, var15, var15);
                    final MovingObjectPosition var17 = var16.HorizonCode_Horizon_È(var7, var8);
                    if (var17 != null) {
                        final double var18 = var7.Ó(var17.Ý);
                        if (var18 < var12 || var12 == 0.0) {
                            var10 = var14;
                            var12 = var18;
                        }
                    }
                }
            }
            if (var10 != null) {
                var9 = new MovingObjectPosition(var10);
            }
            if (var9 != null && var9.Ø­áŒŠá != null && var9.Ø­áŒŠá instanceof EntityPlayer) {
                final EntityPlayer var19 = (EntityPlayer)var9.Ø­áŒŠá;
                if (var19.áˆºáˆºáŠ.HorizonCode_Horizon_È || (this.Ý instanceof EntityPlayer && !((EntityPlayer)this.Ý).Ø­áŒŠá(var19))) {
                    var9 = null;
                }
            }
            if (var9 != null) {
                if (var9.Ø­áŒŠá != null) {
                    final float var20 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ˆá * this.ˆá + this.ÇŽÕ * this.ÇŽÕ);
                    int var21 = MathHelper.Ó(var20 * this.á);
                    if (this.Ø()) {
                        var21 += this.ˆáƒ.nextInt(var21 / 2 + 2);
                    }
                    DamageSource var22;
                    if (this.Ý == null) {
                        var22 = DamageSource.HorizonCode_Horizon_È(this, this);
                    }
                    else {
                        var22 = DamageSource.HorizonCode_Horizon_È(this, this.Ý);
                    }
                    if (this.ˆÏ() && !(var9.Ø­áŒŠá instanceof EntityEnderman)) {
                        var9.Ø­áŒŠá.Âµá€(5);
                    }
                    if (var9.Ø­áŒŠá.HorizonCode_Horizon_È(var22, var21)) {
                        if (var9.Ø­áŒŠá instanceof EntityLivingBase) {
                            final EntityLivingBase var23 = (EntityLivingBase)var9.Ø­áŒŠá;
                            if (!this.Ï­Ðƒà.ŠÄ) {
                                var23.µà(var23.µ() + 1);
                            }
                            if (this.ˆÏ­ > 0) {
                                final float var24 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
                                if (var24 > 0.0f) {
                                    var9.Ø­áŒŠá.à(this.ÇŽÉ * this.ˆÏ­ * 0.6000000238418579 / var24, 0.1, this.ÇŽÕ * this.ˆÏ­ * 0.6000000238418579 / var24);
                                }
                            }
                            if (this.Ý instanceof EntityLivingBase) {
                                EnchantmentHelper.HorizonCode_Horizon_È(var23, this.Ý);
                                EnchantmentHelper.Â((EntityLivingBase)this.Ý, var23);
                            }
                            if (this.Ý != null && var9.Ø­áŒŠá != this.Ý && var9.Ø­áŒŠá instanceof EntityPlayer && this.Ý instanceof EntityPlayerMP) {
                                ((EntityPlayerMP)this.Ý).HorizonCode_Horizon_È.HorizonCode_Horizon_È(new S2BPacketChangeGameState(6, 0.0f));
                            }
                        }
                        this.HorizonCode_Horizon_È("random.bowhit", 1.0f, 1.2f / (this.ˆáƒ.nextFloat() * 0.2f + 0.9f));
                        if (!(var9.Ø­áŒŠá instanceof EntityEnderman)) {
                            this.á€();
                        }
                    }
                    else {
                        this.ÇŽÉ *= -0.10000000149011612;
                        this.ˆá *= -0.10000000149011612;
                        this.ÇŽÕ *= -0.10000000149011612;
                        this.É += 180.0f;
                        this.á€ += 180.0f;
                        this.ÂµÈ = 0;
                    }
                }
                else {
                    final BlockPos var25 = var9.HorizonCode_Horizon_È();
                    this.Ø­áŒŠá = var25.HorizonCode_Horizon_È();
                    this.Âµá€ = var25.Â();
                    this.Ó = var25.Ý();
                    var3 = this.Ï­Ðƒà.Â(var25);
                    this.à = var3.Ý();
                    this.Ø = this.à.Ý(var3);
                    this.ÇŽÉ = (float)(var9.Ý.HorizonCode_Horizon_È - this.ŒÏ);
                    this.ˆá = (float)(var9.Ý.Â - this.Çªà¢);
                    this.ÇŽÕ = (float)(var9.Ý.Ý - this.Ê);
                    final float var26 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ˆá * this.ˆá + this.ÇŽÕ * this.ÇŽÕ);
                    this.ŒÏ -= this.ÇŽÉ / var26 * 0.05000000074505806;
                    this.Çªà¢ -= this.ˆá / var26 * 0.05000000074505806;
                    this.Ê -= this.ÇŽÕ / var26 * 0.05000000074505806;
                    this.HorizonCode_Horizon_È("random.bowhit", 1.0f, 1.2f / (this.ˆáƒ.nextFloat() * 0.2f + 0.9f));
                    this.áŒŠÆ = true;
                    this.Â = 7;
                    this.HorizonCode_Horizon_È(false);
                    if (this.à.Ó() != Material.HorizonCode_Horizon_È) {
                        this.à.HorizonCode_Horizon_È(this.Ï­Ðƒà, var25, var3, this);
                    }
                }
            }
            if (this.Ø()) {
                for (int var13 = 0; var13 < 4; ++var13) {
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.áˆºÑ¢Õ, this.ŒÏ + this.ÇŽÉ * var13 / 4.0, this.Çªà¢ + this.ˆá * var13 / 4.0, this.Ê + this.ÇŽÕ * var13 / 4.0, -this.ÇŽÉ, -this.ˆá + 0.2, -this.ÇŽÕ, new int[0]);
                }
            }
            this.ŒÏ += this.ÇŽÉ;
            this.Çªà¢ += this.ˆá;
            this.Ê += this.ÇŽÕ;
            final float var20 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
            this.É = (float)(Math.atan2(this.ÇŽÉ, this.ÇŽÕ) * 180.0 / 3.141592653589793);
            this.áƒ = (float)(Math.atan2(this.ˆá, var20) * 180.0 / 3.141592653589793);
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
            float var26 = 0.99f;
            final float var15 = 0.05f;
            if (this.£ÂµÄ()) {
                for (int var27 = 0; var27 < 4; ++var27) {
                    final float var24 = 0.25f;
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Âµá€, this.ŒÏ - this.ÇŽÉ * var24, this.Çªà¢ - this.ˆá * var24, this.Ê - this.ÇŽÕ * var24, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
                }
                var26 = 0.6f;
            }
            if (this.áŒŠ()) {
                this.¥à();
            }
            this.ÇŽÉ *= var26;
            this.ˆá *= var26;
            this.ÇŽÕ *= var26;
            this.ˆá -= var15;
            this.Ý(this.ŒÏ, this.Çªà¢, this.Ê);
            this.È();
        }
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        tagCompound.HorizonCode_Horizon_È("xTile", (short)this.Ø­áŒŠá);
        tagCompound.HorizonCode_Horizon_È("yTile", (short)this.Âµá€);
        tagCompound.HorizonCode_Horizon_È("zTile", (short)this.Ó);
        tagCompound.HorizonCode_Horizon_È("life", (short)this.áˆºÑ¢Õ);
        final ResourceLocation_1975012498 var2 = (ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(this.à);
        tagCompound.HorizonCode_Horizon_È("inTile", (var2 == null) ? "" : var2.toString());
        tagCompound.HorizonCode_Horizon_È("inData", (byte)this.Ø);
        tagCompound.HorizonCode_Horizon_È("shake", (byte)this.Â);
        tagCompound.HorizonCode_Horizon_È("inGround", (byte)(this.áŒŠÆ ? 1 : 0));
        tagCompound.HorizonCode_Horizon_È("pickup", (byte)this.HorizonCode_Horizon_È);
        tagCompound.HorizonCode_Horizon_È("damage", this.á);
    }
    
    public void Â(final NBTTagCompound tagCompund) {
        this.Ø­áŒŠá = tagCompund.Âµá€("xTile");
        this.Âµá€ = tagCompund.Âµá€("yTile");
        this.Ó = tagCompund.Âµá€("zTile");
        this.áˆºÑ¢Õ = tagCompund.Âµá€("life");
        if (tagCompund.Â("inTile", 8)) {
            this.à = Block.HorizonCode_Horizon_È(tagCompund.áˆºÑ¢Õ("inTile"));
        }
        else {
            this.à = Block.HorizonCode_Horizon_È(tagCompund.Ø­áŒŠá("inTile") & 0xFF);
        }
        this.Ø = (tagCompund.Ø­áŒŠá("inData") & 0xFF);
        this.Â = (tagCompund.Ø­áŒŠá("shake") & 0xFF);
        this.áŒŠÆ = (tagCompund.Ø­áŒŠá("inGround") == 1);
        if (tagCompund.Â("damage", 99)) {
            this.á = tagCompund.áŒŠÆ("damage");
        }
        if (tagCompund.Â("pickup", 99)) {
            this.HorizonCode_Horizon_È = tagCompund.Ø­áŒŠá("pickup");
        }
        else if (tagCompund.Â("player", 99)) {
            this.HorizonCode_Horizon_È = (tagCompund.£á("player") ? 1 : 0);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer entityIn) {
        if (!this.Ï­Ðƒà.ŠÄ && this.áŒŠÆ && this.Â <= 0) {
            boolean var2 = this.HorizonCode_Horizon_È == 1 || (this.HorizonCode_Horizon_È == 2 && entityIn.áˆºáˆºáŠ.Ø­áŒŠá);
            if (this.HorizonCode_Horizon_È == 1 && !entityIn.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(new ItemStack(Items.à, 1))) {
                var2 = false;
            }
            if (var2) {
                this.HorizonCode_Horizon_È("random.pop", 0.2f, ((this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                entityIn.Â(this, 1);
                this.á€();
            }
        }
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    public void Â(final double p_70239_1_) {
        this.á = p_70239_1_;
    }
    
    public double à() {
        return this.á;
    }
    
    public void HorizonCode_Horizon_È(final int p_70240_1_) {
        this.ˆÏ­ = p_70240_1_;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_70243_1_) {
        final byte var2 = this.£Ó.HorizonCode_Horizon_È(16);
        if (p_70243_1_) {
            this.£Ó.Â(16, (byte)(var2 | 0x1));
        }
        else {
            this.£Ó.Â(16, (byte)(var2 & 0xFFFFFFFE));
        }
    }
    
    public boolean Ø() {
        final byte var1 = this.£Ó.HorizonCode_Horizon_È(16);
        return (var1 & 0x1) != 0x0;
    }
}
