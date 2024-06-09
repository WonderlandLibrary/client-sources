package HORIZON-6-0-SKIDPROTECTION;

public class ListedRenderChunk extends RenderChunk
{
    private final int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00002453";
    
    public ListedRenderChunk(final World worldIn, final RenderGlobal p_i46198_2_, final BlockPos p_i46198_3_, final int p_i46198_4_) {
        super(worldIn, p_i46198_2_, p_i46198_3_, p_i46198_4_);
        this.Ø­áŒŠá = GLAllocation.HorizonCode_Horizon_È(EnumWorldBlockLayer.values().length);
    }
    
    public int HorizonCode_Horizon_È(final EnumWorldBlockLayer p_178600_1_, final CompiledChunk p_178600_2_) {
        return p_178600_2_.Â(p_178600_1_) ? -1 : (this.Ø­áŒŠá + p_178600_1_.ordinal());
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
        GLAllocation.HorizonCode_Horizon_È(this.Ø­áŒŠá, EnumWorldBlockLayer.values().length);
    }
}
