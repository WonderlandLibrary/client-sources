/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.Writer;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.appender.AbstractWriterAppender;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.WriterManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.CloseShieldWriter;

@Plugin(name="Writer", category="Core", elementType="appender", printObject=true)
public final class WriterAppender
extends AbstractWriterAppender<WriterManager> {
    private static WriterManagerFactory factory = new WriterManagerFactory(null);

    @PluginFactory
    public static WriterAppender createAppender(StringLayout stringLayout, Filter filter, Writer writer, String string, boolean bl, boolean bl2) {
        if (string == null) {
            LOGGER.error("No name provided for WriterAppender");
            return null;
        }
        if (stringLayout == null) {
            stringLayout = PatternLayout.createDefaultLayout();
        }
        return new WriterAppender(string, stringLayout, filter, WriterAppender.getManager(writer, bl, stringLayout), bl2);
    }

    private static WriterManager getManager(Writer writer, boolean bl, StringLayout stringLayout) {
        CloseShieldWriter closeShieldWriter = new CloseShieldWriter(writer);
        String string = writer.getClass().getName() + "@" + Integer.toHexString(writer.hashCode()) + '.' + bl;
        return WriterManager.getManager(string, new FactoryData(closeShieldWriter, string, stringLayout), factory);
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    private WriterAppender(String string, StringLayout stringLayout, Filter filter, WriterManager writerManager, boolean bl) {
        super(string, stringLayout, filter, bl, true, writerManager);
    }

    static WriterManager access$000(Writer writer, boolean bl, StringLayout stringLayout) {
        return WriterAppender.getManager(writer, bl, stringLayout);
    }

    WriterAppender(String string, StringLayout stringLayout, Filter filter, WriterManager writerManager, boolean bl, 1 var6_6) {
        this(string, stringLayout, filter, writerManager, bl);
    }

    static class 1 {
    }

    private static class WriterManagerFactory
    implements ManagerFactory<WriterManager, FactoryData> {
        private WriterManagerFactory() {
        }

        @Override
        public WriterManager createManager(String string, FactoryData factoryData) {
            return new WriterManager(FactoryData.access$200(factoryData), FactoryData.access$300(factoryData), FactoryData.access$400(factoryData), true);
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        WriterManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class FactoryData {
        private final StringLayout layout;
        private final String name;
        private final Writer writer;

        public FactoryData(Writer writer, String string, StringLayout stringLayout) {
            this.writer = writer;
            this.name = string;
            this.layout = stringLayout;
        }

        static Writer access$200(FactoryData factoryData) {
            return factoryData.writer;
        }

        static String access$300(FactoryData factoryData) {
            return factoryData.name;
        }

        static StringLayout access$400(FactoryData factoryData) {
            return factoryData.layout;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<WriterAppender> {
        private Filter filter;
        private boolean follow = false;
        private boolean ignoreExceptions = true;
        private StringLayout layout = PatternLayout.createDefaultLayout();
        private String name;
        private Writer target;

        @Override
        public WriterAppender build() {
            return new WriterAppender(this.name, this.layout, this.filter, WriterAppender.access$000(this.target, this.follow, this.layout), this.ignoreExceptions, null);
        }

        public Builder setFilter(Filter filter) {
            this.filter = filter;
            return this;
        }

        public Builder setFollow(boolean bl) {
            this.follow = bl;
            return this;
        }

        public Builder setIgnoreExceptions(boolean bl) {
            this.ignoreExceptions = bl;
            return this;
        }

        public Builder setLayout(StringLayout stringLayout) {
            this.layout = stringLayout;
            return this;
        }

        public Builder setName(String string) {
            this.name = string;
            return this;
        }

        public Builder setTarget(Writer writer) {
            this.target = writer;
            return this;
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

