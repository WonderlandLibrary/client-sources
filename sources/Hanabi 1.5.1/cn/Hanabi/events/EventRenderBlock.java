package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.*;
import net.minecraft.block.*;

public class EventRenderBlock implements Event
{
    public int x;
    public int y;
    public int z;
    public Block block;
    
    public EventRenderBlock(final int x, final int y, final int z, final Block block) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.block = block;
    }
}
