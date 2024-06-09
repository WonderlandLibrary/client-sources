package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class EntityWolf extends EntityTameable
{
    private float Ï­Ï;
    private float ŠØ;
    private boolean ˆÐƒØ;
    private boolean Çªà;
    private float ¥Å;
    private float Œáƒ;
    private static final String Œá = "CL_00001654";
    
    public EntityWolf(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.6f, 0.8f);
        ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, this.ŒÂ);
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAILeapAtTarget(this, 0.4f));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIAttackOnCollide(this, 1.0, true));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIFollowOwner(this, 1.0, 10.0f, 2.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIMate(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAIBeg(this, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(9, new EntityAILookIdle(this));
        this.á.HorizonCode_Horizon_È(1, new EntityAIOwnerHurtByTarget(this));
        this.á.HorizonCode_Horizon_È(2, new EntityAIOwnerHurtTarget(this));
        this.á.HorizonCode_Horizon_È(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.á.HorizonCode_Horizon_È(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, (Predicate)new Predicate() {
            private static final String Â = "CL_00002229";
            
            public boolean HorizonCode_Horizon_È(final Entity p_180094_1_) {
                return p_180094_1_ instanceof EntitySheep || p_180094_1_ instanceof EntityRabbit;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        }));
        this.á.HorizonCode_Horizon_È(5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
        this.á(false);
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.30000001192092896);
        if (this.ÐƒÓ()) {
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(20.0);
        }
        else {
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(8.0);
        }
        this.µÐƒÓ().Â(SharedMonsterAttributes.Âµá€);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(2.0);
    }
    
    @Override
    public void Â(final EntityLivingBase p_70624_1_) {
        super.Â(p_70624_1_);
        if (p_70624_1_ == null) {
            this.Å(false);
        }
        else if (!this.ÐƒÓ()) {
            this.Å(true);
        }
    }
    
    @Override
    protected void ˆØ() {
        this.£Ó.Â(18, this.Ï­Ä());
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(18, new Float(this.Ï­Ä()));
        this.£Ó.HorizonCode_Horizon_È(19, new Byte((byte)0));
        this.£Ó.HorizonCode_Horizon_È(20, new Byte((byte)EnumDyeColor.Å.Â()));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.wolf.step", 0.15f, 1.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Angry", this.Ø­È());
        tagCompound.HorizonCode_Horizon_È("CollarColor", (byte)this.Ñ¢Õ().Ý());
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.Å(tagCompund.£á("Angry"));
        if (tagCompund.Â("CollarColor", 99)) {
            this.HorizonCode_Horizon_È(EnumDyeColor.HorizonCode_Horizon_È(tagCompund.Ø­áŒŠá("CollarColor")));
        }
    }
    
    @Override
    protected String µÐƒáƒ() {
        return this.Ø­È() ? "mob.wolf.growl" : ((this.ˆáƒ.nextInt(3) == 0) ? ((this.ÐƒÓ() && this.£Ó.Ø­áŒŠá(18) < 10.0f) ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.wolf.hurt";
    }
    
    @Override
    protected String µÊ() {
        return "mob.wolf.death";
    }
    
    @Override
    protected float ˆÂ() {
        return 0.4f;
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Item_1028566121.HorizonCode_Horizon_È(-1);
    }
    
    @Override
    public void ˆÏ­() {
        super.ˆÏ­();
        if (!this.Ï­Ðƒà.ŠÄ && this.ˆÐƒØ && !this.Çªà && !this.ˆà() && this.ŠÂµà) {
            this.Çªà = true;
            this.¥Å = 0.0f;
            this.Œáƒ = 0.0f;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)8);
        }
        if (!this.Ï­Ðƒà.ŠÄ && this.Ñ¢Ó() == null && this.Ø­È()) {
            this.Å(false);
        }
    }
    
    @Override
    public void á() {
        super.á();
        this.ŠØ = this.Ï­Ï;
        if (this.Ø­à¢()) {
            this.Ï­Ï += (1.0f - this.Ï­Ï) * 0.4f;
        }
        else {
            this.Ï­Ï += (0.0f - this.Ï­Ï) * 0.4f;
        }
        if (this.áŒŠ()) {
            this.ˆÐƒØ = true;
            this.Çªà = false;
            this.¥Å = 0.0f;
            this.Œáƒ = 0.0f;
        }
        else if ((this.ˆÐƒØ || this.Çªà) && this.Çªà) {
            if (this.¥Å == 0.0f) {
                this.HorizonCode_Horizon_È("mob.wolf.shake", this.ˆÂ(), (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f);
            }
            this.Œáƒ = this.¥Å;
            this.¥Å += 0.05f;
            if (this.Œáƒ >= 2.0f) {
                this.ˆÐƒØ = false;
                this.Çªà = false;
                this.Œáƒ = 0.0f;
                this.¥Å = 0.0f;
            }
            if (this.¥Å > 0.4f) {
                final float var1 = (float)this.£É().Â;
                for (int var2 = (int)(MathHelper.HorizonCode_Horizon_È((this.¥Å - 0.4f) * 3.1415927f) * 7.0f), var3 = 0; var3 < var2; ++var3) {
                    final float var4 = (this.ˆáƒ.nextFloat() * 2.0f - 1.0f) * this.áŒŠ * 0.5f;
                    final float var5 = (this.ˆáƒ.nextFloat() * 2.0f - 1.0f) * this.áŒŠ * 0.5f;
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(EnumParticleTypes.Ó, this.ŒÏ + var4, var1 + 0.8f, this.Ê + var5, this.ÇŽÉ, this.ˆá, this.ÇŽÕ, new int[0]);
                }
            }
        }
    }
    
    public boolean ÐƒÇŽà() {
        return this.ˆÐƒØ;
    }
    
    public float £á(final float p_70915_1_) {
        return 0.75f + (this.Œáƒ + (this.¥Å - this.Œáƒ) * p_70915_1_) / 2.0f * 0.25f;
    }
    
    public float Ø(final float p_70923_1_, final float p_70923_2_) {
        float var3 = (this.Œáƒ + (this.¥Å - this.Œáƒ) * p_70923_1_ + p_70923_2_) / 1.8f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        else if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return MathHelper.HorizonCode_Horizon_È(var3 * 3.1415927f) * MathHelper.HorizonCode_Horizon_È(var3 * 3.1415927f * 11.0f) * 0.15f * 3.1415927f;
    }
    
    public float Å(final float p_70917_1_) {
        return (this.ŠØ + (this.Ï­Ï - this.ŠØ) * p_70917_1_) * 0.15f * 3.1415927f;
    }
    
    @Override
    public float Ðƒáƒ() {
        return this.£ÂµÄ * 0.8f;
    }
    
    @Override
    public int áˆºà() {
        return this.áˆºÕ() ? 20 : super.áˆºà();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        final Entity var3 = source.áˆºÑ¢Õ();
        this.ŒÂ.HorizonCode_Horizon_È(false);
        if (var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow)) {
            amount = (amount + 1.0f) / 2.0f;
        }
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    public boolean Å(final Entity p_70652_1_) {
        final boolean var2 = p_70652_1_.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this), (int)this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).Âµá€());
        if (var2) {
            this.HorizonCode_Horizon_È(this, p_70652_1_);
        }
        return var2;
    }
    
    @Override
    public void á(final boolean p_70903_1_) {
        super.á(p_70903_1_);
        if (p_70903_1_) {
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(20.0);
        }
        else {
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(8.0);
        }
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(4.0);
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (this.ÐƒÓ()) {
            if (var2 != null) {
                if (var2.HorizonCode_Horizon_È() instanceof ItemFood) {
                    final ItemFood var3 = (ItemFood)var2.HorizonCode_Horizon_È();
                    if (var3.ˆà() && this.£Ó.Ø­áŒŠá(18) < 20.0f) {
                        if (!p_70085_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
                            final ItemStack itemStack = var2;
                            --itemStack.Â;
                        }
                        this.a_(var3.ÂµÈ(var2));
                        if (var2.Â <= 0) {
                            p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
                        }
                        return true;
                    }
                }
                else if (var2.HorizonCode_Horizon_È() == Items.áŒŠÔ) {
                    final EnumDyeColor var4 = EnumDyeColor.HorizonCode_Horizon_È(var2.Ø());
                    if (var4 != this.Ñ¢Õ()) {
                        this.HorizonCode_Horizon_È(var4);
                        if (!p_70085_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
                            final ItemStack itemStack2 = var2;
                            if (--itemStack2.Â <= 0) {
                                p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
                            }
                        }
                        return true;
                    }
                }
            }
            if (this.Âµá€((EntityLivingBase)p_70085_1_) && !this.Ï­Ðƒà.ŠÄ && !this.Ø­áŒŠá(var2)) {
                this.ŒÂ.HorizonCode_Horizon_È(!this.áˆºÕ());
                this.ÐƒÂ = false;
                this.áˆºÑ¢Õ.à();
                this.Â((EntityLivingBase)null);
            }
        }
        else if (var2 != null && var2.HorizonCode_Horizon_È() == Items.ŠÕ && !this.Ø­È()) {
            if (!p_70085_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
                final ItemStack itemStack3 = var2;
                --itemStack3.Â;
            }
            if (var2.Â <= 0) {
                p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
            }
            if (!this.Ï­Ðƒà.ŠÄ) {
                if (this.ˆáƒ.nextInt(3) == 0) {
                    this.á(true);
                    this.áˆºÑ¢Õ.à();
                    this.Â((EntityLivingBase)null);
                    this.ŒÂ.HorizonCode_Horizon_È(true);
                    this.áˆºÑ¢Õ(20.0f);
                    this.HorizonCode_Horizon_È(p_70085_1_.£áŒŠá().toString());
                    this.ˆÏ­(true);
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)7);
                }
                else {
                    this.ˆÏ­(false);
                    this.Ï­Ðƒà.HorizonCode_Horizon_È(this, (byte)6);
                }
            }
            return true;
        }
        return super.Ø­áŒŠá(p_70085_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final byte p_70103_1_) {
        if (p_70103_1_ == 8) {
            this.Çªà = true;
            this.¥Å = 0.0f;
            this.Œáƒ = 0.0f;
        }
        else {
            super.HorizonCode_Horizon_È(p_70103_1_);
        }
    }
    
    public float áˆºÉ() {
        return this.Ø­È() ? 1.5393804f : (this.ÐƒÓ() ? ((0.55f - (20.0f - this.£Ó.Ø­áŒŠá(18)) * 0.02f) * 3.1415927f) : 0.62831855f);
    }
    
    @Override
    public boolean Ø­áŒŠá(final ItemStack p_70877_1_) {
        return p_70877_1_ != null && p_70877_1_.HorizonCode_Horizon_È() instanceof ItemFood && ((ItemFood)p_70877_1_.HorizonCode_Horizon_È()).ˆà();
    }
    
    @Override
    public int Ï­áˆºÓ() {
        return 8;
    }
    
    public boolean Ø­È() {
        return (this.£Ó.HorizonCode_Horizon_È(16) & 0x2) != 0x0;
    }
    
    public void Å(final boolean p_70916_1_) {
        final byte var2 = this.£Ó.HorizonCode_Horizon_È(16);
        if (p_70916_1_) {
            this.£Ó.Â(16, (byte)(var2 | 0x2));
        }
        else {
            this.£Ó.Â(16, (byte)(var2 & 0xFFFFFFFD));
        }
    }
    
    public EnumDyeColor Ñ¢Õ() {
        return EnumDyeColor.HorizonCode_Horizon_È(this.£Ó.HorizonCode_Horizon_È(20) & 0xF);
    }
    
    public void HorizonCode_Horizon_È(final EnumDyeColor p_175547_1_) {
        this.£Ó.Â(20, (byte)(p_175547_1_.Ý() & 0xF));
    }
    
    public EntityWolf Â(final EntityAgeable p_90011_1_) {
        final EntityWolf var2 = new EntityWolf(this.Ï­Ðƒà);
        final String var3 = this.Â();
        if (var3 != null && var3.trim().length() > 0) {
            var2.HorizonCode_Horizon_È(var3);
            var2.á(true);
        }
        return var2;
    }
    
    public void £à(final boolean p_70918_1_) {
        if (p_70918_1_) {
            this.£Ó.Â(19, (byte)1);
        }
        else {
            this.£Ó.Â(19, (byte)0);
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityAnimal p_70878_1_) {
        if (p_70878_1_ == this) {
            return false;
        }
        if (!this.ÐƒÓ()) {
            return false;
        }
        if (!(p_70878_1_ instanceof EntityWolf)) {
            return false;
        }
        final EntityWolf var2 = (EntityWolf)p_70878_1_;
        return var2.ÐƒÓ() && !var2.áˆºÕ() && (this.ÇŽÅ() && var2.ÇŽÅ());
    }
    
    public boolean Ø­à¢() {
        return this.£Ó.HorizonCode_Horizon_È(19) == 1;
    }
    
    @Override
    protected boolean ÂµÂ() {
        return !this.ÐƒÓ() && this.Œ > 2400;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityLivingBase p_142018_1_, final EntityLivingBase p_142018_2_) {
        if (!(p_142018_1_ instanceof EntityCreeper) && !(p_142018_1_ instanceof EntityGhast)) {
            if (p_142018_1_ instanceof EntityWolf) {
                final EntityWolf var3 = (EntityWolf)p_142018_1_;
                if (var3.ÐƒÓ() && var3.ŒÐƒà() == p_142018_2_) {
                    return false;
                }
            }
            return (!(p_142018_1_ instanceof EntityPlayer) || !(p_142018_2_ instanceof EntityPlayer) || ((EntityPlayer)p_142018_2_).Ø­áŒŠá((EntityPlayer)p_142018_1_)) && (!(p_142018_1_ instanceof EntityHorse) || !((EntityHorse)p_142018_1_).áˆºÕ());
        }
        return false;
    }
    
    @Override
    public boolean ŠÏ­áˆºá() {
        return !this.Ø­È() && super.ŠÏ­áˆºá();
    }
}
