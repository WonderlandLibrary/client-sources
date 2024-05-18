package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class EntityOcelot extends EntityTameable
{
    private EntityAIAvoidEntity Ï­Ï;
    private EntityAITempt ŠØ;
    private static final String ˆÐƒØ = "CL_00001646";
    
    public EntityOcelot(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.6f, 0.7f);
        ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, this.ŒÂ);
        this.ÂµÈ.HorizonCode_Horizon_È(3, this.ŠØ = new EntityAITempt(this, 0.6, Items.Ñ¢Ó, true));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIFollowOwner(this, 1.0, 10.0f, 5.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIOcelotSit(this, 0.8));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAILeapAtTarget(this, 0.3f));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAIOcelotAttack(this));
        this.ÂµÈ.HorizonCode_Horizon_È(9, new EntityAIMate(this, 0.8));
        this.ÂµÈ.HorizonCode_Horizon_È(10, new EntityAIWander(this, 0.8));
        this.ÂµÈ.HorizonCode_Horizon_È(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0f));
        this.á.HorizonCode_Horizon_È(1, new EntityAITargetNonTamed(this, EntityChicken.class, false, null));
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(18, (Object)(byte)0);
    }
    
    public void ˆØ() {
        if (this.ŒÏ().HorizonCode_Horizon_È()) {
            final double var1 = this.ŒÏ().Â();
            if (var1 == 0.6) {
                this.Âµá€(true);
                this.Â(false);
            }
            else if (var1 == 1.33) {
                this.Âµá€(false);
                this.Â(true);
            }
            else {
                this.Âµá€(false);
                this.Â(false);
            }
        }
        else {
            this.Âµá€(false);
            this.Â(false);
        }
    }
    
    @Override
    protected boolean ÂµÂ() {
        return !this.ÐƒÓ() && this.Œ > 2400;
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(10.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.30000001192092896);
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("CatType", this.ÐƒÇŽà());
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.ˆà(tagCompund.Ó("CatType"));
    }
    
    @Override
    protected String µÐƒáƒ() {
        return this.ÐƒÓ() ? (this.ÇŽÅ() ? "mob.cat.purr" : ((this.ˆáƒ.nextInt(4) == 0) ? "mob.cat.purreow" : "mob.cat.meow")) : "";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.cat.hitt";
    }
    
    @Override
    protected String µÊ() {
        return "mob.cat.hitt";
    }
    
    @Override
    protected float ˆÂ() {
        return 0.4f;
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.£áŒŠá;
    }
    
    @Override
    public boolean Å(final Entity p_70652_1_) {
        return p_70652_1_.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this), 3.0f);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        this.ŒÂ.HorizonCode_Horizon_È(false);
        return super.HorizonCode_Horizon_È(source, amount);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (this.ÐƒÓ()) {
            if (this.Âµá€((EntityLivingBase)p_70085_1_) && !this.Ï­Ðƒà.ŠÄ && !this.Ø­áŒŠá(var2)) {
                this.ŒÂ.HorizonCode_Horizon_È(!this.áˆºÕ());
            }
        }
        else if (this.ŠØ.Ø() && var2 != null && var2.HorizonCode_Horizon_È() == Items.Ñ¢Ó && p_70085_1_.Âµá€(this) < 9.0) {
            if (!p_70085_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
                final ItemStack itemStack = var2;
                --itemStack.Â;
            }
            if (var2.Â <= 0) {
                p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
            }
            if (!this.Ï­Ðƒà.ŠÄ) {
                if (this.ˆáƒ.nextInt(3) == 0) {
                    this.á(true);
                    this.ˆà(1 + this.Ï­Ðƒà.Å.nextInt(3));
                    this.HorizonCode_Horizon_È(p_70085_1_.£áŒŠá().toString());
                    this.ˆÏ­(true);
                    this.ŒÂ.HorizonCode_Horizon_È(true);
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
    
    public EntityOcelot Â(final EntityAgeable p_180493_1_) {
        final EntityOcelot var2 = new EntityOcelot(this.Ï­Ðƒà);
        if (this.ÐƒÓ()) {
            var2.HorizonCode_Horizon_È(this.Â());
            var2.á(true);
            var2.ˆà(this.ÐƒÇŽà());
        }
        return var2;
    }
    
    @Override
    public boolean Ø­áŒŠá(final ItemStack p_70877_1_) {
        return p_70877_1_ != null && p_70877_1_.HorizonCode_Horizon_È() == Items.Ñ¢Ó;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityAnimal p_70878_1_) {
        if (p_70878_1_ == this) {
            return false;
        }
        if (!this.ÐƒÓ()) {
            return false;
        }
        if (!(p_70878_1_ instanceof EntityOcelot)) {
            return false;
        }
        final EntityOcelot var2 = (EntityOcelot)p_70878_1_;
        return var2.ÐƒÓ() && (this.ÇŽÅ() && var2.ÇŽÅ());
    }
    
    public int ÐƒÇŽà() {
        return this.£Ó.HorizonCode_Horizon_È(18);
    }
    
    public void ˆà(final int p_70912_1_) {
        this.£Ó.Â(18, (byte)p_70912_1_);
    }
    
    @Override
    public boolean µà() {
        return this.Ï­Ðƒà.Å.nextInt(3) != 0;
    }
    
    @Override
    public boolean ÐƒÂ() {
        if (this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É(), this) && this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.£É()).isEmpty() && !this.Ï­Ðƒà.Ø­áŒŠá(this.£É())) {
            final BlockPos var1 = new BlockPos(this.ŒÏ, this.£É().Â, this.Ê);
            if (var1.Â() < 63) {
                return false;
            }
            final Block var2 = this.Ï­Ðƒà.Â(var1.Âµá€()).Ý();
            if (var2 == Blocks.Ø­áŒŠá || var2.Ó() == Material.áˆºÑ¢Õ) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String v_() {
        return this.j_() ? this.Šà() : (this.ÐƒÓ() ? StatCollector.HorizonCode_Horizon_È("entity.Cat.name") : super.v_());
    }
    
    @Override
    public void á(final boolean p_70903_1_) {
        super.á(p_70903_1_);
    }
    
    @Override
    protected void ¥Ê() {
        if (this.Ï­Ï == null) {
            this.Ï­Ï = new EntityAIAvoidEntity(this, (Predicate)new Predicate() {
                private static final String Â = "CL_00002243";
                
                public boolean HorizonCode_Horizon_È(final Entity p_179874_1_) {
                    return p_179874_1_ instanceof EntityPlayer;
                }
                
                public boolean apply(final Object p_apply_1_) {
                    return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
                }
            }, 16.0f, 0.8, 1.33);
        }
        this.ÂµÈ.HorizonCode_Horizon_È(this.Ï­Ï);
        if (!this.ÐƒÓ()) {
            this.ÂµÈ.HorizonCode_Horizon_È(4, this.Ï­Ï);
        }
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_) {
        p_180482_2_ = super.HorizonCode_Horizon_È(p_180482_1_, p_180482_2_);
        if (this.Ï­Ðƒà.Å.nextInt(7) == 0) {
            for (int var3 = 0; var3 < 2; ++var3) {
                final EntityOcelot var4 = new EntityOcelot(this.Ï­Ðƒà);
                var4.Â(this.ŒÏ, this.Çªà¢, this.Ê, this.É, 0.0f);
                var4.Â(-24000);
                this.Ï­Ðƒà.HorizonCode_Horizon_È(var4);
            }
        }
        return p_180482_2_;
    }
    
    @Override
    public EntityAgeable HorizonCode_Horizon_È(final EntityAgeable p_90011_1_) {
        return this.Â(p_90011_1_);
    }
}
