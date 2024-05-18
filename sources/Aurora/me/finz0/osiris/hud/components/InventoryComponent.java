package me.finz0.osiris.hud.components;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class InventoryComponent extends Panel {
    public InventoryComponent(double ix, double iy, ClickGUI parent) {
        super("Inventory", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;
        resource = new ResourceLocation("textures/gui/container/shulker_box.png");;

    }




    Color c;
    ResourceLocation resource;

    public void drawHud(){
        drawInventory((int)x, (int)y);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        width = 162;
        c = new Color(50, 50, 50, 100);
        if(isHudComponentPinned) c = new Color(ColorUtil.getClickGUIColor().darker().getRed(), ColorUtil.getClickGUIColor().darker().getGreen(), ColorUtil.getClickGUIColor().darker().getBlue(), 100);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        this.height = FontUtil.getFontHeight() + 2;
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        FontUtil.drawStringWithShadow(title, x, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffffffff);

        if(extended) {
            double startY = y + height;
            drawInventory((int)x, (int)startY);
        }
    }

    public void drawInventory(int x, int y){
        //GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        //GlStateManager.disableDepth();
        mc.renderEngine.bindTexture(resource);
        GlStateManager.color(1, 1, 1, 1);
        mc.ingameGUI.drawTexturedModalRect(x, y, 7, 17, 162, 54);
        //GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        //GlStateManager.enableBlend();

        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        NonNullList<ItemStack> items = Minecraft.getMinecraft().player.inventory.mainInventory;
        for (int size = items.size(), item = 9; item < size; ++item) {
            final int slotX = x + 1 + item % 9 * 18;
            final int slotY = y + 1 + (item / 9 - 1) * 18;
            //GlStateManager.pushMatrix();
            //GlStateManager.enableDepth();
            //GlStateManager.depthMask(true);
            RenderHelper.enableGUIStandardItemLighting();
            mc.getRenderItem().renderItemAndEffectIntoGUI(items.get(item), slotX, slotY);
            mc.getRenderItem().renderItemOverlays(mc.fontRenderer, items.get(item), slotX, slotY);
            RenderHelper.disableStandardItemLighting();
            //GlStateManager.depthMask(false);
            //GlStateManager.disableDepth();
            //GlStateManager.popMatrix();
        }
    }
}
