package net.silentclient.client.mods.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.ModDraggable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class PotionHudMod extends ModDraggable {
	
	public PotionHudMod() {
		super("Potion Hud", ModCategory.MODS, "silentclient/icons/mods/potionhud.png");
	}
	
	@Override
	public void setup() {
		super.setup();
		this.addBooleanSetting("Font Shadow", this, true);
		this.addBooleanSetting("Fancy Font", this, false);
		this.addBooleanSetting("Show Potion Name", this, true);
		this.addColorSetting("Potion Name Color", this, new Color(255, 255, 255));
		this.addBooleanSetting("Show Duration", this, true);
		this.addColorSetting("Duration Color", this, new Color(156, 157, 151));
		this.addBooleanSetting("Potions In Inventory", this, true);
	}
	
	private int maxWidth = 0;
	private int height = 0;
	
	@Override
	public int getWidth() {
		boolean name = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Potion Name").getValBoolean();
		boolean duration = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Duration").getValBoolean();
		if(!name && !duration) {
			return 20;
		}
		return 18 + maxWidth + 2;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean render(ScreenPosition pos) {
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();
		
		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);
		boolean fontShadow = Client.getInstance().getSettingsManager().getSettingByName(this, "Font Shadow").getValBoolean();
		boolean name = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Potion Name").getValBoolean();
		Color nameColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Potion Name Color").getValColor();
		boolean duration = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Duration").getValBoolean();
		Color durationColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Duration Color").getValColor();
		int j = 0;
        this.maxWidth = 0;
        Collection<PotionEffect> collection = this.mc.thePlayer.getActivePotionEffects();
        if (!collection.isEmpty()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            int k = 18;
            for (PotionEffect potioneffect : this.mc.thePlayer.getActivePotionEffects()) {
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                String s = EnumChatFormatting.getTextWithoutFormattingCodes(I18n.format(potion.getName(), new Object[0]));
                
                
                if (potioneffect.getAmplifier() == 0) {
                    s = s + "";
                } else if (potioneffect.getAmplifier() == 1) {
                    s = s + " II";
                } else if (potioneffect.getAmplifier() == 2) {
                    s = s + " III";
                } else if (potioneffect.getAmplifier() == 3) {
                    s = s + " IV";
                } else if (potioneffect.getAmplifier() == 4) {
                    s = s + " V";
                }
                if(font.getStringWidth(s) > this.maxWidth) {
                	this.maxWidth = font.getStringWidth(s);
                }
                if(name) {
                	font.drawString(s, 0 + 20, (0 + j + (fancyFont ? 2 : 0) + (duration ? 5 : ((18 / 2) + ((fancyFont ? 11 : Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT))))), nameColor.getRGB(), fontShadow);
                }
                String s1 = Potion.getDurationString(potioneffect);
                if(!name && duration && font.getStringWidth(s1) > this.maxWidth) {
                	this.maxWidth = font.getStringWidth(s1) + 2;
                }
                
                if(duration) {
                	font.drawString(s1, 0 + 20, (0 + j + (name ? (fancyFont ? 11 : Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT) + 5 : ((18 / 2) + ((fancyFont ? 9 : Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT) / 3)))), durationColor.getRGB(), fontShadow);
                }
                
                mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                if (potion.hasStatusIcon()) {
                    int i1 = potion.getStatusIconIndex();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    new Gui().drawTexturedModalRect(0, 0 + j + (5), 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                }
                j += k + 5;
            }
            this.height = j;
        }
		
		return true;
	}
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		Collection<PotionEffect> effects = this.mc.thePlayer.getActivePotionEffects();
        if (!effects.isEmpty()) {
        	this.render(pos);
        	return;
        }
        boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();
		
		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);
        boolean fontShadow = Client.getInstance().getSettingsManager().getSettingByName(this, "Font Shadow").getValBoolean();
		boolean name = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Potion Name").getValBoolean();
		Color nameColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Potion Name Color").getValColor();
		boolean duration = Client.getInstance().getSettingsManager().getSettingByName(this, "Show Duration").getValBoolean();
		Color durationColor = Client.getInstance().getSettingsManager().getSettingByName(this, "Duration Color").getValColor();
		int j = 0;
		ArrayList<Potion> collection = new ArrayList<Potion>();
		collection.add(Potion.potionTypes[1]);
		collection.add(Potion.potionTypes[2]);
		collection.add(Potion.potionTypes[3]);
		collection.add(Potion.potionTypes[4]);
		collection.add(Potion.potionTypes[5]);
        if (!collection.isEmpty()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            int k = 18;
            if (collection.size() > 5) {
                k = 132 / (collection.size() - 1);
            }
            for (Potion potion : collection) {
            	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                String s = EnumChatFormatting.getTextWithoutFormattingCodes(I18n.format(potion.getName(), new Object[0]));
                
                if(font.getStringWidth(s) > this.maxWidth) {
                	this.maxWidth = font.getStringWidth(s);
                }
                if(name) {
                	font.drawString(s, 0 + 20, (0 + j + (duration ? 5 : ((18 / 2) + ((fancyFont ? 11 : Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT) / 3)))), nameColor.getRGB(), fontShadow);
                }
                String s1 = "00:00";
                if(!name && duration && font.getStringWidth(s1) > this.maxWidth) {
                	this.maxWidth = font.getStringWidth(s1) + 2;
                }
                
                if(duration) {
                	font.drawString(s1, 0 + 20, (0 + j + (name ? (fancyFont ? 11 : Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT) + 5 : ((18 / 2) + (12 / 3)))), durationColor.getRGB(), fontShadow);
                }
                
                mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                if (potion.hasStatusIcon()) {
                    int i1 = potion.getStatusIconIndex();
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    new Gui().drawTexturedModalRect(0, 0 + j + (5), 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                }
                j += k + 5;
            }
            this.height = j;
        }
	}

}
