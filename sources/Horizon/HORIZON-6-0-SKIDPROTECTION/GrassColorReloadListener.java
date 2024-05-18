package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GrassColorReloadListener implements IResourceManagerReloadListener
{
    private static final ResourceLocation_1975012498 HorizonCode_Horizon_È;
    private static final String Â = "CL_00001078";
    
    static {
        HorizonCode_Horizon_È = new ResourceLocation_1975012498("textures/colormap/grass.png");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110549_1_) {
        try {
            ColorizerGrass.HorizonCode_Horizon_È(TextureUtil.HorizonCode_Horizon_È(p_110549_1_, GrassColorReloadListener.HorizonCode_Horizon_È));
        }
        catch (IOException ex) {}
    }
}
