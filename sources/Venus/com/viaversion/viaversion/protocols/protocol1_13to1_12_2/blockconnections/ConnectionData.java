/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
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
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.UserBlockData;
import com.viaversion.viaversion.util.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ConnectionData {
    public static BlockConnectionProvider blockConnectionProvider;
    static final Object2IntMap<String> KEY_TO_ID;
    static final IntSet OCCLUDING_STATES;
    static Int2ObjectMap<ConnectionHandler> connectionHandlerMap;
    static Int2ObjectMap<BlockData> blockConnectionData;
    private static final BlockChangeRecord1_8[] EMPTY_RECORDS;

    public static void update(UserConnection userConnection, Position position) throws Exception {
        Boolean bl = null;
        for (BlockFace blockFace : BlockFace.values()) {
            Position position2 = position.getRelative(blockFace);
            int n = blockConnectionProvider.getBlockData(userConnection, position2.x(), position2.y(), position2.z());
            ConnectionHandler connectionHandler = (ConnectionHandler)connectionHandlerMap.get(n);
            if (connectionHandler == null) continue;
            int n2 = connectionHandler.connect(userConnection, position2, n);
            if (n2 == n) {
                if (bl == null) {
                    bl = blockConnectionProvider.storesBlocks(userConnection, position);
                }
                if (bl.booleanValue()) continue;
            }
            ConnectionData.updateBlockStorage(userConnection, position2.x(), position2.y(), position2.z(), n2);
            PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_13.BLOCK_CHANGE, null, userConnection);
            packetWrapper.write(Type.POSITION, position2);
            packetWrapper.write(Type.VAR_INT, n2);
            packetWrapper.send(Protocol1_13To1_12_2.class);
        }
    }

    public static void updateBlockStorage(UserConnection userConnection, int n, int n2, int n3, int n4) {
        if (!ConnectionData.needStoreBlocks()) {
            return;
        }
        if (ConnectionData.isWelcome(n4)) {
            blockConnectionProvider.storeBlock(userConnection, n, n2, n3, n4);
        } else {
            blockConnectionProvider.removeBlock(userConnection, n, n2, n3);
        }
    }

    public static void clearBlockStorage(UserConnection userConnection) {
        if (!ConnectionData.needStoreBlocks()) {
            return;
        }
        blockConnectionProvider.clearStorage(userConnection);
    }

    public static void markModified(UserConnection userConnection, Position position) {
        if (!ConnectionData.needStoreBlocks()) {
            return;
        }
        blockConnectionProvider.modifiedBlock(userConnection, position);
    }

    public static boolean needStoreBlocks() {
        return blockConnectionProvider.storesBlocks(null, null);
    }

    public static void connectBlocks(UserConnection userConnection, Chunk chunk) {
        int n = chunk.getX() << 4;
        int n2 = chunk.getZ() << 4;
        for (int i = 0; i < chunk.getSections().length; ++i) {
            int n3;
            int n4;
            ChunkSection chunkSection = chunk.getSections()[i];
            if (chunkSection == null) continue;
            DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
            boolean bl = false;
            for (n4 = 0; n4 < dataPalette.size(); ++n4) {
                n3 = dataPalette.idByIndex(n4);
                if (!ConnectionData.connects(n3)) continue;
                bl = true;
                break;
            }
            if (!bl) continue;
            n4 = i << 4;
            for (n3 = 0; n3 < 4096; ++n3) {
                Position position;
                int n5;
                int n6 = dataPalette.idAt(n3);
                ConnectionHandler connectionHandler = ConnectionData.getConnectionHandler(n6);
                if (connectionHandler == null || (n5 = connectionHandler.connect(userConnection, position = new Position(n + ChunkSection.xFromIndex(n3), n4 + ChunkSection.yFromIndex(n3), n2 + ChunkSection.zFromIndex(n3)), n6)) == n6) continue;
                dataPalette.setIdAt(n3, n5);
                ConnectionData.updateBlockStorage(userConnection, position.x(), position.y(), position.z(), n5);
            }
        }
    }

    public static void init() {
        Object object;
        Object object2;
        if (!Via.getConfig().isServersideBlockConnections()) {
            return;
        }
        Via.getPlatform().getLogger().info("Loading block connection mappings ...");
        ListTag listTag = (ListTag)MappingDataLoader.loadNBT("blockstates-1.13.nbt").get("blockstates");
        for (int i = 0; i < listTag.size(); ++i) {
            String string = (String)((Tag)listTag.get(i)).getValue();
            KEY_TO_ID.put(string, i);
        }
        connectionHandlerMap = new Int2ObjectOpenHashMap<ConnectionHandler>(3650, 0.99f);
        if (!Via.getConfig().isReduceBlockStorageMemory()) {
            blockConnectionData = new Int2ObjectOpenHashMap<BlockData>(2048);
            ListTag listTag2 = (ListTag)MappingDataLoader.loadNBT("blockConnections.nbt").get("data");
            for (Tag tag : listTag2) {
                Map.Entry<String, Tag> entry2;
                CompoundTag object32 = (CompoundTag)tag;
                object2 = new BlockData();
                for (Map.Entry<String, Tag> entry2 : object32.entrySet()) {
                    String string = entry2.getKey();
                    if (string.equals("id") || string.equals("ids")) continue;
                    boolean[] blArray = new boolean[4];
                    ByteArrayTag byteArrayTag = (ByteArrayTag)entry2.getValue();
                    for (byte by : byteArrayTag.getValue()) {
                        blArray[by] = true;
                    }
                    Object object3 = Integer.parseInt(string);
                    ((BlockData)object2).put((int)object3, blArray);
                }
                object = (NumberTag)object32.get("id");
                if (object != null) {
                    blockConnectionData.put(((NumberTag)object).asInt(), (BlockData)object2);
                    continue;
                }
                entry2 = (IntArrayTag)object32.get("ids");
                for (Object object3 : (String)((IntArrayTag)((Object)entry2)).getValue()) {
                    blockConnectionData.put((int)object3, (BlockData)object2);
                }
            }
        }
        for (String string : ConnectionData.occludingBlockStates()) {
            OCCLUDING_STATES.add(KEY_TO_ID.getInt(string));
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(PumpkinConnectionHandler.init());
        arrayList.addAll(BasicFenceConnectionHandler.init());
        arrayList.add(NetherFenceConnectionHandler.init());
        arrayList.addAll(WallConnectionHandler.init());
        arrayList.add(MelonConnectionHandler.init());
        arrayList.addAll(GlassConnectionHandler.init());
        arrayList.add(ChestConnectionHandler.init());
        arrayList.add(DoorConnectionHandler.init());
        arrayList.add(RedstoneConnectionHandler.init());
        arrayList.add(StairConnectionHandler.init());
        arrayList.add(FlowerConnectionHandler.init());
        arrayList.addAll(ChorusPlantConnectionHandler.init());
        arrayList.add(TripwireConnectionHandler.init());
        arrayList.add(SnowyGrassConnectionHandler.init());
        arrayList.add(FireConnectionHandler.init());
        if (Via.getConfig().isVineClimbFix()) {
            arrayList.add(VineConnectionHandler.init());
        }
        for (String string : KEY_TO_ID.keySet()) {
            WrappedBlockData wrappedBlockData = WrappedBlockData.fromString(string);
            object2 = arrayList.iterator();
            while (object2.hasNext()) {
                object = (ConnectorInitAction)object2.next();
                object.check(wrappedBlockData);
            }
        }
        if (Via.getConfig().getBlockConnectionMethod().equalsIgnoreCase("packet")) {
            blockConnectionProvider = new PacketBlockConnectionProvider();
            Via.getManager().getProviders().register(BlockConnectionProvider.class, blockConnectionProvider);
        }
    }

    public static boolean isWelcome(int n) {
        return blockConnectionData.containsKey(n) || connectionHandlerMap.containsKey(n);
    }

    public static boolean connects(int n) {
        return connectionHandlerMap.containsKey(n);
    }

    public static int connect(UserConnection userConnection, Position position, int n) {
        ConnectionHandler connectionHandler = (ConnectionHandler)connectionHandlerMap.get(n);
        return connectionHandler != null ? connectionHandler.connect(userConnection, position, n) : n;
    }

    public static ConnectionHandler getConnectionHandler(int n) {
        return (ConnectionHandler)connectionHandlerMap.get(n);
    }

    public static int getId(String string) {
        return KEY_TO_ID.getOrDefault((Object)Key.stripMinecraftNamespace(string), -1);
    }

    private static String[] occludingBlockStates() {
        return new String[]{"stone", "granite", "polished_granite", "diorite", "polished_diorite", "andesite", "polished_andesite", "grass_block[snowy=false]", "dirt", "coarse_dirt", "podzol[snowy=false]", "cobblestone", "oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks", "bedrock", "sand", "red_sand", "gravel", "gold_ore", "iron_ore", "coal_ore", "oak_log[axis=x]", "oak_log[axis=y]", "oak_log[axis=z]", "spruce_log[axis=x]", "spruce_log[axis=y]", "spruce_log[axis=z]", "birch_log[axis=x]", "birch_log[axis=y]", "birch_log[axis=z]", "jungle_log[axis=x]", "jungle_log[axis=y]", "jungle_log[axis=z]", "acacia_log[axis=x]", "acacia_log[axis=y]", "acacia_log[axis=z]", "dark_oak_log[axis=x]", "dark_oak_log[axis=y]", "dark_oak_log[axis=z]", "oak_wood[axis=y]", "spruce_wood[axis=y]", "birch_wood[axis=y]", "jungle_wood[axis=y]", "acacia_wood[axis=y]", "dark_oak_wood[axis=y]", "sponge", "wet_sponge", "lapis_ore", "lapis_block", "dispenser[facing=north,triggered=true]", "dispenser[facing=north,triggered=false]", "dispenser[facing=east,triggered=true]", "dispenser[facing=east,triggered=false]", "dispenser[facing=south,triggered=true]", "dispenser[facing=south,triggered=false]", "dispenser[facing=west,triggered=true]", "dispenser[facing=west,triggered=false]", "dispenser[facing=up,triggered=true]", "dispenser[facing=up,triggered=false]", "dispenser[facing=down,triggered=true]", "dispenser[facing=down,triggered=false]", "sandstone", "chiseled_sandstone", "cut_sandstone", "note_block[instrument=harp,note=0,powered=false]", "white_wool", "orange_wool", "magenta_wool", "light_blue_wool", "yellow_wool", "lime_wool", "pink_wool", "gray_wool", "light_gray_wool", "cyan_wool", "purple_wool", "blue_wool", "brown_wool", "green_wool", "red_wool", "black_wool", "gold_block", "iron_block", "bricks", "bookshelf", "mossy_cobblestone", "obsidian", "spawner", "diamond_ore", "diamond_block", "crafting_table", "furnace[facing=north,lit=true]", "furnace[facing=north,lit=false]", "furnace[facing=south,lit=true]", "furnace[facing=south,lit=false]", "furnace[facing=west,lit=true]", "furnace[facing=west,lit=false]", "furnace[facing=east,lit=true]", "furnace[facing=east,lit=false]", "redstone_ore[lit=true]", "redstone_ore[lit=false]", "snow_block", "clay", "jukebox[has_record=true]", "jukebox[has_record=false]", "netherrack", "soul_sand", "carved_pumpkin[facing=north]", "carved_pumpkin[facing=south]", "carved_pumpkin[facing=west]", "carved_pumpkin[facing=east]", "jack_o_lantern[facing=north]", "jack_o_lantern[facing=south]", "jack_o_lantern[facing=west]", "jack_o_lantern[facing=east]", "infested_stone", "infested_cobblestone", "infested_stone_bricks", "infested_mossy_stone_bricks", "infested_cracked_stone_bricks", "infested_chiseled_stone_bricks", "stone_bricks", "mossy_stone_bricks", "cracked_stone_bricks", "chiseled_stone_bricks", "brown_mushroom_block[down=true,east=true,north=true,south=true,up=true,west=true]", "brown_mushroom_block[down=false,east=true,north=true,south=false,up=true,west=false]", "brown_mushroom_block[down=false,east=true,north=false,south=true,up=true,west=false]", "brown_mushroom_block[down=false,east=true,north=false,south=false,up=true,west=false]", "brown_mushroom_block[down=false,east=false,north=true,south=false,up=true,west=true]", "brown_mushroom_block[down=false,east=false,north=true,south=false,up=true,west=false]", "brown_mushroom_block[down=false,east=false,north=false,south=true,up=true,west=true]", "brown_mushroom_block[down=false,east=false,north=false,south=true,up=true,west=false]", "brown_mushroom_block[down=false,east=false,north=false,south=false,up=true,west=true]", "brown_mushroom_block[down=false,east=false,north=false,south=false,up=true,west=false]", "brown_mushroom_block[down=false,east=false,north=false,south=false,up=false,west=false]", "red_mushroom_block[down=true,east=true,north=true,south=true,up=true,west=true]", "red_mushroom_block[down=false,east=true,north=true,south=false,up=true,west=false]", "red_mushroom_block[down=false,east=true,north=false,south=true,up=true,west=false]", "red_mushroom_block[down=false,east=true,north=false,south=false,up=true,west=false]", "red_mushroom_block[down=false,east=false,north=true,south=false,up=true,west=true]", "red_mushroom_block[down=false,east=false,north=true,south=false,up=true,west=false]", "red_mushroom_block[down=false,east=false,north=false,south=true,up=true,west=true]", "red_mushroom_block[down=false,east=false,north=false,south=true,up=true,west=false]", "red_mushroom_block[down=false,east=false,north=false,south=false,up=true,west=true]", "red_mushroom_block[down=false,east=false,north=false,south=false,up=true,west=false]", "red_mushroom_block[down=false,east=false,north=false,south=false,up=false,west=false]", "mushroom_stem[down=true,east=true,north=true,south=true,up=true,west=true]", "mushroom_stem[down=false,east=true,north=true,south=true,up=false,west=true]", "melon", "mycelium[snowy=false]", "nether_bricks", "end_stone", "redstone_lamp[lit=true]", "redstone_lamp[lit=false]", "emerald_ore", "emerald_block", "command_block[conditional=true,facing=north]", "command_block[conditional=true,facing=east]", "command_block[conditional=true,facing=south]", "command_block[conditional=true,facing=west]", "command_block[conditional=true,facing=up]", "command_block[conditional=true,facing=down]", "command_block[conditional=false,facing=north]", "command_block[conditional=false,facing=east]", "command_block[conditional=false,facing=south]", "command_block[conditional=false,facing=west]", "command_block[conditional=false,facing=up]", "command_block[conditional=false,facing=down]", "nether_quartz_ore", "quartz_block", "chiseled_quartz_block", "quartz_pillar[axis=x]", "quartz_pillar[axis=y]", "quartz_pillar[axis=z]", "dropper[facing=north,triggered=true]", "dropper[facing=north,triggered=false]", "dropper[facing=east,triggered=true]", "dropper[facing=east,triggered=false]", "dropper[facing=south,triggered=true]", "dropper[facing=south,triggered=false]", "dropper[facing=west,triggered=true]", "dropper[facing=west,triggered=false]", "dropper[facing=up,triggered=true]", "dropper[facing=up,triggered=false]", "dropper[facing=down,triggered=true]", "dropper[facing=down,triggered=false]", "white_terracotta", "orange_terracotta", "magenta_terracotta", "light_blue_terracotta", "yellow_terracotta", "lime_terracotta", "pink_terracotta", "gray_terracotta", "light_gray_terracotta", "cyan_terracotta", "purple_terracotta", "blue_terracotta", "brown_terracotta", "green_terracotta", "red_terracotta", "black_terracotta", "slime_block", "barrier", "prismarine", "prismarine_bricks", "dark_prismarine", "hay_block[axis=x]", "hay_block[axis=y]", "hay_block[axis=z]", "terracotta", "coal_block", "packed_ice", "red_sandstone", "chiseled_red_sandstone", "cut_red_sandstone", "oak_slab[type=double,waterlogged=false]", "spruce_slab[type=double,waterlogged=false]", "birch_slab[type=double,waterlogged=false]", "jungle_slab[type=double,waterlogged=false]", "acacia_slab[type=double,waterlogged=false]", "dark_oak_slab[type=double,waterlogged=false]", "stone_slab[type=double,waterlogged=false]", "sandstone_slab[type=double,waterlogged=false]", "petrified_oak_slab[type=double,waterlogged=false]", "cobblestone_slab[type=double,waterlogged=false]", "brick_slab[type=double,waterlogged=false]", "stone_brick_slab[type=double,waterlogged=false]", "nether_brick_slab[type=double,waterlogged=false]", "quartz_slab[type=double,waterlogged=false]", "red_sandstone_slab[type=double,waterlogged=false]", "purpur_slab[type=double,waterlogged=false]", "smooth_stone", "smooth_sandstone", "smooth_quartz", "smooth_red_sandstone", "purpur_block", "purpur_pillar[axis=x]", "purpur_pillar[axis=y]", "purpur_pillar[axis=z]", "end_stone_bricks", "repeating_command_block[conditional=true,facing=north]", "repeating_command_block[conditional=true,facing=east]", "repeating_command_block[conditional=true,facing=south]", "repeating_command_block[conditional=true,facing=west]", "repeating_command_block[conditional=true,facing=up]", "repeating_command_block[conditional=true,facing=down]", "repeating_command_block[conditional=false,facing=north]", "repeating_command_block[conditional=false,facing=east]", "repeating_command_block[conditional=false,facing=south]", "repeating_command_block[conditional=false,facing=west]", "repeating_command_block[conditional=false,facing=up]", "repeating_command_block[conditional=false,facing=down]", "chain_command_block[conditional=true,facing=north]", "chain_command_block[conditional=true,facing=east]", "chain_command_block[conditional=true,facing=south]", "chain_command_block[conditional=true,facing=west]", "chain_command_block[conditional=true,facing=up]", "chain_command_block[conditional=true,facing=down]", "chain_command_block[conditional=false,facing=north]", "chain_command_block[conditional=false,facing=east]", "chain_command_block[conditional=false,facing=south]", "chain_command_block[conditional=false,facing=west]", "chain_command_block[conditional=false,facing=up]", "chain_command_block[conditional=false,facing=down]", "magma_block", "nether_wart_block", "red_nether_bricks", "bone_block[axis=x]", "bone_block[axis=y]", "bone_block[axis=z]", "white_glazed_terracotta[facing=north]", "white_glazed_terracotta[facing=south]", "white_glazed_terracotta[facing=west]", "white_glazed_terracotta[facing=east]", "orange_glazed_terracotta[facing=north]", "orange_glazed_terracotta[facing=south]", "orange_glazed_terracotta[facing=west]", "orange_glazed_terracotta[facing=east]", "magenta_glazed_terracotta[facing=north]", "magenta_glazed_terracotta[facing=south]", "magenta_glazed_terracotta[facing=west]", "magenta_glazed_terracotta[facing=east]", "light_blue_glazed_terracotta[facing=north]", "light_blue_glazed_terracotta[facing=south]", "light_blue_glazed_terracotta[facing=west]", "light_blue_glazed_terracotta[facing=east]", "yellow_glazed_terracotta[facing=north]", "yellow_glazed_terracotta[facing=south]", "yellow_glazed_terracotta[facing=west]", "yellow_glazed_terracotta[facing=east]", "lime_glazed_terracotta[facing=north]", "lime_glazed_terracotta[facing=south]", "lime_glazed_terracotta[facing=west]", "lime_glazed_terracotta[facing=east]", "pink_glazed_terracotta[facing=north]", "pink_glazed_terracotta[facing=south]", "pink_glazed_terracotta[facing=west]", "pink_glazed_terracotta[facing=east]", "gray_glazed_terracotta[facing=north]", "gray_glazed_terracotta[facing=south]", "gray_glazed_terracotta[facing=west]", "gray_glazed_terracotta[facing=east]", "light_gray_glazed_terracotta[facing=north]", "light_gray_glazed_terracotta[facing=south]", "light_gray_glazed_terracotta[facing=west]", "light_gray_glazed_terracotta[facing=east]", "cyan_glazed_terracotta[facing=north]", "cyan_glazed_terracotta[facing=south]", "cyan_glazed_terracotta[facing=west]", "cyan_glazed_terracotta[facing=east]", "purple_glazed_terracotta[facing=north]", "purple_glazed_terracotta[facing=south]", "purple_glazed_terracotta[facing=west]", "purple_glazed_terracotta[facing=east]", "blue_glazed_terracotta[facing=north]", "blue_glazed_terracotta[facing=south]", "blue_glazed_terracotta[facing=west]", "blue_glazed_terracotta[facing=east]", "brown_glazed_terracotta[facing=north]", "brown_glazed_terracotta[facing=south]", "brown_glazed_terracotta[facing=west]", "brown_glazed_terracotta[facing=east]", "green_glazed_terracotta[facing=north]", "green_glazed_terracotta[facing=south]", "green_glazed_terracotta[facing=west]", "green_glazed_terracotta[facing=east]", "red_glazed_terracotta[facing=north]", "red_glazed_terracotta[facing=south]", "red_glazed_terracotta[facing=west]", "red_glazed_terracotta[facing=east]", "black_glazed_terracotta[facing=north]", "black_glazed_terracotta[facing=south]", "black_glazed_terracotta[facing=west]", "black_glazed_terracotta[facing=east]", "white_concrete", "orange_concrete", "magenta_concrete", "light_blue_concrete", "yellow_concrete", "lime_concrete", "pink_concrete", "gray_concrete", "light_gray_concrete", "cyan_concrete", "purple_concrete", "blue_concrete", "brown_concrete", "green_concrete", "red_concrete", "black_concrete", "white_concrete_powder", "orange_concrete_powder", "magenta_concrete_powder", "light_blue_concrete_powder", "yellow_concrete_powder", "lime_concrete_powder", "pink_concrete_powder", "gray_concrete_powder", "light_gray_concrete_powder", "cyan_concrete_powder", "purple_concrete_powder", "blue_concrete_powder", "brown_concrete_powder", "green_concrete_powder", "red_concrete_powder", "black_concrete_powder", "structure_block[mode=save]", "structure_block[mode=load]", "structure_block[mode=corner]", "structure_block[mode=data]", "glowstone"};
    }

    public static Object2IntMap<String> getKeyToId() {
        return KEY_TO_ID;
    }

    static BlockChangeRecord1_8[] access$000() {
        return EMPTY_RECORDS;
    }

    static {
        KEY_TO_ID = new Object2IntOpenHashMap<String>(8582, 0.99f);
        OCCLUDING_STATES = new IntOpenHashSet(377, 0.99f);
        connectionHandlerMap = new Int2ObjectOpenHashMap<ConnectionHandler>();
        blockConnectionData = new Int2ObjectOpenHashMap<BlockData>();
        EMPTY_RECORDS = new BlockChangeRecord1_8[0];
        KEY_TO_ID.defaultReturnValue(-1);
    }

    public static final class NeighbourUpdater {
        private final UserConnection user;
        private final UserBlockData userBlockData;

        public NeighbourUpdater(UserConnection userConnection) {
            this.user = userConnection;
            this.userBlockData = blockConnectionProvider.forUser(userConnection);
        }

        public void updateChunkSectionNeighbours(int n, int n2, int n3) throws Exception {
            int n4 = n3 << 4;
            ArrayList<BlockChangeRecord1_8> arrayList = new ArrayList<BlockChangeRecord1_8>();
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    int n5;
                    int n6;
                    int n7;
                    int n8 = Math.abs(i) + Math.abs(j);
                    if (n8 == 0) continue;
                    int n9 = n + i << 4;
                    int n10 = n2 + j << 4;
                    if (n8 == 2) {
                        for (n7 = n4; n7 < n4 + 16; ++n7) {
                            n6 = i == 1 ? 0 : 15;
                            n5 = j == 1 ? 0 : 15;
                            this.updateBlock(n9 + n6, n7, n10 + n5, arrayList);
                        }
                    } else {
                        for (n7 = n4; n7 < n4 + 16; ++n7) {
                            int n11;
                            int n12;
                            if (i == 1) {
                                n6 = 0;
                                n5 = 2;
                                n12 = 0;
                                n11 = 16;
                            } else if (i == -1) {
                                n6 = 14;
                                n5 = 16;
                                n12 = 0;
                                n11 = 16;
                            } else if (j == 1) {
                                n6 = 0;
                                n5 = 16;
                                n12 = 0;
                                n11 = 2;
                            } else {
                                n6 = 0;
                                n5 = 16;
                                n12 = 14;
                                n11 = 16;
                            }
                            for (int k = n6; k < n5; ++k) {
                                for (int i2 = n12; i2 < n11; ++i2) {
                                    this.updateBlock(n9 + k, n7, n10 + i2, arrayList);
                                }
                            }
                        }
                    }
                    if (arrayList.isEmpty()) continue;
                    PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_13.MULTI_BLOCK_CHANGE, null, this.user);
                    packetWrapper.write(Type.INT, n + i);
                    packetWrapper.write(Type.INT, n2 + j);
                    packetWrapper.write(Type.BLOCK_CHANGE_RECORD_ARRAY, arrayList.toArray(ConnectionData.access$000()));
                    packetWrapper.send(Protocol1_13To1_12_2.class);
                    arrayList.clear();
                }
            }
        }

        private void updateBlock(int n, int n2, int n3, List<BlockChangeRecord1_8> list) {
            int n4 = this.userBlockData.getBlockData(n, n2, n3);
            ConnectionHandler connectionHandler = ConnectionData.getConnectionHandler(n4);
            if (connectionHandler == null) {
                return;
            }
            Position position = new Position(n, n2, n3);
            int n5 = connectionHandler.connect(this.user, position, n4);
            if (n4 != n5 || !blockConnectionProvider.storesBlocks(this.user, null)) {
                list.add(new BlockChangeRecord1_8(n & 0xF, n2, n3 & 0xF, n5));
                ConnectionData.updateBlockStorage(this.user, n, n2, n3, n5);
            }
        }
    }

    @FunctionalInterface
    static interface ConnectorInitAction {
        public void check(WrappedBlockData var1);
    }
}

