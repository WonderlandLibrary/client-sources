package zamorozka.modules.COMBAT;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketServerDifficulty;
import net.minecraft.network.play.server.SPacketPlayerListItem.Action;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.MouseAttackEvent;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.ZAMOROZKA.YoutuberMode;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.ChatUtils;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.MiscUtils;

public class AntiBot2 extends Module {

	public static List<EntityPlayer> getInvalid() {
		return invalid;
	}

	public static List<EntityPlayer> invalid = new ArrayList<>();
	public static ArrayList<Entity> bots = new ArrayList<>();
	Entity currentEntity;
	Entity[] playerList;
	int index;
	boolean next;
	double[] oldPos;
	private boolean wasAdded;

	public AntiBot2() {
		super("AntiBot", Keyboard.KEY_NONE, Category.COMBAT);
	}

	public static ArrayList<EntityPlayer> nobots = new ArrayList();
	public static ArrayList<Entity> nobotsTimolia = new ArrayList();

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		//Zamorozka.settingsManager.rSetting(new Setting("MatrixMixCheck", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("HitBefore", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("HypixelBots", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("MineplexBots", this, false));
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		try {
			if (Zamorozka.settingsManager.getSettingByName("HypixelBots").getValBoolean()) {
				for (Entity player : mc.world.loadedEntityList) {
					if (player instanceof EntityPlayer) {
						if ((player.getName().startsWith("�") && player.getName().contains("�c")) || (isEntityBot(player) && !player.getDisplayName().getFormattedText().contains("NPC"))) {
							mc.world.removeEntity(player);
						}
					}
				}
			}
			/*if (Zamorozka.settingsManager.getSettingByName("MatrixMixCheck").getValBoolean()) {
				for (Entity e : mc.world.getLoadedEntityList()) {
					if (e instanceof EntityPlayer) {
						EntityPlayer o = (EntityPlayer) e;
						if(EntityUtil.hasNoMixArmor(e)) {
							bots.add(e);
						}
 					}
				}
			}*/
			if (Zamorozka.settingsManager.getSettingByName("MineplexBots").getValBoolean()) {
				for (Entity e : mc.world.getLoadedEntityList()) {
					if (e instanceof EntityPlayer) {
						EntityPlayer bot = (EntityPlayer) e;
						if (e.ticksExisted < 2 && bot.getHealth() < 20.0F && bot.getHealth() > 0.0F && e != mc.player)
							mc.world.removeEntity(e);

					}
				}
			}
		} catch (

		Exception e) {
			// TODO: handle exception
		}
	}

	@EventTarget
	public void onMouse(MouseAttackEvent event) {
		try {
			if (Zamorozka.settingsManager.getSettingByName("HitBefore").getValBoolean() && ModuleManager.getModule(AntiBot2.class).getState()) {
				EntityPlayer entityPlayer = (EntityPlayer) mc.objectMouseOver.entityHit;
				String name = entityPlayer.getName();
				if (ModuleManager.getModule(YoutuberMode.class).getState() && Zamorozka.settingsManager.getSettingByName("SpoofNames").getValBoolean()) {
					String newstr = "Protected";
					name = newstr;
				}
				if (indexer.getFriends().isFriend(entityPlayer.getName()))
					return;
				if (nobotsTimolia.contains(entityPlayer)) {
					ChatUtils.printChatprefix(ChatFormatting.RED + name + ChatFormatting.WHITE + " Already in AntiBot-List!");
				} else {
					ChatUtils.printChatprefix(ChatFormatting.RED + name + ChatFormatting.WHITE + " Was added in AntiBot-List!");
				}
			}
		} catch (Exception e1) {
			;

		}
	}

	private boolean isOnTab(Entity entity) {
		for (NetworkPlayerInfo info : mc.getConnection().getPlayerInfoMap()) {
			if (info.getGameProfile().getName().equals(entity.getName()))
				return true;
		}
		return false;
	}

	private boolean isEntityBot(Entity entity) {
		double distance = entity.getDistanceSqToEntity(mc.player);
		if (!(entity instanceof EntityPlayer))
			return false;
		if (mc.getCurrentServerData() == null)
			return false;
		return (((mc.getCurrentServerData()).serverIP.toLowerCase().contains("hypixel") && entity.getDisplayName().getFormattedText().startsWith("\u0e22\u0e07")) || (!isOnTab(entity) && mc.player.ticksExisted > 100));
	}
}