package com.viaversion.viaversion.protocols.protocol1_20_2to1_20;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ServerboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.rewriter.BlockItemPacketRewriter1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.rewriter.EntityPacketRewriter1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.storage.ConfigurationState;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.storage.LastResourcePack;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.storage.LastTags;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.util.Key;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class Protocol1_20_2To1_20
   extends AbstractProtocol<ClientboundPackets1_19_4, ClientboundPackets1_20_2, ServerboundPackets1_19_4, ServerboundPackets1_20_2> {
   public static final MappingData MAPPINGS = new MappingDataBase("1.20", "1.20.2");
   private final EntityPacketRewriter1_20_2 entityPacketRewriter = new EntityPacketRewriter1_20_2(this);
   private final BlockItemPacketRewriter1_20_2 itemPacketRewriter = new BlockItemPacketRewriter1_20_2(this);

   public Protocol1_20_2To1_20() {
      super(ClientboundPackets1_19_4.class, ClientboundPackets1_20_2.class, ServerboundPackets1_19_4.class, ServerboundPackets1_20_2.class);
   }

   @Override
   protected void registerPackets() {
      super.registerPackets();
      SoundRewriter<ClientboundPackets1_19_4> soundRewriter = new SoundRewriter<>(this);
      soundRewriter.register1_19_3Sound(ClientboundPackets1_19_4.SOUND);
      soundRewriter.registerEntitySound(ClientboundPackets1_19_4.ENTITY_SOUND);
      this.registerClientbound(ClientboundPackets1_19_4.PLUGIN_MESSAGE, this::sanitizeCustomPayload);
      this.registerServerbound(ServerboundPackets1_20_2.PLUGIN_MESSAGE, this::sanitizeCustomPayload);
      this.registerClientbound(ClientboundPackets1_19_4.RESOURCE_PACK, wrapper -> {
         String url = wrapper.passthrough(Type.STRING);
         String hash = wrapper.passthrough(Type.STRING);
         boolean required = wrapper.passthrough(Type.BOOLEAN);
         JsonElement prompt = wrapper.passthrough(Type.OPTIONAL_COMPONENT);
         wrapper.user().put(new LastResourcePack(url, hash, required, prompt));
      });
      this.registerClientbound(ClientboundPackets1_19_4.TAGS, wrapper -> wrapper.user().put(new LastTags(wrapper)));
      this.registerClientbound(
         State.CONFIGURATION,
         ClientboundConfigurationPackets1_20_2.UPDATE_TAGS.getId(),
         ClientboundConfigurationPackets1_20_2.UPDATE_TAGS.getId(),
         wrapper -> wrapper.user().put(new LastTags(wrapper))
      );
      this.registerClientbound(ClientboundPackets1_19_4.DISPLAY_SCOREBOARD, wrapper -> {
         byte slot = wrapper.read(Type.BYTE);
         wrapper.write(Type.VAR_INT, Integer.valueOf(slot));
      });
      this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), wrapper -> {
         wrapper.passthrough(Type.STRING);
         UUID uuid = wrapper.read(Type.UUID);
         wrapper.write(Type.OPTIONAL_UUID, uuid);
      });
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE.getId(), ClientboundLoginPackets.GAME_PROFILE.getId(), wrapper -> {
         wrapper.user().get(ConfigurationState.class).setBridgePhase(ConfigurationState.BridgePhase.PROFILE_SENT);
         wrapper.user().getProtocolInfo().setServerState(State.PLAY);
      });
      this.registerServerbound(State.LOGIN, ServerboundLoginPackets.LOGIN_ACKNOWLEDGED.getId(), -1, wrapper -> {
         wrapper.cancel();
         wrapper.user().getProtocolInfo().setServerState(State.PLAY);
         ConfigurationState configurationState = wrapper.user().get(ConfigurationState.class);
         configurationState.setBridgePhase(ConfigurationState.BridgePhase.CONFIGURATION);
         configurationState.sendQueuedPackets(wrapper.user());
      });
      this.registerServerbound(State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.FINISH_CONFIGURATION.getId(), -1, wrapper -> {
         wrapper.cancel();
         wrapper.user().getProtocolInfo().setClientState(State.PLAY);
         ConfigurationState configurationState = wrapper.user().get(ConfigurationState.class);
         configurationState.setBridgePhase(ConfigurationState.BridgePhase.NONE);
         configurationState.sendQueuedPackets(wrapper.user());
         configurationState.clear();
      });
      this.registerServerbound(
         State.CONFIGURATION,
         ServerboundConfigurationPackets1_20_2.CLIENT_INFORMATION.getId(),
         -1,
         wrapper -> {
            ConfigurationState.ClientInformation clientInformation = new ConfigurationState.ClientInformation(
               wrapper.read(Type.STRING),
               wrapper.read(Type.BYTE),
               wrapper.read(Type.VAR_INT),
               wrapper.read(Type.BOOLEAN),
               wrapper.read(Type.UNSIGNED_BYTE),
               wrapper.read(Type.VAR_INT),
               wrapper.read(Type.BOOLEAN),
               wrapper.read(Type.BOOLEAN)
            );
            ConfigurationState configurationState = wrapper.user().get(ConfigurationState.class);
            configurationState.setClientInformation(clientInformation);
            wrapper.cancel();
         }
      );
      this.registerServerbound(
         State.CONFIGURATION,
         ServerboundConfigurationPackets1_20_2.CUSTOM_PAYLOAD.getId(),
         -1,
         this.queueServerboundPacket(ServerboundPackets1_20_2.PLUGIN_MESSAGE)
      );
      this.registerServerbound(
         State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.KEEP_ALIVE.getId(), -1, this.queueServerboundPacket(ServerboundPackets1_20_2.KEEP_ALIVE)
      );
      this.registerServerbound(
         State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.PONG.getId(), -1, this.queueServerboundPacket(ServerboundPackets1_20_2.PONG)
      );
      this.registerServerbound(State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.RESOURCE_PACK.getId(), -1, PacketWrapper::cancel);
      this.cancelClientbound(ClientboundPackets1_19_4.UPDATE_ENABLED_FEATURES);
      this.registerServerbound(ServerboundPackets1_20_2.CONFIGURATION_ACKNOWLEDGED, null, wrapper -> {
         wrapper.cancel();
         ConfigurationState configurationState = wrapper.user().get(ConfigurationState.class);
         if (configurationState.bridgePhase() == ConfigurationState.BridgePhase.REENTERING_CONFIGURATION) {
            wrapper.user().getProtocolInfo().setClientState(State.CONFIGURATION);
            configurationState.setBridgePhase(ConfigurationState.BridgePhase.CONFIGURATION);
            LastResourcePack lastResourcePack = wrapper.user().get(LastResourcePack.class);
            sendConfigurationPackets(wrapper.user(), configurationState.lastDimensionRegistry(), lastResourcePack);
         }
      });
      this.cancelServerbound(ServerboundPackets1_20_2.CHUNK_BATCH_RECEIVED);
      this.registerServerbound(ServerboundPackets1_20_2.PING_REQUEST, null, wrapper -> {
         wrapper.cancel();
         long time = wrapper.read(Type.LONG);
         PacketWrapper responsePacket = wrapper.create(ClientboundPackets1_20_2.PONG_RESPONSE);
         responsePacket.write(Type.LONG, time);
         responsePacket.sendFuture(Protocol1_20_2To1_20.class);
      });
   }

   @Override
   public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
      if (direction == Direction.SERVERBOUND) {
         super.transform(direction, state, packetWrapper);
      } else {
         ConfigurationState configurationBridge = packetWrapper.user().get(ConfigurationState.class);
         if (configurationBridge != null) {
            ConfigurationState.BridgePhase phase = configurationBridge.bridgePhase();
            if (phase == ConfigurationState.BridgePhase.NONE) {
               super.transform(direction, state, packetWrapper);
            } else {
               int unmappedId = packetWrapper.getId();
               if (phase != ConfigurationState.BridgePhase.PROFILE_SENT && phase != ConfigurationState.BridgePhase.REENTERING_CONFIGURATION) {
                  if (packetWrapper.getPacketType() != null && packetWrapper.getPacketType().state() == State.CONFIGURATION) {
                     super.transform(direction, State.CONFIGURATION, packetWrapper);
                  } else if (unmappedId == ClientboundPackets1_19_4.JOIN_GAME.getId()) {
                     super.transform(direction, State.PLAY, packetWrapper);
                  } else if (configurationBridge.queuedOrSentJoinGame()) {
                     if (!packetWrapper.user().isClientSide() && !Via.getPlatform().isProxy() && unmappedId == ClientboundPackets1_19_4.SYSTEM_CHAT.getId()) {
                        super.transform(direction, State.PLAY, packetWrapper);
                     } else {
                        configurationBridge.addPacketToQueue(packetWrapper, true);
                        throw CancelException.generate();
                     }
                  } else {
                     if (unmappedId == ClientboundPackets1_19_4.PLUGIN_MESSAGE.getId()) {
                        packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.CUSTOM_PAYLOAD);
                     } else if (unmappedId == ClientboundPackets1_19_4.DISCONNECT.getId()) {
                        packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.DISCONNECT);
                     } else if (unmappedId == ClientboundPackets1_19_4.KEEP_ALIVE.getId()) {
                        packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.KEEP_ALIVE);
                     } else if (unmappedId == ClientboundPackets1_19_4.PING.getId()) {
                        packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.PING);
                     } else if (unmappedId == ClientboundPackets1_19_4.UPDATE_ENABLED_FEATURES.getId()) {
                        packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.UPDATE_ENABLED_FEATURES);
                     } else {
                        if (unmappedId != ClientboundPackets1_19_4.TAGS.getId()) {
                           configurationBridge.addPacketToQueue(packetWrapper, true);
                           throw CancelException.generate();
                        }

                        packetWrapper.setPacketType(ClientboundConfigurationPackets1_20_2.UPDATE_TAGS);
                     }
                  }
               } else {
                  if (unmappedId == ClientboundPackets1_19_4.TAGS.getId()) {
                     packetWrapper.user().remove(LastTags.class);
                  }

                  configurationBridge.addPacketToQueue(packetWrapper, true);
                  throw CancelException.generate();
               }
            }
         }
      }
   }

   public static void sendConfigurationPackets(UserConnection connection, CompoundTag dimensionRegistry, @Nullable LastResourcePack lastResourcePack) throws Exception {
      ProtocolInfo protocolInfo = connection.getProtocolInfo();
      protocolInfo.setServerState(State.CONFIGURATION);
      PacketWrapper registryDataPacket = PacketWrapper.create(ClientboundConfigurationPackets1_20_2.REGISTRY_DATA, connection);
      registryDataPacket.write(Type.COMPOUND_TAG, dimensionRegistry);
      registryDataPacket.send(Protocol1_20_2To1_20.class);
      LastTags lastTags = connection.get(LastTags.class);
      if (lastTags != null) {
         lastTags.sendLastTags(connection);
      }

      if (lastResourcePack != null && connection.getProtocolInfo().getProtocolVersion() == ProtocolVersion.v1_20_2.getVersion()) {
         PacketWrapper resourcePackPacket = PacketWrapper.create(ClientboundConfigurationPackets1_20_2.RESOURCE_PACK, connection);
         resourcePackPacket.write(Type.STRING, lastResourcePack.url());
         resourcePackPacket.write(Type.STRING, lastResourcePack.hash());
         resourcePackPacket.write(Type.BOOLEAN, lastResourcePack.required());
         resourcePackPacket.write(Type.OPTIONAL_COMPONENT, lastResourcePack.prompt());
         resourcePackPacket.send(Protocol1_20_2To1_20.class);
      }

      PacketWrapper finishConfigurationPacket = PacketWrapper.create(ClientboundConfigurationPackets1_20_2.FINISH_CONFIGURATION, connection);
      finishConfigurationPacket.send(Protocol1_20_2To1_20.class);
      protocolInfo.setServerState(State.PLAY);
   }

   private PacketHandler queueServerboundPacket(ServerboundPackets1_20_2 packetType) {
      return wrapper -> {
         wrapper.setPacketType(packetType);
         wrapper.user().get(ConfigurationState.class).addPacketToQueue(wrapper, false);
         wrapper.cancel();
      };
   }

   private void sanitizeCustomPayload(PacketWrapper wrapper) throws Exception {
      String channel = Key.namespaced(wrapper.passthrough(Type.STRING));
      if (channel.equals("minecraft:brand")) {
         wrapper.passthrough(Type.STRING);
         wrapper.clearInputBuffer();
      }
   }

   @Override
   public MappingData getMappingData() {
      return MAPPINGS;
   }

   @Override
   protected void registerConfigurationChangeHandlers() {
   }

   @Override
   public void init(UserConnection user) {
      user.put(new ConfigurationState());
      this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19_4.PLAYER));
   }

   @Override
   public EntityRewriter<Protocol1_20_2To1_20> getEntityRewriter() {
      return this.entityPacketRewriter;
   }

   @Override
   public ItemRewriter<Protocol1_20_2To1_20> getItemRewriter() {
      return this.itemPacketRewriter;
   }
}
