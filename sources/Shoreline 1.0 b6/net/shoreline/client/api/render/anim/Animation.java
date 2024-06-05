package net.shoreline.client.api.render.anim;

import net.minecraft.util.math.MathHelper;

/**
 * @author linus, Gavin
 * @since 1.0
 */
public class Animation {
    //
    private final long animationTime;
    private final Easing easing;
    //
    private long time;
    private boolean state;

    /**
     * @param easing
     * @param animationTime
     * @param state
     */
    public Animation(Easing easing, long animationTime, boolean state) {
        this.easing = easing;
        this.animationTime = animationTime;
        this.state = state;
    }

    /**
     * @param easing
     * @param animationTime
     */
    public Animation(Easing easing, long animationTime) {
        this(easing, animationTime, false);
    }

    /**
     * @param easing
     */
    public Animation(Easing easing) {
        this(easing, 300, false);
    }

    /**
     * @return
     */
    public float getScaledTime() {
        double linear = (System.currentTimeMillis() - time) / (float) animationTime;
        if (!state) {
            linear = 1.0 - linear;
        }
        return (float) MathHelper.clamp(easing.ease(linear), 0.0, 1.0);
    }

    /**
     * @return
     */
    public boolean getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(boolean state) {
        this.state = state;
        time = System.currentTimeMillis();
    }

    /**
     * @param state
     */
    public void setStateHard(boolean state) {
        this.state = state;
        time = 0;
    }
}
