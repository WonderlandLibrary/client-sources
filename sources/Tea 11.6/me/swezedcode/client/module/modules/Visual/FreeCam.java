package me.swezedcode.client.module.modules.Visual;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventEntityCollision;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class FreeCam extends Module {

	public FreeCam() {
		super("FreeCam", Keyboard.KEY_NONE, 0xFF354354, ModCategory.Visual);
	}

	private EntityOtherPlayerMP prayerCopy;
	private double startX;
	private double startY;
	private double startZ;
	private float startYaw;
	private float startPitch;

	public void onEnable() {
		if (this.mc.thePlayer != null) {
			this.startX = this.mc.thePlayer.posX;
			this.startY = this.mc.thePlayer.posY;
			this.startZ = this.mc.thePlayer.posZ;
			this.startYaw = this.mc.thePlayer.rotationYaw;
			this.startPitch = this.mc.thePlayer.rotationPitch;

			this.prayerCopy = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.getGameProfile());
			this.prayerCopy.inventory = this.mc.thePlayer.inventory;
			this.prayerCopy.inventoryContainer = this.mc.thePlayer.inventoryContainer;
			this.prayerCopy.setPositionAndRotation(this.startX, this.startY, this.startZ, this.startYaw,
					this.startPitch);
			this.prayerCopy.rotationYawHead = this.mc.thePlayer.rotationYawHead;
			this.mc.theWorld.addEntityToWorld(-1, this.prayerCopy);
		}
		super.onEnable();
	}

	@EventListener
	private void onPreUpdate(EventPreMotionUpdates event) {
		this.mc.thePlayer.noClip = true;
		this.mc.thePlayer.capabilities.isFlying = true;
		event.setCancelled(true);
	}

	@EventListener
	private void onBoundingBox(EventEntityCollision event) {
		event.setBoundingBox(null);
		event.setCancelled(false);
	}

	public void onDisable() {
		this.mc.thePlayer.setPositionAndRotation(this.startX, this.startY, this.startZ, this.startYaw, this.startPitch);
		this.mc.thePlayer.noClip = false;
		this.mc.theWorld.removeEntityFromWorld(-1);
		this.mc.thePlayer.capabilities.isFlying = false;
		super.onDisable();
	}

}
