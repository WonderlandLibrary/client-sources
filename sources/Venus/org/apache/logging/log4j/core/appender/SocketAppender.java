/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAliases;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.ValidHost;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.ValidPort;
import org.apache.logging.log4j.core.layout.SerializedLayout;
import org.apache.logging.log4j.core.net.AbstractSocketManager;
import org.apache.logging.log4j.core.net.Advertiser;
import org.apache.logging.log4j.core.net.DatagramSocketManager;
import org.apache.logging.log4j.core.net.Protocol;
import org.apache.logging.log4j.core.net.SocketOptions;
import org.apache.logging.log4j.core.net.SslSocketManager;
import org.apache.logging.log4j.core.net.TcpSocketManager;
import org.apache.logging.log4j.core.net.ssl.SslConfiguration;
import org.apache.logging.log4j.core.util.Booleans;

@Plugin(name="Socket", category="Core", elementType="appender", printObject=true)
public class SocketAppender
extends AbstractOutputStreamAppender<AbstractSocketManager> {
    private final Object advertisement;
    private final Advertiser advertiser;

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    protected SocketAppender(String string, Layout<? extends Serializable> layout, Filter filter, AbstractSocketManager abstractSocketManager, boolean bl, boolean bl2, Advertiser advertiser) {
        super(string, layout, filter, bl, bl2, abstractSocketManager);
        if (advertiser != null) {
            HashMap<String, String> hashMap = new HashMap<String, String>(layout.getContentFormat());
            hashMap.putAll(abstractSocketManager.getContentFormat());
            hashMap.put("contentType", layout.getContentType());
            hashMap.put("name", string);
            this.advertisement = advertiser.advertise(hashMap);
        } else {
            this.advertisement = null;
        }
        this.advertiser = advertiser;
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        super.stop(l, timeUnit, false);
        if (this.advertiser != null) {
            this.advertiser.unadvertise(this.advertisement);
        }
        this.setStopped();
        return false;
    }

    @Deprecated
    @PluginFactory
    public static SocketAppender createAppender(String string, int n, Protocol protocol, SslConfiguration sslConfiguration, int n2, int n3, boolean bl, String string2, boolean bl2, boolean bl3, Layout<? extends Serializable> layout, Filter filter, boolean bl4, Configuration configuration) {
        return ((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)SocketAppender.newBuilder().withAdvertise(bl4)).setConfiguration(configuration)).withConnectTimeoutMillis(n2)).withFilter(filter)).withHost(string)).withIgnoreExceptions(bl3)).withImmediateFail(bl)).withLayout(layout)).withName(string2)).withPort(n)).withProtocol(protocol)).withReconnectDelayMillis(n3)).withSslConfiguration(sslConfiguration)).build();
    }

    @Deprecated
    public static SocketAppender createAppender(String string, String string2, String string3, SslConfiguration sslConfiguration, int n, String string4, String string5, String string6, String string7, String string8, Layout<? extends Serializable> layout, Filter filter, String string9, Configuration configuration) {
        boolean bl = Booleans.parseBoolean(string7, true);
        boolean bl2 = Boolean.parseBoolean(string9);
        boolean bl3 = Booleans.parseBoolean(string8, true);
        boolean bl4 = Booleans.parseBoolean(string5, true);
        int n2 = AbstractAppender.parseInt(string4, 0);
        int n3 = AbstractAppender.parseInt(string2, 0);
        Protocol protocol = string3 == null ? Protocol.UDP : Protocol.valueOf(string3);
        return SocketAppender.createAppender(string, n3, protocol, sslConfiguration, n, n2, bl4, string6, bl, bl3, layout, filter, bl2, configuration);
    }

    @Deprecated
    protected static AbstractSocketManager createSocketManager(String string, Protocol protocol, String string2, int n, int n2, SslConfiguration sslConfiguration, int n3, boolean bl, Layout<? extends Serializable> layout, int n4) {
        return SocketAppender.createSocketManager(string, protocol, string2, n, n2, sslConfiguration, n3, bl, layout, n4, null);
    }

    protected static AbstractSocketManager createSocketManager(String string, Protocol protocol, String string2, int n, int n2, SslConfiguration sslConfiguration, int n3, boolean bl, Layout<? extends Serializable> layout, int n4, SocketOptions socketOptions) {
        if (protocol == Protocol.TCP && sslConfiguration != null) {
            protocol = Protocol.SSL;
        }
        if (protocol != Protocol.SSL && sslConfiguration != null) {
            LOGGER.info("Appender {} ignoring SSL configuration for {} protocol", (Object)string, (Object)protocol);
        }
        switch (1.$SwitchMap$org$apache$logging$log4j$core$net$Protocol[protocol.ordinal()]) {
            case 1: {
                return TcpSocketManager.getSocketManager(string2, n, n2, n3, bl, layout, n4, socketOptions);
            }
            case 2: {
                return DatagramSocketManager.getSocketManager(string2, n, layout, n4);
            }
            case 3: {
                return SslSocketManager.getSocketManager(sslConfiguration, string2, n, n2, n3, bl, layout, n4, socketOptions);
            }
        }
        throw new IllegalArgumentException(protocol.toString());
    }

    @Override
    protected void directEncodeEvent(LogEvent logEvent) {
        this.writeByteArrayToManager(logEvent);
    }

    static Logger access$000() {
        return LOGGER;
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$logging$log4j$core$net$Protocol = new int[Protocol.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$net$Protocol[Protocol.TCP.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$net$Protocol[Protocol.UDP.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$net$Protocol[Protocol.SSL.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    extends AbstractBuilder<Builder>
    implements org.apache.logging.log4j.core.util.Builder<SocketAppender> {
        @Override
        public SocketAppender build() {
            Protocol protocol;
            String string;
            boolean bl = this.isImmediateFlush();
            boolean bl2 = this.isBufferedIo();
            SerializedLayout serializedLayout = this.getLayout();
            if (serializedLayout == null) {
                serializedLayout = SerializedLayout.createLayout();
            }
            if ((string = this.getName()) == null) {
                SocketAppender.access$000().error("No name provided for SocketAppender");
                return null;
            }
            Protocol protocol2 = this.getProtocol();
            Protocol protocol3 = protocol = protocol2 != null ? protocol2 : Protocol.TCP;
            if (protocol == Protocol.UDP) {
                bl = true;
            }
            AbstractSocketManager abstractSocketManager = SocketAppender.createSocketManager(string, protocol, this.getHost(), this.getPort(), this.getConnectTimeoutMillis(), this.getSslConfiguration(), this.getReconnectDelayMillis(), this.getImmediateFail(), serializedLayout, this.getBufferSize(), this.getSocketOptions());
            return new SocketAppender(string, serializedLayout, this.getFilter(), abstractSocketManager, this.isIgnoreExceptions(), !bl2 || bl, this.getAdvertise() ? this.getConfiguration().getAdvertiser() : null);
        }

        @Override
        public Object build() {
            return this.build();
        }
    }

    public static abstract class AbstractBuilder<B extends AbstractBuilder<B>>
    extends AbstractOutputStreamAppender.Builder<B> {
        @PluginBuilderAttribute
        private boolean advertise;
        @PluginBuilderAttribute
        private int connectTimeoutMillis;
        @PluginBuilderAttribute
        @ValidHost
        private String host = "localhost";
        @PluginBuilderAttribute
        private boolean immediateFail = true;
        @PluginBuilderAttribute
        @ValidPort
        private int port;
        @PluginBuilderAttribute
        private Protocol protocol = Protocol.TCP;
        @PluginBuilderAttribute
        @PluginAliases(value={"reconnectDelay", "reconnectionDelay", "delayMillis", "reconnectionDelayMillis"})
        private int reconnectDelayMillis;
        @PluginElement(value="SocketOptions")
        private SocketOptions socketOptions;
        @PluginElement(value="SslConfiguration")
        @PluginAliases(value={"SslConfig"})
        private SslConfiguration sslConfiguration;

        public boolean getAdvertise() {
            return this.advertise;
        }

        public int getConnectTimeoutMillis() {
            return this.connectTimeoutMillis;
        }

        public String getHost() {
            return this.host;
        }

        public int getPort() {
            return this.port;
        }

        public Protocol getProtocol() {
            return this.protocol;
        }

        public SslConfiguration getSslConfiguration() {
            return this.sslConfiguration;
        }

        public boolean getImmediateFail() {
            return this.immediateFail;
        }

        public B withAdvertise(boolean bl) {
            this.advertise = bl;
            return (B)((AbstractBuilder)this.asBuilder());
        }

        public B withConnectTimeoutMillis(int n) {
            this.connectTimeoutMillis = n;
            return (B)((AbstractBuilder)this.asBuilder());
        }

        public B withHost(String string) {
            this.host = string;
            return (B)((AbstractBuilder)this.asBuilder());
        }

        public B withImmediateFail(boolean bl) {
            this.immediateFail = bl;
            return (B)((AbstractBuilder)this.asBuilder());
        }

        public B withPort(int n) {
            this.port = n;
            return (B)((AbstractBuilder)this.asBuilder());
        }

        public B withProtocol(Protocol protocol) {
            this.protocol = protocol;
            return (B)((AbstractBuilder)this.asBuilder());
        }

        public B withReconnectDelayMillis(int n) {
            this.reconnectDelayMillis = n;
            return (B)((AbstractBuilder)this.asBuilder());
        }

        public B withSocketOptions(SocketOptions socketOptions) {
            this.socketOptions = socketOptions;
            return (B)((AbstractBuilder)this.asBuilder());
        }

        public B withSslConfiguration(SslConfiguration sslConfiguration) {
            this.sslConfiguration = sslConfiguration;
            return (B)((AbstractBuilder)this.asBuilder());
        }

        public int getReconnectDelayMillis() {
            return this.reconnectDelayMillis;
        }

        public SocketOptions getSocketOptions() {
            return this.socketOptions;
        }
    }
}

