package zamorozka.modules.HUD;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRender2D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.modules.COMBAT.AntiBot2;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.RotationUtils;

public class TextRadar extends Module {

	@Override
    public void setup() {
        Zamorozka.instance.settingsManager.rSetting(new Setting("RadarPlayers", this, true));
        Zamorozka.instance.settingsManager.rSetting(new Setting("RadarMobs", this, false));
        Zamorozka.instance.settingsManager.rSetting(new Setting("RenderVillagers", this, false));
        Zamorozka.instance.settingsManager.rSetting(new Setting("RenderTeams", this, true));
        Zamorozka.instance.settingsManager.rSetting(new Setting("RenderInvisibles", this, true));
	}
	
	public TextRadar() {
		super("TextRadar", 0, Category.Hud);
	}

	@EventTarget
	public void onRender(EventRender2D event) {
		for (Entity e : mc.world.loadedEntityList) {
			if (e instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase) e;
				if (canRender(entity)) {
					float yaw = RotationUtils.getRotations(entity)[0];
					float diffyaw = -mc.player.rotationYaw + yaw + 90 + 180;
					ScaledResolution sr = new ScaledResolution(mc);
					RenderingUtils.drawCircle223(55, 22, 60, (int) diffyaw - 5,
							(int) diffyaw + 5);

				}
			}
		}
	}

	public boolean canRender(EntityLivingBase player) {
		if (player == mc.player)
			return false;
		if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob
				|| player instanceof EntityVillager) {
			if (player instanceof EntityPlayer
					&& !Zamorozka.settingsManager.getSettingByName("RadarPlayers").getValBoolean())
				return false;
			if (player instanceof EntityAnimal && !Zamorozka.settingsManager.getSettingByName("RadarMobs").getValBoolean())
				return false;
			if (player instanceof EntityMob && !Zamorozka.settingsManager.getSettingByName("RadarMobs").getValBoolean())
				return false;
			if (player instanceof EntityVillager
					&& !Zamorozka.settingsManager.getSettingByName("RadarVillagers").getValBoolean())
				return false;

		}
		if (player instanceof EntityPlayer) {
			if (AntiBot2.nobotsTimolia.contains((EntityPlayer) player))
				return false;
		}
		if (mc.player.isOnSameTeam(player) && Zamorozka.settingsManager.getSettingByName("RadarTeams").getValBoolean())
			return false;
		if (player.isInvisible() && !Zamorozka.settingsManager.getSettingByName("RadarInvisibles").getValBoolean())
			return false;

		return true;
	}
}