package net.shoreline.client.impl.shaders;

import net.minecraft.util.math.Vec2f;
import net.shoreline.client.api.render.shader.Program;
import net.shoreline.client.api.render.shader.Shader;
import net.shoreline.client.api.render.shader.Uniform;
import net.shoreline.client.util.Globals;

import java.awt.*;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author xgraza
 * @since 03/31/24
 */
public final class RoundedRectangleProgram extends Program implements Globals
{
    Uniform<Float> radius = new Uniform<>("radius");
    Uniform<Float> softness = new Uniform<>("softness");
    Uniform<Vec2f> size = new Uniform<>("size");
    Uniform<Color> color = new Uniform<>("color");

    public RoundedRectangleProgram() {
        super(new Shader("roundedrect.frag", GL_FRAGMENT_SHADER));
    }

    @Override
    public void initUniforms()
    {
        radius.init(id);
        softness.init(id);
        size.init(id);
        color.init(id);
    }

    @Override
    public void updateUniforms()
    {
        final float SCALE_FACTOR = (float)mc.getWindow().getScaleFactor();
        glUniform2f(size.getId(), size.get().x * SCALE_FACTOR, size.get().y * SCALE_FACTOR);
        glUniform4f(color.getId(), color.get().getRed() / 255.0f, color.get().getGreen() / 255.0f, color.get().getBlue() / 255.0f, color.get().getAlpha() / 255.0f);
        glUniform1f(radius.getId(), radius.get());
        glUniform1f(softness.getId(), softness.get());
    }

    public void setDimensions(final float width, final float height)
    {
        size.set(new Vec2f(width, height));
    }

    public void setColor(final Color rectColor)
    {
        color.set(rectColor);
    }

    public void setRadius(final float rectRadius)
    {
        radius.set(rectRadius);
    }

    public void setSoftness(final float edgeSoftness)
    {
        softness.set(edgeSoftness);
    }
}
