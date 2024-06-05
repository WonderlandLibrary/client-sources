package net.shoreline.client.impl.event.gui.hud;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;
import net.minecraft.client.util.math.MatrixStack;
import net.shoreline.client.mixin.gui.hud.MixinInGameHud;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see MixinInGameHud
 */
public class RenderOverlayEvent extends Event
{
    //
    private final MatrixStack matrices;


    /**
     *
     *
     * @param matrices
     */
    public RenderOverlayEvent(MatrixStack matrices)
    {
        this.matrices = matrices;
    }

    /**
     *
     *
     * @return
     */
    public MatrixStack getMatrices()
    {
        return matrices;
    }

    public static class Post extends RenderOverlayEvent
    {
        //
        private final float tickDelta;

        /**
         *
         * @param matrices
         * @param tickDelta
         */
        public Post(MatrixStack matrices, float tickDelta)
        {
            super(matrices);
            this.tickDelta = tickDelta;
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

    @Cancelable
    public static class StatusEffect extends RenderOverlayEvent
    {
        /**
         * @param matrices
         */
        public StatusEffect(MatrixStack matrices)
        {
            super(matrices);
        }
    }

    @Cancelable
    public static class ItemName extends RenderOverlayEvent
    {
        //
        private int x, y;

        /**
         * @param matrices
         */
        public ItemName(MatrixStack matrices)
        {
            super(matrices);
        }

        /**
         *
         * @param x
         */
        public void setX(int x)
        {
            this.x = x;
        }

        /**
         *
         * @param y
         */
        public void setY(int y)
        {
            this.y = y;
        }

        public boolean isUpdateXY()
        {
            return x != 0 && y != 0;
        }

        public int getX()
        {
            return x;
        }

        public int getY()
        {
            return y;
        }
    }

    @Cancelable
    public static class Fire extends RenderOverlayEvent
    {
        /**
         * @param matrices
         */
        public Fire(MatrixStack matrices)
        {
            super(matrices);
        }
    }

    @Cancelable
    public static class Water extends RenderOverlayEvent
    {
        /**
         * @param matrices
         */
        public Water(MatrixStack matrices)
        {
            super(matrices);
        }
    }

    @Cancelable
    public static class Block extends RenderOverlayEvent
    {
        /**
         * @param matrices
         */
        public Block(MatrixStack matrices)
        {
            super(matrices);
        }
    }

    @Cancelable
    public static class Spyglass extends RenderOverlayEvent
    {
        /**
         * @param matrices
         */
        public Spyglass(MatrixStack matrices)
        {
            super(matrices);
        }
    }

    @Cancelable
    public static class Pumpkin extends RenderOverlayEvent
    {
        /**
         * @param matrices
         */
        public Pumpkin(MatrixStack matrices)
        {
            super(matrices);
        }
    }

    @Cancelable
    public static class BossBar extends RenderOverlayEvent
    {
        /**
         * @param matrices
         */
        public BossBar(MatrixStack matrices)
        {
            super(matrices);
        }
    }

    @Cancelable
    public static class Frostbite extends RenderOverlayEvent
    {
        /**
         * @param matrices
         */
        public Frostbite(MatrixStack matrices)
        {
            super(matrices);
        }
    }
}
