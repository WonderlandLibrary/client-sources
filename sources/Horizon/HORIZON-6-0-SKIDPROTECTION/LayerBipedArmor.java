package HORIZON-6-0-SKIDPROTECTION;

public class LayerBipedArmor extends LayerArmorBase
{
    private static final String HorizonCode_Horizon_È = "CL_00002417";
    
    public LayerBipedArmor(final RendererLivingEntity p_i46116_1_) {
        super(p_i46116_1_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È() {
        this.Ý = new ModelBiped(0.5f);
        this.Ø­áŒŠá = new ModelBiped(1.0f);
    }
    
    protected void HorizonCode_Horizon_È(final ModelBiped p_177195_1_, final int p_177195_2_) {
        this.HorizonCode_Horizon_È(p_177195_1_);
        switch (p_177195_2_) {
            case 1: {
                p_177195_1_.£à.áˆºÑ¢Õ = true;
                p_177195_1_.µà.áˆºÑ¢Õ = true;
                break;
            }
            case 2: {
                p_177195_1_.ˆÏ­.áˆºÑ¢Õ = true;
                p_177195_1_.£à.áˆºÑ¢Õ = true;
                p_177195_1_.µà.áˆºÑ¢Õ = true;
                break;
            }
            case 3: {
                p_177195_1_.ˆÏ­.áˆºÑ¢Õ = true;
                p_177195_1_.£á.áˆºÑ¢Õ = true;
                p_177195_1_.Å.áˆºÑ¢Õ = true;
                break;
            }
            case 4: {
                p_177195_1_.ÂµÈ.áˆºÑ¢Õ = true;
                p_177195_1_.á.áˆºÑ¢Õ = true;
                break;
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final ModelBiped p_177194_1_) {
        p_177194_1_.HorizonCode_Horizon_È(false);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final ModelBase p_177179_1_, final int p_177179_2_) {
        this.HorizonCode_Horizon_È((ModelBiped)p_177179_1_, p_177179_2_);
    }
}
