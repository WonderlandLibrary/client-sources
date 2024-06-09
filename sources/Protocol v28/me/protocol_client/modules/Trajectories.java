package me.protocol_client.modules;

import java.util.ArrayList;
import java.util.List;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.Render3DEvent;

public class Trajectories extends Module {

	public Trajectories() {
		super("Trajectories", "trajectories", 0, Category.RENDER, new String[] { "" });
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void onRenderEvent(Render3DEvent event) {
		double renderPosX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * event.getPartialTicks();
		double renderPosY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * event.getPartialTicks();
		double renderPosZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * event.getPartialTicks();

		ItemStack stack;
		Item item;

		if (mc.thePlayer.getHeldItem() != null && mc.gameSettings.thirdPersonView == 0) {
			if (!isValidPotion(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getItem()) && mc.thePlayer.getHeldItem().getItem() != Items.experience_bottle && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemFishingRod) && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBow) && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemSnowball) && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemEnderPearl) && !(mc.thePlayer.getHeldItem().getItem() instanceof ItemEgg))
				return;
			stack = mc.thePlayer.getHeldItem();
			item = mc.thePlayer.getHeldItem().getItem();
		} else {
			return;
		}

		double posX = renderPosX - MathHelper.cos(mc.thePlayer.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		double posY = renderPosY + mc.thePlayer.getEyeHeight() - 0.1000000014901161D;
		double posZ = renderPosZ - MathHelper.sin(mc.thePlayer.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;

		double motionX = -MathHelper.sin(mc.thePlayer.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(mc.thePlayer.rotationPitch / 180.0F * (float) Math.PI) * (item instanceof ItemBow ? 1.0D : 0.4D);
		double motionY = -MathHelper.sin(mc.thePlayer.rotationPitch / 180.0F * (float) Math.PI) * (item instanceof ItemBow ? 1.0D : 0.4D);
		double motionZ = MathHelper.cos(mc.thePlayer.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(mc.thePlayer.rotationPitch / 180.0F * (float) Math.PI) * (item instanceof ItemBow ? 1.0D : 0.4D);

		int var6 = 72000 - mc.thePlayer.getItemInUseCount();
		float power = var6 / 20.0F;
		power = (power * power + power * 2.0F) / 3.0F;

		if (power < 0.1D)
			return;

		if (power > 1.0F)
			power = 1.0F;

		float distance = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);

		motionX /= distance;
		motionY /= distance;
		motionZ /= distance;

		float pow = (item instanceof ItemBow ? power * 2.0F : isValidPotion(stack, item) ? 0.325F : item instanceof ItemFishingRod ? 1.25F : mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.9F : 1.0F);

		motionX *= pow * (item instanceof ItemFishingRod ? 0.75F : mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.75F : 1.5F);
		motionY *= pow * (item instanceof ItemFishingRod ? 0.75F : mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.75F : 1.5F);
		motionZ *= pow * (item instanceof ItemFishingRod ? 0.75F : mc.thePlayer.getHeldItem().getItem() == Items.experience_bottle ? 0.75F : 1.5F);

		GlStateManager.pushMatrix();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.func_179090_x();
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(1f);
		if (power > 0.6F) {
			GlStateManager.color(0.0F, 1.0F, 0.0F, 1.0F);
		} else if (power > 0.3F) {
			GlStateManager.color(0.8F, 0.5F, 0.0F, 1.0F);
		} else {
			GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
		}
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer renderer = tessellator.getWorldRenderer();
		renderer.startDrawing(3);

		renderer.addVertex(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);

		float size = (float) (item instanceof ItemBow ? 0.3D : 0.25D);
		boolean hasLanded = false;
		Entity landingOnEntity = null;
		MovingObjectPosition landingPosition = null;
		while (!hasLanded && posY > 0.0D) {
			Vec3 present = new Vec3(posX, posY, posZ);
			Vec3 future = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);

			MovingObjectPosition possibleLandingStrip = mc.theWorld.rayTraceBlocks(present, future, false, true, false);
			if (possibleLandingStrip != null && possibleLandingStrip.typeOfHit != MovingObjectPosition.MovingObjectType.MISS) {
				landingPosition = possibleLandingStrip;
				hasLanded = true;
			}

			AxisAlignedBB arrowBox = new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size);
			List entities = getEntitiesWithinAABB(arrowBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			for (Object entity : entities) {
				Entity boundingBox = (Entity) entity;
				if ((boundingBox.canBeCollidedWith()) && (boundingBox != mc.thePlayer)) {
					float var11 = 0.3F;
					AxisAlignedBB var12 = boundingBox.getEntityBoundingBox().expand(var11, var11, var11);
					MovingObjectPosition possibleEntityLanding = var12.calculateIntercept(present, future);
					if (possibleEntityLanding != null) {
						hasLanded = true;
						landingOnEntity = boundingBox;
						landingPosition = possibleEntityLanding;
					}
				}
			}

			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			float motionAdjustment = 0.99F;
			motionX *= motionAdjustment;
			motionY *= motionAdjustment;
			motionZ *= motionAdjustment;
			motionY -= (item instanceof ItemBow ? 0.05D : 0.03D);
			renderer.addVertex(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);
		}
		tessellator.draw();

		if (landingPosition != null && landingPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			GlStateManager.translate(posX - renderPosX, posY - renderPosY, posZ - renderPosZ);

			int side = landingPosition.field_178784_b.getIndex();

			if (side == 2) {
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			} else if (side == 3) {
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			} else if (side == 4) {
				GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
			} else if (side == 5) {
				GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
			}

			Cylinder c = new Cylinder();
			GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			c.setDrawStyle(GLU.GLU_LINE);

			GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glLineWidth(3.5f);
			c.draw(0.6F, 0.1F, 0.0F, 40, 1);
			GL11.glLineWidth(1.5f);
			if (power > 0.6F) {
				GlStateManager.color(0.0F, 1.0F, 0.0F, 1.0F);
			} else if (power > 0.3F) {
				GlStateManager.color(0.8F, 0.5F, 0.0F, 1.0F);
			} else {
				GlStateManager.color(1.0F, 0.0F, 0.0F, 1.0F);
			}
			if (landingOnEntity != null) {
				GlStateManager.color(1.0F, 1.0F, 0.0F, 1.0F);
			}
			c.draw(0.6F, 0.1F, 0.0F, 40, 1);
		}
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.func_179098_w();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();
	}

	public void drawESP(Entity landingOnEntity) {
		double x = (landingOnEntity.lastTickPosX + (landingOnEntity.posX - landingOnEntity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
		double y = (landingOnEntity.lastTickPosY + (landingOnEntity.posY - landingOnEntity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
		double z = (landingOnEntity.lastTickPosZ + (landingOnEntity.posZ - landingOnEntity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(1, 0.5f, 0, 0.1001F);
		if (landingOnEntity instanceof EntityPlayer) {
			RenderUtils.FilledESP(new AxisAlignedBB(x - landingOnEntity.width + 0.20, y, z - landingOnEntity.width + 0.20, x + landingOnEntity.width - 0.20, y + landingOnEntity.height, z + landingOnEntity.width - 0.20));
		} else {
			RenderUtils.FilledESP(new AxisAlignedBB(x - landingOnEntity.width, y, z - landingOnEntity.width, x + landingOnEntity.width, y + landingOnEntity.height, z + landingOnEntity.width));
		}
		GL11.glLineWidth(0.8F);
		GL11.glColor4f(0, 0, 0, 1F);
		if (landingOnEntity instanceof EntityPlayer) {
			RenderUtils.ProtocolEntityBox(new AxisAlignedBB(x - landingOnEntity.width + 0.20, y, z - landingOnEntity.width + 0.20, x + landingOnEntity.width - 0.20, y + landingOnEntity.height, z + landingOnEntity.width - 0.20));
		} else {
			RenderUtils.ProtocolEntityBox(new AxisAlignedBB(x - landingOnEntity.width, y, z - landingOnEntity.width, x + landingOnEntity.width, y + landingOnEntity.height, z + landingOnEntity.width));
		}
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GlStateManager.color(1.0F, 1.0F, 0.0F, 1.0F);
	}

	private boolean isValidPotion(ItemStack stack, Item item) {
		if (item != null && item instanceof ItemPotion) {
			ItemPotion potion = (ItemPotion) item;
			if (!ItemPotion.isSplash(stack.getItemDamage()))
				return false;

			if (potion.getEffects(stack) != null) {
				return true;
			}
		}
		return false;
	}

	private List getEntitiesWithinAABB(AxisAlignedBB bb) {
		ArrayList list = new ArrayList();
		int chunkMinX = MathHelper.floor_double((bb.minX - 2.0D) / 16.0D);
		int chunkMaxX = MathHelper.floor_double((bb.maxX + 2.0D) / 16.0D);
		int chunkMinZ = MathHelper.floor_double((bb.minZ - 2.0D) / 16.0D);
		int chunkMaxZ = MathHelper.floor_double((bb.maxZ + 2.0D) / 16.0D);
		for (int x = chunkMinX; x <= chunkMaxX; x++) {
			for (int z = chunkMinZ; z <= chunkMaxZ; z++) {
				if (mc.theWorld.getChunkProvider().chunkExists(x, z)) {
					mc.theWorld.getChunkFromChunkCoords(x, z).func_177414_a(mc.thePlayer, bb, list, null);
				}
			}
		}
		return list;
	}
}
