package me.protocol_client.ui.hud;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.ui.click.Protocol.Elements.Element;
import me.protocol_client.ui.click.Protocol.Panel.Panel;
import me.protocol_client.utils.RenderUtils2D;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import darkmagician6.EventManager;
import events.EventRender2D;

public class ProtocolHUD extends GuiScreen {

	public ProtocolHUD() {
		this.mc = Minecraft.getMinecraft();
	}

	static int						xbps;
	static int						zbps;
	boolean							display	= true;
	int								timer	= 0;
	int								var2	= 0xFF00FFFF;
	private static int				pY		= 0;
	int								number;
	int								count	= 0;
	public int						top;
	public boolean					goup;
	public int						bottom;
	public static ArrayList<Module>	toggled	= new ArrayList<Module>();

	public void renderScreen() {
		if (!goup) {
			top++;
			bottom = top + 9;
		}
		if (goup) {
			top = bottom - 9;
			bottom--;
		}
		if (Wrapper.mc().gameSettings.showDebugInfo) {
			return;
		}
		EventRender2D event = new EventRender2D();
		EventManager.call(event);
		if (toggled != null) {
			reorderMods();
		}
		Protocol.getTabGui().drawTabGui();
		// Gui.drawString(Wrapper.bf(), Protocol.irc.getUsers("#Innocent").length + "", 2, 100, -1);
		drawArmor(RenderUtils2D.newScaledResolution());
		int var4 = 25;
        int var5 = 160;
        drawEntityOnScreen(25, 160, 30, -40, 0, this.mc.thePlayer);
		int count1 = 0;
		int count2 = 0;
		int x = ScaledResolution.getScaledWidth();
		for (Module mod : Protocol.getModules()) {
			if (!toggled.contains(mod)) {
				toggled.add(mod);
			}
		}
		String text = Protocol.primColor;
		Color nigger = new Color(255, 0, 255);
		for (int i = 0; i < text.length(); i++) {
			if ((Protocol.primColor.charAt(i) == '§') && (i + 1 < Protocol.primColor.length())) {
				char oneMore = Character.toLowerCase(text.charAt(i + 1));
				int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
				if (colorCode < 16) {
					try {
						int newColor = Minecraft.getMinecraft().fontRendererObj.colorCode[colorCode];
						nigger = new Color((newColor >> 16) / 255.0F, (newColor >> 8 & 0xFF) / 255.0F, (newColor & 0xFF) / 255.0F);
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}
		}
		if (toggled != null) {
			int linesColor = 553648127;
			int bodyColor = -1728053248;
			for (Module module : toggled) {
				if (module.isToggled() && module.shouldShow()) {
					if (Protocol.colors.rainbow) {
						module.setColor(Protocol.getColor().getRGB());
					}
					int y = 0 + (10 * count1);
					String name = module.getDisplayName.replace("[", "§8- §7").replace("]", "");
					//Gui.drawRect(x - Wrapper.fr().getStringWidth(name) - 5, y - 1, x, y + 9, 0xbb000000);
					Gui.drawString(Wrapper.bf(), name, x - Wrapper.fr().getStringWidth(name) - 1, y, module.getColor());
					count1++;
				}
			}
//			Gui.drawRect(x - 3, top, x, bottom, nigger.getRGB());
		}
		ScaledResolution var13 = RenderUtils2D.newScaledResolution();

		int var1 = 1;
		int var2 = 1;
		Collection c = Wrapper.getPlayer().getActivePotionEffects();
		if (!c.isEmpty()) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			int var6 = -10;

			for (Iterator i = Wrapper.getPlayer().getActivePotionEffects().iterator(); i.hasNext(); var2 += var6) {
				PotionEffect pe = (PotionEffect) i.next();
				Potion var9 = Potion.potionTypes[pe.getPotionID()];
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				if (var9.hasStatusIcon()) {
					int var10 = var9.getStatusIconIndex();
				}

				String var12 = StatCollector.translateToLocal(var9.getName());

				if (pe.getAmplifier() == 1)
					var12 = var12 + " II";
				else if (pe.getAmplifier() == 2)
					var12 = var12 + " III";
				else if (pe.getAmplifier() == 3)
					var12 = var12 + " IV";

				int var14 = var13.getScaledWidth() - Wrapper.fr().getStringWidth(var12) / 2 - 21;
				String var11 = Potion.getDurationString(pe);
				String name = var12 + " §8(§7" + var11 + "§8)";
				Gui.drawString(Wrapper.fr(), name, RenderUtils2D.newScaledResolution().getScaledWidth() - Wrapper.fr().getStringWidth(name), var2 + var13.getScaledHeight() - (Wrapper.mc().currentScreen instanceof GuiChat ? 24 : 10), var9.getLiquidColor());
			
			}
		}
		if (!goup) {
			if (bottom >= (10 * count1)) {
				goup = true;
			}
		}
		if (goup) {
			if (top <= 0) {
				goup = false;
			}
		}
	}

	public static void drawSmallString(String s, int x, int y, int co) {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		Gui.drawString(Wrapper.fr(), s, x * 2, y * 2, co);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	private int getLongestModWidth() {
		int longest = 0;
		for (Module mod : toggled) {
			if (mod.isToggled()) {
				String name = Protocol.secColor + mod.getDisplayName.replace("[", "(" + Protocol.primColor).replace("]", Protocol.secColor + ")");
				if (Wrapper.fr().getStringWidth(mod.getName()) >= longest) {
					longest = Wrapper.fr().getStringWidth(name);
				}
			}
		}
		return longest;
	}

	public int getColorforModule(Module mod) {
		if (mod.getCategory() == Category.MOVEMENT) {
			return 14520643;
		}
		if (mod.getCategory() == Category.MISC) {
			return 10892876;
		}
		if (mod.getCategory() == Category.COMBAT) {
			return 14341419;
		}
		if (mod.getCategory() == Category.PLAYER) {
			return 13750981;
		}
		if (mod.getCategory() == Category.RENDER) {
			return 12659246;
		}
		if (mod.getCategory() == Category.WORLD) {
			return 3575182;
		}
		return -1;
	}

	public static void reorderMods() {
		ArrayList<Module> newList = toggled;

		Collections.sort(newList, new Comparator<Module>() {
			public int compare(Module mod, Module mod1) {
				if (Wrapper.fr().getStringWidth(mod.getDisplayName()) > Wrapper.fr().getStringWidth(mod1.getDisplayName())) {
					return -1;
				}
				if (Wrapper.fr().getStringWidth(mod.getDisplayName()) < Wrapper.fr().getStringWidth(mod1.getDisplayName())) {
					return 1;
				}
				return 0;
			}
		});
		toggled = newList;
	}

	private String getTime() {
		SimpleDateFormat date = new SimpleDateFormat("h:mm a");
		return date.format(new Date());
	}

	private void drawArmor(ScaledResolution scaledResolution) {
		int var25 = 15;
		for (final ItemStack var5 : Wrapper.getPlayer().inventory.armorInventory) {
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glPushMatrix();
			mc.getRenderItem().func_180450_b(var5, scaledResolution.getScaledWidth() / 2 + 90, scaledResolution.getScaledHeight() - ((Wrapper.getPlayer().isInsideOfMaterial(Material.water) && Wrapper.getPlayer().getAbsorptionAmount() <= 0.0f) ? var25 + 10 : var25));
			mc.getRenderItem().func_175030_a(Wrapper.fr(), var5, scaledResolution.getScaledWidth() / 2 + 90, scaledResolution.getScaledHeight() - ((Wrapper.getPlayer().isInsideOfMaterial(Material.water) && Wrapper.getPlayer().getAbsorptionAmount() <= 0.0f) ? var25 + 10 : var25));
			if (var5 != null) {
				Gui.drawString(Wrapper.fr(), (var5.getMaxDamage() - var5.getItemDamage()) + "", scaledResolution.getScaledWidth() / 2 + 107, scaledResolution.getScaledHeight() - ((Wrapper.getPlayer().isInsideOfMaterial(Material.water) && Wrapper.getPlayer().getAbsorptionAmount() <= 0.0f) ? var25 + 6 : var25 - 4), -1);
			}
			GL11.glPopMatrix();
			RenderHelper.disableStandardItemLighting();
			var25 += 14;
		}
	}
    public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)p_147046_0_, (float)p_147046_1_, 50.0F);
        GlStateManager.scale((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float var6 = p_147046_5_.renderYawOffset;
        float var7 = p_147046_5_.rotationYaw;
        float var8 = p_147046_5_.rotationPitch;
        float var9 = p_147046_5_.prevRotationYawHead;
        float var10 = p_147046_5_.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        p_147046_5_.renderYawOffset = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 20.0F;
        p_147046_5_.rotationYaw = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 40.0F;
        p_147046_5_.rotationPitch = -((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
        var11.func_178631_a(180.0F);
        var11.func_178633_a(false);
        var11.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        var11.func_178633_a(true);
        p_147046_5_.renderYawOffset = var6;
        p_147046_5_.rotationYaw = var7;
        p_147046_5_.rotationPitch = var8;
        p_147046_5_.prevRotationYawHead = var9;
        p_147046_5_.rotationYawHead = var10;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.func_179090_x();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}


//enchantment.damage.all=§4Sharpness
//enchantment.damage.undead=§3Smite
//enchantment.damage.arthropods=§3Bane of Arthropods
//enchantment.knockback=§7Knockback
//enchantment.fire=§6Fire Aspect
//enchantment.protect.all=§9Protection
//enchantment.protect.fire=§cFire Protection
//enchantment.protect.fall=§eFeather Falling
//enchantment.protect.explosion=§8Blast Protection
//enchantment.protect.projectile=§7Projectile Protection
//enchantment.oxygen=§1Respiration
//enchantment.waterWorker=§bAqua Affinity
//enchantment.digging=§aEfficiency
//enchantment.untouching=§6Silk Touch
//enchantment.durability=§9Unbreaking
//enchantment.lootBonus=§eLooting
//enchantment.lootBonusDigger=§eFortune
//enchantment.arrowDamage=§4Power
//enchantment.arrowFire=§6Flame
//enchantment.arrowKnockback=§7Punch
//enchantment.arrowInfinite=§5Infinity
//enchantment.thorns=§2Thorns


