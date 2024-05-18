package com.darkcart.xdolf.gui;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.clickgui.XdolfGuiClick;
import com.darkcart.xdolf.clickgui.elements.XdolfWindow;
import com.darkcart.xdolf.fonts.Fonts;
import com.darkcart.xdolf.mods.Hacks;
import com.google.common.collect.Ordering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class XDolfOverlay extends GuiIngame {
	
	public static boolean hideGui = false;
	
	public XDolfOverlay(Minecraft mcIn) {
		super(mcIn);
	}

	public void renderGameOverlay(float partialTicks) {
		super.renderGameOverlay(partialTicks);
		renderXDolfOverlay();
	}

	// Color format: argb: 0x[aarrggbb]
	// 00 = completely transparent, ff = opaque

	public void renderXDolfOverlay() {

		if(Wrapper.getMinecraft().gameSettings.showDebugInfo || hideGui || Wrapper.getMinecraft().currentScreen instanceof GuiChat) return;
		
		int width = Client.gameResolution.getScaledWidth();
		int height = Client.gameResolution.getScaledHeight();

		Fonts.roboto18.drawStringWithShadow("Xdolf", 2, 0, 0x55FF55);
		int count = 0;
		try {
			for(Module mod: Hacks.display) {
				int x2 = width - (Fonts.roboto18.getStringWidth(mod.getName()));
				int y = (10 * count);
				Fonts.roboto18.drawStringWithShadow(mod.getName(), x2 - 2, y, 0xffffff);
				count++;
			}
		} catch (Exception ex) {}


		Collection<PotionEffect> var4 = Wrapper.getPlayer().getActivePotionEffects();

		if (!var4.isEmpty()) {
			final ResourceLocation var5 = new ResourceLocation("textures/gui/container/inventory.png");
			int var6 = -23;
			int count2 = 0;

			for (PotionEffect potioneffect : Ordering.natural().sortedCopy(var4)) {
				count2++;
				Potion var9 = potioneffect.getPotion();
				Wrapper.getMinecraft().renderEngine.bindTexture(var5);

				String s1 = I18n.format(var9.getName(), new Object[0]);

				if (potioneffect.getAmplifier() == 1) {
					s1 = s1 + " II";
				} else if (potioneffect.getAmplifier() == 2) {
					s1 = s1 + " III";
				} else if (potioneffect.getAmplifier() == 3) {
					s1 = s1 + " IV";
				}

				String var11 = Potion.getPotionDurationString(potioneffect, 1.0F);
				int var14 = width -Fonts.roboto18.getStringWidth(s1) - 2 - 20;
				int var16 = width - (Fonts.roboto18.getStringWidth(var11) / 2) - 4 - 20;

				if (var9.hasStatusIcon()) {
					int var10 = var9.getStatusIconIndex();
					this.drawTexturedModalRect(width - 20, height - (count2 * 20), 0 + var10 % 8 * 18,
							198 + var10 / 8 * 18, 18, 18);
				}

				Fonts.roboto18.drawStringWithShadow(s1, var14, height - (count2 * 20),
						16777215);
				var14 = width - Fonts.roboto18.getStringWidth(var11) - 2;
				Fonts.roboto18.drawStringWithShadow(var11, (var16) - 8,
						height + 10 - (count2 * 20), 8355711);
			}
		}
		
		for(XdolfWindow window: XdolfGuiClick.windowList) {
			if(!(Wrapper.getMinecraft().currentScreen instanceof XdolfGuiClick)) {
				if(window.isPinned()) {
					window.draw(0, 0);
				}
			}
		}
	}
}
