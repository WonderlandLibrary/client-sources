package me.finz0.osiris.hud.components;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;

import java.awt.*;

public class PlayerComponent extends Panel {
    public PlayerComponent(double ix, double iy, ClickGUI parent) {
        super("Player", ix, iy, 10, 10, false, parent);
        this.isHudComponent = true;
    }




    Color c;
    float mouseXX;
    float mouseYY;


    public void drawHud(){
        mouseXX = mc.getRenderViewEntity().rotationYaw * -1;
        mouseYY = mc.getRenderViewEntity().rotationPitch * -1;
        GlStateManager.color(1, 1, 1, 1);
        drawEntityOnScreen((int)x + 21, (int)y + 60, 30, mouseXX, mouseYY, mc.player);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        c = new Color(50, 50, 50, 100);
        if(isHudComponentPinned) c = new Color(ColorUtil.getClickGUIColor().darker().getRed(), ColorUtil.getClickGUIColor().darker().getGreen(), ColorUtil.getClickGUIColor().darker().getBlue(), 100);
        if (this.dragging) {
            x = x2 + mouseX;
            y = y2 + mouseY;
        }
        width = 43;
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, c.getRGB());
        FontUtil.drawStringWithShadow(title, x, y + height / 2 - FontUtil.getFontHeight()/2f, 0xffffffff);

        if(extended) {
            double startY = y + height;
            Gui.drawRect((int) x, (int) startY, (int) x + (int) width, (int) startY + (int) height + 45, c.getRGB());
            GlStateManager.color(1, 1, 1, 1);
            GuiInventory.drawEntityOnScreen((int)x + 21, (int)y + 60, 30, (float)(x + 51) - mouseX, (float)(y + 75 - 50) - mouseY, mc.player);
        }
    }

    private void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        //ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        //ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        //ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        //ent.rotationYawHead = ent.rotationYaw;
        //ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        //ent.renderYawOffset = f;
        //ent.rotationYaw = f1;
        //ent.rotationPitch = f2;
        //ent.prevRotationYawHead = f3;
        //ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
