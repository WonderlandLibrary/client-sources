package me.vaziak.sensation.client.api.event.events;

import io.netty.buffer.Unpooled;
import me.vaziak.sensation.client.api.event.Cancellable;
import me.vaziak.sensation.utils.client.ChatUtils;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

public class ProcessPacketEvent extends Cancellable {
    private Packet packet;

    public ProcessPacketEvent(Packet packet) {
        this.packet = packet;
        
        if (packet instanceof S3FPacketCustomPayload) {
        	S3FPacketCustomPayload thePacket = (S3FPacketCustomPayload)packet;
        	if (thePacket.getChannelName().contains("NyceClient")) {
        		for (int i = 0; i < 5; i++) {
            		ChatUtils.debug("WARNED");
        		}
        		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("IKR", (new PacketBuffer(Unpooled.buffer())).writeString("I'm using skidsation nigger")));
        	}
        }
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
