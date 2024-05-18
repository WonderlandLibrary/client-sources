package de.labystudio.capes;

import de.labystudio.labymod.LabyMod;
import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class Star
{
    private double x = 0.0D;
    private double y = 0.0D;
    private long duration = 0L;
    private int lastDuration = 700;
    private double motion = 0.0D;
    private double maxSize = 0.0D;
    private boolean killed = false;

    public Star(Random random)
    {
        this.x = random.nextDouble() * 400.0D;
        this.y = random.nextDouble() * 900.0D * 2.0D;
        this.duration = System.currentTimeMillis();
        this.motion = (random.nextDouble() - 0.5D) * 10.0D;
        this.maxSize = random.nextDouble() + 0.9D;
    }

    public void draw()
    {
        long i = System.currentTimeMillis() - this.duration;
        double d0 = (double)i / (double)this.lastDuration / this.maxSize;

        if (i >= (long)this.lastDuration)
        {
            this.killed = true;
        }
        else
        {
            GlStateManager.pushMatrix();
            GlStateManager.scale(5.0E-4D, 5.0E-4D, 5.0E-4D);
            GlStateManager.scale(d0, d0, d0);
            GlStateManager.enableBlend();
            GL11.glColor4f(255.0F, 255.0F, 255.0F, (float)(0.6000000238418579D - d0));
            GlStateManager.translate((this.x - 200.0D - (d0 + this.motion) * 100.0D) / d0, (this.y + 150.0D - d0 * 100.0D) / d0, -135.0D / d0);
            LabyMod.getInstance().draw.drawTexturedModalRect(0, 0, 0, 0, 240, 250);
            GlStateManager.disableBlend();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }

    public boolean isKilled()
    {
        return this.killed;
    }
}
