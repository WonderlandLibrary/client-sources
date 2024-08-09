package mathax.client.systems.hud.modules;

import mathax.client.utils.Utils;
import mathax.client.systems.hud.DoubleTextHudElement;
import mathax.client.systems.hud.HUD;

public class GameTimeHud extends DoubleTextHudElement {

    public GameTimeHud(HUD hud) {
        super(hud, "game-time", "Displays the in-game time.", true);
    }

    @Override
    protected String getLeft() {
        return "Game Time: ";
    }

    @Override
    protected String getRight() {
        if (isInEditor()) return "12:00:00";

        return Utils.getWorldTime();
    }
}
