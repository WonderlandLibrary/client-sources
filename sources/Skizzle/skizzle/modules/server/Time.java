/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.server;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import skizzle.events.Event;
import skizzle.events.listeners.EventPacket;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;

public class Time
extends Module {
    public NumberSetting time = new NumberSetting(Qprot0.0("\uce99\u71c2\uf5cc\ua7e1"), 0.0, 0.0, 24000.0, 100.0);

    public Time() {
        super(Qprot0.0("\uce99\u71c2\uf5cc\ua7e1"), 0, Module.Category.SERVER);
        Time Nigga;
        Nigga.addSettings(Nigga.time);
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        EventPacket Nigga2;
        Time Nigga3;
        if (Minecraft.theWorld != null) {
            Minecraft.theWorld.setWorldTime((long)Nigga3.time.getValue());
        }
        if (Nigga instanceof EventPacket && Nigga3.mc.thePlayer != null && (Nigga2 = (EventPacket)Nigga).getPacket() instanceof S03PacketTimeUpdate) {
            Nigga2.setCancelled(true);
        }
    }
}

