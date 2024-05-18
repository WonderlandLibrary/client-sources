package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public abstract class EntityMob extends EntityCreature implements IMob
{
    protected final EntityAIBase HorizonCode_Horizon_È;
    private static final String Â = "CL_00001692";
    
    public EntityMob(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È = new EntityAIAvoidEntity(this, (Predicate)new Predicate() {
            private static final String Â = "CL_00002208";
            
            public boolean HorizonCode_Horizon_È(final Entity p_179911_1_) {
                return p_179911_1_ instanceof EntityCreeper && ((EntityCreeper)p_179911_1_).ÇŽÅ() > 0;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        }, 4.0f, 1.0, 2.0);
        this.à = 5;
    }
    
    @Override
    public void ˆÏ­() {
        this.µÏ();
        final float var1 = this.Â(1.0f);
        if (var1 > 0.5f) {
            this.ŠÕ += 2;
        }
        super.ˆÏ­();
    }
    
    @Override
    public void á() {
        super.á();
        if (!this.Ï­Ðƒà.ŠÄ && this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.HorizonCode_Horizon_È) {
            this.á€();
        }
    }
    
    @Override
    protected String Ç() {
        return "game.hostile.swim";
    }
    
    @Override
    protected String áˆºáˆºÈ() {
        return "game.hostile.swim.splash";
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        if (this.HorizonCode_Horizon_È(source)) {
            return false;
        }
        if (super.HorizonCode_Horizon_È(source, amount)) {
            final Entity var3 = source.áˆºÑ¢Õ();
            return this.µÕ == var3 || this.Æ == var3 || true;
        }
        return false;
    }
    
    @Override
    protected String ¥áŠ() {
        return "game.hostile.hurt";
    }
    
    @Override
    protected String µÊ() {
        return "game.hostile.die";
    }
    
    @Override
    protected String £à(final int p_146067_1_) {
        return (p_146067_1_ > 4) ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }
    
    @Override
    public boolean Å(final Entity p_70652_1_) {
        float var2 = (float)this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).Âµá€();
        int var3 = 0;
        if (p_70652_1_ instanceof EntityLivingBase) {
            var2 += EnchantmentHelper.HorizonCode_Horizon_È(this.Çª(), ((EntityLivingBase)p_70652_1_).¥áŒŠà());
            var3 += EnchantmentHelper.HorizonCode_Horizon_È(this);
        }
        final boolean var4 = p_70652_1_.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this), var2);
        if (var4) {
            if (var3 > 0) {
                p_70652_1_.à(-MathHelper.HorizonCode_Horizon_È(this.É * 3.1415927f / 180.0f) * var3 * 0.5f, 0.1, MathHelper.Â(this.É * 3.1415927f / 180.0f) * var3 * 0.5f);
                this.ÇŽÉ *= 0.6;
                this.ÇŽÕ *= 0.6;
            }
            final int var5 = EnchantmentHelper.Â(this);
            if (var5 > 0) {
                p_70652_1_.Âµá€(var5 * 4);
            }
            this.HorizonCode_Horizon_È(this, p_70652_1_);
        }
        return var4;
    }
    
    @Override
    public float HorizonCode_Horizon_È(final BlockPos p_180484_1_) {
        return 0.5f - this.Ï­Ðƒà.£à(p_180484_1_);
    }
    
    protected boolean w_() {
        final BlockPos var1 = new BlockPos(this.ŒÏ, this.£É().Â, this.Ê);
        if (this.Ï­Ðƒà.Â(EnumSkyBlock.HorizonCode_Horizon_È, var1) > this.ˆáƒ.nextInt(32)) {
            return false;
        }
        int var2 = this.Ï­Ðƒà.ˆÏ­(var1);
        if (this.Ï­Ðƒà.ÇŽÉ()) {
            final int var3 = this.Ï­Ðƒà.¥à();
            this.Ï­Ðƒà.Ý(10);
            var2 = this.Ï­Ðƒà.ˆÏ­(var1);
            this.Ï­Ðƒà.Ý(var3);
        }
        return var2 <= this.ˆáƒ.nextInt(8);
    }
    
    @Override
    public boolean µà() {
        return this.Ï­Ðƒà.ŠÂµà() != EnumDifficulty.HorizonCode_Horizon_È && this.w_() && super.µà();
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.µÐƒÓ().Â(SharedMonsterAttributes.Âµá€);
    }
    
    @Override
    protected boolean Ï­Ï() {
        return true;
    }
}
