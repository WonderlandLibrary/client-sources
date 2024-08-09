/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.DirectFileRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.DirectWriteRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.Integers;

@Plugin(name="RollingFile", category="Core", elementType="appender", printObject=true)
public final class RollingFileAppender
extends AbstractOutputStreamAppender<RollingFileManager> {
    public static final String PLUGIN_NAME = "RollingFile";
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private final String fileName;
    private final String filePattern;
    private Object advertisement;
    private final Advertiser advertiser;

    private RollingFileAppender(String string, Layout<? extends Serializable> layout, Filter filter, RollingFileManager rollingFileManager, String string2, String string3, boolean bl, boolean bl2, Advertiser advertiser) {
        super(string, layout, filter, bl, bl2, rollingFileManager);
        if (advertiser != null) {
            HashMap<String, String> hashMap = new HashMap<String, String>(layout.getContentFormat());
            hashMap.put("contentType", layout.getContentType());
            hashMap.put("name", string);
            this.advertisement = advertiser.advertise(hashMap);
        }
        this.fileName = string2;
        this.filePattern = string3;
        this.advertiser = advertiser;
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        boolean bl = super.stop(l, timeUnit, false);
        if (this.advertiser != null) {
            this.advertiser.unadvertise(this.advertisement);
        }
        this.setStopped();
        return bl;
    }

    @Override
    public void append(LogEvent logEvent) {
        ((RollingFileManager)this.getManager()).checkRollover(logEvent);
        super.append(logEvent);
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFilePattern() {
        return this.filePattern;
    }

    public <T extends TriggeringPolicy> T getTriggeringPolicy() {
        return ((RollingFileManager)this.getManager()).getTriggeringPolicy();
    }

    @Deprecated
    public static <B extends Builder<B>> RollingFileAppender createAppender(String string, String string2, String string3, String string4, String string5, String string6, String string7, TriggeringPolicy triggeringPolicy, RolloverStrategy rolloverStrategy, Layout<? extends Serializable> layout, Filter filter, String string8, String string9, String string10, Configuration configuration) {
        int n = Integers.parseInt(string6, 8192);
        return ((Builder)((Builder)((Builder)((AbstractAppender.Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((AbstractFilterable.Builder)((Builder)((Builder)((Builder)((Builder)((AbstractOutputStreamAppender.Builder)((Builder)((Builder)((Builder)RollingFileAppender.newBuilder()).withAdvertise(Boolean.parseBoolean(string9))).withAdvertiseUri(string10)).withAppend(Booleans.parseBoolean(string3, true))).withBufferedIo(Booleans.parseBoolean(string5, true))).withBufferSize(n)).setConfiguration(configuration)).withFileName(string)).withFilePattern(string2)).withFilter(filter)).withIgnoreExceptions(Booleans.parseBoolean(string8, true))).withImmediateFlush(Booleans.parseBoolean(string7, true))).withLayout(layout)).withCreateOnDemand(true)).withLocking(true)).withName(string4)).withPolicy(triggeringPolicy)).withStrategy(rolloverStrategy)).build();
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    static Logger access$000() {
        return LOGGER;
    }

    static Logger access$100() {
        return LOGGER;
    }

    static Logger access$200() {
        return LOGGER;
    }

    static Logger access$300() {
        return LOGGER;
    }

    static Logger access$400() {
        return LOGGER;
    }

    RollingFileAppender(String string, Layout layout, Filter filter, RollingFileManager rollingFileManager, String string2, String string3, boolean bl, boolean bl2, Advertiser advertiser, 1 var10_10) {
        this(string, layout, filter, rollingFileManager, string2, string3, bl, bl2, advertiser);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder<B extends Builder<B>>
    extends AbstractOutputStreamAppender.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<RollingFileAppender> {
        @PluginBuilderAttribute
        private String fileName;
        @PluginBuilderAttribute
        @Required
        private String filePattern;
        @PluginBuilderAttribute
        private boolean append = true;
        @PluginBuilderAttribute
        private boolean locking;
        @PluginElement(value="Policy")
        @Required
        private TriggeringPolicy policy;
        @PluginElement(value="Strategy")
        private RolloverStrategy strategy;
        @PluginBuilderAttribute
        private boolean advertise;
        @PluginBuilderAttribute
        private String advertiseUri;
        @PluginBuilderAttribute
        private boolean createOnDemand;

        @Override
        public RollingFileAppender build() {
            boolean bl = this.isBufferedIo();
            int n = this.getBufferSize();
            if (this.getName() == null) {
                RollingFileAppender.access$000().error("RollingFileAppender '{}': No name provided.", (Object)this.getName());
                return null;
            }
            if (!bl && n > 0) {
                RollingFileAppender.access$100().warn("RollingFileAppender '{}': The bufferSize is set to {} but bufferedIO is not true", (Object)this.getName(), (Object)n);
            }
            if (this.filePattern == null) {
                RollingFileAppender.access$200().error("RollingFileAppender '{}': No file name pattern provided.", (Object)this.getName());
                return null;
            }
            if (this.policy == null) {
                RollingFileAppender.access$300().error("RollingFileAppender '{}': No TriggeringPolicy provided.", (Object)this.getName());
                return null;
            }
            if (this.strategy == null) {
                this.strategy = this.fileName != null ? DefaultRolloverStrategy.createStrategy(null, null, null, String.valueOf(-1), null, true, this.getConfiguration()) : DirectWriteRolloverStrategy.createStrategy(null, String.valueOf(-1), null, true, this.getConfiguration());
            } else if (this.fileName == null && !(this.strategy instanceof DirectFileRolloverStrategy)) {
                RollingFileAppender.access$400().error("RollingFileAppender '{}': When no file name is provided a DirectFilenameRolloverStrategy must be configured");
                return null;
            }
            Layout<Serializable> layout = this.getOrCreateLayout();
            RollingFileManager rollingFileManager = RollingFileManager.getFileManager(this.fileName, this.filePattern, this.append, bl, this.policy, this.strategy, this.advertiseUri, layout, n, this.isImmediateFlush(), this.createOnDemand, this.getConfiguration());
            if (rollingFileManager == null) {
                return null;
            }
            rollingFileManager.initialize();
            return new RollingFileAppender(this.getName(), layout, this.getFilter(), rollingFileManager, this.fileName, this.filePattern, this.isIgnoreExceptions(), this.isImmediateFlush(), this.advertise ? this.getConfiguration().getAdvertiser() : null, null);
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

        public B withAdvertise(boolean bl) {
            this.advertise = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B withAdvertiseUri(String string) {
            this.advertiseUri = string;
            return (B)((Builder)this.asBuilder());
        }

        public B withAppend(boolean bl) {
            this.append = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B withFileName(String string) {
            this.fileName = string;
            return (B)((Builder)this.asBuilder());
        }

        public B withCreateOnDemand(boolean bl) {
            this.createOnDemand = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B withLocking(boolean bl) {
            this.locking = bl;
            return (B)((Builder)this.asBuilder());
        }

        public String getFilePattern() {
            return this.filePattern;
        }

        public TriggeringPolicy getPolicy() {
            return this.policy;
        }

        public RolloverStrategy getStrategy() {
            return this.strategy;
        }

        public B withFilePattern(String string) {
            this.filePattern = string;
            return (B)((Builder)this.asBuilder());
        }

        public B withPolicy(TriggeringPolicy triggeringPolicy) {
            this.policy = triggeringPolicy;
            return (B)((Builder)this.asBuilder());
        }

        public B withStrategy(RolloverStrategy rolloverStrategy) {
            this.strategy = rolloverStrategy;
            return (B)((Builder)this.asBuilder());
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

