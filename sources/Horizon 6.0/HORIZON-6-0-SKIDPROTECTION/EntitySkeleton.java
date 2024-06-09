package HORIZON-6-0-SKIDPROTECTION;

import java.util.Calendar;
import com.google.common.base.Predicate;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob
{
    private EntityAIArrowAttack Â;
    private EntityAIAttackOnCollide Ý;
    private static final String Ø­Ñ¢Ï­Ø­áˆº = "CL_00001697";
    
    public EntitySkeleton(final World worldIn) {
        super(worldIn);
        this.Â = new EntityAIArrowAttack(this, 1.0, 20, 60, 15.0f);
        this.Ý = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2, false);
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIRestrictSun(this));
        this.ÂµÈ.HorizonCode_Horizon_È(2, this.HorizonCode_Horizon_È);
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAIFleeSun(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAIAvoidEntity(this, (Predicate)new Predicate() {
            private static final String Â = "CL_00002203";
            
            public boolean HorizonCode_Horizon_È(final Entity p_179945_1_) {
                return p_179945_1_ instanceof EntityWolf;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        }, 6.0f, 1.0, 1.2));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAILookIdle(this));
        this.á.HorizonCode_Horizon_È(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.á.HorizonCode_Horizon_È(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.á.HorizonCode_Horizon_È(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
        if (worldIn != null && !worldIn.ŠÄ) {
            this.Ø();
        }
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.25);
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(13, new Byte((byte)0));
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.skeleton.say";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.skeleton.hurt";
    }
    
    @Override
    protected String µÊ() {
        return "mob.skeleton.death";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.skeleton.step", 0.15f, 1.0f);
    }
    
    @Override
    public boolean Å(final Entity p_70652_1_) {
        if (super.Å(p_70652_1_)) {
            if (this.ÇŽÅ() == 1 && p_70652_1_ instanceof EntityLivingBase) {
                ((EntityLivingBase)p_70652_1_).HorizonCode_Horizon_È(new PotionEffect(Potion.Æ.É, 200));
            }
            return true;
        }
        return false;
    }
    
    @Override
    public EnumCreatureAttribute ¥áŒŠà() {
        return EnumCreatureAttribute.Â;
    }
    
    @Override
    public void ˆÏ­() {
        if (this.Ï­Ðƒà.ÂµÈ() && !this.Ï­Ðƒà.ŠÄ) {
            final float var1 = this.Â(1.0f);
            final BlockPos var2 = new BlockPos(this.ŒÏ, Math.round(this.Çªà¢), this.Ê);
            if (var1 > 0.5f && this.ˆáƒ.nextFloat() * 30.0f < (var1 - 0.4f) * 2.0f && this.Ï­Ðƒà.áˆºÑ¢Õ(var2)) {
                boolean var3 = true;
                final ItemStack var4 = this.Ý(4);
                if (var4 != null) {
                    if (var4.Ø­áŒŠá()) {
                        var4.Â(var4.à() + this.ˆáƒ.nextInt(2));
                        if (var4.à() >= var4.áŒŠÆ()) {
                            this.Ý(var4);
                            this.HorizonCode_Horizon_È(4, (ItemStack)null);
                        }
                    }
                    var3 = false;
                }
                if (var3) {
                    this.Âµá€(8);
                }
            }
        }
        if (this.Ï­Ðƒà.ŠÄ && this.ÇŽÅ() == 1) {
            this.HorizonCode_Horizon_È(0.72f, 2.535f);
        }
        super.ˆÏ­();
    }
    
    @Override
    public void Ø­á() {
        super.Ø­á();
        if (this.Æ instanceof EntityCreature) {
            final EntityCreature var1 = (EntityCreature)this.Æ;
            this.¥É = var1.¥É;
        }
    }
    
    @Override
    public void Â(final DamageSource cause) {
        super.Â(cause);
        if (cause.áŒŠÆ() instanceof EntityArrow && cause.áˆºÑ¢Õ() instanceof EntityPlayer) {
            final EntityPlayer var2 = (EntityPlayer)cause.áˆºÑ¢Õ();
            final double var3 = var2.ŒÏ - this.ŒÏ;
            final double var4 = var2.Ê - this.Ê;
            if (var3 * var3 + var4 * var4 >= 2500.0) {
                var2.HorizonCode_Horizon_È(AchievementList.Æ);
            }
        }
        else if (cause.áˆºÑ¢Õ() instanceof EntityCreeper && ((EntityCreeper)cause.áˆºÑ¢Õ()).Ø() && ((EntityCreeper)cause.áˆºÑ¢Õ()).¥Ê()) {
            ((EntityCreeper)cause.áˆºÑ¢Õ()).ÐƒÓ();
            this.HorizonCode_Horizon_È(new ItemStack(Items.ˆ, 1, (int)((this.ÇŽÅ() == 1) ? 1 : 0)), 0.0f);
        }
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.à;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        if (this.ÇŽÅ() == 1) {
            for (int var3 = this.ˆáƒ.nextInt(3 + p_70628_2_) - 1, var4 = 0; var4 < var3; ++var4) {
                this.HorizonCode_Horizon_È(Items.Ø, 1);
            }
        }
        else {
            for (int var3 = this.ˆáƒ.nextInt(3 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
                this.HorizonCode_Horizon_È(Items.à, 1);
            }
        }
        for (int var3 = this.ˆáƒ.nextInt(3 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(Items.ŠÕ, 1);
        }
    }
    
    @Override
    protected void áˆºáˆºáŠ() {
        if (this.ÇŽÅ() == 1) {
            this.HorizonCode_Horizon_È(new ItemStack(Items.ˆ, 1, 1), 0.0f);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final DifficultyInstance p_180481_1_) {
        super.HorizonCode_Horizon_È(p_180481_1_);
        this.HorizonCode_Horizon_È(0, new ItemStack(Items.Ó));
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_) {
        p_180482_2_ = super.HorizonCode_Horizon_È(p_180482_1_, p_180482_2_);
        if (this.Ï­Ðƒà.£à instanceof WorldProviderHell && this.ˆÐƒØ().nextInt(5) > 0) {
            this.ÂµÈ.HorizonCode_Horizon_È(4, this.Ý);
            this.HorizonCode_Horizon_È(1);
            this.HorizonCode_Horizon_È(0, new ItemStack(Items.µà));
            this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(4.0);
        }
        else {
            this.ÂµÈ.HorizonCode_Horizon_È(4, this.Â);
            this.HorizonCode_Horizon_È(p_180482_1_);
            this.Â(p_180482_1_);
        }
        this.Ý(this.ˆáƒ.nextFloat() < 0.55f * p_180482_1_.Â());
        if (this.Ý(4) == null) {
            final Calendar var3 = this.Ï­Ðƒà.Õ();
            if (var3.get(2) + 1 == 10 && var3.get(5) == 31 && this.ˆáƒ.nextFloat() < 0.25f) {
                this.HorizonCode_Horizon_È(4, new ItemStack((this.ˆáƒ.nextFloat() < 0.1f) ? Blocks.áŒŠÕ : Blocks.Ø­Æ));
                this.ˆÏ­[4] = 0.0f;
            }
        }
        return p_180482_2_;
    }
    
    public void Ø() {
        this.ÂµÈ.HorizonCode_Horizon_È(this.Ý);
        this.ÂµÈ.HorizonCode_Horizon_È(this.Â);
        final ItemStack var1 = this.Çª();
        if (var1 != null && var1.HorizonCode_Horizon_È() == Items.Ó) {
            this.ÂµÈ.HorizonCode_Horizon_È(4, this.Â);
        }
        else {
            this.ÂµÈ.HorizonCode_Horizon_È(4, this.Ý);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_82196_1_, final float p_82196_2_) {
        final EntityArrow var3 = new EntityArrow(this.Ï­Ðƒà, this, p_82196_1_, 1.6f, 14 - this.Ï­Ðƒà.ŠÂµà().HorizonCode_Horizon_È() * 4);
        final int var4 = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Æ.ŒÏ, this.Çª());
        final int var5 = EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Šáƒ.ŒÏ, this.Çª());
        var3.Â(p_82196_2_ * 2.0f + this.ˆáƒ.nextGaussian() * 0.25 + this.Ï­Ðƒà.ŠÂµà().HorizonCode_Horizon_È() * 0.11f);
        if (var4 > 0) {
            var3.Â(var3.à() + var4 * 0.5 + 0.5);
        }
        if (var5 > 0) {
            var3.HorizonCode_Horizon_È(var5);
        }
        if (EnchantmentHelper.HorizonCode_Horizon_È(Enchantment.Ï­Ðƒà.ŒÏ, this.Çª()) > 0 || this.ÇŽÅ() == 1) {
            var3.Âµá€(100);
        }
        this.HorizonCode_Horizon_È("random.bow", 1.0f, 1.0f / (this.ˆÐƒØ().nextFloat() * 0.4f + 0.8f));
        this.Ï­Ðƒà.HorizonCode_Horizon_È(var3);
    }
    
    public int ÇŽÅ() {
        return this.£Ó.HorizonCode_Horizon_È(13);
    }
    
    public void HorizonCode_Horizon_È(final int p_82201_1_) {
        this.£Ó.Â(13, (byte)p_82201_1_);
        this.£Â = (p_82201_1_ == 1);
        if (p_82201_1_ == 1) {
            this.HorizonCode_Horizon_È(0.72f, 2.535f);
        }
        else {
            this.HorizonCode_Horizon_È(0.6f, 1.95f);
        }
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        if (tagCompund.Â("SkeletonType", 99)) {
            final byte var2 = tagCompund.Ø­áŒŠá("SkeletonType");
            this.HorizonCode_Horizon_È((int)var2);
        }
        this.Ø();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("SkeletonType", (byte)this.ÇŽÅ());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int slotIn, final ItemStack itemStackIn) {
        super.HorizonCode_Horizon_È(slotIn, itemStackIn);
        if (!this.Ï­Ðƒà.ŠÄ && slotIn == 0) {
            this.Ø();
        }
    }
    
    @Override
    public float Ðƒáƒ() {
        return (this.ÇŽÅ() == 1) ? super.Ðƒáƒ() : 1.74f;
    }
    
    @Override
    public double Ï­Ï­Ï() {
        return super.Ï­Ï­Ï() - 0.5;
    }
}
