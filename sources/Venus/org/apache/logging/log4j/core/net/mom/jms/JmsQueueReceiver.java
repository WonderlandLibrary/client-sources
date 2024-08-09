/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.mom.jms;

import org.apache.logging.log4j.core.net.mom.jms.AbstractJmsReceiver;

public class JmsQueueReceiver
extends AbstractJmsReceiver {
    private JmsQueueReceiver() {
    }

    public static void main(String[] stringArray) throws Exception {
        JmsQueueReceiver jmsQueueReceiver = new JmsQueueReceiver();
        jmsQueueReceiver.doMain(stringArray);
    }

    @Override
    protected void usage() {
        System.err.println("Wrong number of arguments.");
        System.err.println("Usage: java " + JmsQueueReceiver.class.getName() + " QueueConnectionFactoryBindingName QueueBindingName username password");
    }
}

