package me.gishreload.yukon.events;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;

public class PacketEvent extends Event implements Cancellable {
	private Packet packet;
	private Minecraft minecraft;
	private EntityPlayerMP player;
	private boolean cancelled;

	public PacketEvent(Packet packet, Minecraft minecraft, EntityPlayerMP player) {
		this.packet = packet;
		this.minecraft = minecraft;
		this.player = player;
	}

	public Packet getPacket() {
		return packet;
	}

	public Minecraft getMinecraft() {
		return minecraft;
	}

	public EntityPlayerMP getPlayer() {
		return player;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
