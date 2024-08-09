package wtf.resolute.evented;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorldEvent2 {
    private MatrixStack stack;
    private float partialTicks;
    public Type type;
    public WorldEvent2(MatrixStack stack, float partialTicks)
    {
        this.type = type;
        this.stack = stack;
        this.partialTicks = partialTicks;
    }
    public boolean isRender3D2() {
        return this.type == Type.RENDER3D2;
    }
    public enum Type {
        RENDER3D,RENDER3D2,
        RENDER2D, PRERENDER2D
    }
}
