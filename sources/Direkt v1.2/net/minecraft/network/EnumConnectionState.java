package net.minecraft.network;

import java.util.Map;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.network.login.server.SPacketEnableCompression;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import net.minecraft.network.login.server.SPacketLoginSuccess;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.client.CPacketServerQuery;
import net.minecraft.network.status.server.SPacketPong;
import net.minecraft.network.status.server.SPacketServerInfo;

public enum EnumConnectionState {
	HANDSHAKING(-1) {
		{
			this.registerPacket(EnumPacketDirection.SERVERBOUND, C00Handshake.class);
		}
	},
	PLAY(0) {
		{
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSpawnObject.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSpawnExperienceOrb.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSpawnGlobalEntity.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSpawnMob.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPainting.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPlayer.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketAnimation.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketStatistics.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketBlockBreakAnim.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketUpdateTileEntity.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketBlockAction.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketBlockChange.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketUpdateBossInfo.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketServerDifficulty.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketTabComplete.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketChat.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketMultiBlockChange.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketConfirmTransaction.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketCloseWindow.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketOpenWindow.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketWindowItems.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketWindowProperty.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSetSlot.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketCooldown.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketCustomPayload.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketCustomSound.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketDisconnect.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntityStatus.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketExplosion.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketUnloadChunk.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketChangeGameState.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketKeepAlive.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketChunkData.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEffect.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketParticles.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketJoinGame.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketMaps.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S15PacketEntityRelMove.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S17PacketEntityLookMove.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S16PacketEntityLook.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntity.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketMoveVehicle.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSignEditorOpen.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketPlayerAbilities.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketCombatEvent.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketPlayerListItem.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketPlayerPosLook.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketUseBed.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketDestroyEntities.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketRemoveEntityEffect.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketResourcePackSend.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketRespawn.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntityHeadLook.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketWorldBorder.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketCamera.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketHeldItemChange.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketDisplayObjective.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntityMetadata.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntityAttach.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntityVelocity.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntityEquipment.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSetExperience.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketUpdateHealth.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketScoreboardObjective.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSetPassengers.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketTeams.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketUpdateScore.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPosition.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketTimeUpdate.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketTitle.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketSoundEffect.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketPlayerListHeaderFooter.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketCollectItem.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntityTeleport.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntityProperties.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEntityEffect.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketConfirmTeleport.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketTabComplete.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketChatMessage.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketClientStatus.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketClientSettings.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketConfirmTransaction.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketEnchantItem.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketClickWindow.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketCloseWindow.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketCustomPayload.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketUseEntity.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketKeepAlive.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketPlayer.Position.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketPlayer.PositionRotation.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketPlayer.Rotation.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketPlayer.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketVehicleMove.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketSteerBoat.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketPlayerAbilities.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketPlayerDigging.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketEntityAction.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketInput.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketResourcePackStatus.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketHeldItemChange.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketCreativeInventoryAction.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketUpdateSign.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketAnimation.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketSpectate.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketPlayerTryUseItemOnBlock.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketPlayerTryUseItem.class);
		}
	},
	STATUS(1) {
		{
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketServerQuery.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketServerInfo.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketPing.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketPong.class);
		}
	},
	LOGIN(2) {
		{
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, net.minecraft.network.login.server.SPacketDisconnect.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEncryptionRequest.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketLoginSuccess.class);
			this.registerPacket(EnumPacketDirection.CLIENTBOUND, SPacketEnableCompression.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketLoginStart.class);
			this.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketEncryptionResponse.class);
		}
	};

	private static final EnumConnectionState[] STATES_BY_ID = new EnumConnectionState[4];
	private static final Map<Class<? extends Packet<?>>, EnumConnectionState> STATES_BY_CLASS = Maps.<Class<? extends Packet<?>>, EnumConnectionState> newHashMap();
	private final int id;
	private final Map<EnumPacketDirection, BiMap<Integer, Class<? extends Packet<?>>>> directionMaps;

	private EnumConnectionState(int protocolId) {
		this.directionMaps = Maps.newEnumMap(EnumPacketDirection.class);
		this.id = protocolId;
	}

	protected EnumConnectionState registerPacket(EnumPacketDirection direction, Class<? extends Packet<?>> packetClass) {
		BiMap<Integer, Class<? extends Packet<?>>> bimap = this.directionMaps.get(direction);

		if (bimap == null) {
			bimap = HashBiMap.<Integer, Class<? extends Packet<?>>> create();
			this.directionMaps.put(direction, bimap);
		}

		if (bimap.containsValue(packetClass)) {
			String s = direction + " packet " + packetClass + " is already known to ID " + bimap.inverse().get(packetClass);
			LogManager.getLogger().fatal(s);
			throw new IllegalArgumentException(s);
		} else {
			bimap.put(Integer.valueOf(bimap.size()), packetClass);
			return this;
		}
	}

	public Integer getPacketId(EnumPacketDirection direction, Packet<?> packetIn) {
		return (Integer) ((BiMap) this.directionMaps.get(direction)).inverse().get(packetIn.getClass());
	}

	@Nullable
	public Packet<?> getPacket(EnumPacketDirection direction, int packetId) throws InstantiationException, IllegalAccessException {
		Class<? extends Packet<?>> oclass = (Class) ((BiMap) this.directionMaps.get(direction)).get(Integer.valueOf(packetId));
		return oclass == null ? null : (Packet) oclass.newInstance();
	}

	public int getId() {
		return this.id;
	}

	public static EnumConnectionState getById(int stateId) {
		return (stateId >= -1) && (stateId <= 2) ? STATES_BY_ID[stateId - -1] : null;
	}

	public static EnumConnectionState getFromPacket(Packet<?> packetIn) {
		return STATES_BY_CLASS.get(packetIn.getClass());
	}

	static {
		for (EnumConnectionState enumconnectionstate : values()) {
			int i = enumconnectionstate.getId();

			if ((i < -1) || (i > 2)) { throw new Error("Invalid protocol ID " + Integer.toString(i)); }

			STATES_BY_ID[i - -1] = enumconnectionstate;

			for (EnumPacketDirection enumpacketdirection : enumconnectionstate.directionMaps.keySet()) {
				for (Class<? extends Packet<?>> oclass : (enumconnectionstate.directionMaps.get(enumpacketdirection)).values()) {
					if (STATES_BY_CLASS.containsKey(oclass) && (STATES_BY_CLASS.get(oclass) != enumconnectionstate)) { throw new Error(
							"Packet " + oclass + " is already assigned to protocol " + STATES_BY_CLASS.get(oclass) + " - can\'t reassign to " + enumconnectionstate); }

					try {
						oclass.newInstance();
					} catch (Throwable var10) {
						throw new Error("Packet " + oclass + " fails instantiation checks! " + oclass);
					}

					STATES_BY_CLASS.put(oclass, enumconnectionstate);
				}
			}
		}
	}
}
