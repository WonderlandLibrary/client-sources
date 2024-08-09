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
import org.apache.logging.log4j.core.appender.MemoryMappedFileManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.filter.AbstractFilterable;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.Integers;

@Plugin(name="MemoryMappedFile", category="Core", elementType="appender", printObject=true)
public final class MemoryMappedFileAppender
extends AbstractOutputStreamAppender<MemoryMappedFileManager> {
    private static final int BIT_POSITION_1GB = 30;
    private static final int MAX_REGION_LENGTH = 0x40000000;
    private static final int MIN_REGION_LENGTH = 256;
    private final String fileName;
    private Object advertisement;
    private final Advertiser advertiser;

    private MemoryMappedFileAppender(String string, Layout<? extends Serializable> layout, Filter filter, MemoryMappedFileManager memoryMappedFileManager, String string2, boolean bl, boolean bl2, Advertiser advertiser) {
        super(string, layout, filter, bl, bl2, memoryMappedFileManager);
        if (advertiser != null) {
            HashMap<String, String> hashMap = new HashMap<String, String>(layout.getContentFormat());
            hashMap.putAll(memoryMappedFileManager.getContentFormat());
            hashMap.put("contentType", layout.getContentType());
            hashMap.put("name", string);
            this.advertisement = advertiser.advertise(hashMap);
        }
        this.fileName = string2;
        this.advertiser = advertiser;
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        super.stop(l, timeUnit, false);
        if (this.advertiser != null) {
            this.advertiser.unadvertise(this.advertisement);
        }
        this.setStopped();
        return false;
    }

    @Override
    public void append(LogEvent logEvent) {
        ((MemoryMappedFileManager)this.getManager()).setEndOfBatch(logEvent.isEndOfBatch());
        super.append(logEvent);
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getRegionLength() {
        return ((MemoryMappedFileManager)this.getManager()).getRegionLength();
    }

    @Deprecated
    public static <B extends Builder<B>> MemoryMappedFileAppender createAppender(String string, String string2, String string3, String string4, String string5, String string6, Layout<? extends Serializable> layout, Filter filter, String string7, String string8, Configuration configuration) {
        boolean bl = Booleans.parseBoolean(string2, true);
        boolean bl2 = Booleans.parseBoolean(string4, false);
        boolean bl3 = Booleans.parseBoolean(string6, true);
        boolean bl4 = Boolean.parseBoolean(string7);
        int n = Integers.parseInt(string5, 0x2000000);
        return ((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((AbstractFilterable.Builder)((Builder)((AbstractAppender.Builder)((Builder)((Builder)((Builder)MemoryMappedFileAppender.newBuilder()).setAdvertise(bl4)).setAdvertiseURI(string8)).setAppend(bl)).setConfiguration(configuration)).setFileName(string)).withFilter(filter)).withIgnoreExceptions(bl3)).withImmediateFlush(bl2)).withLayout(layout)).withName(string3)).setRegionLength(n)).build();
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    private static int determineValidRegionLength(String string, int n) {
        if (n > 0x40000000) {
            LOGGER.info("MemoryMappedAppender[{}] Reduced region length from {} to max length: {}", (Object)string, (Object)n, (Object)0x40000000);
            return 1;
        }
        if (n < 256) {
            LOGGER.info("MemoryMappedAppender[{}] Expanded region length from {} to min length: {}", (Object)string, (Object)n, (Object)256);
            return 1;
        }
        int n2 = Integers.ceilingNextPowerOfTwo(n);
        if (n != n2) {
            LOGGER.info("MemoryMappedAppender[{}] Rounded up region length from {} to next power of two: {}", (Object)string, (Object)n, (Object)n2);
        }
        return n2;
    }

    static int access$000(String string, int n) {
        return MemoryMappedFileAppender.determineValidRegionLength(string, n);
    }

    static Logger access$100() {
        return LOGGER;
    }

    static Logger access$200() {
        return LOGGER;
    }

    MemoryMappedFileAppender(String string, Layout layout, Filter filter, MemoryMappedFileManager memoryMappedFileManager, String string2, boolean bl, boolean bl2, Advertiser advertiser, 1 var9_9) {
        this(string, layout, filter, memoryMappedFileManager, string2, bl, bl2, advertiser);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder<B extends Builder<B>>
    extends AbstractOutputStreamAppender.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<MemoryMappedFileAppender> {
        @PluginBuilderAttribute(value="fileName")
        private String fileName;
        @PluginBuilderAttribute(value="append")
        private boolean append = true;
        @PluginBuilderAttribute(value="regionLength")
        private int regionLength = 0x2000000;
        @PluginBuilderAttribute(value="advertise")
        private boolean advertise;
        @PluginBuilderAttribute(value="advertiseURI")
        private String advertiseURI;

        @Override
        public MemoryMappedFileAppender build() {
            String string = this.getName();
            int n = MemoryMappedFileAppender.access$000(string, this.regionLength);
            if (string == null) {
                MemoryMappedFileAppender.access$100().error("No name provided for MemoryMappedFileAppender");
                return null;
            }
            if (this.fileName == null) {
                MemoryMappedFileAppender.access$200().error("No filename provided for MemoryMappedFileAppender with name " + string);
                return null;
            }
            Layout<Serializable> layout = this.getOrCreateLayout();
            MemoryMappedFileManager memoryMappedFileManager = MemoryMappedFileManager.getFileManager(this.fileName, this.append, this.isImmediateFlush(), n, this.advertiseURI, layout);
            if (memoryMappedFileManager == null) {
                return null;
            }
            return new MemoryMappedFileAppender(string, layout, this.getFilter(), memoryMappedFileManager, this.fileName, this.isIgnoreExceptions(), false, this.advertise ? this.getConfiguration().getAdvertiser() : null, null);
        }

        public B setFileName(String string) {
            this.fileName = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setAppend(boolean bl) {
            this.append = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setRegionLength(int n) {
            this.regionLength = n;
            return (B)((Builder)this.asBuilder());
        }

        public B setAdvertise(boolean bl) {
            this.advertise = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setAdvertiseURI(String string) {
            this.advertiseURI = string;
            return (B)((Builder)this.asBuilder());
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

