package com.krazzzzymonkey.catalyst.module.modules.render;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.security.MessageDigest;
import com.krazzzzymonkey.catalyst.module.ModuleCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.client.renderer.RenderHelper;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import com.krazzzzymonkey.catalyst.module.Modules;

public class InventoryViewer extends Modules
{

    private static ResourceLocation box;
    Minecraft mc;
    
    private static void preboxrender() {
        GL11.glPushMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.clear(256);
        GlStateManager.enableBlend();
    }

    
    private static void postboxrender() {
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
        GL11.glPopMatrix();
    }

    
    private static void postitemrender() {
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }
    
    private void boxrender(final int int1, final int int2) {
        final String whiteinv = "WhiteInventory";
        final String blackinv = "BlackInventory";
        final String transpinv = "TransparentInventory";
        preboxrender();
        InventoryViewer.box = new ResourceLocation("catalyst", "textures/gui/invblack.png");
        this.mc.renderEngine.bindTexture(InventoryViewer.box);
        this.mc.ingameGUI.drawTexturedModalRect(int1, int2, 0, 0, 176, 78);
        postboxrender();
    }
    
    private static void preitemrender() {
        GL11.glPushMatrix();
        GL11.glDepthMask((boolean)(1 != 0));
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
    }
    
    public void onRender() {
        final NonNullList<ItemStack> itemStack = (NonNullList<ItemStack>)Minecraft.getMinecraft().player.inventory.mainInventory;
        this.boxrender(10, 10);
        this.itemrender(itemStack, 10, 10);
    }

    public InventoryViewer() {
        super("InvPreview", ModuleCategory.RENDER);
        this.mc = Minecraft.getMinecraft();
    }

    private void itemrender(final NonNullList<ItemStack> itemStack, final int int1, final int int2) {
        final int stackSize = itemStack.size();
        int int3 = 9;
        while (int3 < stackSize) {
            final int int4 = int1 + 8 + int3 % 9 * 18;
            final int int5 = int2 + 18 + (int3 / 9 - 1) * 18;
            preitemrender();
            this.mc.getRenderItem().renderItemAndEffectIntoGUI((ItemStack)itemStack.get(int3), int4, int5);
            this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, (ItemStack)itemStack.get(int3), int4, int5);
            postitemrender();
            ++int3;
        }
    }
    
    private enum Box 
    {


        /*
         * alright my decompiler fucked up here i guess not sure
         * i feel like code is missing and unfinished 
         * invpreview souce is public anyways 
         */


        WhiteInventory; // (not sure what this is)
        TransparentInventory, // (not sure what this is)
        BlackInventory; // (not sure what this is)
        
        static {
            final Box[] $values = new Box[3];
            $values[0] = Box.WhiteInventory; // ?
            $values[1] = Box.BlackInventory;
            $values[2] = Box.TransparentInventory;
            $VALUES = $values;
        }
        
    }
}
