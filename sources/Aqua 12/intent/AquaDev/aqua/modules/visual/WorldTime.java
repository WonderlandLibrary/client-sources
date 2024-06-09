// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import events.listeners.EventPacket;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class WorldTime extends Module
{
    public WorldTime() {
        super("WorldTime", Type.Visual, "WorldTime", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("Time", this, 3.0, 0.0, 24.0, false));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        final float time = (float)Aqua.setmgr.getSetting("WorldTimeTime").getCurrentNumber();
        if (event instanceof EventPacket) {
            WorldTime.mc.theWorld.setWorldTime(Math.round(time * 1000.0f) - 100);
            final Packet p = EventPacket.getPacket();
            if (p instanceof S03PacketTimeUpdate) {
                event.setCancelled(true);
            }
        }
    }
}
