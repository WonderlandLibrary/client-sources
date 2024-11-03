package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.api.rewriter.item.Replacement;
import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.VirtualHologramEntity;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.List;
import java.util.UUID;

public class SpawnPackets {
   public static void register(final Protocol1_7_6_10To1_8 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               UUID uuid = wrapper.read(Type.UUID);
               wrapper.write(Type.STRING, uuid.toString());
               GameProfileStorage gameProfileStorage = wrapper.user().get(GameProfileStorage.class);
               GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
               if (gameProfile == null) {
                  wrapper.write(Type.STRING, "");
                  wrapper.write(Type.VAR_INT, 0);
               } else {
                  wrapper.write(Type.STRING, gameProfile.name.length() > 16 ? gameProfile.name.substring(0, 16) : gameProfile.name);
                  wrapper.write(Type.VAR_INT, gameProfile.properties.size());

                  for (GameProfileStorage.Property property : gameProfile.properties) {
                     wrapper.write(Type.STRING, property.name);
                     wrapper.write(Type.STRING, property.value);
                     wrapper.write(Type.STRING, property.signature == null ? "" : property.signature);
                  }
               }

               int entityId = wrapper.get(Type.VAR_INT, 0);
               EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
               if (gameProfile != null && gameProfile.gamemode == 3) {
                  for (short i = 0; i < 5; i++) {
                     PacketWrapper entityEquipment = PacketWrapper.create(ClientboundPackets1_7_2_5.ENTITY_EQUIPMENT, wrapper.user());
                     entityEquipment.write(Type.INT, entityId);
                     entityEquipment.write(Type.SHORT, i);
                     entityEquipment.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, i == 4 ? gameProfile.getSkull() : null);
                     entityEquipment.scheduleSend(Protocol1_7_6_10To1_8.class, true);
                  }
               }

               tracker.addPlayer(entityId, uuid);
            });
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
            this.handler(wrapper -> {
               List<Metadata> metadata = wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
               protocol.getMetadataRewriter().transform(EntityTypes1_10.EntityType.PLAYER, metadata);
               wrapper.set(Types1_7_6_10.METADATA_LIST, 0, metadata);
            });
            this.handler(wrapper -> {
               EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
               tracker.addEntity(wrapper.get(Type.VAR_INT, 0), EntityTypes1_10.EntityType.PLAYER);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.handler(wrapper -> {
               EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
               EntityTypes1_10.EntityType type = EntityTypes1_10.getTypeFromId(wrapper.get(Type.BYTE, 0), true);
               int entityId = wrapper.get(Type.VAR_INT, 0);
               int x = wrapper.get(Type.INT, 0);
               int y = wrapper.get(Type.INT, 1);
               int z = wrapper.get(Type.INT, 2);
               byte pitch = wrapper.get(Type.BYTE, 1);
               byte yaw = wrapper.get(Type.BYTE, 2);
               int data = wrapper.get(Type.INT, 3);
               if (type == EntityTypes1_10.ObjectType.ITEM_FRAME.getType()) {
                  switch (yaw) {
                     case -128:
                        z += 32;
                        yaw = 0;
                        break;
                     case -64:
                        x -= 32;
                        yaw = -64;
                        break;
                     case 0:
                        z -= 32;
                        yaw = -128;
                        break;
                     case 64:
                        x += 32;
                        yaw = 64;
                  }
               } else if (type == EntityTypes1_10.ObjectType.ARMOR_STAND.getType()) {
                  wrapper.cancel();
                  VirtualHologramEntity hologram = new VirtualHologramEntity(wrapper.user(), protocol.getMetadataRewriter(), entityId);
                  hologram.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                  hologram.setYawPitch((float)yaw * 360.0F / 256.0F, (float)pitch * 360.0F / 256.0F);
                  hologram.setHeadYaw((float)yaw * 360.0F / 256.0F);
                  tracker.trackHologram(entityId, hologram);
               }

               wrapper.set(Type.INT, 0, x);
               wrapper.set(Type.INT, 1, y);
               wrapper.set(Type.INT, 2, z);
               wrapper.set(Type.BYTE, 2, yaw);
               tracker.addEntity(entityId, type);
               if (type != null && type.isOrHasParent(EntityTypes1_10.EntityType.FALLING_BLOCK)) {
                  int blockId = data & 4095;
                  int blockData = data >> 12 & 15;
                  Replacement replace = protocol.getItemRewriter().replace(blockId, blockData);
                  if (replace != null) {
                     blockId = replace.getId();
                     blockData = replace.replaceData(blockData);
                  }

                  wrapper.set(Type.INT, 3, data = blockId | blockData << 16);
               }

               if (data > 0) {
                  wrapper.passthrough(Type.SHORT);
                  wrapper.passthrough(Type.SHORT);
                  wrapper.passthrough(Type.SHORT);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_MOB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
            this.handler(wrapper -> {
               EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
               short typeId = wrapper.get(Type.UNSIGNED_BYTE, 0);
               if (typeId == 255 || typeId == -1) {
                  wrapper.cancel();
               }

               EntityTypes1_10.EntityType type = EntityTypes1_10.getTypeFromId(typeId, false);
               int entityId = wrapper.get(Type.VAR_INT, 0);
               int x = wrapper.get(Type.INT, 0);
               int y = wrapper.get(Type.INT, 1);
               int z = wrapper.get(Type.INT, 2);
               byte pitch = wrapper.get(Type.BYTE, 1);
               byte yaw = wrapper.get(Type.BYTE, 0);
               byte headYaw = wrapper.get(Type.BYTE, 2);
               List<Metadata> metadataList = wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
               if (type == EntityTypes1_10.EntityType.ARMOR_STAND) {
                  VirtualHologramEntity hologram = new VirtualHologramEntity(wrapper.user(), protocol.getMetadataRewriter(), entityId);
                  hologram.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                  hologram.setYawPitch((float)yaw * 360.0F / 256.0F, (float)pitch * 360.0F / 256.0F);
                  hologram.setHeadYaw((float)headYaw * 360.0F / 256.0F);
                  tracker.trackHologram(entityId, hologram);
                  tracker.updateMetadata(entityId, metadataList);
                  wrapper.cancel();
               } else {
                  protocol.getMetadataRewriter().transform(type, metadataList);
                  tracker.addEntity(entityId, type);
                  if (tracker.isReplaced(type)) {
                     int newTypeId = tracker.replaceEntity(entityId, type);
                     wrapper.set(Type.UNSIGNED_BYTE, 0, (short)newTypeId);
                     tracker.updateMetadata(entityId, metadataList);
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_PAINTING, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.STRING);
            this.handler(wrapper -> {
               Position position = wrapper.read(Type.POSITION1_8);
               wrapper.write(Type.INT, position.x());
               wrapper.write(Type.INT, position.y());
               wrapper.write(Type.INT, position.z());
            });
            this.map(Type.UNSIGNED_BYTE, Type.INT);
            this.handler(wrapper -> {
               EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
               tracker.addEntity(wrapper.get(Type.VAR_INT, 0), EntityTypes1_10.EntityType.PAINTING);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_EXPERIENCE_ORB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.SHORT);
            this.handler(wrapper -> {
               EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
               tracker.addEntity(wrapper.get(Type.VAR_INT, 0), EntityTypes1_10.EntityType.EXPERIENCE_ORB);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_GLOBAL_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.handler(wrapper -> {
               EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
               tracker.addEntity(wrapper.get(Type.VAR_INT, 0), EntityTypes1_10.EntityType.LIGHTNING);
            });
         }
      });
   }
}
