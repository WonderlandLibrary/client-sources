package net.minecraft.client.renderer.chunk;

import java.util.concurrent.locks.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;
import java.util.*;

public class ChunkCompileTaskGenerator
{
    private CompiledChunk compiledChunk;
    private final RenderChunk renderChunk;
    private final ReentrantLock lock;
    private boolean finished;
    private RegionRenderCacheBuilder regionRenderCacheBuilder;
    private final List<Runnable> listFinishRunnables;
    private Status status;
    private final Type type;
    
    public boolean isFinished() {
        return this.finished;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public ChunkCompileTaskGenerator(final RenderChunk renderChunk, final Type type) {
        this.lock = new ReentrantLock();
        this.listFinishRunnables = (List<Runnable>)Lists.newArrayList();
        this.status = Status.PENDING;
        this.renderChunk = renderChunk;
        this.type = type;
    }
    
    public void finish() {
        this.lock.lock();
        try {
            if (this.type == Type.REBUILD_CHUNK && this.status != Status.DONE) {
                this.renderChunk.setNeedsUpdate(" ".length() != 0);
            }
            this.finished = (" ".length() != 0);
            this.status = Status.DONE;
            final Iterator<Runnable> iterator = this.listFinishRunnables.iterator();
            "".length();
            if (4 == 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().run();
            }
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        finally {
            this.lock.unlock();
        }
        this.lock.unlock();
    }
    
    public ReentrantLock getLock() {
        return this.lock;
    }
    
    public void setStatus(final Status status) {
        this.lock.lock();
        try {
            this.status = status;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        finally {
            this.lock.unlock();
        }
        this.lock.unlock();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    public void setCompiledChunk(final CompiledChunk compiledChunk) {
        this.compiledChunk = compiledChunk;
    }
    
    public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
        return this.regionRenderCacheBuilder;
    }
    
    public CompiledChunk getCompiledChunk() {
        return this.compiledChunk;
    }
    
    public void setRegionRenderCacheBuilder(final RegionRenderCacheBuilder regionRenderCacheBuilder) {
        this.regionRenderCacheBuilder = regionRenderCacheBuilder;
    }
    
    public RenderChunk getRenderChunk() {
        return this.renderChunk;
    }
    
    public void addFinishRunnable(final Runnable runnable) {
        this.lock.lock();
        try {
            this.listFinishRunnables.add(runnable);
            if (this.finished) {
                runnable.run();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
        }
        finally {
            this.lock.unlock();
        }
        this.lock.unlock();
    }
    
    public enum Status
    {
        COMPILING(Status.I[" ".length()], " ".length()), 
        DONE(Status.I["   ".length()], "   ".length()), 
        PENDING(Status.I["".length()], "".length());
        
        private static final Status[] ENUM$VALUES;
        private static final String[] I;
        
        UPLOADING(Status.I["  ".length()], "  ".length());
        
        private static void I() {
            (I = new String[0x9D ^ 0x99])["".length()] = I("?\u0016\u0017\u0010\u0001!\u0014", "oSYTH");
            Status.I[" ".length()] = I("-\u000b\u0001\n\u0000\"\r\u0002\u001d", "nDLZI");
            Status.I["  ".length()] = I("\u001f#/\f\u0003\u000e:-\u0004", "JscCB");
            Status.I["   ".length()] = I(".'\u0003\u0015", "jhMPi");
        }
        
        static {
            I();
            final Status[] enum$VALUES = new Status[0x5D ^ 0x59];
            enum$VALUES["".length()] = Status.PENDING;
            enum$VALUES[" ".length()] = Status.COMPILING;
            enum$VALUES["  ".length()] = Status.UPLOADING;
            enum$VALUES["   ".length()] = Status.DONE;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private Status(final String s, final int n) {
        }
    }
    
    public enum Type
    {
        RESORT_TRANSPARENCY(Type.I[" ".length()], " ".length());
        
        private static final String[] I;
        
        REBUILD_CHUNK(Type.I["".length()], "".length());
        
        private static final Type[] ENUM$VALUES;
        
        private Type(final String s, final int n) {
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (2 == 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("1\n#/$/\u000b>9%6\u0001*", "cOazm");
            Type.I[" ".length()] = I("\u001f==\t\u0002\u0019':\u0014\u0011\u0003+>\u0007\u0002\b6-\u001f", "MxnFP");
        }
        
        static {
            I();
            final Type[] enum$VALUES = new Type["  ".length()];
            enum$VALUES["".length()] = Type.REBUILD_CHUNK;
            enum$VALUES[" ".length()] = Type.RESORT_TRANSPARENCY;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
