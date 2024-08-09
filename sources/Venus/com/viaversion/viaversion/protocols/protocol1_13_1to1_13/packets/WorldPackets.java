/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public class WorldPackets {
    public static void register(Protocol1_13_1To1_13 protocol1_13_1To1_13) {
        BlockRewriter<ClientboundPackets1_13> blockRewriter = new BlockRewriter<ClientboundPackets1_13>(protocol1_13_1To1_13, Type.POSITION);
        protocol1_13_1To1_13.registerClientbound(ClientboundPackets1_13.CHUNK_DATA, arg_0 -> WorldPackets.lambda$register$0(protocol1_13_1To1_13, arg_0));
        blockRewriter.registerBlockAction(ClientboundPackets1_13.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_13.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_13.MULTI_BLOCK_CHANGE);
        protocol1_13_1To1_13.registerClientbound(ClientboundPackets1_13.EFFECT, new PacketHandlers(protocol1_13_1To1_13){
            final Protocol1_13_1To1_13 val$protocol;
            {
                this.val$protocol = protocol1_13_1To1_13;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION);
                this.map(Type.INT);
                this.handler(arg_0 -> 1.lambda$register$0(this.val$protocol, arg_0));
            }

            private static void lambda$register$0(Protocol1_13_1To1_13 protocol1_13_1To1_13, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                if (n == 2000) {
                    int n2 = packetWrapper.get(Type.INT, 1);
                    switch (n2) {
                        case 1: {
                            packetWrapper.set(Type.INT, 1, 2);
                            break;
                        }
                        case 0: 
                        case 3: 
                        case 6: {
                            packetWrapper.set(Type.INT, 1, 4);
                            break;
                        }
                        case 2: 
                        case 5: 
                        case 8: {
                            packetWrapper.set(Type.INT, 1, 5);
                            break;
                        }
                        case 7: {
                            packetWrapper.set(Type.INT, 1, 3);
                            break;
                        }
                        default: {
                            packetWrapper.set(Type.INT, 1, 0);
                            break;
                        }
                    }
                } else if (n == 1010) {
                    packetWrapper.set(Type.INT, 1, protocol1_13_1To1_13.getMappingData().getNewItemId(packetWrapper.get(Type.INT, 1)));
                } else if (n == 2001) {
                    packetWrapper.set(Type.INT, 1, protocol1_13_1To1_13.getMappingData().getNewBlockStateId(packetWrapper.get(Type.INT, 1)));
                }
            }
        });
        protocol1_13_1To1_13.registerClientbound(ClientboundPackets1_13.JOIN_GAME, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.INT);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 1);
                clientWorld.setEnvironment(n);
            }
        });
        protocol1_13_1To1_13.registerClientbound(ClientboundPackets1_13.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
                int n = packetWrapper.get(Type.INT, 0);
                clientWorld.setEnvironment(n);
            }
        });
    }

    private static void lambda$register$0(Protocol1_13_1To1_13 protocol1_13_1To1_13, PacketWrapper packetWrapper) throws Exception {
        ClientWorld clientWorld = packetWrapper.user().get(ClientWorld.class);
        Chunk chunk = packetWrapper.passthrough(new Chunk1_13Type(clientWorld));
        for (ChunkSection chunkSection : chunk.getSections()) {
            if (chunkSection == null) continue;
            DataPalette dataPalette = chunkSection.palette(PaletteType.BLOCKS);
            for (int i = 0; i < dataPalette.size(); ++i) {
                int n = protocol1_13_1To1_13.getMappingData().getNewBlockStateId(dataPalette.idByIndex(i));
                dataPalette.setIdByIndex(i, n);
            }
        }
    }
}

