package com.kilo.mod.all;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleSubOption;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.ChatUtil;
import com.kilo.util.RenderUtil;
import com.kilo.util.Util;

public class Nametags extends Module {
	
	public Nametags(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		List<ModuleSubOption> armorSubs = new ArrayList<ModuleSubOption>();
		armorSubs.add(new ModuleSubOption("Enchants", "Show item enchantments", Interactable.TYPE.CHECKBOX, true, null, false));
		addOption("Armor", "Show entities armor and held item", Interactable.TYPE.CHECKBOX, true, null, false, armorSubs);
		addOption("Health", "Show entities health in their nametag", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("State", "Show entities standing state (sneaking etc.)", Interactable.TYPE.CHECKBOX, true, null, false);
	}
	
	public List<String> getEnchantList(ItemStack stack) {
		List<String> eList = new ArrayList<String>();
		if (stack != null && stack.getEnchantmentTagList() != null) {
    		for(int j = stack.getEnchantmentTagList().tagCount()-1; j >= 0; j--) {
    			int enchantLevel = stack.getEnchantmentTagList().getCompoundTagAt(j).getInteger("lvl");
    			int enchantID = stack.getEnchantmentTagList().getCompoundTagAt(j).getInteger("id");
    			Enchantment enchant = Enchantment.getEnchantmentById(enchantID);
    			
    			if (enchant == null) {
    				continue;
    			}
    			
    	        String name = StatCollector.translateToLocal(enchant.getName());
	        	name = ChatUtil.clearFormat(name);
    	        String level = enchantLevel+"";//StatCollector.translateToLocal("enchantment.level." + enchantLevel);
    			
    			String[] parts = name.split(" ");
    			
    			String disp = "";
    			for(String s : parts) {
    				disp+= s.substring(0, 1).toUpperCase();
    			}
    			disp+= " "+level;
    			eList.add(disp);
    		}
        }
		return eList;
	}
	
	public void renderArmorESP(EntityLivingBase entity) {
        GlStateManager.depthMask(true);
		int xOff = -40;
		renderItem(entity.getHeldItem(), xOff, -18);
		for(int i = 0; i <= 3; i++) {
			ItemStack stack = entity.getCurrentArmor(3-i);
			if (stack == null) {
				continue;
			}
			renderItem(stack, xOff+((i+1)*16), -18);
		}
		mc.fontRendererObj.drawString("", (xOff), -44-(mc.fontRendererObj.FONT_HEIGHT), -1);
        GlStateManager.depthMask(false);
	}
	
	public void renderItem(ItemStack stack, int x, int y) {
		if (Util.makeBoolean(getSubOptionValue("armor", "enchants"))) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            List<String> eList = getEnchantList(stack);
    		for(String s : eList) {
    			mc.fontRendererObj.drawString(s, ((x+8)*2)-(mc.fontRendererObj.getStringWidth(s)/2), (y-7)*2-(mc.fontRendererObj.FONT_HEIGHT*eList.indexOf(s)), -1);
    		}
            GlStateManager.popMatrix();
        }
        mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, stack, x, y-2, "");
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, -150);
		mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y-4);
		GlStateManager.popMatrix();
	}
	
	public void render3DPost() {
		if (!active || mc.theWorld == null || mc.theWorld.loadedEntityList == null) { return; }

        RenderHelper.enableStandardItemLighting();
		for(int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
			Entity e = (Entity)mc.theWorld.loadedEntityList.get(i);
			
			if (e instanceof EntityLivingBase) {
				if (mc.getRenderManager().getEntityRenderObject(e) instanceof RendererLivingEntity) {
					double[] p = RenderUtil.entityRenderPos(e);
					((RendererLivingEntity)mc.getRenderManager().getEntityRenderObject(e)).passSpecialRender((EntityLivingBase)e, p[0], p[1], p[2]);
				}
			}
		}
        RenderHelper.disableStandardItemLighting();
	}
}
