/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.NibbleArray;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import java.util.Arrays;

public class WorldPackets {
    public static final int SERVERSIDE_VIEW_DISTANCE = 64;
    private static final byte[] FULL_LIGHT = new byte[2048];
    public static int air;
    public static int voidAir;
    public static int caveAir;

    public static void register(Protocol1_14To1_13_2 protocol1_14To1_13_2) {
        BlockRewriter<ClientboundPackets1_13> blockRewriter = new BlockRewriter<ClientboundPackets1_13>(protocol1_14To1_13_2, null);
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.BLOCK_BREAK_ANIMATION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.BYTE);
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.BLOCK_ACTION, new PacketHandlers(protocol1_14To1_13_2){
            final Protocol1_14To1_13_2 val$protocol;
            {
                this.val$protocol = protocol1_14To1_13_2;
            }

            @Override
            public void register() {
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(arg_0 -> 3.lambda$register$0(this.val$protocol, arg_0));
            }

            private static void lambda$register$0(Protocol1_14To1_13_2 protocol1_14To1_13_2, PacketWrapper packetWrapper) throws Exception {
                packetWrapper.set(Type.VAR_INT, 0, protocol1_14To1_13_2.getMappingData().getNewBlockId(packetWrapper.get(Type.VAR_INT, 0)));
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.BLOCK_CHANGE, new PacketHandlers(protocol1_14To1_13_2){
            final Protocol1_14To1_13_2 val$protocol;
            {
                this.val$protocol = protocol1_14To1_13_2;
            }

            @Override
            public void register() {
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.VAR_INT);
                this.handler(arg_0 -> 4.lambda$register$0(this.val$protocol, arg_0));
            }

            private static void lambda$register$0(Protocol1_14To1_13_2 protocol1_14To1_13_2, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 0);
                packetWrapper.set(Type.VAR_INT, 0, protocol1_14To1_13_2.getMappingData().getNewBlockStateId(n));
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SERVER_DIFFICULTY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, false);
            }
        });
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.EXPLOSION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                for (int i = 0; i < 3; ++i) {
                    float f = packetWrapper.get(Type.FLOAT, i).floatValue();
                    if (!(f < 0.0f)) continue;
                    f = (int)f;
                    packetWrapper.set(Type.FLOAT, i, Float.valueOf(f));
                }
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, arg_0 -> WorldPackets.lambda$register$0(protocol1_14To1_13_2, arg_0));
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.EFFECT, new PacketHandlers(protocol1_14To1_13_2){
            final Protocol1_14To1_13_2 val$protocol;
            {
                this.val$protocol = protocol1_14To1_13_2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION, Type.POSITION1_14);
                this.map(Type.INT);
                this.handler(arg_0 -> 7.lambda$register$0(this.val$protocol, arg_0));
            }

            private static void lambda$register$0(Protocol1_14To1_13_2 protocol1_14To1_13_2, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                int n2 = packetWrapper.get(Type.INT, 1);
                if (n == 1010) {
                    packetWrapper.set(Type.INT, 1, protocol1_14To1_13_2.getMappingData().getNewItemId(n2));
                } else if (n == 2001) {
                    packetWrapper.set(Type.INT, 1, protocol1_14To1_13_2.getMappingData().getNewBlockStateId(n2));
                }
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.MAP_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.map(Type.BOOLEAN);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, false);
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.RESPAWN, new PacketHandlers(protocol1_14To1_13_2){
            final Protocol1_14To1_13_2 val$protocol;
            {
                this.val$protocol = protocol1_14To1_13_2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(9::lambda$register$0);
                this.handler(arg_0 -> 9.lambda$register$1(this.val$protocol, arg_0));
                this.handler(9::lambda$register$2);
            }

            private static void lambda$register$2(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.send(Protocol1_14To1_13_2.class);
                packetWrapper.cancel();
                WorldPackets.sendViewDistancePacket(packetWrapper.user());
            }

            private static void lambda$register$1(Protocol1_14To1_13_2 protocol1_14To1_13_2, PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.read(Type.UNSIGNED_BYTE);
                PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_14.SERVER_DIFFICULTY);
                packetWrapper2.write(Type.UNSIGNED_BYTE, s);
                packetWrapper2.write(Type.BOOLEAN, false);
                packetWrapper2.scheduleSend(protocol1_14To1_13_2.getClass());
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 0);
                clientWorld.setEnvironment(n);
                EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
                entityTracker1_14.setForceSendCenterChunk(false);
            }
        });
        protocol1_14To1_13_2.registerClientbound(ClientboundPackets1_13.SPAWN_POSITION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.POSITION, Type.POSITION1_14);
            }
        });
    }

    static void sendViewDistancePacket(UserConnection userConnection) throws Exception {
        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_14.UPDATE_VIEW_DISTANCE, null, userConnection);
        packetWrapper.write(Type.VAR_INT, 64);
        packetWrapper.send(Protocol1_14To1_13_2.class);
    }

    private static long[] encodeHeightMap(int[] nArray) {
        return CompactArrayUtil.createCompactArray(9, nArray.length, arg_0 -> WorldPackets.lambda$encodeHeightMap$1(nArray, arg_0));
    }

    private static void setNonFullLight(Chunk chunk, ChunkSection chunkSection, int n, int n2, int n3, int n4) {
        int n5 = 0;
        int n6 = 0;
        for (BlockFace blockFace : BlockFace.values()) {
            NibbleArray nibbleArray = chunkSection.getLight().getSkyLightNibbleArray();
            NibbleArray nibbleArray2 = chunkSection.getLight().getBlockLightNibbleArray();
            int n7 = n2 + blockFace.modX();
            int n8 = n3 + blockFace.modY();
            int n9 = n4 + blockFace.modZ();
            if (blockFace.modX() != 0) {
                if (n7 == 16 || n7 == -1) {
                    continue;
                }
            } else if (blockFace.modY() != 0) {
                if (n8 == 16 || n8 == -1) {
                    ChunkSection n10;
                    if (n8 == 16) {
                        ++n;
                        n8 = 0;
                    } else {
                        --n;
                        n8 = 15;
                    }
                    if (n == chunk.getSections().length || n == -1 || (n10 = chunk.getSections()[n]) == null) continue;
                    nibbleArray = n10.getLight().getSkyLightNibbleArray();
                    nibbleArray2 = n10.getLight().getBlockLightNibbleArray();
                }
            } else if (blockFace.modZ() != 0 && (n9 == 16 || n9 == -1)) continue;
            if (nibbleArray2 != null && n6 != 15) {
                int n11 = nibbleArray2.get(n7, n8, n9);
                if (n11 == 15) {
                    n6 = 14;
                } else if (n11 > n6) {
                    n6 = n11 - 1;
                }
            }
            if (nibbleArray == null || n5 == 15) continue;
            int n10 = nibbleArray.get(n7, n8, n9);
            if (n10 == 15) {
                if (blockFace.modY() == 1) {
                    n5 = 15;
                    continue;
                }
                n5 = 14;
                continue;
            }
            if (n10 <= n5) continue;
            n5 = n10 - 1;
        }
        if (n5 != 0) {
            if (!chunkSection.getLight().hasSkyLight()) {
                byte[] byArray = new byte[2028];
                chunkSection.getLight().setSkyLight(byArray);
            }
            chunkSection.getLight().getSkyLightNibbleArray().set(n2, n3, n4, n5);
        }
        if (n6 != 0) {
            chunkSection.getLight().getBlockLightNibbleArray().set(n2, n3, n4, n6);
        }
    }

    private static long getChunkIndex(int n, int n2) {
        return ((long)n & 0x3FFFFFFL) << 38 | (long)n2 & 0x3FFFFFFL;
    }

    private static long lambda$encodeHeightMap$1(int[] nArray, int n) {
        return nArray[n];
    }

    private static void lambda$register$0(Protocol1_14To1_13_2 protocol1_14To1_13_2, PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2;
        int n3;
        Object object;
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk chunk = packetWrapper.read(new Chunk1_13Type(clientWorld));
        packetWrapper.write(new Chunk1_14Type(), chunk);
        int[] nArray = new int[256];
        int[] nArray2 = new int[256];
        for (int i = 0; i < chunk.getSections().length; ++i) {
            int n4;
            object = chunk.getSections()[i];
            if (object == null) continue;
            DataPalette dataPalette = object.palette(PaletteType.BLOCKS);
            n3 = 0;
            for (n2 = 0; n2 < dataPalette.size(); ++n2) {
                n4 = dataPalette.idByIndex(n2);
                n = protocol1_14To1_13_2.getMappingData().getNewBlockStateId(n4);
                if (n3 == 0 && n != air && n != voidAir && n != caveAir) {
                    n3 = 1;
                }
                dataPalette.setIdByIndex(n2, n);
            }
            if (n3 == 0) {
                object.setNonAirBlocksCount(0);
                continue;
            }
            n2 = 0;
            n4 = i << 4;
            for (n = 0; n < 4096; ++n) {
                int n5 = dataPalette.idAt(n);
                if (n5 == air || n5 == voidAir || n5 == caveAir) continue;
                ++n2;
                int n6 = n & 0xFF;
                int n7 = ChunkSection.yFromIndex(n);
                nArray2[n6] = n4 + n7 + 1;
                if (protocol1_14To1_13_2.getMappingData().getMotionBlocking().contains(n5)) {
                    nArray[n6] = n4 + n7 + 1;
                }
                if (!Via.getConfig().isNonFullBlockLightFix() || !protocol1_14To1_13_2.getMappingData().getNonFullBlocks().contains(n5)) continue;
                int n8 = ChunkSection.xFromIndex(n);
                int n9 = ChunkSection.zFromIndex(n);
                WorldPackets.setNonFullLight(chunk, (ChunkSection)object, i, n8, n7, n9);
            }
            object.setNonAirBlocksCount(n2);
        }
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("MOTION_BLOCKING", new LongArrayTag(WorldPackets.encodeHeightMap(nArray)));
        compoundTag.put("WORLD_SURFACE", new LongArrayTag(WorldPackets.encodeHeightMap(nArray2)));
        chunk.setHeightMap(compoundTag);
        object = packetWrapper.create(ClientboundPackets1_14.UPDATE_LIGHT);
        object.write(Type.VAR_INT, chunk.getX());
        object.write(Type.VAR_INT, chunk.getZ());
        int n10 = chunk.isFullChunk() ? 262143 : 0;
        n3 = 0;
        for (n2 = 0; n2 < chunk.getSections().length; ++n2) {
            ChunkSection chunkSection = chunk.getSections()[n2];
            if (chunkSection == null) continue;
            if (!chunk.isFullChunk() && chunkSection.getLight().hasSkyLight()) {
                n10 |= 1 << n2 + 1;
            }
            n3 |= 1 << n2 + 1;
        }
        object.write(Type.VAR_INT, n10);
        object.write(Type.VAR_INT, n3);
        object.write(Type.VAR_INT, 0);
        object.write(Type.VAR_INT, 0);
        if (chunk.isFullChunk()) {
            object.write(Type.BYTE_ARRAY_PRIMITIVE, FULL_LIGHT);
        }
        for (ChunkSection chunkSection : chunk.getSections()) {
            if (chunkSection == null || !chunkSection.getLight().hasSkyLight()) {
                if (!chunk.isFullChunk()) continue;
                object.write(Type.BYTE_ARRAY_PRIMITIVE, FULL_LIGHT);
                continue;
            }
            object.write(Type.BYTE_ARRAY_PRIMITIVE, chunkSection.getLight().getSkyLight());
        }
        if (chunk.isFullChunk()) {
            object.write(Type.BYTE_ARRAY_PRIMITIVE, FULL_LIGHT);
        }
        for (ChunkSection chunkSection : chunk.getSections()) {
            if (chunkSection == null) continue;
            object.write(Type.BYTE_ARRAY_PRIMITIVE, chunkSection.getLight().getBlockLight());
        }
        EntityTracker1_14 entityTracker1_14 = (EntityTracker1_14)packetWrapper.user().getEntityTracker(Protocol1_14To1_13_2.class);
        int n11 = Math.abs(entityTracker1_14.getChunkCenterX() - chunk.getX());
        n = Math.abs(entityTracker1_14.getChunkCenterZ() - chunk.getZ());
        if (entityTracker1_14.isForceSendCenterChunk() || n11 >= 64 || n >= 64) {
            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_14.UPDATE_VIEW_POSITION);
            packetWrapper2.write(Type.VAR_INT, chunk.getX());
            packetWrapper2.write(Type.VAR_INT, chunk.getZ());
            packetWrapper2.send(Protocol1_14To1_13_2.class);
            entityTracker1_14.setChunkCenterX(chunk.getX());
            entityTracker1_14.setChunkCenterZ(chunk.getZ());
        }
        object.send(Protocol1_14To1_13_2.class);
        for (ChunkSection chunkSection : chunk.getSections()) {
            if (chunkSection == null) continue;
            chunkSection.setLight(null);
        }
    }

    static {
        Arrays.fill(FULL_LIGHT, (byte)-1);
    }
}

