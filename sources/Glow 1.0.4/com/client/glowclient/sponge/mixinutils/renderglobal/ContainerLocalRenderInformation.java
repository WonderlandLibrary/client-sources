package com.client.glowclient.sponge.mixinutils.renderglobal;

import net.minecraft.client.renderer.chunk.*;
import net.minecraft.util.*;
import javax.annotation.*;

public class ContainerLocalRenderInformation
{
    public byte setFacing;
    public final RenderChunk renderChunk;
    public final EnumFacing facing;
    public final int counter;
    
    public boolean hasDirection(final EnumFacing enumFacing) {
        return (this.setFacing & 1 << enumFacing.ordinal()) > 0;
    }
    
    public void setDirection(final byte b, final EnumFacing enumFacing) {
        this.setFacing = (byte)(this.setFacing | b | 1 << enumFacing.ordinal());
    }
    
    public ContainerLocalRenderInformation(final RenderChunk renderChunk, final EnumFacing facing, @Nullable final int counter) {
        super();
        this.renderChunk = renderChunk;
        this.facing = facing;
        this.counter = counter;
    }
}
