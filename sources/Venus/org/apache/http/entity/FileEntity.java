/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.Args;

public class FileEntity
extends AbstractHttpEntity
implements Cloneable {
    protected final File file;

    @Deprecated
    public FileEntity(File file, String string) {
        this.file = Args.notNull(file, "File");
        this.setContentType(string);
    }

    public FileEntity(File file, ContentType contentType) {
        this.file = Args.notNull(file, "File");
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }
    }

    public FileEntity(File file) {
        this.file = Args.notNull(file, "File");
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public long getContentLength() {
        return this.file.length();
    }

    @Override
    public InputStream getContent() throws IOException {
        return new FileInputStream(this.file);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "Output stream");
        FileInputStream fileInputStream = new FileInputStream(this.file);
        try {
            int n;
            byte[] byArray = new byte[4096];
            while ((n = ((InputStream)fileInputStream).read(byArray)) != -1) {
                outputStream.write(byArray, 0, n);
            }
            outputStream.flush();
        } finally {
            ((InputStream)fileInputStream).close();
        }
    }

    @Override
    public boolean isStreaming() {
        return true;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

