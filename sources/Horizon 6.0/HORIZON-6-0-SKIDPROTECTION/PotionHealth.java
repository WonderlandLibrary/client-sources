package HORIZON-6-0-SKIDPROTECTION;

public class PotionHealth extends Potion
{
    private static final String áƒ = "CL_00001527";
    
    public PotionHealth(final int p_i45898_1_, final ResourceLocation_1975012498 p_i45898_2_, final boolean p_i45898_3_, final int p_i45898_4_) {
        super(p_i45898_1_, p_i45898_2_, p_i45898_3_, p_i45898_4_);
    }
    
    @Override
    public boolean Ý() {
        return true;
    }
    
    @Override
    public boolean Â(final int p_76397_1_, final int p_76397_2_) {
        return p_76397_1_ >= 1;
    }
}
