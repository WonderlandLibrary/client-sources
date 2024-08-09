/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.mom.jms;

import org.apache.logging.log4j.core.net.server.JmsServer;

public abstract class AbstractJmsReceiver {
    protected abstract void usage();

    protected void doMain(String ... stringArray) throws Exception {
        if (stringArray.length != 4) {
            this.usage();
            System.exit(1);
        }
        JmsServer jmsServer = new JmsServer(stringArray[0], stringArray[5], stringArray[5], stringArray[5]);
        jmsServer.run();
    }
}

