/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import org.apache.http.util.Args;

public final class NetUtils {
    public static void formatAddress(StringBuilder stringBuilder, SocketAddress socketAddress) {
        Args.notNull(stringBuilder, "Buffer");
        Args.notNull(socketAddress, "Socket address");
        if (socketAddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
            InetAddress inetAddress = inetSocketAddress.getAddress();
            stringBuilder.append(inetAddress != null ? inetAddress.getHostAddress() : inetAddress).append(':').append(inetSocketAddress.getPort());
        } else {
            stringBuilder.append(socketAddress);
        }
    }
}

