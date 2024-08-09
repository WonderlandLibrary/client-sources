/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

final class IOStreamUtil {
    private IOStreamUtil() {
    }

    static InputStream closeShield(InputStream inputStream) {
        return new InputStream(inputStream){
            final InputStream val$stream;
            {
                this.val$stream = inputStream;
            }

            @Override
            public int read() throws IOException {
                return this.val$stream.read();
            }

            @Override
            public int read(byte[] byArray) throws IOException {
                return this.val$stream.read(byArray);
            }

            @Override
            public int read(byte[] byArray, int n, int n2) throws IOException {
                return this.val$stream.read(byArray, n, n2);
            }
        };
    }

    static OutputStream closeShield(OutputStream outputStream) {
        return new OutputStream(outputStream){
            final OutputStream val$stream;
            {
                this.val$stream = outputStream;
            }

            @Override
            public void write(int n) throws IOException {
                this.val$stream.write(n);
            }

            @Override
            public void write(byte[] byArray) throws IOException {
                this.val$stream.write(byArray);
            }

            @Override
            public void write(byte[] byArray, int n, int n2) throws IOException {
                this.val$stream.write(byArray, n, n2);
            }
        };
    }
}

