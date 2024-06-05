package zamorozka.modules.VISUALLY;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventNameTag;
import zamorozka.event.events.EventRender2D;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.main.Zamorozka;
import zamorozka.main.indexer;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.ZAMOROZKA.YoutuberMode;
import zamorozka.ui.Colors;
import zamorozka.ui.EntityUtil;
import zamorozka.ui.MiscUtils;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.RotUtils;
import zamorozka.ui.font.CFontRenderer;
import zamorozka.ui.font.Fonts;

public class NameTags extends Module {
	public static Map<EntityLivingBase, double[]> entityPositions = new HashMap();

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		Zamorozka.settingsManager.rSetting(new Setting("NametagsSize", this, 3.0F, 1.0F, 10.0F, true));
		Zamorozka.settingsManager.rSetting(new Setting("ItemsDur", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("NameBorder", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("Invisibles", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("Armor", this, true));
	}

	public NameTags() {
		super("NameTags", 0, Category.VISUALLY);
	}

	@EventTarget
	public void onRender3D(RenderEvent3D render) {
		try {
			updatePositions();
		} catch (Exception ignored) {
			;
		}
	}

	@EventTarget
	public void onRender2D(EventRender2D event) {
		GlStateManager.pushMatrix();
		final ScaledResolution scaledResolution = new ScaledResolution(mc);
		for (final Entity entity : entityPositions.keySet()) {
			if (entity != mc.player && Zamorozka.settingsManager.getSettingByName("Invisibles").getValBoolean() || !entity.isInvisible()) {
				GlStateManager.pushMatrix();
				if (entity instanceof EntityPlayer) {
					final double[] array = entityPositions.get(entity);
					if (array[3] < 0.0 || array[3] >= 1.0) {
						GlStateManager.popMatrix();
						continue;
					}
					final CFontRenderer wqy18 = Fonts.Tahoma18;
					GlStateManager.translate(array[0] / scaledResolution.getScaleFactor(), array[1] / scaledResolution.getScaleFactor(), 0.0);
					this.scale();
					float t = (float) Zamorozka.settingsManager.getSettingByName("NametagsSize").getValDouble();
					GlStateManager.translate(0.0, -2.5, 0.0);
					// int healthColor = Colors.getHealthColor(((EntityLivingBase)
					// entity).getHealth(), ((EntityLivingBase) entity).getMaxHealth()).getRGB();
					String string = "Health: " + Math.round(((EntityLivingBase) entity).getHealth() * 10.0f) / 20;
					String prefix = (indexer.getFriends().isFriend(((Entity) entity).getName()) ? " \247a[§f§lFRIEND\247a]" : " ");
					Object ping = " Ping: " + (int) getPing((EntityLivingBase) entity);
					String name = entity.getDisplayName().getFormattedText();
					if (ModuleManager.getModule(YoutuberMode.class).getState() && Zamorozka.settingsManager.getSettingByName("SpoofNames").getValBoolean()) {
						String newstr = "";
						char[] rdm = { 'l', 'i', 'j', '\'', ';', ':', '|' };
						for (int k = 0; k < name.length(); k++) {
							char ch = rdm[MiscUtils.randomNumber(rdm.length - 1, 0)];
							newstr = newstr.concat(ch + "");
						}
						name = newstr;
					}
					final String string4 = name + prefix;
					final float n = wqy18.getStringWidth(string4.replaceAll("Â.", ""));
					final float n2 = Fonts.comfortaa12.getStringWidth(string);
					final float n3 = ((n > n2) ? n : n2) + 10f;
					if (Zamorozka.settingsManager.getSettingByName("NameBorder").getValBoolean()) {
						RenderingUtils.drawRect(-n3 / t, -25.0f, n3 / t, 0.0f, Colors.getColor(0, 130));
					}
					final int n4 = (int) (array[0] + -n3 / t - 3.0) / 2 - 26;
					final int n5 = (int) (array[0] + n3 / t + 3.0) / 2 + 20;
					final int n6 = (int) (array[1] - 30.0) / 2;
					final int n7 = (int) (array[1] + 11.0) / 2;
					final int n8 = scaledResolution.getScaledHeight() / 2;
					final int n9 = scaledResolution.getScaledWidth() / 2;
					Zamorozka.FONT_MANAGER.chat1.drawStringWithShadow(string4, -n3 / t + 4.0f, -22.0f, new Color(255, 255, 255, 255).getRGB());
					Fonts.comfortaa16.drawString(string, -n3 / t + 4.0f, -10.0f, new Color(255, 255, 255, 255).getRGB());
					final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
					if (Zamorozka.settingsManager.getSettingByName("NameBorder").getValBoolean()) {
						RenderingUtils.drawRect(-n3 / t, -1.0f, n3 / t, 0.0f, Colors.getColor(0, 100));
						RenderingUtils.drawRect(-n3 / t, -1.0f,
								n3 / t - n3 / t * (1.0f - (float) Math.ceil(entityLivingBase.getHealth() + entityLivingBase.getAbsorptionAmount()) / (entityLivingBase.getMaxHealth() + entityLivingBase.getAbsorptionAmount())) * 2.0f, 0.0f,
								Colors.getTeamColor(entity));
					}
					/*
					 * for (Object o : ((EntityPlayer) entity).getActivePotionEffects()) {
					 * PotionEffect pot = (PotionEffect) o; String potName =
					 * StringUtils.capitalize(pot.getEffectName().substring(pot.getEffectName().
					 * lastIndexOf(".") + 1)); int XD = pot.getDuration() / 20; SimpleDateFormat df
					 * = new SimpleDateFormat("m:ss"); String time = df.format(XD * 1000);
					 * Fonts.comfortaa17.drawStringWithShadow((XD > 0 ? potName + " " + time : ""),
					 * -n3 / 2.0f + 4.0f, -22.0f, -1); }
					 */
					if (Zamorozka.settingsManager.getSettingByName("Armor").getValBoolean()) {
						final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
						for (int i = 0; i < 5; ++i) {
							final ItemStack getEquipmentInSlot = ((EntityPlayer) entity).getEquipmentInSlot(i);
							if (getEquipmentInSlot != null) {
								list.add(getEquipmentInSlot);
							}
						}
						int n10 = -(list.size() * 9);
						for (final ItemStack itemStack : list) {
							RenderHelper.enableGUIStandardItemLighting();
							mc.getRenderItem().renderItemIntoGUI(itemStack, n10 + 6, -42);
							if (Zamorozka.settingsManager.getSettingByName("ItemsDur").getValBoolean()) {
								mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, itemStack, n10, -40);
							}
							n10 += 3;
							RenderHelper.disableStandardItemLighting();
							if (itemStack != null) {
								int n11 = 21;
								int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(16), itemStack);
								int getEnchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(20), itemStack);
								int getEnchantmentLevel3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(19), itemStack);
								if (getEnchantmentLevel > 0) {
									this.drawEnchantTag("Sh" + this.getColor(getEnchantmentLevel) + getEnchantmentLevel, n10, n11);
									n11 += 6;
								}
								if (getEnchantmentLevel2 > 0) {
									this.drawEnchantTag("Fir" + this.getColor(getEnchantmentLevel2) + getEnchantmentLevel2, n10, n11);
									n11 += 6;
								}
								if (getEnchantmentLevel3 > 0) {
									this.drawEnchantTag("Kb" + this.getColor(getEnchantmentLevel3) + getEnchantmentLevel3, n10, n11);
								} else if (itemStack.getItem() instanceof ItemArmor) {
									int getEnchantmentLevel4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(0), itemStack);
									int getEnchantmentLevel5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(7), itemStack);
									int getEnchantmentLevel6 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(34), itemStack);
									if (getEnchantmentLevel4 > 0) {
										this.drawEnchantTag("P" + this.getColor(getEnchantmentLevel4) + getEnchantmentLevel4, n10, n11);
										n11 += 6;
									}
									if (getEnchantmentLevel5 > 0) {
										this.drawEnchantTag("Th" + this.getColor(getEnchantmentLevel5) + getEnchantmentLevel5, n10, n11);
										n11 += 6;
									}
									if (getEnchantmentLevel6 > 0) {
										this.drawEnchantTag("Un" + this.getColor(getEnchantmentLevel6) + getEnchantmentLevel6, n10, n11);
									}
								} else if (itemStack.getItem() instanceof ItemBow) {
									int getEnchantmentLevel7 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(48), itemStack);
									int getEnchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(49), itemStack);
									int getEnchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(50), itemStack);
									if (getEnchantmentLevel7 > 0) {
										this.drawEnchantTag("Pw" + this.getColor(getEnchantmentLevel7) + getEnchantmentLevel7, n10, n11);
										n11 += 6;
									}
									if (getEnchantmentLevel8 > 0) {
										this.drawEnchantTag("Pn" + this.getColor(getEnchantmentLevel8) + getEnchantmentLevel8, n10, n11);
										n11 += 6;
									}
									if (getEnchantmentLevel9 > 0) {
										this.drawEnchantTag("Fa" + this.getColor(getEnchantmentLevel9) + getEnchantmentLevel9, n10, n11);
									}
								} else if (itemStack.getRarity() == EnumRarity.EPIC) {
									this.drawEnchantTag("Â§6Â§lGod", n10 - 2, n11);
								}
								final int n12 = (int) Math.round(255.0 - itemStack.getItemDamage() * 255.0 / itemStack.getMaxDamage());
								new Color(255 - n12 << 16 | n12 << 8).brighter();
								final float n13 = (float) (n10 * 1.05) - 2.0f;
								if (itemStack.getMaxDamage() - itemStack.getItemDamage() > 0) {
									GlStateManager.pushMatrix();
									GlStateManager.disableDepth();
									GlStateManager.enableDepth();
									GlStateManager.popMatrix();
								}
								n10 += 12;
							}
						}
					}
				}
				GlStateManager.popMatrix();
			}
		}
		GL11.glPopMatrix();
		GlStateManager.enableBlend();
	}

	private void drawEnchantTag(final String text, int n, int n2) {
		GlStateManager.pushMatrix();
		GlStateManager.disableDepth();
		n *= (int) 1.05;
		n2 -= 6;
		CFontRenderer font = Fonts.consolas13;
		font.drawStringWithShadow(text, n + 9, -30 - n2, Colors.getColor(255));
		GlStateManager.enableDepth();
		GlStateManager.popMatrix();
	}

	private String getColor(final int n) {
		if (n != 1) {
			if (n == 2) {
				return "";
			}
			if (n == 3) {
				return "";
			}
			if (n == 4) {
				return "";
			}
			if (n >= 5) {
				return "";
			}
		}
		return "";
	}

	private void updatePositions() {
		entityPositions.clear();
		float pTicks = mc.timer.renderPartialTicks;
		for (Object o : mc.world.loadedEntityList) {
			Entity ent = (Entity) o;
			if ((ent != mc.player) && ((ent instanceof EntityPlayer))) {
				double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks - mc.getRenderManager().viewerPosX;
				double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * pTicks - mc.getRenderManager().viewerPosY;
				double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks - mc.getRenderManager().viewerPosZ;
				y += ent.height + 0.2D;
				if ((convertTo2D(x, y, z)[2] >= 0.0D) && (convertTo2D(x, y, z)[2] < 1.0D)) {
					entityPositions.put((EntityPlayer) ent, new double[] { convertTo2D(x, y, z)[0], convertTo2D(x, y, z)[1], Math.abs(convertTo2D(x, y + 1.0D, z, ent)[1] - convertTo2D(x, y, z, ent)[1]), convertTo2D(x, y, z)[2] });
				}
			}
		}
	}

	@EventTarget
	public void onNameTags(EventNameTag event) {
		event.setCancelled(true);
	}

	public float getPing(EntityLivingBase target) {
		float ping = 0;
		try {
			ping = EntityUtil.clamp(Objects.requireNonNull(mc.getConnection()).getPlayerInfo(target.getUniqueID()).getResponseTime(), 1, 1000);
		} catch (NullPointerException ignored) {
		}
		return ping;
	}

	private double[] convertTo2D(double x, double y, double z, Entity ent) {
		float pTicks = mc.timer.renderPartialTicks;
		float prevYaw = mc.player.rotationYaw;
		float prevPrevYaw = mc.player.prevRotationYaw;
		float[] rotations = RotUtils.getRotationFromPosition(ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks, ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks, ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * pTicks - 1.6D);
		mc.getRenderViewEntity().rotationYaw = (mc.getRenderViewEntity().prevRotationYaw = rotations[0]);
		mc.entityRenderer.setupCameraTransform(pTicks, 0);
		double[] convertedPoints = convertTo2D(x, y, z);
		mc.getRenderViewEntity().rotationYaw = prevYaw;
		mc.getRenderViewEntity().prevRotationYaw = prevPrevYaw;
		mc.entityRenderer.setupCameraTransform(pTicks, 0);
		return convertedPoints;
	}

	private double[] convertTo2D(double x, double y, double z) {
		FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
		IntBuffer viewport = BufferUtils.createIntBuffer(16);
		FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		GL11.glGetFloat(2982, modelView);
		GL11.glGetFloat(2983, projection);
		GL11.glGetInteger(2978, viewport);
		boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport, screenCoords);
		if (result) {
			return new double[] { screenCoords.get(0), Display.getHeight() - screenCoords.get(1), screenCoords.get(2) };
		}
		return null;
	}

	private void scale() {
		final float n = 1.0f * (mc.gameSettings.smoothCamera ? 2.0f : 1.0f);
		GlStateManager.scale(n, n, n);
	}
}
