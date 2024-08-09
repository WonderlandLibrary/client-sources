/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.ConfigurationFactoryData;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.NullOutputStream;

public class RandomAccessFileManager
extends OutputStreamManager {
    static final int DEFAULT_BUFFER_SIZE = 262144;
    private static final RandomAccessFileManagerFactory FACTORY = new RandomAccessFileManagerFactory(null);
    private final String advertiseURI;
    private final RandomAccessFile randomAccessFile;
    private final ThreadLocal<Boolean> isEndOfBatch = new ThreadLocal();

    protected RandomAccessFileManager(LoggerContext loggerContext, RandomAccessFile randomAccessFile, String string, OutputStream outputStream, int n, String string2, Layout<? extends Serializable> layout, boolean bl) {
        super(loggerContext, outputStream, string, false, layout, bl, ByteBuffer.wrap(new byte[n]));
        this.randomAccessFile = randomAccessFile;
        this.advertiseURI = string2;
        this.isEndOfBatch.set(Boolean.FALSE);
    }

    public static RandomAccessFileManager getFileManager(String string, boolean bl, boolean bl2, int n, String string2, Layout<? extends Serializable> layout, Configuration configuration) {
        return (RandomAccessFileManager)RandomAccessFileManager.getManager(string, new FactoryData(bl, bl2, n, string2, layout, configuration), FACTORY);
    }

    public Boolean isEndOfBatch() {
        return this.isEndOfBatch.get();
    }

    public void setEndOfBatch(boolean bl) {
        this.isEndOfBatch.set(bl);
    }

    @Override
    protected void writeToDestination(byte[] byArray, int n, int n2) {
        try {
            this.randomAccessFile.write(byArray, n, n2);
        } catch (IOException iOException) {
            String string = "Error writing to RandomAccessFile " + this.getName();
            throw new AppenderLoggingException(string, iOException);
        }
    }

    @Override
    public synchronized void flush() {
        this.flushBuffer(this.byteBuffer);
    }

    @Override
    public synchronized boolean closeOutputStream() {
        this.flush();
        try {
            this.randomAccessFile.close();
            return true;
        } catch (IOException iOException) {
            this.logError("Unable to close RandomAccessFile", iOException);
            return true;
        }
    }

    public String getFileName() {
        return this.getName();
    }

    public int getBufferSize() {
        return this.byteBuffer.capacity();
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("fileURI", this.advertiseURI);
        return hashMap;
    }

    static class 1 {
    }

    private static class RandomAccessFileManagerFactory
    implements ManagerFactory<RandomAccessFileManager, FactoryData> {
        private RandomAccessFileManagerFactory() {
        }

        @Override
        public RandomAccessFileManager createManager(String string, FactoryData factoryData) {
            File file = new File(string);
            if (!FactoryData.access$100(factoryData)) {
                file.delete();
            }
            boolean bl = !FactoryData.access$100(factoryData) || !file.exists();
            NullOutputStream nullOutputStream = NullOutputStream.getInstance();
            try {
                FileUtils.makeParentDirs(file);
                RandomAccessFile randomAccessFile = new RandomAccessFile(string, "rw");
                if (FactoryData.access$100(factoryData)) {
                    randomAccessFile.seek(randomAccessFile.length());
                } else {
                    randomAccessFile.setLength(0L);
                }
                return new RandomAccessFileManager(factoryData.getLoggerContext(), randomAccessFile, string, nullOutputStream, FactoryData.access$200(factoryData), FactoryData.access$300(factoryData), FactoryData.access$400(factoryData), bl);
            } catch (Exception exception) {
                AbstractManager.LOGGER.error("RandomAccessFileManager (" + string + ") " + exception, (Throwable)exception);
                return null;
            }
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        RandomAccessFileManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class FactoryData
    extends ConfigurationFactoryData {
        private final boolean append;
        private final boolean immediateFlush;
        private final int bufferSize;
        private final String advertiseURI;
        private final Layout<? extends Serializable> layout;

        public FactoryData(boolean bl, boolean bl2, int n, String string, Layout<? extends Serializable> layout, Configuration configuration) {
            super(configuration);
            this.append = bl;
            this.immediateFlush = bl2;
            this.bufferSize = n;
            this.advertiseURI = string;
            this.layout = layout;
        }

        static boolean access$100(FactoryData factoryData) {
            return factoryData.append;
        }

        static int access$200(FactoryData factoryData) {
            return factoryData.bufferSize;
        }

        static String access$300(FactoryData factoryData) {
            return factoryData.advertiseURI;
        }

        static Layout access$400(FactoryData factoryData) {
            return factoryData.layout;
        }
    }
}

