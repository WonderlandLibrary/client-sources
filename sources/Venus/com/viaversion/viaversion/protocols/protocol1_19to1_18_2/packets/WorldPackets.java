/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.MathUtil;

public final class WorldPackets {
    public static void register(Protocol1_19To1_18_2 protocol1_19To1_18_2) {
        BlockRewriter<ClientboundPackets1_18> blockRewriter = new BlockRewriter<ClientboundPackets1_18>(protocol1_19To1_18_2, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_18.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_18.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_18.MULTI_BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_18.EFFECT, 1010, 2001);
        protocol1_19To1_18_2.cancelClientbound(ClientboundPackets1_18.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol1_19To1_18_2.registerClientbound(ClientboundPackets1_18.CHUNK_DATA, arg_0 -> WorldPackets.lambda$register$0(protocol1_19To1_18_2, arg_0));
        protocol1_19To1_18_2.registerServerbound(ServerboundPackets1_19.SET_BEACON_EFFECT, WorldPackets::lambda$register$1);
    }

    private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
        if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
            packetWrapper.passthrough(Type.VAR_INT);
        } else {
            packetWrapper.write(Type.VAR_INT, -1);
        }
        if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
            packetWrapper.passthrough(Type.VAR_INT);
        } else {
            packetWrapper.write(Type.VAR_INT, -1);
        }
    }

    private static void lambda$register$0(Protocol1_19To1_18_2 protocol1_19To1_18_2, PacketWrapper packetWrapper) throws Exception {
        Object e = protocol1_19To1_18_2.getEntityRewriter().tracker(packetWrapper.user());
        Preconditions.checkArgument(e.biomesSent() != 0, "Biome count not set");
        Preconditions.checkArgument(e.currentWorldSectionHeight() != 0, "Section height not set");
        Chunk1_18Type chunk1_18Type = new Chunk1_18Type(e.currentWorldSectionHeight(), MathUtil.ceilLog2(protocol1_19To1_18_2.getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(e.biomesSent()));
        Chunk chunk = packetWrapper.passthrough(chunk1_18Type);
        for (ChunkSection chunkSection : chunk.getSections()) {
            DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
            for (int i = 0; i < dataPalette.size(); ++i) {
                int n = dataPalette.idByIndex(i);
                dataPalette.setIdByIndex(i, protocol1_19To1_18_2.getMappingData().getNewBlockStateId(n));
            }
        }
    }
}

