package HORIZON-6-0-SKIDPROTECTION;

public class RenderPotion extends RenderSnowball
{
    private static final String Âµá€ = "CL_00002430";
    
    public RenderPotion(final RenderManager p_i46136_1_, final RenderItem p_i46136_2_) {
        super(p_i46136_1_, Items.µÂ, p_i46136_2_);
    }
    
    public ItemStack HorizonCode_Horizon_È(final EntityPotion p_177085_1_) {
        return new ItemStack(this.HorizonCode_Horizon_È, 1, p_177085_1_.ˆÏ­());
    }
    
    @Override
    public ItemStack Ø­áŒŠá(final Entity p_177082_1_) {
        return this.HorizonCode_Horizon_È((EntityPotion)p_177082_1_);
    }
}
