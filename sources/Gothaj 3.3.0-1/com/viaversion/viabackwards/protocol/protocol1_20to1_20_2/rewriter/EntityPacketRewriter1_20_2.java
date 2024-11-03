package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.storage.ConfigurationPacketStorage;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.GlobalPosition;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_20;
import com.viaversion.viaversion.api.type.types.version.Types1_20_2;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundPackets1_20_2;

public final class EntityPacketRewriter1_20_2 extends EntityRewriter<ClientboundPackets1_20_2, Protocol1_20To1_20_2> {
   public EntityPacketRewriter1_20_2(Protocol1_20To1_20_2 protocol) {
      super(protocol, Types1_20.META_TYPES.optionalComponentType, Types1_20.META_TYPES.booleanType);
   }

   @Override
   public void registerPackets() {
      this.registerMetadataRewriter(ClientboundPackets1_20_2.ENTITY_METADATA, Types1_20_2.METADATA_LIST, Types1_20.METADATA_LIST);
      this.registerRemoveEntities(ClientboundPackets1_20_2.REMOVE_ENTITIES);
      this.protocol
         .registerClientbound(
            ClientboundPackets1_20_2.SPAWN_ENTITY,
            new PacketHandlers() {
               @Override
               protected void register() {
                  this.handler(
                     wrapper -> {
                        int entityId = wrapper.passthrough(Type.VAR_INT);
                        wrapper.passthrough(Type.UUID);
                        int entityType = wrapper.read(Type.VAR_INT);
                        EntityPacketRewriter1_20_2.this.<EntityTracker>tracker(wrapper.user())
                           .addEntity(entityId, EntityPacketRewriter1_20_2.this.typeFromId(entityType));
                        if (entityType != EntityTypes1_19_4.PLAYER.getId()) {
                           wrapper.write(Type.VAR_INT, entityType);
                           if (entityType == EntityTypes1_19_4.FALLING_BLOCK.getId()) {
                              wrapper.passthrough(Type.DOUBLE);
                              wrapper.passthrough(Type.DOUBLE);
                              wrapper.passthrough(Type.DOUBLE);
                              wrapper.passthrough(Type.BYTE);
                              wrapper.passthrough(Type.BYTE);
                              wrapper.passthrough(Type.BYTE);
                              int blockState = wrapper.read(Type.VAR_INT);
                              wrapper.write(Type.VAR_INT, EntityPacketRewriter1_20_2.this.protocol.getMappingData().getNewBlockStateId(blockState));
                           }
                        } else {
                           wrapper.setPacketType(ClientboundPackets1_19_4.SPAWN_PLAYER);
                           wrapper.passthrough(Type.DOUBLE);
                           wrapper.passthrough(Type.DOUBLE);
                           wrapper.passthrough(Type.DOUBLE);
                           byte pitch = wrapper.read(Type.BYTE);
                           wrapper.passthrough(Type.BYTE);
                           wrapper.write(Type.BYTE, pitch);
                           wrapper.read(Type.BYTE);
                           wrapper.read(Type.VAR_INT);
                           short velocityX = wrapper.read(Type.SHORT);
                           short velocityY = wrapper.read(Type.SHORT);
                           short velocityZ = wrapper.read(Type.SHORT);
                           if (velocityX != 0 || velocityY != 0 || velocityZ != 0) {
                              wrapper.send(Protocol1_20To1_20_2.class);
                              wrapper.cancel();
                              PacketWrapper velocityPacket = wrapper.create(ClientboundPackets1_19_4.ENTITY_VELOCITY);
                              velocityPacket.write(Type.VAR_INT, entityId);
                              velocityPacket.write(Type.SHORT, velocityX);
                              velocityPacket.write(Type.SHORT, velocityY);
                              velocityPacket.write(Type.SHORT, velocityZ);
                              velocityPacket.send(Protocol1_20To1_20_2.class);
                           }
                        }
                     }
                  );
               }
            }
         );
      this.protocol.registerClientbound(ClientboundPackets1_20_2.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> {
               ConfigurationPacketStorage configurationPacketStorage = wrapper.user().remove(ConfigurationPacketStorage.class);
               wrapper.passthrough(Type.INT);
               wrapper.passthrough(Type.BOOLEAN);
               String[] worlds = wrapper.read(Type.STRING_ARRAY);
               int maxPlayers = wrapper.read(Type.VAR_INT);
               int viewDistance = wrapper.read(Type.VAR_INT);
               int simulationDistance = wrapper.read(Type.VAR_INT);
               boolean reducedDebugInfo = wrapper.read(Type.BOOLEAN);
               boolean showRespawnScreen = wrapper.read(Type.BOOLEAN);
               wrapper.read(Type.BOOLEAN);
               String dimensionType = wrapper.read(Type.STRING);
               String world = wrapper.read(Type.STRING);
               long seed = wrapper.read(Type.LONG);
               wrapper.passthrough(Type.BYTE);
               wrapper.passthrough(Type.BYTE);
               wrapper.write(Type.STRING_ARRAY, worlds);
               wrapper.write(Type.NAMED_COMPOUND_TAG, configurationPacketStorage.registry());
               wrapper.write(Type.STRING, dimensionType);
               wrapper.write(Type.STRING, world);
               wrapper.write(Type.LONG, seed);
               wrapper.write(Type.VAR_INT, maxPlayers);
               wrapper.write(Type.VAR_INT, viewDistance);
               wrapper.write(Type.VAR_INT, simulationDistance);
               wrapper.write(Type.BOOLEAN, reducedDebugInfo);
               wrapper.write(Type.BOOLEAN, showRespawnScreen);
               EntityPacketRewriter1_20_2.this.worldDataTrackerHandlerByKey().handle(wrapper);
               wrapper.send(Protocol1_20To1_20_2.class);
               wrapper.cancel();
               if (configurationPacketStorage.enabledFeatures() != null) {
                  PacketWrapper featuresPacket = wrapper.create(ClientboundPackets1_19_4.UPDATE_ENABLED_FEATURES);
                  featuresPacket.write(Type.STRING_ARRAY, configurationPacketStorage.enabledFeatures());
                  featuresPacket.send(Protocol1_20To1_20_2.class);
               }

               configurationPacketStorage.sendQueuedPackets(wrapper.user());
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_20_2.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> {
               wrapper.passthrough(Type.STRING);
               wrapper.passthrough(Type.STRING);
               wrapper.passthrough(Type.LONG);
               wrapper.write(Type.UNSIGNED_BYTE, wrapper.read(Type.BYTE).shortValue());
               wrapper.passthrough(Type.BYTE);
               wrapper.passthrough(Type.BOOLEAN);
               wrapper.passthrough(Type.BOOLEAN);
               GlobalPosition lastDeathPosition = wrapper.read(Type.OPTIONAL_GLOBAL_POSITION);
               int portalCooldown = wrapper.read(Type.VAR_INT);
               wrapper.passthrough(Type.BYTE);
               wrapper.write(Type.OPTIONAL_GLOBAL_POSITION, lastDeathPosition);
               wrapper.write(Type.VAR_INT, portalCooldown);
            });
            this.handler(EntityPacketRewriter1_20_2.this.worldDataTrackerHandlerByKey());
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_20_2.ENTITY_EFFECT, wrapper -> {
         wrapper.passthrough(Type.VAR_INT);
         wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT) + 1);
         wrapper.passthrough(Type.BYTE);
         wrapper.passthrough(Type.VAR_INT);
         wrapper.passthrough(Type.BYTE);
         if (wrapper.passthrough(Type.BOOLEAN)) {
            wrapper.write(Type.NAMED_COMPOUND_TAG, wrapper.read(Type.COMPOUND_TAG));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_20_2.REMOVE_ENTITY_EFFECT, wrapper -> {
         wrapper.passthrough(Type.VAR_INT);
         wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT) + 1);
      });
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> meta.setMetaType(Types1_20.META_TYPES.byId(meta.metaType().typeId())));
      this.registerMetaTypeHandler(
         Types1_20.META_TYPES.itemType,
         Types1_20.META_TYPES.blockStateType,
         Types1_20.META_TYPES.optionalBlockStateType,
         Types1_20.META_TYPES.particleType,
         null,
         null
      );
      this.filter().filterFamily(EntityTypes1_19_4.DISPLAY).removeIndex(10);
      this.filter().filterFamily(EntityTypes1_19_4.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
         int blockState = meta.<Integer>value();
         meta.setValue(this.protocol.getMappingData().getNewBlockStateId(blockState));
      });
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_19_4.getTypeFromId(type);
   }
}
