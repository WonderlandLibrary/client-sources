package HORIZON-6-0-SKIDPROTECTION;

public class BlockRendererDispatcher implements IResourceManagerReloadListener
{
    private BlockModelShapes HorizonCode_Horizon_È;
    private final GameSettings Â;
    private final BlockModelRenderer Ý;
    private final ChestRenderer Ø­áŒŠá;
    private final BlockFluidRenderer Âµá€;
    private static final String Ó = "CL_00002520";
    
    public BlockRendererDispatcher(final BlockModelShapes p_i46237_1_, final GameSettings p_i46237_2_) {
        this.Ý = new BlockModelRenderer();
        this.Ø­áŒŠá = new ChestRenderer();
        this.Âµá€ = new BlockFluidRenderer();
        this.HorizonCode_Horizon_È = p_i46237_1_;
        this.Â = p_i46237_2_;
    }
    
    public BlockModelShapes HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(IBlockState p_175020_1_, final BlockPos p_175020_2_, final TextureAtlasSprite p_175020_3_, final IBlockAccess p_175020_4_) {
        final Block var5 = p_175020_1_.Ý();
        final int var6 = var5.ÂµÈ();
        if (var6 == 3) {
            p_175020_1_ = var5.HorizonCode_Horizon_È(p_175020_1_, p_175020_4_, p_175020_2_);
            final IBakedModel var7 = this.HorizonCode_Horizon_È.Â(p_175020_1_);
            final IBakedModel var8 = new SimpleBakedModel.HorizonCode_Horizon_È(var7, p_175020_3_).HorizonCode_Horizon_È();
            this.Ý.HorizonCode_Horizon_È(p_175020_4_, var8, p_175020_1_, p_175020_2_, Tessellator.HorizonCode_Horizon_È().Ý());
        }
    }
    
    public boolean HorizonCode_Horizon_È(final IBlockState p_175018_1_, final BlockPos p_175018_2_, final IBlockAccess p_175018_3_, final WorldRenderer p_175018_4_) {
        try {
            final int var5 = p_175018_1_.Ý().ÂµÈ();
            if (var5 == -1) {
                return false;
            }
            switch (var5) {
                case 1: {
                    return this.Âµá€.HorizonCode_Horizon_È(p_175018_3_, p_175018_1_, p_175018_2_, p_175018_4_);
                }
                case 2: {
                    return false;
                }
                case 3: {
                    final IBakedModel var6 = this.HorizonCode_Horizon_È(p_175018_1_, p_175018_3_, p_175018_2_);
                    return this.Ý.HorizonCode_Horizon_È(p_175018_3_, var6, p_175018_1_, p_175018_2_, p_175018_4_);
                }
                default: {
                    return false;
                }
            }
        }
        catch (Throwable var8) {
            final CrashReport var7 = CrashReport.HorizonCode_Horizon_È(var8, "Tesselating block in world");
            final CrashReportCategory var9 = var7.HorizonCode_Horizon_È("Block being tesselated");
            CrashReportCategory.HorizonCode_Horizon_È(var9, p_175018_2_, p_175018_1_.Ý(), p_175018_1_.Ý().Ý(p_175018_1_));
            throw new ReportedException(var7);
        }
    }
    
    public BlockModelRenderer Â() {
        return this.Ý;
    }
    
    private IBakedModel HorizonCode_Horizon_È(final IBlockState p_175017_1_, final BlockPos p_175017_2_) {
        IBakedModel var3 = this.HorizonCode_Horizon_È.Â(p_175017_1_);
        if (p_175017_2_ != null && this.Â.£É && var3 instanceof WeightedBakedModel) {
            var3 = ((WeightedBakedModel)var3).HorizonCode_Horizon_È(MathHelper.HorizonCode_Horizon_È(p_175017_2_));
        }
        return var3;
    }
    
    public IBakedModel HorizonCode_Horizon_È(IBlockState p_175022_1_, final IBlockAccess p_175022_2_, final BlockPos p_175022_3_) {
        final Block var4 = p_175022_1_.Ý();
        if (p_175022_2_.s_() != WorldType.Ø) {
            try {
                p_175022_1_ = var4.HorizonCode_Horizon_È(p_175022_1_, p_175022_2_, p_175022_3_);
            }
            catch (Exception ex) {}
        }
        IBakedModel var5 = this.HorizonCode_Horizon_È.Â(p_175022_1_);
        if (p_175022_3_ != null && this.Â.£É && var5 instanceof WeightedBakedModel) {
            var5 = ((WeightedBakedModel)var5).HorizonCode_Horizon_È(MathHelper.HorizonCode_Horizon_È(p_175022_3_));
        }
        return var5;
    }
    
    public void HorizonCode_Horizon_È(final IBlockState p_175016_1_, final float p_175016_2_) {
        final int var3 = p_175016_1_.Ý().ÂµÈ();
        if (var3 != -1) {
            switch (var3) {
                case 2: {
                    this.Ø­áŒŠá.HorizonCode_Horizon_È(p_175016_1_.Ý(), p_175016_2_);
                    break;
                }
                case 3: {
                    final IBakedModel var4 = this.HorizonCode_Horizon_È(p_175016_1_, null);
                    this.Ý.HorizonCode_Horizon_È(var4, p_175016_1_, p_175016_2_, true);
                    break;
                }
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final Block p_175021_1_, final int p_175021_2_) {
        if (p_175021_1_ == null) {
            return false;
        }
        final int var3 = p_175021_1_.ÂµÈ();
        return var3 != 3 && var3 == 2;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IResourceManager p_110549_1_) {
        this.Âµá€.HorizonCode_Horizon_È();
    }
}
