/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.params;

import java.nio.charset.CodingErrorAction;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

@Deprecated
public final class HttpProtocolParams
implements CoreProtocolPNames {
    private HttpProtocolParams() {
    }

    public static String getHttpElementCharset(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        String string = (String)httpParams.getParameter("http.protocol.element-charset");
        if (string == null) {
            string = HTTP.DEF_PROTOCOL_CHARSET.name();
        }
        return string;
    }

    public static void setHttpElementCharset(HttpParams httpParams, String string) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.protocol.element-charset", string);
    }

    public static String getContentCharset(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        String string = (String)httpParams.getParameter("http.protocol.content-charset");
        if (string == null) {
            string = HTTP.DEF_CONTENT_CHARSET.name();
        }
        return string;
    }

    public static void setContentCharset(HttpParams httpParams, String string) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.protocol.content-charset", string);
    }

    public static ProtocolVersion getVersion(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        Object object = httpParams.getParameter("http.protocol.version");
        if (object == null) {
            return HttpVersion.HTTP_1_1;
        }
        return (ProtocolVersion)object;
    }

    public static void setVersion(HttpParams httpParams, ProtocolVersion protocolVersion) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.protocol.version", protocolVersion);
    }

    public static String getUserAgent(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return (String)httpParams.getParameter("http.useragent");
    }

    public static void setUserAgent(HttpParams httpParams, String string) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.useragent", string);
    }

    public static boolean useExpectContinue(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        return httpParams.getBooleanParameter("http.protocol.expect-continue", false);
    }

    public static void setUseExpectContinue(HttpParams httpParams, boolean bl) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setBooleanParameter("http.protocol.expect-continue", bl);
    }

    public static CodingErrorAction getMalformedInputAction(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        Object object = httpParams.getParameter("http.malformed.input.action");
        if (object == null) {
            return CodingErrorAction.REPORT;
        }
        return (CodingErrorAction)object;
    }

    public static void setMalformedInputAction(HttpParams httpParams, CodingErrorAction codingErrorAction) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.malformed.input.action", codingErrorAction);
    }

    public static CodingErrorAction getUnmappableInputAction(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        Object object = httpParams.getParameter("http.unmappable.input.action");
        if (object == null) {
            return CodingErrorAction.REPORT;
        }
        return (CodingErrorAction)object;
    }

    public static void setUnmappableInputAction(HttpParams httpParams, CodingErrorAction codingErrorAction) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.unmappable.input.action", codingErrorAction);
    }
}

