package dev.excellent.client.screen.snow;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.GLUtils;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.time.TimerUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.ResourceLocation;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2f;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SnowRender implements IAccess {
    private ConcurrentLinkedQueue<Snow> snows;

    public SnowRender() {
        cppInit();
    }

    private void cppInit() {
        snows = new ConcurrentLinkedQueue<>();
    }

    public void init() {
        snows.clear();
        for (int snow = 0; snow < 250; snow++) {
            addSnow(scaled().y);
        }
    }

    public void renderSnow(MatrixStack matrixStack) {
        for (Snow snow : snows) {
            snow.animation.setDuration(500);
            snow.animation.run(snow.position.y < scaled().y - (scaled().y / 10) ? 255 : 0);

            if (snow.position.y > scaled().y || snow.position.y < 0) {
                snow.position.set((float) Mathf.getRandom(0, scaled().x), snow.scale);
                snow.velocity.set((float) ((-0.5 + Math.random())), (float) (0.5F + Math.random()));
            }
            snow.render(matrixStack);
        }
    }

    private void addSnow(double y) {
        int color = ColorUtil.multDark(-1, 0.25F + (float) (Math.random() * 0.75F));
        float scale = (float) (3 + Math.random() * 3);
        Snowflake snowflake = Snowflake.random();
        Vector2f position = new Vector2f((float) Mathf.getRandom(0, scaled().x), (float) Mathf.getRandom(0, y));
        Vector2f velocity = new Vector2f((float) ((-0.5 + Math.random())), (float) (0.5F + Math.random()));

        snows.add(new Snow(position.add(0, scale), velocity, color, scale, snowflake));
    }

    public void onClose() {
        snows.clear();
    }

    private static String getNamespace() {
        return excellent.getInfo().getNamespace();
    }

    @RequiredArgsConstructor
    private enum Snowflake {
        TYPE_1(new ResourceLocation(getNamespace(), "texture/snowflake/type_1.png")),
        TYPE_2(new ResourceLocation(getNamespace(), "texture/snowflake/type_2.png")),
        TYPE_3(new ResourceLocation(getNamespace(), "texture/snowflake/type_3.png")),
        TYPE_4(new ResourceLocation(getNamespace(), "texture/snowflake/type_4.png"));
        private final ResourceLocation location;
        private static final Random random = new Random();

        public static Snowflake random() {
            Snowflake[] values = Snowflake.values();
            int randomIndex = random.nextInt(values.length);
            return values[randomIndex];
        }
    }

    @Data
    private static class Snow implements IAccess {
        private final Vector2f position;
        private final Vector2f velocity;
        private final float scale;
        private final int color;
        private final Snowflake snowflake;
        public TimerUtil time = TimerUtil.create();
        public final Animation animation = new Animation(Easing.LINEAR, 500);
        private long previousTimeMillis = System.currentTimeMillis();

        public Snow(final Vector2f position, Vector2f velocity, int color, float scale, Snowflake snowflake) {
            this.position = position;
            this.velocity = velocity;
            this.color = color;
            this.scale = scale;
            this.snowflake = snowflake;
            time.reset();
        }

        public void render(MatrixStack matrixStack) {
            for (int i = 0; i <= time.elapsedTime(); i++) {
                this.position.x = (this.position.x() + this.velocity.x() / 20F);
                this.position.y = (this.position.y() + this.velocity.y() / 20F);

                if (this.position.x + scale >= scaled().x) velocity.mul(-1, 1).add(-0.1F, 0);
                if (this.position.x - scale <= 0) velocity.mul(-1, 1).add(0.1F, 0);
            }
            int color = ColorUtil.replAlpha(this.color, (int) Mathf.clamp(0, 255, animation.getValue()));
            RectUtil.bindTexture(snowflake.location);
            float angle = ((((System.currentTimeMillis() % (1000 * 1000)) * velocity.x) / 2) % 360);
            GLUtils.startRotate(position.x, position.y, angle);
            RectUtil.drawRect(matrixStack, position.x - scale, position.y - scale, position.x + scale, position.y + scale, color, color, color, color, true, true);
            GLUtils.endRotate();
            time.reset();
        }
    }

}
