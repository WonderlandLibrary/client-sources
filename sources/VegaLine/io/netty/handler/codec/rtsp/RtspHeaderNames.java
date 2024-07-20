/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.rtsp;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.util.AsciiString;

public final class RtspHeaderNames {
    public static final AsciiString ACCEPT = HttpHeaderNames.ACCEPT;
    public static final AsciiString ACCEPT_ENCODING = HttpHeaderNames.ACCEPT_ENCODING;
    public static final AsciiString ACCEPT_LANGUAGE = HttpHeaderNames.ACCEPT_LANGUAGE;
    public static final AsciiString ALLOW = new AsciiString("allow");
    public static final AsciiString AUTHORIZATION = HttpHeaderNames.AUTHORIZATION;
    public static final AsciiString BANDWIDTH = new AsciiString("bandwidth");
    public static final AsciiString BLOCKSIZE = new AsciiString("blocksize");
    public static final AsciiString CACHE_CONTROL = HttpHeaderNames.CACHE_CONTROL;
    public static final AsciiString CONFERENCE = new AsciiString("conference");
    public static final AsciiString CONNECTION = HttpHeaderNames.CONNECTION;
    public static final AsciiString CONTENT_BASE = HttpHeaderNames.CONTENT_BASE;
    public static final AsciiString CONTENT_ENCODING = HttpHeaderNames.CONTENT_ENCODING;
    public static final AsciiString CONTENT_LANGUAGE = HttpHeaderNames.CONTENT_LANGUAGE;
    public static final AsciiString CONTENT_LENGTH = HttpHeaderNames.CONTENT_LENGTH;
    public static final AsciiString CONTENT_LOCATION = HttpHeaderNames.CONTENT_LOCATION;
    public static final AsciiString CONTENT_TYPE = HttpHeaderNames.CONTENT_TYPE;
    public static final AsciiString CSEQ = new AsciiString("cseq");
    public static final AsciiString DATE = HttpHeaderNames.DATE;
    public static final AsciiString EXPIRES = HttpHeaderNames.EXPIRES;
    public static final AsciiString FROM = HttpHeaderNames.FROM;
    public static final AsciiString HOST = HttpHeaderNames.HOST;
    public static final AsciiString IF_MATCH = HttpHeaderNames.IF_MATCH;
    public static final AsciiString IF_MODIFIED_SINCE = HttpHeaderNames.IF_MODIFIED_SINCE;
    public static final AsciiString KEYMGMT = new AsciiString("keymgmt");
    public static final AsciiString LAST_MODIFIED = HttpHeaderNames.LAST_MODIFIED;
    public static final AsciiString PROXY_AUTHENTICATE = HttpHeaderNames.PROXY_AUTHENTICATE;
    public static final AsciiString PROXY_REQUIRE = new AsciiString("proxy-require");
    public static final AsciiString PUBLIC = new AsciiString("public");
    public static final AsciiString RANGE = HttpHeaderNames.RANGE;
    public static final AsciiString REFERER = HttpHeaderNames.REFERER;
    public static final AsciiString REQUIRE = new AsciiString("require");
    public static final AsciiString RETRT_AFTER = HttpHeaderNames.RETRY_AFTER;
    public static final AsciiString RTP_INFO = new AsciiString("rtp-info");
    public static final AsciiString SCALE = new AsciiString("scale");
    public static final AsciiString SESSION = new AsciiString("session");
    public static final AsciiString SERVER = HttpHeaderNames.SERVER;
    public static final AsciiString SPEED = new AsciiString("speed");
    public static final AsciiString TIMESTAMP = new AsciiString("timestamp");
    public static final AsciiString TRANSPORT = new AsciiString("transport");
    public static final AsciiString UNSUPPORTED = new AsciiString("unsupported");
    public static final AsciiString USER_AGENT = HttpHeaderNames.USER_AGENT;
    public static final AsciiString VARY = HttpHeaderNames.VARY;
    public static final AsciiString VIA = HttpHeaderNames.VIA;
    public static final AsciiString WWW_AUTHENTICATE = HttpHeaderNames.WWW_AUTHENTICATE;

    private RtspHeaderNames() {
    }
}

