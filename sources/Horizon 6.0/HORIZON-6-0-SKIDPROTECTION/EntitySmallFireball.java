package HORIZON-6-0-SKIDPROTECTION;

public class EntitySmallFireball extends EntityFireball
{
    private static final String Âµá€ = "CL_00001721";
    
    public EntitySmallFireball(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.3125f, 0.3125f);
    }
    
    public EntitySmallFireball(final World worldIn, final EntityLivingBase p_i1771_2_, final double p_i1771_3_, final double p_i1771_5_, final double p_i1771_7_) {
        super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
        this.HorizonCode_Horizon_È(0.3125f, 0.3125f);
    }
    
    public EntitySmallFireball(final World worldIn, final double p_i1772_2_, final double p_i1772_4_, final double p_i1772_6_, final double p_i1772_8_, final double p_i1772_10_, final double p_i1772_12_) {
        super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
        this.HorizonCode_Horizon_È(0.3125f, 0.3125f);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final MovingObjectPosition p_70227_1_) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            if (p_70227_1_.Ø­áŒŠá != null) {
                final boolean var2 = p_70227_1_.Ø­áŒŠá.HorizonCode_Horizon_È(DamageSource.HorizonCode_Horizon_È(this, this.HorizonCode_Horizon_È), 5.0f);
                if (var2) {
                    this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_70227_1_.Ø­áŒŠá);
                    if (!p_70227_1_.Ø­áŒŠá.ˆáŠ()) {
                        p_70227_1_.Ø­áŒŠá.Âµá€(5);
                    }
                }
            }
            else {
                boolean var2 = true;
                if (this.HorizonCode_Horizon_È != null && this.HorizonCode_Horizon_È instanceof EntityLiving) {
                    var2 = this.Ï­Ðƒà.Çªà¢().Â("mobGriefing");
                }
                if (var2) {
                    final BlockPos var3 = p_70227_1_.HorizonCode_Horizon_È().HorizonCode_Horizon_È(p_70227_1_.Â);
                    if (this.Ï­Ðƒà.Ø­áŒŠá(var3)) {
                        this.Ï­Ðƒà.Â(var3, Blocks.Ô.¥à());
                    }
                }
            }
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
}
