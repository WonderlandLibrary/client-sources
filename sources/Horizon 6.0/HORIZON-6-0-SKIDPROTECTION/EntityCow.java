package HORIZON-6-0-SKIDPROTECTION;

public class EntityCow extends EntityAnimal
{
    private static final String ŒÂ = "CL_00001640";
    
    public EntityCow(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.9f, 1.3f);
        ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
        this.ÂµÈ.HorizonCode_Horizon_È(0, new EntityAISwimming(this));
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAIPanic(this, 2.0));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIMate(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAITempt(this, 1.25, Items.Âµà, false));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAIFollowParent(this, 1.25));
        this.ÂµÈ.HorizonCode_Horizon_È(5, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(7, new EntityAILookIdle(this));
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(10.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.20000000298023224);
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "mob.cow.say";
    }
    
    @Override
    protected String ¥áŠ() {
        return "mob.cow.hurt";
    }
    
    @Override
    protected String µÊ() {
        return "mob.cow.hurt";
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BlockPos p_180429_1_, final Block p_180429_2_) {
        this.HorizonCode_Horizon_È("mob.cow.step", 0.15f, 1.0f);
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
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.ˆáƒ.nextInt(3) + this.ˆáƒ.nextInt(1 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(Items.£áŒŠá, 1);
        }
        for (int var3 = this.ˆáƒ.nextInt(3) + 1 + this.ˆáƒ.nextInt(1 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            if (this.ˆÏ()) {
                this.HorizonCode_Horizon_È(Items.ÇŽÄ, 1);
            }
            else {
                this.HorizonCode_Horizon_È(Items.Çª, 1);
            }
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        final ItemStack var2 = p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var2 != null && var2.HorizonCode_Horizon_È() == Items.áŒŠáŠ && !p_70085_1_.áˆºáˆºáŠ.Ø­áŒŠá) {
            if (var2.Â-- == 1) {
                p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý, new ItemStack(Items.áˆº));
            }
            else if (!p_70085_1_.Ø­Ñ¢Ï­Ø­áˆº.HorizonCode_Horizon_È(new ItemStack(Items.áˆº))) {
                p_70085_1_.HorizonCode_Horizon_È(new ItemStack(Items.áˆº, 1, 0), false);
            }
            return true;
        }
        return super.Ø­áŒŠá(p_70085_1_);
    }
    
    public EntityCow Â(final EntityAgeable p_90011_1_) {
        return new EntityCow(this.Ï­Ðƒà);
    }
    
    @Override
    public float Ðƒáƒ() {
        return this.£ÂµÄ;
    }
}
