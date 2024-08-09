/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageProducer
 */
package org.apache.logging.log4j.core.appender.mom;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.mom.JmsManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.SerializedLayout;
import org.apache.logging.log4j.core.net.JndiManager;

@Plugin(name="JMS", category="Core", elementType="appender", printObject=true)
@PluginAliases(value={"JMSQueue", "JMSTopic"})
public class JmsAppender
extends AbstractAppender {
    private final JmsManager manager;
    private final MessageProducer producer;

    protected JmsAppender(String string, Filter filter, Layout<? extends Serializable> layout, boolean bl, JmsManager jmsManager) throws JMSException {
        super(string, filter, layout, bl);
        this.manager = jmsManager;
        this.producer = this.manager.createMessageProducer();
    }

    @Override
    public void append(LogEvent logEvent) {
        try {
            Message message = this.manager.createMessage(this.getLayout().toSerializable(logEvent));
            message.setJMSTimestamp(logEvent.getTimeMillis());
            this.producer.send(message);
        } catch (JMSException jMSException) {
            throw new AppenderLoggingException(jMSException);
        }
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        boolean bl = super.stop(l, timeUnit, false);
        this.setStopped();
        return bl &= this.manager.stop(l, timeUnit);
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder(null);
    }

    static Logger access$100() {
        return LOGGER;
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<JmsAppender> {
        @PluginBuilderAttribute
        @Required(message="A name for the JmsAppender must be specified")
        private String name;
        @PluginBuilderAttribute
        private String factoryName;
        @PluginBuilderAttribute
        private String providerUrl;
        @PluginBuilderAttribute
        private String urlPkgPrefixes;
        @PluginBuilderAttribute
        private String securityPrincipalName;
        @PluginBuilderAttribute(sensitive=true)
        private String securityCredentials;
        @PluginBuilderAttribute
        @Required(message="A javax.jms.ConnectionFactory JNDI name must be specified")
        private String factoryBindingName;
        @PluginBuilderAttribute
        @PluginAliases(value={"queueBindingName", "topicBindingName"})
        @Required(message="A javax.jms.Destination JNDI name must be specified")
        private String destinationBindingName;
        @PluginBuilderAttribute
        private String username;
        @PluginBuilderAttribute(sensitive=true)
        private String password;
        @PluginElement(value="Layout")
        private Layout<? extends Serializable> layout = SerializedLayout.createLayout();
        @PluginElement(value="Filter")
        private Filter filter;
        @PluginBuilderAttribute
        private boolean ignoreExceptions = true;
        private JmsManager jmsManager;

        private Builder() {
        }

        public Builder setName(String string) {
            this.name = string;
            return this;
        }

        public Builder setFactoryName(String string) {
            this.factoryName = string;
            return this;
        }

        public Builder setProviderUrl(String string) {
            this.providerUrl = string;
            return this;
        }

        public Builder setUrlPkgPrefixes(String string) {
            this.urlPkgPrefixes = string;
            return this;
        }

        public Builder setSecurityPrincipalName(String string) {
            this.securityPrincipalName = string;
            return this;
        }

        public Builder setSecurityCredentials(String string) {
            this.securityCredentials = string;
            return this;
        }

        public Builder setFactoryBindingName(String string) {
            this.factoryBindingName = string;
            return this;
        }

        public Builder setDestinationBindingName(String string) {
            this.destinationBindingName = string;
            return this;
        }

        public Builder setUsername(String string) {
            this.username = string;
            return this;
        }

        public Builder setPassword(String string) {
            this.password = string;
            return this;
        }

        public Builder setLayout(Layout<? extends Serializable> layout) {
            this.layout = layout;
            return this;
        }

        public Builder setFilter(Filter filter) {
            this.filter = filter;
            return this;
        }

        public Builder setJmsManager(JmsManager jmsManager) {
            this.jmsManager = jmsManager;
            return this;
        }

        public Builder setIgnoreExceptions(boolean bl) {
            this.ignoreExceptions = bl;
            return this;
        }

        @Override
        public JmsAppender build() {
            JmsManager jmsManager = this.jmsManager;
            if (jmsManager == null) {
                JndiManager jndiManager = JndiManager.getJndiManager(this.factoryName, this.providerUrl, this.urlPkgPrefixes, this.securityPrincipalName, this.securityCredentials, null);
                jmsManager = JmsManager.getJmsManager(this.name, jndiManager, this.factoryBindingName, this.destinationBindingName, this.username, this.password);
            }
            try {
                return new JmsAppender(this.name, this.filter, this.layout, this.ignoreExceptions, jmsManager);
            } catch (JMSException jMSException) {
                JmsAppender.access$100().error("Error creating JmsAppender [{}].", (Object)this.name, (Object)jMSException);
                return null;
            }
        }

        @Override
        public Object build() {
            return this.build();
        }

        Builder(1 var1_1) {
            this();
        }
    }
}

