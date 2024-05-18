/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import java.util.List;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.misc.CustomColor;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.Render3DEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.ColorUtil;
import me.thekirkayt.utils.RenderUtils;
import me.thekirkayt.utils.SallosRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

@Module.Mod(shown=false)
public class ESP
extends Module {
    @Option.Op
    private boolean players = true;
    @Option.Op
    private boolean monsters;
    @Option.Op
    private boolean animals;
    @Option.Op
    public static boolean outline;
    @Option.Op
    public static boolean cutomscolor;
    private int state;
    private float r = 0.33f;
    private float g = 0.34f;
    private float b = 0.33f;
    private static float[] rainbowArray;
    private static int index;

    static {
        index = 0;
    }

    @EventTarget
    private void onPreUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            rainbowArray = this.getRainbow();
        }
    }

    @EventTarget(value=0)
    private void onRender3D(Render3DEvent event) {
        for (Object o : ClientUtils.world().loadedEntityList) {
            if (!(o instanceof EntityLivingBase)) continue;
            ClientUtils.mc();
            if (o == Minecraft.thePlayer) continue;
            EntityLivingBase entity = (EntityLivingBase)o;
            int color = 65280;
            int thingyt = 1186128252;
            if (entity.hurtTime != 0) {
                color = -5054084;
                thingyt = 1186128252;
            }
            if (!ESP.checkValidity(entity) || outline) continue;
            RenderUtils.drawEsp(entity, event.getPartialTicks(), color, thingyt);
        }
    }

    public void drawEntityOutlines(float partialTicks) {
        int entityDispList = GL11.glGenLists((int)1);
        SallosRender.Stencil.getInstance().startLayer();
        GL11.glPushMatrix();
        ClientUtils.mc().entityRenderer.setupCameraTransform(partialTicks, 0);
        GL11.glMatrixMode((int)5888);
        RenderHelper.enableStandardItemLighting();
        GL11.glEnable((int)2884);
        Minecraft.getMinecraft();
        SallosRender.Camera playerCam = new SallosRender.Camera(Minecraft.thePlayer);
        Frustrum frustrum = new Frustrum();
        frustrum.setPosition(playerCam.getPosX(), playerCam.getPosY(), playerCam.getPosZ());
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)true);
        SallosRender.Stencil.getInstance().setBuffer(true);
        GL11.glNewList((int)entityDispList, (int)4864);
        Minecraft.getMinecraft();
        for (Object obj : Minecraft.theWorld.loadedEntityList) {
            Entity entity = (Entity)obj;
            Minecraft.getMinecraft();
            if (entity == Minecraft.thePlayer) continue;
            GL11.glLineWidth((float)3.0f);
            GL11.glEnable((int)3042);
            GL11.glEnable((int)2848);
            SallosRender.Camera entityCam = new SallosRender.Camera(entity);
            GL11.glPushMatrix();
            GL11.glDisable((int)3553);
            GL11.glTranslated((double)(entityCam.getPosX() - playerCam.getPosX()), (double)(entityCam.getPosY() - playerCam.getPosY()), (double)(entityCam.getPosZ() - playerCam.getPosZ()));
            ClientUtils.mc();
            Render entityRender = Minecraft.getRenderManager().getEntityRenderObject(entity);
            if (entityRender != null) {
                ClientUtils.mc();
                float distance = Minecraft.thePlayer.getDistanceToEntity(entity);
                if (entity instanceof EntityLivingBase) {
                    if (entity instanceof EntityPlayer && FriendManager.isFriend(entity.getName())) {
                        GL11.glColor4f((float)0.92f, (float)0.72f, (float)0.0f, (float)1.0f);
                        break;
                    }
                    if (((EntityLivingBase)entity).hurtTime > 0) {
                        GL11.glColor4f((float)2.55f, (float)0.0f, (float)0.0f, (float)1.0f);
                    } else {
                        GL11.glColor4f((float)0.0f, (float)2.55f, (float)2.55f, (float)1.0f);
                    }
                    RendererLivingEntity.shouldRenderParts = false;
                    entityRender.doRender(entity, 0.0, 0.0, 0.0, 0.0f, 0.0f);
                    RendererLivingEntity.shouldRenderParts = true;
                }
            }
            GL11.glEnable((int)3553);
            GL11.glPopMatrix();
        }
        GL11.glEndList();
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glCallList((int)entityDispList);
        GL11.glPolygonMode((int)1032, (int)6912);
        GL11.glCallList((int)entityDispList);
        SallosRender.Stencil.getInstance().setBuffer(false);
        GL11.glPolygonMode((int)1032, (int)6914);
        GL11.glCallList((int)entityDispList);
        SallosRender.Stencil.getInstance().cropInside();
        GL11.glPolygonMode((int)1032, (int)6913);
        GL11.glCallList((int)entityDispList);
        GL11.glPolygonMode((int)1032, (int)6912);
        GL11.glCallList((int)entityDispList);
        GL11.glPolygonMode((int)1032, (int)6914);
        Minecraft.getMinecraft().entityRenderer.func_175072_h();
        RenderHelper.disableStandardItemLighting();
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
        SallosRender.Stencil.getInstance().stopLayer();
        GL11.glDisable((int)2960);
        GL11.glPopMatrix();
        Minecraft.getMinecraft().entityRenderer.func_175072_h();
        RenderHelper.disableStandardItemLighting();
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
        GL11.glDeleteLists((int)entityDispList, (int)1);
    }

    public static void renderOne(Entity ent) {
        if (!ESP.checkValidity((EntityLivingBase)ent)) {
            return;
        }
        ESP.checkSetupFBO();
        GL11.glPushAttrib((int)1048575);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)0.2f);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glEnable((int)2960);
        GL11.glClear((int)1024);
        GL11.glClearStencil((int)15);
        GL11.glStencilFunc((int)512, (int)1, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        ESP.colorLines(ent);
        GL11.glLineWidth((float)CustomColor.linewidth);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1032, (int)6913);
    }

    public static void renderTwo(Entity ent) {
        if (!ESP.checkValidity((EntityLivingBase)ent)) {
            return;
        }
        GL11.glStencilFunc((int)512, (int)0, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1032, (int)6914);
    }

    public static void renderThree(Entity ent) {
        if (!ESP.checkValidity((EntityLivingBase)ent)) {
            return;
        }
        GL11.glStencilFunc((int)514, (int)1, (int)15);
        GL11.glStencilOp((int)7680, (int)7680, (int)7680);
        GL11.glPolygonMode((int)1032, (int)6913);
    }

    public static void renderFour(Entity ent) {
        if (!ESP.checkValidity((EntityLivingBase)ent)) {
            return;
        }
        GL11.glEnable((int)10754);
        GL11.glPolygonOffset((float)1.0f, (float)-2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }

    public static void renderFive(Entity ent) {
        if (!ESP.checkValidity((EntityLivingBase)ent)) {
            return;
        }
        GL11.glPolygonOffset((float)1.0f, (float)2000000.0f);
        GL11.glDisable((int)10754);
        GL11.glDisable((int)2960);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3008);
        GL11.glPopAttrib();
    }

    private static void colorLines(Entity ent) {
        int color = -1727987713;
        if (FriendManager.isFriend(ent.getName())) {
            color = ColorUtil.rainbow((long)index + 200000000L, 1.0f).getRGB();
        }
        if (!cutomscolor) {
            ESP.color(color, 1.0f);
        } else if (!FriendManager.isFriend(ent.getName())) {
            GL11.glColor4f((float)CustomColor.red, (float)CustomColor.green, (float)CustomColor.blue, (float)CustomColor.alpha);
        } else {
            color = ColorUtil.rainbow((long)index + 200000000L, 1.0f).getRGB();
        }
    }

    public static void color(int color, float alpha) {
        float red = (float)(color >> 16 & 255) / 255.0f;
        float green = (float)(color >> 8 & 255) / 255.0f;
        float blue = (float)(color & 255) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    private static boolean checkValidity(EntityLivingBase entity) {
        return true;
    }

    private float[] getRainbow() {
        if (this.state == 0) {
            this.r = (float)((double)this.r + 0.02);
            this.b = (float)((double)this.b - 0.02);
            if ((double)this.r >= 0.85) {
                ++this.state;
            }
        } else if (this.state == 1) {
            this.g = (float)((double)this.g + 0.02);
            this.r = (float)((double)this.r - 0.02);
            if ((double)this.g >= 0.85) {
                ++this.state;
            }
        } else {
            this.b = (float)((double)this.b + 0.02);
            this.g = (float)((double)this.g - 0.02);
            if ((double)this.b >= 0.85) {
                this.state = 0;
            }
        }
        return new float[]{this.r, this.g, this.b};
    }

    public boolean isOutline() {
        return outline;
    }

    public static void checkSetupFBO() {
        Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            ESP.setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }

    public static void setupFBO(Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT((int)fbo.depthBuffer);
        int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)Minecraft.getMinecraft().displayWidth, (int)Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)stencil_depth_buffer_ID);
    }
}

