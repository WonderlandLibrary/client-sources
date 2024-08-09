/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public class WorldPackets1_13_1 {
    public static void register(Protocol1_13To1_13_1 protocol1_13To1_13_1) {
        BlockRewriter<ClientboundPackets1_13> blockRewriter = new BlockRewriter<ClientboundPackets1_13>(protocol1_13To1_13_1, Type.POSITION);
        protocol1_13To1_13_1.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, arg_0 -> WorldPackets1_13_1.lambda$register$0(protocol1_13To1_13_1, arg_0));
        blockRewriter.registerBlockAction(ClientboundPackets1_13.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_13.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
        protocol1_13To1_13_1.registerClientbound(ClientboundPackets1_13.EFFECT, new PacketHandlers(protocol1_13To1_13_1){
            final Protocol1_13To1_13_1 val$protocol;
            {
                this.val$protocol = protocol1_13To1_13_1;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(arg_0 -> 1.lambda$register$0(this.val$protocol, arg_0));
            }

            private static void lambda$register$0(Protocol1_13To1_13_1 protocol1_13To1_13_1, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                int n2 = packetWrapper.get(Type.INT, 1);
                if (n == 1010) {
                    packetWrapper.set(Type.INT, 1, protocol1_13To1_13_1.getMappingData().getNewItemId(n2));
                } else if (n == 2001) {
                    packetWrapper.set(Type.INT, 1, protocol1_13To1_13_1.getMappingData().getNewBlockStateId(n2));
                } else if (n == 2000) {
                    switch (n2) {
                        case 0: 
                        case 1: {
                            Position position = packetWrapper.get(Type.POSITION, 0);
                            BlockFace blockFace = n2 == 0 ? BlockFace.BOTTOM : BlockFace.TOP;
                            packetWrapper.set(Type.POSITION, 0, position.getRelative(blockFace));
                            packetWrapper.set(Type.INT, 1, 4);
                            break;
                        }
                        case 2: {
                            packetWrapper.set(Type.INT, 1, 1);
                            break;
                        }
                        case 3: {
                            packetWrapper.set(Type.INT, 1, 7);
                            break;
                        }
                        case 4: {
                            packetWrapper.set(Type.INT, 1, 3);
                            break;
                        }
                        case 5: {
                            packetWrapper.set(Type.INT, 1, 5);
                        }
                    }
                }
            }
        });
    }

    private static void lambda$register$0(Protocol1_13To1_13_1 protocol1_13To1_13_1, PacketWrapper packetWrapper) throws Exception {
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk chunk = packetWrapper.passthrough(new Chunk1_13Type(clientWorld));
        for (ChunkSection chunkSection : chunk.getSections()) {
            if (chunkSection == null) continue;
            DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
            for (int i = 0; i < dataPalette.size(); ++i) {
                int n = protocol1_13To1_13_1.getMappingData().getNewBlockStateId(dataPalette.idByIndex(i));
                dataPalette.setIdByIndex(i, n);
            }
        }
    }
}

