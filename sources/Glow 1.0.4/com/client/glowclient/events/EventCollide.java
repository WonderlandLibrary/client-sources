package com.client.glowclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

@Cancelable
public class EventCollide extends Event
{
    private final List<AxisAlignedBB> f;
    private final AxisAlignedBB M;
    private final Block G;
    private final boolean d;
    private final World L;
    private final IBlockState A;
    private final Entity B;
    private final BlockPos b;
    
    public List<AxisAlignedBB> getCollidingBoxes() {
        return this.f;
    }
    
    public World getWorld() {
        return this.L;
    }
    
    public Entity getEntity() {
        return this.B;
    }
    
    public EventCollide(final Block g, final IBlockState a, final World l, final BlockPos b, final AxisAlignedBB m, final List<AxisAlignedBB> f, final Entity b2, final boolean d) {
        super();
        this.G = g;
        this.A = a;
        this.L = l;
        this.b = b;
        this.M = m;
        this.f = f;
        this.B = b2;
        this.d = d;
    }
    
    public Block getBlock() {
        return this.G;
    }
    
    public AxisAlignedBB getEntityBox() {
        return this.M;
    }
    
    public boolean isBool() {
        return this.d;
    }
    
    public BlockPos getPos() {
        return this.b;
    }
    
    public IBlockState getState() {
        return this.A;
    }
}
