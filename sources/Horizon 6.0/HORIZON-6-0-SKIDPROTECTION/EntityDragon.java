package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public class EntityDragon extends EntityLiving implements IEntityMultiPart, IMob, IBossDisplayData
{
    public double HorizonCode_Horizon_È;
    public double Â;
    public double Ý;
    public double[][] Ø­áŒŠá;
    public int Âµá€;
    public EntityDragonPart[] Ø­Ñ¢Ï­Ø­áˆº;
    public EntityDragonPart ŒÂ;
    public EntityDragonPart Ï­Ï;
    public EntityDragonPart ŠØ;
    public EntityDragonPart ˆÐƒØ;
    public EntityDragonPart Çªà;
    public EntityDragonPart ¥Å;
    public EntityDragonPart Œáƒ;
    public float Œá;
    public float µÂ;
    public boolean Ñ¢ÇŽÏ;
    public boolean ÇªÂ;
    private Entity ÇŽÈ;
    public int ÂµáˆºÂ;
    public EntityEnderCrystal ¥Âµá€;
    private static final String ÇªáˆºÕ = "CL_00001659";
    
    public EntityDragon(final World worldIn) {
        super(worldIn);
        this.Ø­áŒŠá = new double[64][3];
        this.Âµá€ = -1;
        this.Ø­Ñ¢Ï­Ø­áˆº = new EntityDragonPart[] { this.ŒÂ = new EntityDragonPart(this, "head", 6.0f, 6.0f), this.Ï­Ï = new EntityDragonPart(this, "body", 8.0f, 8.0f), this.ŠØ = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.ˆÐƒØ = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.Çªà = new EntityDragonPart(this, "tail", 4.0f, 4.0f), this.¥Å = new EntityDragonPart(this, "wing", 4.0f, 4.0f), this.Œáƒ = new EntityDragonPart(this, "wing", 4.0f, 4.0f) };
        this.áˆºÑ¢Õ(this.ÇŽÊ());
        this.HorizonCode_Horizon_È(16.0f, 8.0f);
        this.ÇªÓ = true;
        this.£Â = true;
        this.Â = 100.0;
        this.ÇªÂµÕ = true;
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(200.0);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
    }
    
    public double[] Â(final int p_70974_1_, float p_70974_2_) {
        if (this.Ï­Ä() <= 0.0f) {
            p_70974_2_ = 0.0f;
        }
        p_70974_2_ = 1.0f - p_70974_2_;
        final int var3 = this.Âµá€ - p_70974_1_ * 1 & 0x3F;
        final int var4 = this.Âµá€ - p_70974_1_ * 1 - 1 & 0x3F;
        final double[] var5 = new double[3];
        double var6 = this.Ø­áŒŠá[var3][0];
        double var7 = MathHelper.à(this.Ø­áŒŠá[var4][0] - var6);
        var5[0] = var6 + var7 * p_70974_2_;
        var6 = this.Ø­áŒŠá[var3][1];
        var7 = this.Ø­áŒŠá[var4][1] - var6;
        var5[1] = var6 + var7 * p_70974_2_;
        var5[2] = this.Ø­áŒŠá[var3][2] + (this.Ø­áŒŠá[var4][2] - this.Ø­áŒŠá[var3][2]) * p_70974_2_;
        return var5;
    }
    
    @Override
    public void ˆÏ­() {
        if (this.Ï­Ðƒà.ŠÄ) {
            final float var1 = MathHelper.Â(this.µÂ * 3.1415927f * 2.0f);
            final float var2 = MathHelper.Â(this.Œá * 3.1415927f * 2.0f);
            if (var2 <= -0.3f && var1 >= -0.3f && !this.áŠ()) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, "mob.enderdragon.wings", 5.0f, 0.8f + this.ˆáƒ.nextFloat() * 0.3f, false);
            }
        }
        this.Œá = this.µÂ;
        if (this.Ï­Ä() <= 0.0f) {
            final float var1 = (this.ˆáƒ.nextFloat() - 0.5f) * 8.0f;
            final float var2 = (this.ˆáƒ.nextFloat() - 0.5f) * 4.0f;
            final float var3 = (this.ˆáƒ.nextFloat() - 0.5f) * 8.0f;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Â, this.ŒÏ + var1, this.Çªà¢ + 2.0 + var2, this.Ê + var3, 0.0, 0.0, 0.0, new int[0]);
        }
        else {
            this.Ø();
            float var1 = 0.2f / (MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ) * 10.0f + 1.0f);
            var1 *= (float)Math.pow(2.0, this.ˆá);
            if (this.ÇªÂ) {
                this.µÂ += var1 * 0.5f;
            }
            else {
                this.µÂ += var1;
            }
            this.É = MathHelper.à(this.É);
            if (this.Âµá€ < 0) {
                for (int var4 = 0; var4 < this.Ø­áŒŠá.length; ++var4) {
                    this.Ø­áŒŠá[var4][0] = this.É;
                    this.Ø­áŒŠá[var4][1] = this.Çªà¢;
                }
            }
            if (++this.Âµá€ == this.Ø­áŒŠá.length) {
                this.Âµá€ = 0;
            }
            this.Ø­áŒŠá[this.Âµá€][0] = this.É;
            this.Ø­áŒŠá[this.Âµá€][1] = this.Çªà¢;
            if (this.Ï­Ðƒà.ŠÄ) {
                if (this.ÇŽÄ > 0) {
                    final double var5 = this.ŒÏ + (this.ˆÈ - this.ŒÏ) / this.ÇŽÄ;
                    final double var6 = this.Çªà¢ + (this.ˆÅ - this.Çªà¢) / this.ÇŽÄ;
                    final double var7 = this.Ê + (this.ÇªÉ - this.Ê) / this.ÇŽÄ;
                    final double var8 = MathHelper.à(this.ŠÏ­áˆºá - this.É);
                    this.É += (float)(var8 / this.ÇŽÄ);
                    this.áƒ += (float)((this.ÇŽà - this.áƒ) / this.ÇŽÄ);
                    --this.ÇŽÄ;
                    this.Ý(var5, var6, var7);
                    this.Â(this.É, this.áƒ);
                }
            }
            else {
                final double var5 = this.HorizonCode_Horizon_È - this.ŒÏ;
                double var6 = this.Â - this.Çªà¢;
                final double var7 = this.Ý - this.Ê;
                final double var8 = var5 * var5 + var6 * var6 + var7 * var7;
                if (this.ÇŽÈ != null) {
                    this.HorizonCode_Horizon_È = this.ÇŽÈ.ŒÏ;
                    this.Ý = this.ÇŽÈ.Ê;
                    final double var9 = this.HorizonCode_Horizon_È - this.ŒÏ;
                    final double var10 = this.Ý - this.Ê;
                    final double var11 = Math.sqrt(var9 * var9 + var10 * var10);
                    double var12 = 0.4000000059604645 + var11 / 80.0 - 1.0;
                    if (var12 > 10.0) {
                        var12 = 10.0;
                    }
                    this.Â = this.ÇŽÈ.£É().Â + var12;
                }
                else {
                    this.HorizonCode_Horizon_È += this.ˆáƒ.nextGaussian() * 2.0;
                    this.Ý += this.ˆáƒ.nextGaussian() * 2.0;
                }
                if (this.Ñ¢ÇŽÏ || var8 < 100.0 || var8 > 22500.0 || this.¥à || this.Âµà) {
                    this.ˆà();
                }
                var6 /= MathHelper.HorizonCode_Horizon_È(var5 * var5 + var7 * var7);
                final float var13 = 0.6f;
                var6 = MathHelper.HorizonCode_Horizon_È(var6, -var13, var13);
                this.ˆá += var6 * 0.10000000149011612;
                this.É = MathHelper.à(this.É);
                final double var14 = 180.0 - Math.atan2(var5, var7) * 180.0 / 3.141592653589793;
                double var15 = MathHelper.à(var14 - this.É);
                if (var15 > 50.0) {
                    var15 = 50.0;
                }
                if (var15 < -50.0) {
                    var15 = -50.0;
                }
                final Vec3 var16 = new Vec3(this.HorizonCode_Horizon_È - this.ŒÏ, this.Â - this.Çªà¢, this.Ý - this.Ê).HorizonCode_Horizon_È();
                double var12 = -MathHelper.Â(this.É * 3.1415927f / 180.0f);
                final Vec3 var17 = new Vec3(MathHelper.HorizonCode_Horizon_È(this.É * 3.1415927f / 180.0f), this.ˆá, var12).HorizonCode_Horizon_È();
                float var18 = ((float)var17.Â(var16) + 0.5f) / 1.5f;
                if (var18 < 0.0f) {
                    var18 = 0.0f;
                }
                this.Çª *= 0.8f;
                final float var19 = MathHelper.HorizonCode_Horizon_È(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ) * 1.0f + 1.0f;
                double var20 = Math.sqrt(this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ) * 1.0 + 1.0;
                if (var20 > 40.0) {
                    var20 = 40.0;
                }
                this.Çª += (float)(var15 * (0.699999988079071 / var20 / var19));
                this.É += this.Çª * 0.1f;
                final float var21 = (float)(2.0 / (var20 + 1.0));
                final float var22 = 0.06f;
                this.Â(0.0f, -1.0f, var22 * (var18 * var21 + (1.0f - var21)));
                if (this.ÇªÂ) {
                    this.HorizonCode_Horizon_È(this.ÇŽÉ * 0.800000011920929, this.ˆá * 0.800000011920929, this.ÇŽÕ * 0.800000011920929);
                }
                else {
                    this.HorizonCode_Horizon_È(this.ÇŽÉ, this.ˆá, this.ÇŽÕ);
                }
                final Vec3 var23 = new Vec3(this.ÇŽÉ, this.ˆá, this.ÇŽÕ).HorizonCode_Horizon_È();
                float var24 = ((float)var23.Â(var17) + 1.0f) / 2.0f;
                var24 = 0.8f + 0.15f * var24;
                this.ÇŽÉ *= var24;
                this.ÇŽÕ *= var24;
                this.ˆá *= 0.9100000262260437;
            }
            this.¥É = this.É;
            final EntityDragonPart œâ = this.ŒÂ;
            final EntityDragonPart œâ2 = this.ŒÂ;
            final float n = 3.0f;
            œâ2.£ÂµÄ = n;
            œâ.áŒŠ = n;
            final EntityDragonPart šø = this.ŠØ;
            final EntityDragonPart šø2 = this.ŠØ;
            final float n2 = 2.0f;
            šø2.£ÂµÄ = n2;
            šø.áŒŠ = n2;
            final EntityDragonPart ˆÐƒØ = this.ˆÐƒØ;
            final EntityDragonPart ˆÐƒØ2 = this.ˆÐƒØ;
            final float n3 = 2.0f;
            ˆÐƒØ2.£ÂµÄ = n3;
            ˆÐƒØ.áŒŠ = n3;
            final EntityDragonPart çªà = this.Çªà;
            final EntityDragonPart çªà2 = this.Çªà;
            final float n4 = 2.0f;
            çªà2.£ÂµÄ = n4;
            çªà.áŒŠ = n4;
            this.Ï­Ï.£ÂµÄ = 3.0f;
            this.Ï­Ï.áŒŠ = 5.0f;
            this.¥Å.£ÂµÄ = 2.0f;
            this.¥Å.áŒŠ = 4.0f;
            this.Œáƒ.£ÂµÄ = 3.0f;
            this.Œáƒ.áŒŠ = 4.0f;
            final float var2 = (float)(this.Â(5, 1.0f)[1] - this.Â(10, 1.0f)[1]) * 10.0f / 180.0f * 3.1415927f;
            final float var3 = MathHelper.Â(var2);
            final float var25 = -MathHelper.HorizonCode_Horizon_È(var2);
            final float var26 = this.É * 3.1415927f / 180.0f;
            final float var27 = MathHelper.HorizonCode_Horizon_È(var26);
            final float var28 = MathHelper.Â(var26);
            this.Ï­Ï.á();
            this.Ï­Ï.Â(this.ŒÏ + var27 * 0.5f, this.Çªà¢, this.Ê - var28 * 0.5f, 0.0f, 0.0f);
            this.¥Å.á();
            this.¥Å.Â(this.ŒÏ + var28 * 4.5f, this.Çªà¢ + 2.0, this.Ê + var27 * 4.5f, 0.0f, 0.0f);
            this.Œáƒ.á();
            this.Œáƒ.Â(this.ŒÏ - var28 * 4.5f, this.Çªà¢ + 2.0, this.Ê - var27 * 4.5f, 0.0f, 0.0f);
            if (!this.Ï­Ðƒà.ŠÄ && this.µà == 0) {
                this.HorizonCode_Horizon_È(this.Ï­Ðƒà.Â(this, this.¥Å.£É().Â(4.0, 2.0, 4.0).Ý(0.0, -2.0, 0.0)));
                this.HorizonCode_Horizon_È(this.Ï­Ðƒà.Â(this, this.Œáƒ.£É().Â(4.0, 2.0, 4.0).Ý(0.0, -2.0, 0.0)));
                this.Â(this.Ï­Ðƒà.Â(this, this.ŒÂ.£É().Â(1.0, 1.0, 1.0)));
            }
            final double[] var29 = this.Â(5, 1.0f);
            final double[] var30 = this.Â(0, 1.0f);
            final float var13 = MathHelper.HorizonCode_Horizon_È(this.É * 3.1415927f / 180.0f - this.Çª * 0.01f);
            final float var31 = MathHelper.Â(this.É * 3.1415927f / 180.0f - this.Çª * 0.01f);
            this.ŒÂ.á();
            this.ŒÂ.Â(this.ŒÏ + var13 * 5.5f * var3, this.Çªà¢ + (var30[1] - var29[1]) * 1.0 + var25 * 5.5f, this.Ê - var31 * 5.5f * var3, 0.0f, 0.0f);
            for (int var32 = 0; var32 < 3; ++var32) {
                EntityDragonPart var33 = null;
                if (var32 == 0) {
                    var33 = this.ŠØ;
                }
                if (var32 == 1) {
                    var33 = this.ˆÐƒØ;
                }
                if (var32 == 2) {
                    var33 = this.Çªà;
                }
                final double[] var34 = this.Â(12 + var32 * 2, 1.0f);
                final float var35 = this.É * 3.1415927f / 180.0f + this.Â(var34[0] - var29[0]) * 3.1415927f / 180.0f * 1.0f;
                final float var36 = MathHelper.HorizonCode_Horizon_È(var35);
                final float var37 = MathHelper.Â(var35);
                final float var38 = 1.5f;
                final float var39 = (var32 + 1) * 2.0f;
                var33.á();
                var33.Â(this.ŒÏ - (var27 * var38 + var36 * var39) * var3, this.Çªà¢ + (var34[1] - var29[1]) * 1.0 - (var39 + var38) * var25 + 1.5, this.Ê + (var28 * var38 + var37 * var39) * var3, 0.0f, 0.0f);
            }
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.ÇªÂ = (this.Â(this.ŒÂ.£É()) | this.Â(this.Ï­Ï.£É()));
            }
        }
    }
    
    private void Ø() {
        if (this.¥Âµá€ != null) {
            if (this.¥Âµá€.ˆáŠ) {
                if (!this.Ï­Ðƒà.ŠÄ) {
                    this.HorizonCode_Horizon_È(this.ŒÂ, DamageSource.HorizonCode_Horizon_È((Explosion)null), 10.0f);
                }
                this.¥Âµá€ = null;
            }
            else if (this.Œ % 10 == 0 && this.Ï­Ä() < this.ÇŽÊ()) {
                this.áˆºÑ¢Õ(this.Ï­Ä() + 1.0f);
            }
        }
        if (this.ˆáƒ.nextInt(10) == 0) {
            final float var1 = 32.0f;
            final List var2 = this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityEnderCrystal.class, this.£É().Â(var1, var1, var1));
            EntityEnderCrystal var3 = null;
            double var4 = Double.MAX_VALUE;
            for (final EntityEnderCrystal var6 : var2) {
                final double var7 = var6.Âµá€(this);
                if (var7 < var4) {
                    var4 = var7;
                    var3 = var6;
                }
            }
            this.¥Âµá€ = var3;
        }
    }
    
    private void HorizonCode_Horizon_È(final List p_70970_1_) {
        final double var2 = (this.Ï­Ï.£É().HorizonCode_Horizon_È + this.Ï­Ï.£É().Ø­áŒŠá) / 2.0;
        final double var3 = (this.Ï­Ï.£É().Ý + this.Ï­Ï.£É().Ó) / 2.0;
        for (final Entity var5 : p_70970_1_) {
            if (var5 instanceof EntityLivingBase) {
                final double var6 = var5.ŒÏ - var2;
                final double var7 = var5.Ê - var3;
                final double var8 = var6 * var6 + var7 * var7;
                var5.à(var6 / var8 * 4.0, 0.20000000298023224, var7 / var8 * 4.0);
            }
        }
    }
    
    private void Â(final List p_70971_1_) {
        for (int var2 = 0; var2 < p_70971_1_.size(); ++var2) {
            final Entity var3 = p_70971_1_.get(var2);
            if (var3 instanceof EntityLivingBase) {
                var3.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this), 10.0f);
                this.HorizonCode_Horizon_È(this, var3);
            }
        }
    }
    
    private void ˆà() {
        this.Ñ¢ÇŽÏ = false;
        final ArrayList var1 = Lists.newArrayList((Iterable)this.Ï­Ðƒà.Ó);
        final Iterator var2 = var1.iterator();
        while (var2.hasNext()) {
            if (var2.next().Ø­áŒŠá()) {
                var2.remove();
            }
        }
        if (this.ˆáƒ.nextInt(2) == 0 && !var1.isEmpty()) {
            this.ÇŽÈ = var1.get(this.ˆáƒ.nextInt(var1.size()));
        }
        else {
            boolean var3;
            do {
                this.HorizonCode_Horizon_È = 0.0;
                this.Â = 70.0f + this.ˆáƒ.nextFloat() * 50.0f;
                this.Ý = 0.0;
                this.HorizonCode_Horizon_È += this.ˆáƒ.nextFloat() * 120.0f - 60.0f;
                this.Ý += this.ˆáƒ.nextFloat() * 120.0f - 60.0f;
                final double var4 = this.ŒÏ - this.HorizonCode_Horizon_È;
                final double var5 = this.Çªà¢ - this.Â;
                final double var6 = this.Ê - this.Ý;
                var3 = (var4 * var4 + var5 * var5 + var6 * var6 > 100.0);
            } while (!var3);
            this.ÇŽÈ = null;
        }
    }
    
    private float Â(final double p_70973_1_) {
        return (float)MathHelper.à(p_70973_1_);
    }
    
    private boolean Â(final AxisAlignedBB p_70972_1_) {
        final int var2 = MathHelper.Ý(p_70972_1_.HorizonCode_Horizon_È);
        final int var3 = MathHelper.Ý(p_70972_1_.Â);
        final int var4 = MathHelper.Ý(p_70972_1_.Ý);
        final int var5 = MathHelper.Ý(p_70972_1_.Ø­áŒŠá);
        final int var6 = MathHelper.Ý(p_70972_1_.Âµá€);
        final int var7 = MathHelper.Ý(p_70972_1_.Ó);
        boolean var8 = false;
        boolean var9 = false;
        for (int var10 = var2; var10 <= var5; ++var10) {
            for (int var11 = var3; var11 <= var6; ++var11) {
                for (int var12 = var4; var12 <= var7; ++var12) {
                    final Block var13 = this.Ï­Ðƒà.Â(new BlockPos(var10, var11, var12)).Ý();
                    if (var13.Ó() != Material.HorizonCode_Horizon_È) {
                        if (var13 != Blocks.¥ÇªÅ && var13 != Blocks.ÇŽá€ && var13 != Blocks.µÊ && var13 != Blocks.áŒŠÆ && var13 != Blocks.ŠÑ¢Ó && this.Ï­Ðƒà.Çªà¢().Â("mobGriefing")) {
                            var9 = (this.Ï­Ðƒà.Ø(new BlockPos(var10, var11, var12)) || var9);
                        }
                        else {
                            var8 = true;
                        }
                    }
                }
            }
        }
        if (var9) {
            final double var14 = p_70972_1_.HorizonCode_Horizon_È + (p_70972_1_.Ø­áŒŠá - p_70972_1_.HorizonCode_Horizon_È) * this.ˆáƒ.nextFloat();
            final double var15 = p_70972_1_.Â + (p_70972_1_.Âµá€ - p_70972_1_.Â) * this.ˆáƒ.nextFloat();
            final double var16 = p_70972_1_.Ý + (p_70972_1_.Ó - p_70972_1_.Ý) * this.ˆáƒ.nextFloat();
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Â, var14, var15, var16, 0.0, 0.0, 0.0, new int[0]);
        }
        return var8;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityDragonPart p_70965_1_, final DamageSource p_70965_2_, float p_70965_3_) {
        if (p_70965_1_ != this.ŒÂ) {
            p_70965_3_ = p_70965_3_ / 4.0f + 1.0f;
        }
        final float var4 = this.É * 3.1415927f / 180.0f;
        final float var5 = MathHelper.HorizonCode_Horizon_È(var4);
        final float var6 = MathHelper.Â(var4);
        this.HorizonCode_Horizon_È = this.ŒÏ + var5 * 5.0f + (this.ˆáƒ.nextFloat() - 0.5f) * 2.0f;
        this.Â = this.Çªà¢ + this.ˆáƒ.nextFloat() * 3.0f + 1.0;
        this.Ý = this.Ê - var6 * 5.0f + (this.ˆáƒ.nextFloat() - 0.5f) * 2.0f;
        this.ÇŽÈ = null;
        if (p_70965_2_.áˆºÑ¢Õ() instanceof EntityPlayer || p_70965_2_.Ý()) {
            this.Âµá€(p_70965_2_, p_70965_3_);
        }
        return true;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (source instanceof EntityDamageSource && ((EntityDamageSource)source).Šáƒ()) {
            this.Âµá€(source, amount);
        }
        return false;
    }
    
    protected boolean Âµá€(final DamageSource p_82195_1_, final float p_82195_2_) {
        return super.HorizonCode_Horizon_È(p_82195_1_, p_82195_2_);
    }
    
    @Override
    public void ÇŽÕ() {
        this.á€();
    }
    
    @Override
    protected void ŒÂ() {
        ++this.ÂµáˆºÂ;
        if (this.ÂµáˆºÂ >= 180 && this.ÂµáˆºÂ <= 200) {
            final float var1 = (this.ˆáƒ.nextFloat() - 0.5f) * 8.0f;
            final float var2 = (this.ˆáƒ.nextFloat() - 0.5f) * 4.0f;
            final float var3 = (this.ˆáƒ.nextFloat() - 0.5f) * 8.0f;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Ý, this.ŒÏ + var1, this.Çªà¢ + 2.0 + var2, this.Ê + var3, 0.0, 0.0, 0.0, new int[0]);
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            if (this.ÂµáˆºÂ > 150 && this.ÂµáˆºÂ % 5 == 0 && this.Ï­Ðƒà.Çªà¢().Â("doMobLoot")) {
                int var4 = 1000;
                while (var4 > 0) {
                    final int var5 = EntityXPOrb.HorizonCode_Horizon_È(var4);
                    var4 -= var5;
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(new EntityXPOrb(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢, this.Ê, var5));
                }
            }
            if (this.ÂµáˆºÂ == 1) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(1018, new BlockPos(this), 0);
            }
        }
        this.HorizonCode_Horizon_È(0.0, 0.10000000149011612, 0.0);
        final float n = this.É + 20.0f;
        this.É = n;
        this.¥É = n;
        if (this.ÂµáˆºÂ == 200 && !this.Ï­Ðƒà.ŠÄ) {
            int var4 = 2000;
            while (var4 > 0) {
                final int var5 = EntityXPOrb.HorizonCode_Horizon_È(var4);
                var4 -= var5;
                this.Ï­Ðƒà.HorizonCode_Horizon_È(new EntityXPOrb(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢, this.Ê, var5));
            }
            this.HorizonCode_Horizon_È(new BlockPos(this.ŒÏ, 64.0, this.Ê));
            this.á€();
        }
    }
    
    private void HorizonCode_Horizon_È(final BlockPos p_175499_1_) {
        final boolean var2 = true;
        final double var3 = 12.25;
        final double var4 = 6.25;
        for (int var5 = -1; var5 <= 32; ++var5) {
            for (int var6 = -4; var6 <= 4; ++var6) {
                for (int var7 = -4; var7 <= 4; ++var7) {
                    final double var8 = var6 * var6 + var7 * var7;
                    if (var8 <= 12.25) {
                        final BlockPos var9 = p_175499_1_.Â(var6, var5, var7);
                        if (var5 < 0) {
                            if (var8 <= 6.25) {
                                this.Ï­Ðƒà.Â(var9, Blocks.áŒŠÆ.¥à());
                            }
                        }
                        else if (var5 > 0) {
                            this.Ï­Ðƒà.Â(var9, Blocks.Â.¥à());
                        }
                        else if (var8 > 6.25) {
                            this.Ï­Ðƒà.Â(var9, Blocks.áŒŠÆ.¥à());
                        }
                        else {
                            this.Ï­Ðƒà.Â(var9, Blocks.Ï­Ä.¥à());
                        }
                    }
                }
            }
        }
        this.Ï­Ðƒà.Â(p_175499_1_, Blocks.áŒŠÆ.¥à());
        this.Ï­Ðƒà.Â(p_175499_1_.Ø­áŒŠá(), Blocks.áŒŠÆ.¥à());
        final BlockPos var10 = p_175499_1_.Â(2);
        this.Ï­Ðƒà.Â(var10, Blocks.áŒŠÆ.¥à());
        this.Ï­Ðƒà.Â(var10.Ø(), Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, EnumFacing.Ó));
        this.Ï­Ðƒà.Â(var10.áŒŠÆ(), Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, EnumFacing.Âµá€));
        this.Ï­Ðƒà.Â(var10.Ó(), Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, EnumFacing.Ø­áŒŠá));
        this.Ï­Ðƒà.Â(var10.à(), Blocks.Ï.¥à().HorizonCode_Horizon_È(BlockTorch.Õ, EnumFacing.Ý));
        this.Ï­Ðƒà.Â(p_175499_1_.Â(3), Blocks.áŒŠÆ.¥à());
        this.Ï­Ðƒà.Â(p_175499_1_.Â(4), Blocks.áˆºáˆºáŠ.¥à());
    }
    
    @Override
    protected void áŒŠá() {
    }
    
    @Override
    public Entity[] ÇªÔ() {
        return this.Ø­Ñ¢Ï­Ø­áˆº;
    }
    
    @Override
    public boolean Ô() {
        return false;
    }
    
    @Override
    public World HorizonCode_Horizon_È() {
        return this.Ï­Ðƒà;
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.enderdragon.growl";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.enderdragon.hit";
    }
    
    @Override
    protected float ˆÂ() {
        return 5.0f;
    }
}
