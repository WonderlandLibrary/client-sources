package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class ChunkProviderClient implements IChunkProvider
{
    private static final Logger HorizonCode_Horizon_È;
    private Chunk Â;
    private LongHashMap Ý;
    private List Ø­áŒŠá;
    private World Âµá€;
    private static final String Ó = "CL_00000880";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public ChunkProviderClient(final World worldIn) {
        this.Ý = new LongHashMap();
        this.Ø­áŒŠá = Lists.newArrayList();
        this.Â = new EmptyChunk(worldIn, 0, 0);
        this.Âµá€ = worldIn;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int p_73149_1_, final int p_73149_2_) {
        return true;
    }
    
    public void Â(final int p_73234_1_, final int p_73234_2_) {
        final Chunk var3 = this.Ø­áŒŠá(p_73234_1_, p_73234_2_);
        if (!var3.Ø()) {
            var3.Ó();
        }
        this.Ý.Ø­áŒŠá(ChunkCoordIntPair.HorizonCode_Horizon_È(p_73234_1_, p_73234_2_));
        this.Ø­áŒŠá.remove(var3);
    }
    
    public Chunk Ý(final int p_73158_1_, final int p_73158_2_) {
        final Chunk var3 = new Chunk(this.Âµá€, p_73158_1_, p_73158_2_);
        this.Ý.HorizonCode_Horizon_È(ChunkCoordIntPair.HorizonCode_Horizon_È(p_73158_1_, p_73158_2_), var3);
        this.Ø­áŒŠá.add(var3);
        var3.Ý(true);
        return var3;
    }
    
    @Override
    public Chunk Ø­áŒŠá(final int p_73154_1_, final int p_73154_2_) {
        final Chunk var3 = (Chunk)this.Ý.HorizonCode_Horizon_È(ChunkCoordIntPair.HorizonCode_Horizon_È(p_73154_1_, p_73154_2_));
        return (var3 == null) ? this.Â : var3;
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
        final long var1 = System.currentTimeMillis();
        for (final Chunk var3 : this.Ø­áŒŠá) {
            var3.Â(System.currentTimeMillis() - var1 > 5L);
        }
        if (System.currentTimeMillis() - var1 > 100L) {
            ChunkProviderClient.HorizonCode_Horizon_È.info("Warning: Clientside chunk ticking took {} ms", new Object[] { System.currentTimeMillis() - var1 });
        }
        return false;
    }
    
    @Override
    public boolean Ý() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IChunkProvider p_177460_1_, final Chunk p_177460_2_, final int p_177460_3_, final int p_177460_4_) {
        return false;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "MultiplayerChunkCache: " + this.Ý.HorizonCode_Horizon_È() + ", " + this.Ø­áŒŠá.size();
    }
    
    @Override
    public List HorizonCode_Horizon_È(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
        return null;
    }
    
    @Override
    public BlockPos HorizonCode_Horizon_È(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
        return null;
    }
    
    @Override
    public int Âµá€() {
        return this.Ø­áŒŠá.size();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {
    }
    
    @Override
    public Chunk HorizonCode_Horizon_È(final BlockPos p_177459_1_) {
        return this.Ø­áŒŠá(p_177459_1_.HorizonCode_Horizon_È() >> 4, p_177459_1_.Ý() >> 4);
    }
}
