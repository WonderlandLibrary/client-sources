package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketCooldown implements Packet<INetHandlerPlayClient> {
	private Item item;
	private int ticks;

	public SPacketCooldown() {
	}

	public SPacketCooldown(Item itemIn, int ticksIn) {
		this.item = itemIn;
		this.ticks = ticksIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.item = Item.getItemById(buf.readVarIntFromBuffer());
		this.ticks = buf.readVarIntFromBuffer();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeVarIntToBuffer(Item.getIdFromItem(this.item));
		buf.writeVarIntToBuffer(this.ticks);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(INetHandlerPlayClient handler) {
		handler.handleCooldown(this);
	}

	public Item getItem() {
		return this.item;
	}

	public int getTicks() {
		return this.ticks;
	}
}
