// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.xray;

import net.minecraft.util.math.BlockPos;

public class XRayBlock
{
    private /* synthetic */ XRayData xRayData;
    private /* synthetic */ BlockPos blockPos;
    
    public XRayBlock(final BlockPos llIllIlIIlllIII, final XRayData llIllIlIIllIlII) {
        this.blockPos = llIllIlIIlllIII;
        this.xRayData = llIllIlIIllIlII;
    }
    
    public XRayData getxRayData() {
        return this.xRayData;
    }
    
    public BlockPos getBlockPos() {
        return this.blockPos;
    }
}
