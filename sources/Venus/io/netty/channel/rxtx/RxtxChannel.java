/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  gnu.io.CommPort
 *  gnu.io.CommPortIdentifier
 *  gnu.io.SerialPort
 */
package io.netty.channel.rxtx;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import io.netty.channel.AbstractChannel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.oio.OioByteStreamChannel;
import io.netty.channel.rxtx.DefaultRxtxChannelConfig;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxChannelOption;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public class RxtxChannel
extends OioByteStreamChannel {
    private static final RxtxDeviceAddress LOCAL_ADDRESS = new RxtxDeviceAddress("localhost");
    private final RxtxChannelConfig config = new DefaultRxtxChannelConfig(this);
    private boolean open = true;
    private RxtxDeviceAddress deviceAddress;
    private SerialPort serialPort;

    public RxtxChannel() {
        super(null);
    }

    @Override
    public RxtxChannelConfig config() {
        return this.config;
    }

    @Override
    public boolean isOpen() {
        return this.open;
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return new RxtxUnsafe(this, null);
    }

    @Override
    protected void doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        RxtxDeviceAddress rxtxDeviceAddress = (RxtxDeviceAddress)socketAddress;
        CommPortIdentifier commPortIdentifier = CommPortIdentifier.getPortIdentifier((String)rxtxDeviceAddress.value());
        CommPort commPort = commPortIdentifier.open(this.getClass().getName(), 1000);
        commPort.enableReceiveTimeout(this.config().getOption(RxtxChannelOption.READ_TIMEOUT).intValue());
        this.deviceAddress = rxtxDeviceAddress;
        this.serialPort = (SerialPort)commPort;
    }

    protected void doInit() throws Exception {
        this.serialPort.setSerialPortParams(this.config().getOption(RxtxChannelOption.BAUD_RATE).intValue(), this.config().getOption(RxtxChannelOption.DATA_BITS).value(), this.config().getOption(RxtxChannelOption.STOP_BITS).value(), this.config().getOption(RxtxChannelOption.PARITY_BIT).value());
        this.serialPort.setDTR(this.config().getOption(RxtxChannelOption.DTR).booleanValue());
        this.serialPort.setRTS(this.config().getOption(RxtxChannelOption.RTS).booleanValue());
        this.activate(this.serialPort.getInputStream(), this.serialPort.getOutputStream());
    }

    @Override
    public RxtxDeviceAddress localAddress() {
        return (RxtxDeviceAddress)super.localAddress();
    }

    @Override
    public RxtxDeviceAddress remoteAddress() {
        return (RxtxDeviceAddress)super.remoteAddress();
    }

    @Override
    protected RxtxDeviceAddress localAddress0() {
        return LOCAL_ADDRESS;
    }

    @Override
    protected RxtxDeviceAddress remoteAddress0() {
        return this.deviceAddress;
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }

    @Override
    protected void doClose() throws Exception {
        this.open = false;
        try {
            super.doClose();
        } finally {
            if (this.serialPort != null) {
                this.serialPort.removeEventListener();
                this.serialPort.close();
                this.serialPort = null;
            }
        }
    }

    @Override
    protected boolean isInputShutdown() {
        return !this.open;
    }

    @Override
    protected ChannelFuture shutdownInput() {
        return this.newFailedFuture(new UnsupportedOperationException("shutdownInput"));
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return this.remoteAddress0();
    }

    @Override
    protected SocketAddress localAddress0() {
        return this.localAddress0();
    }

    @Override
    public SocketAddress remoteAddress() {
        return this.remoteAddress();
    }

    @Override
    public SocketAddress localAddress() {
        return this.localAddress();
    }

    @Override
    public ChannelConfig config() {
        return this.config();
    }

    private final class RxtxUnsafe
    extends AbstractChannel.AbstractUnsafe {
        final RxtxChannel this$0;

        private RxtxUnsafe(RxtxChannel rxtxChannel) {
            this.this$0 = rxtxChannel;
            super(rxtxChannel);
        }

        @Override
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            try {
                boolean bl = this.this$0.isActive();
                this.this$0.doConnect(socketAddress, socketAddress2);
                int n = this.this$0.config().getOption(RxtxChannelOption.WAIT_TIME);
                if (n > 0) {
                    this.this$0.eventLoop().schedule(new Runnable(this, channelPromise, bl){
                        final ChannelPromise val$promise;
                        final boolean val$wasActive;
                        final RxtxUnsafe this$1;
                        {
                            this.this$1 = rxtxUnsafe;
                            this.val$promise = channelPromise;
                            this.val$wasActive = bl;
                        }

                        @Override
                        public void run() {
                            try {
                                this.this$1.this$0.doInit();
                                RxtxUnsafe.access$100(this.this$1, this.val$promise);
                                if (!this.val$wasActive && this.this$1.this$0.isActive()) {
                                    this.this$1.this$0.pipeline().fireChannelActive();
                                }
                            } catch (Throwable throwable) {
                                RxtxUnsafe.access$200(this.this$1, this.val$promise, throwable);
                                RxtxUnsafe.access$300(this.this$1);
                            }
                        }
                    }, (long)n, TimeUnit.MILLISECONDS);
                } else {
                    this.this$0.doInit();
                    this.safeSetSuccess(channelPromise);
                    if (!bl && this.this$0.isActive()) {
                        this.this$0.pipeline().fireChannelActive();
                    }
                }
            } catch (Throwable throwable) {
                this.safeSetFailure(channelPromise, throwable);
                this.closeIfClosed();
            }
        }

        RxtxUnsafe(RxtxChannel rxtxChannel, 1 var2_2) {
            this(rxtxChannel);
        }

        static void access$100(RxtxUnsafe rxtxUnsafe, ChannelPromise channelPromise) {
            rxtxUnsafe.safeSetSuccess(channelPromise);
        }

        static void access$200(RxtxUnsafe rxtxUnsafe, ChannelPromise channelPromise, Throwable throwable) {
            rxtxUnsafe.safeSetFailure(channelPromise, throwable);
        }

        static void access$300(RxtxUnsafe rxtxUnsafe) {
            rxtxUnsafe.closeIfClosed();
        }
    }
}

