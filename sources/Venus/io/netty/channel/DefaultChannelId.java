/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelId;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.MacAddressUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public final class DefaultChannelId
implements ChannelId {
    private static final long serialVersionUID = 3884076183504074063L;
    private static final InternalLogger logger;
    private static final byte[] MACHINE_ID;
    private static final int PROCESS_ID_LEN = 4;
    private static final int PROCESS_ID;
    private static final int SEQUENCE_LEN = 4;
    private static final int TIMESTAMP_LEN = 8;
    private static final int RANDOM_LEN = 4;
    private static final AtomicInteger nextSequence;
    private final byte[] data = new byte[MACHINE_ID.length + 4 + 4 + 8 + 4];
    private final int hashCode;
    private transient String shortValue;
    private transient String longValue;
    static final boolean $assertionsDisabled;

    public static DefaultChannelId newInstance() {
        return new DefaultChannelId();
    }

    private static int defaultProcessId() {
        int n;
        String string;
        Class<?> clazz;
        ClassLoader classLoader = null;
        try {
            classLoader = PlatformDependent.getClassLoader(DefaultChannelId.class);
            Class<?> clazz2 = Class.forName("java.lang.management.ManagementFactory", true, classLoader);
            clazz = Class.forName("java.lang.management.RuntimeMXBean", true, classLoader);
            Method method = clazz2.getMethod("getRuntimeMXBean", EmptyArrays.EMPTY_CLASSES);
            Object object = method.invoke(null, EmptyArrays.EMPTY_OBJECTS);
            Method method2 = clazz.getMethod("getName", EmptyArrays.EMPTY_CLASSES);
            string = (String)method2.invoke(object, EmptyArrays.EMPTY_OBJECTS);
        } catch (Throwable throwable) {
            logger.debug("Could not invoke ManagementFactory.getRuntimeMXBean().getName(); Android?", throwable);
            try {
                clazz = Class.forName("android.os.Process", true, classLoader);
                Method method = clazz.getMethod("myPid", EmptyArrays.EMPTY_CLASSES);
                string = method.invoke(null, EmptyArrays.EMPTY_OBJECTS).toString();
            } catch (Throwable throwable2) {
                logger.debug("Could not invoke Process.myPid(); not Android?", throwable2);
                string = "";
            }
        }
        int n2 = string.indexOf(64);
        if (n2 >= 0) {
            string = string.substring(0, n2);
        }
        try {
            n = Integer.parseInt(string);
        } catch (NumberFormatException numberFormatException) {
            n = -1;
        }
        if (n < 0) {
            n = PlatformDependent.threadLocalRandom().nextInt();
            logger.warn("Failed to find the current process ID from '{}'; using a random value: {}", (Object)string, (Object)n);
        }
        return n;
    }

    private DefaultChannelId() {
        int n = 0;
        System.arraycopy(MACHINE_ID, 0, this.data, n, MACHINE_ID.length);
        n += MACHINE_ID.length;
        n = this.writeInt(n, PROCESS_ID);
        n = this.writeInt(n, nextSequence.getAndIncrement());
        n = this.writeLong(n, Long.reverse(System.nanoTime()) ^ System.currentTimeMillis());
        int n2 = PlatformDependent.threadLocalRandom().nextInt();
        n = this.writeInt(n, n2);
        if (!$assertionsDisabled && n != this.data.length) {
            throw new AssertionError();
        }
        this.hashCode = Arrays.hashCode(this.data);
    }

    private int writeInt(int n, int n2) {
        this.data[n++] = (byte)(n2 >>> 24);
        this.data[n++] = (byte)(n2 >>> 16);
        this.data[n++] = (byte)(n2 >>> 8);
        this.data[n++] = (byte)n2;
        return n;
    }

    private int writeLong(int n, long l) {
        this.data[n++] = (byte)(l >>> 56);
        this.data[n++] = (byte)(l >>> 48);
        this.data[n++] = (byte)(l >>> 40);
        this.data[n++] = (byte)(l >>> 32);
        this.data[n++] = (byte)(l >>> 24);
        this.data[n++] = (byte)(l >>> 16);
        this.data[n++] = (byte)(l >>> 8);
        this.data[n++] = (byte)l;
        return n;
    }

    @Override
    public String asShortText() {
        String string = this.shortValue;
        if (string == null) {
            this.shortValue = string = ByteBufUtil.hexDump(this.data, this.data.length - 4, 4);
        }
        return string;
    }

    @Override
    public String asLongText() {
        String string = this.longValue;
        if (string == null) {
            this.longValue = string = this.newLongValue();
        }
        return string;
    }

    private String newLongValue() {
        StringBuilder stringBuilder = new StringBuilder(2 * this.data.length + 5);
        int n = 0;
        n = this.appendHexDumpField(stringBuilder, n, MACHINE_ID.length);
        n = this.appendHexDumpField(stringBuilder, n, 4);
        n = this.appendHexDumpField(stringBuilder, n, 4);
        n = this.appendHexDumpField(stringBuilder, n, 8);
        n = this.appendHexDumpField(stringBuilder, n, 4);
        if (!$assertionsDisabled && n != this.data.length) {
            throw new AssertionError();
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    private int appendHexDumpField(StringBuilder stringBuilder, int n, int n2) {
        stringBuilder.append(ByteBufUtil.hexDump(this.data, n, n2));
        stringBuilder.append('-');
        return n += n2;
    }

    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public int compareTo(ChannelId channelId) {
        if (this == channelId) {
            return 1;
        }
        if (channelId instanceof DefaultChannelId) {
            byte[] byArray = ((DefaultChannelId)channelId).data;
            int n = this.data.length;
            int n2 = byArray.length;
            int n3 = Math.min(n, n2);
            for (int i = 0; i < n3; ++i) {
                byte by = this.data[i];
                byte by2 = byArray[i];
                if (by == by2) continue;
                return (by & 0xFF) - (by2 & 0xFF);
            }
            return n - n2;
        }
        return this.asLongText().compareTo(channelId.asLongText());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof DefaultChannelId)) {
            return true;
        }
        DefaultChannelId defaultChannelId = (DefaultChannelId)object;
        return this.hashCode == defaultChannelId.hashCode && Arrays.equals(this.data, defaultChannelId.data);
    }

    public String toString() {
        return this.asShortText();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ChannelId)object);
    }

    static {
        $assertionsDisabled = !DefaultChannelId.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(DefaultChannelId.class);
        nextSequence = new AtomicInteger();
        int n = -1;
        String string = SystemPropertyUtil.get("io.netty.processId");
        if (string != null) {
            try {
                n = Integer.parseInt(string);
            } catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            if (n < 0) {
                n = -1;
                logger.warn("-Dio.netty.processId: {} (malformed)", (Object)string);
            } else if (logger.isDebugEnabled()) {
                logger.debug("-Dio.netty.processId: {} (user-set)", (Object)n);
            }
        }
        if (n < 0) {
            n = DefaultChannelId.defaultProcessId();
            if (logger.isDebugEnabled()) {
                logger.debug("-Dio.netty.processId: {} (auto-detected)", (Object)n);
            }
        }
        PROCESS_ID = n;
        byte[] byArray = null;
        String string2 = SystemPropertyUtil.get("io.netty.machineId");
        if (string2 != null) {
            try {
                byArray = MacAddressUtil.parseMAC(string2);
            } catch (Exception exception) {
                logger.warn("-Dio.netty.machineId: {} (malformed)", (Object)string2, (Object)exception);
            }
            if (byArray != null) {
                logger.debug("-Dio.netty.machineId: {} (user-set)", (Object)string2);
            }
        }
        if (byArray == null) {
            byArray = MacAddressUtil.defaultMachineId();
            if (logger.isDebugEnabled()) {
                logger.debug("-Dio.netty.machineId: {} (auto-detected)", (Object)MacAddressUtil.formatAddress(byArray));
            }
        }
        MACHINE_ID = byArray;
    }
}

