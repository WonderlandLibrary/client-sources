package HORIZON-6-0-SKIDPROTECTION;

public class PlayerItemRenderer
{
    private int HorizonCode_Horizon_È;
    private float Â;
    private ModelRenderer Ý;
    
    public PlayerItemRenderer(final int attachTo, final float scaleFactor, final ModelRenderer modelRenderer) {
        this.HorizonCode_Horizon_È = 0;
        this.Â = 0.0f;
        this.Ý = null;
        this.HorizonCode_Horizon_È = attachTo;
        this.Â = scaleFactor;
        this.Ý = modelRenderer;
    }
    
    public ModelRenderer HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    public void HorizonCode_Horizon_È(final ModelBiped modelBiped, final float scale) {
        final ModelRenderer attachModel = PlayerItemModel.HorizonCode_Horizon_È(modelBiped, this.HorizonCode_Horizon_È);
        if (attachModel != null) {
            attachModel.Ý(scale);
        }
        this.Ý.HorizonCode_Horizon_È(scale * this.Â);
    }
}
