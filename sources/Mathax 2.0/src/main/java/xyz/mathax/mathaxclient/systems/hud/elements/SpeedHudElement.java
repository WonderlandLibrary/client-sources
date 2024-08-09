package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.systems.hud.DoubleTextHudElement;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.utils.Utils;

public class SpeedHudElement extends DoubleTextHudElement {
    public SpeedHudElement(Hud hud) {
        super(hud, "Speed", "Displays your horizontal speed.");
    }

    @Override
    protected String getLeft() {
        return name + ": ";
    }

    @Override
    protected String getRight() {
        if (isInEditor()) {
            return "0";
        }

        return String.format("%.1f", Utils.getPlayerSpeed());
    }
}
