/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.FileManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.Integers;

@Plugin(name="File", category="Core", elementType="appender", printObject=true)
public final class FileAppender
extends AbstractOutputStreamAppender<FileManager> {
    public static final String PLUGIN_NAME = "File";
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private final String fileName;
    private final Advertiser advertiser;
    private final Object advertisement;

    @Deprecated
    public static <B extends Builder<B>> FileAppender createAppender(String fileName, String append, String locking, String name, String immediateFlush, String ignoreExceptions, String bufferedIo, String bufferSizeStr, Layout<? extends Serializable> layout, Filter filter, String advertise, String advertiseUri, Configuration config) {
        return ((Builder)((AbstractAppender.Builder)((Builder)((Builder)((Builder)((Builder)((AbstractFilterable.Builder)((Builder)((Builder)((Builder)((AbstractOutputStreamAppender.Builder)((Builder)((Builder)((Builder)FileAppender.newBuilder()).withAdvertise(Boolean.parseBoolean(advertise))).withAdvertiseUri(advertiseUri)).withAppend(Booleans.parseBoolean(append, true))).withBufferedIo(Booleans.parseBoolean(bufferedIo, true))).withBufferSize(Integers.parseInt(bufferSizeStr, 8192))).setConfiguration(config)).withFileName(fileName)).withFilter(filter)).withIgnoreExceptions(Booleans.parseBoolean(ignoreExceptions, true))).withImmediateFlush(Booleans.parseBoolean(immediateFlush, true))).withLayout(layout)).withLocking(Boolean.parseBoolean(locking))).withName(name)).build();
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    private FileAppender(String name, Layout<? extends Serializable> layout, Filter filter, FileManager manager, String filename, boolean ignoreExceptions, boolean immediateFlush, Advertiser advertiser) {
        super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
        if (advertiser != null) {
            HashMap<String, String> configuration = new HashMap<String, String>(layout.getContentFormat());
            configuration.putAll(manager.getContentFormat());
            configuration.put("contentType", layout.getContentType());
            configuration.put("name", name);
            this.advertisement = advertiser.advertise(configuration);
        } else {
            this.advertisement = null;
        }
        this.fileName = filename;
        this.advertiser = advertiser;
    }

    public String getFileName() {
        return this.fileName;
    }

    @Override
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        super.stop(timeout, timeUnit, false);
        if (this.advertiser != null) {
            this.advertiser.unadvertise(this.advertisement);
        }
        this.setStopped();
        return true;
    }

    public static class Builder<B extends Builder<B>>
    extends AbstractOutputStreamAppender.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<FileAppender> {
        @PluginBuilderAttribute
        @Required
        private String fileName;
        @PluginBuilderAttribute
        private boolean append = true;
        @PluginBuilderAttribute
        private boolean locking;
        @PluginBuilderAttribute
        private boolean advertise;
        @PluginBuilderAttribute
        private String advertiseUri;
        @PluginBuilderAttribute
        private boolean createOnDemand;

        @Override
        public FileAppender build() {
            Layout<Serializable> layout;
            FileManager manager;
            boolean bufferedIo = this.isBufferedIo();
            int bufferSize = this.getBufferSize();
            if (this.locking && bufferedIo) {
                LOGGER.warn("Locking and buffering are mutually exclusive. No buffering will occur for {}", (Object)this.fileName);
                bufferedIo = false;
            }
            if (!bufferedIo && bufferSize > 0) {
                LOGGER.warn("The bufferSize is set to {} but bufferedIo is false: {}", (Object)bufferSize, (Object)bufferedIo);
            }
            if ((manager = FileManager.getFileManager(this.fileName, this.append, this.locking, bufferedIo, this.createOnDemand, this.advertiseUri, layout = this.getOrCreateLayout(), bufferSize, this.getConfiguration())) == null) {
                return null;
            }
            return new FileAppender(this.getName(), layout, this.getFilter(), manager, this.fileName, this.isIgnoreExceptions(), !bufferedIo || this.isImmediateFlush(), this.advertise ? this.getConfiguration().getAdvertiser() : null);
        }

        public String getAdvertiseUri() {
            return this.advertiseUri;
        }

        public String getFileName() {
            return this.fileName;
        }

        public boolean isAdvertise() {
            return this.advertise;
        }

        public boolean isAppend() {
            return this.append;
        }

        public boolean isCreateOnDemand() {
            return this.createOnDemand;
        }

        public boolean isLocking() {
            return this.locking;
        }

        public B withAdvertise(boolean advertise) {
            this.advertise = advertise;
            return (B)((Builder)this.asBuilder());
        }

        public B withAdvertiseUri(String advertiseUri) {
            this.advertiseUri = advertiseUri;
            return (B)((Builder)this.asBuilder());
        }

        public B withAppend(boolean append) {
            this.append = append;
            return (B)((Builder)this.asBuilder());
        }

        public B withFileName(String fileName) {
            this.fileName = fileName;
            return (B)((Builder)this.asBuilder());
        }

        public B withCreateOnDemand(boolean createOnDemand) {
            this.createOnDemand = createOnDemand;
            return (B)((Builder)this.asBuilder());
        }

        public B withLocking(boolean locking) {
            this.locking = locking;
            return (B)((Builder)this.asBuilder());
        }
    }
}

