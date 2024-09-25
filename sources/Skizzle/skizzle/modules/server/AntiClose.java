/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.server;

import net.minecraft.network.play.server.S2EPacketCloseWindow;
import skizzle.events.Event;
import skizzle.events.listeners.EventPacket;
import skizzle.modules.Module;

public class AntiClose
extends Module {
    @Override
    public void onEvent(Event Nigga) {
        EventPacket Nigga2;
        AntiClose Nigga3;
        if (Nigga instanceof EventPacket && Nigga3.mc.thePlayer != null && (Nigga2 = (EventPacket)Nigga).getPacket() instanceof S2EPacketCloseWindow) {
            Nigga.setCancelled(true);
        }
    }

    public AntiClose() {
        super(Qprot0.0("\u68e7\u71c5\u53ae\ua7ed\u4236\ued70\u8c20\u0490\u5707"), 0, Module.Category.SERVER);
        AntiClose Nigga;
    }

    public static {
        throw throwable;
    }
}

