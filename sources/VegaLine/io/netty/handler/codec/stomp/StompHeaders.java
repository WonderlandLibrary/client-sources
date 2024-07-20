/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.handler.codec.Headers;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface StompHeaders
extends Headers<CharSequence, CharSequence, StompHeaders> {
    public static final AsciiString ACCEPT_VERSION = new AsciiString("accept-version");
    public static final AsciiString HOST = new AsciiString("host");
    public static final AsciiString LOGIN = new AsciiString("login");
    public static final AsciiString PASSCODE = new AsciiString("passcode");
    public static final AsciiString HEART_BEAT = new AsciiString("heart-beat");
    public static final AsciiString VERSION = new AsciiString("version");
    public static final AsciiString SESSION = new AsciiString("session");
    public static final AsciiString SERVER = new AsciiString("server");
    public static final AsciiString DESTINATION = new AsciiString("destination");
    public static final AsciiString ID = new AsciiString("id");
    public static final AsciiString ACK = new AsciiString("ack");
    public static final AsciiString TRANSACTION = new AsciiString("transaction");
    public static final AsciiString RECEIPT = new AsciiString("receipt");
    public static final AsciiString MESSAGE_ID = new AsciiString("message-id");
    public static final AsciiString SUBSCRIPTION = new AsciiString("subscription");
    public static final AsciiString RECEIPT_ID = new AsciiString("receipt-id");
    public static final AsciiString MESSAGE = new AsciiString("message");
    public static final AsciiString CONTENT_LENGTH = new AsciiString("content-length");
    public static final AsciiString CONTENT_TYPE = new AsciiString("content-type");

    public String getAsString(CharSequence var1);

    public List<String> getAllAsString(CharSequence var1);

    public Iterator<Map.Entry<String, String>> iteratorAsString();

    public boolean contains(CharSequence var1, CharSequence var2, boolean var3);
}

