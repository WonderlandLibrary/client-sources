package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Render.EventRender2D;
import Ascii4UwUWareClient.API.Events.Render.EventRender3D;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.Manager.FriendManager;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.Math.Vec3f;
import Ascii4UwUWareClient.Util.Render.RenderUtil;
import Ascii4UwUWareClient.Util.Render.gl.GLUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ESP extends Module {
    private ArrayList<Vec3f> points = new ArrayList();
    public Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[]) ESPMode.values(), (Enum) ESPMode.Outline);
    public Numbers<Double> red = new Numbers<Double>("Red", "Red", 4.5, 1.0, 255.0, 1.0);
    public Numbers<Double> blue = new Numbers<Double>("Blue", "Blue", 4.5, 1.0, 255.0, 1.0);
    public Numbers<Double> green = new Numbers<Double>("Green", "Green", 4.5, 1.0, 255.0, 1.0);


    private static Map<EntityPlayer, float[][]> entities = new HashMap<>();

    public ESP() {
        super("Glow", new String[]{"outline", "wallhack"}, ModuleType.Render);
        this.addValues(this.mode, red, blue, green);
        int i = 0;
        while (i < 8) {
            this.points.add(new Vec3f());
            ++i;
        }
        this.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
    }

    @EventHandler
    public void onScreen(EventRender2D eventRender) {

    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        this.setSuffix(this.mode.getValue());
    }

    @EventHandler
    public void onRender(EventRender3D eventRender) {
    }


    private void render(Entity entity) {
        RenderManager renderManager = this.mc.getRenderManager();
        Vec3f offset = entity.interpolate(this.mc.timer.renderPartialTicks).sub(entity.getPos()).add(0.0D, 0.1D, 0.0D);
        if (!entity.isInvisible()) {
            AxisAlignedBB bb = entity.getEntityBoundingBox().offset(offset.getX() - RenderManager.renderPosX,
                    offset.getY() - RenderManager.renderPosY, offset.getZ() - RenderManager.renderPosZ);
            ((Vec3f) this.points.get(0)).setX(bb.minX).setY(bb.minY).setZ(bb.minZ);
            ((Vec3f) this.points.get(1)).setX(bb.maxX).setY(bb.minY).setZ(bb.minZ);
            ((Vec3f) this.points.get(2)).setX(bb.maxX).setY(bb.minY).setZ(bb.maxZ);
            ((Vec3f) this.points.get(3)).setX(bb.minX).setY(bb.minY).setZ(bb.maxZ);
            ((Vec3f) this.points.get(4)).setX(bb.minX).setY(bb.maxY).setZ(bb.minZ);
            ((Vec3f) this.points.get(5)).setX(bb.maxX).setY(bb.maxY).setZ(bb.minZ);
            ((Vec3f) this.points.get(6)).setX(bb.maxX).setY(bb.maxY).setZ(bb.maxZ);
            ((Vec3f) this.points.get(7)).setX(bb.minX).setY(bb.maxY).setZ(bb.maxZ);
            float left = Float.MAX_VALUE;
            float right = 0.0F;
            float top = Float.MAX_VALUE;
            float bottom = 0.0F;
            Iterator var11 = this.points.iterator();

            while (var11.hasNext()) {
                Vec3f living = (Vec3f) var11.next();
                Vec3f screen = living.toScreen();
                if (screen.getZ() >= 0.0D && screen.getZ() < 1.0D) {
                    if (screen.getX() < (double) left) {
                        left = (float) screen.getX();
                    }

                    if (screen.getY() < (double) top) {
                        top = (float) screen.getY();
                    }

                    if (screen.getX() > (double) right) {
                        right = (float) screen.getX();
                    }

                    if (screen.getY() > (double) bottom) {
                        bottom = (float) screen.getY();
                    }
                }
            }

            if (bottom > 1.0F || right > 1.0F) {
                this.box(left, top, right, bottom);
                this.name(entity, left, top, right, bottom);
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase living1 = (EntityLivingBase) entity;
                    this.health(living1, left, top, right, bottom);
                }
            }
        }
    }

    private static void drawVerticalLine(double xPos, double yPos, double xSize, double thickness, Color color) {
        Tessellator tesselator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tesselator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(xPos - xSize, yPos - thickness / 2.0D, 0.0D).color((float) color.getRed() / 255.0F,
                (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F)
                .endVertex();
        worldRenderer.pos(xPos - xSize, yPos + thickness / 2.0D, 0.0D).color((float) color.getRed() / 255.0F,
                (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F)
                .endVertex();
        worldRenderer.pos(xPos + xSize, yPos + thickness / 2.0D, 0.0D).color((float) color.getRed() / 255.0F,
                (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F)
                .endVertex();
        worldRenderer.pos(xPos + xSize, yPos - thickness / 2.0D, 0.0D).color((float) color.getRed() / 255.0F,
                (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F)
                .endVertex();
        tesselator.draw();
    }

    private void drawHorizontalLine(double xPos, double yPos, double ySize, double thickness, Color color) {
        Tessellator tesselator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tesselator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(xPos - thickness / 2.0D, yPos - ySize, 0.0D).color((float) color.getRed() / 255.0F,
                (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F)
                .endVertex();
        worldRenderer.pos(xPos - thickness / 2.0D, yPos + ySize, 0.0D).color((float) color.getRed() / 255.0F,
                (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F)
                .endVertex();
        worldRenderer.pos(xPos + thickness / 2.0D, yPos + ySize, 0.0D).color((float) color.getRed() / 255.0F,
                (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F)
                .endVertex();
        worldRenderer.pos(xPos + thickness / 2.0D, yPos - ySize, 0.0D).color((float) color.getRed() / 255.0F,
                (float) color.getGreen() / 255.0F, (float) color.getBlue() / 255.0F, (float) color.getAlpha() / 255.0F)
                .endVertex();
        tesselator.draw();
    }

    private void box(float left, float top, float right, float bottom) {
        GL11.glColor4d(1.0D, 1.0D, 1.0D, 0.5D);
        RenderUtil.drawLine(left, top, right, top, 2.0F);
        RenderUtil.drawLine(left, bottom, right, bottom, 2.0F);
        RenderUtil.drawLine(left, top, left, bottom, 2.0F);
        RenderUtil.drawLine(right, top, right, bottom, 2.0F);
        RenderUtil.drawLine(left + 1.0F, top + 1.0F, right - 1.0F, top + 1.0F, 1.0F);
        RenderUtil.drawLine(left + 1.0F, bottom - 1.0F, right - 1.0F, bottom - 1.0F, 1.0F);
        RenderUtil.drawLine(left + 1.0F, top + 1.0F, left + 1.0F, bottom - 1.0F, 1.0F);
        RenderUtil.drawLine(right - 1.0F, top + 1.0F, right - 1.0F, bottom - 1.0F, 1.0F);
        RenderUtil.drawLine(left - 1.0F, top - 1.0F, right + 1.0F, top - 1.0F, 1.0F);
        RenderUtil.drawLine(left - 1.0F, bottom + 1.0F, right + 1.0F, bottom + 1.0F, 1.0F);
        RenderUtil.drawLine(left - 1.0F, top + 1.0F, left - 1.0F, bottom + 1.0F, 1.0F);
        RenderUtil.drawLine(right + 1.0F, top - 1.0F, right + 1.0F, bottom + 1.0F, 1.0F);
    }

    private void name(Entity entity, float left, float top, float right, float bottom) {
        this.mc.fontRendererObj.drawCenteredString(
                FriendManager.isFriend(entity.getName()) ? "\u00a7b" + FriendManager.getAlias(entity.getName())
                        : entity.getName(),
                (int) (left + right) / 2, (int) (top - (float) this.mc.fontRendererObj.FONT_HEIGHT - 2.0F + 1.0F), -1);
        if (((EntityPlayer) entity).getCurrentEquippedItem() != null) {
            String stack = ((EntityPlayer) entity).getCurrentEquippedItem().getDisplayName();
            this.mc.fontRendererObj.drawCenteredString(stack, (int) (left + right) / 2, (int) bottom, -1);
        }

    }

    private void health(EntityLivingBase entity, float left, float top, float right, float bottom) {
        float height = bottom - top;
        float currentHealth = entity.getHealth();
        float maxHealth = entity.getMaxHealth();
        float healthPercent = currentHealth / maxHealth;
        GLUtils.glColor(this.getHealthColor(entity));
        RenderUtil.drawLine(left - 5.0F, top + height * (1.0F - healthPercent) + 1.0F, left - 5.0F, bottom, 2.0F);
    }

    private int getHealthColor(EntityLivingBase player) {
        float f = player.getHealth();
        float f1 = player.getMaxHealth();
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | -16777216;
    }

    private boolean isValid(EntityLivingBase entity) {
        if (entity == this.mc.thePlayer) {
            return false;
        } else if (entity.getHealth() <= 0.0F) {
            return false;
        } else if (entity instanceof EntityPlayer) {
            return true;
        } else if (entity instanceof EntityAnimal) {
            return true;
        } else if (entity.isInvisible() && entity == this.mc.thePlayer) {
            return true;
        } else {
            return entity instanceof EntityMob;
        }
    }

    public static enum ESPMode {
        Outline, Shader;
    }

    public final boolean isItem(final Entity entity) {
        return (entity instanceof EntityItem);
    }

    private void drawSkeleton(final EventRender3D event, final EntityPlayer e) {
        final Color color = new Color(
                e.getName().equalsIgnoreCase(mc.thePlayer.getName()) ? -6684775 : new Color(16775672).getRGB());
        if (!e.isInvisible()) {
            final float[][] entPos = entities.get(e);
            if (entPos != null && e.isEntityAlive() /*&& !e.isDead*/ && e != mc.thePlayer && !e.isPlayerSleeping()) {
                GL11.glPushMatrix();
                GL11.glLineWidth(1.3F);
                if (e.hurtTime > 0) {
                    GL11.glColor3f(150f, 0f, 0f);
                } else
                    GlStateManager.color((float) (color.getRed() / 255), (float) (color.getGreen() / 255),
                            (float) (color.getBlue() / 255), 1.0f);

                final Vec3 vec = this.getVec3(e);
                final double x = vec.xCoord - RenderManager.renderPosX;
                final double y = vec.yCoord - RenderManager.renderPosY;
                final double z = vec.zCoord - RenderManager.renderPosZ;
                GL11.glTranslated(x, y, z);
                final float xOff = e.prevRenderYawOffset
                        + (e.renderYawOffset - e.prevRenderYawOffset) * event.getPartialTicks();
                GL11.glRotatef(-xOff, 0.0f, 1.0f, 0.0f);
                GL11.glTranslated(0.0, 0.0, e.isSneaking() ? -0.235 : 0.0);
                final float yOff = e.isSneaking() ? 0.6f : 0.75f;
                GL11.glPushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glTranslated(-0.125, yOff, 0.0);
                if (entPos[3][0] != 0.0f) {
                    GL11.glRotatef(entPos[3][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                if (entPos[3][1] != 0.0f) {
                    GL11.glRotatef(entPos[3][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (entPos[3][2] != 0.0f) {
                    GL11.glRotatef(entPos[3][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, -yOff, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glTranslated(0.125, yOff, 0.0);
                if (entPos[4][0] != 0.0f) {
                    GL11.glRotatef(entPos[4][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                if (entPos[4][1] != 0.0f) {
                    GL11.glRotatef(entPos[4][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (entPos[4][2] != 0.0f) {
                    GL11.glRotatef(entPos[4][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, -yOff, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glTranslated(0.0, 0.0, e.isSneaking() ? 0.25 : 0.0);
                GL11.glPushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glTranslated(0.0, e.isSneaking() ? -0.05 : 0.0, e.isSneaking() ? -0.01725 : 0.0);
                GL11.glPushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glTranslated(-0.375, yOff + 0.55, 0.0);
                if (entPos[1][0] != 0.0f) {
                    GL11.glRotatef(entPos[1][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                if (entPos[1][1] != 0.0f) {
                    GL11.glRotatef(entPos[1][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (entPos[1][2] != 0.0f) {
                    GL11.glRotatef(-entPos[1][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, -0.5, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.375, yOff + 0.55, 0.0);
                if (entPos[2][0] != 0.0f) {
                    GL11.glRotatef(entPos[2][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                if (entPos[2][1] != 0.0f) {
                    GL11.glRotatef(entPos[2][1] * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (entPos[2][2] != 0.0f) {
                    GL11.glRotatef(-entPos[2][2] * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, -0.5, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glRotatef(xOff - e.rotationYawHead, 0.0f, 1.0f, 0.0f);
                GL11.glPushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glTranslated(0.0, yOff + 0.55, 0.0);
                if (entPos[0][0] != 0.0f) {
                    GL11.glRotatef(entPos[0][0] * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, 0.3, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GL11.glRotatef(e.isSneaking() ? 25.0f : 0.0f, 1.0f, 0.0f, 0.0f);
                GL11.glTranslated(0.0, e.isSneaking() ? -0.16175 : 0.0, e.isSneaking() ? -0.48025 : 0.0);
                GL11.glPushMatrix();
                GL11.glTranslated(0.0, yOff, 0.0);
                GL11.glBegin(3);
                GL11.glVertex3d(-0.125, 0.0, 0.0);
                GL11.glVertex3d(0.125, 0.0, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glTranslated(0.0, yOff, 0.0);
                GL11.glBegin(3);
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(0.0, 0.55, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glTranslated(0.0, yOff + 0.55, 0.0);
                GL11.glBegin(3);
                GL11.glVertex3d(-0.375, 0.0, 0.0);
                GL11.glVertex3d(0.375, 0.0, 0.0);
                GL11.glEnd();
                GL11.glPopMatrix();
                GL11.glPopMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }

    private Vec3 getVec3(final EntityPlayer var0) {
        final float timer = mc.timer.renderPartialTicks;
        final double x = var0.lastTickPosX + (var0.posX - var0.lastTickPosX) * timer;
        final double y = var0.lastTickPosY + (var0.posY - var0.lastTickPosY) * timer;
        final double z = var0.lastTickPosZ + (var0.posZ - var0.lastTickPosZ) * timer;
        return new Vec3(x, y, z);
    }

}