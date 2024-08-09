/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.util.MessageSupplier;
import org.apache.logging.log4j.util.Supplier;

public final class LambdaUtil {
    private LambdaUtil() {
    }

    public static Object[] getAll(Supplier<?> ... supplierArray) {
        if (supplierArray == null) {
            return null;
        }
        Object[] objectArray = new Object[supplierArray.length];
        for (int i = 0; i < objectArray.length; ++i) {
            objectArray[i] = LambdaUtil.get(supplierArray[i]);
        }
        return objectArray;
    }

    public static Object get(Supplier<?> supplier) {
        if (supplier == null) {
            return null;
        }
        Object obj = supplier.get();
        return obj instanceof Message ? ((Message)obj).getFormattedMessage() : obj;
    }

    public static Message get(MessageSupplier messageSupplier) {
        if (messageSupplier == null) {
            return null;
        }
        return messageSupplier.get();
    }

    public static Message getMessage(Supplier<?> supplier, MessageFactory messageFactory) {
        if (supplier == null) {
            return null;
        }
        Object obj = supplier.get();
        return obj instanceof Message ? (Message)obj : messageFactory.newMessage(obj);
    }
}

