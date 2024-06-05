package net.shoreline.client.init;

import net.shoreline.client.impl.shaders.GradientProgram;

/**
 *
 *
 * @author 06ED
 * @since 1.0
 */
public class Programs
{
    public static GradientProgram GRADIENT;
    //

    public static void initPrograms()
    {
        GRADIENT = new GradientProgram();
    }
}
