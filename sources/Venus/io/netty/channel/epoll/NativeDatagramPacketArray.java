/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.unix.IovArray;
import io.netty.channel.unix.Limits;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.util.concurrent.FastThreadLocal;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

final class NativeDatagramPacketArray
implements ChannelOutboundBuffer.MessageProcessor {
    private static final FastThreadLocal<NativeDatagramPacketArray> ARRAY = new FastThreadLocal<NativeDatagramPacketArray>(){

        @Override
        protected NativeDatagramPacketArray initialValue() throws Exception {
            return new NativeDatagramPacketArray(null);
        }

        @Override
        protected void onRemoval(NativeDatagramPacketArray nativeDatagramPacketArray) throws Exception {
            NativeDatagramPacket[] nativeDatagramPacketArray2;
            for (NativeDatagramPacket nativeDatagramPacket : nativeDatagramPacketArray2 = NativeDatagramPacketArray.access$100(nativeDatagramPacketArray)) {
                NativeDatagramPacket.access$200(nativeDatagramPacket);
            }
        }

        @Override
        protected void onRemoval(Object object) throws Exception {
            this.onRemoval((NativeDatagramPacketArray)object);
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    };
    private final NativeDatagramPacket[] packets = new NativeDatagramPacket[Limits.UIO_MAX_IOV];
    private int count;

    private NativeDatagramPacketArray() {
        for (int i = 0; i < this.packets.length; ++i) {
            this.packets[i] = new NativeDatagramPacket();
        }
    }

    boolean add(DatagramPacket datagramPacket) {
        if (this.count == this.packets.length) {
            return true;
        }
        ByteBuf byteBuf = (ByteBuf)datagramPacket.content();
        int n = byteBuf.readableBytes();
        if (n == 0) {
            return false;
        }
        NativeDatagramPacket nativeDatagramPacket = this.packets[this.count];
        InetSocketAddress inetSocketAddress = (InetSocketAddress)datagramPacket.recipient();
        if (!NativeDatagramPacket.access$300(nativeDatagramPacket, byteBuf, inetSocketAddress)) {
            return true;
        }
        ++this.count;
        return false;
    }

    @Override
    public boolean processMessage(Object object) throws Exception {
        return object instanceof DatagramPacket && this.add((DatagramPacket)object);
    }

    int count() {
        return this.count;
    }

    NativeDatagramPacket[] packets() {
        return this.packets;
    }

    static NativeDatagramPacketArray getInstance(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        NativeDatagramPacketArray nativeDatagramPacketArray = ARRAY.get();
        nativeDatagramPacketArray.count = 0;
        channelOutboundBuffer.forEachFlushedMessage(nativeDatagramPacketArray);
        return nativeDatagramPacketArray;
    }

    NativeDatagramPacketArray(1 var1_1) {
        this();
    }

    static NativeDatagramPacket[] access$100(NativeDatagramPacketArray nativeDatagramPacketArray) {
        return nativeDatagramPacketArray.packets;
    }

    static final class NativeDatagramPacket {
        private final IovArray array = new IovArray();
        private long memoryAddress;
        private int count;
        private byte[] addr;
        private int scopeId;
        private int port;

        NativeDatagramPacket() {
        }

        private void release() {
            this.array.release();
        }

        private boolean init(ByteBuf byteBuf, InetSocketAddress inetSocketAddress) {
            this.array.clear();
            if (!this.array.add(byteBuf)) {
                return true;
            }
            this.memoryAddress = this.array.memoryAddress(0);
            this.count = this.array.count();
            InetAddress inetAddress = inetSocketAddress.getAddress();
            if (inetAddress instanceof Inet6Address) {
                this.addr = inetAddress.getAddress();
                this.scopeId = ((Inet6Address)inetAddress).getScopeId();
            } else {
                this.addr = NativeInetAddress.ipv4MappedIpv6Address(inetAddress.getAddress());
                this.scopeId = 0;
            }
            this.port = inetSocketAddress.getPort();
            return false;
        }

        static void access$200(NativeDatagramPacket nativeDatagramPacket) {
            nativeDatagramPacket.release();
        }

        static boolean access$300(NativeDatagramPacket nativeDatagramPacket, ByteBuf byteBuf, InetSocketAddress inetSocketAddress) {
            return nativeDatagramPacket.init(byteBuf, inetSocketAddress);
        }
    }
}

