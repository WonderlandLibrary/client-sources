/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_16_2to1_16_1.packets;

import java.util.ArrayList;
import java.util.List;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.minecraft.BlockChangeRecord;
import us.myles.ViaVersion.api.minecraft.BlockChangeRecord1_16_2;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.BlockRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import us.myles.ViaVersion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import us.myles.ViaVersion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import us.myles.ViaVersion.protocols.protocol1_16to1_15_2.types.Chunk1_16Type;

public class WorldPackets {
    private static final BlockChangeRecord[] EMPTY_RECORDS = new BlockChangeRecord[0];

    public static void register(final Protocol protocol) {
        BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
        protocol.registerOutgoing(ClientboundPackets1_16.CHUNK_DATA, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    Chunk chunk = wrapper.read(new Chunk1_16Type());
                    wrapper.write(new Chunk1_16_2Type(), chunk);
                    for (int s = 0; s < 16; ++s) {
                        ChunkSection section = chunk.getSections()[s];
                        if (section == null) continue;
                        for (int i = 0; i < section.getPaletteSize(); ++i) {
                            int old = section.getPaletteEntry(i);
                            section.setPaletteEntry(i, protocol.getMappingData().getNewBlockStateId(old));
                        }
                    }
                });
            }
        });
        protocol.registerOutgoing(ClientboundPackets1_16.MULTI_BLOCK_CHANGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    BlockChangeRecord[] blockChangeRecord;
                    wrapper.cancel();
                    int chunkX = wrapper.read(Type.INT);
                    int chunkZ = wrapper.read(Type.INT);
                    long chunkPosition = 0L;
                    chunkPosition |= ((long)chunkX & 0x3FFFFFL) << 42;
                    chunkPosition |= ((long)chunkZ & 0x3FFFFFL) << 20;
                    List[] sectionRecords = new List[16];
                    for (BlockChangeRecord record : blockChangeRecord = wrapper.read(Type.BLOCK_CHANGE_RECORD_ARRAY)) {
                        int chunkY = record.getY() >> 4;
                        ArrayList<BlockChangeRecord1_16_2> list = sectionRecords[chunkY];
                        if (list == null) {
                            sectionRecords[chunkY] = list = new ArrayList<BlockChangeRecord1_16_2>();
                        }
                        int blockId = protocol.getMappingData().getNewBlockStateId(record.getBlockId());
                        list.add(new BlockChangeRecord1_16_2(record.getSectionX(), record.getSectionY(), record.getSectionZ(), blockId));
                    }
                    for (int chunkY = 0; chunkY < sectionRecords.length; ++chunkY) {
                        List sectionRecord = sectionRecords[chunkY];
                        if (sectionRecord == null) continue;
                        PacketWrapper newPacket = wrapper.create(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE.ordinal());
                        newPacket.write(Type.LONG, chunkPosition | (long)chunkY & 0xFFFFFL);
                        newPacket.write(Type.BOOLEAN, false);
                        newPacket.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, sectionRecord.toArray(EMPTY_RECORDS));
                        newPacket.send(Protocol1_16_2To1_16_1.class, true, true);
                    }
                });
            }
        });
        blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
    }
}

