package zamorozka.modules.COMBAT;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ChatUtils;

public class RadiusAntiBot extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("RadiusAdd", this, 60, 10, 180, true));
	}

	public static ArrayList<Entity> notAlwaysInRadius = new ArrayList<>();

	public RadiusAntiBot() {
		super("RadiusAntiBot", 0, Category.COMBAT);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		for (Entity e : mc.world.getLoadedEntityList()) {
			if (e instanceof EntityPlayer) {
				EntityPlayer o = (EntityPlayer) e;
				if (e instanceof EntityPlayer && e != null && e != mc.player
						&& (isInFOV((EntityLivingBase) e, Zamorozka.settingsManager.getSettingByName("RadiusAdd").getValDouble()) && mc.player.getDistanceToEntity(e) <= Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble())) {
					notAlwaysInRadius.add(e);
					// ChatUtils.printChatprefix("Player " + e.getName() + " was added in radius
					// antibot list!");
				}
			}
		}
	}

	private static boolean isInFOV(EntityLivingBase entity, double angle) {
		angle *= .5D;
		double angleDiff = getAngleDifference(Minecraft.player.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
		return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
	}

	private static float getAngleDifference(float dir, float yaw) {
		float f = Math.abs(yaw - dir) % 360F;
		return f > 180F ? 360F - f : f;
	}

	private static float[] getRotations(double x, double y, double z) {
		double diffX = x + .5D - Minecraft.player.posX;
		double diffY = (y + .5D) / 2D - (Minecraft.player.posY + Minecraft.player.getEyeHeight());
		double diffZ = z + .5D - Minecraft.player.posZ;

		double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180D / Math.PI);

		return new float[] { yaw, pitch };
	}

	private boolean within(double n, double ma, double mi) {
		return n <= ma && n >= mi;
	}
}
