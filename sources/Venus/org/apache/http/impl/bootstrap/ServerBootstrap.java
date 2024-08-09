/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.bootstrap;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.ExceptionLogger;
import org.apache.http.HttpConnectionFactory;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.impl.DefaultBHttpServerConnectionFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.SSLServerSetupHandler;
import org.apache.http.protocol.HttpExpectationVerifier;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.protocol.HttpRequestHandlerMapper;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;
import org.apache.http.protocol.UriHttpRequestHandlerMapper;

public class ServerBootstrap {
    private int listenerPort;
    private InetAddress localAddress;
    private SocketConfig socketConfig;
    private ConnectionConfig connectionConfig;
    private LinkedList<HttpRequestInterceptor> requestFirst;
    private LinkedList<HttpRequestInterceptor> requestLast;
    private LinkedList<HttpResponseInterceptor> responseFirst;
    private LinkedList<HttpResponseInterceptor> responseLast;
    private String serverInfo;
    private HttpProcessor httpProcessor;
    private ConnectionReuseStrategy connStrategy;
    private HttpResponseFactory responseFactory;
    private HttpRequestHandlerMapper handlerMapper;
    private Map<String, HttpRequestHandler> handlerMap;
    private HttpExpectationVerifier expectationVerifier;
    private ServerSocketFactory serverSocketFactory;
    private SSLContext sslContext;
    private SSLServerSetupHandler sslSetupHandler;
    private HttpConnectionFactory<? extends DefaultBHttpServerConnection> connectionFactory;
    private ExceptionLogger exceptionLogger;

    private ServerBootstrap() {
    }

    public static ServerBootstrap bootstrap() {
        return new ServerBootstrap();
    }

    public final ServerBootstrap setListenerPort(int n) {
        this.listenerPort = n;
        return this;
    }

    public final ServerBootstrap setLocalAddress(InetAddress inetAddress) {
        this.localAddress = inetAddress;
        return this;
    }

    public final ServerBootstrap setSocketConfig(SocketConfig socketConfig) {
        this.socketConfig = socketConfig;
        return this;
    }

    public final ServerBootstrap setConnectionConfig(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
        return this;
    }

    public final ServerBootstrap setHttpProcessor(HttpProcessor httpProcessor) {
        this.httpProcessor = httpProcessor;
        return this;
    }

    public final ServerBootstrap addInterceptorFirst(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseFirst == null) {
            this.responseFirst = new LinkedList();
        }
        this.responseFirst.addFirst(httpResponseInterceptor);
        return this;
    }

    public final ServerBootstrap addInterceptorLast(HttpResponseInterceptor httpResponseInterceptor) {
        if (httpResponseInterceptor == null) {
            return this;
        }
        if (this.responseLast == null) {
            this.responseLast = new LinkedList();
        }
        this.responseLast.addLast(httpResponseInterceptor);
        return this;
    }

    public final ServerBootstrap addInterceptorFirst(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestFirst == null) {
            this.requestFirst = new LinkedList();
        }
        this.requestFirst.addFirst(httpRequestInterceptor);
        return this;
    }

    public final ServerBootstrap addInterceptorLast(HttpRequestInterceptor httpRequestInterceptor) {
        if (httpRequestInterceptor == null) {
            return this;
        }
        if (this.requestLast == null) {
            this.requestLast = new LinkedList();
        }
        this.requestLast.addLast(httpRequestInterceptor);
        return this;
    }

    public final ServerBootstrap setServerInfo(String string) {
        this.serverInfo = string;
        return this;
    }

    public final ServerBootstrap setConnectionReuseStrategy(ConnectionReuseStrategy connectionReuseStrategy) {
        this.connStrategy = connectionReuseStrategy;
        return this;
    }

    public final ServerBootstrap setResponseFactory(HttpResponseFactory httpResponseFactory) {
        this.responseFactory = httpResponseFactory;
        return this;
    }

    public final ServerBootstrap setHandlerMapper(HttpRequestHandlerMapper httpRequestHandlerMapper) {
        this.handlerMapper = httpRequestHandlerMapper;
        return this;
    }

    public final ServerBootstrap registerHandler(String string, HttpRequestHandler httpRequestHandler) {
        if (string == null || httpRequestHandler == null) {
            return this;
        }
        if (this.handlerMap == null) {
            this.handlerMap = new HashMap<String, HttpRequestHandler>();
        }
        this.handlerMap.put(string, httpRequestHandler);
        return this;
    }

    public final ServerBootstrap setExpectationVerifier(HttpExpectationVerifier httpExpectationVerifier) {
        this.expectationVerifier = httpExpectationVerifier;
        return this;
    }

    public final ServerBootstrap setConnectionFactory(HttpConnectionFactory<? extends DefaultBHttpServerConnection> httpConnectionFactory) {
        this.connectionFactory = httpConnectionFactory;
        return this;
    }

    public final ServerBootstrap setSslSetupHandler(SSLServerSetupHandler sSLServerSetupHandler) {
        this.sslSetupHandler = sSLServerSetupHandler;
        return this;
    }

    public final ServerBootstrap setServerSocketFactory(ServerSocketFactory serverSocketFactory) {
        this.serverSocketFactory = serverSocketFactory;
        return this;
    }

    public final ServerBootstrap setSslContext(SSLContext sSLContext) {
        this.sslContext = sSLContext;
        return this;
    }

    public final ServerBootstrap setExceptionLogger(ExceptionLogger exceptionLogger) {
        this.exceptionLogger = exceptionLogger;
        return this;
    }

    public HttpServer create() {
        ExceptionLogger exceptionLogger;
        DefaultBHttpServerConnectionFactory defaultBHttpServerConnectionFactory;
        Object object;
        Iterator iterator2;
        Object object2;
        Object object3;
        HttpProcessor httpProcessor = this.httpProcessor;
        if (httpProcessor == null) {
            object3 = HttpProcessorBuilder.create();
            if (this.requestFirst != null) {
                object2 = this.requestFirst.iterator();
                while (object2.hasNext()) {
                    iterator2 = (HttpRequestInterceptor)object2.next();
                    ((HttpProcessorBuilder)object3).addFirst((HttpRequestInterceptor)((Object)iterator2));
                }
            }
            if (this.responseFirst != null) {
                object2 = this.responseFirst.iterator();
                while (object2.hasNext()) {
                    iterator2 = (HttpResponseInterceptor)object2.next();
                    ((HttpProcessorBuilder)object3).addFirst((HttpResponseInterceptor)((Object)iterator2));
                }
            }
            if ((object2 = this.serverInfo) == null) {
                object2 = "Apache-HttpCore/1.1";
            }
            ((HttpProcessorBuilder)object3).addAll(new ResponseDate(), new ResponseServer((String)object2), new ResponseContent(), new ResponseConnControl());
            if (this.requestLast != null) {
                iterator2 = this.requestLast.iterator();
                while (iterator2.hasNext()) {
                    object = (HttpRequestInterceptor)iterator2.next();
                    ((HttpProcessorBuilder)object3).addLast((HttpRequestInterceptor)object);
                }
            }
            if (this.responseLast != null) {
                iterator2 = this.responseLast.iterator();
                while (iterator2.hasNext()) {
                    object = (HttpResponseInterceptor)iterator2.next();
                    ((HttpProcessorBuilder)object3).addLast((HttpResponseInterceptor)object);
                }
            }
            httpProcessor = ((HttpProcessorBuilder)object3).build();
        }
        if ((object3 = this.handlerMapper) == null) {
            object2 = new UriHttpRequestHandlerMapper();
            if (this.handlerMap != null) {
                iterator2 = this.handlerMap.entrySet().iterator();
                while (iterator2.hasNext()) {
                    object = (Map.Entry)iterator2.next();
                    ((UriHttpRequestHandlerMapper)object2).register((String)object.getKey(), (HttpRequestHandler)object.getValue());
                }
            }
            object3 = object2;
        }
        if ((object2 = this.connStrategy) == null) {
            object2 = DefaultConnectionReuseStrategy.INSTANCE;
        }
        if ((iterator2 = this.responseFactory) == null) {
            iterator2 = DefaultHttpResponseFactory.INSTANCE;
        }
        object = new HttpService(httpProcessor, (ConnectionReuseStrategy)object2, (HttpResponseFactory)((Object)iterator2), (HttpRequestHandlerMapper)object3, this.expectationVerifier);
        ServerSocketFactory serverSocketFactory = this.serverSocketFactory;
        if (serverSocketFactory == null) {
            serverSocketFactory = this.sslContext != null ? this.sslContext.getServerSocketFactory() : ServerSocketFactory.getDefault();
        }
        if ((defaultBHttpServerConnectionFactory = this.connectionFactory) == null) {
            defaultBHttpServerConnectionFactory = this.connectionConfig != null ? new DefaultBHttpServerConnectionFactory(this.connectionConfig) : DefaultBHttpServerConnectionFactory.INSTANCE;
        }
        if ((exceptionLogger = this.exceptionLogger) == null) {
            exceptionLogger = ExceptionLogger.NO_OP;
        }
        return new HttpServer(this.listenerPort > 0 ? this.listenerPort : 0, this.localAddress, this.socketConfig != null ? this.socketConfig : SocketConfig.DEFAULT, serverSocketFactory, (HttpService)object, defaultBHttpServerConnectionFactory, this.sslSetupHandler, exceptionLogger);
    }
}

