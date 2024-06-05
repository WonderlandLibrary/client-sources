package Focus.Beta.IMPL.Module.impl.render;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.render.EventRender2D;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.Module.impl.combat.Killaura;
import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.UTILS.GuiUtils;
import Focus.Beta.UTILS.render.RenderUtil;
import com.sun.javafx.geom.RoundRectangle2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Focus.Beta.UTILS.render.color.ColorUtils.blendColors;

public class TargetHUD extends Module{

	private static double lastP = 0.0D;
	private float animated = 20f;
	private final Dimension screenSize;
	private static double a = 0.0D;
	public Mode<Enum> modes = new Mode<>("Mode", "Mode", Modes.values(), Modes.Focus);
	
	public TargetHUD() {
		super("TargetHUD", new String[0], Type.RENDER, "Rendering target info while attacking");
		this.addValues(modes);
		this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	
	@EventHandler
	public void e(EventRender2D e) {
		EntityLivingBase target = Killaura.target;
		ScaledResolution sr = new ScaledResolution(mc);
		int n2;
		int n3;
		switch(modes.getModeAsString()) {

			case "AstolfoEdit":

				if (target != null) {

					float sw = (float) sr.getScaledWidth();
					float sh = (float) sr.getScaledHeight();
					final float y = sh / 2.0f + 35.0f;
					final float x = sw / 2.0f - 77.5f;
					final int color = HUD.color;
					//this.drawRectB(x - 1.0f, y + 2.0f, 155.0f, 57.0f, new Color(-1459157241, true));
					RenderUtil.blur2(x - 1.0f, y + 2.0f, x - 1.0f, y + 2.0f, 65.0f, 160.0f);
					this.mc.fontRendererObj.drawStringWithShadow(Killaura.target.getName(), x + 31.0f, y + 6.0f, -1);
					GL11.glPushMatrix();
					GlStateManager.translate(x, y, 1.0f);
					GL11.glScalef(2.0f, 2.0f, 2.0f);
					GlStateManager.translate(-x, -y, 1.0f);
					this.mc.fontRendererObj.drawStringWithShadow(Math.round(Killaura.target.getHealth() / 2.0f * 10.0) / 10.0 + " \u2764", x + 16.0f, y + 13.0f, new Color(color).darker().getRGB());
					GL11.glPopMatrix();
					GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
					GuiInventory.drawEntityOnScreen((int) x + 16, (int) y + 55, 25, Killaura.target.rotationYaw, -Killaura.target.rotationPitch, Killaura.target);
					final int xHealthbar = 30;
					final int yHealthbar = 46;
					final float add = 120.0f;
					this.drawRectB(x + xHealthbar, y + yHealthbar, add, 8.0f, new Color(color).darker().darker().darker());
					this.drawRectB(x + xHealthbar, y + yHealthbar, Killaura.target.getHealth() / Killaura.target.getMaxHealth() * add, 8.0f, new Color(color));
					final double addX = x + xHealthbar + Killaura.target.getHealth() / Killaura.target.getMaxHealth() * add;
					this.drawRectB((float) (addX - 3.0), y + yHealthbar, 3.0f, 8.0f, new Color(-1979711488, true));
					for (int index = 1; index < 5; ++index) {
						if (Killaura.target.getEquipmentInSlot(index) == null) {
						}
					}
				}
				break;
			case "Novoline":
				if(target != null && target instanceof EntityPlayer) {
					float var28 = target.getHealth() + target.getAbsorptionAmount();
					float var32 = target.getMaxHealth() + target.getAbsorptionAmount() - 0.05f;
					float var37 = 35 + mc.fontRendererObj.getStringWidth(target.getName()) + 40;
					float var42 = (float) (Math.round(var28 * 100.0D) / 100.0D);
					if (var42 > var32) {
						var42 *= var32 / var42;
					}

					float var46 = 100.0F / var32;
					float var48 = var42 * var46;
					float var51 = (var37 - 50.0F) / 100.0F;
					float var53 = (float) (this.screenSize.getWidth() / 4);
					float var57 = (float) (this.screenSize.getHeight() / 4);
					if ((double) var48 < lastP) {
						a = lastP - (double) var48;
					}

					lastP = (double) var48;
					if (a > 0.0D) {
						a += (0.0D - a) * 0.05000000074505806D;
					}

					a = MathHelper.clamp_double(a, 0.0D, (double) (100.0F - var48));
					RenderUtil.drawBorderedRect((double) var53, (double) ((float) var57 - 1.5F), (double) (var53 + var37 - 6.0F), (double) var57 + 37.5D, 1.0F, (new Color(0, 0, 0, 50)).getRGB(), (new Color(29, 29, 29, 255)).getRGB());
					Gui.drawRect(var53 + 1, var57, var53 + var37 - 7.0F, var57 + 36, (new Color(40, 40, 40, 255)).getRGB());


					Gui.drawRect(var53 + 40, (float) (var57 + 16.5D), var53 + 40 + 100.0F * var51, (float)(var57 + 27.3D),(new Color(0, 0, 0, 50)).getRGB());
					Gui.drawRect(var53 + 40, (float) (var57 + 16.5D), var53 + 40 + var48 * var51, (float)(var57 + 27.3D),HUD.color);
					Gui.drawRect(var53 + 40 + var48 * var51, (float) (var57 + 16.5D), (float) (var53 + 40 + var48 * var51 + a * var51), (float)(var57 + 27.3D),(new Color(HUD.color)).darker().getRGB());
					String healthString = String.format("%.1f", new Object[]{Float.valueOf(var48)}) + "%";
					mc.fontRendererObj.drawStringWithShadow(healthString , var53 + 40 + 50.0F * var51 - mc.fontRendererObj.getStringWidth(healthString) / 2, var57 + 18.0F, -1);
					FontLoaders.arial22.drawStringWithShadow(target.getName() , var53 + 40, var57 + 4, -1);

					drawFace((int)(var53 + 1.5D / 4.4D), (int)(var57 + 0.2D / 4.4D), 8.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F, (AbstractClientPlayer)target);
				}
				break;
			case "Focus":
				if(target != null){
					float sw = (float) sr.getScaledWidth();
					float sh = (float) sr.getScaledHeight();
					if (Killaura.target != null) {
						if (Killaura.target instanceof EntityPlayer) {
							String name = target.getName();
							int percent = Integer.valueOf(((int) Killaura.target.getHealth()) / 2);
							String uni = "";
							String healthColor = "";
							uni = "\u2764 ";
							healthColor = "\2477";
							float xSpeed = 133f / (Minecraft.getDebugFPS() * 1.05f);
							float desiredWidth = ((FontLoaders.Comfortaa18.getStringWidth(name) + 45) / Killaura.target.getMaxHealth()) * Math.min(Killaura.target.getHealth(), Killaura.target.getMaxHealth());
							if (desiredWidth < animated || desiredWidth > animated) {
								if (Math.abs(desiredWidth - animated) <= xSpeed) {
									animated = desiredWidth;
								} else {
									animated += (animated < desiredWidth ? xSpeed * 3 : -xSpeed);
								}
							}
							GuiUtils.drawRoundedRect(sw / 2 + 10, sh / 2 - 30, (FontLoaders.Comfortaa18.getStringWidth(name) - 35) + sw / 2 + 110, sh / 2 + 20, 0x90000000, 0x90000000);
							drawFace((int) sw / 2 + 12, (int) sh / 2 - 28, 8, 8, 8, 8, 28, 28, 64, 64, (AbstractClientPlayer) target);
							Minecraft.getMinecraft().fontRendererObj.drawString(uni, (int) sw / 2 + 12, (int) sh / 2, new Color(244, 102, 101).getRGB());
							GuiUtils.drawRoundedRect1(sw / 2 + 21, sh / 2 + 3.5f, (FontLoaders.Comfortaa18.getStringWidth(name) + 45), 0.1F, 0x900000, 0x90000000);
							GuiUtils.drawRoundedRect1(sw / 2 + 21, sh / 2 + 3.5f, animated, 0.1F, 0x900000, getHealthColorTest(target).getRGB());
							FontLoaders.Comfortaa18.drawString(name, sw / 2 + 45, sh / 2 - 20, -1);
							FontLoaders.Tahoma12.drawString("Health: " + String.format("%.1f", target.getHealth()), sw / 2 + 20, sh / 2 + 10, -1);
						}
					}
				break;
				}
		}
	}


	public void drawFace(double x, double y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
		try {
			ResourceLocation skin = target.getLocationSkin();
			Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1, 1, 1, 1);
			Gui.drawScaledCustomSizeModalRect((int)x, (int)y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
			GL11.glDisable(GL11.GL_BLEND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Color getHealthColorTest(EntityLivingBase entityLivingBase) {
		float health = entityLivingBase.getHealth();
		float[] fractions = new float[]{0.0F, 0.15f, .55F, 0.7f, .9f};
		Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
		float progress = health / entityLivingBase.getMaxHealth();
		return health >= 0.0f ? blendColors(fractions, colors, progress).brighter() : colors[0];
	}

	public void drawRectB(final float x, final float y, final float w, final float h, final Color color) {
		Gui.drawRect(x, y, x + w, y + h, color.getRGB());
	}

	enum Modes{
		Focus, AstolfoEdit
	}

}
