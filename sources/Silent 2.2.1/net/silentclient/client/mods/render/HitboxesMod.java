package net.silentclient.client.mods.render;

import java.awt.Color;

import net.silentclient.client.mods.render.crosshair.CrosshairMod;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ClientTickEvent;
import net.silentclient.client.event.impl.EventRenderHitbox;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class HitboxesMod extends Mod {
	public HitboxesMod() {
		super("Hitboxes", ModCategory.MODS, "silentclient/icons/mods/hitboxes.png");
	}
	
	@Override
	public void setup() {
		this.addBooleanSetting("Bounding Box", this, true);
		this.addColorSetting("Bounding Box Color", this, new Color(255, 255, 255));
		this.addBooleanSetting("Eye Height", this, false);
		this.addColorSetting("Eye Height Color", this, new Color(176,46,38));
		this.addBooleanSetting("Look Vector", this, false);
		this.addColorSetting("Look Vector Color", this, new Color(60,68,169));
		this.addSliderSetting("Line Width", this, 2, 1, 5, true);
		this.addBooleanSetting("Projectile Hitbox", this, true);
		this.addBooleanSetting("Arrow Hitbox", this, true);
		this.addBooleanSetting("Players Hitbox", this, true);
		this.addBooleanSetting("Passive Hitbox", this, true);
		this.addBooleanSetting("Monsters Hitbox", this, true);
		this.addBooleanSetting("Fireballs Hitbox", this, true);
		this.addBooleanSetting("Snowballs Hitbox", this, true);
		this.addBooleanSetting("Wither Skulls Hitbox", this, true);
		this.addBooleanSetting("Item Drops Hitbox", this, true);
		this.addBooleanSetting("Fireworks Hitbox", this, true);
		this.addBooleanSetting("XP Orbs Hitbox", this, true);
		this.addBooleanSetting("Item Frames Hitbox", this, true);
	}
	
	@EventTarget
	public void onRenderHitbox(EventRenderHitbox event) {
		event.setCancelled(true);
		boolean projectile = Client.getInstance().getSettingsManager().getSettingByName(this, "Projectile Hitbox").getValBoolean();
		boolean arrow = Client.getInstance().getSettingsManager().getSettingByName(this, "Arrow Hitbox").getValBoolean();
		boolean players = Client.getInstance().getSettingsManager().getSettingByName(this, "Players Hitbox").getValBoolean();
		boolean passive = Client.getInstance().getSettingsManager().getSettingByName(this, "Passive Hitbox").getValBoolean();
		boolean monsters = Client.getInstance().getSettingsManager().getSettingByName(this, "Monsters Hitbox").getValBoolean();
		boolean fireballs = Client.getInstance().getSettingsManager().getSettingByName(this, "Fireballs Hitbox").getValBoolean();
		boolean snowballs = Client.getInstance().getSettingsManager().getSettingByName(this, "Snowballs Hitbox").getValBoolean();
		boolean witherSkulls = Client.getInstance().getSettingsManager().getSettingByName(this, "Wither Skulls Hitbox").getValBoolean();
		boolean itemDrops = Client.getInstance().getSettingsManager().getSettingByName(this, "Item Drops Hitbox").getValBoolean();
		boolean fireworks = Client.getInstance().getSettingsManager().getSettingByName(this, "Fireworks Hitbox").getValBoolean();
		boolean xpOrbs = Client.getInstance().getSettingsManager().getSettingByName(this, "XP Orbs Hitbox").getValBoolean();
		boolean itemFrames = Client.getInstance().getSettingsManager().getSettingByName(this, "Item Frames Hitbox").getValBoolean();
		
		if(
				(event.getEntity() instanceof EntityThrowable && !projectile) ||
				(event.getEntity() instanceof EntityArrow && !arrow) ||
				(event.getEntity() instanceof EntityPlayer && !players) ||
				(!CrosshairMod.hostileEntity(event.getEntity()) && !passive) ||
				(event.getEntity() instanceof EntityMob && !monsters) ||
				(event.getEntity() instanceof EntityFireball && !fireballs) ||
				(event.getEntity() instanceof EntitySnowball && !snowballs) ||
				(event.getEntity() instanceof EntityWitherSkull && !witherSkulls) ||
				(event.getEntity() instanceof EntityItem && !itemDrops) ||
				(event.getEntity() instanceof EntityFireworkRocket && !fireworks) ||
				(event.getEntity() instanceof EntityXPOrb && !xpOrbs) ||
				(event.getEntity() instanceof EntityItemFrame && !itemFrames)
		) {
			return;
		}
				
		boolean boundingBox = Client.getInstance().getSettingsManager().getSettingByName(this, "Bounding Box").getValBoolean();
		boolean eyeHeight = Client.getInstance().getSettingsManager().getSettingByName(this, "Eye Height").getValBoolean();
		boolean lookVector = Client.getInstance().getSettingsManager().getSettingByName(this, "Look Vector").getValBoolean();
		Color boundingBoxColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Bounding Box Color").getValColor();
		Color eyeHeightColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Eye Height Color").getValColor();
		Color lookVectorColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Look Vector Color").getValColor();
		int lineWidth = Client.getInstance().getSettingsManager().getSettingByName(this, "Line Width").getValInt();
		
		float half = event.getEntity().width / 2.0F;
		
		GlStateManager.depthMask(false);
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.enableBlend();
		GL11.glLineWidth(lineWidth);
		
		if(boundingBox) {
			AxisAlignedBB box = event.getEntity().getEntityBoundingBox();
			AxisAlignedBB offsetBox = new AxisAlignedBB(box.minX - event.getEntity().posX + event.getX(),
					box.minY - event.getEntity().posY + event.getY(), box.minZ - event.getEntity().posZ + event.getZ(),
					box.maxX - event.getEntity().posX + event.getX(), box.maxY - event.getEntity().posY + event.getY(),
					box.maxZ - event.getEntity().posZ + event.getZ());
			
			RenderGlobal.drawOutlinedBoundingBox(offsetBox, boundingBoxColor.getRed(), boundingBoxColor.getGreen(), boundingBoxColor.getBlue(), boundingBoxColor.getAlpha());
		}
		
		if(eyeHeight && event.getEntity() instanceof EntityLivingBase) {
			RenderGlobal.drawOutlinedBoundingBox(
					new AxisAlignedBB(event.getX() - half, event.getY() + event.getEntity().getEyeHeight() - 0.009999999776482582D,
							event.getZ() - half, event.getX() + half,
							event.getY() + event.getEntity().getEyeHeight() + 0.009999999776482582D, event.getZ() + half),
					eyeHeightColor.getRed(), eyeHeightColor.getGreen(), eyeHeightColor.getBlue(),
					eyeHeightColor.getAlpha());
		}
		
		if(lookVector) {
			
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			
			Vec3 look = event.getEntity().getLook(event.getPartialTicks());
			worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
			worldrenderer.pos(event.getX(), event.getY() + event.getEntity().getEyeHeight(), event.getZ()).color(0, 0, 255, 255)
					.endVertex();
			worldrenderer.pos(event.getX() + look.xCoord * 2,
					event.getY() + event.getEntity().getEyeHeight() + look.yCoord * 2, event.getZ() + look.zCoord * 2)
					.color(lookVectorColor.getRed(), lookVectorColor.getGreen(), lookVectorColor.getBlue(), lookVectorColor.getAlpha()).endVertex();
			tessellator.draw();
		}
		
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
	}
	
	@EventTarget
	public void tick(ClientTickEvent event) {
		if(mc.getRenderManager() != null  && mc.getRenderManager().isDebugBoundingBox() == false) {
			mc.getRenderManager().setDebugBoundingBox(true);
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		if(mc.getRenderManager() != null) {
			mc.getRenderManager().setDebugBoundingBox(true);
		}
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		if(mc.getRenderManager() != null) {
			mc.getRenderManager().setDebugBoundingBox(false);
		}
	}
}
