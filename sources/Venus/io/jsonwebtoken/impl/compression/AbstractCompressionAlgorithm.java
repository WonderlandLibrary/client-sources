/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.compression;

import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionException;
import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.lang.PropagatingExceptionFunction;
import io.jsonwebtoken.io.CompressionAlgorithm;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Strings;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractCompressionAlgorithm
implements CompressionAlgorithm,
CompressionCodec {
    private final String id;
    private final Function<OutputStream, OutputStream> OS_WRAP_FN;
    private final Function<InputStream, InputStream> IS_WRAP_FN;
    private final Function<byte[], byte[]> COMPRESS_FN;
    private final Function<byte[], byte[]> DECOMPRESS_FN;

    private static <T, R> Function<T, R> propagate(CheckedFunction<T, R> checkedFunction, String string) {
        return new PropagatingExceptionFunction<T, R, CompressionException>(checkedFunction, CompressionException.class, string);
    }

    private static <T, R> Function<T, R> forCompression(CheckedFunction<T, R> checkedFunction) {
        return AbstractCompressionAlgorithm.propagate(checkedFunction, "Compression failed.");
    }

    private static <T, R> Function<T, R> forDecompression(CheckedFunction<T, R> checkedFunction) {
        return AbstractCompressionAlgorithm.propagate(checkedFunction, "Decompression failed.");
    }

    protected AbstractCompressionAlgorithm(String string) {
        this.id = Assert.hasText(Strings.clean(string), "id argument cannot be null or empty.");
        this.OS_WRAP_FN = AbstractCompressionAlgorithm.forCompression(new CheckedFunction<OutputStream, OutputStream>(this){
            final AbstractCompressionAlgorithm this$0;
            {
                this.this$0 = abstractCompressionAlgorithm;
            }

            @Override
            public OutputStream apply(OutputStream outputStream) throws Exception {
                return this.this$0.doCompress(outputStream);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((OutputStream)object);
            }
        });
        this.COMPRESS_FN = AbstractCompressionAlgorithm.forCompression(new CheckedFunction<byte[], byte[]>(this){
            final AbstractCompressionAlgorithm this$0;
            {
                this.this$0 = abstractCompressionAlgorithm;
            }

            @Override
            public byte[] apply(byte[] byArray) throws Exception {
                return AbstractCompressionAlgorithm.access$000(this.this$0, byArray);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((byte[])object);
            }
        });
        this.IS_WRAP_FN = AbstractCompressionAlgorithm.forDecompression(new CheckedFunction<InputStream, InputStream>(this){
            final AbstractCompressionAlgorithm this$0;
            {
                this.this$0 = abstractCompressionAlgorithm;
            }

            @Override
            public InputStream apply(InputStream inputStream) throws Exception {
                return this.this$0.doDecompress(inputStream);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((InputStream)object);
            }
        });
        this.DECOMPRESS_FN = AbstractCompressionAlgorithm.forDecompression(new CheckedFunction<byte[], byte[]>(this){
            final AbstractCompressionAlgorithm this$0;
            {
                this.this$0 = abstractCompressionAlgorithm;
            }

            @Override
            public byte[] apply(byte[] byArray) throws Exception {
                return this.this$0.doDecompress(byArray);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((byte[])object);
            }
        });
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getAlgorithmName() {
        return this.getId();
    }

    @Override
    public final OutputStream compress(OutputStream outputStream) throws CompressionException {
        return this.OS_WRAP_FN.apply(outputStream);
    }

    protected abstract OutputStream doCompress(OutputStream var1) throws IOException;

    @Override
    public final InputStream decompress(InputStream inputStream) throws CompressionException {
        return this.IS_WRAP_FN.apply(inputStream);
    }

    protected abstract InputStream doDecompress(InputStream var1) throws IOException;

    @Override
    public final byte[] compress(byte[] byArray) {
        if (Bytes.isEmpty(byArray)) {
            return Bytes.EMPTY;
        }
        return this.COMPRESS_FN.apply(byArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private byte[] doCompress(byte[] byArray) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        OutputStream outputStream = this.compress(byteArrayOutputStream);
        try {
            outputStream.write(byArray);
            outputStream.flush();
        } catch (Throwable throwable) {
            Objects.nullSafeClose(outputStream);
            throw throwable;
        }
        Objects.nullSafeClose(outputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public final byte[] decompress(byte[] byArray) {
        if (Bytes.isEmpty(byArray)) {
            return Bytes.EMPTY;
        }
        return this.DECOMPRESS_FN.apply(byArray);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected byte[] doDecompress(byte[] byArray) throws IOException {
        InputStream inputStream = Streams.of(byArray);
        InputStream inputStream2 = this.decompress(inputStream);
        byte[] byArray2 = new byte[512];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(byArray2.length);
        int n = 0;
        try {
            while (n != -1) {
                n = inputStream2.read(byArray2);
                if (n <= 0) continue;
                byteArrayOutputStream.write(byArray2, 0, n);
            }
        } catch (Throwable throwable) {
            Objects.nullSafeClose(inputStream2);
            throw throwable;
        }
        Objects.nullSafeClose(inputStream2);
        return byteArrayOutputStream.toByteArray();
    }

    static byte[] access$000(AbstractCompressionAlgorithm abstractCompressionAlgorithm, byte[] byArray) throws IOException {
        return abstractCompressionAlgorithm.doCompress(byArray);
    }
}

