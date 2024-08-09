package wtf.resolute.evented;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.MainWindow;
import net.minecraft.util.math.vector.Matrix4f;
import wtf.resolute.evented.interfaces.Event;

public class EventRender extends Event {
    public float partialTicks;
    public MainWindow scaledResolution;
    public Type type;
    public MatrixStack matrixStack;
    public Matrix4f matrix;


    public EventRender(float partialTicks, MatrixStack stack, MainWindow scaledResolution, Type type,Matrix4f matrix) {
        this.partialTicks = partialTicks;
        this.scaledResolution = scaledResolution;
        this.matrixStack = stack;
        this.type = type;
        this.matrix = matrix;
    }

    public boolean isRender3D() {
        return this.type == Type.RENDER3D;
    }
    public boolean isRender3D2() {
        return this.type == Type.RENDER3D2;
    }

    public boolean isRender2D() {
        return this.type == Type.RENDER2D;
    }
    //    public boolean isPostRender2D() {
//        return this.type == Type.POSTRENDER2D;
//    }
    public boolean isPreRender2D() {
        return this.type == Type.PRERENDER2D;
    }

    public enum Type {
        RENDER3D,RENDER3D2,
        RENDER2D, PRERENDER2D
    }
}

