/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public class WorldPackets {
    public static void register(final Protocol1_13_1To1_13 protocol) {
        BlockRewriter<ClientboundPackets1_13> blockRewriter = new BlockRewriter<ClientboundPackets1_13>(protocol, Type.POSITION);
        protocol.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, wrapper -> {
            ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
            Chunk chunk = wrapper.passthrough(new Chunk1_13Type(clientWorld));
            for (ChunkSection section : chunk.getSections()) {
                if (section == null) continue;
                DataPalette palette = section.palette(PaletteType.BLOCKS);
                for (int i = 0; i < palette.size(); ++i) {
                    int mappedBlockStateId = protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
                    palette.setIdByIndex(i, mappedBlockStateId);
                }
            }
        });
        blockRewriter.registerBlockAction(ClientboundPackets1_13.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_13.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
        protocol.registerClientbound(ClientboundPackets1_13.EFFECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Type.INT, 0);
                    if (id == 2000) {
                        int data = wrapper.get(Type.INT, 1);
                        switch (data) {
                            case 1: {
                                wrapper.set(Type.INT, 1, 2);
                                break;
                            }
                            case 0: 
                            case 3: 
                            case 6: {
                                wrapper.set(Type.INT, 1, 4);
                                break;
                            }
                            case 2: 
                            case 5: 
                            case 8: {
                                wrapper.set(Type.INT, 1, 5);
                                break;
                            }
                            case 7: {
                                wrapper.set(Type.INT, 1, 3);
                                break;
                            }
                            default: {
                                wrapper.set(Type.INT, 1, 0);
                                break;
                            }
                        }
                    } else if (id == 1010) {
                        wrapper.set(Type.INT, 1, protocol.getMappingData().getNewItemId(wrapper.get(Type.INT, 1)));
                    } else if (id == 2001) {
                        wrapper.set(Type.INT, 1, protocol.getMappingData().getNewBlockStateId(wrapper.get(Type.INT, 1)));
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
                    int dimensionId = wrapper.get(Type.INT, 1);
                    clientChunks.setEnvironment(dimensionId);
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_13.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(wrapper -> {
                    ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
                    int dimensionId = wrapper.get(Type.INT, 0);
                    clientWorld.setEnvironment(dimensionId);
                });
            }
        });
    }
}

