/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.util.AbstractConstant;
import io.netty.util.Constant;
import io.netty.util.ConstantPool;
import java.net.InetAddress;
import java.net.NetworkInterface;

public class ChannelOption<T>
extends AbstractConstant<ChannelOption<T>> {
    private static final ConstantPool<ChannelOption<Object>> pool = new ConstantPool<ChannelOption<Object>>(){

        @Override
        protected ChannelOption<Object> newConstant(int n, String string) {
            return new ChannelOption<Object>(n, string, null);
        }

        @Override
        protected Constant newConstant(int n, String string) {
            return this.newConstant(n, string);
        }
    };
    public static final ChannelOption<ByteBufAllocator> ALLOCATOR = ChannelOption.valueOf("ALLOCATOR");
    public static final ChannelOption<RecvByteBufAllocator> RCVBUF_ALLOCATOR = ChannelOption.valueOf("RCVBUF_ALLOCATOR");
    public static final ChannelOption<MessageSizeEstimator> MESSAGE_SIZE_ESTIMATOR = ChannelOption.valueOf("MESSAGE_SIZE_ESTIMATOR");
    public static final ChannelOption<Integer> CONNECT_TIMEOUT_MILLIS = ChannelOption.valueOf("CONNECT_TIMEOUT_MILLIS");
    @Deprecated
    public static final ChannelOption<Integer> MAX_MESSAGES_PER_READ = ChannelOption.valueOf("MAX_MESSAGES_PER_READ");
    public static final ChannelOption<Integer> WRITE_SPIN_COUNT = ChannelOption.valueOf("WRITE_SPIN_COUNT");
    @Deprecated
    public static final ChannelOption<Integer> WRITE_BUFFER_HIGH_WATER_MARK = ChannelOption.valueOf("WRITE_BUFFER_HIGH_WATER_MARK");
    @Deprecated
    public static final ChannelOption<Integer> WRITE_BUFFER_LOW_WATER_MARK = ChannelOption.valueOf("WRITE_BUFFER_LOW_WATER_MARK");
    public static final ChannelOption<WriteBufferWaterMark> WRITE_BUFFER_WATER_MARK = ChannelOption.valueOf("WRITE_BUFFER_WATER_MARK");
    public static final ChannelOption<Boolean> ALLOW_HALF_CLOSURE = ChannelOption.valueOf("ALLOW_HALF_CLOSURE");
    public static final ChannelOption<Boolean> AUTO_READ = ChannelOption.valueOf("AUTO_READ");
    @Deprecated
    public static final ChannelOption<Boolean> AUTO_CLOSE = ChannelOption.valueOf("AUTO_CLOSE");
    public static final ChannelOption<Boolean> SO_BROADCAST = ChannelOption.valueOf("SO_BROADCAST");
    public static final ChannelOption<Boolean> SO_KEEPALIVE = ChannelOption.valueOf("SO_KEEPALIVE");
    public static final ChannelOption<Integer> SO_SNDBUF = ChannelOption.valueOf("SO_SNDBUF");
    public static final ChannelOption<Integer> SO_RCVBUF = ChannelOption.valueOf("SO_RCVBUF");
    public static final ChannelOption<Boolean> SO_REUSEADDR = ChannelOption.valueOf("SO_REUSEADDR");
    public static final ChannelOption<Integer> SO_LINGER = ChannelOption.valueOf("SO_LINGER");
    public static final ChannelOption<Integer> SO_BACKLOG = ChannelOption.valueOf("SO_BACKLOG");
    public static final ChannelOption<Integer> SO_TIMEOUT = ChannelOption.valueOf("SO_TIMEOUT");
    public static final ChannelOption<Integer> IP_TOS = ChannelOption.valueOf("IP_TOS");
    public static final ChannelOption<InetAddress> IP_MULTICAST_ADDR = ChannelOption.valueOf("IP_MULTICAST_ADDR");
    public static final ChannelOption<NetworkInterface> IP_MULTICAST_IF = ChannelOption.valueOf("IP_MULTICAST_IF");
    public static final ChannelOption<Integer> IP_MULTICAST_TTL = ChannelOption.valueOf("IP_MULTICAST_TTL");
    public static final ChannelOption<Boolean> IP_MULTICAST_LOOP_DISABLED = ChannelOption.valueOf("IP_MULTICAST_LOOP_DISABLED");
    public static final ChannelOption<Boolean> TCP_NODELAY = ChannelOption.valueOf("TCP_NODELAY");
    @Deprecated
    public static final ChannelOption<Boolean> DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION = ChannelOption.valueOf("DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION");
    public static final ChannelOption<Boolean> SINGLE_EVENTEXECUTOR_PER_GROUP = ChannelOption.valueOf("SINGLE_EVENTEXECUTOR_PER_GROUP");

    public static <T> ChannelOption<T> valueOf(String string) {
        return pool.valueOf(string);
    }

    public static <T> ChannelOption<T> valueOf(Class<?> clazz, String string) {
        return pool.valueOf(clazz, string);
    }

    public static boolean exists(String string) {
        return pool.exists(string);
    }

    public static <T> ChannelOption<T> newInstance(String string) {
        return pool.newInstance(string);
    }

    private ChannelOption(int n, String string) {
        super(n, string);
    }

    @Deprecated
    protected ChannelOption(String string) {
        this(pool.nextId(), string);
    }

    public void validate(T t) {
        if (t == null) {
            throw new NullPointerException("value");
        }
    }

    ChannelOption(int n, String string, 1 var3_3) {
        this(n, string);
    }
}

