package me.satisfactory.base.gui;

import net.minecraft.client.multiplayer.*;
import java.net.*;

static final class ServerFinderHelper$1 implements Runnable {
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
}