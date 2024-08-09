/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.mom.jms;

import org.apache.logging.log4j.core.net.mom.jms.AbstractJmsReceiver;

public class JmsTopicReceiver
extends AbstractJmsReceiver {
    private JmsTopicReceiver() {
    }

    public static void main(String[] stringArray) throws Exception {
        JmsTopicReceiver jmsTopicReceiver = new JmsTopicReceiver();
        jmsTopicReceiver.doMain(stringArray);
    }

    @Override
    protected void usage() {
        System.err.println("Wrong number of arguments.");
        System.err.println("Usage: java " + JmsTopicReceiver.class.getName() + " TopicConnectionFactoryBindingName TopicBindingName username password");
    }
}

