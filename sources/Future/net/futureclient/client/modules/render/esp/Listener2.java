package net.futureclient.client.modules.render.esp;

import net.futureclient.client.events.Event;
import net.minecraft.client.renderer.entity.Render;
import java.util.Iterator;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.Level;
import net.futureclient.client.s;
import net.minecraft.client.renderer.GlStateManager;
import net.futureclient.client.ye;
import net.futureclient.loader.mixin.common.render.entity.wrapper.IRenderItem;
import net.futureclient.client.pg;
import net.minecraft.entity.player.EntityPlayer;
import net.futureclient.client.ZG;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.futureclient.client.xG;
import net.minecraft.client.renderer.RenderHelper;
import net.futureclient.loader.mixin.common.render.entity.wrapper.IEntityRenderer;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.minecraft.client.shader.Framebuffer;
import net.futureclient.client.ed;
import net.futureclient.client.modules.render.ESP;
import net.futureclient.client.kD;
import net.futureclient.client.n;

public class Listener2 extends n<kD>
{
    public final ESP k;
    
    public Listener2(final ESP k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final kD kd) {
        if (ESP.M(this.k).M() == ed.bC.d) {
            try {
                ESP.j = false;
                if (ESP.M(this.k) != null) {
                    ESP.M(this.k).framebufferClear();
                }
                Listener2 listener2;
                if (ESP.M(this.k) == null) {
                    listener2 = this;
                    ESP.M(this.k, new Framebuffer(ESP.getMinecraft35().displayWidth, ESP.getMinecraft64().displayHeight, false));
                }
                else {
                    if (ESP.M(this.k).framebufferWidth != ESP.getMinecraft7().displayWidth || ESP.M(this.k).framebufferHeight != ESP.getMinecraft32().displayHeight) {
                        ESP.M(this.k).unbindFramebuffer();
                        ESP.M(this.k, new Framebuffer(ESP.getMinecraft45().displayWidth, ESP.getMinecraft40().displayHeight, false));
                        if (ESP.M(this.k) != null) {
                            ESP.M(this.k).e();
                            ESP.M(this.k, new ed.Xc(ESP.M(this.k).framebufferTexture, ESP.getMinecraft59().displayWidth, ESP.getMinecraft().displayHeight, ESP.getMinecraft56().displayWidth / 2, ESP.getMinecraft37().displayHeight / 2, 1.0f, (int)Integer.valueOf(String.valueOf(3).substring(0, 1))));
                        }
                    }
                    listener2 = this;
                }
                if (ESP.M(listener2.k) == null) {
                    ESP.M(this.k, new ed.Xc(ESP.M(this.k).framebufferTexture, ESP.getMinecraft57().displayWidth, ESP.getMinecraft62().displayHeight, ESP.getMinecraft17().displayWidth / 2, ESP.getMinecraft48().displayHeight / 2, 1.0f, (int)Integer.valueOf(String.valueOf(3).substring(0, 1))));
                }
                ESP.M(this.k).M(1.0f);
                final float renderPartialTicks = ((IMinecraft)ESP.getMinecraft66()).getTimer().renderPartialTicks;
                ((IEntityRenderer)ESP.getMinecraft65().entityRenderer).setupCameraTransformWrapper(renderPartialTicks, 0);
                RenderHelper.enableStandardItemLighting();
                final Vec3d m = xG.M((Entity)ESP.getMinecraft16().player);
                final double x = m.x;
                final double y = m.y;
                final double z = m.z;
                ESP.M(this.k).bindFramebuffer(false);
                final float n = 0.0f;
                final int n2 = 0;
                GL11.glClearColor((float)n2, (float)n2, n, (float)n2);
                GL11.glClear(16640);
                final Iterator<Entity> iterator = ZG.e().iterator();
            Label_0435:
                while (true) {
                    Iterator<Entity> iterator2 = iterator;
                    while (iterator2.hasNext()) {
                        final Entity entity = iterator.next();
                        if (!ESP.e(this.k, entity)) {
                            continue Label_0435;
                        }
                        if (entity instanceof EntityPlayer && pg.M().M().M(entity.getName())) {
                            iterator2 = iterator;
                        }
                        else {
                            ESP.getMinecraft19().entityRenderer.disableLightmap();
                            RenderHelper.disableStandardItemLighting();
                            final Vec3d i = xG.M(entity);
                            final double x2 = i.x;
                            final double y2 = i.y;
                            final double z2 = i.z;
                            GL11.glPushMatrix();
                            final Render entityRenderObject;
                            if ((entityRenderObject = ESP.getMinecraft6().getRenderManager().getEntityRenderObject(entity)) != null) {
                                ESP.D = false;
                                ((IRenderItem)ESP.getMinecraft4().getRenderItem()).setNotRenderingEffectsInGUI(false);
                                ESP.F = false;
                                final boolean f = true;
                                entityRenderObject.doRender(entity, x2 - x, y2 - y, z2 - z, 0.0f, renderPartialTicks);
                                ESP.F = f;
                                ((IRenderItem)ESP.getMinecraft22().getRenderItem()).setNotRenderingEffectsInGUI(true);
                                ESP.D = true;
                            }
                            GL11.glPopMatrix();
                            iterator2 = iterator;
                        }
                    }
                    break;
                }
                ESP.getMinecraft52().entityRenderer.disableLightmap();
                RenderHelper.disableStandardItemLighting();
                ESP.getMinecraft53().entityRenderer.setupOverlayRendering();
                ESP.M(this.k).M();
                ESP.M(this.k).unbindFramebuffer();
                ESP.getMinecraft11().getFramebuffer().bindFramebuffer(true);
                GL11.glPushMatrix();
                final ye ye;
                if ((ye = (ye)pg.M().M().M((Class)ye.class)) != null) {
                    GL11.glColor3f(ye.L.getRed() / 255.0f, ye.L.getGreen() / 255.0f, ye.L.getBlue() / 255.0f);
                }
                GlStateManager.bindTexture(ESP.M(this.k).M());
                GL11.glBegin(9);
                GL11.glTexCoord2d(0.0, 1.0);
                final double n3 = 0.0;
                GL11.glVertex2d(n3, n3);
                final double n4 = 0.0;
                GL11.glTexCoord2d(n4, n4);
                GL11.glVertex2d(0.0, (double)ESP.getMinecraft58().displayHeight);
                GL11.glTexCoord2d(1.0, 0.0);
                GL11.glVertex2d((double)ESP.getMinecraft29().displayWidth, (double)ESP.getMinecraft54().displayHeight);
                GL11.glTexCoord2d(1.0, 0.0);
                GL11.glVertex2d((double)ESP.getMinecraft24().displayWidth, (double)ESP.getMinecraft34().displayHeight);
                final double n5 = 1.0;
                GL11.glTexCoord2d(n5, n5);
                GL11.glVertex2d((double)ESP.getMinecraft18().displayWidth, 0.0);
                GL11.glTexCoord2d(0.0, 1.0);
                final double n6 = 0.0;
                GL11.glVertex2d(n6, n6);
                GL11.glEnd();
                final float n7 = 1.0f;
                final int n8 = 1;
                GL11.glColor4f((float)n8, (float)n8, n7, (float)n8);
                GL11.glPopMatrix();
                ((IEntityRenderer)ESP.getMinecraft39().entityRenderer).setupCameraTransformWrapper(renderPartialTicks, 0);
                RenderHelper.enableStandardItemLighting();
                ESP.M(this.k).bindFramebuffer(false);
                final float n9 = 0.0f;
                final int n10 = 0;
                GL11.glClearColor((float)n10, (float)n10, n9, (float)n10);
                GL11.glClear(16640);
                final Iterator<EntityPlayer> iterator4;
                Iterator<EntityPlayer> iterator3 = iterator4 = ZG.M().iterator();
                while (iterator3.hasNext()) {
                    final EntityPlayer entityPlayer = iterator4.next();
                    if (!ESP.e(this.k, (Entity)entityPlayer)) {
                        iterator3 = iterator4;
                    }
                    else {
                        ESP.getMinecraft14().entityRenderer.disableLightmap();
                        RenderHelper.disableStandardItemLighting();
                        final Vec3d j = xG.M((Entity)entityPlayer);
                        final double x3 = j.x;
                        final double y3 = j.y;
                        final double z3 = j.z;
                        GL11.glPushMatrix();
                        final Render entityRenderObject2;
                        if ((entityRenderObject2 = ESP.getMinecraft36().getRenderManager().getEntityRenderObject((Entity)entityPlayer)) != null && pg.M().M().M(entityPlayer.getName())) {
                            ESP.D = false;
                            ((IRenderItem)ESP.getMinecraft26().getRenderItem()).setNotRenderingEffectsInGUI(false);
                            ESP.F = false;
                            final boolean f2 = true;
                            entityRenderObject2.doRender((Entity)entityPlayer, x3 - x, y3 - y, z3 - z, 0.0f, renderPartialTicks);
                            ESP.F = f2;
                            ((IRenderItem)ESP.getMinecraft13().getRenderItem()).setNotRenderingEffectsInGUI(true);
                            ESP.D = true;
                        }
                        GL11.glPopMatrix();
                        iterator3 = iterator4;
                    }
                }
                ESP.getMinecraft27().entityRenderer.disableLightmap();
                RenderHelper.disableStandardItemLighting();
                ESP.getMinecraft42().entityRenderer.setupOverlayRendering();
                final boolean k = true;
                ESP.M(this.k).M();
                final int n11 = 9;
                final float n12 = 0.27f;
                ESP.M(this.k).unbindFramebuffer();
                ESP.getMinecraft9().getFramebuffer().bindFramebuffer(true);
                GL11.glPushMatrix();
                GL11.glColor3f(n12, 0.7f, 1.0f);
                GlStateManager.bindTexture(ESP.M(this.k).M());
                GL11.glBegin(n11);
                GL11.glTexCoord2d(0.0, 1.0);
                final double n13 = 0.0;
                GL11.glVertex2d(n13, n13);
                final double n14 = 0.0;
                GL11.glTexCoord2d(n14, n14);
                GL11.glVertex2d(0.0, (double)ESP.getMinecraft3().displayHeight);
                GL11.glTexCoord2d(1.0, 0.0);
                GL11.glVertex2d((double)ESP.getMinecraft63().displayWidth, (double)ESP.getMinecraft33().displayHeight);
                GL11.glTexCoord2d(1.0, 0.0);
                GL11.glVertex2d((double)ESP.getMinecraft49().displayWidth, (double)ESP.getMinecraft25().displayHeight);
                final double n15 = 1.0;
                GL11.glTexCoord2d(n15, n15);
                GL11.glVertex2d((double)ESP.getMinecraft51().displayWidth, 0.0);
                GL11.glTexCoord2d(0.0, 1.0);
                final double n16 = 0.0;
                GL11.glVertex2d(n16, n16);
                GL11.glEnd();
                final float n17 = 1.0f;
                final int n18 = 1;
                GL11.glColor4f((float)n18, (float)n18, n17, (float)n18);
                GL11.glPopMatrix();
                ESP.j = k;
            }
            catch (Exception ex) {
                s.M().M(Level.ERROR, "Shader ESP is causing a crash.");
                ex.printStackTrace();
            }
        }
    }
    
    public void M(final Event event) {
        this.M((kD)event);
    }
}
