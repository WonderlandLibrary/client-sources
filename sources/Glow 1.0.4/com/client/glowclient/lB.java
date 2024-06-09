package com.client.glowclient;

import net.minecraft.util.math.*;

public class LB
{
    public static Iterable<wc> A(final BlockPos blockPos, final BlockPos blockPos2) {
        return D(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
    }
    
    public static Iterable<wc> D(final BlockPos blockPos, final BlockPos blockPos2) {
        return M(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
    }
    
    public static Iterable<wc> A(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return new YC(new BlockPos(Math.min(n, n4), Math.min(n2, n5), Math.min(n3, n6)), new BlockPos(Math.max(n, n4), Math.max(n2, n5), Math.max(n3, n6)));
    }
    
    public static Iterable<wc> M(final BlockPos blockPos, final BlockPos blockPos2) {
        return A(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
    }
    
    public LB() {
        super();
    }
    
    public static Iterable<wc> D(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return new yb(new BlockPos(Math.min(n, n4), Math.min(n2, n5), Math.min(n3, n6)), new BlockPos(Math.max(n, n4), Math.max(n2, n5), Math.max(n3, n6)));
    }
    
    public static Iterable<wc> M(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return new BA(new BlockPos(Math.min(n, n4), Math.min(n2, n5), Math.min(n3, n6)), new BlockPos(Math.max(n, n4), Math.max(n2, n5), Math.max(n3, n6)));
    }
}
