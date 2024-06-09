package com.client.glowclient;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.*;

public class od implements E
{
    public final JB b;
    
    public RenderChunk create(final World world, final RenderGlobal renderGlobal, final int n) {
        return new zB(world, renderGlobal, n);
    }
    
    public od(final JB b) {
        this.b = b;
        super();
    }
    
    @Override
    public RA makeRenderOverlay(final World world, final RenderGlobal renderGlobal, final int n) {
        return new RA(world, renderGlobal, n);
    }
}
