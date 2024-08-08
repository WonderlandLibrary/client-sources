package net.futureclient.client;

import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.futureclient.client.events.Event;

public class xF extends Event
{
    private EnumFacing j;
    private int K;
    public BlockPos M;
    private int d;
    private int a;
    private float D;
    private int k;
    
    public xF(final int d, final int k, final int i, final int a, final float d2, final EnumFacing j, final BlockPos m) {
        super();
        this.d = d;
        this.K = k;
        this.k = i;
        this.a = a;
        this.D = d2;
        this.j = j;
        this.M = m;
    }
    
    public int B() {
        return this.K;
    }
    
    public void B(final int k) {
        this.k = k;
    }
    
    public void b(final int d) {
        this.d = d;
    }
    
    public int b() {
        return this.d;
    }
    
    public int e() {
        return this.a;
    }
    
    public void e(final int k) {
        this.K = k;
    }
    
    public int M() {
        return this.k;
    }
    
    public void M(final EnumFacing j) {
        this.j = j;
    }
    
    public void M(final int a) {
        this.a = a;
    }
    
    public void M(final float d) {
        this.D = d;
    }
    
    public EnumFacing M() {
        return this.j;
    }
    
    public float M() {
        return this.D;
    }
    
    public Block M() {
        return Minecraft.getMinecraft().world.getBlockState(this.M).getBlock();
    }
    
    public BlockPos M() {
        return this.M;
    }
}
