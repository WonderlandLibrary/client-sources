/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.zeromq.ZMQ
 *  org.zeromq.ZMQ$Context
 *  org.zeromq.ZMQ$Socket
 */
package org.apache.logging.log4j.core.appender.mom.jeromq;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.util.ShutdownCallbackRegistry;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.zeromq.ZMQ;

public class JeroMqManager
extends AbstractManager {
    public static final String SYS_PROPERTY_ENABLE_SHUTDOWN_HOOK = "log4j.jeromq.enableShutdownHook";
    public static final String SYS_PROPERTY_IO_THREADS = "log4j.jeromq.ioThreads";
    private static final JeroMqManagerFactory FACTORY = new JeroMqManagerFactory(null);
    private static final ZMQ.Context CONTEXT;
    private final ZMQ.Socket publisher = CONTEXT.socket(0);

    private JeroMqManager(String string, JeroMqConfiguration jeroMqConfiguration) {
        super(null, string);
        this.publisher.setAffinity(JeroMqConfiguration.access$200(jeroMqConfiguration));
        this.publisher.setBacklog(JeroMqConfiguration.access$300(jeroMqConfiguration));
        this.publisher.setDelayAttachOnConnect(JeroMqConfiguration.access$400(jeroMqConfiguration));
        if (JeroMqConfiguration.access$500(jeroMqConfiguration) != null) {
            this.publisher.setIdentity(JeroMqConfiguration.access$500(jeroMqConfiguration));
        }
        this.publisher.setIPv4Only(JeroMqConfiguration.access$600(jeroMqConfiguration));
        this.publisher.setLinger(JeroMqConfiguration.access$700(jeroMqConfiguration));
        this.publisher.setMaxMsgSize(JeroMqConfiguration.access$800(jeroMqConfiguration));
        this.publisher.setRcvHWM(JeroMqConfiguration.access$900(jeroMqConfiguration));
        this.publisher.setReceiveBufferSize(JeroMqConfiguration.access$1000(jeroMqConfiguration));
        this.publisher.setReceiveTimeOut(JeroMqConfiguration.access$1100(jeroMqConfiguration));
        this.publisher.setReconnectIVL(JeroMqConfiguration.access$1200(jeroMqConfiguration));
        this.publisher.setReconnectIVLMax(JeroMqConfiguration.access$1300(jeroMqConfiguration));
        this.publisher.setSendBufferSize(JeroMqConfiguration.access$1400(jeroMqConfiguration));
        this.publisher.setSendTimeOut(JeroMqConfiguration.access$1500(jeroMqConfiguration));
        this.publisher.setSndHWM(JeroMqConfiguration.access$1600(jeroMqConfiguration));
        this.publisher.setTCPKeepAlive(JeroMqConfiguration.access$1700(jeroMqConfiguration));
        this.publisher.setTCPKeepAliveCount(JeroMqConfiguration.access$1800(jeroMqConfiguration));
        this.publisher.setTCPKeepAliveIdle(JeroMqConfiguration.access$1900(jeroMqConfiguration));
        this.publisher.setTCPKeepAliveInterval(JeroMqConfiguration.access$2000(jeroMqConfiguration));
        this.publisher.setXpubVerbose(JeroMqConfiguration.access$2100(jeroMqConfiguration));
        for (String string2 : JeroMqConfiguration.access$2200(jeroMqConfiguration)) {
            this.publisher.bind(string2);
        }
        LOGGER.debug("Created JeroMqManager with {}", (Object)jeroMqConfiguration);
    }

    public boolean send(byte[] byArray) {
        return this.publisher.send(byArray);
    }

    @Override
    protected boolean releaseSub(long l, TimeUnit timeUnit) {
        this.publisher.close();
        return false;
    }

    public static JeroMqManager getJeroMqManager(String string, long l, long l2, boolean bl, byte[] byArray, boolean bl2, long l3, long l4, long l5, long l6, int n, long l7, long l8, long l9, int n2, long l10, int n3, long l11, long l12, long l13, boolean bl3, List<String> list) {
        return JeroMqManager.getManager(string, FACTORY, new JeroMqConfiguration(l, l2, bl, byArray, bl2, l3, l4, l5, l6, n, l7, l8, l9, n2, l10, n3, l11, l12, l13, bl3, list, null));
    }

    public static ZMQ.Context getContext() {
        return CONTEXT;
    }

    static ZMQ.Context access$100() {
        return CONTEXT;
    }

    JeroMqManager(String string, JeroMqConfiguration jeroMqConfiguration, 1 var3_3) {
        this(string, jeroMqConfiguration);
    }

    static {
        LOGGER.trace("JeroMqManager using ZMQ version {}", (Object)ZMQ.getVersionString());
        int n = PropertiesUtil.getProperties().getIntegerProperty(SYS_PROPERTY_IO_THREADS, 1);
        LOGGER.trace("JeroMqManager creating ZMQ context with ioThreads = {}", (Object)n);
        CONTEXT = ZMQ.context((int)n);
        boolean bl = PropertiesUtil.getProperties().getBooleanProperty(SYS_PROPERTY_ENABLE_SHUTDOWN_HOOK, false);
        if (bl) {
            ((ShutdownCallbackRegistry)((Object)LogManager.getFactory())).addShutdownCallback(new Runnable(){

                @Override
                public void run() {
                    JeroMqManager.access$100().close();
                }
            });
        }
    }

    private static class JeroMqManagerFactory
    implements ManagerFactory<JeroMqManager, JeroMqConfiguration> {
        private JeroMqManagerFactory() {
        }

        @Override
        public JeroMqManager createManager(String string, JeroMqConfiguration jeroMqConfiguration) {
            return new JeroMqManager(string, jeroMqConfiguration, null);
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (JeroMqConfiguration)object);
        }

        JeroMqManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class JeroMqConfiguration {
        private final long affinity;
        private final long backlog;
        private final boolean delayAttachOnConnect;
        private final byte[] identity;
        private final boolean ipv4Only;
        private final long linger;
        private final long maxMsgSize;
        private final long rcvHwm;
        private final long receiveBufferSize;
        private final int receiveTimeOut;
        private final long reconnectIVL;
        private final long reconnectIVLMax;
        private final long sendBufferSize;
        private final int sendTimeOut;
        private final long sndHwm;
        private final int tcpKeepAlive;
        private final long tcpKeepAliveCount;
        private final long tcpKeepAliveIdle;
        private final long tcpKeepAliveInterval;
        private final boolean xpubVerbose;
        private final List<String> endpoints;

        private JeroMqConfiguration(long l, long l2, boolean bl, byte[] byArray, boolean bl2, long l3, long l4, long l5, long l6, int n, long l7, long l8, long l9, int n2, long l10, int n3, long l11, long l12, long l13, boolean bl3, List<String> list) {
            this.affinity = l;
            this.backlog = l2;
            this.delayAttachOnConnect = bl;
            this.identity = byArray;
            this.ipv4Only = bl2;
            this.linger = l3;
            this.maxMsgSize = l4;
            this.rcvHwm = l5;
            this.receiveBufferSize = l6;
            this.receiveTimeOut = n;
            this.reconnectIVL = l7;
            this.reconnectIVLMax = l8;
            this.sendBufferSize = l9;
            this.sendTimeOut = n2;
            this.sndHwm = l10;
            this.tcpKeepAlive = n3;
            this.tcpKeepAliveCount = l11;
            this.tcpKeepAliveIdle = l12;
            this.tcpKeepAliveInterval = l13;
            this.xpubVerbose = bl3;
            this.endpoints = list;
        }

        public String toString() {
            return "JeroMqConfiguration{affinity=" + this.affinity + ", backlog=" + this.backlog + ", delayAttachOnConnect=" + this.delayAttachOnConnect + ", identity=" + Arrays.toString(this.identity) + ", ipv4Only=" + this.ipv4Only + ", linger=" + this.linger + ", maxMsgSize=" + this.maxMsgSize + ", rcvHwm=" + this.rcvHwm + ", receiveBufferSize=" + this.receiveBufferSize + ", receiveTimeOut=" + this.receiveTimeOut + ", reconnectIVL=" + this.reconnectIVL + ", reconnectIVLMax=" + this.reconnectIVLMax + ", sendBufferSize=" + this.sendBufferSize + ", sendTimeOut=" + this.sendTimeOut + ", sndHwm=" + this.sndHwm + ", tcpKeepAlive=" + this.tcpKeepAlive + ", tcpKeepAliveCount=" + this.tcpKeepAliveCount + ", tcpKeepAliveIdle=" + this.tcpKeepAliveIdle + ", tcpKeepAliveInterval=" + this.tcpKeepAliveInterval + ", xpubVerbose=" + this.xpubVerbose + ", endpoints=" + this.endpoints + '}';
        }

        static long access$200(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.affinity;
        }

        static long access$300(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.backlog;
        }

        static boolean access$400(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.delayAttachOnConnect;
        }

        static byte[] access$500(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.identity;
        }

        static boolean access$600(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.ipv4Only;
        }

        static long access$700(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.linger;
        }

        static long access$800(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.maxMsgSize;
        }

        static long access$900(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.rcvHwm;
        }

        static long access$1000(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.receiveBufferSize;
        }

        static int access$1100(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.receiveTimeOut;
        }

        static long access$1200(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.reconnectIVL;
        }

        static long access$1300(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.reconnectIVLMax;
        }

        static long access$1400(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.sendBufferSize;
        }

        static int access$1500(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.sendTimeOut;
        }

        static long access$1600(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.sndHwm;
        }

        static int access$1700(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.tcpKeepAlive;
        }

        static long access$1800(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.tcpKeepAliveCount;
        }

        static long access$1900(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.tcpKeepAliveIdle;
        }

        static long access$2000(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.tcpKeepAliveInterval;
        }

        static boolean access$2100(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.xpubVerbose;
        }

        static List access$2200(JeroMqConfiguration jeroMqConfiguration) {
            return jeroMqConfiguration.endpoints;
        }

        JeroMqConfiguration(long l, long l2, boolean bl, byte[] byArray, boolean bl2, long l3, long l4, long l5, long l6, int n, long l7, long l8, long l9, int n2, long l10, int n3, long l11, long l12, long l13, boolean bl3, List list, 1 var35_5) {
            this(l, l2, bl, byArray, bl2, l3, l4, l5, l6, n, l7, l8, l9, n2, l10, n3, l11, l12, l13, bl3, list);
        }
    }
}

