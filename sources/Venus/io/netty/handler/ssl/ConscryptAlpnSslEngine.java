/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.conscrypt.AllocatedBuffer
 *  org.conscrypt.BufferAllocator
 *  org.conscrypt.Conscrypt
 *  org.conscrypt.HandshakeListener
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkSslEngine;
import io.netty.handler.ssl.SslUtils;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.SystemPropertyUtil;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import org.conscrypt.AllocatedBuffer;
import org.conscrypt.BufferAllocator;
import org.conscrypt.Conscrypt;
import org.conscrypt.HandshakeListener;

abstract class ConscryptAlpnSslEngine
extends JdkSslEngine {
    private static final boolean USE_BUFFER_ALLOCATOR = SystemPropertyUtil.getBoolean("io.netty.handler.ssl.conscrypt.useBufferAllocator", true);

    static ConscryptAlpnSslEngine newClientEngine(SSLEngine sSLEngine, ByteBufAllocator byteBufAllocator, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
        return new ClientEngine(sSLEngine, byteBufAllocator, jdkApplicationProtocolNegotiator);
    }

    static ConscryptAlpnSslEngine newServerEngine(SSLEngine sSLEngine, ByteBufAllocator byteBufAllocator, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
        return new ServerEngine(sSLEngine, byteBufAllocator, jdkApplicationProtocolNegotiator);
    }

    private ConscryptAlpnSslEngine(SSLEngine sSLEngine, ByteBufAllocator byteBufAllocator, List<String> list) {
        super(sSLEngine);
        if (USE_BUFFER_ALLOCATOR) {
            Conscrypt.setBufferAllocator((SSLEngine)sSLEngine, (BufferAllocator)new BufferAllocatorAdapter(byteBufAllocator));
        }
        Conscrypt.setApplicationProtocols((SSLEngine)sSLEngine, (String[])list.toArray(new String[list.size()]));
    }

    final int calculateOutNetBufSize(int n, int n2) {
        long l = (long)Conscrypt.maxSealOverhead((SSLEngine)this.getWrappedEngine()) * (long)n2;
        return (int)Math.min(Integer.MAX_VALUE, (long)n + l);
    }

    final SSLEngineResult unwrap(ByteBuffer[] byteBufferArray, ByteBuffer[] byteBufferArray2) throws SSLException {
        return Conscrypt.unwrap((SSLEngine)this.getWrappedEngine(), (ByteBuffer[])byteBufferArray, (ByteBuffer[])byteBufferArray2);
    }

    ConscryptAlpnSslEngine(SSLEngine sSLEngine, ByteBufAllocator byteBufAllocator, List list, 1 var4_4) {
        this(sSLEngine, byteBufAllocator, list);
    }

    private static final class BufferAdapter
    extends AllocatedBuffer {
        private final ByteBuf nettyBuffer;
        private final ByteBuffer buffer;

        BufferAdapter(ByteBuf byteBuf) {
            this.nettyBuffer = byteBuf;
            this.buffer = byteBuf.nioBuffer(0, byteBuf.capacity());
        }

        public ByteBuffer nioBuffer() {
            return this.buffer;
        }

        public AllocatedBuffer retain() {
            this.nettyBuffer.retain();
            return this;
        }

        public AllocatedBuffer release() {
            this.nettyBuffer.release();
            return this;
        }
    }

    private static final class BufferAllocatorAdapter
    extends BufferAllocator {
        private final ByteBufAllocator alloc;

        BufferAllocatorAdapter(ByteBufAllocator byteBufAllocator) {
            this.alloc = byteBufAllocator;
        }

        public AllocatedBuffer allocateDirectBuffer(int n) {
            return new BufferAdapter(this.alloc.directBuffer(n));
        }
    }

    private static final class ServerEngine
    extends ConscryptAlpnSslEngine {
        private final JdkApplicationProtocolNegotiator.ProtocolSelector protocolSelector;

        ServerEngine(SSLEngine sSLEngine, ByteBufAllocator byteBufAllocator, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
            super(sSLEngine, byteBufAllocator, jdkApplicationProtocolNegotiator.protocols(), null);
            Conscrypt.setHandshakeListener((SSLEngine)sSLEngine, (HandshakeListener)new HandshakeListener(this){
                final ServerEngine this$0;
                {
                    this.this$0 = serverEngine;
                }

                public void onHandshakeFinished() throws SSLException {
                    ServerEngine.access$200(this.this$0);
                }
            });
            this.protocolSelector = ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet<String>(jdkApplicationProtocolNegotiator.protocols())), "protocolSelector");
        }

        private void selectProtocol() throws SSLException {
            try {
                String string = Conscrypt.getApplicationProtocol((SSLEngine)this.getWrappedEngine());
                this.protocolSelector.select(string != null ? Collections.singletonList(string) : Collections.emptyList());
            } catch (Throwable throwable) {
                throw SslUtils.toSSLHandshakeException(throwable);
            }
        }

        static void access$200(ServerEngine serverEngine) throws SSLException {
            serverEngine.selectProtocol();
        }
    }

    private static final class ClientEngine
    extends ConscryptAlpnSslEngine {
        private final JdkApplicationProtocolNegotiator.ProtocolSelectionListener protocolListener;

        ClientEngine(SSLEngine sSLEngine, ByteBufAllocator byteBufAllocator, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
            super(sSLEngine, byteBufAllocator, jdkApplicationProtocolNegotiator.protocols(), null);
            Conscrypt.setHandshakeListener((SSLEngine)sSLEngine, (HandshakeListener)new HandshakeListener(this){
                final ClientEngine this$0;
                {
                    this.this$0 = clientEngine;
                }

                public void onHandshakeFinished() throws SSLException {
                    ClientEngine.access$100(this.this$0);
                }
            });
            this.protocolListener = ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolListenerFactory().newListener(this, jdkApplicationProtocolNegotiator.protocols()), "protocolListener");
        }

        private void selectProtocol() throws SSLException {
            String string = Conscrypt.getApplicationProtocol((SSLEngine)this.getWrappedEngine());
            try {
                this.protocolListener.selected(string);
            } catch (Throwable throwable) {
                throw SslUtils.toSSLHandshakeException(throwable);
            }
        }

        static void access$100(ClientEngine clientEngine) throws SSLException {
            clientEngine.selectProtocol();
        }
    }
}

