package me.xatzdevelopments.modules.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityBoat;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventAttack;
import me.xatzdevelopments.events.listeners.EventRenderGUI;
import me.xatzdevelopments.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import me.xatzdevelopments.util.ColorUtil;

public class TargetHUD extends Module
{
    Entity target;
    float posX;
    float mcHeight;
    float mcWidth;
    float offSetWidth;
    float offSetHeight;
    double health;
    String Distance;
    FontRenderer fr;
    
    public TargetHUD() {
        super("TargetHud", 0, Category.RENDER, null);
        this.posX = 400.0f;
        this.mcHeight = (float)Minecraft.displayHeight;
        this.mcWidth = (float)Minecraft.displayWidth;
        this.offSetWidth = this.mcWidth / 2.0f + 46.0f;
        this.offSetHeight = this.mcHeight / 2.0f + 40.0f;
        this.health = 0.0;
        this.Distance = "";
        this.fr = Minecraft.getMinecraft().fontRendererObj;
    }
    
    @Override
    public void onEnable() {
        this.target = null;
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventAttack) {
            this.target = ((EventAttack)e).entity;
        }
        if (e instanceof EventRenderGUI) {
            if (this.target == null || this.target.isDead || this.target instanceof EntityBoat || this.target instanceof EntityItemFrame) {
                return;
            }
            String targetOnGround;
            if (this.target.onGround) {
                targetOnGround = "OnGround";
            }
            else {
                targetOnGround = "OffGround";
            }
            this.health = (this.target.isDead ? 0 : Math.round(((EntityLivingBase)this.target).getHealth()));
            this.Distance = Long.toString(Math.round(this.target.getDistance(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ)));
            if (Integer.parseInt(this.Distance) > 15) {
                return;
            }
            String Winning = "";
            if (this.health > this.mc.thePlayer.getHealth()) {
                Winning = "Losing";
            }
            else if (this.health < this.mc.thePlayer.getHealth()) {
                Winning = "Winning";
            }
            else if (this.health == this.mc.thePlayer.getHealth()) {
                Winning = "Tying";
            }
            Gui.drawRect(this.offSetWidth, this.offSetHeight, this.mcWidth / 2.0f + 200.0f, this.mcHeight / 2.0f + 100.0f, -1073741824);
            for (int i = 1; i < 3; ++i) {
                Gui.drawRect(this.offSetWidth - 1 * i, this.offSetHeight - 1 * i, this.mcWidth / 2.0f + 200.0f + 1 * i, this.mcHeight / 2.0f + 100.0f + 1 * i, 436207616);
            }
            this.fr.drawString(this.target.getName(), this.offSetWidth + 5.0f, this.offSetHeight + 5.0f, ColorUtil.getRainbow());
            this.drawEntityOnScreen(this.offSetWidth + 14.0f, this.offSetHeight + 55.0f, 20.3f, this.target.rotationYaw, this.target.rotationPitch, this.target);
            Gui.drawRect(this.offSetWidth + 33.0f, this.offSetHeight + 50.0f, this.offSetWidth + 33.0f + ((this.health > 20.0) ? 20.0 : this.health) * 5.8, this.offSetHeight + 4.0f + 50.0f, ColorUtil.getRainbow());
            if (Integer.parseInt(this.Distance) > 9) {
                this.Distance = "+9";
            }
            this.fr.drawString(String.valueOf(this.Distance) + "  |  " + targetOnGround + "  |  Hurt " + ((EntityLivingBase)this.target).hurtTime, this.offSetWidth + 33.0f, this.offSetHeight + 18.0f, -1);
            this.fr.drawString(Winning, this.offSetWidth + 33.0f, this.offSetHeight + 30.0f, -1);
        }
    }
    
    private void drawEntityOnScreen(final float posX, final float posY, final float scale, final float mouseX, final float mouseY, final Entity ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, 50.0);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0, 0.0, 0.0);
        final RenderManager rendermanager = this.mc.getRenderManager();
        rendermanager.renderEntityWithPosYaw(ent, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
