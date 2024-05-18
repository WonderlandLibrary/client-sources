package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;
import com.google.common.base.Predicates;
import com.google.common.base.Predicate;

public class EntityWither extends EntityMob implements IRangedAttackMob, IBossDisplayData
{
    private float[] Â;
    private float[] Ý;
    private float[] Ø­Ñ¢Ï­Ø­áˆº;
    private float[] ŒÂ;
    private int[] Ï­Ï;
    private int[] ŠØ;
    private int ˆÐƒØ;
    private static final Predicate Çªà;
    private static final String ¥Å = "CL_00001661";
    
    static {
        Çªà = (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00001662";
            
            public boolean HorizonCode_Horizon_È(final Entity p_180027_1_) {
                return p_180027_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_180027_1_).¥áŒŠà() != EnumCreatureAttribute.Â;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        };
    }
    
    public EntityWither(final World worldIn) {
        super(worldIn);
        this.Â = new float[2];
        this.Ý = new float[2];
        this.Ø­Ñ¢Ï­Ø­áˆº = new float[2];
        this.ŒÂ = new float[2];
        this.Ï­Ï = new int[2];
        this.ŠØ = new int[2];
        this.áˆºÑ¢Õ(this.ÇŽÊ());
        this.HorizonCode_Horizon_È(0.9f, 3.5f);
        this.£Â = true;
        ((PathNavigateGround)this.Š()).Ø­áŒŠá(true);
        this.ÂµÈ.HorizonCode_Horizon_È(0, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIArrowAttack(this, 1.0, 40, 20.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAILookIdle(this));
        this.á.HorizonCode_Horizon_È(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, EntityWither.Çªà));
        this.à = 50;
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(17, new Integer(0));
        this.£Ó.HorizonCode_Horizon_È(18, new Integer(0));
        this.£Ó.HorizonCode_Horizon_È(19, new Integer(0));
        this.£Ó.HorizonCode_Horizon_È(20, new Integer(0));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Invul", this.ÇŽ());
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.ˆà(tagCompund.Ó("Invul"));
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.wither.idle";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.wither.hurt";
    }
    
    @Override
    protected String µÊ() {
        return "mob.wither.death";
    }
    
    @Override
    public void ˆÏ­() {
        this.ˆá *= 0.6000000238418579;
        if (!this.Ï­Ðƒà.ŠÄ && this.¥Æ(0) > 0) {
            final Entity var1 = this.Ï­Ðƒà.HorizonCode_Horizon_È(this.¥Æ(0));
            if (var1 != null) {
                if (this.Çªà¢ < var1.Çªà¢ || (!this.ÇŽÅ() && this.Çªà¢ < var1.Çªà¢ + 5.0)) {
                    if (this.ˆá < 0.0) {
                        this.ˆá = 0.0;
                    }
                    this.ˆá += (0.5 - this.ˆá) * 0.6000000238418579;
                }
                final double var2 = var1.ŒÏ - this.ŒÏ;
                final double var3 = var1.Ê - this.Ê;
                final double var4 = var2 * var2 + var3 * var3;
                if (var4 > 9.0) {
                    final double var5 = MathHelper.HorizonCode_Horizon_È(var4);
                    this.ÇŽÉ += (var2 / var5 * 0.5 - this.ÇŽÉ) * 0.6000000238418579;
                    this.ÇŽÕ += (var3 / var5 * 0.5 - this.ÇŽÕ) * 0.6000000238418579;
                }
            }
        }
        if (this.ÇŽÉ * this.ÇŽÉ + this.ÇŽÕ * this.ÇŽÕ > 0.05000000074505806) {
            this.É = (float)Math.atan2(this.ÇŽÕ, this.ÇŽÉ) * 57.295776f - 90.0f;
        }
        super.ˆÏ­();
        for (int var6 = 0; var6 < 2; ++var6) {
            this.ŒÂ[var6] = this.Ý[var6];
            this.Ø­Ñ¢Ï­Ø­áˆº[var6] = this.Â[var6];
        }
        for (int var6 = 0; var6 < 2; ++var6) {
            final int var7 = this.¥Æ(var6 + 1);
            Entity var8 = null;
            if (var7 > 0) {
                var8 = this.Ï­Ðƒà.HorizonCode_Horizon_È(var7);
            }
            if (var8 != null) {
                final double var3 = this.Ø­à(var6 + 1);
                final double var4 = this.µÕ(var6 + 1);
                final double var5 = this.Æ(var6 + 1);
                final double var9 = var8.ŒÏ - var3;
                final double var10 = var8.Çªà¢ + var8.Ðƒáƒ() - var4;
                final double var11 = var8.Ê - var5;
                final double var12 = MathHelper.HorizonCode_Horizon_È(var9 * var9 + var11 * var11);
                final float var13 = (float)(Math.atan2(var11, var9) * 180.0 / 3.141592653589793) - 90.0f;
                final float var14 = (float)(-(Math.atan2(var10, var12) * 180.0 / 3.141592653589793));
                this.Â[var6] = this.HorizonCode_Horizon_È(this.Â[var6], var14, 40.0f);
                this.Ý[var6] = this.HorizonCode_Horizon_È(this.Ý[var6], var13, 10.0f);
            }
            else {
                this.Ý[var6] = this.HorizonCode_Horizon_È(this.Ý[var6], this.¥É, 10.0f);
            }
        }
        final boolean var15 = this.ÇŽÅ();
        for (int var7 = 0; var7 < 3; ++var7) {
            final double var16 = this.Ø­à(var7);
            final double var17 = this.µÕ(var7);
            final double var18 = this.Æ(var7);
            this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.á, var16 + this.ˆáƒ.nextGaussian() * 0.30000001192092896, var17 + this.ˆáƒ.nextGaussian() * 0.30000001192092896, var18 + this.ˆáƒ.nextGaussian() * 0.30000001192092896, 0.0, 0.0, 0.0, new int[0]);
            if (var15 && this.Ï­Ðƒà.Å.nextInt(4) == 0) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.£à, var16 + this.ˆáƒ.nextGaussian() * 0.30000001192092896, var17 + this.ˆáƒ.nextGaussian() * 0.30000001192092896, var18 + this.ˆáƒ.nextGaussian() * 0.30000001192092896, 0.699999988079071, 0.699999988079071, 0.5, new int[0]);
            }
        }
        if (this.ÇŽ() > 0) {
            for (int var7 = 0; var7 < 3; ++var7) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.£à, this.ŒÏ + this.ˆáƒ.nextGaussian() * 1.0, this.Çªà¢ + this.ˆáƒ.nextFloat() * 3.3f, this.Ê + this.ˆáƒ.nextGaussian() * 1.0, 0.699999988079071, 0.699999988079071, 0.8999999761581421, new int[0]);
            }
        }
    }
    
    @Override
    protected void ˆØ() {
        if (this.ÇŽ() > 0) {
            final int var1 = this.ÇŽ() - 1;
            if (var1 <= 0) {
                this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.ŒÏ, this.Çªà¢ + this.Ðƒáƒ(), this.Ê, 7.0f, false, this.Ï­Ðƒà.Çªà¢().Â("mobGriefing"));
                this.Ï­Ðƒà.HorizonCode_Horizon_È(1013, new BlockPos(this), 0);
            }
            this.ˆà(var1);
            if (this.Œ % 10 == 0) {
                this.a_(10.0f);
            }
        }
        else {
            super.ˆØ();
            for (int var1 = 1; var1 < 3; ++var1) {
                if (this.Œ >= this.Ï­Ï[var1 - 1]) {
                    this.Ï­Ï[var1 - 1] = this.Œ + 10 + this.ˆáƒ.nextInt(10);
                    if (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ý || this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá) {
                        final int var2 = var1 - 1;
                        final int var3 = this.ŠØ[var1 - 1];
                        this.ŠØ[var2] = this.ŠØ[var1 - 1] + 1;
                        if (var3 > 15) {
                            final float var4 = 10.0f;
                            final float var5 = 5.0f;
                            final double var6 = MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, this.ŒÏ - var4, this.ŒÏ + var4);
                            final double var7 = MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, this.Çªà¢ - var5, this.Çªà¢ + var5);
                            final double var8 = MathHelper.HorizonCode_Horizon_È(this.ˆáƒ, this.Ê - var4, this.Ê + var4);
                            this.HorizonCode_Horizon_È(var1 + 1, var6, var7, var8, true);
                            this.ŠØ[var1 - 1] = 0;
                        }
                    }
                    final int var9 = this.¥Æ(var1);
                    if (var9 > 0) {
                        final Entity var10 = this.Ï­Ðƒà.HorizonCode_Horizon_È(var9);
                        if (var10 != null && var10.Œ() && this.Âµá€(var10) <= 900.0 && this.µà(var10)) {
                            this.HorizonCode_Horizon_È(var1 + 1, (EntityLivingBase)var10);
                            this.Ï­Ï[var1 - 1] = this.Œ + 40 + this.ˆáƒ.nextInt(20);
                            this.ŠØ[var1 - 1] = 0;
                        }
                        else {
                            this.Â(var1, 0);
                        }
                    }
                    else {
                        final List var11 = this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityLivingBase.class, this.£É().Â(20.0, 8.0, 20.0), Predicates.and(EntityWither.Çªà, IEntitySelector.Ø­áŒŠá));
                        int var12 = 0;
                        while (var12 < 10 && !var11.isEmpty()) {
                            final EntityLivingBase var13 = var11.get(this.ˆáƒ.nextInt(var11.size()));
                            if (var13 != this && var13.Œ() && this.µà(var13)) {
                                if (!(var13 instanceof EntityPlayer)) {
                                    this.Â(var1, var13.ˆá());
                                    break;
                                }
                                if (!((EntityPlayer)var13).áˆºáˆºáŠ.HorizonCode_Horizon_È) {
                                    this.Â(var1, var13.ˆá());
                                    break;
                                }
                                break;
                            }
                            else {
                                var11.remove(var13);
                                ++var12;
                            }
                        }
                    }
                }
            }
            if (this.Ñ¢Ó() != null) {
                this.Â(0, this.Ñ¢Ó().ˆá());
            }
            else {
                this.Â(0, 0);
            }
            if (this.ˆÐƒØ > 0) {
                --this.ˆÐƒØ;
                if (this.ˆÐƒØ == 0 && this.Ï­Ðƒà.Çªà¢().Â("mobGriefing")) {
                    final int var1 = MathHelper.Ý(this.Çªà¢);
                    final int var9 = MathHelper.Ý(this.ŒÏ);
                    final int var14 = MathHelper.Ý(this.Ê);
                    boolean var15 = false;
                    for (int var16 = -1; var16 <= 1; ++var16) {
                        for (int var17 = -1; var17 <= 1; ++var17) {
                            for (int var18 = 0; var18 <= 3; ++var18) {
                                final int var19 = var9 + var16;
                                final int var20 = var1 + var18;
                                final int var21 = var14 + var17;
                                final Block var22 = this.Ï­Ðƒà.Â(new BlockPos(var19, var20, var21)).Ý();
                                if (var22.Ó() != Material.HorizonCode_Horizon_È && var22 != Blocks.áŒŠÆ && var22 != Blocks.Ï­Ä && var22 != Blocks.¥áŠ && var22 != Blocks.ŠÑ¢Ó && var22 != Blocks.¥ÇªÅ) {
                                    var15 = (this.Ï­Ðƒà.Â(new BlockPos(var19, var20, var21), true) || var15);
                                }
                            }
                        }
                    }
                    if (var15) {
                        this.Ï­Ðƒà.HorizonCode_Horizon_È(null, 1012, new BlockPos(this), 0);
                    }
                }
            }
            if (this.Œ % 20 == 0) {
                this.a_(1.0f);
            }
        }
    }
    
    public void Ø() {
        this.ˆà(220);
        this.áˆºÑ¢Õ(this.ÇŽÊ() / 3.0f);
    }
    
    @Override
    public void ¥Ä() {
    }
    
    @Override
    public int áŒŠÉ() {
        return 4;
    }
    
    private double Ø­à(final int p_82214_1_) {
        if (p_82214_1_ <= 0) {
            return this.ŒÏ;
        }
        final float var2 = (this.¥É + 180 * (p_82214_1_ - 1)) / 180.0f * 3.1415927f;
        final float var3 = MathHelper.Â(var2);
        return this.ŒÏ + var3 * 1.3;
    }
    
    private double µÕ(final int p_82208_1_) {
        return (p_82208_1_ <= 0) ? (this.Çªà¢ + 3.0) : (this.Çªà¢ + 2.2);
    }
    
    private double Æ(final int p_82213_1_) {
        if (p_82213_1_ <= 0) {
            return this.Ê;
        }
        final float var2 = (this.¥É + 180 * (p_82213_1_ - 1)) / 180.0f * 3.1415927f;
        final float var3 = MathHelper.HorizonCode_Horizon_È(var2);
        return this.Ê + var3 * 1.3;
    }
    
    private float HorizonCode_Horizon_È(final float p_82204_1_, final float p_82204_2_, final float p_82204_3_) {
        float var4 = MathHelper.à(p_82204_2_ - p_82204_1_);
        if (var4 > p_82204_3_) {
            var4 = p_82204_3_;
        }
        if (var4 < -p_82204_3_) {
            var4 = -p_82204_3_;
        }
        return p_82204_1_ + var4;
    }
    
    private void HorizonCode_Horizon_È(final int p_82216_1_, final EntityLivingBase p_82216_2_) {
        this.HorizonCode_Horizon_È(p_82216_1_, p_82216_2_.ŒÏ, p_82216_2_.Çªà¢ + p_82216_2_.Ðƒáƒ() * 0.5, p_82216_2_.Ê, p_82216_1_ == 0 && this.ˆáƒ.nextFloat() < 0.001f);
    }
    
    private void HorizonCode_Horizon_È(final int p_82209_1_, final double p_82209_2_, final double p_82209_4_, final double p_82209_6_, final boolean p_82209_8_) {
        this.Ï­Ðƒà.HorizonCode_Horizon_È(null, 1014, new BlockPos(this), 0);
        final double var9 = this.Ø­à(p_82209_1_);
        final double var10 = this.µÕ(p_82209_1_);
        final double var11 = this.Æ(p_82209_1_);
        final double var12 = p_82209_2_ - var9;
        final double var13 = p_82209_4_ - var10;
        final double var14 = p_82209_6_ - var11;
        final EntityWitherSkull var15 = new EntityWitherSkull(this.Ï­Ðƒà, this, var12, var13, var14);
        if (p_82209_8_) {
            var15.HorizonCode_Horizon_È(true);
        }
        var15.Çªà¢ = var10;
        var15.ŒÏ = var9;
        var15.Ê = var11;
        this.Ï­Ðƒà.HorizonCode_Horizon_È(var15);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_82196_1_, final float p_82196_2_) {
        this.HorizonCode_Horizon_È(0, p_82196_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (source == DamageSource.Ó || source.áˆºÑ¢Õ() instanceof EntityWither) {
            return false;
        }
        if (this.ÇŽ() > 0 && source != DamageSource.áˆºÑ¢Õ) {
            return false;
        }
        if (this.ÇŽÅ()) {
            final Entity var3 = source.áŒŠÆ();
            if (var3 instanceof EntityArrow) {
                return false;
            }
        }
        final Entity var3 = source.áˆºÑ¢Õ();
        if (var3 != null && !(var3 instanceof EntityPlayer) && var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).¥áŒŠà() == this.¥áŒŠà()) {
            return false;
        }
        if (this.ˆÐƒØ <= 0) {
            this.ˆÐƒØ = 20;
        }
        for (int var4 = 0; var4 < this.ŠØ.length; ++var4) {
            final int[] šø = this.ŠØ;
            final int n = var4;
            šø[n] += 3;
        }
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        final EntityItem var3 = this.HorizonCode_Horizon_È(Items.áˆºá, 1);
        if (var3 != null) {
            var3.Æ();
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            for (final EntityPlayer var5 : this.Ï­Ðƒà.HorizonCode_Horizon_È(EntityPlayer.class, this.£É().Â(50.0, 100.0, 50.0))) {
                var5.HorizonCode_Horizon_È(AchievementList.á€);
            }
        }
    }
    
    @Override
    protected void áŒŠá() {
        this.ŠÕ = 0;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_70070_1_) {
        return 15728880;
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PotionEffect p_70690_1_) {
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(300.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.6000000238418579);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Â).HorizonCode_Horizon_È(40.0);
    }
    
    public float HorizonCode_Horizon_È(final int p_82207_1_) {
        return this.Ý[p_82207_1_];
    }
    
    public float Â(final int p_82210_1_) {
        return this.Â[p_82210_1_];
    }
    
    public int ÇŽ() {
        return this.£Ó.Ý(20);
    }
    
    public void ˆà(final int p_82215_1_) {
        this.£Ó.Â(20, p_82215_1_);
    }
    
    public int ¥Æ(final int p_82203_1_) {
        return this.£Ó.Ý(17 + p_82203_1_);
    }
    
    public void Â(final int p_82211_1_, final int p_82211_2_) {
        this.£Ó.Â(17 + p_82211_1_, p_82211_2_);
    }
    
    public boolean ÇŽÅ() {
        return this.Ï­Ä() <= this.ÇŽÊ() / 2.0f;
    }
    
    @Override
    public EnumCreatureAttribute ¥áŒŠà() {
        return EnumCreatureAttribute.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Entity entityIn) {
        this.Æ = null;
    }
}
