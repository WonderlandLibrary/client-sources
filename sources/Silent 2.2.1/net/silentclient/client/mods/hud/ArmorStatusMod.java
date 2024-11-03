package net.silentclient.client.mods.hud;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.silentclient.client.Client;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.ModDraggable;

public class ArmorStatusMod extends ModDraggable {
	
	public ArmorStatusMod() {
		super("Armor Status", ModCategory.MODS, "silentclient/icons/mods/armorstatus.png");
	}
	
	@Override
	public void setup() {
		super.setup();
		ArrayList<String> options = new ArrayList<>();
		
		options.add("Vertical");
		options.add("Horizontal");
		
		this.addModeSetting("Orientation", this, "Vertical", options);
		this.addBooleanSetting("Font Shadow", this, true);
		this.addBooleanSetting("Show Percentage", this, true);
		this.addColorSetting("Percentage Color", this, new Color(255, 255, 255));
		this.addBooleanSetting("Fancy Font", this, false);
	}
	
	@Override
	public int getWidth() {
		boolean horizontal = Client.getInstance().getSettingsManager().getSettingByName(this, "Orientation").getValString().equals("Horizontal");
		
		if(horizontal) {
			return 64;
		}
		
		return 64 - (Client.getInstance().getSettingsManager().getSettingByName(this, "Show Percentage").getValBoolean() ? 18 : 48);
	}

	@Override
	public int getHeight() {
		boolean horizontal = Client.getInstance().getSettingsManager().getSettingByName(this, "Orientation").getValString().equals("Horizontal");
		
		if(horizontal) {
			return 16 + (Client.getInstance().getSettingsManager().getSettingByName(this, "Show Percentage").getValBoolean() ? 6 : 0);
		}
		
		return 64;
	}

	@Override
	public boolean render(ScreenPosition pos) {
		boolean horizontal = Client.getInstance().getSettingsManager().getSettingByName(this, "Orientation").getValString().equals("Horizontal");
		
		for(int i = 0; i < mc.thePlayer.inventory.armorInventory.length; i++) {
			ItemStack itemStack = mc.thePlayer.inventory.armorInventory[i];
			
			renderItemStack(pos, i, itemStack, horizontal);
		}
		return true;
	}
	
	@Override
	public void renderDummy(ScreenPosition pos) {
		boolean horizontal = Client.getInstance().getSettingsManager().getSettingByName(this, "Orientation").getValString().equals("Horizontal");
		renderItemStack(pos, 3, new ItemStack(Items.diamond_helmet), horizontal);
		renderItemStack(pos, 2, new ItemStack(Items.diamond_chestplate), horizontal);
		renderItemStack(pos, 1, new ItemStack(Items.diamond_leggings), horizontal);
		renderItemStack(pos, 0, new ItemStack(Items.diamond_boots), horizontal);
	}

	private void renderItemStack(ScreenPosition pos, int i, ItemStack is, boolean horizontal) {
		if(is == null) {
			return;
		}
		boolean fancyFont = Client.getInstance().getSettingsManager().getSettingByName(this, "Fancy Font").getValBoolean();
		CustomFontRenderer font = new CustomFontRenderer();
		
		font.setRenderMode(fancyFont ? CustomFontRenderer.RenderMode.CUSTOM : CustomFontRenderer.RenderMode.DEFAULT);
		
		boolean fontShadow = Client.getInstance().getSettingsManager().getSettingByName(this, "Font Shadow").getValBoolean();
		Color color = Client.getInstance().getSettingsManager().getSettingByName(this, "Percentage Color").getValColor();
		
		GlStateManager.pushMatrix();
		int yAdd = horizontal ? 0 : (-16 * i) + 48;
		int xAdd = horizontal ? (-16 * i) + 48 : 0;
		if(is.getItem().isDamageable() && Client.getInstance().getSettingsManager().getSettingByName(this, "Show Percentage").getValBoolean()) {
			double damage = ((is.getMaxDamage() - is.getItemDamage()) / (double) is.getMaxDamage()) * 100;
			if(horizontal) {
				GlStateManager.pushMatrix();
				GlUtils.startScale(0 + 20 + xAdd - 20, 0 + yAdd + 5 + (horizontal ? 10 : 0), 0.5F);
			}
			GlStateManager.disableLighting();
	        GlStateManager.disableDepth();
	        GlStateManager.disableBlend();
			font.drawString(Math.round(damage) + "%", 0 + 20 + xAdd - (horizontal ? 15 : 0), 0 + yAdd + 5 + (horizontal ? 13 : 0), color.getRGB(), fontShadow);
			GlStateManager.enableLighting();
	        GlStateManager.enableDepth();
	        if(horizontal) {
	        	GlUtils.stopScale();
				GlStateManager.popMatrix();
			}
		}
		GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();
		
		GlStateManager.color(1, 1, 1, 1);
		
		mc.getRenderItem().renderItemAndEffectIntoGUI(is, 0 + xAdd, 0 + yAdd);
		
		RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

}