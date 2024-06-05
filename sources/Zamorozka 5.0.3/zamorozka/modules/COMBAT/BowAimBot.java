package zamorozka.modules.COMBAT;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.Hero.settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;

public class BowAimBot extends Module {

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("ServerSide");
		options.add("ClientSide");
		Zamorozka.settingsManager.rSetting(new Setting("BowRotation Mode", this, "ServerSide", options));
		Zamorozka.settingsManager.rSetting(new Setting("BowRange", this, 6, 3, 50, true));
	}

	public BowAimBot() {
		super("BowAimBot", 0, Category.COMBAT);
	}

	@EventTarget
	public void onPre(EventPreMotionUpdates event) {
		double range = Zamorozka.settingsManager.getSettingByName("BowRange").getValDouble();
		String mode = Zamorozka.settingsManager.getSettingByName("BowRotation Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("BowAimBot " + ChatFormatting.WHITE + modeput);
		List list = mc.world.playerEntities;

		for (int k = 0; k < list.size(); k++) {
			if (((EntityPlayer) list.get(k)).getName() == mc.player.getName()) {
				continue;
			}

			EntityPlayer entityplayer = (EntityPlayer) list.get(1);

			if (mc.player.getDistanceToEntity(entityplayer) > mc.player.getDistanceToEntity((Entity) list.get(k))) {
				entityplayer = (EntityPlayer) list.get(k);
			}

			float f = mc.player.getDistanceToEntity(entityplayer);

			if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
				if (ModuleManager.getModule(AntiBot2.class).getState() && Zamorozka.settingsManager.getSettingByName("HitBefore").getValBoolean() && !AntiBot2.nobotsTimolia.contains((Object) entityplayer))
					return;
				if (f <= range && mc.player.canEntityBeSeen(entityplayer) && !entityplayer.isInvisible()) {
					if (mode.equalsIgnoreCase("ServerSide")) {
						final float[] rots = getRotationsToward(entityplayer);
						float sens = getSensitivityMultiplier();
						event.setYaw(Math.round(rots[0] / sens) * sens);
						mc.player.renderYawOffset = (Math.round(rots[0] / sens) * sens);
						mc.player.rotationYawHead = (Math.round(rots[0] / sens) * sens);
						event.setPitch(Math.round(rots[1] / sens) * sens);
						mc.player.rotationPitchHead = (Math.round(rots[1] / sens) * sens);
					}
					if (mode.equalsIgnoreCase("ClientSide")) {
						final float[] rots = getRotationsToward(entityplayer);
						float sens = getSensitivityMultiplier();
						mc.player.rotationYaw = (Math.round(rots[0] / sens) * sens);
						mc.player.renderYawOffset = (Math.round(rots[0] / sens) * sens);
						mc.player.rotationYawHead = (Math.round(rots[0] / sens) * sens);
						mc.player.rotationPitch = (Math.round(rots[1] / sens) * sens);
						mc.player.rotationPitchHead = (Math.round(rots[1] / sens) * sens);
					}
				}
			}
		}
	}

	public float[] getRotationsToward(final Entity closestEntity) {
		double xDist = closestEntity.posX - mc.player.posX;
		double yDist = closestEntity.posY + closestEntity.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
		double zDist = closestEntity.posZ - mc.player.posZ;
		double fDist = MathHelper.sqrt(xDist * xDist + zDist * zDist);
		float yaw = this.fixRotation(mc.player.rotationYaw, (float) (MathHelper.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F, 360F);
		float pitch = this.fixRotation(mc.player.rotationPitch, (float) (-(MathHelper.atan2(yDist, fDist) * 180.0D / Math.PI)), 360F);
		return new float[] { yaw, pitch };
	}

	private float fixRotation(final float p_70663_1_, final float p_70663_2_, final float p_70663_3_) {
		float var4 = MathHelper.wrapDegrees(p_70663_2_ - p_70663_1_);
		if (var4 > p_70663_3_) {
			var4 = p_70663_3_;
		}
		if (var4 < -p_70663_3_) {
			var4 = -p_70663_3_;
		}
		return p_70663_1_ + var4;
	}

	private static float getSensitivityMultiplier() {
		float f = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
		return (f * f * f * 8.0F) * 0.15F;
	}
}