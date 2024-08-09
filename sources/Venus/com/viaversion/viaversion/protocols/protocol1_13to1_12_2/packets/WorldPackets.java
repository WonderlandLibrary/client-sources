/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_12_1to1_12.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.NamedSoundRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.PaintingProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class WorldPackets {
    private static final IntSet VALID_BIOMES;

    public static void register(Protocol1_13To1_12_2 protocol1_13To1_12_2) {
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.SPAWN_PAINTING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.UUID);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string;
                PaintingProvider paintingProvider = Via.getManager().getProviders().get(PaintingProvider.class);
                Optional<Integer> optional = paintingProvider.getIntByIdentifier(string = packetWrapper.read(Type.STRING));
                if (!(optional.isPresent() || Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug())) {
                    Via.getPlatform().getLogger().warning("Could not find painting motive: " + string + " falling back to default (0)");
                }
                packetWrapper.write(Type.VAR_INT, optional.orElse(0));
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.NBT);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                BlockStorage blockStorage;
                BlockStorage.ReplacementData replacementData;
                Position position = packetWrapper.get(Type.POSITION, 0);
                short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                CompoundTag compoundTag = packetWrapper.get(Type.NBT, 0);
                BlockEntityProvider blockEntityProvider = Via.getManager().getProviders().get(BlockEntityProvider.class);
                int n = blockEntityProvider.transform(packetWrapper.user(), position, compoundTag, false);
                if (n != -1 && (replacementData = (blockStorage = packetWrapper.user().get(BlockStorage.class)).get(position)) != null) {
                    replacementData.setReplacement(n);
                }
                if (s == 5) {
                    packetWrapper.cancel();
                }
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.BLOCK_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Position position = packetWrapper.get(Type.POSITION, 0);
                short s = packetWrapper.get(Type.UNSIGNED_BYTE, 0);
                short s2 = packetWrapper.get(Type.UNSIGNED_BYTE, 1);
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == 25) {
                    n = 73;
                } else if (n == 33) {
                    n = 99;
                } else if (n == 29) {
                    n = 92;
                } else if (n == 54) {
                    n = 142;
                } else if (n == 146) {
                    n = 305;
                } else if (n == 130) {
                    n = 249;
                } else if (n == 138) {
                    n = 257;
                } else if (n == 52) {
                    n = 140;
                } else if (n == 209) {
                    n = 472;
                } else if (n >= 219 && n <= 234) {
                    n = n - 219 + 483;
                }
                if (n == 73) {
                    PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_13.BLOCK_CHANGE);
                    packetWrapper2.write(Type.POSITION, position);
                    packetWrapper2.write(Type.VAR_INT, 249 + s * 24 * 2 + s2 * 2);
                    packetWrapper2.send(Protocol1_13To1_12_2.class);
                }
                packetWrapper.set(Type.VAR_INT, 0, n);
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.BLOCK_CHANGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION);
                this.map(Type.VAR_INT);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Position position = packetWrapper.get(Type.POSITION, 0);
                int n = WorldPackets.toNewId(packetWrapper.get(Type.VAR_INT, 0));
                UserConnection userConnection = packetWrapper.user();
                if (Via.getConfig().isServersideBlockConnections()) {
                    n = ConnectionData.connect(userConnection, position, n);
                    ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), n);
                }
                packetWrapper.set(Type.VAR_INT, 0, WorldPackets.access$000(packetWrapper.user(), position, n));
                if (Via.getConfig().isServersideBlockConnections()) {
                    packetWrapper.send(Protocol1_13To1_12_2.class);
                    packetWrapper.cancel();
                    ConnectionData.update(userConnection, position);
                }
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.MULTI_BLOCK_CHANGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Position position;
                int n;
                BlockChangeRecord[] blockChangeRecordArray;
                int n2 = packetWrapper.get(Type.INT, 0);
                int n3 = packetWrapper.get(Type.INT, 1);
                UserConnection userConnection = packetWrapper.user();
                for (BlockChangeRecord blockChangeRecord : blockChangeRecordArray = packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                    n = WorldPackets.toNewId(blockChangeRecord.getBlockId());
                    position = new Position(blockChangeRecord.getSectionX() + (n2 << 4), blockChangeRecord.getY(), blockChangeRecord.getSectionZ() + (n3 << 4));
                    blockChangeRecord.setBlockId(WorldPackets.access$000(packetWrapper.user(), position, n));
                    if (!Via.getConfig().isServersideBlockConnections()) continue;
                    ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), n);
                }
                if (Via.getConfig().isServersideBlockConnections()) {
                    for (BlockChangeRecord blockChangeRecord : blockChangeRecordArray) {
                        n = blockChangeRecord.getBlockId();
                        position = new Position(blockChangeRecord.getSectionX() + n2 * 16, blockChangeRecord.getY(), blockChangeRecord.getSectionZ() + n3 * 16);
                        ConnectionHandler connectionHandler = ConnectionData.getConnectionHandler(n);
                        if (connectionHandler == null) continue;
                        n = connectionHandler.connect(userConnection, position, n);
                        blockChangeRecord.setBlockId(n);
                        ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), n);
                    }
                    packetWrapper.send(Protocol1_13To1_12_2.class);
                    packetWrapper.cancel();
                    for (BlockChangeRecord blockChangeRecord : blockChangeRecordArray) {
                        Position position2 = new Position(blockChangeRecord.getSectionX() + n2 * 16, blockChangeRecord.getY(), blockChangeRecord.getSectionZ() + n3 * 16);
                        ConnectionData.update(userConnection, position2);
                    }
                }
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.EXPLOSION, new PacketHandlers(){

            @Override
            public void register() {
                if (!Via.getConfig().isServersideBlockConnections()) {
                    return;
                }
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                UserConnection userConnection = packetWrapper.user();
                int n2 = (int)Math.floor(packetWrapper.get(Type.FLOAT, 0).floatValue());
                int n3 = (int)Math.floor(packetWrapper.get(Type.FLOAT, 1).floatValue());
                int n4 = (int)Math.floor(packetWrapper.get(Type.FLOAT, 2).floatValue());
                int n5 = packetWrapper.get(Type.INT, 0);
                Position[] positionArray = new Position[n5];
                for (n = 0; n < n5; ++n) {
                    Position position;
                    positionArray[n] = position = new Position(n2 + packetWrapper.passthrough(Type.BYTE), (short)(n3 + packetWrapper.passthrough(Type.BYTE)), n4 + packetWrapper.passthrough(Type.BYTE));
                    ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), 0);
                }
                packetWrapper.send(Protocol1_13To1_12_2.class);
                packetWrapper.cancel();
                for (n = 0; n < n5; ++n) {
                    ConnectionData.update(userConnection, positionArray[n]);
                }
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.UNLOAD_CHUNK, new PacketHandlers(){

            @Override
            public void register() {
                if (Via.getConfig().isServersideBlockConnections()) {
                    this.handler(7::lambda$register$0);
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.INT);
                int n2 = packetWrapper.passthrough(Type.INT);
                ConnectionData.blockConnectionProvider.unloadChunk(packetWrapper.user(), n, n2);
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.NAMED_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0).replace("minecraft:", "");
                String string2 = NamedSoundRewriter.getNewId(string);
                packetWrapper.set(Type.STRING, 0, string2);
            }
        });
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.CHUNK_DATA, WorldPackets::lambda$register$0);
        protocol1_13To1_12_2.registerClientbound(ClientboundPackets1_12_1.SPAWN_PARTICLE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(9::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                int n2 = 0;
                if (n == 37 || n == 38 || n == 46) {
                    n2 = 1;
                } else if (n == 36) {
                    n2 = 2;
                }
                Integer[] integerArray = new Integer[n2];
                for (int i = 0; i < integerArray.length; ++i) {
                    integerArray[i] = packetWrapper.read(Type.VAR_INT);
                }
                Particle particle = ParticleRewriter.rewriteParticle(n, integerArray);
                if (particle == null || particle.getId() == -1) {
                    packetWrapper.cancel();
                    return;
                }
                if (particle.getId() == 11) {
                    int n3 = packetWrapper.get(Type.INT, 1);
                    float f = packetWrapper.get(Type.FLOAT, 6).floatValue();
                    if (n3 == 0) {
                        packetWrapper.set(Type.INT, 1, 1);
                        packetWrapper.set(Type.FLOAT, 6, Float.valueOf(0.0f));
                        List<Particle.ParticleData> list = particle.getArguments();
                        for (int i = 0; i < 3; ++i) {
                            float f2 = packetWrapper.get(Type.FLOAT, i + 3).floatValue() * f;
                            if (f2 == 0.0f && i == 0) {
                                f2 = 1.0f;
                            }
                            list.get(i).setValue(Float.valueOf(f2));
                            packetWrapper.set(Type.FLOAT, i + 3, Float.valueOf(0.0f));
                        }
                    }
                }
                packetWrapper.set(Type.INT, 0, particle.getId());
                for (Particle.ParticleData particleData : particle.getArguments()) {
                    packetWrapper.write(particleData.getType(), particleData.getValue());
                }
            }
        });
        protocol1_13To1_12_2.registerServerbound(ServerboundPackets1_13.PLAYER_BLOCK_PLACEMENT, WorldPackets::lambda$register$1);
        protocol1_13To1_12_2.registerServerbound(ServerboundPackets1_13.PLAYER_DIGGING, WorldPackets::lambda$register$2);
    }

    public static int toNewId(int n) {
        int n2;
        if (n < 0) {
            n = 0;
        }
        if ((n2 = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(n)) != -1) {
            return n2;
        }
        n2 = Protocol1_13To1_12_2.MAPPINGS.getBlockMappings().getNewId(n & 0xFFFFFFF0);
        if (n2 != -1) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Missing block " + n);
            }
            return n2;
        }
        if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().warning("Missing block completely " + n);
        }
        return 0;
    }

    private static int checkStorage(UserConnection userConnection, Position position, int n) {
        BlockStorage blockStorage = userConnection.get(BlockStorage.class);
        if (blockStorage.contains(position)) {
            BlockStorage.ReplacementData replacementData = blockStorage.get(position);
            if (replacementData.getOriginal() == n) {
                if (replacementData.getReplacement() != -1) {
                    return replacementData.getReplacement();
                }
            } else {
                blockStorage.remove(position);
                if (blockStorage.isWelcome(n)) {
                    blockStorage.store(position, n);
                }
            }
        } else if (blockStorage.isWelcome(n)) {
            blockStorage.store(position, n);
        }
        return n;
    }

    private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        Position position = packetWrapper.passthrough(Type.POSITION);
        packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        if (n == 0 && Via.getConfig().isServersideBlockConnections() && ConnectionData.needStoreBlocks()) {
            ConnectionData.markModified(packetWrapper.user(), position);
        }
    }

    private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
        Position position = packetWrapper.passthrough(Type.POSITION);
        packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.FLOAT);
        packetWrapper.passthrough(Type.FLOAT);
        packetWrapper.passthrough(Type.FLOAT);
        if (Via.getConfig().isServersideBlockConnections() && ConnectionData.needStoreBlocks()) {
            ConnectionData.markModified(packetWrapper.user(), position);
        }
    }

    private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2;
        int n3;
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        BlockStorage blockStorage = packetWrapper.user().get(BlockStorage.class);
        Chunk1_9_3_4Type chunk1_9_3_4Type = new Chunk1_9_3_4Type(clientWorld);
        Chunk1_13Type chunk1_13Type = new Chunk1_13Type(clientWorld);
        Chunk chunk = packetWrapper.read(chunk1_9_3_4Type);
        packetWrapper.write(chunk1_13Type, chunk);
        for (n3 = 0; n3 < chunk.getSections().length; ++n3) {
            int n4;
            DataPalette dataPalette;
            block21: {
                block20: {
                    ChunkSection chunkSection = chunk.getSections()[n3];
                    if (chunkSection == null) continue;
                    dataPalette = chunkSection.palette(PaletteType.BLOCKS);
                    for (n2 = 0; n2 < dataPalette.size(); ++n2) {
                        n4 = dataPalette.idByIndex(n2);
                        int n5 = WorldPackets.toNewId(n4);
                        dataPalette.setIdByIndex(n2, n5);
                    }
                    if (!chunk.isFullChunk()) break block20;
                    n2 = 0;
                    for (n4 = 0; n4 < dataPalette.size(); ++n4) {
                        if (!blockStorage.isWelcome(dataPalette.idByIndex(n4))) continue;
                        n2 = 1;
                        break;
                    }
                    if (n2 == 0) break block21;
                }
                for (n2 = 0; n2 < 4096; ++n2) {
                    n4 = dataPalette.idAt(n2);
                    Position position = new Position(ChunkSection.xFromIndex(n2) + (chunk.getX() << 4), ChunkSection.yFromIndex(n2) + (n3 << 4), ChunkSection.zFromIndex(n2) + (chunk.getZ() << 4));
                    if (blockStorage.isWelcome(n4)) {
                        blockStorage.store(position, n4);
                        continue;
                    }
                    if (chunk.isFullChunk()) continue;
                    blockStorage.remove(position);
                }
            }
            if (!Via.getConfig().isServersideBlockConnections() || !ConnectionData.needStoreBlocks()) continue;
            if (!chunk.isFullChunk()) {
                ConnectionData.blockConnectionProvider.unloadChunkSection(packetWrapper.user(), chunk.getX(), n3, chunk.getZ());
            }
            n2 = 0;
            for (n4 = 0; n4 < dataPalette.size(); ++n4) {
                if (!ConnectionData.isWelcome(dataPalette.idByIndex(n4))) continue;
                n2 = 1;
                break;
            }
            if (n2 == 0) continue;
            for (n4 = 0; n4 < 4096; ++n4) {
                int n6 = dataPalette.idAt(n4);
                if (!ConnectionData.isWelcome(n6)) continue;
                n = ChunkSection.xFromIndex(n4) + (chunk.getX() << 4);
                int n7 = ChunkSection.yFromIndex(n4) + (n3 << 4);
                int n8 = ChunkSection.zFromIndex(n4) + (chunk.getZ() << 4);
                ConnectionData.blockConnectionProvider.storeBlock(packetWrapper.user(), n, n7, n8, n6);
            }
        }
        if (chunk.isBiomeData()) {
            n3 = Integer.MIN_VALUE;
            for (int i = 0; i < 256; ++i) {
                int n9 = chunk.getBiomeData()[i];
                if (VALID_BIOMES.contains(n9)) continue;
                if (n9 != 255 && n3 != n9) {
                    if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                        Via.getPlatform().getLogger().warning("Received invalid biome id " + n9);
                    }
                    n3 = n9;
                }
                chunk.getBiomeData()[i] = 1;
            }
        }
        BlockEntityProvider blockEntityProvider = Via.getManager().getProviders().get(BlockEntityProvider.class);
        Iterator<CompoundTag> iterator2 = chunk.getBlockEntities().iterator();
        while (iterator2.hasNext()) {
            String string;
            Object t;
            CompoundTag compoundTag = iterator2.next();
            n2 = blockEntityProvider.transform(packetWrapper.user(), null, compoundTag, true);
            if (n2 != -1) {
                int n10;
                int n11 = ((NumberTag)compoundTag.get("x")).asInt();
                Position position = new Position(n11, (short)(n10 = ((NumberTag)compoundTag.get("y")).asInt()), n = ((NumberTag)compoundTag.get("z")).asInt());
                BlockStorage.ReplacementData replacementData = blockStorage.get(position);
                if (replacementData != null) {
                    replacementData.setReplacement(n2);
                }
                chunk.getSections()[n10 >> 4].palette(PaletteType.BLOCKS).setIdAt(n11 & 0xF, n10 & 0xF, n & 0xF, n2);
            }
            if (!((t = compoundTag.get("id")) instanceof StringTag) || !(string = ((StringTag)t).getValue()).equals("minecraft:noteblock") && !string.equals("minecraft:flower_pot")) continue;
            iterator2.remove();
        }
        if (Via.getConfig().isServersideBlockConnections()) {
            ConnectionData.connectBlocks(packetWrapper.user(), chunk);
            packetWrapper.send(Protocol1_13To1_12_2.class);
            packetWrapper.cancel();
            ConnectionData.NeighbourUpdater neighbourUpdater = new ConnectionData.NeighbourUpdater(packetWrapper.user());
            for (n2 = 0; n2 < chunk.getSections().length; ++n2) {
                ChunkSection chunkSection = chunk.getSections()[n2];
                if (chunkSection == null) continue;
                neighbourUpdater.updateChunkSectionNeighbours(chunk.getX(), chunk.getZ(), n2);
            }
        }
    }

    static int access$000(UserConnection userConnection, Position position, int n) {
        return WorldPackets.checkStorage(userConnection, position, n);
    }

    static {
        int n;
        VALID_BIOMES = new IntOpenHashSet(70, 0.99f);
        for (n = 0; n < 50; ++n) {
            VALID_BIOMES.add(n);
        }
        VALID_BIOMES.add(127);
        for (n = 129; n <= 134; ++n) {
            VALID_BIOMES.add(n);
        }
        VALID_BIOMES.add(140);
        VALID_BIOMES.add(149);
        VALID_BIOMES.add(151);
        for (n = 155; n <= 158; ++n) {
            VALID_BIOMES.add(n);
        }
        for (n = 160; n <= 167; ++n) {
            VALID_BIOMES.add(n);
        }
    }
}

