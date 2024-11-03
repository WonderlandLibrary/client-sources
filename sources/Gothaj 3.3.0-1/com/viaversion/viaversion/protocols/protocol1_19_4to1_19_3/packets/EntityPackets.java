package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets;

import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_4;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.api.type.types.version.Types1_19_4;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.storage.PlayerVehicleTracker;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public final class EntityPackets extends EntityRewriter<ClientboundPackets1_19_3, Protocol1_19_4To1_19_3> {
   public EntityPackets(Protocol1_19_4To1_19_3 protocol) {
      super(protocol);
   }

   @Override
   public void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_19_3.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.STRING_ARRAY);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.map(Type.STRING);
            this.map(Type.STRING);
            this.handler(EntityPackets.this.dimensionDataHandler());
            this.handler(EntityPackets.this.biomeSizeTracker());
            this.handler(EntityPackets.this.worldDataTrackerHandlerByKey());
            this.handler(EntityPackets.this.playerTrackerHandler());
            this.handler(wrapper -> {
               CompoundTag registry = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
               CompoundTag damageTypeRegistry = EntityPackets.this.protocol.getMappingData().damageTypesRegistry();
               registry.put("minecraft:damage_type", damageTypeRegistry);
               CompoundTag biomeRegistry = registry.get("minecraft:worldgen/biome");

               for (Tag biomeTag : (ListTag)biomeRegistry.get("value")) {
                  CompoundTag biomeData = ((CompoundTag)biomeTag).get("element");
                  StringTag precipitation = biomeData.get("precipitation");
                  byte precipitationByte = (byte)(precipitation.getValue().equals("none") ? 0 : 1);
                  biomeData.put("has_precipitation", new ByteTag(precipitationByte));
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_3.PLAYER_POSITION, new PacketHandlers() {
         @Override
         protected void register() {
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               if (wrapper.read(Type.BOOLEAN)) {
                  PlayerVehicleTracker playerVehicleTracker = wrapper.user().get(PlayerVehicleTracker.class);
                  if (playerVehicleTracker.getVehicleId() != -1) {
                     PacketWrapper bundleStart = wrapper.create(ClientboundPackets1_19_4.BUNDLE);
                     bundleStart.send(Protocol1_19_4To1_19_3.class);
                     PacketWrapper setPassengers = wrapper.create(ClientboundPackets1_19_4.SET_PASSENGERS);
                     setPassengers.write(Type.VAR_INT, playerVehicleTracker.getVehicleId());
                     setPassengers.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[0]);
                     setPassengers.send(Protocol1_19_4To1_19_3.class);
                     wrapper.send(Protocol1_19_4To1_19_3.class);
                     wrapper.cancel();
                     PacketWrapper bundleEnd = wrapper.create(ClientboundPackets1_19_4.BUNDLE);
                     bundleEnd.send(Protocol1_19_4To1_19_3.class);
                     playerVehicleTracker.setVehicleId(-1);
                  }
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_3.SET_PASSENGERS, new PacketHandlers() {
         @Override
         protected void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
            this.handler(wrapper -> {
               PlayerVehicleTracker playerVehicleTracker = wrapper.user().get(PlayerVehicleTracker.class);
               int clientEntityId = wrapper.user().<EntityTracker>getEntityTracker(Protocol1_19_4To1_19_3.class).clientEntityId();
               int vehicleId = wrapper.get(Type.VAR_INT, 0);
               if (playerVehicleTracker.getVehicleId() == vehicleId) {
                  playerVehicleTracker.setVehicleId(-1);
               }

               int[] passengerIds = wrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0);

               for (int passengerId : passengerIds) {
                  if (passengerId == clientEntityId) {
                     playerVehicleTracker.setVehicleId(vehicleId);
                     break;
                  }
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_3.ENTITY_TELEPORT, new PacketHandlers() {
         @Override
         protected void register() {
            this.handler(wrapper -> {
               int entityId = wrapper.read(Type.VAR_INT);
               int clientEntityId = wrapper.user().<EntityTracker>getEntityTracker(Protocol1_19_4To1_19_3.class).clientEntityId();
               if (entityId != clientEntityId) {
                  wrapper.write(Type.VAR_INT, entityId);
               } else {
                  wrapper.setPacketType(ClientboundPackets1_19_4.PLAYER_POSITION);
                  wrapper.passthrough(Type.DOUBLE);
                  wrapper.passthrough(Type.DOUBLE);
                  wrapper.passthrough(Type.DOUBLE);
                  wrapper.write(Type.FLOAT, (float)wrapper.read(Type.BYTE).byteValue() * 360.0F / 256.0F);
                  wrapper.write(Type.FLOAT, (float)wrapper.read(Type.BYTE).byteValue() * 360.0F / 256.0F);
                  wrapper.read(Type.BOOLEAN);
                  wrapper.write(Type.BYTE, (byte)0);
                  wrapper.write(Type.VAR_INT, -1);
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_3.ENTITY_ANIMATION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               short action = wrapper.read(Type.UNSIGNED_BYTE);
               if (action != 1) {
                  wrapper.write(Type.UNSIGNED_BYTE, action);
               } else {
                  wrapper.setPacketType(ClientboundPackets1_19_4.HIT_ANIMATION);
                  wrapper.write(Type.FLOAT, 0.0F);
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_3.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.STRING);
            this.handler(EntityPackets.this.worldDataTrackerHandlerByKey());
            this.handler(wrapper -> wrapper.user().put(new PlayerVehicleTracker()));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_3.ENTITY_STATUS, wrapper -> {
         int entityId = wrapper.read(Type.INT);
         byte event = wrapper.read(Type.BYTE);
         int damageType = this.damageTypeFromEntityEvent(event);
         if (damageType != -1) {
            wrapper.setPacketType(ClientboundPackets1_19_4.DAMAGE_EVENT);
            wrapper.write(Type.VAR_INT, entityId);
            wrapper.write(Type.VAR_INT, damageType);
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(Type.BOOLEAN, false);
         } else {
            wrapper.write(Type.INT, entityId);
            wrapper.write(Type.BYTE, event);
         }
      });
      this.registerTrackerWithData1_19(ClientboundPackets1_19_3.SPAWN_ENTITY, EntityTypes1_19_4.FALLING_BLOCK);
      this.registerRemoveEntities(ClientboundPackets1_19_3.REMOVE_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_19_3.ENTITY_METADATA, Types1_19_3.METADATA_LIST, Types1_19_4.METADATA_LIST);
   }

   private int damageTypeFromEntityEvent(byte entityEvent) {
      switch (entityEvent) {
         case 2:
         case 44:
            return 16;
         case 33:
            return 36;
         case 36:
            return 5;
         case 37:
            return 27;
         case 57:
            return 15;
         default:
            return -1;
      }
   }

   @Override
   protected void registerRewrites() {
      this.filter().handler((event, meta) -> {
         int id = meta.metaType().typeId();
         if (id >= 14) {
            id++;
         }

         meta.setMetaType(Types1_19_4.META_TYPES.byId(id));
      });
      this.registerMetaTypeHandler(
         Types1_19_4.META_TYPES.itemType,
         Types1_19_4.META_TYPES.blockStateType,
         Types1_19_4.META_TYPES.optionalBlockStateType,
         Types1_19_4.META_TYPES.particleType
      );
      this.filter().filterFamily(EntityTypes1_19_4.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
         int blockState = meta.<Integer>value();
         meta.setValue(this.protocol.getMappingData().getNewBlockStateId(blockState));
      });
      this.filter().filterFamily(EntityTypes1_19_4.BOAT).index(11).handler((event, meta) -> {
         int boatType = meta.<Integer>value();
         if (boatType > 4) {
            meta.setValue(boatType + 1);
         }
      });
      this.filter().filterFamily(EntityTypes1_19_4.ABSTRACT_HORSE).removeIndex(18);
   }

   @Override
   public void onMappingDataLoaded() {
      this.mapTypes();
   }

   @Override
   public EntityType typeFromId(int type) {
      return EntityTypes1_19_4.getTypeFromId(type);
   }
}
