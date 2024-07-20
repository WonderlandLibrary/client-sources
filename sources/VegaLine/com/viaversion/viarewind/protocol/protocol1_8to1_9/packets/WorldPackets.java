/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.items.ReplacementRegistry1_8to1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.sound.Effect;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.sound.SoundRemapper;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_1to1_9.types.Chunk1_9_1_2Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_8Type;
import java.util.ArrayList;

public class WorldPackets {
    public static void register(Protocol<ClientboundPackets1_9, ClientboundPackets1_8, ServerboundPackets1_9, ServerboundPackets1_8> protocol) {
        protocol.registerClientbound(ClientboundPackets1_9.BLOCK_ENTITY_DATA, (ClientboundPackets1_8)((Object)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(packetWrapper -> {
                    CompoundTag spawnData;
                    CompoundTag tag = packetWrapper.get(Type.NBT, 0);
                    if (tag != null && tag.contains("SpawnData") && (spawnData = (CompoundTag)tag.get("SpawnData")).contains("id")) {
                        String entity = (String)((Tag)spawnData.get("id")).getValue();
                        tag.remove("SpawnData");
                        tag.put("entityId", new StringTag(entity));
                    }
                });
            }
        }));
        protocol.registerClientbound(ClientboundPackets1_9.BLOCK_ACTION, (ClientboundPackets1_8)((Object)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int block = packetWrapper.get(Type.VAR_INT, 0);
                    if (block >= 219 && block <= 234) {
                        block = 130;
                        packetWrapper.set(Type.VAR_INT, 0, 130);
                    }
                });
            }
        }));
        protocol.registerClientbound(ClientboundPackets1_9.BLOCK_CHANGE, (ClientboundPackets1_8)((Object)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(packetWrapper -> {
                    int combined = packetWrapper.get(Type.VAR_INT, 0);
                    int replacedCombined = ReplacementRegistry1_8to1_9.replace(combined);
                    packetWrapper.set(Type.VAR_INT, 0, replacedCombined);
                });
            }
        }));
        protocol.registerClientbound(ClientboundPackets1_9.MULTI_BLOCK_CHANGE, (ClientboundPackets1_8)((Object)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(packetWrapper -> {
                    for (BlockChangeRecord record : packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                        int replacedCombined = ReplacementRegistry1_8to1_9.replace(record.getBlockId());
                        record.setBlockId(replacedCombined);
                    }
                });
            }
        }));
        protocol.registerClientbound(ClientboundPackets1_9.NAMED_SOUND, (ClientboundPackets1_8)((Object)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(packetWrapper -> {
                    String name = packetWrapper.get(Type.STRING, 0);
                    if ((name = SoundRemapper.getOldName(name)) == null) {
                        packetWrapper.cancel();
                    } else {
                        packetWrapper.set(Type.STRING, 0, name);
                    }
                });
                this.map((Type)Type.VAR_INT, Type.NOTHING);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE);
            }
        }));
        protocol.registerClientbound(ClientboundPackets1_9.EXPLOSION, (ClientboundPackets1_8)((Object)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(packetWrapper -> {
                    int count = packetWrapper.read(Type.INT);
                    packetWrapper.write(Type.INT, count);
                    for (int i = 0; i < count; ++i) {
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                    }
                });
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
            }
        }));
        protocol.registerClientbound(ClientboundPackets1_9.UNLOAD_CHUNK, ClientboundPackets1_8.CHUNK_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(packetWrapper -> {
                    int chunkX = packetWrapper.read(Type.INT);
                    int chunkZ = packetWrapper.read(Type.INT);
                    ClientWorld world = packetWrapper.user().get(ClientWorld.class);
                    packetWrapper.write(new Chunk1_8Type(world), new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList<CompoundTag>()));
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9.CHUNK_DATA, (ClientboundPackets1_8)((Object)new PacketHandlers(){

            @Override
            public void register() {
                this.handler(packetWrapper -> {
                    ClientWorld world = packetWrapper.user().get(ClientWorld.class);
                    Chunk chunk = packetWrapper.read(new Chunk1_9_1_2Type(world));
                    for (ChunkSection section : chunk.getSections()) {
                        if (section == null) continue;
                        DataPalette palette = section.palette(PaletteType.BLOCKS);
                        for (int i = 0; i < palette.size(); ++i) {
                            int block = palette.idByIndex(i);
                            int replacedBlock = ReplacementRegistry1_8to1_9.replace(block);
                            palette.setIdByIndex(i, replacedBlock);
                        }
                    }
                    if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                        boolean skylight = world.getEnvironment() == Environment.NORMAL;
                        ChunkSection[] sections = new ChunkSection[16];
                        ChunkSectionImpl section = new ChunkSectionImpl(true);
                        sections[0] = section;
                        section.palette(PaletteType.BLOCKS).addId(0);
                        if (skylight) {
                            section.getLight().setSkyLight(new byte[2048]);
                        }
                        chunk = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, 1, sections, chunk.getBiomeData(), chunk.getBlockEntities());
                    }
                    packetWrapper.write(new Chunk1_8Type(world), chunk);
                    UserConnection user = packetWrapper.user();
                    chunk.getBlockEntities().forEach(nbt -> {
                        short action;
                        String id;
                        if (!(nbt.contains("x") && nbt.contains("y") && nbt.contains("z") && nbt.contains("id"))) {
                            return;
                        }
                        Position position = new Position((int)((Integer)((Tag)nbt.get("x")).getValue()), (Integer)((Tag)nbt.get("y")).getValue(), (int)((Integer)((Tag)nbt.get("z")).getValue()));
                        switch (id = (String)((Tag)nbt.get("id")).getValue()) {
                            case "minecraft:mob_spawner": {
                                action = 1;
                                break;
                            }
                            case "minecraft:command_block": {
                                action = 2;
                                break;
                            }
                            case "minecraft:beacon": {
                                action = 3;
                                break;
                            }
                            case "minecraft:skull": {
                                action = 4;
                                break;
                            }
                            case "minecraft:flower_pot": {
                                action = 5;
                                break;
                            }
                            case "minecraft:banner": {
                                action = 6;
                                break;
                            }
                            default: {
                                return;
                            }
                        }
                        PacketWrapper updateTileEntity = PacketWrapper.create(9, null, user);
                        updateTileEntity.write(Type.POSITION, position);
                        updateTileEntity.write(Type.UNSIGNED_BYTE, action);
                        updateTileEntity.write(Type.NBT, nbt);
                        PacketUtil.sendPacket(updateTileEntity, Protocol1_8To1_9.class, false, false);
                    });
                });
            }
        }));
        protocol.registerClientbound(ClientboundPackets1_9.EFFECT, (ClientboundPackets1_8)((Object)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(packetWrapper -> {
                    int id = packetWrapper.get(Type.INT, 0);
                    if ((id = Effect.getOldId(id)) == -1) {
                        packetWrapper.cancel();
                        return;
                    }
                    packetWrapper.set(Type.INT, 0, id);
                    if (id == 2001) {
                        int replacedBlock = ReplacementRegistry1_8to1_9.replace(packetWrapper.get(Type.INT, 1));
                        packetWrapper.set(Type.INT, 1, replacedBlock);
                    }
                });
            }
        }));
        protocol.registerClientbound(ClientboundPackets1_9.SPAWN_PARTICLE, (ClientboundPackets1_8)((Object)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(packetWrapper -> {
                    int type2 = packetWrapper.get(Type.INT, 0);
                    if (type2 > 41 && !ViaRewind.getConfig().isReplaceParticles()) {
                        packetWrapper.cancel();
                        return;
                    }
                    if (type2 == 42) {
                        packetWrapper.set(Type.INT, 0, 24);
                    } else if (type2 == 43) {
                        packetWrapper.set(Type.INT, 0, 3);
                    } else if (type2 == 44) {
                        packetWrapper.set(Type.INT, 0, 34);
                    } else if (type2 == 45) {
                        packetWrapper.set(Type.INT, 0, 1);
                    }
                });
            }
        }));
        protocol.registerClientbound(ClientboundPackets1_9.MAP_DATA, (ClientboundPackets1_8)((Object)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map((Type)Type.BOOLEAN, Type.NOTHING);
            }
        }));
        protocol.registerClientbound(ClientboundPackets1_9.SOUND, ClientboundPackets1_8.NAMED_SOUND, new PacketHandlers(){

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

