package com.client.glowclient.modules.jewishtricks;

import net.minecraft.client.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.*;
import com.client.glowclient.events.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import com.client.glowclient.utils.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TracersImpact extends ModuleContainer
{
    private static final Minecraft b;
    
    static {
        b = Minecraft.getMinecraft();
    }
    
    public TracersImpact() {
        super(Category.JEWISH TRICKS, "TracersImpact", false, -1, "Draws lines to entities");
    }
    
    @Override
    public void D() {
        ModuleManager.M("Tracers").k();
    }
    
    @SubscribeEvent
    public void M(final EventRenderWorld eventRenderWorld) {
        if (this.k()) {
            try {
                final double renderPosX = TracersImpact.b.getRenderManager().renderPosX;
                final double renderPosY = TracersImpact.b.getRenderManager().renderPosY;
                final double renderPosZ = TracersImpact.b.getRenderManager().renderPosZ;
                try {
                    GL11.glPushMatrix();
                    GL11.glEnable(2848);
                    GL11.glDisable(2929);
                    GL11.glDisable(3553);
                    GL11.glDepthMask(false);
                    GL11.glBlendFunc(770, 771);
                    GL11.glEnable(3042);
                    GL11.glLineWidth(0.5f);
                    final Iterator<Entity> iterator = TracersImpact.b.world.loadedEntityList.iterator();
                Label_0099:
                    while (true) {
                        Iterator<Entity> iterator2 = iterator;
                        while (iterator2.hasNext()) {
                            final Entity entity = iterator.next();
                            TracersImpact.b.player.getDistance(entity);
                            if (entity == TracersImpact.b.player) {
                                continue Label_0099;
                            }
                            final Entity entity2 = entity;
                            final double n = entity2.lastTickPosX + (entity.posX - entity.lastTickPosX) * TracersImpact.b.getRenderPartialTicks() - renderPosX;
                            final double lastTickPosY = entity2.lastTickPosY;
                            final Entity entity3 = entity;
                            final double n2 = lastTickPosY + (entity3.posY - entity.lastTickPosY) * TracersImpact.b.getRenderPartialTicks() - renderPosY;
                            final double n3 = entity3.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * TracersImpact.b.getRenderPartialTicks() - renderPosZ;
                            final float n4 = 0.15f;
                            final float n5 = 1.0f;
                            GL11.glColor4f(n4, n5, n5, n5);
                            final double n6 = 0.0;
                            final Vec3d rotateYaw = new Vec3d(n6, n6, 1.0).rotatePitch(-(float)Math.toRadians(MinecraftHelper.getPlayer().rotationPitch)).rotateYaw(-(float)Math.toRadians(MinecraftHelper.getPlayer().rotationYaw));
                            GL11.glBegin(2);
                            GL11.glVertex3d(rotateYaw.x, MinecraftHelper.getPlayer().getEyeHeight() + rotateYaw.y, rotateYaw.z);
                            if (!(entity2 instanceof EntityPlayerSP)) {
                                if (entity instanceof EntityPlayer) {
                                    GL11.glVertex3d(n, n2, n3);
                                }
                                GL11.glEnd();
                                GL11.glBegin(2);
                                if (entity instanceof EntityPlayer) {
                                    GL11.glVertex3d(n, n2, n3);
                                    GL11.glVertex3d(n, n2 + 1.75, n3);
                                }
                                GL11.glEnd();
                                continue Label_0099;
                            }
                            iterator2 = iterator;
                        }
                        break;
                    }
                    GL11.glDisable(3042);
                    GL11.glDepthMask(true);
                    GL11.glEnable(3553);
                    GL11.glEnable(2929);
                    GL11.glDisable(2848);
                    GL11.glPopMatrix();
                }
                catch (Exception ex) {}
            }
            catch (Exception ex2) {}
        }
    }
}
