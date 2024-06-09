package com.client.glowclient.modules.render;

import net.minecraft.client.*;
import com.client.glowclient.events.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import com.client.glowclient.*;
import com.client.glowclient.modules.other.*;
import net.minecraft.util.math.*;
import com.client.glowclient.utils.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class Tracers extends ModuleContainer
{
    private static final Minecraft M;
    public static BooleanValue passive;
    public static BooleanValue hostile;
    public static BooleanValue items;
    public static final NumberValue width;
    public static BooleanValue distance;
    public static BooleanValue players;
    
    static {
        Tracers.players = ValueFactory.M("Tracers", "Players", "Draws Players", true);
        Tracers.passive = ValueFactory.M("Tracers", "Passive", "Draws Passive", false);
        Tracers.hostile = ValueFactory.M("Tracers", "Hostile", "Draws Hostile Mobs", false);
        Tracers.items = ValueFactory.M("Tracers", "Items", "Draws Items", false);
        Tracers.distance = ValueFactory.M("Tracers", "Distance", "Turns Red if close", true);
        final String s = "Tracers";
        final String s2 = "Width";
        final String s3 = "Line thickness";
        final double n = 1.5;
        final double n2 = 0.5;
        width = ValueFactory.M(s, s2, s3, n, n2, n2, 10.0);
        M = Minecraft.getMinecraft();
    }
    
    @SubscribeEvent
    public void M(final EventRenderWorld eventRenderWorld) {
        if (this.k()) {
            try {
                final double renderPosX = Tracers.M.getRenderManager().renderPosX;
                final double renderPosY = Tracers.M.getRenderManager().renderPosY;
                final double renderPosZ = Tracers.M.getRenderManager().renderPosZ;
                GL11.glPushMatrix();
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDisable(3553);
                GL11.glDepthMask(false);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3042);
                GL11.glLineWidth((float)Tracers.width.k());
                final Iterator<Entity> iterator = Tracers.M.world.loadedEntityList.iterator();
            Label_0104:
                while (true) {
                    Iterator<Entity> iterator2 = iterator;
                    while (iterator2.hasNext()) {
                        final Entity entity = iterator.next();
                        final float distance = Tracers.M.player.getDistance(entity);
                        if (entity != Tracers.M.player) {
                            final Entity entity2 = entity;
                            final double n = entity2.lastTickPosX + (entity.posX - entity.lastTickPosX) * Tracers.M.getRenderPartialTicks() - renderPosX;
                            final double lastTickPosY = entity2.lastTickPosY;
                            final Entity entity3 = entity;
                            final double n2 = lastTickPosY + (entity3.posY - entity.lastTickPosY) * Tracers.M.getRenderPartialTicks() - renderPosY;
                            final double n3 = entity3.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * Tracers.M.getRenderPartialTicks() - renderPosZ;
                            if (Va.M().M(entity.getName())) {
                                final float n4 = 0.15f;
                                final float n5 = 1.0f;
                                GL11.glColor4f(n4, n5, n5, n5);
                            }
                            else if (wa.M().M(entity.getName())) {
                                final float n6 = 0.0f;
                                final float n7 = 1.0f;
                                GL11.glColor4f(n7, n6, n7, n7);
                            }
                            else {
                                if (!Tracers.distance.M()) {
                                    GL11.glColor3f(HUD.red.M() / 255.0f, HUD.green.M() / 255.0f, HUD.blue.M() / 255.0f);
                                }
                                if (Tracers.distance.M()) {
                                    if (distance <= 6.0f) {
                                        final float n8 = 1.0f;
                                        final float n9 = 0.0f;
                                        GL11.glColor3f(n8, n9, n9);
                                    }
                                    else if (distance <= 40.0f) {
                                        GL11.glColor3f(1.0f - distance / 50.0f, distance / 50.0f, 0.0f);
                                    }
                                    else if (distance > 40.0f) {
                                        final float n10 = 0.8f;
                                        final float n11 = 0.1f;
                                        GL11.glColor3f(n11, n10, n11);
                                    }
                                }
                            }
                            final double n12 = 0.0;
                            final Vec3d rotateYaw = new Vec3d(n12, n12, 1.0).rotatePitch(-(float)Math.toRadians(MinecraftHelper.getPlayer().rotationPitch)).rotateYaw(-(float)Math.toRadians(MinecraftHelper.getPlayer().rotationYaw));
                            GL11.glBegin(2);
                            GL11.glVertex3d(rotateYaw.x, MinecraftHelper.getPlayer().getEyeHeight() + rotateYaw.y, rotateYaw.z);
                            if (Tracers.players.M()) {
                                if (entity instanceof EntityPlayerSP) {
                                    iterator2 = iterator;
                                    continue;
                                }
                                if (entity instanceof EntityPlayer) {
                                    GL11.glVertex3d(n, n2 + 1.0, n3);
                                }
                            }
                            if (Tracers.passive.M() && entity instanceof EntityAnimal) {
                                GL11.glVertex3d(n, n2 + 1.0, n3);
                            }
                            if (Tracers.hostile.M() && entity instanceof EntityMob) {
                                GL11.glVertex3d(n, n2 + 1.0, n3);
                            }
                            if (Tracers.items.M() && entity instanceof EntityItem) {
                                GL11.glVertex3d(n, n2 + 1.0, n3);
                            }
                            GL11.glEnd();
                            continue Label_0104;
                        }
                        continue Label_0104;
                    }
                    break;
                }
                final float n13 = 1.0f;
                GL11.glColor3f(n13, n13, n13);
                GL11.glDisable(3042);
                GL11.glDepthMask(true);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDisable(2848);
                GL11.glPopMatrix();
            }
            catch (Exception ex) {}
        }
    }
    
    public Tracers() {
        super(Category.RENDER, "Tracers", false, -1, "Draws lines to entities");
    }
}
