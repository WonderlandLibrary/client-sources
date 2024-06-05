package zamorozka.modules.PLAYER;

import java.util.ArrayList;

import java.util.List;

import de.Hero.settings.Setting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.gui.HitAc;
import zamorozka.gui.IpCheck;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.KillAura;
import zamorozka.ui.ClientUtils;

public class HitBox extends Module {
	public HitBox() {
		super("HitBoxes", 0, Category.COMBAT);
	}

	int delay;

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		ArrayList<String> options1 = new ArrayList<>();
		Zamorozka.settingsManager.rSetting(new Setting("XYZ", this, 1, 0.1, 2, true));
		Zamorozka.settingsManager.rSetting(new Setting("No flags", this, false));
	}

	private final ArrayList<EntityPlayer> players = new ArrayList();
	public static int hitac1;
	private static GuiScreen parentScreen;

	private void HitBox() {

		List list = mc.world.playerEntities;
		this.delay += 1;
		for (int k = 0; k < list.size(); k++) {
			if (((EntityPlayer) list.get(k)).getName() != mc.player.getName()) {
				EntityPlayer entityplayer = (EntityPlayer) list.get(1);
				if (mc.player.getDistanceToEntity(entityplayer) > mc.player.getDistanceToEntity((Entity) list.get(k))) {
					entityplayer = (EntityPlayer) list.get(k);
				}
				double x = entityplayer.posX;
				entityplayer.posX = x;
				double y = entityplayer.posY;
				entityplayer.posY = y;
				double z = entityplayer.posZ;
				entityplayer.posZ = z;

			}
		}
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		double reach = Zamorozka.settingsManager.getSettingByName("XYZ").getValDouble();
		this.setDisplayName("HitBoxes §f§ " + ClientUtils.round((float) Zamorozka.settingsManager.getSettingByName("XYZ").getValDouble(), 2));
	}

	public static float getAmount(Entity entity) {
		if (entity.equals(mc.player) || !ModuleManager.getModule(HitBox.class).getState())
			return 0.0F;
		if (Zamorozka.settingsManager.getSettingByName("No flags").getValBoolean() && ModuleManager.getModule(KillAura.class).getState()) {
			return 0;
		} else {
			return (float) (Zamorozka.settingsManager.getSettingByName("XYZ").getValDouble());
		}

	}

}
