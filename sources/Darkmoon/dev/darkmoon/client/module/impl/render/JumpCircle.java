package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.event.render.EventRender3D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.render.AntiAliasingUtility;
import dev.darkmoon.client.utility.render.animation.AnimationMath;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.GL_GREATER;

@ModuleAnnotation(name = "JumpCircle", category = Category.RENDER)
public class JumpCircle extends Module {
    public static ArrayList<Circle> circles = new ArrayList<>();

    @EventTarget
    public void onUpdate(EventUpdate event) {
        circles.removeIf(circle -> circle.age > 990);
    }

    @EventTarget
    public void onRender(EventRender3D eventRender3D) {
        for (Circle c : circles) {
            renderCircle(c, eventRender3D.getPartialTicks());
            c.age = AnimationMath.fast((float) c.age, 1000F, 2);
        }
    }

    private void renderCircle(Circle circle, float partialTicks) {
        if (circle.age >= 1000) return;
        double ix = -(mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * partialTicks);
        double iy = -(mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * partialTicks);
        double iz = -(mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * partialTicks);
        GlStateManager.pushMatrix();
        GL11.glDepthMask(false);
        GlStateManager.enableDepth();
        GlStateManager.translate(ix, iy, iz);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture2D();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        AntiAliasingUtility.hook(true, false, false);
        GlStateManager.alphaFunc(GL_GREATER, 0.0F);
        GL11.glBegin(GL11.GL_QUAD_STRIP);
        for (int i = 0; i <= 360; i += 1) {
            Color color = Arraylist.getColor(i);

            double x = Math.cos(i * Math.PI / 180);
            double z = Math.sin(i * Math.PI / 180);
            GL11.glColor4d(new Color(color.getRGB()).getRed() / 255f, new Color(color.getRGB()).getGreen() / 255f, new Color(color.getRGB()).getBlue() / 255f, 0);
            GL11.glVertex3d(circle.x + x * ((circle.age / 1000) * 1.5), circle.y, circle.z + z * ((circle.age / 1000) * 1.5));
            GL11.glColor4d(new Color(color.getRGB()).getRed() / 255f, new Color(color.getRGB()).getGreen() / 255f, new Color(color.getRGB()).getBlue() / 255f, 0.5 - circle.age / 2000f);
            GL11.glVertex3d(circle.x + x * (circle.age / 1000), circle.y, circle.z + z * (circle.age / 1000));
        }

        GL11.glEnd();
        GL11.glBegin(GL11.GL_QUAD_STRIP);
        for (int i = 0; i <= 360; i += 1) {
            Color color = Arraylist.getColor(i);
            double x = Math.cos(i * Math.PI / 180);
            double z = Math.sin(i * Math.PI / 180);
            GL11.glColor4d(new Color(color.getRGB()).getRed() / 255f, new Color(color.getRGB()).getGreen() / 255f, new Color(color.getRGB()).getBlue() / 255f, 0.9 - circle.age / 1100f);
            GL11.glVertex3d(circle.x + x * (circle.age / 1000), circle.y, circle.z + z * (circle.age / 1000));
            GL11.glColor4d(new Color(color.getRGB()).getRed() / 255f, new Color(color.getRGB()).getGreen() / 255f, new Color(color.getRGB()).getBlue() / 255f, 0);
            GL11.glVertex3d(circle.x + x * ((circle.age / 1000) * 0.5), circle.y, circle.z + z * ((circle.age / 1000) * 0.5));
        }
        GL11.glEnd();
        AntiAliasingUtility.unhook(true, false, false);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.resetColor();
        GL11.glDepthMask(true);
        GlStateManager.popMatrix();
    }

    public static class Circle {
        double x, y, z;
        double age;

        public Circle(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.age = 0;
        }
    }
}
