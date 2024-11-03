package dev.stephen.nexus.utils.render;

import dev.stephen.nexus.utils.Utils;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

// kawase
public class W2SUtil implements Utils {
    public static Matrix4f matrixProject = new Matrix4f();
    public static Matrix4f matrixModel = new Matrix4f();
    public static Matrix4f matrixWorldSpace = new Matrix4f();

    public static Vec3d getCoords(final Vec3d vector) {
        final Camera camera = mc.getEntityRenderDispatcher().camera;
        final int displayHeight = mc.getWindow().getHeight();
        final int[] viewport = new int[4];

        GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);

        final Vector3f target = new Vector3f();

        final double deltaX = vector.x - camera.getPos().x;
        final double deltaY = vector.y - camera.getPos().y;
        final double deltaZ = vector.z - camera.getPos().z;

        final Vector4f transformedCoordinates = new Vector4f((float) deltaX, (float) deltaY, (float) deltaZ, 1.0F).mul(matrixWorldSpace);

        final Matrix4f matrixProj = new Matrix4f(matrixProject);
        final Matrix4f matrixModel = new Matrix4f(W2SUtil.matrixModel);

        matrixProj.mul(matrixModel).project(transformedCoordinates.x(), transformedCoordinates.y(), transformedCoordinates.z(), viewport, target);

        return new Vec3d(target.x / mc.getWindow().getScaleFactor(), (displayHeight - target.y) / mc.getWindow().getScaleFactor(), target.z);
    }
}