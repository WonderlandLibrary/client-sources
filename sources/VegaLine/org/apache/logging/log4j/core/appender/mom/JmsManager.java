/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.ConnectionFactory
 *  javax.jms.Destination
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageConsumer
 *  javax.jms.MessageProducer
 *  javax.jms.Session
 */
package org.apache.logging.log4j.core.appender.mom;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.NamingException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.net.JndiManager;
import org.apache.logging.log4j.status.StatusLogger;

public class JmsManager
extends AbstractManager {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final JmsManagerFactory FACTORY = new JmsManagerFactory();
    private final JndiManager jndiManager;
    private final Connection connection;
    private final Session session;
    private final Destination destination;

    private JmsManager(String name, JndiManager jndiManager, String connectionFactoryName, String destinationName, String username, String password) throws NamingException, JMSException {
        super(null, name);
        this.jndiManager = jndiManager;
        ConnectionFactory connectionFactory = (ConnectionFactory)this.jndiManager.lookup(connectionFactoryName);
        this.connection = username != null && password != null ? connectionFactory.createConnection(username, password) : connectionFactory.createConnection();
        this.session = this.connection.createSession(false, 1);
        this.destination = (Destination)this.jndiManager.lookup(destinationName);
        this.connection.start();
    }

    public static JmsManager getJmsManager(String name, JndiManager jndiManager, String connectionFactoryName, String destinationName, String username, String password) {
        JmsConfiguration configuration = new JmsConfiguration(jndiManager, connectionFactoryName, destinationName, username, password);
        return JmsManager.getManager(name, FACTORY, configuration);
    }

    public MessageConsumer createMessageConsumer() throws JMSException {
        return this.session.createConsumer(this.destination);
    }

    public MessageProducer createMessageProducer() throws JMSException {
        return this.session.createProducer(this.destination);
    }

    public Message createMessage(Serializable object) throws JMSException {
        if (object instanceof String) {
            return this.session.createTextMessage((String)((Object)object));
        }
        return this.session.createObjectMessage(object);
    }

    @Override
    protected boolean releaseSub(long timeout, TimeUnit timeUnit) {
        boolean closed = true;
        try {
            this.session.close();
        } catch (JMSException ignored) {
            closed = false;
        }
        try {
            this.connection.close();
        } catch (JMSException ignored) {
            closed = false;
        }
        return closed && this.jndiManager.stop(timeout, timeUnit);
    }

    private static class JmsManagerFactory
    implements ManagerFactory<JmsManager, JmsConfiguration> {
        private JmsManagerFactory() {
        }

        @Override
        public JmsManager createManager(String name, JmsConfiguration data) {
            try {
                return new JmsManager(name, data.jndiManager, data.connectionFactoryName, data.destinationName, data.username, data.password);
            } catch (Exception e) {
                LOGGER.error("Error creating JmsManager using ConnectionFactory [{}] and Destination [{}].", (Object)data.connectionFactoryName, (Object)data.destinationName, (Object)e);
                return null;
            }
        }
    }

    private static class JmsConfiguration {
        private final JndiManager jndiManager;
        private final String connectionFactoryName;
        private final String destinationName;
        private final String username;
        private final String password;

        private JmsConfiguration(JndiManager jndiManager, String connectionFactoryName, String destinationName, String username, String password) {
            this.jndiManager = jndiManager;
            this.connectionFactoryName = connectionFactoryName;
            this.destinationName = destinationName;
            this.username = username;
            this.password = password;
        }
    }
}

