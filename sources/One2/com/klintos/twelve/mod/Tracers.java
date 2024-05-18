// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import java.util.Iterator;
import com.klintos.twelve.Twelve;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import com.klintos.twelve.mod.events.EventRender;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import com.klintos.twelve.mod.events.EventPreUpdate;

public class Tracers extends Mod
{
    public Tracers() {
        super("Tracers", 0, ModCategory.RENDER);
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        RendererLivingEntity.updateESPColours();
    }
    
    @EventTarget
    public void onRender(final EventRender event) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        for (final Object o : Tracers.mc.theWorld.loadedEntityList) {
            if (o != Tracers.mc.thePlayer && o != null && o instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)o;
                final double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) - RenderManager.renderPosX;
                final double posY = player.lastTickPosY + 0.4 + (player.posY - player.lastTickPosY) - RenderManager.renderPosY;
                final double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) - RenderManager.renderPosZ;
                GL11.glLineWidth(3.0f);
                GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
                GL11.glBegin(1);
                GL11.glVertex3d(0.0, 1.62, 0.0);
                GL11.glVertex3d(posX, posY, posZ);
                GL11.glEnd();
                GL11.glBegin(1);
                GL11.glVertex3d(posX, posY, posZ);
                GL11.glVertex3d(posX, posY + 1.2, posZ);
                GL11.glEnd();
                GL11.glLineWidth(2.0f);
                if (Twelve.getInstance().getFriendHandler().isFriend(player.getName())) {
                    GL11.glColor4f(RendererLivingEntity.r / 255.0f, RendererLivingEntity.g / 255.0f, RendererLivingEntity.b / 255.0f, 1.0f);
                }
                else {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                }
                GL11.glBegin(1);
                GL11.glVertex3d(0.0, 1.62, 0.0);
                GL11.glVertex3d(posX, posY, posZ);
                GL11.glEnd();
                GL11.glBegin(1);
                GL11.glVertex3d(posX, posY, posZ);
                GL11.glVertex3d(posX, posY + 1.2, posZ);
                GL11.glEnd();
            }
        }
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}
