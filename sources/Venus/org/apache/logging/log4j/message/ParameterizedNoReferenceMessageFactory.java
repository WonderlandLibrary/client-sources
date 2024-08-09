/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.SimpleMessage;

public final class ParameterizedNoReferenceMessageFactory
extends AbstractMessageFactory {
    private static final long serialVersionUID = 5027639245636870500L;
    public static final ParameterizedNoReferenceMessageFactory INSTANCE = new ParameterizedNoReferenceMessageFactory();

    @Override
    public Message newMessage(String string, Object ... objectArray) {
        if (objectArray == null) {
            return new SimpleMessage(string);
        }
        ParameterizedMessage parameterizedMessage = new ParameterizedMessage(string, objectArray);
        return new StatusMessage(parameterizedMessage.getFormattedMessage(), parameterizedMessage.getThrowable());
    }

    static class StatusMessage
    implements Message {
        private final String formattedMessage;
        private final Throwable throwable;

        public StatusMessage(String string, Throwable throwable) {
            this.formattedMessage = string;
            this.throwable = throwable;
        }

        @Override
        public String getFormattedMessage() {
            return this.formattedMessage;
        }

        @Override
        public String getFormat() {
            return this.formattedMessage;
        }

        @Override
        public Object[] getParameters() {
            return null;
        }

        @Override
        public Throwable getThrowable() {
            return this.throwable;
        }
    }
}

