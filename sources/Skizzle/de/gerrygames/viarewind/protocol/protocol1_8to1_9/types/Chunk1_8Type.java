/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.types;

import de.gerrygames.viarewind.protocol.protocol1_8to1_9.types.ChunkSectionType1_8;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.logging.Level;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.minecraft.Environment;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk1_8;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.type.PartialType;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class Chunk1_8Type
extends PartialType<Chunk, ClientWorld> {
    private static final Type<ChunkSection> CHUNK_SECTION_TYPE = new ChunkSectionType1_8();

    public Chunk1_8Type(ClientWorld param) {
        super(param, Chunk.class);
    }

    @Override
    public Chunk read(ByteBuf input, ClientWorld world) throws Exception {
        int i;
        int i2;
        int chunkX = input.readInt();
        int chunkZ = input.readInt();
        boolean groundUp = input.readByte() != 0;
        int bitmask = input.readUnsignedShort();
        int dataLength = Type.VAR_INT.readPrimitive(input);
        if (bitmask == 0 && groundUp) {
            if (dataLength >= 256) {
                input.readerIndex(input.readerIndex() + 256);
            }
            return new Chunk1_8(chunkX, chunkZ);
        }
        ChunkSection[] sections = new ChunkSection[16];
        int[] biomeData = null;
        int startIndex = input.readerIndex();
        for (i2 = 0; i2 < 16; ++i2) {
            if ((bitmask & 1 << i2) == 0) continue;
            sections[i2] = (ChunkSection)CHUNK_SECTION_TYPE.read(input);
        }
        for (i2 = 0; i2 < 16; ++i2) {
            if ((bitmask & 1 << i2) == 0) continue;
            sections[i2].readBlockLight(input);
        }
        int bytesLeft = dataLength - (input.readerIndex() - startIndex);
        if (bytesLeft >= 2048) {
            for (i = 0; i < 16; ++i) {
                if ((bitmask & 1 << i) == 0) continue;
                sections[i].readSkyLight(input);
                bytesLeft -= 2048;
            }
        }
        if (bytesLeft >= 256) {
            biomeData = new int[256];
            for (i = 0; i < 256; ++i) {
                biomeData[i] = input.readByte() & 0xFF;
            }
            bytesLeft -= 256;
        }
        if (bytesLeft > 0) {
            Via.getPlatform().getLogger().log(Level.WARNING, bytesLeft + " Bytes left after reading chunks! (" + groundUp + ")");
        }
        return new Chunk1_8(chunkX, chunkZ, groundUp, bitmask, sections, biomeData, new ArrayList<CompoundTag>());
    }

    @Override
    public void write(ByteBuf output, ClientWorld world, Chunk chunk) throws Exception {
        boolean skyLight;
        int i;
        ByteBuf buf = output.alloc().buffer();
        for (i = 0; i < chunk.getSections().length; ++i) {
            if ((chunk.getBitmask() & 1 << i) == 0) continue;
            CHUNK_SECTION_TYPE.write(buf, chunk.getSections()[i]);
        }
        for (i = 0; i < chunk.getSections().length; ++i) {
            if ((chunk.getBitmask() & 1 << i) == 0) continue;
            chunk.getSections()[i].writeBlockLight(buf);
        }
        boolean bl = skyLight = world.getEnvironment() == Environment.NORMAL;
        if (skyLight) {
            for (int i2 = 0; i2 < chunk.getSections().length; ++i2) {
                if ((chunk.getBitmask() & 1 << i2) == 0) continue;
                chunk.getSections()[i2].writeSkyLight(buf);
            }
        }
        if (chunk.isFullChunk() && chunk.isBiomeData()) {
            for (int biome : chunk.getBiomeData()) {
                buf.writeByte((int)((byte)biome));
            }
        }
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        output.writeBoolean(chunk.isFullChunk());
        output.writeShort(chunk.getBitmask());
        Type.VAR_INT.writePrimitive(output, buf.readableBytes());
        output.writeBytes(buf, buf.readableBytes());
        buf.release();
    }
}

