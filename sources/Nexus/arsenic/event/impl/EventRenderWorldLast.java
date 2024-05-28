package arsenic.event.impl;

import arsenic.event.types.Event;
import net.minecraft.client.renderer.RenderGlobal;

public class EventRenderWorldLast implements Event {

    public final RenderGlobal context;
    public final float partialTicks;
    public EventRenderWorldLast(RenderGlobal context, float partialTicks)
    {
        this.context = context;
        this.partialTicks = partialTicks;
    }
}
