package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class EntityBoat extends Entity
{
    private boolean HorizonCode_Horizon_È;
    private double Â;
    private int Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private double à;
    private double Ø;
    private double áŒŠÆ;
    private double áˆºÑ¢Õ;
    private double ÂµÈ;
    private static final String á = "CL_00001667";
    
    public EntityBoat(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È = true;
        this.Â = 0.07;
        this.Ø­à = true;
        this.HorizonCode_Horizon_È(1.5f, 0.6f);
    }
    
    @Override
    protected boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    protected void ÂµÈ() {
        this.£Ó.HorizonCode_Horizon_È(17, new Integer(0));
        this.£Ó.HorizonCode_Horizon_È(18, new Integer(1));
        this.£Ó.HorizonCode_Horizon_È(19, new Float(0.0f));
    }
    
    @Override
    public AxisAlignedBB à(final Entity entityIn) {
        return entityIn.£É();
    }
    
    @Override
    public AxisAlignedBB t_() {
        return this.£É();
    }
    
    @Override
    public boolean £à() {
        return true;
    }
    
    public EntityBoat(final World worldIn, final double p_i1705_2_, final double p_i1705_4_, final double p_i1705_6_) {
        this(worldIn);
        this.Ý(p_i1705_2_, p_i1705_4_, p_i1705_6_);
        this.ÇŽÉ = 0.0;
        this.ˆá = 0.0;
        this.ÇŽÕ = 0.0;
        this.áŒŠà = p_i1705_2_;
        this.ŠÄ = p_i1705_4_;
        this.Ñ¢á = p_i1705_6_;
    }
    
    @Override
    public double £Â() {
        return this.£ÂµÄ * 0.0 - 0.30000001192092896;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (this.Ï­Ðƒà.ŠÄ || this.ˆáŠ) {
            return true;
        }
        if (this.µÕ != null && this.µÕ == source.áˆºÑ¢Õ() && source instanceof EntityDamageSourceIndirect) {
            return false;
        }
        this.Â(-this.áŒŠÆ());
        this.HorizonCode_Horizon_È(10);
        this.Ý(this.à() + amount * 10.0f);
        this.Ï();
        final boolean var3 = source.áˆºÑ¢Õ() instanceof EntityPlayer && ((EntityPlayer)source.áˆºÑ¢Õ()).áˆºáˆºáŠ.Ø­áŒŠá;
        if (var3 || this.à() > 40.0f) {
            if (this.µÕ != null) {
                this.µÕ.HorizonCode_Horizon_È(this);
            }
            if (!var3) {
                this.HorizonCode_Horizon_È(Items.ÇªØ­, 1, 0.0f);
            }
            this.á€();
        }
        return true;
    }
    
    @Override
    public void Œà() {
        this.Â(-this.áŒŠÆ());
        this.HorizonCode_Horizon_È(10);
        this.Ý(this.à() * 11.0f);
    }
    
    @Override
    public boolean Ô() {
        return !this.ˆáŠ;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final double p_180426_1_, final double p_180426_3_, final double p_180426_5_, final float p_180426_7_, final float p_180426_8_, final int p_180426_9_, final boolean p_180426_10_) {
        if (p_180426_10_ && this.µÕ != null) {
            this.ŒÏ = p_180426_1_;
            this.áŒŠà = p_180426_1_;
            this.Çªà¢ = p_180426_3_;
            this.ŠÄ = p_180426_3_;
            this.Ê = p_180426_5_;
            this.Ñ¢á = p_180426_5_;
            this.É = p_180426_7_;
            this.áƒ = p_180426_8_;
            this.Ý = 0;
            this.Ý(p_180426_1_, p_180426_3_, p_180426_5_);
            final double n = 0.0;
            this.áŒŠÆ = n;
            this.ÇŽÉ = n;
            final double n2 = 0.0;
            this.áˆºÑ¢Õ = n2;
            this.ˆá = n2;
            final double n3 = 0.0;
            this.ÂµÈ = n3;
            this.ÇŽÕ = n3;
        }
        else {
            if (this.HorizonCode_Horizon_È) {
                this.Ý = p_180426_9_ + 5;
            }
            else {
                final double var11 = p_180426_1_ - this.ŒÏ;
                final double var12 = p_180426_3_ - this.Çªà¢;
                final double var13 = p_180426_5_ - this.Ê;
                final double var14 = var11 * var11 + var12 * var12 + var13 * var13;
                if (var14 <= 1.0) {
                    return;
                }
                this.Ý = 3;
            }
            this.Ø­áŒŠá = p_180426_1_;
            this.Âµá€ = p_180426_3_;
            this.Ó = p_180426_5_;
            this.à = p_180426_7_;
            this.Ø = p_180426_8_;
            this.ÇŽÉ = this.áŒŠÆ;
            this.ˆá = this.áˆºÑ¢Õ;
            this.ÇŽÕ = this.ÂµÈ;
        }
    }
    
    @Override
    public void áŒŠÆ(final double x, final double y, final double z) {
        this.ÇŽÉ = x;
        this.áŒŠÆ = x;
        this.ˆá = y;
        this.áˆºÑ¢Õ = y;
        this.ÇŽÕ = z;
        this.ÂµÈ = z;
    }
    
    @Override
    public void á() {
        super.á();
        if (this.Ø() > 0) {
            this.HorizonCode_Horizon_È(this.Ø() - 1);
        }
        if (this.à() > 0.0f) {
            this.Ý(this.à() - 1.0f);
        }
        this.áŒŠà = this.ŒÏ;
        this.ŠÄ = this.Çªà¢;
        this.Ñ¢á = this.Ê;
        final byte var1 = 5;
        double var2 = 0.0;
        for (int var3 = 0; var3 < var1; ++var3) {
            final double var4 = this.£É().Â + (this.£É().Âµá€ - this.£É().Â) * (var3 + 0) / var1 - 0.125;
            final double var5 = this.£É().Â + (this.£É().Âµá€ - this.£É().Â) * (var3 + 1) / var1 - 0.125;
            final AxisAlignedBB var6 = new AxisAlignedBB(this.£É().HorizonCode_Horizon_È, var4, this.£É().Ý, this.£É().Ø­áŒŠá, var5, this.£É().Ó);
            if (this.Ï­Ðƒà.Â(var6, Material.Ø)) {
                var2 += 1.0 / var1;
            }
        }
        final double var7 = Math.sqrt(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
        if (var7 > 0.2975) {
            final double var8 = Math.cos(this.É * 3.141592653589793 / 180.0);
            final double var9 = Math.sin(this.É * 3.141592653589793 / 180.0);
            for (int var10 = 0; var10 < 1.0 + var7 * 60.0; ++var10) {
                final double var11 = this.ˆáƒ.nextFloat() * 2.0f - 1.0f;
                final double var12 = (this.ˆáƒ.nextInt(2) * 2 - 1) * 0.7;
                if (this.ˆáƒ.nextBoolean()) {
                    final double var13 = this.ŒÏ - var8 * var11 * 0.8 + var9 * var12;
                    final double var14 = this.Ê - var9 * var11 * 0.8 - var8 * var12;
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Ó, var13, this.Çªà¢ - 0.125, var14, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
                }
                else {
                    final double var13 = this.ŒÏ + var8 + var9 * var11 * 0.7;
                    final double var14 = this.Ê + var9 - var8 * var11 * 0.7;
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Ó, var13, this.Çªà¢ - 0.125, var14, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
                }
            }
        }
        if (this.Ï­Ðƒà.ŠÄ && this.HorizonCode_Horizon_È) {
            if (this.Ý > 0) {
                final double var8 = this.ŒÏ + (this.Ø­áŒŠá - this.ŒÏ) / this.Ý;
                final double var9 = this.Çªà¢ + (this.Âµá€ - this.Çªà¢) / this.Ý;
                final double var15 = this.Ê + (this.Ó - this.Ê) / this.Ý;
                final double var16 = MathHelper.à(this.à - this.É);
                this.É += (float)(var16 / this.Ý);
                this.áƒ += (float)((this.Ø - this.áƒ) / this.Ý);
                --this.Ý;
                this.Ý(var8, var9, var15);
                this.Â(this.É, this.áƒ);
            }
            else {
                final double var8 = this.ŒÏ + this.ÇŽÉ;
                final double var9 = this.Çªà¢ + this.ˆá;
                final double var15 = this.Ê + this.ÇŽÕ;
                this.Ý(var8, var9, var15);
                if (this.ŠÂµà) {
                    this.ÇŽÉ *= 0.5;
                    this.ˆá *= 0.5;
                    this.ÇŽÕ *= 0.5;
                }
                this.ÇŽÉ *= 0.9900000095367432;
                this.ˆá *= 0.949999988079071;
                this.ÇŽÕ *= 0.9900000095367432;
            }
        }
        else {
            if (var2 < 1.0) {
                final double var8 = var2 * 2.0 - 1.0;
                this.ˆá += 0.03999999910593033 * var8;
            }
            else {
                if (this.ˆá < 0.0) {
                    this.ˆá /= 2.0;
                }
                this.ˆá += 0.007000000216066837;
            }
            if (this.µÕ instanceof EntityLivingBase) {
                final EntityLivingBase var17 = (EntityLivingBase)this.µÕ;
                final float var18 = this.µÕ.É + -var17.£áƒ * 90.0f;
                this.ÇŽÉ += -Math.sin(var18 * 3.1415927f / 180.0f) * this.Â * var17.Ï­áˆºÓ * 0.05000000074505806;
                this.ÇŽÕ += Math.cos(var18 * 3.1415927f / 180.0f) * this.Â * var17.Ï­áˆºÓ * 0.05000000074505806;
            }
            double var8 = Math.sqrt(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ);
            if (var8 > 0.35) {
                final double var9 = 0.35 / var8;
                this.ÇŽÉ *= var9;
                this.ÇŽÕ *= var9;
                var8 = 0.35;
            }
            if (var8 > var7 && this.Â < 0.35) {
                this.Â += (0.35 - this.Â) / 35.0;
                if (this.Â > 0.35) {
                    this.Â = 0.35;
                }
            }
            else {
                this.Â -= (this.Â - 0.07) / 35.0;
                if (this.Â < 0.07) {
                    this.Â = 0.07;
                }
            }
            for (int var19 = 0; var19 < 4; ++var19) {
                final int var20 = MathHelper.Ý(this.ŒÏ + (var19 % 2 - 0.5) * 0.8);
                final int var10 = MathHelper.Ý(this.Ê + (var19 / 2 - 0.5) * 0.8);
                for (int var21 = 0; var21 < 2; ++var21) {
                    final int var22 = MathHelper.Ý(this.Çªà¢) + var21;
                    final BlockPos var23 = new BlockPos(var20, var22, var10);
                    final Block var24 = this.Ï­Ðƒà.Â(var23).Ý();
                    if (var24 == Blocks.áŒŠá€) {
                        this.Ï­Ðƒà.Ø(var23);
                        this.¥à = false;
                    }
                    else if (var24 == Blocks.Œá) {
                        this.Ï­Ðƒà.Â(var23, true);
                        this.¥à = false;
                    }
                }
            }
            if (this.ŠÂµà) {
                this.ÇŽÉ *= 0.5;
                this.ˆá *= 0.5;
                this.ÇŽÕ *= 0.5;
            }
            this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
            if (this.¥à && var7 > 0.2) {
                if (!this.Ï­Ðƒà.ŠÄ && !this.ˆáŠ) {
                    this.á€();
                    for (int var19 = 0; var19 < 3; ++var19) {
                        this.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.à), 1, 0.0f);
                    }
                    for (int var19 = 0; var19 < 2; ++var19) {
                        this.HorizonCode_Horizon_È(Items.áŒŠà, 1, 0.0f);
                    }
                }
            }
            else {
                this.ÇŽÉ *= 0.9900000095367432;
                this.ˆá *= 0.949999988079071;
                this.ÇŽÕ *= 0.9900000095367432;
            }
            this.áƒ = 0.0f;
            double var9 = this.É;
            final double var15 = this.áŒŠà - this.ŒÏ;
            final double var16 = this.Ñ¢á - this.Ê;
            if (var15 * var15 + var16 * var16 > 0.001) {
                var9 = (float)(Math.atan2(var16, var15) * 180.0 / 3.141592653589793);
            }
            double var25 = MathHelper.à(var9 - this.É);
            if (var25 > 20.0) {
                var25 = 20.0;
            }
            if (var25 < -20.0) {
                var25 = -20.0;
            }
            this.Â(this.É += (float)var25, this.áƒ);
            if (!this.Ï­Ðƒà.ŠÄ) {
                final List var26 = this.Ï­Ðƒà.Â(this, this.£É().Â(0.20000000298023224, 0.0, 0.20000000298023224));
                if (var26 != null && !var26.isEmpty()) {
                    for (int var27 = 0; var27 < var26.size(); ++var27) {
                        final Entity var28 = var26.get(var27);
                        if (var28 != this.µÕ && var28.£à() && var28 instanceof EntityBoat) {
                            var28.Ó(this);
                        }
                    }
                }
                if (this.µÕ != null && this.µÕ.ˆáŠ) {
                    this.µÕ = null;
                }
            }
        }
    }
    
    @Override
    public void ˆÉ() {
        if (this.µÕ != null) {
            final double var1 = Math.cos(this.É * 3.141592653589793 / 180.0) * 0.4;
            final double var2 = Math.sin(this.É * 3.141592653589793 / 180.0) * 0.4;
            this.µÕ.Ý(this.ŒÏ + var1, this.Çªà¢ + this.£Â() + this.µÕ.Ï­Ï­Ï(), this.Ê + var2);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
    }
    
    @Override
    public boolean b_(final EntityPlayer playerIn) {
        if (this.µÕ != null && this.µÕ instanceof EntityPlayer && this.µÕ != playerIn) {
            return true;
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            playerIn.HorizonCode_Horizon_È(this);
        }
        return true;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final double p_180433_1_, final boolean p_180433_3_, final Block p_180433_4_, final BlockPos p_180433_5_) {
        if (p_180433_3_) {
            if (this.Ï­à > 3.0f) {
                this.Ø­áŒŠá(this.Ï­à, 1.0f);
                if (!this.Ï­Ðƒà.ŠÄ && !this.ˆáŠ) {
                    this.á€();
                    for (int var6 = 0; var6 < 3; ++var6) {
                        this.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(Blocks.à), 1, 0.0f);
                    }
                    for (int var6 = 0; var6 < 2; ++var6) {
                        this.HorizonCode_Horizon_È(Items.áŒŠà, 1, 0.0f);
                    }
                }
                this.Ï­à = 0.0f;
            }
        }
        else if (this.Ï­Ðƒà.Â(new BlockPos(this).Âµá€()).Ý().Ó() != Material.Ø && p_180433_1_ < 0.0) {
            this.Ï­à -= (float)p_180433_1_;
        }
    }
    
    public void Ý(final float p_70266_1_) {
        this.£Ó.Â(19, p_70266_1_);
    }
    
    public float à() {
        return this.£Ó.Ø­áŒŠá(19);
    }
    
    public void HorizonCode_Horizon_È(final int p_70265_1_) {
        this.£Ó.Â(17, p_70265_1_);
    }
    
    public int Ø() {
        return this.£Ó.Ý(17);
    }
    
    public void Â(final int p_70269_1_) {
        this.£Ó.Â(18, p_70269_1_);
    }
    
    public int áŒŠÆ() {
        return this.£Ó.Ý(18);
    }
    
    public void HorizonCode_Horizon_È(final boolean p_70270_1_) {
        this.HorizonCode_Horizon_È = p_70270_1_;
    }
}
