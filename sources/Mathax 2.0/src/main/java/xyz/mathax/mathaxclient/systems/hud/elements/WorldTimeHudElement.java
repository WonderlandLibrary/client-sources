package xyz.mathax.mathaxclient.systems.hud.elements;

import xyz.mathax.mathaxclient.systems.hud.DoubleTextHudElement;
import xyz.mathax.mathaxclient.systems.hud.Hud;
import xyz.mathax.mathaxclient.utils.Utils;

public class WorldTimeHudElement extends DoubleTextHudElement {
    WorldTimeHudElement(Hud hud) {
        super(hud, "World Time", "Shows current in-game world time.");
    }

    @Override
    protected String getLeft() {
        return name + ": ";
    }

    @Override
    protected String getRight() {
        return Utils.getWorldTime();
    }
}
