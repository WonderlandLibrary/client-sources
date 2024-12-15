package com.alan.clients.module.impl.render.targetinfo;

import com.alan.clients.component.impl.player.IRCInfoComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.impl.render.TargetInfo;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ExhibitionTargetInfo extends Mode<TargetInfo> {

    public ExhibitionTargetInfo(String name, TargetInfo parent) {
        super(name, parent);
    }

    private TargetInfo targetInfoModule;

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (this.targetInfoModule == null) {
            this.targetInfoModule = this.getModule(TargetInfo.class);
        }

        ///////////////////////////////////////////////////

        Entity target = this.targetInfoModule.target;
        boolean out = (!this.targetInfoModule.inWorld || this.targetInfoModule.stopwatch.finished(1000));
        if (target == null || out) return;
        String normalName = target.getCommandSenderName();
        String name = IRCInfoComponent.formatNick(normalName, normalName);
        double x = this.targetInfoModule.position.x;
        double y = this.targetInfoModule.position.y;

        ///////////////////////////////////////////////////

        RenderUtil.rectangle(x, y, 140, 50, new Color(0, 0, 0)); // rect 1
        RenderUtil.rectangle(x + 0.5, y + 0.5, 139, 49, new Color(60, 60, 60));// rect 2
        RenderUtil.rectangle(x + 1.5, y + 1.5, 137, 47, new Color(0, 0, 0)); // rect 3
        RenderUtil.rectangle(x + 2, y + 2, 136, 46, new Color(25, 25, 24)); // rect 4

        ///////////////////////////////////////////////////

        mc.fontRendererObj.draw(name, x + 40, y + 6, Color.WHITE.getRGB()); //drawing name obviously

        ///////////////////////////////////////////////////

        GlStateManager.pushMatrix(); // gay shit
        GlStateManager.scale(0.7, 0.7, 0.7); //scaling text
        mc.fontRendererObj.draw("HP: " + Math.round(((AbstractClientPlayer) target).getHealth()) + " | Dist: " + Math.round(mc.thePlayer.getDistanceToEntity(target)), (x + 40) * (1 / 0.7), (y + 17) * (1 / 0.7), Color.WHITE.getRGB()); //drawing said scaled text
        GlStateManager.popMatrix(); // more gay shit

        ///////////////////////////////////////////////////

        this.targetInfoModule.positionValue.scale = new Vector2d(140, 50); // extra gay shit (draggable stuff)
        double health = Math.min(!this.targetInfoModule.inWorld ? 0 : MathUtil.round(((AbstractClientPlayer) target).getHealth(), 1), ((AbstractClientPlayer) target).getMaxHealth()); //health calculations

        ///////////////////////////////////////////////////

        Color healthColor = getColor((AbstractClientPlayer) target); //getting le color

        ///////////////////////////////////////////////////

        double x2 = x + 40; // new "x" variable for loop
        RenderUtil.rectangle(x2, y + 25, 100 - 9, 5, ColorUtil.withAlpha(healthColor, 50)); //static healthbar rendered below healthbar
        RenderUtil.rectangle(x2, y + 25, (100 - 9) * (health / ((AbstractClientPlayer) target).getMaxHealth()), 6, healthColor); //actual functioning healthbar
        RenderUtil.rectangle(x2, y + 25, 91, 1, Color.BLACK); // top healthbar outline
        RenderUtil.rectangle(x2, y + 30, 91, 1, Color.BLACK); // bottom healthbar outline

        ///////////////////////////////////////////////////

        for (int i = 0; i < 10; i++) {
            RenderUtil.rectangle(x2 + 10 * i, y + 25, 1, 6, Color.BLACK); //so i don't need to render 10 rectangles (messy code)
        }

        ///////////////////////////////////////////////////

        RenderUtil.renderItemIcon(x2, y + 31, -1, ((AbstractClientPlayer) target).getHeldItem()); //rendering targets held item
        RenderUtil.renderItemIcon(x2 + 15, y + 31, -1, ((AbstractClientPlayer) target).getEquipmentInSlot(4)); //rendering targets helmet
        RenderUtil.renderItemIcon(x2 + 30, y + 31, -1, ((AbstractClientPlayer) target).getEquipmentInSlot(3)); //rendering targets chestplate
        RenderUtil.renderItemIcon(x2 + 45, y + 31, -1, ((AbstractClientPlayer) target).getEquipmentInSlot(2)); //rendering targets leggings
        RenderUtil.renderItemIcon(x2 + 60, y + 31, -1, ((AbstractClientPlayer) target).getEquipmentInSlot(1)); //rendering targets boots

        ///////////////////////////////////////////////////

        GlStateManager.pushMatrix(); //gay shit
        GlStateManager.scale(0.4, 0.4, 0.4); //scaling the gay shit
        GlStateManager.translate((x + 20) * (1 / 0.4), (y + 44) * (1 / 0.4), 40f * (1 / 0.4)); //translating
        drawModel(target.rotationYaw, target.rotationPitch, (EntityLivingBase) target); //drawing model
        GlStateManager.popMatrix(); //more gay shit
    };

    ///////////////////////////////////////////////////

    private static Color getColor(AbstractClientPlayer target) { // a VERY retarded way to do health colors :smile:
        Color healthColor = new Color(0, 165, 0); //green
        if (target.getHealth() < target.getMaxHealth() / 1.5)
            healthColor = new Color(200, 200, 0); //yellow
        if (target.getHealth() < target.getMaxHealth() / 2.5)
            healthColor = new Color(200, 155, 0); //orange
        if (target.getHealth() < target.getMaxHealth() / 4)
            healthColor = new Color(120, 0, 0); //red
        return healthColor;
    }

    ///////////////////////////////////////////////////

    public static void drawModel(final float yaw, final float pitch, final EntityLivingBase entityLivingBase) { //method to draw model (skidded)
        GlStateManager.resetColor();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 0.0f, 50.0f);
        GlStateManager.scale(-50.0f, 50.0f, 50.0f);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        final float renderYawOffset = entityLivingBase.renderYawOffset;
        final float rotationYaw = entityLivingBase.rotationYaw;
        final float rotationPitch = entityLivingBase.rotationPitch;
        final float prevRotationYawHead = entityLivingBase.prevRotationYawHead;
        final float rotationYawHead = entityLivingBase.rotationYawHead;
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((float) (-Math.atan(pitch / 40.0f) * 20.0), 1.0f, 0.0f, 0.0f);
        entityLivingBase.renderYawOffset = yaw - 0.4f;
        entityLivingBase.rotationYaw = yaw - 0.2f;
        entityLivingBase.rotationPitch = pitch;
        entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
        entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        final RenderManager renderManager = mc.getRenderManager();
        renderManager.setPlayerViewY(180.0f);
        renderManager.setRenderShadow(false);
        renderManager.renderEntityWithPosYaw(entityLivingBase, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        renderManager.setRenderShadow(true);
        entityLivingBase.renderYawOffset = renderYawOffset;
        entityLivingBase.rotationYaw = rotationYaw;
        entityLivingBase.rotationPitch = rotationPitch;
        entityLivingBase.prevRotationYawHead = prevRotationYawHead;
        entityLivingBase.rotationYawHead = rotationYawHead;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.resetColor();
    }
}
