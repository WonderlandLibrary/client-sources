package me.travis.wurstplus.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;

public final class Invdraw { 
    public void drawInventory(Integer x, Integer y, EntityPlayerSP player) {    
        
        NonNullList<ItemStack> inventory = Minecraft.getMinecraft().player.inventory.mainInventory;
       
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/gui/container/shulker_box.png"));
        GlStateManager.color(1f, 1f, 1f);
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x - 8, y + 1, 0, 0, 176, 76);
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
       
        for(int i = 9; i < inventory.size(); i++)
        {
            GlStateManager.pushMatrix();
            GlStateManager.clear(256);
           
            GlStateManager.disableDepth();
            GlStateManager.enableDepth();
           
            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
            GlStateManager.scale(1, 1, 0.01f);
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(inventory.get(i), x + i % 9 * 18, (i / 9 + 1) * 18 + y + 1 - 18);
            Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRenderer, inventory.get(i), x + i % 9 * 18, (i / 9 + 1) * 18 + y + 1 - 18);
            GlStateManager.scale(1, 1, 1f);
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.disableLighting();
            GlStateManager.scale(0.5D, 0.5D, 0.5D);
            GlStateManager.disableDepth();
           
            GlStateManager.enableDepth();
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.popMatrix();
        }
    }
}
