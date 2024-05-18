package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.awt.image.BufferedImage;

public class DynamicTexture extends AbstractTexture
{
    private final int[] Ó;
    private final int à;
    private final int Ø;
    private static final String áŒŠÆ = "CL_00001048";
    
    public DynamicTexture(final BufferedImage p_i1270_1_) {
        this(p_i1270_1_.getWidth(), p_i1270_1_.getHeight());
        p_i1270_1_.getRGB(0, 0, p_i1270_1_.getWidth(), p_i1270_1_.getHeight(), this.Ó, 0, p_i1270_1_.getWidth());
        this.Â();
    }
    
    public DynamicTexture(final int p_i1271_1_, final int p_i1271_2_) {
        this.à = p_i1271_1_;
        this.Ø = p_i1271_2_;
        this.Ó = new int[p_i1271_1_ * p_i1271_2_];
        TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(), p_i1271_1_, p_i1271_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110551_1_) throws IOException {
    }
    
    public void Â() {
        TextureUtil.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(), this.Ó, this.à, this.Ø);
    }
    
    public int[] Ý() {
        return this.Ó;
    }
}
