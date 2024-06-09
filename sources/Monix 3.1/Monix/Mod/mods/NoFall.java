/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Event.EventTarget;
import Monix.Event.events.EventUpdate;
import Monix.Mod.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall
extends Mod {
    public NoFall() {
        super("NoFall", "NoFall", 0, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (NoFall.mc.thePlayer.fallDistance >= 2.0f) {
            NoFall.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
    }
}

