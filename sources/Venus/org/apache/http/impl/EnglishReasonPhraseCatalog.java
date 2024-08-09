/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl;

import java.util.Locale;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class EnglishReasonPhraseCatalog
implements ReasonPhraseCatalog {
    public static final EnglishReasonPhraseCatalog INSTANCE = new EnglishReasonPhraseCatalog();
    private static final String[][] REASON_PHRASES = new String[][]{null, new String[3], new String[8], new String[8], new String[30], new String[8]};

    protected EnglishReasonPhraseCatalog() {
    }

    @Override
    public String getReason(int n, Locale locale) {
        Args.check(n >= 100 && n < 600, "Unknown category for status code " + n);
        int n2 = n / 100;
        int n3 = n - 100 * n2;
        String string = null;
        if (REASON_PHRASES[n2].length > n3) {
            string = REASON_PHRASES[n2][n3];
        }
        return string;
    }

    private static void setReason(int n, String string) {
        int n2 = n / 100;
        int n3 = n - 100 * n2;
        EnglishReasonPhraseCatalog.REASON_PHRASES[n2][n3] = string;
    }

    static {
        EnglishReasonPhraseCatalog.setReason(200, "OK");
        EnglishReasonPhraseCatalog.setReason(201, "Created");
        EnglishReasonPhraseCatalog.setReason(202, "Accepted");
        EnglishReasonPhraseCatalog.setReason(204, "No Content");
        EnglishReasonPhraseCatalog.setReason(301, "Moved Permanently");
        EnglishReasonPhraseCatalog.setReason(302, "Moved Temporarily");
        EnglishReasonPhraseCatalog.setReason(304, "Not Modified");
        EnglishReasonPhraseCatalog.setReason(400, "Bad Request");
        EnglishReasonPhraseCatalog.setReason(401, "Unauthorized");
        EnglishReasonPhraseCatalog.setReason(403, "Forbidden");
        EnglishReasonPhraseCatalog.setReason(404, "Not Found");
        EnglishReasonPhraseCatalog.setReason(429, "Too Many Requests");
        EnglishReasonPhraseCatalog.setReason(500, "Internal Server Error");
        EnglishReasonPhraseCatalog.setReason(501, "Not Implemented");
        EnglishReasonPhraseCatalog.setReason(502, "Bad Gateway");
        EnglishReasonPhraseCatalog.setReason(503, "Service Unavailable");
        EnglishReasonPhraseCatalog.setReason(100, "Continue");
        EnglishReasonPhraseCatalog.setReason(307, "Temporary Redirect");
        EnglishReasonPhraseCatalog.setReason(405, "Method Not Allowed");
        EnglishReasonPhraseCatalog.setReason(409, "Conflict");
        EnglishReasonPhraseCatalog.setReason(412, "Precondition Failed");
        EnglishReasonPhraseCatalog.setReason(413, "Request Too Long");
        EnglishReasonPhraseCatalog.setReason(414, "Request-URI Too Long");
        EnglishReasonPhraseCatalog.setReason(415, "Unsupported Media Type");
        EnglishReasonPhraseCatalog.setReason(300, "Multiple Choices");
        EnglishReasonPhraseCatalog.setReason(303, "See Other");
        EnglishReasonPhraseCatalog.setReason(305, "Use Proxy");
        EnglishReasonPhraseCatalog.setReason(402, "Payment Required");
        EnglishReasonPhraseCatalog.setReason(406, "Not Acceptable");
        EnglishReasonPhraseCatalog.setReason(407, "Proxy Authentication Required");
        EnglishReasonPhraseCatalog.setReason(408, "Request Timeout");
        EnglishReasonPhraseCatalog.setReason(101, "Switching Protocols");
        EnglishReasonPhraseCatalog.setReason(203, "Non Authoritative Information");
        EnglishReasonPhraseCatalog.setReason(205, "Reset Content");
        EnglishReasonPhraseCatalog.setReason(206, "Partial Content");
        EnglishReasonPhraseCatalog.setReason(504, "Gateway Timeout");
        EnglishReasonPhraseCatalog.setReason(505, "Http Version Not Supported");
        EnglishReasonPhraseCatalog.setReason(410, "Gone");
        EnglishReasonPhraseCatalog.setReason(411, "Length Required");
        EnglishReasonPhraseCatalog.setReason(416, "Requested Range Not Satisfiable");
        EnglishReasonPhraseCatalog.setReason(417, "Expectation Failed");
        EnglishReasonPhraseCatalog.setReason(102, "Processing");
        EnglishReasonPhraseCatalog.setReason(207, "Multi-Status");
        EnglishReasonPhraseCatalog.setReason(422, "Unprocessable Entity");
        EnglishReasonPhraseCatalog.setReason(419, "Insufficient Space On Resource");
        EnglishReasonPhraseCatalog.setReason(420, "Method Failure");
        EnglishReasonPhraseCatalog.setReason(423, "Locked");
        EnglishReasonPhraseCatalog.setReason(507, "Insufficient Storage");
        EnglishReasonPhraseCatalog.setReason(424, "Failed Dependency");
    }
}

