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
    public Message newMessage(CharSequence charSequence) {
        return new SimpleMessage(charSequence);
    }

    @Override
    public Message newMessage(Object object) {
        return new ObjectMessage(object);
    }

    @Override
    public Message newMessage(String string) {
        return new SimpleMessage(string);
    }

    @Override
    public Message newMessage(String string, Object object) {
        return this.newMessage(string, new Object[]{object});
    }

    @Override
    public Message newMessage(String string, Object object, Object object2) {
        return this.newMessage(string, new Object[]{object, object2});
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3) {
        return this.newMessage(string, new Object[]{object, object2, object3});
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4) {
        return this.newMessage(string, new Object[]{object, object2, object3, object4});
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return this.newMessage(string, new Object[]{object, object2, object3, object4, object5});
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.newMessage(string, new Object[]{object, object2, object3, object4, object5, object6});
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.newMessage(string, new Object[]{object, object2, object3, object4, object5, object6, object7});
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return this.newMessage(string, new Object[]{object, object2, object3, object4, object5, object6, object7, object8});
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return this.newMessage(string, new Object[]{object, object2, object3, object4, object5, object6, object7, object8, object9});
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return this.newMessage(string, new Object[]{object, object2, object3, object4, object5, object6, object7, object8, object9, object10});
    }
}

