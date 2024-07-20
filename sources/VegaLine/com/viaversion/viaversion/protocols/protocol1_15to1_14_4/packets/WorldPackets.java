/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public final class WorldPackets {
    public static void register(final Protocol1_15To1_14_4 protocol) {
        BlockRewriter<ClientboundPackets1_14_4> blockRewriter = new BlockRewriter<ClientboundPackets1_14_4>(protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_14_4.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_14_4.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_14_4.MULTI_BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_14_4.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol.registerClientbound(ClientboundPackets1_14_4.CHUNK_DATA, wrapper -> {
            Chunk chunk = wrapper.read(new Chunk1_14Type());
            wrapper.write(new Chunk1_15Type(), chunk);
            if (chunk.isFullChunk()) {
                int[] biomeData = chunk.getBiomeData();
                int[] newBiomeData = new int[1024];
                if (biomeData != null) {
                    int i;
                    for (i = 0; i < 4; ++i) {
                        for (int j = 0; j < 4; ++j) {
                            int x = (j << 2) + 2;
                            int z = (i << 2) + 2;
                            int oldIndex = z << 4 | x;
                            newBiomeData[i << 2 | j] = biomeData[oldIndex];
                        }
                    }
                    for (i = 1; i < 64; ++i) {
                        System.arraycopy(newBiomeData, 0, newBiomeData, i * 16, 16);
                    }
                }
                chunk.setBiomeData(newBiomeData);
            }
            for (int s = 0; s < chunk.getSections().length; ++s) {
                ChunkSection section = chunk.getSections()[s];
                if (section == null) continue;
                DataPalette palette = section.palette(PaletteType.BLOCKS);
                for (int i = 0; i < palette.size(); ++i) {
                    int mappedBlockStateId = protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
                    palette.setIdByIndex(i, mappedBlockStateId);
                }
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_14_4.EFFECT, 1010, 2001);
        protocol.registerClientbound(ClientboundPackets1_14_4.SPAWN_PARTICLE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map((Type)Type.FLOAT, Type.DOUBLE);
                this.map((Type)Type.FLOAT, Type.DOUBLE);
                this.map((Type)Type.FLOAT, Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Type.INT, 0);
                    if (id == 3 || id == 23) {
                        int data = wrapper.passthrough(Type.VAR_INT);
                        wrapper.set(Type.VAR_INT, 0, protocol.getMappingData().getNewBlockStateId(data));
                    } else if (id == 32) {
                        protocol.getItemRewriter().handleItemToClient(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                    }
                });
            }
        });
    }
}

