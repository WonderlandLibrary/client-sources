package me.satisfactory.base.gui;

import java.util.concurrent.*;
import net.minecraft.client.multiplayer.*;
import java.net.*;
import java.util.regex.*;

public class ServerFinderHelper
{
    public static CopyOnWriteArrayList<ServerData> ips;
    public static int ipsScanned;
    public static int ipsWorking;
    public static int ipsRedServer;
    static CopyOnWriteArrayList<ServerData> scannedIps;
    private static boolean isReady;
    private static boolean isScanning;
    
    public static boolean start(final String fullIp) {
        final String ip = fullIp.split(":")[0];
        int port = 25565;
        if (fullIp.split(":").length == 2) {
            try {
                port = Integer.valueOf(fullIp.split(":")[1]);
            }
            catch (Exception ex) {}
        }
        if (validIP(ip)) {
            startScann(String.valueOf(ip) + ":" + port);
            return true;
        }
        try {
            final InetAddress inet = InetAddress.getByName(ip);
            startScann(String.valueOf(inet.getHostAddress()) + ":" + port);
            return true;
        }
        catch (UnknownHostException e1) {
            return false;
        }
    }
    
    public static CopyOnWriteArrayList<ServerData> getScannedIps() {
        return ServerFinderHelper.scannedIps;
    }
    
    public static void initOrStop() {
        ServerFinderHelper.ips.clear();
        ServerFinderHelper.scannedIps.clear();
        ServerFinderHelper.isReady = false;
        ServerFinderHelper.isScanning = false;
        ServerFinderHelper.ipsScanned = 0;
        ServerFinderHelper.ipsWorking = 0;
        ServerFinderHelper.ipsRedServer = 0;
    }
    
    public static boolean isScannComplet() {
        return ServerFinderHelper.isReady;
    }
    
    public static boolean isScanning() {
        return ServerFinderHelper.isScanning;
    }
    
    private static void startScann(String ip) {
        ip = getLastIp(ip);
        ServerFinderHelper.ipsScanned = 0;
        ServerFinderHelper.ipsWorking = 0;
        ServerFinderHelper.isReady = false;
        if (!validIP(ip)) {
            return;
        }
        for (int i = 0; i < 256; ++i) {
            ServerFinderHelper.ips.add(new ServerData(ip, ip));
            ip = getNextIP(ip);
        }
        for (int i = 0; i < 30; ++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (ServerFinderHelper.ips.size() > 0) {
                        ServerFinderHelper.access$0(true);
                        final ServerData data = ServerFinderHelper.ips.get(0);
                        ServerFinderHelper.ips.remove(0);
                        try {
                            final Socket s = new Socket();
                            s.setTcpNoDelay(true);
                            s.connect(new InetSocketAddress(data.serverIP, 25565), 2500);
                            s.close();
                            ++ServerFinderHelper.ipsScanned;
                            ++ServerFinderHelper.ipsWorking;
                            if (data.gameVersion.equalsIgnoreCase("1.8")) {
                                ++ServerFinderHelper.ipsRedServer;
                            }
                        }
                        catch (Exception e) {
                            ++ServerFinderHelper.ipsScanned;
                            continue;
                        }
                        ServerFinderHelper.scannedIps.add(data);
                        if (!ServerFinderHelper.ips.isEmpty()) {
                            continue;
                        }
                        ServerFinderHelper.ips.clear();
                        ServerFinderHelper.access$1(true);
                        ServerFinderHelper.access$0(false);
                        System.out.println("Fertig");
                    }
                }
            }).start();
        }
    }
    
    private static String getNextIP(final String ip) {
        String s = ip;
        final int[] split = new int[4];
        for (int i = 0; i < 4; ++i) {
            split[i] = Integer.parseInt(s.split("\\.")[i]);
        }
        for (int i = 3; i >= 0; --i) {
            final int[] arrn = split;
            final int n = i;
            ++arrn[n];
            if (split[i] != 255) {
                break;
            }
            split[i] = 1;
        }
        s = String.valueOf(split[0]) + "." + split[1] + "." + split[2] + "." + split[3];
        return s;
    }
    
    private static String getLastIp(String ip) {
        if (validIP(ip = ip.split(":")[0])) {
            final int[] split = { Integer.valueOf(ip.split("\\.")[0]), Integer.valueOf(ip.split("\\.")[1]), Integer.valueOf(ip.split("\\.")[2]), 1 };
            return String.valueOf(split[0]) + "." + split[1] + "." + split[2] + "." + split[3];
        }
        return ip;
    }
    
    static void access$0(final boolean bl) {
        ServerFinderHelper.isScanning = bl;
    }
    
    static void access$1(final boolean bl) {
        ServerFinderHelper.isReady = bl;
    }
    
    public static boolean validIP(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        if ((ip = ip.trim()).length() < 6 & ip.length() > 15) {
            return false;
        }
        try {
            final Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            final Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        }
        catch (PatternSyntaxException ex) {
            return false;
        }
    }
    
    static {
        ServerFinderHelper.ips = new CopyOnWriteArrayList<ServerData>();
        ServerFinderHelper.scannedIps = new CopyOnWriteArrayList<ServerData>();
        ServerFinderHelper.isReady = false;
        ServerFinderHelper.isScanning = false;
    }
}
