package me.gishreload.yukon.commands;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.UUID;

import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.command.Command;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.realms.RealmsBridge;

public class ConnectCommand extends Command{

	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return "connect";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "§c.connect type ip, type port";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if (args.length == 1) {
            Edictum.addChatMessage("§cType ip!");
        }
        String ip = args[1];
        if (!ip.contains(":")) {
        	Edictum.addChatMessage("§cType port");
        }
        String[] a = ip.split(":");
        if (!ip.contains(a[1])) {
        	Edictum.addChatMessage("Invalid port");
        }
        boolean flag = mc.isIntegratedServerRunning();
        boolean flag1 = mc.isConnectedToRealms();
        mc.theWorld.sendQuittingDisconnectingPacket();
        this.unloadWorld();
 
        if (flag) {
            mc.displayGuiScreen(new GuiMainMenu());
        } else if (flag1) {
            RealmsBridge realmsbridge = new RealmsBridge();
            realmsbridge.switchToRealms(new GuiMainMenu());
        } else {
            mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
        }
        String subIp = a[0];
        int port = Integer.parseInt(a[1]);
        Random rand = new Random();
        InetAddress var1 = null;
        try {
            var1 = InetAddress.getByName(a[0]);
        } catch (UnknownHostException e) {
        	Edictum.addChatMessage("§cAddress not found " + a[0]);
        }
        try {
            GuiConnecting.networkManager = NetworkManager.createNetworkManagerAndConnect(
                    var1, port, mc.gameSettings.isUsingNativeTransport()
            );
            GuiConnecting.networkManager.setNetHandler(
                    new NetHandlerLoginClient(
                            GuiConnecting.networkManager, Minecraft.getMinecraft(), new GuiIngameMenu()
                    )
            );
            GuiConnecting.networkManager.sendPacket(
                    new C00Handshake(
                            210,
                            a[0] + "\000" + "42.173." + String.valueOf(rand.nextInt(255)) + "."
                                    + String.valueOf(rand.nextInt(255)) + "\000" + UUID.randomUUID().toString(),
                            port, EnumConnectionState.LOGIN
                    )
            );
            GuiConnecting.networkManager.sendPacket(new CPacketLoginStart(mc.getSession().getProfile()));
        } catch (Exception e) {
            mc.displayGuiScreen(
                    new GuiErrorScreen(
                            "Connection failed",
                            "Seems like port is closed: " + e.getClass().getName() + ":" + e.getMessage()
                    )
            );
        }
    }
	private void unloadWorld() {
        NetHandlerPlayClient nethandlerplayclient = mc.getConnection();
 
        if (nethandlerplayclient != null) {
            nethandlerplayclient.cleanup();
        }
 
        if (mc.theIntegratedServer != null && mc.theIntegratedServer.isAnvilFileSet()) {
            mc.theIntegratedServer.initiateShutdown();
        }
 
        mc.theIntegratedServer = null;
        mc.guiAchievement.clearAchievements();
        mc.entityRenderer.getMapItemRenderer().clearLoadedMaps();
        mc.playerController = null;
 
        mc.renderViewEntity = null;
        mc.myNetworkManager = null;
 
        if (mc.loadingScreen != null) {
            mc.loadingScreen.resetProgressAndMessage("");
            mc.loadingScreen.displayLoadingString("Unloading world...");
        }
 
        if (mc.theWorld != null) {
            mc.getResourcePackRepository().clearResourcePack();
            mc.ingameGUI.resetPlayersOverlayFooterHeader();
            mc.setServerData(null);
            mc.integratedServerIsRunning = false;
        }
 
        mc.getSoundHandler().stopSounds();
        mc.theWorld = null;
 
        if (mc.renderGlobal != null) {
            mc.renderGlobal.setWorldAndLoadRenderers(null);
        }
 
        if (mc.effectRenderer != null) {
            mc.effectRenderer.clearEffects(null);
        }
 
        TileEntityRendererDispatcher.instance.setWorld(null);
		}
}
