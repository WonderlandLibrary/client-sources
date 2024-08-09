/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.BytesInputStream;
import io.jsonwebtoken.impl.io.CharSequenceReader;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Strings;
import java.io.ByteArrayOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.concurrent.Callable;

public class Streams {
    public static final int EOF = -1;

    public static byte[] bytes(InputStream inputStream, String string) {
        if (inputStream instanceof BytesInputStream) {
            return ((BytesInputStream)inputStream).getBytes();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8192);
        Streams.copy(inputStream, byteArrayOutputStream, new byte[8192], string);
        return byteArrayOutputStream.toByteArray();
    }

    public static InputStream of(byte[] byArray) {
        return new BytesInputStream(byArray);
    }

    public static InputStream of(CharSequence charSequence) {
        return Streams.of(Strings.utf8(charSequence));
    }

    public static Reader reader(byte[] byArray) {
        return Streams.reader(Streams.of(byArray));
    }

    public static Reader reader(InputStream inputStream) {
        return new InputStreamReader(inputStream, Strings.UTF_8);
    }

    public static Reader reader(CharSequence charSequence) {
        return new CharSequenceReader(charSequence);
    }

    public static void flush(Flushable ... flushableArray) {
        Objects.nullSafeFlush(flushableArray);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, byte[] byArray) throws IOException {
        Assert.notNull(inputStream, "inputStream cannot be null.");
        Assert.notNull(outputStream, "outputStream cannot be null.");
        Assert.notEmpty(byArray, "buffer cannot be null or empty.");
        long l = 0L;
        int n = 0;
        while (n != -1) {
            n = inputStream.read(byArray);
            if (n > 0) {
                outputStream.write(byArray, 0, n);
            }
            l += (long)n;
        }
        return l;
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, byte[] byArray, String string) {
        return Streams.run(new Callable<Long>(inputStream, outputStream, byArray){
            final InputStream val$in;
            final OutputStream val$out;
            final byte[] val$buffer;
            {
                this.val$in = inputStream;
                this.val$out = outputStream;
                this.val$buffer = byArray;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public Long call() throws IOException {
                Long l;
                try {
                    Streams.reset(this.val$in);
                    l = Streams.copy(this.val$in, this.val$out, this.val$buffer);
                } catch (Throwable throwable) {
                    Objects.nullSafeFlush(this.val$out);
                    Streams.reset(this.val$in);
                    throw throwable;
                }
                Objects.nullSafeFlush(this.val$out);
                Streams.reset(this.val$in);
                return l;
            }

            @Override
            public Object call() throws Exception {
                return this.call();
            }
        }, string);
    }

    public static void reset(InputStream inputStream) {
        if (inputStream == null) {
            return;
        }
        Callable<Object> callable = new Callable<Object>(inputStream){
            final InputStream val$in;
            {
                this.val$in = inputStream;
            }

            @Override
            public Object call() {
                try {
                    this.val$in.reset();
                } catch (Throwable throwable) {
                    // empty catch block
                }
                return null;
            }
        };
        try {
            callable.call();
        } catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static void write(OutputStream outputStream, byte[] byArray, String string) {
        Streams.write(outputStream, byArray, 0, Bytes.length(byArray), string);
    }

    public static void write(OutputStream outputStream, byte[] byArray, int n, int n2, String string) {
        if (outputStream == null || Bytes.isEmpty(byArray) || n2 <= 0) {
            return;
        }
        Streams.run(new Callable<Object>(outputStream, byArray, n, n2){
            final OutputStream val$out;
            final byte[] val$data;
            final int val$offset;
            final int val$len;
            {
                this.val$out = outputStream;
                this.val$data = byArray;
                this.val$offset = n;
                this.val$len = n2;
            }

            @Override
            public Object call() throws Exception {
                this.val$out.write(this.val$data, this.val$offset, this.val$len);
                return null;
            }
        }, string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeAndClose(OutputStream outputStream, byte[] byArray, String string) {
        try {
            Streams.write(outputStream, byArray, string);
        } catch (Throwable throwable) {
            Objects.nullSafeClose(outputStream);
            throw throwable;
        }
        Objects.nullSafeClose(outputStream);
    }

    public static <V> V run(Callable<V> callable, String string) {
        Assert.hasText(string, "IO Exception Message cannot be null or empty.");
        try {
            return callable.call();
        } catch (Throwable throwable) {
            String string2 = "IO failure: " + string;
            if (!string2.endsWith(".")) {
                string2 = string2 + ".";
            }
            string2 = string2 + " Cause: " + throwable.getMessage();
            throw new io.jsonwebtoken.io.IOException(string2, throwable);
        }
    }
}

