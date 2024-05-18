package HORIZON-6-0-SKIDPROTECTION;

public class PotionHealthBoost extends Potion
{
    private static final String áƒ = "CL_00001526";
    
    public PotionHealthBoost(final int p_i45899_1_, final ResourceLocation_1975012498 p_i45899_2_, final boolean p_i45899_3_, final int p_i45899_4_) {
        super(p_i45899_1_, p_i45899_2_, p_i45899_3_, p_i45899_4_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_111187_1_, final BaseAttributeMap p_111187_2_, final int p_111187_3_) {
        super.HorizonCode_Horizon_È(p_111187_1_, p_111187_2_, p_111187_3_);
        if (p_111187_1_.Ï­Ä() > p_111187_1_.ÇŽÊ()) {
            p_111187_1_.áˆºÑ¢Õ(p_111187_1_.ÇŽÊ());
        }
    }
}
