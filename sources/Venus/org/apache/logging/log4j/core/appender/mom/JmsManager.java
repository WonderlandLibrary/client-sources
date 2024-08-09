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
    private static final JmsManagerFactory FACTORY = new JmsManagerFactory(null);
    private final JndiManager jndiManager;
    private final Connection connection;
    private final Session session;
    private final Destination destination;

    private JmsManager(String string, JndiManager jndiManager, String string2, String string3, String string4, String string5) throws NamingException, JMSException {
        super(null, string);
        this.jndiManager = jndiManager;
        ConnectionFactory connectionFactory = (ConnectionFactory)this.jndiManager.lookup(string2);
        this.connection = string4 != null && string5 != null ? connectionFactory.createConnection(string4, string5) : connectionFactory.createConnection();
        this.session = this.connection.createSession(false, 1);
        this.destination = (Destination)this.jndiManager.lookup(string3);
        this.connection.start();
    }

    public static JmsManager getJmsManager(String string, JndiManager jndiManager, String string2, String string3, String string4, String string5) {
        JmsConfiguration jmsConfiguration = new JmsConfiguration(jndiManager, string2, string3, string4, string5, null);
        return JmsManager.getManager(string, FACTORY, jmsConfiguration);
    }

    public MessageConsumer createMessageConsumer() throws JMSException {
        return this.session.createConsumer(this.destination);
    }

    public MessageProducer createMessageProducer() throws JMSException {
        return this.session.createProducer(this.destination);
    }

    public Message createMessage(Serializable serializable) throws JMSException {
        if (serializable instanceof String) {
            return this.session.createTextMessage((String)((Object)serializable));
        }
        return this.session.createObjectMessage(serializable);
    }

    @Override
    protected boolean releaseSub(long l, TimeUnit timeUnit) {
        boolean bl = true;
        try {
            this.session.close();
        } catch (JMSException jMSException) {
            bl = false;
        }
        try {
            this.connection.close();
        } catch (JMSException jMSException) {
            bl = false;
        }
        return bl && this.jndiManager.stop(l, timeUnit);
    }

    JmsManager(String string, JndiManager jndiManager, String string2, String string3, String string4, String string5, 1 var7_7) throws NamingException, JMSException {
        this(string, jndiManager, string2, string3, string4, string5);
    }

    static Logger access$800() {
        return LOGGER;
    }

    static class 1 {
    }

    private static class JmsManagerFactory
    implements ManagerFactory<JmsManager, JmsConfiguration> {
        private JmsManagerFactory() {
        }

        @Override
        public JmsManager createManager(String string, JmsConfiguration jmsConfiguration) {
            try {
                return new JmsManager(string, JmsConfiguration.access$200(jmsConfiguration), JmsConfiguration.access$300(jmsConfiguration), JmsConfiguration.access$400(jmsConfiguration), JmsConfiguration.access$500(jmsConfiguration), JmsConfiguration.access$600(jmsConfiguration), null);
            } catch (Exception exception) {
                JmsManager.access$800().error("Error creating JmsManager using ConnectionFactory [{}] and Destination [{}].", (Object)JmsConfiguration.access$300(jmsConfiguration), (Object)JmsConfiguration.access$400(jmsConfiguration), (Object)exception);
                return null;
            }
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (JmsConfiguration)object);
        }

        JmsManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class JmsConfiguration {
        private final JndiManager jndiManager;
        private final String connectionFactoryName;
        private final String destinationName;
        private final String username;
        private final String password;

        private JmsConfiguration(JndiManager jndiManager, String string, String string2, String string3, String string4) {
            this.jndiManager = jndiManager;
            this.connectionFactoryName = string;
            this.destinationName = string2;
            this.username = string3;
            this.password = string4;
        }

        JmsConfiguration(JndiManager jndiManager, String string, String string2, String string3, String string4, 1 var6_6) {
            this(jndiManager, string, string2, string3, string4);
        }

        static JndiManager access$200(JmsConfiguration jmsConfiguration) {
            return jmsConfiguration.jndiManager;
        }

        static String access$300(JmsConfiguration jmsConfiguration) {
            return jmsConfiguration.connectionFactoryName;
        }

        static String access$400(JmsConfiguration jmsConfiguration) {
            return jmsConfiguration.destinationName;
        }

        static String access$500(JmsConfiguration jmsConfiguration) {
            return jmsConfiguration.username;
        }

        static String access$600(JmsConfiguration jmsConfiguration) {
            return jmsConfiguration.password;
        }
    }
}

