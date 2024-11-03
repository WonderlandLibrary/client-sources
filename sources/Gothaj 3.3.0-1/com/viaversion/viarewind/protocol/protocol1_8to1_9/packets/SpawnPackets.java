package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.minecraft.EntityModel;
import com.viaversion.viarewind.api.rewriter.item.Replacement;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.entityreplacement.ShulkerBulletModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.entityreplacement.ShulkerModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import java.util.List;

public class SpawnPackets {
   public static void register(final Protocol1_8To1_9 protocol) {
      protocol.registerClientbound(ClientboundPackets1_9.SPAWN_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.read(Type.UUID);
            this.map(Type.BYTE);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               int typeId = packetWrapper.get(Type.BYTE, 0);
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               EntityTypes1_10.EntityType type = EntityTypes1_10.getTypeFromId(typeId, true);
               if (typeId == 3 || typeId == 91 || typeId == 92 || typeId == 93) {
                  packetWrapper.cancel();
               } else if (type == null) {
                  ViaRewind.getPlatform().getLogger().warning("[ViaRewind] Unhandled Spawn Object Type: " + typeId);
                  packetWrapper.cancel();
               } else {
                  int x = packetWrapper.get(Type.INT, 0);
                  int y = packetWrapper.get(Type.INT, 1);
                  int z = packetWrapper.get(Type.INT, 2);
                  if (type.is(EntityTypes1_10.EntityType.BOAT)) {
                     byte yaw = packetWrapper.get(Type.BYTE, 1);
                     yaw = (byte)(yaw - 64);
                     packetWrapper.set(Type.BYTE, 1, yaw);
                     y += 10;
                     packetWrapper.set(Type.INT, 1, y);
                  } else if (type.is(EntityTypes1_10.EntityType.SHULKER_BULLET)) {
                     packetWrapper.cancel();
                     ShulkerBulletModel shulkerBulletReplacement = new ShulkerBulletModel(packetWrapper.user(), protocol, entityId);
                     shulkerBulletReplacement.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                     tracker.addEntityReplacement(shulkerBulletReplacement);
                     return;
                  }

                  int data = packetWrapper.get(Type.INT, 3);
                  if (type.isOrHasParent(EntityTypes1_10.EntityType.ARROW) && data != 0) {
                     packetWrapper.set(Type.INT, 3, --data);
                  }

                  if (type.is(EntityTypes1_10.EntityType.FALLING_BLOCK)) {
                     int blockId = data & 4095;
                     int blockData = data >> 12 & 15;
                     Replacement replace = protocol.getItemRewriter().replace(blockId, blockData);
                     if (replace != null) {
                        packetWrapper.set(Type.INT, 3, replace.getId() | replace.replaceData(data) << 12);
                     }
                  }

                  if (data > 0) {
                     packetWrapper.passthrough(Type.SHORT);
                     packetWrapper.passthrough(Type.SHORT);
                     packetWrapper.passthrough(Type.SHORT);
                  } else {
                     short vX = packetWrapper.read(Type.SHORT);
                     short vY = packetWrapper.read(Type.SHORT);
                     short vZ = packetWrapper.read(Type.SHORT);
                     PacketWrapper velocityPacket = PacketWrapper.create(18, null, packetWrapper.user());
                     velocityPacket.write(Type.VAR_INT, entityId);
                     velocityPacket.write(Type.SHORT, vX);
                     velocityPacket.write(Type.SHORT, vY);
                     velocityPacket.write(Type.SHORT, vZ);
                     PacketUtil.sendPacket(velocityPacket, Protocol1_8To1_9.class);
                  }

                  tracker.getClientEntityTypes().put(entityId, type);
                  tracker.sendMetadataBuffer(entityId);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.SPAWN_EXPERIENCE_ORB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.SHORT);
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               tracker.getClientEntityTypes().put(entityId, EntityTypes1_10.EntityType.EXPERIENCE_ORB);
               tracker.sendMetadataBuffer(entityId);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.SPAWN_GLOBAL_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               tracker.getClientEntityTypes().put(entityId, EntityTypes1_10.EntityType.LIGHTNING);
               tracker.sendMetadataBuffer(entityId);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.SPAWN_MOB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.read(Type.UUID);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               int typeId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
               int x = packetWrapper.get(Type.INT, 0);
               int y = packetWrapper.get(Type.INT, 1);
               int z = packetWrapper.get(Type.INT, 2);
               byte pitch = packetWrapper.get(Type.BYTE, 1);
               byte yaw = packetWrapper.get(Type.BYTE, 0);
               byte headYaw = packetWrapper.get(Type.BYTE, 2);
               if (typeId == 69) {
                  packetWrapper.cancel();
                  EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                  ShulkerModel shulkerReplacement = new ShulkerModel(packetWrapper.user(), protocol, entityId);
                  shulkerReplacement.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                  shulkerReplacement.setYawPitch((float)yaw * 360.0F / 256.0F, (float)pitch * 360.0F / 256.0F);
                  shulkerReplacement.setHeadYaw((float)headYaw * 360.0F / 256.0F);
                  tracker.addEntityReplacement(shulkerReplacement);
               } else if (typeId == -1 || typeId == 255) {
                  packetWrapper.cancel();
               }
            });
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               int typeId = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               tracker.getClientEntityTypes().put(entityId, EntityTypes1_10.getTypeFromId(typeId, false));
               tracker.sendMetadataBuffer(entityId);
            });
            this.handler(wrapper -> {
               List<Metadata> metadataList = wrapper.get(Types1_8.METADATA_LIST, 0);
               int entityId = wrapper.get(Type.VAR_INT, 0);
               EntityTracker tracker = wrapper.user().get(EntityTracker.class);
               EntityModel replacement;
               if ((replacement = tracker.getEntityReplacement(entityId)) != null) {
                  replacement.updateMetadata(metadataList);
               } else if (tracker.getClientEntityTypes().containsKey(entityId)) {
                  protocol.getMetadataRewriter().transform(tracker, entityId, metadataList);
               } else {
                  wrapper.cancel();
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.SPAWN_PAINTING, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.read(Type.UUID);
            this.map(Type.STRING);
            this.map(Type.POSITION1_8);
            this.map(Type.BYTE, Type.UNSIGNED_BYTE);
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               tracker.getClientEntityTypes().put(entityId, EntityTypes1_10.EntityType.PAINTING);
               tracker.sendMetadataBuffer(entityId);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.handler(packetWrapper -> packetWrapper.write(Type.SHORT, (short)0));
            this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
            this.handler(wrapper -> {
               int entityId = wrapper.get(Type.VAR_INT, 0);
               List<Metadata> metadataList = wrapper.get(Types1_8.METADATA_LIST, 0);
               protocol.getMetadataRewriter().transform(wrapper.user().get(EntityTracker.class), entityId, metadataList, EntityTypes1_10.EntityType.PLAYER);
            });
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               tracker.getClientEntityTypes().put(entityId, EntityTypes1_10.EntityType.PLAYER);
               tracker.sendMetadataBuffer(entityId);
            });
         }
      });
   }
}
