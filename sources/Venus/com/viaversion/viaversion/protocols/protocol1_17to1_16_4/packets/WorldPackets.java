/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.BitSet;

public final class WorldPackets {
    public static void register(Protocol1_17To1_16_4 protocol1_17To1_16_4) {
        BlockRewriter<ClientboundPackets1_16_2> blockRewriter = new BlockRewriter<ClientboundPackets1_16_2>(protocol1_17To1_16_4, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol1_17To1_16_4.registerClientbound(ClientboundPackets1_16_2.WORLD_BORDER, null, WorldPackets::lambda$register$0);
        protocol1_17To1_16_4.registerClientbound(ClientboundPackets1_16_2.UPDATE_LIGHT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$register$0);
            }

            private void writeLightArrays(PacketWrapper packetWrapper, int n) throws Exception {
                ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
                for (int i = 0; i < 18; ++i) {
                    if (!this.isSet(n, i)) continue;
                    arrayList.add(packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
                }
                packetWrapper.write(Type.VAR_INT, arrayList.size());
                for (byte[] byArray : arrayList) {
                    packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, byArray);
                }
            }

            private long[] toBitSetLongArray(int n) {
                return new long[]{n};
            }

            private boolean isSet(int n, int n2) {
                return (n & 1 << n2) != 0;
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                int n2 = packetWrapper.read(Type.VAR_INT);
                packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(n));
                packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(n2));
                packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(packetWrapper.read(Type.VAR_INT)));
                packetWrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(packetWrapper.read(Type.VAR_INT)));
                this.writeLightArrays(packetWrapper, n);
                this.writeLightArrays(packetWrapper, n2);
            }
        });
        protocol1_17To1_16_4.registerClientbound(ClientboundPackets1_16_2.CHUNK_DATA, arg_0 -> WorldPackets.lambda$register$1(protocol1_17To1_16_4, arg_0));
        blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
    }

    private static void writeMultiBlockChangePacket(PacketWrapper packetWrapper, Chunk chunk) throws Exception {
        long l = ((long)chunk.getX() & 0x3FFFFFL) << 42;
        l |= ((long)chunk.getZ() & 0x3FFFFFL) << 20;
        ChunkSection[] chunkSectionArray = chunk.getSections();
        for (int i = 0; i < chunkSectionArray.length; ++i) {
            ChunkSection chunkSection = chunkSectionArray[i];
            if (chunkSection == null) continue;
            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_17.MULTI_BLOCK_CHANGE);
            packetWrapper2.write(Type.LONG, l | (long)i & 0xFFFFFL);
            packetWrapper2.write(Type.BOOLEAN, true);
            BlockChangeRecord[] blockChangeRecordArray = new BlockChangeRecord[4096];
            DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
            int n = 0;
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    for (int i2 = 0; i2 < 16; ++i2) {
                        int n2 = Protocol1_17To1_16_4.MAPPINGS.getNewBlockStateId(dataPalette.idAt(j, k, i2));
                        blockChangeRecordArray[n++] = new BlockChangeRecord1_16_2(j, k, i2, n2);
                    }
                }
            }
            packetWrapper2.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecordArray);
            packetWrapper2.send(Protocol1_17To1_16_4.class);
        }
    }

    private static void lambda$register$1(Protocol1_17To1_16_4 protocol1_17To1_16_4, PacketWrapper packetWrapper) throws Exception {
        Chunk chunk = packetWrapper.read(new Chunk1_16_2Type());
        if (!chunk.isFullChunk()) {
            WorldPackets.writeMultiBlockChangePacket(packetWrapper, chunk);
            packetWrapper.cancel();
            return;
        }
        packetWrapper.write(new Chunk1_17Type(chunk.getSections().length), chunk);
        chunk.setChunkMask(BitSet.valueOf(new long[]{chunk.getBitmask()}));
        for (int i = 0; i < chunk.getSections().length; ++i) {
            ChunkSection chunkSection = chunk.getSections()[i];
            if (chunkSection == null) continue;
            DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
            for (int j = 0; j < dataPalette.size(); ++j) {
                int n = protocol1_17To1_16_4.getMappingData().getNewBlockStateId(dataPalette.idByIndex(j));
                dataPalette.setIdByIndex(j, n);
            }
        }
    }

    private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
        ClientboundPackets1_17 clientboundPackets1_17;
        int n = packetWrapper.read(Type.VAR_INT);
        switch (n) {
            case 0: {
                clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_SIZE;
                break;
            }
            case 1: {
                clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE;
                break;
            }
            case 2: {
                clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_CENTER;
                break;
            }
            case 3: {
                clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_INIT;
                break;
            }
            case 4: {
                clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY;
                break;
            }
            case 5: {
                clientboundPackets1_17 = ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid world border type received: " + n);
            }
        }
        packetWrapper.setPacketType(clientboundPackets1_17);
    }
}

