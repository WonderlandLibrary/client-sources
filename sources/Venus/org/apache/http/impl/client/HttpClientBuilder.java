/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.BackoffManager;
import org.apache.http.client.ConnectionBackoffStrategy;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.InputStreamFactory;
import org.apache.http.client.protocol.RequestAcceptEncoding;
import org.apache.http.client.protocol.RequestAddCookies;
import org.apache.http.client.protocol.RequestAuthCache;
import org.apache.http.client.protocol.RequestClientConnControl;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.client.protocol.RequestExpectContinue;
import org.apache.http.client.protocol.ResponseContentEncoding;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.auth.DigestSchemeFactory;
import org.apache.http.impl.auth.KerberosSchemeFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.CookieSpecRegistries;
import org.apache.http.impl.client.DefaultClientConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.DefaultUserTokenHandler;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.client.InternalHttpClient;
import org.apache.http.impl.client.NoopUserTokenHandler;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.apache.http.impl.client.TargetAuthenticationStrategy;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.impl.execchain.BackoffStrategyExec;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.impl.execchain.MainClientExec;
import org.apache.http.impl.execchain.ProtocolExec;
import org.apache.http.impl.execchain.RedirectExec;
import org.apache.http.impl.execchain.RetryExec;
import org.apache.http.impl.execchain.ServiceUnavailableRetryExec;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.TextUtils;
import org.apache.http.util.VersionInfo;

public class HttpClientBuilder {
    private HttpRequestExecutor requestExec;
    private HostnameVerifier hostnameVerifier;
    private LayeredConnectionSocketFactory sslSocketFactory;
    private SSLContext sslContext;
    private HttpClientConnectionManager connManager;
    private boolean connManagerShared;
    private SchemePortResolver schemePortResolver;
    private ConnectionReuseStrategy reuseStrategy;
    private ConnectionKeepAliveStrategy keepAliveStrategy;
    private AuthenticationStrategy targetAuthStrategy;
    private AuthenticationStrategy proxyAuthStrategy;
    private UserTokenHandler userTokenHandler;
    private HttpProcessor httpprocessor;
    private DnsResolver dnsResolver;
    private LinkedList<HttpRequestInterceptor> requestFirst;
    private LinkedList<HttpRequestInterceptor> requestLast;
    private LinkedList<HttpResponseInterceptor> responseFirst;
    private LinkedList<HttpResponseInterceptor> responseLast;
    private HttpRequestRetryHandler retryHandler;
    private HttpRoutePlanner routePlanner;
    private RedirectStrategy redirectStrategy;
    private ConnectionBackoffStrategy connectionBackoffStrategy;
    private BackoffManager backoffManager;
    private ServiceUnavailableRetryStrategy serviceUnavailStrategy;
    private Lookup<AuthSchemeProvider> authSchemeRegistry;
    private Lookup<CookieSpecProvider> cookieSpecRegistry;
    private Map<String, InputStreamFactory> contentDecoderMap;
    private CookieStore cookieStore;
    private CredentialsProvider credentialsProvider;
    private String userAgent;
    private HttpHost proxy;
    private Collection<? extends Header> defaultHeaders;
    private SocketConfig defaultSocketConfig;
    private ConnectionConfig defaultConnectionConfig;
    private RequestConfig defaultRequestConfig;
    private boolean evictExpiredConnections;
    private boolean evictIdleConnections;
    private long maxIdleTime;
    private TimeUnit maxIdleTimeUnit;
    private boolean systemProperties;
    private boolean redirectHandlingDisabled;
    private boolean automaticRetriesDisabled;
    private boolean contentCompressionDisabled;
    private boolean cookieManagementDisabled;
    private boolean authCachingDisabled;
    private boolean connectionStateDisabled;
    private boolean defaultUserAgentDisabled;
    private int maxConnTotal = 0;
    private int maxConnPerRoute = 0;
    private long connTimeToLive = -1L;
    private TimeUnit connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;
    private List<Closeable> closeables;
    private PublicSuffixMatcher publicSuffixMatcher;

    public static HttpClientBuilder create() {
        return new HttpClientBuilder();
    }

    protected HttpClientBuilder() {
    }

    public final HttpClientBuilder setRequestExecutor(HttpRequestExecutor httpRequestExecutor) {
        this.requestExec = httpRequestExecutor;
        return this;
    }

    @Deprecated
    public final HttpClientBuilder setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        this.hostnameVerifier = x509HostnameVerifier;
        return this;
    }

    public final HttpClientBuilder setSSLHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    public final HttpClientBuilder setPublicSuffixMatcher(PublicSuffixMatcher publicSuffixMatcher) {
        this.publicSuffixMatcher = publicSuffixMatcher;
        return this;
    }

    @Deprecated
    public final HttpClientBuilder setSslcontext(SSLContext sSLContext) {
        return this.setSSLContext(sSLContext);
    }

    public final HttpClientBuilder setSSLContext(SSLContext sSLContext) {
        this.sslContext = sSLContext;
        return this;
    }

    public final HttpClientBuilder setSSLSocketFactory(LayeredConnectionSocketFactory layeredConnectionSocketFactory) {
        this.sslSocketFactory = layeredConnectionSocketFactory;
        return this;
    }

    public final HttpClientBuilder setMaxConnTotal(int n) {
        this.maxConnTotal = n;
        return this;
    }

    public final HttpClientBuilder setMaxConnPerRoute(int n) {
        this.maxConnPerRoute = n;
        return this;
    }

    public final HttpClientBuilder setDefaultSocketConfig(SocketConfig socketConfig) {
        this.defaultSocketConfig = socketConfig;
        return this;
    }

    public final HttpClientBuilder setDefaultConnectionConfig(ConnectionConfig connectionConfig) {
        this.defaultConnectionConfig = connectionConfig;
        return this;
    }

    public final HttpClientBuilder setConnectionTimeToLive(long l, TimeUnit timeUnit) {
        this.connTimeToLive = l;
        this.connTimeToLiveTimeUnit = timeUnit;
        return this;
    }

    public final HttpClientBuilder setConnectionManager(HttpClientConnectionManager httpClientConnectionManager) {
        this.connManager = httpClientConnectionManager;
        return this;
    }

    public final HttpClientBuilder setConnectionManagerShared(boolean bl) {
        this.connManagerShared = bl;
        return this;
    }

    public final HttpClientBuilder setConnectionReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        this.reuseStrategy = connectionReuseStrategy;
        return this;
    }

    public final HttpClientBuilder setKeepAliveStrategy(ConnectionKeepAliveStrategy connectionKeepAliveStrategy) {
        this.keepAliveStrategy = connectionKeepAliveStrategy;
        return this;
    }

    public final HttpClientBuilder setTargetAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.targetAuthStrategy = authenticationStrategy;
        return this;
    }

    public final HttpClientBuilder setProxyAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.proxyAuthStrategy = authenticationStrategy;
        return this;
    }

    public final HttpClientBuilder setUserTokenHandler(UserTokenHandler userTokenHandler) {
        this.userTokenHandler = userTokenHandler;
        return this;
    }

    public final HttpClientBuilder disableConnectionState() {
        this.connectionStateDisabled = true;
        return this;
    }

    public final HttpClientBuilder setSchemePortResolver(SchemePortResolver schemePortResolver) {
        this.schemePortResolver = schemePortResolver;
        return this;
    }

    public final HttpClientBuilder setUserAgent(String string) {
        this.userAgent = string;
        return this;
    }

    public final HttpClientBuilder setDefaultHeaders(Collection<? extends Header> collection) {
        this.defaultHeaders = collection;
        return this;
    }

    public final HttpClientBuilder addInterceptorFirst(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseFirst == null) {
            this.responseFirst = new LinkedList();
        }
        this.responseFirst.addFirst(httpResponseInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorLast(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseLast == null) {
            this.responseLast = new LinkedList();
        }
        this.responseLast.addLast(httpResponseInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorFirst(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestFirst == null) {
            this.requestFirst = new LinkedList();
        }
        this.requestFirst.addFirst(httpRequestInterceptor);
        return this;
    }

    public final HttpClientBuilder addInterceptorLast(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestLast == null) {
            this.requestLast = new LinkedList();
        }
        this.requestLast.addLast(httpRequestInterceptor);
        return this;
    }

    public final HttpClientBuilder disableCookieManagement() {
        this.cookieManagementDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableContentCompression() {
        this.contentCompressionDisabled = true;
        return this;
    }

    public final HttpClientBuilder disableAuthCaching() {
        this.authCachingDisabled = true;
        return this;
    }

    public final HttpClientBuilder setHttpProcessor(HttpProcessor httpProcessor) {
        this.httpprocessor = httpProcessor;
        return this;
    }

    public final HttpClientBuilder setDnsResolver(DnsResolver dnsResolver) {
        this.dnsResolver = dnsResolver;
        return this;
    }

    public final HttpClientBuilder setRetryHandler(HttpRequestRetryHandler httpRequestRetryHandler) {
        this.retryHandler = httpRequestRetryHandler;
        return this;
    }

    public final HttpClientBuilder disableAutomaticRetries() {
        this.automaticRetriesDisabled = true;
        return this;
    }

    public final HttpClientBuilder setProxy(HttpHost httpHost) {
        this.proxy = httpHost;
        return this;
    }

    public final HttpClientBuilder setRoutePlanner(HttpRoutePlanner httpRoutePlanner) {
        this.routePlanner = httpRoutePlanner;
        return this;
    }

    public final HttpClientBuilder setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
        return this;
    }

    public final HttpClientBuilder disableRedirectHandling() {
        this.redirectHandlingDisabled = true;
        return this;
    }

    public final HttpClientBuilder setConnectionBackoffStrategy(ConnectionBackoffStrategy connectionBackoffStrategy) {
        this.connectionBackoffStrategy = connectionBackoffStrategy;
        return this;
    }

    public final HttpClientBuilder setBackoffManager(BackoffManager backoffManager) {
        this.backoffManager = backoffManager;
        return this;
    }

    public final HttpClientBuilder setServiceUnavailableRetryStrategy(ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy) {
        this.serviceUnavailStrategy = serviceUnavailableRetryStrategy;
        return this;
    }

    public final HttpClientBuilder setDefaultCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        return this;
    }

    public final HttpClientBuilder setDefaultCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
        return this;
    }

    public final HttpClientBuilder setDefaultAuthSchemeRegistry(Lookup<AuthSchemeProvider> lookup) {
        this.authSchemeRegistry = lookup;
        return this;
    }

    public final HttpClientBuilder setDefaultCookieSpecRegistry(Lookup<CookieSpecProvider> lookup) {
        this.cookieSpecRegistry = lookup;
        return this;
    }

    public final HttpClientBuilder setContentDecoderRegistry(Map<String, InputStreamFactory> map) {
        this.contentDecoderMap = map;
        return this;
    }

    public final HttpClientBuilder setDefaultRequestConfig(RequestConfig requestConfig) {
        this.defaultRequestConfig = requestConfig;
        return this;
    }

    public final HttpClientBuilder useSystemProperties() {
        this.systemProperties = true;
        return this;
    }

    public final HttpClientBuilder evictExpiredConnections() {
        this.evictExpiredConnections = true;
        return this;
    }

    @Deprecated
    public final HttpClientBuilder evictIdleConnections(Long l, TimeUnit timeUnit) {
        return this.evictIdleConnections((long)l, timeUnit);
    }

    public final HttpClientBuilder evictIdleConnections(long l, TimeUnit timeUnit) {
        this.evictIdleConnections = true;
        this.maxIdleTime = l;
        this.maxIdleTimeUnit = timeUnit;
        return this;
    }

    public final HttpClientBuilder disableDefaultUserAgent() {
        this.defaultUserAgentDisabled = true;
        return this;
    }

    protected ClientExecChain createMainExec(HttpRequestExecutor httpRequestExecutor, HttpClientConnectionManager httpClientConnectionManager, ConnectionReuseStrategy connectionReuseStrategy, ConnectionKeepAliveStrategy connectionKeepAliveStrategy, HttpProcessor httpProcessor, AuthenticationStrategy authenticationStrategy, AuthenticationStrategy authenticationStrategy2, UserTokenHandler userTokenHandler) {
        return new MainClientExec(httpRequestExecutor, httpClientConnectionManager, connectionReuseStrategy, connectionKeepAliveStrategy, httpProcessor, authenticationStrategy, authenticationStrategy2, userTokenHandler);
    }

    protected ClientExecChain decorateMainExec(ClientExecChain clientExecChain) {
        return clientExecChain;
    }

    protected ClientExecChain decorateProtocolExec(ClientExecChain clientExecChain) {
        return clientExecChain;
    }

    protected void addCloseable(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        if (this.closeables == null) {
            this.closeables = new ArrayList<Closeable>();
        }
        this.closeables.add(closeable);
    }

    private static String[] split(String string) {
        if (TextUtils.isBlank(string)) {
            return null;
        }
        return string.split(" *, *");
    }

    public CloseableHttpClient build() {
        ArrayList<Closeable> arrayList;
        CredentialsProvider credentialsProvider;
        CookieStore cookieStore;
        Object object;
        Object object2;
        Object object3;
        Object object4;
        String string;
        UserTokenHandler userTokenHandler;
        AuthenticationStrategy authenticationStrategy;
        Object object5;
        Object object6;
        Object object7;
        Object object8;
        HttpRequestExecutor httpRequestExecutor;
        PublicSuffixMatcher publicSuffixMatcher = this.publicSuffixMatcher;
        if (publicSuffixMatcher == null) {
            publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();
        }
        if ((httpRequestExecutor = this.requestExec) == null) {
            httpRequestExecutor = new HttpRequestExecutor();
        }
        if ((object8 = this.connManager) == null) {
            object7 = this.sslSocketFactory;
            if (object7 == null) {
                object6 = this.systemProperties ? HttpClientBuilder.split(System.getProperty("https.protocols")) : null;
                object5 = this.systemProperties ? HttpClientBuilder.split(System.getProperty("https.cipherSuites")) : null;
                HostnameVerifier hostnameVerifier = this.hostnameVerifier;
                if (hostnameVerifier == null) {
                    hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
                }
                object7 = this.sslContext != null ? new SSLConnectionSocketFactory(this.sslContext, (String[])object6, (String[])object5, hostnameVerifier) : (this.systemProperties ? new SSLConnectionSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault(), (String[])object6, (String[])object5, hostnameVerifier) : new SSLConnectionSocketFactory(SSLContexts.createDefault(), hostnameVerifier));
            }
            object6 = new PoolingHttpClientConnectionManager(RegistryBuilder.create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", (PlainConnectionSocketFactory)object7).build(), null, null, this.dnsResolver, this.connTimeToLive, this.connTimeToLiveTimeUnit != null ? this.connTimeToLiveTimeUnit : TimeUnit.MILLISECONDS);
            if (this.defaultSocketConfig != null) {
                ((PoolingHttpClientConnectionManager)object6).setDefaultSocketConfig(this.defaultSocketConfig);
            }
            if (this.defaultConnectionConfig != null) {
                ((PoolingHttpClientConnectionManager)object6).setDefaultConnectionConfig(this.defaultConnectionConfig);
            }
            if (this.systemProperties && "true".equalsIgnoreCase((String)(object5 = System.getProperty("http.keepAlive", "true")))) {
                object5 = System.getProperty("http.maxConnections", "5");
                int n = Integer.parseInt((String)object5);
                ((PoolingHttpClientConnectionManager)object6).setDefaultMaxPerRoute(n);
                ((PoolingHttpClientConnectionManager)object6).setMaxTotal(2 * n);
            }
            if (this.maxConnTotal > 0) {
                ((PoolingHttpClientConnectionManager)object6).setMaxTotal(this.maxConnTotal);
            }
            if (this.maxConnPerRoute > 0) {
                ((PoolingHttpClientConnectionManager)object6).setDefaultMaxPerRoute(this.maxConnPerRoute);
            }
            object8 = object6;
        }
        if ((object7 = this.reuseStrategy) == null) {
            object7 = this.systemProperties ? ("true".equalsIgnoreCase((String)(object6 = System.getProperty("http.keepAlive", "true"))) ? DefaultClientConnectionReuseStrategy.INSTANCE : NoConnectionReuseStrategy.INSTANCE) : DefaultClientConnectionReuseStrategy.INSTANCE;
        }
        if ((object6 = this.keepAliveStrategy) == null) {
            object6 = DefaultConnectionKeepAliveStrategy.INSTANCE;
        }
        if ((object5 = this.targetAuthStrategy) == null) {
            object5 = TargetAuthenticationStrategy.INSTANCE;
        }
        if ((authenticationStrategy = this.proxyAuthStrategy) == null) {
            authenticationStrategy = ProxyAuthenticationStrategy.INSTANCE;
        }
        if ((userTokenHandler = this.userTokenHandler) == null) {
            userTokenHandler = !this.connectionStateDisabled ? DefaultUserTokenHandler.INSTANCE : NoopUserTokenHandler.INSTANCE;
        }
        if ((string = this.userAgent) == null) {
            if (this.systemProperties) {
                string = System.getProperty("http.agent");
            }
            if (string == null && !this.defaultUserAgentDisabled) {
                string = VersionInfo.getUserAgent("Apache-HttpClient", "org.apache.http.client", this.getClass());
            }
        }
        ClientExecChain clientExecChain = this.createMainExec(httpRequestExecutor, (HttpClientConnectionManager)object8, (ConnectionReuseStrategy)object7, (ConnectionKeepAliveStrategy)object6, new ImmutableHttpProcessor(new RequestTargetHost(), new RequestUserAgent(string)), (AuthenticationStrategy)object5, authenticationStrategy, userTokenHandler);
        clientExecChain = this.decorateMainExec(clientExecChain);
        HttpProcessor httpProcessor = this.httpprocessor;
        if (httpProcessor == null) {
            object4 = HttpProcessorBuilder.create();
            if (this.requestFirst != null) {
                object3 = this.requestFirst.iterator();
                while (object3.hasNext()) {
                    object2 = (HttpRequestInterceptor)object3.next();
                    ((HttpProcessorBuilder)object4).addFirst((HttpRequestInterceptor)object2);
                }
            }
            if (this.responseFirst != null) {
                object3 = this.responseFirst.iterator();
                while (object3.hasNext()) {
                    object2 = (HttpResponseInterceptor)object3.next();
                    ((HttpProcessorBuilder)object4).addFirst((HttpResponseInterceptor)object2);
                }
            }
            ((HttpProcessorBuilder)object4).addAll(new RequestDefaultHeaders(this.defaultHeaders), new RequestContent(), new RequestTargetHost(), new RequestClientConnControl(), new RequestUserAgent(string), new RequestExpectContinue());
            if (!this.cookieManagementDisabled) {
                ((HttpProcessorBuilder)object4).add(new RequestAddCookies());
            }
            if (!this.contentCompressionDisabled) {
                if (this.contentDecoderMap != null) {
                    object3 = new ArrayList<String>(this.contentDecoderMap.keySet());
                    Collections.sort(object3);
                    ((HttpProcessorBuilder)object4).add(new RequestAcceptEncoding((List<String>)object3));
                } else {
                    ((HttpProcessorBuilder)object4).add(new RequestAcceptEncoding());
                }
            }
            if (!this.authCachingDisabled) {
                ((HttpProcessorBuilder)object4).add(new RequestAuthCache());
            }
            if (!this.cookieManagementDisabled) {
                ((HttpProcessorBuilder)object4).add(new ResponseProcessCookies());
            }
            if (!this.contentCompressionDisabled) {
                if (this.contentDecoderMap != null) {
                    object3 = RegistryBuilder.create();
                    object2 = this.contentDecoderMap.entrySet().iterator();
                    while (object2.hasNext()) {
                        object = (Map.Entry)object2.next();
                        ((RegistryBuilder)object3).register((String)object.getKey(), object.getValue());
                    }
                    ((HttpProcessorBuilder)object4).add(new ResponseContentEncoding(((RegistryBuilder)object3).build()));
                } else {
                    ((HttpProcessorBuilder)object4).add(new ResponseContentEncoding());
                }
            }
            if (this.requestLast != null) {
                object3 = this.requestLast.iterator();
                while (object3.hasNext()) {
                    object2 = (HttpRequestInterceptor)object3.next();
                    ((HttpProcessorBuilder)object4).addLast((HttpRequestInterceptor)object2);
                }
            }
            if (this.responseLast != null) {
                object3 = this.responseLast.iterator();
                while (object3.hasNext()) {
                    object2 = (HttpResponseInterceptor)object3.next();
                    ((HttpProcessorBuilder)object4).addLast((HttpResponseInterceptor)object2);
                }
            }
            httpProcessor = ((HttpProcessorBuilder)object4).build();
        }
        clientExecChain = new ProtocolExec(clientExecChain, httpProcessor);
        clientExecChain = this.decorateProtocolExec(clientExecChain);
        if (!this.automaticRetriesDisabled) {
            object4 = this.retryHandler;
            if (object4 == null) {
                object4 = DefaultHttpRequestRetryHandler.INSTANCE;
            }
            clientExecChain = new RetryExec(clientExecChain, (HttpRequestRetryHandler)object4);
        }
        if ((object4 = this.routePlanner) == null) {
            object3 = this.schemePortResolver;
            if (object3 == null) {
                object3 = DefaultSchemePortResolver.INSTANCE;
            }
            object4 = this.proxy != null ? new DefaultProxyRoutePlanner(this.proxy, (SchemePortResolver)object3) : (this.systemProperties ? new SystemDefaultRoutePlanner((SchemePortResolver)object3, ProxySelector.getDefault()) : new DefaultRoutePlanner((SchemePortResolver)object3));
        }
        if ((object3 = this.serviceUnavailStrategy) != null) {
            clientExecChain = new ServiceUnavailableRetryExec(clientExecChain, (ServiceUnavailableRetryStrategy)object3);
        }
        if (!this.redirectHandlingDisabled) {
            object2 = this.redirectStrategy;
            if (object2 == null) {
                object2 = DefaultRedirectStrategy.INSTANCE;
            }
            clientExecChain = new RedirectExec(clientExecChain, (HttpRoutePlanner)object4, (RedirectStrategy)object2);
        }
        if (this.backoffManager != null && this.connectionBackoffStrategy != null) {
            clientExecChain = new BackoffStrategyExec(clientExecChain, this.connectionBackoffStrategy, this.backoffManager);
        }
        if ((object2 = this.authSchemeRegistry) == null) {
            object2 = RegistryBuilder.create().register("Basic", new BasicSchemeFactory()).register("Digest", (BasicSchemeFactory)((Object)new DigestSchemeFactory())).register("NTLM", (BasicSchemeFactory)((Object)new NTLMSchemeFactory())).register("Negotiate", (BasicSchemeFactory)((Object)new SPNegoSchemeFactory())).register("Kerberos", (BasicSchemeFactory)((Object)new KerberosSchemeFactory())).build();
        }
        if ((object = this.cookieSpecRegistry) == null) {
            object = CookieSpecRegistries.createDefault(publicSuffixMatcher);
        }
        if ((cookieStore = this.cookieStore) == null) {
            cookieStore = new BasicCookieStore();
        }
        if ((credentialsProvider = this.credentialsProvider) == null) {
            credentialsProvider = this.systemProperties ? new SystemDefaultCredentialsProvider() : new BasicCredentialsProvider();
        }
        ArrayList<Closeable> arrayList2 = arrayList = this.closeables != null ? new ArrayList<Closeable>(this.closeables) : null;
        if (!this.connManagerShared) {
            if (arrayList == null) {
                arrayList = new ArrayList(1);
            }
            Object object9 = object8;
            if (this.evictExpiredConnections || this.evictIdleConnections) {
                IdleConnectionEvictor idleConnectionEvictor = new IdleConnectionEvictor((HttpClientConnectionManager)object9, this.maxIdleTime > 0L ? this.maxIdleTime : 10L, this.maxIdleTimeUnit != null ? this.maxIdleTimeUnit : TimeUnit.SECONDS, this.maxIdleTime, this.maxIdleTimeUnit);
                arrayList.add(new Closeable(this, idleConnectionEvictor){
                    final IdleConnectionEvictor val$connectionEvictor;
                    final HttpClientBuilder this$0;
                    {
                        this.this$0 = httpClientBuilder;
                        this.val$connectionEvictor = idleConnectionEvictor;
                    }

                    @Override
                    public void close() throws IOException {
                        this.val$connectionEvictor.shutdown();
                        try {
                            this.val$connectionEvictor.awaitTermination(1L, TimeUnit.SECONDS);
                        } catch (InterruptedException interruptedException) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
                idleConnectionEvictor.start();
            }
            arrayList.add(new Closeable(this, (HttpClientConnectionManager)object9){
                final HttpClientConnectionManager val$cm;
                final HttpClientBuilder this$0;
                {
                    this.this$0 = httpClientBuilder;
                    this.val$cm = httpClientConnectionManager;
                }

                @Override
                public void close() throws IOException {
                    this.val$cm.shutdown();
                }
            });
        }
        return new InternalHttpClient(clientExecChain, (HttpClientConnectionManager)object8, (HttpRoutePlanner)object4, (Lookup<CookieSpecProvider>)object, (Lookup<AuthSchemeProvider>)object2, cookieStore, credentialsProvider, this.defaultRequestConfig != null ? this.defaultRequestConfig : RequestConfig.DEFAULT, (List<Closeable>)arrayList);
    }
}

