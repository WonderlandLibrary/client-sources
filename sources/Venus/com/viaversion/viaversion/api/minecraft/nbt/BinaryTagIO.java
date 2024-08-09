/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft.nbt;

import com.viaversion.viaversion.api.minecraft.nbt.CharBuffer;
import com.viaversion.viaversion.api.minecraft.nbt.StringTagParseException;
import com.viaversion.viaversion.api.minecraft.nbt.TagStringReader;
import com.viaversion.viaversion.api.minecraft.nbt.TagStringWriter;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class BinaryTagIO {
    private BinaryTagIO() {
    }

    public static @NonNull CompoundTag readPath(@NonNull Path path) throws IOException {
        return BinaryTagIO.readInputStream(Files.newInputStream(path, new OpenOption[0]));
    }

    public static @NonNull CompoundTag readInputStream(@NonNull InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream);){
            CompoundTag compoundTag = BinaryTagIO.readDataInput(dataInputStream);
            return compoundTag;
        }
    }

    public static @NonNull CompoundTag readCompressedPath(@NonNull Path path) throws IOException {
        return BinaryTagIO.readCompressedInputStream(Files.newInputStream(path, new OpenOption[0]));
    }

    public static @NonNull CompoundTag readCompressedInputStream(@NonNull InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)));){
            CompoundTag compoundTag = BinaryTagIO.readDataInput(dataInputStream);
            return compoundTag;
        }
    }

    public static @NonNull CompoundTag readDataInput(@NonNull DataInput dataInput) throws IOException {
        byte by = dataInput.readByte();
        if (by != 10) {
            throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", by));
        }
        dataInput.skipBytes(dataInput.readUnsignedShort());
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.read(dataInput);
        return compoundTag;
    }

    public static void writePath(@NonNull CompoundTag compoundTag, @NonNull Path path) throws IOException {
        BinaryTagIO.writeOutputStream(compoundTag, Files.newOutputStream(path, new OpenOption[0]));
    }

    public static void writeOutputStream(@NonNull CompoundTag compoundTag, @NonNull OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream);){
            BinaryTagIO.writeDataOutput(compoundTag, dataOutputStream);
        }
    }

    public static void writeCompressedPath(@NonNull CompoundTag compoundTag, @NonNull Path path) throws IOException {
        BinaryTagIO.writeCompressedOutputStream(compoundTag, Files.newOutputStream(path, new OpenOption[0]));
    }

    public static void writeCompressedOutputStream(@NonNull CompoundTag compoundTag, @NonNull OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(outputStream));){
            BinaryTagIO.writeDataOutput(compoundTag, dataOutputStream);
        }
    }

    public static void writeDataOutput(@NonNull CompoundTag compoundTag, @NonNull DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(10);
        dataOutput.writeUTF("");
        compoundTag.write(dataOutput);
    }

    public static @NonNull CompoundTag readString(@NonNull String string) throws IOException {
        try {
            CharBuffer charBuffer = new CharBuffer(string);
            TagStringReader tagStringReader = new TagStringReader(charBuffer);
            CompoundTag compoundTag = tagStringReader.compound();
            if (charBuffer.skipWhitespace().hasMore()) {
                throw new IOException("Document had trailing content after first CompoundTag");
            }
            return compoundTag;
        } catch (StringTagParseException stringTagParseException) {
            throw new IOException(stringTagParseException);
        }
    }

    public static @NonNull String writeString(@NonNull CompoundTag compoundTag) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (TagStringWriter tagStringWriter = new TagStringWriter(stringBuilder);){
            tagStringWriter.writeTag(compoundTag);
        }
        return stringBuilder.toString();
    }
}

