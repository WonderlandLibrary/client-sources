package HORIZON-6-0-SKIDPROTECTION;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob
{
    private static final String HorizonCode_Horizon_È = "CL_00001650";
    
    public EntitySnowman(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.7f, 1.9f);
        ((PathNavigateGround)this.Š()).HorizonCode_Horizon_È(true);
        this.ÂµÈ.HorizonCode_Horizon_È(1, new EntityAIArrowAttack(this, 1.25, 20, 10.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(2, new EntityAIWander(this, 1.0));
        this.ÂµÈ.HorizonCode_Horizon_È(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.ÂµÈ.HorizonCode_Horizon_È(4, new EntityAILookIdle(this));
        this.á.HorizonCode_Horizon_È(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, true, false, IMob.a_));
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(4.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.20000000298023224);
    }
    
    @Override
    public void ˆÏ­() {
        super.ˆÏ­();
        if (!this.Ï­Ðƒà.ŠÄ) {
            int var1 = MathHelper.Ý(this.ŒÏ);
            int var2 = MathHelper.Ý(this.Çªà¢);
            int var3 = MathHelper.Ý(this.Ê);
            if (this.áŒŠ()) {
                this.HorizonCode_Horizon_È(DamageSource.Ó, 1.0f);
            }
            if (this.Ï­Ðƒà.Ý(new BlockPos(var1, 0, var3)).HorizonCode_Horizon_È(new BlockPos(var1, var2, var3)) > 1.0f) {
                this.HorizonCode_Horizon_È(DamageSource.Ý, 1.0f);
            }
            for (int var4 = 0; var4 < 4; ++var4) {
                var1 = MathHelper.Ý(this.ŒÏ + (var4 % 2 * 2 - 1) * 0.25f);
                var2 = MathHelper.Ý(this.Çªà¢);
                var3 = MathHelper.Ý(this.Ê + (var4 / 2 % 2 * 2 - 1) * 0.25f);
                if (this.Ï­Ðƒà.Â(new BlockPos(var1, var2, var3)).Ý().Ó() == Material.HorizonCode_Horizon_È && this.Ï­Ðƒà.Ý(new BlockPos(var1, 0, var3)).HorizonCode_Horizon_È(new BlockPos(var1, var2, var3)) < 0.8f && Blocks.áŒŠá€.Ø­áŒŠá(this.Ï­Ðƒà, new BlockPos(var1, var2, var3))) {
                    this.Ï­Ðƒà.Â(new BlockPos(var1, var2, var3), Blocks.áŒŠá€.¥à());
                }
            }
        }
    }
    
    @Override
    protected Item_1028566121 áŒŠÕ() {
        return Items.Ñ¢à;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.ˆáƒ.nextInt(16), var4 = 0; var4 < var3; ++var4) {
            this.HorizonCode_Horizon_È(Items.Ñ¢à, 1);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_82196_1_, final float p_82196_2_) {
        final EntitySnowball var3 = new EntitySnowball(this.Ï­Ðƒà, this);
        final double var4 = p_82196_1_.Çªà¢ + p_82196_1_.Ðƒáƒ() - 1.100000023841858;
        final double var5 = p_82196_1_.ŒÏ - this.ŒÏ;
        final double var6 = var4 - var3.Çªà¢;
        final double var7 = p_82196_1_.Ê - this.Ê;
        final float var8 = MathHelper.HorizonCode_Horizon_È(var5 * var5 + var7 * var7) * 0.2f;
        var3.a_(var5, var6 + var8, var7, 1.6f, 12.0f);
        this.HorizonCode_Horizon_È("random.bow", 1.0f, 1.0f / (this.ˆÐƒØ().nextFloat() * 0.4f + 0.8f));
        this.Ï­Ðƒà.HorizonCode_Horizon_È(var3);
    }
    
    @Override
    public float Ðƒáƒ() {
        return 1.7f;
    }
}
