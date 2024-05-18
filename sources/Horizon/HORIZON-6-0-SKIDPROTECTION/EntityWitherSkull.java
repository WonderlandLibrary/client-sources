package HORIZON-6-0-SKIDPROTECTION;

public class EntityWitherSkull extends EntityFireball
{
    private static final String Âµá€ = "CL_00001728";
    
    public EntityWitherSkull(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.3125f, 0.3125f);
    }
    
    public EntityWitherSkull(final World worldIn, final EntityLivingBase p_i1794_2_, final double p_i1794_3_, final double p_i1794_5_, final double p_i1794_7_) {
        super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
        this.HorizonCode_Horizon_È(0.3125f, 0.3125f);
    }
    
    @Override
    protected float à() {
        return this.Ø() ? 0.73f : super.à();
    }
    
    public EntityWitherSkull(final World worldIn, final double p_i1795_2_, final double p_i1795_4_, final double p_i1795_6_, final double p_i1795_8_, final double p_i1795_10_, final double p_i1795_12_) {
        super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
        this.HorizonCode_Horizon_È(0.3125f, 0.3125f);
    }
    
    @Override
    public boolean ˆÏ() {
        return false;
    }
    
    @Override
    public float HorizonCode_Horizon_È(final Explosion p_180428_1_, final World worldIn, final BlockPos p_180428_3_, final IBlockState p_180428_4_) {
        float var5 = super.HorizonCode_Horizon_È(p_180428_1_, worldIn, p_180428_3_, p_180428_4_);
        if (this.Ø() && p_180428_4_.Ý() != Blocks.áŒŠÆ && p_180428_4_.Ý() != Blocks.Ï­Ä && p_180428_4_.Ý() != Blocks.¥áŠ && p_180428_4_.Ý() != Blocks.ŠÑ¢Ó) {
            var5 = Math.min(0.8f, var5);
        }
        return var5;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final MovingObjectPosition p_70227_1_) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            if (p_70227_1_.Ø­áŒŠá != null) {
                if (this.HorizonCode_Horizon_È != null) {
                    if (p_70227_1_.Ø­áŒŠá.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È), 8.0f)) {
                        if (!p_70227_1_.Ø­áŒŠá.Œ()) {
                            this.HorizonCode_Horizon_È.a_(5.0f);
                        }
                        else {
                            this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_70227_1_.Ø­áŒŠá);
                        }
                    }
                }
                else {
                    p_70227_1_.Ø­áŒŠá.HorizonCode_Horizon_È(DamageSource.á, 5.0f);
                }
                if (p_70227_1_.Ø­áŒŠá instanceof EntityLivingBase) {
                    byte var2 = 0;
                    if (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ý) {
                        var2 = 10;
                    }
                    else if (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá) {
                        var2 = 40;
                    }
                    if (var2 > 0) {
                        ((EntityLivingBase)p_70227_1_.Ø­áŒŠá).HorizonCode_Horizon_È(new PotionEffect(Potion.Æ.É, 20 * var2, 1));
                    }
                }
            }
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.ŒÏ, this.Çªà¢, this.Ê, 1.0f, false, this.Ï­Ðƒà.Çªà¢().Â("mobGriefing"));
            this.á€();
        }
    }
    
    @Override
    public boolean Ô() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final DamageSource source, final float amount) {
        return false;
    }
    
    @Override
    protected void ÂµÈ() {
        this.£Ó.HorizonCode_Horizon_È(10, (Object)(byte)0);
    }
    
    public boolean Ø() {
        return this.£Ó.HorizonCode_Horizon_È(10) == 1;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_82343_1_) {
        this.£Ó.Â(10, (byte)(byte)(p_82343_1_ ? 1 : 0));
    }
}
