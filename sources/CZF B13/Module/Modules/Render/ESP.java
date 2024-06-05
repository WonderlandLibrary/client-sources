package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Manager.FriendManager;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Combat.AntiBot;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.Math.Vec3f;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import gq.vapu.czfclient.Util.Render.gl.GLUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class ESP extends Module {
    public Mode<Enum> mode = new Mode("Mode", "Mode", ESPMode.values(), ESPMode.Other2D);
    public Option<Boolean> invisible = new Option<>("Invisible", "invisible", true);
    private final ArrayList<Vec3f> points = new ArrayList();

    public ESP() {
        super("ESP", new String[]{"outline", "wallhack"}, ModuleType.Render);
        this.addValues(this.mode, this.invisible);
        int i = 0;
        while (i < 8) {
            this.points.add(new Vec3f());
            ++i;
        }
        this.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
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

    @EventHandler
    public void onScreen(EventRender2D eventRender) {
        if (this.mode.getValue() == ESPMode.Other2D) {
            GlStateManager.pushMatrix();
            ScaledResolution scaledRes = new ScaledResolution(mc);
            double twoDscale = (double) scaledRes.getScaleFactor() / Math.pow(scaledRes.getScaleFactor(), 2.0);
            GlStateManager.scale(twoDscale, twoDscale, twoDscale);
            for (Object o : mc.theWorld.getLoadedEntityList()) {
                if (!(o instanceof EntityLivingBase) || o == mc.thePlayer || !(o instanceof EntityPlayer))
                    continue;
                EntityLivingBase ent = (EntityLivingBase) o;
                this.render(ent);
            }
            GlStateManager.popMatrix();
        }
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        this.setSuffix(this.mode.getValue());
    }

    @EventHandler
    public void onRender(EventRender3D eventRender) {
        if (this.mode.getValue() == ESPMode.New2D) {
            for (Object o : mc.theWorld.getLoadedEntityList()) {
                if (!(o instanceof EntityLivingBase) || o == mc.thePlayer || !(o instanceof EntityPlayer))
                    continue;
                EntityLivingBase ent = (EntityLivingBase) o;
                this.doCornerESP(ent);
            }
        }
    }

    private void doCornerESP(EntityLivingBase entity) {
        if (AntiBot.isServerBot(entity)) {
            return;
        }
        Iterator var2 = mc.theWorld.playerEntities.iterator();
        if (!entity.isInvisible() || (entity.isInvisible() && invisible.getValue())) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.enableBlend();
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            float partialTicks = mc.timer.renderPartialTicks;
            mc.getRenderManager();
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks
                    - RenderManager.renderPosX;
            mc.getRenderManager();
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks
                    - RenderManager.renderPosY;
            mc.getRenderManager();
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks
                    - RenderManager.renderPosZ;
            float DISTANCE = mc.thePlayer.getDistanceToEntity(entity);
            float DISTANCE_SCALE = Math.min(DISTANCE * 0.15F, 2.5F);
            float SCALE = 0.035F;
            SCALE /= 2.0F;
            GlStateManager.translate((float) x,
                    (float) y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2.0F : 0.0F), (float) z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-SCALE, -SCALE, -SCALE);
            Tessellator tesselator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tesselator.getWorldRenderer();
            Color color = new Color(-13330213);
            if (entity.hurtTime > 0) {
                color = new Color(255, 0, 0);
            }
            double thickness = 2.0F + DISTANCE * 0.08F;
            double xLeft = -30.0D;
            double xRight = 30.0D;
            double yUp = (entity.isSneaking()) ? 28 : 18.0D;
            double yDown = 140.0D;
            double size = 10.0D;
            drawVerticalLine(xLeft + size / 2.0D, yUp, size / 2.0D, thickness, color);
            this.drawHorizontalLine(xLeft, yUp + size - 1, size, thickness, color);
            drawVerticalLine(xRight - size / 2.0D, yUp, size / 2.0D, thickness, color);
            this.drawHorizontalLine(xRight, yUp + size - 1, size, thickness, color);
            drawVerticalLine(xLeft + size / 2.0D, yDown, size / 2.0D, thickness, color);
            this.drawHorizontalLine(xLeft, yDown - size + 1, size, thickness, color);
            drawVerticalLine(xRight - size / 2.0D, yDown, size / 2.0D, thickness, color);
            this.drawHorizontalLine(xRight, yDown - size + 1, size, thickness, color);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.disableBlend();
            GL11.glDisable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glNormal3f(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        }
    }

    private void render(Entity entity) {
        if (AntiBot.isServerBot(entity)) {
            return;
        }
        RenderManager renderManager = mc.getRenderManager();
        Vec3f offset = entity.interpolate(mc.timer.renderPartialTicks).sub(entity.getPos()).add(0.0D, 0.1D, 0.0D);
        if (!entity.isInvisible() || (entity.isInvisible() && invisible.getValue())) {
            AxisAlignedBB bb = entity.getEntityBoundingBox().offset(offset.getX() - RenderManager.renderPosX,
                    offset.getY() - RenderManager.renderPosY, offset.getZ() - RenderManager.renderPosZ);
            this.points.get(0).setX(bb.minX).setY(bb.minY).setZ(bb.minZ);
            this.points.get(1).setX(bb.maxX).setY(bb.minY).setZ(bb.minZ);
            this.points.get(2).setX(bb.maxX).setY(bb.minY).setZ(bb.maxZ);
            this.points.get(3).setX(bb.minX).setY(bb.minY).setZ(bb.maxZ);
            this.points.get(4).setX(bb.minX).setY(bb.maxY).setZ(bb.minZ);
            this.points.get(5).setX(bb.maxX).setY(bb.maxY).setZ(bb.minZ);
            this.points.get(6).setX(bb.maxX).setY(bb.maxY).setZ(bb.maxZ);
            this.points.get(7).setX(bb.minX).setY(bb.maxY).setZ(bb.maxZ);
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
        if (AntiBot.isServerBot(entity) || Objects.requireNonNull(ModuleManager.getModuleByClass(NameTag.class)).isEnabled()) {
            return;
        }
        mc.fontRendererObj.drawCenteredString(
                FriendManager.isFriend(entity.getName()) ? "\u00a7b" + FriendManager.getAlias(entity.getName())
                        : entity.getName(),
                (int) (left + right) / 2, (int) (top - (float) mc.fontRendererObj.FONT_HEIGHT - 2.0F + 1.0F), -1);
        if (((EntityPlayer) entity).getCurrentEquippedItem() != null) {
            String stack = ((EntityPlayer) entity).getCurrentEquippedItem().getDisplayName();
            mc.fontRendererObj.drawCenteredString(stack, (int) (left + right) / 2, (int) bottom, -1);
        }

    }

    private void health(EntityLivingBase entity, float left, float top, float right, float bottom) {
        if (AntiBot.isServerBot(entity)) {
            return;
        }
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
        if (entity == mc.thePlayer) {
            return false;
        } else if (entity.getHealth() <= 0.0F) {
            return false;
        } else if (entity instanceof EntityPlayer) {
            return true;
        } else if (entity instanceof EntityAnimal) {
            return true;
        } else if (entity.isInvisible() && entity == mc.thePlayer) {
            return true;
        } else if (AntiBot.isServerBot(entity)) {
            return false;
        } else {
            return entity instanceof EntityMob;
        }
    }

    public enum ESPMode {
        New2D, Outline, Other2D
    }
}
