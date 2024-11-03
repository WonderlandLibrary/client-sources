package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ParrotStorage;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import java.util.Optional;

public class EntityPackets1_12 extends LegacyEntityRewriter<ClientboundPackets1_12, Protocol1_11_1To1_12> {
   public EntityPackets1_12(Protocol1_11_1To1_12 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_12.SPAWN_ENTITY, new PacketHandlers() {
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
            this.handler(EntityPackets1_12.this.getObjectTrackerHandler());
            this.handler(EntityPackets1_12.this.getObjectRewriter(id -> EntityTypes1_12.ObjectType.findById(id).orElse(null)));
            this.handler(wrapper -> {
               Optional<EntityTypes1_12.ObjectType> type = EntityTypes1_12.ObjectType.findById(wrapper.get(Type.BYTE, 0));
               if (type.isPresent() && type.get() == EntityTypes1_12.ObjectType.FALLING_BLOCK) {
                  int objectData = wrapper.get(Type.INT, 0);
                  int objType = objectData & 4095;
                  int data = objectData >> 12 & 15;
                  Block block = EntityPackets1_12.this.protocol.getItemRewriter().handleBlock(objType, data);
                  if (block == null) {
                     return;
                  }

                  wrapper.set(Type.INT, 0, block.getId() | block.getData() << 12);
               }
            });
         }
      });
      this.registerTracker(ClientboundPackets1_12.SPAWN_EXPERIENCE_ORB, EntityTypes1_12.EntityType.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_12.SPAWN_GLOBAL_ENTITY, EntityTypes1_12.EntityType.WEATHER);
      this.protocol.registerClientbound(ClientboundPackets1_12.SPAWN_MOB, new PacketHandlers() {
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
            this.map(Types1_12.METADATA_LIST);
            this.handler(EntityPackets1_12.this.getTrackerHandler());
            this.handler(EntityPackets1_12.this.getMobSpawnRewriter(Types1_12.METADATA_LIST));
         }
      });
      this.registerTracker(ClientboundPackets1_12.SPAWN_PAINTING, EntityTypes1_12.EntityType.PAINTING);
      this.protocol.registerClientbound(ClientboundPackets1_12.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Types1_12.METADATA_LIST);
            this.handler(EntityPackets1_12.this.getTrackerAndMetaHandler(Types1_12.METADATA_LIST, EntityTypes1_12.EntityType.PLAYER));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_12.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.handler(EntityPackets1_12.this.getTrackerHandler(EntityTypes1_12.EntityType.PLAYER, Type.INT));
            this.handler(EntityPackets1_12.this.getDimensionHandler(1));
            this.handler(wrapper -> {
               ShoulderTracker tracker = wrapper.user().get(ShoulderTracker.class);
               tracker.setEntityId(wrapper.get(Type.INT, 0));
            });
            this.handler(packetWrapper -> {
               PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9_3.STATISTICS, packetWrapper.user());
               wrapper.write(Type.VAR_INT, 1);
               wrapper.write(Type.STRING, "achievement.openInventory");
               wrapper.write(Type.VAR_INT, 1);
               wrapper.scheduleSend(Protocol1_11_1To1_12.class);
            });
         }
      });
      this.registerRespawn(ClientboundPackets1_12.RESPAWN);
      this.registerRemoveEntities(ClientboundPackets1_12.DESTROY_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_12.ENTITY_METADATA, Types1_12.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_12.ENTITY_PROPERTIES, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.handler(wrapper -> {
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
            });
         }
      });
   }

   @Override
   protected void registerRewrites() {
      this.mapEntityTypeWithData(EntityTypes1_12.EntityType.PARROT, EntityTypes1_12.EntityType.BAT)
         .plainName()
         .spawnMetadata(storage -> storage.add(new Metadata(12, MetaType1_12.Byte, (byte)0)));
      this.mapEntityTypeWithData(EntityTypes1_12.EntityType.ILLUSION_ILLAGER, EntityTypes1_12.EntityType.EVOCATION_ILLAGER).plainName();
      this.filter().handler((event, meta) -> {
         if (meta.metaType() == MetaType1_12.Chat) {
            ChatPackets1_12.COMPONENT_REWRITER.processText((JsonElement)meta.getValue());
         }
      });
      this.filter().filterFamily(EntityTypes1_12.EntityType.EVOCATION_ILLAGER).cancel(12);
      this.filter().filterFamily(EntityTypes1_12.EntityType.EVOCATION_ILLAGER).index(13).toIndex(12);
      this.filter().type(EntityTypes1_12.EntityType.ILLUSION_ILLAGER).index(0).handler((event, meta) -> {
         byte mask = (Byte)meta.getValue();
         if ((mask & 32) == 32) {
            mask &= -33;
         }

         meta.setValue(mask);
      });
      this.filter().filterFamily(EntityTypes1_12.EntityType.PARROT).handler((event, meta) -> {
         StoredEntityData data = this.storedEntityData(event);
         if (!data.has(ParrotStorage.class)) {
            data.put(new ParrotStorage());
         }
      });
      this.filter().type(EntityTypes1_12.EntityType.PARROT).cancel(12);
      this.filter().type(EntityTypes1_12.EntityType.PARROT).index(13).handler((event, meta) -> {
         StoredEntityData data = this.storedEntityData(event);
         ParrotStorage storage = data.get(ParrotStorage.class);
         boolean isSitting = ((Byte)meta.getValue() & 1) == 1;
         boolean isTamed = ((Byte)meta.getValue() & 4) == 4;
         if (!storage.isTamed() && isTamed) {
         }

         storage.setTamed(isTamed);
         if (isSitting) {
            event.setIndex(12);
            meta.setValue((byte)1);
            storage.setSitting(true);
         } else if (storage.isSitting()) {
            event.setIndex(12);
            meta.setValue((byte)0);
            storage.setSitting(false);
         } else {
            event.cancel();
         }
      });
      this.filter().type(EntityTypes1_12.EntityType.PARROT).cancel(14);
      this.filter().type(EntityTypes1_12.EntityType.PARROT).cancel(15);
      this.filter().type(EntityTypes1_12.EntityType.PLAYER).index(15).handler((event, meta) -> {
         CompoundTag tag = (CompoundTag)meta.getValue();
         ShoulderTracker tracker = event.user().get(ShoulderTracker.class);
         if (tag.isEmpty() && tracker.getLeftShoulder() != null) {
            tracker.setLeftShoulder(null);
            tracker.update();
         } else if (tag.contains("id") && event.entityId() == tracker.getEntityId()) {
            String id = (String)tag.<Tag>get("id").getValue();
            if (tracker.getLeftShoulder() == null || !tracker.getLeftShoulder().equals(id)) {
               tracker.setLeftShoulder(id);
               tracker.update();
            }
         }

         event.cancel();
      });
      this.filter().type(EntityTypes1_12.EntityType.PLAYER).index(16).handler((event, meta) -> {
         CompoundTag tag = (CompoundTag)event.meta().getValue();
         ShoulderTracker tracker = event.user().get(ShoulderTracker.class);
         if (tag.isEmpty() && tracker.getRightShoulder() != null) {
            tracker.setRightShoulder(null);
            tracker.update();
         } else if (tag.contains("id") && event.entityId() == tracker.getEntityId()) {
            String id = (String)tag.<Tag>get("id").getValue();
            if (tracker.getRightShoulder() == null || !tracker.getRightShoulder().equals(id)) {
               tracker.setRightShoulder(id);
               tracker.update();
            }
         }

         event.cancel();
      });
   }

   @Override
   public EntityType typeFromId(int typeId) {
      return EntityTypes1_12.getTypeFromId(typeId, false);
   }

   @Override
   protected EntityType getObjectTypeFromId(int typeId) {
      return EntityTypes1_12.getTypeFromId(typeId, true);
   }
}
