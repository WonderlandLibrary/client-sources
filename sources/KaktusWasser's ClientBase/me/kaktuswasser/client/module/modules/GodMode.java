// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import java.util.Iterator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.item.ItemSword;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.CopyOnWriteArrayList;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;
import net.minecraft.network.Packet;
import java.util.List;

public class GodMode extends Module
{
    private List<Packet> packetArray;
    private boolean sendPackets;
    
    public GodMode() {
        super("GodMode", -6632142, Category.EXPLOITS);
        this.packetArray = new CopyOnWriteArrayList<Packet>();
        this.setTag("God Mode");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof SentPacket) {
            final SentPacket event = (SentPacket)e;
            if (!Client.getModuleManager().getModuleByName("phase").isEnabled()) {
                try {
                    if (GodMode.mc.thePlayer.getCurrentEquippedItem().getItem() != null && GodMode.mc.thePlayer != null && GodMode.mc.thePlayer.getCurrentEquippedItem() != null && GodMode.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                        if ((event.getPacket() instanceof C01PacketChatMessage || event.getPacket() instanceof C00PacketKeepAlive || event.getPacket() instanceof C16PacketClientStatus || event.getPacket() instanceof C09PacketHeldItemChange || event.getPacket() instanceof C0APacketAnimation) && event.getPacket() instanceof C02PacketUseEntity) {
                            final C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
                            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                                return;
                            }
                        }
                        if (!this.sendPackets) {
                            this.packetArray.add(event.getPacket());
                            event.setCancelled(true);
                        }
                    }
                }
                catch (Exception ex2) {}
            }
        }
        else if (e instanceof PreMotion && GodMode.mc.thePlayer != null && Client.getModuleManager().getModuleByName("phase").isEnabled()) {
            try {
                if (GodMode.mc.thePlayer.getCurrentEquippedItem().getItem() != null && GodMode.mc.thePlayer != null && GodMode.mc.thePlayer.getCurrentEquippedItem() != null && GodMode.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                    if (this.packetArray.size() >= 25) {
                        this.packetArray.clear();
                        return;
                    }
                    this.sendPackets = true;
                    if (this.sendPackets) {
                        GodMode.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(GodMode.mc.thePlayer.inventory.getCurrentItem()));
                        for (final Packet packet2 : this.packetArray) {
                            GodMode.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            GodMode.mc.getNetHandler().addToSendQueue(packet2);
                        }
                        this.packetArray.clear();
                        GodMode.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(GodMode.mc.thePlayer.inventory.getCurrentItem()));
                        this.sendPackets = false;
                    }
                }
            }
            catch (Exception ex3) {}
        }
    }
    
    @Override
    public void onDisabled() {
        if (GodMode.mc.thePlayer != null) {
            GodMode.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}
