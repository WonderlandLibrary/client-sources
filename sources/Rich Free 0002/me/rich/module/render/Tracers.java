package me.rich.module.render;

import org.lwjgl.opengl.GL11;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.helpers.friend.FriendManager;
import me.rich.helpers.render.ColorHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;


public class Tracers extends Feature {
	public Tracers() {
		super("Tracers", 0, Category.RENDER);
		Main.settingsManager.rSetting(new Setting("FriendAstolfo", this, false));
	}

	@EventTarget
	public void onEvent3D(Event3D event) {
		boolean old = mc.gameSettings.viewBobbing;
		mc.gameSettings.viewBobbing = false;
		mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 2);
		mc.gameSettings.viewBobbing = old;
		GL11.glPushMatrix();
		GL11.glEnable(2848);
		GL11.glDisable(2929);
		RenderHelper.startSmooth();
		GL11.glDisable(3553);
		GL11.glDisable(2896);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(0.6f);
		for (Entity entity : mc.world.loadedEntityList) {
			if (entity == mc.player || !(entity instanceof EntityPlayer))
				continue;
			assert (mc.getRenderViewEntity() != null);
			mc.getRenderViewEntity().getDistanceToEntity(entity);
			double d = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) - mc.getRenderManager().viewerPosX;
			double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) - mc.getRenderManager().viewerPosY;
			double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) - mc.getRenderManager().viewerPosZ;
			if(!FriendManager.isFriend(entity.getName())) {
			GL11.glColor4f(255.0f, 255.0f, 255.0f, 255.0f);
			} else if(FriendManager.isFriend(entity.getName()) && Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Tracers.class), "FriendAstolfo").getValBoolean()) {
				RenderHelper.setColor(ColorHelper.astolfoColors1(0, 0).getRGB()	);
			} else if(FriendManager.isFriend(entity.getName()) && !Main.settingsManager.getSettingByName(Main.moduleManager.getModule(Tracers.class), "FriendAstolfo").getValBoolean()) {
				GL11.glColor4f(0f, 255f, 0f, 255.0f);
			}
			Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0);
			vec3d = vec3d.rotatePitch(-((float) Math.toRadians(mc.player.rotationPitch)));
			Vec3d vec3d2 = vec3d.rotateYaw(-((float) Math.toRadians(mc.player.rotationYaw)));
			GL11.glBegin(2);
			GL11.glVertex3d(vec3d2.xCoord, (double) mc.player.getEyeHeight() + vec3d2.yCoord, vec3d2.zCoord);
			GL11.glVertex3d(d, d2 + 1.1, d3);
			GL11.glEnd();
		}
		GL11.glDisable(3042);
		GL11.glDepthMask(true);
		RenderHelper.endSmooth();
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDisable(2848);
		GL11.glPopMatrix();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(this.getName(), "was enabled.", NotificationType.INFO);
	}

	public void onDisable() {
		NotificationPublisher.queue(this.getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}

