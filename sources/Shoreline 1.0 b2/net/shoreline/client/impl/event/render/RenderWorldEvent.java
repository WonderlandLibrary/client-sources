package net.shoreline.client.impl.event.render;

import net.shoreline.client.api.event.Event;
import net.minecraft.client.util.math.MatrixStack;

/**
 *
 * @author linus
 * @since 1.0
 */
public class RenderWorldEvent extends Event
{
    //
    private final MatrixStack matrices;
    private final float tickDelta;

    /**
     *
     *
     * @param matrices
     */
    public RenderWorldEvent(MatrixStack matrices, float tickDelta)
    {
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }

    /**
     *
     * @return
     */
    public MatrixStack getMatrices()
    {
        return matrices;
    }

    /**
     *
     * @return
     */
    public float getTickDelta()
    {
        return tickDelta;
    }
}
