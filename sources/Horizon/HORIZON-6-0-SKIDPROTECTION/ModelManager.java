package HORIZON-6-0-SKIDPROTECTION;

public class ModelManager implements IResourceManagerReloadListener
{
    private IRegistry HorizonCode_Horizon_È;
    private final TextureMap Â;
    private final BlockModelShapes Ý;
    private IBakedModel Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002388";
    
    public ModelManager(final TextureMap p_i46082_1_) {
        this.Â = p_i46082_1_;
        this.Ý = new BlockModelShapes(this);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110549_1_) {
        final ModelBakery var2 = new ModelBakery(p_110549_1_, this.Â, this.Ý);
        this.HorizonCode_Horizon_È = var2.HorizonCode_Horizon_È();
        this.Ø­áŒŠá = (IBakedModel)this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(ModelBakery.HorizonCode_Horizon_È);
        this.Ý.Ý();
    }
    
    public IBakedModel HorizonCode_Horizon_È(final ModelResourceLocation p_174953_1_) {
        if (p_174953_1_ == null) {
            return this.Ø­áŒŠá;
        }
        final IBakedModel var2 = (IBakedModel)this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_174953_1_);
        return (var2 == null) ? this.Ø­áŒŠá : var2;
    }
    
    public IBakedModel HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public TextureMap Â() {
        return this.Â;
    }
    
    public BlockModelShapes Ý() {
        return this.Ý;
    }
}
