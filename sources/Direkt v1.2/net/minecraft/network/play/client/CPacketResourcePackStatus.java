package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketResourcePackStatus implements Packet<INetHandlerPlayServer> {
	private CPacketResourcePackStatus.Action action;

	public CPacketResourcePackStatus() {
	}

	public CPacketResourcePackStatus(CPacketResourcePackStatus.Action p_i47156_1_) {
		this.action = p_i47156_1_;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.action = buf.readEnumValue(CPacketResourcePackStatus.Action.class);
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeEnumValue(this.action);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(INetHandlerPlayServer handler) {
		handler.handleResourcePackStatus(this);
	}

	public static enum Action {
		SUCCESSFULLY_LOADED, DECLINED, FAILED_DOWNLOAD, ACCEPTED;
	}
}
