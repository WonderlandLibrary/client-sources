/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.BasicFenceConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.BlockData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ChestConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ChorusPlantConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.DoorConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.FireConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.FlowerConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.GlassConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.MelonConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.NetherFenceConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.PumpkinConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.RedstoneConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.SnowyGrassConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.StairConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.TripwireConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.VineConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WallConnectionHandler;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.PacketBlockConnectionProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConnectionData {
    private static final BlockChangeRecord1_8[] EMPTY_RECORDS = new BlockChangeRecord1_8[0];
    public static BlockConnectionProvider blockConnectionProvider;
    static Int2ObjectMap<String> idToKey;
    static Object2IntMap<String> keyToId;
    static Int2ObjectMap<ConnectionHandler> connectionHandlerMap;
    static Int2ObjectMap<BlockData> blockConnectionData;
    static IntSet occludingStates;

    public static void update(UserConnection user, Position position) {
        BlockFace[] blockFaceArray = BlockFace.values();
        int n = blockFaceArray.length;
        int n2 = 0;
        while (n2 < n) {
            BlockFace face = blockFaceArray[n2];
            Position pos = position.getRelative(face);
            int blockState = blockConnectionProvider.getBlockData(user, pos.x(), pos.y(), pos.z());
            ConnectionHandler handler = (ConnectionHandler)connectionHandlerMap.get(blockState);
            if (handler != null) {
                int newBlockState = handler.connect(user, pos, blockState);
                PacketWrapper blockUpdatePacket = PacketWrapper.create(ClientboundPackets1_13.BLOCK_CHANGE, null, user);
                blockUpdatePacket.write(Type.POSITION, pos);
                blockUpdatePacket.write(Type.VAR_INT, newBlockState);
                try {
                    blockUpdatePacket.send(Protocol1_13To1_12_2.class);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            ++n2;
        }
    }

    /*
     * Exception decompiling
     */
    public static void updateChunkSectionNeighbours(UserConnection user, int chunkX, int chunkZ, int chunkSectionY) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[UNCONDITIONALDOLOOP]], but top level block is 3[UNCONDITIONALDOLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at the.bytecode.club.bytecodeviewer.decompilers.impl.CFRDecompiler.decompileToZip(CFRDecompiler.java:306)
         *     at the.bytecode.club.bytecodeviewer.resources.ResourceDecompiling.lambda$null$5(ResourceDecompiling.java:159)
         *     at java.lang.Thread.run(Unknown Source)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static void updateBlock(UserConnection user, Position pos, List<BlockChangeRecord1_8> records) {
        int blockState = blockConnectionProvider.getBlockData(user, pos.x(), pos.y(), pos.z());
        ConnectionHandler handler = ConnectionData.getConnectionHandler(blockState);
        if (handler == null) {
            return;
        }
        int newBlockState = handler.connect(user, pos, blockState);
        records.add(new BlockChangeRecord1_8(pos.x() & 0xF, pos.y(), pos.z() & 0xF, newBlockState));
    }

    public static void updateBlockStorage(UserConnection userConnection, int x, int y, int z, int blockState) {
        if (!ConnectionData.needStoreBlocks()) {
            return;
        }
        if (ConnectionData.isWelcome(blockState)) {
            blockConnectionProvider.storeBlock(userConnection, x, y, z, blockState);
            return;
        }
        blockConnectionProvider.removeBlock(userConnection, x, y, z);
    }

    public static void clearBlockStorage(UserConnection connection) {
        if (!ConnectionData.needStoreBlocks()) {
            return;
        }
        blockConnectionProvider.clearStorage(connection);
    }

    public static boolean needStoreBlocks() {
        return blockConnectionProvider.storesBlocks();
    }

    /*
     * Unable to fully structure code
     */
    public static void connectBlocks(UserConnection user, Chunk chunk) {
        xOff = chunk.getX() << 4;
        zOff = chunk.getZ() << 4;
        i = 0;
        block0: while (true) {
            if (i >= chunk.getSections().length) return;
            section = chunk.getSections()[i];
            if (section == null) ** GOTO lbl-1000
            willConnect = false;
            for (p = 0; p < section.getPaletteSize(); ++p) {
                id = section.getPaletteEntry(p);
                if (!ConnectionData.connects(id)) continue;
                willConnect = true;
                break;
            }
            if (!willConnect) ** GOTO lbl-1000
            yOff = i << 4;
            y = 0;
            while (true) {
                if (y < 16) {
                } else lbl-1000:
                // 3 sources

                {
                    ++i;
                    continue block0;
                }
                for (z = 0; z < 16; ++z) {
                    for (x = 0; x < 16; ++x) {
                        block = section.getFlatBlock(x, y, z);
                        handler = ConnectionData.getConnectionHandler(block);
                        if (handler == null) continue;
                        block = handler.connect(user, new Position((int)(xOff + (long)x), (short)(yOff + (long)y), (int)(zOff + (long)z)), block);
                        section.setFlatBlock(x, y, z, block);
                    }
                }
                ++y;
            }
            break;
        }
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public static void init() {
        if (!Via.getConfig().isServersideBlockConnections()) {
            return;
        }
        Via.getPlatform().getLogger().info("Loading block connection mappings ...");
        mapping1_13 = MappingDataLoader.loadData("mapping-1.13.json", true);
        blocks1_13 = mapping1_13.getAsJsonObject("blockstates");
        for (Object blockState : blocks1_13.entrySet()) {
            id = Integer.parseInt(blockState.getKey());
            key = blockState.getValue().getAsString();
            ConnectionData.idToKey.put(id, key);
            ConnectionData.keyToId.put(key, id);
        }
        ConnectionData.connectionHandlerMap = new Int2ObjectOpenHashMap<ConnectionHandler>(3650, 0.99f);
        if (Via.getConfig().isReduceBlockStorageMemory()) ** GOTO lbl-1000
        ConnectionData.blockConnectionData = new Int2ObjectOpenHashMap<BlockData>(1146, 0.99f);
        mappingBlockConnections = MappingDataLoader.loadData("blockConnections.json");
        blockState = mappingBlockConnections.entrySet().iterator();
        while (true) {
            if (blockState.hasNext()) {
                entry = (Map.Entry)blockState.next();
                id = ConnectionData.keyToId.get(entry.getKey());
                blockData = new BlockData();
                var7_12 = ((JsonElement)entry.getValue()).getAsJsonObject().entrySet().iterator();
            } else lbl-1000:
            // 2 sources

            {
                blockData = MappingDataLoader.loadData("blockData.json");
                occluding = blockData.getAsJsonArray("occluding");
                for (JsonElement jsonElement : occluding) {
                    ConnectionData.occludingStates.add((int)ConnectionData.keyToId.get(jsonElement.getAsString()));
                }
                initActions = new ArrayList<ConnectorInitAction>();
                initActions.add(PumpkinConnectionHandler.init());
                initActions.addAll(BasicFenceConnectionHandler.init());
                initActions.add(NetherFenceConnectionHandler.init());
                initActions.addAll(WallConnectionHandler.init());
                initActions.add(MelonConnectionHandler.init());
                initActions.addAll(GlassConnectionHandler.init());
                initActions.add(ChestConnectionHandler.init());
                initActions.add(DoorConnectionHandler.init());
                initActions.add(RedstoneConnectionHandler.init());
                initActions.add(StairConnectionHandler.init());
                initActions.add(FlowerConnectionHandler.init());
                initActions.addAll(ChorusPlantConnectionHandler.init());
                initActions.add(TripwireConnectionHandler.init());
                initActions.add(SnowyGrassConnectionHandler.init());
                initActions.add(FireConnectionHandler.init());
                if (Via.getConfig().isVineClimbFix()) {
                    initActions.add(VineConnectionHandler.init());
                }
                var5_10 = ConnectionData.keyToId.keySet().iterator();
                block3: while (true) {
                    if (!var5_10.hasNext()) {
                        if (Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("packet") == false) return;
                        ConnectionData.blockConnectionProvider = new PacketBlockConnectionProvider();
                        Via.getManager().getProviders().register(BlockConnectionProvider.class, ConnectionData.blockConnectionProvider);
                        return;
                    }
                    key = (String)var5_10.next();
                    wrappedBlockData = WrappedBlockData.fromString(key);
                    var8_13 = initActions.iterator();
                    while (true) {
                        if (!var8_13.hasNext()) continue block3;
                        action = (ConnectorInitAction)var8_13.next();
                        action.check(wrappedBlockData);
                    }
                    break;
                }
            }
            while (var7_12.hasNext()) {
                type = var7_12.next();
                name = type.getKey();
                object = type.getValue().getAsJsonObject();
                data = new boolean[6];
                for (BlockFace value : BlockFace.values()) {
                    face = value.toString().toLowerCase(Locale.ROOT);
                    if (!object.has(face)) continue;
                    data[value.ordinal()] = object.getAsJsonPrimitive(face).getAsBoolean();
                }
                blockData.put(name, data);
            }
            if (((String)entry.getKey()).contains("stairs")) {
                blockData.put("allFalseIfStairPre1_12", new boolean[6]);
            }
            ConnectionData.blockConnectionData.put(id, blockData);
        }
    }

    public static boolean isWelcome(int blockState) {
        if (blockConnectionData.containsKey(blockState)) return true;
        if (connectionHandlerMap.containsKey(blockState)) return true;
        return false;
    }

    public static boolean connects(int blockState) {
        return connectionHandlerMap.containsKey(blockState);
    }

    public static int connect(UserConnection user, Position position, int blockState) {
        int n;
        ConnectionHandler handler = (ConnectionHandler)connectionHandlerMap.get(blockState);
        if (handler != null) {
            n = handler.connect(user, position, blockState);
            return n;
        }
        n = blockState;
        return n;
    }

    public static ConnectionHandler getConnectionHandler(int blockstate) {
        return (ConnectionHandler)connectionHandlerMap.get(blockstate);
    }

    public static int getId(String key) {
        return keyToId.getOrDefault((Object)key, -1);
    }

    public static String getKey(int id) {
        return (String)idToKey.get(id);
    }

    static {
        idToKey = new Int2ObjectOpenHashMap<String>(8582, 0.99f);
        keyToId = new Object2IntOpenHashMap<String>(8582, 0.99f);
        connectionHandlerMap = new Int2ObjectOpenHashMap<ConnectionHandler>(1);
        blockConnectionData = new Int2ObjectOpenHashMap<BlockData>(1);
        occludingStates = new IntOpenHashSet(377, 0.99f);
    }

    @FunctionalInterface
    static interface ConnectorInitAction {
        public void check(WrappedBlockData var1);
    }
}

