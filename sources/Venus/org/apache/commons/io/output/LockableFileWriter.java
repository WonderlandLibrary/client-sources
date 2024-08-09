/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class LockableFileWriter
extends Writer {
    private static final String LCK = ".lck";
    private final Writer out;
    private final File lockFile;

    public LockableFileWriter(String string) throws IOException {
        this(string, false, null);
    }

    public LockableFileWriter(String string, boolean bl) throws IOException {
        this(string, bl, null);
    }

    public LockableFileWriter(String string, boolean bl, String string2) throws IOException {
        this(new File(string), bl, string2);
    }

    public LockableFileWriter(File file) throws IOException {
        this(file, false, null);
    }

    public LockableFileWriter(File file, boolean bl) throws IOException {
        this(file, bl, null);
    }

    @Deprecated
    public LockableFileWriter(File file, boolean bl, String string) throws IOException {
        this(file, Charset.defaultCharset(), bl, string);
    }

    public LockableFileWriter(File file, Charset charset) throws IOException {
        this(file, charset, false, null);
    }

    public LockableFileWriter(File file, String string) throws IOException {
        this(file, string, false, null);
    }

    public LockableFileWriter(File file, Charset charset, boolean bl, String string) throws IOException {
        file = file.getAbsoluteFile();
        if (file.getParentFile() != null) {
            FileUtils.forceMkdir(file.getParentFile());
        }
        if (file.isDirectory()) {
            throw new IOException("File specified is a directory");
        }
        if (string == null) {
            string = System.getProperty("java.io.tmpdir");
        }
        File file2 = new File(string);
        FileUtils.forceMkdir(file2);
        this.testLockDir(file2);
        this.lockFile = new File(file2, file.getName() + LCK);
        this.createLock();
        this.out = this.initWriter(file, charset, bl);
    }

    public LockableFileWriter(File file, String string, boolean bl, String string2) throws IOException {
        this(file, Charsets.toCharset(string), bl, string2);
    }

    private void testLockDir(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("Could not find lockDir: " + file.getAbsolutePath());
        }
        if (!file.canWrite()) {
            throw new IOException("Could not write to lockDir: " + file.getAbsolutePath());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void createLock() throws IOException {
        Class<LockableFileWriter> clazz = LockableFileWriter.class;
        synchronized (LockableFileWriter.class) {
            if (!this.lockFile.createNewFile()) {
                throw new IOException("Can't write file, lock " + this.lockFile.getAbsolutePath() + " exists");
            }
            this.lockFile.deleteOnExit();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    private Writer initWriter(File file, Charset charset, boolean bl) throws IOException {
        boolean bl2 = file.exists();
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            fileOutputStream = new FileOutputStream(file.getAbsolutePath(), bl);
            outputStreamWriter = new OutputStreamWriter((OutputStream)fileOutputStream, Charsets.toCharset(charset));
        } catch (IOException iOException) {
            IOUtils.closeQuietly(outputStreamWriter);
            IOUtils.closeQuietly(fileOutputStream);
            FileUtils.deleteQuietly(this.lockFile);
            if (!bl2) {
                FileUtils.deleteQuietly(file);
            }
            throw iOException;
        } catch (RuntimeException runtimeException) {
            IOUtils.closeQuietly(outputStreamWriter);
            IOUtils.closeQuietly(fileOutputStream);
            FileUtils.deleteQuietly(this.lockFile);
            if (!bl2) {
                FileUtils.deleteQuietly(file);
            }
            throw runtimeException;
        }
        return outputStreamWriter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        try {
            this.out.close();
        } finally {
            this.lockFile.delete();
        }
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
    }

    @Override
    public void write(char[] cArray) throws IOException {
        this.out.write(cArray);
    }

    @Override
    public void write(char[] cArray, int n, int n2) throws IOException {
        this.out.write(cArray, n, n2);
    }

    @Override
    public void write(String string) throws IOException {
        this.out.write(string);
    }

    @Override
    public void write(String string, int n, int n2) throws IOException {
        this.out.write(string, n, n2);
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }
}

