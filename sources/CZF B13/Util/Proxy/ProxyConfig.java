package gq.vapu.czfclient.Util.Proxy;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventTick;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.ChatComponentText;

import java.net.ServerSocket;
import java.util.Random;

public class ProxyConfig {
    public static int listenerPort = ProxyConfig.findFreePort(29999);
    public static TransparentProxy proxy = null;
    public static ConnectionInfo proxyAddr = null;

    public static ConnectionInfo connect(String ip, int port) {
        if (proxyAddr != null) {
            if (proxy == null) {
                proxy = new TransparentProxy(listenerPort);
            }
            if (proxy.isRunning()) {
                proxy.stop();
            }
            try {
                proxy.start(ProxyConfig.proxyAddr.ip, ProxyConfig.proxyAddr.port, ip, port);
                while (!proxy.isReady() && !proxy.hasFailed()) {
                    Thread.sleep(50L);
                }
                if (proxy.hasFailed()) {
                    throw proxy.getFailReason();
                }
                ConnectionInfo returnment = new ConnectionInfo();
                returnment.ip = "127.0.0.1";
                returnment.port = listenerPort;
                return returnment;
            } catch (Exception e) {
                e.printStackTrace();
                ConnectionInfo returnment = new ConnectionInfo();
                returnment.ip = ip;
                returnment.port = port;
                return returnment;
            }
        }
        ConnectionInfo returnment = new ConnectionInfo();
        returnment.ip = ip;
        returnment.port = port;
        return returnment;
    }

    public static void stop() {
        if (proxy != null) {
            proxy.stop();
        }
    }

    private static int findFreePort(int start) {
        int cur = new Random().nextInt(10000) + start - 1;
        do {
            try {
                ServerSocket sock = new ServerSocket(cur, 1);
                sock.close();
                return cur;
            } catch (Exception var3) {
                cur = new Random().nextInt(10000) + start - 1;
                continue;
            }
        } while (true);
    }

    @EventHandler
    public void onGlobalGameLoop(EventTick e) {
        if (proxy != null && proxy.isRunning() && proxy.hasFailed()) {
            boolean flag = Helper.mc.isIntegratedServerRunning();
            boolean flag1 = Helper.mc.isSingleplayer();
            Helper.mc.theWorld.sendQuittingDisconnectingPacket();
            Helper.mc.loadWorld(null);
            if (flag) {
                Helper.mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()),
                        "disconnect.closed", new ChatComponentText("[AC-Proxy] Lost Connection to server!")));
            } else if (flag1) {
                RealmsBridge realmsbridge = new RealmsBridge();
                realmsbridge.switchToRealms(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()),
                        "disconnect.closed", new ChatComponentText("[AC-Proxy] Lost Connection to server!")));
            } else {
                Helper.mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()),
                        "disconnect.closed", new ChatComponentText("[AC-Proxy] Lost Connection to server!")));
            }
        }
    }
}
