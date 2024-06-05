package xyz.vergoclient.util.animations;

// DO NOT SHARE, I WILL BE MOTHERFUCKING SUED

public class AnimationUtils {

    public static float rotateDirection = 0;
    public static boolean animationDone = false;

    public static double delta;

    public static float getAnimationState(float animation, float finalState, float speed) {
        final float add = (float) (delta * (speed / 1000f));
        if (animation < finalState) {
            if (animation + add < finalState) {
                animation += add;
            } else {
                animation = finalState;
            }
        } else if (animation - add > finalState) {
            animation -= add;
        } else {
            animation = finalState;
        }
        return animation;
    }

    public static float smoothAnimation(float ani, float finalState, float speed, float scale) {
        return getAnimationState(ani, finalState, (float) (Math.max(10, (Math.abs(ani - finalState)) * speed) * scale));
    }

    public static float getRotateDirection() {// AllitemRotate->Rotate
        rotateDirection = rotateDirection + (float) delta;
        if (rotateDirection > 360)
            rotateDirection = 0;
        return rotateDirection;
    }

    public enum Direction {
        FORWARDS(new int[]{0, 0}),
        BACKWARDS(new int[]{0, 0}),
        UP(new int[]{0, -1}),
        DOWN(new int[]{0, 1}),
        LEFT(new int[]{-1, 0}),
        RIGHT(new int[]{1, 0});

        private final int[] xy;

        Direction(int[] xy) {
            this.xy = xy;
        }

        public int[] getXy() {
            return xy;
        }

    }

}
