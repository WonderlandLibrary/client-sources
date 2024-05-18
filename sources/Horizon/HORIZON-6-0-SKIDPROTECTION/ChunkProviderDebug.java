package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Collection;
import com.google.common.collect.Lists;
import java.util.List;

public class ChunkProviderDebug implements IChunkProvider
{
    private static final List HorizonCode_Horizon_È;
    private static final int Â;
    private final World Ý;
    private static final String Ø­áŒŠá = "CL_00002002";
    
    static {
        HorizonCode_Horizon_È = Lists.newArrayList();
        for (final Block var2 : Block.HorizonCode_Horizon_È) {
            ChunkProviderDebug.HorizonCode_Horizon_È.addAll((Collection)var2.ŠÂµà().HorizonCode_Horizon_È());
        }
        Â = MathHelper.Ó(MathHelper.Ý((float)ChunkProviderDebug.HorizonCode_Horizon_È.size()));
    }
    
    public ChunkProviderDebug(final World worldIn) {
        this.Ý = worldIn;
    }
    
    @Override
    public Chunk Ø­áŒŠá(final int p_73154_1_, final int p_73154_2_) {
        final ChunkPrimer var3 = new ChunkPrimer();
        for (int var4 = 0; var4 < 16; ++var4) {
            for (int var5 = 0; var5 < 16; ++var5) {
                final int var6 = p_73154_1_ * 16 + var4;
                final int var7 = p_73154_2_ * 16 + var5;
                var3.HorizonCode_Horizon_È(var4, 60, var5, Blocks.¥ÇªÅ.¥à());
                final IBlockState var8 = Â(var6, var7);
                if (var8 != null) {
                    var3.HorizonCode_Horizon_È(var4, 70, var5, var8);
                }
            }
        }
        final Chunk var9 = new Chunk(this.Ý, var3, p_73154_1_, p_73154_2_);
        var9.Ø­áŒŠá();
        final BiomeGenBase[] var10 = this.Ý.áŒŠÆ().Â(null, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
        int var7;
        byte[] var11;
        for (var11 = var9.ÂµÈ(), var7 = 0; var7 < var11.length; ++var7) {
            var11[var7] = (byte)var10[var7].ÇªÔ;
        }
        var9.Ø­áŒŠá();
        return var9;
    }
    
    public static IBlockState Â(int p_177461_0_, int p_177461_1_) {
        IBlockState var2 = null;
        if (p_177461_0_ > 0 && p_177461_1_ > 0 && p_177461_0_ % 2 != 0 && p_177461_1_ % 2 != 0) {
            p_177461_0_ /= 2;
            p_177461_1_ /= 2;
            if (p_177461_0_ <= ChunkProviderDebug.Â && p_177461_1_ <= ChunkProviderDebug.Â) {
                final int var3 = MathHelper.HorizonCode_Horizon_È(p_177461_0_ * ChunkProviderDebug.Â + p_177461_1_);
                if (var3 < ChunkProviderDebug.HorizonCode_Horizon_È.size()) {
                    var2 = ChunkProviderDebug.HorizonCode_Horizon_È.get(var3);
                }
            }
        }
        return var2;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int p_73149_1_, final int p_73149_2_) {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IChunkProvider p_177460_1_, final Chunk p_177460_2_, final int p_177460_3_, final int p_177460_4_) {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final boolean p_73151_1_, final IProgressUpdate p_73151_2_) {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public boolean Ý() {
        return true;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "DebugLevelSource";
    }
    
    @Override
    public List HorizonCode_Horizon_È(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
        final BiomeGenBase var3 = this.Ý.Ý(p_177458_2_);
        return var3.HorizonCode_Horizon_È(p_177458_1_);
    }
    
    @Override
    public BlockPos HorizonCode_Horizon_È(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
        return null;
    }
    
    @Override
    public int Âµá€() {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {
    }
    
    @Override
    public Chunk HorizonCode_Horizon_È(final BlockPos p_177459_1_) {
        return this.Ø­áŒŠá(p_177459_1_.HorizonCode_Horizon_È() >> 4, p_177459_1_.Ý() >> 4);
    }
}
