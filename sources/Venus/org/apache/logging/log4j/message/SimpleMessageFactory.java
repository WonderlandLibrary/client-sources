/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;

public final class SimpleMessageFactory
extends AbstractMessageFactory {
    public static final SimpleMessageFactory INSTANCE = new SimpleMessageFactory();
    private static final long serialVersionUID = 4418995198790088516L;

    @Override
    public Message newMessage(String string, Object ... objectArray) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return new SimpleMessage(string);
    }
}

