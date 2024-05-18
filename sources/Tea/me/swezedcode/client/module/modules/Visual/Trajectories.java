package me.swezedcode.client.module.modules.Visual;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventRender;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemSnowball;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Trajectories extends Module {

	public Trajectories() {
		super("Trajectories", Keyboard.KEY_NONE, 0xFF3BDE35, ModCategory.Visual);
	}

	@EventListener
	public void onEvent(EventRender event) {
		boolean bow = false;
		if (this.mc.thePlayer.getHeldItem() == null) {
			return;
		}
		if ((!(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow))
				&& (!(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSnowball))
				&& (!(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemEnderPearl))
				&& (!(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemEgg))) {
			return;
		}
		bow = this.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow;
		this.mc.getRenderManager();
		double posX = RenderManager.renderPosX
				- MathHelper.cos(this.mc.thePlayer.rotationYaw / 180.0F * 3.141593F) * 0.16F;
		this.mc.getRenderManager();
		double posY = RenderManager.renderPosY + this.mc.thePlayer.getEyeHeight() - 0.1000000014901161D;
		this.mc.getRenderManager();
		double posZ = RenderManager.renderPosZ
				- MathHelper.sin(this.mc.thePlayer.rotationYaw / 180.0F * 3.141593F) * 0.16F;
		double motionX = -MathHelper.sin(this.mc.thePlayer.rotationYaw / 180.0F * 3.141593F)
				* MathHelper.cos(this.mc.thePlayer.rotationPitch / 180.0F * 3.141593F) * (bow ? 1.0D : 0.4D);
		double motionY = -MathHelper.sin(this.mc.thePlayer.rotationPitch / 180.0F * 3.141593F) * (bow ? 1.0D : 0.4D);
		double motionZ = MathHelper.cos(this.mc.thePlayer.rotationYaw / 180.0F * 3.141593F)
				* MathHelper.cos(this.mc.thePlayer.rotationPitch / 180.0F * 3.141593F) * (bow ? 1.0D : 0.4D);
		int var6 = 72000 - this.mc.thePlayer.getItemInUseCount();
		float power = var6 / 20.0F;
		power = (power * power + power * 2.0F) / 3.0F;
		if (power < 0.1D) {
			return;
		}
		if (power > 1.0F) {
			power = 1.0F;
		}
		float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		motionX /= distance;
		motionY /= distance;
		motionZ /= distance;
		motionX *= (bow ? power * 2.0F : 1.0F) * 1.5D;
		motionY *= (bow ? power * 2.0F : 1.0F) * 1.5D;
		motionZ *= (bow ? power * 2.0F : 1.0F) * 1.5D;
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glEnable(3553);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200.0F, 0.0F);
		GL11.glDisable(2896);
		GL11.glEnable(2848);
		GL11.glDisable(2929);
		GL11.glPushMatrix();
		GL11.glColor4f(0.5F, 1.0F, 1.0F, 0.5F);
		GL11.glDisable(3553);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glLineWidth(2.0F);
		GL11.glBegin(3);
		boolean hasLanded = false;
		Entity hitEntity = null;
		MovingObjectPosition landingPosition = null;
		for (; (!hasLanded) && (posY > 0.0D); GL11.glVertex3d(posX - RenderManager.renderPosX,
				posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ)) {
			Vec3 present = new Vec3(posX, posY, posZ);
			Vec3 future = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition possibleLandingStrip = this.mc.theWorld.rayTraceBlocks(present, future, false, true,
					false);
			if ((possibleLandingStrip != null)
					&& (possibleLandingStrip.typeOfHit != MovingObjectPosition.MovingObjectType.MISS)) {
				landingPosition = possibleLandingStrip;
				hasLanded = true;
			}
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			float motionAdjustment = 0.99F;
			motionX *= 0.9900000095367432D;
			motionY *= 0.9900000095367432D;
			motionZ *= 0.9900000095367432D;
			motionY -= (bow ? 0.05D : 0.03D);
			this.mc.getRenderManager();
			this.mc.getRenderManager();
			this.mc.getRenderManager();
		}
		GL11.glEnd();
		GL11.glPushMatrix();
		this.mc.getRenderManager();
		this.mc.getRenderManager();
		this.mc.getRenderManager();
		GL11.glTranslated(posX - RenderManager.renderPosX, posY - RenderManager.renderPosY,
				posZ - RenderManager.renderPosZ);
		if ((landingPosition != null) && (landingPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)) {
			int kek = landingPosition.field_178784_b.getIndex();
			if (kek == 2) {
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			} else if (kek == 3) {
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			} else if (kek == 4) {
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			} else if (kek == 5) {
				GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			}
			GL11.glBegin(1);
			GL11.glVertex3d(-0.4D, 0.0D, 0.0D);
			GL11.glVertex3d(0.0D, 0.0D, 0.0D);
			GL11.glVertex3d(0.0D, 0.0D, -0.4D);
			GL11.glVertex3d(0.0D, 0.0D, 0.0D);
			GL11.glVertex3d(0.4D, 0.0D, 0.0D);
			GL11.glVertex3d(0.0D, 0.0D, 0.0D);
			GL11.glVertex3d(0.0D, 0.0D, 0.4D);
			GL11.glVertex3d(0.0D, 0.0D, 0.0D);
			GL11.glEnd();
			GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
		}
		GL11.glPopMatrix();
		GL11.glDisable(3042);
		GL11.glDepthMask(true);
		GL11.glEnable(3553);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(2929);
		GL11.glDisable(2848);
		GL11.glPopMatrix();
	}

}
