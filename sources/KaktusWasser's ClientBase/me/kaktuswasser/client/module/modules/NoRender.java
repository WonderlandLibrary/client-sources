// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.RendererItem;
import me.kaktuswasser.client.module.Module;

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
