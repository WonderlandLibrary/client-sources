/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class DefaultRedirectHandler
implements RedirectHandler {
    private final Log log = LogFactory.getLog(this.getClass());
    private static final String REDIRECT_LOCATIONS = "http.protocol.redirect-locations";

    @Override
    public boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        int n = httpResponse.getStatusLine().getStatusCode();
        switch (n) {
            case 301: 
            case 302: 
            case 307: {
                HttpRequest httpRequest = (HttpRequest)httpContext.getAttribute("http.request");
                String string = httpRequest.getRequestLine().getMethod();
                return string.equalsIgnoreCase("GET") || string.equalsIgnoreCase("HEAD");
            }
            case 303: {
                return false;
            }
        }
        return true;
    }

    @Override
    public URI getLocationURI(HttpResponse httpResponse, HttpContext httpContext) throws ProtocolException {
        Serializable serializable;
        Object object;
        Object object2;
        URI uRI;
        Args.notNull(httpResponse, "HTTP response");
        Header header = httpResponse.getFirstHeader("location");
        if (header == null) {
            throw new ProtocolException("Received redirect response " + httpResponse.getStatusLine() + " but no location header");
        }
        String string = header.getValue();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Redirect requested to location '" + string + "'");
        }
        try {
            uRI = new URI(string);
        } catch (URISyntaxException uRISyntaxException) {
            throw new ProtocolException("Invalid redirect URI: " + string, uRISyntaxException);
        }
        HttpParams httpParams = httpResponse.getParams();
        if (!uRI.isAbsolute()) {
            if (httpParams.isParameterTrue("http.protocol.reject-relative-redirect")) {
                throw new ProtocolException("Relative redirect location '" + uRI + "' not allowed");
            }
            object2 = (HttpHost)httpContext.getAttribute("http.target_host");
            Asserts.notNull(object2, "Target host");
            object = (HttpRequest)httpContext.getAttribute("http.request");
            try {
                serializable = new URI(object.getRequestLine().getUri());
                URI uRI2 = URIUtils.rewriteURI(serializable, (HttpHost)object2, URIUtils.DROP_FRAGMENT_AND_NORMALIZE);
                uRI = URIUtils.resolve(uRI2, uRI);
            } catch (URISyntaxException uRISyntaxException) {
                throw new ProtocolException(uRISyntaxException.getMessage(), uRISyntaxException);
            }
        }
        if (httpParams.isParameterFalse("http.protocol.allow-circular-redirects")) {
            object2 = (RedirectLocations)httpContext.getAttribute(REDIRECT_LOCATIONS);
            if (object2 == null) {
                object2 = new RedirectLocations();
                httpContext.setAttribute(REDIRECT_LOCATIONS, object2);
            }
            if (uRI.getFragment() != null) {
                try {
                    serializable = new HttpHost(uRI.getHost(), uRI.getPort(), uRI.getScheme());
                    object = URIUtils.rewriteURI(uRI, (HttpHost)serializable, URIUtils.DROP_FRAGMENT_AND_NORMALIZE);
                } catch (URISyntaxException uRISyntaxException) {
                    throw new ProtocolException(uRISyntaxException.getMessage(), uRISyntaxException);
                }
            } else {
                object = uRI;
            }
            if (((RedirectLocations)object2).contains((URI)object)) {
                throw new CircularRedirectException("Circular redirect to '" + object + "'");
            }
            ((RedirectLocations)object2).add((URI)object);
        }
        return uRI;
    }
}

