package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.entity.item.EntityPainting;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class SPacketSpawnPainting implements Packet<INetHandlerPlayClient> {
	private int entityID;
	private UUID uniqueId;
	private BlockPos position;
	private EnumFacing facing;
	private String title;

	public SPacketSpawnPainting() {
	}

	public SPacketSpawnPainting(EntityPainting painting) {
		this.entityID = painting.getEntityId();
		this.uniqueId = painting.getUniqueID();
		this.position = painting.getHangingPosition();
		this.facing = painting.facingDirection;
		this.title = painting.art.title;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.entityID = buf.readVarIntFromBuffer();
		this.uniqueId = buf.readUuid();
		this.title = buf.readStringFromBuffer(EntityPainting.EnumArt.MAX_NAME_LENGTH);
		this.position = buf.readBlockPos();
		this.facing = EnumFacing.getHorizontal(buf.readUnsignedByte());
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeVarIntToBuffer(this.entityID);
		buf.writeUuid(this.uniqueId);
		buf.writeString(this.title);
		buf.writeBlockPos(this.position);
		buf.writeByte(this.facing.getHorizontalIndex());
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(INetHandlerPlayClient handler) {
		handler.handleSpawnPainting(this);
	}

	public int getEntityID() {
		return this.entityID;
	}

	public UUID getUniqueId() {
		return this.uniqueId;
	}

	public BlockPos getPosition() {
		return this.position;
	}

	public EnumFacing getFacing() {
		return this.facing;
	}

	public String getTitle() {
		return this.title;
	}
}
