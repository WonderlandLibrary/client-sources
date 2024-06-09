package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.List;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderWorker implements Runnable
{
    private static final Logger HorizonCode_Horizon_È;
    private final ChunkRenderDispatcher Â;
    private final RegionRenderCacheBuilder Ý;
    private static final String Ø­áŒŠá = "CL_00002459";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public ChunkRenderWorker(final ChunkRenderDispatcher p_i46201_1_) {
        this(p_i46201_1_, null);
    }
    
    public ChunkRenderWorker(final ChunkRenderDispatcher p_i46202_1_, final RegionRenderCacheBuilder p_i46202_2_) {
        this.Â = p_i46202_1_;
        this.Ý = p_i46202_2_;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                this.HorizonCode_Horizon_È(this.Â.Ø­áŒŠá());
            }
        }
        catch (InterruptedException var4) {
            ChunkRenderWorker.HorizonCode_Horizon_È.debug("Stopping due to interrupt");
        }
        catch (Throwable var3) {
            final CrashReport var2 = CrashReport.HorizonCode_Horizon_È(var3, "Batching chunks");
            Minecraft.áŒŠà().HorizonCode_Horizon_È(Minecraft.áŒŠà().Ý(var2));
        }
    }
    
    protected void HorizonCode_Horizon_È(final ChunkCompileTaskGenerator p_178474_1_) throws InterruptedException {
        p_178474_1_.Ó().lock();
        Label_0094: {
            try {
                if (p_178474_1_.HorizonCode_Horizon_È() == ChunkCompileTaskGenerator.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                    p_178474_1_.HorizonCode_Horizon_È(ChunkCompileTaskGenerator.HorizonCode_Horizon_È.Â);
                    break Label_0094;
                }
                if (!p_178474_1_.Ø()) {
                    ChunkRenderWorker.HorizonCode_Horizon_È.warn("Chunk render task was " + p_178474_1_.HorizonCode_Horizon_È() + " when I expected it to be pending; ignoring task");
                }
            }
            finally {
                p_178474_1_.Ó().unlock();
            }
            p_178474_1_.Ó().unlock();
            return;
        }
        final Entity var2 = Minecraft.áŒŠà().ÇŽá€();
        if (var2 == null) {
            p_178474_1_.Âµá€();
        }
        else {
            p_178474_1_.HorizonCode_Horizon_È(this.Â());
            final float var3 = (float)var2.ŒÏ;
            final float var4 = (float)var2.Çªà¢ + var2.Ðƒáƒ();
            final float var5 = (float)var2.Ê;
            final ChunkCompileTaskGenerator.Â var6 = p_178474_1_.à();
            if (var6 == ChunkCompileTaskGenerator.Â.HorizonCode_Horizon_È) {
                p_178474_1_.Â().Â(var3, var4, var5, p_178474_1_);
            }
            else if (var6 == ChunkCompileTaskGenerator.Â.Â) {
                p_178474_1_.Â().HorizonCode_Horizon_È(var3, var4, var5, p_178474_1_);
            }
            p_178474_1_.Ó().lock();
            try {
                if (p_178474_1_.HorizonCode_Horizon_È() != ChunkCompileTaskGenerator.HorizonCode_Horizon_È.Â) {
                    if (!p_178474_1_.Ø()) {
                        ChunkRenderWorker.HorizonCode_Horizon_È.warn("Chunk render task was " + p_178474_1_.HorizonCode_Horizon_È() + " when I expected it to be compiling; aborting task");
                    }
                    this.Â(p_178474_1_);
                    return;
                }
                p_178474_1_.HorizonCode_Horizon_È(ChunkCompileTaskGenerator.HorizonCode_Horizon_È.Ý);
            }
            finally {
                p_178474_1_.Ó().unlock();
            }
            p_178474_1_.Ó().unlock();
            final CompiledChunk var7 = p_178474_1_.Ý();
            final ArrayList var8 = Lists.newArrayList();
            if (var6 == ChunkCompileTaskGenerator.Â.HorizonCode_Horizon_È) {
                for (final EnumWorldBlockLayer var12 : EnumWorldBlockLayer.values()) {
                    if (var7.Ø­áŒŠá(var12)) {
                        var8.add(this.Â.HorizonCode_Horizon_È(var12, p_178474_1_.Ø­áŒŠá().HorizonCode_Horizon_È(var12), p_178474_1_.Â(), var7));
                    }
                }
            }
            else if (var6 == ChunkCompileTaskGenerator.Â.Â) {
                var8.add(this.Â.HorizonCode_Horizon_È(EnumWorldBlockLayer.Ø­áŒŠá, p_178474_1_.Ø­áŒŠá().HorizonCode_Horizon_È(EnumWorldBlockLayer.Ø­áŒŠá), p_178474_1_.Â(), var7));
            }
            final ListenableFuture var13 = Futures.allAsList((Iterable)var8);
            p_178474_1_.HorizonCode_Horizon_È(new Runnable() {
                private static final String Â = "CL_00002458";
                
                @Override
                public void run() {
                    var13.cancel(false);
                }
            });
            Futures.addCallback(var13, (FutureCallback)new FutureCallback() {
                private static final String Â = "CL_00002457";
                
                public void HorizonCode_Horizon_È(final List p_178481_1_) {
                    ChunkRenderWorker.this.Â(p_178474_1_);
                    p_178474_1_.Ó().lock();
                    try {
                        if (p_178474_1_.HorizonCode_Horizon_È() != ChunkCompileTaskGenerator.HorizonCode_Horizon_È.Ý) {
                            if (!p_178474_1_.Ø()) {
                                ChunkRenderWorker.HorizonCode_Horizon_È.warn("Chunk render task was " + p_178474_1_.HorizonCode_Horizon_È() + " when I expected it to be uploading; aborting task");
                            }
                            return;
                        }
                        p_178474_1_.HorizonCode_Horizon_È(ChunkCompileTaskGenerator.HorizonCode_Horizon_È.Ø­áŒŠá);
                    }
                    finally {
                        p_178474_1_.Ó().unlock();
                    }
                    p_178474_1_.Ó().unlock();
                    p_178474_1_.Â().HorizonCode_Horizon_È(var7);
                }
                
                public void onFailure(final Throwable p_onFailure_1_) {
                    ChunkRenderWorker.this.Â(p_178474_1_);
                    if (!(p_onFailure_1_ instanceof CancellationException) && !(p_onFailure_1_ instanceof InterruptedException)) {
                        Minecraft.áŒŠà().HorizonCode_Horizon_È(CrashReport.HorizonCode_Horizon_È(p_onFailure_1_, "Rendering chunk"));
                    }
                }
                
                public void onSuccess(final Object p_onSuccess_1_) {
                    this.HorizonCode_Horizon_È((List)p_onSuccess_1_);
                }
            });
        }
    }
    
    private RegionRenderCacheBuilder Â() throws InterruptedException {
        return (this.Ý != null) ? this.Ý : this.Â.Ý();
    }
    
    private void Â(final ChunkCompileTaskGenerator p_178473_1_) {
        if (this.Ý == null) {
            this.Â.HorizonCode_Horizon_È(p_178473_1_.Ø­áŒŠá());
        }
    }
}
