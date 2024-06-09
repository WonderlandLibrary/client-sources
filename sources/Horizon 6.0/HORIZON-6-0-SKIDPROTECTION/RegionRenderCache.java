package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;

public class RegionRenderCache extends ChunkCache
{
    private static final IBlockState Ó;
    private final BlockPos à;
    private int[] Ø;
    private IBlockState[] áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00002565";
    
    static {
        Ó = Blocks.Â.¥à();
    }
    
    public RegionRenderCache(final World worldIn, final BlockPos p_i46273_2_, final BlockPos p_i46273_3_, final int p_i46273_4_) {
        super(worldIn, p_i46273_2_, p_i46273_3_, p_i46273_4_);
        this.à = p_i46273_2_.Â(new Vec3i(p_i46273_4_, p_i46273_4_, p_i46273_4_));
        final boolean var5 = true;
        Arrays.fill(this.Ø = new int[8000], -1);
        this.áŒŠÆ = new IBlockState[8000];
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final BlockPos pos) {
        final int var2 = (pos.HorizonCode_Horizon_È() >> 4) - this.HorizonCode_Horizon_È;
        final int var3 = (pos.Ý() >> 4) - this.Â;
        return this.Ý[var2][var3].HorizonCode_Horizon_È(pos, Chunk.HorizonCode_Horizon_È.Â);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final BlockPos p_175626_1_, final int p_175626_2_) {
        final int var3 = this.Ó(p_175626_1_);
        int var4 = this.Ø[var3];
        if (var4 == -1) {
            var4 = super.HorizonCode_Horizon_È(p_175626_1_, p_175626_2_);
            this.Ø[var3] = var4;
        }
        return var4;
    }
    
    @Override
    public IBlockState Â(final BlockPos pos) {
        final int var2 = this.Ó(pos);
        IBlockState var3 = this.áŒŠÆ[var2];
        if (var3 == null) {
            var3 = this.Âµá€(pos);
            this.áŒŠÆ[var2] = var3;
        }
        return var3;
    }
    
    private IBlockState Âµá€(final BlockPos p_175631_1_) {
        if (p_175631_1_.Â() >= 0 && p_175631_1_.Â() < 256) {
            final int var2 = (p_175631_1_.HorizonCode_Horizon_È() >> 4) - this.HorizonCode_Horizon_È;
            final int var3 = (p_175631_1_.Ý() >> 4) - this.Â;
            return this.Ý[var2][var3].Ø­áŒŠá(p_175631_1_);
        }
        return RegionRenderCache.Ó;
    }
    
    private int Ó(final BlockPos p_175630_1_) {
        final int var2 = p_175630_1_.HorizonCode_Horizon_È() - this.à.HorizonCode_Horizon_È();
        final int var3 = p_175630_1_.Â() - this.à.Â();
        final int var4 = p_175630_1_.Ý() - this.à.Ý();
        return var2 * 400 + var4 * 20 + var3;
    }
}
