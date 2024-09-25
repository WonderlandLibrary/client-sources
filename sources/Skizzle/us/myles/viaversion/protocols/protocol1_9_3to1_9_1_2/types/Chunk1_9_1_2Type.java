/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.types;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.BitSet;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.minecraft.Environment;
import us.myles.ViaVersion.api.minecraft.chunks.BaseChunk;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.type.PartialType;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.minecraft.BaseChunkType;
import us.myles.ViaVersion.api.type.types.version.Types1_9;
import us.myles.ViaVersion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class Chunk1_9_1_2Type
extends PartialType<Chunk, ClientWorld> {
    public Chunk1_9_1_2Type(ClientWorld clientWorld) {
        super(clientWorld, Chunk.class);
    }

    @Override
    public Chunk read(ByteBuf input, ClientWorld world) throws Exception {
        int[] biomeData;
        int i;
        boolean replacePistons = world.getUser().getProtocolInfo().getPipeline().contains(Protocol1_10To1_9_3_4.class) && Via.getConfig().isReplacePistons();
        int replacementId = Via.getConfig().getPistonReplacementId();
        int chunkX = input.readInt();
        int chunkZ = input.readInt();
        boolean groundUp = input.readBoolean();
        int primaryBitmask = Type.VAR_INT.readPrimitive(input);
        Type.VAR_INT.readPrimitive(input);
        BitSet usedSections = new BitSet(16);
        ChunkSection[] sections = new ChunkSection[16];
        for (i = 0; i < 16; ++i) {
            if ((primaryBitmask & 1 << i) == 0) continue;
            usedSections.set(i);
        }
        for (i = 0; i < 16; ++i) {
            ChunkSection section;
            if (!usedSections.get(i)) continue;
            sections[i] = section = (ChunkSection)Types1_9.CHUNK_SECTION.read(input);
            section.readBlockLight(input);
            if (world.getEnvironment() == Environment.NORMAL) {
                section.readSkyLight(input);
            }
            if (!replacePistons) continue;
            section.replacePaletteEntry(36, replacementId);
        }
        int[] arrn = biomeData = groundUp ? new int[256] : null;
        if (groundUp) {
            for (int i2 = 0; i2 < 256; ++i2) {
                biomeData[i2] = input.readByte() & 0xFF;
            }
        }
        return new BaseChunk(chunkX, chunkZ, groundUp, false, primaryBitmask, sections, biomeData, new ArrayList<CompoundTag>());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ByteBuf output, ClientWorld world, Chunk chunk) throws Exception {
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        output.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
        ByteBuf buf = output.alloc().buffer();
        try {
            for (int i = 0; i < 16; ++i) {
                ChunkSection section = chunk.getSections()[i];
                if (section == null) continue;
                Types1_9.CHUNK_SECTION.write(buf, section);
                section.writeBlockLight(buf);
                if (!section.hasSkyLight()) continue;
                section.writeSkyLight(buf);
            }
            buf.readerIndex(0);
            Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 256 : 0));
            output.writeBytes(buf);
        }
        finally {
            buf.release();
        }
        if (chunk.isBiomeData()) {
            for (int biome : chunk.getBiomeData()) {
                output.writeByte((int)((byte)biome));
            }
        }
    }

    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }
}

