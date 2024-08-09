/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.execchain;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthState;
import org.apache.http.client.RedirectException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.impl.execchain.RequestEntityProxy;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public class RedirectExec
implements ClientExecChain {
    private final Log log = LogFactory.getLog(this.getClass());
    private final ClientExecChain requestExecutor;
    private final RedirectStrategy redirectStrategy;
    private final HttpRoutePlanner routePlanner;

    public RedirectExec(ClientExecChain clientExecChain, HttpRoutePlanner httpRoutePlanner, RedirectStrategy redirectStrategy) {
        Args.notNull(clientExecChain, "HTTP client request executor");
        Args.notNull(httpRoutePlanner, "HTTP route planner");
        Args.notNull(redirectStrategy, "HTTP redirect strategy");
        this.requestExecutor = clientExecChain;
        this.routePlanner = httpRoutePlanner;
        this.redirectStrategy = redirectStrategy;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        RequestConfig requestConfig;
        Args.notNull(httpRoute, "HTTP route");
        Args.notNull(httpRequestWrapper, "HTTP request");
        Args.notNull(httpClientContext, "HTTP context");
        List<URI> list = httpClientContext.getRedirectLocations();
        if (list != null) {
            list.clear();
        }
        int n = (requestConfig = httpClientContext.getRequestConfig()).getMaxRedirects() > 0 ? requestConfig.getMaxRedirects() : 50;
        HttpRoute httpRoute2 = httpRoute;
        HttpRequestWrapper httpRequestWrapper2 = httpRequestWrapper;
        int n2 = 0;
        while (true) {
            CloseableHttpResponse closeableHttpResponse = this.requestExecutor.execute(httpRoute2, httpRequestWrapper2, httpClientContext, httpExecutionAware);
            try {
                HttpHost httpHost;
                Object object;
                if (!requestConfig.isRedirectsEnabled()) return closeableHttpResponse;
                if (!this.redirectStrategy.isRedirected(httpRequestWrapper2.getOriginal(), closeableHttpResponse, httpClientContext)) return closeableHttpResponse;
                if (!RequestEntityProxy.isRepeatable(httpRequestWrapper2)) {
                    if (!this.log.isDebugEnabled()) return closeableHttpResponse;
                    this.log.debug("Cannot redirect non-repeatable request");
                    return closeableHttpResponse;
                }
                if (n2 >= n) {
                    throw new RedirectException("Maximum redirects (" + n + ") exceeded");
                }
                ++n2;
                HttpUriRequest httpUriRequest = this.redirectStrategy.getRedirect(httpRequestWrapper2.getOriginal(), closeableHttpResponse, httpClientContext);
                if (!httpUriRequest.headerIterator().hasNext()) {
                    object = httpRequestWrapper.getOriginal();
                    httpUriRequest.setHeaders(object.getAllHeaders());
                }
                if ((httpRequestWrapper2 = HttpRequestWrapper.wrap(httpUriRequest)) instanceof HttpEntityEnclosingRequest) {
                    RequestEntityProxy.enhance((HttpEntityEnclosingRequest)((Object)httpRequestWrapper2));
                }
                if ((httpHost = URIUtils.extractHost((URI)(object = httpRequestWrapper2.getURI()))) == null) {
                    throw new ProtocolException("Redirect URI does not specify a valid host name: " + object);
                }
                if (!httpRoute2.getTargetHost().equals(httpHost)) {
                    AuthState authState;
                    AuthState authState2 = httpClientContext.getTargetAuthState();
                    if (authState2 != null) {
                        this.log.debug("Resetting target auth state");
                        authState2.reset();
                    }
                    if ((authState = httpClientContext.getProxyAuthState()) != null && authState.isConnectionBased()) {
                        this.log.debug("Resetting proxy auth state");
                        authState.reset();
                    }
                }
                httpRoute2 = this.routePlanner.determineRoute(httpHost, httpRequestWrapper2, httpClientContext);
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Redirecting to '" + object + "' via " + httpRoute2);
                }
                EntityUtils.consume(closeableHttpResponse.getEntity());
                closeableHttpResponse.close();
            } catch (RuntimeException runtimeException) {
                closeableHttpResponse.close();
                throw runtimeException;
            } catch (IOException iOException) {
                closeableHttpResponse.close();
                throw iOException;
            } catch (HttpException httpException) {
                try {
                    EntityUtils.consume(closeableHttpResponse.getEntity());
                    throw httpException;
                } catch (IOException iOException) {
                    this.log.debug("I/O error while releasing connection", iOException);
                    throw httpException;
                } finally {
                    closeableHttpResponse.close();
                }
            }
        }
    }
}

