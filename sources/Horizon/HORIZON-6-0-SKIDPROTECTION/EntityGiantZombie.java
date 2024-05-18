package HORIZON-6-0-SKIDPROTECTION;

public class EntityGiantZombie extends EntityMob
{
    private static final String Â = "CL_00001690";
    
    public EntityGiantZombie(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(this.áŒŠ * 6.0f, this.£ÂµÄ * 6.0f);
    }
    
    @Override
    public float Ðƒáƒ() {
        return 10.440001f;
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(100.0);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Ø­áŒŠá).HorizonCode_Horizon_È(0.5);
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.Âµá€).HorizonCode_Horizon_È(50.0);
    }
    
    @Override
    public float HorizonCode_Horizon_È(final BlockPos p_180484_1_) {
        return this.Ï­Ðƒà.£à(p_180484_1_) - 0.5f;
    }
}
