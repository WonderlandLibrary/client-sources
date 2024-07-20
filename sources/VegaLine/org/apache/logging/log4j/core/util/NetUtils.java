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
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostName();
        } catch (UnknownHostException uhe) {
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface nic = interfaces.nextElement();
                    Enumeration<InetAddress> addresses = nic.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        String hostname;
                        InetAddress address = addresses.nextElement();
                        if (address.isLoopbackAddress() || (hostname = address.getHostName()) == null) continue;
                        return hostname;
                    }
                }
            } catch (SocketException se) {
                LOGGER.error("Could not determine local host name", (Throwable)uhe);
                return UNKNOWN_LOCALHOST;
            }
            LOGGER.error("Could not determine local host name", (Throwable)uhe);
            return UNKNOWN_LOCALHOST;
        }
    }

    public static URI toURI(String path) {
        try {
            return new URI(path);
        } catch (URISyntaxException e) {
            try {
                URL url = new URL(path);
                return new URI(url.getProtocol(), url.getHost(), url.getPath(), null);
            } catch (MalformedURLException | URISyntaxException nestedEx) {
                return new File(path).toURI();
            }
        }
    }
}

