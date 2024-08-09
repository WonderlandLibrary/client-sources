/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IOUtils {
    private static final int COPY_BUF_SIZE = 8024;
    private static final int SKIP_BUF_SIZE = 4096;
    private static final byte[] SKIP_BUF = new byte[4096];

    private IOUtils() {
    }

    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        return IOUtils.copy(inputStream, outputStream, 8024);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, int n) throws IOException {
        byte[] byArray = new byte[n];
        int n2 = 0;
        long l = 0L;
        while (-1 != (n2 = inputStream.read(byArray))) {
            outputStream.write(byArray, 0, n2);
            l += (long)n2;
        }
        return l;
    }

    public static long skip(InputStream inputStream, long l) throws IOException {
        int n;
        long l2;
        long l3 = l;
        while (l > 0L && (l2 = inputStream.skip(l)) != 0L) {
            l -= l2;
        }
        while (l > 0L && (n = IOUtils.readFully(inputStream, SKIP_BUF, 0, (int)Math.min(l, 4096L))) >= 1) {
            l -= (long)n;
        }
        return l3 - l;
    }

    public static int readFully(InputStream inputStream, byte[] byArray) throws IOException {
        return IOUtils.readFully(inputStream, byArray, 0, byArray.length);
    }

    public static int readFully(InputStream inputStream, byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (n2 < 0 || n < 0 || n2 + n > byArray.length) {
            throw new IndexOutOfBoundsException();
        }
        int n4 = 0;
        for (n3 = 0; n3 != n2 && (n4 = inputStream.read(byArray, n + n3, n2 - n3)) != -1; n3 += n4) {
        }
        return n3;
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException iOException) {
                // empty catch block
            }
        }
    }
}

