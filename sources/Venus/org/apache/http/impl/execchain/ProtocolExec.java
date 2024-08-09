/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.execchain;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpExecutionAware;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.execchain.ClientExecChain;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public class ProtocolExec
implements ClientExecChain {
    private final Log log = LogFactory.getLog(this.getClass());
    private final ClientExecChain requestExecutor;
    private final HttpProcessor httpProcessor;

    public ProtocolExec(ClientExecChain clientExecChain, HttpProcessor httpProcessor) {
        Args.notNull(clientExecChain, "HTTP client request executor");
        Args.notNull(httpProcessor, "HTTP protocol processor");
        this.requestExecutor = clientExecChain;
        this.httpProcessor = httpProcessor;
    }

    void rewriteRequestURI(HttpRequestWrapper httpRequestWrapper, HttpRoute httpRoute, boolean bl) throws ProtocolException {
        URI uRI = httpRequestWrapper.getURI();
        if (uRI != null) {
            try {
                httpRequestWrapper.setURI(URIUtils.rewriteURIForRoute(uRI, httpRoute, bl));
            } catch (URISyntaxException uRISyntaxException) {
                throw new ProtocolException("Invalid URI: " + uRI, uRISyntaxException);
            }
        }
    }

    @Override
    public CloseableHttpResponse execute(HttpRoute httpRoute, HttpRequestWrapper httpRequestWrapper, HttpClientContext httpClientContext, HttpExecutionAware httpExecutionAware) throws IOException, HttpException {
        Object object;
        Object object2;
        URI uRI;
        block18: {
            Args.notNull(httpRoute, "HTTP route");
            Args.notNull(httpRequestWrapper, "HTTP request");
            Args.notNull(httpClientContext, "HTTP context");
            HttpRequest httpRequest = httpRequestWrapper.getOriginal();
            uRI = null;
            if (httpRequest instanceof HttpUriRequest) {
                uRI = ((HttpUriRequest)httpRequest).getURI();
            } else {
                object2 = httpRequest.getRequestLine().getUri();
                try {
                    uRI = URI.create((String)object2);
                } catch (IllegalArgumentException illegalArgumentException) {
                    if (!this.log.isDebugEnabled()) break block18;
                    this.log.debug("Unable to parse '" + (String)object2 + "' as a valid URI; " + "request URI and Host header may be inconsistent", illegalArgumentException);
                }
            }
        }
        httpRequestWrapper.setURI(uRI);
        this.rewriteRequestURI(httpRequestWrapper, httpRoute, httpClientContext.getRequestConfig().isNormalizeUri());
        object2 = httpRequestWrapper.getParams();
        HttpHost httpHost = (HttpHost)object2.getParameter("http.virtual-host");
        if (httpHost != null && httpHost.getPort() == -1) {
            int n = httpRoute.getTargetHost().getPort();
            if (n != -1) {
                httpHost = new HttpHost(httpHost.getHostName(), n, httpHost.getSchemeName());
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Using virtual host" + httpHost);
            }
        }
        HttpHost httpHost2 = null;
        if (httpHost != null) {
            httpHost2 = httpHost;
        } else if (uRI != null && uRI.isAbsolute() && uRI.getHost() != null) {
            httpHost2 = new HttpHost(uRI.getHost(), uRI.getPort(), uRI.getScheme());
        }
        if (httpHost2 == null) {
            httpHost2 = httpRequestWrapper.getTarget();
        }
        if (httpHost2 == null) {
            httpHost2 = httpRoute.getTargetHost();
        }
        if (uRI != null && (object = uRI.getUserInfo()) != null) {
            CredentialsProvider credentialsProvider = httpClientContext.getCredentialsProvider();
            if (credentialsProvider == null) {
                credentialsProvider = new BasicCredentialsProvider();
                httpClientContext.setCredentialsProvider(credentialsProvider);
            }
            credentialsProvider.setCredentials(new AuthScope(httpHost2), new UsernamePasswordCredentials((String)object));
        }
        httpClientContext.setAttribute("http.target_host", httpHost2);
        httpClientContext.setAttribute("http.route", httpRoute);
        httpClientContext.setAttribute("http.request", httpRequestWrapper);
        this.httpProcessor.process(httpRequestWrapper, (HttpContext)httpClientContext);
        object = this.requestExecutor.execute(httpRoute, httpRequestWrapper, httpClientContext, httpExecutionAware);
        try {
            httpClientContext.setAttribute("http.response", object);
            this.httpProcessor.process((HttpResponse)object, (HttpContext)httpClientContext);
            return object;
        } catch (RuntimeException runtimeException) {
            object.close();
            throw runtimeException;
        } catch (IOException iOException) {
            object.close();
            throw iOException;
        } catch (HttpException httpException) {
            object.close();
            throw httpException;
        }
    }
}

