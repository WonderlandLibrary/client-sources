/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.ThresholdingOutputStream;

public class DeferredFileOutputStream
extends ThresholdingOutputStream {
    private ByteArrayOutputStream memoryOutputStream;
    private OutputStream currentOutputStream;
    private File outputFile;
    private final String prefix;
    private final String suffix;
    private final File directory;
    private boolean closed = false;

    public DeferredFileOutputStream(int n, File file) {
        this(n, file, null, null, null);
    }

    public DeferredFileOutputStream(int n, String string, String string2, File file) {
        this(n, null, string, string2, file);
        if (string == null) {
            throw new IllegalArgumentException("Temporary file prefix is missing");
        }
    }

    private DeferredFileOutputStream(int n, File file, String string, String string2, File file2) {
        super(n);
        this.outputFile = file;
        this.memoryOutputStream = new ByteArrayOutputStream();
        this.currentOutputStream = this.memoryOutputStream;
        this.prefix = string;
        this.suffix = string2;
        this.directory = file2;
    }

    @Override
    protected OutputStream getStream() throws IOException {
        return this.currentOutputStream;
    }

    @Override
    protected void thresholdReached() throws IOException {
        if (this.prefix != null) {
            this.outputFile = File.createTempFile(this.prefix, this.suffix, this.directory);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(this.outputFile);
        try {
            this.memoryOutputStream.writeTo(fileOutputStream);
        } catch (IOException iOException) {
            fileOutputStream.close();
            throw iOException;
        }
        this.currentOutputStream = fileOutputStream;
        this.memoryOutputStream = null;
    }

    public boolean isInMemory() {
        return !this.isThresholdExceeded();
    }

    public byte[] getData() {
        if (this.memoryOutputStream != null) {
            return this.memoryOutputStream.toByteArray();
        }
        return null;
    }

    public File getFile() {
        return this.outputFile;
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.closed = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void writeTo(OutputStream outputStream) throws IOException {
        if (!this.closed) {
            throw new IOException("Stream not closed");
        }
        if (this.isInMemory()) {
            this.memoryOutputStream.writeTo(outputStream);
        } else {
            FileInputStream fileInputStream = new FileInputStream(this.outputFile);
            try {
                IOUtils.copy((InputStream)fileInputStream, outputStream);
            } finally {
                IOUtils.closeQuietly(fileInputStream);
            }
        }
    }
}

