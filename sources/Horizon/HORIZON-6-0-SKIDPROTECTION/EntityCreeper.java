package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class EntityCreeper extends EntityMob
{
    private int Â;
    private int Ý;
    private int Ø­Ñ¢Ï­Ø­áˆº;
    private int ŒÂ;
    private int Ï­Ï;
    private static final String ŠØ = "CL_00001684";
    
    public EntityCreeper(final World worldIn) {
        super(worldIn);
        this.Ø­Ñ¢Ï­Ø­áˆº = 30;
        this.ŒÂ = 3;
        this.Ï­Ï = 0;
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAICreeperSwell(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, this.HorizonCode_Horizon_È);
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAIAvoidEntity(this, (Predicate)new Predicate() {
            private static final String Â = "CL_00002224";
            
            public boolean HorizonCode_Horizon_È(final Entity p_179958_1_) {
                return p_179958_1_ instanceof EntityOcelot;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        }, 6.0f, 1.0, 1.2));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIAttackOnCollide(this, 1.0, false));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIWander(this, 0.8));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAILookIdle(this));
        this.á.HorizonCode_Horizon_È(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.á.HorizonCode_Horizon_È(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.25);
    }
    
    @Override
    public int ŠÓ() {
        return (this.Ñ¢Ó() == null) ? 3 : (3 + (int)(this.Ï­Ä() - 1.0f));
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
        super.Ø­áŒŠá(distance, damageMultiplier);
        this.Ý += (int)(distance * 1.5f);
        if (this.Ý > this.Ø­Ñ¢Ï­Ø­áˆº - 5) {
            this.Ý = this.Ø­Ñ¢Ï­Ø­áˆº - 5;
        }
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, (Object)(byte)(-1));
        this.£Ó.HorizonCode_Horizon_È(17, (Object)(byte)0);
        this.£Ó.HorizonCode_Horizon_È(18, (Object)(byte)0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        if (this.£Ó.HorizonCode_Horizon_È(17) == 1) {
            tagCompound.HorizonCode_Horizon_È("powered", true);
        }
        tagCompound.HorizonCode_Horizon_È("Fuse", (short)this.Ø­Ñ¢Ï­Ø­áˆº);
        tagCompound.HorizonCode_Horizon_È("ExplosionRadius", (byte)this.ŒÂ);
        tagCompound.HorizonCode_Horizon_È("ignited", this.¥Ðƒá());
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.£Ó.Â(17, (byte)(byte)(tagCompund.£á("powered") ? 1 : 0));
        if (tagCompund.Â("Fuse", 99)) {
            this.Ø­Ñ¢Ï­Ø­áˆº = tagCompund.Âµá€("Fuse");
        }
        if (tagCompund.Â("ExplosionRadius", 99)) {
            this.ŒÂ = tagCompund.Ø­áŒŠá("ExplosionRadius");
        }
        if (tagCompund.£á("ignited")) {
            this.ÐƒÇŽà();
        }
    }
    
    @Override
    public void á() {
        if (this.Œ()) {
            this.Â = this.Ý;
            if (this.¥Ðƒá()) {
                this.HorizonCode_Horizon_È(1);
            }
            final int var1 = this.ÇŽÅ();
            if (var1 > 0 && this.Ý == 0) {
                this.HorizonCode_Horizon_È("creeper.primed", 1.0f, 0.5f);
            }
            this.Ý += var1;
            if (this.Ý < 0) {
                this.Ý = 0;
            }
            if (this.Ý >= this.Ø­Ñ¢Ï­Ø­áˆº) {
                this.Ý = this.Ø­Ñ¢Ï­Ø­áˆº;
                this.áˆºÕ();
            }
        }
        super.á();
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.creeper.say";
    }
    
    @Override
    protected String µÊ() {
        return "mob.creeper.death";
    }
    
    @Override
    public void Â(final DamageSource cause) {
        super.Â(cause);
        if (cause.áˆºÑ¢Õ() instanceof EntitySkeleton) {
            final int var2 = Item_1028566121.HorizonCode_Horizon_È(Items.áˆºÉ);
            final int var3 = Item_1028566121.HorizonCode_Horizon_È(Items.ÐƒÉ);
            final int var4 = var2 + this.ˆáƒ.nextInt(var3 - var2 + 1);
            this.HorizonCode_Horizon_È(Item_1028566121.HorizonCode_Horizon_È(var4), 1);
        }
        else if (cause.áˆºÑ¢Õ() instanceof EntityCreeper && cause.áˆºÑ¢Õ() != this && ((EntityCreeper)cause.áˆºÑ¢Õ()).Ø() && ((EntityCreeper)cause.áˆºÑ¢Õ()).¥Ê()) {
            ((EntityCreeper)cause.áˆºÑ¢Õ()).ÐƒÓ();
            this.HorizonCode_Horizon_È(new ItemStack(Items.ˆ, 1, 4), 0.0f);
        }
    }
    
    @Override
    public boolean Å(final Entity p_70652_1_) {
        return true;
    }
    
    public boolean Ø() {
        return this.£Ó.HorizonCode_Horizon_È(17) == 1;
    }
    
    public float Ý(final float p_70831_1_) {
        return (this.Â + (this.Ý - this.Â) * p_70831_1_) / (this.Ø­Ñ¢Ï­Ø­áˆº - 2);
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.É;
    }
    
    public int ÇŽÅ() {
        return this.£Ó.HorizonCode_Horizon_È(16);
    }
    
    public void HorizonCode_Horizon_È(final int p_70829_1_) {
        this.£Ó.Â(16, (byte)p_70829_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLightningBolt lightningBolt) {
        super.HorizonCode_Horizon_È(lightningBolt);
        this.£Ó.Â(17, (byte)1);
    }
    
    @Override
    protected boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.Ø­áŒŠá) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ + 0.5, this.Çªà¢ + 0.5, this.Ê + 0.5, "fire.ignite", 1.0f, this.ˆáƒ.nextFloat() * 0.4f + 0.8f);
            p_70085_1_.b_();
            if (!this.Ï­Ðƒà.ŠÄ) {
                this.ÐƒÇŽà();
                var2.HorizonCode_Horizon_È(1, p_70085_1_);
                return true;
            }
        }
        return super.Ø­áŒŠá(p_70085_1_);
    }
    
    private void áˆºÕ() {
        if (!this.Ï­Ðƒà.ŠÄ) {
            final boolean var1 = this.Ï­Ðƒà.Çªà¢().Â("mobGriefing");
            final float var2 = this.Ø() ? 2.0f : 1.0f;
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.ŒÏ, this.Çªà¢, this.Ê, this.ŒÂ * var2, var1);
            this.á€();
        }
    }
    
    public boolean ¥Ðƒá() {
        return this.£Ó.HorizonCode_Horizon_È(18) != 0;
    }
    
    public void ÐƒÇŽà() {
        this.£Ó.Â(18, (byte)1);
    }
    
    public boolean ¥Ê() {
        return this.Ï­Ï < 1 && this.Ï­Ðƒà.Çªà¢().Â("doMobLoot");
    }
    
    public void ÐƒÓ() {
        ++this.Ï­Ï;
    }
}
