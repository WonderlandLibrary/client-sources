package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.nio.FloatBuffer;
import java.util.concurrent.locks.ReentrantLock;

public class RenderChunk
{
    private World Ø­áŒŠá;
    private final RenderGlobal Âµá€;
    public static int HorizonCode_Horizon_È;
    private BlockPos Ó;
    public CompiledChunk Â;
    private final ReentrantLock à;
    private final ReentrantLock Ø;
    private ChunkCompileTaskGenerator áŒŠÆ;
    private final int áˆºÑ¢Õ;
    private final FloatBuffer ÂµÈ;
    private final VertexBuffer[] á;
    public AxisAlignedBB Ý;
    private int ˆÏ­;
    private boolean £á;
    private static final String Å = "CL_00002452";
    private BlockPos[] £à;
    
    public RenderChunk(final World worldIn, final RenderGlobal renderGlobalIn, final BlockPos blockPosIn, final int indexIn) {
        this.£à = new BlockPos[EnumFacing.à.length];
        this.Â = CompiledChunk.HorizonCode_Horizon_È;
        this.à = new ReentrantLock();
        this.Ø = new ReentrantLock();
        this.áŒŠÆ = null;
        this.ÂµÈ = GLAllocation.Âµá€(16);
        this.á = new VertexBuffer[EnumWorldBlockLayer.values().length];
        this.ˆÏ­ = -1;
        this.£á = true;
        this.Ø­áŒŠá = worldIn;
        this.Âµá€ = renderGlobalIn;
        this.áˆºÑ¢Õ = indexIn;
        if (!blockPosIn.equals(this.áŒŠÆ())) {
            this.HorizonCode_Horizon_È(blockPosIn);
        }
        if (OpenGlHelper.Ó()) {
            for (int var5 = 0; var5 < EnumWorldBlockLayer.values().length; ++var5) {
                this.á[var5] = new VertexBuffer(DefaultVertexFormats.HorizonCode_Horizon_È);
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final int frameIndexIn) {
        if (this.ˆÏ­ == frameIndexIn) {
            return false;
        }
        this.ˆÏ­ = frameIndexIn;
        return true;
    }
    
    public VertexBuffer Â(final int p_178565_1_) {
        return this.á[p_178565_1_];
    }
    
    public void HorizonCode_Horizon_È(final BlockPos p_178576_1_) {
        this.Ø();
        this.Ó = p_178576_1_;
        this.Ý = new AxisAlignedBB(p_178576_1_, p_178576_1_.Â(16, 16, 16));
        this.á();
        for (int i = 0; i < this.£à.length; ++i) {
            this.£à[i] = null;
        }
    }
    
    public void HorizonCode_Horizon_È(final float p_178570_1_, final float p_178570_2_, final float p_178570_3_, final ChunkCompileTaskGenerator p_178570_4_) {
        final CompiledChunk var5 = p_178570_4_.Ý();
        if (var5.Ý() != null && !var5.Â(EnumWorldBlockLayer.Ø­áŒŠá)) {
            final WorldRenderer worldRenderer = p_178570_4_.Ø­áŒŠá().HorizonCode_Horizon_È(EnumWorldBlockLayer.Ø­áŒŠá);
            this.HorizonCode_Horizon_È(worldRenderer, this.Ó);
            worldRenderer.HorizonCode_Horizon_È(var5.Ý());
            this.HorizonCode_Horizon_È(EnumWorldBlockLayer.Ø­áŒŠá, p_178570_1_, p_178570_2_, p_178570_3_, worldRenderer, var5);
        }
    }
    
    public void Â(final float p_178581_1_, final float p_178581_2_, final float p_178581_3_, final ChunkCompileTaskGenerator p_178581_4_) {
        final CompiledChunk var5 = new CompiledChunk();
        final boolean var6 = true;
        final BlockPos var7 = this.Ó;
        final BlockPos var8 = var7.Â(15, 15, 15);
        p_178581_4_.Ó().lock();
        RegionRenderCache var9;
        try {
            if (p_178581_4_.HorizonCode_Horizon_È() != ChunkCompileTaskGenerator.HorizonCode_Horizon_È.Â) {
                return;
            }
            if (this.Ø­áŒŠá == null) {
                return;
            }
            var9 = new RegionRenderCache(this.Ø­áŒŠá, var7.Â(-1, -1, -1), var8.Â(1, 1, 1), 1);
            p_178581_4_.HorizonCode_Horizon_È(var5);
        }
        finally {
            p_178581_4_.Ó().unlock();
        }
        p_178581_4_.Ó().unlock();
        final VisGraph var10 = new VisGraph();
        if (!var9.HorizonCode_Horizon_È()) {
            ++RenderChunk.HorizonCode_Horizon_È;
            for (final BlockPosM var12 : BlockPosM.HorizonCode_Horizon_È(var7, var8)) {
                final IBlockState var13 = var9.Â(var12);
                final Block var14 = var13.Ý();
                if (var14.Å()) {
                    var10.HorizonCode_Horizon_È(var12);
                }
                if (var14.£á()) {
                    final TileEntity var15 = var9.HorizonCode_Horizon_È(new BlockPos(var12));
                    if (var15 != null && TileEntityRendererDispatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var15)) {
                        var5.HorizonCode_Horizon_È(var15);
                    }
                }
                final EnumWorldBlockLayer var16 = var14.µà();
                final int var17 = var16.ordinal();
                if (var14.ÂµÈ() != -1) {
                    final WorldRenderer var18 = p_178581_4_.Ø­áŒŠá().HorizonCode_Horizon_È(var17);
                    if (!var5.Ø­áŒŠá(var16)) {
                        var5.Ý(var16);
                        this.HorizonCode_Horizon_È(var18, var7);
                    }
                    if (!Minecraft.áŒŠà().Ô().HorizonCode_Horizon_È(var13, var12, var9, var18)) {
                        continue;
                    }
                    var5.HorizonCode_Horizon_È(var16);
                }
            }
            final EnumWorldBlockLayer[] var19 = EnumWorldBlockLayer.values();
            for (int var20 = var19.length, var21 = 0; var21 < var20; ++var21) {
                final EnumWorldBlockLayer var16 = var19[var21];
                if (var5.Ø­áŒŠá(var16)) {
                    this.HorizonCode_Horizon_È(var16, p_178581_1_, p_178581_2_, p_178581_3_, p_178581_4_.Ø­áŒŠá().HorizonCode_Horizon_È(var16), var5);
                }
            }
        }
        var5.HorizonCode_Horizon_È(var10.HorizonCode_Horizon_È());
    }
    
    protected void Â() {
        this.à.lock();
        try {
            if (this.áŒŠÆ != null && this.áŒŠÆ.HorizonCode_Horizon_È() != ChunkCompileTaskGenerator.HorizonCode_Horizon_È.Ø­áŒŠá) {
                this.áŒŠÆ.Âµá€();
                this.áŒŠÆ = null;
            }
        }
        finally {
            this.à.unlock();
        }
        this.à.unlock();
    }
    
    public ReentrantLock Ý() {
        return this.à;
    }
    
    public ChunkCompileTaskGenerator Ø­áŒŠá() {
        this.à.lock();
        ChunkCompileTaskGenerator var1;
        try {
            this.Â();
            this.áŒŠÆ = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Â.HorizonCode_Horizon_È);
            var1 = this.áŒŠÆ;
        }
        finally {
            this.à.unlock();
        }
        this.à.unlock();
        return var1;
    }
    
    public ChunkCompileTaskGenerator Âµá€() {
        this.à.lock();
        ChunkCompileTaskGenerator var1;
        try {
            if (this.áŒŠÆ == null || this.áŒŠÆ.HorizonCode_Horizon_È() != ChunkCompileTaskGenerator.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                if (this.áŒŠÆ != null && this.áŒŠÆ.HorizonCode_Horizon_È() != ChunkCompileTaskGenerator.HorizonCode_Horizon_È.Ø­áŒŠá) {
                    this.áŒŠÆ.Âµá€();
                    this.áŒŠÆ = null;
                }
                (this.áŒŠÆ = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Â.Â)).HorizonCode_Horizon_È(this.Â);
                final ChunkCompileTaskGenerator var2;
                var1 = (var2 = this.áŒŠÆ);
                return var2;
            }
            var1 = null;
        }
        finally {
            this.à.unlock();
        }
        this.à.unlock();
        return var1;
    }
    
    private void HorizonCode_Horizon_È(final WorldRenderer p_178573_1_, final BlockPos p_178573_2_) {
        p_178573_1_.HorizonCode_Horizon_È(7);
        p_178573_1_.HorizonCode_Horizon_È(DefaultVertexFormats.HorizonCode_Horizon_È);
        p_178573_1_.Ý(-p_178573_2_.HorizonCode_Horizon_È(), -p_178573_2_.Â(), (double)(-p_178573_2_.Ý()));
    }
    
    private void HorizonCode_Horizon_È(final EnumWorldBlockLayer p_178584_1_, final float p_178584_2_, final float p_178584_3_, final float p_178584_4_, final WorldRenderer p_178584_5_, final CompiledChunk p_178584_6_) {
        if (p_178584_1_ == EnumWorldBlockLayer.Ø­áŒŠá && !p_178584_6_.Â(p_178584_1_)) {
            p_178584_6_.HorizonCode_Horizon_È(p_178584_5_.HorizonCode_Horizon_È(p_178584_2_, p_178584_3_, p_178584_4_));
        }
        p_178584_5_.Ø­áŒŠá();
    }
    
    private void á() {
        GlStateManager.Çªà¢();
        GlStateManager.ŒÏ();
        final float var1 = 1.000001f;
        GlStateManager.Â(-8.0f, -8.0f, -8.0f);
        GlStateManager.HorizonCode_Horizon_È(var1, var1, var1);
        GlStateManager.Â(8.0f, 8.0f, 8.0f);
        GlStateManager.HorizonCode_Horizon_È(2982, this.ÂµÈ);
        GlStateManager.Ê();
    }
    
    public void Ó() {
        GlStateManager.HorizonCode_Horizon_È(this.ÂµÈ);
    }
    
    public CompiledChunk à() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final CompiledChunk p_178580_1_) {
        this.Ø.lock();
        try {
            this.Â = p_178580_1_;
        }
        finally {
            this.Ø.unlock();
        }
        this.Ø.unlock();
    }
    
    public void Ø() {
        this.Â();
        this.Â = CompiledChunk.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È() {
        this.Ø();
        this.Ø­áŒŠá = null;
        for (int var1 = 0; var1 < EnumWorldBlockLayer.values().length; ++var1) {
            if (this.á[var1] != null) {
                this.á[var1].Ý();
            }
        }
    }
    
    public BlockPos áŒŠÆ() {
        return this.Ó;
    }
    
    public boolean áˆºÑ¢Õ() {
        this.à.lock();
        boolean var1;
        try {
            var1 = (this.áŒŠÆ == null || this.áŒŠÆ.HorizonCode_Horizon_È() == ChunkCompileTaskGenerator.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        }
        finally {
            this.à.unlock();
        }
        this.à.unlock();
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final boolean p_178575_1_) {
        this.£á = p_178575_1_;
    }
    
    public boolean ÂµÈ() {
        return this.£á;
    }
    
    public BlockPos HorizonCode_Horizon_È(final EnumFacing facing) {
        final int index = facing.Â();
        BlockPos posOffset = this.£à[index];
        if (posOffset == null) {
            posOffset = this.áŒŠÆ().HorizonCode_Horizon_È(facing, 16);
            this.£à[index] = posOffset;
        }
        return posOffset;
    }
}
