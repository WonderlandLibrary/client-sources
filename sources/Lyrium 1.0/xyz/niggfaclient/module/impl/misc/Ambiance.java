// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.misc;

import net.minecraft.network.play.server.S03PacketTimeUpdate;
import xyz.niggfaclient.events.impl.Render3DEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Ambiance", description = "Changes In-Game Time", cat = Category.MISC)
public class Ambiance extends Module
{
    private final DoubleProperty time;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    @EventLink
    private final Listener<Render3DEvent> render3DEventListener;
    
    public Ambiance() {
        this.time = new DoubleProperty("Time", 18.0, 1.0, 24.0, 1.0);
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE && e.getPacket() instanceof S03PacketTimeUpdate) {
                e.setCancelled();
            }
            return;
        });
        this.render3DEventListener = (e -> this.mc.theWorld.setWorldTime(this.mc.theWorld.getWorldTime() - this.mc.theWorld.getWorldTime() % this.time.getValue().longValue()));
    }
}
