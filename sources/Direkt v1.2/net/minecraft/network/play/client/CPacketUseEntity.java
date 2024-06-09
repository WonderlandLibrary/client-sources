package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CPacketUseEntity implements Packet<INetHandlerPlayServer> {
	private int entityId;
	private CPacketUseEntity.Action action;
	private Vec3d hitVec;
	private EnumHand hand;

	public CPacketUseEntity() {
	}

	public CPacketUseEntity(Entity entityIn) {
		this.entityId = entityIn.getEntityId();
		this.action = CPacketUseEntity.Action.ATTACK;
	}

	public CPacketUseEntity(Entity entityIn, EnumHand handIn) {
		this.entityId = entityIn.getEntityId();
		this.action = CPacketUseEntity.Action.INTERACT;
		this.hand = handIn;
	}

	public CPacketUseEntity(Entity entityIn, EnumHand handIn, Vec3d hitVecIn) {
		this.entityId = entityIn.getEntityId();
		this.action = CPacketUseEntity.Action.INTERACT_AT;
		this.hand = handIn;
		this.hitVec = hitVecIn;
	}

	/**
	 * Reads the raw packet data from the data stream.
	 */
	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		this.entityId = buf.readVarIntFromBuffer();
		this.action = buf.readEnumValue(CPacketUseEntity.Action.class);

		if (this.action == CPacketUseEntity.Action.INTERACT_AT) {
			this.hitVec = new Vec3d(buf.readFloat(), buf.readFloat(), buf.readFloat());
		}

		if ((this.action == CPacketUseEntity.Action.INTERACT) || (this.action == CPacketUseEntity.Action.INTERACT_AT)) {
			this.hand = buf.readEnumValue(EnumHand.class);
		}
	}

	/**
	 * Writes the raw packet data to the data stream.
	 */
	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
		buf.writeVarIntToBuffer(this.entityId);
		buf.writeEnumValue(this.action);

		if (this.action == CPacketUseEntity.Action.INTERACT_AT) {
			buf.writeFloat((float) this.hitVec.xCoord);
			buf.writeFloat((float) this.hitVec.yCoord);
			buf.writeFloat((float) this.hitVec.zCoord);
		}

		if ((this.action == CPacketUseEntity.Action.INTERACT) || (this.action == CPacketUseEntity.Action.INTERACT_AT)) {
			buf.writeEnumValue(this.hand);
		}
	}

	/**
	 * Passes this Packet on to the NetHandler for processing.
	 */
	@Override
	public void processPacket(INetHandlerPlayServer handler) {
		handler.processUseEntity(this);
	}

	public Entity getEntityFromWorld(World worldIn) {
		return worldIn.getEntityByID(this.entityId);
	}

	public CPacketUseEntity.Action getAction() {
		return this.action;
	}

	public EnumHand getHand() {
		return this.hand;
	}

	public Vec3d getHitVec() {
		return this.hitVec;
	}

	public static enum Action {
		INTERACT, ATTACK, INTERACT_AT;
	}
}
