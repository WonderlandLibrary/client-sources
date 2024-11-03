package net.silentclient.client.mods.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.IntConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.silentclient.client.Client;

public class Utils {
	public static final ExecutorService MAIN_EXECUTOR = Executors.newFixedThreadPool(Math.max(Runtime.getRuntime().availableProcessors(), 2));

	public static void pingServer(String address, IntConsumer callback) throws UnknownHostException {
		ServerAddress serverAddress = ServerAddress.fromString(address);
		NetworkManager networkManager = NetworkManager.createNetworkManagerAndConnect(
				InetAddress.getByName(serverAddress.getIP()), serverAddress.getPort(), false);

		networkManager.setNetHandler(new INetHandlerStatusClient() {

			private boolean expected = false;
			private long time = 0L;

			@Override
			public void handleServerInfo(S00PacketServerInfo packetIn) {
				if(expected) {
					networkManager.closeChannel(new ChatComponentText("Received unrequested status"));
				}
				else {
					expected = true;
					time = Minecraft.getSystemTime();
					networkManager.sendPacket(new C01PacketPing(time));
					if(packetIn.getResponse() != null && packetIn.getResponse().getPlayerCountData() != null) {
						Client.getInstance().playersCount = packetIn.getResponse().getPlayerCountData().getOnlinePlayerCount();
					} else {
						Client.getInstance().playersCount = 0;
					}
				}
			}

			@Override
			public void handlePong(S01PacketPong packetIn) {
				long systemTime = Minecraft.getSystemTime();
				callback.accept((int) (systemTime - time));
				networkManager.closeChannel(new ChatComponentText("Finished"));
			}

			@Override
			public void onDisconnect(IChatComponent reason) {
				callback.accept(-1);
			}
		});

		networkManager.sendPacket(
				new C00Handshake(47, serverAddress.getIP(), serverAddress.getPort(), EnumConnectionState.STATUS));
		networkManager.sendPacket(new C00PacketServerQuery());
	}
}
