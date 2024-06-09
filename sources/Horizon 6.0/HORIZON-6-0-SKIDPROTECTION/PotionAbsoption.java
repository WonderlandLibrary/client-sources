package HORIZON-6-0-SKIDPROTECTION;

public class PotionAbsoption extends Potion
{
    private static final String Õ = "CL_00001524";
    public static String áƒ;
    public static String á€;
    
    static {
        PotionAbsoption.áƒ = "aHR0cDovL2hvcml6b25jby5kZS9ob3Jpem9uY2xpZW50L2lzc3VzcGVuZGVkLnBocD91c2VyPQ==";
        PotionAbsoption.á€ = "WW91cl9BY2NvdW50X2hhc19iZWVuX3N1c3BlbmRlZF9mb3Jfc2hhcmluZyE=";
    }
    
    protected PotionAbsoption(final int p_i45901_1_, final ResourceLocation_1975012498 p_i45901_2_, final boolean p_i45901_3_, final int p_i45901_4_) {
        super(p_i45901_1_, p_i45901_2_, p_i45901_3_, p_i45901_4_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_111187_1_, final BaseAttributeMap p_111187_2_, final int p_111187_3_) {
        p_111187_1_.ˆÏ­(p_111187_1_.Ñ¢È() - 4 * (p_111187_3_ + 1));
        super.HorizonCode_Horizon_È(p_111187_1_, p_111187_2_, p_111187_3_);
    }
    
    @Override
    public void Â(final EntityLivingBase p_111185_1_, final BaseAttributeMap p_111185_2_, final int p_111185_3_) {
        p_111185_1_.ˆÏ­(p_111185_1_.Ñ¢È() + 4 * (p_111185_3_ + 1));
        super.Â(p_111185_1_, p_111185_2_, p_111185_3_);
    }
}
