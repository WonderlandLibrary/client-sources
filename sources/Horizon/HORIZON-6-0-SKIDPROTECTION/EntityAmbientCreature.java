package HORIZON-6-0-SKIDPROTECTION;

public abstract class EntityAmbientCreature extends EntityLiving implements IAnimals
{
    private static final String HorizonCode_Horizon_È = "CL_00001636";
    
    public EntityAmbientCreature(final World worldIn) {
        super(worldIn);
    }
    
    @Override
    public boolean ŠÏ­áˆºá() {
        return false;
    }
    
    @Override
    protected boolean Ø­áŒŠá(final EntityPlayer p_70085_1_) {
        return false;
    }
}
