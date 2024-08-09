/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Locale;
import javax.annotation.Nullable;

@Beta
@GwtIncompatible
public final class InetAddresses {
    private static final int IPV4_PART_COUNT = 4;
    private static final int IPV6_PART_COUNT = 8;
    private static final Splitter IPV4_SPLITTER = Splitter.on('.').limit(4);
    private static final Inet4Address LOOPBACK4 = (Inet4Address)InetAddresses.forString("127.0.0.1");
    private static final Inet4Address ANY4 = (Inet4Address)InetAddresses.forString("0.0.0.0");

    private InetAddresses() {
    }

    private static Inet4Address getInet4Address(byte[] byArray) {
        Preconditions.checkArgument(byArray.length == 4, "Byte array has invalid length for an IPv4 address: %s != 4.", byArray.length);
        return (Inet4Address)InetAddresses.bytesToInetAddress(byArray);
    }

    public static InetAddress forString(String string) {
        byte[] byArray = InetAddresses.ipStringToBytes(string);
        if (byArray == null) {
            throw InetAddresses.formatIllegalArgumentException("'%s' is not an IP string literal.", string);
        }
        return InetAddresses.bytesToInetAddress(byArray);
    }

    public static boolean isInetAddress(String string) {
        return InetAddresses.ipStringToBytes(string) != null;
    }

    @Nullable
    private static byte[] ipStringToBytes(String string) {
        boolean bl = false;
        boolean bl2 = false;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '.') {
                bl2 = true;
                continue;
            }
            if (c == ':') {
                if (bl2) {
                    return null;
                }
                bl = true;
                continue;
            }
            if (Character.digit(c, 16) != -1) continue;
            return null;
        }
        if (bl) {
            if (bl2 && (string = InetAddresses.convertDottedQuadToHex(string)) == null) {
                return null;
            }
            return InetAddresses.textToNumericFormatV6(string);
        }
        if (bl2) {
            return InetAddresses.textToNumericFormatV4(string);
        }
        return null;
    }

    @Nullable
    private static byte[] textToNumericFormatV4(String string) {
        byte[] byArray = new byte[4];
        int n = 0;
        try {
            for (String string2 : IPV4_SPLITTER.split(string)) {
                byArray[n++] = InetAddresses.parseOctet(string2);
            }
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
        return (byte[])(n == 4 ? byArray : null);
    }

    @Nullable
    private static byte[] textToNumericFormatV6(String string) {
        int n;
        int n2;
        String[] stringArray = string.split(":", 10);
        if (stringArray.length < 3 || stringArray.length > 9) {
            return null;
        }
        int n3 = -1;
        for (n2 = 1; n2 < stringArray.length - 1; ++n2) {
            if (stringArray[n2].length() != 0) continue;
            if (n3 >= 0) {
                return null;
            }
            n3 = n2;
        }
        if (n3 >= 0) {
            n2 = n3;
            n = stringArray.length - n3 - 1;
            if (stringArray[0].length() == 0 && --n2 != 0) {
                return null;
            }
            if (stringArray[stringArray.length - 1].length() == 0 && --n != 0) {
                return null;
            }
        } else {
            n2 = stringArray.length;
            n = 0;
        }
        int n4 = 8 - (n2 + n);
        if (!(n3 < 0 ? n4 == 0 : n4 >= 1)) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        try {
            int n5;
            for (n5 = 0; n5 < n2; ++n5) {
                byteBuffer.putShort(InetAddresses.parseHextet(stringArray[n5]));
            }
            for (n5 = 0; n5 < n4; ++n5) {
                byteBuffer.putShort((short)0);
            }
            for (n5 = n; n5 > 0; --n5) {
                byteBuffer.putShort(InetAddresses.parseHextet(stringArray[stringArray.length - n5]));
            }
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
        return byteBuffer.array();
    }

    @Nullable
    private static String convertDottedQuadToHex(String string) {
        int n = string.lastIndexOf(58);
        String string2 = string.substring(0, n + 1);
        String string3 = string.substring(n + 1);
        byte[] byArray = InetAddresses.textToNumericFormatV4(string3);
        if (byArray == null) {
            return null;
        }
        String string4 = Integer.toHexString((byArray[0] & 0xFF) << 8 | byArray[1] & 0xFF);
        String string5 = Integer.toHexString((byArray[2] & 0xFF) << 8 | byArray[3] & 0xFF);
        return string2 + string4 + ":" + string5;
    }

    private static byte parseOctet(String string) {
        int n = Integer.parseInt(string);
        if (n > 255 || string.startsWith("0") && string.length() > 1) {
            throw new NumberFormatException();
        }
        return (byte)n;
    }

    private static short parseHextet(String string) {
        int n = Integer.parseInt(string, 16);
        if (n > 65535) {
            throw new NumberFormatException();
        }
        return (short)n;
    }

    private static InetAddress bytesToInetAddress(byte[] byArray) {
        try {
            return InetAddress.getByAddress(byArray);
        } catch (UnknownHostException unknownHostException) {
            throw new AssertionError((Object)unknownHostException);
        }
    }

    public static String toAddrString(InetAddress inetAddress) {
        Preconditions.checkNotNull(inetAddress);
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        Preconditions.checkArgument(inetAddress instanceof Inet6Address);
        byte[] byArray = inetAddress.getAddress();
        int[] nArray = new int[8];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = Ints.fromBytes((byte)0, (byte)0, byArray[2 * i], byArray[2 * i + 1]);
        }
        InetAddresses.compressLongestRunOfZeroes(nArray);
        return InetAddresses.hextetsToIPv6String(nArray);
    }

    private static void compressLongestRunOfZeroes(int[] nArray) {
        int n = -1;
        int n2 = -1;
        int n3 = -1;
        for (int i = 0; i < nArray.length + 1; ++i) {
            if (i < nArray.length && nArray[i] == 0) {
                if (n3 >= 0) continue;
                n3 = i;
                continue;
            }
            if (n3 < 0) continue;
            int n4 = i - n3;
            if (n4 > n2) {
                n = n3;
                n2 = n4;
            }
            n3 = -1;
        }
        if (n2 >= 2) {
            Arrays.fill(nArray, n, n + n2, -1);
        }
    }

    private static String hextetsToIPv6String(int[] nArray) {
        StringBuilder stringBuilder = new StringBuilder(39);
        boolean bl = false;
        for (int i = 0; i < nArray.length; ++i) {
            boolean bl2;
            boolean bl3 = bl2 = nArray[i] >= 0;
            if (bl2) {
                if (bl) {
                    stringBuilder.append(':');
                }
                stringBuilder.append(Integer.toHexString(nArray[i]));
            } else if (i == 0 || bl) {
                stringBuilder.append("::");
            }
            bl = bl2;
        }
        return stringBuilder.toString();
    }

    public static String toUriString(InetAddress inetAddress) {
        if (inetAddress instanceof Inet6Address) {
            return "[" + InetAddresses.toAddrString(inetAddress) + "]";
        }
        return InetAddresses.toAddrString(inetAddress);
    }

    public static InetAddress forUriString(String string) {
        InetAddress inetAddress = InetAddresses.forUriStringNoThrow(string);
        if (inetAddress == null) {
            throw InetAddresses.formatIllegalArgumentException("Not a valid URI IP literal: '%s'", string);
        }
        return inetAddress;
    }

    @Nullable
    private static InetAddress forUriStringNoThrow(String string) {
        int n;
        String string2;
        Preconditions.checkNotNull(string);
        if (string.startsWith("[") && string.endsWith("]")) {
            string2 = string.substring(1, string.length() - 1);
            n = 16;
        } else {
            string2 = string;
            n = 4;
        }
        byte[] byArray = InetAddresses.ipStringToBytes(string2);
        if (byArray == null || byArray.length != n) {
            return null;
        }
        return InetAddresses.bytesToInetAddress(byArray);
    }

    public static boolean isUriInetAddress(String string) {
        return InetAddresses.forUriStringNoThrow(string) != null;
    }

    public static boolean isCompatIPv4Address(Inet6Address inet6Address) {
        if (!inet6Address.isIPv4CompatibleAddress()) {
            return true;
        }
        byte[] byArray = inet6Address.getAddress();
        return byArray[12] == 0 && byArray[13] == 0 && byArray[14] == 0 && (byArray[15] == 0 || byArray[15] == 1);
    }

    public static Inet4Address getCompatIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(InetAddresses.isCompatIPv4Address(inet6Address), "Address '%s' is not IPv4-compatible.", (Object)InetAddresses.toAddrString(inet6Address));
        return InetAddresses.getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static boolean is6to4Address(Inet6Address inet6Address) {
        byte[] byArray = inet6Address.getAddress();
        return byArray[0] == 32 && byArray[1] == 2;
    }

    public static Inet4Address get6to4IPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(InetAddresses.is6to4Address(inet6Address), "Address '%s' is not a 6to4 address.", (Object)InetAddresses.toAddrString(inet6Address));
        return InetAddresses.getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 2, 6));
    }

    public static boolean isTeredoAddress(Inet6Address inet6Address) {
        byte[] byArray = inet6Address.getAddress();
        return byArray[0] == 32 && byArray[1] == 1 && byArray[2] == 0 && byArray[3] == 0;
    }

    public static TeredoInfo getTeredoInfo(Inet6Address inet6Address) {
        Preconditions.checkArgument(InetAddresses.isTeredoAddress(inet6Address), "Address '%s' is not a Teredo address.", (Object)InetAddresses.toAddrString(inet6Address));
        byte[] byArray = inet6Address.getAddress();
        Inet4Address inet4Address = InetAddresses.getInet4Address(Arrays.copyOfRange(byArray, 4, 8));
        int n = ByteStreams.newDataInput(byArray, 8).readShort() & 0xFFFF;
        int n2 = ~ByteStreams.newDataInput(byArray, 10).readShort() & 0xFFFF;
        byte[] byArray2 = Arrays.copyOfRange(byArray, 12, 16);
        for (int i = 0; i < byArray2.length; ++i) {
            byArray2[i] = ~byArray2[i];
        }
        Inet4Address inet4Address2 = InetAddresses.getInet4Address(byArray2);
        return new TeredoInfo(inet4Address, inet4Address2, n2, n);
    }

    public static boolean isIsatapAddress(Inet6Address inet6Address) {
        if (InetAddresses.isTeredoAddress(inet6Address)) {
            return true;
        }
        byte[] byArray = inet6Address.getAddress();
        if ((byArray[8] | 3) != 3) {
            return true;
        }
        return byArray[9] == 0 && byArray[10] == 94 && byArray[11] == -2;
    }

    public static Inet4Address getIsatapIPv4Address(Inet6Address inet6Address) {
        Preconditions.checkArgument(InetAddresses.isIsatapAddress(inet6Address), "Address '%s' is not an ISATAP address.", (Object)InetAddresses.toAddrString(inet6Address));
        return InetAddresses.getInet4Address(Arrays.copyOfRange(inet6Address.getAddress(), 12, 16));
    }

    public static boolean hasEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        return InetAddresses.isCompatIPv4Address(inet6Address) || InetAddresses.is6to4Address(inet6Address) || InetAddresses.isTeredoAddress(inet6Address);
    }

    public static Inet4Address getEmbeddedIPv4ClientAddress(Inet6Address inet6Address) {
        if (InetAddresses.isCompatIPv4Address(inet6Address)) {
            return InetAddresses.getCompatIPv4Address(inet6Address);
        }
        if (InetAddresses.is6to4Address(inet6Address)) {
            return InetAddresses.get6to4IPv4Address(inet6Address);
        }
        if (InetAddresses.isTeredoAddress(inet6Address)) {
            return InetAddresses.getTeredoInfo(inet6Address).getClient();
        }
        throw InetAddresses.formatIllegalArgumentException("'%s' has no embedded IPv4 address.", InetAddresses.toAddrString(inet6Address));
    }

    public static boolean isMappedIPv4Address(String string) {
        byte[] byArray = InetAddresses.ipStringToBytes(string);
        if (byArray != null && byArray.length == 16) {
            int n;
            for (n = 0; n < 10; ++n) {
                if (byArray[n] == 0) continue;
                return true;
            }
            for (n = 10; n < 12; ++n) {
                if (byArray[n] == -1) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public static Inet4Address getCoercedIPv4Address(InetAddress inetAddress) {
        if (inetAddress instanceof Inet4Address) {
            return (Inet4Address)inetAddress;
        }
        byte[] byArray = inetAddress.getAddress();
        boolean bl = true;
        for (int i = 0; i < 15; ++i) {
            if (byArray[i] == 0) continue;
            bl = false;
            break;
        }
        if (bl && byArray[15] == 1) {
            return LOOPBACK4;
        }
        if (bl && byArray[15] == 0) {
            return ANY4;
        }
        Inet6Address inet6Address = (Inet6Address)inetAddress;
        long l = 0L;
        l = InetAddresses.hasEmbeddedIPv4ClientAddress(inet6Address) ? (long)InetAddresses.getEmbeddedIPv4ClientAddress(inet6Address).hashCode() : ByteBuffer.wrap(inet6Address.getAddress(), 0, 8).getLong();
        int n = Hashing.murmur3_32().hashLong(l).asInt();
        if ((n |= 0xE0000000) == -1) {
            n = -2;
        }
        return InetAddresses.getInet4Address(Ints.toByteArray(n));
    }

    public static int coerceToInteger(InetAddress inetAddress) {
        return ByteStreams.newDataInput(InetAddresses.getCoercedIPv4Address(inetAddress).getAddress()).readInt();
    }

    public static Inet4Address fromInteger(int n) {
        return InetAddresses.getInet4Address(Ints.toByteArray(n));
    }

    public static InetAddress fromLittleEndianByteArray(byte[] byArray) throws UnknownHostException {
        byte[] byArray2 = new byte[byArray.length];
        for (int i = 0; i < byArray.length; ++i) {
            byArray2[i] = byArray[byArray.length - i - 1];
        }
        return InetAddress.getByAddress(byArray2);
    }

    public static InetAddress decrement(InetAddress inetAddress) {
        int n;
        byte[] byArray = inetAddress.getAddress();
        for (n = byArray.length - 1; n >= 0 && byArray[n] == 0; --n) {
            byArray[n] = -1;
        }
        Preconditions.checkArgument(n >= 0, "Decrementing %s would wrap.", (Object)inetAddress);
        int n2 = n;
        byArray[n2] = (byte)(byArray[n2] - 1);
        return InetAddresses.bytesToInetAddress(byArray);
    }

    public static InetAddress increment(InetAddress inetAddress) {
        int n;
        byte[] byArray = inetAddress.getAddress();
        for (n = byArray.length - 1; n >= 0 && byArray[n] == -1; --n) {
            byArray[n] = 0;
        }
        Preconditions.checkArgument(n >= 0, "Incrementing %s would wrap.", (Object)inetAddress);
        int n2 = n;
        byArray[n2] = (byte)(byArray[n2] + 1);
        return InetAddresses.bytesToInetAddress(byArray);
    }

    public static boolean isMaximum(InetAddress inetAddress) {
        byte[] byArray = inetAddress.getAddress();
        for (int i = 0; i < byArray.length; ++i) {
            if (byArray[i] == -1) continue;
            return true;
        }
        return false;
    }

    private static IllegalArgumentException formatIllegalArgumentException(String string, Object ... objectArray) {
        return new IllegalArgumentException(String.format(Locale.ROOT, string, objectArray));
    }

    static Inet4Address access$000() {
        return ANY4;
    }

    @Beta
    public static final class TeredoInfo {
        private final Inet4Address server;
        private final Inet4Address client;
        private final int port;
        private final int flags;

        public TeredoInfo(@Nullable Inet4Address inet4Address, @Nullable Inet4Address inet4Address2, int n, int n2) {
            Preconditions.checkArgument(n >= 0 && n <= 65535, "port '%s' is out of range (0 <= port <= 0xffff)", n);
            Preconditions.checkArgument(n2 >= 0 && n2 <= 65535, "flags '%s' is out of range (0 <= flags <= 0xffff)", n2);
            this.server = MoreObjects.firstNonNull(inet4Address, InetAddresses.access$000());
            this.client = MoreObjects.firstNonNull(inet4Address2, InetAddresses.access$000());
            this.port = n;
            this.flags = n2;
        }

        public Inet4Address getServer() {
            return this.server;
        }

        public Inet4Address getClient() {
            return this.client;
        }

        public int getPort() {
            return this.port;
        }

        public int getFlags() {
            return this.flags;
        }
    }
}

