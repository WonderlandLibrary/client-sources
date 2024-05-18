package com.kilo.mod.all;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S14PacketEntity;

import com.kilo.mod.Category;
import com.kilo.mod.Module;

public class NoViewReset extends Module {
	
	public NoViewReset(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public Packet onPacketReceive(Packet packet) {
		if (packet instanceof S08PacketPlayerPosLook) {
			S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook) packet;
			pac.field_148936_d = mc.thePlayer.rotationYaw;
			pac.field_148937_e = mc.thePlayer.rotationPitch;
			return pac;
		}
		return packet;
	}
}
