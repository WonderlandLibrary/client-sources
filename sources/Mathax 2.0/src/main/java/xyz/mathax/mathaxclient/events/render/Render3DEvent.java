package xyz.mathax.mathaxclient.events.render;

import xyz.mathax.mathaxclient.renderer.Renderer3D;
import xyz.mathax.mathaxclient.utils.Utils;
import net.minecraft.client.util.math.MatrixStack;

public class Render3DEvent {
    private static final Render3DEvent INSTANCE = new Render3DEvent();

    public MatrixStack matrixStack;
    public Renderer3D renderer;
    public double frameTime;
    public float tickDelta;
    public double offsetX, offsetY, offsetZ;

    public static Render3DEvent get(MatrixStack matrixStack, Renderer3D renderer, float tickDelta, double offsetX, double offsetY, double offsetZ) {
        INSTANCE.matrixStack = matrixStack;
        INSTANCE.renderer = renderer;
        INSTANCE.frameTime = Utils.frameTime;
        INSTANCE.tickDelta = tickDelta;
        INSTANCE.offsetX = offsetX;
        INSTANCE.offsetY = offsetY;
        INSTANCE.offsetZ = offsetZ;
        return INSTANCE;
    }
}
