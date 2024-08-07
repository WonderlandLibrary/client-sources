package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;

public class SPacketPlayerListHeaderFooter implements Packet<INetHandlerPlayClient> {
	private ITextComponent header;
	private ITextComponent footer;

	public SPacketPlayerListHeaderFooter() {
	}

	public SPacketPlayerListHeaderFooter(ITextComponent headerIn) {
		this.header = headerIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.header = buf.readTextComponent();
		this.footer = buf.readTextComponent();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeTextComponent(this.header);
		buf.writeTextComponent(this.footer);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(INetHandlerPlayClient handler) {
		handler.handlePlayerListHeaderFooter(this);
	}

	public ITextComponent getHeader() {
		return this.header;
	}

	public ITextComponent getFooter() {
		return this.footer;
	}
}
