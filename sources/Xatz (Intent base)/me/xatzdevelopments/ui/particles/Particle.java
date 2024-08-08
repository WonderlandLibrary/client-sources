package me.xatzdevelopments.ui.particles;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.ScaledResolution2;

import java.util.Random;

public class Particle {

    public float x, y, radius, speed, ticks, opacity;

    public Particle(ScaledResolution2 sr, float radius, float speed) {
        x = new Random().nextFloat() * sr.getScaledWidth();
        y = new Random().nextFloat() * sr.getScaledHeight();
        ticks = new Random().nextFloat() * sr.getScaledHeight() / 2;
        this.radius = radius;
        this.speed = speed;
    }
}
