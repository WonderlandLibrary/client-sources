package zamorozka.event.events;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;
import zamorozka.event.Event;

public class RenderEvent extends Event {
    private final Tessellator tessellator;
    private final Vec3d renderPos;
    private final float partialTicks;

    public RenderEvent(Tessellator tessellator, Vec3d renderPos, float ticks) {
        super();
        this.tessellator = tessellator;
        this.renderPos = renderPos;
        partialTicks = ticks;
    }

    public Tessellator getTessellator() {
        return tessellator;
    }

    public BufferBuilder getBuffer() {
        return tessellator.getBuffer();
    }

    public Vec3d getRenderPos() {
        return renderPos;
    }

    public void setTranslation(Vec3d translation) {
    }

    public void resetTranslation() {
        setTranslation(renderPos);
    }

    public float getPartialTicks(){
        return partialTicks;
    }
}
