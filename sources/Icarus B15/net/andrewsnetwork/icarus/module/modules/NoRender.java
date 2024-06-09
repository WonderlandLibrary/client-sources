// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.event.events.RendererItem;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class NoRender extends Module
{
    public NoRender() {
        super("NoRender", -9830551, Category.RENDER);
        this.setTag("No Render");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof RendererItem) {
            final RendererItem event = (RendererItem)e;
            event.setCancelled(true);
        }
    }
}
