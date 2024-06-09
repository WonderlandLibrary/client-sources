package com.client.glowclient;

import net.minecraft.client.renderer.*;
import java.util.*;
import com.google.common.collect.*;

public abstract class oB extends ChunkRenderContainer
{
    public List<RA> b;
    
    public void addRenderOverlay(final RA ra) {
        this.b.add(ra);
    }
    
    public oB() {
        final int n = 17424;
        super();
        this.b = (List<RA>)Lists.newArrayListWithCapacity(n);
    }
    
    public abstract void renderOverlay();
    
    public void initialize(final double n, final double n2, final double n3) {
        super.initialize(n, n2, n3);
        this.b.clear();
    }
}
