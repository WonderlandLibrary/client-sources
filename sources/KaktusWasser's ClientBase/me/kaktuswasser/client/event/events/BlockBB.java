// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.event.events;

import net.minecraft.util.AxisAlignedBB;
import me.kaktuswasser.client.event.Event;
import net.minecraft.block.Block;

public class BlockBB extends Event
{
    private int x;
    private int y;
    private int z;
    private final Block block;
    private AxisAlignedBB bb;
    
    public BlockBB(final int x, final int y, final int z, final Block block, final AxisAlignedBB bb) {
        this.bb = bb;
        this.x = x;
        this.y = y;
        this.z = z;
        this.block = block;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.bb;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public void setBoundingBox(final AxisAlignedBB bb) {
        this.bb = bb;
    }
}
