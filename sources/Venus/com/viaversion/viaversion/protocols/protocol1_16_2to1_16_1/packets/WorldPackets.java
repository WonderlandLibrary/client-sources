/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.List;

public class WorldPackets {
    private static final BlockChangeRecord[] EMPTY_RECORDS = new BlockChangeRecord[0];

    public static void register(Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1) {
        BlockRewriter<ClientboundPackets1_16> blockRewriter = new BlockRewriter<ClientboundPackets1_16>(protocol1_16_2To1_16_1, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol1_16_2To1_16_1.registerClientbound(ClientboundPackets1_16.CHUNK_DATA, arg_0 -> WorldPackets.lambda$register$0(protocol1_16_2To1_16_1, arg_0));
        protocol1_16_2To1_16_1.registerClientbound(ClientboundPackets1_16.MULTI_BLOCK_CHANGE, arg_0 -> WorldPackets.lambda$register$1(protocol1_16_2To1_16_1, arg_0));
        blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
    }

    private static void lambda$register$1(Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1, PacketWrapper packetWrapper) throws Exception {
        BlockChangeRecord[] blockChangeRecordArray;
        packetWrapper.cancel();
        int n = packetWrapper.read(Type.INT);
        int n2 = packetWrapper.read(Type.INT);
        long l = 0L;
        l |= ((long)n & 0x3FFFFFL) << 42;
        l |= ((long)n2 & 0x3FFFFFL) << 20;
        List[] listArray = new List[16];
        for (BlockChangeRecord blockChangeRecord : blockChangeRecordArray = packetWrapper.read(Type.BLOCK_CHANGE_RECORD_ARRAY)) {
            int n3 = blockChangeRecord.getY() >> 4;
            ArrayList<BlockChangeRecord1_16_2> arrayList = listArray[n3];
            if (arrayList == null) {
                listArray[n3] = arrayList = new ArrayList<BlockChangeRecord1_16_2>();
            }
            int n4 = protocol1_16_2To1_16_1.getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId());
            arrayList.add(new BlockChangeRecord1_16_2(blockChangeRecord.getSectionX(), blockChangeRecord.getSectionY(), blockChangeRecord.getSectionZ(), n4));
        }
        for (int i = 0; i < listArray.length; ++i) {
            List list = listArray[i];
            if (list == null) continue;
            PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE);
            packetWrapper2.write(Type.LONG, l | (long)i & 0xFFFFFL);
            packetWrapper2.write(Type.BOOLEAN, false);
            packetWrapper2.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, list.toArray(EMPTY_RECORDS));
            packetWrapper2.send(Protocol1_16_2To1_16_1.class);
        }
    }

    private static void lambda$register$0(Protocol1_16_2To1_16_1 protocol1_16_2To1_16_1, PacketWrapper packetWrapper) throws Exception {
        Chunk chunk = packetWrapper.read(new Chunk1_16Type());
        packetWrapper.write(new Chunk1_16_2Type(), chunk);
        for (int i = 0; i < chunk.getSections().length; ++i) {
            ChunkSection chunkSection = chunk.getSections()[i];
            if (chunkSection == null) continue;
            DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
            for (int j = 0; j < dataPalette.size(); ++j) {
                int n = protocol1_16_2To1_16_1.getMappingData().getNewBlockStateId(dataPalette.idByIndex(j));
                dataPalette.setIdByIndex(j, n);
            }
        }
    }
}

