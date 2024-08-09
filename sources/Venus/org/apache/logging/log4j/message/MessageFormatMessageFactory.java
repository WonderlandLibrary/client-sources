/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFormatMessage;

public class MessageFormatMessageFactory
extends AbstractMessageFactory {
    private static final long serialVersionUID = 3584821740584192453L;

    @Override
    public Message newMessage(String string, Object ... objectArray) {
        return new MessageFormatMessage(string, objectArray);
    }

    @Override
    public Message newMessage(String string, Object object) {
        return new MessageFormatMessage(string, object);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2) {
        return new MessageFormatMessage(string, object, object2);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3) {
        return new MessageFormatMessage(string, object, object2, object3);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4) {
        return new MessageFormatMessage(string, object, object2, object3, object4);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return new MessageFormatMessage(string, object, object2, object3, object4, object5);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return new MessageFormatMessage(string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return new MessageFormatMessage(string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return new MessageFormatMessage(string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return new MessageFormatMessage(string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return new MessageFormatMessage(string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }
}

