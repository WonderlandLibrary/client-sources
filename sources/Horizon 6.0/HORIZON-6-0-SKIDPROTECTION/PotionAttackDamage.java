package HORIZON-6-0-SKIDPROTECTION;

public class PotionAttackDamage extends Potion
{
    private static final String áƒ = "CL_00001525";
    
    protected PotionAttackDamage(final int p_i45900_1_, final ResourceLocation_1975012498 p_i45900_2_, final boolean p_i45900_3_, final int p_i45900_4_) {
        super(p_i45900_1_, p_i45900_2_, p_i45900_3_, p_i45900_4_);
    }
    
    @Override
    public double HorizonCode_Horizon_È(final int p_111183_1_, final AttributeModifier p_111183_2_) {
        return (this.É == Potion.Ø­à.É) ? (-0.5f * (p_111183_1_ + 1)) : (1.3 * (p_111183_1_ + 1));
    }
}
