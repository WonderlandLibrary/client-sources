/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.Headers;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface SpdyHeaders
extends Headers<CharSequence, CharSequence, SpdyHeaders> {
    public String getAsString(CharSequence var1);

    public List<String> getAllAsString(CharSequence var1);

    public Iterator<Map.Entry<String, String>> iteratorAsString();

    public boolean contains(CharSequence var1, CharSequence var2, boolean var3);

    public static final class HttpNames {
        public static final AsciiString HOST = new AsciiString(":host");
        public static final AsciiString METHOD = new AsciiString(":method");
        public static final AsciiString PATH = new AsciiString(":path");
        public static final AsciiString SCHEME = new AsciiString(":scheme");
        public static final AsciiString STATUS = new AsciiString(":status");
        public static final AsciiString VERSION = new AsciiString(":version");

        private HttpNames() {
        }
    }
}

