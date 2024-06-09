package HORIZON-6-0-SKIDPROTECTION;

public class ListChunkFactory implements IRenderChunkFactory
{
    @Override
    public RenderChunk HorizonCode_Horizon_È(final World worldIn, final RenderGlobal p_178602_2_, final BlockPos p_178602_3_, final int p_178602_4_) {
        return new ListedRenderChunk(worldIn, p_178602_2_, p_178602_3_, p_178602_4_);
    }
}
