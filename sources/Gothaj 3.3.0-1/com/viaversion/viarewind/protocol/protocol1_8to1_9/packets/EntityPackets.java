package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.api.minecraft.EntityModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Levitation;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.util.RelativeMoveUtil;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityPackets {
   public static void register(final Protocol1_8To1_9 protocol) {
      protocol.registerClientbound(ClientboundPackets1_9.ENTITY_STATUS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.handler(packetWrapper -> {
               byte status = packetWrapper.read(Type.BYTE);
               if (status > 23) {
                  packetWrapper.cancel();
               } else {
                  packetWrapper.write(Type.BYTE, status);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.ENTITY_POSITION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               int relX = packetWrapper.read(Type.SHORT);
               int relY = packetWrapper.read(Type.SHORT);
               int relZ = packetWrapper.read(Type.SHORT);
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               EntityModel replacement = tracker.getEntityReplacement(entityId);
               if (replacement != null) {
                  packetWrapper.cancel();
                  replacement.handleOriginalMovementPacket((double)relX / 4096.0, (double)relY / 4096.0, (double)relZ / 4096.0);
               } else {
                  Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
                  packetWrapper.write(Type.BYTE, (byte)moves[0].blockX());
                  packetWrapper.write(Type.BYTE, (byte)moves[0].blockY());
                  packetWrapper.write(Type.BYTE, (byte)moves[0].blockZ());
                  boolean onGround = packetWrapper.passthrough(Type.BOOLEAN);
                  if (moves.length > 1) {
                     PacketWrapper secondPacket = PacketWrapper.create(ClientboundPackets1_8.ENTITY_POSITION, null, packetWrapper.user());
                     secondPacket.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                     secondPacket.write(Type.BYTE, (byte)moves[1].blockX());
                     secondPacket.write(Type.BYTE, (byte)moves[1].blockY());
                     secondPacket.write(Type.BYTE, (byte)moves[1].blockZ());
                     secondPacket.write(Type.BOOLEAN, onGround);
                     PacketUtil.sendPacket(secondPacket, Protocol1_8To1_9.class);
                  }
               }
            });
         }
      });
      protocol.registerClientbound(
         ClientboundPackets1_9.ENTITY_POSITION_AND_ROTATION,
         new PacketHandlers() {
            @Override
            public void register() {
               this.map(Type.VAR_INT);
               this.handler(
                  packetWrapper -> {
                     int entityId = packetWrapper.get(Type.VAR_INT, 0);
                     int relX = packetWrapper.read(Type.SHORT);
                     int relY = packetWrapper.read(Type.SHORT);
                     int relZ = packetWrapper.read(Type.SHORT);
                     EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
                     EntityModel replacement = tracker.getEntityReplacement(entityId);
                     if (replacement != null) {
                        packetWrapper.cancel();
                        replacement.handleOriginalMovementPacket((double)relX / 4096.0, (double)relY / 4096.0, (double)relZ / 4096.0);
                        replacement.setYawPitch(
                           (float)packetWrapper.read(Type.BYTE).byteValue() * 360.0F / 256.0F,
                           (float)packetWrapper.read(Type.BYTE).byteValue() * 360.0F / 256.0F
                        );
                     } else {
                        Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
                        packetWrapper.write(Type.BYTE, (byte)moves[0].blockX());
                        packetWrapper.write(Type.BYTE, (byte)moves[0].blockY());
                        packetWrapper.write(Type.BYTE, (byte)moves[0].blockZ());
                        byte yaw = packetWrapper.passthrough(Type.BYTE);
                        byte pitch = packetWrapper.passthrough(Type.BYTE);
                        boolean onGround = packetWrapper.passthrough(Type.BOOLEAN);
                        EntityTypes1_10.EntityType type = packetWrapper.user().get(EntityTracker.class).getClientEntityTypes().get(entityId);
                        if (type == EntityTypes1_10.EntityType.BOAT) {
                           yaw = (byte)(yaw - 64);
                           packetWrapper.set(Type.BYTE, 3, yaw);
                        }

                        if (moves.length > 1) {
                           PacketWrapper secondPacket = PacketWrapper.create(ClientboundPackets1_8.ENTITY_POSITION_AND_ROTATION, null, packetWrapper.user());
                           secondPacket.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                           secondPacket.write(Type.BYTE, (byte)moves[1].blockX());
                           secondPacket.write(Type.BYTE, (byte)moves[1].blockY());
                           secondPacket.write(Type.BYTE, (byte)moves[1].blockZ());
                           secondPacket.write(Type.BYTE, yaw);
                           secondPacket.write(Type.BYTE, pitch);
                           secondPacket.write(Type.BOOLEAN, onGround);
                           PacketUtil.sendPacket(secondPacket, Protocol1_8To1_9.class);
                        }
                     }
                  }
               );
            }
         }
      );
      protocol.registerClientbound(ClientboundPackets1_9.ENTITY_ROTATION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               EntityModel replacement = tracker.getEntityReplacement(entityId);
               if (replacement != null) {
                  packetWrapper.cancel();
                  int yaw = packetWrapper.get(Type.BYTE, 0);
                  int pitch = packetWrapper.get(Type.BYTE, 1);
                  replacement.setYawPitch((float)yaw * 360.0F / 256.0F, (float)pitch * 360.0F / 256.0F);
               }
            });
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               EntityTypes1_10.EntityType type = packetWrapper.user().get(EntityTracker.class).getClientEntityTypes().get(entityId);
               if (type == EntityTypes1_10.EntityType.BOAT) {
                  byte yaw = packetWrapper.get(Type.BYTE, 0);
                  yaw = (byte)(yaw - 64);
                  packetWrapper.set(Type.BYTE, 0, yaw);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.VEHICLE_MOVE, ClientboundPackets1_8.ENTITY_TELEPORT, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(packetWrapper -> {
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               int vehicle = tracker.getVehicle(tracker.getPlayerId());
               if (vehicle == -1) {
                  packetWrapper.cancel();
               }

               packetWrapper.write(Type.VAR_INT, vehicle);
            });
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.FLOAT, Protocol1_8To1_9.DEGREES_TO_ANGLE);
            this.map(Type.FLOAT, Protocol1_8To1_9.DEGREES_TO_ANGLE);
            this.handler(packetWrapper -> {
               if (!packetWrapper.isCancelled()) {
                  PlayerPosition position = packetWrapper.user().get(PlayerPosition.class);
                  double x = (double)packetWrapper.get(Type.INT, 0).intValue() / 32.0;
                  double y = (double)packetWrapper.get(Type.INT, 1).intValue() / 32.0;
                  double z = (double)packetWrapper.get(Type.INT, 2).intValue() / 32.0;
                  position.setPos(x, y, z);
               }
            });
            this.create(Type.BOOLEAN, Boolean.valueOf(true));
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               EntityTypes1_10.EntityType type = packetWrapper.user().get(EntityTracker.class).getClientEntityTypes().get(entityId);
               if (type == EntityTypes1_10.EntityType.BOAT) {
                  byte yaw = packetWrapper.get(Type.BYTE, 1);
                  yaw = (byte)(yaw - 64);
                  packetWrapper.set(Type.BYTE, 0, yaw);
                  int y = packetWrapper.get(Type.INT, 1);
                  y += 10;
                  packetWrapper.set(Type.INT, 1, y);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.DESTROY_ENTITIES, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT_ARRAY_PRIMITIVE);
            this.handler(packetWrapper -> {
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);

               for (int entityId : packetWrapper.get(Type.VAR_INT_ARRAY_PRIMITIVE, 0)) {
                  tracker.removeEntity(entityId);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.REMOVE_ENTITY_EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.handler(packetWrapper -> {
               int id = packetWrapper.get(Type.BYTE, 0);
               if (id > 23) {
                  packetWrapper.cancel();
               }

               if (id == 25) {
                  if (packetWrapper.get(Type.VAR_INT, 0) != packetWrapper.user().get(EntityTracker.class).getPlayerId()) {
                     return;
                  }

                  Levitation levitation = packetWrapper.user().get(Levitation.class);
                  levitation.setActive(false);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.ENTITY_HEAD_LOOK, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               EntityModel replacement = tracker.getEntityReplacement(entityId);
               if (replacement != null) {
                  packetWrapper.cancel();
                  int yaw = packetWrapper.get(Type.BYTE, 0);
                  replacement.setHeadYaw((float)yaw * 360.0F / 256.0F);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.ENTITY_METADATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Types1_9.METADATA_LIST, Types1_8.METADATA_LIST);
            this.handler(wrapper -> {
               List<Metadata> metadataList = wrapper.get(Types1_8.METADATA_LIST, 0);
               int entityId = wrapper.get(Type.VAR_INT, 0);
               EntityTracker tracker = wrapper.user().get(EntityTracker.class);
               if (tracker.getClientEntityTypes().containsKey(entityId)) {
                  protocol.getMetadataRewriter().transform(tracker, entityId, metadataList);
                  if (metadataList.isEmpty()) {
                     wrapper.cancel();
                  }
               } else {
                  tracker.addMetadataToBuffer(entityId, metadataList);
                  wrapper.cancel();
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.ATTACH_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.INT);
            this.create(Type.BOOLEAN, Boolean.valueOf(true));
         }
      });
      protocol.registerClientbound(
         ClientboundPackets1_9.ENTITY_EQUIPMENT,
         new PacketHandlers() {
            @Override
            public void register() {
               this.map(Type.VAR_INT);
               this.handler(packetWrapper -> {
                  int slot = packetWrapper.read(Type.VAR_INT);
                  if (slot == 1) {
                     packetWrapper.cancel();
                  } else if (slot > 1) {
                     slot--;
                  }

                  packetWrapper.write(Type.SHORT, (short)slot);
               });
               this.map(Type.ITEM1_8);
               this.handler(
                  packetWrapper -> packetWrapper.set(Type.ITEM1_8, 0, protocol.getItemRewriter().handleItemToClient(packetWrapper.get(Type.ITEM1_8, 0)))
               );
            }
         }
      );
      protocol.registerClientbound(ClientboundPackets1_9.SET_PASSENGERS, null, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(packetWrapper -> {
               packetWrapper.cancel();
               EntityTracker entityTracker = packetWrapper.user().get(EntityTracker.class);
               int vehicle = packetWrapper.read(Type.VAR_INT);
               int count = packetWrapper.read(Type.VAR_INT);
               ArrayList<Integer> passengers = new ArrayList<>();

               for (int i = 0; i < count; i++) {
                  passengers.add(packetWrapper.read(Type.VAR_INT));
               }

               List<Integer> oldPassengers = entityTracker.getPassengers(vehicle);
               entityTracker.setPassengers(vehicle, passengers);
               if (!oldPassengers.isEmpty()) {
                  for (Integer passenger : oldPassengers) {
                     PacketWrapper detach = PacketWrapper.create(ClientboundPackets1_8.ATTACH_ENTITY, null, packetWrapper.user());
                     detach.write(Type.INT, passenger);
                     detach.write(Type.INT, -1);
                     detach.write(Type.BOOLEAN, false);
                     PacketUtil.sendPacket(detach, Protocol1_8To1_9.class);
                  }
               }

               for (int i = 0; i < count; i++) {
                  int v = i == 0 ? vehicle : passengers.get(i - 1);
                  int p = passengers.get(i);
                  PacketWrapper attach = PacketWrapper.create(ClientboundPackets1_8.ATTACH_ENTITY, null, packetWrapper.user());
                  attach.write(Type.INT, p);
                  attach.write(Type.INT, v);
                  attach.write(Type.BOOLEAN, false);
                  PacketUtil.sendPacket(attach, Protocol1_8To1_9.class);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.ENTITY_TELEPORT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               EntityTypes1_10.EntityType type = packetWrapper.user().get(EntityTracker.class).getClientEntityTypes().get(entityId);
               if (type == EntityTypes1_10.EntityType.BOAT) {
                  byte yaw = packetWrapper.get(Type.BYTE, 1);
                  yaw = (byte)(yaw - 64);
                  packetWrapper.set(Type.BYTE, 0, yaw);
                  int y = packetWrapper.get(Type.INT, 1);
                  y += 10;
                  packetWrapper.set(Type.INT, 1, y);
               }
            });
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               packetWrapper.user().get(EntityTracker.class).resetEntityOffset(entityId);
            });
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.get(Type.VAR_INT, 0);
               EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
               EntityModel replacement = tracker.getEntityReplacement(entityId);
               if (replacement != null) {
                  packetWrapper.cancel();
                  int x = packetWrapper.get(Type.INT, 0);
                  int y = packetWrapper.get(Type.INT, 1);
                  int z = packetWrapper.get(Type.INT, 2);
                  int yaw = packetWrapper.get(Type.BYTE, 0);
                  int pitch = packetWrapper.get(Type.BYTE, 1);
                  replacement.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                  replacement.setYawPitch((float)yaw * 360.0F / 256.0F, (float)pitch * 360.0F / 256.0F);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.ENTITY_PROPERTIES, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.handler(packetWrapper -> {
               boolean player = packetWrapper.get(Type.VAR_INT, 0) == packetWrapper.user().get(EntityTracker.class).getPlayerId();
               int size = packetWrapper.get(Type.INT, 0);
               int removed = 0;

               for (int i = 0; i < size; i++) {
                  String key = packetWrapper.read(Type.STRING);
                  boolean skip = !Protocol1_8To1_9.VALID_ATTRIBUTES.contains(key);
                  double value = packetWrapper.read(Type.DOUBLE);
                  int modifierSize = packetWrapper.read(Type.VAR_INT);
                  if (!skip) {
                     packetWrapper.write(Type.STRING, key);
                     packetWrapper.write(Type.DOUBLE, value);
                     packetWrapper.write(Type.VAR_INT, modifierSize);
                  } else {
                     removed++;
                  }

                  ArrayList<Pair<Byte, Double>> modifiers = new ArrayList<>();

                  for (int j = 0; j < modifierSize; j++) {
                     UUID uuid = packetWrapper.read(Type.UUID);
                     double amount = packetWrapper.read(Type.DOUBLE);
                     byte operation = packetWrapper.read(Type.BYTE);
                     modifiers.add(new Pair<>(operation, amount));
                     if (!skip) {
                        packetWrapper.write(Type.UUID, uuid);
                        packetWrapper.write(Type.DOUBLE, amount);
                        packetWrapper.write(Type.BYTE, operation);
                     }
                  }

                  if (player && key.equals("generic.attackSpeed")) {
                     packetWrapper.user().get(Cooldown.class).setAttackSpeed(value, modifiers);
                  }
               }

               packetWrapper.set(Type.INT, 0, size - removed);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.ENTITY_EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.handler(packetWrapper -> {
               int id = packetWrapper.get(Type.BYTE, 0);
               if (id > 23) {
                  packetWrapper.cancel();
               }

               if (id == 25) {
                  if (packetWrapper.get(Type.VAR_INT, 0) != packetWrapper.user().get(EntityTracker.class).getPlayerId()) {
                     return;
                  }

                  Levitation levitation = packetWrapper.user().get(Levitation.class);
                  levitation.setActive(true);
                  levitation.setAmplifier(packetWrapper.get(Type.BYTE, 1));
               }
            });
         }
      });
   }
}
