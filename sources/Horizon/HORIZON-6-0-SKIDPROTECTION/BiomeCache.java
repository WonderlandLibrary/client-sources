package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class BiomeCache
{
    private final WorldChunkManager HorizonCode_Horizon_È;
    private long Â;
    private LongHashMap Ý;
    private List Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000162";
    
    public BiomeCache(final WorldChunkManager p_i1973_1_) {
        this.Ý = new LongHashMap();
        this.Ø­áŒŠá = Lists.newArrayList();
        this.HorizonCode_Horizon_È = p_i1973_1_;
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_76840_1_, int p_76840_2_) {
        p_76840_1_ >>= 4;
        p_76840_2_ >>= 4;
        final long var3 = (p_76840_1_ & 0xFFFFFFFFL) | (p_76840_2_ & 0xFFFFFFFFL) << 32;
        HorizonCode_Horizon_È var4 = (HorizonCode_Horizon_È)this.Ý.HorizonCode_Horizon_È(var3);
        if (var4 == null) {
            var4 = new HorizonCode_Horizon_È(p_76840_1_, p_76840_2_);
            this.Ý.HorizonCode_Horizon_È(var3, var4);
            this.Ø­áŒŠá.add(var4);
        }
        var4.Âµá€ = MinecraftServer.Œà();
        return var4;
    }
    
    public BiomeGenBase HorizonCode_Horizon_È(final int p_180284_1_, final int p_180284_2_, final BiomeGenBase p_180284_3_) {
        final BiomeGenBase var4 = this.HorizonCode_Horizon_È(p_180284_1_, p_180284_2_).HorizonCode_Horizon_È(p_180284_1_, p_180284_2_);
        return (var4 == null) ? p_180284_3_ : var4;
    }
    
    public void HorizonCode_Horizon_È() {
        final long var1 = MinecraftServer.Œà();
        final long var2 = var1 - this.Â;
        if (var2 > 7500L || var2 < 0L) {
            this.Â = var1;
            for (int var3 = 0; var3 < this.Ø­áŒŠá.size(); ++var3) {
                final HorizonCode_Horizon_È var4 = this.Ø­áŒŠá.get(var3);
                final long var5 = var1 - var4.Âµá€;
                if (var5 > 30000L || var5 < 0L) {
                    this.Ø­áŒŠá.remove(var3--);
                    final long var6 = (var4.Ý & 0xFFFFFFFFL) | (var4.Ø­áŒŠá & 0xFFFFFFFFL) << 32;
                    this.Ý.Ø­áŒŠá(var6);
                }
            }
        }
    }
    
    public BiomeGenBase[] Â(final int p_76839_1_, final int p_76839_2_) {
        return this.HorizonCode_Horizon_È(p_76839_1_, p_76839_2_).Â;
    }
    
    public class HorizonCode_Horizon_È
    {
        public float[] HorizonCode_Horizon_È;
        public BiomeGenBase[] Â;
        public int Ý;
        public int Ø­áŒŠá;
        public long Âµá€;
        private static final String à = "CL_00000163";
        
        public HorizonCode_Horizon_È(final int p_i1972_2_, final int p_i1972_3_) {
            this.HorizonCode_Horizon_È = new float[256];
            this.Â = new BiomeGenBase[256];
            this.Ý = p_i1972_2_;
            this.Ø­áŒŠá = p_i1972_3_;
            BiomeCache.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, p_i1972_2_ << 4, p_i1972_3_ << 4, 16, 16);
            BiomeCache.this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Â, p_i1972_2_ << 4, p_i1972_3_ << 4, 16, 16, false);
        }
        
        public BiomeGenBase HorizonCode_Horizon_È(final int p_76885_1_, final int p_76885_2_) {
            return this.Â[(p_76885_1_ & 0xF) | (p_76885_2_ & 0xF) << 4];
        }
    }
}
