/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.OutputStream;
import java.io.Serializable;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.CloseShieldOutputStream;

@Plugin(name="OutputStream", category="Core", elementType="appender", printObject=true)
public final class OutputStreamAppender
extends AbstractOutputStreamAppender<OutputStreamManager> {
    private static OutputStreamManagerFactory factory = new OutputStreamManagerFactory(null);

    @PluginFactory
    public static OutputStreamAppender createAppender(Layout<? extends Serializable> patternLayout, Filter filter, OutputStream outputStream, String string, boolean bl, boolean bl2) {
        if (string == null) {
            LOGGER.error("No name provided for OutputStreamAppender");
            return null;
        }
        if (patternLayout == null) {
            patternLayout = PatternLayout.createDefaultLayout();
        }
        return new OutputStreamAppender(string, patternLayout, filter, OutputStreamAppender.getManager(outputStream, bl, patternLayout), bl2);
    }

    private static OutputStreamManager getManager(OutputStream outputStream, boolean bl, Layout<? extends Serializable> layout) {
        CloseShieldOutputStream closeShieldOutputStream = new CloseShieldOutputStream(outputStream);
        String string = outputStream.getClass().getName() + "@" + Integer.toHexString(outputStream.hashCode()) + '.' + bl;
        return OutputStreamManager.getManager(string, new FactoryData(closeShieldOutputStream, string, layout), factory);
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    private OutputStreamAppender(String string, Layout<? extends Serializable> layout, Filter filter, OutputStreamManager outputStreamManager, boolean bl) {
        super(string, layout, filter, bl, true, outputStreamManager);
    }

    static OutputStreamManager access$000(OutputStream outputStream, boolean bl, Layout layout) {
        return OutputStreamAppender.getManager(outputStream, bl, layout);
    }

    OutputStreamAppender(String string, Layout layout, Filter filter, OutputStreamManager outputStreamManager, boolean bl, 1 var6_6) {
        this(string, layout, filter, outputStreamManager, bl);
    }

    static class 1 {
    }

    private static class OutputStreamManagerFactory
    implements ManagerFactory<OutputStreamManager, FactoryData> {
        private OutputStreamManagerFactory() {
        }

        @Override
        public OutputStreamManager createManager(String string, FactoryData factoryData) {
            return new OutputStreamManager(FactoryData.access$200(factoryData), FactoryData.access$300(factoryData), FactoryData.access$400(factoryData), true);
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        OutputStreamManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class FactoryData {
        private final Layout<? extends Serializable> layout;
        private final String name;
        private final OutputStream os;

        public FactoryData(OutputStream outputStream, String string, Layout<? extends Serializable> layout) {
            this.os = outputStream;
            this.name = string;
            this.layout = layout;
        }

        static OutputStream access$200(FactoryData factoryData) {
            return factoryData.os;
        }

        static String access$300(FactoryData factoryData) {
            return factoryData.name;
        }

        static Layout access$400(FactoryData factoryData) {
            return factoryData.layout;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<OutputStreamAppender> {
        private Filter filter;
        private boolean follow = false;
        private boolean ignoreExceptions = true;
        private Layout<? extends Serializable> layout = PatternLayout.createDefaultLayout();
        private String name;
        private OutputStream target;

        @Override
        public OutputStreamAppender build() {
            return new OutputStreamAppender(this.name, this.layout, this.filter, OutputStreamAppender.access$000(this.target, this.follow, this.layout), this.ignoreExceptions, null);
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

        public Builder setLayout(Layout<? extends Serializable> layout) {
            this.layout = layout;
            return this;
        }

        public Builder setName(String string) {
            this.name = string;
            return this;
        }

        public Builder setTarget(OutputStream outputStream) {
            this.target = outputStream;
            return this;
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

