// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import java.net.*;
import java.util.Enumeration;

public class MacUtil {
    public static String method504() {
        String address = "";
        InetAddress lanIp = null;
        try {
            String ipAddress;
            Enumeration<NetworkInterface> net;
            net = NetworkInterface.getNetworkInterfaces();
            while (net.hasMoreElements()) {
                NetworkInterface element = net.nextElement();
                Enumeration<InetAddress> addresses = element.getInetAddresses();
                while (addresses.hasMoreElements() && !MacUtil.method506(element.getHardwareAddress())) {
                    InetAddress ip = addresses.nextElement();
                    if (!(ip instanceof Inet4Address) || !ip.isSiteLocalAddress()) continue;
                    ipAddress = ip.getHostAddress();
                    lanIp = InetAddress.getByName(ipAddress);
                }
            }
            if (lanIp == null) {
                return null;
            }
            address = MacUtil.method505(lanIp);
        } catch (Exception exception) {
            // empty catch block
        }
        return address;
    }

    private static String method505(InetAddress ip) {
        String address = null;
        try {
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; ++i) {
                sb.append(String.format("%02X%s", mac[i], i < mac.length - 1 ? "-" : ""));
            }
            address = sb.toString();
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return address;
    }

    public static boolean method506(byte[] mac) throws UnknownHostException, SocketException {
        byte[][] invalidMacs;

        NetworkInterface network = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        byte[] mac2 = network.getHardwareAddress();

        if (mac == null) {
            return false;
        }

        if (mac2 != null && mac2.length > 1) {
            return (mac2[0] == 0x00 && mac2[1] == 0x05) // VMware
                    || (mac2[0] == 0x00 && mac2[1] == 0x1C) // VMware
                    || (mac2[0] == 0x00 && mac2[1] == 0x0C) // VMware
                    || (mac2[0] == 0x00 && mac2[1] == 0x50) // VMware
                    || (mac2[0] == 0x08 && mac2[1] == 0x00) // VirtualBox
                    || (mac2[0] == 0x00 && mac2[1] == 0x1A) // VirtualBox
                    || (mac2[0] == 0x00 && mac2[1] == 0x16) // Xen
                    || (mac2[0] == 0x00 && mac2[1] == 0x0F) // KVM
                    || (mac2[0] == 0x00 && mac2[1] == 0x03) // Parallels
                    || (mac2[0] == 0x52 && mac2[1] == 0x54); // Hyper-V
        }
        for (byte[] invalid : invalidMacs = new byte[][]{{0, 5, 105}, {0, 28, 20}, {0, 12, 41}, {0, 80, 86}, {8, 0, 39}, {10, 0, 39}, {0, 3, -1}, {0, 21, 93}}) {
            if (invalid[0] != mac[0] || invalid[1] != mac[1] || invalid[2] != mac[2]) continue;
            return true;
        }
        return false;
    }
}
