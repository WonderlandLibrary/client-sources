package com.client.glowclient;

import net.minecraft.client.multiplayer.*;
import mcp.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import com.google.common.base.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import java.util.concurrent.*;

@MethodsReturnNonnullByDefault
public class Dc extends ChunkProviderClient implements IChunkProvider
{
    private final XA A;
    private final Map<Long, UC> B;
    private final Chunk b;
    
    public Chunk provideChunk(final int n, final int n2) {
        return this.getLoadedChunk(n, n2);
    }
    
    public Chunk loadChunk(final int n, final int n2) {
        return MoreObjects.firstNonNull(this.getLoadedChunk(n, n2), this.b);
    }
    
    public String makeString() {
        return "SchematicChunkCache";
    }
    
    public void unloadChunk(final int n, final int n2) {
    }
    
    public Chunk getLoadedChunk(final int n, final int n2) {
        if (!this.M(n, n2)) {
            return this.b;
        }
        final long long1 = ChunkPos.asLong(n, n2);
        UC uc;
        if ((uc = this.B.get(long1)) == null) {
            uc = new UC((World)this.A, n, n2);
            this.B.put(long1, uc);
        }
        return uc;
    }
    
    public Dc(final XA a) {
        super((World)a);
        this.B = new ConcurrentHashMap<Long, UC>();
        this.A = a;
        final int n = 0;
        this.b = (Chunk)new lC(this, (World)a, n, n);
    }
    
    private boolean M(final int n, final int n2) {
        return n >= 0 && n2 >= 0 && n < this.A.getWidth() && n2 < this.A.getLength();
    }
}
