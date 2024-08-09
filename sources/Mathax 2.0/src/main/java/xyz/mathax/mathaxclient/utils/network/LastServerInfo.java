package xyz.mathax.mathaxclient.utils.network;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.mixininterface.IMultiplayerScreen;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class LastServerInfo {
    private static ServerInfo lastServer;

    public static ServerInfo getLastServer() {
        return lastServer;
    }

    public static void setLastServer(ServerInfo server) {
        lastServer = server;
    }

    public static void joinLastServer(MultiplayerScreen multiplayerScreen) {
        if (lastServer == null) {
            MatHax.LOG.info("No last server found!");
            return;
        }

        ((IMultiplayerScreen) multiplayerScreen).connectToServer(lastServer);
    }

    public static void reconnect(Screen previousScreen) {
        if (lastServer == null) {
            MatHax.LOG.info("No last server found!");
            return;
        }

        ConnectScreen.connect(previousScreen, mc, ServerAddress.parse(lastServer.address), lastServer);
    }
}