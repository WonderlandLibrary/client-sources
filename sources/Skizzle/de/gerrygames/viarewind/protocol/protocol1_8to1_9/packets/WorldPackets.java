/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.packets;

import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.items.ReplacementRegistry1_8to1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.sound.Effect;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.sound.SoundRemapper;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.types.Chunk1_8Type;
import de.gerrygames.viarewind.storage.BlockState;
import de.gerrygames.viarewind.utils.PacketUtil;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.BlockChangeRecord;
import us.myles.ViaVersion.api.minecraft.Environment;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk1_8;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class WorldPackets {
    public static void register(Protocol protocol) {
        protocol.registerOutgoing(State.PLAY, 8, 37);
        protocol.registerOutgoing(State.PLAY, 9, 53, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        CompoundTag tag = packetWrapper.get(Type.NBT, 0);
                        if (tag != null && tag.contains("SpawnData")) {
                            String entity = (String)((Tag)((CompoundTag)tag.get("SpawnData")).get("id")).getValue();
                            tag.remove("SpawnData");
                            tag.put(new StringTag("entityId", entity));
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 10, 36, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int block = packetWrapper.get(Type.VAR_INT, 0);
                        if (block >= 219 && block <= 234) {
                            block = 130;
                            packetWrapper.set(Type.VAR_INT, 0, 130);
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 11, 35, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int combined = packetWrapper.get(Type.VAR_INT, 0);
                        BlockState state = BlockState.rawToState(combined);
                        state = ReplacementRegistry1_8to1_9.replace(state);
                        packetWrapper.set(Type.VAR_INT, 0, BlockState.stateToRaw(state));
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 13, 65);
        protocol.registerOutgoing(State.PLAY, 16, 34, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        for (BlockChangeRecord record : packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                            BlockState state = BlockState.rawToState(record.getBlockId());
                            state = ReplacementRegistry1_8to1_9.replace(state);
                            record.setBlockId(BlockState.stateToRaw(state));
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 25, 41, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        String name = packetWrapper.get(Type.STRING, 0);
                        if ((name = SoundRemapper.getOldName(name)) == null) {
                            packetWrapper.cancel();
                        } else {
                            packetWrapper.set(Type.STRING, 0, name);
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.VAR_INT);
                    }
                });
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE);
            }
        });
        protocol.registerOutgoing(State.PLAY, 28, 39, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int count = packetWrapper.read(Type.INT);
                        packetWrapper.write(Type.INT, count);
                        for (int i = 0; i < count; ++i) {
                            packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                            packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                            packetWrapper.passthrough(Type.UNSIGNED_BYTE);
                        }
                    }
                });
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
            }
        });
        protocol.registerOutgoing(State.PLAY, 29, 33, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int chunkX = packetWrapper.read(Type.INT);
                        int chunkZ = packetWrapper.read(Type.INT);
                        ClientWorld world = packetWrapper.user().get(ClientWorld.class);
                        packetWrapper.write(new Chunk1_8Type(world), new Chunk1_8(chunkX, chunkZ));
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 32, 33, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        ClientWorld world = packetWrapper.user().get(ClientWorld.class);
                        Chunk chunk = packetWrapper.read(new Chunk1_9_1_2Type(world));
                        for (ChunkSection section : chunk.getSections()) {
                            if (section == null) continue;
                            for (int i = 0; i < section.getPaletteSize(); ++i) {
                                int block = section.getPaletteEntry(i);
                                BlockState state = BlockState.rawToState(block);
                                state = ReplacementRegistry1_8to1_9.replace(state);
                                section.setPaletteEntry(i, BlockState.stateToRaw(state));
                            }
                        }
                        if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                            ChunkSection section;
                            boolean skylight = world.getEnvironment() == Environment.NORMAL;
                            ChunkSection[] sections = new ChunkSection[16];
                            sections[0] = section = new ChunkSection();
                            section.addPaletteEntry(0);
                            if (skylight) {
                                section.setSkyLight(new byte[2048]);
                            }
                            chunk = new Chunk1_8(chunk.getX(), chunk.getZ(), true, 1, sections, chunk.getBiomeData(), chunk.getBlockEntities());
                        }
                        packetWrapper.write(new Chunk1_8Type(world), chunk);
                        UserConnection user = packetWrapper.user();
                        chunk.getBlockEntities().forEach(nbt -> {
                            short action;
                            String id;
                            if (!(nbt.contains("x") && nbt.contains("y") && nbt.contains("z") && nbt.contains("id"))) {
                                return;
                            }
                            Position position = new Position((Integer)((Tag)nbt.get("x")).getValue(), (short)((Integer)((Tag)nbt.get("y")).getValue()).intValue(), (Integer)((Tag)nbt.get("z")).getValue());
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
                            PacketWrapper updateTileEntity = new PacketWrapper(9, null, user);
                            updateTileEntity.write(Type.POSITION, position);
                            updateTileEntity.write(Type.UNSIGNED_BYTE, action);
                            updateTileEntity.write(Type.NBT, nbt);
                            PacketUtil.sendPacket(updateTileEntity, Protocol1_8TO1_9.class, false, false);
                        });
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 33, 40, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int id = packetWrapper.get(Type.INT, 0);
                        if ((id = Effect.getOldId(id)) == -1) {
                            packetWrapper.cancel();
                            return;
                        }
                        packetWrapper.set(Type.INT, 0, id);
                        if (id == 2001) {
                            BlockState state = BlockState.rawToState(packetWrapper.get(Type.INT, 1));
                            state = ReplacementRegistry1_8to1_9.replace(state);
                            packetWrapper.set(Type.INT, 1, BlockState.stateToRaw(state));
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 34, 42, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int type = packetWrapper.get(Type.INT, 0);
                        if (type > 41 && !ViaRewind.getConfig().isReplaceParticles()) {
                            packetWrapper.cancel();
                            return;
                        }
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
        protocol.registerOutgoing(State.PLAY, 36, 52, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.BOOLEAN);
                    }
                });
            }
        });
        protocol.registerOutgoing(State.PLAY, 44, 66);
        protocol.registerOutgoing(State.PLAY, 53, 68);
        protocol.registerOutgoing(State.PLAY, 68, 3);
        protocol.registerOutgoing(State.PLAY, 70, 51);
        protocol.registerOutgoing(State.PLAY, 71, 41, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int soundId = packetWrapper.read(Type.VAR_INT);
                        String sound = SoundRemapper.oldNameFromId(soundId);
                        if (sound == null) {
                            packetWrapper.cancel();
                        } else {
                            packetWrapper.write(Type.STRING, sound);
                        }
                    }
                });
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.read(Type.VAR_INT);
                    }
                });
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.UNSIGNED_BYTE);
            }
        });
    }
}

