package HORIZON-6-0-SKIDPROTECTION;

public abstract class EntityWaterMob extends EntityLiving implements IAnimals
{
    private static final String HorizonCode_Horizon_È = "CL_00001653";
    
    public EntityWaterMob(final World worldIn) {
        super(worldIn);
    }
    
    @Override
    public boolean Ø­Ñ¢Ï­Ø­áˆº() {
        return true;
    }
    
    @Override
    public boolean µà() {
        return true;
    }
    
    @Override
    public boolean ÐƒÂ() {
        return this.Ï­Ðƒà.HorizonCode_Horizon_È(this.£É(), this);
    }
    
    @Override
    public int áŒŠÔ() {
        return 120;
    }
    
    @Override
    protected boolean ÂµÂ() {
        return true;
    }
    
    @Override
    protected int Âµá€(final EntityPlayer p_70693_1_) {
        return 1 + this.Ï­Ðƒà.Å.nextInt(3);
    }
    
    @Override
    public void Õ() {
        int var1 = this.ˆÓ();
        super.Õ();
        if (this.Œ() && !this.£ÂµÄ()) {
            --var1;
            this.Ø(var1);
            if (this.ˆÓ() == -20) {
                this.Ø(0);
                this.HorizonCode_Horizon_È(DamageSource.Ó, 2.0f);
            }
        }
        else {
            this.Ø(300);
        }
    }
    
    @Override
    public boolean áˆº() {
        return false;
    }
}
