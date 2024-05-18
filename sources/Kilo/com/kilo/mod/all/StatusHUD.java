package com.kilo.mod.all;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.Display;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleSubOption;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.render.Draw;
import com.kilo.util.Util;

public class StatusHUD extends Module {
	
    private final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
	
	public StatusHUD(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		List<ModuleSubOption> armor = new ArrayList<ModuleSubOption>();
		armor.add(new ModuleSubOption("Durability", "Use numbers to show the durability", Interactable.TYPE.CHECKBOX, false, null, false));
		
		addOption("Armor", "Show held item and armor", Interactable.TYPE.CHECKBOX, true, null, false, armor);
		addOption("Potions", "Show potion effects", Interactable.TYPE.CHECKBOX, true, null, false);
	}
	
	public void render2D() {
		GlStateManager.enableDepth();
		boolean armor = Util.makeBoolean(getOptionValue("armor"));
		boolean duraNum = Util.makeBoolean(getSubOptionValue("armor", "durability"));
		boolean potions = Util.makeBoolean(getOptionValue("potions"));
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, -1, 0);
		GlStateManager.scale(2f, 2f, 2f);
		
		int hbw = 100*mc.gameSettings.guiScale;
		
		if (armor) {
			Draw.rectBorder(Display.getWidth()/4+(hbw/2)-2, Display.getHeight()/2-20, Display.getWidth()/4+(hbw/2)+82, Display.getHeight()/2-1, 0x88FFFFFF);
			Draw.rect(Display.getWidth()/4+(hbw/2)-1, Display.getHeight()/2-19, Display.getWidth()/4+(hbw/2)+81, Display.getHeight()/2-2, 0x88010101);
			
	        RenderHelper.enableStandardItemLighting();
			
			ItemStack item = mc.thePlayer.getCurrentEquippedItem();
			if (item != null) {
				mc.getRenderItem().renderItemAndEffectIntoGUI(item, Display.getWidth()/4+(hbw/2), Display.getHeight()/2-19);
				if (!duraNum) {
					mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, item, Display.getWidth()/4+(hbw/2), Display.getHeight()/2-19, "");
				} else {
			        GlStateManager.pushMatrix();
			        GlStateManager.scale(0.5f, 0.5f, 0.5f);
			        GlStateManager.disableLighting();
			        GlStateManager.disableDepth();
			        mc.fontRendererObj.drawString(item.getItemDamage()+"", ((Display.getWidth()/4+(hbw/2)+8)*2)-(mc.fontRendererObj.getStringWidth(item.getItemDamage()+"")/2), (Display.getHeight()/2-7)*2, -1);
			        GlStateManager.enableDepth();
			        GlStateManager.enableLighting();
			        GlStateManager.popMatrix();
				}
			}
			for(int i = 0; i <= 3; i++) {
				ItemStack piece = mc.thePlayer.getCurrentArmor(3-i);
				mc.getRenderItem().renderItemAndEffectIntoGUI(piece, Display.getWidth()/4+(hbw/2)+((i+1)*16), Display.getHeight()/2-19);
				if (!duraNum) {
					mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, piece, Display.getWidth()/4+(hbw/2)+((i+1)*16), Display.getHeight()/2-19, "");
				} else {
			        GlStateManager.pushMatrix();
			        GlStateManager.scale(0.5f, 0.5f, 0.5f);
			        GlStateManager.disableLighting();
			        GlStateManager.disableDepth();
			        mc.fontRendererObj.drawString(piece.getItemDamage()+"", ((Display.getWidth()/4+(hbw/2)+((i+1)*16)+8)*2)-(mc.fontRendererObj.getStringWidth(piece.getItemDamage()+"")/2), (Display.getHeight()/2-7)*2, -1);
			        GlStateManager.enableDepth();
			        GlStateManager.enableLighting();
			        GlStateManager.popMatrix();
				}
			}
			
	        RenderHelper.disableStandardItemLighting();
		}
		
		if (potions) {
			int var1 = (Display.getWidth()/2)-60;
	        boolean var3 = true;
	        Collection var4 = this.mc.thePlayer.getActivePotionEffects();
	        int var2 = (Display.getHeight()/2)-(var4.size()*20)-8;

	        if (!var4.isEmpty())
	        {
	            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	            GlStateManager.disableLighting();
	            int var5 = 20;

	            for (Iterator var6 = this.mc.thePlayer.getActivePotionEffects().iterator(); var6.hasNext(); var2 += var5)
	            {
	                PotionEffect var7 = (PotionEffect)var6.next();
	                Potion var8 = Potion.potionTypes[var7.getPotionID()];
	                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	                mc.getTextureManager().bindTexture(inventoryBackground);

	                if (var8.hasStatusIcon())
	                {
	                    int var9 = var8.getStatusIconIndex();
	                    this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
	                }

	                String var11 = "";//I18n.format(var8.getName(), new Object[0]);

	                if (var7.getAmplifier() == 1)
	                {
	                    var11 = var11 + " " + I18n.format("enchantment.level.2", new Object[0]);
	                }
	                else if (var7.getAmplifier() == 2)
	                {
	                    var11 = var11 + " " + I18n.format("enchantment.level.3", new Object[0]);
	                }
	                else if (var7.getAmplifier() == 3)
	                {
	                    var11 = var11 + " " + I18n.format("enchantment.level.4", new Object[0]);
	                }

	                mc.fontRendererObj.drawStringWithShadow(var11, (float)(var1-8), (float)(var2+12), 16777215);
	                String var10 = Potion.getDurationString(var7);
	                mc.fontRendererObj.drawStringWithShadow(var10, (float)(var1 + 10 + 18), (float)(var2+12), 16777215);
	            }
	        }
		}
		
		GlStateManager.popMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.disableDepth();
	}
	
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV((double)(x + 0), (double)(y + height), 0, (double)((float)(textureX + 0) * var7), (double)((float)(textureY + height) * var8));
        var10.addVertexWithUV((double)(x + width), (double)(y + height), 0, (double)((float)(textureX + width) * var7), (double)((float)(textureY + height) * var8));
        var10.addVertexWithUV((double)(x + width), (double)(y + 0), 0, (double)((float)(textureX + width) * var7), (double)((float)(textureY + 0) * var8));
        var10.addVertexWithUV((double)(x + 0), (double)(y + 0), 0, (double)((float)(textureX + 0) * var7), (double)((float)(textureY + 0) * var8));
        var9.draw();
    }
}
