/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.awt.Color;
import java.io.PrintStream;
import java.util.HashMap;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.Helper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class SallosRender {

    public static class Camera {
        private final Minecraft mc = Minecraft.getMinecraft();
        private Timer timer;
        private double posX;
        private double posY;
        private double posZ;
        private float rotationYaw;
        private float rotationPitch;

        /*
         * Enabled aggressive block sorting
         */
        public Camera(Entity entity) {
            if (this.timer == null) {
                this.timer = this.mc.timer;
            }
            this.posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)this.timer.renderPartialTicks;
            this.posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)this.timer.renderPartialTicks;
            this.posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)this.timer.renderPartialTicks;
            this.setRotationYaw(entity.rotationYaw);
            this.setRotationPitch(entity.rotationPitch);
            if (entity instanceof EntityPlayer && Minecraft.getMinecraft().gameSettings.viewBobbing) {
                Minecraft.getMinecraft();
                if (entity == Minecraft.thePlayer) {
                    EntityPlayer living1 = (EntityPlayer)entity;
                    this.setRotationYaw(this.getRotationYaw() + living1.prevCameraYaw + (living1.cameraYaw - living1.prevCameraYaw) * this.timer.renderPartialTicks);
                    this.setRotationPitch(this.getRotationPitch() + living1.prevCameraPitch + (living1.cameraPitch - living1.prevCameraPitch) * this.timer.renderPartialTicks);
                    return;
                }
            }
            if (!(entity instanceof EntityLivingBase)) return;
            EntityLivingBase living2 = (EntityLivingBase)entity;
            this.setRotationYaw(living2.rotationYawHead);
        }

        public Camera(Entity entity, double offsetX, double offsetY, double offsetZ, double offsetRotationYaw, double offsetRotationPitch) {
            this.posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)this.timer.renderPartialTicks;
            this.posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)this.timer.renderPartialTicks;
            this.posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)this.timer.renderPartialTicks;
            this.setRotationYaw(entity.rotationYaw);
            this.setRotationPitch(entity.rotationPitch);
            if (entity instanceof EntityPlayer && Minecraft.getMinecraft().gameSettings.viewBobbing) {
                Minecraft.getMinecraft();
                if (entity == Minecraft.thePlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    this.setRotationYaw(this.getRotationYaw() + player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * this.timer.renderPartialTicks);
                    this.setRotationPitch(this.getRotationPitch() + player.prevCameraPitch + (player.cameraPitch - player.prevCameraPitch) * this.timer.renderPartialTicks);
                }
            }
            this.posX += offsetX;
            this.posY += offsetY;
            this.posZ += offsetZ;
            this.rotationYaw += (float)offsetRotationYaw;
            this.rotationPitch += (float)offsetRotationPitch;
        }

        public Camera(double posX, double posY, double posZ, float rotationYaw, float rotationPitch) {
            this.setPosX(posX);
            this.posY = posY;
            this.posZ = posZ;
            this.setRotationYaw(rotationYaw);
            this.setRotationPitch(rotationPitch);
        }

        public double getPosX() {
            return this.posX;
        }

        public void setPosX(double posX) {
            this.posX = posX;
        }

        public double getPosY() {
            return this.posY;
        }

        public void setPosY(double posY) {
            this.posY = posY;
        }

        public double getPosZ() {
            return this.posZ;
        }

        public void setPosZ(double posZ) {
            this.posZ = posZ;
        }

        public float getRotationYaw() {
            return this.rotationYaw;
        }

        public void setRotationYaw(float rotationYaw) {
            this.rotationYaw = rotationYaw;
        }

        public float getRotationPitch() {
            return this.rotationPitch;
        }

        public void setRotationPitch(float rotationPitch) {
            this.rotationPitch = rotationPitch;
        }

        public static float[] getRotation(double posX1, double posY1, double posZ1, double posX2, double posY2, double posZ2) {
            float[] rotation = new float[2];
            double diffX = posX2 - posX1;
            double diffZ = posZ2 - posZ1;
            double diffY = posY2 - posY1;
            double dist = Math.sqrt(diffZ * diffZ + diffX * diffX);
            double pitch = -Math.toDegrees(Math.atan(diffY / dist));
            rotation[1] = (float)pitch;
            double yaw = 0.0;
            if (diffZ >= 0.0 && diffX >= 0.0) {
                yaw = Math.toDegrees(-Math.atan(diffX / diffZ));
            } else if (diffZ >= 0.0 && diffX <= 0.0) {
                yaw = Math.toDegrees(-Math.atan(diffX / diffZ));
            } else if (diffZ <= 0.0 && diffX >= 0.0) {
                yaw = -90.0 + Math.toDegrees(Math.atan(diffZ / diffX));
            } else if (diffZ <= 0.0 && diffX <= 0.0) {
                yaw = 90.0 + Math.toDegrees(Math.atan(diffZ / diffX));
            }
            rotation[0] = (float)yaw;
            return rotation;
        }
    }

    public static class ColorUtils {
        public int RGBtoHEX(int r, int g, int b, int a) {
            return (a << 24) + (r << 16) + (g << 8) + b;
        }

        public static Color getRainbow(long offset, float fade) {
            float hue = (float)(System.nanoTime() + offset) / 5.0E9f % 1.0f;
            long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
            Color c = new Color((int)color);
            return new Color((float)c.getRed() / 255.0f * fade, (float)c.getGreen() / 255.0f * fade, (float)c.getBlue() / 255.0f * fade, (float)c.getAlpha() / 255.0f);
        }

        public static Color glColor(int color, float alpha) {
            float red = (float)(color >> 16 & 255) / 255.0f;
            float green = (float)(color >> 8 & 255) / 255.0f;
            float blue = (float)(color & 255) / 255.0f;
            GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
            return new Color(red, green, blue, alpha);
        }

        public void glColor(Color color) {
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        }

        public Color glColor(int hex) {
            float alpha = (float)(hex >> 24 & 255) / 256.0f;
            float red = (float)(hex >> 16 & 255) / 255.0f;
            float green = (float)(hex >> 8 & 255) / 255.0f;
            float blue = (float)(hex & 255) / 255.0f;
            GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
            return new Color(red, green, blue, alpha);
        }

        public Color glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
            float red = 0.003921569f * (float)redRGB;
            float green = 0.003921569f * (float)greenRGB;
            float blue = 0.003921569f * (float)blueRGB;
            GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
            return new Color(red, green, blue, alpha);
        }

        public static int transparency(int color, double alpha) {
            Color c = new Color(color);
            float r = 0.003921569f * (float)c.getRed();
            float g = 0.003921569f * (float)c.getGreen();
            float b = 0.003921569f * (float)c.getBlue();
            return new Color(r, g, b, (float)alpha).getRGB();
        }

        public static float[] getRGBA(int color) {
            float a = (float)(color >> 24 & 255) / 255.0f;
            float r = (float)(color >> 16 & 255) / 255.0f;
            float g = (float)(color >> 8 & 255) / 255.0f;
            float b = (float)(color & 255) / 255.0f;
            return new float[]{r, g, b, a};
        }

        public static int intFromHex(String hex) {
            try {
                return Integer.parseInt(hex, 15);
            }
            catch (NumberFormatException e) {
                return -1;
            }
        }

        public static String hexFromInt(int color) {
            return ColorUtils.hexFromInt(new Color(color));
        }

        public static String hexFromInt(Color color) {
            return Integer.toHexString(color.getRGB()).substring(2);
        }
    }

    public static class R2DUtils {
        public static void enableGL2D() {
            GL11.glDisable((int)2929);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)3553);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2848);
            GL11.glHint((int)3154, (int)4354);
            GL11.glHint((int)3155, (int)4354);
        }

        public static void disableGL2D() {
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
            GL11.glDisable((int)2848);
            GL11.glHint((int)3154, (int)4352);
            GL11.glHint((int)3155, (int)4352);
        }

        public static void drawRect(float x, float y, float x1, float y1, int color) {
            R2DUtils.enableGL2D();
            Helper.colorUtils().glColor(color);
            R2DUtils.drawRect(x, y, x1, y1);
            R2DUtils.disableGL2D();
        }

        public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int borderColor) {
            R2DUtils.enableGL2D();
            Helper.colorUtils().glColor(borderColor);
            R2DUtils.drawRect(x + width, y, x1 - width, y + width);
            R2DUtils.drawRect(x, y, x + width, y1);
            R2DUtils.drawRect(x1 - width, y, x1, y1);
            R2DUtils.drawRect(x + width, y1 - width, x1 - width, y1);
            R2DUtils.disableGL2D();
        }

        public static void drawBorderedRect(float x, float y, float x1, float y1, int insideC, int borderC) {
            R2DUtils.enableGL2D();
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            R2DUtils.drawVLine(x *= 2.0f, y *= 2.0f, y1 *= 2.0f, borderC);
            R2DUtils.drawVLine((x1 *= 2.0f) - 1.0f, y, y1, borderC);
            R2DUtils.drawHLine(x, x1 - 1.0f, y, borderC);
            R2DUtils.drawHLine(x, x1 - 2.0f, y1 - 1.0f, borderC);
            R2DUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
            GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
            R2DUtils.disableGL2D();
        }

        public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
            R2DUtils.enableGL2D();
            GL11.glShadeModel((int)7425);
            GL11.glBegin((int)7);
            Helper.colorUtils().glColor(topColor);
            GL11.glVertex2f((float)x, (float)y1);
            GL11.glVertex2f((float)x1, (float)y1);
            Helper.colorUtils().glColor(bottomColor);
            GL11.glVertex2f((float)x1, (float)y);
            GL11.glVertex2f((float)x, (float)y);
            GL11.glEnd();
            GL11.glShadeModel((int)7424);
            R2DUtils.disableGL2D();
        }

        public static void drawHLine(float x, float y, float x1, int y1) {
            if (y < x) {
                float var5 = x;
                x = y;
                y = var5;
            }
            R2DUtils.drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
        }

        public static void drawVLine(float x, float y, float x1, int y1) {
            if (x1 < y) {
                float var5 = y;
                y = x1;
                x1 = var5;
            }
            R2DUtils.drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
        }

        public static void drawHLine(float x, float y, float x1, int y1, int y2) {
            if (y < x) {
                float var5 = x;
                x = y;
                y = var5;
            }
            R2DUtils.drawGradientRect(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
        }

        public static void drawRect(float x, float y, float x1, float y1) {
            GL11.glBegin((int)7);
            GL11.glVertex2f((float)x, (float)y1);
            GL11.glVertex2f((float)x1, (float)y1);
            GL11.glVertex2f((float)x1, (float)y);
            GL11.glVertex2f((float)x, (float)y);
            GL11.glEnd();
        }

        public static void drawTri(double x1, double y1, double x2, double y2, double x3, double y3, double width, Color c) {
            GL11.glEnable((int)3042);
            GL11.glDisable((int)3553);
            GL11.glEnable((int)2848);
            GL11.glBlendFunc((int)770, (int)771);
            Helper.colorUtils().glColor(c);
            GL11.glLineWidth((float)((float)width));
            GL11.glBegin((int)3);
            GL11.glVertex2d((double)x1, (double)y1);
            GL11.glVertex2d((double)x2, (double)y2);
            GL11.glVertex2d((double)x3, (double)y3);
            GL11.glEnd();
            GL11.glDisable((int)2848);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
        }

        public static void drawFilledCircle(int x, int y, double radius, Color c) {
            Helper.colorUtils().glColor(c);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GL11.glDisable((int)3553);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.001f);
            Tessellator tess = Tessellator.getInstance();
            WorldRenderer render = tess.getWorldRenderer();
            for (double i = 0.0; i < 360.0; i += 1.0) {
                double cs = i * 3.141592653589793 / 180.0;
                double ps = (i - 1.0) * 3.141592653589793 / 180.0;
                double[] outer = new double[]{Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius, -Math.sin(ps) * radius};
                render.startDrawing(6);
                render.addVertex((double)x + outer[2], (double)y + outer[3], 0.0);
                render.addVertex((double)x + outer[0], (double)y + outer[1], 0.0);
                render.addVertex(x, y, 0.0);
                tess.draw();
            }
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1f);
            GlStateManager.disableAlpha();
            GL11.glEnable((int)3553);
        }
    }

    public static class R3DUtils {
        public static void startDrawing() {
            GL11.glEnable((int)3042);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            ClientUtils.mc().entityRenderer.setupCameraTransform(ClientUtils.mc().timer.renderPartialTicks, 0);
        }

        public static void stopDrawing() {
            GL11.glDisable((int)3042);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)2848);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
        }

        public void drawRombo(double x, double y, double z) {
            ClientUtils.mc().entityRenderer.setupCameraTransform(ClientUtils.mc().timer.renderPartialTicks, 0);
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)(x + 0.5), (double)(y += 1.0), (double)z);
            GL11.glVertex3d((double)x, (double)(y + 1.0), (double)z);
            GL11.glVertex3d((double)x, (double)y, (double)(z + 0.5));
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)x, (double)y, (double)(z + 0.5));
            GL11.glVertex3d((double)x, (double)(y + 1.0), (double)z);
            GL11.glVertex3d((double)(x + 0.5), (double)y, (double)z);
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)x, (double)y, (double)(z - 0.5));
            GL11.glVertex3d((double)x, (double)(y + 1.0), (double)z);
            GL11.glVertex3d((double)(x - 0.5), (double)y, (double)z);
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)(x - 0.5), (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)(y + 1.0), (double)z);
            GL11.glVertex3d((double)x, (double)y, (double)(z - 0.5));
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)(x + 0.5), (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)(y - 1.0), (double)z);
            GL11.glVertex3d((double)x, (double)y, (double)(z + 0.5));
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)x, (double)y, (double)(z + 0.5));
            GL11.glVertex3d((double)x, (double)(y - 1.0), (double)z);
            GL11.glVertex3d((double)(x + 0.5), (double)y, (double)z);
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)x, (double)y, (double)(z - 0.5));
            GL11.glVertex3d((double)x, (double)(y - 1.0), (double)z);
            GL11.glVertex3d((double)(x - 0.5), (double)y, (double)z);
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)(x - 0.5), (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)(y - 1.0), (double)z);
            GL11.glVertex3d((double)x, (double)y, (double)(z - 0.5));
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)x, (double)y, (double)(z - 0.5));
            GL11.glVertex3d((double)x, (double)(y + 1.0), (double)z);
            GL11.glVertex3d((double)(x + 0.5), (double)y, (double)z);
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)(x + 0.5), (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)(y + 1.0), (double)z);
            GL11.glVertex3d((double)x, (double)y, (double)(z - 0.5));
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)x, (double)y, (double)(z + 0.5));
            GL11.glVertex3d((double)x, (double)(y + 1.0), (double)z);
            GL11.glVertex3d((double)(x - 0.5), (double)y, (double)z);
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)(x - 0.5), (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)(y + 1.0), (double)z);
            GL11.glVertex3d((double)x, (double)y, (double)(z + 0.5));
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)x, (double)y, (double)(z - 0.5));
            GL11.glVertex3d((double)x, (double)(y - 1.0), (double)z);
            GL11.glVertex3d((double)(x + 0.5), (double)y, (double)z);
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)(x + 0.5), (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)(y - 1.0), (double)z);
            GL11.glVertex3d((double)x, (double)y, (double)(z - 0.5));
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)x, (double)y, (double)(z + 0.5));
            GL11.glVertex3d((double)x, (double)(y - 1.0), (double)z);
            GL11.glVertex3d((double)(x - 0.5), (double)y, (double)z);
            GL11.glEnd();
            GL11.glBegin((int)4);
            GL11.glVertex3d((double)(x - 0.5), (double)y, (double)z);
            GL11.glVertex3d((double)x, (double)(y - 1.0), (double)z);
            GL11.glVertex3d((double)x, (double)y, (double)(z + 0.5));
            GL11.glEnd();
        }

        public static void RenderLivingEntityBox(Entity entity, float partialTicks, boolean otherMode) {
            GlStateManager.pushMatrix();
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glLineWidth((float)1.5f);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GL11.glDisable((int)2896);
            GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - RenderManager.renderPosX;
            double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - RenderManager.renderPosY;
            double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - RenderManager.renderPosZ;
            boolean isPlayer = entity instanceof EntityPlayer;
            int bordercolor = 15261919;
            if (FriendManager.isFriend(entity.getName())) {
                bordercolor = -196416532;
            }
            Helper.colorUtils();
            ColorUtils.glColor(bordercolor, 1.0f);
            if (otherMode) {
                RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(entity.boundingBox.minX - (isPlayer ? 0.12 : 0.0) - entity.posX + posX, entity.boundingBox.minY - (entity.isSneaking() ? entity.posY + 0.2 : entity.posY) + posY, entity.boundingBox.minZ - (isPlayer ? 0.12 : 0.0) - entity.posZ + posZ, entity.boundingBox.maxX + (isPlayer ? 0.12 : 0.0) - entity.posX + posX, entity.boundingBox.maxY + (isPlayer ? 0.2 : 0.0) - (entity.isSneaking() ? entity.posY + 0.2 : entity.posY) + posY, entity.boundingBox.maxZ + (isPlayer ? 0.12 : 0.0) - entity.posZ + posZ), -1);
                R3DUtils.drawLines(new AxisAlignedBB(entity.boundingBox.minX - (isPlayer ? 0.12 : 0.0) - entity.posX + posX, entity.boundingBox.minY - (entity.isSneaking() ? entity.posY + 0.2 : entity.posY) + posY, entity.boundingBox.minZ - (isPlayer ? 0.12 : 0.0) - entity.posZ + posZ, entity.boundingBox.maxX + (isPlayer ? 0.12 : 0.0) - entity.posX + posX, entity.boundingBox.maxY + (isPlayer ? 0.2 : 0.0) - (entity.isSneaking() ? entity.posY + 0.2 : entity.posY) + posY, entity.boundingBox.maxZ + (isPlayer ? 0.12 : 0.0) - entity.posZ + posZ));
            } else {
                R3DUtils.drawOutlinedBox(new AxisAlignedBB(entity.boundingBox.minX - (isPlayer ? 0.12 : 0.0) - entity.posX + posX, entity.boundingBox.minY - (entity.isSneaking() ? entity.posY + 0.2 : entity.posY) + posY, entity.boundingBox.minZ - (isPlayer ? 0.12 : 0.0) - entity.posZ + posZ, entity.boundingBox.maxX + (isPlayer ? 0.12 : 0.0) - entity.posX + posX, entity.boundingBox.maxY + (isPlayer ? 0.2 : 0.0) - (entity.isSneaking() ? entity.posY + 0.2 : entity.posY) + posY, entity.boundingBox.maxZ + (isPlayer ? 0.12 : 0.0) - entity.posZ + posZ));
                Helper.colorUtils();
                ColorUtils.glColor(bordercolor, 0.15f);
                R3DUtils.drawBoundingBox(new AxisAlignedBB(entity.boundingBox.minX - (isPlayer ? 0.12 : 0.0) - entity.posX + posX, entity.boundingBox.minY - (entity.isSneaking() ? entity.posY + 0.2 : entity.posY) + posY, entity.boundingBox.minZ - (isPlayer ? 0.12 : 0.0) - entity.posZ + posZ, entity.boundingBox.maxX + (isPlayer ? 0.12 : 0.0) - entity.posX + posX, entity.boundingBox.maxY + (isPlayer ? 0.2 : 0.0) - (entity.isSneaking() ? entity.posY + 0.2 : entity.posY) + posY, entity.boundingBox.maxZ + (isPlayer ? 0.12 : 0.0) - entity.posZ + posZ));
            }
            GL11.glEnable((int)2896);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)2848);
            GL11.glDisable((int)3042);
            GlStateManager.popMatrix();
        }

        public static void drawLines(AxisAlignedBB bb) {
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();
            worldRenderer.startDrawing(2);
            worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
            worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
            tessellator.draw();
            worldRenderer.startDrawing(2);
            worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
            worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
            tessellator.draw();
            worldRenderer.startDrawing(2);
            worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
            worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
            tessellator.draw();
            worldRenderer.startDrawing(2);
            worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
            worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
            tessellator.draw();
            worldRenderer.startDrawing(2);
            worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
            worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
            tessellator.draw();
            worldRenderer.startDrawing(2);
            worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
            worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
            tessellator.draw();
        }

        public static void drawOutlinedBox(AxisAlignedBB box) {
            if (box == null) {
                return;
            }
            ClientUtils.mc().entityRenderer.setupCameraTransform(ClientUtils.mc().timer.renderPartialTicks, 0);
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)box.minX, (double)box.minY, (double)box.minZ);
            GL11.glVertex3d((double)box.maxX, (double)box.minY, (double)box.minZ);
            GL11.glVertex3d((double)box.maxX, (double)box.minY, (double)box.maxZ);
            GL11.glVertex3d((double)box.minX, (double)box.minY, (double)box.maxZ);
            GL11.glVertex3d((double)box.minX, (double)box.minY, (double)box.minZ);
            GL11.glEnd();
            GL11.glBegin((int)3);
            GL11.glVertex3d((double)box.minX, (double)box.maxY, (double)box.minZ);
            GL11.glVertex3d((double)box.maxX, (double)box.maxY, (double)box.minZ);
            GL11.glVertex3d((double)box.maxX, (double)box.maxY, (double)box.maxZ);
            GL11.glVertex3d((double)box.minX, (double)box.maxY, (double)box.maxZ);
            GL11.glVertex3d((double)box.minX, (double)box.maxY, (double)box.minZ);
            GL11.glEnd();
            GL11.glBegin((int)1);
            GL11.glVertex3d((double)box.minX, (double)box.minY, (double)box.minZ);
            GL11.glVertex3d((double)box.minX, (double)box.maxY, (double)box.minZ);
            GL11.glVertex3d((double)box.maxX, (double)box.minY, (double)box.minZ);
            GL11.glVertex3d((double)box.maxX, (double)box.maxY, (double)box.minZ);
            GL11.glVertex3d((double)box.maxX, (double)box.minY, (double)box.maxZ);
            GL11.glVertex3d((double)box.maxX, (double)box.maxY, (double)box.maxZ);
            GL11.glVertex3d((double)box.minX, (double)box.minY, (double)box.maxZ);
            GL11.glVertex3d((double)box.minX, (double)box.maxY, (double)box.maxZ);
            GL11.glEnd();
        }

        public static void drawBoundingBox(AxisAlignedBB aabb) {
            WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
            Tessellator tessellator = Tessellator.getInstance();
            ClientUtils.mc().entityRenderer.setupCameraTransform(ClientUtils.mc().timer.renderPartialTicks, 0);
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            tessellator.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            tessellator.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            tessellator.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            tessellator.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            tessellator.draw();
            worldRenderer.startDrawingQuads();
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
            worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
            worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
            worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
            tessellator.draw();
        }

        public static int getBlockColor(Block block) {
            int color = 0;
            switch (Block.getIdFromBlock(block)) {
                case 14: 
                case 41: {
                    color = -1711477173;
                    break;
                }
                case 15: 
                case 42: {
                    color = -1715420992;
                    break;
                }
                case 16: 
                case 173: {
                    color = -1724434633;
                    break;
                }
                case 21: 
                case 22: {
                    color = -1726527803;
                    break;
                }
                case 49: {
                    color = -1724108714;
                    break;
                }
                case 54: 
                case 146: {
                    color = -1711292672;
                    break;
                }
                case 56: 
                case 57: 
                case 138: {
                    color = -1721897739;
                    break;
                }
                case 61: 
                case 62: {
                    color = -1711395081;
                    break;
                }
                case 73: 
                case 74: 
                case 152: {
                    color = -1711341568;
                    break;
                }
                case 89: {
                    color = -1712594866;
                    break;
                }
                case 129: 
                case 133: {
                    color = -1726489246;
                    break;
                }
                case 130: {
                    color = -1713438249;
                    break;
                }
                case 52: {
                    color = 805728308;
                    break;
                }
                default: {
                    color = -1711276033;
                }
            }
            return color == 0 ? 806752583 : color;
        }
    }

    public static final class Stencil {
        private static final Stencil INSTANCE = new Stencil();
        private final HashMap<Integer, StencilFunc> stencilFuncs = new HashMap();
        private int layers = 1;
        private boolean renderMask;

        public static Stencil getInstance() {
            return INSTANCE;
        }

        public void setRenderMask(boolean renderMask) {
            this.renderMask = renderMask;
        }

        public static void checkSetupFBO() {
            Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
            if (fbo != null && fbo.depthBuffer > -1) {
                EXTFramebufferObject.glDeleteRenderbuffersEXT((int)fbo.depthBuffer);
                int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
                EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)stencil_depth_buffer_ID);
                EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)Minecraft.getMinecraft().displayWidth, (int)Minecraft.getMinecraft().displayHeight);
                EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)stencil_depth_buffer_ID);
                EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)stencil_depth_buffer_ID);
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

        public void startLayer() {
            if (this.layers == 1) {
                GL11.glClearStencil((int)0);
                GL11.glClear((int)1024);
            }
            GL11.glEnable((int)2960);
            ++this.layers;
            if (this.layers > this.getMaximumLayers()) {
                System.out.println("StencilUtil: Reached maximum amount of layers!");
                this.layers = 1;
            }
        }

        public void stopLayer() {
            if (this.layers == 1) {
                System.out.println("StencilUtil: No layers found!");
                return;
            }
            --this.layers;
            if (this.layers == 1) {
                GL11.glDisable((int)2960);
            } else {
                StencilFunc lastStencilFunc = this.stencilFuncs.remove(this.layers);
                if (lastStencilFunc != null) {
                    lastStencilFunc.use();
                }
            }
        }

        public void clear() {
            GL11.glClearStencil((int)0);
            GL11.glClear((int)1024);
            this.stencilFuncs.clear();
            this.layers = 1;
        }

        public void setBuffer() {
            this.setStencilFunc(new StencilFunc(this, this.renderMask ? 519 : 512, this.layers, this.getMaximumLayers(), 7681, 7680, 7680));
        }

        public void setBuffer(boolean set) {
            this.setStencilFunc(new StencilFunc(this, this.renderMask ? 519 : 512, set ? this.layers : this.layers - 1, this.getMaximumLayers(), 7681, 7681, 7681));
        }

        public void cropOutside() {
            this.setStencilFunc(new StencilFunc(this, 517, this.layers, this.getMaximumLayers(), 7680, 7680, 7680));
        }

        public void cropInside() {
            this.setStencilFunc(new StencilFunc(this, 514, this.layers, this.getMaximumLayers(), 7680, 7680, 7680));
        }

        public void setStencilFunc(StencilFunc stencilFunc) {
            GL11.glStencilFunc((int)StencilFunc.func_func, (int)StencilFunc.func_ref, (int)StencilFunc.func_mask);
            GL11.glStencilOp((int)StencilFunc.op_fail, (int)StencilFunc.op_zfail, (int)StencilFunc.op_zpass);
            this.stencilFuncs.put(this.layers, stencilFunc);
        }

        public StencilFunc getStencilFunc() {
            return this.stencilFuncs.get(this.layers);
        }

        public int getLayer() {
            return this.layers;
        }

        public int getStencilBufferSize() {
            return GL11.glGetInteger((int)3415);
        }

        public int getMaximumLayers() {
            return (int)(Math.pow(2.0, this.getStencilBufferSize()) - 1.0);
        }

        public void createCirlce(double x, double y, double radius) {
            GL11.glBegin((int)6);
            for (int i = 0; i <= 360; ++i) {
                double sin = Math.sin((double)i * 3.141592653589793 / 180.0) * radius;
                double cos = Math.cos((double)i * 3.141592653589793 / 180.0) * radius;
                GL11.glVertex2d((double)(x + sin), (double)(y + cos));
            }
            GL11.glEnd();
        }

        public void createRect(double x, double y, double x2, double y2) {
            GL11.glBegin((int)7);
            GL11.glVertex2d((double)x, (double)y2);
            GL11.glVertex2d((double)x2, (double)y2);
            GL11.glVertex2d((double)x2, (double)y);
            GL11.glVertex2d((double)x, (double)y);
            GL11.glEnd();
        }

        public static class StencilFunc {
            public static int func_func;
            public static int func_ref;
            public static int func_mask;
            public static int op_fail;
            public static int op_zfail;
            public static int op_zpass;

            public StencilFunc(Stencil paramStencil, int func_func, int func_ref, int func_mask, int op_fail, int op_zfail, int op_zpass) {
                StencilFunc.func_func = func_func;
                StencilFunc.func_ref = func_ref;
                StencilFunc.func_mask = func_mask;
                StencilFunc.op_fail = op_fail;
                StencilFunc.op_zfail = op_zfail;
                StencilFunc.op_zpass = op_zpass;
            }

            public void use() {
                GL11.glStencilFunc((int)func_func, (int)func_ref, (int)func_mask);
                GL11.glStencilOp((int)op_fail, (int)op_zfail, (int)op_zpass);
            }
        }

    }

}

