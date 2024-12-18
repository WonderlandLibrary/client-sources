package net.minecraft.network.play.server;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class SPacketEntityHeadLook implements Packet<INetHandlerPlayClient> {
	private int entityId;
	private byte yaw;

	public SPacketEntityHeadLook() {
	}

	public SPacketEntityHeadLook(Entity entityIn, byte yawIn) {
		this.entityId = entityIn.getEntityId();
		this.yaw = yawIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.entityId = buf.readVarIntFromBuffer();
		this.yaw = buf.readByte();
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeVarIntToBuffer(this.entityId);
		buf.writeByte(this.yaw);
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(INetHandlerPlayClient handler) {
		handler.handleEntityHeadLook(this);
	}

	public Entity getEntity(World worldIn) {
		return worldIn.getEntityByID(this.entityId);
	}

	public byte getYaw() {
		return this.yaw;
	}
}
