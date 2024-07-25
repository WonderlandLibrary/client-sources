package club.bluezenith.util.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

import java.util.function.Function;

public class Scroll {
    public float target, amountScrolled;
    public long start = System.currentTimeMillis();
    public float height;
    public float rawY;
    public float rawY2;
    public Scroll(float height){
        this.height = height;
        this.target = 0;
        this.rawY = 0;
        this.rawY2 = 0;
        this.amountScrolled = 0;
    }
    public void update(float height, float y, float y2) {
        this.rawY = y;
        this.rawY2 = y2;
        this.height = getHeightOrSomethingIdk(height, y, Math.min(y + height, y2));
        if (Mouse.hasWheel()) {
            float wheel = Mouse.getDWheel();
            if (wheel != 0) {
                if (wheel > 0) {
                    wheel = -1;
                } else if (wheel < 0) {
                    wheel = 1;
                }
                scrollTo((float) (getScrollStep() * wheel));
            }
        }
    }

    public void scrollTo(float value) {
        target = clamp(target + value, height);

        start = System.currentTimeMillis();
    }

    private float getHeightOrSomethingIdk(float height, float y, float y2) {
        float max = height - (y2 - y) + 4;
        if (max < 0)
            max /= 2;
        return max;
    }

    public float getAmountScrolled() {
        float[] tr = new float[]{this.target};
        amountScrolled = handleScrollingPosition(tr, this.amountScrolled, height, 20f / Minecraft.getDebugFPS(), (double) this.start, getScrollDuration());
        target = tr[0];
        amountScrolled = clamp(amountScrolled, height);
        target = clamp(target, height);

        return amountScrolled;
    }
    public static long getScrollDuration() {
        //return 800;
        return 600L;
    }

    public static double getScrollStep() {
        //return 19;
        return 56d;
    }

    public double getBounceBackMultiplier() {
        return .24;
    }
    public float handleScrollingPosition(float[] target, float scroll, float maxScroll, float delta, double start, double duration) {
        if (getBounceBackMultiplier() >= 0) {
            target[0] = clamp(target[0], maxScroll);
            if (target[0] < 0) {
                target[0] -= target[0] * (1 - getBounceBackMultiplier()) * delta / 3;
            } else if (target[0] > maxScroll) {
                target[0] = (float) ((target[0] - maxScroll) * (1 - (1 - getBounceBackMultiplier()) * delta / 3) + maxScroll);
            }
        } else
            target[0] = clamp(target[0], maxScroll, 0);
        return expoEase(scroll, target[0], Math.min((System.currentTimeMillis() - start) / duration * delta * 3, 1));
    }
    public static Function<Double, Double> getEasingMethod() {
        return v -> v;
    }
    public static float expoEase(float start, float end, double amount) {
        return start + (end - start) * getEasingMethod().apply(amount).floatValue();
    }

    public static double clamp(double v, double maxScroll) {
        return clamp(v, maxScroll, 300);
    }

    public static double clamp(double v, double maxScroll, double clampExtension) {
        return MathHelper.clamp(v, -clampExtension, maxScroll + clampExtension);
    }

    public static float clamp(float v, float maxScroll) {
        return clamp(v, maxScroll, 300);
    }

    public static float clamp(float v, float maxScroll, float clampExtension) {
        return MathHelper.clamp(v, -clampExtension, maxScroll + clampExtension);
    }
}
