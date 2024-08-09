/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.NullOutputStream;

public class MemoryMappedFileManager
extends OutputStreamManager {
    static final int DEFAULT_REGION_LENGTH = 0x2000000;
    private static final int MAX_REMAP_COUNT = 10;
    private static final MemoryMappedFileManagerFactory FACTORY = new MemoryMappedFileManagerFactory(null);
    private static final double NANOS_PER_MILLISEC = 1000000.0;
    private final boolean immediateFlush;
    private final int regionLength;
    private final String advertiseURI;
    private final RandomAccessFile randomAccessFile;
    private final ThreadLocal<Boolean> isEndOfBatch = new ThreadLocal();
    private MappedByteBuffer mappedBuffer;
    private long mappingOffset;

    protected MemoryMappedFileManager(RandomAccessFile randomAccessFile, String string, OutputStream outputStream, boolean bl, long l, int n, String string2, Layout<? extends Serializable> layout, boolean bl2) throws IOException {
        super(outputStream, string, layout, bl2, ByteBuffer.wrap(new byte[0]));
        this.immediateFlush = bl;
        this.randomAccessFile = Objects.requireNonNull(randomAccessFile, "RandomAccessFile");
        this.regionLength = n;
        this.advertiseURI = string2;
        this.isEndOfBatch.set(Boolean.FALSE);
        this.mappedBuffer = MemoryMappedFileManager.mmap(this.randomAccessFile.getChannel(), this.getFileName(), l, n);
        this.byteBuffer = this.mappedBuffer;
        this.mappingOffset = l;
    }

    public static MemoryMappedFileManager getFileManager(String string, boolean bl, boolean bl2, int n, String string2, Layout<? extends Serializable> layout) {
        return (MemoryMappedFileManager)MemoryMappedFileManager.getManager(string, new FactoryData(bl, bl2, n, string2, layout), FACTORY);
    }

    public Boolean isEndOfBatch() {
        return this.isEndOfBatch.get();
    }

    public void setEndOfBatch(boolean bl) {
        this.isEndOfBatch.set(bl);
    }

    @Override
    protected synchronized void write(byte[] byArray, int n, int n2, boolean bl) {
        while (n2 > this.mappedBuffer.remaining()) {
            int n3 = this.mappedBuffer.remaining();
            this.mappedBuffer.put(byArray, n, n3);
            n += n3;
            n2 -= n3;
            this.remap();
        }
        this.mappedBuffer.put(byArray, n, n2);
    }

    private synchronized void remap() {
        long l = this.mappingOffset + (long)this.mappedBuffer.position();
        int n = this.mappedBuffer.remaining() + this.regionLength;
        try {
            MemoryMappedFileManager.unsafeUnmap(this.mappedBuffer);
            long l2 = this.randomAccessFile.length() + (long)this.regionLength;
            LOGGER.debug("{} {} extending {} by {} bytes to {}", (Object)this.getClass().getSimpleName(), (Object)this.getName(), (Object)this.getFileName(), (Object)this.regionLength, (Object)l2);
            long l3 = System.nanoTime();
            this.randomAccessFile.setLength(l2);
            float f = (float)((double)(System.nanoTime() - l3) / 1000000.0);
            LOGGER.debug("{} {} extended {} OK in {} millis", (Object)this.getClass().getSimpleName(), (Object)this.getName(), (Object)this.getFileName(), (Object)Float.valueOf(f));
            this.mappedBuffer = MemoryMappedFileManager.mmap(this.randomAccessFile.getChannel(), this.getFileName(), l, n);
            this.byteBuffer = this.mappedBuffer;
            this.mappingOffset = l;
        } catch (Exception exception) {
            this.logError("Unable to remap", exception);
        }
    }

    @Override
    public synchronized void flush() {
        this.mappedBuffer.force();
    }

    @Override
    public synchronized boolean closeOutputStream() {
        long l = this.mappedBuffer.position();
        long l2 = this.mappingOffset + l;
        try {
            MemoryMappedFileManager.unsafeUnmap(this.mappedBuffer);
        } catch (Exception exception) {
            this.logError("Unable to unmap MappedBuffer", exception);
        }
        try {
            LOGGER.debug("MMapAppender closing. Setting {} length to {} (offset {} + position {})", (Object)this.getFileName(), (Object)l2, (Object)this.mappingOffset, (Object)l);
            this.randomAccessFile.setLength(l2);
            this.randomAccessFile.close();
            return true;
        } catch (IOException iOException) {
            this.logError("Unable to close MemoryMappedFile", iOException);
            return true;
        }
    }

    public static MappedByteBuffer mmap(FileChannel fileChannel, String string, long l, int n) throws IOException {
        int n2 = 1;
        while (true) {
            try {
                LOGGER.debug("MMapAppender remapping {} start={}, size={}", (Object)string, (Object)l, (Object)n);
                long l2 = System.nanoTime();
                MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, l, n);
                mappedByteBuffer.order(ByteOrder.nativeOrder());
                float f = (float)((double)(System.nanoTime() - l2) / 1000000.0);
                LOGGER.debug("MMapAppender remapped {} OK in {} millis", (Object)string, (Object)Float.valueOf(f));
                return mappedByteBuffer;
            } catch (IOException iOException) {
                if (iOException.getMessage() == null || !iOException.getMessage().endsWith("user-mapped section open")) {
                    throw iOException;
                }
                LOGGER.debug("Remap attempt {}/{} failed. Retrying...", (Object)n2, (Object)10, (Object)iOException);
                if (n2 < 10) {
                    Thread.yield();
                } else {
                    try {
                        Thread.sleep(1L);
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        throw iOException;
                    }
                }
                ++n2;
                continue;
            }
            break;
        }
    }

    private static void unsafeUnmap(MappedByteBuffer mappedByteBuffer) throws PrivilegedActionException {
        LOGGER.debug("MMapAppender unmapping old buffer...");
        long l = System.nanoTime();
        AccessController.doPrivileged(new PrivilegedExceptionAction<Object>(mappedByteBuffer){
            final MappedByteBuffer val$mbb;
            {
                this.val$mbb = mappedByteBuffer;
            }

            @Override
            public Object run() throws Exception {
                Method method = this.val$mbb.getClass().getMethod("cleaner", new Class[0]);
                method.setAccessible(false);
                Object object = method.invoke(this.val$mbb, new Object[0]);
                Method method2 = object.getClass().getMethod("clean", new Class[0]);
                method2.invoke(object, new Object[0]);
                return null;
            }
        });
        float f = (float)((double)(System.nanoTime() - l) / 1000000.0);
        LOGGER.debug("MMapAppender unmapped buffer OK in {} millis", (Object)Float.valueOf(f));
    }

    public String getFileName() {
        return this.getName();
    }

    public int getRegionLength() {
        return this.regionLength;
    }

    public boolean isImmediateFlush() {
        return this.immediateFlush;
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("fileURI", this.advertiseURI);
        return hashMap;
    }

    @Override
    protected void flushBuffer(ByteBuffer byteBuffer) {
    }

    @Override
    public ByteBuffer getByteBuffer() {
        return this.mappedBuffer;
    }

    @Override
    public ByteBuffer drain(ByteBuffer byteBuffer) {
        this.remap();
        return this.mappedBuffer;
    }

    private static class MemoryMappedFileManagerFactory
    implements ManagerFactory<MemoryMappedFileManager, FactoryData> {
        private MemoryMappedFileManagerFactory() {
        }

        @Override
        public MemoryMappedFileManager createManager(String string, FactoryData factoryData) {
            File file = new File(string);
            if (!FactoryData.access$100(factoryData)) {
                file.delete();
            }
            boolean bl = !FactoryData.access$100(factoryData) || !file.exists();
            NullOutputStream nullOutputStream = NullOutputStream.getInstance();
            RandomAccessFile randomAccessFile = null;
            try {
                FileUtils.makeParentDirs(file);
                randomAccessFile = new RandomAccessFile(string, "rw");
                long l = FactoryData.access$100(factoryData) ? randomAccessFile.length() : 0L;
                randomAccessFile.setLength(l + (long)FactoryData.access$200(factoryData));
                return new MemoryMappedFileManager(randomAccessFile, string, nullOutputStream, FactoryData.access$300(factoryData), l, FactoryData.access$200(factoryData), FactoryData.access$400(factoryData), FactoryData.access$500(factoryData), bl);
            } catch (Exception exception) {
                AbstractManager.LOGGER.error("MemoryMappedFileManager (" + string + ") " + exception, (Throwable)exception);
                Closer.closeSilently(randomAccessFile);
                return null;
            }
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        MemoryMappedFileManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class FactoryData {
        private final boolean append;
        private final boolean immediateFlush;
        private final int regionLength;
        private final String advertiseURI;
        private final Layout<? extends Serializable> layout;

        public FactoryData(boolean bl, boolean bl2, int n, String string, Layout<? extends Serializable> layout) {
            this.append = bl;
            this.immediateFlush = bl2;
            this.regionLength = n;
            this.advertiseURI = string;
            this.layout = layout;
        }

        static boolean access$100(FactoryData factoryData) {
            return factoryData.append;
        }

        static int access$200(FactoryData factoryData) {
            return factoryData.regionLength;
        }

        static boolean access$300(FactoryData factoryData) {
            return factoryData.immediateFlush;
        }

        static String access$400(FactoryData factoryData) {
            return factoryData.advertiseURI;
        }

        static Layout access$500(FactoryData factoryData) {
            return factoryData.layout;
        }
    }
}

