/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket.nio;

import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.socket.DatagramChannelConfig;
import io.netty.channel.socket.DefaultDatagramChannelConfig;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;
import java.util.Enumeration;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class NioDatagramChannelConfig
extends DefaultDatagramChannelConfig {
    private static final Object IP_MULTICAST_TTL;
    private static final Object IP_MULTICAST_IF;
    private static final Object IP_MULTICAST_LOOP;
    private static final Method GET_OPTION;
    private static final Method SET_OPTION;
    private final DatagramChannel javaChannel;

    NioDatagramChannelConfig(NioDatagramChannel nioDatagramChannel, DatagramChannel datagramChannel) {
        super(nioDatagramChannel, datagramChannel.socket());
        this.javaChannel = datagramChannel;
    }

    @Override
    public int getTimeToLive() {
        return (Integer)this.getOption0(IP_MULTICAST_TTL);
    }

    @Override
    public DatagramChannelConfig setTimeToLive(int n) {
        this.setOption0(IP_MULTICAST_TTL, n);
        return this;
    }

    @Override
    public InetAddress getInterface() {
        Enumeration<InetAddress> enumeration;
        NetworkInterface networkInterface = this.getNetworkInterface();
        if (networkInterface != null && (enumeration = SocketUtils.addressesFromNetworkInterface(networkInterface)).hasMoreElements()) {
            return enumeration.nextElement();
        }
        return null;
    }

    @Override
    public DatagramChannelConfig setInterface(InetAddress inetAddress) {
        try {
            this.setNetworkInterface(NetworkInterface.getByInetAddress(inetAddress));
        } catch (SocketException socketException) {
            throw new ChannelException(socketException);
        }
        return this;
    }

    @Override
    public NetworkInterface getNetworkInterface() {
        return (NetworkInterface)this.getOption0(IP_MULTICAST_IF);
    }

    @Override
    public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        this.setOption0(IP_MULTICAST_IF, networkInterface);
        return this;
    }

    @Override
    public boolean isLoopbackModeDisabled() {
        return (Boolean)this.getOption0(IP_MULTICAST_LOOP);
    }

    @Override
    public DatagramChannelConfig setLoopbackModeDisabled(boolean bl) {
        this.setOption0(IP_MULTICAST_LOOP, bl);
        return this;
    }

    @Override
    public DatagramChannelConfig setAutoRead(boolean bl) {
        super.setAutoRead(bl);
        return this;
    }

    @Override
    protected void autoReadCleared() {
        ((NioDatagramChannel)this.channel).clearReadPending0();
    }

    private Object getOption0(Object object) {
        if (GET_OPTION == null) {
            throw new UnsupportedOperationException();
        }
        try {
            return GET_OPTION.invoke(this.javaChannel, object);
        } catch (Exception exception) {
            throw new ChannelException(exception);
        }
    }

    private void setOption0(Object object, Object object2) {
        if (SET_OPTION == null) {
            throw new UnsupportedOperationException();
        }
        try {
            SET_OPTION.invoke(this.javaChannel, object, object2);
        } catch (Exception exception) {
            throw new ChannelException(exception);
        }
    }

    @Override
    public ChannelConfig setAutoRead(boolean bl) {
        return this.setAutoRead(bl);
    }

    static {
        ClassLoader classLoader = PlatformDependent.getClassLoader(DatagramChannel.class);
        Class<?> clazz = null;
        try {
            clazz = Class.forName("java.net.SocketOption", true, classLoader);
        } catch (Exception exception) {
            // empty catch block
        }
        Class<?> clazz2 = null;
        try {
            clazz2 = Class.forName("java.net.StandardSocketOptions", true, classLoader);
        } catch (Exception exception) {
            // empty catch block
        }
        Object object = null;
        Object object2 = null;
        Object object3 = null;
        Method method = null;
        Method method2 = null;
        if (clazz != null) {
            try {
                object = clazz2.getDeclaredField("IP_MULTICAST_TTL").get(null);
            } catch (Exception exception) {
                throw new Error("cannot locate the IP_MULTICAST_TTL field", exception);
            }
            try {
                object2 = clazz2.getDeclaredField("IP_MULTICAST_IF").get(null);
            } catch (Exception exception) {
                throw new Error("cannot locate the IP_MULTICAST_IF field", exception);
            }
            try {
                object3 = clazz2.getDeclaredField("IP_MULTICAST_LOOP").get(null);
            } catch (Exception exception) {
                throw new Error("cannot locate the IP_MULTICAST_LOOP field", exception);
            }
            Class<?> clazz3 = null;
            try {
                clazz3 = Class.forName("java.nio.channels.NetworkChannel", true, classLoader);
            } catch (Throwable throwable) {
                // empty catch block
            }
            if (clazz3 == null) {
                method = null;
                method2 = null;
            } else {
                try {
                    method = clazz3.getDeclaredMethod("getOption", clazz);
                } catch (Exception exception) {
                    throw new Error("cannot locate the getOption() method", exception);
                }
                try {
                    method2 = clazz3.getDeclaredMethod("setOption", clazz, Object.class);
                } catch (Exception exception) {
                    throw new Error("cannot locate the setOption() method", exception);
                }
            }
        }
        IP_MULTICAST_TTL = object;
        IP_MULTICAST_IF = object2;
        IP_MULTICAST_LOOP = object3;
        GET_OPTION = method;
        SET_OPTION = method2;
    }
}

