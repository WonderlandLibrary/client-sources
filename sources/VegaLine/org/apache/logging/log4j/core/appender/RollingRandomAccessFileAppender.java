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
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.RollingRandomAccessFileManager;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.Integers;

@Plugin(name="RollingRandomAccessFile", category="Core", elementType="appender", printObject=true)
public final class RollingRandomAccessFileAppender
extends AbstractOutputStreamAppender<RollingRandomAccessFileManager> {
    private final String fileName;
    private final String filePattern;
    private final Object advertisement;
    private final Advertiser advertiser;

    private RollingRandomAccessFileAppender(String name, Layout<? extends Serializable> layout, Filter filter, RollingRandomAccessFileManager manager, String fileName, String filePattern, boolean ignoreExceptions, boolean immediateFlush, int bufferSize, Advertiser advertiser) {
        super(name, layout, filter, ignoreExceptions, immediateFlush, manager);
        if (advertiser != null) {
            HashMap<String, String> configuration = new HashMap<String, String>(layout.getContentFormat());
            configuration.put("contentType", layout.getContentType());
            configuration.put("name", name);
            this.advertisement = advertiser.advertise(configuration);
        } else {
            this.advertisement = null;
        }
        this.fileName = fileName;
        this.filePattern = filePattern;
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
        RollingRandomAccessFileManager manager = (RollingRandomAccessFileManager)this.getManager();
        manager.checkRollover(event);
        manager.setEndOfBatch(event.isEndOfBatch());
        super.append(event);
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFilePattern() {
        return this.filePattern;
    }

    public int getBufferSize() {
        return ((RollingRandomAccessFileManager)this.getManager()).getBufferSize();
    }

    @Deprecated
    public static <B extends Builder<B>> RollingRandomAccessFileAppender createAppender(String fileName, String filePattern, String append, String name, String immediateFlush, String bufferSizeStr, TriggeringPolicy policy, RolloverStrategy strategy, Layout<? extends Serializable> layout, Filter filter, String ignoreExceptions, String advertise, String advertiseURI, Configuration configuration) {
        boolean isAppend = Booleans.parseBoolean(append, true);
        boolean isIgnoreExceptions = Booleans.parseBoolean(ignoreExceptions, true);
        boolean isImmediateFlush = Booleans.parseBoolean(immediateFlush, true);
        boolean isAdvertise = Boolean.parseBoolean(advertise);
        int bufferSize = Integers.parseInt(bufferSizeStr, 262144);
        return ((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((AbstractFilterable.Builder)((Builder)((Builder)((Builder)((AbstractOutputStreamAppender.Builder)((Builder)((Builder)((Builder)RollingRandomAccessFileAppender.newBuilder()).withAdvertise(isAdvertise)).withAdvertiseURI(advertiseURI)).withAppend(isAppend)).withBufferSize(bufferSize)).setConfiguration(configuration)).withFileName(fileName)).withFilePattern(filePattern)).withFilter(filter)).withIgnoreExceptions(isIgnoreExceptions)).withImmediateFlush(isImmediateFlush)).withLayout(layout)).withName(name)).withPolicy(policy)).withStrategy(strategy)).build();
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    public static class Builder<B extends Builder<B>>
    extends AbstractOutputStreamAppender.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<RollingRandomAccessFileAppender> {
        @PluginBuilderAttribute(value="fileName")
        private String fileName;
        @PluginBuilderAttribute(value="filePattern")
        private String filePattern;
        @PluginBuilderAttribute(value="append")
        private boolean append = true;
        @PluginElement(value="Policy")
        private TriggeringPolicy policy;
        @PluginElement(value="Strategy")
        private RolloverStrategy strategy;
        @PluginBuilderAttribute(value="advertise")
        private boolean advertise;
        @PluginBuilderAttribute(value="advertiseURI")
        private String advertiseURI;

        public Builder() {
            this.withBufferSize(262144);
            this.withIgnoreExceptions(true);
            this.withImmediateFlush(true);
        }

        @Override
        public RollingRandomAccessFileAppender build() {
            int bufferSize;
            String name = this.getName();
            if (name == null) {
                LOGGER.error("No name provided for FileAppender");
                return null;
            }
            if (this.fileName == null) {
                LOGGER.error("No filename was provided for FileAppender with name " + name);
                return null;
            }
            if (this.filePattern == null) {
                LOGGER.error("No filename pattern provided for FileAppender with name " + name);
                return null;
            }
            if (this.policy == null) {
                LOGGER.error("A TriggeringPolicy must be provided");
                return null;
            }
            if (this.strategy == null) {
                this.strategy = DefaultRolloverStrategy.createStrategy(null, null, null, String.valueOf(-1), null, true, this.getConfiguration());
            }
            Layout<Serializable> layout = this.getOrCreateLayout();
            boolean immediateFlush = this.isImmediateFlush();
            RollingRandomAccessFileManager manager = RollingRandomAccessFileManager.getRollingRandomAccessFileManager(this.fileName, this.filePattern, this.append, immediateFlush, bufferSize = this.getBufferSize(), this.policy, this.strategy, this.advertiseURI, layout, this.getConfiguration());
            if (manager == null) {
                return null;
            }
            manager.initialize();
            return new RollingRandomAccessFileAppender(name, layout, this.getFilter(), manager, this.fileName, this.filePattern, this.isIgnoreExceptions(), immediateFlush, bufferSize, this.advertise ? this.getConfiguration().getAdvertiser() : null);
        }

        public B withFileName(String fileName) {
            this.fileName = fileName;
            return (B)((Builder)this.asBuilder());
        }

        public B withFilePattern(String filePattern) {
            this.filePattern = filePattern;
            return (B)((Builder)this.asBuilder());
        }

        public B withAppend(boolean append) {
            this.append = append;
            return (B)((Builder)this.asBuilder());
        }

        public B withPolicy(TriggeringPolicy policy) {
            this.policy = policy;
            return (B)((Builder)this.asBuilder());
        }

        public B withStrategy(RolloverStrategy strategy) {
            this.strategy = strategy;
            return (B)((Builder)this.asBuilder());
        }

        public B withAdvertise(boolean advertise) {
            this.advertise = advertise;
            return (B)((Builder)this.asBuilder());
        }

        public B withAdvertiseURI(String advertiseURI) {
            this.advertiseURI = advertiseURI;
            return (B)((Builder)this.asBuilder());
        }
    }
}

