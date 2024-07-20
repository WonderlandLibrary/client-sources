/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.ByteBuffer;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.ConfigurationFactoryData;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.NullOutputStream;

public class RollingRandomAccessFileManager
extends RollingFileManager {
    public static final int DEFAULT_BUFFER_SIZE = 262144;
    private static final RollingRandomAccessFileManagerFactory FACTORY = new RollingRandomAccessFileManagerFactory();
    private RandomAccessFile randomAccessFile;
    private final ThreadLocal<Boolean> isEndOfBatch = new ThreadLocal();

    public RollingRandomAccessFileManager(LoggerContext loggerContext, RandomAccessFile raf, String fileName, String pattern, OutputStream os, boolean append, boolean immediateFlush, int bufferSize, long size, long time, TriggeringPolicy policy, RolloverStrategy strategy, String advertiseURI, Layout<? extends Serializable> layout, boolean writeHeader) {
        super(loggerContext, fileName, pattern, os, append, false, size, time, policy, strategy, advertiseURI, layout, writeHeader, ByteBuffer.wrap(new byte[bufferSize]));
        this.randomAccessFile = raf;
        this.isEndOfBatch.set(Boolean.FALSE);
        this.writeHeader();
    }

    private void writeHeader() {
        if (this.layout == null) {
            return;
        }
        byte[] header = this.layout.getHeader();
        if (header == null) {
            return;
        }
        try {
            if (this.randomAccessFile.length() == 0L) {
                this.randomAccessFile.write(header, 0, header.length);
            }
        } catch (IOException e) {
            this.logError("Unable to write header", e);
        }
    }

    public static RollingRandomAccessFileManager getRollingRandomAccessFileManager(String fileName, String filePattern, boolean isAppend, boolean immediateFlush, int bufferSize, TriggeringPolicy policy, RolloverStrategy strategy, String advertiseURI, Layout<? extends Serializable> layout, Configuration configuration) {
        return (RollingRandomAccessFileManager)RollingRandomAccessFileManager.getManager(fileName, new FactoryData(filePattern, isAppend, immediateFlush, bufferSize, policy, strategy, advertiseURI, layout, configuration), FACTORY);
    }

    public Boolean isEndOfBatch() {
        return this.isEndOfBatch.get();
    }

    public void setEndOfBatch(boolean endOfBatch) {
        this.isEndOfBatch.set(endOfBatch);
    }

    @Override
    protected synchronized void write(byte[] bytes, int offset, int length, boolean immediateFlush) {
        super.write(bytes, offset, length, immediateFlush);
    }

    @Override
    protected synchronized void writeToDestination(byte[] bytes, int offset, int length) {
        try {
            this.randomAccessFile.write(bytes, offset, length);
            this.size += (long)length;
        } catch (IOException ex) {
            String msg = "Error writing to RandomAccessFile " + this.getName();
            throw new AppenderLoggingException(msg, ex);
        }
    }

    @Override
    protected void createFileAfterRollover() throws IOException {
        this.randomAccessFile = new RandomAccessFile(this.getFileName(), "rw");
        if (this.isAppend()) {
            this.randomAccessFile.seek(this.randomAccessFile.length());
        }
        this.writeHeader();
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
        } catch (IOException e) {
            this.logError("Unable to close RandomAccessFile", e);
            return false;
        }
    }

    @Override
    public int getBufferSize() {
        return this.byteBuffer.capacity();
    }

    @Override
    public void updateData(Object data) {
        FactoryData factoryData = (FactoryData)data;
        this.setRolloverStrategy(factoryData.getRolloverStrategy());
        this.setTriggeringPolicy(factoryData.getTriggeringPolicy());
    }

    private static class FactoryData
    extends ConfigurationFactoryData {
        private final String pattern;
        private final boolean append;
        private final boolean immediateFlush;
        private final int bufferSize;
        private final TriggeringPolicy policy;
        private final RolloverStrategy strategy;
        private final String advertiseURI;
        private final Layout<? extends Serializable> layout;

        public FactoryData(String pattern, boolean append, boolean immediateFlush, int bufferSize, TriggeringPolicy policy, RolloverStrategy strategy, String advertiseURI, Layout<? extends Serializable> layout, Configuration configuration) {
            super(configuration);
            this.pattern = pattern;
            this.append = append;
            this.immediateFlush = immediateFlush;
            this.bufferSize = bufferSize;
            this.policy = policy;
            this.strategy = strategy;
            this.advertiseURI = advertiseURI;
            this.layout = layout;
        }

        public TriggeringPolicy getTriggeringPolicy() {
            return this.policy;
        }

        public RolloverStrategy getRolloverStrategy() {
            return this.strategy;
        }
    }

    private static class RollingRandomAccessFileManagerFactory
    implements ManagerFactory<RollingRandomAccessFileManager, FactoryData> {
        private RollingRandomAccessFileManagerFactory() {
        }

        @Override
        public RollingRandomAccessFileManager createManager(String name, FactoryData data) {
            File file = new File(name);
            if (!data.append) {
                file.delete();
            }
            long size = data.append ? file.length() : 0L;
            long time = file.exists() ? file.lastModified() : System.currentTimeMillis();
            boolean writeHeader = !data.append || !file.exists();
            RandomAccessFile raf = null;
            try {
                FileUtils.makeParentDirs(file);
                raf = new RandomAccessFile(name, "rw");
                if (data.append) {
                    long length = raf.length();
                    LOGGER.trace("RandomAccessFile {} seek to {}", (Object)name, (Object)length);
                    raf.seek(length);
                } else {
                    LOGGER.trace("RandomAccessFile {} set length to 0", (Object)name);
                    raf.setLength(0L);
                }
                return new RollingRandomAccessFileManager(data.getLoggerContext(), raf, name, data.pattern, NullOutputStream.getInstance(), data.append, data.immediateFlush, data.bufferSize, size, time, data.policy, data.strategy, data.advertiseURI, data.layout, writeHeader);
            } catch (IOException ex) {
                LOGGER.error("Cannot access RandomAccessFile " + ex, (Throwable)ex);
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        LOGGER.error("Cannot close RandomAccessFile {}", (Object)name, (Object)e);
                    }
                }
                return null;
            }
        }
    }
}

