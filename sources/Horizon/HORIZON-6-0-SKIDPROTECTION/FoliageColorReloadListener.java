package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class FoliageColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Â = "CL_00001077";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/colormap/foliage.png");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110549_1_) {
        try {
            ColorizerFoliage.HorizonCode_Horizon_È(TextureUtil.HorizonCode_Horizon_È(p_110549_1_, FoliageColorReloadListener.HorizonCode_Horizon_È));
        }
        catch (IOException ex) {}
    }
}
