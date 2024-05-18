package HORIZON-6-0-SKIDPROTECTION;

public abstract class EntityGolem extends EntityCreature implements IAnimals
{
    private static final String HorizonCode_Horizon_È = "CL_00001644";
    
    public EntityGolem(final World worldIn) {
        super(worldIn);
    }
    
    @Override
    public void Ø­áŒŠá(final float distance, final float damageMultiplier) {
    }
    
    @Override
    protected String µÐƒáƒ() {
        return "none";
    }
    
    @Override
    protected String ¥áŠ() {
        return "none";
    }
    
    @Override
    protected String µÊ() {
        return "none";
    }
    
    @Override
    public int áŒŠÔ() {
        return 120;
    }
    
    @Override
    protected boolean ÂµÂ() {
        return false;
    }
}
