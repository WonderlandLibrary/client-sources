package xyz.mathax.mathaxclient.systems.modules.chat;

import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.systems.modules.Categories;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class AntiSale extends Module {
    private TitleS2CPacket packet;

    @EventHandler
    public void onPacketSend(PacketEvent.Receive event) {
        if (event.packet instanceof TitleS2CPacket) {
            packet = (TitleS2CPacket) event.packet;
            if (packet.getTitle().getString().contains("SALE") || packet.getTitle().getString().contains("sale") || packet.getTitle().getString().contains("Sale")) {
                event.cancel();
            }
        }
        if (event.packet instanceof GameMessageS2CPacket) {
            GameMessageS2CPacket packet = (GameMessageS2CPacket) event.packet;
            if (packet.toString().contains("SALE") || packet.toString().contains("sale") || packet.toString().contains("Sale")) {
                event.cancel();
            }
        }
    }
    public AntiSale(Category category) {
        super(category, "Anti Sale", "AD blocker For BlockGame");
    }
}

