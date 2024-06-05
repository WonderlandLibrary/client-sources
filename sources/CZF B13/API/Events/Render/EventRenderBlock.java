package gq.vapu.czfclient.API.Events.Render;

import gq.vapu.czfclient.API.eventapi.events.Event;
import net.minecraft.block.Block;

public class EventRenderBlock implements Event {
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