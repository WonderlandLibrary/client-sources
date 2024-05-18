/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.movement;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.movement.Jesus;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketSendEvent;
import me.thekirkayt.utils.LiquidUtils;
import me.thekirkayt.utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

@Module.Mod
public class AntiWater
extends Module {
    private Minecraft mc = Minecraft.getMinecraft();
    public boolean damage;
    public static boolean shouldOffsetPacket;
    private double stage;
    private TimerUtil time = new TimerUtil();

    @Override
    public void enable() {
        this.stage = 1.0;
        if (this.damage && Minecraft.thePlayer != null) {
            int i = 0;
            while ((double)i < 80.0) {
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.049, Minecraft.thePlayer.posZ, false));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                ++i;
            }
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
        }
        super.enable();
    }

    @EventTarget
    private void onPacketSend(PacketSendEvent event) {
        event.getState();
        if (event.getState() == Event.State.PRE && event.getPacket() instanceof C03PacketPlayer && LiquidUtils.isOnLiquid()) {
            C03PacketPlayer packet = (C03PacketPlayer)event.getPacket();
            Jesus.shouldOffsetPacket = !shouldOffsetPacket;
            boolean bl = Jesus.shouldOffsetPacket;
            if (shouldOffsetPacket) {
                packet.y -= 1.0E-6;
            }
        }
    }
}

