/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.chunks;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.items.ReplacementRegistry1_7_6_10to1_8;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.CustomByteType;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;

public class ChunkPacketTransformer {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static byte[] transformChunkData(byte[] data, int primaryBitMask, boolean skyLight, boolean groundUp) throws Exception {
        ByteBuf inputData = Unpooled.wrappedBuffer(data);
        ByteBuf finalBuf = ByteBufAllocator.DEFAULT.buffer();
        try {
            int j;
            DataPalette palette;
            ChunkSection section;
            int i;
            ChunkSection[] sections = new ChunkSection[16];
            for (i = 0; i < 16; ++i) {
                if ((primaryBitMask & 1 << i) == 0) continue;
                sections[i] = (ChunkSection)Types1_8.CHUNK_SECTION.read(inputData);
            }
            for (i = 0; i < 16; ++i) {
                if ((primaryBitMask & 1 << i) == 0) continue;
                section = sections[i];
                palette = section.palette(PaletteType.BLOCKS);
                for (int k = 0; k < palette.size(); ++k) {
                    int blockData = palette.idByIndex(k);
                    blockData = ReplacementRegistry1_7_6_10to1_8.replace(blockData);
                    palette.setIdByIndex(k, blockData);
                }
            }
            for (i = 0; i < 16; ++i) {
                if ((primaryBitMask & 1 << i) == 0) continue;
                section = sections[i];
                palette = section.palette(PaletteType.BLOCKS);
                for (j = 0; j < 4096; ++j) {
                    int raw = palette.idAt(j);
                    finalBuf.writeByte(raw >> 4);
                }
            }
            for (i = 0; i < 16; ++i) {
                if ((primaryBitMask & 1 << i) == 0) continue;
                section = sections[i];
                palette = section.palette(PaletteType.BLOCKS);
                for (j = 0; j < 4096; j += 2) {
                    int meta0 = palette.idAt(j) & 0xF;
                    int meta1 = palette.idAt(j + 1) & 0xF;
                    finalBuf.writeByte(meta1 << 4 | meta0);
                }
            }
            int columnCount = Integer.bitCount(primaryBitMask);
            finalBuf.writeBytes(inputData, 2048 * columnCount);
            if (skyLight) {
                finalBuf.writeBytes(inputData, 2048 * columnCount);
            }
            if (groundUp && inputData.isReadable(256)) {
                finalBuf.writeBytes(inputData, 256);
            }
            byte[] byArray = (byte[])Type.REMAINING_BYTES.read(finalBuf);
            return byArray;
        } finally {
            finalBuf.release();
        }
    }

    private static int calcSize(int i, boolean hasSkyLight, boolean hasBiome) {
        int blocks = i * 2 * 16 * 16 * 16;
        int blockLight = i * 16 * 16 * 16 / 2;
        int skyLight = hasSkyLight ? i * 16 * 16 * 16 / 2 : 0;
        int biome = hasBiome ? 256 : 0;
        return blocks + blockLight + skyLight + biome;
    }

    public static void transformChunkBulk(PacketWrapper packetWrapper) throws Exception {
        Object customByteType;
        int i;
        boolean skyLightSent = packetWrapper.read(Type.BOOLEAN);
        int columnCount = packetWrapper.read(Type.VAR_INT);
        int[] chunkX = new int[columnCount];
        int[] chunkZ = new int[columnCount];
        int[] primaryBitMask = new int[columnCount];
        byte[][] data = new byte[columnCount][];
        for (i = 0; i < columnCount; ++i) {
            chunkX[i] = packetWrapper.read(Type.INT);
            chunkZ[i] = packetWrapper.read(Type.INT);
            primaryBitMask[i] = packetWrapper.read(Type.UNSIGNED_SHORT);
        }
        for (i = 0; i < columnCount; ++i) {
            int size = ChunkPacketTransformer.calcSize(Integer.bitCount(primaryBitMask[i]), skyLightSent, true);
            customByteType = new CustomByteType(size);
            data[i] = ChunkPacketTransformer.transformChunkData((byte[])packetWrapper.read(customByteType), primaryBitMask[i], skyLightSent, true);
        }
        ByteArrayOutputStream compressedData = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterStream = new DeflaterOutputStream(compressedData);
        customByteType = null;
        try {
            for (int i2 = 0; i2 < columnCount; ++i2) {
                deflaterStream.write(data[i2]);
            }
        } catch (Throwable throwable) {
            customByteType = throwable;
            throw throwable;
        } finally {
            if (deflaterStream != null) {
                if (customByteType != null) {
                    try {
                        deflaterStream.close();
                    } catch (Throwable throwable) {
                        ((Throwable)customByteType).addSuppressed(throwable);
                    }
                } else {
                    deflaterStream.close();
                }
            }
        }
        packetWrapper.write(Type.SHORT, (short)columnCount);
        packetWrapper.write(Type.INT, compressedData.size());
        packetWrapper.write(Type.BOOLEAN, skyLightSent);
        CustomByteType customByteType2 = new CustomByteType(compressedData.size());
        packetWrapper.write(customByteType2, compressedData.toByteArray());
        for (int i3 = 0; i3 < columnCount; ++i3) {
            packetWrapper.write(Type.INT, chunkX[i3]);
            packetWrapper.write(Type.INT, chunkZ[i3]);
            packetWrapper.write(Type.SHORT, (short)primaryBitMask[i3]);
            packetWrapper.write(Type.SHORT, (short)0);
        }
    }
}

