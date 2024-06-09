package HORIZON-6-0-SKIDPROTECTION;

public class EntityMinecartEmpty extends EntityMinecart
{
    private static final String HorizonCode_Horizon_È = "CL_00001677";
    
    public EntityMinecartEmpty(final World worldIn) {
        super(worldIn);
    }
    
    public EntityMinecartEmpty(final World worldIn, final double p_i1723_2_, final double p_i1723_4_, final double p_i1723_6_) {
        super(worldIn, p_i1723_2_, p_i1723_4_, p_i1723_6_);
    }
    
    @Override
    public boolean b_(final EntityPlayer playerIn) {
        if (this.µÕ != null && this.µÕ instanceof EntityPlayer && this.µÕ != playerIn) {
            return true;
        }
        if (this.µÕ != null && this.µÕ != playerIn) {
            return false;
        }
        if (!this.Ï­Ðƒà.ŠÄ) {
            playerIn.HorizonCode_Horizon_È(this);
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_96095_1_, final int p_96095_2_, final int p_96095_3_, final boolean p_96095_4_) {
        if (p_96095_4_) {
            if (this.µÕ != null) {
                this.µÕ.HorizonCode_Horizon_È((Entity)null);
            }
            if (this.Ø­à() == 0) {
                this.Ý(-this.µÕ());
                this.Â(10);
                this.Ý(50.0f);
                this.Ï();
            }
        }
    }
    
    @Override
    public HorizonCode_Horizon_È à() {
        return EntityMinecart.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
    }
}
