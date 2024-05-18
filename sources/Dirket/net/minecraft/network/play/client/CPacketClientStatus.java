package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketClientStatus implements Packet<INetHandlerPlayServer> {
	private CPacketClientStatus.State status;

	public CPacketClientStatus() {
	}

	public CPacketClientStatus(CPacketClientStatus.State p_i46886_1_) {
		this.status = p_i46886_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.status = buf.readEnumValue(CPacketClientStatus.State.class);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeEnumValue(this.status);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(INetHandlerPlayServer handler) {
		handler.processClientStatus(this);
	}

	public CPacketClientStatus.State getStatus() {
		return this.status;
	}

	public static enum State {
		PERFORM_RESPAWN, REQUEST_STATS, OPEN_INVENTORY_ACHIEVEMENT;
	}
}
