package HORIZON-6-0-SKIDPROTECTION;

public class EntityPig extends EntityAnimal
{
    private final EntityAIControlledByPlayer ŒÂ;
    private static final String Ï­Ï = "CL_00001647";
    
    public EntityPig(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.9f, 0.9f);
        ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
        this.ÂµÈ.HorizonCode_Horizon_È(0, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAIPanic(this, 1.25));
        this.ÂµÈ.HorizonCode_Horizon_È(2, this.ŒÂ = new EntityAIControlledByPlayer(this, 0.3f));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAIMate(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAITempt(this, 1.2, Items.ŠÑ¢Ó, false));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAITempt(this, 1.2, Items.¥áŒŠà, false));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIFollowParent(this, 1.1));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(8, new EntityAILookIdle(this));
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(10.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.25);
    }
    
    @Override
    public boolean ÇŽÄ() {
        final ItemStack var1 = ((EntityPlayer)this.µÕ).Çª();
        return var1 != null && var1.HorizonCode_Horizon_È() == Items.ŠÑ¢Ó;
    }
    
    @Override
    protected void ÂµÈ() {
        super.ÂµÈ();
        this.£Ó.HorizonCode_Horizon_È(16, (Object)(byte)0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("Saddle", this.ÐƒÇŽà());
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.á(tagCompund.£á("Saddle"));
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.pig.say";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.pig.say";
    }
    
    @Override
    protected String µÊ() {
        return "mob.pig.death";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.pig.step", 0.15f, 1.0f);
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        if (super.Ø­áŒŠá(p_70085_1_)) {
            return true;
        }
        if (this.ÐƒÇŽà() && !this.Ï­Ðƒà.ŠÄ && (this.µÕ == null || this.µÕ == p_70085_1_)) {
            p_70085_1_.HorizonCode_Horizon_È((Entity)this);
            return true;
        }
        return false;
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return this.ˆÏ() ? Items.£Ó : Items.£Â;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.ˆáƒ.nextInt(3) + 1 + this.ˆáƒ.nextInt(1 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            if (this.ˆÏ()) {
                this.HorizonCode_Horizon_È(Items.£Ó, 1);
            }
            else {
                this.HorizonCode_Horizon_È(Items.£Â, 1);
            }
        }
        if (this.ÐƒÇŽà()) {
            this.HorizonCode_Horizon_È(Items.Û, 1);
        }
    }
    
    public boolean ÐƒÇŽà() {
        return (this.£Ó.HorizonCode_Horizon_È(16) & 0x1) != 0x0;
    }
    
    public void á(final boolean p_70900_1_) {
        if (p_70900_1_) {
            this.£Ó.Â(16, (byte)1);
        }
        else {
            this.£Ó.Â(16, (byte)0);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLightningBolt lightningBolt) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            final EntityPigZombie var2 = new EntityPigZombie(this.Ï­Ðƒà);
            var2.HorizonCode_Horizon_È(0, new ItemStack(Items.ŒÏ));
            var2.Â(this.ŒÏ, this.Çªà¢, this.Ê, this.É, this.áƒ);
            this.Ï­Ðƒà.HorizonCode_Horizon_È(var2);
            this.á€();
        }
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
        super.Ø­áŒŠá(distance, damageMultiplier);
        if (distance > 5.0f && this.µÕ instanceof EntityPlayer) {
            ((EntityPlayer)this.µÕ).HorizonCode_Horizon_È(AchievementList.µÕ);
        }
    }
    
    public EntityPig Â(final EntityAgeable p_90011_1_) {
        return new EntityPig(this.Ï­Ðƒà);
    }
    
    @Override
    public boolean Ø­áŒŠá(final ItemStack p_70877_1_) {
        return p_70877_1_ != null && p_70877_1_.HorizonCode_Horizon_È() == Items.¥áŒŠà;
    }
    
    public EntityAIControlledByPlayer ¥Ê() {
        return this.ŒÂ;
    }
}
