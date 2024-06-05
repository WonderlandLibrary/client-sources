package cn.Hanabi.modules.Render;

import cn.Hanabi.value.*;
import cn.Hanabi.events.*;
import net.minecraft.entity.player.*;
import java.util.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.entity.*;
import cn.Hanabi.modules.Combat.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import ClassSub.*;

public class ESP extends Mod
{
    private Value<String> mode;
    private Value<Boolean> invisible;
    
    
    public ESP() {
        super("ESP", Category.RENDER);
        this.mode = new Value<String>("ESP", "Mode", 0);
        this.invisible = new Value<Boolean>("ESP_Invisible", false);
        this.mode.addValue("Box");
        this.mode.addValue("2D");
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        if (this.mode.isCurrentMode("Box")) {
            this.setDisplayName("Box");
            for (final EntityPlayer next : ESP.mc.theWorld.loadedEntityList) {
                if (next instanceof EntityPlayer) {
                    final EntityPlayer entityPlayer = next;
                    if (entityPlayer == ESP.mc.thePlayer || entityPlayer.isDead) {
                        continue;
                    }
                    this.renderBox((Entity)entityPlayer, 0.0, 1.0, 0.0);
                }
            }
        }
        if (this.mode.isCurrentMode("2D")) {
            this.setDisplayName("2D");
            this.doOther2DESP();
        }
    }
    
    private boolean isValid(final EntityLivingBase entityLivingBase) {
        return entityLivingBase != ESP.mc.thePlayer && entityLivingBase.getHealth() > 0.0f && entityLivingBase instanceof EntityPlayer;
    }
    
    public void renderBox(final Entity entity, final double n, final double n2, final double n3) {
        if ((entity.isInvisible() && !this.invisible.getValueState()) || (KillAura.targets.contains(entity) && ((KillAura)ModManager.getModule("Killaura")).esp.getValueState())) {
            return;
        }
        ESP.mc.getRenderManager();
        final double n4 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * Class211.getTimer().renderPartialTicks - ((IRenderManager)ESP.mc.getRenderManager()).getRenderPosX();
        ESP.mc.getRenderManager();
        final double n5 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * Class211.getTimer().renderPartialTicks - ((IRenderManager)ESP.mc.getRenderManager()).getRenderPosY();
        ESP.mc.getRenderManager();
        Class246.drawEntityESP(n4, n5, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Class211.getTimer().renderPartialTicks - ((IRenderManager)ESP.mc.getRenderManager()).getRenderPosZ(), entity.getEntityBoundingBox().maxX - entity.getEntityBoundingBox().minX - 0.1, entity.getEntityBoundingBox().maxY - entity.getEntityBoundingBox().minY + 0.25, 1.0f, 1.0f, 1.0f, 0.2f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f);
    }
    
    public static void drawSelectionBoundingBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator getInstance = Tessellator.getInstance();
        final WorldRenderer getWorldRenderer = getInstance.getWorldRenderer();
        getWorldRenderer.begin(3, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(3, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getInstance.draw();
        getWorldRenderer.begin(1, DefaultVertexFormats.POSITION);
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        getWorldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        getInstance.draw();
    }
    
    private void doOther2DESP() {
        for (final EntityPlayer entityPlayer : ESP.mc.theWorld.playerEntities) {
            if ((entityPlayer.isInvisible() && !this.invisible.getValueState()) || (KillAura.targets.contains(entityPlayer) && ((KillAura)ModManager.getModule("Killaura")).esp.getValueState())) {
                return;
            }
            if (!this.isValid((EntityLivingBase)entityPlayer)) {
                continue;
            }
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.enableBlend();
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            final float renderPartialTicks = Class211.getTimer().renderPartialTicks;
            ESP.mc.getRenderManager();
            final double n = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * renderPartialTicks - ((IRenderManager)ESP.mc.getRenderManager()).getRenderPosX();
            ESP.mc.getRenderManager();
            final double n2 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * renderPartialTicks - ((IRenderManager)ESP.mc.getRenderManager()).getRenderPosY();
            ESP.mc.getRenderManager();
            final double n3 = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * renderPartialTicks - ((IRenderManager)ESP.mc.getRenderManager()).getRenderPosZ();
            final float getDistanceToEntity = ESP.mc.thePlayer.getDistanceToEntity((Entity)entityPlayer);
            Math.min(getDistanceToEntity * 0.15f, 0.15f);
            final float n4 = 0.035f / 2.0f;
            final float n5 = (float)n;
            final float n6 = (float)n2 + entityPlayer.height + 0.5f - (entityPlayer.isChild() ? (entityPlayer.height / 2.0f) : 0.0f);
            final float n7 = (float)n3;
            GlStateManager.translate((float)n, (float)n2 + entityPlayer.height + 0.5f - (entityPlayer.isChild() ? (entityPlayer.height / 2.0f) : 0.0f), (float)n3);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-ESP.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(-n4, -n4, -n4);
            Tessellator.getInstance().getWorldRenderer();
            final float getHealth = entityPlayer.getHealth();
            if (getHealth <= 20.0) {
                if (getHealth < 10.0) {
                    if (getHealth >= 3.0) {}
                }
            }
            final Color color = new Color(0, 0, 0);
            final double n8 = 1.5f + getDistanceToEntity * 0.01f;
            Class246.drawRect(-30.0f, 15.0f, 30.0f, 140.0f, Class120.reAlpha(new Color(255, 255, 255).getRGB(), 0.2f));
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.disableBlend();
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glNormal3f(1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
    
    public static void drawBorderedRect(final float n, final float n2, final float n3, final float n4, final float n5, final int n6, final int n7) {
        drawRect(n, n2, n3, n4, n7);
        final float n8 = (n6 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n6 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n6 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n6 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glLineWidth(n5);
        GL11.glBegin(1);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawRect(final float n, final float n2, final float n3, final float n4, final int n5) {
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(n7, n8, n9, n6);
        GL11.glBegin(7);
        GL11.glVertex2d((double)n3, (double)n2);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glVertex2d((double)n, (double)n4);
        GL11.glVertex2d((double)n3, (double)n4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}
