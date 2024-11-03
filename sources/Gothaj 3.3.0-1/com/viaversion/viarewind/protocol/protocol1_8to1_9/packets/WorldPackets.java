package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.sound.Effect;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.sound.SoundRemapper;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_1;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import java.util.ArrayList;

public class WorldPackets {
   public static void register(final Protocol1_8To1_9 protocol) {
      protocol.registerClientbound(ClientboundPackets1_9.BLOCK_ENTITY_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.NAMED_COMPOUND_TAG);
            this.handler(packetWrapper -> {
               CompoundTag tag = packetWrapper.get(Type.NAMED_COMPOUND_TAG, 0);
               if (tag != null && tag.contains("SpawnData")) {
                  CompoundTag spawnData = tag.get("SpawnData");
                  if (spawnData.contains("id")) {
                     String entity = (String)spawnData.<Tag>get("id").getValue();
                     tag.remove("SpawnData");
                     tag.put("entityId", new StringTag(entity));
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.BLOCK_ACTION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.VAR_INT);
            this.handler(packetWrapper -> {
               int block = packetWrapper.get(Type.VAR_INT, 0);
               if (block >= 219 && block <= 234) {
                  int var2 = 130;
                  packetWrapper.set(Type.VAR_INT, 0, 130);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.BLOCK_CHANGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.VAR_INT);
            this.handler(packetWrapper -> {
               int combined = packetWrapper.get(Type.VAR_INT, 0);
               int replacedCombined = protocol.getItemRewriter().replace(combined);
               packetWrapper.set(Type.VAR_INT, 0, replacedCombined);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.MULTI_BLOCK_CHANGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
            this.handler(packetWrapper -> {
               for (BlockChangeRecord record : packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                  int replacedCombined = protocol.getItemRewriter().replace(record.getBlockId());
                  record.setBlockId(replacedCombined);
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.NAMED_SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(packetWrapper -> {
               String name = packetWrapper.get(Type.STRING, 0);
               name = SoundRemapper.getOldName(name);
               if (name == null) {
                  packetWrapper.cancel();
               } else {
                  packetWrapper.set(Type.STRING, 0, name);
               }
            });
            this.read(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Type.UNSIGNED_BYTE);
         }
      });
      protocol.registerClientbound(
         ClientboundPackets1_9.UNLOAD_CHUNK,
         ClientboundPackets1_8.CHUNK_DATA,
         new PacketHandlers() {
            @Override
            public void register() {
               this.handler(
                  packetWrapper -> {
                     Environment environment = packetWrapper.user().get(ClientWorld.class).getEnvironment();
                     int chunkX = packetWrapper.read(Type.INT);
                     int chunkZ = packetWrapper.read(Type.INT);
                     packetWrapper.write(
                        ChunkType1_8.forEnvironment(environment), new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList<>())
                     );
                  }
               );
            }
         }
      );
      protocol.registerClientbound(
         ClientboundPackets1_9.CHUNK_DATA,
         new PacketHandlers() {
            @Override
            public void register() {
               this.handler(
                  packetWrapper -> {
                     Environment environment = packetWrapper.user().get(ClientWorld.class).getEnvironment();
                     Chunk chunk = packetWrapper.read(ChunkType1_9_1.forEnvironment(environment));

                     for (ChunkSection section : chunk.getSections()) {
                        if (section != null) {
                           DataPalette palette = section.palette(PaletteType.BLOCKS);

                           for (int i = 0; i < palette.size(); i++) {
                              int block = palette.idByIndex(i);
                              int replacedBlock = protocol.getItemRewriter().replace(block);
                              palette.setIdByIndex(i, replacedBlock);
                           }
                        }
                     }

                     if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                        boolean skylight = environment == Environment.NORMAL;
                        ChunkSection[] sections = new ChunkSection[16];
                        ChunkSection sectionx = new ChunkSectionImpl(true);
                        sections[0] = sectionx;
                        sectionx.palette(PaletteType.BLOCKS).addId(0);
                        if (skylight) {
                           sectionx.getLight().setSkyLight(new byte[2048]);
                        }

                        chunk = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, 1, sections, chunk.getBiomeData(), chunk.getBlockEntities());
                     }

                     packetWrapper.write(ChunkType1_8.forEnvironment(environment), chunk);
                     UserConnection user = packetWrapper.user();
                     chunk.getBlockEntities()
                        .forEach(
                           nbt -> {
                              if (nbt.contains("x") && nbt.contains("y") && nbt.contains("z") && nbt.contains("id")) {
                                 Position position = new Position(
                                    (Integer)nbt.<Tag>get("x").getValue(), (Integer)nbt.<Tag>get("y").getValue(), (Integer)nbt.<Tag>get("z").getValue()
                                 );
                                 String id = (String)nbt.<Tag>get("id").getValue();
                                 short action;
                                 switch (id) {
                                    case "minecraft:mob_spawner":
                                       action = 1;
                                       break;
                                    case "minecraft:command_block":
                                       action = 2;
                                       break;
                                    case "minecraft:beacon":
                                       action = 3;
                                       break;
                                    case "minecraft:skull":
                                       action = 4;
                                       break;
                                    case "minecraft:flower_pot":
                                       action = 5;
                                       break;
                                    case "minecraft:banner":
                                       action = 6;
                                       break;
                                    default:
                                       return;
                                 }

                                 PacketWrapper updateTileEntity = PacketWrapper.create(9, null, user);
                                 updateTileEntity.write(Type.POSITION1_8, position);
                                 updateTileEntity.write(Type.UNSIGNED_BYTE, action);
                                 updateTileEntity.write(Type.NBT, nbt);
                                 PacketUtil.sendPacket(updateTileEntity, Protocol1_8To1_9.class, false, false);
                              }
                           }
                        );
                  }
               );
            }
         }
      );
      protocol.registerClientbound(ClientboundPackets1_9.EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.POSITION1_8);
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.handler(packetWrapper -> {
               int id = packetWrapper.get(Type.INT, 0);
               id = Effect.getOldId(id);
               if (id == -1) {
                  packetWrapper.cancel();
               } else {
                  packetWrapper.set(Type.INT, 0, id);
                  if (id == 2001) {
                     int replacedBlock = protocol.getItemRewriter().replace(packetWrapper.get(Type.INT, 1));
                     packetWrapper.set(Type.INT, 1, replacedBlock);
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.SPAWN_PARTICLE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.handler(packetWrapper -> {
               int type = packetWrapper.get(Type.INT, 0);
               if (type > 41 && !ViaRewind.getConfig().isReplaceParticles()) {
                  packetWrapper.cancel();
               } else {
                  if (type == 42) {
                     packetWrapper.set(Type.INT, 0, 24);
                  } else if (type == 43) {
                     packetWrapper.set(Type.INT, 0, 3);
                  } else if (type == 44) {
                     packetWrapper.set(Type.INT, 0, 34);
                  } else if (type == 45) {
                     packetWrapper.set(Type.INT, 0, 1);
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.MAP_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.read(Type.BOOLEAN);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_9.SOUND, ClientboundPackets1_8.NAMED_SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(packetWrapper -> {
               int soundId = packetWrapper.read(Type.VAR_INT);
               String sound = SoundRemapper.oldNameFromId(soundId);
               if (sound == null) {
                  packetWrapper.cancel();
               } else {
                  packetWrapper.write(Type.STRING, sound);
               }
            });
            this.handler(packetWrapper -> packetWrapper.read(Type.VAR_INT));
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Type.UNSIGNED_BYTE);
         }
      });
   }
}
