/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.AsciiString;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;

public final class NetUtil {
    public static final Inet4Address LOCALHOST4;
    public static final Inet6Address LOCALHOST6;
    public static final InetAddress LOCALHOST;
    public static final NetworkInterface LOOPBACK_IF;
    public static final int SOMAXCONN;
    private static final int IPV6_WORD_COUNT = 8;
    private static final int IPV6_MAX_CHAR_COUNT = 39;
    private static final int IPV6_BYTE_COUNT = 16;
    private static final int IPV6_MAX_CHAR_BETWEEN_SEPARATOR = 4;
    private static final int IPV6_MIN_SEPARATORS = 2;
    private static final int IPV6_MAX_SEPARATORS = 8;
    private static final int IPV4_MAX_CHAR_BETWEEN_SEPARATOR = 3;
    private static final int IPV4_SEPARATORS = 3;
    private static final boolean IPV4_PREFERRED;
    private static final boolean IPV6_ADDRESSES_PREFERRED;
    private static final InternalLogger logger;

    /*
     * Exception decompiling
     */
    private static Integer sysctlGetInt(String var0) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [8[FORLOOP]], but top level block is 3[TRYBLOCK]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:538)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at async.DecompilerRunnable.cfrDecompilation(DecompilerRunnable.java:348)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:309)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:31)
         *     at java.util.concurrent.FutureTask.run(FutureTask.java:266)
         *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         *     at java.lang.Thread.run(Thread.java:750)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static boolean isIpV4StackPreferred() {
        return IPV4_PREFERRED;
    }

    public static boolean isIpV6AddressesPreferred() {
        return IPV6_ADDRESSES_PREFERRED;
    }

    public static byte[] createByteArrayFromIpAddressString(String string) {
        if (NetUtil.isValidIpV4Address(string)) {
            return NetUtil.validIpV4ToBytes(string);
        }
        if (NetUtil.isValidIpV6Address(string)) {
            int n;
            if (string.charAt(0) == '[') {
                string = string.substring(1, string.length() - 1);
            }
            if ((n = string.indexOf(37)) >= 0) {
                string = string.substring(0, n);
            }
            return NetUtil.getIPv6ByName(string, true);
        }
        return null;
    }

    private static int decimalDigit(String string, int n) {
        return string.charAt(n) - 48;
    }

    private static byte ipv4WordToByte(String string, int n, int n2) {
        int n3 = NetUtil.decimalDigit(string, n);
        if (++n == n2) {
            return (byte)n3;
        }
        n3 = n3 * 10 + NetUtil.decimalDigit(string, n);
        if (++n == n2) {
            return (byte)n3;
        }
        return (byte)(n3 * 10 + NetUtil.decimalDigit(string, n));
    }

    static byte[] validIpV4ToBytes(String string) {
        byte[] byArray = new byte[4];
        int n = string.indexOf(46, 1);
        byArray[0] = NetUtil.ipv4WordToByte(string, 0, n);
        int n2 = n + 1;
        n = string.indexOf(46, n + 2);
        byArray[1] = NetUtil.ipv4WordToByte(string, n2, n);
        int n3 = n + 1;
        n = string.indexOf(46, n + 2);
        byArray[2] = NetUtil.ipv4WordToByte(string, n3, n);
        byArray[3] = NetUtil.ipv4WordToByte(string, n + 1, string.length());
        return byArray;
    }

    public static String intToIpAddress(int n) {
        StringBuilder stringBuilder = new StringBuilder(15);
        stringBuilder.append(n >> 24 & 0xFF);
        stringBuilder.append('.');
        stringBuilder.append(n >> 16 & 0xFF);
        stringBuilder.append('.');
        stringBuilder.append(n >> 8 & 0xFF);
        stringBuilder.append('.');
        stringBuilder.append(n & 0xFF);
        return stringBuilder.toString();
    }

    public static String bytesToIpAddress(byte[] byArray) {
        return NetUtil.bytesToIpAddress(byArray, 0, byArray.length);
    }

    public static String bytesToIpAddress(byte[] byArray, int n, int n2) {
        switch (n2) {
            case 4: {
                return new StringBuilder(15).append(byArray[n] & 0xFF).append('.').append(byArray[n + 1] & 0xFF).append('.').append(byArray[n + 2] & 0xFF).append('.').append(byArray[n + 3] & 0xFF).toString();
            }
            case 16: {
                return NetUtil.toAddressString(byArray, n, false);
            }
        }
        throw new IllegalArgumentException("length: " + n2 + " (expected: 4 or 16)");
    }

    public static boolean isValidIpV6Address(String string) {
        return NetUtil.isValidIpV6Address((CharSequence)string);
    }

    public static boolean isValidIpV6Address(CharSequence charSequence) {
        int n;
        int n2;
        int n3;
        int n4 = charSequence.length();
        if (n4 < 2) {
            return true;
        }
        char c = charSequence.charAt(0);
        if (c == '[') {
            if (charSequence.charAt(--n4) != ']') {
                return true;
            }
            n3 = 1;
            c = charSequence.charAt(1);
        } else {
            n3 = 0;
        }
        if (c == ':') {
            if (charSequence.charAt(n3 + 1) != ':') {
                return true;
            }
            n2 = 2;
            n = n3;
            n3 += 2;
        } else {
            n2 = 0;
            n = -1;
        }
        int n5 = 0;
        block5: for (int i = n3; i < n4; ++i) {
            c = charSequence.charAt(i);
            if (NetUtil.isValidHexChar(c)) {
                if (n5 < 4) {
                    ++n5;
                    continue;
                }
                return true;
            }
            switch (c) {
                case ':': {
                    if (n2 > 7) {
                        return true;
                    }
                    if (charSequence.charAt(i - 1) == ':') {
                        if (n >= 0) {
                            return true;
                        }
                        n = i - 1;
                    } else {
                        n5 = 0;
                    }
                    ++n2;
                    continue block5;
                }
                case '.': {
                    int n6;
                    if (n < 0 && n2 != 6 || n2 == 7 && n >= n3 || n2 > 7) {
                        return true;
                    }
                    int n7 = i - n5;
                    int n8 = n7 - 2;
                    if (NetUtil.isValidIPv4MappedChar(charSequence.charAt(n8))) {
                        if (!(NetUtil.isValidIPv4MappedChar(charSequence.charAt(n8 - 1)) && NetUtil.isValidIPv4MappedChar(charSequence.charAt(n8 - 2)) && NetUtil.isValidIPv4MappedChar(charSequence.charAt(n8 - 3)))) {
                            return true;
                        }
                        n8 -= 5;
                    }
                    while (n8 >= n3) {
                        n6 = charSequence.charAt(n8);
                        if (n6 != 48 && n6 != 58) {
                            return true;
                        }
                        --n8;
                    }
                    n6 = AsciiString.indexOf(charSequence, '%', n7 + 7);
                    if (n6 < 0) {
                        n6 = n4;
                    }
                    return NetUtil.isValidIpV4Address(charSequence, n7, n6);
                }
                case '%': {
                    n4 = i;
                    break block5;
                }
                default: {
                    return true;
                }
            }
        }
        if (n < 0) {
            return n2 == 7 && n5 > 0;
        }
        return n + 2 == n4 || n5 > 0 && (n2 < 8 || n <= n3);
    }

    private static boolean isValidIpV4Word(CharSequence charSequence, int n, int n2) {
        char c;
        int n3 = n2 - n;
        if (n3 < 1 || n3 > 3 || (c = charSequence.charAt(n)) < '0') {
            return true;
        }
        if (n3 == 3) {
            char c2;
            char c3 = charSequence.charAt(n + 1);
            return c3 >= '0' && (c2 = charSequence.charAt(n + 2)) >= '0' && (c <= '1' && c3 <= '9' && c2 <= '9' || c == '2' && c3 <= '5' && (c2 <= '5' || c3 < '5' && c2 <= '9'));
        }
        return c <= '9' && (n3 == 1 || NetUtil.isValidNumericChar(charSequence.charAt(n + 1)));
    }

    private static boolean isValidHexChar(char c) {
        return c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f';
    }

    private static boolean isValidNumericChar(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isValidIPv4MappedChar(char c) {
        return c == 'f' || c == 'F';
    }

    private static boolean isValidIPv4MappedSeparators(byte by, byte by2, boolean bl) {
        return by == by2 && (by == 0 || !bl && by2 == -1);
    }

    private static boolean isValidIPv4Mapped(byte[] byArray, int n, int n2, int n3) {
        boolean bl = n2 + n3 >= 14;
        return n <= 12 && n >= 2 && (!bl || n2 < 12) && NetUtil.isValidIPv4MappedSeparators(byArray[n - 1], byArray[n - 2], bl) && PlatformDependent.isZero(byArray, 0, n - 3);
    }

    public static boolean isValidIpV4Address(CharSequence charSequence) {
        return NetUtil.isValidIpV4Address(charSequence, 0, charSequence.length());
    }

    public static boolean isValidIpV4Address(String string) {
        return NetUtil.isValidIpV4Address(string, 0, string.length());
    }

    private static boolean isValidIpV4Address(CharSequence charSequence, int n, int n2) {
        return charSequence instanceof String ? NetUtil.isValidIpV4Address((String)charSequence, n, n2) : (charSequence instanceof AsciiString ? NetUtil.isValidIpV4Address((AsciiString)charSequence, n, n2) : NetUtil.isValidIpV4Address0(charSequence, n, n2));
    }

    private static boolean isValidIpV4Address(String string, int n, int n2) {
        int n3;
        int n4 = n2 - n;
        return n4 <= 15 && n4 >= 7 && (n3 = string.indexOf(46, n + 1)) > 0 && NetUtil.isValidIpV4Word(string, n, n3) && (n3 = string.indexOf(46, n = n3 + 2)) > 0 && NetUtil.isValidIpV4Word(string, n - 1, n3) && (n3 = string.indexOf(46, n = n3 + 2)) > 0 && NetUtil.isValidIpV4Word(string, n - 1, n3) && NetUtil.isValidIpV4Word(string, n3 + 1, n2);
    }

    private static boolean isValidIpV4Address(AsciiString asciiString, int n, int n2) {
        int n3;
        int n4 = n2 - n;
        return n4 <= 15 && n4 >= 7 && (n3 = asciiString.indexOf('.', n + 1)) > 0 && NetUtil.isValidIpV4Word(asciiString, n, n3) && (n3 = asciiString.indexOf('.', n = n3 + 2)) > 0 && NetUtil.isValidIpV4Word(asciiString, n - 1, n3) && (n3 = asciiString.indexOf('.', n = n3 + 2)) > 0 && NetUtil.isValidIpV4Word(asciiString, n - 1, n3) && NetUtil.isValidIpV4Word(asciiString, n3 + 1, n2);
    }

    private static boolean isValidIpV4Address0(CharSequence charSequence, int n, int n2) {
        int n3;
        int n4 = n2 - n;
        return n4 <= 15 && n4 >= 7 && (n3 = AsciiString.indexOf(charSequence, '.', n + 1)) > 0 && NetUtil.isValidIpV4Word(charSequence, n, n3) && (n3 = AsciiString.indexOf(charSequence, '.', n = n3 + 2)) > 0 && NetUtil.isValidIpV4Word(charSequence, n - 1, n3) && (n3 = AsciiString.indexOf(charSequence, '.', n = n3 + 2)) > 0 && NetUtil.isValidIpV4Word(charSequence, n - 1, n3) && NetUtil.isValidIpV4Word(charSequence, n3 + 1, n2);
    }

    public static Inet6Address getByName(CharSequence charSequence) {
        return NetUtil.getByName(charSequence, true);
    }

    public static Inet6Address getByName(CharSequence charSequence, boolean bl) {
        byte[] byArray = NetUtil.getIPv6ByName(charSequence, bl);
        if (byArray == null) {
            return null;
        }
        try {
            return Inet6Address.getByAddress(null, byArray, -1);
        } catch (UnknownHostException unknownHostException) {
            throw new RuntimeException(unknownHostException);
        }
    }

    private static byte[] getIPv6ByName(CharSequence charSequence, boolean bl) {
        int n;
        char c;
        int n2;
        byte[] byArray = new byte[16];
        int n3 = charSequence.length();
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = -1;
        int n9 = 0;
        int n10 = 0;
        boolean bl2 = false;
        block4: for (n2 = 0; n2 < n3; ++n2) {
            c = charSequence.charAt(n2);
            switch (c) {
                case ':': {
                    if (n2 - n8 > 4 || n10 > 0 || ++n9 > 8 || n6 + 1 >= byArray.length) {
                        return null;
                    }
                    n7 <<= 4 - (n2 - n8) << 2;
                    if (n5 > 0) {
                        n5 -= 2;
                    }
                    byArray[n6++] = (byte)((n7 & 0xF) << 4 | n7 >> 4 & 0xF);
                    byArray[n6++] = (byte)((n7 >> 8 & 0xF) << 4 | n7 >> 12 & 0xF);
                    n = n2 + 1;
                    if (n < n3 && charSequence.charAt(n) == ':') {
                        if (n4 != 0 || ++n < n3 && charSequence.charAt(n) == ':') {
                            return null;
                        }
                        bl2 = ++n9 == 2 && n7 == 0;
                        n4 = n6;
                        n5 = byArray.length - n4 - 2;
                        ++n2;
                    }
                    n7 = 0;
                    n8 = -1;
                    continue block4;
                }
                case '.': {
                    n = n2 - n8;
                    if (n > 3 || n8 < 0 || ++n10 > 3 || n9 > 0 && n6 + n5 < 12 || n2 + 1 >= n3 || n6 >= byArray.length || n10 == 1 && (!bl || n6 != 0 && !NetUtil.isValidIPv4Mapped(byArray, n6, n4, n5) || n == 3 && (!NetUtil.isValidNumericChar(charSequence.charAt(n2 - 1)) || !NetUtil.isValidNumericChar(charSequence.charAt(n2 - 2)) || !NetUtil.isValidNumericChar(charSequence.charAt(n2 - 3))) || n == 2 && (!NetUtil.isValidNumericChar(charSequence.charAt(n2 - 1)) || !NetUtil.isValidNumericChar(charSequence.charAt(n2 - 2))) || n == 1 && !NetUtil.isValidNumericChar(charSequence.charAt(n2 - 1)))) {
                        return null;
                    }
                    if ((n8 = ((n7 <<= 3 - n << 2) & 0xF) * 100 + (n7 >> 4 & 0xF) * 10 + (n7 >> 8 & 0xF)) < 0 || n8 > 255) {
                        return null;
                    }
                    byArray[n6++] = (byte)n8;
                    n7 = 0;
                    n8 = -1;
                    continue block4;
                }
                default: {
                    if (!NetUtil.isValidHexChar(c) || n10 > 0 && !NetUtil.isValidNumericChar(c)) {
                        return null;
                    }
                    if (n8 < 0) {
                        n8 = n2;
                    } else if (n2 - n8 > 4) {
                        return null;
                    }
                    n7 += StringUtil.decodeHexNibble(c) << (n2 - n8 << 2);
                }
            }
        }
        char c2 = c = n4 > 0 ? (char)'\u0001' : '\u0000';
        if (n10 > 0) {
            if (n8 > 0 && n2 - n8 > 3 || n10 != 3 || n6 >= byArray.length) {
                return null;
            }
            if (n9 == 0) {
                n5 = 12;
            } else if (n9 >= 2 && (c == '\u0000' && n9 == 6 && charSequence.charAt(0) != ':' || c != '\u0000' && n9 < 8 && (charSequence.charAt(0) != ':' || n4 <= 2))) {
                n5 -= 2;
            } else {
                return null;
            }
            n7 <<= 3 - (n2 - n8) << 2;
            n8 = (n7 & 0xF) * 100 + (n7 >> 4 & 0xF) * 10 + (n7 >> 8 & 0xF);
            if (n8 < 0 || n8 > 255) {
                return null;
            }
            byArray[n6++] = (byte)n8;
        } else {
            n = n3 - 1;
            if (n8 > 0 && n2 - n8 > 4 || n9 < 2 || c == '\u0000' && (n9 + 1 != 8 || charSequence.charAt(0) == ':' || charSequence.charAt(n) == ':') || c != '\u0000' && (n9 > 8 || n9 == 8 && (n4 <= 2 && charSequence.charAt(0) != ':' || n4 >= 14 && charSequence.charAt(n) != ':')) || n6 + 1 >= byArray.length || n8 < 0 && charSequence.charAt(n - 1) != ':' || n4 > 2 && charSequence.charAt(0) == ':') {
                return null;
            }
            if (n8 >= 0 && n2 - n8 <= 4) {
                n7 <<= 4 - (n2 - n8) << 2;
            }
            byArray[n6++] = (byte)((n7 & 0xF) << 4 | n7 >> 4 & 0xF);
            byArray[n6++] = (byte)((n7 >> 8 & 0xF) << 4 | n7 >> 12 & 0xF);
        }
        n2 = n6 + n5;
        if (bl2 || n2 >= byArray.length) {
            if (n2 >= byArray.length) {
                ++n4;
            }
            for (n2 = n6; n2 < byArray.length; ++n2) {
                for (n8 = byArray.length - 1; n8 >= n4; --n8) {
                    byArray[n8] = byArray[n8 - 1];
                }
                byArray[n8] = 0;
                ++n4;
            }
        } else {
            for (n2 = 0; n2 < n5 && (n6 = (n8 = n2 + n4) + n5) < byArray.length; ++n2) {
                byArray[n6] = byArray[n8];
                byArray[n8] = 0;
            }
        }
        if (n10 > 0) {
            byArray[11] = -1;
            byArray[10] = -1;
        }
        return byArray;
    }

    public static String toSocketAddressString(InetSocketAddress inetSocketAddress) {
        StringBuilder stringBuilder;
        String string = String.valueOf(inetSocketAddress.getPort());
        if (inetSocketAddress.isUnresolved()) {
            String string2;
            stringBuilder = NetUtil.newSocketAddressStringBuilder(string2, string, !NetUtil.isValidIpV6Address(string2 = NetUtil.getHostname(inetSocketAddress)));
        } else {
            InetAddress inetAddress = inetSocketAddress.getAddress();
            String string3 = NetUtil.toAddressString(inetAddress);
            stringBuilder = NetUtil.newSocketAddressStringBuilder(string3, string, inetAddress instanceof Inet4Address);
        }
        return stringBuilder.append(':').append(string).toString();
    }

    public static String toSocketAddressString(String string, int n) {
        String string2 = String.valueOf(n);
        return NetUtil.newSocketAddressStringBuilder(string, string2, !NetUtil.isValidIpV6Address(string)).append(':').append(string2).toString();
    }

    private static StringBuilder newSocketAddressStringBuilder(String string, String string2, boolean bl) {
        int n = string.length();
        if (bl) {
            return new StringBuilder(n + 1 + string2.length()).append(string);
        }
        StringBuilder stringBuilder = new StringBuilder(n + 3 + string2.length());
        if (n > 1 && string.charAt(0) == '[' && string.charAt(n - 1) == ']') {
            return stringBuilder.append(string);
        }
        return stringBuilder.append('[').append(string).append(']');
    }

    public static String toAddressString(InetAddress inetAddress) {
        return NetUtil.toAddressString(inetAddress, false);
    }

    public static String toAddressString(InetAddress inetAddress, boolean bl) {
        if (inetAddress instanceof Inet4Address) {
            return inetAddress.getHostAddress();
        }
        if (!(inetAddress instanceof Inet6Address)) {
            throw new IllegalArgumentException("Unhandled type: " + inetAddress);
        }
        return NetUtil.toAddressString(inetAddress.getAddress(), 0, bl);
    }

    private static String toAddressString(byte[] byArray, int n, boolean bl) {
        int n2;
        int n3;
        int[] nArray = new int[8];
        int n4 = n + nArray.length;
        for (n3 = n; n3 < n4; ++n3) {
            nArray[n3] = (byArray[n3 << 1] & 0xFF) << 8 | byArray[(n3 << 1) + 1] & 0xFF;
        }
        int n5 = -1;
        int n6 = -1;
        int n7 = 0;
        for (n3 = 0; n3 < nArray.length; ++n3) {
            if (nArray[n3] == 0) {
                if (n5 >= 0) continue;
                n5 = n3;
                continue;
            }
            if (n5 < 0) continue;
            n2 = n3 - n5;
            if (n2 > n7) {
                n6 = n5;
                n7 = n2;
            }
            n5 = -1;
        }
        if (n5 >= 0 && (n2 = n3 - n5) > n7) {
            n6 = n5;
            n7 = n2;
        }
        if (n7 == 1) {
            n7 = 0;
            n6 = -1;
        }
        int n8 = n6 + n7;
        StringBuilder stringBuilder = new StringBuilder(39);
        if (n8 < 0) {
            stringBuilder.append(Integer.toHexString(nArray[0]));
            for (n3 = 1; n3 < nArray.length; ++n3) {
                stringBuilder.append(':');
                stringBuilder.append(Integer.toHexString(nArray[n3]));
            }
        } else {
            boolean bl2;
            if (NetUtil.inRangeEndExclusive(0, n6, n8)) {
                stringBuilder.append("::");
                bl2 = bl && n8 == 5 && nArray[5] == 65535;
            } else {
                stringBuilder.append(Integer.toHexString(nArray[0]));
                bl2 = false;
            }
            for (n3 = 1; n3 < nArray.length; ++n3) {
                if (!NetUtil.inRangeEndExclusive(n3, n6, n8)) {
                    if (!NetUtil.inRangeEndExclusive(n3 - 1, n6, n8)) {
                        if (!bl2 || n3 == 6) {
                            stringBuilder.append(':');
                        } else {
                            stringBuilder.append('.');
                        }
                    }
                    if (bl2 && n3 > 5) {
                        stringBuilder.append(nArray[n3] >> 8);
                        stringBuilder.append('.');
                        stringBuilder.append(nArray[n3] & 0xFF);
                        continue;
                    }
                    stringBuilder.append(Integer.toHexString(nArray[n3]));
                    continue;
                }
                if (NetUtil.inRangeEndExclusive(n3 - 1, n6, n8)) continue;
                stringBuilder.append("::");
            }
        }
        return stringBuilder.toString();
    }

    public static String getHostname(InetSocketAddress inetSocketAddress) {
        return PlatformDependent.javaVersion() >= 7 ? inetSocketAddress.getHostString() : inetSocketAddress.getHostName();
    }

    private static boolean inRangeEndExclusive(int n, int n2, int n3) {
        return n >= n2 && n < n3;
    }

    private NetUtil() {
    }

    static InternalLogger access$000() {
        return logger;
    }

    static Integer access$100(String string) throws IOException {
        return NetUtil.sysctlGetInt(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        Enumeration<InetAddress> enumeration;
        Object object;
        Object object2;
        IPV4_PREFERRED = SystemPropertyUtil.getBoolean("java.net.preferIPv4Stack", false);
        IPV6_ADDRESSES_PREFERRED = SystemPropertyUtil.getBoolean("java.net.preferIPv6Addresses", false);
        logger = InternalLoggerFactory.getInstance(NetUtil.class);
        logger.debug("-Djava.net.preferIPv4Stack: {}", (Object)IPV4_PREFERRED);
        logger.debug("-Djava.net.preferIPv6Addresses: {}", (Object)IPV6_ADDRESSES_PREFERRED);
        byte[] byArray = new byte[]{127, 0, 0, 1};
        byte[] byArray2 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
        Inet4Address inet4Address = null;
        try {
            inet4Address = (Inet4Address)InetAddress.getByAddress("localhost", byArray);
        } catch (Exception exception) {
            PlatformDependent.throwException(exception);
        }
        LOCALHOST4 = inet4Address;
        Inet6Address inet6Address = null;
        try {
            inet6Address = (Inet6Address)InetAddress.getByAddress("localhost", byArray2);
        } catch (Exception exception) {
            PlatformDependent.throwException(exception);
        }
        LOCALHOST6 = inet6Address;
        ArrayList<NetworkInterface> arrayList = new ArrayList<NetworkInterface>();
        try {
            object2 = NetworkInterface.getNetworkInterfaces();
            if (object2 != null) {
                while (object2.hasMoreElements()) {
                    object = object2.nextElement();
                    if (!SocketUtils.addressesFromNetworkInterface((NetworkInterface)object).hasMoreElements()) continue;
                    arrayList.add((NetworkInterface)object);
                }
            }
        } catch (SocketException socketException) {
            logger.warn("Failed to retrieve the list of available network interfaces", socketException);
        }
        object2 = null;
        object = null;
        block14: for (NetworkInterface networkInterface : arrayList) {
            enumeration = SocketUtils.addressesFromNetworkInterface(networkInterface);
            while (enumeration.hasMoreElements()) {
                InetAddress inetAddress = enumeration.nextElement();
                if (!inetAddress.isLoopbackAddress()) continue;
                object2 = networkInterface;
                object = inetAddress;
                break block14;
            }
        }
        if (object2 == null) {
            try {
                for (NetworkInterface networkInterface : arrayList) {
                    if (!networkInterface.isLoopback() || !(enumeration = SocketUtils.addressesFromNetworkInterface(networkInterface)).hasMoreElements()) continue;
                    object2 = networkInterface;
                    object = enumeration.nextElement();
                    break;
                }
                if (object2 == null) {
                    logger.warn("Failed to find the loopback interface");
                }
            } catch (SocketException socketException) {
                logger.warn("Failed to find the loopback interface", socketException);
            }
        }
        if (object2 != null) {
            logger.debug("Loopback interface: {} ({}, {})", ((NetworkInterface)object2).getName(), ((NetworkInterface)object2).getDisplayName(), ((InetAddress)object).getHostAddress());
        } else if (object == null) {
            try {
                if (NetworkInterface.getByInetAddress(LOCALHOST6) != null) {
                    logger.debug("Using hard-coded IPv6 localhost address: {}", (Object)inet6Address);
                    object = inet6Address;
                }
            } catch (Exception exception) {
            } finally {
                if (object == null) {
                    logger.debug("Using hard-coded IPv4 localhost address: {}", (Object)inet4Address);
                    object = inet4Address;
                }
            }
        }
        LOOPBACK_IF = object2;
        LOCALHOST = object;
        SOMAXCONN = AccessController.doPrivileged(new PrivilegedAction<Integer>(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public Integer run() {
                int n = PlatformDependent.isWindows() ? 200 : 128;
                File file = new File("/proc/sys/net/core/somaxconn");
                BufferedReader bufferedReader = null;
                try {
                    if (file.exists()) {
                        bufferedReader = new BufferedReader(new FileReader(file));
                        n = Integer.parseInt(bufferedReader.readLine());
                        if (NetUtil.access$000().isDebugEnabled()) {
                            NetUtil.access$000().debug("{}: {}", (Object)file, (Object)n);
                        }
                    } else {
                        Integer n2 = null;
                        if (SystemPropertyUtil.getBoolean("io.netty.net.somaxconn.trySysctl", false)) {
                            n2 = NetUtil.access$100("kern.ipc.somaxconn");
                            if (n2 == null) {
                                n2 = NetUtil.access$100("kern.ipc.soacceptqueue");
                                if (n2 != null) {
                                    n = n2;
                                }
                            } else {
                                n = n2;
                            }
                        }
                        if (n2 == null) {
                            NetUtil.access$000().debug("Failed to get SOMAXCONN from sysctl and file {}. Default: {}", (Object)file, (Object)n);
                        }
                    }
                } catch (Exception exception) {
                    NetUtil.access$000().debug("Failed to get SOMAXCONN from sysctl and file {}. Default: {}", file, n, exception);
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception exception) {}
                    }
                }
                return n;
            }

            @Override
            public Object run() {
                return this.run();
            }
        });
    }
}

