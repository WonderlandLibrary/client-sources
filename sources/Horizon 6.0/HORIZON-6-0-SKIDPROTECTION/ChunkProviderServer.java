package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.Map;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.Logger;

public class ChunkProviderServer implements IChunkProvider
{
    private static final Logger Â;
    private Set Ý;
    private Chunk Ø­áŒŠá;
    private IChunkProvider Âµá€;
    private IChunkLoader Ó;
    public boolean HorizonCode_Horizon_È;
    private LongHashMap à;
    private List Ø;
    private WorldServer áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001436";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public ChunkProviderServer(final WorldServer p_i1520_1_, final IChunkLoader p_i1520_2_, final IChunkProvider p_i1520_3_) {
        this.Ý = Collections.newSetFromMap(new ConcurrentHashMap<Object, Boolean>());
        this.HorizonCode_Horizon_È = true;
        this.à = new LongHashMap();
        this.Ø = Lists.newArrayList();
        this.Ø­áŒŠá = new EmptyChunk(p_i1520_1_, 0, 0);
        this.áŒŠÆ = p_i1520_1_;
        this.Ó = p_i1520_2_;
        this.Âµá€ = p_i1520_3_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int p_73149_1_, final int p_73149_2_) {
        return this.à.Â(ChunkCoordIntPair.HorizonCode_Horizon_È(p_73149_1_, p_73149_2_));
    }
    
    public List Ó() {
        return this.Ø;
    }
    
    public void Â(final int p_73241_1_, final int p_73241_2_) {
        if (this.áŒŠÆ.£à.Âµá€()) {
            if (!this.áŒŠÆ.Ý(p_73241_1_, p_73241_2_)) {
                this.Ý.add(ChunkCoordIntPair.HorizonCode_Horizon_È(p_73241_1_, p_73241_2_));
            }
        }
        else {
            this.Ý.add(ChunkCoordIntPair.HorizonCode_Horizon_È(p_73241_1_, p_73241_2_));
        }
    }
    
    public void à() {
        for (final Chunk var2 : this.Ø) {
            this.Â(var2.HorizonCode_Horizon_È, var2.Â);
        }
    }
    
    public Chunk Ý(final int p_73158_1_, final int p_73158_2_) {
        final long var3 = ChunkCoordIntPair.HorizonCode_Horizon_È(p_73158_1_, p_73158_2_);
        this.Ý.remove(var3);
        Chunk var4 = (Chunk)this.à.HorizonCode_Horizon_È(var3);
        if (var4 == null) {
            var4 = this.Âµá€(p_73158_1_, p_73158_2_);
            if (var4 == null) {
                if (this.Âµá€ == null) {
                    var4 = this.Ø­áŒŠá;
                }
                else {
                    try {
                        var4 = this.Âµá€.Ø­áŒŠá(p_73158_1_, p_73158_2_);
                    }
                    catch (Throwable var6) {
                        final CrashReport var5 = CrashReport.HorizonCode_Horizon_È(var6, "Exception generating new chunk");
                        final CrashReportCategory var7 = var5.HorizonCode_Horizon_È("Chunk to be generated");
                        var7.HorizonCode_Horizon_È("Location", String.format("%d,%d", p_73158_1_, p_73158_2_));
                        var7.HorizonCode_Horizon_È("Position hash", var3);
                        var7.HorizonCode_Horizon_È("Generator", this.Âµá€.Ø­áŒŠá());
                        throw new ReportedException(var5);
                    }
                }
            }
            this.à.HorizonCode_Horizon_È(var3, var4);
            this.Ø.add(var4);
            var4.Âµá€();
            var4.HorizonCode_Horizon_È(this, this, p_73158_1_, p_73158_2_);
        }
        return var4;
    }
    
    @Override
    public Chunk Ø­áŒŠá(final int p_73154_1_, final int p_73154_2_) {
        final Chunk var3 = (Chunk)this.à.HorizonCode_Horizon_È(ChunkCoordIntPair.HorizonCode_Horizon_È(p_73154_1_, p_73154_2_));
        return (var3 == null) ? ((!this.áŒŠÆ.Ç() && !this.HorizonCode_Horizon_È) ? this.Ø­áŒŠá : this.Ý(p_73154_1_, p_73154_2_)) : var3;
    }
    
    private Chunk Âµá€(final int p_73239_1_, final int p_73239_2_) {
        if (this.Ó == null) {
            return null;
        }
        try {
            final Chunk var3 = this.Ó.HorizonCode_Horizon_È(this.áŒŠÆ, p_73239_1_, p_73239_2_);
            if (var3 != null) {
                var3.Â(this.áŒŠÆ.Šáƒ());
                if (this.Âµá€ != null) {
                    this.Âµá€.HorizonCode_Horizon_È(var3, p_73239_1_, p_73239_2_);
                }
            }
            return var3;
        }
        catch (Exception var4) {
            ChunkProviderServer.Â.error("Couldn't load chunk", (Throwable)var4);
            return null;
        }
    }
    
    private void HorizonCode_Horizon_È(final Chunk p_73243_1_) {
        if (this.Ó != null) {
            try {
                this.Ó.Â(this.áŒŠÆ, p_73243_1_);
            }
            catch (Exception var3) {
                ChunkProviderServer.Â.error("Couldn't save entities", (Throwable)var3);
            }
        }
    }
    
    private void Â(final Chunk p_73242_1_) {
        if (this.Ó != null) {
            try {
                p_73242_1_.Â(this.áŒŠÆ.Šáƒ());
                this.Ó.HorizonCode_Horizon_È(this.áŒŠÆ, p_73242_1_);
            }
            catch (IOException var3) {
                ChunkProviderServer.Â.error("Couldn't save chunk", (Throwable)var3);
            }
            catch (MinecraftException var4) {
                ChunkProviderServer.Â.error("Couldn't save chunk; already in use by another instance of Minecraft?", (Throwable)var4);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
        final Chunk var4 = this.Ø­áŒŠá(p_73153_2_, p_73153_3_);
        if (!var4.Ø­à()) {
            var4.£á();
            if (this.Âµá€ != null) {
                this.Âµá€.HorizonCode_Horizon_È(p_73153_1_, p_73153_2_, p_73153_3_);
                var4.à();
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IChunkProvider p_177460_1_, final Chunk p_177460_2_, final int p_177460_3_, final int p_177460_4_) {
        if (this.Âµá€ != null && this.Âµá€.HorizonCode_Horizon_È(p_177460_1_, p_177460_2_, p_177460_3_, p_177460_4_)) {
            final Chunk var5 = this.Ø­áŒŠá(p_177460_3_, p_177460_4_);
            var5.à();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final boolean p_73151_1_, final IProgressUpdate p_73151_2_) {
        int var3 = 0;
        for (int var4 = 0; var4 < this.Ø.size(); ++var4) {
            final Chunk var5 = this.Ø.get(var4);
            if (p_73151_1_) {
                this.HorizonCode_Horizon_È(var5);
            }
            if (var5.HorizonCode_Horizon_È(p_73151_1_)) {
                this.Â(var5);
                var5.Ó(false);
                if (++var3 == 24 && !p_73151_1_) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.Ó != null) {
            this.Ó.Ý();
        }
    }
    
    @Override
    public boolean Â() {
        if (!this.áŒŠÆ.ˆá) {
            for (int var1 = 0; var1 < 100; ++var1) {
                if (!this.Ý.isEmpty()) {
                    final Long var2 = this.Ý.iterator().next();
                    final Chunk var3 = (Chunk)this.à.HorizonCode_Horizon_È(var2);
                    if (var3 != null) {
                        var3.Ó();
                        this.Â(var3);
                        this.HorizonCode_Horizon_È(var3);
                        this.à.Ø­áŒŠá(var2);
                        this.Ø.remove(var3);
                    }
                    this.Ý.remove(var2);
                }
            }
            if (this.Ó != null) {
                this.Ó.Â();
            }
        }
        return this.Âµá€.Â();
    }
    
    @Override
    public boolean Ý() {
        return !this.áŒŠÆ.ˆá;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "ServerChunkCache: " + this.à.HorizonCode_Horizon_È() + " Drop: " + this.Ý.size();
    }
    
    @Override
    public List HorizonCode_Horizon_È(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
        return this.Âµá€.HorizonCode_Horizon_È(p_177458_1_, p_177458_2_);
    }
    
    @Override
    public BlockPos HorizonCode_Horizon_È(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
        return this.Âµá€.HorizonCode_Horizon_È(worldIn, p_180513_2_, p_180513_3_);
    }
    
    @Override
    public int Âµá€() {
        return this.à.HorizonCode_Horizon_È();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {
    }
    
    @Override
    public Chunk HorizonCode_Horizon_È(final BlockPos p_177459_1_) {
        return this.Ø­áŒŠá(p_177459_1_.HorizonCode_Horizon_È() >> 4, p_177459_1_.Ý() >> 4);
    }
}
