/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteProcessor;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSource;
import com.google.common.io.Closer;
import com.google.common.io.MultiInputStream;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;

@GwtIncompatible
public abstract class ByteSource {
    protected ByteSource() {
    }

    public CharSource asCharSource(Charset charset) {
        return new AsCharSource(this, charset);
    }

    public abstract InputStream openStream() throws IOException;

    public InputStream openBufferedStream() throws IOException {
        InputStream inputStream = this.openStream();
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream)inputStream : new BufferedInputStream(inputStream);
    }

    public ByteSource slice(long l, long l2) {
        return new SlicedByteSource(this, l, l2);
    }

    public boolean isEmpty() throws IOException {
        Optional<Long> optional = this.sizeIfKnown();
        if (optional.isPresent() && optional.get() == 0L) {
            return false;
        }
        try (Closer closer = Closer.create();){
            InputStream inputStream = closer.register(this.openStream());
            boolean bl = inputStream.read() == -1;
            return bl;
        }
    }

    @Beta
    public Optional<Long> sizeIfKnown() {
        return Optional.absent();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long size() throws IOException {
        Optional<Long> optional = this.sizeIfKnown();
        if (optional.isPresent()) {
            return optional.get();
        }
        try (Closer closer = Closer.create();){
            InputStream inputStream = closer.register(this.openStream());
            long l = this.countBySkipping(inputStream);
            return l;
        }
        closer = Closer.create();
        try {
            InputStream inputStream = closer.register(this.openStream());
            long l = ByteStreams.exhaust(inputStream);
            return l;
        } catch (Throwable throwable) {
            throw closer.rethrow(throwable);
        } finally {
            closer.close();
        }
    }

    private long countBySkipping(InputStream inputStream) throws IOException {
        long l;
        long l2 = 0L;
        while ((l = ByteStreams.skipUpTo(inputStream, Integer.MAX_VALUE)) > 0L) {
            l2 += l;
        }
        return l2;
    }

    @CanIgnoreReturnValue
    public long copyTo(OutputStream outputStream) throws IOException {
        Preconditions.checkNotNull(outputStream);
        try (Closer closer = Closer.create();){
            InputStream inputStream = closer.register(this.openStream());
            long l = ByteStreams.copy(inputStream, outputStream);
            return l;
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(ByteSink byteSink) throws IOException {
        Preconditions.checkNotNull(byteSink);
        try (Closer closer = Closer.create();){
            InputStream inputStream = closer.register(this.openStream());
            OutputStream outputStream = closer.register(byteSink.openStream());
            long l = ByteStreams.copy(inputStream, outputStream);
            return l;
        }
    }

    public byte[] read() throws IOException {
        try (Closer closer = Closer.create();){
            InputStream inputStream = closer.register(this.openStream());
            byte[] byArray = ByteStreams.toByteArray(inputStream);
            return byArray;
        }
    }

    @Beta
    @CanIgnoreReturnValue
    public <T> T read(ByteProcessor<T> byteProcessor) throws IOException {
        Preconditions.checkNotNull(byteProcessor);
        try (Closer closer = Closer.create();){
            InputStream inputStream = closer.register(this.openStream());
            T t = ByteStreams.readBytes(inputStream, byteProcessor);
            return t;
        }
    }

    public HashCode hash(HashFunction hashFunction) throws IOException {
        Hasher hasher = hashFunction.newHasher();
        this.copyTo(Funnels.asOutputStream(hasher));
        return hasher.hash();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean contentEquals(ByteSource byteSource) throws IOException {
        Preconditions.checkNotNull(byteSource);
        byte[] byArray = ByteStreams.createBuffer();
        byte[] byArray2 = ByteStreams.createBuffer();
        try (Closer closer = Closer.create();){
            InputStream inputStream = closer.register(this.openStream());
            InputStream inputStream2 = closer.register(byteSource.openStream());
            while (true) {
                int n;
                int n2;
                if ((n2 = ByteStreams.read(inputStream, byArray, 0, byArray.length)) != (n = ByteStreams.read(inputStream2, byArray2, 0, byArray2.length)) || !Arrays.equals(byArray, byArray2)) {
                    boolean bl = false;
                    return bl;
                }
                if (n2 != byArray.length) {
                    boolean bl = true;
                    return bl;
                }
                continue;
                break;
            }
        }
    }

    public static ByteSource concat(Iterable<? extends ByteSource> iterable) {
        return new ConcatenatedByteSource(iterable);
    }

    public static ByteSource concat(Iterator<? extends ByteSource> iterator2) {
        return ByteSource.concat(ImmutableList.copyOf(iterator2));
    }

    public static ByteSource concat(ByteSource ... byteSourceArray) {
        return ByteSource.concat(ImmutableList.copyOf(byteSourceArray));
    }

    public static ByteSource wrap(byte[] byArray) {
        return new ByteArrayByteSource(byArray);
    }

    public static ByteSource empty() {
        return EmptyByteSource.INSTANCE;
    }

    private static final class ConcatenatedByteSource
    extends ByteSource {
        final Iterable<? extends ByteSource> sources;

        ConcatenatedByteSource(Iterable<? extends ByteSource> iterable) {
            this.sources = Preconditions.checkNotNull(iterable);
        }

        @Override
        public InputStream openStream() throws IOException {
            return new MultiInputStream(this.sources.iterator());
        }

        @Override
        public boolean isEmpty() throws IOException {
            for (ByteSource byteSource : this.sources) {
                if (byteSource.isEmpty()) continue;
                return true;
            }
            return false;
        }

        @Override
        public Optional<Long> sizeIfKnown() {
            long l = 0L;
            for (ByteSource byteSource : this.sources) {
                Optional<Long> optional = byteSource.sizeIfKnown();
                if (!optional.isPresent()) {
                    return Optional.absent();
                }
                l += optional.get().longValue();
            }
            return Optional.of(l);
        }

        @Override
        public long size() throws IOException {
            long l = 0L;
            for (ByteSource byteSource : this.sources) {
                l += byteSource.size();
            }
            return l;
        }

        public String toString() {
            return "ByteSource.concat(" + this.sources + ")";
        }
    }

    private static final class EmptyByteSource
    extends ByteArrayByteSource {
        static final EmptyByteSource INSTANCE = new EmptyByteSource();

        EmptyByteSource() {
            super(new byte[0]);
        }

        @Override
        public CharSource asCharSource(Charset charset) {
            Preconditions.checkNotNull(charset);
            return CharSource.empty();
        }

        @Override
        public byte[] read() {
            return this.bytes;
        }

        @Override
        public String toString() {
            return "ByteSource.empty()";
        }
    }

    private static class ByteArrayByteSource
    extends ByteSource {
        final byte[] bytes;
        final int offset;
        final int length;

        ByteArrayByteSource(byte[] byArray) {
            this(byArray, 0, byArray.length);
        }

        ByteArrayByteSource(byte[] byArray, int n, int n2) {
            this.bytes = byArray;
            this.offset = n;
            this.length = n2;
        }

        @Override
        public InputStream openStream() {
            return new ByteArrayInputStream(this.bytes, this.offset, this.length);
        }

        @Override
        public InputStream openBufferedStream() throws IOException {
            return this.openStream();
        }

        @Override
        public boolean isEmpty() {
            return this.length == 0;
        }

        @Override
        public long size() {
            return this.length;
        }

        @Override
        public Optional<Long> sizeIfKnown() {
            return Optional.of(Long.valueOf(this.length));
        }

        @Override
        public byte[] read() {
            return Arrays.copyOfRange(this.bytes, this.offset, this.offset + this.length);
        }

        @Override
        public long copyTo(OutputStream outputStream) throws IOException {
            outputStream.write(this.bytes, this.offset, this.length);
            return this.length;
        }

        @Override
        public <T> T read(ByteProcessor<T> byteProcessor) throws IOException {
            byteProcessor.processBytes(this.bytes, this.offset, this.length);
            return byteProcessor.getResult();
        }

        @Override
        public HashCode hash(HashFunction hashFunction) throws IOException {
            return hashFunction.hashBytes(this.bytes, this.offset, this.length);
        }

        @Override
        public ByteSource slice(long l, long l2) {
            Preconditions.checkArgument(l >= 0L, "offset (%s) may not be negative", l);
            Preconditions.checkArgument(l2 >= 0L, "length (%s) may not be negative", l2);
            l = Math.min(l, (long)this.length);
            l2 = Math.min(l2, (long)this.length - l);
            int n = this.offset + (int)l;
            return new ByteArrayByteSource(this.bytes, n, (int)l2);
        }

        public String toString() {
            return "ByteSource.wrap(" + Ascii.truncate(BaseEncoding.base16().encode(this.bytes, this.offset, this.length), 30, "...") + ")";
        }
    }

    private final class SlicedByteSource
    extends ByteSource {
        final long offset;
        final long length;
        final ByteSource this$0;

        SlicedByteSource(ByteSource byteSource, long l, long l2) {
            this.this$0 = byteSource;
            Preconditions.checkArgument(l >= 0L, "offset (%s) may not be negative", l);
            Preconditions.checkArgument(l2 >= 0L, "length (%s) may not be negative", l2);
            this.offset = l;
            this.length = l2;
        }

        @Override
        public InputStream openStream() throws IOException {
            return this.sliceStream(this.this$0.openStream());
        }

        @Override
        public InputStream openBufferedStream() throws IOException {
            return this.sliceStream(this.this$0.openBufferedStream());
        }

        private InputStream sliceStream(InputStream inputStream) throws IOException {
            if (this.offset > 0L) {
                long l;
                try {
                    l = ByteStreams.skipUpTo(inputStream, this.offset);
                } catch (Throwable throwable) {
                    Closer closer = Closer.create();
                    closer.register(inputStream);
                    try {
                        throw closer.rethrow(throwable);
                    } catch (Throwable throwable2) {
                        closer.close();
                        throw throwable2;
                    }
                }
                if (l < this.offset) {
                    inputStream.close();
                    return new ByteArrayInputStream(new byte[0]);
                }
            }
            return ByteStreams.limit(inputStream, this.length);
        }

        @Override
        public ByteSource slice(long l, long l2) {
            Preconditions.checkArgument(l >= 0L, "offset (%s) may not be negative", l);
            Preconditions.checkArgument(l2 >= 0L, "length (%s) may not be negative", l2);
            long l3 = this.length - l;
            return this.this$0.slice(this.offset + l, Math.min(l2, l3));
        }

        @Override
        public boolean isEmpty() throws IOException {
            return this.length == 0L || super.isEmpty();
        }

        @Override
        public Optional<Long> sizeIfKnown() {
            Optional<Long> optional = this.this$0.sizeIfKnown();
            if (optional.isPresent()) {
                long l = optional.get();
                long l2 = Math.min(this.offset, l);
                return Optional.of(Math.min(this.length, l - l2));
            }
            return Optional.absent();
        }

        public String toString() {
            return this.this$0.toString() + ".slice(" + this.offset + ", " + this.length + ")";
        }
    }

    private final class AsCharSource
    extends CharSource {
        final Charset charset;
        final ByteSource this$0;

        AsCharSource(ByteSource byteSource, Charset charset) {
            this.this$0 = byteSource;
            this.charset = Preconditions.checkNotNull(charset);
        }

        @Override
        public ByteSource asByteSource(Charset charset) {
            if (charset.equals(this.charset)) {
                return this.this$0;
            }
            return super.asByteSource(charset);
        }

        @Override
        public Reader openStream() throws IOException {
            return new InputStreamReader(this.this$0.openStream(), this.charset);
        }

        public String toString() {
            return this.this$0.toString() + ".asCharSource(" + this.charset + ")";
        }
    }
}

