package net.minecraft.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import ru.FecuritySQ.shader.GaussianBlur;
import ru.FecuritySQ.utils.MathUtil;

import java.util.Random;

public class RenderSkybox
{
    private final Minecraft mc;
    private final RenderSkyboxCube renderer;
    private float time;

    public float animate = 0;

    public RenderSkybox(RenderSkyboxCube rendererIn)
    {
        this.renderer = rendererIn;
        this.mc = Minecraft.getInstance();
    }

    public void render(float deltaT, float alpha)
    {
        this.time += deltaT;

        this.renderer.render(this.mc, (float) (MathHelper.sin(this.time * 0.001F) * 5.0F + 0.0F), -this.time * 0.1F, alpha);
    }
}
