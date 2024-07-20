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
    private static ConsoleManagerFactory factory = new ConsoleManagerFactory();
    private static final Target DEFAULT_TARGET = Target.SYSTEM_OUT;
    private static final AtomicInteger COUNT = new AtomicInteger();
    private final Target target;

    private ConsoleAppender(String name, Layout<? extends Serializable> layout, Filter filter, OutputStreamManager manager, boolean ignoreExceptions, Target target) {
        super(name, layout, filter, ignoreExceptions, true, manager);
        this.target = target;
    }

    @Deprecated
    public static ConsoleAppender createAppender(Layout<? extends Serializable> layout, Filter filter, String targetStr, String name, String follow, String ignore) {
        if (name == null) {
            LOGGER.error("No name provided for ConsoleAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        boolean isFollow = Boolean.parseBoolean(follow);
        boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
        Target target = targetStr == null ? DEFAULT_TARGET : Target.valueOf(targetStr);
        return new ConsoleAppender(name, (Layout<? extends Serializable>)layout, filter, ConsoleAppender.getManager(target, isFollow, false, layout), ignoreExceptions, target);
    }

    @Deprecated
    public static ConsoleAppender createAppender(Layout<? extends Serializable> layout, Filter filter, Target target, String name, boolean follow, boolean direct, boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("No name provided for ConsoleAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        Target target2 = target = target == null ? Target.SYSTEM_OUT : target;
        if (follow && direct) {
            LOGGER.error("Cannot use both follow and direct on ConsoleAppender");
            return null;
        }
        return new ConsoleAppender(name, (Layout<? extends Serializable>)layout, filter, ConsoleAppender.getManager(target, follow, direct, layout), ignoreExceptions, target);
    }

    public static ConsoleAppender createDefaultAppenderForLayout(Layout<? extends Serializable> layout) {
        return new ConsoleAppender("DefaultConsole-" + COUNT.incrementAndGet(), layout, null, ConsoleAppender.getDefaultManager(DEFAULT_TARGET, false, false, layout), true, DEFAULT_TARGET);
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    private static OutputStreamManager getDefaultManager(Target target, boolean follow, boolean direct, Layout<? extends Serializable> layout) {
        OutputStream os = ConsoleAppender.getOutputStream(follow, direct, target);
        String managerName = target.name() + '.' + follow + '.' + direct + "-" + COUNT.get();
        return OutputStreamManager.getManager(managerName, new FactoryData(os, managerName, layout), factory);
    }

    private static OutputStreamManager getManager(Target target, boolean follow, boolean direct, Layout<? extends Serializable> layout) {
        OutputStream os = ConsoleAppender.getOutputStream(follow, direct, target);
        String managerName = target.name() + '.' + follow + '.' + direct;
        return OutputStreamManager.getManager(managerName, new FactoryData(os, managerName, layout), factory);
    }

    private static OutputStream getOutputStream(boolean follow, boolean direct, Target target) {
        OutputStream outputStream;
        String enc = Charset.defaultCharset().name();
        try {
            outputStream = target == Target.SYSTEM_OUT ? (direct ? new FileOutputStream(FileDescriptor.out) : (follow ? new PrintStream(new SystemOutStream(), true, enc) : System.out)) : (direct ? new FileOutputStream(FileDescriptor.err) : (follow ? new PrintStream(new SystemErrStream(), true, enc) : System.err));
            outputStream = new CloseShieldOutputStream(outputStream);
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Unsupported default encoding " + enc, ex);
        }
        PropertiesUtil propsUtil = PropertiesUtil.getProperties();
        if (!propsUtil.isOsWindows() || propsUtil.getBooleanProperty("log4j.skipJansi") || direct) {
            return outputStream;
        }
        try {
            Class<?> clazz = LoaderUtil.loadClass(JANSI_CLASS);
            Constructor<?> constructor = clazz.getConstructor(OutputStream.class);
            return new CloseShieldOutputStream((OutputStream)constructor.newInstance(outputStream));
        } catch (ClassNotFoundException cnfe) {
            LOGGER.debug("Jansi is not installed, cannot find {}", (Object)JANSI_CLASS);
        } catch (NoSuchMethodException nsme) {
            LOGGER.warn("{} is missing the proper constructor", (Object)JANSI_CLASS);
        } catch (Exception ex) {
            LOGGER.warn("Unable to instantiate {}", (Object)JANSI_CLASS);
        }
        return outputStream;
    }

    public Target getTarget() {
        return this.target;
    }

    static /* synthetic */ Target access$200() {
        return DEFAULT_TARGET;
    }

    private static class ConsoleManagerFactory
    implements ManagerFactory<OutputStreamManager, FactoryData> {
        private ConsoleManagerFactory() {
        }

        @Override
        public OutputStreamManager createManager(String name, FactoryData data) {
            return new OutputStreamManager(data.os, data.name, data.layout, true);
        }
    }

    private static class FactoryData {
        private final OutputStream os;
        private final String name;
        private final Layout<? extends Serializable> layout;

        public FactoryData(OutputStream os, String type2, Layout<? extends Serializable> layout) {
            this.os = os;
            this.name = type2;
            this.layout = layout;
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
        public void write(byte[] b) throws IOException {
            System.out.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            System.out.write(b, off, len);
        }

        @Override
        public void write(int b) throws IOException {
            System.out.write(b);
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
        public void write(byte[] b) throws IOException {
            System.err.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            System.err.write(b, off, len);
        }

        @Override
        public void write(int b) {
            System.err.write(b);
        }
    }

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

        public B setTarget(Target aTarget) {
            this.target = aTarget;
            return (B)((Builder)this.asBuilder());
        }

        public B setFollow(boolean shouldFollow) {
            this.follow = shouldFollow;
            return (B)((Builder)this.asBuilder());
        }

        public B setDirect(boolean shouldDirect) {
            this.direct = shouldDirect;
            return (B)((Builder)this.asBuilder());
        }

        @Override
        public ConsoleAppender build() {
            if (this.follow && this.direct) {
                throw new IllegalArgumentException("Cannot use both follow and direct on ConsoleAppender '" + this.getName() + "'");
            }
            Layout<Serializable> layout = this.getOrCreateLayout(this.target.getDefaultCharset());
            return new ConsoleAppender(this.getName(), layout, this.getFilter(), ConsoleAppender.getManager(this.target, this.follow, this.direct, layout), this.isIgnoreExceptions(), this.target);
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


        public abstract Charset getDefaultCharset();

        protected Charset getCharset(String property) {
            return new PropertiesUtil(PropertiesUtil.getSystemProperties()).getCharsetProperty(property);
        }
    }
}

