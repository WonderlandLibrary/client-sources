// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.event.events;

import net.minecraft.util.BlockPos;
import me.kaktuswasser.client.event.Event;
import net.minecraft.block.Block;

public class BlockRender extends Event
{
    private final Block block;
    private final BlockPos pos;
    
    public BlockRender(final Block block, final BlockPos pos) {
        this.block = block;
        this.pos = pos;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
}
