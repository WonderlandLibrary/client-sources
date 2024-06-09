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

public class PlayerChecker extends Module {

	static ArrayList<NetworkPlayerInfo> playerMap = new ArrayList<NetworkPlayerInfo>();
	static int cachePlayerCount;
	boolean isOnServer;

	public static ArrayList<String> greets = new ArrayList<>();
	public static ArrayList<String> goodbyes = new ArrayList<>();
	static String[] insults;

	
	public PlayerChecker() {
		super("PlayerChecker", Keyboard.KEY_NONE, Category.PLAYER, false);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		 if(mc.thePlayer != null) {
				if (mc.thePlayer.ticksExisted % 10 == 0) {
					checkPlayers();
				}else if (z.mc().isSingleplayer()){
					ModuleManager.findMod(PlayerChecker.class).toggle();
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
		String[] join = {  "§4>> §7The player " + playerInfo.getGameProfile().getName() + " just joined the server!"};
		      Random random = new Random();
		      String iteminhand = Minecraft.getMinecraft().thePlayer.getHeldEquipment().toString();
		      int index = random.nextInt(join.length);
		      String chat = join[index];
		      z.addPlayerCheckMessageP("" + chat);
		}}
	//Player leave Messages
		protected void playerLeft(NetworkPlayerInfo playerInfo) {
			boolean send = true;
			if (send == true)
			{
				String[] leave = {  "§4>> §7The player " + playerInfo.getGameProfile().getName() + " just left the server!" };
		      Random random = new Random();
		      String iteminhand = Minecraft.getMinecraft().thePlayer.getHeldEquipment().toString();
		      int index = random.nextInt(leave.length);
		      String chat = leave[index];
		      z.addPlayerCheckMessageP("" + chat);
		}
	}

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public String getValue() {
		return null;
	}

}
