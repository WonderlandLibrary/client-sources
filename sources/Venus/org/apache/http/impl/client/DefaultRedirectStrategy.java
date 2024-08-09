/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.CircularRedirectException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class DefaultRedirectStrategy
implements RedirectStrategy {
    private final Log log = LogFactory.getLog(this.getClass());
    public static final int SC_PERMANENT_REDIRECT = 308;
    @Deprecated
    public static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";
    public static final DefaultRedirectStrategy INSTANCE = new DefaultRedirectStrategy();
    private final String[] redirectMethods;

    public DefaultRedirectStrategy() {
        this(new String[]{"GET", "HEAD"});
    }

    public DefaultRedirectStrategy(String[] stringArray) {
        Object[] objectArray = (String[])stringArray.clone();
        Arrays.sort(objectArray);
        this.redirectMethods = objectArray;
    }

    @Override
    public boolean isRedirected(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpResponse, "HTTP response");
        int n = httpResponse.getStatusLine().getStatusCode();
        String string = httpRequest.getRequestLine().getMethod();
        Header header = httpResponse.getFirstHeader("location");
        switch (n) {
            case 302: {
                return this.isRedirectable(string) && header != null;
            }
            case 301: 
            case 307: 
            case 308: {
                return this.isRedirectable(string);
            }
            case 303: {
                return false;
            }
        }
        return true;
    }

    public URI getLocationURI(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
        Object object;
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpResponse, "HTTP response");
        Args.notNull(httpContext, "HTTP context");
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        Header header = httpResponse.getFirstHeader("location");
        if (header == null) {
            throw new ProtocolException("Received redirect response " + httpResponse.getStatusLine() + " but no location header");
        }
        String string = header.getValue();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Redirect requested to location '" + string + "'");
        }
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        URI uRI = this.createLocationURI(string);
        try {
            if (requestConfig.isNormalizeUri()) {
                uRI = URIUtils.normalizeSyntax(uRI);
            }
            if (!uRI.isAbsolute()) {
                if (!requestConfig.isRelativeRedirectsAllowed()) {
                    throw new ProtocolException("Relative redirect location '" + uRI + "' not allowed");
                }
                object = httpClientContext.getTargetHost();
                Asserts.notNull(object, "Target host");
                URI uRI2 = new URI(httpRequest.getRequestLine().getUri());
                URI uRI3 = URIUtils.rewriteURI(uRI2, (HttpHost)object, requestConfig.isNormalizeUri() ? URIUtils.NORMALIZE : URIUtils.NO_FLAGS);
                uRI = URIUtils.resolve(uRI3, uRI);
            }
        } catch (URISyntaxException uRISyntaxException) {
            throw new ProtocolException(uRISyntaxException.getMessage(), uRISyntaxException);
        }
        object = (RedirectLocations)httpClientContext.getAttribute(REDIRECT_LOCATIONS);
        if (object == null) {
            object = new RedirectLocations();
            httpContext.setAttribute(REDIRECT_LOCATIONS, object);
        }
        if (!requestConfig.isCircularRedirectsAllowed() && ((RedirectLocations)object).contains(uRI)) {
            throw new CircularRedirectException("Circular redirect to '" + uRI + "'");
        }
        ((RedirectLocations)object).add(uRI);
        return uRI;
    }

    protected URI createLocationURI(String string) throws ProtocolException {
        try {
            return new URI(string);
        } catch (URISyntaxException uRISyntaxException) {
            throw new ProtocolException("Invalid redirect URI: " + string, uRISyntaxException);
        }
    }

    protected boolean isRedirectable(String string) {
        return Arrays.binarySearch(this.redirectMethods, string) >= 0;
    }

    @Override
    public HttpUriRequest getRedirect(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
        URI uRI = this.getLocationURI(httpRequest, httpResponse, httpContext);
        String string = httpRequest.getRequestLine().getMethod();
        if (string.equalsIgnoreCase("HEAD")) {
            return new HttpHead(uRI);
        }
        if (string.equalsIgnoreCase("GET")) {
            return new HttpGet(uRI);
        }
        int n = httpResponse.getStatusLine().getStatusCode();
        return n == 307 || n == 308 ? RequestBuilder.copy(httpRequest).setUri(uRI).build() : new HttpGet(uRI);
    }
}

