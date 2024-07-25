package club.bluezenith.util.anim;

import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

import java.util.function.Function;

import static java.lang.Float.isNaN;
import static java.lang.Math.max;

public class ScrollV {
    public float target, amountScrolled;
    public long start = System.currentTimeMillis();
    public float minY;
    public float maxY;
    public float alpha = 0;
    private float counter = 0;

    public ScrollV(){
        this.minY = 0;
        this.maxY = 0;
        this.target = 0;
        this.amountScrolled = 0;
    }
    public void update(float y, float y2, float screenHeight) {
        this.minY = y;
        this.maxY = y2 - screenHeight;
        if(this.maxY < 0){
            this.minY = -this.minY;
            this.maxY = -this.maxY;
        }
        if (Mouse.hasWheel()) {
            float wheel = Mouse.getDWheel();
            if (wheel != 0) {
                alpha = 255;
                counter = 0;
                if (wheel > 0) {
                    wheel = -1;
                } else if (wheel < 0) {
                    wheel = 1;
                }
                scrollTo((float) (getScrollStep() * wheel));
            }else if(counter > RenderUtil.delta * 69){
                alpha = MathHelper.clamp(alpha - (RenderUtil.delta * 0.5f), 0, 255);
            }else{
                counter += RenderUtil.delta;
            }
        }
    }

    public void reset(){
        this.minY = 0;
        this.maxY = 0;
        this.amountScrolled = 0;
        this.target = 0;
    }

    public void scrollTo(float value) {
        this.clampTarget(target + value);

        start = System.currentTimeMillis();
    }

    protected void clampTarget(float target) {
        this.target = MathHelper.clamp(target, minY - 50, maxY + 50);
    }

    public float getAmountScrolled() {
        float delta = 20f / max(1, Minecraft.getDebugFPS());  //fixme this might have been returning NaN before (when fps drops to 0 because of some sort of freeze),
                                                              //fixme which'd lead to scrollable elements not displaying. (TODO: test)
                                                              //fixme though what I've done (max(1, fps)) might have fixed that problem.

        if (getBounceBackMultiplier() >= 0) {
            this.clampTarget(target);
            if (target < 0) {
                target -= target * getBounceBackMultiplier() * delta / 3;
            } else if (target > maxY) {
                float diff = target - maxY;
                target = (float) (diff * (1 - getBounceBackMultiplier() * delta / 3) + maxY);
            }
        } else {
            this.clampTarget(target);
        }

        amountScrolled = expoEase(this.amountScrolled, target, Math.min((System.currentTimeMillis() - start) / (getScrollDuration() + 0f) * delta * 3, 1));
        this.clampTarget(target);

        if(isNaN(amountScrolled)) //this sometimes happens to be NaN??
            this.reset();

        return amountScrolled;
    }

    private static long getScrollDuration() {
        //return 800;
        return 400L;
    }

    private static double getScrollStep() {
        //return 19;
        return 56d;
    }

    private double getBounceBackMultiplier() {
        double value = 0.14;
        return 1 - value;
    }

    private Function<Double, Double> getEasingMethod() {
        return v -> v;
    }
    private float expoEase(float start, float end, double amount) {
        return start + (end - start) * getEasingMethod().apply(amount).floatValue();
    }

    public float getMaxY() {
        return maxY;
    }

    public float getMinY() {
        return minY;
    }

    public float getPercent(){
        return 1 - ((this.getMaxY() - this.amountScrolled) / this.getMaxY());
    }
}
