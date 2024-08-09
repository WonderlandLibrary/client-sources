/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.socksx.v4.DefaultSocks4CommandRequest;
import io.netty.handler.codec.socksx.v4.Socks4ClientDecoder;
import io.netty.handler.codec.socksx.v4.Socks4ClientEncoder;
import io.netty.handler.codec.socksx.v4.Socks4CommandResponse;
import io.netty.handler.codec.socksx.v4.Socks4CommandStatus;
import io.netty.handler.codec.socksx.v4.Socks4CommandType;
import io.netty.handler.proxy.ProxyConnectException;
import io.netty.handler.proxy.ProxyHandler;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public final class Socks4ProxyHandler
extends ProxyHandler {
    private static final String PROTOCOL = "socks4";
    private static final String AUTH_USERNAME = "username";
    private final String username;
    private String decoderName;
    private String encoderName;

    public Socks4ProxyHandler(SocketAddress socketAddress) {
        this(socketAddress, null);
    }

    public Socks4ProxyHandler(SocketAddress socketAddress, String string) {
        super(socketAddress);
        if (string != null && string.isEmpty()) {
            string = null;
        }
        this.username = string;
    }

    @Override
    public String protocol() {
        return PROTOCOL;
    }

    @Override
    public String authScheme() {
        return this.username != null ? AUTH_USERNAME : "none";
    }

    public String username() {
        return this.username;
    }

    @Override
    protected void addCodec(ChannelHandlerContext channelHandlerContext) throws Exception {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        String string = channelHandlerContext.name();
        Socks4ClientDecoder socks4ClientDecoder = new Socks4ClientDecoder();
        channelPipeline.addBefore(string, null, socks4ClientDecoder);
        this.decoderName = channelPipeline.context(socks4ClientDecoder).name();
        this.encoderName = this.decoderName + ".encoder";
        channelPipeline.addBefore(string, this.encoderName, Socks4ClientEncoder.INSTANCE);
    }

    @Override
    protected void removeEncoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        channelPipeline.remove(this.encoderName);
    }

    @Override
    protected void removeDecoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        channelPipeline.remove(this.decoderName);
    }

    @Override
    protected Object newInitialMessage(ChannelHandlerContext channelHandlerContext) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress)this.destinationAddress();
        String string = inetSocketAddress.isUnresolved() ? inetSocketAddress.getHostString() : inetSocketAddress.getAddress().getHostAddress();
        return new DefaultSocks4CommandRequest(Socks4CommandType.CONNECT, string, inetSocketAddress.getPort(), this.username != null ? this.username : "");
    }

    @Override
    protected boolean handleResponse(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        Socks4CommandResponse socks4CommandResponse = (Socks4CommandResponse)object;
        Socks4CommandStatus socks4CommandStatus = socks4CommandResponse.status();
        if (socks4CommandStatus == Socks4CommandStatus.SUCCESS) {
            return false;
        }
        throw new ProxyConnectException(this.exceptionMessage("status: " + socks4CommandStatus));
    }
}

