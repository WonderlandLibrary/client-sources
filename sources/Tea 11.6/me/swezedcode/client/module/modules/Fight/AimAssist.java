package me.swezedcode.client.module.modules.Fight;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.gui.swingbuilders.ClickGui;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.AimUtils;
import me.swezedcode.client.utils.events.EventMotion;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.location.Location;
import me.swezedcode.client.utils.values.BooleanValue;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class AimAssist extends Module {

	public AimAssist() {
		super("AimAssist", Keyboard.KEY_G, 0xFF1FAB2A, ModCategory.Fight);
		setDisplayName("Aim Assist");
	}

	private BooleanValue mouse_down = new BooleanValue(this, "Mouse down", "sword_required", Boolean.valueOf(false));

	public final float range = 3.7F;
	public final float fov = 120F;
	public final float speed = 8.0F;
	public final boolean pitch = true;

	public EntityLivingBase target;

	@EventListener
	public void onMotion(EventPreMotionUpdates event) {
		double distance = 2.147483647E9D;
		boolean found = false;
		for (Object object : this.mc.theWorld.loadedEntityList) {
			if (!mouse_down.getValue()) {
				if ((object instanceof EntityPlayer)) {
					EntityLivingBase entity = (EntityLivingBase) object;
					float[] direction = AimUtils.getRotations(entity);
					float yawDif = getAngleDifference(direction[0], this.mc.thePlayer.rotationYaw);
					float pitchDif = getAngleDifference(direction[1], this.mc.thePlayer.rotationPitch);
					if ((!entity.isDead) && (this.mc.thePlayer.getDistanceToEntity(entity) < 5)
							&& (entity.isEntityAlive()) && (entity != this.mc.thePlayer) && (yawDif < 360.0F)) {
						if (distance > Math.sqrt(yawDif * yawDif + pitchDif * pitchDif)) {
							distance = Math.sqrt(yawDif * yawDif + pitchDif * pitchDif);
							this.target = entity;
							found = true;
						}
					}
				}
			} else {
				if (mc.gameSettings.keyBindAttack.pressed) {
					if ((object instanceof EntityPlayer)) {
						EntityLivingBase entity = (EntityLivingBase) object;
						float[] direction = AimUtils.getRotations(entity);
						float yawDif = getAngleDifference(direction[0], this.mc.thePlayer.rotationYaw);
						float pitchDif = getAngleDifference(direction[0], this.mc.thePlayer.rotationPitch);
						if ((!entity.isDead) && (this.mc.thePlayer.getDistanceToEntity(entity) < 3.4F)
								&& (entity.isEntityAlive()) && (entity != this.mc.thePlayer) && (yawDif < 360.0F)) {
							if (distance > Math.sqrt(yawDif * yawDif + pitchDif * pitchDif)) {
								distance = Math.sqrt(yawDif * yawDif + pitchDif * pitchDif);
								this.target = entity;
								found = true;
							}
						}
					}
				}
			}
			if (!found) {
				this.target = null;
			}
			if (this.target != null) {
				if (!this.target.isInvisible() && !Manager.getManager().getFriendManager().isFriend(target.getName())) {
					Location location = this.mc.thePlayer.getLocation();
					float yawChange = AimUtils.getYawChangeToEntity(this.target) / 70;
					float pitchChange = AimUtils.getPitchChangeToEntity(this.target) / 70;
					location.setYaw(this.mc.thePlayer.rotationYaw + yawChange);
					location.setPitch(this.mc.thePlayer.rotationPitch + pitchChange);

					this.mc.thePlayer.setLocation(location);
				}
			}
		}
	}

	public int getRandom(int min, int max) {
		Random rn = new Random();
		int n = max - min + 1;
		int i = rn.nextInt() % n;
		int randomNum = min + i;
		return randomNum;
	}

	public static float getAngleDifference(float a, float b) {
		float dist = (a - b + 360.0F) % 360.0F;
		if (dist > 180.0F) {
			dist = 360.0F - dist;
		}
		return Math.abs(dist);
	}
}