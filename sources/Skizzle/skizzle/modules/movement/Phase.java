/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;

public class Phase
extends Module {
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u3f7a\u71c4\u042f\ua7e1"), Qprot0.0("\u3f65\u71ce\u042f\ua7e1\u1ef7\ubae6\u8c36"), Qprot0.0("\u3f65\u71ce\u042f\ua7e1\u1ef7\ubae6\u8c36"));

    public Phase() {
        super(Qprot0.0("\u3f67\u71c3\u042a\ua7f7\u1ee1"), 0, Module.Category.MOVEMENT);
        Phase Nigga;
        Nigga.addSettings(Nigga.mode);
    }

    @Override
    public void onEnable() {
        Phase Nigga;
        if (Nigga.mode.getMode().equals(Qprot0.0("\u3f65\u71ce\u042f\u59d1\u14c7\ubae6\u8c36")) && Nigga.mc.thePlayer.isCollidedHorizontally) {
            Nigga.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(Nigga.mc.thePlayer.posX, Nigga.mc.thePlayer.posY + -1.0E-8, Nigga.mc.thePlayer.posZ, Nigga.mc.thePlayer.rotationYaw, Nigga.mc.thePlayer.rotationPitch, false));
            Nigga.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(Nigga.mc.thePlayer.posX, Nigga.mc.thePlayer.posY + -1.0E-8, Nigga.mc.thePlayer.posZ, Nigga.mc.thePlayer.rotationYaw, Nigga.mc.thePlayer.rotationPitch, false));
            Nigga.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(Nigga.mc.thePlayer.posX, Nigga.mc.thePlayer.posY - 1.0, Nigga.mc.thePlayer.posZ, Nigga.mc.thePlayer.rotationYaw, Nigga.mc.thePlayer.rotationPitch, false));
            Nigga.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(Nigga.mc.thePlayer.posX, Nigga.mc.thePlayer.posY - 1.0, Nigga.mc.thePlayer.posZ, Nigga.mc.thePlayer.rotationYaw, Nigga.mc.thePlayer.rotationPitch, false));
        }
    }

    public static {
        throw throwable;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEvent(Event Nigga) {
        Phase Nigga2;
        if (Nigga instanceof EventUpdate && Nigga2.mode.getMode().equals(Qprot0.0("\u3f65\u71ce\u042f\ue268\u5b0c\ubae6\u8c36"))) {
            Nigga2.toggle();
        }
    }
}

