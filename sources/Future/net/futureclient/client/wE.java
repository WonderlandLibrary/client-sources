package net.futureclient.client;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.futureclient.client.events.Event;

public class wE extends Event
{
    private int j;
    private Entity K;
    private int M;
    private int d;
    private Block a;
    private BlockPos D;
    private AxisAlignedBB k;
    
    public wE(final Block a, final AxisAlignedBB k, final BlockPos d, final Entity i) {
        super();
        this.a = a;
        this.k = k;
        this.D = d;
        this.d = d.getX();
        this.M = d.getY();
        this.j = d.getZ();
        this.K = i;
    }
    
    public double b() {
        return this.d;
    }
    
    public double e() {
        return this.j;
    }
    
    public void M(final Block a) {
        this.a = a;
    }
    
    public double M() {
        return this.M;
    }
    
    public void M(final BlockPos d) {
        this.D = d;
    }
    
    public void M(final AxisAlignedBB k) {
        this.k = k;
    }
    
    public AxisAlignedBB M() {
        return this.k;
    }
    
    public Entity M() {
        return this.K;
    }
    
    public Block M() {
        return this.a;
    }
    
    public BlockPos M() {
        return this.D;
    }
}
