package me.mees.remix.modules.render.esp;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.render.*;
import me.satisfactory.base.events.*;
import org.lwjgl.opengl.*;
import me.satisfactory.base.utils.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.client.renderer.entity.*;
import pw.stamina.causam.scan.method.model.*;

public class Outline extends Mode<ESP>
{
    int counter;
    
    public Outline(final ESP parent) {
        super(parent, "Outline");
        this.counter = 0;
    }
    
    @Subscriber
    public void event3DRender(final Event3DRender event3DRender) {
        final int list = GL11.glGenLists(1);
        Stencil.checkSetupFBO();
        Stencil.getInstance().startLayer();
        GL11.glPushMatrix();
        Stencil.getInstance().setBuffer(true);
        GL11.glNewList(list, 4864);
        GlStateManager.enableLighting();
        for (final Object obj : this.mc.theWorld.loadedEntityList) {
            final Entity entity = (Entity)obj;
            if (entity != this.mc.thePlayer && entity instanceof EntityPlayer) {
                GL11.glPushMatrix();
                GL11.glDisable(2929);
                GL11.glLineWidth(3.5f);
                GL11.glEnable(3042);
                GL11.glEnable(2848);
                GL11.glDisable(3553);
                final double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * this.mc.timer.renderPartialTicks;
                final double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * this.mc.timer.renderPartialTicks;
                final double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * this.mc.timer.renderPartialTicks;
                GL11.glTranslated(posX - this.mc.getRenderManager().renderPosX, posY - this.mc.getRenderManager().renderPosY, posZ - this.mc.getRenderManager().renderPosZ);
                final Render entityRender = this.mc.getRenderManager().getEntityRenderObject(entity);
                if (entityRender != null) {
                    final float distance = this.mc.thePlayer.getDistanceToEntity(entity);
                    GlStateManager.disableLighting();
                    entityRender.doRender(entity, 0.0, 0.0, 0.0, this.mc.timer.renderPartialTicks, this.mc.timer.renderPartialTicks);
                    GlStateManager.enableLighting();
                }
                GL11.glEnable(3553);
                GL11.glPopMatrix();
            }
        }
        GlStateManager.disableLighting();
        GL11.glEndList();
        GL11.glPolygonMode(1032, 6913);
        GL11.glCallList(list);
        GL11.glPolygonMode(1032, 6912);
        GL11.glCallList(list);
        Stencil.getInstance().setBuffer(false);
        GL11.glPolygonMode(1032, 6914);
        GL11.glCallList(list);
        Stencil.getInstance().cropInside();
        GL11.glPolygonMode(1032, 6913);
        GL11.glCallList(list);
        GL11.glPolygonMode(1032, 6912);
        GL11.glCallList(list);
        GL11.glPolygonMode(1032, 6914);
        Stencil.getInstance().stopLayer();
        GL11.glEnable(2929);
        GL11.glDeleteLists(list, 1);
        GL11.glPopMatrix();
    }
}
