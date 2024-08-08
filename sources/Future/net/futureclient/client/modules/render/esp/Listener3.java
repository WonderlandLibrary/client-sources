package net.futureclient.client.modules.render.esp;

import net.futureclient.client.ed;
import net.minecraft.client.shader.Framebuffer;
import net.futureclient.client.events.EventWorld;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.ESP;
import net.futureclient.client.fF;
import net.futureclient.client.n;

public class Listener3 extends n<fF>
{
    public final ESP k;
    
    public Listener3(final ESP k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventWorld)event);
    }
    
    public void M(final EventWorld eventWorld) {
        if (ESP.M(this.k) != null) {
            ESP.M(this.k).unbindFramebuffer();
        }
        ESP.M(this.k, (Framebuffer)null);
        if (ESP.M(this.k) != null) {
            ESP.M(this.k).e();
        }
        ESP.M(this.k, (ed.Xc)null);
    }
}
