package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.entities.storage.EntityPositionHandler;
import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.EntityTypeMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.PaintingMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.BackwardsBlockStorage;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ServerboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import java.util.Optional;

public class EntityPackets1_13 extends LegacyEntityRewriter<ClientboundPackets1_13, Protocol1_12_2To1_13> {
   public EntityPackets1_13(Protocol1_12_2To1_13 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_13.PLAYER_POSITION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.BYTE);
            this.handler(wrapper -> {
               if (ViaBackwards.getConfig().isFix1_13FacePlayer()) {
                  PlayerPositionStorage1_13 playerStorage = wrapper.user().get(PlayerPositionStorage1_13.class);
                  byte bitField = wrapper.get(Type.BYTE, 0);
                  playerStorage.setX(EntityPackets1_13.toSet(bitField, 0, playerStorage.getX(), wrapper.get(Type.DOUBLE, 0)));
                  playerStorage.setY(EntityPackets1_13.toSet(bitField, 1, playerStorage.getY(), wrapper.get(Type.DOUBLE, 1)));
                  playerStorage.setZ(EntityPackets1_13.toSet(bitField, 2, playerStorage.getZ(), wrapper.get(Type.DOUBLE, 2)));
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.SPAWN_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.BYTE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.handler(EntityPackets1_13.this.getObjectTrackerHandler());
            this.handler(wrapper -> {
               Optional<EntityTypes1_13.ObjectType> optionalType = EntityTypes1_13.ObjectType.findById(wrapper.get(Type.BYTE, 0));
               if (optionalType.isPresent()) {
                  EntityTypes1_13.ObjectType type = optionalType.get();
                  if (type == EntityTypes1_13.ObjectType.FALLING_BLOCK) {
                     int blockState = wrapper.get(Type.INT, 0);
                     int combined = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(blockState);
                     combined = combined >> 4 & 4095 | (combined & 15) << 12;
                     wrapper.set(Type.INT, 0, combined);
                  } else if (type == EntityTypes1_13.ObjectType.ITEM_FRAME) {
                     int data = wrapper.get(Type.INT, 0);
                     switch (data) {
                        case 3:
                           data = 0;
                           break;
                        case 4:
                           data = 1;
                           break;
                        case 5:
                           data = 3;
                     }

                     wrapper.set(Type.INT, 0, data);
                  } else if (type == EntityTypes1_13.ObjectType.TRIDENT) {
                     wrapper.set(Type.BYTE, 0, (byte)EntityTypes1_13.ObjectType.TIPPED_ARROW.getId());
                  }
               }
            });
         }
      });
      this.registerTracker(ClientboundPackets1_13.SPAWN_EXPERIENCE_ORB, EntityTypes1_13.EntityType.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_13.SPAWN_GLOBAL_ENTITY, EntityTypes1_13.EntityType.LIGHTNING_BOLT);
      this.protocol.registerClientbound(ClientboundPackets1_13.SPAWN_MOB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
            this.handler(wrapper -> {
               int type = wrapper.get(Type.VAR_INT, 1);
               EntityType entityType = EntityTypes1_13.getTypeFromId(type, false);
               EntityPackets1_13.this.<EntityTracker>tracker(wrapper.user()).addEntity(wrapper.get(Type.VAR_INT, 0), entityType);
               int oldId = EntityTypeMapping.getOldId(type);
               if (oldId == -1) {
                  if (!EntityPackets1_13.this.hasData(entityType)) {
                     ViaBackwards.getPlatform().getLogger().warning("Could not find 1.12 entity type for 1.13 entity type " + type + "/" + entityType);
                  }
               } else {
                  wrapper.set(Type.VAR_INT, 1, oldId);
               }
            });
            this.handler(EntityPackets1_13.this.getMobSpawnRewriter(Types1_12.METADATA_LIST));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
            this.handler(EntityPackets1_13.this.getTrackerAndMetaHandler(Types1_12.METADATA_LIST, EntityTypes1_13.EntityType.PLAYER));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_13.SPAWN_PAINTING, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.handler(EntityPackets1_13.this.getTrackerHandler(EntityTypes1_13.EntityType.PAINTING, Type.VAR_INT));
            this.handler(wrapper -> {
               int motive = wrapper.read(Type.VAR_INT);
               String title = PaintingMapping.getStringId(motive);
               wrapper.write(Type.STRING, title);
            });
         }
      });
      this.registerJoinGame(ClientboundPackets1_13.JOIN_GAME, EntityTypes1_13.EntityType.PLAYER);
      this.protocol.registerClientbound(ClientboundPackets1_13.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.handler(EntityPackets1_13.this.getDimensionHandler(0));
            this.handler(wrapper -> wrapper.user().get(BackwardsBlockStorage.class).clear());
         }
      });
      this.registerRemoveEntities(ClientboundPackets1_13.DESTROY_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_13.ENTITY_METADATA, Types1_13.METADATA_LIST, Types1_12.METADATA_LIST);
      this.protocol
         .registerClientbound(
            ClientboundPackets1_13.FACE_PLAYER,
            null,
            wrapper -> {
               wrapper.cancel();
               if (ViaBackwards.getConfig().isFix1_13FacePlayer()) {
                  int anchor = wrapper.read(Type.VAR_INT);
                  double x = wrapper.read(Type.DOUBLE);
                  double y = wrapper.read(Type.DOUBLE);
                  double z = wrapper.read(Type.DOUBLE);
                  PlayerPositionStorage1_13 positionStorage = wrapper.user().get(PlayerPositionStorage1_13.class);
                  PacketWrapper positionAndLook = wrapper.create(ClientboundPackets1_12_1.PLAYER_POSITION);
                  positionAndLook.write(Type.DOUBLE, 0.0);
                  positionAndLook.write(Type.DOUBLE, 0.0);
                  positionAndLook.write(Type.DOUBLE, 0.0);
                  EntityPositionHandler.writeFacingDegrees(
                     positionAndLook,
                     positionStorage.getX(),
                     anchor == 1 ? positionStorage.getY() + 1.62 : positionStorage.getY(),
                     positionStorage.getZ(),
                     x,
                     y,
                     z
                  );
                  positionAndLook.write(Type.BYTE, (byte)7);
                  positionAndLook.write(Type.VAR_INT, -1);
                  positionAndLook.send(Protocol1_12_2To1_13.class);
               }
            }
         );
      if (ViaBackwards.getConfig().isFix1_13FacePlayer()) {
         PacketHandlers movementRemapper = new PacketHandlers() {
            @Override
            public void register() {
               this.map(Type.DOUBLE);
               this.map(Type.DOUBLE);
               this.map(Type.DOUBLE);
               this.handler(wrapper -> wrapper.user().get(PlayerPositionStorage1_13.class).setCoordinates(wrapper, false));
            }
         };
         this.protocol.registerServerbound(ServerboundPackets1_12_1.PLAYER_POSITION, movementRemapper);
         this.protocol.registerServerbound(ServerboundPackets1_12_1.PLAYER_POSITION_AND_ROTATION, movementRemapper);
         this.protocol.registerServerbound(ServerboundPackets1_12_1.VEHICLE_MOVE, movementRemapper);
      }
   }

   @Override
   protected void registerRewrites() {
      this.mapEntityTypeWithData(EntityTypes1_13.EntityType.DROWNED, EntityTypes1_13.EntityType.ZOMBIE_VILLAGER).plainName();
      this.mapEntityTypeWithData(EntityTypes1_13.EntityType.COD, EntityTypes1_13.EntityType.SQUID).plainName();
      this.mapEntityTypeWithData(EntityTypes1_13.EntityType.SALMON, EntityTypes1_13.EntityType.SQUID).plainName();
      this.mapEntityTypeWithData(EntityTypes1_13.EntityType.PUFFERFISH, EntityTypes1_13.EntityType.SQUID).plainName();
      this.mapEntityTypeWithData(EntityTypes1_13.EntityType.TROPICAL_FISH, EntityTypes1_13.EntityType.SQUID).plainName();
      this.mapEntityTypeWithData(EntityTypes1_13.EntityType.PHANTOM, EntityTypes1_13.EntityType.PARROT)
         .plainName()
         .spawnMetadata(storage -> storage.add(new Metadata(15, MetaType1_12.VarInt, 3)));
      this.mapEntityTypeWithData(EntityTypes1_13.EntityType.DOLPHIN, EntityTypes1_13.EntityType.SQUID).plainName();
      this.mapEntityTypeWithData(EntityTypes1_13.EntityType.TURTLE, EntityTypes1_13.EntityType.OCELOT).plainName();
      this.filter().handler((event, meta) -> {
         int typeId = meta.metaType().typeId();
         if (typeId == 4) {
            JsonElement element = meta.value();
            this.protocol.translatableRewriter().processText(element);
            meta.setMetaType(MetaType1_12.Chat);
         } else if (typeId == 5) {
            JsonElement element = meta.value();
            meta.setTypeAndValue(MetaType1_12.String, this.protocol.jsonToLegacy(element));
         } else if (typeId == 6) {
            Item item = (Item)meta.getValue();
            meta.setTypeAndValue(MetaType1_12.Slot, this.protocol.getItemRewriter().handleItemToClient(item));
         } else if (typeId == 15) {
            event.cancel();
         } else {
            meta.setMetaType(MetaType1_12.byId(typeId > 5 ? typeId - 1 : typeId));
         }
      });
      this.filter().filterFamily(EntityTypes1_13.EntityType.ZOMBIE).removeIndex(15);
      this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(13);
      this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(14);
      this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(15);
      this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(16);
      this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(17);
      this.filter().type(EntityTypes1_13.EntityType.TURTLE).cancel(18);
      this.filter().filterFamily(EntityTypes1_13.EntityType.ABSTRACT_FISHES).cancel(12);
      this.filter().filterFamily(EntityTypes1_13.EntityType.ABSTRACT_FISHES).cancel(13);
      this.filter().type(EntityTypes1_13.EntityType.PHANTOM).cancel(12);
      this.filter().type(EntityTypes1_13.EntityType.BOAT).cancel(12);
      this.filter().type(EntityTypes1_13.EntityType.TRIDENT).cancel(7);
      this.filter().type(EntityTypes1_13.EntityType.WOLF).index(17).handler((event, meta) -> meta.setValue(15 - (Integer)meta.getValue()));
      this.filter().type(EntityTypes1_13.EntityType.AREA_EFFECT_CLOUD).index(9).handler((event, meta) -> {
         Particle particle = (Particle)meta.getValue();
         ParticleMapping.ParticleData data = ParticleMapping.getMapping(particle.getId());
         int firstArg = 0;
         int secondArg = 0;
         int[] particleArgs = data.rewriteMeta(this.protocol, particle.getArguments());
         if (particleArgs != null && particleArgs.length != 0) {
            if (data.getHandler().isBlockHandler() && particleArgs[0] == 0) {
               particleArgs[0] = 102;
            }

            firstArg = particleArgs[0];
            secondArg = particleArgs.length == 2 ? particleArgs[1] : 0;
         }

         event.createExtraMeta(new Metadata(9, MetaType1_12.VarInt, data.getHistoryId()));
         event.createExtraMeta(new Metadata(10, MetaType1_12.VarInt, firstArg));
         event.createExtraMeta(new Metadata(11, MetaType1_12.VarInt, secondArg));
         event.cancel();
      });
   }

   @Override
   public EntityType typeFromId(int typeId) {
      return EntityTypes1_13.getTypeFromId(typeId, false);
   }

   @Override
   protected EntityType getObjectTypeFromId(int typeId) {
      return EntityTypes1_13.getTypeFromId(typeId, true);
   }

   @Override
   public int newEntityId(int newId) {
      return EntityTypeMapping.getOldId(newId);
   }

   private static double toSet(int field, int bitIndex, double origin, double packetValue) {
      return (field & 1 << bitIndex) != 0 ? origin + packetValue : packetValue;
   }
}
