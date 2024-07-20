/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.Serializable;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory2;
import org.apache.logging.log4j.message.ObjectMessage;
import org.apache.logging.log4j.message.SimpleMessage;

public abstract class AbstractMessageFactory
implements MessageFactory2,
Serializable {
    private static final long serialVersionUID = -1307891137684031187L;

    @Override
    public Message newMessage(CharSequence message) {
        return new SimpleMessage(message);
    }

    @Override
    public Message newMessage(Object message) {
        return new ObjectMessage(message);
    }

    @Override
    public Message newMessage(String message) {
        return new SimpleMessage(message);
    }

    @Override
    public Message newMessage(String message, Object p0) {
        return this.newMessage(message, new Object[]{p0});
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1) {
        return this.newMessage(message, new Object[]{p0, p1});
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2) {
        return this.newMessage(message, new Object[]{p0, p1, p2});
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3) {
        return this.newMessage(message, new Object[]{p0, p1, p2, p3});
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return this.newMessage(message, new Object[]{p0, p1, p2, p3, p4});
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return this.newMessage(message, new Object[]{p0, p1, p2, p3, p4, p5});
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return this.newMessage(message, new Object[]{p0, p1, p2, p3, p4, p5, p6});
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return this.newMessage(message, new Object[]{p0, p1, p2, p3, p4, p5, p6, p7});
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return this.newMessage(message, new Object[]{p0, p1, p2, p3, p4, p5, p6, p7, p8});
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return this.newMessage(message, new Object[]{p0, p1, p2, p3, p4, p5, p6, p7, p8, p9});
    }
}

