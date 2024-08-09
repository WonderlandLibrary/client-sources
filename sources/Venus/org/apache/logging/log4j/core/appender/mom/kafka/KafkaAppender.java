/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.mom.kafka;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.mom.kafka.KafkaManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.SerializedLayout;
import org.apache.logging.log4j.core.util.StringEncoder;

@Plugin(name="Kafka", category="Core", elementType="appender", printObject=true)
public final class KafkaAppender
extends AbstractAppender {
    private final KafkaManager manager;

    @Deprecated
    public static KafkaAppender createAppender(@PluginElement(value="Layout") Layout<? extends Serializable> layout, @PluginElement(value="Filter") Filter filter, @Required(message="No name provided for KafkaAppender") @PluginAttribute(value="name") String string, @PluginAttribute(value="ignoreExceptions", defaultBoolean=true) boolean bl, @Required(message="No topic provided for KafkaAppender") @PluginAttribute(value="topic") String string2, @PluginElement(value="Properties") Property[] propertyArray, @PluginConfiguration Configuration configuration) {
        KafkaManager kafkaManager = new KafkaManager(configuration.getLoggerContext(), string, string2, true, propertyArray);
        return new KafkaAppender(string, layout, filter, bl, kafkaManager);
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    private KafkaAppender(String string, Layout<? extends Serializable> layout, Filter filter, boolean bl, KafkaManager kafkaManager) {
        super(string, filter, layout, bl);
        this.manager = Objects.requireNonNull(kafkaManager, "manager");
    }

    @Override
    public void append(LogEvent logEvent) {
        if (logEvent.getLoggerName().startsWith("org.apache.kafka")) {
            LOGGER.warn("Recursive logging from [{}] for appender [{}].", (Object)logEvent.getLoggerName(), (Object)this.getName());
        } else {
            try {
                byte[] byArray;
                Layout<? extends Serializable> layout = this.getLayout();
                if (layout != null) {
                    if (layout instanceof SerializedLayout) {
                        byte[] byArray2 = layout.getHeader();
                        byte[] byArray3 = layout.toByteArray(logEvent);
                        byArray = new byte[byArray2.length + byArray3.length];
                        System.arraycopy(byArray2, 0, byArray, 0, byArray2.length);
                        System.arraycopy(byArray3, 0, byArray, byArray2.length, byArray3.length);
                    } else {
                        byArray = layout.toByteArray(logEvent);
                    }
                } else {
                    byArray = StringEncoder.toBytes(logEvent.getMessage().getFormattedMessage(), StandardCharsets.UTF_8);
                }
                this.manager.send(byArray);
            } catch (Exception exception) {
                LOGGER.error("Unable to write to Kafka [{}] for appender [{}].", (Object)this.manager.getName(), (Object)this.getName(), (Object)exception);
                throw new AppenderLoggingException("Unable to write to Kafka in appender: " + exception.getMessage(), exception);
            }
        }
    }

    @Override
    public void start() {
        super.start();
        this.manager.startup();
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        boolean bl = super.stop(l, timeUnit, false);
        this.setStopped();
        return bl &= this.manager.stop(l, timeUnit);
    }

    @Override
    public String toString() {
        return "KafkaAppender{name=" + this.getName() + ", state=" + (Object)((Object)this.getState()) + ", topic=" + this.manager.getTopic() + '}';
    }

    KafkaAppender(String string, Layout layout, Filter filter, boolean bl, KafkaManager kafkaManager, 1 var6_6) {
        this(string, layout, filter, bl, kafkaManager);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder<B extends Builder<B>>
    extends AbstractAppender.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<KafkaAppender> {
        @PluginAttribute(value="topic")
        private String topic;
        @PluginAttribute(value="syncSend", defaultBoolean=true)
        private boolean syncSend;
        @PluginElement(value="Properties")
        private Property[] properties;

        @Override
        public KafkaAppender build() {
            KafkaManager kafkaManager = new KafkaManager(this.getConfiguration().getLoggerContext(), this.getName(), this.topic, this.syncSend, this.properties);
            return new KafkaAppender(this.getName(), this.getLayout(), this.getFilter(), this.isIgnoreExceptions(), kafkaManager, null);
        }

        public String getTopic() {
            return this.topic;
        }

        public Property[] getProperties() {
            return this.properties;
        }

        public B setTopic(String string) {
            this.topic = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setSyncSend(boolean bl) {
            this.syncSend = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setProperties(Property[] propertyArray) {
            this.properties = propertyArray;
            return (B)((Builder)this.asBuilder());
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

