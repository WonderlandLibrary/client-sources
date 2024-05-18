package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ChunkCompileTaskGenerator
{
    private final RenderChunk HorizonCode_Horizon_È;
    private final ReentrantLock Â;
    private final List Ý;
    private final Â Ø­áŒŠá;
    private RegionRenderCacheBuilder Âµá€;
    private CompiledChunk Ó;
    private HorizonCode_Horizon_È à;
    private boolean Ø;
    private static final String áŒŠÆ = "CL_00002466";
    
    public ChunkCompileTaskGenerator(final RenderChunk p_i46208_1_, final Â p_i46208_2_) {
        this.Â = new ReentrantLock();
        this.Ý = Lists.newArrayList();
        this.à = ChunkCompileTaskGenerator.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        this.HorizonCode_Horizon_È = p_i46208_1_;
        this.Ø­áŒŠá = p_i46208_2_;
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È() {
        return this.à;
    }
    
    public RenderChunk Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public CompiledChunk Ý() {
        return this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final CompiledChunk p_178543_1_) {
        this.Ó = p_178543_1_;
    }
    
    public RegionRenderCacheBuilder Ø­áŒŠá() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final RegionRenderCacheBuilder p_178541_1_) {
        this.Âµá€ = p_178541_1_;
    }
    
    public void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_178535_1_) {
        this.Â.lock();
        try {
            this.à = p_178535_1_;
        }
        finally {
            this.Â.unlock();
        }
        this.Â.unlock();
    }
    
    public void Âµá€() {
        this.Â.lock();
        try {
            if (this.Ø­áŒŠá == ChunkCompileTaskGenerator.Â.HorizonCode_Horizon_È && this.à != ChunkCompileTaskGenerator.HorizonCode_Horizon_È.Ø­áŒŠá) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(true);
            }
            this.Ø = true;
            this.à = ChunkCompileTaskGenerator.HorizonCode_Horizon_È.Ø­áŒŠá;
            for (final Runnable var2 : this.Ý) {
                var2.run();
            }
        }
        finally {
            this.Â.unlock();
        }
        this.Â.unlock();
    }
    
    public void HorizonCode_Horizon_È(final Runnable p_178539_1_) {
        this.Â.lock();
        try {
            this.Ý.add(p_178539_1_);
            if (this.Ø) {
                p_178539_1_.run();
            }
        }
        finally {
            this.Â.unlock();
        }
        this.Â.unlock();
    }
    
    public ReentrantLock Ó() {
        return this.Â;
    }
    
    public Â à() {
        return this.Ø­áŒŠá;
    }
    
    public boolean Ø() {
        return this.Ø;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("PENDING", 0, "PENDING", 0, "PENDING", 0), 
        Â("COMPILING", 1, "COMPILING", 1, "COMPILING", 1), 
        Ý("UPLOADING", 2, "UPLOADING", 2, "UPLOADING", 2), 
        Ø­áŒŠá("DONE", 3, "DONE", 3, "DONE", 3);
        
        private static final HorizonCode_Horizon_È[] Âµá€;
        private static final String Ó = "CL_00002465";
        private static final HorizonCode_Horizon_È[] à;
        
        static {
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i46391_1_, final int p_i46391_2_, final String p_i46207_1_, final int p_i46207_2_) {
        }
    }
    
    public enum Â
    {
        HorizonCode_Horizon_È("REBUILD_CHUNK", 0, "REBUILD_CHUNK", 0, "REBUILD_CHUNK", 0), 
        Â("RESORT_TRANSPARENCY", 1, "RESORT_TRANSPARENCY", 1, "RESORT_TRANSPARENCY", 1);
        
        private static final Â[] Ý;
        private static final String Ø­áŒŠá = "CL_00002464";
        private static final Â[] Âµá€;
        
        static {
            Ó = new Â[] { Â.HorizonCode_Horizon_È, Â.Â };
            Ý = new Â[] { Â.HorizonCode_Horizon_È, Â.Â };
            Âµá€ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â };
        }
        
        private Â(final String s, final int n, final String p_i46392_1_, final int p_i46392_2_, final String p_i46206_1_, final int p_i46206_2_) {
        }
    }
}
