/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.proxy;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.socksx.v5.DefaultSocks5CommandRequest;
import io.netty.handler.codec.socksx.v5.DefaultSocks5InitialRequest;
import io.netty.handler.codec.socksx.v5.DefaultSocks5PasswordAuthRequest;
import io.netty.handler.codec.socksx.v5.Socks5AddressType;
import io.netty.handler.codec.socksx.v5.Socks5AuthMethod;
import io.netty.handler.codec.socksx.v5.Socks5ClientEncoder;
import io.netty.handler.codec.socksx.v5.Socks5CommandResponse;
import io.netty.handler.codec.socksx.v5.Socks5CommandResponseDecoder;
import io.netty.handler.codec.socksx.v5.Socks5CommandStatus;
import io.netty.handler.codec.socksx.v5.Socks5CommandType;
import io.netty.handler.codec.socksx.v5.Socks5InitialRequest;
import io.netty.handler.codec.socksx.v5.Socks5InitialResponse;
import io.netty.handler.codec.socksx.v5.Socks5InitialResponseDecoder;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthResponse;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthResponseDecoder;
import io.netty.handler.codec.socksx.v5.Socks5PasswordAuthStatus;
import io.netty.handler.proxy.ProxyConnectException;
import io.netty.handler.proxy.ProxyHandler;
import io.netty.util.NetUtil;
import io.netty.util.internal.StringUtil;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Collections;

public final class Socks5ProxyHandler
extends ProxyHandler {
    private static final String PROTOCOL = "socks5";
    private static final String AUTH_PASSWORD = "password";
    private static final Socks5InitialRequest INIT_REQUEST_NO_AUTH = new DefaultSocks5InitialRequest(Collections.singletonList(Socks5AuthMethod.NO_AUTH));
    private static final Socks5InitialRequest INIT_REQUEST_PASSWORD = new DefaultSocks5InitialRequest(Arrays.asList(Socks5AuthMethod.NO_AUTH, Socks5AuthMethod.PASSWORD));
    private final String username;
    private final String password;
    private String decoderName;
    private String encoderName;

    public Socks5ProxyHandler(SocketAddress socketAddress) {
        this(socketAddress, null, null);
    }

    public Socks5ProxyHandler(SocketAddress socketAddress, String string, String string2) {
        super(socketAddress);
        if (string != null && string.isEmpty()) {
            string = null;
        }
        if (string2 != null && string2.isEmpty()) {
            string2 = null;
        }
        this.username = string;
        this.password = string2;
    }

    @Override
    public String protocol() {
        return PROTOCOL;
    }

    @Override
    public String authScheme() {
        return this.socksAuthMethod() == Socks5AuthMethod.PASSWORD ? AUTH_PASSWORD : "none";
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    @Override
    protected void addCodec(ChannelHandlerContext channelHandlerContext) throws Exception {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        String string = channelHandlerContext.name();
        Socks5InitialResponseDecoder socks5InitialResponseDecoder = new Socks5InitialResponseDecoder();
        channelPipeline.addBefore(string, null, socks5InitialResponseDecoder);
        this.decoderName = channelPipeline.context(socks5InitialResponseDecoder).name();
        this.encoderName = this.decoderName + ".encoder";
        channelPipeline.addBefore(string, this.encoderName, Socks5ClientEncoder.DEFAULT);
    }

    @Override
    protected void removeEncoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.pipeline().remove(this.encoderName);
    }

    @Override
    protected void removeDecoder(ChannelHandlerContext channelHandlerContext) throws Exception {
        ChannelPipeline channelPipeline = channelHandlerContext.pipeline();
        if (channelPipeline.context(this.decoderName) != null) {
            channelPipeline.remove(this.decoderName);
        }
    }

    @Override
    protected Object newInitialMessage(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.socksAuthMethod() == Socks5AuthMethod.PASSWORD ? INIT_REQUEST_PASSWORD : INIT_REQUEST_NO_AUTH;
    }

    @Override
    protected boolean handleResponse(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (object instanceof Socks5InitialResponse) {
            Socks5InitialResponse socks5InitialResponse = (Socks5InitialResponse)object;
            Socks5AuthMethod socks5AuthMethod = this.socksAuthMethod();
            if (socks5InitialResponse.authMethod() != Socks5AuthMethod.NO_AUTH && socks5InitialResponse.authMethod() != socks5AuthMethod) {
                throw new ProxyConnectException(this.exceptionMessage("unexpected authMethod: " + socks5InitialResponse.authMethod()));
            }
            if (socks5AuthMethod == Socks5AuthMethod.NO_AUTH) {
                this.sendConnectCommand(channelHandlerContext);
            } else if (socks5AuthMethod == Socks5AuthMethod.PASSWORD) {
                channelHandlerContext.pipeline().replace(this.decoderName, this.decoderName, (ChannelHandler)new Socks5PasswordAuthResponseDecoder());
                this.sendToProxyServer(new DefaultSocks5PasswordAuthRequest(this.username != null ? this.username : "", this.password != null ? this.password : ""));
            } else {
                throw new Error();
            }
            return true;
        }
        if (object instanceof Socks5PasswordAuthResponse) {
            Socks5PasswordAuthResponse socks5PasswordAuthResponse = (Socks5PasswordAuthResponse)object;
            if (socks5PasswordAuthResponse.status() != Socks5PasswordAuthStatus.SUCCESS) {
                throw new ProxyConnectException(this.exceptionMessage("authStatus: " + socks5PasswordAuthResponse.status()));
            }
            this.sendConnectCommand(channelHandlerContext);
            return true;
        }
        Socks5CommandResponse socks5CommandResponse = (Socks5CommandResponse)object;
        if (socks5CommandResponse.status() != Socks5CommandStatus.SUCCESS) {
            throw new ProxyConnectException(this.exceptionMessage("status: " + socks5CommandResponse.status()));
        }
        return false;
    }

    private Socks5AuthMethod socksAuthMethod() {
        Socks5AuthMethod socks5AuthMethod = this.username == null && this.password == null ? Socks5AuthMethod.NO_AUTH : Socks5AuthMethod.PASSWORD;
        return socks5AuthMethod;
    }

    private void sendConnectCommand(ChannelHandlerContext channelHandlerContext) throws Exception {
        String string;
        Socks5AddressType socks5AddressType;
        InetSocketAddress inetSocketAddress = (InetSocketAddress)this.destinationAddress();
        if (inetSocketAddress.isUnresolved()) {
            socks5AddressType = Socks5AddressType.DOMAIN;
            string = inetSocketAddress.getHostString();
        } else {
            string = inetSocketAddress.getAddress().getHostAddress();
            if (NetUtil.isValidIpV4Address(string)) {
                socks5AddressType = Socks5AddressType.IPv4;
            } else if (NetUtil.isValidIpV6Address(string)) {
                socks5AddressType = Socks5AddressType.IPv6;
            } else {
                throw new ProxyConnectException(this.exceptionMessage("unknown address type: " + StringUtil.simpleClassName(string)));
            }
        }
        channelHandlerContext.pipeline().replace(this.decoderName, this.decoderName, (ChannelHandler)new Socks5CommandResponseDecoder());
        this.sendToProxyServer(new DefaultSocks5CommandRequest(Socks5CommandType.CONNECT, socks5AddressType, string, inetSocketAddress.getPort()));
    }
}

