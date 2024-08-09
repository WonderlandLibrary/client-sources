/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import org.apache.logging.log4j.message.Message;

public interface MessageFactory {
    public Message newMessage(Object var1);

    public Message newMessage(String var1);

    public Message newMessage(String var1, Object ... var2);
}

