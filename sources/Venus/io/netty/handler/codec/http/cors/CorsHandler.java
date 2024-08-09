/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cors;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.cors.CorsConfig;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collections;
import java.util.List;

public class CorsHandler
extends ChannelDuplexHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(CorsHandler.class);
    private static final String ANY_ORIGIN = "*";
    private static final String NULL_ORIGIN = "null";
    private CorsConfig config;
    private HttpRequest request;
    private final List<CorsConfig> configList;
    private boolean isShortCircuit;

    public CorsHandler(CorsConfig corsConfig) {
        this(Collections.singletonList(ObjectUtil.checkNotNull(corsConfig, "config")), corsConfig.isShortCircuit());
    }

    public CorsHandler(List<CorsConfig> list, boolean bl) {
        ObjectUtil.checkNonEmpty(list, "configList");
        this.configList = list;
        this.isShortCircuit = bl;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (object instanceof HttpRequest) {
            this.request = (HttpRequest)object;
            String string = this.request.headers().get(HttpHeaderNames.ORIGIN);
            this.config = this.getForOrigin(string);
            if (CorsHandler.isPreflightRequest(this.request)) {
                this.handlePreflight(channelHandlerContext, this.request);
                return;
            }
            if (this.isShortCircuit && string != null && this.config == null) {
                CorsHandler.forbidden(channelHandlerContext, this.request);
                return;
            }
        }
        channelHandlerContext.fireChannelRead(object);
    }

    private void handlePreflight(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK, true, true);
        if (this.setOrigin(defaultFullHttpResponse)) {
            this.setAllowMethods(defaultFullHttpResponse);
            this.setAllowHeaders(defaultFullHttpResponse);
            this.setAllowCredentials(defaultFullHttpResponse);
            this.setMaxAge(defaultFullHttpResponse);
            this.setPreflightHeaders(defaultFullHttpResponse);
        }
        if (!defaultFullHttpResponse.headers().contains(HttpHeaderNames.CONTENT_LENGTH)) {
            defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)HttpHeaderValues.ZERO);
        }
        ReferenceCountUtil.release(httpRequest);
        CorsHandler.respond(channelHandlerContext, httpRequest, defaultFullHttpResponse);
    }

    private void setPreflightHeaders(HttpResponse httpResponse) {
        httpResponse.headers().add(this.config.preflightResponseHeaders());
    }

    private CorsConfig getForOrigin(String string) {
        for (CorsConfig corsConfig : this.configList) {
            if (corsConfig.isAnyOriginSupported()) {
                return corsConfig;
            }
            if (corsConfig.origins().contains(string)) {
                return corsConfig;
            }
            if (!corsConfig.isNullOriginAllowed() && !NULL_ORIGIN.equals(string)) continue;
            return corsConfig;
        }
        return null;
    }

    private boolean setOrigin(HttpResponse httpResponse) {
        String string = this.request.headers().get(HttpHeaderNames.ORIGIN);
        if (string != null && this.config != null) {
            if (NULL_ORIGIN.equals(string) && this.config.isNullOriginAllowed()) {
                CorsHandler.setNullOrigin(httpResponse);
                return false;
            }
            if (this.config.isAnyOriginSupported()) {
                if (this.config.isCredentialsAllowed()) {
                    this.echoRequestOrigin(httpResponse);
                    CorsHandler.setVaryHeader(httpResponse);
                } else {
                    CorsHandler.setAnyOrigin(httpResponse);
                }
                return false;
            }
            if (this.config.origins().contains(string)) {
                CorsHandler.setOrigin(httpResponse, string);
                CorsHandler.setVaryHeader(httpResponse);
                return false;
            }
            logger.debug("Request origin [{}]] was not among the configured origins [{}]", (Object)string, (Object)this.config.origins());
        }
        return true;
    }

    private void echoRequestOrigin(HttpResponse httpResponse) {
        CorsHandler.setOrigin(httpResponse, this.request.headers().get(HttpHeaderNames.ORIGIN));
    }

    private static void setVaryHeader(HttpResponse httpResponse) {
        httpResponse.headers().set((CharSequence)HttpHeaderNames.VARY, (Object)HttpHeaderNames.ORIGIN);
    }

    private static void setAnyOrigin(HttpResponse httpResponse) {
        CorsHandler.setOrigin(httpResponse, ANY_ORIGIN);
    }

    private static void setNullOrigin(HttpResponse httpResponse) {
        CorsHandler.setOrigin(httpResponse, NULL_ORIGIN);
    }

    private static void setOrigin(HttpResponse httpResponse, String string) {
        httpResponse.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, (Object)string);
    }

    private void setAllowCredentials(HttpResponse httpResponse) {
        if (this.config.isCredentialsAllowed() && !httpResponse.headers().get(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN).equals(ANY_ORIGIN)) {
            httpResponse.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_CREDENTIALS, (Object)"true");
        }
    }

    private static boolean isPreflightRequest(HttpRequest httpRequest) {
        HttpHeaders httpHeaders = httpRequest.headers();
        return httpRequest.method().equals(HttpMethod.OPTIONS) && httpHeaders.contains(HttpHeaderNames.ORIGIN) && httpHeaders.contains(HttpHeaderNames.ACCESS_CONTROL_REQUEST_METHOD);
    }

    private void setExposeHeaders(HttpResponse httpResponse) {
        if (!this.config.exposedHeaders().isEmpty()) {
            httpResponse.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_EXPOSE_HEADERS, this.config.exposedHeaders());
        }
    }

    private void setAllowMethods(HttpResponse httpResponse) {
        httpResponse.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, this.config.allowedRequestMethods());
    }

    private void setAllowHeaders(HttpResponse httpResponse) {
        httpResponse.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, this.config.allowedRequestHeaders());
    }

    private void setMaxAge(HttpResponse httpResponse) {
        httpResponse.headers().set((CharSequence)HttpHeaderNames.ACCESS_CONTROL_MAX_AGE, (Object)this.config.maxAge());
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        HttpResponse httpResponse;
        if (this.config != null && this.config.isCorsSupportEnabled() && object instanceof HttpResponse && this.setOrigin(httpResponse = (HttpResponse)object)) {
            this.setAllowCredentials(httpResponse);
            this.setExposeHeaders(httpResponse);
        }
        channelHandlerContext.write(object, channelPromise);
    }

    private static void forbidden(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
        DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.FORBIDDEN);
        defaultFullHttpResponse.headers().set((CharSequence)HttpHeaderNames.CONTENT_LENGTH, (Object)HttpHeaderValues.ZERO);
        ReferenceCountUtil.release(httpRequest);
        CorsHandler.respond(channelHandlerContext, httpRequest, defaultFullHttpResponse);
    }

    private static void respond(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest, HttpResponse httpResponse) {
        boolean bl = HttpUtil.isKeepAlive(httpRequest);
        HttpUtil.setKeepAlive(httpResponse, bl);
        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(httpResponse);
        if (!bl) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }
}

