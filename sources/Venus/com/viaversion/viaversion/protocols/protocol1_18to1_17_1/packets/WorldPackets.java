/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_18;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.BlockEntityIds;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage.ChunkLightStorage;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.MathUtil;
import java.util.ArrayList;
import java.util.BitSet;

public final class WorldPackets {
    public static void register(Protocol1_18To1_17_1 protocol1_18To1_17_1) {
        protocol1_18To1_17_1.registerClientbound(ClientboundPackets1_17_1.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION1_14);
                this.handler(1::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.read(Type.UNSIGNED_BYTE);
                int n = BlockEntityIds.newId(s);
                packetWrapper.write(Type.VAR_INT, n);
                WorldPackets.access$000(n, packetWrapper.passthrough(Type.NBT));
            }
        });
        protocol1_18To1_17_1.registerClientbound(ClientboundPackets1_17_1.UPDATE_LIGHT, WorldPackets::lambda$register$0);
        protocol1_18To1_17_1.registerClientbound(ClientboundPackets1_17_1.CHUNK_DATA, arg_0 -> WorldPackets.lambda$register$1(protocol1_18To1_17_1, arg_0));
        protocol1_18To1_17_1.registerClientbound(ClientboundPackets1_17_1.UNLOAD_CHUNK, WorldPackets::lambda$register$2);
    }

    private static void handleSpawners(int n, CompoundTag compoundTag) {
        Object t;
        if (n == 8 && (t = compoundTag.get("SpawnData")) instanceof CompoundTag) {
            CompoundTag compoundTag2 = new CompoundTag();
            compoundTag.put("SpawnData", compoundTag2);
            compoundTag2.put("entity", t);
        }
    }

    private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.INT);
        int n2 = packetWrapper.passthrough(Type.INT);
        packetWrapper.user().get(ChunkLightStorage.class).clear(n, n2);
    }

    private static void lambda$register$1(Protocol1_18To1_17_1 protocol1_18To1_17_1, PacketWrapper packetWrapper) throws Exception {
        ChunkLightStorage.ChunkLight chunkLight;
        Object object;
        int n;
        Object bl;
        Object object2;
        Object e = protocol1_18To1_17_1.getEntityRewriter().tracker(packetWrapper.user());
        Chunk chunk = packetWrapper.read(new Chunk1_17Type(e.currentWorldSectionHeight()));
        ArrayList<BlockEntity> arrayList = new ArrayList<BlockEntity>(chunk.getBlockEntities().size());
        for (CompoundTag chunkSectionArray2 : chunk.getBlockEntities()) {
            NumberTag i = (NumberTag)chunkSectionArray2.get("x");
            object2 = (NumberTag)chunkSectionArray2.get("y");
            bl = (NumberTag)chunkSectionArray2.get("z");
            StringTag n2 = (StringTag)chunkSectionArray2.get("id");
            if (i == null || object2 == null || bl == null || n2 == null) continue;
            String n3 = n2.getValue();
            n = protocol1_18To1_17_1.getMappingData().blockEntityIds().getInt(Key.stripMinecraftNamespace(n3));
            if (n == -1) {
                Via.getPlatform().getLogger().warning("Unknown block entity: " + n3);
            }
            WorldPackets.handleSpawners(n, chunkSectionArray2);
            object = (byte)((i.asInt() & 0xF) << 4 | ((NumberTag)bl).asInt() & 0xF);
            arrayList.add(new BlockEntityImpl((byte)object, ((NumberTag)object2).asShort(), n, chunkSectionArray2));
        }
        Object object4 = chunk.getBiomeData();
        ChunkSection[] chunkSectionArray = chunk.getSections();
        for (int chunk1_18 = 0; chunk1_18 < chunkSectionArray.length; ++chunk1_18) {
            object2 = chunkSectionArray[chunk1_18];
            if (object2 == null) {
                chunkSectionArray[chunk1_18] = object2 = new ChunkSectionImpl();
                object2.setNonAirBlocksCount(0);
                bl = new DataPaletteImpl(4096);
                ((DataPaletteImpl)bl).addId(0);
                object2.addPalette(PaletteType.BLOCKS, (DataPalette)bl);
            }
            bl = new DataPaletteImpl(64);
            object2.addPalette(PaletteType.BIOMES, (DataPalette)bl);
            int chunkLight2 = chunk1_18 * 64;
            int bitSet = 0;
            n = chunkLight2;
            while (bitSet < 64) {
                object = object4[n];
                ((DataPaletteImpl)bl).setIdAt(bitSet, (int)(object != -1 ? object : (Object)false));
                ++bitSet;
                ++n;
            }
        }
        Chunk1_18 chunk1_18 = new Chunk1_18(chunk.getX(), chunk.getZ(), chunkSectionArray, chunk.getHeightMap(), arrayList);
        packetWrapper.write(new Chunk1_18Type(e.currentWorldSectionHeight(), MathUtil.ceilLog2(protocol1_18To1_17_1.getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(e.biomesSent())), chunk1_18);
        object2 = packetWrapper.user().get(ChunkLightStorage.class);
        boolean bl2 = !((ChunkLightStorage)object2).addLoadedChunk(chunk1_18.getX(), chunk1_18.getZ());
        ChunkLightStorage.ChunkLight chunkLight3 = chunkLight = Via.getConfig().cache1_17Light() ? ((ChunkLightStorage)object2).getLight(chunk1_18.getX(), chunk1_18.getZ()) : ((ChunkLightStorage)object2).removeLight(chunk1_18.getX(), chunk1_18.getZ());
        if (chunkLight == null) {
            Via.getPlatform().getLogger().warning("No light data found for chunk at " + chunk1_18.getX() + ", " + chunk1_18.getZ() + ". Chunk was already loaded: " + bl2);
            BitSet byArray2 = new BitSet();
            byArray2.set(0, e.currentWorldSectionHeight() + 2);
            packetWrapper.write(Type.BOOLEAN, false);
            packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, new long[0]);
            packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, new long[0]);
            packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, byArray2.toLongArray());
            packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, byArray2.toLongArray());
            packetWrapper.write(Type.VAR_INT, 0);
            packetWrapper.write(Type.VAR_INT, 0);
        } else {
            byte[] byArray;
            packetWrapper.write(Type.BOOLEAN, chunkLight.trustEdges());
            packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, chunkLight.skyLightMask());
            packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, chunkLight.blockLightMask());
            packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, chunkLight.emptySkyLightMask());
            packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, chunkLight.emptyBlockLightMask());
            packetWrapper.write(Type.VAR_INT, chunkLight.skyLight().length);
            byte[][] byArray2 = chunkLight.skyLight();
            n = byArray2.length;
            for (object = (Object)false; object < n; ++object) {
                byArray = byArray2[object];
                packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, byArray);
            }
            packetWrapper.write(Type.VAR_INT, chunkLight.blockLight().length);
            byArray2 = chunkLight.blockLight();
            n = byArray2.length;
            for (object = (Object)false; object < n; ++object) {
                byArray = byArray2[object];
                packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, byArray);
            }
        }
    }

    private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2 = packetWrapper.passthrough(Type.VAR_INT);
        int n3 = packetWrapper.passthrough(Type.VAR_INT);
        if (packetWrapper.user().get(ChunkLightStorage.class).isLoaded(n2, n3)) {
            if (!Via.getConfig().cache1_17Light()) {
                return;
            }
        } else {
            packetWrapper.cancel();
        }
        boolean bl = packetWrapper.passthrough(Type.BOOLEAN);
        long[] lArray = packetWrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
        long[] lArray2 = packetWrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
        long[] lArray3 = packetWrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
        long[] lArray4 = packetWrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
        int n4 = packetWrapper.passthrough(Type.VAR_INT);
        byte[][] byArrayArray = new byte[n4][];
        for (n = 0; n < n4; ++n) {
            byArrayArray[n] = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
        }
        n = packetWrapper.passthrough(Type.VAR_INT);
        byte[][] byArrayArray2 = new byte[n][];
        for (int i = 0; i < n; ++i) {
            byArrayArray2[i] = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
        }
        ChunkLightStorage chunkLightStorage = packetWrapper.user().get(ChunkLightStorage.class);
        chunkLightStorage.storeLight(n2, n3, new ChunkLightStorage.ChunkLight(bl, lArray, lArray2, lArray3, lArray4, byArrayArray, byArrayArray2));
    }

    static void access$000(int n, CompoundTag compoundTag) {
        WorldPackets.handleSpawners(n, compoundTag);
    }
}

