package lunadevs.luna.module.player;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.network.NetworkPlayerInfo;

public class Greeter extends Module {

	static ArrayList<NetworkPlayerInfo> playerMap = new ArrayList<NetworkPlayerInfo>();
	static int cachePlayerCount;
	boolean isOnServer;

	public static ArrayList<String> greets = new ArrayList<>();
	public static ArrayList<String> goodbyes = new ArrayList<>();
	static String[] insults;

	
	public Greeter() {
		super("Greeter", Keyboard.KEY_NONE, Category.PLAYER, false);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		 if(mc.thePlayer != null) {
				if (mc.thePlayer.ticksExisted % 10 == 0) {
					checkPlayers();
				}else if (z.mc().isSingleplayer()){
					ModuleManager.findMod(Greeter.class).toggle();
				}
			}
		}
		
		private void checkPlayers() {
			ArrayList<NetworkPlayerInfo> infoMap = new ArrayList<NetworkPlayerInfo>(
					Minecraft.getMinecraft().getConnection().getPlayerInfoMap());

			int currentPlayerCount = infoMap.size();

			if (currentPlayerCount != cachePlayerCount) {

				ArrayList<NetworkPlayerInfo> currentInfoMap = (ArrayList<NetworkPlayerInfo>) infoMap.clone();
				currentInfoMap.removeAll(playerMap);

				if (currentInfoMap.size() > 5) {
					cachePlayerCount = playerMap.size();
					onJoinServer();
					return;
				}

				ArrayList<NetworkPlayerInfo> playerMapClone = (ArrayList<NetworkPlayerInfo>) playerMap.clone();
				playerMapClone.removeAll(infoMap);

				for (NetworkPlayerInfo npi : currentInfoMap) {
					playerJoined(npi);
				}

				for (NetworkPlayerInfo npi : playerMapClone) {
					playerLeft(npi);
				}

				cachePlayerCount = playerMap.size();
				onJoinServer();
			}
		}

		private void onJoinServer() {
			playerMap = new ArrayList<>(Minecraft.getMinecraft().getConnection().getPlayerInfoMap());
			cachePlayerCount = playerMap.size();
			isOnServer = true;
		}

		protected void playerJoined(NetworkPlayerInfo playerInfo) {
			boolean send = true;
			if (send == true)
			{
				//Player Join Messages
		String[] join = {  "Good evening, " + playerInfo.getGameProfile().getName() + "!", "We are all happy to see you, " + playerInfo.getGameProfile().getName() + "","SpawnMason couldn't code their own chatbot, " + playerInfo.getGameProfile().getName() + "","Good morning, " + playerInfo.getGameProfile().getName() + "! Have a good day!","Oh, You're back again " + playerInfo.getGameProfile().getName() + "?", "Good to see you again, " + playerInfo.getGameProfile().getName() + "!","Aww, it's you " + playerInfo.getGameProfile().getName(),"Good evening, "+ playerInfo.getGameProfile().getName(),"Nice to see you " + playerInfo.getGameProfile().getName() };
		      Random random = new Random();
		      String iteminhand = Minecraft.getMinecraft().thePlayer.getHeldEquipment().toString();
		      int index = random.nextInt(join.length);
		      String chat = join[index];
		      Minecraft.getMinecraft().thePlayer.sendChatMessage("" + chat);
		}}
	//Player leave Messages
		protected void playerLeft(NetworkPlayerInfo playerInfo) {
			boolean send = true;
			if (send == true)
			{
				String[] join = {  "Well, It was nice to have you here, " + playerInfo.getGameProfile().getName(), "Bye, Bye "+playerInfo.getGameProfile().getName(),"Hope you had a good time, "+playerInfo.getGameProfile().getName() };
		      Random random = new Random();
		      String iteminhand = Minecraft.getMinecraft().thePlayer.getHeldEquipment().toString();
		      int index = random.nextInt(join.length);
		      String chat = join[index];
		      Minecraft.getMinecraft().thePlayer.sendChatMessage("" + chat);
		}
	}

	@Override
	public void onEnable() {
		zCore.addChatMessageP("§cWARNING: §7Turn off Greeter when you leave a server.");
		super.onEnable();
	}

	@Override
	public String getValue() {
		return null;
	}

}
