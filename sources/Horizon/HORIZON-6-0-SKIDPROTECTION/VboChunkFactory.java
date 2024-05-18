package HORIZON-6-0-SKIDPROTECTION;

public class VboChunkFactory implements IRenderChunkFactory
{
    private static final String HorizonCode_Horizon_È = "CL_00002451";
    
    @Override
    public RenderChunk HorizonCode_Horizon_È(final World worldIn, final RenderGlobal p_178602_2_, final BlockPos p_178602_3_, final int p_178602_4_) {
        return new RenderChunk(worldIn, p_178602_2_, p_178602_3_, p_178602_4_);
    }
}
