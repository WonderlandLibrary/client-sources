/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagIO;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagType;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.IOStreamUtil;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.TrackingDataInput;
import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

final class BinaryTagReaderImpl
implements BinaryTagIO.Reader {
    private final long maxBytes;
    static final BinaryTagIO.Reader UNLIMITED = new BinaryTagReaderImpl(-1L);
    static final BinaryTagIO.Reader DEFAULT_LIMIT = new BinaryTagReaderImpl(131082L);

    BinaryTagReaderImpl(long l) {
        this.maxBytes = l;
    }

    @Override
    @NotNull
    public CompoundBinaryTag read(@NotNull Path path, @NotNull BinaryTagIO.Compression compression) throws IOException {
        try (InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);){
            CompoundBinaryTag compoundBinaryTag = this.read(inputStream, compression);
            return compoundBinaryTag;
        }
    }

    @Override
    @NotNull
    public CompoundBinaryTag read(@NotNull InputStream inputStream, @NotNull BinaryTagIO.Compression compression) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(compression.decompress(IOStreamUtil.closeShield(inputStream))));){
            CompoundBinaryTag compoundBinaryTag = this.read(dataInputStream);
            return compoundBinaryTag;
        }
    }

    @Override
    @NotNull
    public CompoundBinaryTag read(@NotNull DataInput dataInput) throws IOException {
        if (!(dataInput instanceof TrackingDataInput)) {
            dataInput = new TrackingDataInput(dataInput, this.maxBytes);
        }
        BinaryTagType<BinaryTag> binaryTagType = BinaryTagType.of(dataInput.readByte());
        BinaryTagReaderImpl.requireCompound(binaryTagType);
        dataInput.skipBytes(dataInput.readUnsignedShort());
        return BinaryTagTypes.COMPOUND.read(dataInput);
    }

    @Override
    public  @NotNull Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull Path path, @NotNull BinaryTagIO.Compression compression) throws IOException {
        try (InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);){
            Map.Entry<String, CompoundBinaryTag> entry = this.readNamed(inputStream, compression);
            return entry;
        }
    }

    @Override
    public  @NotNull Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull InputStream inputStream, @NotNull BinaryTagIO.Compression compression) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(compression.decompress(IOStreamUtil.closeShield(inputStream))));){
            Map.Entry<String, CompoundBinaryTag> entry = this.readNamed(dataInputStream);
            return entry;
        }
    }

    @Override
    public  @NotNull Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull DataInput dataInput) throws IOException {
        BinaryTagType<BinaryTag> binaryTagType = BinaryTagType.of(dataInput.readByte());
        BinaryTagReaderImpl.requireCompound(binaryTagType);
        String string = dataInput.readUTF();
        return new AbstractMap.SimpleImmutableEntry<String, CompoundBinaryTag>(string, BinaryTagTypes.COMPOUND.read(dataInput));
    }

    private static void requireCompound(BinaryTagType<? extends BinaryTag> binaryTagType) throws IOException {
        if (binaryTagType != BinaryTagTypes.COMPOUND) {
            throw new IOException(String.format("Expected root tag to be a %s, was %s", BinaryTagTypes.COMPOUND, binaryTagType));
        }
    }
}

