package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.mixin.MinecraftClientAccessor;
import xyz.mathax.mathaxclient.systems.hud.DoubleTextHudElement;
import xyz.mathax.mathaxclient.systems.hud.Hud;

public class FpsHudElement extends DoubleTextHudElement {
    public FpsHudElement(Hud hud) {
        super(hud, "FPS", "Displays your FPS.");
    }

    @Override
    protected String getLeft() {
        return name + ": ";
    }

    @Override
    protected String getRight() {
        return Integer.toString(MinecraftClientAccessor.getFps());
    }
}
