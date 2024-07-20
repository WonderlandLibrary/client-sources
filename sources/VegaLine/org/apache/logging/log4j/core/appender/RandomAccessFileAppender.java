/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.RandomAccessFileManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.Integers;

@Plugin(name="RandomAccessFile", category="Core", elementType="appender", printObject=true)
public final class RandomAccessFileAppender
extends AbstractOutputStreamAppender<RandomAccessFileManager> {
    private final String fileName;
    private Object advertisement;
    private final Advertiser advertiser;

    private RandomAccessFileAppender(String name, Layout<? extends Serializable> layout, Filter filter, RandomAccessFileManager manager, String filename, boolean ignoreExceptions, boolean immediateFlush, Advertiser advertiser) {
        super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
        if (advertiser != null) {
            HashMap<String, String> configuration = new HashMap<String, String>(layout.getContentFormat());
            configuration.putAll(manager.getContentFormat());
            configuration.put("contentType", layout.getContentType());
            configuration.put("name", name);
            this.advertisement = advertiser.advertise(configuration);
        }
        this.fileName = filename;
        this.advertiser = advertiser;
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

    @Override
    public void append(LogEvent event) {
        ((RandomAccessFileManager)this.getManager()).setEndOfBatch(event.isEndOfBatch());
        super.append(event);
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getBufferSize() {
        return ((RandomAccessFileManager)this.getManager()).getBufferSize();
    }

    @Deprecated
    public static <B extends Builder<B>> RandomAccessFileAppender createAppender(String fileName, String append, String name, String immediateFlush, String bufferSizeStr, String ignore, Layout<? extends Serializable> layout, Filter filter, String advertise, String advertiseURI, Configuration configuration) {
        boolean isAppend = Booleans.parseBoolean(append, true);
        boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
        boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
        boolean isAdvertise = Boolean.parseBoolean(advertise);
        int bufferSize = Integers.parseInt(bufferSizeStr, 262144);
        return ((Builder)((Builder)((Builder)((Builder)((Builder)((AbstractFilterable.Builder)((Builder)((Builder)((AbstractOutputStreamAppender.Builder)((Builder)((Builder)((Builder)RandomAccessFileAppender.newBuilder()).setAdvertise(isAdvertise)).setAdvertiseURI(advertiseURI)).setAppend(isAppend)).withBufferSize(bufferSize)).setConfiguration(configuration)).setFileName(fileName)).withFilter(filter)).withIgnoreExceptions(ignoreExceptions)).withImmediateFlush(isFlush)).withLayout(layout)).withName(name)).build();
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    public static class Builder<B extends Builder<B>>
    extends AbstractOutputStreamAppender.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<RandomAccessFileAppender> {
        @PluginBuilderAttribute(value="fileName")
        private String fileName;
        @PluginBuilderAttribute(value="append")
        private boolean append;
        @PluginBuilderAttribute(value="advertise")
        private boolean advertise;
        @PluginBuilderAttribute(value="advertiseURI")
        private String advertiseURI;

        @Override
        public RandomAccessFileAppender build() {
            String name = this.getName();
            if (name == null) {
                LOGGER.error("No name provided for FileAppender");
                return null;
            }
            if (this.fileName == null) {
                LOGGER.error("No filename provided for FileAppender with name " + name);
                return null;
            }
            Layout<Serializable> layout = this.getOrCreateLayout();
            boolean immediateFlush = this.isImmediateFlush();
            RandomAccessFileManager manager = RandomAccessFileManager.getFileManager(this.fileName, this.append, immediateFlush, this.getBufferSize(), this.advertiseURI, layout, null);
            if (manager == null) {
                return null;
            }
            return new RandomAccessFileAppender(name, layout, this.getFilter(), manager, this.fileName, this.isIgnoreExceptions(), immediateFlush, this.advertise ? this.getConfiguration().getAdvertiser() : null);
        }

        public B setFileName(String fileName) {
            this.fileName = fileName;
            return (B)((Builder)this.asBuilder());
        }

        public B setAppend(boolean append) {
            this.append = append;
            return (B)((Builder)this.asBuilder());
        }

        public B setAdvertise(boolean advertise) {
            this.advertise = advertise;
            return (B)((Builder)this.asBuilder());
        }

        public B setAdvertiseURI(String advertiseURI) {
            this.advertiseURI = advertiseURI;
            return (B)((Builder)this.asBuilder());
        }
    }
}

