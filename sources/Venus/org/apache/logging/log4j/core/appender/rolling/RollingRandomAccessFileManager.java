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
import org.apache.logging.log4j.Logger;
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
    private static final RollingRandomAccessFileManagerFactory FACTORY = new RollingRandomAccessFileManagerFactory(null);
    private RandomAccessFile randomAccessFile;
    private final ThreadLocal<Boolean> isEndOfBatch = new ThreadLocal();

    public RollingRandomAccessFileManager(LoggerContext loggerContext, RandomAccessFile randomAccessFile, String string, String string2, OutputStream outputStream, boolean bl, boolean bl2, int n, long l, long l2, TriggeringPolicy triggeringPolicy, RolloverStrategy rolloverStrategy, String string3, Layout<? extends Serializable> layout, boolean bl3) {
        super(loggerContext, string, string2, outputStream, bl, false, l, l2, triggeringPolicy, rolloverStrategy, string3, layout, bl3, ByteBuffer.wrap(new byte[n]));
        this.randomAccessFile = randomAccessFile;
        this.isEndOfBatch.set(Boolean.FALSE);
        this.writeHeader();
    }

    private void writeHeader() {
        if (this.layout == null) {
            return;
        }
        byte[] byArray = this.layout.getHeader();
        if (byArray == null) {
            return;
        }
        try {
            if (this.randomAccessFile.length() == 0L) {
                this.randomAccessFile.write(byArray, 0, byArray.length);
            }
        } catch (IOException iOException) {
            this.logError("Unable to write header", iOException);
        }
    }

    public static RollingRandomAccessFileManager getRollingRandomAccessFileManager(String string, String string2, boolean bl, boolean bl2, int n, TriggeringPolicy triggeringPolicy, RolloverStrategy rolloverStrategy, String string3, Layout<? extends Serializable> layout, Configuration configuration) {
        return (RollingRandomAccessFileManager)RollingRandomAccessFileManager.getManager(string, new FactoryData(string2, bl, bl2, n, triggeringPolicy, rolloverStrategy, string3, layout, configuration), FACTORY);
    }

    public Boolean isEndOfBatch() {
        return this.isEndOfBatch.get();
    }

    public void setEndOfBatch(boolean bl) {
        this.isEndOfBatch.set(bl);
    }

    @Override
    protected synchronized void write(byte[] byArray, int n, int n2, boolean bl) {
        super.write(byArray, n, n2, bl);
    }

    @Override
    protected synchronized void writeToDestination(byte[] byArray, int n, int n2) {
        try {
            this.randomAccessFile.write(byArray, n, n2);
            this.size += (long)n2;
        } catch (IOException iOException) {
            String string = "Error writing to RandomAccessFile " + this.getName();
            throw new AppenderLoggingException(string, iOException);
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
        } catch (IOException iOException) {
            this.logError("Unable to close RandomAccessFile", iOException);
            return true;
        }
    }

    @Override
    public int getBufferSize() {
        return this.byteBuffer.capacity();
    }

    @Override
    public void updateData(Object object) {
        FactoryData factoryData = (FactoryData)object;
        this.setRolloverStrategy(factoryData.getRolloverStrategy());
        this.setTriggeringPolicy(factoryData.getTriggeringPolicy());
    }

    static Logger access$200() {
        return LOGGER;
    }

    static Logger access$300() {
        return LOGGER;
    }

    static Logger access$1100() {
        return LOGGER;
    }

    static Logger access$1200() {
        return LOGGER;
    }

    static class 1 {
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

        public FactoryData(String string, boolean bl, boolean bl2, int n, TriggeringPolicy triggeringPolicy, RolloverStrategy rolloverStrategy, String string2, Layout<? extends Serializable> layout, Configuration configuration) {
            super(configuration);
            this.pattern = string;
            this.append = bl;
            this.immediateFlush = bl2;
            this.bufferSize = n;
            this.policy = triggeringPolicy;
            this.strategy = rolloverStrategy;
            this.advertiseURI = string2;
            this.layout = layout;
        }

        public TriggeringPolicy getTriggeringPolicy() {
            return this.policy;
        }

        public RolloverStrategy getRolloverStrategy() {
            return this.strategy;
        }

        static boolean access$100(FactoryData factoryData) {
            return factoryData.append;
        }

        static String access$400(FactoryData factoryData) {
            return factoryData.pattern;
        }

        static boolean access$500(FactoryData factoryData) {
            return factoryData.immediateFlush;
        }

        static int access$600(FactoryData factoryData) {
            return factoryData.bufferSize;
        }

        static TriggeringPolicy access$700(FactoryData factoryData) {
            return factoryData.policy;
        }

        static RolloverStrategy access$800(FactoryData factoryData) {
            return factoryData.strategy;
        }

        static String access$900(FactoryData factoryData) {
            return factoryData.advertiseURI;
        }

        static Layout access$1000(FactoryData factoryData) {
            return factoryData.layout;
        }
    }

    private static class RollingRandomAccessFileManagerFactory
    implements ManagerFactory<RollingRandomAccessFileManager, FactoryData> {
        private RollingRandomAccessFileManagerFactory() {
        }

        @Override
        public RollingRandomAccessFileManager createManager(String string, FactoryData factoryData) {
            File file = new File(string);
            if (!FactoryData.access$100(factoryData)) {
                file.delete();
            }
            long l = FactoryData.access$100(factoryData) ? file.length() : 0L;
            long l2 = file.exists() ? file.lastModified() : System.currentTimeMillis();
            boolean bl = !FactoryData.access$100(factoryData) || !file.exists();
            RandomAccessFile randomAccessFile = null;
            try {
                FileUtils.makeParentDirs(file);
                randomAccessFile = new RandomAccessFile(string, "rw");
                if (FactoryData.access$100(factoryData)) {
                    long l3 = randomAccessFile.length();
                    RollingRandomAccessFileManager.access$200().trace("RandomAccessFile {} seek to {}", (Object)string, (Object)l3);
                    randomAccessFile.seek(l3);
                } else {
                    RollingRandomAccessFileManager.access$300().trace("RandomAccessFile {} set length to 0", (Object)string);
                    randomAccessFile.setLength(0L);
                }
                return new RollingRandomAccessFileManager(factoryData.getLoggerContext(), randomAccessFile, string, FactoryData.access$400(factoryData), NullOutputStream.getInstance(), FactoryData.access$100(factoryData), FactoryData.access$500(factoryData), FactoryData.access$600(factoryData), l, l2, FactoryData.access$700(factoryData), FactoryData.access$800(factoryData), FactoryData.access$900(factoryData), FactoryData.access$1000(factoryData), bl);
            } catch (IOException iOException) {
                RollingRandomAccessFileManager.access$1100().error("Cannot access RandomAccessFile " + iOException, (Throwable)iOException);
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException iOException2) {
                        RollingRandomAccessFileManager.access$1200().error("Cannot close RandomAccessFile {}", (Object)string, (Object)iOException2);
                    }
                }
                return null;
            }
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        RollingRandomAccessFileManagerFactory(1 var1_1) {
            this();
        }
    }
}

