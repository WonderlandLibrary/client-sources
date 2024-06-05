
package me.superskidder.lune.modules.render;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.combat.KillAura;
import me.superskidder.lune.modules.combat.Teams;
import me.superskidder.lune.events.EventRender3D;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.values.type.Mode;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class ESP extends Mod {
    public static Mode mode = new Mode("Mode", (Enum[]) ESPMode.values(), (Enum) ESPMode.Box);

    public ESP() {
        super("ESP", ModCategory.Render, "Draw boxes on players");
        this.addValues(mode);
    }

    @EventTarget
    public void onRender(EventRender3D e) {

        if (mode.getValue() == ESPMode.Box) {
            doBoxESP(e);
        } else if (mode.getValue() == ESPMode.Box2D) {
            doOther2DESP(e);
        }
    }

    private boolean isValid(EntityLivingBase entity) {
        return entity == this.mc.thePlayer ? false : (entity.getHealth() <= 0.0F ? false : (entity instanceof EntityPlayer));
    }

    int i = 0;

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (i < 50) {
            i++;
        } else if (i >= 50) {
            i = 0;
        }
    }


    private void doOther2DESP(EventRender3D e) {
        for (EntityPlayer entity : this.mc.theWorld.playerEntities) {
            if (!this.isValid(entity)) continue;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            GlStateManager.enableBlend();
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);


            float partialTicks = this.mc.timer.renderPartialTicks;
            this.mc.getRenderManager();
            double x2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks - RenderManager.renderPosX;
            this.mc.getRenderManager();
            double y2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks - RenderManager.renderPosY;
            this.mc.getRenderManager();
            double z2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks - RenderManager.renderPosZ;
            float DISTANCE = mc.thePlayer.getDistanceToEntity(entity);
            float DISTANCE_SCALE = Math.min(DISTANCE * 0.15f, 0.15f);
            float SCALE = 0.035f;
            float xMid = (float) x2;
            float yMid = (float) y2 + entity.height + 0.5f - (entity.isChild() ? entity.height / 2.0f : 0.0f);
            float zMid = (float) z2;
            GlStateManager.translate((float) x2, (float) y2 + entity.height + 0.5f - (entity.isChild() ? entity.height / 2.0f : 0.0f), (float) z2);
            this.mc.getRenderManager();
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(-SCALE, -SCALE, -(SCALE /= 2.0f));
            Tessellator tesselator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tesselator.getWorldRenderer();
            float HEALTH = entity.getHealth();
            int COLOR = -1;
            COLOR = (double) HEALTH > 20.0 ? -65292 : ((double) HEALTH >= 10.0 ? -16711936 : ((double) HEALTH >= 3.0 ? -23296 : -65536));
            Color gray = new Color(0, 0, 0);
            double thickness = 1.5f + DISTANCE * 0.01f;
            double xLeft = -15.0;
            double xRight = 15.0;
            double yUp = 10.0;
            double yDown = 70.0;
            double size = 10.0;

            drawBorderedRect((float) xLeft - 1 - DISTANCE * 0.2f, (float) ((float) yDown - (float) (yDown - yUp) - 0.1),
                    (float) xLeft - 3, (float) yDown + 0.1f, 0.15f, Color.BLACK.getRGB(),
                    Color.BLACK.getRGB());
            Gui.drawRect((float) xLeft - 1.0f - DISTANCE * 0.2f,
                    (float) yDown - ((float) yDown - (float) yUp) * (entity.getHealth() / entity.getMaxHealth()),
                    (float) xLeft - 3f, (float) yDown, new Color(255, 100, 100).getRGB());
            GlStateManager.scale(0.5, 0.5, 0);
            mc.fontRendererObj.drawStringWithShadow((entity.getHeldItem() == null) ? "" : entity.getHeldItem().getDisplayName(), (float) xLeft - 3.0f - DISTANCE * 0.2f,
                    (float) yDown * 2 + 15, -1);
            GlStateManager.scale(2, 2, 0);


            RenderUtil.startDrawing();
            ArrayList<ItemStack> stuff = new ArrayList<ItemStack>();
            int split = (int) ((float) xLeft + 55);
            int index = 3;
            while (index >= 0) {
                ItemStack armer = entity.inventory.armorInventory[index];
                if (armer != null) {
                    stuff.add(armer);
                }
                --index;
            }
            GlStateManager.scale(0.5, 0.5, 0);
            for (ItemStack errything : stuff) {
                if (mc.theWorld != null) {
                    RenderHelper.enableGUIStandardItemLighting();
                    y2 += 30;
                }
                GlStateManager.disableAlpha();
                GlStateManager.clear(256);
                GlStateManager.scale(0.8, 0.8, 0);
                mc.getRenderItem().zLevel = -150.0f;
                mc.getRenderItem().renderItemAndEffectIntoGUI(errything, split, (int) y2);
                mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, errything, split, (int) y2);
                mc.getRenderItem().zLevel = 0.0f;
                GlStateManager.scale(1.25, 1.25, 0);
                GlStateManager.disableBlend();
                GlStateManager.scale(0.5, 0.5, 0.5);
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.enableDepth();
                GlStateManager.scale(2.0f, 2.0f, 2.0f);
                GlStateManager.enableAlpha();
            }

            GlStateManager.scale(2, 2, 0);
            RenderUtil.stopDrawing();


            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.disableBlend();
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
    }

    public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
        RenderUtil.drawRect(x, y, x2, y2, col2);
        float f = (float) (col1 >> 24 & 255) / 255.0F;
        float f1 = (float) (col1 >> 16 & 255) / 255.0F;
        float f2 = (float) (col1 >> 8 & 255) / 255.0F;
        float f3 = (float) (col1 & 255) / 255.0F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    private void doBoxESP(EventRender3D event) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        for (Object o2 : this.mc.theWorld.loadedEntityList) {
            if (!(o2 instanceof EntityPlayer) || o2 == mc.thePlayer) continue;

            EntityPlayer ent = (EntityPlayer) o2;
            if (Teams.isOnSameTeam(ent)) {
                RenderUtil.entityESPBox(ent, new Color(0, 255, 0, 100), event);
                continue;
            }
            if (ent.hurtTime > 0) {
                //RenderUtil.drawBoundingBox((AxisAlignedBB)new AxisAlignedBB(ent.motionX, ent.motionY, ent.motionZ, ent.motionX-1, ent.motionY-1, ent.motionZ-1));
                RenderUtil.entityESPBox(ent, new Color(255, 0, 0, 100), event);
                continue;
            }
            if (ent.isInvisible()) {
                //RenderUtil.drawBoundingBox((AxisAlignedBB)new AxisAlignedBB(ent.motionX, ent.motionY, ent.motionZ, ent.motionX-1, ent.motionY-1, ent.motionZ-1));
                RenderUtil.entityESPBox(ent, new Color(155, 155, 255, 100), event);
                continue;
            }

            RenderUtil.entityESPBox(ent, new Color(255, 255, 255, 100), event);

        }
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static enum ESPMode {
        Box, Box2D

    }

}
