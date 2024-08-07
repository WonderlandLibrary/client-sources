package net.shoreline.client.impl.event.entity;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Cancelable
public class VelocityMultiplierEvent extends Event
{
    //
    private final BlockState state;
    
    /**
     *
     *
     * @param state
     */
    public VelocityMultiplierEvent(BlockState state)
    {
        this.state = state;
    }
    
    /**
     *
     * @return
     */
    public Block getBlock()
    {
        return state.getBlock();
    }
}
