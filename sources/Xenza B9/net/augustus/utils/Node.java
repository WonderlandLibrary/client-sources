// 
// Decompiled by Procyon v0.6.0
// 

package net.augustus.utils;

import net.minecraft.util.BlockPos;

public class Node
{
    private final BlockPos blockPos;
    
    public Node(final BlockPos blockPos) {
        this.blockPos = blockPos;
    }
    
    public BlockPos getBlockPos() {
        return this.blockPos;
    }
}
