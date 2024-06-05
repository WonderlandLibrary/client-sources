package koks.modules.impl.visuals;

import koks.event.Event;
import koks.event.impl.EventRender3D;
import koks.modules.Module;
import koks.utilities.FriendManager;
import koks.utilities.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 08:29
 */
public class NameTags extends Module {

    FriendManager friendManager = new FriendManager();

    public NameTags() {
        super("NameTags", "Its make better Nametags", Category.VISUALS);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender3D) {
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (isValid(entity)) {

                    double x = entity.prevPosX + (entity.posX - entity.prevPosX) * ((EventRender3D) event).getPartialTicks() - mc.getRenderManager().renderPosX;
                    double y = entity.prevPosY + (entity.posY - entity.prevPosY) * ((EventRender3D) event).getPartialTicks() - mc.getRenderManager().renderPosY;
                    double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * ((EventRender3D) event).getPartialTicks() - mc.getRenderManager().renderPosZ;

                    double defaultScale = 0.01;
                    double scale = mc.thePlayer.getDistanceToEntity(entity) / 600;

                    double height = (defaultScale + scale) * 75;

                    String name = friendManager.isFriend(entity.getName()) ? friendManager.getFriend(entity.getName())[1] : entity.getDisplayName().getUnformattedText();
                    float finalHealth = Float.isNaN(((EntityLivingBase) entity).getHealth()) ? -1 : Math.round(((EntityLivingBase) entity).getHealth() * 5);
                    String colorPrefix = finalHealth == -1 ? "" : (finalHealth >= 80 ? "§a" : finalHealth < 80 && finalHealth >= 60 ? "§e" : finalHealth < 60 && finalHealth >= 40 ? "§6" : finalHealth < 40 && finalHealth >= 20 ? "§c" : finalHealth < 20 ? "§4" : "§f");
                    String health = finalHealth == -1 ? "" : " " + colorPrefix + Math.round(finalHealth) + "%";

                    String tagText = name + health;

                    GL11.glPushMatrix();
                    GL11.glTranslated(x, y + 2 - height, z);
                    GL11.glScaled(-(defaultScale + scale), -(defaultScale + scale), -(defaultScale + scale));
                    GL11.glRotated(mc.getRenderManager().playerViewY, 0, -1, 0);
                    GlStateManager.disableDepth();

                    Gui.drawRect(-mc.fontRendererObj.getStringWidth(tagText) / 2 - 2, -92, mc.fontRendererObj.getStringWidth(tagText) / 2 + 2, -80, Integer.MIN_VALUE);
                    RenderUtils renderUtils = new RenderUtils();
                    renderUtils.drawOutlineRect(-mc.fontRendererObj.getStringWidth(tagText) / 2 - 2, -92, mc.fontRendererObj.getStringWidth(tagText) / 2 + 2, -80, 1, Color.BLACK);
                    mc.fontRendererObj.drawString(tagText, 0 - mc.fontRendererObj.getStringWidth(tagText) / 2, -90, 0xFFFFFFFF);

                    GlStateManager.enableDepth();
                    GL11.glPopMatrix();

                }
            }
        }
    }

    public boolean isValid(Entity entity) {
        if (!(entity instanceof EntityPlayer))
            return false;
        if (entity == mc.thePlayer)
            return false;
        if (entity.getName().contains("-"))
            return false;
        if(entity.isInvisible())
            return false;
        return true;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}