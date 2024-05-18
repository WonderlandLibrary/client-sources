package com.darkcart.xdolf.mods.render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.RenderUtils;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemSnowball;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Trajectories extends Module {
	
	private double x, y, z;
	private double motionX, motionY, motionZ;
	private boolean hitEntity = false;
	private double r, g, b;
	
	public Trajectories() {
		super("trajectories", "Old", "Predicts where throwable items and projectiles will land.", Keyboard.KEY_F6, 0xFFFFFF, Category.RENDER);
	}
	
	public double pX = -9000, pY = -9000, pZ = -9000;
	
	@Override
	public void onRender() {
		EntityPlayerSP player = Wrapper.getPlayer();
		if (player.inventory.getCurrentItem() != null && isEnabled()) {
			if (this.isThrowable(player.inventory.getCurrentItem().getItem())) {
				this.x = player.lastTickPosX
						+ (player.posX - player.lastTickPosX)
						* (double) Wrapper.getMinecraft().timer.renderPartialTicks
						- (double) (MathHelper.cos((float) Math.toRadians((double) player.rotationYaw)) * 0.16F);
				this.y = player.lastTickPosY
						+ (player.posY - player.lastTickPosY) * (double) Wrapper.getMinecraft().timer.renderPartialTicks
						+ (double) player.getEyeHeight() - 0.100149011612D;
				this.z = player.lastTickPosZ
						+ (player.posZ - player.lastTickPosZ)
						* (double) Wrapper.getMinecraft().timer.renderPartialTicks
						- (double) (MathHelper.sin((float) Math.toRadians((double) player.rotationYaw)) * 0.16F);
				float con = 1.0F;
				if (!(player.inventory.getCurrentItem().getItem() instanceof ItemBow)) {
					con = 0.4F;
				}

				this.motionX = (double) (-MathHelper.sin((float) Math.toRadians((double) player.rotationYaw))
						* MathHelper.cos((float) Math.toRadians((double) player.rotationPitch)) * con);
				this.motionZ = (double) (MathHelper.cos((float) Math.toRadians((double) player.rotationYaw))
						* MathHelper.cos((float) Math.toRadians((double) player.rotationPitch)) * con);
				this.motionY = (double) (-MathHelper.sin((float) Math.toRadians((double) player.rotationPitch)) * con);
				double ssum = Math.sqrt(this.motionX * this.motionX
						+ this.motionY * this.motionY + this.motionZ
						* this.motionZ);
				
				this.motionX /= ssum;
				this.motionY /= ssum;
				this.motionZ /= ssum;

				if (player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
					float pow = (float) (72000 - player.getItemInUseCount()) / 20.0F;
					pow = (pow * pow + pow * 2.0F) / 3.0F;

					if (pow > 1.0F) {
						pow = 1.0F;
					}

					if (pow <= 0.1F) {
						pow = 1.0F;
					}

					pow *= 2.0F;
					pow *= 1.5F;
					this.motionX *= (double) pow;
					this.motionY *= (double) pow;
					this.motionZ *= (double) pow;
				} else {
					this.motionX *= 1.5D;
					this.motionY *= 1.5D;
					this.motionZ *= 1.5D;
				}

				Vec3d playerVector = new Vec3d(player.posX,
						player.posY + player.getEyeHeight(), player.posZ);
				GL11.glPushMatrix();
				enableDefaults();
				GL11.glLineWidth(1.8F);
				GL11.glColor3d(r, g, b);
				GL11.glBegin(GL11.GL_LINE_STRIP);
				double gravity = this.getGravity(player.inventory.getCurrentItem().getItem());

				for (int q = 0; q < 1000; ++q) {
					double rx = this.x * 1.0D - RenderManager.renderPosX;
					double ry = this.y * 1.0D - RenderManager.renderPosY;
					double rz = this.z * 1.0D - RenderManager.renderPosZ;
					GL11.glVertex3d(rx, ry, rz);

					this.x += this.motionX;
					this.y += this.motionY;
					this.z += this.motionZ;
					this.motionX *= 0.99D;
					this.motionY *= 0.99D;
					this.motionZ *= 0.99D;
					this.motionY -= gravity;
					
					if(Wrapper.getWorld().rayTraceBlocks(playerVector,
							new Vec3d(this.x, this.y, this.z)) != null)
							break;
				}

				GL11.glEnd();

				AxisAlignedBB bbox = new AxisAlignedBB(x - 0.5 - RenderManager.renderPosX, y - 0.5 - RenderManager.renderPosY, z - 0.5 - RenderManager.renderPosZ, (x - 0.5 - RenderManager.renderPosX) + 1, (y - 0.5 - RenderManager.renderPosY) + 1, (z - 0.5 - RenderManager.renderPosZ) + 1);
				
				GL11.glTranslated(x  - RenderManager.renderPosX, y  - RenderManager.renderPosY, z  - RenderManager.renderPosZ);
				GL11.glRotatef(Wrapper.getPlayer().rotationYaw, 0.0F, (float) (y  - RenderManager.renderPosY), 0.0F);
				GL11.glTranslated(-(x  - RenderManager.renderPosX), -(y  - RenderManager.renderPosY), -(z  - RenderManager.renderPosZ));
				RenderUtils.drawESP(x - 0.35 - RenderManager.renderPosX, y - 0.5 - RenderManager.renderPosY, z - 0.5 - RenderManager.renderPosZ, r, b, g);
				disableDefaults();
				GL11.glPopMatrix();
			}
		}
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			r = player.getDistance(x, y, z) / 100;
			g = 1.0;
			b = 0.0;
		}
	}
	
	public void enableDefaults() {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glDisable(2929);
		GL11.glEnable(GL13.GL_MULTISAMPLE);
		GL11.glDepthMask(false);
	}

	public void disableDefaults() {
		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDisable(GL13.GL_MULTISAMPLE);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	private double getGravity(Item item) {
		return item instanceof ItemBow ? 0.05D : 0.03D;
	}

	private boolean isThrowable(Item item) {
		return item instanceof ItemBow || item instanceof ItemSnowball
				|| item instanceof ItemEgg || item instanceof ItemEnderPearl;
	}
	
	public void drawLine3D(float var1, float var2, float var3, float var4,
			float var5, float var6) {
		GL11.glVertex3d(var1, var2, var3);
		GL11.glVertex3d(var4, var5, var6);
	}
}