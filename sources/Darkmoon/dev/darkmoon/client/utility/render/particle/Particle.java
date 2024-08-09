package dev.darkmoon.client.utility.render.particle;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.math.MathUtility;
import dev.darkmoon.client.utility.Utility;
import dev.darkmoon.client.utility.render.RenderUtility;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.List;

public class Particle implements Utility {
    public float x;
    public float y;

    public Particle() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int scaledWidth = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledWidth());
        int scaledHeight = DarkMoon.getInstance().getScaleMath().calc(scaledResolution.getScaledHeight());
        this.x = MathUtility.randomizeFloat(0, scaledWidth);
        this.y = MathUtility.randomizeFloat(0, scaledHeight);
        motionY = MathUtility.randomizeFloat(-.1f, .1f);
        motionX = MathUtility.randomizeFloat(-.1f, .1f);
    }

    float motionX = 0, motionY = 0;

    public float mX, mY;

    public void draw(List<Particle> particles, int mouseX, int mouseY) {
        RenderUtility.drawRoundCircle(x, y, 5, 4, new Color(255, 255, 255, 150).getRGB());

        particles.forEach(particle -> {
            if (particle != this) {
                if (MathUtility.getDistance(x, y, particle.x, particle.y) < 50) {
                    RenderUtility.drawLine(x, y, particle.x, particle.y, 2, new Color(255, 255, 255, 15).getRGB());
                }
            }
        });
    }

    public void update(int mouseX, int mouseY, int scaledWidth, int scaledHeight) {
        x += motionX;
        y += motionY;

        motionX *= .99;
        motionY *= .99;


        if (Math.abs(motionX) < .1) {
            motionX += MathUtility.randomizeFloat(-.01f, .01f);
        }

        if (Math.abs(motionY) < .1) {
            motionY += MathUtility.randomizeFloat(-.01f, .01f);
        }

        if (Mouse.isButtonDown(0)) {
            if (MathUtility.getDistance(x, y, mouseX, mouseY) < 50) {
                motionX += (mouseX - x) / 500;
                motionY += (mouseY - y) / 500;
            }

        }

        if (x < 0) x = scaledWidth;
        if (x > scaledWidth) x = 0;
        if (y < 0) y = scaledHeight;
        if (y > scaledHeight) y = 0;
    }

}