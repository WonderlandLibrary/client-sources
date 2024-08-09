/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagReaderImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagTypes;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.BinaryTagWriterImpl;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import org.jetbrains.annotations.NotNull;

public final class BinaryTagIO {
    private BinaryTagIO() {
    }

    @NotNull
    public static Reader unlimitedReader() {
        return BinaryTagReaderImpl.UNLIMITED;
    }

    @NotNull
    public static Reader reader() {
        return BinaryTagReaderImpl.DEFAULT_LIMIT;
    }

    @NotNull
    public static Reader reader(long l) {
        if (l <= 0L) {
            throw new IllegalArgumentException("The size limit must be greater than zero");
        }
        return new BinaryTagReaderImpl(l);
    }

    @NotNull
    public static Writer writer() {
        return BinaryTagWriterImpl.INSTANCE;
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readPath(@NotNull Path path) throws IOException {
        return BinaryTagIO.reader().read(path);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readInputStream(@NotNull InputStream inputStream) throws IOException {
        return BinaryTagIO.reader().read(inputStream);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readCompressedPath(@NotNull Path path) throws IOException {
        return BinaryTagIO.reader().read(path, Compression.GZIP);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readCompressedInputStream(@NotNull InputStream inputStream) throws IOException {
        return BinaryTagIO.reader().read(inputStream, Compression.GZIP);
    }

    @Deprecated
    @NotNull
    public static CompoundBinaryTag readDataInput(@NotNull DataInput dataInput) throws IOException {
        return BinaryTagIO.reader().read(dataInput);
    }

    @Deprecated
    public static void writePath(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull Path path) throws IOException {
        BinaryTagIO.writer().write(compoundBinaryTag, path);
    }

    @Deprecated
    public static void writeOutputStream(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull OutputStream outputStream) throws IOException {
        BinaryTagIO.writer().write(compoundBinaryTag, outputStream);
    }

    @Deprecated
    public static void writeCompressedPath(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull Path path) throws IOException {
        BinaryTagIO.writer().write(compoundBinaryTag, path, Compression.GZIP);
    }

    @Deprecated
    public static void writeCompressedOutputStream(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull OutputStream outputStream) throws IOException {
        BinaryTagIO.writer().write(compoundBinaryTag, outputStream, Compression.GZIP);
    }

    @Deprecated
    public static void writeDataOutput(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull DataOutput dataOutput) throws IOException {
        BinaryTagIO.writer().write(compoundBinaryTag, dataOutput);
    }

    static {
        BinaryTagTypes.COMPOUND.id();
    }

    public static abstract class Compression {
        public static final Compression NONE = new Compression(){

            @Override
            @NotNull
            InputStream decompress(@NotNull InputStream inputStream) {
                return inputStream;
            }

            @Override
            @NotNull
            OutputStream compress(@NotNull OutputStream outputStream) {
                return outputStream;
            }

            public String toString() {
                return "Compression.NONE";
            }
        };
        public static final Compression GZIP = new Compression(){

            @Override
            @NotNull
            InputStream decompress(@NotNull InputStream inputStream) throws IOException {
                return new GZIPInputStream(inputStream);
            }

            @Override
            @NotNull
            OutputStream compress(@NotNull OutputStream outputStream) throws IOException {
                return new GZIPOutputStream(outputStream);
            }

            public String toString() {
                return "Compression.GZIP";
            }
        };
        public static final Compression ZLIB = new Compression(){

            @Override
            @NotNull
            InputStream decompress(@NotNull InputStream inputStream) {
                return new InflaterInputStream(inputStream);
            }

            @Override
            @NotNull
            OutputStream compress(@NotNull OutputStream outputStream) {
                return new DeflaterOutputStream(outputStream);
            }

            public String toString() {
                return "Compression.ZLIB";
            }
        };

        @NotNull
        abstract InputStream decompress(@NotNull InputStream var1) throws IOException;

        @NotNull
        abstract OutputStream compress(@NotNull OutputStream var1) throws IOException;
    }

    public static interface Writer {
        default public void write(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull Path path) throws IOException {
            this.write(compoundBinaryTag, path, Compression.NONE);
        }

        public void write(@NotNull CompoundBinaryTag var1, @NotNull Path var2, @NotNull Compression var3) throws IOException;

        default public void write(@NotNull CompoundBinaryTag compoundBinaryTag, @NotNull OutputStream outputStream) throws IOException {
            this.write(compoundBinaryTag, outputStream, Compression.NONE);
        }

        public void write(@NotNull CompoundBinaryTag var1, @NotNull OutputStream var2, @NotNull Compression var3) throws IOException;

        public void write(@NotNull CompoundBinaryTag var1, @NotNull DataOutput var2) throws IOException;

        default public void writeNamed( @NotNull Map.Entry<String, CompoundBinaryTag> entry, @NotNull Path path) throws IOException {
            this.writeNamed(entry, path, Compression.NONE);
        }

        public void writeNamed( @NotNull Map.Entry<String, CompoundBinaryTag> var1, @NotNull Path var2, @NotNull Compression var3) throws IOException;

        default public void writeNamed( @NotNull Map.Entry<String, CompoundBinaryTag> entry, @NotNull OutputStream outputStream) throws IOException {
            this.writeNamed(entry, outputStream, Compression.NONE);
        }

        public void writeNamed( @NotNull Map.Entry<String, CompoundBinaryTag> var1, @NotNull OutputStream var2, @NotNull Compression var3) throws IOException;

        public void writeNamed( @NotNull Map.Entry<String, CompoundBinaryTag> var1, @NotNull DataOutput var2) throws IOException;
    }

    public static interface Reader {
        @NotNull
        default public CompoundBinaryTag read(@NotNull Path path) throws IOException {
            return this.read(path, Compression.NONE);
        }

        @NotNull
        public CompoundBinaryTag read(@NotNull Path var1, @NotNull Compression var2) throws IOException;

        @NotNull
        default public CompoundBinaryTag read(@NotNull InputStream inputStream) throws IOException {
            return this.read(inputStream, Compression.NONE);
        }

        @NotNull
        public CompoundBinaryTag read(@NotNull InputStream var1, @NotNull Compression var2) throws IOException;

        @NotNull
        public CompoundBinaryTag read(@NotNull DataInput var1) throws IOException;

        default public  @NotNull Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull Path path) throws IOException {
            return this.readNamed(path, Compression.NONE);
        }

        public  @NotNull Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull Path var1, @NotNull Compression var2) throws IOException;

        default public  @NotNull Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull InputStream inputStream) throws IOException {
            return this.readNamed(inputStream, Compression.NONE);
        }

        public  @NotNull Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull InputStream var1, @NotNull Compression var2) throws IOException;

        public  @NotNull Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull DataInput var1) throws IOException;
    }
}

