package dev.luvbeeq.baritone.api.utils;

import dev.luvbeeq.baritone.api.behavior.IBehavior;
import dev.luvbeeq.baritone.api.utils.input.Input;

/**
 * @author Brady
 * @since 11/12/2018
 */
public interface IInputOverrideHandler extends IBehavior {

    boolean isInputForcedDown(Input input);

    void setInputForceState(Input input, boolean forced);

    void clearAllKeys();
}
