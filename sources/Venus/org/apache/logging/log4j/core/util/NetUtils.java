/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Enumeration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public final class NetUtils {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final String UNKNOWN_LOCALHOST = "UNKNOWN_LOCALHOST";

    private NetUtils() {
    }

    public static String getLocalHostname() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostName();
        } catch (UnknownHostException unknownHostException) {
            try {
                Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                while (enumeration.hasMoreElements()) {
                    NetworkInterface networkInterface = enumeration.nextElement();
                    Enumeration<InetAddress> enumeration2 = networkInterface.getInetAddresses();
                    while (enumeration2.hasMoreElements()) {
                        String string;
                        InetAddress inetAddress = enumeration2.nextElement();
                        if (inetAddress.isLoopbackAddress() || (string = inetAddress.getHostName()) == null) continue;
                        return string;
                    }
                }
            } catch (SocketException socketException) {
                LOGGER.error("Could not determine local host name", (Throwable)unknownHostException);
                return UNKNOWN_LOCALHOST;
            }
            LOGGER.error("Could not determine local host name", (Throwable)unknownHostException);
            return UNKNOWN_LOCALHOST;
        }
    }

    public static URI toURI(String string) {
        try {
            return new URI(string);
        } catch (URISyntaxException uRISyntaxException) {
            try {
                URL uRL = new URL(string);
                return new URI(uRL.getProtocol(), uRL.getHost(), uRL.getPath(), null);
            } catch (MalformedURLException | URISyntaxException exception) {
                return new File(string).toURI();
            }
        }
    }
}

