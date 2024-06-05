package zamorozka.modules.WORLD;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemShears;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoSheepShearing extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("SheepRotations", this, true));
	}

	public AutoSheepShearing() {
		super("AutoSheepShearing", 0, Category.WORLD);
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates event) {
		if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemShears) {
			for (Entity e : mc.world.loadedEntityList) {
				if (e != null && e instanceof EntitySheep) {
					final EntitySheep sheep = (EntitySheep) e;
					if (sheep.getHealth() > 0) {
						if (!sheep.isChild() && !sheep.getSheared() && mc.player.getDistanceToEntity(sheep) <= 4.5f) {
							mc.playerController.interactWithEntity(mc.player, sheep, EnumHand.MAIN_HAND);
							if (Zamorozka.settingsManager.getSettingByName("SheepRotations").getValBoolean()) {
								final float[] rots = getRotationsToward(sheep);
								float sens = getSensitivityMultiplier();
								float yawGCD = (Math.round(rots[0] / sens) * sens);
								float pitchGCD = (Math.round(rots[1] / sens) * sens);
								event.setYaw(yawGCD);
								event.setPitch(pitchGCD);
								mc.player.rotationYawHead = yawGCD;
								mc.player.renderYawOffset = yawGCD;
							}
						}
					}
				}
			}
		}
	}

	private float[] getRotationsToward(final Entity closestEntity) {
		double xDist = closestEntity.posX - mc.player.posX;
		double yDist = closestEntity.posY + closestEntity.getEyeHeight() - (mc.player.posY + mc.player.getEyeHeight());
		double zDist = closestEntity.posZ - mc.player.posZ;
		double fDist = MathHelper.sqrt(xDist * xDist + zDist * zDist);
		float yaw = this.fixRotation(mc.player.rotationYaw,
				(float) (MathHelper.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F, 360F);
		float pitch = this.fixRotation(mc.player.rotationPitch,
				(float) (-(MathHelper.atan2(yDist, fDist) * 180.0D / Math.PI)), 360F);
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

	private float getSensitivityMultiplier() {
		float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
		return (f * f * f * 8.0F) * 0.15F;
	}

}