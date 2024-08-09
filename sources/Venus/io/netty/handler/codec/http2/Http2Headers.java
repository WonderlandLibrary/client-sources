/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.Headers;
import io.netty.handler.codec.http2.CharSequenceMap;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.Map;

public interface Http2Headers
extends Headers<CharSequence, CharSequence, Http2Headers> {
    @Override
    public Iterator<Map.Entry<CharSequence, CharSequence>> iterator();

    public Iterator<CharSequence> valueIterator(CharSequence var1);

    public Http2Headers method(CharSequence var1);

    public Http2Headers scheme(CharSequence var1);

    public Http2Headers authority(CharSequence var1);

    public Http2Headers path(CharSequence var1);

    public Http2Headers status(CharSequence var1);

    public CharSequence method();

    public CharSequence scheme();

    public CharSequence authority();

    public CharSequence path();

    public CharSequence status();

    public boolean contains(CharSequence var1, CharSequence var2, boolean var3);

    public static enum PseudoHeaderName {
        METHOD(":method", true),
        SCHEME(":scheme", true),
        AUTHORITY(":authority", true),
        PATH(":path", true),
        STATUS(":status", false);

        private static final char PSEUDO_HEADER_PREFIX = ':';
        private static final byte PSEUDO_HEADER_PREFIX_BYTE = 58;
        private final AsciiString value;
        private final boolean requestOnly;
        private static final CharSequenceMap<PseudoHeaderName> PSEUDO_HEADERS;

        private PseudoHeaderName(String string2, boolean bl) {
            this.value = AsciiString.cached(string2);
            this.requestOnly = bl;
        }

        public AsciiString value() {
            return this.value;
        }

        public static boolean hasPseudoHeaderFormat(CharSequence charSequence) {
            if (charSequence instanceof AsciiString) {
                AsciiString asciiString = (AsciiString)charSequence;
                return asciiString.length() > 0 && asciiString.byteAt(0) == 58;
            }
            return charSequence.length() > 0 && charSequence.charAt(0) == ':';
        }

        public static boolean isPseudoHeader(CharSequence charSequence) {
            return PSEUDO_HEADERS.contains(charSequence);
        }

        public static PseudoHeaderName getPseudoHeader(CharSequence charSequence) {
            return (PseudoHeaderName)((Object)PSEUDO_HEADERS.get(charSequence));
        }

        public boolean isRequestOnly() {
            return this.requestOnly;
        }

        static {
            PSEUDO_HEADERS = new CharSequenceMap();
            for (PseudoHeaderName pseudoHeaderName : PseudoHeaderName.values()) {
                PSEUDO_HEADERS.add(pseudoHeaderName.value(), pseudoHeaderName);
            }
        }
    }
}

