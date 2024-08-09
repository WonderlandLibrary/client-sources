package ru.FecuritySQ.module.визуальные;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventHud;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionColor;
import ru.FecuritySQ.option.imp.OptionNumric;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;

public class Arrows extends Module {

    public OptionColor color = new OptionColor("Обычный цвет", new Color(255, 255, 255, 255));
    public OptionColor fcolor = new OptionColor("Цвет друзей", new Color(25, 227, 142, 255));
    public OptionNumric sizeValue = new OptionNumric("Размер", 0.8F, 0.4F, 1F, 0.05F);
    public OptionNumric rangeSizeValue = new OptionNumric("Растояние", 20F, 10F, 50F, 1F);

    public Arrows() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
        addOption(color);
        addOption(fcolor);
        addOption(sizeValue);
        addOption(rangeSizeValue);
    }

    @Override
    public void event(Event event) {
        if(event instanceof EventHud && isEnabled()) {

            float size = 50;
            float xOffset = mc.getMainWindow().getScaledWidth() / 2F - 24.5F;
            float yOffset = mc.getMainWindow().getScaledHeight() / 2F - 25.2F;
            mc.world.getPlayers().forEach(entity -> {
                Color col = FecuritySQ.get().getFriendManager().isFriend(entity.getName().getString()) ? fcolor.get() : color.get();

                if (entity != mc.player) {
                    GlStateManager.pushMatrix();
                    GlStateManager.disableBlend();
                    double x = entity.lastTickPosX + (entity.getPosX() - entity.lastTickPosX) * mc.getRenderPartialTicks() - mc.getRenderManager().renderPosX();
                    double z = entity.lastTickPosZ + (entity.getPosZ() - entity.lastTickPosZ) * mc.getRenderPartialTicks() - mc.getRenderManager().renderPosZ();
                    double cos = Math.cos(mc.player.rotationYaw * (Math.PI * 2 / 360));
                    double sin = Math.sin(mc.player.rotationYaw * (Math.PI * 2 / 360));
                    double rotY = -(z * cos - x * sin);
                    double rotX = -(x * cos + z * sin);

                    float angle = (float) (Math.atan2(rotY, rotX) * 180 / Math.PI);
                    double xPos = ((rangeSizeValue.get()) * Math.cos(Math.toRadians(angle))) + xOffset + size / 2;
                    double y = ((rangeSizeValue.get()) * Math.sin(Math.toRadians(angle))) + yOffset + size / 2;
                    GL11.glPushMatrix();
                    GlStateManager.translated(xPos - 6, y + 4, 0);

                    Fonts.MCR8.drawCenteredString(new MatrixStack(), String.format("%.1f", mc.player.getDistance(entity)) + "m", 7, 8, new Color(255, 255, 255,  180).getRGB());
                    GL11.glPopMatrix();

                    GlStateManager.translated(xPos, y, 0);

                    GlStateManager.rotatef(angle, 0, 0, 1);

                    GlStateManager.disableBlend();
                    GlStateManager.scaled(sizeValue.get(), sizeValue.get(), sizeValue.get());
                    RenderUtil.drawTriangle(0 - 5F, 0F, 5F, 10F, col.brighter().getRGB(), col.darker().getRGB());
                    GlStateManager.enableBlend();

                    GlStateManager.popMatrix();
                }
            });

        }

    }

    public static void drawTriangle(int red, int green, int blue) {
        int alpha = 255;
        int red_2 = Math.max(red - 40, 0);
        int green_2 = Math.max(green - 40, 0);
        int blue_2 = Math.max(blue - 40, 0);
        float width = 6, height = 12;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor4f(red / 255f, green / 255f, blue / 255f, alpha / 255f);
        GL11.glVertex2d(0, 0 - height);
        GL11.glVertex2d(0 - width, 0);
        GL11.glVertex2d(0, 0 - 3);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor4f(red_2 / 255f, green_2 / 255f, blue_2 / 255f, alpha / 255f);
        GL11.glVertex2d(0, 0 - height);
        GL11.glVertex2d(0, 0 - 3);
        GL11.glVertex2d(0 + width, 0);
        GL11.glEnd();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

    }

}
