/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.server;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import skizzle.events.Event;
import skizzle.events.listeners.EventPacket;
import skizzle.modules.Module;

public class NoRotations
extends Module {
    public NoRotations() {
        super(Qprot0.0("\u142a\u71c4\u2f4a\ua7eb\u07cf\u91bf\u8c3b\u7848\u570d\uac9f\u1b80"), 0, Module.Category.SERVER);
        NoRotations Nigga;
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        EventPacket Nigga2;
        NoRotations Nigga3;
        if (Nigga instanceof EventPacket && Nigga3.mc.thePlayer != null && (Nigga2 = (EventPacket)Nigga).getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook Nigga4 = (S08PacketPlayerPosLook)Nigga2.getPacket();
            Nigga4.yaw = Nigga3.mc.thePlayer.rotationYaw;
            Nigga4.pitch = Nigga3.mc.thePlayer.rotationPitch;
        }
    }
}

