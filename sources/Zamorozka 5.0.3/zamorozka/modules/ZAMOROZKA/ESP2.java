package zamorozka.modules.ZAMOROZKA;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventNameTag;
import zamorozka.event.events.EventRender2D;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.AntiBot2;
import zamorozka.modules.TRAFFIC.AutoSneak;
import zamorozka.modules.TRAFFIC.Sneak;
import zamorozka.modules.VISUALLY.NameTags;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.AttackEvent;
import zamorozka.ui.ChatUtils;
import zamorozka.ui.Colors;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.MiscUtils;
import zamorozka.ui.Render3DEvent;
import zamorozka.ui.RenderUtils;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.RenderingTools;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.font.CFontRenderer;
import zamorozka.ui.font.Fonts;

public class ESP2 extends Module {

	public final List<Entity> collectedEntities = new ArrayList<>();
	private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
	private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
	private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
	private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
	private int color = Color.WHITE.getRGB();
	private final int backgroundColor = (new Color(0, 0, 0, 120)).getRGB();
	private final int black = Color.BLACK.getRGB();

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Box");
		options.add("Corner");
		options.add("Apex");
		Zamorozka.instance.settingsManager.rSetting(new Setting("2D Mode", this, "Corner", options));
		Zamorozka.instance.settingsManager.rSetting(new Setting("RenderHealth", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("RenderArmor", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("RenderBorder", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("RenderTags", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("RenderItems", this, true));
		Zamorozka.instance.settingsManager.rSetting(new Setting("RenderItemsRange", this, 10, 5, 100, true));
	}

	public ESP2() {
		super("ESP2D", 0, Category.VISUALLY);
	}

	@EventTarget
	public void onRender2D(EventRender2D render) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("2D Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		if (Zamorozka.settingsManager.getSettingByName("RenderBorder").getValBoolean()) {
			this.setDisplayName("ESP2D §f§" + " " + modeput);
		} else {
			this.setDisplayName("ESP2D §f§" + " " + "WithoutBorder");
		}
		GL11.glPushMatrix();
		collectEntities();
		float partialTicks = render.getPartialTicks();
		ScaledResolution scaledResolution = render.getResolution();
		int scaleFactor = scaledResolution.getScaleFactor();
		double scaling = scaleFactor / Math.pow(scaleFactor, 2.0D);
		GL11.glScaled(scaling, scaling, scaling);
		int black = this.black;
		int color = this.color;
		int background = this.backgroundColor;
		float scale = 1F;
		float upscale = 1.0F / scale;
		FontRenderer fr = mc.fontRendererObj;
		CFontRenderer font1 = Fonts.default12;
		CFontRenderer font2 = Fonts.default20;
		CFontRenderer font3 = Fonts.default10;
		RenderManager renderMng = mc.getRenderManager();
		EntityRenderer entityRenderer = mc.entityRenderer;
		List<Entity> collectedEntities = this.collectedEntities;

		for (int i = 0, collectedEntitiesSize = collectedEntities.size(); i < collectedEntitiesSize; i++) {
			Entity entity = collectedEntities.get(i);
			if (isValid(entity) && RenderingUtils.isInViewFrustrum(entity)) {
				double x = RenderingUtils.interpolate(entity.posX, entity.lastTickPosX, partialTicks);
				double y = RenderingUtils.interpolate(entity.posY, entity.lastTickPosY, partialTicks);
				double z = RenderingUtils.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks);
				double width = entity.width / 1.5D;
				double height = entity.height + ((entity.isSneaking() || (entity == mc.player && ModuleManager.getModule(AutoSneak.class).getState()) ? -0.3D : 0.2D));
				AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
				Vector3d[] vectors = new Vector3d[] { new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ),
						new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ) };
				entityRenderer.setupCameraTransform(partialTicks, 0);
				Vector4d position = null;
				for (Vector3d vector : vectors) {
					vector = project2D(scaleFactor, vector.x - renderMng.viewerPosX, vector.y - renderMng.viewerPosY, vector.z - renderMng.viewerPosZ);
					if (vector != null && vector.z >= 0.0D && vector.z < 1.0D) {
						if (position == null)
							position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
						position.x = Math.min(vector.x, position.x);
						position.y = Math.min(vector.y, position.y);
						position.z = Math.max(vector.x, position.z);
						position.w = Math.max(vector.y, position.w);
					}
				}

				if (position != null) {
					entityRenderer.setupOverlayRendering();
					double posX = position.x;
					double posY = position.y;
					double endPosX = position.z;
					double endPosY = position.w;
					if (mode.equalsIgnoreCase("Box") && Zamorozka.settingsManager.getSettingByName("RenderBorder").getValBoolean()) {
						RenderingUtils.drawRect(posX - 1.0D, posY, posX + 0.5D, endPosY + 0.5D, black);
						RenderingUtils.drawRect(posX - 1.0D, posY - 0.5D, endPosX + 0.5D, posY + 0.5D + 0.5D, black);
						RenderingUtils.drawRect(endPosX - 0.5D - 0.5D, posY, endPosX + 0.5D, endPosY + 0.5D, black);
						RenderingUtils.drawRect(posX - 1.0D, endPosY - 0.5D - 0.5D, endPosX + 0.5D, endPosY + 0.5D, black);
						RenderingUtils.drawRect(posX - 0.5D, posY, posX + 0.5D - 0.5D, endPosY, color);
						RenderingUtils.drawRect(posX, endPosY - 0.5D, endPosX, endPosY, color);
						RenderingUtils.drawRect(posX - 0.5D, posY, endPosX, posY + 0.5D, color);
						RenderingUtils.drawRect(endPosX - 0.5D, posY, endPosX, endPosY, color);
					}
					if (mode.equalsIgnoreCase("Apex") && Zamorozka.settingsManager.getSettingByName("RenderBorder").getValBoolean()) {
						RenderingUtils.drawRect(endPosX - .5 - .5, posY, endPosX + .5, endPosY + .5, black);
						RenderingUtils.drawRect(posX - 1, posY, posX + .5, endPosY + .5, black);

						RenderingUtils.drawRect(posX - 1, endPosY - 1, posX + (endPosX - posX) / 4 + .5, endPosY + .5, black);
						RenderingUtils.drawRect(endPosX - 1, endPosY - 1, endPosX + (posX - endPosX) / 4 - .5, endPosY + .5, black);
						RenderingUtils.drawRect(posX - 1, posY - .5, posX + (endPosX - posX) / 4 + .5, posY + 1, black);
						RenderingUtils.drawRect(endPosX, posY - .5, endPosX + (posX - endPosX) / 4 - .5, posY + 1, black);

						RenderingUtils.drawRect(posX - .5, posY, posX + .5 - .5, endPosY, color);
						RenderingUtils.drawRect(endPosX - .5, posY, endPosX, endPosY, color);

						RenderingUtils.drawRect(posX, endPosY - .5, posX + (endPosX - posX) / 4, endPosY, color);
						RenderingUtils.drawRect(endPosX, endPosY - .5, endPosX + (posX - endPosX) / 4, endPosY, color);
						RenderingUtils.drawRect(posX - .5, posY, posX + (endPosX - posX) / 4, posY + .5, color);
						RenderingUtils.drawRect(endPosX, posY, endPosX + (posX - endPosX) / 4, posY + .5, color);
					}
					if (mode.equalsIgnoreCase("Corner") && Zamorozka.settingsManager.getSettingByName("RenderBorder").getValBoolean()) {
						RenderingUtils.drawRect(posX + 0.5D, posY, posX - 1.0D, posY + (endPosY - posY) / 4.0D + 0.5D, black);
						RenderingUtils.drawRect(posX - 1.0D, endPosY, posX + 0.5D, endPosY - (endPosY - posY) / 4.0D - 0.5D, black);
						RenderingUtils.drawRect(posX - 1.0D, posY - 0.5D, posX + (endPosX - posX) / 3.0D + 0.5D, posY + 1.0D, black);
						RenderingUtils.drawRect(endPosX - (endPosX - posX) / 3.0D - 0.5D, posY - 0.5D, endPosX, posY + 1.0D, black);
						RenderingUtils.drawRect(endPosX - 1.0D, posY, endPosX + 0.5D, posY + (endPosY - posY) / 4.0D + 0.5D, black);
						RenderingUtils.drawRect(endPosX - 1.0D, endPosY, endPosX + 0.5D, endPosY - (endPosY - posY) / 4.0D - 0.5D, black);
						RenderingUtils.drawRect(posX - 1.0D, endPosY - 1.0D, posX + (endPosX - posX) / 3.0D + 0.5D, endPosY + 0.5D, black);
						RenderingUtils.drawRect(endPosX - (endPosX - posX) / 3.0D - 0.5D, endPosY - 1.0D, endPosX + 0.5D, endPosY + 0.5D, black);
						RenderingUtils.drawRect(posX, posY, posX - 0.5D, posY + (endPosY - posY) / 4.0D, color);
						RenderingUtils.drawRect(posX, endPosY, posX - 0.5D, endPosY - (endPosY - posY) / 4.0D, color);
						RenderingUtils.drawRect(posX - 0.5D, posY, posX + (endPosX - posX) / 3.0D, posY + 0.5D, color);
						RenderingUtils.drawRect(endPosX - (endPosX - posX) / 3.0D, posY, endPosX, posY + 0.5D, color);
						RenderingUtils.drawRect(endPosX - 0.5D, posY, endPosX, posY + (endPosY - posY) / 4.0D, color);
						RenderingUtils.drawRect(endPosX - 0.5D, endPosY, endPosX, endPosY - (endPosY - posY) / 4.0D, color);
						RenderingUtils.drawRect(posX, endPosY - 0.5D, posX + (endPosX - posX) / 3.0D, endPosY, color);
						RenderingUtils.drawRect(endPosX - (endPosX - posX) / 3.0D, endPosY - 0.5D, endPosX - 0.5D, endPosY, color);
					}
					boolean living = entity instanceof EntityLivingBase;
					if (living) {
						EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
						float hp2 = entityLivingBase.getHealth();
						float maxHealth = entityLivingBase.getMaxHealth();
						double hpPercentage = (hp2 / maxHealth);
						double hpHeight2 = (endPosY - posY) * hpPercentage;
						RenderingUtils.drawRect(posX - 3.5D, posY - 0.5D, posX - 1.5D, endPosY + 0.5D, background);
						if (hp2 > 0.0F) {
							float absorption = entityLivingBase.getAbsorptionAmount();
							int healthColor2 = (int) hp2;
							if (hp2 > 12) {
								healthColor2 = new Color(0, 251, 0).getRGB();
							} else if (hp2 < 12) {
								healthColor2 = new Color(199, 101, 0).getRGB();
							}
							RenderingUtils.drawRect(posX - 3.0D, endPosY, posX - 5.0D, endPosY - hpHeight2, healthColor2);
							// if (mc.player.getDistanceToEntity(entityLivingBase) < 20)
							// Fonts.comfortaa10.drawStringWithShadow("" + MathHelper.floor(hp2), posX -
							// 13.0D, endPosY - hpHeight2, healthColor2);
							// }
						}
					}
					if (living && Zamorozka.settingsManager.getSettingByName("RenderTags").getValBoolean()) {

						ModuleManager.getModule(NameTags.class).setState(false);
						if (!(ModuleManager.getModule(NameTags.class).getState())) {
							float scaledHeight = 20.0F;
							int texcolor = -1;
							String name = entity.getDisplayName().getFormattedText();
							if (entity instanceof EntityItem) {
								name = ((EntityItem) entity).getEntityItem().getDisplayName();
							} else if (ModuleManager.getModule(YoutuberMode.class).getState() && Zamorozka.settingsManager.getSettingByName("SpoofNames").getValBoolean()) {
								String newstr = "";
								char[] rdm = { 'l', 'i', 'j', '\'', ';', ':', '|' };
								for (int k = 0; k < name.length(); k++) {
									char ch = rdm[MiscUtils.randomNumber(rdm.length - 1, 0)];
									newstr = newstr.concat(ch + "");
								}
								name = newstr;
							}
							if (ModuleManager.getModule(YoutuberMode.class).getState() && Zamorozka.settingsManager.getSettingByName("NameProtect").getValBoolean()) {
								if (entity == mc.player) {
									name = "I'm Smertnix Fan";
								}
							}
							String prefix = "";
							texcolor = new Color(255, 255, 255).getRGB();
							if (AntiBot2.nobotsTimolia.contains((Object) entity) && ModuleManager.getModule(AntiBot2.class).getState()) {
								prefix = ChatFormatting.WHITE + "[" + ChatFormatting.BLUE + "ANTIBOT" + ChatFormatting.WHITE + "] ";
							} else {
								prefix = ChatFormatting.WHITE + "[" + ChatFormatting.RED + "ENEMY" + ChatFormatting.WHITE + "] ";
							}
							if (indexer.getFriends().isFriend(name)) {
								prefix = ChatFormatting.WHITE + "[" + ChatFormatting.GREEN + "FRIEND" + ChatFormatting.WHITE + "] ";
							}
							if (entity == mc.player) {
								prefix = ChatFormatting.WHITE + "[" + ChatFormatting.GREEN + "YOU" + ChatFormatting.WHITE + "] ";
							}
							Color clientColor = new Color(-1);
							String mode2 = Zamorozka.settingsManager.getSettingByName("Array Mode").getValString();
							if (mode2.equalsIgnoreCase("Test")) {
								clientColor = new Color(248, 54, 255);
							}
							if (mode2.equalsIgnoreCase("Pulse")) {
								clientColor = new Color(0, 150, 255);
							}
							if (mode2.equalsIgnoreCase("Category")) {
								clientColor = new Color(255, 255, 0);
							}
							if (mode2.equalsIgnoreCase("GreenWhite")) {
								clientColor = new Color(0, 150, 150);
							}
							if (mode2.equalsIgnoreCase("Red-Blue")) {
								clientColor = new Color(255, 25, 25);
							}
							if (mode2.equalsIgnoreCase("Grape")) {
								clientColor = new Color(155, 30, 255);
							}
							double dif = (endPosX - posX) / 2.0D;
							double textWidth = (Zamorozka.FONT_MANAGER.arraylist2.getStringWidth(prefix + name + " §7" + (int) mc.player.getDistanceToEntity(entity) + "m") * scale);
							float tagX = (float) ((posX + dif - textWidth / 2.0D) * upscale);
							float tagY = (float) (posY * upscale) - scaledHeight;
							GL11.glPushMatrix();
							GL11.glScalef(scale, scale, scale);
							if (living)
								RenderingUtils.drawRect((tagX - 2.0F), (tagY - 2.0F), tagX + textWidth * upscale + 2.0D, (tagY + 9.0F), (new Color(0, 0, 0, 140)).getRGB());
							// RenderingUtils.drawRect((tagX - 2.0F), (tagY - 2.0F), tagX - 5, (tagY +
							// 9.0F), clientColor.getRGB());
							Zamorozka.FONT_MANAGER.arraylist2.drawStringWithShadow(prefix + name + " §7" + (int) mc.player.getDistanceToEntity(entity) + "m", tagX - 1.5, tagY - 1, texcolor);
							GL11.glPopMatrix();
						}
					}
					if (living && Zamorozka.settingsManager.getSettingByName("RenderArmor").getValBoolean()) {
						if (entity instanceof EntityPlayer) {
							EntityPlayer player = (EntityPlayer) entity;
							double ydiff = (endPosY - posY) / 4;

							ItemStack stack = (player).getEquipmentInSlot(4);
							if (stack != null) {
								RenderingUtils.drawRect(endPosX + 3.5D, posY - 0.5D, endPosX + 1.5D, posY + ydiff, background);
								double diff1 = (posY + ydiff - 1) - (posY + 2);
								double percent = 1 - (double) stack.getItemDamage() / (double) stack.getMaxDamage();
								RenderingUtils.drawRect(endPosX + 3.0D, posY + ydiff, endPosX + 2.0D, posY + ydiff - 3.0D - (diff1 * percent), new Color(78, 206, 229).getRGB());

								String stackname = (stack.getDisplayName().equalsIgnoreCase("Air")) ? "0" : !(stack.getItem() instanceof ItemArmor) ? stack.getDisplayName() : stack.getMaxDamage() - stack.getItemDamage() + "";
								if (mc.player.getDistanceToEntity(player) < 20) {
									RenderingTools.renderItem(stack, (int) endPosX + 4, (int) posY + (int) ydiff - 1 - (int) (diff1 / 2) - 18);
									font1.drawStringWithShadow(stackname, (float) endPosX + 5, (float) (posY + ydiff - 1 - (diff1 / 2)) - (font1.getStringHeight(stack.getMaxDamage() - stack.getItemDamage() + "") / 2), -1);
								}
							}
							ItemStack stack2 = (player).getEquipmentInSlot(3);
							if (stack2 != null) {
								RenderingUtils.drawRect(endPosX + 3.5D, posY + ydiff, endPosX + 1.5D, posY + ydiff * 2, background);
								double diff1 = (posY + ydiff * 2) - (posY + ydiff + 2);
								double percent = 1 - (double) stack2.getItemDamage() * 1 / (double) stack2.getMaxDamage();
								RenderingUtils.drawRect(endPosX + 3.0D, (posY + ydiff * 2), endPosX + 2.0D, (posY + ydiff * 2) - 1.0D - (diff1 * percent), new Color(78, 206, 229).getRGB());

								String stackname = (stack.getDisplayName().equalsIgnoreCase("Air")) ? "0" : !(stack2.getItem() instanceof ItemArmor) ? stack2.getDisplayName() : stack2.getMaxDamage() - stack2.getItemDamage() + "";
								if (mc.player.getDistanceToEntity(player) < 20) {
									RenderingTools.renderItem(stack2, (int) endPosX + 4, (int) (posY + ydiff * 2) - (int) (diff1 / 2) - 18);
									font1.drawStringWithShadow(stackname, (float) endPosX + 5, (float) ((posY + ydiff * 2) - (diff1 / 2)) - (font1.getStringHeight(stack2.getMaxDamage() - stack2.getItemDamage() + "") / 2), -1);
								}
							}
							ItemStack stack3 = (player).getEquipmentInSlot(2);
							if (stack3 != null) {
								RenderingUtils.drawRect(endPosX + 3.5D, posY + ydiff * 2, endPosX + 1.5D, posY + ydiff * 3, background);
								double diff1 = (posY + ydiff * 3) - (posY + ydiff * 2 + 2);
								double percent = 1 - (double) stack3.getItemDamage() * 1 / (double) stack3.getMaxDamage();
								RenderingUtils.drawRect(endPosX + 3.0D, (posY + ydiff * 3), endPosX + 2.0D, (posY + ydiff * 3) - 1.0D - (diff1 * percent), new Color(78, 206, 229).getRGB());

								String stackname = (stack.getDisplayName().equalsIgnoreCase("Air")) ? "0" : !(stack3.getItem() instanceof ItemArmor) ? stack3.getDisplayName() : stack3.getMaxDamage() - stack3.getItemDamage() + "";
								if (mc.player.getDistanceToEntity(player) < 20) {
									RenderingTools.renderItem(stack3, (int) endPosX + 4, (int) (posY + ydiff * 3) - (int) (diff1 / 2) - 18);
									font1.drawStringWithShadow(stackname, (float) endPosX + 5, (float) ((posY + ydiff * 3) - (diff1 / 2)) - (font1.getStringHeight(stack3.getMaxDamage() - stack3.getItemDamage() + "") / 2), -1);
								}
							}
							ItemStack stack4 = (player).getEquipmentInSlot(1);
							if (stack4 != null) {
								RenderingUtils.drawRect(endPosX + 3.5D, posY + ydiff * 3, endPosX + 1.5D, posY + ydiff * 4 + 0.5D, background);
								double diff1 = (posY + ydiff * 4) - (posY + ydiff * 3 + 2);
								double percent = 1 - (double) stack4.getItemDamage() * 1 / (double) stack4.getMaxDamage();
								RenderingUtils.drawRect(endPosX + 3.0D, (posY + ydiff * 4), endPosX + 2.0D, (posY + ydiff * 4) - 1.0D - (diff1 * percent), new Color(78, 206, 229).getRGB());

								String stackname = (stack.getDisplayName().equalsIgnoreCase("Air")) ? "0" : !(stack4.getItem() instanceof ItemArmor) ? stack4.getDisplayName() : stack4.getMaxDamage() - stack4.getItemDamage() + "";
								if (mc.player.getDistanceToEntity(player) < 20) {
									RenderingTools.renderItem(stack4, (int) endPosX + 4, (int) (posY + ydiff * 4) - (int) (diff1 / 2) - 18);
									font1.drawStringWithShadow(stackname, (float) endPosX + 5, (float) ((posY + ydiff * 4) - (diff1 / 2)) - (font1.getStringHeight(stack4.getMaxDamage() - stack4.getItemDamage() + "") / 2), -1);
								}
							}
						}
					} else if (entity instanceof EntityItem) {
						ItemStack itemStack = ((EntityItem) entity).getEntityItem();
						if (itemStack.isItemStackDamageable()) {
							int maxDamage = itemStack.getMaxDamage();
							float itemDurability = (maxDamage - itemStack.getItemDamage());
							double durabilityWidth = (endPosX - posX) * itemDurability / maxDamage;
							// RenderingUtils.drawRect(posX - 0.5D, endPosY + 1.5D, posX - 0.5D + endPosX -
							// posX + 1.0D, endPosY + 1.5D + 2.0D, background);
							RenderingUtils.drawRect(posX, endPosY + 2.0D, posX + durabilityWidth, endPosY + 3.0D, 16777215);
						}
					}
				}
			}
		}
		GL11.glPopMatrix();
		GL11.glEnable(2929);
		GlStateManager.enableBlend();
		entityRenderer.setupOverlayRendering();
	}

	private boolean isValid(Entity entity) {
		if (mc.gameSettings.thirdPersonView == 0 && entity == mc.player)
			return false;
		if (entity.isDead)
			return false;
		if ((entity instanceof EntityItem && mc.player.getDistanceToEntity(entity) < Zamorozka.settingsManager.getSettingByName("RenderItemsRange").getValDouble()) && (Zamorozka.settingsManager.getSettingByName("RenderItems").getValBoolean()))
			return true;
		if ((entity instanceof net.minecraft.entity.passive.EntityAnimal))
			return false;
		if ((entity instanceof EntityPlayer))
			return true;
		if ((entity instanceof EntityArmorStand))
			return false;
		if ((entity instanceof IAnimals))
			return false;
		if ((entity instanceof EntityItemFrame))
			return false;
		if ((entity instanceof EntityArrow || entity instanceof EntitySpectralArrow))
			return false;
		if ((entity instanceof EntityMinecart))
			return false;
		if ((entity instanceof EntityBoat))
			return false;
		if ((entity instanceof EntityDragonFireball))
			return false;
		if ((entity instanceof EntityXPOrb))
			return false;
		if ((entity instanceof EntityMinecartChest))
			return false;
		if ((entity instanceof EntityTNTPrimed))
			return false;
		if ((entity instanceof EntityMinecartTNT))
			return false;
		if ((entity instanceof EntityVillager))
			return false;
		if ((entity instanceof EntityExpBottle))
			return false;
		if ((entity instanceof EntityLightningBolt))
			return false;
		if ((entity instanceof EntityPotion))
			return false;
		if ((entity instanceof Entity))
			return false;
		if (((entity instanceof net.minecraft.entity.monster.EntityMob || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.boss.EntityDragon
				|| entity instanceof net.minecraft.entity.monster.EntityGolem)))
			return false;
		return entity != mc.player;
	}

	@EventTarget
	public void onNameTags(EventNameTag event) {
		if (Zamorozka.settingsManager.getSettingByName("RenderTags").getValBoolean()) {
			event.setCancelled(true);
		}
	}

	private void collectEntities() {
		this.collectedEntities.clear();
		List<Entity> playerEntities = mc.world.loadedEntityList;
		for (int i = 0, playerEntitiesSize = playerEntities.size(); i < playerEntitiesSize; i++) {
			Entity entity = playerEntities.get(i);
			if (isValid(entity)) {
				this.collectedEntities.add(entity);
			}
		}
	}

	private Vector3d project2D(int scaleFactor, double x, double y, double z) {
		GL11.glGetFloat(2982, this.modelview);
		GL11.glGetFloat(2983, this.projection);
		GL11.glGetInteger(2978, this.viewport);
		if (GLU.gluProject((float) x, (float) y, (float) z, this.modelview, this.projection, this.viewport, this.vector))
			return new Vector3d((this.vector.get(0) / scaleFactor), ((Display.getHeight() - this.vector.get(1)) / scaleFactor), this.vector.get(2));
		return null;
	}
}