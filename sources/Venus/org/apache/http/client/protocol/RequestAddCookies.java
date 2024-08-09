/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RequestAddCookies
implements HttpRequestInterceptor {
    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Header header;
        int n;
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        String string = httpRequest.getRequestLine().getMethod();
        if (string.equalsIgnoreCase("CONNECT")) {
            return;
        }
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        CookieStore cookieStore = httpClientContext.getCookieStore();
        if (cookieStore == null) {
            this.log.debug("Cookie store not specified in HTTP context");
            return;
        }
        Lookup<CookieSpecProvider> lookup = httpClientContext.getCookieSpecRegistry();
        if (lookup == null) {
            this.log.debug("CookieSpec registry not specified in HTTP context");
            return;
        }
        HttpHost httpHost = httpClientContext.getTargetHost();
        if (httpHost == null) {
            this.log.debug("Target host not set in the context");
            return;
        }
        RouteInfo routeInfo = httpClientContext.getHttpRoute();
        if (routeInfo == null) {
            this.log.debug("Connection route not set in the context");
            return;
        }
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        String string2 = requestConfig.getCookieSpec();
        if (string2 == null) {
            string2 = "default";
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("CookieSpec selected: " + string2);
        }
        URI uRI = null;
        if (httpRequest instanceof HttpUriRequest) {
            uRI = ((HttpUriRequest)httpRequest).getURI();
        } else {
            try {
                uRI = new URI(httpRequest.getRequestLine().getUri());
            } catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
        }
        String string3 = uRI != null ? uRI.getPath() : null;
        String string4 = httpHost.getHostName();
        int n2 = httpHost.getPort();
        if (n2 < 0) {
            n2 = routeInfo.getTargetHost().getPort();
        }
        CookieOrigin cookieOrigin = new CookieOrigin(string4, n2 >= 0 ? n2 : 0, !TextUtils.isEmpty(string3) ? string3 : "/", routeInfo.isSecure());
        CookieSpecProvider cookieSpecProvider = lookup.lookup(string2);
        if (cookieSpecProvider == null) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Unsupported cookie policy: " + string2);
            }
            return;
        }
        CookieSpec cookieSpec = cookieSpecProvider.create(httpClientContext);
        List<Cookie> list = cookieStore.getCookies();
        ArrayList<Cookie> arrayList = new ArrayList<Cookie>();
        Date date = new Date();
        boolean bl = false;
        for (Cookie object2 : list) {
            if (!object2.isExpired(date)) {
                if (!cookieSpec.match(object2, cookieOrigin)) continue;
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Cookie " + object2 + " match " + cookieOrigin);
                }
                arrayList.add(object2);
                continue;
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Cookie " + object2 + " expired");
            }
            bl = true;
        }
        if (bl) {
            cookieStore.clearExpired(date);
        }
        if (!arrayList.isEmpty()) {
            List<Header> list2 = cookieSpec.formatCookies(arrayList);
            Iterator iterator2 = list2.iterator();
            while (iterator2.hasNext()) {
                Header header2 = (Header)iterator2.next();
                httpRequest.addHeader(header2);
            }
        }
        if ((n = cookieSpec.getVersion()) > 0 && (header = cookieSpec.getVersionHeader()) != null) {
            httpRequest.addHeader(header);
        }
        httpContext.setAttribute("http.cookie-spec", cookieSpec);
        httpContext.setAttribute("http.cookie-origin", cookieOrigin);
    }
}

