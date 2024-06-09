package com.client.glowclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;

public class EventRenderChunk extends Event
{
    private final RenderChunk B;
    private final BlockRenderLayer b;
    
    public EventRenderChunk(final RenderChunk b, final BlockRenderLayer b2) {
        super();
        this.B = b;
        this.b = b2;
    }
    
    public BlockRenderLayer getBlockRenderLayer() {
        return this.b;
    }
    
    public RenderChunk getRenderChunk() {
        return this.B;
    }
}
