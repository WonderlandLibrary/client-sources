package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.api.rewriters.EntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.EntityTypeMapping;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import java.util.ArrayList;

public class EntityPackets1_15 extends EntityRewriter<ClientboundPackets1_15, Protocol1_14_4To1_15> {
   public EntityPackets1_15(Protocol1_14_4To1_15 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_15.UPDATE_HEALTH, wrapper -> {
         float health = wrapper.passthrough(Type.FLOAT);
         if (!(health > 0.0F)) {
            if (wrapper.user().get(ImmediateRespawn.class).isImmediateRespawn()) {
               PacketWrapper statusPacket = wrapper.create(ServerboundPackets1_14.CLIENT_STATUS);
               statusPacket.write(Type.VAR_INT, 0);
               statusPacket.sendToServer(Protocol1_14_4To1_15.class);
            }
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_15.GAME_EVENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.FLOAT);
            this.handler(wrapper -> {
               if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 11) {
                  wrapper.user().get(ImmediateRespawn.class).setImmediateRespawn(wrapper.get(Type.FLOAT, 0) == 1.0F);
               }
            });
         }
      });
      this.registerTrackerWithData(ClientboundPackets1_15.SPAWN_ENTITY, EntityTypes1_15.FALLING_BLOCK);
      this.protocol.registerClientbound(ClientboundPackets1_15.SPAWN_MOB, new PacketHandlers() {
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
            this.handler(wrapper -> wrapper.write(Types1_14.METADATA_LIST, new ArrayList<>()));
            this.handler(wrapper -> {
               int type = wrapper.get(Type.VAR_INT, 1);
               EntityType entityType = EntityTypes1_15.getTypeFromId(type);
               EntityPackets1_15.this.<EntityTracker>tracker(wrapper.user()).addEntity(wrapper.get(Type.VAR_INT, 0), entityType);
               wrapper.set(Type.VAR_INT, 1, EntityTypeMapping.getOldEntityId(type));
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_15.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.read(Type.LONG);
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_15.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.read(Type.LONG);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.STRING);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.handler(EntityPackets1_15.this.getTrackerHandler(EntityTypes1_15.PLAYER, Type.INT));
            this.handler(wrapper -> {
               boolean immediateRespawn = !wrapper.read(Type.BOOLEAN);
               wrapper.user().get(ImmediateRespawn.class).setImmediateRespawn(immediateRespawn);
            });
         }
      });
      this.registerTracker(ClientboundPackets1_15.SPAWN_EXPERIENCE_ORB, EntityTypes1_15.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_15.SPAWN_GLOBAL_ENTITY, EntityTypes1_15.LIGHTNING_BOLT);
      this.registerTracker(ClientboundPackets1_15.SPAWN_PAINTING, EntityTypes1_15.PAINTING);
      this.protocol.registerClientbound(ClientboundPackets1_15.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.handler(wrapper -> wrapper.write(Types1_14.METADATA_LIST, new ArrayList<>()));
            this.handler(EntityPackets1_15.this.getTrackerHandler(EntityTypes1_15.PLAYER, Type.VAR_INT));
         }
      });
      this.registerRemoveEntities(ClientboundPackets1_15.DESTROY_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_15.ENTITY_METADATA, Types1_14.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_15.ENTITY_PROPERTIES, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int entityId = wrapper.get(Type.VAR_INT, 0);
               EntityType entityType = EntityPackets1_15.this.<EntityTracker>tracker(wrapper.user()).entityType(entityId);
               if (entityType == EntityTypes1_15.BEE) {
                  int size = wrapper.get(Type.INT, 0);
                  int newSize = size;

                  for (int i = 0; i < size; i++) {
                     String key = wrapper.read(Type.STRING);
                     if (key.equals("generic.flyingSpeed")) {
                        newSize--;
                        wrapper.read(Type.DOUBLE);
                        int modSize = wrapper.read(Type.VAR_INT);

                        for (int j = 0; j < modSize; j++) {
                           wrapper.read(Type.UUID);
                           wrapper.read(Type.DOUBLE);
                           wrapper.read(Type.BYTE);
                        }
                     } else {
                        wrapper.write(Type.STRING, key);
                        wrapper.passthrough(Type.DOUBLE);
                        int modSize = wrapper.passthrough(Type.VAR_INT);

                        for (int j = 0; j < modSize; j++) {
                           wrapper.passthrough(Type.UUID);
                           wrapper.passthrough(Type.DOUBLE);
                           wrapper.passthrough(Type.BYTE);
                        }
                     }
                  }

                  if (newSize != size) {
                     wrapper.set(Type.INT, 0, newSize);
                  }
               }
            });
         }
      });
   }

   @Override
   protected void registerRewrites() {
      this.registerMetaTypeHandler(
         Types1_14.META_TYPES.itemType,
         Types1_14.META_TYPES.blockStateType,
         null,
         Types1_14.META_TYPES.particleType,
         Types1_14.META_TYPES.componentType,
         Types1_14.META_TYPES.optionalComponentType
      );
      this.filter().filterFamily(EntityTypes1_15.LIVINGENTITY).removeIndex(12);
      this.filter().type(EntityTypes1_15.BEE).cancel(15);
      this.filter().type(EntityTypes1_15.BEE).cancel(16);
      this.mapEntityTypeWithData(EntityTypes1_15.BEE, EntityTypes1_15.PUFFERFISH).jsonName().spawnMetadata(storage -> {
         storage.add(new Metadata(14, Types1_14.META_TYPES.booleanType, false));
         storage.add(new Metadata(15, Types1_14.META_TYPES.varIntType, 2));
      });
      this.filter().type(EntityTypes1_15.ENDERMAN).cancel(16);
      this.filter().type(EntityTypes1_15.TRIDENT).cancel(10);
      this.filter().type(EntityTypes1_15.WOLF).addIndex(17);
      this.filter()
         .type(EntityTypes1_15.WOLF)
         .index(8)
         .handler((event, meta) -> event.createExtraMeta(new Metadata(17, Types1_14.META_TYPES.floatType, event.meta().value())));
   }

   @Override
   public EntityType typeFromId(int typeId) {
      return EntityTypes1_15.getTypeFromId(typeId);
   }

   @Override
   public int newEntityId(int newId) {
      return EntityTypeMapping.getOldEntityId(newId);
   }
}
