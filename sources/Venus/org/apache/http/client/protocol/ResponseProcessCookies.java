/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class ResponseProcessCookies
implements HttpResponseInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpResponse, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        CookieSpec cookieSpec = httpClientContext.getCookieSpec();
        if (cookieSpec == null) {
            this.log.debug("Cookie spec not specified in HTTP context");
            return;
        }
        CookieStore cookieStore = httpClientContext.getCookieStore();
        if (cookieStore == null) {
            this.log.debug("Cookie store not specified in HTTP context");
            return;
        }
        CookieOrigin cookieOrigin = httpClientContext.getCookieOrigin();
        if (cookieOrigin == null) {
            this.log.debug("Cookie origin not specified in HTTP context");
            return;
        }
        HeaderIterator headerIterator = httpResponse.headerIterator("Set-Cookie");
        this.processCookies(headerIterator, cookieSpec, cookieOrigin, cookieStore);
        if (cookieSpec.getVersion() > 0) {
            headerIterator = httpResponse.headerIterator("Set-Cookie2");
            this.processCookies(headerIterator, cookieSpec, cookieOrigin, cookieStore);
        }
    }

    private void processCookies(HeaderIterator headerIterator, CookieSpec cookieSpec, CookieOrigin cookieOrigin, CookieStore cookieStore) {
        while (headerIterator.hasNext()) {
            Header header = headerIterator.nextHeader();
            try {
                List<Cookie> list = cookieSpec.parse(header, cookieOrigin);
                for (Cookie cookie : list) {
                    try {
                        cookieSpec.validate(cookie, cookieOrigin);
                        cookieStore.addCookie(cookie);
                        if (!this.log.isDebugEnabled()) continue;
                        this.log.debug("Cookie accepted [" + ResponseProcessCookies.formatCooke(cookie) + "]");
                    } catch (MalformedCookieException malformedCookieException) {
                        if (!this.log.isWarnEnabled()) continue;
                        this.log.warn("Cookie rejected [" + ResponseProcessCookies.formatCooke(cookie) + "] " + malformedCookieException.getMessage());
                    }
                }
            } catch (MalformedCookieException malformedCookieException) {
                if (!this.log.isWarnEnabled()) continue;
                this.log.warn("Invalid cookie header: \"" + header + "\". " + malformedCookieException.getMessage());
            }
        }
    }

    private static String formatCooke(Cookie cookie) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cookie.getName());
        stringBuilder.append("=\"");
        String string = cookie.getValue();
        if (string != null) {
            if (string.length() > 100) {
                string = string.substring(0, 100) + "...";
            }
            stringBuilder.append(string);
        }
        stringBuilder.append("\"");
        stringBuilder.append(", version:");
        stringBuilder.append(Integer.toString(cookie.getVersion()));
        stringBuilder.append(", domain:");
        stringBuilder.append(cookie.getDomain());
        stringBuilder.append(", path:");
        stringBuilder.append(cookie.getPath());
        stringBuilder.append(", expiry:");
        stringBuilder.append(cookie.getExpiryDate());
        return stringBuilder.toString();
    }
}

