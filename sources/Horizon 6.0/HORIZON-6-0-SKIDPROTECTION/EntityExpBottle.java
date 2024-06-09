package HORIZON-6-0-SKIDPROTECTION;

public class EntityExpBottle extends EntityThrowable
{
    private static final String Ý = "CL_00001726";
    
    public EntityExpBottle(final World worldIn) {
        super(worldIn);
    }
    
    public EntityExpBottle(final World worldIn, final EntityLivingBase p_i1786_2_) {
        super(worldIn, p_i1786_2_);
    }
    
    public EntityExpBottle(final World worldIn, final double p_i1787_2_, final double p_i1787_4_, final double p_i1787_6_) {
        super(worldIn, p_i1787_2_, p_i1787_4_, p_i1787_6_);
    }
    
    @Override
    protected float à() {
        return 0.07f;
    }
    
    @Override
    protected float Ø() {
        return 0.7f;
    }
    
    @Override
    protected float áŒŠÆ() {
        return -20.0f;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final MovingObjectPosition p_70184_1_) {
        if (!this.Ï­Ðƒà.ŠÄ) {
            this.Ï­Ðƒà.Â(2002, new BlockPos(this), 0);
            int var2 = 3 + this.Ï­Ðƒà.Å.nextInt(5) + this.Ï­Ðƒà.Å.nextInt(5);
            while (var2 > 0) {
                final int var3 = EntityXPOrb.HorizonCode_Horizon_È(var2);
                var2 -= var3;
                this.Ï­Ðƒà.HorizonCode_Horizon_È(new EntityXPOrb(this.Ï­Ðƒà, this.ŒÏ, this.Çªà¢, this.Ê, var3));
            }
            this.á€();
        }
    }
}
