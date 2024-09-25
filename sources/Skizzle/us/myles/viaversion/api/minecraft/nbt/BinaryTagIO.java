/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package us.myles.ViaVersion.api.minecraft.nbt;

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
import org.jetbrains.annotations.NotNull;
import us.myles.ViaVersion.api.minecraft.nbt.CharBuffer;
import us.myles.ViaVersion.api.minecraft.nbt.StringTagParseException;
import us.myles.ViaVersion.api.minecraft.nbt.TagStringReader;
import us.myles.ViaVersion.api.minecraft.nbt.TagStringWriter;
import us.myles.viaversion.libs.opennbt.tag.TagRegistry;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public final class BinaryTagIO {
    private BinaryTagIO() {
    }

    @NotNull
    public static CompoundTag readPath(@NotNull Path path) throws IOException {
        return BinaryTagIO.readInputStream(Files.newInputStream(path, new OpenOption[0]));
    }

    @NotNull
    public static CompoundTag readInputStream(@NotNull InputStream input) throws IOException {
        try (DataInputStream dis = new DataInputStream(input);){
            CompoundTag compoundTag = BinaryTagIO.readDataInput(dis);
            return compoundTag;
        }
    }

    @NotNull
    public static CompoundTag readCompressedPath(@NotNull Path path) throws IOException {
        return BinaryTagIO.readCompressedInputStream(Files.newInputStream(path, new OpenOption[0]));
    }

    @NotNull
    public static CompoundTag readCompressedInputStream(@NotNull InputStream input) throws IOException {
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new GZIPInputStream(input)));){
            CompoundTag compoundTag = BinaryTagIO.readDataInput(dis);
            return compoundTag;
        }
    }

    @NotNull
    public static CompoundTag readDataInput(@NotNull DataInput input) throws IOException {
        byte type = input.readByte();
        if (type != TagRegistry.getIdFor(CompoundTag.class)) {
            throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", type));
        }
        input.skipBytes(input.readUnsignedShort());
        CompoundTag compoundTag = new CompoundTag("");
        compoundTag.read(input);
        return compoundTag;
    }

    public static void writePath(@NotNull CompoundTag tag, @NotNull Path path) throws IOException {
        BinaryTagIO.writeOutputStream(tag, Files.newOutputStream(path, new OpenOption[0]));
    }

    public static void writeOutputStream(@NotNull CompoundTag tag, @NotNull OutputStream output) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(output);){
            BinaryTagIO.writeDataOutput(tag, dos);
        }
    }

    public static void writeCompressedPath(@NotNull CompoundTag tag, @NotNull Path path) throws IOException {
        BinaryTagIO.writeCompressedOutputStream(tag, Files.newOutputStream(path, new OpenOption[0]));
    }

    public static void writeCompressedOutputStream(@NotNull CompoundTag tag, @NotNull OutputStream output) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(output));){
            BinaryTagIO.writeDataOutput(tag, dos);
        }
    }

    public static void writeDataOutput(@NotNull CompoundTag tag, @NotNull DataOutput output) throws IOException {
        output.writeByte(TagRegistry.getIdFor(CompoundTag.class));
        output.writeUTF("");
        tag.write(output);
    }

    @NotNull
    public static CompoundTag readString(@NotNull String input) throws IOException {
        try {
            CharBuffer buffer = new CharBuffer(input);
            TagStringReader parser = new TagStringReader(buffer);
            CompoundTag tag = parser.compound();
            if (buffer.skipWhitespace().hasMore()) {
                throw new IOException("Document had trailing content after first CompoundTag");
            }
            return tag;
        }
        catch (StringTagParseException ex) {
            throw new IOException(ex);
        }
    }

    @NotNull
    public static String writeString(@NotNull CompoundTag tag) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (TagStringWriter emit = new TagStringWriter(sb);){
            emit.writeTag(tag);
        }
        return sb.toString();
    }
}

