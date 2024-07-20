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

    protected JmsAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, JmsManager manager) throws JMSException {
        super(name, filter, layout, ignoreExceptions);
        this.manager = manager;
        this.producer = this.manager.createMessageProducer();
    }

    @Override
    public void append(LogEvent event) {
        try {
            Message message = this.manager.createMessage(this.getLayout().toSerializable(event));
            message.setJMSTimestamp(event.getTimeMillis());
            this.producer.send(message);
        } catch (JMSException e) {
            throw new AppenderLoggingException(e);
        }
    }

    @Override
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        boolean stopped = super.stop(timeout, timeUnit, false);
        this.setStopped();
        return stopped &= this.manager.stop(timeout, timeUnit);
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

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

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFactoryName(String factoryName) {
            this.factoryName = factoryName;
            return this;
        }

        public Builder setProviderUrl(String providerUrl) {
            this.providerUrl = providerUrl;
            return this;
        }

        public Builder setUrlPkgPrefixes(String urlPkgPrefixes) {
            this.urlPkgPrefixes = urlPkgPrefixes;
            return this;
        }

        public Builder setSecurityPrincipalName(String securityPrincipalName) {
            this.securityPrincipalName = securityPrincipalName;
            return this;
        }

        public Builder setSecurityCredentials(String securityCredentials) {
            this.securityCredentials = securityCredentials;
            return this;
        }

        public Builder setFactoryBindingName(String factoryBindingName) {
            this.factoryBindingName = factoryBindingName;
            return this;
        }

        public Builder setDestinationBindingName(String destinationBindingName) {
            this.destinationBindingName = destinationBindingName;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
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

        public Builder setIgnoreExceptions(boolean ignoreExceptions) {
            this.ignoreExceptions = ignoreExceptions;
            return this;
        }

        @Override
        public JmsAppender build() {
            JmsManager actualJmsManager = this.jmsManager;
            if (actualJmsManager == null) {
                JndiManager jndiManager = JndiManager.getJndiManager(this.factoryName, this.providerUrl, this.urlPkgPrefixes, this.securityPrincipalName, this.securityCredentials, null);
                actualJmsManager = JmsManager.getJmsManager(this.name, jndiManager, this.factoryBindingName, this.destinationBindingName, this.username, this.password);
            }
            try {
                return new JmsAppender(this.name, this.filter, this.layout, this.ignoreExceptions, actualJmsManager);
            } catch (JMSException e) {
                LOGGER.error("Error creating JmsAppender [{}].", (Object)this.name, (Object)e);
                return null;
            }
        }
    }
}

