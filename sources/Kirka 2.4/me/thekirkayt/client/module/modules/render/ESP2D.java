/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import java.util.ArrayList;
import java.util.List;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.Render3DEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.ColorUtil;
import me.thekirkayt.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;

@Module.Mod(displayName="ESP2D")
public class ESP2D
extends Module {
    public static double red;
    public static double green;
    public static double blue;
    public static boolean rainbow;
    public static boolean mode;
    private final List<Entity> entities = new ArrayList<Entity>();
    private int ticks = 0;
    public int index = 0;
    public me.thekirkayt.utils.Timer time = new me.thekirkayt.utils.Timer();
    private Minecraft mc = Minecraft.getMinecraft();

    public void onEnable() {
        if (Minecraft.theWorld != null) {
            this.ticks = 0;
        }
    }

    @EventTarget
    public void onRender(Render3DEvent render) {
        int f\u00e4rbung = -1;
        Minecraft.getMinecraft();
        for (Object o : ClientUtils.world().loadedEntityList) {
            EntityLivingBase entity2;
            EntityLivingBase entity;
            if (!(o instanceof EntityLivingBase)) continue;
            ClientUtils.mc();
            if (o == Minecraft.thePlayer || (entity = (EntityLivingBase)o) == null || (entity2 = entity) == Minecraft.thePlayer || !(entity instanceof EntityPlayer) || ((EntityPlayer)entity).isInvisible()) continue;
            f\u00e4rbung = entity instanceof EntityLivingBase && entity.hurtTime > 0 ? -65536 : (FriendManager.isFriend(entity.getName()) ? ColorUtil.rainbow(2000000L, 1.0f).getRGB() : (rainbow ? ColorUtil.rainbow(2000000L, 1.0f).getRGB() : -14825443));
            float posX = (float)((double)((float)entity.lastTickPosX) + (entity.posX - entity.lastTickPosX) * (double)this.mc.timer.renderPartialTicks);
            float posY = (float)((double)((float)entity.lastTickPosY) + (entity.posY - entity.lastTickPosY) * (double)this.mc.timer.renderPartialTicks);
            float posZ = (float)((double)((float)entity.lastTickPosZ) + (entity.posZ - entity.lastTickPosZ) * (double)this.mc.timer.renderPartialTicks);
            this.draw2DCorner(entity, (double)posX - RenderManager.renderPosX, (double)posY - RenderManager.renderPosY, (double)posZ - RenderManager.renderPosZ, f\u00e4rbung);
        }
    }

    public static void renderTwoDimensionalESP(Entity e, int color, Render3DEvent render) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)(e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)render.getPartialTicks() - RenderManager.renderPosX), (double)(e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)render.getPartialTicks() - RenderManager.renderPosY + 1.0), (double)(e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)render.getPartialTicks() - RenderManager.renderPosZ));
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        GL11.glRotatef((float)(-Minecraft.thePlayer.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glDisable((int)2929);
        double extraDist = 0.2;
        if (mode) {
            RenderHelper.drawBorderedRect(-e.width, (float)((double)(-e.height / 2.0f) - extraDist), e.width, (float)((double)e.height - 0.55), 4.0f, color, 16777215);
        }
        GL11.glEnable((int)2929);
        GL11.glPopMatrix();
    }

    public void draw2DCorner(Entity e, double posX, double posY, double posZ, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        GL11.glNormal3f((float)0.0f, (float)0.0f, (float)0.0f);
        GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.func_179098_w();
        GlStateManager.depthMask(true);
        GuiUtils.drawRect(4.0f, -20.0f, 7.0f, -19.0f, color);
        GuiUtils.drawRect(-7.0f, -20.0f, -4.0f, -19.0f, color);
        GuiUtils.drawRect(6.0f, -20.0f, 7.0f, -17.5f, color);
        GuiUtils.drawRect(-7.0f, -20.0f, -6.0f, -17.5f, color);
        GuiUtils.drawRect(-7.0f, 2.0f, -4.0f, 3.0f, color);
        GuiUtils.drawRect(4.0f, 2.0f, 7.0f, 3.0f, color);
        GuiUtils.drawRect(-7.0f, 0.5f, -6.0f, 3.0f, color);
        GuiUtils.drawRect(6.0f, 0.5f, 7.0f, 3.0f, color);
        GuiUtils.drawRect(7.0f, -20.0f, 7.3f, -17.5f, -16777216);
        GuiUtils.drawRect(-7.3f, -20.0f, -7.0f, -17.5f, -16777216);
        GuiUtils.drawRect(4.0f, -20.3f, 7.3f, -20.0f, -16777216);
        GuiUtils.drawRect(-7.3f, -20.3f, -4.0f, -20.0f, -16777216);
        GuiUtils.drawRect(-7.0f, 3.0f, -4.0f, 3.3f, -16777216);
        GuiUtils.drawRect(4.0f, 3.0f, 7.0f, 3.3f, -16777216);
        GuiUtils.drawRect(-7.3f, 0.5f, -7.0f, 3.3f, -16777216);
        GuiUtils.drawRect(7.0f, 0.5f, 7.3f, 3.3f, -16777216);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)2896);
        GlStateManager.popMatrix();
    }

    public static void color(int color, float alpha) {
        float red = (float)(color >> 16 & 255) / 255.0f;
        float green = (float)(color >> 8 & 255) / 255.0f;
        float blue = (float)(color & 255) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void drawRectum(double g, double h, double i, double j, int col1) {
        float f = (float)(col1 >> 24 & 255) / 255.0f;
        float f2 = (float)(col1 >> 16 & 255) / 255.0f;
        float f3 = (float)(col1 >> 8 & 255) / 255.0f;
        float f4 = (float)(col1 & 255) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)i, (double)h);
        GL11.glVertex2d((double)g, (double)h);
        GL11.glVertex2d((double)g, (double)j);
        GL11.glVertex2d((double)i, (double)j);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    public static void drawBorderedRectum(double x, double y, double x2, double y2, float l1, int col1, int col2) {
        ESP2D.drawRectum(x, y, x2, y2, col2);
        float f = (float)(col1 >> 24 & 255) / 255.0f;
        float f2 = (float)(col1 >> 16 & 255) / 255.0f;
        float f3 = (float)(col1 >> 8 & 255) / 255.0f;
        float f4 = (float)(col1 & 255) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
        GL11.glLineWidth((float)l1);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }
}

