package com.alan.clients.util.render.particle;

import com.alan.clients.util.Accessor;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.vector.Vector2f;
import rip.vantage.commons.util.time.StopWatch;

import java.awt.*;

public class Particle implements Accessor {
    private final Vector2f position;
    private final Vector2f velocity;
    private final float scale;
    private final Color color;
    private Color baseColor, glowColor;
    public StopWatch stopWatch = new StopWatch(), time = new StopWatch();
    public float alpha;

    public Particle(final Vector2f position, final Color color, final Vector2f velocity, final float scale) {
        this.position = position;
        this.color = color;
        this.velocity = velocity;
        this.scale = scale;
        stopWatch.reset();
        alpha = color.getAlpha();
    }

    public Particle(final Vector2f position, final Vector2f velocity) {
        this.position = position;
        this.color = ColorUtil.withAlpha(ColorUtil.mixColors(getTheme().getFirstColor(), getTheme().getSecondColor(), Math.random()), (int) (Math.random() * 255));
        this.velocity = velocity;
        this.scale = (float) (2 + Math.random() * 3);
        stopWatch.reset();
        alpha = color.getAlpha();
    }

    public void render() {
        if (baseColor == null || glowColor == null) return;
        RenderUtil.roundedRectangle(position.x, position.y, scale, scale, scale / 2, baseColor);
    }

    public void bloom() {
        if (baseColor == null || glowColor == null) return;
        RenderUtil.roundedRectangle(position.x, position.y, scale, scale, scale / 2, glowColor);
    }

    public void update() {
        for (int i = 0; i <= stopWatch.getElapsedTime(); i++) {
            this.position.setX(this.position.getX() + this.velocity.getX() / 10f);
            this.position.setY(this.position.getY() + this.velocity.getY() / 10f);

            this.velocity.setX(this.velocity.getX() * 0.999f);
            this.velocity.setY(this.velocity.getY() * 0.999f);

//                this.velocity.setY(this.velocity.getY() + 0.005f);
        }

        glowColor = ColorUtil.withAlpha(color, (int) alpha * 3);
        baseColor = ColorUtil.withAlpha(color, (int) alpha);

        alpha = Math.max(alpha - stopWatch.getElapsedTime() / 18f, 0);

        stopWatch.reset();
    }
}
