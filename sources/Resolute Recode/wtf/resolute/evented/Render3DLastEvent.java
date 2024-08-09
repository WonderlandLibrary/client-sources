package wtf.resolute.evented;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.vector.Matrix4f;

public class Render3DLastEvent {
    private final WorldRenderer context;
    private final MatrixStack matrix;
    private final Matrix4f projectionMatrix;
    private final ActiveRenderInfo activeRenderInfo;
    private final float partialTicks;
    private final long finishTimeNano;
    public Type type;

    public WorldRenderer getContext() {
        return this.context;
    }

    public MatrixStack getMatrix() {
        return this.matrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public ActiveRenderInfo getActiveRenderInfo() {
        return this.activeRenderInfo;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public long getFinishTimeNano() {
        return this.finishTimeNano;
    }

    public Render3DLastEvent(WorldRenderer context, MatrixStack matrix, Matrix4f projectionMatrix, ActiveRenderInfo activeRenderInfo, float partialTicks, long finishTimeNano,Type type) {
        this.context = context;
        this.matrix = matrix;
        this.type = type;
        this.projectionMatrix = projectionMatrix;
        this.activeRenderInfo = activeRenderInfo;
        this.partialTicks = partialTicks;
        this.finishTimeNano = finishTimeNano;
    }
    public boolean isRender3D() {
        return this.type == Type.RENDER3D;
    }

    public boolean isRender2D() {
        return this.type == Type.RENDER2D;
    }
    public enum Type {
        RENDER3D, RENDER2D
    }
}
