package net.futureclient.client.modules.render.tabgui;

import net.minecraft.client.renderer.GlStateManager;
import net.futureclient.client.qa;
import net.futureclient.client.pg;
import net.futureclient.client.gD;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.TabGui;
import net.futureclient.client.be;
import net.futureclient.client.n;

public class Listener1 extends n<be>
{
    public final TabGui k;
    
    public Listener1(final TabGui k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((be)event);
    }
    
    @Override
    public void M(final be be) {
        final gD gd = (gD)pg.M().M().M((Class)gD.class);
        if (this.k.D == null) {
            this.k.D = new qa();
        }
        if (TabGui.getMinecraft().gameSettings.showDebugInfo) {
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        this.k.D.M(2 + (int)this.k.M, ((boolean)gd.H.M()) ? (gd.E.M() ? (gd.p.M() + 3) : 12) : 3);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
