package de.tired.api.util.shader.renderapi;

import de.tired.api.extension.Extension;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AnimationUtil {

    public double getAnimationState(double animation, double finalState, double speed) {
        final float additional = (float) ((double) Extension.EXTENSION.getGenerallyProcessor().renderProcessor.delta * speed);
        if (animation < finalState) {
            return (Math.min(animation + (double) additional, finalState));
        } else {
            return (Math.max(animation - (double) additional, finalState));
        }
    }

}
