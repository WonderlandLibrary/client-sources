package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 19:40
 */
public class FOVEvent extends Event {

    float modifierHand;

    public FOVEvent(float fovModifierHand) {
        this.modifierHand = fovModifierHand;
    }

    public float getModifierHand() {
        return modifierHand;
    }

    public void setModifierHand(float modifierHand) {
        this.modifierHand = modifierHand;
    }
}
