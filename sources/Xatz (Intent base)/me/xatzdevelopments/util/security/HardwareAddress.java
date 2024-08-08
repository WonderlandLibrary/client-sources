package me.xatzdevelopments.util.security;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.NetworkInterface;
import java.net.InetAddress;

public class HardwareAddress
{
    public static String getMacAddress() throws UnknownHostException, SocketException {
        final InetAddress ipAddress = InetAddress.getLocalHost();
        final NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ipAddress);
        final byte[] macAddressBytes = networkInterface.getHardwareAddress();
        final StringBuilder macAddressBuilder = new StringBuilder();
        for (int macAddressByteIndex = 0; macAddressByteIndex < macAddressBytes.length; ++macAddressByteIndex) {
            final String macAddressHexByte = String.format("%02X", macAddressBytes[macAddressByteIndex]);
            macAddressBuilder.append(macAddressHexByte);
            if (macAddressByteIndex != macAddressBytes.length - 1) {
                macAddressBuilder.append(":");
            }
        }
        return macAddressBuilder.toString();
    }
}
