package io.github.nevalackin.radium.utils.render;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class OGLUtils {

    private static final FloatBuffer windowPosition = GLAllocation.createDirectFloatBuffer(4);
    private static final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private static final FloatBuffer modelMatrix = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer projectionMatrix = GLAllocation.createDirectFloatBuffer(16);

    private OGLUtils() {
    }

    public static void startBlending() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void endBlending() {
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void startScissorBox(ScaledResolution sr,
                                       int x,
                                       int y,
                                       int width,
                                       int height) {
        int sf = sr.getScaleFactor();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x * sf, (sr.getScaledHeight() - (y + height)) * sf, width * sf, height * sf);
    }

    public static void startScissorBox(LockedResolution lr,
                                       int x,
                                       int y,
                                       int width,
                                       int height) {
        int sf = 2;
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x * sf, (lr.getHeight() - (y + height)) * sf, width * sf, height * sf);
    }

    public static Vector3f project2D(float x,
                                     float y,
                                     float z,
                                     int scaleFactor) {
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelMatrix);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionMatrix);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

        if (GLU.gluProject(x, y, z, modelMatrix, projectionMatrix, viewport, windowPosition)) {
            return new Vector3f(
                    windowPosition.get(0) / scaleFactor,
                    (Display.getHeight() - windowPosition.get(1)) / scaleFactor,
                    windowPosition.get(2));
        }

        return null;
    }

    public static void endScissorBox() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
