/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.ByteSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Beta
@GwtIncompatible
public final class FileBackedOutputStream
extends OutputStream {
    private final int fileThreshold;
    private final boolean resetOnFinalize;
    private final ByteSource source;
    private OutputStream out;
    private MemoryOutput memory;
    private File file;

    @VisibleForTesting
    synchronized File getFile() {
        return this.file;
    }

    public FileBackedOutputStream(int n) {
        this(n, false);
    }

    public FileBackedOutputStream(int n, boolean bl) {
        this.fileThreshold = n;
        this.resetOnFinalize = bl;
        this.memory = new MemoryOutput(null);
        this.out = this.memory;
        this.source = bl ? new ByteSource(this){
            final FileBackedOutputStream this$0;
            {
                this.this$0 = fileBackedOutputStream;
            }

            @Override
            public InputStream openStream() throws IOException {
                return FileBackedOutputStream.access$100(this.this$0);
            }

            protected void finalize() {
                try {
                    this.this$0.reset();
                } catch (Throwable throwable) {
                    throwable.printStackTrace(System.err);
                }
            }
        } : new ByteSource(this){
            final FileBackedOutputStream this$0;
            {
                this.this$0 = fileBackedOutputStream;
            }

            @Override
            public InputStream openStream() throws IOException {
                return FileBackedOutputStream.access$100(this.this$0);
            }
        };
    }

    public ByteSource asByteSource() {
        return this.source;
    }

    private synchronized InputStream openInputStream() throws IOException {
        if (this.file != null) {
            return new FileInputStream(this.file);
        }
        return new ByteArrayInputStream(this.memory.getBuffer(), 0, this.memory.getCount());
    }

    public synchronized void reset() throws IOException {
        try {
            this.close();
        } finally {
            if (this.memory == null) {
                this.memory = new MemoryOutput(null);
            } else {
                this.memory.reset();
            }
            this.out = this.memory;
            if (this.file != null) {
                File file = this.file;
                this.file = null;
                if (!file.delete()) {
                    throw new IOException("Could not delete: " + file);
                }
            }
        }
    }

    @Override
    public synchronized void write(int n) throws IOException {
        this.update(1);
        this.out.write(n);
    }

    @Override
    public synchronized void write(byte[] byArray) throws IOException {
        this.write(byArray, 0, byArray.length);
    }

    @Override
    public synchronized void write(byte[] byArray, int n, int n2) throws IOException {
        this.update(n2);
        this.out.write(byArray, n, n2);
    }

    @Override
    public synchronized void close() throws IOException {
        this.out.close();
    }

    @Override
    public synchronized void flush() throws IOException {
        this.out.flush();
    }

    private void update(int n) throws IOException {
        if (this.file == null && this.memory.getCount() + n > this.fileThreshold) {
            File file = File.createTempFile("FileBackedOutputStream", null);
            if (this.resetOnFinalize) {
                file.deleteOnExit();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(this.memory.getBuffer(), 0, this.memory.getCount());
            fileOutputStream.flush();
            this.out = fileOutputStream;
            this.file = file;
            this.memory = null;
        }
    }

    static InputStream access$100(FileBackedOutputStream fileBackedOutputStream) throws IOException {
        return fileBackedOutputStream.openInputStream();
    }

    private static class MemoryOutput
    extends ByteArrayOutputStream {
        private MemoryOutput() {
        }

        byte[] getBuffer() {
            return this.buf;
        }

        int getCount() {
            return this.count;
        }

        MemoryOutput(1 var1_1) {
            this();
        }
    }
}

