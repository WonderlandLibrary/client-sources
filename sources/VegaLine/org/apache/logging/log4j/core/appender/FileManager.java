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
    private static final FileManagerFactory FACTORY = new FileManagerFactory();
    private final boolean isAppend;
    private final boolean createOnDemand;
    private final boolean isLocking;
    private final String advertiseURI;
    private final int bufferSize;

    @Deprecated
    protected FileManager(String fileName, OutputStream os, boolean append, boolean locking, String advertiseURI, Layout<? extends Serializable> layout, int bufferSize, boolean writeHeader) {
        this(fileName, os, append, locking, advertiseURI, layout, writeHeader, ByteBuffer.wrap(new byte[bufferSize]));
    }

    @Deprecated
    protected FileManager(String fileName, OutputStream os, boolean append, boolean locking, String advertiseURI, Layout<? extends Serializable> layout, boolean writeHeader, ByteBuffer buffer) {
        super(os, fileName, layout, writeHeader, buffer);
        this.isAppend = append;
        this.createOnDemand = false;
        this.isLocking = locking;
        this.advertiseURI = advertiseURI;
        this.bufferSize = buffer.capacity();
    }

    protected FileManager(LoggerContext loggerContext, String fileName, OutputStream os, boolean append, boolean locking, boolean createOnDemand, String advertiseURI, Layout<? extends Serializable> layout, boolean writeHeader, ByteBuffer buffer) {
        super(loggerContext, os, fileName, createOnDemand, layout, writeHeader, buffer);
        this.isAppend = append;
        this.createOnDemand = createOnDemand;
        this.isLocking = locking;
        this.advertiseURI = advertiseURI;
        this.bufferSize = buffer.capacity();
    }

    public static FileManager getFileManager(String fileName, boolean append, boolean locking, boolean bufferedIo, boolean createOnDemand, String advertiseUri, Layout<? extends Serializable> layout, int bufferSize, Configuration configuration) {
        if (locking && bufferedIo) {
            locking = false;
        }
        return (FileManager)FileManager.getManager(fileName, new FactoryData(append, locking, bufferedIo, bufferSize, createOnDemand, advertiseUri, layout, configuration), FACTORY);
    }

    @Override
    protected OutputStream createOutputStream() throws FileNotFoundException {
        String filename = this.getFileName();
        LOGGER.debug("Now writing to {} at {}", (Object)filename, (Object)new Date());
        return new FileOutputStream(filename, this.isAppend);
    }

    @Override
    protected synchronized void write(byte[] bytes, int offset, int length, boolean immediateFlush) {
        block15: {
            if (this.isLocking) {
                try {
                    FileChannel channel = ((FileOutputStream)this.getOutputStream()).getChannel();
                    try (FileLock lock = channel.lock(0L, Long.MAX_VALUE, false);){
                        super.write(bytes, offset, length, immediateFlush);
                        break block15;
                    }
                } catch (IOException ex) {
                    throw new AppenderLoggingException("Unable to obtain lock on " + this.getName(), ex);
                }
            }
            super.write(bytes, offset, length, immediateFlush);
        }
    }

    @Override
    protected synchronized void writeToDestination(byte[] bytes, int offset, int length) {
        block15: {
            if (this.isLocking) {
                try {
                    FileChannel channel = ((FileOutputStream)this.getOutputStream()).getChannel();
                    try (FileLock lock = channel.lock(0L, Long.MAX_VALUE, false);){
                        super.writeToDestination(bytes, offset, length);
                        break block15;
                    }
                } catch (IOException ex) {
                    throw new AppenderLoggingException("Unable to obtain lock on " + this.getName(), ex);
                }
            }
            super.writeToDestination(bytes, offset, length);
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
        HashMap<String, String> result = new HashMap<String, String>(super.getContentFormat());
        result.put("fileURI", this.advertiseURI);
        return result;
    }

    private static class FileManagerFactory
    implements ManagerFactory<FileManager, FactoryData> {
        private FileManagerFactory() {
        }

        @Override
        public FileManager createManager(String name, FactoryData data) {
            File file = new File(name);
            try {
                FileUtils.makeParentDirs(file);
                boolean writeHeader = !data.append || !file.exists();
                int actualSize = data.bufferedIo ? data.bufferSize : Constants.ENCODER_BYTE_BUFFER_SIZE;
                ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[actualSize]);
                FileOutputStream fos = data.createOnDemand ? null : new FileOutputStream(file, data.append);
                return new FileManager(data.getLoggerContext(), name, fos, data.append, data.locking, data.createOnDemand, data.advertiseURI, data.layout, writeHeader, byteBuffer);
            } catch (IOException ex) {
                AbstractManager.LOGGER.error("FileManager (" + name + ") " + ex, (Throwable)ex);
                return null;
            }
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

        public FactoryData(boolean append, boolean locking, boolean bufferedIo, int bufferSize, boolean createOnDemand, String advertiseURI, Layout<? extends Serializable> layout, Configuration configuration) {
            super(configuration);
            this.append = append;
            this.locking = locking;
            this.bufferedIo = bufferedIo;
            this.bufferSize = bufferSize;
            this.createOnDemand = createOnDemand;
            this.advertiseURI = advertiseURI;
            this.layout = layout;
        }
    }
}

