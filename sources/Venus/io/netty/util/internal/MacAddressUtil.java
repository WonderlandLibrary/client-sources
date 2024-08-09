/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.NetUtil;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

public final class MacAddressUtil {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(MacAddressUtil.class);
    private static final int EUI64_MAC_ADDRESS_LENGTH = 8;
    private static final int EUI48_MAC_ADDRESS_LENGTH = 6;

    public static byte[] bestAvailableMac() {
        InetAddress inetAddress;
        Enumeration<InetAddress> enumeration;
        Object object;
        Object object2 = EmptyArrays.EMPTY_BYTES;
        InetAddress inetAddress2 = NetUtil.LOCALHOST4;
        LinkedHashMap<NetworkInterface, InetAddress> linkedHashMap = new LinkedHashMap<NetworkInterface, InetAddress>();
        try {
            object = NetworkInterface.getNetworkInterfaces();
            if (object != null) {
                while (object.hasMoreElements()) {
                    NetworkInterface object3 = object.nextElement();
                    enumeration = SocketUtils.addressesFromNetworkInterface(object3);
                    if (!enumeration.hasMoreElements() || (inetAddress = enumeration.nextElement()).isLoopbackAddress()) continue;
                    linkedHashMap.put(object3, inetAddress);
                }
            }
        } catch (SocketException socketException) {
            logger.warn("Failed to retrieve the list of available network interfaces", socketException);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            byte[] byArray;
            enumeration = (NetworkInterface)entry.getKey();
            inetAddress = (InetAddress)entry.getValue();
            if (((NetworkInterface)((Object)enumeration)).isVirtual()) continue;
            try {
                byArray = SocketUtils.hardwareAddressFromNetworkInterface((NetworkInterface)((Object)enumeration));
            } catch (SocketException socketException) {
                logger.debug("Failed to get the hardware address of a network interface: {}", (Object)enumeration, (Object)socketException);
                continue;
            }
            boolean bl = false;
            int n = MacAddressUtil.compareAddresses(object2, byArray);
            if (n < 0) {
                bl = true;
            } else if (n == 0) {
                n = MacAddressUtil.compareAddresses(inetAddress2, inetAddress);
                if (n < 0) {
                    bl = true;
                } else if (n == 0 && ((byte[])object2).length < byArray.length) {
                    bl = true;
                }
            }
            if (!bl) continue;
            object2 = byArray;
            inetAddress2 = inetAddress;
        }
        if (object2 == EmptyArrays.EMPTY_BYTES) {
            return null;
        }
        switch (((byte[])object2).length) {
            case 6: {
                object = new byte[8];
                System.arraycopy(object2, 0, object, 0, 3);
                object[3] = -1;
                object[4] = -2;
                System.arraycopy(object2, 3, object, 5, 3);
                object2 = object;
                break;
            }
            default: {
                object2 = Arrays.copyOf(object2, 8);
            }
        }
        return object2;
    }

    public static byte[] defaultMachineId() {
        byte[] byArray = MacAddressUtil.bestAvailableMac();
        if (byArray == null) {
            byArray = new byte[8];
            PlatformDependent.threadLocalRandom().nextBytes(byArray);
            logger.warn("Failed to find a usable hardware address from the network interfaces; using random bytes: {}", (Object)MacAddressUtil.formatAddress(byArray));
        }
        return byArray;
    }

    public static byte[] parseMAC(String string) {
        byte[] byArray;
        char c;
        switch (string.length()) {
            case 17: {
                c = string.charAt(2);
                MacAddressUtil.validateMacSeparator(c);
                byArray = new byte[6];
                break;
            }
            case 23: {
                c = string.charAt(2);
                MacAddressUtil.validateMacSeparator(c);
                byArray = new byte[8];
                break;
            }
            default: {
                throw new IllegalArgumentException("value is not supported [MAC-48, EUI-48, EUI-64]");
            }
        }
        int n = byArray.length - 1;
        int n2 = 0;
        int n3 = 0;
        while (n3 < n) {
            int n4 = n2 + 2;
            byArray[n3] = StringUtil.decodeHexByte(string, n2);
            if (string.charAt(n4) != c) {
                throw new IllegalArgumentException("expected separator '" + c + " but got '" + string.charAt(n4) + "' at index: " + n4);
            }
            ++n3;
            n2 += 3;
        }
        byArray[n] = StringUtil.decodeHexByte(string, n2);
        return byArray;
    }

    private static void validateMacSeparator(char c) {
        if (c != ':' && c != '-') {
            throw new IllegalArgumentException("unsupported separator: " + c + " (expected: [:-])");
        }
    }

    public static String formatAddress(byte[] byArray) {
        StringBuilder stringBuilder = new StringBuilder(24);
        for (byte by : byArray) {
            stringBuilder.append(String.format("%02x:", by & 0xFF));
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    static int compareAddresses(byte[] byArray, byte[] byArray2) {
        if (byArray2 == null || byArray2.length < 6) {
            return 0;
        }
        boolean bl = true;
        for (byte by : byArray2) {
            if (by == 0 || by == 1) continue;
            bl = false;
            break;
        }
        if (bl) {
            return 0;
        }
        if ((byArray2[0] & 1) != 0) {
            return 0;
        }
        if ((byArray2[0] & 2) == 0) {
            if (byArray.length != 0 && (byArray[0] & 2) == 0) {
                return 1;
            }
            return 1;
        }
        if (byArray.length != 0 && (byArray[0] & 2) == 0) {
            return 0;
        }
        return 1;
    }

    private static int compareAddresses(InetAddress inetAddress, InetAddress inetAddress2) {
        return MacAddressUtil.scoreAddress(inetAddress) - MacAddressUtil.scoreAddress(inetAddress2);
    }

    private static int scoreAddress(InetAddress inetAddress) {
        if (inetAddress.isAnyLocalAddress() || inetAddress.isLoopbackAddress()) {
            return 1;
        }
        if (inetAddress.isMulticastAddress()) {
            return 0;
        }
        if (inetAddress.isLinkLocalAddress()) {
            return 1;
        }
        if (inetAddress.isSiteLocalAddress()) {
            return 0;
        }
        return 1;
    }

    private MacAddressUtil() {
    }
}

