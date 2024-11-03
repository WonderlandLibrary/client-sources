package com.viaversion.viaversion.protocols.protocol1_9to1_8.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ItemRewriter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.metadata.MetadataRewriter1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import java.util.ArrayList;
import java.util.List;

public class SpawnPackets {
   public static final ValueTransformer<Integer, Double> toNewDouble = new ValueTransformer<Integer, Double>(Type.DOUBLE) {
      public Double transform(PacketWrapper wrapper, Integer inputValue) {
         return (double)inputValue.intValue() / 32.0;
      }
   };

   public static void register(final Protocol1_9To1_8 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
            });
            this.map(Type.BYTE);
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               int typeID = wrapper.get(Type.BYTE, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               tracker.addEntity(entityID, EntityTypes1_10.getTypeFromId(typeID, true));
               tracker.sendMetadataBuffer(entityID);
            });
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int data = wrapper.get(Type.INT, 0);
               short vX = 0;
               short vY = 0;
               short vZ = 0;
               if (data > 0) {
                  vX = wrapper.read(Type.SHORT);
                  vY = wrapper.read(Type.SHORT);
                  vZ = wrapper.read(Type.SHORT);
               }

               wrapper.write(Type.SHORT, vX);
               wrapper.write(Type.SHORT, vY);
               wrapper.write(Type.SHORT, vZ);
            });
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               int data = wrapper.get(Type.INT, 0);
               int typeID = wrapper.get(Type.BYTE, 0);
               if (EntityTypes1_10.getTypeFromId(typeID, true) == EntityTypes1_10.EntityType.SPLASH_POTION) {
                  PacketWrapper metaPacket = wrapper.create(ClientboundPackets1_9.ENTITY_METADATA, wrapper1 -> {
                     wrapper1.write(Type.VAR_INT, entityID);
                     List<Metadata> meta = new ArrayList<>();
                     Item item = new DataItem(373, (byte)1, (short)data, null);
                     ItemRewriter.toClient(item);
                     Metadata potion = new Metadata(5, MetaType1_9.Slot, item);
                     meta.add(potion);
                     wrapper1.write(Types1_9.METADATA_LIST, meta);
                  });
                  wrapper.send(Protocol1_9To1_8.class);
                  metaPacket.send(Protocol1_9To1_8.class);
                  wrapper.cancel();
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               tracker.addEntity(entityID, EntityTypes1_10.EntityType.EXPERIENCE_ORB);
               tracker.sendMetadataBuffer(entityID);
            });
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.SHORT);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               tracker.addEntity(entityID, EntityTypes1_10.EntityType.LIGHTNING);
               tracker.sendMetadataBuffer(entityID);
            });
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_MOB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
            });
            this.map(Type.UNSIGNED_BYTE);
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               int typeID = wrapper.get(Type.UNSIGNED_BYTE, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               tracker.addEntity(entityID, EntityTypes1_10.getTypeFromId(typeID, false));
               tracker.sendMetadataBuffer(entityID);
            });
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
            this.handler(wrapper -> {
               List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
               int entityId = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               if (tracker.hasEntity(entityId)) {
                  protocol.get(MetadataRewriter1_9To1_8.class).handleMetadata(entityId, metadataList, wrapper.user());
               } else {
                  Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + entityId);
                  metadataList.clear();
               }
            });
            this.handler(wrapper -> {
               List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
               int entityID = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               tracker.handleMetadata(entityID, metadataList);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_PAINTING, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               tracker.addEntity(entityID, EntityTypes1_10.EntityType.PAINTING);
               tracker.sendMetadataBuffer(entityID);
            });
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               wrapper.write(Type.UUID, tracker.getEntityUUID(entityID));
            });
            this.map(Type.STRING);
            this.map(Type.POSITION1_8);
            this.map(Type.BYTE);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.handler(wrapper -> {
               int entityID = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               tracker.addEntity(entityID, EntityTypes1_10.EntityType.PLAYER);
               tracker.sendMetadataBuffer(entityID);
            });
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.INT, SpawnPackets.toNewDouble);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.handler(wrapper -> {
               short item = wrapper.read(Type.SHORT);
               if (item != 0) {
                  PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_9.ENTITY_EQUIPMENT, null, wrapper.user());
                  packet.write(Type.VAR_INT, wrapper.get(Type.VAR_INT, 0));
                  packet.write(Type.VAR_INT, 0);
                  packet.write(Type.ITEM1_8, new DataItem(item, (byte)1, (short)0, null));

                  try {
                     packet.send(Protocol1_9To1_8.class);
                  } catch (Exception var4) {
                     var4.printStackTrace();
                  }
               }
            });
            this.map(Types1_8.METADATA_LIST, Types1_9.METADATA_LIST);
            this.handler(wrapper -> {
               List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
               int entityId = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               if (tracker.hasEntity(entityId)) {
                  protocol.get(MetadataRewriter1_9To1_8.class).handleMetadata(entityId, metadataList, wrapper.user());
               } else {
                  Via.getPlatform().getLogger().warning("Unable to find entity for metadata, entity ID: " + entityId);
                  metadataList.clear();
               }
            });
            this.handler(wrapper -> {
               List<Metadata> metadataList = wrapper.get(Types1_9.METADATA_LIST, 0);
               int entityID = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_9 tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);
               tracker.handleMetadata(entityID, metadataList);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.DESTROY_ENTITIES, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
            this.handler(wrapper -> {
               int[] entities = wrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0);
               EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_9To1_8.class);

               for (int entity : entities) {
                  tracker.removeEntity(entity);
               }
            });
         }
      });
   }
}
