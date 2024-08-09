/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.jms.JMSException
 *  javax.jms.Message
 *  javax.jms.MessageConsumer
 *  javax.jms.MessageListener
 *  javax.jms.ObjectMessage
 */
package org.apache.logging.log4j.core.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.logging.log4j.LoggingException;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LogEventListener;
import org.apache.logging.log4j.core.appender.mom.JmsManager;
import org.apache.logging.log4j.core.net.JndiManager;

public class JmsServer
extends LogEventListener
implements MessageListener,
LifeCycle2 {
    private final AtomicReference<LifeCycle.State> state = new AtomicReference<LifeCycle.State>(LifeCycle.State.INITIALIZED);
    private final JmsManager jmsManager;
    private MessageConsumer messageConsumer;

    public JmsServer(String string, String string2, String string3, String string4) {
        String string5 = JmsServer.class.getName() + '@' + JmsServer.class.hashCode();
        JndiManager jndiManager = JndiManager.getDefaultManager(string5);
        this.jmsManager = JmsManager.getJmsManager(string5, jndiManager, string, string2, string3, string4);
    }

    @Override
    public LifeCycle.State getState() {
        return this.state.get();
    }

    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                Serializable serializable = ((ObjectMessage)message).getObject();
                if (serializable instanceof LogEvent) {
                    this.log((LogEvent)serializable);
                } else {
                    LOGGER.warn("Expected ObjectMessage to contain LogEvent. Got type {} instead.", (Object)serializable.getClass());
                }
            } else {
                LOGGER.warn("Received message of type {} and JMSType {} which cannot be handled.", (Object)message.getClass(), (Object)message.getJMSType());
            }
        } catch (JMSException jMSException) {
            LOGGER.catching(jMSException);
        }
    }

    @Override
    public void initialize() {
    }

    @Override
    public void start() {
        if (this.state.compareAndSet(LifeCycle.State.INITIALIZED, LifeCycle.State.STARTING)) {
            try {
                this.messageConsumer = this.jmsManager.createMessageConsumer();
                this.messageConsumer.setMessageListener((MessageListener)this);
            } catch (JMSException jMSException) {
                throw new LoggingException(jMSException);
            }
        }
    }

    @Override
    public void stop() {
        this.stop(0L, AbstractLifeCycle.DEFAULT_STOP_TIMEUNIT);
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        boolean bl = true;
        try {
            this.messageConsumer.close();
        } catch (JMSException jMSException) {
            LOGGER.debug("Exception closing {}", (Object)this.messageConsumer, (Object)jMSException);
            bl = false;
        }
        return bl && this.jmsManager.stop(l, timeUnit);
    }

    @Override
    public boolean isStarted() {
        return this.state.get() == LifeCycle.State.STARTED;
    }

    @Override
    public boolean isStopped() {
        return this.state.get() == LifeCycle.State.STOPPED;
    }

    public void run() throws IOException {
        String string;
        this.start();
        System.out.println("Type \"exit\" to quit.");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
        while ((string = bufferedReader.readLine()) != null && !string.equalsIgnoreCase("exit")) {
        }
        System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
        this.stop();
    }
}

