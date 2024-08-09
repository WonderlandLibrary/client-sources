/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;
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
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.FileUtils;

public class FileManager
extends OutputStreamManager {
    private static final FileManagerFactory FACTORY = new FileManagerFactory(null);
    private final boolean isAppend;
    private final boolean createOnDemand;
    private final boolean isLocking;
    private final String advertiseURI;
    private final int bufferSize;

    @Deprecated
    protected FileManager(String string, OutputStream outputStream, boolean bl, boolean bl2, String string2, Layout<? extends Serializable> layout, int n, boolean bl3) {
        this(string, outputStream, bl, bl2, string2, layout, bl3, ByteBuffer.wrap(new byte[n]));
    }

    @Deprecated
    protected FileManager(String string, OutputStream outputStream, boolean bl, boolean bl2, String string2, Layout<? extends Serializable> layout, boolean bl3, ByteBuffer byteBuffer) {
        super(outputStream, string, layout, bl3, byteBuffer);
        this.isAppend = bl;
        this.createOnDemand = false;
        this.isLocking = bl2;
        this.advertiseURI = string2;
        this.bufferSize = byteBuffer.capacity();
    }

    protected FileManager(LoggerContext loggerContext, String string, OutputStream outputStream, boolean bl, boolean bl2, boolean bl3, String string2, Layout<? extends Serializable> layout, boolean bl4, ByteBuffer byteBuffer) {
        super(loggerContext, outputStream, string, bl3, layout, bl4, byteBuffer);
        this.isAppend = bl;
        this.createOnDemand = bl3;
        this.isLocking = bl2;
        this.advertiseURI = string2;
        this.bufferSize = byteBuffer.capacity();
    }

    public static FileManager getFileManager(String string, boolean bl, boolean bl2, boolean bl3, boolean bl4, String string2, Layout<? extends Serializable> layout, int n, Configuration configuration) {
        if (bl2 && bl3) {
            bl2 = false;
        }
        return (FileManager)FileManager.getManager(string, new FactoryData(bl, bl2, bl3, n, bl4, string2, layout, configuration), FACTORY);
    }

    @Override
    protected OutputStream createOutputStream() throws FileNotFoundException {
        String string = this.getFileName();
        LOGGER.debug("Now writing to {} at {}", (Object)string, (Object)new Date());
        return new FileOutputStream(string, this.isAppend);
    }

    @Override
    protected synchronized void write(byte[] byArray, int n, int n2, boolean bl) {
        block15: {
            if (this.isLocking) {
                try {
                    FileChannel fileChannel = ((FileOutputStream)this.getOutputStream()).getChannel();
                    try (FileLock fileLock = fileChannel.lock(0L, Long.MAX_VALUE, true);){
                        super.write(byArray, n, n2, bl);
                        break block15;
                    }
                } catch (IOException iOException) {
                    throw new AppenderLoggingException("Unable to obtain lock on " + this.getName(), iOException);
                }
            }
            super.write(byArray, n, n2, bl);
        }
    }

    @Override
    protected synchronized void writeToDestination(byte[] byArray, int n, int n2) {
        block15: {
            if (this.isLocking) {
                try {
                    FileChannel fileChannel = ((FileOutputStream)this.getOutputStream()).getChannel();
                    try (FileLock fileLock = fileChannel.lock(0L, Long.MAX_VALUE, true);){
                        super.writeToDestination(byArray, n, n2);
                        break block15;
                    }
                } catch (IOException iOException) {
                    throw new AppenderLoggingException("Unable to obtain lock on " + this.getName(), iOException);
                }
            }
            super.writeToDestination(byArray, n, n2);
        }
    }

    public String getFileName() {
        return this.getName();
    }

    public boolean isAppend() {
        return this.isAppend;
    }

    public boolean isCreateOnDemand() {
        return this.createOnDemand;
    }

    public boolean isLocking() {
        return this.isLocking;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("fileURI", this.advertiseURI);
        return hashMap;
    }

    static class 1 {
    }

    private static class FileManagerFactory
    implements ManagerFactory<FileManager, FactoryData> {
        private FileManagerFactory() {
        }

        @Override
        public FileManager createManager(String string, FactoryData factoryData) {
            File file = new File(string);
            try {
                FileUtils.makeParentDirs(file);
                boolean bl = !FactoryData.access$100(factoryData) || !file.exists();
                int n = FactoryData.access$200(factoryData) ? FactoryData.access$300(factoryData) : Constants.ENCODER_BYTE_BUFFER_SIZE;
                ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[n]);
                FileOutputStream fileOutputStream = FactoryData.access$400(factoryData) ? null : new FileOutputStream(file, FactoryData.access$100(factoryData));
                return new FileManager(factoryData.getLoggerContext(), string, fileOutputStream, FactoryData.access$100(factoryData), FactoryData.access$500(factoryData), FactoryData.access$400(factoryData), FactoryData.access$600(factoryData), FactoryData.access$700(factoryData), bl, byteBuffer);
            } catch (IOException iOException) {
                AbstractManager.LOGGER.error("FileManager (" + string + ") " + iOException, (Throwable)iOException);
                return null;
            }
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        FileManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class FactoryData
    extends ConfigurationFactoryData {
        private final boolean append;
        private final boolean locking;
        private final boolean bufferedIo;
        private final int bufferSize;
        private final boolean createOnDemand;
        private final String advertiseURI;
        private final Layout<? extends Serializable> layout;

        public FactoryData(boolean bl, boolean bl2, boolean bl3, int n, boolean bl4, String string, Layout<? extends Serializable> layout, Configuration configuration) {
            super(configuration);
            this.append = bl;
            this.locking = bl2;
            this.bufferedIo = bl3;
            this.bufferSize = n;
            this.createOnDemand = bl4;
            this.advertiseURI = string;
            this.layout = layout;
        }

        static boolean access$100(FactoryData factoryData) {
            return factoryData.append;
        }

        static boolean access$200(FactoryData factoryData) {
            return factoryData.bufferedIo;
        }

        static int access$300(FactoryData factoryData) {
            return factoryData.bufferSize;
        }

        static boolean access$400(FactoryData factoryData) {
            return factoryData.createOnDemand;
        }

        static boolean access$500(FactoryData factoryData) {
            return factoryData.locking;
        }

        static String access$600(FactoryData factoryData) {
            return factoryData.advertiseURI;
        }

        static Layout access$700(FactoryData factoryData) {
            return factoryData.layout;
        }
    }
}

