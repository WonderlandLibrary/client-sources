package HORIZON-6-0-SKIDPROTECTION;

public class EntityCaveSpider extends EntitySpider
{
    private static final String Â = "CL_00001683";
    
    public EntityCaveSpider(final World worldIn) {
        super(worldIn);
        this.HorizonCode_Horizon_È(0.7f, 0.5f);
    }
    
    @Override
    protected void áŒŠà() {
        super.áŒŠà();
        this.HorizonCode_Horizon_È(SharedMonsterAttributes.HorizonCode_Horizon_È).HorizonCode_Horizon_È(12.0);
    }
    
    @Override
    public boolean Å(final Entity p_70652_1_) {
        if (super.Å(p_70652_1_)) {
            if (p_70652_1_ instanceof EntityLivingBase) {
                byte var2 = 0;
                if (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ý) {
                    var2 = 7;
                }
                else if (this.Ï­Ðƒà.ŠÂµà() == EnumDifficulty.Ø­áŒŠá) {
                    var2 = 15;
                }
                if (var2 > 0) {
                    ((EntityLivingBase)p_70652_1_).HorizonCode_Horizon_È(new PotionEffect(Potion.µÕ.É, var2 * 20, 0));
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public IEntityLivingData HorizonCode_Horizon_È(final DifficultyInstance p_180482_1_, final IEntityLivingData p_180482_2_) {
        return p_180482_2_;
    }
    
    @Override
    public float Ðƒáƒ() {
        return 0.45f;
    }
}
