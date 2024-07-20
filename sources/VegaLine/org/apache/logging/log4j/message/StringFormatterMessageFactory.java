/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import org.apache.logging.log4j.message.AbstractMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.StringFormattedMessage;

public final class StringFormatterMessageFactory
extends AbstractMessageFactory {
    public static final StringFormatterMessageFactory INSTANCE = new StringFormatterMessageFactory();
    private static final long serialVersionUID = -1626332412176965642L;

    @Override
    public Message newMessage(String message, Object ... params) {
        return new StringFormattedMessage(message, params);
    }

    @Override
    public Message newMessage(String message, Object p0) {
        return new StringFormattedMessage(message, p0);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1) {
        return new StringFormattedMessage(message, p0, p1);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2) {
        return new StringFormattedMessage(message, p0, p1, p2);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3) {
        return new StringFormattedMessage(message, p0, p1, p2, p3);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return new StringFormattedMessage(message, p0, p1, p2, p3, p4);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return new StringFormattedMessage(message, p0, p1, p2, p3, p4, p5);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return new StringFormattedMessage(message, p0, p1, p2, p3, p4, p5, p6);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return new StringFormattedMessage(message, p0, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return new StringFormattedMessage(message, p0, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    public Message newMessage(String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return new StringFormattedMessage(message, p0, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }
}

