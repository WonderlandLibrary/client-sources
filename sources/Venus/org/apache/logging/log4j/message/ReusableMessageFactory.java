/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.Serializable;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory2;
import org.apache.logging.log4j.message.ReusableObjectMessage;
import org.apache.logging.log4j.message.ReusableParameterizedMessage;
import org.apache.logging.log4j.message.ReusableSimpleMessage;
import org.apache.logging.log4j.util.PerformanceSensitive;

@PerformanceSensitive(value={"allocation"})
public final class ReusableMessageFactory
implements MessageFactory2,
Serializable {
    public static final ReusableMessageFactory INSTANCE = new ReusableMessageFactory();
    private static final long serialVersionUID = -8970940216592525651L;
    private static ThreadLocal<ReusableParameterizedMessage> threadLocalParameterized = new ThreadLocal();
    private static ThreadLocal<ReusableSimpleMessage> threadLocalSimpleMessage = new ThreadLocal();
    private static ThreadLocal<ReusableObjectMessage> threadLocalObjectMessage = new ThreadLocal();

    private static ReusableParameterizedMessage getParameterized() {
        ReusableParameterizedMessage reusableParameterizedMessage = threadLocalParameterized.get();
        if (reusableParameterizedMessage == null) {
            reusableParameterizedMessage = new ReusableParameterizedMessage();
            threadLocalParameterized.set(reusableParameterizedMessage);
        }
        return reusableParameterizedMessage.reserved ? new ReusableParameterizedMessage().reserve() : reusableParameterizedMessage.reserve();
    }

    private static ReusableSimpleMessage getSimple() {
        ReusableSimpleMessage reusableSimpleMessage = threadLocalSimpleMessage.get();
        if (reusableSimpleMessage == null) {
            reusableSimpleMessage = new ReusableSimpleMessage();
            threadLocalSimpleMessage.set(reusableSimpleMessage);
        }
        return reusableSimpleMessage;
    }

    private static ReusableObjectMessage getObject() {
        ReusableObjectMessage reusableObjectMessage = threadLocalObjectMessage.get();
        if (reusableObjectMessage == null) {
            reusableObjectMessage = new ReusableObjectMessage();
            threadLocalObjectMessage.set(reusableObjectMessage);
        }
        return reusableObjectMessage;
    }

    public static void release(Message message) {
        if (message instanceof ReusableParameterizedMessage) {
            ((ReusableParameterizedMessage)message).reserved = false;
        }
    }

    @Override
    public Message newMessage(CharSequence charSequence) {
        ReusableSimpleMessage reusableSimpleMessage = ReusableMessageFactory.getSimple();
        reusableSimpleMessage.set(charSequence);
        return reusableSimpleMessage;
    }

    @Override
    public Message newMessage(String string, Object ... objectArray) {
        return ReusableMessageFactory.getParameterized().set(string, objectArray);
    }

    @Override
    public Message newMessage(String string, Object object) {
        return ReusableMessageFactory.getParameterized().set(string, object);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2) {
        return ReusableMessageFactory.getParameterized().set(string, object, object2);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3) {
        return ReusableMessageFactory.getParameterized().set(string, object, object2, object3);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4) {
        return ReusableMessageFactory.getParameterized().set(string, object, object2, object3, object4);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return ReusableMessageFactory.getParameterized().set(string, object, object2, object3, object4, object5);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return ReusableMessageFactory.getParameterized().set(string, object, object2, object3, object4, object5, object6);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return ReusableMessageFactory.getParameterized().set(string, object, object2, object3, object4, object5, object6, object7);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return ReusableMessageFactory.getParameterized().set(string, object, object2, object3, object4, object5, object6, object7, object8);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return ReusableMessageFactory.getParameterized().set(string, object, object2, object3, object4, object5, object6, object7, object8, object9);
    }

    @Override
    public Message newMessage(String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return ReusableMessageFactory.getParameterized().set(string, object, object2, object3, object4, object5, object6, object7, object8, object9, object10);
    }

    @Override
    public Message newMessage(String string) {
        ReusableSimpleMessage reusableSimpleMessage = ReusableMessageFactory.getSimple();
        reusableSimpleMessage.set(string);
        return reusableSimpleMessage;
    }

    @Override
    public Message newMessage(Object object) {
        ReusableObjectMessage reusableObjectMessage = ReusableMessageFactory.getObject();
        reusableObjectMessage.set(object);
        return reusableObjectMessage;
    }
}

