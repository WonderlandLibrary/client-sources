package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public abstract class ChunkRenderContainer
{
    private double Ý;
    private double Ø­áŒŠá;
    private double Âµá€;
    protected List HorizonCode_Horizon_È;
    protected boolean Â;
    private static final String Ó = "CL_00002563";
    
    public ChunkRenderContainer() {
        this.HorizonCode_Horizon_È = Lists.newArrayListWithCapacity(17424);
    }
    
    public void HorizonCode_Horizon_È(final double p_178004_1_, final double p_178004_3_, final double p_178004_5_) {
        this.Â = true;
        this.HorizonCode_Horizon_È.clear();
        this.Ý = p_178004_1_;
        this.Ø­áŒŠá = p_178004_3_;
        this.Âµá€ = p_178004_5_;
    }
    
    public void HorizonCode_Horizon_È(final RenderChunk p_178003_1_) {
        final BlockPos var2 = p_178003_1_.áŒŠÆ();
        GlStateManager.Â((float)(var2.HorizonCode_Horizon_È() - this.Ý), (float)(var2.Â() - this.Ø­áŒŠá), (float)(var2.Ý() - this.Âµá€));
    }
    
    public void HorizonCode_Horizon_È(final RenderChunk p_178002_1_, final EnumWorldBlockLayer p_178002_2_) {
        this.HorizonCode_Horizon_È.add(p_178002_1_);
    }
    
    public abstract void HorizonCode_Horizon_È(final EnumWorldBlockLayer p0);
}
