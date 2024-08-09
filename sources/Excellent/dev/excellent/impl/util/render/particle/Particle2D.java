package dev.excellent.impl.util.render.particle;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.time.TimerUtil;
import lombok.Data;
import org.joml.Vector2f;

@Data
public class Particle2D implements IAccess {
    private final Vector2f position;
    private final Vector2f velocity;
    private final float scale;
    private final int color;
    public TimerUtil time = TimerUtil.create();
    public float alpha;

    public Particle2D(final Vector2f position, int color, Vector2f velocity, float scale) {
        this.position = position;
        this.color = color;
        this.velocity = velocity;
        this.scale = scale;
        time.reset();
        alpha = ColorUtil.alpha(color);
    }


    public void render() {

        for (int i = 0; i <= time.elapsedTime(); i++) {
            this.position.x = (this.position.x() + this.velocity.x() / 10f);
            this.position.y = (this.position.y() + this.velocity.y() / 10f);

            this.velocity.x = (this.velocity.x() * 0.999f);
            this.velocity.y = (this.velocity.y() * 0.999f);

//                this.velocity.setY(this.velocity.getY() + 0.005f);
        }

        alpha = Math.max(alpha - time.elapsedTime() / 20f, 0);

        time.reset();

    }
}