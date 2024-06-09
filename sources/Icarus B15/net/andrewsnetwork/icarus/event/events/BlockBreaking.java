// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.andrewsnetwork.icarus.event.Event;

public class BlockBreaking extends Event
{
    private float multiplier;
    private EnumBlock state;
    private BlockPos pos;
    private EnumFacing side;
    private int delay;
    
    public BlockBreaking(final EnumBlock state, final BlockPos pos, final EnumFacing side) {
        this.side = side;
        this.state = state;
        this.pos = pos;
        this.delay = 4;
        this.multiplier = 1.0f;
    }
    
    public void setState(final EnumBlock state) {
        this.state = state;
    }
    
    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
    
    public void setSide(final EnumFacing side) {
        this.side = side;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public EnumBlock getState() {
        return this.state;
    }
    
    public EnumFacing getSide() {
        return this.side;
    }
    
    public void setMultiplier(final float multiplier) {
        this.multiplier = multiplier;
    }
    
    public float getMutliplier() {
        return this.multiplier;
    }
    
    public void setDelay(final int delay) {
        this.delay = delay;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public enum EnumBlock
    {
        CLICK("CLICK", 0), 
        DAMAGE("DAMAGE", 1), 
        DESTROY("DESTROY", 2);
        
        private EnumBlock(final String s, final int n) {
        }
    }
}
