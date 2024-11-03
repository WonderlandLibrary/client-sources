package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ServerboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.InventoryTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viarewind.utils.math.AABB;
import com.viaversion.viarewind.utils.math.Ray3d;
import com.viaversion.viarewind.utils.math.RayTracing;
import com.viaversion.viarewind.utils.math.Vector3d;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.util.ComponentUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class PlayerPackets {
   public static void register(final Protocol1_7_6_10To1_8 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.STRING);
            this.read(Type.BOOLEAN);
            this.handler(wrapper -> {
               if (ViaRewind.getConfig().isReplaceAdventureMode() && wrapper.get(Type.UNSIGNED_BYTE, 0) == 2) {
                  wrapper.set(Type.UNSIGNED_BYTE, 0, (short)0);
               }

               EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
               tracker.setClientEntityId(wrapper.get(Type.INT, 0));
               tracker.addPlayer(wrapper.get(Type.INT, 0), wrapper.user().getProtocolInfo().getUuid());
               wrapper.user().get(PlayerSessionStorage.class).gameMode = wrapper.get(Type.UNSIGNED_BYTE, 0);
               wrapper.user().get(ClientWorld.class).setEnvironment(wrapper.get(Type.BYTE, 0));
               wrapper.user().put(new Scoreboard(wrapper.user()));
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.CHAT_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.COMPONENT);
            this.handler(wrapper -> {
               int position = wrapper.read(Type.BYTE);
               if (position == 2) {
                  wrapper.cancel();
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SPAWN_POSITION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8, Types1_7_6_10.INT_POSITION);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.UPDATE_HEALTH, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.FLOAT);
            this.map(Type.VAR_INT, Type.SHORT);
            this.map(Type.FLOAT);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.STRING);
            this.handler(wrapper -> {
               if (ViaRewind.getConfig().isReplaceAdventureMode() && wrapper.get(Type.UNSIGNED_BYTE, 1) == 2) {
                  wrapper.set(Type.UNSIGNED_BYTE, 1, (short)0);
               }

               wrapper.user().get(PlayerSessionStorage.class).gameMode = wrapper.get(Type.UNSIGNED_BYTE, 1);
               ClientWorld world = wrapper.user().get(ClientWorld.class);
               Environment dimension = Environment.getEnvironmentById(wrapper.get(Type.INT, 0));
               EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
               if (world.getEnvironment() != dimension) {
                  world.setEnvironment(dimension.id());
                  tracker.clear();
                  tracker.getEntityMap().put(tracker.getPlayerId(), EntityTypes1_10.EntityType.ENTITY_HUMAN);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.PLAYER_POSITION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.handler(wrapper -> {
               double x = wrapper.get(Type.DOUBLE, 0);
               double y = wrapper.get(Type.DOUBLE, 1);
               double z = wrapper.get(Type.DOUBLE, 2);
               float yaw = wrapper.get(Type.FLOAT, 0);
               float pitch = wrapper.get(Type.FLOAT, 1);
               PlayerSessionStorage playerSession = wrapper.user().get(PlayerSessionStorage.class);
               int flags = wrapper.read(Type.BYTE);
               if ((flags & 1) == 1) {
                  wrapper.set(Type.DOUBLE, 0, x + playerSession.getPosX());
               }

               if ((flags & 2) == 2) {
                  y += playerSession.getPosY();
               }

               playerSession.receivedPosY = y;
               wrapper.set(Type.DOUBLE, 1, y + 1.62F);
               if ((flags & 4) == 4) {
                  wrapper.set(Type.DOUBLE, 2, z + playerSession.getPosZ());
               }

               if ((flags & 8) == 8) {
                  wrapper.set(Type.FLOAT, 0, yaw + playerSession.yaw);
               }

               if ((flags & 16) == 16) {
                  wrapper.set(Type.FLOAT, 1, pitch + playerSession.pitch);
               }

               wrapper.write(Type.BOOLEAN, playerSession.onGround);
               EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
               if (tracker.spectatingPlayerId != tracker.getPlayerId()) {
                  wrapper.cancel();
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.SET_EXPERIENCE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.FLOAT);
            this.map(Type.VAR_INT, Type.SHORT);
            this.map(Type.VAR_INT, Type.SHORT);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.GAME_EVENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.FLOAT);
            this.handler(wrapper -> {
               if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 3) {
                  int gameMode = wrapper.get(Type.FLOAT, 0).intValue();
                  PlayerSessionStorage playerSession = wrapper.user().get(PlayerSessionStorage.class);
                  if (gameMode == 3 || playerSession.gameMode == 3) {
                     UUID myId = wrapper.user().getProtocolInfo().getUuid();
                     Item[] equipment = new Item[4];
                     if (gameMode == 3) {
                        GameProfileStorage.GameProfile profile = wrapper.user().get(GameProfileStorage.class).get(myId);
                        equipment[3] = profile == null ? null : profile.getSkull();
                     } else {
                        for (int i = 0; i < equipment.length; i++) {
                           equipment[i] = playerSession.getPlayerEquipment(myId, i);
                        }
                     }

                     for (int i = 0; i < equipment.length; i++) {
                        PacketWrapper setSlot = PacketWrapper.create(ClientboundPackets1_7_2_5.SET_SLOT, wrapper.user());
                        setSlot.write(Type.BYTE, (byte)0);
                        setSlot.write(Type.SHORT, (short)(8 - i));
                        setSlot.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, equipment[i]);
                        PacketUtil.sendPacket(setSlot, Protocol1_7_6_10To1_8.class);
                     }
                  }

                  if (gameMode == 2 && ViaRewind.getConfig().isReplaceAdventureMode()) {
                     gameMode = 0;
                     wrapper.set(Type.FLOAT, 0, 0.0F);
                  }

                  wrapper.user().get(PlayerSessionStorage.class).gameMode = gameMode;
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.OPEN_SIGN_EDITOR, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8, Types1_7_6_10.INT_POSITION);
         }
      });
      protocol.registerClientbound(
         ClientboundPackets1_8.PLAYER_INFO,
         new PacketHandlers() {
            @Override
            public void register() {
               this.handler(
                  packetWrapper -> {
                     packetWrapper.cancel();
                     int action = packetWrapper.read(Type.VAR_INT);
                     int count = packetWrapper.read(Type.VAR_INT);
                     GameProfileStorage gameProfileStorage = packetWrapper.user().get(GameProfileStorage.class);

                     for (int i = 0; i < count; i++) {
                        UUID uuid = packetWrapper.read(Type.UUID);
                        if (action == 0) {
                           String name = packetWrapper.read(Type.STRING);
                           GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
                           if (gameProfile == null) {
                              gameProfile = gameProfileStorage.put(uuid, name);
                           }

                           int propertyCount = packetWrapper.read(Type.VAR_INT);

                           while (propertyCount-- > 0) {
                              String propertyName = packetWrapper.read(Type.STRING);
                              String propertyValue = packetWrapper.read(Type.STRING);
                              String propertySignature = packetWrapper.read(Type.OPTIONAL_STRING);
                              gameProfile.properties.add(new GameProfileStorage.Property(propertyName, propertyValue, propertySignature));
                           }

                           int gamemode = packetWrapper.read(Type.VAR_INT);
                           int ping = packetWrapper.read(Type.VAR_INT);
                           gameProfile.ping = ping;
                           gameProfile.gamemode = gamemode;
                           JsonElement displayName = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                           if (displayName != null) {
                              gameProfile.setDisplayName(ChatUtil.jsonToLegacy(displayName));
                           }

                           PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                           packet.write(Type.STRING, gameProfile.getDisplayName());
                           packet.write(Type.BOOLEAN, true);
                           packet.write(Type.SHORT, (short)ping);
                           PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                        } else if (action == 1) {
                           int gamemode = packetWrapper.read(Type.VAR_INT);
                           GameProfileStorage.GameProfile gameProfilex = gameProfileStorage.get(uuid);
                           if (gameProfilex != null && gameProfilex.gamemode != gamemode) {
                              if (gamemode == 3 || gameProfilex.gamemode == 3) {
                                 EntityTracker1_7_6_10 tracker = packetWrapper.user().get(EntityTracker1_7_6_10.class);
                                 int entityId = tracker.getPlayerEntityId(uuid);
                                 boolean isOwnPlayer = entityId == tracker.getPlayerId();
                                 if (entityId != -1) {
                                    Item[] equipment = new Item[isOwnPlayer ? 4 : 5];
                                    if (gamemode == 3) {
                                       equipment[isOwnPlayer ? 3 : 4] = gameProfilex.getSkull();
                                    } else {
                                       for (int j = 0; j < equipment.length; j++) {
                                          equipment[j] = packetWrapper.user().get(PlayerSessionStorage.class).getPlayerEquipment(uuid, j);
                                       }
                                    }

                                    for (short slot = 0; slot < equipment.length; slot++) {
                                       PacketWrapper equipmentPacket = PacketWrapper.create(ClientboundPackets1_7_2_5.ENTITY_EQUIPMENT, packetWrapper.user());
                                       equipmentPacket.write(Type.INT, entityId);
                                       equipmentPacket.write(Type.SHORT, slot);
                                       equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, equipment[slot]);
                                       PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10To1_8.class);
                                    }
                                 }
                              }

                              gameProfilex.gamemode = gamemode;
                           }
                        } else if (action == 2) {
                           int ping = packetWrapper.read(Type.VAR_INT);
                           GameProfileStorage.GameProfile gameProfilex = gameProfileStorage.get(uuid);
                           if (gameProfilex != null) {
                              PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                              packet.write(Type.STRING, gameProfilex.getDisplayName());
                              packet.write(Type.BOOLEAN, false);
                              packet.write(Type.SHORT, (short)gameProfilex.ping);
                              PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                              gameProfilex.ping = ping;
                              packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                              packet.write(Type.STRING, gameProfilex.getDisplayName());
                              packet.write(Type.BOOLEAN, true);
                              packet.write(Type.SHORT, (short)ping);
                              PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                           }
                        } else if (action == 3) {
                           JsonElement displayNameComponent = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                           String displayName = displayNameComponent != null ? ChatUtil.jsonToLegacy(displayNameComponent) : null;
                           GameProfileStorage.GameProfile gameProfilex = gameProfileStorage.get(uuid);
                           if (gameProfilex != null && (gameProfilex.displayName != null || displayName != null)) {
                              PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                              packet.write(Type.STRING, gameProfilex.getDisplayName());
                              packet.write(Type.BOOLEAN, false);
                              packet.write(Type.SHORT, (short)gameProfilex.ping);
                              PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                              if (gameProfilex.displayName == null && displayName != null
                                 || gameProfilex.displayName != null && displayName == null
                                 || !gameProfilex.displayName.equals(displayName)) {
                                 gameProfilex.setDisplayName(displayName);
                              }

                              packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                              packet.write(Type.STRING, gameProfilex.getDisplayName());
                              packet.write(Type.BOOLEAN, true);
                              packet.write(Type.SHORT, (short)gameProfilex.ping);
                              PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                           }
                        } else if (action == 4) {
                           GameProfileStorage.GameProfile gameProfilex = gameProfileStorage.remove(uuid);
                           if (gameProfilex != null) {
                              PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                              packet.write(Type.STRING, gameProfilex.getDisplayName());
                              packet.write(Type.BOOLEAN, false);
                              packet.write(Type.SHORT, (short)gameProfilex.ping);
                              PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                           }
                        }
                     }
                  }
               );
            }
         }
      );
      protocol.registerClientbound(ClientboundPackets1_8.PLAYER_ABILITIES, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.BYTE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.handler(packetWrapper -> {
               byte flags = packetWrapper.get(Type.BYTE, 0);
               float flySpeed = packetWrapper.get(Type.FLOAT, 0);
               float walkSpeed = packetWrapper.get(Type.FLOAT, 1);
               PlayerSessionStorage abilities = packetWrapper.user().get(PlayerSessionStorage.class);
               abilities.invincible = (flags & 8) == 8;
               abilities.allowFly = (flags & 4) == 4;
               abilities.flying = (flags & 2) == 2;
               abilities.creative = (flags & 1) == 1;
               abilities.flySpeed = flySpeed;
               abilities.walkSpeed = walkSpeed;
               if (abilities.sprinting && abilities.flying) {
                  packetWrapper.set(Type.FLOAT, 0, abilities.flySpeed * 2.0F);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(packetWrapper -> {
               String channel = packetWrapper.get(Type.STRING, 0);
               if (channel.equalsIgnoreCase("MC|TrList")) {
                  packetWrapper.passthrough(Type.INT);
                  int size;
                  if (packetWrapper.isReadable(Type.BYTE, 0)) {
                     size = packetWrapper.passthrough(Type.BYTE);
                  } else {
                     size = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                  }

                  for (int i = 0; i < size; i++) {
                     Item item = protocol.getItemRewriter().handleItemToClient(packetWrapper.read(Type.ITEM1_8));
                     packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
                     item = protocol.getItemRewriter().handleItemToClient(packetWrapper.read(Type.ITEM1_8));
                     packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
                     boolean has3Items = packetWrapper.passthrough(Type.BOOLEAN);
                     if (has3Items) {
                        item = protocol.getItemRewriter().handleItemToClient(packetWrapper.read(Type.ITEM1_8));
                        packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
                     }

                     packetWrapper.passthrough(Type.BOOLEAN);
                     packetWrapper.read(Type.INT);
                     packetWrapper.read(Type.INT);
                  }
               } else if (channel.equalsIgnoreCase("MC|Brand")) {
                  packetWrapper.write(Type.REMAINING_BYTES, packetWrapper.read(Type.STRING).getBytes(StandardCharsets.UTF_8));
               }

               packetWrapper.cancel();
               packetWrapper.setPacketType(null);
               ByteBuf newPacketBuf = Unpooled.buffer();
               packetWrapper.writeToBuffer(newPacketBuf);
               PacketWrapper newWrapper = PacketWrapper.create(ClientboundPackets1_7_2_5.PLUGIN_MESSAGE, newPacketBuf, packetWrapper.user());
               newWrapper.passthrough(Type.STRING);
               if (newPacketBuf.readableBytes() <= 32767) {
                  newWrapper.write(Type.SHORT, (short)newPacketBuf.readableBytes());
                  newWrapper.send(Protocol1_7_6_10To1_8.class);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.CAMERA, null, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(packetWrapper -> {
               packetWrapper.cancel();
               EntityTracker1_7_6_10 tracker = packetWrapper.user().get(EntityTracker1_7_6_10.class);
               int entityId = packetWrapper.read(Type.VAR_INT);
               int spectating = tracker.spectatingPlayerId;
               if (spectating != entityId) {
                  tracker.setSpectating(entityId);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.TITLE, null, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(packetWrapper -> {
               packetWrapper.cancel();
               TitleRenderProvider titleRenderProvider = Via.getManager().getProviders().get(TitleRenderProvider.class);
               if (titleRenderProvider != null) {
                  int action = packetWrapper.read(Type.VAR_INT);
                  UUID uuid = packetWrapper.user().getProtocolInfo().getUuid();
                  switch (action) {
                     case 0:
                        titleRenderProvider.setTitle(uuid, packetWrapper.read(Type.STRING));
                        break;
                     case 1:
                        titleRenderProvider.setSubTitle(uuid, packetWrapper.read(Type.STRING));
                        break;
                     case 2:
                        titleRenderProvider.setTimings(uuid, packetWrapper.read(Type.INT), packetWrapper.read(Type.INT), packetWrapper.read(Type.INT));
                        break;
                     case 3:
                        titleRenderProvider.clear(uuid);
                        break;
                     case 4:
                        titleRenderProvider.reset(uuid);
                  }
               }
            });
         }
      });
      protocol.cancelClientbound(ClientboundPackets1_8.TAB_LIST);
      protocol.registerClientbound(ClientboundPackets1_8.RESOURCE_PACK, ClientboundPackets1_7_2_5.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.create(Type.STRING, "MC|RPack");
            this.handler(packetWrapper -> {
               ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();

               try {
                  Type.STRING.write(buf, packetWrapper.read(Type.STRING));
                  packetWrapper.write(Type.SHORT_BYTE_ARRAY, Type.REMAINING_BYTES.read(buf));
               } finally {
                  buf.release();
               }
            });
            this.read(Type.STRING);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.CHAT_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(packetWrapper -> {
               String msg = packetWrapper.get(Type.STRING, 0);
               int gamemode = packetWrapper.user().get(PlayerSessionStorage.class).gameMode;
               if (gamemode == 3 && msg.toLowerCase().startsWith("/stp ")) {
                  String username = msg.split(" ")[1];
                  GameProfileStorage storage = packetWrapper.user().get(GameProfileStorage.class);
                  GameProfileStorage.GameProfile profile = storage.get(username, true);
                  if (profile != null && profile.uuid != null) {
                     packetWrapper.cancel();
                     PacketWrapper teleportPacket = PacketWrapper.create(24, null, packetWrapper.user());
                     teleportPacket.write(Type.UUID, profile.uuid);
                     PacketUtil.sendToServer(teleportPacket, Protocol1_7_6_10To1_8.class, true, true);
                  }
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.INTERACT_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT, Type.VAR_INT);
            this.map(Type.BYTE, Type.VAR_INT);
            this.handler(wrapper -> {
               int mode = wrapper.get(Type.VAR_INT, 1);
               if (mode == 0) {
                  int entityId = wrapper.get(Type.VAR_INT, 0);
                  EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
                  PlayerSessionStorage position = wrapper.user().get(PlayerSessionStorage.class);
                  if (tracker.getVirtualHologramMap().containsKey(entityId)) {
                     AABB boundingBox = tracker.getVirtualHologramMap().get(entityId).getBoundingBox();
                     Vector3d pos = new Vector3d(position.getPosX(), position.getPosY() + 1.8, position.getPosZ());
                     double yaw = Math.toRadians((double)position.yaw);
                     double pitch = Math.toRadians((double)position.pitch);
                     Vector3d dir = new Vector3d(-Math.cos(pitch) * Math.sin(yaw), -Math.sin(pitch), Math.cos(pitch) * Math.cos(yaw));
                     Ray3d ray = new Ray3d(pos, dir);
                     Vector3d intersection = RayTracing.trace(ray, boundingBox, 5.0);
                     if (intersection == null) {
                        return;
                     }

                     intersection.substract(boundingBox.getMin());
                     int var14 = 2;
                     wrapper.set(Type.VAR_INT, 1, Integer.valueOf(var14));
                     wrapper.write(Type.FLOAT, (float)intersection.getX());
                     wrapper.write(Type.FLOAT, (float)intersection.getY());
                     wrapper.write(Type.FLOAT, (float)intersection.getZ());
                  }
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.PLAYER_MOVEMENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.BOOLEAN);
            this.handler(packetWrapper -> {
               PlayerSessionStorage playerSession = packetWrapper.user().get(PlayerSessionStorage.class);
               playerSession.onGround = packetWrapper.get(Type.BOOLEAN, 0);
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.PLAYER_POSITION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.read(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BOOLEAN);
            this.handler(packetWrapper -> {
               double x = packetWrapper.get(Type.DOUBLE, 0);
               double feetY = packetWrapper.get(Type.DOUBLE, 1);
               double z = packetWrapper.get(Type.DOUBLE, 2);
               PlayerSessionStorage playerSession = packetWrapper.user().get(PlayerSessionStorage.class);
               playerSession.onGround = packetWrapper.get(Type.BOOLEAN, 0);
               playerSession.setPos(x, feetY, z);
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.PLAYER_ROTATION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.BOOLEAN);
            this.handler(packetWrapper -> {
               PlayerSessionStorage playerSession = packetWrapper.user().get(PlayerSessionStorage.class);
               playerSession.yaw = packetWrapper.get(Type.FLOAT, 0);
               playerSession.pitch = packetWrapper.get(Type.FLOAT, 1);
               playerSession.onGround = packetWrapper.get(Type.BOOLEAN, 0);
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.PLAYER_POSITION_AND_ROTATION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.read(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.BOOLEAN);
            this.handler(packetWrapper -> {
               double x = packetWrapper.get(Type.DOUBLE, 0);
               double feetY = packetWrapper.get(Type.DOUBLE, 1);
               double z = packetWrapper.get(Type.DOUBLE, 2);
               float yaw = packetWrapper.get(Type.FLOAT, 0);
               float pitch = packetWrapper.get(Type.FLOAT, 1);
               PlayerSessionStorage playerSession = packetWrapper.user().get(PlayerSessionStorage.class);
               playerSession.onGround = packetWrapper.get(Type.BOOLEAN, 0);
               playerSession.setPos(x, feetY, z);
               playerSession.yaw = yaw;
               playerSession.pitch = pitch;
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.PLAYER_DIGGING, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(packetWrapper -> {
               int x = packetWrapper.read(Type.INT);
               int y = packetWrapper.read(Type.UNSIGNED_BYTE);
               int z = packetWrapper.read(Type.INT);
               packetWrapper.write(Type.POSITION1_8, new Position(x, y, z));
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.PLAYER_BLOCK_PLACEMENT, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(packetWrapper -> {
               int x = packetWrapper.read(Type.INT);
               int y = packetWrapper.read(Type.UNSIGNED_BYTE);
               int z = packetWrapper.read(Type.INT);
               packetWrapper.write(Type.POSITION1_8, new Position(x, y, z));
               packetWrapper.passthrough(Type.BYTE);
               Item item = packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
               item = protocol.getItemRewriter().handleItemToServer(item);
               packetWrapper.write(Type.ITEM1_8, item);

               for (int i = 0; i < 3; i++) {
                  packetWrapper.passthrough(Type.BYTE);
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.ANIMATION, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(packetWrapper -> {
               int entityId = packetWrapper.read(Type.INT);
               int animation = packetWrapper.read(Type.BYTE);
               if (animation != 1) {
                  packetWrapper.cancel();
                  byte var4;
                  switch (animation) {
                     case 3:
                        var4 = 2;
                        break;
                     case 104:
                        var4 = 0;
                        break;
                     case 105:
                        var4 = 1;
                        break;
                     default:
                        return;
                  }

                  PacketWrapper entityAction = PacketWrapper.create(11, null, packetWrapper.user());
                  entityAction.write(Type.VAR_INT, entityId);
                  entityAction.write(Type.VAR_INT, Integer.valueOf(var4));
                  entityAction.write(Type.VAR_INT, 0);
                  PacketUtil.sendPacket(entityAction, Protocol1_7_6_10To1_8.class, true, true);
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.ENTITY_ACTION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT, Type.VAR_INT);
            this.handler(packetWrapper -> packetWrapper.write(Type.VAR_INT, packetWrapper.read(Type.BYTE) - 1));
            this.map(Type.INT, Type.VAR_INT);
            this.handler(packetWrapper -> {
               int action = packetWrapper.get(Type.VAR_INT, 1);
               if (action == 3 || action == 4) {
                  PlayerSessionStorage playerSession = packetWrapper.user().get(PlayerSessionStorage.class);
                  playerSession.sprinting = action == 3;
                  PacketWrapper abilitiesPacket = PacketWrapper.create(57, null, packetWrapper.user());
                  abilitiesPacket.write(Type.BYTE, playerSession.combineAbilities());
                  abilitiesPacket.write(Type.FLOAT, playerSession.sprinting ? playerSession.flySpeed * 2.0F : playerSession.flySpeed);
                  abilitiesPacket.write(Type.FLOAT, playerSession.walkSpeed);
                  PacketUtil.sendPacket(abilitiesPacket, Protocol1_7_6_10To1_8.class);
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.STEER_VEHICLE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.handler(packetWrapper -> {
               boolean jump = packetWrapper.read(Type.BOOLEAN);
               boolean unmount = packetWrapper.read(Type.BOOLEAN);
               short flags = 0;
               if (jump) {
                  flags++;
               }

               if (unmount) {
                  flags = (short)(flags + 2);
               }

               packetWrapper.write(Type.UNSIGNED_BYTE, flags);
               if (unmount) {
                  EntityTracker1_7_6_10 tracker = packetWrapper.user().get(EntityTracker1_7_6_10.class);
                  if (tracker.spectatingPlayerId != tracker.getPlayerId()) {
                     PacketWrapper sneakPacket = PacketWrapper.create(11, null, packetWrapper.user());
                     sneakPacket.write(Type.VAR_INT, tracker.getPlayerId());
                     sneakPacket.write(Type.VAR_INT, 0);
                     sneakPacket.write(Type.VAR_INT, 0);
                     PacketWrapper unsneakPacket = PacketWrapper.create(11, null, packetWrapper.user());
                     unsneakPacket.write(Type.VAR_INT, tracker.getPlayerId());
                     unsneakPacket.write(Type.VAR_INT, 1);
                     unsneakPacket.write(Type.VAR_INT, 0);
                     PacketUtil.sendToServer(sneakPacket, Protocol1_7_6_10To1_8.class);
                  }
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.UPDATE_SIGN, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(packetWrapper -> {
               int x = packetWrapper.read(Type.INT);
               int y = packetWrapper.read(Type.SHORT);
               int z = packetWrapper.read(Type.INT);
               packetWrapper.write(Type.POSITION1_8, new Position(x, y, z));

               for (int i = 0; i < 4; i++) {
                  String line = packetWrapper.read(Type.STRING);
                  packetWrapper.write(Type.COMPONENT, ComponentUtil.legacyToJson(line));
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.PLAYER_ABILITIES, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.BYTE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.handler(packetWrapper -> {
               PlayerSessionStorage playerSession = packetWrapper.user().get(PlayerSessionStorage.class);
               if (playerSession.allowFly) {
                  byte flags = packetWrapper.get(Type.BYTE, 0);
                  playerSession.flying = (flags & 2) == 2;
               }

               packetWrapper.set(Type.FLOAT, 0, playerSession.flySpeed);
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.TAB_COMPLETE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.create(Type.OPTIONAL_POSITION1_8, null);
            this.handler(packetWrapper -> {
               String msg = packetWrapper.get(Type.STRING, 0);
               if (msg.toLowerCase().startsWith("/stp ")) {
                  packetWrapper.cancel();
                  String[] args = msg.split(" ");
                  if (args.length <= 2) {
                     String prefix = args.length == 1 ? "" : args[1];
                     GameProfileStorage storage = packetWrapper.user().get(GameProfileStorage.class);
                     List<GameProfileStorage.GameProfile> profiles = storage.getAllWithPrefix(prefix, true);
                     PacketWrapper tabComplete = PacketWrapper.create(58, null, packetWrapper.user());
                     tabComplete.write(Type.VAR_INT, profiles.size());

                     for (GameProfileStorage.GameProfile profile : profiles) {
                        tabComplete.write(Type.STRING, profile.name);
                     }

                     PacketUtil.sendPacket(tabComplete, Protocol1_7_6_10To1_8.class);
                  }
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.CLIENT_SETTINGS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BOOLEAN);
            this.read(Type.BYTE);
            this.handler(packetWrapper -> {
               boolean cape = packetWrapper.read(Type.BOOLEAN);
               packetWrapper.write(Type.UNSIGNED_BYTE, (short)(cape ? 127 : 126));
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.read(Type.SHORT);
            this.handler(packetWrapper -> {
               String channel = packetWrapper.get(Type.STRING, 0);
               switch (channel) {
                  case "MC|TrSel":
                     packetWrapper.passthrough(Type.INT);
                     packetWrapper.read(Type.REMAINING_BYTES);
                     break;
                  case "MC|ItemName":
                     byte[] data = packetWrapper.read(Type.REMAINING_BYTES);
                     String name = new String(data, StandardCharsets.UTF_8);
                     packetWrapper.write(Type.STRING, name);
                     InventoryTracker windowTracker = packetWrapper.user().get(InventoryTracker.class);
                     PacketWrapper updateCost = PacketWrapper.create(49, null, packetWrapper.user());
                     updateCost.write(Type.UNSIGNED_BYTE, windowTracker.anvilId);
                     updateCost.write(Type.SHORT, (short)0);
                     updateCost.write(Type.SHORT, windowTracker.levelCost);
                     PacketUtil.sendPacket(updateCost, Protocol1_7_6_10To1_8.class, true, true);
                     break;
                  case "MC|BEdit":
                  case "MC|BSign":
                     Item book = packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
                     CompoundTag tag = book.tag();
                     if (tag != null && tag.contains("pages")) {
                        ListTag pages = tag.get("pages");

                        for (int i = 0; i < pages.size(); i++) {
                           StringTag page = pages.get(i);
                           String value = page.getValue();
                           value = ComponentUtil.legacyToJsonString(value);
                           page.setValue(value);
                        }
                     }

                     packetWrapper.write(Type.ITEM1_8, book);
                     break;
                  case "MC|Brand":
                     packetWrapper.write(Type.STRING, new String(packetWrapper.read(Type.REMAINING_BYTES), StandardCharsets.UTF_8));
               }
            });
         }
      });
   }
}
