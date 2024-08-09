/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.Patterns;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class UuidUtil {
    public static final String UUID_SEQUENCE = "org.apache.logging.log4j.uuidSequence";
    private static final Logger LOGGER;
    private static final String ASSIGNED_SEQUENCES = "org.apache.logging.log4j.assignedSequences";
    private static final AtomicInteger COUNT;
    private static final long TYPE1 = 4096L;
    private static final byte VARIANT = -128;
    private static final int SEQUENCE_MASK = 16383;
    private static final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 122192928000000000L;
    private static final long INITIAL_UUID_SEQNO;
    private static final long LEAST;
    private static final long LOW_MASK = 0xFFFFFFFFL;
    private static final long MID_MASK = 0xFFFF00000000L;
    private static final long HIGH_MASK = 0xFFF000000000000L;
    private static final int NODE_SIZE = 8;
    private static final int SHIFT_2 = 16;
    private static final int SHIFT_4 = 32;
    private static final int SHIFT_6 = 48;
    private static final int HUNDRED_NANOS_PER_MILLI = 10000;

    private UuidUtil() {
    }

    public static UUID getTimeBasedUuid() {
        long l = System.currentTimeMillis() * 10000L + 122192928000000000L + (long)(COUNT.incrementAndGet() % 10000);
        long l2 = (l & 0xFFFFFFFFL) << 32;
        long l3 = (l & 0xFFFF00000000L) >> 16;
        long l4 = (l & 0xFFF000000000000L) >> 48;
        long l5 = l2 | l3 | 0x1000L | l4;
        return new UUID(l5, LEAST);
    }

    private static byte[] getLocalMacAddress() {
        byte[] byArray = null;
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            try {
                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
                if (UuidUtil.isUpAndNotLoopback(networkInterface)) {
                    byArray = networkInterface.getHardwareAddress();
                }
                if (byArray == null) {
                    Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                    while (enumeration.hasMoreElements() && byArray == null) {
                        NetworkInterface networkInterface2 = enumeration.nextElement();
                        if (!UuidUtil.isUpAndNotLoopback(networkInterface2)) continue;
                        byArray = networkInterface2.getHardwareAddress();
                    }
                }
            } catch (SocketException socketException) {
                LOGGER.catching(socketException);
            }
            if (byArray == null || byArray.length == 0) {
                byArray = inetAddress.getAddress();
            }
        } catch (UnknownHostException unknownHostException) {
            // empty catch block
        }
        return byArray;
    }

    private static boolean isUpAndNotLoopback(NetworkInterface networkInterface) throws SocketException {
        return networkInterface != null && !networkInterface.isLoopback() && networkInterface.isUp();
    }

    static {
        boolean bl;
        long[] lArray;
        LOGGER = StatusLogger.getLogger();
        COUNT = new AtomicInteger(0);
        INITIAL_UUID_SEQNO = PropertiesUtil.getProperties().getLongProperty(UUID_SEQUENCE, 0L);
        byte[] byArray = UuidUtil.getLocalMacAddress();
        SecureRandom secureRandom = new SecureRandom();
        if (byArray == null || byArray.length == 0) {
            byArray = new byte[6];
            ((Random)secureRandom).nextBytes(byArray);
        }
        int n = byArray.length >= 6 ? 6 : byArray.length;
        int n2 = byArray.length >= 6 ? byArray.length - 6 : 0;
        byte[] byArray2 = new byte[8];
        byArray2[0] = -128;
        byArray2[1] = 0;
        for (int i = 2; i < 8; ++i) {
            byArray2[i] = 0;
        }
        System.arraycopy(byArray, n2, byArray2, n2 + 2, n);
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray2);
        long l = INITIAL_UUID_SEQNO;
        String string = PropertiesUtil.getProperties().getStringProperty(ASSIGNED_SEQUENCES);
        if (string == null) {
            lArray = new long[]{};
        } else {
            String[] stringArray = string.split(Patterns.COMMA_SEPARATOR);
            lArray = new long[stringArray.length];
            int n3 = 0;
            String[] stringArray2 = stringArray;
            int n4 = stringArray2.length;
            for (int i = 0; i < n4; ++i) {
                String string2 = stringArray2[i];
                lArray[n3] = Long.parseLong(string2);
                ++n3;
            }
        }
        if (l == 0L) {
            l = secureRandom.nextLong();
        }
        l &= 0x3FFFL;
        do {
            bl = false;
            for (long l2 : lArray) {
                if (l2 != l) continue;
                bl = true;
                break;
            }
            if (!bl) continue;
            l = l + 1L & 0x3FFFL;
        } while (bl);
        string = string == null ? Long.toString(l) : string + ',' + Long.toString(l);
        System.setProperty(ASSIGNED_SEQUENCES, string);
        LEAST = byteBuffer.getLong() | l << 48;
    }
}

