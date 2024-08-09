/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public final class WorldPackets {
    public static void register(Protocol1_15To1_14_4 protocol1_15To1_14_4) {
        BlockRewriter<ClientboundPackets1_14_4> blockRewriter = new BlockRewriter<ClientboundPackets1_14_4>(protocol1_15To1_14_4, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_14_4.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_14_4.BLOCK_CHANGE);
        blockRewriter.registerMultiBlockChange(ClientboundPackets1_14_4.MULTI_BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_14_4.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol1_15To1_14_4.registerClientbound(ClientboundPackets1_14_4.CHUNK_DATA, arg_0 -> WorldPackets.lambda$register$0(protocol1_15To1_14_4, arg_0));
        blockRewriter.registerEffect(ClientboundPackets1_14_4.EFFECT, 1010, 2001);
        protocol1_15To1_14_4.registerClientbound(ClientboundPackets1_14_4.SPAWN_PARTICLE, new PacketHandlers(protocol1_15To1_14_4){
            final Protocol1_15To1_14_4 val$protocol;
            {
                this.val$protocol = protocol1_15To1_14_4;
            }

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
                this.handler(arg_0 -> 1.lambda$register$0(this.val$protocol, arg_0));
            }

            private static void lambda$register$0(Protocol1_15To1_14_4 protocol1_15To1_14_4, PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                if (n == 3 || n == 23) {
                    int n2 = packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.set(Type.VAR_INT, 0, protocol1_15To1_14_4.getMappingData().getNewBlockStateId(n2));
                } else if (n == 32) {
                    protocol1_15To1_14_4.getItemRewriter().handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                }
            }
        });
    }

    private static void lambda$register$0(Protocol1_15To1_14_4 protocol1_15To1_14_4, PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2;
        Object object;
        Chunk chunk = packetWrapper.read(new Chunk1_14Type());
        packetWrapper.write(new Chunk1_15Type(), chunk);
        if (chunk.isFullChunk()) {
            int[] nArray = chunk.getBiomeData();
            object = new int[1024];
            if (nArray != null) {
                int n3;
                for (n3 = 0; n3 < 4; ++n3) {
                    for (n2 = 0; n2 < 4; ++n2) {
                        n = (n2 << 2) + 2;
                        int n4 = (n3 << 2) + 2;
                        int n5 = n4 << 4 | n;
                        object[n3 << 2 | n2] = nArray[n5];
                    }
                }
                for (n3 = 1; n3 < 64; ++n3) {
                    System.arraycopy(object, 0, object, n3 * 16, 16);
                }
            }
            chunk.setBiomeData((int[])object);
        }
        for (int i = 0; i < chunk.getSections().length; ++i) {
            object = chunk.getSections()[i];
            if (object == null) continue;
            DataPalette dataPalette = object.palette(PaletteType.BLOCKS);
            for (n2 = 0; n2 < dataPalette.size(); ++n2) {
                n = protocol1_15To1_14_4.getMappingData().getNewBlockStateId(dataPalette.idByIndex(n2));
                dataPalette.setIdByIndex(n2, n);
            }
        }
    }
}

