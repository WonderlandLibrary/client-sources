/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.MathUtil;

public final class WorldPackets {
    public static void register(Protocol1_19To1_18_2 protocol) {
        BlockRewriter<ClientboundPackets1_18> blockRewriter = new BlockRewriter<ClientboundPackets1_18>(protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_18.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_18.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_18.MULTI_BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_18.EFFECT, 1010, 2001);
        protocol.cancelClientbound(ClientboundPackets1_18.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol.registerClientbound(ClientboundPackets1_18.CHUNK_DATA, wrapper -> {
            Object tracker = protocol.getEntityRewriter().tracker(wrapper.user());
            Preconditions.checkArgument(tracker.biomesSent() != -1, "Biome count not set");
            Preconditions.checkArgument(tracker.currentWorldSectionHeight() != -1, "Section height not set");
            Chunk1_18Type chunkType = new Chunk1_18Type(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(protocol.getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
            Chunk chunk = wrapper.passthrough(chunkType);
            for (ChunkSection section : chunk.getSections()) {
                DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
                for (int i = 0; i < blockPalette.size(); ++i) {
                    int id = blockPalette.idByIndex(i);
                    blockPalette.setIdByIndex(i, protocol.getMappingData().getNewBlockStateId(id));
                }
            }
        });
        protocol.registerServerbound(ServerboundPackets1_19.SET_BEACON_EFFECT, wrapper -> {
            if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                wrapper.passthrough(Type.VAR_INT);
            } else {
                wrapper.write(Type.VAR_INT, -1);
            }
            if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                wrapper.passthrough(Type.VAR_INT);
            } else {
                wrapper.write(Type.VAR_INT, -1);
            }
        });
    }
}

