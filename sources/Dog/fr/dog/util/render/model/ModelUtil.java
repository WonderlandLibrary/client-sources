package fr.dog.util.render.model;

import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static net.minecraft.client.renderer.GlStateManager.disableCull;
import static net.minecraft.client.renderer.GlStateManager.enableCull;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glDisable;

public class ModelUtil {

    public static void draw3DSphere(Vec3 pos, float size, Color c){
        pos = VecUtil.fixVec3(pos);

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        disableCull();
        glDepthMask(false);

        glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);

        int numSegments = 20;
        int numStacks = 30;
        for (int i = 0; i < numStacks; i++) {
            double lat0 = Math.PI * (-0.5 + (double) (i - 1) / numStacks);
            double z0  = Math.sin(lat0);
            double zr0 =  Math.cos(lat0);

            double lat1 = Math.PI * (-0.5 + (double) i / numStacks);
            double z1 = Math.sin(lat1);
            double zr1 = Math.cos(lat1);

            glBegin(GL_TRIANGLE_STRIP);
            for (int j = 0; j <= numSegments; j++) {
                double lng = 2 * Math.PI * (double) (j - 1) / numSegments;
                double x = Math.cos(lng);
                double y = Math.sin(lng);

                glVertex3d(pos.xCoord + size * x * zr0, pos.yCoord + size * y * zr0, pos.zCoord + size * z0);
                glVertex3d(pos.xCoord + size * x * zr1, pos.yCoord + size * y * zr1, pos.zCoord + size * z1);
            }
            glEnd();
        }

        glEnable(GL11.GL_TEXTURE_2D);
        glEnable(GL11.GL_DEPTH_TEST);
        glDepthMask(true);
        enableCull();
        glDisable(GL11.GL_BLEND);
    }

}
