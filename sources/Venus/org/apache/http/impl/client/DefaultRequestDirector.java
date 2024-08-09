/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ProtocolException;
import org.apache.http.ProtocolVersion;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.NonRepeatableRequestException;
import org.apache.http.client.RedirectException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.methods.AbortableHttpRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ClientConnectionRequest;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.BasicRouteDirector;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AuthenticationStrategyAdaptor;
import org.apache.http.impl.client.DefaultRedirectStrategyAdaptor;
import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.apache.http.impl.client.HttpAuthenticator;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.client.RoutedRequest;
import org.apache.http.impl.client.TunnelRefusedException;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;

@Deprecated
public class DefaultRequestDirector
implements RequestDirector {
    private final Log log;
    protected final ClientConnectionManager connManager;
    protected final HttpRoutePlanner routePlanner;
    protected final ConnectionReuseStrategy reuseStrategy;
    protected final ConnectionKeepAliveStrategy keepAliveStrategy;
    protected final HttpRequestExecutor requestExec;
    protected final HttpProcessor httpProcessor;
    protected final HttpRequestRetryHandler retryHandler;
    protected final RedirectHandler redirectHandler;
    protected final RedirectStrategy redirectStrategy;
    protected final AuthenticationHandler targetAuthHandler;
    protected final AuthenticationStrategy targetAuthStrategy;
    protected final AuthenticationHandler proxyAuthHandler;
    protected final AuthenticationStrategy proxyAuthStrategy;
    protected final UserTokenHandler userTokenHandler;
    protected final HttpParams params;
    protected ManagedClientConnection managedConn;
    protected final AuthState targetAuthState;
    protected final AuthState proxyAuthState;
    private final HttpAuthenticator authenticator;
    private int execCount;
    private int redirectCount;
    private final int maxRedirects;
    private HttpHost virtualHost;

    public DefaultRequestDirector(HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectHandler redirectHandler, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        this(LogFactory.getLog(DefaultRequestDirector.class), httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, (RedirectStrategy)new DefaultRedirectStrategyAdaptor(redirectHandler), new AuthenticationStrategyAdaptor(authenticationHandler), new AuthenticationStrategyAdaptor(authenticationHandler2), userTokenHandler, httpParams);
    }

    public DefaultRequestDirector(Log log2, HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectStrategy redirectStrategy, AuthenticationHandler authenticationHandler, AuthenticationHandler authenticationHandler2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        this(LogFactory.getLog(DefaultRequestDirector.class), httpRequestExecutor, clientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpRoutePlanner, httpProcessor, httpRequestRetryHandler, redirectStrategy, new AuthenticationStrategyAdaptor(authenticationHandler), new AuthenticationStrategyAdaptor(authenticationHandler2), userTokenHandler, httpParams);
    }

    public DefaultRequestDirector(Log log2, HttpRequestExecutor httpRequestExecutor, ClientConnectionManager clientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpRoutePlanner httpRoutePlanner, HttpProcessor httpProcessor, HttpRequestRetryHandler httpRequestRetryHandler, RedirectStrategy redirectStrategy, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler, HttpParams httpParams) {
        Args.notNull(log2, "Log");
        Args.notNull(httpRequestExecutor, "Request executor");
        Args.notNull(clientConnectionManager, "Client connection manager");
        Args.notNull(connectionReuseStrategy, "Connection reuse strategy");
        Args.notNull(connectionKeepAliveStrategy, "Connection keep alive strategy");
        Args.notNull(httpRoutePlanner, "Route planner");
        Args.notNull(httpProcessor, "HTTP protocol processor");
        Args.notNull(httpRequestRetryHandler, "HTTP request retry handler");
        Args.notNull(redirectStrategy, "Redirect strategy");
        Args.notNull(authenticationStrategy, "Target authentication strategy");
        Args.notNull(authenticationStrategy2, "Proxy authentication strategy");
        Args.notNull(userTokenHandler, "User token handler");
        Args.notNull(httpParams, "HTTP parameters");
        this.log = log2;
        this.authenticator = new HttpAuthenticator(log2);
        this.requestExec = httpRequestExecutor;
        this.connManager = clientConnectionManager;
        this.reuseStrategy = connectionReuseStrategy;
        this.keepAliveStrategy = connectionKeepAliveStrategy;
        this.routePlanner = httpRoutePlanner;
        this.httpProcessor = httpProcessor;
        this.retryHandler = httpRequestRetryHandler;
        this.redirectStrategy = redirectStrategy;
        this.targetAuthStrategy = authenticationStrategy;
        this.proxyAuthStrategy = authenticationStrategy2;
        this.userTokenHandler = userTokenHandler;
        this.params = httpParams;
        this.redirectHandler = redirectStrategy instanceof DefaultRedirectStrategyAdaptor ? ((DefaultRedirectStrategyAdaptor)redirectStrategy).getHandler() : null;
        this.targetAuthHandler = authenticationStrategy instanceof AuthenticationStrategyAdaptor ? ((AuthenticationStrategyAdaptor)authenticationStrategy).getHandler() : null;
        this.proxyAuthHandler = authenticationStrategy2 instanceof AuthenticationStrategyAdaptor ? ((AuthenticationStrategyAdaptor)authenticationStrategy2).getHandler() : null;
        this.managedConn = null;
        this.execCount = 0;
        this.redirectCount = 0;
        this.targetAuthState = new AuthState();
        this.proxyAuthState = new AuthState();
        this.maxRedirects = this.params.getIntParameter("http.protocol.max-redirects", 100);
    }

    private RequestWrapper wrapRequest(HttpRequest httpRequest) throws ProtocolException {
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            return new EntityEnclosingRequestWrapper((HttpEntityEnclosingRequest)httpRequest);
        }
        return new RequestWrapper(httpRequest);
    }

    protected void rewriteRequestURI(RequestWrapper requestWrapper, HttpRoute httpRoute) throws ProtocolException {
        try {
            URI uRI = requestWrapper.getURI();
            if (httpRoute.getProxyHost() != null && !httpRoute.isTunnelled()) {
                if (!uRI.isAbsolute()) {
                    HttpHost httpHost = httpRoute.getTargetHost();
                    uRI = URIUtils.rewriteURI(uRI, httpHost, URIUtils.DROP_FRAGMENT_AND_NORMALIZE);
                } else {
                    uRI = URIUtils.rewriteURI(uRI);
                }
            } else {
                uRI = uRI.isAbsolute() ? URIUtils.rewriteURI(uRI, null, URIUtils.DROP_FRAGMENT_AND_NORMALIZE) : URIUtils.rewriteURI(uRI);
            }
            requestWrapper.setURI(uRI);
        } catch (URISyntaxException uRISyntaxException) {
            throw new ProtocolException("Invalid URI: " + requestWrapper.getRequestLine().getUri(), uRISyntaxException);
        }
    }

    @Override
    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Object object;
        boolean bl;
        httpContext.setAttribute("http.auth.target-scope", this.targetAuthState);
        httpContext.setAttribute("http.auth.proxy-scope", this.proxyAuthState);
        HttpHost httpHost2 = httpHost;
        HttpRequest httpRequest2 = httpRequest;
        RequestWrapper requestWrapper = this.wrapRequest(httpRequest2);
        requestWrapper.setParams(this.params);
        HttpRoute httpRoute = this.determineRoute(httpHost2, requestWrapper, httpContext);
        this.virtualHost = (HttpHost)requestWrapper.getParams().getParameter("http.virtual-host");
        if (this.virtualHost != null && this.virtualHost.getPort() == -1 && !(bl = ((HttpHost)(object = httpHost2 != null ? httpHost2 : httpRoute.getTargetHost())).getPort())) {
            this.virtualHost = new HttpHost(this.virtualHost.getHostName(), bl ? 1 : 0, this.virtualHost.getSchemeName());
        }
        object = new RoutedRequest(requestWrapper, httpRoute);
        bl = false;
        boolean bl2 = false;
        try {
            Object object2;
            HttpResponse httpResponse = null;
            while (!bl2) {
                RoutedRequest routedRequest;
                Object object3;
                object2 = ((RoutedRequest)object).getRequest();
                HttpRoute httpRoute2 = ((RoutedRequest)object).getRoute();
                httpResponse = null;
                Object object4 = httpContext.getAttribute("http.user-token");
                if (this.managedConn == null) {
                    object3 = this.connManager.requestConnection(httpRoute2, object4);
                    if (httpRequest2 instanceof AbortableHttpRequest) {
                        ((AbortableHttpRequest)((Object)httpRequest2)).setConnectionRequest((ClientConnectionRequest)object3);
                    }
                    long l = HttpClientParams.getConnectionManagerTimeout(this.params);
                    try {
                        this.managedConn = object3.getConnection(l, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException interruptedException) {
                        Thread.currentThread().interrupt();
                        throw new InterruptedIOException();
                    }
                    if (HttpConnectionParams.isStaleCheckingEnabled(this.params) && this.managedConn.isOpen()) {
                        this.log.debug("Stale connection check");
                        if (this.managedConn.isStale()) {
                            this.log.debug("Stale connection detected");
                            this.managedConn.close();
                        }
                    }
                }
                if (httpRequest2 instanceof AbortableHttpRequest) {
                    ((AbortableHttpRequest)((Object)httpRequest2)).setReleaseTrigger(this.managedConn);
                }
                try {
                    this.tryConnect((RoutedRequest)object, httpContext);
                } catch (TunnelRefusedException tunnelRefusedException) {
                    if (this.log.isDebugEnabled()) {
                        this.log.debug(tunnelRefusedException.getMessage());
                    }
                    httpResponse = tunnelRefusedException.getResponse();
                    break;
                }
                object3 = ((RequestWrapper)object2).getURI().getUserInfo();
                if (object3 != null) {
                    this.targetAuthState.update(new BasicScheme(), new UsernamePasswordCredentials((String)object3));
                }
                if (this.virtualHost != null) {
                    httpHost2 = this.virtualHost;
                } else {
                    URI uRI = ((RequestWrapper)object2).getURI();
                    if (uRI.isAbsolute()) {
                        httpHost2 = URIUtils.extractHost(uRI);
                    }
                }
                if (httpHost2 == null) {
                    httpHost2 = httpRoute2.getTargetHost();
                }
                ((RequestWrapper)object2).resetHeaders();
                this.rewriteRequestURI((RequestWrapper)object2, httpRoute2);
                httpContext.setAttribute("http.target_host", httpHost2);
                httpContext.setAttribute("http.route", httpRoute2);
                httpContext.setAttribute("http.connection", this.managedConn);
                this.requestExec.preProcess((HttpRequest)object2, this.httpProcessor, httpContext);
                httpResponse = this.tryExecute((RoutedRequest)object, httpContext);
                if (httpResponse == null) continue;
                httpResponse.setParams(this.params);
                this.requestExec.postProcess(httpResponse, this.httpProcessor, httpContext);
                bl = this.reuseStrategy.keepAlive(httpResponse, httpContext);
                if (bl) {
                    long l = this.keepAliveStrategy.getKeepAliveDuration(httpResponse, httpContext);
                    if (this.log.isDebugEnabled()) {
                        String string = l > 0L ? "for " + l + " " + (Object)((Object)TimeUnit.MILLISECONDS) : "indefinitely";
                        this.log.debug("Connection can be kept alive " + string);
                    }
                    this.managedConn.setIdleDuration(l, TimeUnit.MILLISECONDS);
                }
                if ((routedRequest = this.handleResponse((RoutedRequest)object, httpResponse, httpContext)) == null) {
                    bl2 = true;
                } else {
                    if (bl) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        EntityUtils.consume(httpEntity);
                        this.managedConn.markReusable();
                    } else {
                        this.managedConn.close();
                        if (this.proxyAuthState.getState().compareTo(AuthProtocolState.CHALLENGED) > 0 && this.proxyAuthState.getAuthScheme() != null && this.proxyAuthState.getAuthScheme().isConnectionBased()) {
                            this.log.debug("Resetting proxy auth state");
                            this.proxyAuthState.reset();
                        }
                        if (this.targetAuthState.getState().compareTo(AuthProtocolState.CHALLENGED) > 0 && this.targetAuthState.getAuthScheme() != null && this.targetAuthState.getAuthScheme().isConnectionBased()) {
                            this.log.debug("Resetting target auth state");
                            this.targetAuthState.reset();
                        }
                    }
                    if (!routedRequest.getRoute().equals(((RoutedRequest)object).getRoute())) {
                        this.releaseConnection();
                    }
                    object = routedRequest;
                }
                if (this.managedConn == null) continue;
                if (object4 == null) {
                    object4 = this.userTokenHandler.getUserToken(httpContext);
                    httpContext.setAttribute("http.user-token", object4);
                }
                if (object4 == null) continue;
                this.managedConn.setState(object4);
            }
            if (httpResponse == null || httpResponse.getEntity() == null || !httpResponse.getEntity().isStreaming()) {
                if (bl) {
                    this.managedConn.markReusable();
                }
                this.releaseConnection();
            } else {
                object2 = httpResponse.getEntity();
                object2 = new BasicManagedEntity((HttpEntity)object2, this.managedConn, bl);
                httpResponse.setEntity((HttpEntity)object2);
            }
            return httpResponse;
        } catch (ConnectionShutdownException connectionShutdownException) {
            InterruptedIOException interruptedIOException = new InterruptedIOException("Connection has been shut down");
            interruptedIOException.initCause(connectionShutdownException);
            throw interruptedIOException;
        } catch (HttpException httpException) {
            this.abortConnection();
            throw httpException;
        } catch (IOException iOException) {
            this.abortConnection();
            throw iOException;
        } catch (RuntimeException runtimeException) {
            this.abortConnection();
            throw runtimeException;
        }
    }

    private void tryConnect(RoutedRequest routedRequest, HttpContext httpContext) throws HttpException, IOException {
        HttpRoute httpRoute = routedRequest.getRoute();
        RequestWrapper requestWrapper = routedRequest.getRequest();
        int n = 0;
        while (true) {
            httpContext.setAttribute("http.request", requestWrapper);
            ++n;
            try {
                if (!this.managedConn.isOpen()) {
                    this.managedConn.open(httpRoute, httpContext, this.params);
                } else {
                    this.managedConn.setSocketTimeout(HttpConnectionParams.getSoTimeout(this.params));
                }
                this.establishRoute(httpRoute, httpContext);
            } catch (IOException iOException) {
                try {
                    this.managedConn.close();
                } catch (IOException iOException2) {
                    // empty catch block
                }
                if (this.retryHandler.retryRequest(iOException, n, httpContext)) {
                    if (!this.log.isInfoEnabled()) continue;
                    this.log.info("I/O exception (" + iOException.getClass().getName() + ") caught when connecting to " + httpRoute + ": " + iOException.getMessage());
                    if (this.log.isDebugEnabled()) {
                        this.log.debug(iOException.getMessage(), iOException);
                    }
                    this.log.info("Retrying connect to " + httpRoute);
                    continue;
                }
                throw iOException;
            }
            break;
        }
    }

    private HttpResponse tryExecute(RoutedRequest routedRequest, HttpContext httpContext) throws HttpException, IOException {
        RequestWrapper requestWrapper = routedRequest.getRequest();
        HttpRoute httpRoute = routedRequest.getRoute();
        HttpResponse httpResponse = null;
        IOException iOException = null;
        while (true) {
            ++this.execCount;
            requestWrapper.incrementExecCount();
            if (!requestWrapper.isRepeatable()) {
                this.log.debug("Cannot retry non-repeatable request");
                if (iOException != null) {
                    throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.  The cause lists the reason the original request failed.", iOException);
                }
                throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
            }
            try {
                if (!this.managedConn.isOpen()) {
                    if (!httpRoute.isTunnelled()) {
                        this.log.debug("Reopening the direct connection.");
                        this.managedConn.open(httpRoute, httpContext, this.params);
                    } else {
                        this.log.debug("Proxied connection. Need to start over.");
                        break;
                    }
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Attempt " + this.execCount + " to execute request");
                }
                httpResponse = this.requestExec.execute(requestWrapper, this.managedConn, httpContext);
            } catch (IOException iOException2) {
                this.log.debug("Closing the connection.");
                try {
                    this.managedConn.close();
                } catch (IOException iOException3) {
                    // empty catch block
                }
                if (this.retryHandler.retryRequest(iOException2, requestWrapper.getExecCount(), httpContext)) {
                    if (this.log.isInfoEnabled()) {
                        this.log.info("I/O exception (" + iOException2.getClass().getName() + ") caught when processing request to " + httpRoute + ": " + iOException2.getMessage());
                    }
                    if (this.log.isDebugEnabled()) {
                        this.log.debug(iOException2.getMessage(), iOException2);
                    }
                    if (this.log.isInfoEnabled()) {
                        this.log.info("Retrying request to " + httpRoute);
                    }
                    iOException = iOException2;
                    continue;
                }
                if (iOException2 instanceof NoHttpResponseException) {
                    NoHttpResponseException noHttpResponseException = new NoHttpResponseException(httpRoute.getTargetHost().toHostString() + " failed to respond");
                    noHttpResponseException.setStackTrace(iOException2.getStackTrace());
                    throw noHttpResponseException;
                }
                throw iOException2;
            }
            break;
        }
        return httpResponse;
    }

    protected void releaseConnection() {
        try {
            this.managedConn.releaseConnection();
        } catch (IOException iOException) {
            this.log.debug("IOException releasing connection", iOException);
        }
        this.managedConn = null;
    }

    protected HttpRoute determineRoute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        return this.routePlanner.determineRoute(httpHost != null ? httpHost : (HttpHost)httpRequest.getParams().getParameter("http.default-host"), httpRequest, httpContext);
    }

    protected void establishRoute(HttpRoute httpRoute, HttpContext httpContext) throws HttpException, IOException {
        int n;
        BasicRouteDirector basicRouteDirector = new BasicRouteDirector();
        do {
            HttpRoute httpRoute2 = this.managedConn.getRoute();
            n = basicRouteDirector.nextStep(httpRoute, httpRoute2);
            switch (n) {
                case 1: 
                case 2: {
                    this.managedConn.open(httpRoute, httpContext, this.params);
                    break;
                }
                case 3: {
                    int n2 = this.createTunnelToTarget(httpRoute, httpContext);
                    this.log.debug("Tunnel to target created.");
                    this.managedConn.tunnelTarget(n2 != 0, this.params);
                    break;
                }
                case 4: {
                    int n2 = httpRoute2.getHopCount() - 1;
                    boolean bl = this.createTunnelToProxy(httpRoute, n2, httpContext);
                    this.log.debug("Tunnel to proxy created.");
                    this.managedConn.tunnelProxy(httpRoute.getHopTarget(n2), bl, this.params);
                    break;
                }
                case 5: {
                    this.managedConn.layerProtocol(httpContext, this.params);
                    break;
                }
                case -1: {
                    throw new HttpException("Unable to establish route: planned = " + httpRoute + "; current = " + httpRoute2);
                }
                case 0: {
                    break;
                }
                default: {
                    throw new IllegalStateException("Unknown step indicator " + n + " from RouteDirector.");
                }
            }
        } while (n > 0);
    }

    protected boolean createTunnelToTarget(HttpRoute httpRoute, HttpContext httpContext) throws HttpException, IOException {
        HttpHost httpHost = httpRoute.getProxyHost();
        HttpHost httpHost2 = httpRoute.getTargetHost();
        HttpResponse httpResponse = null;
        while (true) {
            if (!this.managedConn.isOpen()) {
                this.managedConn.open(httpRoute, httpContext, this.params);
            }
            HttpRequest httpRequest = this.createConnectRequest(httpRoute, httpContext);
            httpRequest.setParams(this.params);
            httpContext.setAttribute("http.target_host", httpHost2);
            httpContext.setAttribute("http.route", httpRoute);
            httpContext.setAttribute("http.proxy_host", httpHost);
            httpContext.setAttribute("http.connection", this.managedConn);
            httpContext.setAttribute("http.request", httpRequest);
            this.requestExec.preProcess(httpRequest, this.httpProcessor, httpContext);
            httpResponse = this.requestExec.execute(httpRequest, this.managedConn, httpContext);
            httpResponse.setParams(this.params);
            this.requestExec.postProcess(httpResponse, this.httpProcessor, httpContext);
            int n = httpResponse.getStatusLine().getStatusCode();
            if (n < 200) {
                throw new HttpException("Unexpected response to CONNECT request: " + httpResponse.getStatusLine());
            }
            if (!HttpClientParams.isAuthenticating(this.params)) continue;
            if (!this.authenticator.isAuthenticationRequested(httpHost, httpResponse, this.proxyAuthStrategy, this.proxyAuthState, httpContext) || !this.authenticator.authenticate(httpHost, httpResponse, this.proxyAuthStrategy, this.proxyAuthState, httpContext)) break;
            if (this.reuseStrategy.keepAlive(httpResponse, httpContext)) {
                this.log.debug("Connection kept alive");
                HttpEntity httpEntity = httpResponse.getEntity();
                EntityUtils.consume(httpEntity);
                continue;
            }
            this.managedConn.close();
        }
        int n = httpResponse.getStatusLine().getStatusCode();
        if (n > 299) {
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                httpResponse.setEntity(new BufferedHttpEntity(httpEntity));
            }
            this.managedConn.close();
            throw new TunnelRefusedException("CONNECT refused by proxy: " + httpResponse.getStatusLine(), httpResponse);
        }
        this.managedConn.markReusable();
        return true;
    }

    protected boolean createTunnelToProxy(HttpRoute httpRoute, int n, HttpContext httpContext) throws HttpException, IOException {
        throw new HttpException("Proxy chains are not supported.");
    }

    protected HttpRequest createConnectRequest(HttpRoute httpRoute, HttpContext httpContext) {
        Object object;
        HttpHost httpHost = httpRoute.getTargetHost();
        String string = httpHost.getHostName();
        int n = httpHost.getPort();
        if (n < 0) {
            object = this.connManager.getSchemeRegistry().getScheme(httpHost.getSchemeName());
            n = ((Scheme)object).getDefaultPort();
        }
        object = new StringBuilder(string.length() + 6);
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(':');
        ((StringBuilder)object).append(Integer.toString(n));
        String string2 = ((StringBuilder)object).toString();
        ProtocolVersion protocolVersion = HttpProtocolParams.getVersion(this.params);
        BasicHttpRequest basicHttpRequest = new BasicHttpRequest("CONNECT", string2, protocolVersion);
        return basicHttpRequest;
    }

    protected RoutedRequest handleResponse(RoutedRequest routedRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Serializable serializable;
        Object object;
        HttpRoute httpRoute = routedRequest.getRoute();
        RequestWrapper requestWrapper = routedRequest.getRequest();
        HttpParams httpParams = requestWrapper.getParams();
        if (HttpClientParams.isAuthenticating(httpParams)) {
            object = (HttpHost)httpContext.getAttribute("http.target_host");
            if (object == null) {
                object = httpRoute.getTargetHost();
            }
            if (((HttpHost)object).getPort() < 0) {
                Scheme scheme = this.connManager.getSchemeRegistry().getScheme((HttpHost)object);
                object = new HttpHost(((HttpHost)object).getHostName(), scheme.getDefaultPort(), ((HttpHost)object).getSchemeName());
            }
            boolean bl = this.authenticator.isAuthenticationRequested((HttpHost)object, httpResponse, this.targetAuthStrategy, this.targetAuthState, httpContext);
            serializable = httpRoute.getProxyHost();
            if (serializable == null) {
                serializable = httpRoute.getTargetHost();
            }
            boolean bl2 = this.authenticator.isAuthenticationRequested((HttpHost)serializable, httpResponse, this.proxyAuthStrategy, this.proxyAuthState, httpContext);
            if (bl && this.authenticator.authenticate((HttpHost)object, httpResponse, this.targetAuthStrategy, this.targetAuthState, httpContext)) {
                return routedRequest;
            }
            if (bl2 && this.authenticator.authenticate((HttpHost)serializable, httpResponse, this.proxyAuthStrategy, this.proxyAuthState, httpContext)) {
                return routedRequest;
            }
        }
        if (HttpClientParams.isRedirecting(httpParams) && this.redirectStrategy.isRedirected(requestWrapper, httpResponse, httpContext)) {
            Object object2;
            if (this.redirectCount >= this.maxRedirects) {
                throw new RedirectException("Maximum redirects (" + this.maxRedirects + ") exceeded");
            }
            ++this.redirectCount;
            this.virtualHost = null;
            object = this.redirectStrategy.getRedirect(requestWrapper, httpResponse, httpContext);
            HttpRequest httpRequest = requestWrapper.getOriginal();
            object.setHeaders(httpRequest.getAllHeaders());
            serializable = object.getURI();
            HttpHost httpHost = URIUtils.extractHost((URI)serializable);
            if (httpHost == null) {
                throw new ProtocolException("Redirect URI does not specify a valid host name: " + serializable);
            }
            if (!httpRoute.getTargetHost().equals(httpHost)) {
                this.log.debug("Resetting target auth state");
                this.targetAuthState.reset();
                object2 = this.proxyAuthState.getAuthScheme();
                if (object2 != null && object2.isConnectionBased()) {
                    this.log.debug("Resetting proxy auth state");
                    this.proxyAuthState.reset();
                }
            }
            object2 = this.wrapRequest((HttpRequest)object);
            ((AbstractHttpMessage)object2).setParams(httpParams);
            HttpRoute httpRoute2 = this.determineRoute(httpHost, (HttpRequest)object2, httpContext);
            RoutedRequest routedRequest2 = new RoutedRequest((RequestWrapper)object2, httpRoute2);
            if (this.log.isDebugEnabled()) {
                this.log.debug("Redirecting to '" + serializable + "' via " + httpRoute2);
            }
            return routedRequest2;
        }
        return null;
    }

    private void abortConnection() {
        ManagedClientConnection managedClientConnection = this.managedConn;
        if (managedClientConnection != null) {
            block5: {
                this.managedConn = null;
                try {
                    managedClientConnection.abortConnection();
                } catch (IOException iOException) {
                    if (!this.log.isDebugEnabled()) break block5;
                    this.log.debug(iOException.getMessage(), iOException);
                }
            }
            try {
                managedClientConnection.releaseConnection();
            } catch (IOException iOException) {
                this.log.debug("Error releasing connection", iOException);
            }
        }
    }
}

