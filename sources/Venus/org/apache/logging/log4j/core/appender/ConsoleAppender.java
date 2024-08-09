/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.core.util.CloseShieldOutputStream;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

@Plugin(name="Console", category="Core", elementType="appender", printObject=true)
public final class ConsoleAppender
extends AbstractOutputStreamAppender<OutputStreamManager> {
    public static final String PLUGIN_NAME = "Console";
    private static final String JANSI_CLASS = "org.fusesource.jansi.WindowsAnsiOutputStream";
    private static ConsoleManagerFactory factory = new ConsoleManagerFactory(null);
    private static final Target DEFAULT_TARGET = Target.SYSTEM_OUT;
    private static final AtomicInteger COUNT = new AtomicInteger();
    private final Target target;

    private ConsoleAppender(String string, Layout<? extends Serializable> layout, Filter filter, OutputStreamManager outputStreamManager, boolean bl, Target target) {
        super(string, layout, filter, bl, true, outputStreamManager);
        this.target = target;
    }

    @Deprecated
    public static ConsoleAppender createAppender(Layout<? extends Serializable> patternLayout, Filter filter, String string, String string2, String string3, String string4) {
        if (string2 == null) {
            LOGGER.error("No name provided for ConsoleAppender");
            return null;
        }
        if (patternLayout == null) {
            patternLayout = PatternLayout.createDefaultLayout();
        }
        boolean bl = Boolean.parseBoolean(string3);
        boolean bl2 = Booleans.parseBoolean(string4, true);
        Target target = string == null ? DEFAULT_TARGET : Target.valueOf(string);
        return new ConsoleAppender(string2, (Layout<? extends Serializable>)patternLayout, filter, ConsoleAppender.getManager(target, bl, false, patternLayout), bl2, target);
    }

    @Deprecated
    public static ConsoleAppender createAppender(Layout<? extends Serializable> patternLayout, Filter filter, Target target, String string, boolean bl, boolean bl2, boolean bl3) {
        if (string == null) {
            LOGGER.error("No name provided for ConsoleAppender");
            return null;
        }
        if (patternLayout == null) {
            patternLayout = PatternLayout.createDefaultLayout();
        }
        Target target2 = target = target == null ? Target.SYSTEM_OUT : target;
        if (bl && bl2) {
            LOGGER.error("Cannot use both follow and direct on ConsoleAppender");
            return null;
        }
        return new ConsoleAppender(string, (Layout<? extends Serializable>)patternLayout, filter, ConsoleAppender.getManager(target, bl, bl2, patternLayout), bl3, target);
    }

    public static ConsoleAppender createDefaultAppenderForLayout(Layout<? extends Serializable> layout) {
        return new ConsoleAppender("DefaultConsole-" + COUNT.incrementAndGet(), layout, null, ConsoleAppender.getDefaultManager(DEFAULT_TARGET, false, false, layout), true, DEFAULT_TARGET);
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    private static OutputStreamManager getDefaultManager(Target target, boolean bl, boolean bl2, Layout<? extends Serializable> layout) {
        OutputStream outputStream = ConsoleAppender.getOutputStream(bl, bl2, target);
        String string = target.name() + '.' + bl + '.' + bl2 + "-" + COUNT.get();
        return OutputStreamManager.getManager(string, new FactoryData(outputStream, string, layout), factory);
    }

    private static OutputStreamManager getManager(Target target, boolean bl, boolean bl2, Layout<? extends Serializable> layout) {
        OutputStream outputStream = ConsoleAppender.getOutputStream(bl, bl2, target);
        String string = target.name() + '.' + bl + '.' + bl2;
        return OutputStreamManager.getManager(string, new FactoryData(outputStream, string, layout), factory);
    }

    private static OutputStream getOutputStream(boolean bl, boolean bl2, Target target) {
        OutputStream outputStream;
        String string = Charset.defaultCharset().name();
        try {
            outputStream = target == Target.SYSTEM_OUT ? (bl2 ? new FileOutputStream(FileDescriptor.out) : (bl ? new PrintStream(new SystemOutStream(), true, string) : System.out)) : (bl2 ? new FileOutputStream(FileDescriptor.err) : (bl ? new PrintStream(new SystemErrStream(), true, string) : System.err));
            outputStream = new CloseShieldOutputStream(outputStream);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalStateException("Unsupported default encoding " + string, unsupportedEncodingException);
        }
        PropertiesUtil propertiesUtil = PropertiesUtil.getProperties();
        if (!propertiesUtil.isOsWindows() || propertiesUtil.getBooleanProperty("log4j.skipJansi") || bl2) {
            return outputStream;
        }
        try {
            Class<?> clazz = LoaderUtil.loadClass(JANSI_CLASS);
            Constructor<?> constructor = clazz.getConstructor(OutputStream.class);
            return new CloseShieldOutputStream((OutputStream)constructor.newInstance(outputStream));
        } catch (ClassNotFoundException classNotFoundException) {
            LOGGER.debug("Jansi is not installed, cannot find {}", (Object)JANSI_CLASS);
        } catch (NoSuchMethodException noSuchMethodException) {
            LOGGER.warn("{} is missing the proper constructor", (Object)JANSI_CLASS);
        } catch (Exception exception) {
            LOGGER.warn("Unable to instantiate {}", (Object)JANSI_CLASS);
        }
        return outputStream;
    }

    public Target getTarget() {
        return this.target;
    }

    static Target access$200() {
        return DEFAULT_TARGET;
    }

    static OutputStreamManager access$300(Target target, boolean bl, boolean bl2, Layout layout) {
        return ConsoleAppender.getManager(target, bl, bl2, layout);
    }

    ConsoleAppender(String string, Layout layout, Filter filter, OutputStreamManager outputStreamManager, boolean bl, Target target, 1 var7_7) {
        this(string, (Layout<? extends Serializable>)layout, filter, outputStreamManager, bl, target);
    }

    static class 1 {
    }

    private static class ConsoleManagerFactory
    implements ManagerFactory<OutputStreamManager, FactoryData> {
        private ConsoleManagerFactory() {
        }

        @Override
        public OutputStreamManager createManager(String string, FactoryData factoryData) {
            return new OutputStreamManager(FactoryData.access$500(factoryData), FactoryData.access$600(factoryData), FactoryData.access$700(factoryData), true);
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        ConsoleManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class FactoryData {
        private final OutputStream os;
        private final String name;
        private final Layout<? extends Serializable> layout;

        public FactoryData(OutputStream outputStream, String string, Layout<? extends Serializable> layout) {
            this.os = outputStream;
            this.name = string;
            this.layout = layout;
        }

        static OutputStream access$500(FactoryData factoryData) {
            return factoryData.os;
        }

        static String access$600(FactoryData factoryData) {
            return factoryData.name;
        }

        static Layout access$700(FactoryData factoryData) {
            return factoryData.layout;
        }
    }

    private static class SystemOutStream
    extends OutputStream {
        @Override
        public void close() {
        }

        @Override
        public void flush() {
            System.out.flush();
        }

        @Override
        public void write(byte[] byArray) throws IOException {
            System.out.write(byArray);
        }

        @Override
        public void write(byte[] byArray, int n, int n2) throws IOException {
            System.out.write(byArray, n, n2);
        }

        @Override
        public void write(int n) throws IOException {
            System.out.write(n);
        }
    }

    private static class SystemErrStream
    extends OutputStream {
        @Override
        public void close() {
        }

        @Override
        public void flush() {
            System.err.flush();
        }

        @Override
        public void write(byte[] byArray) throws IOException {
            System.err.write(byArray);
        }

        @Override
        public void write(byte[] byArray, int n, int n2) throws IOException {
            System.err.write(byArray, n, n2);
        }

        @Override
        public void write(int n) {
            System.err.write(n);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder<B extends Builder<B>>
    extends AbstractOutputStreamAppender.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<ConsoleAppender> {
        @PluginBuilderAttribute
        @Required
        private Target target = ConsoleAppender.access$200();
        @PluginBuilderAttribute
        private boolean follow;
        @PluginBuilderAttribute
        private boolean direct;

        public B setTarget(Target target) {
            this.target = target;
            return (B)((Builder)this.asBuilder());
        }

        public B setFollow(boolean bl) {
            this.follow = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setDirect(boolean bl) {
            this.direct = bl;
            return (B)((Builder)this.asBuilder());
        }

        @Override
        public ConsoleAppender build() {
            if (this.follow && this.direct) {
                throw new IllegalArgumentException("Cannot use both follow and direct on ConsoleAppender '" + this.getName() + "'");
            }
            Layout<Serializable> layout = this.getOrCreateLayout(this.target.getDefaultCharset());
            return new ConsoleAppender(this.getName(), layout, this.getFilter(), ConsoleAppender.access$300(this.target, this.follow, this.direct, layout), this.isIgnoreExceptions(), this.target, null);
        }

        @Override
        public Object build() {
            return this.build();
        }
    }

    public static enum Target {
        SYSTEM_OUT{

            @Override
            public Charset getDefaultCharset() {
                return this.getCharset("sun.stdout.encoding");
            }
        }
        ,
        SYSTEM_ERR{

            @Override
            public Charset getDefaultCharset() {
                return this.getCharset("sun.stderr.encoding");
            }
        };


        private Target() {
        }

        public abstract Charset getDefaultCharset();

        protected Charset getCharset(String string) {
            return new PropertiesUtil(PropertiesUtil.getSystemProperties()).getCharsetProperty(string);
        }

        Target(1 var3_3) {
            this();
        }
    }
}

