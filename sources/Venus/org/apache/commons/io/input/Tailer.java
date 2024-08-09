/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;

public class Tailer
implements Runnable {
    private static final int DEFAULT_DELAY_MILLIS = 1000;
    private static final String RAF_MODE = "r";
    private static final int DEFAULT_BUFSIZE = 4096;
    private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
    private final byte[] inbuf;
    private final File file;
    private final Charset cset;
    private final long delayMillis;
    private final boolean end;
    private final TailerListener listener;
    private final boolean reOpen;
    private volatile boolean run = true;

    public Tailer(File file, TailerListener tailerListener) {
        this(file, tailerListener, 1000L);
    }

    public Tailer(File file, TailerListener tailerListener, long l) {
        this(file, tailerListener, l, false);
    }

    public Tailer(File file, TailerListener tailerListener, long l, boolean bl) {
        this(file, tailerListener, l, bl, 4096);
    }

    public Tailer(File file, TailerListener tailerListener, long l, boolean bl, boolean bl2) {
        this(file, tailerListener, l, bl, bl2, 4096);
    }

    public Tailer(File file, TailerListener tailerListener, long l, boolean bl, int n) {
        this(file, tailerListener, l, bl, false, n);
    }

    public Tailer(File file, TailerListener tailerListener, long l, boolean bl, boolean bl2, int n) {
        this(file, DEFAULT_CHARSET, tailerListener, l, bl, bl2, n);
    }

    public Tailer(File file, Charset charset, TailerListener tailerListener, long l, boolean bl, boolean bl2, int n) {
        this.file = file;
        this.delayMillis = l;
        this.end = bl;
        this.inbuf = new byte[n];
        this.listener = tailerListener;
        tailerListener.init(this);
        this.reOpen = bl2;
        this.cset = charset;
    }

    public static Tailer create(File file, TailerListener tailerListener, long l, boolean bl, int n) {
        return Tailer.create(file, tailerListener, l, bl, false, n);
    }

    public static Tailer create(File file, TailerListener tailerListener, long l, boolean bl, boolean bl2, int n) {
        return Tailer.create(file, DEFAULT_CHARSET, tailerListener, l, bl, bl2, n);
    }

    public static Tailer create(File file, Charset charset, TailerListener tailerListener, long l, boolean bl, boolean bl2, int n) {
        Tailer tailer = new Tailer(file, charset, tailerListener, l, bl, bl2, n);
        Thread thread2 = new Thread(tailer);
        thread2.setDaemon(false);
        thread2.start();
        return tailer;
    }

    public static Tailer create(File file, TailerListener tailerListener, long l, boolean bl) {
        return Tailer.create(file, tailerListener, l, bl, 4096);
    }

    public static Tailer create(File file, TailerListener tailerListener, long l, boolean bl, boolean bl2) {
        return Tailer.create(file, tailerListener, l, bl, bl2, 4096);
    }

    public static Tailer create(File file, TailerListener tailerListener, long l) {
        return Tailer.create(file, tailerListener, l, false);
    }

    public static Tailer create(File file, TailerListener tailerListener) {
        return Tailer.create(file, tailerListener, 1000L, false);
    }

    public File getFile() {
        return this.file;
    }

    protected boolean getRun() {
        return this.run;
    }

    public long getDelay() {
        return this.delayMillis;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        RandomAccessFile randomAccessFile = null;
        try {
            long l = 0L;
            long l2 = 0L;
            while (this.getRun() && randomAccessFile == null) {
                try {
                    randomAccessFile = new RandomAccessFile(this.file, RAF_MODE);
                } catch (FileNotFoundException fileNotFoundException) {
                    this.listener.fileNotFound();
                }
                if (randomAccessFile == null) {
                    Thread.sleep(this.delayMillis);
                    continue;
                }
                l2 = this.end ? this.file.length() : 0L;
                l = this.file.lastModified();
                randomAccessFile.seek(l2);
            }
            while (this.getRun()) {
                boolean bl = FileUtils.isFileNewer(this.file, l);
                long l3 = this.file.length();
                if (l3 < l2) {
                    this.listener.fileRotated();
                    try {
                        RandomAccessFile randomAccessFile2 = randomAccessFile;
                        randomAccessFile = new RandomAccessFile(this.file, RAF_MODE);
                        try {
                            this.readLines(randomAccessFile2);
                        } catch (IOException iOException) {
                            this.listener.handle(iOException);
                        }
                        l2 = 0L;
                        IOUtils.closeQuietly((Closeable)randomAccessFile2);
                    } catch (FileNotFoundException fileNotFoundException) {
                        this.listener.fileNotFound();
                    }
                    continue;
                }
                if (l3 > l2) {
                    l2 = this.readLines(randomAccessFile);
                    l = this.file.lastModified();
                } else if (bl) {
                    l2 = 0L;
                    randomAccessFile.seek(l2);
                    l2 = this.readLines(randomAccessFile);
                    l = this.file.lastModified();
                }
                if (this.reOpen) {
                    IOUtils.closeQuietly((Closeable)randomAccessFile);
                }
                Thread.sleep(this.delayMillis);
                if (!this.getRun() || !this.reOpen) continue;
                randomAccessFile = new RandomAccessFile(this.file, RAF_MODE);
                randomAccessFile.seek(l2);
            }
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            this.stop(interruptedException);
        } catch (Exception exception) {
            this.stop(exception);
        } finally {
            IOUtils.closeQuietly(randomAccessFile);
        }
    }

    private void stop(Exception exception) {
        this.listener.handle(exception);
        this.stop();
    }

    public void stop() {
        this.run = false;
    }

    private long readLines(RandomAccessFile randomAccessFile) throws IOException {
        int n;
        long l;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(64);
        long l2 = l = randomAccessFile.getFilePointer();
        boolean bl = false;
        while (this.getRun() && (n = randomAccessFile.read(this.inbuf)) != -1) {
            block5: for (int i = 0; i < n; ++i) {
                byte by = this.inbuf[i];
                switch (by) {
                    case 10: {
                        bl = false;
                        this.listener.handle(new String(byteArrayOutputStream.toByteArray(), this.cset));
                        byteArrayOutputStream.reset();
                        l2 = l + (long)i + 1L;
                        continue block5;
                    }
                    case 13: {
                        if (bl) {
                            byteArrayOutputStream.write(13);
                        }
                        bl = true;
                        continue block5;
                    }
                    default: {
                        if (bl) {
                            bl = false;
                            this.listener.handle(new String(byteArrayOutputStream.toByteArray(), this.cset));
                            byteArrayOutputStream.reset();
                            l2 = l + (long)i + 1L;
                        }
                        byteArrayOutputStream.write(by);
                    }
                }
            }
            l = randomAccessFile.getFilePointer();
        }
        IOUtils.closeQuietly(byteArrayOutputStream);
        randomAccessFile.seek(l2);
        if (this.listener instanceof TailerListenerAdapter) {
            ((TailerListenerAdapter)this.listener).endOfFileReached();
        }
        return l2;
    }
}

