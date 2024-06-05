package net.shoreline.client.impl.event.world;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class SkyboxEvent extends Event
{
    private Vec3d color;

    public Vec3d getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        setColor(new Vec3d(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0));
    }

    public void setColor(Vec3d color)
    {
        this.color = color;
    }

    @Cancelable
    public static class Sky extends SkyboxEvent
    {

    }

    @Cancelable
    public static class Cloud extends SkyboxEvent
    {

    }

    @Cancelable
    public static class Fog extends SkyboxEvent
    {
        private final float tickDelta;

        public Fog(float tickDelta)
        {
            this.tickDelta = tickDelta;
        }

        public float getTickDelta()
        {
            return tickDelta;
        }
    }
}
