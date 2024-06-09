package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumHand;

public class CPacketPlayerTryUseItem implements Packet<INetHandlerPlayServer> {
	private EnumHand hand;

	public CPacketPlayerTryUseItem() {
	}

	public CPacketPlayerTryUseItem(EnumHand handIn) {
		this.hand = handIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.hand = buf.readEnumValue(EnumHand.class);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeEnumValue(this.hand);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(INetHandlerPlayServer handler) {
		handler.processPlayerBlockPlacement(this);
	}

	public EnumHand getHand() {
		return this.hand;
	}
}
