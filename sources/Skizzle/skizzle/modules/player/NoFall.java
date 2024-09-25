/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.player;

import net.minecraft.network.play.client.C03PacketPlayer;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;

public class NoFall
extends Module {
    public NoFall() {
        super(Qprot0.0("\u1a50\u71c4\u2114\ua7e5\u3581\u9fc8"), 0, Module.Category.PLAYER);
        NoFall Nigga;
    }

    @Override
    public void onEvent(Event Nigga) {
        NoFall Nigga2;
        if (Nigga instanceof EventUpdate && Nigga.isPre() && !Client.ghostMode && Nigga2.mc.thePlayer.fallDistance > Float.intBitsToFloat(1.0617856E9f ^ 0x7F498FE7)) {
            Nigga2.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
    }

    public static {
        throw throwable;
    }
}

