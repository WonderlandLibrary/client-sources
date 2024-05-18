package HORIZON-6-0-SKIDPROTECTION;

public class EntityChicken extends EntityAnimal
{
    public float ŒÂ;
    public float Ï­Ï;
    public float ŠØ;
    public float ˆÐƒØ;
    public float Çªà;
    public int ¥Å;
    public boolean Œáƒ;
    private static final String Œá = "CL_00001639";
    
    public EntityChicken(final World worldIn) {
        super(worldIn);
        this.Çªà = 1.0f;
        this.HorizonCode_Horizon_È(0.4f, 0.7f);
        this.¥Å = this.ˆáƒ.nextInt(6000) + 6000;
        this.ÂµÈ.HorizonCode_Horizon_È(0, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAIPanic(this, 1.4));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIMate(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAITempt(this, 1.0, Items.¥à, false));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIFollowParent(this, 1.1));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAILookIdle(this));
    }
    
    @Override
    public float Ðƒáƒ() {
        return this.£ÂµÄ;
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(4.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.25);
    }
    
    @Override
    public void ˆÏ­() {
        super.ˆÏ­();
        this.ˆÐƒØ = this.ŒÂ;
        this.ŠØ = this.Ï­Ï;
        this.Ï­Ï += (float)((this.ŠÂµà ? -1 : 4) * 0.3);
        this.Ï­Ï = MathHelper.HorizonCode_Horizon_È(this.Ï­Ï, 0.0f, 1.0f);
        if (!this.ŠÂµà && this.Çªà < 1.0f) {
            this.Çªà = 1.0f;
        }
        this.Çªà *= 0.9;
        if (!this.ŠÂµà && this.ˆá < 0.0) {
            this.ˆá *= 0.6;
        }
        this.ŒÂ += this.Çªà * 2.0f;
        if (!this.Ï­Ðƒà.ŠÄ && !this.h_() && !this.ÐƒÇŽà() && --this.¥Å <= 0) {
            this.HorizonCode_Horizon_È("mob.chicken.plop", 1.0f, (this.ˆáƒ.nextFloat() - this.ˆáƒ.nextFloat()) * 0.2f + 1.0f);
            this.HorizonCode_Horizon_È(Items.¥É, 1);
            this.¥Å = this.ˆáƒ.nextInt(6000) + 6000;
        }
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.chicken.say";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.chicken.hurt";
    }
    
    @Override
    protected String µÊ() {
        return "mob.chicken.hurt";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.chicken.step", 0.15f, 1.0f);
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.ÇŽÕ;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.ˆáƒ.nextInt(3) + this.ˆáƒ.nextInt(1 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(Items.ÇŽÕ, 1);
        }
        if (this.ˆÏ()) {
            this.HorizonCode_Horizon_È(Items.ˆÅ, 1);
        }
        else {
            this.HorizonCode_Horizon_È(Items.ˆÈ, 1);
        }
    }
    
    public EntityChicken Â(final EntityAgeable p_90011_1_) {
        return new EntityChicken(this.Ï­Ðƒà);
    }
    
    @Override
    public boolean Ø­áŒŠá(final ItemStack p_70877_1_) {
        return p_70877_1_ != null && p_70877_1_.HorizonCode_Horizon_È() == Items.¥à;
    }
    
    @Override
    public void Â(final NBTTagCompound tagCompund) {
        super.Â(tagCompund);
        this.Œáƒ = tagCompund.£á("IsChickenJockey");
        if (tagCompund.Ý("EggLayTime")) {
            this.¥Å = tagCompund.Ó("EggLayTime");
        }
    }
    
    @Override
    protected int Âµá€(final EntityPlayer p_70693_1_) {
        return this.ÐƒÇŽà() ? 10 : super.Âµá€(p_70693_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
        super.HorizonCode_Horizon_È(tagCompound);
        tagCompound.HorizonCode_Horizon_È("IsChickenJockey", this.Œáƒ);
        tagCompound.HorizonCode_Horizon_È("EggLayTime", this.¥Å);
    }
    
    @Override
    protected boolean ÂµÂ() {
        return this.ÐƒÇŽà() && this.µÕ == null;
    }
    
    @Override
    public void ˆÉ() {
        super.ˆÉ();
        final float var1 = MathHelper.HorizonCode_Horizon_È(this.¥É * 3.1415927f / 180.0f);
        final float var2 = MathHelper.Â(this.¥É * 3.1415927f / 180.0f);
        final float var3 = 0.1f;
        final float var4 = 0.0f;
        this.µÕ.Ý(this.ŒÏ + var3 * var1, this.Çªà¢ + this.£ÂµÄ * 0.5f + this.µÕ.Ï­Ï­Ï() + var4, this.Ê - var3 * var2);
        if (this.µÕ instanceof EntityLivingBase) {
            ((EntityLivingBase)this.µÕ).¥É = this.¥É;
        }
    }
    
    public boolean ÐƒÇŽà() {
        return this.Œáƒ;
    }
    
    public void á(final boolean p_152117_1_) {
        this.Œáƒ = p_152117_1_;
    }
}
